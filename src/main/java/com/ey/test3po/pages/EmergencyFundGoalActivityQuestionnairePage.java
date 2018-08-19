package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;

import io.appium.java_client.MobileBy;

public class EmergencyFundGoalActivityQuestionnairePage extends TestBase 
{

	public static String monthsofsal;

	public void goalJourney(String goalname, String monthsofsaltosave, String key) 
	{

		// entering the details
		driver.findElement(By.id(prop.getProperty("emegencygoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("emegencygoalname"))).sendKeys(goalname);
		driver.hideKeyboard();
		driver.findElement(By.id(prop.getProperty("emergencygoalduration"))).click();
		String upd_monthsofsaltosave = monthsofsaltosave.substring(0, monthsofsaltosave.length()) + " month";

		monthsofsal = monthsofsaltosave.substring(0, monthsofsaltosave.length());

		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\""+ upd_monthsofsaltosave + "\"))")).click();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
	}

	public ArrayList<Object> getPageContent() 
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("emergencygoalheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("emergencygoalsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("emergencygoalnameplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("emergencygoaldurationplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("emegencygoalname"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("emergencygoalduration"))).isDisplayed());

		return pagecontent;
	}

	public ArrayList<Object> pageLevelErrorMsg() 
	{

		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
		
		ArrayList<Object> list = new ArrayList<Object>();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		return list;
	}

	public ArrayList<Object> fieldLevelErrorMsg() 
	{

		ArrayList<Object> list = new ArrayList<Object>();

		// Goal name is blank
		driver.findElement(By.id(prop.getProperty("emegencygoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		return list;
	}
}