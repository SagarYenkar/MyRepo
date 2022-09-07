package com.qa.orange.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.orange.baseclass.BaseClass;

public class LoginPage extends BaseClass {
	
	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@placeholder='Username']") WebElement txt_username;
	@FindBy(xpath="//input[@placeholder='Password']") WebElement txt_password; 
	@FindBy(xpath="//button[normalize-space()='Login']") WebElement btn_login;
	@FindBy(xpath="//p[@class='oxd-text oxd-text--p oxd-alert-content-text']") WebElement error_msg;
	
	public void enterUserName(String email)
	{
		PresenceOfElement(By.xpath("//input[@placeholder='Username']"), driver);
		txt_username.clear();
		txt_username.sendKeys(email);
	}
	
	public void enterPassword(String pass)
	{
		PresenceOfElement(By.xpath("//input[@placeholder='Password']"), driver);
		txt_password.clear();
		txt_password.sendKeys(pass);
	}
	
	public void clearPassword()
	{
		PresenceOfElement(By.xpath("//input[@placeholder='Password']"), driver);
		txt_password.clear();
	}
	
	public void clickOnLoginButton()
	{
		PresenceOfElement(By.xpath("//button[normalize-space()='Login']"), driver);
		btn_login.click();
	}
	
	
	public boolean loginButtonStatus()
	{
		PresenceOfElement(By.xpath("//button[normalize-space()='Login']"), driver);
		boolean loginButton =btn_login.isEnabled();
		return loginButton;
	}
	
	public String verifyErrorMessage()
	{
		PresenceOfElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']"), driver);
		String msg = error_msg.getText();
		return msg;
	}
	
	

}
