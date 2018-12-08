package com.ey.test3po.pagelevel;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ey.test3po.pages.GoalSelectionPage;
import com.ey.test3po.pages.RetirementGoalActivityQuestionnairePage;
import com.ey.test3po.pages.UserProfilePage;
import com.ey.test3po.pages.WelcomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class RetirementGoalActivityQuestionnairePageTest extends TestBase
{

	WelcomePage homepage;
	GoalSelectionPage selectgoal;
	UserProfilePage userprofile;
	RetirementGoalActivityQuestionnairePage goalactivity;
	String sheetName = "Retirement";

	public RetirementGoalActivityQuestionnairePageTest()
	{

		super();
	}

	@BeforeMethod
	public void setUp() throws MalformedURLException
	{
		initialization();
		homepage = new WelcomePage();
		selectgoal = new GoalSelectionPage();
		userprofile = new UserProfilePage();
		goalactivity = new RetirementGoalActivityQuestionnairePage();
		homepage.getStartedButtonClcik();
	}

	@DataProvider
	public Object[][] getQuestionnaireTestData()
	{
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}

	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void retirementGoalActivityQuestionnaire(String annualincome, String zip, String age, String goalname, String retage, String expperyr, String endyr, String incomepostret, String riskfactor, String plannedcontributionamt, String plannedinvamt, String delayduration, String username, String email, String pwd, String repwd, String suggestedcontribution, String suggestedaffordability1, String suggestedcurrentasset, String suggestedaffordability2, String suggestedaffordability3, String expctedretgae, String checkbox, String netincome, String key, String enabled)
	{
		userprofile.fillUserProfileQuestionnaire(annualincome, zip, age, key);
		selectgoal.goalChoice("Retirement", key);

		ArrayList<Object> pagecontent = goalactivity.getPageContentOfGoalQuestionnaire();

		Assert.assertEquals("Setting Up Your Retirement Goal", pagecontent.get(0));
		Assert.assertEquals("We populated some answers based on what we know about you.", pagecontent.get(1));
		Assert.assertEquals("Goal Name", pagecontent.get(2));
		Assert.assertEquals("At what age would you like to/did you retire?", pagecontent.get(3));
		Assert.assertEquals("How much do you want to spend per year after retirement?", pagecontent.get(4));
		Assert.assertEquals("End year (Analysis)", pagecontent.get(5));
		Assert.assertEquals("What is your expected income per year after retirement? (optional)", pagecontent.get(6));
		Assert.assertEquals(true, pagecontent.get(7));
		Assert.assertEquals(true, pagecontent.get(8));
		Assert.assertEquals(true, pagecontent.get(9));
		Assert.assertEquals(true, pagecontent.get(10));
		Assert.assertEquals(true, pagecontent.get(11));
		Assert.assertEquals(false, pagecontent.get(12));
		Assert.assertEquals(false, pagecontent.get(13));

		goalactivity.fillGoalQuestionnaire(goalname, retage, expperyr, endyr, incomepostret, key);
		// Assert.assertEquals(".activity.DashBoardActivity", findCurrentActivity());
	}

	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}