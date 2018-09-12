package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class EmergencyFundPlannedInvPage extends TestBase
{

	public ArrayList<Object> pagecontentafterclick = new ArrayList<Object>();

	public ArrayList<Object> getPageContentOfPlannedInvestment()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("emegencyplannedinvheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("emergenctplannedinvsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("emergencyplannedinvsuggestedplan"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("emergencyplannedinvsub"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("invsuggestiontextbox"))).isDisplayed());

		String suggestcurrentassettxt = driver.findElement(By.xpath(prop.getProperty("emergencyplannedinvsuggestedplan"))).getText();

		int dollarpos1 = suggestcurrentassettxt.indexOf("$");
		int num1 = suggestcurrentassettxt.indexOf(" ", dollarpos1);
		String suggestcurrentasset = suggestcurrentassettxt.substring(dollarpos1 + 1, num1 - 1);

		pagecontent.add(suggestcurrentasset);

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		return pagecontent;
	}

	public void setMonthlyInvestment(String amount, String key)
	{

		String upd_amount = amount.substring(0, amount.length());

		driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).click();
		do
		{
			driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).clear();
			driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).sendKeys(TestUtil.convNum(upd_amount) + "");
		}
		while (!driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsuggestionamount"))).getText().equals("$" + TestUtil.convNum(upd_amount)));

		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
		// driver.hideKeyboard();
		pagecontentafterclick = getPageContentAfterClickOfPlannedInvestment();
		
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

	public static ArrayList<Object> getPageContentAfterClickOfPlannedInvestment()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		String affordabilitytxt = driver.findElement(By.xpath(prop.getProperty("emergencylabellegand"))).getText();
		pagecontent.add(affordabilitytxt);

		return pagecontent;
	}
}