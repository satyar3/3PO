package com.ey.test3po.pagelevellessthan55;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ey.test3po.extentreport.ExtentReporterNG;
import com.ey.test3po.pages.GoalSelectionPage;
import com.ey.test3po.pages.LargePurchaseGoalActivityQuestionnairePage;
import com.ey.test3po.pages.LargePurchaseGoalImportancePage;
import com.ey.test3po.pages.UserProfilePage;
import com.ey.test3po.pages.WelcomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

@Listeners(ExtentReporterNG.class)
public class LargePurchaseGoalImportancePageTest extends TestBase{
	
	WelcomePage homepage;
	GoalSelectionPage selectgoal;
	UserProfilePage userprofile;	
	LargePurchaseGoalActivityQuestionnairePage goalactivity;
	LargePurchaseGoalImportancePage goalimportance;
	String sheetName = "LargePurchase";	
	
	public LargePurchaseGoalImportancePageTest() {

		super();
	}
	
	@BeforeMethod
	public void setUp() throws MalformedURLException
	{
		initialization();
		homepage = new WelcomePage();
		selectgoal = new GoalSelectionPage();
		userprofile = new UserProfilePage();
		goalactivity = new LargePurchaseGoalActivityQuestionnairePage();
		goalimportance = new LargePurchaseGoalImportancePage();
		homepage.getStartedButtonClcik();			
	}
	
	@DataProvider
	public Object[][] getQuestionnaireTestData() {
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}
	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void largePurchaseGoalImportance(String annualincome, String zip, String age,
			String goalname, String purchaseyear, String goalamount, String goalduration, String riskfactor, 
			String plannedcontributionamt, String plannedinvamt, String delayduration, 
			String username, String email, String pwd, String repwd, 
			String suggestedcontribution, String suggestedaffordability1, String suggestedcurrentasset, String suggestedaffordability2, String suggestedaffordability3, String planneddelay, String key)
	{
		userprofile.userProfileQuestionnaire(annualincome, zip, age, key);
		selectgoal.goalChoice("Large Purchase", key);
		goalactivity.goalJourney(goalname, purchaseyear, goalamount, goalduration, key);
				
		ArrayList<Object> pagecontent = goalimportance.getPageContent();
		
		Assert.assertEquals("Setting up your Large Purchase Goal", pagecontent.get(0));
		Assert.assertEquals("How would you feel if you could not meet this goal?", pagecontent.get(1));
		Assert.assertEquals("It's extremely important that I achieve this goal", pagecontent.get(2));
		Assert.assertEquals("It's important but not critical I achieve this goal", pagecontent.get(3));
		Assert.assertEquals("It's not important that I achieve this goal", pagecontent.get(4));
		Assert.assertEquals(true, pagecontent.get(5));
		Assert.assertEquals("Have questions?", pagecontent.get(6));
		Assert.assertEquals(" Call an Advisor", pagecontent.get(7));
		
		goalimportance.riskFactor(riskfactor, key);
		//Assert.assertEquals(".activity.LargePurchaseGoalActivity", findCurrentActivity());
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}