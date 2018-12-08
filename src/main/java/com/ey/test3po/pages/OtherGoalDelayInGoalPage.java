package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

import io.appium.java_client.MobileBy;

public class OtherGoalDelayInGoalPage extends TestBase
{

	public ArrayList<Object> pagecontentafterclick = new ArrayList<Object>();

	public ArrayList<Object> getPageContentOfDelayGoalScreen(String plannedcontributionamt, String plannedinvamount, String age)
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoaldelayheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoaldealyyr"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalplaneeddelaysuggestedplan"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoaldealyyearfield"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("delayyearplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("delaytxtbox"))).isDisplayed());

		if (Integer.parseInt(age) < 55)
		{
			if ((Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamount) != 0) || (Double.parseDouble(plannedcontributionamt) == 0 && Double.parseDouble(plannedinvamount) != 0))
			{
				pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoaldelaylabellegand"))).isDisplayed());
				pagecontent.add(driver.findElement(By.id(prop.getProperty("labellegandsubtxt"))).isDisplayed());
			}
		}
		else
		{
			if ((Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamount) != 0) || Double.parseDouble(plannedinvamount) == 0 && (Double.parseDouble(plannedcontributionamt) != 0))
			{
				pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoaldelaylabellegand"))).isDisplayed());
				pagecontent.add(driver.findElement(By.id(prop.getProperty("labellegandsubtxt"))).isDisplayed());
			}
		}

		String delayyeartxt = driver.findElement(By.xpath(prop.getProperty("othergoalplaneeddelaysuggestedplan"))).getText();
		String delayyr = delayyeartxt.replaceAll("[^0-9]", "");
		pagecontent.add(delayyr);

		if (Integer.parseInt(age) < 55)
		{
			if ((Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamount) != 0) || (Double.parseDouble(plannedcontributionamt) == 0 && Double.parseDouble(plannedinvamount) != 0))
			{
				String labellegandtxt = driver.findElement(By.xpath(prop.getProperty("othergoaldelaylabellegand"))).getText();
				pagecontent.add(labellegandtxt);
			}
		}
		else
		{
			if ((Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamount) != 0) || Double.parseDouble(plannedinvamount) == 0 && (Double.parseDouble(plannedcontributionamt) != 0))
			{
				String labellegandtxt = driver.findElement(By.xpath(prop.getProperty("othergoaldelaylabellegand"))).getText();
				pagecontent.add(labellegandtxt);
			}
		}

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		return pagecontent;
	}

	public void setGoalDelayDuration(String year, String key)
	{

		driver.findElement(By.xpath(prop.getProperty("delayyearplaceholdertxt"))).click();
		driver.findElement(By.xpath(prop.getProperty("delaytextboxafterclick"))).click();
		String upd_year = year.substring(0, year.length());
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"" + upd_year + "\"))")).click();
		driver.findElement(By.xpath(prop.getProperty("othergoaldelayheader"))).click();
		// driver.hideKeyboard();
		pagecontentafterclick = getPageContentAfterClickOfDelayGoalScreen();
		
		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
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

	public static ArrayList<Object> getPageContentAfterClickOfDelayGoalScreen()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		String affordabilitytxt = driver.findElement(By.xpath(prop.getProperty("othergoaldelaylabellegand"))).getText();
		pagecontent.add(affordabilitytxt);
		return pagecontent;
	}
}