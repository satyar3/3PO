package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class EducationGoalImportancePage extends TestBase
{

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

		driver.findElement(By.id(EducationGoalImportancePage.riskXpath)).click();
		
		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
	}

	public ArrayList<Object> getPageContentOfGoalImportance()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("educationgoalriskscreenheader"))).getText());
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

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.findElement(By.id(prop.getProperty("okbutton"))).click();

		return list;

	}

}
