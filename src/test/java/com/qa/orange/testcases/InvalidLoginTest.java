package com.qa.orange.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.qa.orange.baseclass.BaseClass;
import com.qa.orange.utilites.TestUtil;
import com.qa.orange.pages.LoginPage;


public class InvalidLoginTest extends BaseClass{
	LoginPage loginPage;
	@Test(priority=1,description="Valid Login")
	public void validLogin()
	{
		//TestUtil.pause(2);
		loginPage = new LoginPage(driver);
		 TestUtil.pause(2);
		 loginPage.enterUserName("Admin@gmail.com");
		 TestUtil.pause(2);
		 loginPage.enterPassword("admin@#$%123");
		 TestUtil.pause(2);
		 loginPage.clickOnLoginButton();
		 TestUtil.pause(2);
		 String InvalidUser = loginPage.verifyErrorMessage();
			Assert.assertEquals(InvalidUser, "Invalid credentials", "Invalid credentials validation message not matched");
			TestUtil.pause(5);
		 
		 
		 
		 /*test = extent.createTest("Valid Login");
		 TestUtil.pause(10);
		test.log(Status.INFO, "Enter valid UserName");
		logger.info("Enter valid UserName");
		TestUtil.pause(3);
		loginPage.enterUserName(userName);
		test.log(Status.INFO, "Enter valid Password");
		logger.info("Enter valid Password");
		loginPage.enterPassword(passWord);
		test.log(Status.INFO, "Click on Login Button");
		logger.info("Click on Login Button");
		loginPage.clickOnLoginButton();*/
	}

}
