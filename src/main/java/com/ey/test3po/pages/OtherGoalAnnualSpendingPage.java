package com.ey.test3po.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class OtherGoalAnnualSpendingPage extends TestBase
{
	
	public ArrayList<Object> getPageContentOfAnnualSpending(String planedcontribution, String plannedinvamount)
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();
		
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalannualspendingheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("annualspendingsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalannualspendingsuggestedplan"))).isDisplayed());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("goalsubheader"))).isDisplayed());
		
		try 
		{
			if(Double.parseDouble(planedcontribution) != 0 || Double.parseDouble(plannedinvamount) != 0)
			{
				pagecontent.add(driver.findElement(By.id(prop.getProperty("labellegandannualspending"))).isDisplayed());
				pagecontent.add(driver.findElement(By.id(prop.getProperty("labellegandsubtxt"))).isDisplayed());
			}
			else
			{
				pagecontent.add(true);
				pagecontent.add(true);
			}
		}
		catch(NumberFormatException e)
		{
			if(Double.parseDouble(plannedinvamount) != 0 || Double.parseDouble(planedcontribution) != 0)
			{
				pagecontent.add(driver.findElement(By.id(prop.getProperty("labellegandannualspending"))).isDisplayed());
				pagecontent.add(driver.findElement(By.id(prop.getProperty("labellegandsubtxt"))).isDisplayed());
			}
			else
			{
				pagecontent.add(true);
				pagecontent.add(true);
			}
		}
		pagecontent.add(driver.findElement(By.id(prop.getProperty("anualspendingcontactbtn"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("haveqtnsannualspending"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("calladv"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("recommentassetallocation"))).getText());
		
		String plansuggestiontxt = driver.findElement(By.xpath(prop.getProperty("othergoalannualspendingsuggestedplan"))).getText();
		if (!plansuggestiontxt.startsWith("Let's do this!")) 
		{
			int dolstartpos1 = plansuggestiontxt.indexOf("$");
			int dolendpos1 = plansuggestiontxt.indexOf("you", dolstartpos1);		
			String goalamtonscreen = plansuggestiontxt.substring(dolstartpos1+1, dolendpos1-2);
			pagecontent.add(goalamtonscreen);
		
			int dolstartpos2 = plansuggestiontxt.indexOf("$",dolstartpos1+1);
			String affordabilityamtonscreenheadr = plansuggestiontxt.substring(dolstartpos2+1, plansuggestiontxt.length());
			pagecontent.add(affordabilityamtonscreenheadr);
		}
		else 
		{
			String new_plansuggestiontxt = plansuggestiontxt.replaceAll("[^0-9]", "");
			pagecontent.add(new_plansuggestiontxt);
		}
		
		try 
		{
			if(Double.parseDouble(planedcontribution) != 0 || Double.parseDouble(plannedinvamount) != 0)
			{
				String legandtxt = driver.findElement(By.xpath(prop.getProperty("othergoalannualspendinglagendtxt"))).getText();
				pagecontent.add(legandtxt);
			}
			else
			{
				pagecontent.add("");
			}
		}
		catch(NumberFormatException e)
		{
			if(Double.parseDouble(plannedinvamount) != 0 || Double.parseDouble(planedcontribution) != 0)
			{
				String legandtxt = driver.findElement(By.xpath(prop.getProperty("othergoalannualspendinglagendtxt"))).getText();
				pagecontent.add(legandtxt);
			}
			else
			{
				pagecontent.add("");
			}
		}
		
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