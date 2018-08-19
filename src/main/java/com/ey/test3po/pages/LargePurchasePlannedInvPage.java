package com.ey.test3po.pages;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class LargePurchasePlannedInvPage extends TestBase{
	
	public static Logger log = Logger.getLogger(LargePurchasePlannedInvPage.class);
	
	public ArrayList<Object> pagecontentafterclick = new ArrayList<Object>();
		
	public ArrayList<Object> getPageContent()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();
		
		log.info("Large Purchase Planned Investment page Header is : "+driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvheader"))).getText());
		log.info("Large Purchase Planned Investment page Sub-header is : "+driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsubheader"))).getText());
		log.info("Large Purchase Planned Investment page suggested plan is displayed : "+driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsuggestedplan"))).isDisplayed());
		log.info("Large Purchase Planned Investment page investment is : "+driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsub"))).getText());
		log.info("Large Purchase Planned Investment page planned investment text box placeholder text : "+driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).getText());		
		log.info("Large Purchase Planned Investment page planned investment text box is displayed : "+driver.findElement(By.xpath(prop.getProperty("plannedinvtextbox"))).isDisplayed());
		log.info("Large Purchase Planned Investment page label legand before refresh : "+driver.findElement(By.xpath(prop.getProperty("labellegandPlannedInv"))).getText());
		log.info("Large Purchase Planned Investment page label legand text : "+driver.findElement(By.id(prop.getProperty("labellegandsubtxt"))).isDisplayed());
		
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsuggestedplan"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsub"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).getText());		
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("plannedinvtextbox"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("labellegandPlannedInv"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("labellegandsubtxt"))).isDisplayed());
		
		String suggestcurrentassettxt = driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsuggestedplan"))).getText();
		String affordabilitytxt = driver.findElement(By.xpath(prop.getProperty("labellegandPlannedInv"))).getText();
		
		int dollarpos1 = suggestcurrentassettxt.indexOf("$");
		int num1 = suggestcurrentassettxt.indexOf(" ",dollarpos1);
		String suggestcurrentasset = suggestcurrentassettxt.substring(dollarpos1+1, num1-1);
		log.info("Suggested current asset is : "+suggestcurrentasset);
		int dollarpos2 = suggestcurrentassettxt.indexOf("$",dollarpos1+1);
		int num2= suggestcurrentassettxt.indexOf(" ",dollarpos2);
		String suggestedcontribution = suggestcurrentassettxt.substring(dollarpos2+1, num2-1);
		log.info("Suggested contributtion is : "+suggestedcontribution);
		
		pagecontent.add(suggestcurrentasset);
		pagecontent.add(suggestedcontribution);
		pagecontent.add(affordabilitytxt);
		return pagecontent;
	}
	
	public void setMonthlyInv(String amount, String key, boolean flag, String goalamount)
	{
		
		driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).click();
		
		String upd_amount = amount.substring(0, amount.length());
		log.info("Entered monthly investment amount is : "+upd_amount);
		
		driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).click();
		do
		{
			driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).clear();
			driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).sendKeys(TestUtil.convNum(upd_amount)+"");
		}
		while(!driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsuggestionamount"))).getText().equals("$"+TestUtil.convNum(upd_amount)));
		
		driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvheader"))).click();
		
		//driver.hideKeyboard();			
		pagecontentafterclick = getPageContentAfterClick(flag, goalamount);		
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
	
	public static ArrayList<Object> getPageContentAfterClick(boolean flag, String goalamount)
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();
		
		
		String affordabilitytxt = driver.findElement(By.xpath(prop.getProperty("largepurchaselabellegand"))).getText();
		log.info("Amount displayed in Label legand after page refresh is : "+affordabilitytxt);
		pagecontent.add(affordabilitytxt);		
		
		if(flag == true && Double.parseDouble(affordabilitytxt.replaceAll("[^0-9.]", "")) < Double.parseDouble(goalamount.replaceAll("[^0-9.]", "")))
		{
			String popuptxt = driver.findElement(By.xpath(prop.getProperty("popup"))).getText();
			int dollarpos2 = popuptxt.indexOf("$");
			int num2 = popuptxt.indexOf("?",dollarpos2);
			String suggestedcontributioninpopup = popuptxt.substring(dollarpos2+1, num2);
			log.info("Contribution amount displayed in the pop up is : "+suggestedcontributioninpopup);
			pagecontent.add(suggestedcontributioninpopup);
		}

		return pagecontent;
	}
	
	public ArrayList<Object> getPageContentMoreThan55()
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();
		
		log.info("Large Purchase Planned Investment page Header is : "+driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvheader"))).getText());
		log.info("Large Purchase Planned Investment page Sub-header is : "+driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsubheader"))).getText());
		log.info("Large Purchase Planned Investment page suggested plan is displayed : "+driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsuggestedplanMoreThan55"))).isDisplayed());
		log.info("Large Purchase Planned Investment page investment is : "+driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsub"))).getText());
		log.info("Large Purchase Planned Investment page planned investment text box placeholder text : "+driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).getText());		
		log.info("Large Purchase Planned Investment page planned investment text box is displayed : "+driver.findElement(By.xpath(prop.getProperty("plannedinvtextbox"))).isDisplayed());
		
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsuggestedplanMoreThan55"))).isDisplayed());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsub"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("plannedinvtxtboxplaceholdertxt"))).getText());		
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("plannedinvtextbox"))).isDisplayed());

		
		String suggestcurrentassettxt = driver.findElement(By.xpath(prop.getProperty("largepurchaseplannedinvsuggestedplanMoreThan55"))).getText();
		
		int dollarpos1 = suggestcurrentassettxt.indexOf("$");
		int num1 = suggestcurrentassettxt.indexOf(" ",dollarpos1);
		String suggestcurrentasset = suggestcurrentassettxt.substring(dollarpos1+1, num1-1);
	
		pagecontent.add(suggestcurrentasset);
		return pagecontent;
	}

}
