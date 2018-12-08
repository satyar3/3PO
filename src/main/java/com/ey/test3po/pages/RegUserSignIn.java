package com.ey.test3po.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ey.test3po.testbase.TestBase;

public class RegUserSignIn extends TestBase
{
	public void signIn(String email, String password)
	{
		driver.findElement(By.xpath(prop.getProperty("signInButton"))).click();
		
		driver.findElement(By.id(prop.getProperty("signinpageemail"))).clear();
		driver.findElement(By.id(prop.getProperty("signinpageemail"))).sendKeys(email);
		driver.findElement(By.id(prop.getProperty("signinpagepwd"))).sendKeys(password);
		driver.hideKeyboard();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();		
		new WebDriverWait(driver, 60).withMessage("Log in failed !!").until(ExpectedConditions.presenceOfElementLocated(By.id(prop.getProperty("hambergermenu"))));
	}
}
