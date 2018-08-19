package com.ey.test3po.pages;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

import io.appium.java_client.MobileBy;

public class LargePurchaseGoalActivityQuestionnairePage extends TestBase 
{

	public static Logger log = Logger.getLogger(LargePurchaseGoalActivityQuestionnairePage.class);

	public static String usergoalamount;
	public static String goalduration;

	// Entering the details
	public void goalJourney(String goalname, String purchasingyear, String goalamount, String duration, String key) 
	{

		log.info("Goal Name is : " + goalname);
		log.info("Goal Purchasing Year is : " + purchasingyear);
		log.info("Goal Amount is : " + goalamount);
		log.info("Goal Duration is : " + duration);

		driver.findElement(By.id(prop.getProperty("largepurchasegoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("largepurchasegoalname"))).sendKeys(goalname);
		driver.findElement(By.id(prop.getProperty("largepurchasepurchasingyear"))).click();
		String upd_purchasing_year = purchasingyear.substring(0, purchasingyear.length());
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\""+ upd_purchasing_year + "\"))")).click();
		String upd_goalamount = goalamount.substring(0, goalamount.length());

		usergoalamount = upd_purchasing_year;

		do 
		{
			driver.findElement(By.id(prop.getProperty("largepurchasegoalamount"))).sendKeys(TestUtil.convNum(upd_goalamount) + "");
		} 
		while (!driver.findElement(By.id(prop.getProperty("largepurchasegoalamount"))).getText().equals("$" + TestUtil.convNum(upd_goalamount)));

		driver.hideKeyboard();
		driver.findElement(By.id(prop.getProperty("largepurchaseage"))).click();
		String upd_duration = duration.substring(0, duration.length()) + " year";

		goalduration = duration.substring(0, duration.length());
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\""
						+ upd_duration + "\"))")).click();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
		log.info("Successfully filled all the quesntionnaire");
	}

	public ArrayList<Object> getPageContent() 
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		log.info("Large Purchase Goal Header is : "
				+ driver.findElement(By.xpath(prop.getProperty("largepurchaseheader"))).getText());
		log.info("Large Purchase Goal Sub-header is : "
				+ driver.findElement(By.xpath(prop.getProperty("largepurchasesubheader"))).getText());
		log.info("Large Purchase Goal Name field is displayed : "
				+ driver.findElement(By.id(prop.getProperty("largepurchasegoalname"))).isDisplayed());
		log.info("Large Purchase Goal Purchase year field is displayed : "
				+ driver.findElement(By.id(prop.getProperty("largepurchasepurchasingyear"))).isDisplayed());
		log.info("Large Purchase Goal Amount field is displayed : "
				+ driver.findElement(By.id(prop.getProperty("largepurchasegoalamount"))).isDisplayed());
		log.info("Large Purchase Goal Duration field is displayed : "
				+ driver.findElement(By.id(prop.getProperty("largepurchaseage"))).isDisplayed());
		log.info("Large Purchase Goal Name text is displayed : "
				+ driver.findElement(By.xpath(prop.getProperty("largepurchasegoalnameplaceholdertext"))).getText());
		log.info("Large Purchase Goal Purchase year text is displayed : "
				+ driver.findElement(By.xpath(prop.getProperty("largepurchasegoalyearplaceholdertext"))).getText());
		log.info("Large Purchase Goal Amount text is displayed : "
				+ driver.findElement(By.xpath(prop.getProperty("largepurchasegoalamountplaceholdertext"))).getText());
		log.info("Large Purchase Goal Duration text is displayed : "
				+ driver.findElement(By.xpath(prop.getProperty("largepurchasegoalduratonplaceholdertext"))).getText());

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchasesubheader"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("largepurchasegoalname"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("largepurchasepurchasingyear"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("largepurchasegoalamount"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("largepurchaseage"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchasegoalnameplaceholdertext"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchasegoalyearplaceholdertext"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchasegoalamountplaceholdertext"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchasegoalduratonplaceholdertext"))).getText());

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

	public ArrayList<Object> fieldLevelErrorMsg() {

		ArrayList<Object> list = new ArrayList<Object>();

		// Purchasing year is blank
		driver.findElement(By.id(prop.getProperty("largepurchasegoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("largepurchasegoalname"))).sendKeys("test");
		driver.findElement(By.id(prop.getProperty("largepurchasegoalamount"))).sendKeys("10000");
		driver.findElement(By.id(prop.getProperty("largepurchaseage"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"2 year\"))")).click();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		// Goal name is blank
		driver.findElement(By.id(prop.getProperty("largepurchasegoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("largepurchasepurchasingyear"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"2020\"))")).click();
		driver.findElement(By.id(prop.getProperty("largepurchasegoalamount"))).sendKeys("10000" /* + "\n" */);
		driver.findElement(By.id(prop.getProperty("largepurchaseage"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"2 year\"))")).click();

		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		// Goal amount is blank
		driver.findElement(By.id(prop.getProperty("largepurchasegoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("largepurchasegoalname"))).sendKeys("test");
		driver.findElement(By.id(prop.getProperty("largepurchasepurchasingyear"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"2020\"))")).click();
		driver.findElement(By.id(prop.getProperty("largepurchasegoalamount"))).clear();
		driver.findElement(By.id(prop.getProperty("largepurchaseage"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"2 year\"))")).click();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		return list;
	}
}