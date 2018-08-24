package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class LargePurchaseGoalImportancePage extends TestBase
{

	// public static Logger log =
	// Logger.getLogger(LargePurchaseGoalImportancePage.class);
	public static String riskXpath;

	public void riskFactor(String riskfactor, String key)
	{
		if (riskfactor.contains("extremely important"))
		{
			riskXpath = prop.getProperty("fullStartRisk");
		}
		else if (riskfactor.contains("not critical"))
		{
			riskXpath = prop.getProperty("halfstartRisk");
		}
		else if (riskfactor.contains("not important"))
		{
			riskXpath = prop.getProperty("noStartRisk");
		}

		driver.findElement(By.id(LargePurchaseGoalImportancePage.riskXpath)).click();
		// log.info("Goal Selected is : " +
		// driver.findElement(By.id(LargePurchaseGoalImportancePage.riskXpath)).getText());

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
	}

	public ArrayList<Object> getPageContentOfGoalImportance()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		/*
		 * log.info("Risk SCreen Header is : " +
		 * driver.findElement(By.xpath(prop.getProperty("largepurchaseriskscreenheader")
		 * )).getText()); log.info("Risk Screen Sub-header is : " +
		 * driver.findElement(By.xpath(prop.getProperty("riskscreensubheader"))).getText
		 * ()); log.info("Full Star risk : " +
		 * driver.findElement(By.id(prop.getProperty("fullStartRisk"))).getText());
		 * log.info("Half Star risk is : " +
		 * driver.findElement(By.id(prop.getProperty("halfstartRisk"))).getText());
		 * log.info("No Risk is : " +
		 * driver.findElement(By.id(prop.getProperty("noStartRisk"))).getText());
		 * log.info("Phone image is displayed : " +
		 * driver.findElement(By.xpath(prop.getProperty("phoneimg"))).isDisplayed());
		 * log.info("Have questions is displayed : " +
		 * driver.findElement(By.id(prop.getProperty("haveqtns"))).getText());
		 * log.info("Advisor call details displayed : " +
		 * driver.findElement(By.id(prop.getProperty("advisor"))).getText());
		 */

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseriskscreenheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("riskscreensubheader"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("fullStartRisk"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("halfstartRisk"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("noStartRisk"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("phoneimg"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("haveqtns"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("advisor"))).getText());

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
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
}