package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

import io.appium.java_client.MobileBy;

public class RetirementGoalActivityQuestionnairePage extends TestBase
{
	
	public static String usergoalamount;
	public static String retyr;
	
	public void goalJourney(String goalname, String age,String expperyear, String endyr, String incomePostRetirement, String key) 
	{

		// entering the details
		driver.findElement(By.id(prop.getProperty("retirementgoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("retirementgoalname"))).sendKeys(goalname);
		driver.hideKeyboard();
		driver.findElement(By.id(prop.getProperty("retirementgoalage"))).click();
		String upd_age = age.substring(0, age.length())+" year";
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\""+ upd_age + "\"))")).click();
		String upd_expperyear = expperyear.substring(0, expperyear.length());
		usergoalamount = upd_expperyear;
		
		do 
		{
			driver.findElement(By.id(prop.getProperty("retirementgoalperyrexp"))).sendKeys(TestUtil.convNum(upd_expperyear) + "");
		} 
		while (!driver.findElement(By.id(prop.getProperty("retirementgoalperyrexp"))).getText().equals("$" + TestUtil.convNum(upd_expperyear)));
		
		//driver.findElement(By.id(prop.getProperty("retirementgoalperyrexp"))).sendKeys(TestUtil.convNum(upd_expperyear)+"");
		driver.hideKeyboard();
		
		if(incomePostRetirement.length() != 0)
		{
			String upd_incomePostRetirement = incomePostRetirement.substring(0, incomePostRetirement.length());		
			do 
			{
				driver.findElement(By.id(prop.getProperty("retirementgoalexpincome"))).sendKeys(TestUtil.convNum(upd_incomePostRetirement) + "");
			} 
			while (!driver.findElement(By.id(prop.getProperty("retirementgoalexpincome"))).getText().equals("$" + TestUtil.convNum(upd_incomePostRetirement)));
		}
		
		
		if(driver.getKeyboard() != null)
		{
			driver.hideKeyboard();
		}
		
		//driver.findElement(By.id(prop.getProperty("retirementgoalexpincome"))).sendKeys(TestUtil.convNum(upd_incomePostRetirement)+"");
		//driver.hideKeyboard();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();	

	}
	
	public ArrayList<Object> getPageContent()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();
		
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoalheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoalsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoalplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoalyrplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoalafterretexpplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoalendyrplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoalincomeafterretplaceholdertxt"))).getText());		
		pagecontent.add(driver.findElement(By.id(prop.getProperty("retirementgoalname"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("retirementgoalage"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("retirementgoalperyrexp"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("retirementgoalduration"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("retirementgoalexpincome"))).isDisplayed());		
		pagecontent.add(driver.findElement(By.id(prop.getProperty("retirementgoalage"))).getText().isEmpty());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("retirementgoalduration"))).getText().isEmpty());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("retirementgoalperyrexp"))).getText().isEmpty());
		
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
		
		//Goal name is blank		
		driver.findElement(By.id(prop.getProperty("retirementgoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("retirementgoalage"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"65 years\"))")).click();
		driver.findElement(By.id(prop.getProperty("retirementgoalperyrexp"))).sendKeys("1000");
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
		
		list.add(driver.findElement(By.id("com.eygsskpoc.ey3po:id/exception_img_view")).isDisplayed());
		list.add(driver.findElement(By.id("com.eygsskpoc.ey3po:id/exception_txt_message")).getText());
		
		driver.findElement(By.id("com.eygsskpoc.ey3po:id/btn_ok")).click();
		
		//Goal name is blank		
		driver.findElement(By.id("com.eygsskpoc.ey3po:id/edt_goalname")).clear();
		driver.findElement(By.id("com.eygsskpoc.ey3po:id/edt_goalname")).sendKeys("test");
		driver.findElement(By.id("com.eygsskpoc.ey3po:id/edt_userretirementage")).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"65 years\"))")).click();
		driver.findElement(By.id(prop.getProperty("retirementgoalperyrexp"))).clear();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		return list;
	}
}