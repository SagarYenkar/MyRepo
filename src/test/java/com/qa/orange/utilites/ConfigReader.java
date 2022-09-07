package com.qa.orange.utilites;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	File file;
	FileInputStream fis;
	Properties pro;

	

	public ConfigReader() {

		try {
			
			System.out.println(System.getProperty("user.dir") + "/config/config.properties");
			file = new File(System.getProperty("user.dir") + "/Config/config.properties");
			
			fis = new FileInputStream(file);
			pro = new Properties();
			pro.load(fis);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public String getUrl() {
		String url = pro.getProperty("baseUrl");
		return url;
	}
	
	public String getUserName() {
		String userName = pro.getProperty("Username");
		return userName;
	}
	
	public String getPassword() {
		String password = pro.getProperty("Password");
		return password;
	}
	
	public String getInvalidEmail() {
		String invalidEmail = pro.getProperty("InvaldEmail");
		return invalidEmail;
	}
	
	
	public String getInvalidPassword() {
		String InvalidPassword = pro.getProperty("InvalidPassword");
		return InvalidPassword;
	}
}
