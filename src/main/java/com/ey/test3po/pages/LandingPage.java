package com.ey.test3po.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ey.test3po.testbase.TestBase;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class LandingPage extends TestBase {

	public boolean appLogo()
	{
		boolean isdisplayed = driver.findElement(By.xpath(prop.getProperty("spalshScreenXpath"))).isDisplayed();
		
		return isdisplayed;
		
	}
}
