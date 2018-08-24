package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

import io.appium.java_client.MobileBy;

public class UserProfilePage extends TestBase
{

	// public static Logger log = Logger.getLogger(UserProfilePage.class);

	// Entering the details
	public void fillUserProfileQuestionnaire(String annualIncome, String locaton, String age, String key)
	{

		/*
		 * log.info("Annual income of the user is : " + annualIncome);
		 * log.info("Zip code is : " + locaton); log.info("User age is : " + age);
		 */

		driver.findElement(By.id(prop.getProperty("annualIncome"))).click();
		String upd_annualIncome = annualIncome.substring(0, annualIncome.length());

		do
		{
			driver.findElement(By.id(prop.getProperty("annualIncome"))).sendKeys(TestUtil.convNum(upd_annualIncome) + "");
		}
		while (!driver.findElement(By.id(prop.getProperty("annualIncome"))).getText().equals("$" + TestUtil.convNum(upd_annualIncome)));

		driver.findElement(By.id(prop.getProperty("locationZip"))).clear();
		driver.findElement(By.id(prop.getProperty("locationZip"))).sendKeys(locaton);
		driver.hideKeyboard();
		driver.findElement(By.id("com.eygsskpoc.ey3po:id/edt_age")).click();
		String upd_age = age.substring(0, age.length());
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"" + upd_age + "\"))")).click();

		explicitWait(driver.findElement(By.id(prop.getProperty("continueButtonbyid"))));
		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
	}

	public ArrayList<Object> getPageContentOfUserProfile()
	{
		ArrayList<Object> screenconetent = new ArrayList<Object>();

		screenconetent.add(driver.findElement(By.xpath(prop.getProperty("userprofileheader"))).getText());
		screenconetent.add(driver.findElement(By.xpath(prop.getProperty("userprofilesubheader"))).getText());
		screenconetent.add(driver.findElement(By.id(prop.getProperty("annualIncome"))).isDisplayed());
		screenconetent.add(driver.findElement(By.id(prop.getProperty("locationZip"))).getText().isEmpty());
		screenconetent.add(driver.findElement(By.id(prop.getProperty("locationZip"))).isDisplayed());
		screenconetent.add(driver.findElement(By.id(prop.getProperty("userprofileage"))).isDisplayed());
		screenconetent.add(driver.findElement(By.xpath(prop.getProperty("annualincomeplaceholdertext"))).getText());
		screenconetent.add(driver.findElement(By.xpath(prop.getProperty("zipcodeplaceholdertext"))).getText());
		screenconetent.add(driver.findElement(By.xpath(prop.getProperty("ageplaceholdertext"))).getText());

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		return screenconetent;
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

		// Entering data into the fields except age field
		driver.findElement(By.id(prop.getProperty("annualIncome"))).clear();
		driver.findElement(By.id(prop.getProperty("annualIncome"))).sendKeys("10000");
		driver.findElement(By.id(prop.getProperty("locationZip"))).clear();
		driver.findElement(By.id(prop.getProperty("locationZip"))).sendKeys("22222");
		driver.hideKeyboard();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());

		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		// Entering data into the fields except amount field
		driver.findElement(By.id(prop.getProperty("annualIncome"))).clear();
		driver.findElement(By.id(prop.getProperty("locationZip"))).clear();
		driver.findElement(By.id(prop.getProperty("locationZip"))).sendKeys("11111");
		driver.hideKeyboard();
		driver.findElement(By.id(prop.getProperty("userprofileage"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"23\"))")).click();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		// Entering data into the fields except zip code field
		driver.findElement(By.id(prop.getProperty("annualIncome"))).clear();
		driver.findElement(By.id(prop.getProperty("annualIncome"))).sendKeys("10000");
		driver.findElement(By.id(prop.getProperty("locationZip"))).clear();
		driver.hideKeyboard();
		driver.findElement(By.id(prop.getProperty("userprofileage"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"23\"))")).click();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		return list;
	}
}