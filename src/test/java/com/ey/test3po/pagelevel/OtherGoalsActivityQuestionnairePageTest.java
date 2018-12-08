package com.ey.test3po.pagelevel;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ey.test3po.pages.GoalSelectionPage;
import com.ey.test3po.pages.OtherGoalActivityQuestionnairePage;
import com.ey.test3po.pages.UserProfilePage;
import com.ey.test3po.pages.WelcomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class OtherGoalsActivityQuestionnairePageTest extends TestBase
{

	WelcomePage homepage;
	GoalSelectionPage selectgoal;
	UserProfilePage userprofile;
	OtherGoalActivityQuestionnairePage goalactivity;
	String sheetName = "OtherGoal";

	public OtherGoalsActivityQuestionnairePageTest()
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
		goalactivity = new OtherGoalActivityQuestionnairePage();
		homepage.getStartedButtonClcik();
	}

	@DataProvider
	public Object[][] getQuestionnaireTestData()
	{
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}

	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void otherGoalsActivityQuestionnaire(String annualincome, String zip, String age, String goalname, String purchaseyear, String goalamount, String goalduration, String riskfactor, String plannedcontributionamt, String plannedinvamt, String delayduration, String username, String email, String pwd, String repwd, String suggestedcontribution, String suggestedaffordability1, String suggestedcurrentasset, String suggestedaffordability2, String suggestedaffordability3, String planneddelay, String checkbox, String key, String enabled)
	{
		userprofile.fillUserProfileQuestionnaire(annualincome, zip, age, key);
		selectgoal.goalChoice("Other Goal", key);

		ArrayList<Object> pagecontent = goalactivity.getPageContentOfGoalQuestionnaire();

		Assert.assertEquals("Setting up your Other Goal", pagecontent.get(0));
		Assert.assertEquals("Please provide additional details of your goal.", pagecontent.get(1));
		Assert.assertEquals("Goal Name", pagecontent.get(2));
		Assert.assertEquals("Goal Start Year", pagecontent.get(3));
		Assert.assertEquals("Goal Amount", pagecontent.get(4));
		Assert.assertEquals("Duration of Goal Amount Needed", pagecontent.get(5));
		Assert.assertEquals(true, pagecontent.get(6));
		Assert.assertEquals(true, pagecontent.get(7));
		Assert.assertEquals(true, pagecontent.get(8));
		Assert.assertEquals(true, pagecontent.get(9));

		goalactivity.fillGoalQuestionnaire(goalname, purchaseyear, goalamount, goalduration, key);
		// Assert.assertEquals(".activity.DashBoardActivity", findCurrentActivity());
	}

	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}