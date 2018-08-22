package com.ey.test3po.pages;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class EmergencyFundAnnualSpendingPage extends TestBase
{

	public static Logger log = Logger.getLogger(EmergencyFundAnnualSpendingPage.class);

	public ArrayList<Object> getPageContentOfAnnualSpending()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("emergencyfundannualspendingheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("annualspendingsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("emergencyfundannualspendingsuggestedplan"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("goalsubheader"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("emergencyannualspendinglagendtxt"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("labellegandsubtxt"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("anualspendingcontactbtn"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("haveqtnsannualspending"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("calladv"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("recommentassetallocation"))).getText());

		String screen_plansuggestiontxt = driver.findElement(By.xpath(prop.getProperty("emergencyfundannualspendingsuggestedplan"))).getText();

		if (!screen_plansuggestiontxt.startsWith("Let's do this!"))
		{
			String plansuggestiontxt = screen_plansuggestiontxt.replaceAll("[^0-9.m]", "");

			int startpos1 = plansuggestiontxt.indexOf("m");
			String goaltimescreen1 = plansuggestiontxt.substring(0, startpos1);
			pagecontent.add(goaltimescreen1);

			int startpos2 = plansuggestiontxt.indexOf("m", startpos1 + 1);
			String goaltimescreen2 = plansuggestiontxt.substring(startpos1 + 1, startpos2);
			pagecontent.add(goaltimescreen2);
		}
		else
		{
			String plansuggestiontxt = screen_plansuggestiontxt.replaceAll("[^0-9]", "");
			pagecontent.add(plansuggestiontxt);
		}

		String legandtxt = driver.findElement(By.xpath(prop.getProperty("emergencyannualspendinglagendtxt"))).getText();
		pagecontent.add(legandtxt);

		TestUtil.captureScreenShotForEachStep(Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[1].getMethodName());
		return pagecontent;
	}

	public void signUp()
	{
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
}