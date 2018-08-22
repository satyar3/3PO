package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

import io.appium.java_client.MobileBy;

public class EducationGoalActivityQuestionnairePage extends TestBase
{

	public static WebElement flag;

	public void fillGoalQuestionnaire(String goalname, String StartYear, String collegename, String yearlyexp, String collegeduration, String key)
	{

		// entering the details
		driver.findElement(By.id(prop.getProperty("educationgoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("educationgoalname"))).sendKeys(goalname);
		driver.hideKeyboard();
		driver.findElement(By.id(prop.getProperty("educationstartyr"))).click();
		String upd_StartYear = StartYear.substring(0, StartYear.length());
		// collegestartyear = upd_StartYear;
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"" + upd_StartYear + "\"))")).click();
		driver.findElement(By.id(prop.getProperty("educationcollegename"))).click();
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.eygsskpoc.ey3po:id/edt_collegeName_adp\")").click();

		capabilities.setCapability("unicodeKeyboard", "true");
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.eygsskpoc.ey3po:id/edt_collegeName_adp\")").sendKeys(collegename);

		try
		{
			flag = new WebDriverWait(driver, 5000).ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOf(driver.findElement(By.id(prop.getProperty("recyclerview")))));
			driver.findElement(By.xpath(prop.getProperty("newyorkuniversity"))).click();
		}
		catch (NoSuchElementException e)
		{
			driver.findElement(By.id(prop.getProperty("educationaddcollege"))).click();

		}
		String upd_yearlyexp = yearlyexp.substring(0, yearlyexp.length());

		// usergoalamount = upd_yearlyexp;
		if ((driver.findElement(By.id(prop.getProperty("educationgoalamount"))).getText()).length() <= 1)
		{
			do
			{
				driver.findElement(By.id(prop.getProperty("educationgoalamount"))).sendKeys(TestUtil.convNum(upd_yearlyexp) + "");
			}
			while (!driver.findElement(By.id(prop.getProperty("educationgoalamount"))).getText().equals("$" + TestUtil.convNum(upd_yearlyexp)));

			driver.hideKeyboard();
		}

		driver.findElement(By.id(prop.getProperty("educationcollegeduration"))).click();
		String upd_collegeduration = collegeduration.substring(0, collegeduration.length()) + " year";
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"" + upd_collegeduration + "\"))")).click();

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
	}

	public ArrayList<Object> getPageContentOfGoalQuestionnaire()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("educationgoalheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("educationgoalsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("educationgoalnameplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("educationgoalstartplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("educationgoalcollegenameplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("educationgoalyearlycollegeexpplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("educationgoalcollegedurationplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("educationgoalfield"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("educationpurchaseyrfield"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("educationcollegenamefield"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("educationgoalamountfield"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("educationrecurringfield"))).isDisplayed());

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		return pagecontent;
	}

	public ArrayList<Object> pageLevelErrorMsg()
	{

		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		ArrayList<Object> list = new ArrayList<Object>();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		return list;
	}

	public ArrayList<Object> fieldLevelErrorMsg()
	{

		ArrayList<Object> list = new ArrayList<Object>();

		// College Name missing
		driver.findElement(By.id(prop.getProperty("educationgoalname"))).clear();
		driver.findElement(By.id(prop.getProperty("educationgoalname"))).sendKeys("test");
		driver.hideKeyboard();
		driver.findElement(By.id(prop.getProperty("educationstartyr"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"2020\"))")).click();

		driver.findElement(By.id(prop.getProperty("educationgoalamount"))).sendKeys("1000");
		driver.hideKeyboard();
		driver.findElement(By.id(prop.getProperty("educationcollegeduration"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"4 years\"))")).click();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		// Goal Name missing
		driver.findElement(By.id(prop.getProperty("educationgoalname"))).clear();
		driver.hideKeyboard();
		driver.findElement(By.id(prop.getProperty("educationstartyr"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"2020\"))")).click();
		driver.findElement(By.id(prop.getProperty("educationcollegename"))).click();

		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.eygsskpoc.ey3po:id/edt_collegeName_adp\")").click();
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.eygsskpoc.ey3po:id/edt_collegeName_adp\")").sendKeys("test");
		driver.findElement(By.id(prop.getProperty("educationaddcollege"))).click();
		driver.findElement(By.id(prop.getProperty("educationgoalamount"))).sendKeys("1000");
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		// Yearly expense Name missing
		driver.findElement(By.id(prop.getProperty("educationgoalfield"))).clear();
		driver.findElement(By.id(prop.getProperty("educationgoalfield"))).sendKeys("test");
		driver.hideKeyboard();
		driver.findElement(By.id(prop.getProperty("educationstartyr"))).click();
		// String upd_StartYear = StartYear.substring(0, StartYear.length() - 2);
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"2020\"))")).click();

		driver.findElement(By.id(prop.getProperty("educationgoalamount"))).clear();
		driver.findElement(By.id(prop.getProperty("educationcollegeduration"))).click();
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"4 years\"))")).click();
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();

		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsgimage"))).isDisplayed());
		list.add(driver.findElement(By.id(prop.getProperty("pagelevelerrormsg"))).getText());

		// TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(),
		// Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		return list;
	}
}