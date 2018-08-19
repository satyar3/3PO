package com.ey.test3po.pages;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;

public class SplashPage extends TestBase {

	public boolean appLogo()
	{
		boolean isdisplayed = driver.findElement(By.xpath(prop.getProperty("spalshScreenXpath"))).isDisplayed();		
		return isdisplayed;				
	}
}
