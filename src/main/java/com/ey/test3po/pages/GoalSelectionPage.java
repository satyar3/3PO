package com.ey.test3po.pages;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;

public class GoalSelectionPage extends TestBase 
{

	public static String goalXpath;
	public static Logger log = Logger.getLogger(GoalSelectionPage.class);

	public void goalChoice(String goal, String key) 
	{

		// creating xpath for goal
		String beforeGoalXpath = "//android.widget.TextView[@text='"; // not available in config file
		String afterGoalXpath = "']"; // not available in config file
		goalXpath = beforeGoalXpath + goal + afterGoalXpath;
		driver.findElement(By.xpath(GoalSelectionPage.goalXpath)).click();
		log.info("Goal Selected");
		driver.findElement(By.id(prop.getProperty("continueButtonbyid"))).click();
	}

	public ArrayList<Object> getPageContent() 
	{
		ArrayList<Object> pagecontent = new ArrayList<Object>();

		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("goalpageheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("goalpagesubheader"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("emergencyfundtxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("educationgoaltxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("retirementgoaltxt"))).getText());
		pagecontent.add(driver.findElement(By.xpath(prop.getProperty("othergoalstxt"))).getText());

		driver.findElement(By.xpath(prop.getProperty("largepurchasegoaltxt"))).click();
		pagecontent.add(driver.findElement(By.id(prop.getProperty("goalheader"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("goalsubheader"))).getText());

		driver.findElement(By.xpath(prop.getProperty("emergencyfundtxt"))).click();
		pagecontent.add(driver.findElement(By.id(prop.getProperty("goalheader"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("goalsubheader"))).getText());

		driver.findElement(By.xpath(prop.getProperty("educationgoaltxt"))).click();
		pagecontent.add(driver.findElement(By.id(prop.getProperty("goalheader"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("goalsubheader"))).getText());

		driver.findElement(By.xpath(prop.getProperty("retirementgoaltxt"))).click();
		pagecontent.add(driver.findElement(By.id(prop.getProperty("goalheader"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("goalsubheader"))).getText());

		driver.findElement(By.xpath(prop.getProperty("othergoalstxt"))).click();
		pagecontent.add(driver.findElement(By.id(prop.getProperty("goalheader"))).getText());
		pagecontent.add(driver.findElement(By.id(prop.getProperty("goalsubheader"))).getText());

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
