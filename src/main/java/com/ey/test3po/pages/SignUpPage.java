package com.ey.test3po.pages;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class SignUpPage extends TestBase
{
	
	public static Logger log = Logger.getLogger(SignUpPage.class);
	
	public static ArrayList<Object> getPageContentOfSignUpScreen()
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
		
		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(),Thread.currentThread().getStackTrace()[1].getMethodName());
		return pagecontent;
	}
	
	public void fillSignUpDetails(String username, String email, String password, String repassword, String key)
	{
		log.info("Username is : "+username);
		log.info("E-mail is : "+email);
		log.info("Password is "+password);
				
		driver.findElement(By.id(prop.getProperty("username"))).sendKeys(username);
		driver.findElement(By.id(prop.getProperty("useremail"))).sendKeys(email);
		driver.findElement(By.id(prop.getProperty("userpwd"))).sendKeys(password);
		driver.findElement(By.id(prop.getProperty("userrepwd"))).sendKeys(repassword);	
		
		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(),Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
		
		//WebElement element = driver.findElement(By.xpath("//*[@resource-id='com.eygsskpoc.ey3po:id/txtUserName' and @text='Hi "+username+"!']"));
		
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@resource-id='com.eygsskpoc.ey3po:id/txtUserName' and @text='Hi "+username+"!']")));
	}

}
