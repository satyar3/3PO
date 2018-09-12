package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class OtherGoalInvSuggestionsPage extends TestBase
{

	public static String usercontribution;
	public ArrayList<Object> pagecontentafterclick = new ArrayList<Object>();

	public ArrayList<Object> getPageContentOfInvestmentSuggestion()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalinvsuggestionheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalinvsuggestionsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalinvsuggestionsuggestedplan"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalinvsuggestionssuggestedplansub"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("invsuggestiontextbox"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("invsuggestionchkbox"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("invsuggestiontextboxdescription"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("escimg"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("invsuggestionplaceholdertxbox"))).getText());

		String suggestedplantxt = driver.findElement(By.xpath(prop.getProperty("othergoalinvsuggestionsuggestedplan"))).getText();

		int dollarpos1 = suggestedplantxt.indexOf("$");
		int num1 = suggestedplantxt.indexOf(" ", dollarpos1);
		String annualincome = suggestedplantxt.substring(dollarpos1 + 1, num1);

		int dollarpos2 = suggestedplantxt.indexOf("$", dollarpos1 + 1);
		int num2 = suggestedplantxt.indexOf(" ", dollarpos2);
		String suggestedcontribution = suggestedplantxt.substring(dollarpos2 + 1, num2);

		int tillyrpos = suggestedplantxt.indexOf("till");
		int num3 = suggestedplantxt.indexOf(" ", tillyrpos);
		String savingtillyr = suggestedplantxt.substring(num3 + 1, num3 + 5);

		pagecontent.add(annualincome);
		pagecontent.add(suggestedcontribution);
		pagecontent.add(savingtillyr);

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		return pagecontent;
	}

	public void setMonthlyContribution(String amount, String key, boolean flag, String checkbox, String goalamount, String plannedcontribution, String plannedinvamount, String age)
	{
		String upd_amount = amount.substring(0, amount.length());

		usercontribution = upd_amount;

		driver.findElement(By.xpath(prop.getProperty("invsuggestionplaceholdertxbox"))).click();
		do
		{
			driver.findElement(By.xpath(prop.getProperty("plannedinvsuggestiontextbox"))).sendKeys(TestUtil.convNum(upd_amount) + "");
		}
		while (!driver.findElement(By.xpath(prop.getProperty("plannedinvsuggestiontextbox"))).getText().equals("$" + TestUtil.convNum(upd_amount)));

		if (checkbox.length() != 0)
		{
			driver.findElement(By.id("com.eygsskpoc.ey3po:id/war_checkbox")).click();
		}
		else
		{
			//driver.hideKeyboard();
			driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
		}

		if (Integer.parseInt(age) < 55)
		{
			if (Double.parseDouble(plannedcontribution) != 0)
			{
				pagecontentafterclick = getPageContentAfterClickOfInvestmentSuggestion(flag, goalamount);
			}
			else
			{
				pagecontentafterclick = getPageContentAfterClickZeroContribution(flag, goalamount);
			}
		}
		else if (Integer.parseInt(age) >= 55)
		{
			if (Double.parseDouble(plannedcontribution) != 0 || (Double.parseDouble(plannedinvamount) != 0 && Double.parseDouble(plannedcontribution) == 0))
			{
				pagecontentafterclick = getPageContentAfterClickOfInvestmentSuggestion(flag, goalamount);
			}
		}

		// driver.hideKeyboard();
		
		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
	}

	public static ArrayList<Object> getPageContentAfterClickOfInvestmentSuggestion(boolean flag, String goalamount)
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		String affordabilitytxt = driver.findElement(By.xpath(prop.getProperty("labellegandInvSuggestion"))).getText();

		pagecontent.add(affordabilitytxt);

		if (flag == true && Double.parseDouble(affordabilitytxt.replaceAll("[^0-9]", "")) < Double.parseDouble(goalamount))
		{
			String popuptxt = driver.findElement(By.xpath(prop.getProperty("popup"))).getText();
			int dollarpos2 = popuptxt.indexOf("$");
			int num2 = popuptxt.indexOf(" ", dollarpos2);
			String suggestedcontributioninpopup = popuptxt.substring(dollarpos2 + 1, num2);
			pagecontent.add(suggestedcontributioninpopup);
		}

		return pagecontent;
	}

	public ArrayList<Object> getPageContentOfInvestmentSuggestionMoreThan55(String plannedinvamount)
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalinvsuggestionheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalinvsuggestionsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalinvsuggestionsuggestedplan"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalinvsuggestionssuggestedplansub"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("invsuggestiontextbox"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("invsuggestionchkbox"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("invsuggestiontextboxdescription"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("escimg"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("invsuggestionplaceholdertxbox"))).getText());

		String suggestedplantxt = driver.findElement(By.xpath(prop.getProperty("largepurchasesuggestedplanMoreThan55"))).getText();
		int dollarpos1 = suggestedplantxt.indexOf("$");
		int num1 = suggestedplantxt.indexOf(" ", dollarpos1);

		String annualincome = suggestedplantxt.substring(dollarpos1 + 1, num1);
		int dollarpos2 = suggestedplantxt.indexOf("$", dollarpos1 + 1);
		int num2 = suggestedplantxt.indexOf(" ", dollarpos2);

		String suggestedcontribution = suggestedplantxt.substring(dollarpos2 + 1, num2);
		pagecontent.add(annualincome);
		pagecontent.add(suggestedcontribution);

		if (Double.parseDouble(plannedinvamount) > 0)
		{
			String affordabilitytxt = driver.findElement(By.xpath(prop.getProperty("labellegandInvSuggestion"))).getText();
			pagecontent.add(affordabilitytxt);
		}
		
		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		return pagecontent;
	}

	public static ArrayList<Object> getPageContentAfterClickZeroContribution(boolean flag, String goalamount)
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		String popuptxt = driver.findElement(By.xpath(prop.getProperty("popup"))).getText();
		int dollarpos2 = popuptxt.indexOf("$");
		int num2 = popuptxt.indexOf(" ", dollarpos2);
		String suggestedcontributioninpopup = popuptxt.substring(dollarpos2 + 1, num2);
		pagecontent.add(suggestedcontributioninpopup);

		return pagecontent;
	}
}