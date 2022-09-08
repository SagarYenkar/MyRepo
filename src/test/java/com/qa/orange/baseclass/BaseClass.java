package com.qa.orange.baseclass;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.qa.orange.utilites.ConfigReader;
import com.qa.orange.utilites.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	public int DRIVER_WAIT = 80;
	public static int note_data = 0;
	public static String suiteName = "";
	public static String testName = "";
	public String currentTest;
	protected static String screenshot_folder_path = null; // Path to screenshot folder
	ConfigReader con = new ConfigReader();
	TestUtil testutil = new TestUtil();
	public  WebDriver driver;
	public String baseURl = con.getUrl();
	public String userName = con.getUserName();
	public String passWord = con.getPassword();
	public String invalidEmail = con.getInvalidEmail();
	public String invalidPassWord = con.getInvalidPassword();
	
	public static Logger logger;
	public static  String userDir = System.getProperty("user.dir");
	public static String browserName = "";
	public static String browserVersion = "";
	public  static String testUrl ;
	public static HashMap<String,String> globalMap=new HashMap<String,String>();
	public static ArrayList<String> arrayList=new ArrayList<String>();
	DesiredCapabilities capability = null;
	public static String seleniumHub="192.168.43.176"; // Selenium hub IP
	public static String seleniumHubPort="5566"; // Selenium hub port
	public static  String targetBrowser; // Target browser
	public ThreadLocal<WebDriver> threadedDriver = new ThreadLocal<WebDriver>();
	public ThreadLocal<String> threadedBrowser= new ThreadLocal<String>();
	public ExtentHtmlReporter htmlReporter;
	    public ExtentReports extent;
	    public ExtentTest test;
	
	@BeforeClass(alwaysRun = true)
	public void setup(ITestContext testContext) throws MalformedURLException {
		
		URL remote_grid;
		remote_grid = new URL("http://" + seleniumHub + ":" + seleniumHubPort + "/wd/hub");
		String SCREENSHOT_FOLDER_NAME = "screenshots";		
		screenshot_folder_path = new File(SCREENSHOT_FOLDER_NAME).getAbsolutePath();
		suiteName = testContext.getSuite().getName();
        currentTest = testContext.getCurrentXmlTest().getName(); // get Name of current test.	
        targetBrowser = testContext.getCurrentXmlTest().getParameter("selenium.browser");
      		currentTest = testContext.getCurrentXmlTest().getName(); // get Name of current test.
		//currentTest = methodName.getRealClass().getSimpleName() + "." + methodName.getMethodName();
      		System.out.println("=========Browser"+targetBrowser);
      		log("current test- " +currentTest);
		log("Current Xml Suite is:---->" + suiteName);
		logger = Logger.getLogger("Base Class");
		// configure log4j properties file
		//PropertyConfigurator.configure("log4j.properties");
		if (targetBrowser == null || targetBrowser.contains("chrome"))
		{
			//setup the chromedriver using WebDriverManager
	        WebDriverManager.chromedriver().setup();
	        //Create driver object for Chrome
	        driver = new ChromeDriver();
	        threadedDriver.set(driver);
			logger.info("Chrome Browser Opened");
			driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			logger.info("PageLoad wait given");
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
			logger.info("Implicit wait given");
			driver.manage().timeouts().setScriptTimeout(TestUtil.SET_SCRIPT_TIMEOUT, TimeUnit.SECONDS);
			logger.info("Java Script wait given");
			driver.manage().window().maximize();
			logger.info("Maximize Window Size of chrome");
		}

		else if (targetBrowser.contains("firefox")) {
			//setup the firefoxdriver using WebDriverManager
			WebDriverManager.firefoxdriver().setup();
			//Create driver object for firefox
			driver = new FirefoxDriver();
			threadedDriver.set(driver);
			logger.info("FireFox Browser Opened");
			driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			logger.info("PageLoad wait given");
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
			logger.info("Implicit wait given");
			driver.manage().timeouts().setScriptTimeout(TestUtil.SET_SCRIPT_TIMEOUT, TimeUnit.SECONDS);
			logger.info("Java Script wait given");
			driver.manage().window().maximize();
			logger.info("Maximize Window Size of firefox");
		}
		
		else if (targetBrowser.contains("local")) {
			remote_grid = new URL("http://" + seleniumHub + ":" + seleniumHubPort + "/wd/hub");
			// We have to mention browser which we want to use for test execution
				capability=DesiredCapabilities.chrome();
			 
			// Set the platform where we want to run our test- we can use MAC and Linux and other platforms as well
			//cap.setPlatform(Platform.WINDOWS);
			 
			// Here you can use hub address, hub will take the responsibility to execute the test on respective node
			URL url=new URL("http://localhost:4444/wd/hub");
			 
			// Create driver with hub address and capability
			driver=new RemoteWebDriver(url, capability);
			threadedDriver.set(driver);
		}
		
		else if(targetBrowser.contains("headless"))
		{
			 WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions().setHeadless(true);
			//add the headless argument
	      //  options.addArguments("headless");
			options.addArguments("--disable-gpu");
			System.out.print("stmt1");
			options.addArguments("--no-sandbox");
			System.out.print("stmt2");
			options.addArguments("--start-maximized");
			System.out.print("stmt3");
			options.addArguments("--window-size=1325x744");
			System.out.print("stmt4");
	        driver = new ChromeDriver(options);
	    	System.out.print("stmt5");
	        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			logger.info("PageLoad wait given");
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
			logger.info("Implicit wait given");
			driver.manage().timeouts().setScriptTimeout(TestUtil.SET_SCRIPT_TIMEOUT, TimeUnit.SECONDS);
			logger.info("Java Script wait given");
			driver.manage().window().maximize();
			logger.info("Maximize Window Size in headless mode ");
		}
		
		driver.get(con.getUrl());
		logger.info("Url opened successfully");
		
	}

	 @BeforeTest
	    public void config()
	    {
	        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/ExtendReport.html");
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
	    }
	 @AfterTest
	    public void tearDown()
	    {
	        extent.flush();
	    }
	
	@AfterClass

	public void close() {
         TestUtil.pause(10);
		 driver.close();
     	// driver.quit();

	}
	public void testStepsLog(String msg) {
		
		System.err.println(msg);
		log(msg + "<br>");
	}
	
public static void log(String msg) {
		
		System.out.println("======" + msg + "======");
		Reporter.log("<br>" + msg);
	}
	
public static void PresenceOfElement( By locator,WebDriver driver) 
{	
	(new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(locator));

}
public void validLogin()
{
	 
	
	TestUtil.pause(2);
	PresenceOfElement(By.xpath("//input[@placeholder='Username']"), driver);
	WebElement txt_username = driver.findElement(By.xpath("//input[@placeholder='Username']"));
	txt_username.clear();
	txt_username.sendKeys(userName);
	TestUtil.pause(2);
	PresenceOfElement(By.xpath("//input[@placeholder='Password']"), driver);
	WebElement txt_password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
	txt_password.clear();
	txt_password.sendKeys(passWord);
	TestUtil.pause(2);
	PresenceOfElement(By.xpath("//button[normalize-space()='Login']"), driver);
	WebElement btn_login = driver.findElement(By.xpath("//button[normalize-space()='Login']"));
	btn_login.click();
	
	
}
}
