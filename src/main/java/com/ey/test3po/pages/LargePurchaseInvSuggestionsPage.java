package com.ey.test3po.pages;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class LargePurchaseInvSuggestionsPage extends TestBase
{

	public static Logger log = Logger.getLogger(LargePurchaseInvSuggestionsPage.class);

	public static String usercontribution;
	public ArrayList<Object> pagecontentafterclick = new ArrayList<Object>();

	public ArrayList<Object> getPageContentOfInvestmentSuggestion()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		log.info("Large Purchase Planned Contribution page Header is : " + driver.findElement(By.xpath(prop.getProperty("largepurchaseinvsuggestionheader"))).getText());
		log.info("Large Purchase Planned Contribution page Sub-header is : " + driver.findElement(By.xpath(prop.getProperty("largepurchaseinvsuggestionsubheader"))).getText());
		log.info("Large Purchase Planned Contribution page plan suggestion is displayed : " + driver.findElement(By.xpath(prop.getProperty("largepurchasesuggestedplan"))).isDisplayed());
		log.info("Large Purchase Planned Contribution page plan how much you can afford text is displayed : " + driver.findElement(By.xpath(prop.getProperty("suggestedplansub"))).getText());
		log.info("Large Purchase Planned Contribution page contributin suggestion box is displayed : " + driver.findElement(By.xpath(prop.getProperty("plannedinvsuggestiontextbox"))).isDisplayed());
		log.info("Large Purchase Planned Contribution page check box is displayed : " + driver.findElement(By.id(prop.getProperty("invsuggestionchkbox"))).isDisplayed());
		log.info("Large Purchase Planned Contribution page contribution textbox tip is displayed : " + driver.findElement(By.id(prop.getProperty("invsuggestiontextboxdescription"))).getText());
		log.info("Large Purchase Planned Contribution page image in the text box displayed : " + driver.findElement(By.id(prop.getProperty("escimg"))).isDisplayed());
		log.info("Large Purchase Planned Contribution page planned contribution placeholder text is displayed : " + driver.findElement(By.xpath(prop.getProperty("invsuggestionplaceholdertxbox"))).getText());

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseinvsuggestionheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseinvsuggestionsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchasesuggestedplan"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("suggestedplansub"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("plannedinvsuggestiontextbox"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("invsuggestionchkbox"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("invsuggestiontextboxdescription"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("escimg"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("invsuggestionplaceholdertxbox"))).getText());

		String suggestedplantxt = driver.findElement(By.xpath(prop.getProperty("largepurchasesuggestedplan"))).getText();

		int dollarpos1 = suggestedplantxt.indexOf("$");
		int num1 = suggestedplantxt.indexOf(" ", dollarpos1);
		String annualincome = suggestedplantxt.substring(dollarpos1 + 1, num1);
		log.info("Annual Income entered is : " + annualincome);

		int dollarpos2 = suggestedplantxt.indexOf("$", dollarpos1 + 1);
		int num2 = suggestedplantxt.indexOf(" ", dollarpos2);
		String suggestedcontribution = suggestedplantxt.substring(dollarpos2 + 1, num2);
		log.info("Suggesed monthly contribution is : " + suggestedcontribution);

		int tillyrpos = suggestedplantxt.indexOf("till");
		int num3 = suggestedplantxt.indexOf(" ", tillyrpos);
		String savingtillyr = suggestedplantxt.substring(num3 + 1, num3 + 5);
		log.info("Savings till year is : " + savingtillyr);

		pagecontent.add(annualincome);
		pagecontent.add(suggestedcontribution);
		pagecontent.add(savingtillyr);

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		return pagecontent;
	}

	public void setMonthlyContribution(String amount, String key, boolean flag, String checkbox, String goalamount, String plannedcontribution, String plannedinvamount, String age)
	{
		String upd_amount = amount.substring(0, amount.length());
		log.info("Entered monthly contribution is : " + upd_amount);
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
			driver.findElement(By.xpath(prop.getProperty("largepurchaseinvsuggestionheader"))).click();
		}

		// driver.hideKeyboard();

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

		// pagecontentafterclick = getPageContentAfterClickZeroContribution(flag, goalamount);

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

	public static ArrayList<Object> getPageContentAfterClickOfInvestmentSuggestion(boolean flag, String goalamount)
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		String affordabilitytxt = driver.findElement(By.xpath(prop.getProperty("labellegandInvSuggestion"))).getText();
		log.info("Based on the entered planned monthly amount, the affordability amount is : " + affordabilitytxt);
		pagecontent.add(affordabilitytxt);

		if (flag == true && Double.parseDouble(affordabilitytxt.replaceAll("[^0-9]", "")) < Double.parseDouble(goalamount))
		{
			String popuptxt = driver.findElement(By.xpath(prop.getProperty("popup"))).getText();
			int dollarpos2 = popuptxt.indexOf("$");
			int num2 = popuptxt.indexOf(" ", dollarpos2);
			String suggestedcontributioninpopup = popuptxt.substring(dollarpos2 + 1, num2);
			log.info("Suggested investment amount in the pop up is : " + suggestedcontributioninpopup);
			pagecontent.add(suggestedcontributioninpopup);
		}

		return pagecontent;
	}

	public ArrayList<Object> getPageContentOfInvestmentSuggestionMoreThan55(String plannedinvamount)
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		log.info("Large Purchase Planned Contribution page Header is : " + driver.findElement(By.xpath(prop.getProperty("largepurchaseinvsuggestionheader"))).getText());
		log.info("Large Purchase Planned Contribution page Sub-header is : " + driver.findElement(By.xpath(prop.getProperty("largepurchaseinvsuggestionsubheader"))).getText());
		log.info("Large Purchase Planned Contribution page plan suggestion is displayed : " + driver.findElement(By.xpath(prop.getProperty("largepurchasesuggestedplanMoreThan55"))).isDisplayed());
		log.info("Large Purchase Planned Contribution page plan how much you can afford text is displayed : " + driver.findElement(By.xpath(prop.getProperty("suggestedplansub"))).getText());
		log.info("Large Purchase Planned Contribution page contributin suggestion box is displayed : " + driver.findElement(By.xpath(prop.getProperty("plannedinvsuggestiontextbox"))).isDisplayed());
		log.info("Large Purchase Planned Contribution page check box is displayed : " + driver.findElement(By.id(prop.getProperty("invsuggestionchkbox"))).isDisplayed());
		log.info("Large Purchase Planned Contribution page contribution textbox tip is displayed : " + driver.findElement(By.id(prop.getProperty("invsuggestiontextboxdescription"))).getText());
		log.info("Large Purchase Planned Contribution page image in the text box displayed : " + driver.findElement(By.id(prop.getProperty("escimg"))).isDisplayed());
		log.info("Large Purchase Planned Contribution page planned contribution placeholder text is displayed : " + driver.findElement(By.xpath(prop.getProperty("invsuggestionplaceholdertxbox"))).getText());

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseinvsuggestionheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseinvsuggestionsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchasesuggestedplanMoreThan55"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("suggestedplansub"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("plannedinvsuggestiontextbox"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("invsuggestionchkbox"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("invsuggestiontextboxdescription"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("escimg"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("invsuggestionplaceholdertxbox"))).getText());

		String suggestedplantxt = driver.findElement(By.xpath(prop.getProperty("largepurchasesuggestedplanMoreThan55"))).getText();

		int dollarpos1 = suggestedplantxt.indexOf("$");
		int num1 = suggestedplantxt.indexOf(" ", dollarpos1);
		String annualincome = suggestedplantxt.substring(dollarpos1 + 1, num1);
		log.info("Annual Income entered is : " + annualincome);

		int dollarpos2 = suggestedplantxt.indexOf("$", dollarpos1 + 1);
		int num2 = suggestedplantxt.indexOf(" ", dollarpos2);
		String suggestedcontribution = suggestedplantxt.substring(dollarpos2 + 1, num2);
		log.info("Suggesed monthly contribution is : " + suggestedcontribution);

		pagecontent.add(annualincome);
		pagecontent.add(suggestedcontribution);

		if (Double.parseDouble(plannedinvamount) != 0)
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
		log.info("Suggested investment amount in the pop up is : " + suggestedcontributioninpopup);
		pagecontent.add(suggestedcontributioninpopup);

		return pagecontent;
	}
}
