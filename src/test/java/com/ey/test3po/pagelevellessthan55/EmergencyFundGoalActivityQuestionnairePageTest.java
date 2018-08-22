package com.ey.test3po.pagelevellessthan55;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ey.test3po.pages.EmergencyFundGoalActivityQuestionnairePage;
import com.ey.test3po.pages.GoalSelectionPage;
import com.ey.test3po.pages.UserProfilePage;
import com.ey.test3po.pages.WelcomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class EmergencyFundGoalActivityQuestionnairePageTest extends TestBase
{

	WelcomePage homepage;
	GoalSelectionPage selectgoal;
	UserProfilePage userprofile;
	EmergencyFundGoalActivityQuestionnairePage goalactivity;
	String sheetName = "EmergencyFund";

	public EmergencyFundGoalActivityQuestionnairePageTest()
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
		goalactivity = new EmergencyFundGoalActivityQuestionnairePage();
		homepage.getStartedButtonClcik();
	}

	@DataProvider
	public Object[][] getQuestionnaireTestData()
	{
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}

	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void emergencyFundGoalActivityQuestionnaire(String annualincome, String zip, String age, String goalname, String monthsofsaltosave, String plannedinvamt, String username, String email, String pwd, String repwd, String suggestinvamount, String affordablemonthofsal, String key)
	{
		userprofile.fillUserProfileQuestionnaire(annualincome, zip, age, key);
		selectgoal.goalChoice("Emergency Fund", key);

		ArrayList<Object> pagecontent = goalactivity.getPageContentOfGoalQuestionnaire();

		Assert.assertEquals("Setting up your Emergency Fund", pagecontent.get(0));
		Assert.assertEquals("We populated some answers based on what we know about you.", pagecontent.get(1));
		Assert.assertEquals("Goal Name", pagecontent.get(2));
		Assert.assertEquals("Months of Salary to Save", pagecontent.get(3));
		Assert.assertEquals(true, pagecontent.get(4));
		Assert.assertEquals(true, pagecontent.get(5));

		goalactivity.fillGoalQuestionnaire(goalname, monthsofsaltosave, key);
		// Assert.assertEquals(".activity.DashBoardActivity", findCurrentActivity());
	}

	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}