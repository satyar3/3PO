package com.ey.test3po.pagelevellessthan55;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ey.test3po.pages.GoalSelectionPage;
import com.ey.test3po.pages.LargePurchaseGoalActivityQuestionnairePage;
import com.ey.test3po.pages.UserProfilePage;
import com.ey.test3po.pages.WelcomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class LargePurchaseGoalActivityQuestionnairePageTest extends TestBase{
	
	WelcomePage homepage;
	GoalSelectionPage selectgoal;
	UserProfilePage userprofile;	
	LargePurchaseGoalActivityQuestionnairePage goalactivity;
	String sheetName = "LargePurchase";	
	
	
	public LargePurchaseGoalActivityQuestionnairePageTest() {

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
		homepage.getStartedButtonClcik();			
	}
	
	@DataProvider
	public Object[][] getQuestionnaireTestData() {
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}
	
	@Test(priority = 1 , dataProvider = "getQuestionnaireTestData")
	public void largePurchaseGoalActivityQuestionnaire(String annualincome, String zip, String age, String goalname,
			String purchaseyear, String goalamount, String goalduration, String riskfactor,
			String plannedcontributionamt, String plannedinvamt, String delayduration, String username, String email,
			String pwd, String repwd, String suggestedcontribution, String suggestedaffordability1,
			String suggestedcurrentasset, String suggestedaffordability2, String suggestedaffordability3,
			String planneddelay, String checkbox, String key) 
	{
		userprofile.userProfileQuestionnaire(annualincome, zip, age, key);
		selectgoal.goalChoice("Large Purchase", key);
		
		ArrayList<Object> pagecontent = goalactivity.getPageContent();
		
		Assert.assertEquals("Setting Up Your Large Purchase Goal", pagecontent.get(0));
		Assert.assertEquals("We populated some answers based on what we know about you.", pagecontent.get(1));
		Assert.assertEquals(true, pagecontent.get(2));
		Assert.assertEquals(true, pagecontent.get(3));
		Assert.assertEquals(true, pagecontent.get(4));
		Assert.assertEquals(true, pagecontent.get(5));		
		Assert.assertEquals("Goal Name", pagecontent.get(6));
		Assert.assertEquals("Purchasing Year", pagecontent.get(7));
		Assert.assertEquals("Goal Amount", pagecontent.get(8));
		Assert.assertEquals("Is this a recurring goal? If so, please specify the duration of your goal", pagecontent.get(9));		
		
		goalactivity.goalJourney(goalname, purchaseyear, goalamount, goalduration, key);
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}

}
