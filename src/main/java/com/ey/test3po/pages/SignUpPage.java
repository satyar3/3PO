package com.ey.test3po.pages;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;

public class SignUpPage extends TestBase
{
	
	public static Logger log = Logger.getLogger(SignUpPage.class);
	
	public static ArrayList<Object> getPageContent()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();
		
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("signinheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("signupsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("signupname"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("signupemail"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("signuppassword"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("signupconfpassword"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("username"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("useremail"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("userpwd"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("userrepwd"))).isDisplayed());		
		
		return pagecontent;
	}
	
	public void signUp(String username, String email, String password, String repassword, String key)
	{
		log.info("Username is : "+username);
		log.info("E-mail is : "+email);
		log.info("Password is "+password);
				
		driver.findElement(By.id(prop.getProperty("username"))).sendKeys(username);
		driver.findElement(By.id(prop.getProperty("useremail"))).sendKeys(email);
		driver.findElement(By.id(prop.getProperty("userpwd"))).sendKeys(password);
		driver.findElement(By.id(prop.getProperty("userrepwd"))).sendKeys(repassword);		
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
	}

}
