package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;

public class LandingPage extends TestBase
{
	public String validateUser(String name)
	{
		String usernametext = driver.findElement(By.id(prop.getProperty("homepageusername"))).getText();
		int statpos = usernametext.indexOf(" ");

		String username = usernametext.substring(statpos+1, usernametext.length() - 1);
		
		//System.out.println(driver.findElement(By.id(prop.getProperty("homemenu"))).isSelected());
		//System.out.println(driver.findElement(By.id(prop.getProperty("addgoalmenu"))).isSelected());
		//System.out.println(driver.findElement(By.id(prop.getProperty("tradeoffmenu"))).isSelected());

		return username;

	}

	public ArrayList<Object> presenceOfMenuElements()
	{
		ArrayList<Object> menuelements = new ArrayList<Object>();

		menuelements.add(driver.findElement(By.id(prop.getProperty("hambergermenu"))).isDisplayed());
		menuelements.add(driver.findElement(By.id(prop.getProperty("homemenu"))).isDisplayed());
		menuelements.add(driver.findElement(By.id(prop.getProperty("addgoalmenu"))).isDisplayed());
		menuelements.add(driver.findElement(By.id(prop.getProperty("tradeoffmenu"))).isDisplayed());

		return menuelements;
	}
}