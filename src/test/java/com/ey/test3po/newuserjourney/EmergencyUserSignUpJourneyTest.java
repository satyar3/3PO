package com.ey.test3po.newuserjourney;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ey.test3po.pages.EmergencyFundAnnualSpendingPage;
import com.ey.test3po.pages.EmergencyFundGoalActivityQuestionnairePage;
import com.ey.test3po.pages.EmergencyFundPlannedInvPage;
import com.ey.test3po.pages.GoalSelectionPage;
import com.ey.test3po.pages.SignUpPage;
import com.ey.test3po.pages.UserProfilePage;
import com.ey.test3po.pages.WelcomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class EmergencyUserSignUpJourneyTest extends TestBase
{

	WelcomePage homepage;
	GoalSelectionPage selectgoal;
	UserProfilePage userprofile;
	EmergencyFundGoalActivityQuestionnairePage goalactivity;
	EmergencyFundPlannedInvPage plannedinv;
	EmergencyFundAnnualSpendingPage annualspending;
	SignUpPage signup;
	String sheetName = "EmergencyFund";

	boolean flag;

	public EmergencyUserSignUpJourneyTest()
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
		plannedinv = new EmergencyFundPlannedInvPage();
		annualspending = new EmergencyFundAnnualSpendingPage();
		signup = new SignUpPage();
		homepage.getStartedButtonClcik();
		flag = false;
	}

	@DataProvider
	public Object[][] getQuestionnaireTestData()
	{
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}

	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void emergencyGoalUserJourney(String annualincome, String zip, String age, String goalname, String monthsofsaltosave, String plannedinvamt, String username, String email, String pwd, String repwd, String suggestinvamount, String affordablemonthofsal, String testcaseid, String enabled)
	{
		testcasenum = testcaseid;
		System.out.println(testcaseid + " execution has been started.");

		userprofile.fillUserProfileQuestionnaire(annualincome, zip, age, testcaseid);
		selectgoal.goalChoice("Emergency fund", testcaseid);
		goalactivity.fillGoalQuestionnaire(goalname, monthsofsaltosave, testcaseid);

		ArrayList<Object> plannedinvpagecontent = plannedinv.getPageContentOfPlannedInvestment();

		Assert.assertEquals("Setting up your emergency fund", plannedinvpagecontent.get(0), "Header mismatch in investment page");
		Assert.assertEquals("Planned investments", plannedinvpagecontent.get(1), "Sub-header mismatch in investment suggestion page");
		Assert.assertEquals(true, plannedinvpagecontent.get(2), "Suggested plan not displayed in the investment page");
		Assert.assertEquals("How much can you set aside as emergency funds?", plannedinvpagecontent.get(3), "Investment subtext mismatch");
		Assert.assertEquals("Planned investments", plannedinvpagecontent.get(4), "Planned investment placeholder text mismatch");
		Assert.assertEquals(true, plannedinvpagecontent.get(5), "Investment inputbox is missing");
		Assert.assertEquals(TestUtil.convNum(suggestinvamount), plannedinvpagecontent.get(6), "Suggested investment amount mismatch in investment page");

		plannedinv.setMonthlyInvestment(plannedinvamt, testcaseid);
		Assert.assertEquals(affordablemonthofsal, plannedinv.pagecontentafterclick.get(0), "months of salary mismatch in investment page");

		if (Double.parseDouble(((String) plannedinv.pagecontentafterclick.get(0)).replaceAll("[^0-9.]", "")) != Double.parseDouble(monthsofsaltosave))
		{
			flag = true;
		}

		ArrayList<Object> annualspendingpagecontent = annualspending.getPageContentOfAnnualSpending();

		Assert.assertEquals("Setting up your emergency fund", annualspendingpagecontent.get(0), "Header mismatch in Annual spending page");
		Assert.assertEquals("Annual spending", annualspendingpagecontent.get(1), "Sub-header mismatch in Annual spending page");
		Assert.assertEquals(true, annualspendingpagecontent.get(2), "Annual spending suggested plan not displayed");
		Assert.assertEquals(true, annualspendingpagecontent.get(3), "Annual spending sub-text not displayed");
		Assert.assertEquals(true, annualspendingpagecontent.get(4), "Label legand is not displayed in Annual spending page");
		Assert.assertEquals(true, annualspendingpagecontent.get(5), "Label legand sub-text is not displayed in Annual spending page");
		Assert.assertEquals(true, annualspendingpagecontent.get(6), "Phone dialier image is missing in Annual spending page");
		Assert.assertEquals("Have questions?", annualspendingpagecontent.get(7), "Have questions section is missing");
		Assert.assertEquals(" Call an Advisor", annualspendingpagecontent.get(8), "Call an Advisor section is missing");
		Assert.assertEquals("Sign up and view your recommended asset allocation", annualspendingpagecontent.get(9), "Asset allocation section is missing in Annual spending page");
		Assert.assertEquals(monthsofsaltosave, annualspendingpagecontent.get(10));

		if (flag == true)
		{
			Assert.assertEquals(affordablemonthofsal, annualspendingpagecontent.get(11), "User selected months of sal mismatch");
			Assert.assertEquals(affordablemonthofsal, annualspendingpagecontent.get(12), "System suggested months of sal mismatch");
		}
		else
		{
			Assert.assertEquals(affordablemonthofsal, annualspendingpagecontent.get(11), "System suggested months of sal mismatch");
		}

		annualspending.signUp();
		// signup.signUp(username, email, pwd, repwd, key);
		System.out.println(testcaseid + " execution has been completed.");
	}

	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}