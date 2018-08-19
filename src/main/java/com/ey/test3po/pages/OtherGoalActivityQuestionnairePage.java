package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

import io.appium.java_client.MobileBy;

public class OtherGoalActivityQuestionnairePage extends TestBase
{
	
	public static String usergoalamount; 
	public static String goalduration;
	
	public void goalJourney(String goalname, String startyear, String goalamount, String duation, String key) {

		// entering the details
		driver.findElement(By.id(prop.getProperty("othergoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("othergoalname"))).sendKeys(goalname);		
		driver.findElement(By.id(prop.getProperty("othergoalpurchaseyr"))).click();		
		String upd_startyear = startyear.substring(0, startyear.length());		
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\""+ upd_startyear + "\"))")).click();		
		String upd_goalamount = goalamount.substring(0, goalamount.length());
		
		usergoalamount = upd_goalamount;		
		do
		{
			driver.findElement(By.id(prop.getProperty("othergoalamount"))).sendKeys(TestUtil.convNum(upd_goalamount)+"");
		}
		while(!driver.findElement(By.id(prop.getProperty("othergoalamount"))).getText().equals("$"+TestUtil.convNum(upd_goalamount)));		
		driver.hideKeyboard();		
		String upd_duation = duation.substring(0, duation.length())+" year";			
		goalduration = duation.substring(0, duation.length());		
		driver.findElement(By.id(prop.getProperty("othergoalage"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\""+ upd_duation + "\"))")).click();		
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();		
	}
	
	public ArrayList<Object> getPageContent()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();
		
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalstartyearplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalamountplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalamountdurationplaceholdertxt"))).getText());		
		pagecontent.add(driver.findElement(By.id(prop.getProperty("othergoalname"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("othergoalpurchaseyr"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("othergoalamount"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("othergoalage"))).isDisplayed());			
		
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
		
		// Year and Duration is blank
		driver.findElement(By.id(prop.getProperty("othergoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("othergoalname"))).sendKeys("test");
		driver.findElement(By.id(prop.getProperty("othergoalamount"))).sendKeys("1000"/* +"\n" */);
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();		

		// Goal Amount is blank
		driver.findElement(By.id(prop.getProperty("othergoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("othergoalname"))).sendKeys("test");
		driver.findElement(By.id(prop.getProperty("othergoalpurchaseyr"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator(	"new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"2020\"))")).click();
		driver.findElement(By.id(prop.getProperty("othergoalage"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"2 year\"))")).click();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();		
		// Goal Name is blank

		driver.findElement(By.id(prop.getProperty("othergoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("othergoalpurchaseyr"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"2020\"))")).click();
		driver.findElement(By.id(prop.getProperty("othergoalage"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"2 year\"))")).click();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();		

		return list;	
	}
}