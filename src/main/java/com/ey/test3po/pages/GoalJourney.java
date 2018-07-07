package com.ey.test3po.pages;

import org.openqa.selenium.By;

import com.ey.test3po.testbase.TestBase;

import io.appium.java_client.MobileBy;

public class GoalJourney extends TestBase {

	public static String goalXpath;
	public static String riskXpath;

	public void fillQuestionnaire(String annualincome, String location, String age, String key) {

		// entering the details
		driver.findElement(By.id(prop.getProperty("annualIncome"))).clear();
		driver.findElement(By.id(prop.getProperty("annualIncome"))).sendKeys(annualincome + "\n");

		driver.findElement(By.id(prop.getProperty("locationZip"))).clear();
		driver.findElement(By.id(prop.getProperty("locationZip"))).sendKeys(location + "\n");

		String upd_age = age.substring(0, age.length() - 2);

		driver.findElement(MobileBy
				.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\""
						+ upd_age + "\"))"))
				.click();

		// clicking on continue button
		driver.findElement(By.xpath(prop.getProperty("continueButtonUsrScreen"))).click();

	}

	public static void userChoice(String goal, String riskfactor, String key) {

		// creating xpath for goal
		String beforeGoalXpath = "//android.widget.TextView[@text='";	//not available in config file
		String afterGoalXpath = "']";									//not available in config file
		goalXpath = beforeGoalXpath + goal + afterGoalXpath;

		// creating xpath for riskfactor

		if (riskfactor.contains("extremely important")) {
			// case "It's extremely important that I achieve this goal":
			riskXpath = prop.getProperty("fullStartRisk");
			// break;
		} else if (riskfactor.contains("not critical")) {
			// case "It's important but not critical I achieve this goal":
			riskXpath = prop.getProperty("halfstartRisk");
			// break;
		} else if (riskfactor.contains("not important")) {
			// case "It's not important that I achieve this goal":
			riskXpath = prop.getProperty("noStartRisk");
			// break;
		}

	}

	public void goalJourney(String goalname, String purchasingyear, String goalamount, String duration, String key) {

		// select large purchase from the goal list
		driver.findElement(By.xpath(GoalJourney.goalXpath)).click();
		// driver.findElement(By.xpath("//android.widget.TextView[@text='Large Purchase']")).click();
		driver.findElement(By.xpath(prop.getProperty("continueButtonGoalScreen"))).click();

		// entering the details
		driver.findElement(By.id(prop.getProperty("goalName"))).clear();

		driver.findElement(By.id(prop.getProperty("goalName"))).sendKeys(goalname);

		driver.findElement(By.id(prop.getProperty("purchasingYear"))).click();

		String upd_purchasing_year = purchasingyear.substring(0, purchasingyear.length() - 2);
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\""+ upd_purchasing_year + "\"))")).click();

		driver.findElement(By.id(prop.getProperty("goalAmount"))).sendKeys(goalamount + "\n");

		String upd_duration = duration.substring(0, duration.length() - 2);
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\""+ upd_duration + "\"))")).click();

		// clicking on submit button
		driver.findElement(By.id(prop.getProperty("continueButtonPurchaseQuestionnaireScreen"))).click();
	}

	public void riskFactor() {

		// driver.findElement(By.id("com.eygsskpoc.ey3po:id/star_full")).click();
		driver.findElement(By.id(GoalJourney.riskXpath)).click();
		driver.findElement(By.id(prop.getProperty("conitnueButtonRiskScreen"))).click();
	}
}
