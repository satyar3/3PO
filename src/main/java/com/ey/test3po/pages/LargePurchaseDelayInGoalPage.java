package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

import io.appium.java_client.MobileBy;

public class LargePurchaseDelayInGoalPage extends TestBase
{

	// public static Logger log = Logger.getLogger(LargePurchaseDelayInGoalPage.class);

	public ArrayList<Object> pagecontentafterclick = new ArrayList<Object>();

	public ArrayList<Object> getPageContentOfDelayGoalScreen(String plannedcontributionamt, String plannedinvamount, String age)
	{

		ArrayList<Object> pagecontent = new ArrayList<Object>();

		/*
		 * log.info("Large Purchase Delay Page Header : " +
		 * driver.findElement(By.xpath(prop.getProperty("largepurchasedelayingoalheader"
		 * ))).getText()); log.info("Large Purchase Delay Page Sub-header : " +
		 * driver.findElement(By.xpath(prop.getProperty(
		 * "largepurchasedelayingoalsubheader"))).getText());
		 * log.info("Large Purchase Goal Delay suggestion displayed : " +
		 * driver.findElement(By.xpath(prop.getProperty(
		 * "largepurchaselplaneeddelaysuggestedplan"))).isDisplayed());
		 * log.info("When would you like to make purchase is displayed : " +
		 * driver.findElement(By.xpath(prop.getProperty("largepurchasedelaysub"))).
		 * getText()); log.info("Delay year Placeholder text : " +
		 * driver.findElement(By.xpath(prop.getProperty("delayyearplaceholdertxt"))).
		 * getText()); log.info("Delay Year text field is displayed : " +
		 * driver.findElement(By.id(prop.getProperty("invsuggestiontextbox"))).
		 * isDisplayed());
		 * 
		 * if (Integer.parseInt(age) < 55) { if
		 * ((Double.parseDouble(plannedcontributionamt) != 0 &&
		 * Double.parseDouble(plannedinvamount) != 0) ||
		 * (Double.parseDouble(plannedcontributionamt) == 0 &&
		 * Double.parseDouble(plannedinvamount) != 0)) {
		 * log.info("Large Purchase Label Legand is displayed : " +
		 * driver.findElement(By.xpath(prop.getProperty("largepurchasedelaylabellegand")
		 * )).isDisplayed());
		 * log.info("Large Purchase Label Legend text is displayed : " +
		 * driver.findElement(By.id(prop.getProperty("labellegandsubtxt"))).isDisplayed(
		 * )); } } else { if ((Double.parseDouble(plannedcontributionamt) != 0 &&
		 * Double.parseDouble(plannedinvamount) != 0) ||
		 * Double.parseDouble(plannedinvamount) == 0 &&
		 * (Double.parseDouble(plannedcontributionamt) != 0)) {
		 * log.info("Large Purchase Label Legand is displayed : " +
		 * driver.findElement(By.xpath(prop.getProperty("largepurchasedelaylabellegand")
		 * )).isDisplayed());
		 * log.info("Large Purchase Label Legend text is displayed : " +
		 * driver.findElement(By.id(prop.getProperty("labellegandsubtxt"))).isDisplayed(
		 * )); } }
		 */

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchasedelayingoalheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchasedelayingoalsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaselplaneeddelaysuggestedplan"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchasedelaysub"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("delayyearplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("invsuggestiontextbox"))).isDisplayed());

		if (Integer.parseInt(age) < 55)
		{
			if ((Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamount) != 0) || (Double.parseDouble(plannedcontributionamt) == 0 && Double.parseDouble(plannedinvamount) != 0))
			{
				pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchasedelaylabellegand"))).isDisplayed());
				pagecontent.add(driver.findElement(By.id(prop.getProperty("labellegandsubtxt"))).isDisplayed());
			}
		}
		else
		{
			if ((Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamount) != 0) || Double.parseDouble(plannedinvamount) == 0 && (Double.parseDouble(plannedcontributionamt) != 0))
			{
				pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchasedelaylabellegand"))).isDisplayed());
				pagecontent.add(driver.findElement(By.id(prop.getProperty("labellegandsubtxt"))).isDisplayed());
			}
		}

		String delayyeartxt = driver.findElement(By.xpath(prop.getProperty("largepurchaselplaneeddelaysuggestedplan"))).getText();

		int pos1 = delayyeartxt.indexOf("by");
		int pos2 = delayyeartxt.indexOf("year", pos1);

		String delayyr = delayyeartxt.substring(pos1 + 4, pos2 - 1);
		// log.info("Suggested Delay year : " + delayyr);
		pagecontent.add(delayyr);

		if (Integer.parseInt(age) < 55)
		{
			if ((Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamount) != 0) || (Double.parseDouble(plannedcontributionamt) == 0 && Double.parseDouble(plannedinvamount) != 0))
			{
				String labellegandtxt = driver.findElement(By.xpath(prop.getProperty("largepurchasedelaylabellegand"))).getText();
				// log.info("Label legand text before refresh : " + labellegandtxt);
				pagecontent.add(labellegandtxt);
			}
		}
		else
		{
			if ((Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamount) != 0) || Double.parseDouble(plannedinvamount) == 0 && (Double.parseDouble(plannedcontributionamt) != 0))
			{
				String labellegandtxt = driver.findElement(By.xpath(prop.getProperty("largepurchasedelaylabellegand"))).getText();
				// log.info("Label legand text before refresh : " + labellegandtxt);
				pagecontent.add(labellegandtxt);
			}
		}

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		return pagecontent;
	}

	public void setGoalDelayDuration(String year, String key)
	{

		driver.findElement(By.xpath(prop.getProperty("delaytxtbox"))).click();

		String upd_year = year.substring(0, year.length());
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"" + upd_year + "\"))")).click();
		driver.findElement(By.xpath(prop.getProperty("largepurchasedelayingoalheader"))).click();
		// log.info("Delayed by years : " + upd_year);
		pagecontentafterclick = getPageContentAfterClickOfDelayGoalScreen();
		// driver.hideKeyboard();

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

		String affordabilitytxt = driver.findElement(By.xpath(prop.getProperty("largepurchasedelaylabellegand"))).getText();
		// log.info("Suggested affordability after page refresh : " + affordabilitytxt);
		pagecontent.add(affordabilitytxt);

		return pagecontent;
	}
}