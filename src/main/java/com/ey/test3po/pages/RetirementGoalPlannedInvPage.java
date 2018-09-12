package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class RetirementGoalPlannedInvPage extends TestBase
{
	public ArrayList<Object> pagecontentafterclick = new ArrayList<Object>();

	public ArrayList<Object> getPageContentOfPlannedInvestment(String plannedcontribution)
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoalplannedinvheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoalplannedinvsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementplannedinvsuggestedplan"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoalsub"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementplannedinvtextbox"))).isDisplayed());

		if (Double.parseDouble(plannedcontribution) > 0)
		{
			pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementplannedinvlabellegand"))).isDisplayed());
			pagecontent.add(driver.findElement(By.id(prop.getProperty("labellegandsubtxt"))).isDisplayed());
		}

		String suggestcurrentassettxt = driver.findElement(By.xpath(prop.getProperty("retirementplannedinvsuggestedplan"))).getText();

		int dollarpos1 = suggestcurrentassettxt.indexOf("$");
		int num1 = suggestcurrentassettxt.indexOf(" ", dollarpos1);
		String suggestcurrentasset = suggestcurrentassettxt.substring(dollarpos1 + 1, num1 - 1);
		pagecontent.add(suggestcurrentasset);

		int dollarpos2 = suggestcurrentassettxt.indexOf("$", dollarpos1 + 1);
		int num2 = suggestcurrentassettxt.indexOf(" ", dollarpos2);
		String suggestedcontribution = suggestcurrentassettxt.substring(dollarpos2 + 1, num2 - 1);
		pagecontent.add(suggestedcontribution);

		if (Double.parseDouble(plannedcontribution) > 0)
		{
			String affordabilitytxt = driver.findElement(By.xpath(prop.getProperty("retirementplannedinvlabellegand"))).getText();
			pagecontent.add(affordabilitytxt);
		}

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		return pagecontent;
	}

	public void setMonthlyInvestment(String amount, String key, boolean flag, String goalamount, String plannedinvamount, String plannedcontributionamount, String age)
	{

		driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).click();
		String upd_amount = amount.substring(0, amount.length());

		do
		{
			driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).clear();
			driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).sendKeys(TestUtil.convNum(upd_amount) + "");
		}
		while (!driver.findElement(By.xpath(prop.getProperty("retirementplannedinvtextbox"))).getText().equals("$" + TestUtil.convNum(upd_amount)));

		// driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).sendKeys(TestUtil.convNum(upd_amount)+"");
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
		// driver.hideKeyboard();

		if (Integer.valueOf(age) < 55)
		{
			if (Double.parseDouble(plannedinvamount) != 0 || (Double.parseDouble(plannedinvamount) == 0 && Double.parseDouble(plannedcontributionamount) != 0))
			{
				pagecontentafterclick = getPageContentAfterClickOfPlannedInvestment(flag, goalamount);
			}
		}
		else if (Integer.valueOf(age) >= 55)
		{
			if (Double.parseDouble(plannedinvamount) == 0)
			{
				pagecontentafterclick = getPageContentAfterClickZeroInvestment(flag, upd_amount);
			}
			else
			{
				pagecontentafterclick = getPageContentAfterClickOfPlannedInvestment(flag, goalamount);
			}
		}

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
	}

	public static ArrayList<Object> getPageContentAfterClickOfPlannedInvestment(boolean flag, String goalamount)
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		String affordabilitytxt = driver.findElement(By.xpath(prop.getProperty("retirementplannedinvlabellegand"))).getText();
		pagecontent.add(affordabilitytxt);

		if (flag == true && Double.parseDouble(affordabilitytxt.replaceAll("[^0-9.]", "")) < Double.parseDouble(goalamount.replaceAll("[^0-9.]", "")))
		{
			String popuptxt = driver.findElement(By.xpath(prop.getProperty("popup"))).getText();
			int dollarpos2 = popuptxt.indexOf("$");
			int num2 = popuptxt.indexOf("?", dollarpos2);
			String suggestedcontributioninpopup = popuptxt.substring(dollarpos2 + 1, num2);
			pagecontent.add(suggestedcontributioninpopup);
		}

		return pagecontent;
	}

	public static ArrayList<Object> getPageContentAfterClickZeroInvestment(boolean flag, String goalamount)
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		String popuptxt = driver.findElement(By.xpath(prop.getProperty("popup"))).getText();
		int dollarpos2 = popuptxt.indexOf("$");
		int num2 = popuptxt.indexOf("?", dollarpos2);
		String suggestedcontributioninpopup = popuptxt.substring(dollarpos2 + 1, num2);
		pagecontent.add(suggestedcontributioninpopup);
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

	public ArrayList<Object> getPageContentOfPlannedInvestmentMoreThan55()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoalplannedinvheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoalplannedinvsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementplannedinvsuggestedplanMoreThan55"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoalsub"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementplannedinvtextbox"))).isDisplayed());

		String suggestcurrentassettxt = driver.findElement(By.xpath(prop.getProperty("retirementplannedinvsuggestedplanMoreThan55"))).getText();
		String suggestcurrentasset = suggestcurrentassettxt.replaceAll("[^0-9]", "");
		pagecontent.add(suggestcurrentasset);

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		return pagecontent;
	}
}