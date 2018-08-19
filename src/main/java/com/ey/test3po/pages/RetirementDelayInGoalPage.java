package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;

import io.appium.java_client.MobileBy;

public class RetirementDelayInGoalPage extends TestBase
{
	public ArrayList<Object> pagecontentafterclick = new ArrayList<Object>();
	
	public ArrayList<Object> getPageContent()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();
		
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementdelayheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementdelaysubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementplaneeddelaysuggestedplan"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementdelaysub"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementdelayplaceholdertxt"))).getText());		
		pagecontent.add(driver.findElement(By.id(prop.getProperty("invsuggestiontextbox"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementdelaylabellegand"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("labellegandsubtxt"))).isDisplayed());
		
		String delayyeartxt = driver.findElement(By.xpath(prop.getProperty("retirementplaneeddelaysuggestedplan"))).getText();
		String delayyr = delayyeartxt.replaceAll("[^0-9]", "");
		pagecontent.add(delayyr);
		
		String labellegandtxt = driver.findElement(By.xpath(prop.getProperty("retirementdelaylabellegand"))).getText();
		pagecontent.add(labellegandtxt);	
		
		return pagecontent;
	}
	
	public void setdelayInGoal(String year, String key)
	{
		
		driver.findElement(By.xpath(prop.getProperty("retirementdelayplaceholdertxt"))).click();
		//driver.findElement(By.xpath(prop.getProperty("retirementdelaydropdown"))).click();		
		String upd_year = year.substring(0, year.length());
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\""	+ upd_year + "\"))")).click();
		driver.findElement(By.xpath(prop.getProperty("retirementdelayheader"))).click();
		//driver.hideKeyboard();
		pagecontentafterclick = getPageContentAfterClick();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
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
	
	public static ArrayList<Object> getPageContentAfterClick()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();		
		
		String affordabilitytxt =  driver.findElement(By.xpath(prop.getProperty("retirementdelaylabellegand"))).getText();
		pagecontent.add(affordabilitytxt);
		
		return pagecontent;
	}
}
