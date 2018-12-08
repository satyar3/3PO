package com.ey.test3po.newuserjourney;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ey.test3po.pages.GoalSelectionPage;
import com.ey.test3po.pages.OtherGoalActivityQuestionnairePage;
import com.ey.test3po.pages.OtherGoalAnnualSpendingPage;
import com.ey.test3po.pages.OtherGoalDelayInGoalPage;
import com.ey.test3po.pages.OtherGoalImportancePage;
import com.ey.test3po.pages.OtherGoalInvSuggestionsPage;
import com.ey.test3po.pages.OtherGoalPlannedInvPage;
import com.ey.test3po.pages.SignUpPage;
import com.ey.test3po.pages.UserProfilePage;
import com.ey.test3po.pages.WelcomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class OtherGoalUserSignUpJourneyLessThan55Test extends TestBase
{

	WelcomePage homepage;
	GoalSelectionPage selectgoal;
	UserProfilePage userprofile;
	public OtherGoalActivityQuestionnairePage goalactivity;
	public OtherGoalImportancePage goalimportance;
	public OtherGoalInvSuggestionsPage plannedinvsuggestions;
	public OtherGoalAnnualSpendingPage annualspending;
	public OtherGoalPlannedInvPage plannedinv;
	public OtherGoalDelayInGoalPage delayingoal;
	public SignUpPage signup;
	public String sheetName = "OtherGoal";

	public static boolean popup_flag_contribution;
	public static boolean popup_flag_inv;
	public static boolean signInUser;

	public OtherGoalUserSignUpJourneyLessThan55Test()
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
		goalimportance = new OtherGoalImportancePage();
		plannedinvsuggestions = new OtherGoalInvSuggestionsPage();
		annualspending = new OtherGoalAnnualSpendingPage();
		plannedinv = new OtherGoalPlannedInvPage();
		delayingoal = new OtherGoalDelayInGoalPage();
		signup = new SignUpPage();
		homepage.getStartedButtonClcik();

		popup_flag_contribution = true;
		popup_flag_inv = true;
		signInUser = false;
	}

	@DataProvider
	public Object[][] getQuestionnaireTestData()
	{
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}

	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void otherGoalGoalLessThan55(String annualincome, String zip, String age, String goalname, String purchaseyear, String goalamount, String goalduration, String riskfactor, String plannedcontributionamt, String plannedinvamt, String delayduration, String username, String email, String pwd, String repwd, String suggestedcontribution, String suggestedaffordability1, String suggestedcurrentasset, String suggestedaffordability2, String suggestedaffordability3, String planneddelay, String checkbox, String testcaseid, String enabled)
	{

		testcasenum = testcaseid;
		System.out.println(testcaseid + " execution has been started.");

		try
		{
			if (Double.parseDouble(plannedcontributionamt) >= Double.parseDouble(suggestedcontribution))
			{
				popup_flag_contribution = false;
			}
			else if (Double.parseDouble(plannedinvamt) >= Double.parseDouble(suggestedcurrentasset))
			{
				popup_flag_inv = false;
			}
		}
		catch (NumberFormatException e)
		{
			popup_flag_contribution = false;
		}

		if(signInUser == false)
		{
			userprofile.fillUserProfileQuestionnaire(annualincome, zip, age, testcaseid);
			selectgoal.goalChoice("Other goal", testcaseid);
		}
		
		goalactivity.fillGoalQuestionnaire(goalname, purchaseyear, goalamount, goalduration, testcaseid);
		goalimportance.riskFactor(riskfactor, testcaseid);

		if (Double.parseDouble(plannedcontributionamt) != 0)
		{

			ArrayList<Object> plannedcontributionpagecontent = plannedinvsuggestions.getPageContentOfInvestmentSuggestion();

			Assert.assertEquals("Setting up your other goal", plannedcontributionpagecontent.get(0), "Header mismatch in monthly contribution page");
			Assert.assertEquals("Planned contributions", plannedcontributionpagecontent.get(1), "Sub-header mismatch in monthly contribution page");
			Assert.assertEquals(true, plannedcontributionpagecontent.get(2), "Suggestion text not visible in  monthly contribution page");
			Assert.assertEquals("Too much? Too little?" + "\n" + "Let us know and we'll rerun the numbers", plannedcontributionpagecontent.get(3), "Sub text mismatch in  monthly contribution page");
			Assert.assertEquals(true, plannedcontributionpagecontent.get(4), "Contribution suggestion textbox not displayed in  monthly contribution page");
			Assert.assertEquals(true, plannedcontributionpagecontent.get(5), "Check box not visible in monthly contribution page ");
			Assert.assertEquals("Adjust my contributions with salary growth", plannedcontributionpagecontent.get(6), "Text next to check box mismatch in monthly contribution page");
			Assert.assertEquals(true, plannedcontributionpagecontent.get(7), "Image in text box not displayed");
			Assert.assertEquals("Planned monthly contribution ($)", plannedcontributionpagecontent.get(8), "Contribution box placeholder text mismatch");
			Assert.assertEquals(TestUtil.convNum(goalamount), plannedcontributionpagecontent.get(9), "Goal amount mismatch in suggestion text in monthly contribution page");
			Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedcontributionpagecontent.get(10), "Investment suggestion mismatch in monthly contribution page");
			Assert.assertEquals(String.valueOf(Integer.parseInt(purchaseyear) - 1), plannedcontributionpagecontent.get(11), "No of years mismatch in monthly contribution page");

			plannedinvsuggestions.setMonthlyContribution(plannedcontributionamt, testcaseid, popup_flag_contribution, checkbox, goalamount, plannedcontributionamt, plannedinvamt, age);
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinvsuggestions.pagecontentafterclick.get(0), "Suggested affordability mismatch in monthly contribution page");

			if (Double.parseDouble(plannedcontributionamt) < Double.parseDouble(suggestedcontribution) && Double.parseDouble(suggestedaffordability1) < Double.parseDouble(goalamount))
			{
				Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedinvsuggestions.pagecontentafterclick.get(1), "pop up amount mismatch in monthly contribution page");
				ArrayList<Object> plannedinvpagecontent = plannedinv.getPageContentOfPlannedInvestment(plannedcontributionamt);

				Assert.assertEquals("Setting up your other goal", plannedinvpagecontent.get(0), "Goal header mismatch in planned inv page");
				Assert.assertEquals("Planned investments", plannedinvpagecontent.get(1), "Goal sub header mismatch in planned inv page");
				Assert.assertEquals(true, plannedinvpagecontent.get(2), "Goal suggestion text missing in planned inv page");
				Assert.assertEquals("How much have you already saved for your other goal?", plannedinvpagecontent.get(3), "Goal suggestion sub text mismatch in planned inv page");
				Assert.assertEquals("Planned investments", plannedinvpagecontent.get(4), "Text field placeholder mismatch in planned inv page");
				Assert.assertEquals(true, plannedinvpagecontent.get(5), "Text field is missing in planned inv page");
				Assert.assertEquals(true, plannedinvpagecontent.get(6), "Label legand missing in planned inv page");
				Assert.assertEquals(true, plannedinvpagecontent.get(7), "label legand text missing in planned inv page");
				Assert.assertEquals(TestUtil.convNum(plannedcontributionamt), plannedinvpagecontent.get(8), "Planned contribution amount mismatch in planned inv page");
				Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), plannedinvpagecontent.get(9), "Suggested current asset mismatch in planned inv page");
				Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinvpagecontent.get(10), "Suggested affordability mismatch before click in planned inv page");

				plannedinv.setMonthlyInvestment(plannedinvamt, testcaseid, popup_flag_inv, goalamount, plannedinvamt, plannedcontributionamt, age);
				Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), plannedinv.pagecontentafterclick.get(0), "Suggested affordability mismatch after click in planned inv page");

				if (Double.parseDouble(plannedinvamt) < Double.parseDouble(suggestedcurrentasset))
				{
					if (Double.parseDouble(suggestedaffordability2) != Double.parseDouble(goalamount) && Double.parseDouble(plannedinvamt) != 0)
					{
						Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), plannedinv.pagecontentafterclick.get(1), "Pop up text mismatch in planned inv page");
						ArrayList<Object> delaypagecontent = delayingoal.getPageContentOfDelayGoalScreen(plannedcontributionamt, plannedinvamt, age);

						Assert.assertEquals("Setting up your other goal", delaypagecontent.get(0), "Goal header mismatch in delay page");
						Assert.assertEquals("Start year", delaypagecontent.get(1), "Goal sub header mismatch in delay page");
						Assert.assertEquals(true, delaypagecontent.get(2), "Delay suggestion text missing in delay page");
						Assert.assertEquals("When would you like to make your purchase?", delaypagecontent.get(3), "Sub text mismatch in delay page");
						Assert.assertEquals("Number of years", delaypagecontent.get(4), "Delay placeholder text mismatch in delay page");
						Assert.assertEquals(true, delaypagecontent.get(5), "Delay drop down missing in delay page");
						Assert.assertEquals(true, delaypagecontent.get(6), "Lable legand missing in delay page");
						Assert.assertEquals(true, delaypagecontent.get(7), "Lable legand sub text missing in delay page");
						Assert.assertEquals(planneddelay, delaypagecontent.get(8), "Delay period mismatch");
						Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), delaypagecontent.get(9), "Suggested affordability before click mismatch in delay page");

						delayingoal.setGoalDelayDuration(delayduration, testcaseid);
						Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), delayingoal.pagecontentafterclick.get(0), "Suggested affordabilty mismatch after click in delay page");
					}

					else if (Double.parseDouble(suggestedaffordability2) != Double.parseDouble(goalamount) && Double.parseDouble(plannedinvamt) == 0)
					{
						Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), plannedinv.pagecontentafterclick.get(1), "Pop up text mismatch in planned inv page");
						ArrayList<Object> delaypagecontent = delayingoal.getPageContentOfDelayGoalScreen(plannedcontributionamt, plannedinvamt, age);

						Assert.assertEquals("Setting up your other goal", delaypagecontent.get(0), "Goal header mismatch in delay page");
						Assert.assertEquals("Start year", delaypagecontent.get(1), "Goal sub header mismatch in delay page");
						Assert.assertEquals(true, delaypagecontent.get(2), "Delay suggestion text missing in delay page");
						Assert.assertEquals("When would you like to make your purchase?", delaypagecontent.get(3), "Sub text mismatch in delay page");
						Assert.assertEquals("Number of years", delaypagecontent.get(4), "Delay placeholder text mismatch in delay page");
						Assert.assertEquals(true, delaypagecontent.get(5), "Delay drop down missing in delay page");
						// Assert.assertEquals(true, delaypagecontent.get(6), "Lable legand missing in delay page");
						// Assert.assertEquals(true, delaypagecontent.get(7), "Lable legand sub text missing in delay page");
						Assert.assertEquals(planneddelay, delaypagecontent.get(6), "Delay period mismatch");
						// Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), delaypagecontent.get(9), "Suggested affordability before click mismatch in delay page");

						delayingoal.setGoalDelayDuration(delayduration, testcaseid);
						Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), delayingoal.pagecontentafterclick.get(0), "Suggested affordabilty mismatch after click in delay page");
					}
				}
			}
		}

		else
		{

			ArrayList<Object> plannedcontributionpagecontent = plannedinvsuggestions.getPageContentOfInvestmentSuggestion();

			Assert.assertEquals("Setting up your other goal", plannedcontributionpagecontent.get(0), "Header mismatch in monthly contribution page");
			Assert.assertEquals("Planned contributions", plannedcontributionpagecontent.get(1), "Sub-header mismatch in monthly contribution page");
			Assert.assertEquals(true, plannedcontributionpagecontent.get(2), "Suggestion text not visible in  monthly contribution page");
			Assert.assertEquals("Too much? Too little?" + "\n" + "Let us know and we'll rerun the numbers", plannedcontributionpagecontent.get(3), "Sub text mismatch in  monthly contribution page");
			Assert.assertEquals(true, plannedcontributionpagecontent.get(4), "Contribution suggestion textbox not displayed in  monthly contribution page");
			Assert.assertEquals(true, plannedcontributionpagecontent.get(5), "Check box not visible in monthly contribution page ");
			Assert.assertEquals("Adjust my contributions with salary growth", plannedcontributionpagecontent.get(6), "Text next to check box mismatch in monthly contribution page");
			Assert.assertEquals(true, plannedcontributionpagecontent.get(7), "Image in text box not displayed");
			Assert.assertEquals("Planned monthly contribution ($)", plannedcontributionpagecontent.get(8), "Contribution box placeholder text mismatch");
			Assert.assertEquals(TestUtil.convNum(goalamount), plannedcontributionpagecontent.get(9), "Goal amount mismatch in suggestion text in monthly contribution page");
			Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedcontributionpagecontent.get(10), "Investment suggestion mismatch in monthly contribution page");
			Assert.assertEquals(String.valueOf(Integer.parseInt(purchaseyear) - 1), plannedcontributionpagecontent.get(11), "No of years mismatch in monthly contribution page");

			plannedinvsuggestions.setMonthlyContribution(plannedcontributionamt, testcaseid, popup_flag_contribution, checkbox, goalamount, plannedcontributionamt, plannedinvamt, age);
			// Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinvsuggestions.pagecontentafterclick.get(0),"Suggested affordability mismatch in monthly contribution page");

			// if(Double.parseDouble(plannedcontributionamt)<Double.parseDouble(suggestedcontribution) && Double.parseDouble(suggestedaffordability1) < Double.parseDouble(goalamount))
			// {
			Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedinvsuggestions.pagecontentafterclick.get(0), "pop up amount mismatch in monthly contribution page");
			ArrayList<Object> plannedinvpagecontent = plannedinv.getPageContentOfPlannedInvestment(plannedcontributionamt);

			Assert.assertEquals("Setting up your other goal", plannedinvpagecontent.get(0), "Goal header mismatch in planned inv page");
			Assert.assertEquals("Planned investments", plannedinvpagecontent.get(1), "Goal sub header mismatch in planned inv page");
			Assert.assertEquals(true, plannedinvpagecontent.get(2), "Goal suggestion text missing in planned inv page");
			Assert.assertEquals("How much have you already saved for your other goal?", plannedinvpagecontent.get(3), "Goal suggestion sub text mismatch in planned inv page");
			Assert.assertEquals("Planned investments", plannedinvpagecontent.get(4), "Text field placeholder mismatch in planned inv page");
			Assert.assertEquals(true, plannedinvpagecontent.get(5), "Text field is missing in planned inv page");
			// Assert.assertEquals(true, plannedinvpagecontent.get(6),"Label legand missing in planned inv page");
			// Assert.assertEquals(true, plannedinvpagecontent.get(7),"label legand text missing in planned inv page");
			Assert.assertEquals(TestUtil.convNum(plannedcontributionamt), plannedinvpagecontent.get(6), "Planned contribution amount mismatch in planned inv page");
			Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), plannedinvpagecontent.get(7), "Suggested current asset mismatch in planned inv page");
			// Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinvpagecontent.get(10),"Suggested affordability mismatch before click in planned inv page");

			plannedinv.setMonthlyInvestment(plannedinvamt, testcaseid, popup_flag_inv, goalamount, plannedinvamt, plannedcontributionamt, age);

			if (Double.parseDouble(plannedinvamt) < Double.parseDouble(suggestedcurrentasset) && Double.parseDouble(plannedinvamt) != 0)
			{
				// if(Double.parseDouble(suggestedaffordability2) != Double.parseDouble(goalamount) && Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamt) != 0 )
				// {
				Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), plannedinv.pagecontentafterclick.get(0), "Suggested affordability mismatch after click in planned inv page");
				Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), plannedinv.pagecontentafterclick.get(1), "Pop up text mismatch in planned inv page");
				ArrayList<Object> delaypagecontent = delayingoal.getPageContentOfDelayGoalScreen(plannedcontributionamt, plannedinvamt, age);

				Assert.assertEquals("Setting up your other goal", delaypagecontent.get(0), "Goal header mismatch in delay page");
				Assert.assertEquals("Start year", delaypagecontent.get(1), "Goal sub header mismatch in delay page");
				Assert.assertEquals(true, delaypagecontent.get(2), "Delay suggestion text missing in delay page");
				Assert.assertEquals("When would you like to make your purchase?", delaypagecontent.get(3), "Sub text mismatch in delay page");
				Assert.assertEquals("Number of years", delaypagecontent.get(4), "Delay placeholder text mismatch in delay page");
				Assert.assertEquals(true, delaypagecontent.get(5), "Delay drop down missing in delay page");
				Assert.assertEquals(true, delaypagecontent.get(6), "Lable legand missing in delay page");
				Assert.assertEquals(true, delaypagecontent.get(7), "Lable legand sub text missing in delay page");
				Assert.assertEquals(planneddelay, delaypagecontent.get(8), "Delay period mismatch");
				Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), delaypagecontent.get(9), "Suggested affordability before click mismatch in delay page");

				delayingoal.setGoalDelayDuration(delayduration, testcaseid);
				Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), delayingoal.pagecontentafterclick.get(0), "Suggested affordabilty mismatch after click in delay page");
			}
		}

		ArrayList<Object> annualspendingpagecontent = annualspending.getPageContentOfAnnualSpending(plannedcontributionamt, plannedinvamt);

		Assert.assertEquals("Setting up your other goal", annualspendingpagecontent.get(0), "Header mismatch in Annual spending page");
		Assert.assertEquals("Annual spending", annualspendingpagecontent.get(1), "Sub-header mismatch in Annual spending page");
		Assert.assertEquals(true, annualspendingpagecontent.get(2), "Annual spending suggested plan not displayed");
		Assert.assertEquals(true, annualspendingpagecontent.get(3), "Annual spending sub-text not displayed");
		Assert.assertEquals(true, annualspendingpagecontent.get(4), "Label legand is not displayed in Annual spending page");
		Assert.assertEquals(true, annualspendingpagecontent.get(5), "Label legand sub-text is not displayed in Annual spending page");
		Assert.assertEquals(true, annualspendingpagecontent.get(6), "Phone dialier image is missing in Annual spending page");
		Assert.assertEquals("Have questions?", annualspendingpagecontent.get(7), "Have questions section is missing");
		Assert.assertEquals(" Call an Advisor", annualspendingpagecontent.get(8), "Call an Advisor section is missing");
		Assert.assertEquals("Sign up and view your recommended asset allocation", annualspendingpagecontent.get(9), "Asset allocation section is missing in Annual spending page");
		Assert.assertEquals(TestUtil.convNum(goalamount), TestUtil.convNum((String) annualspendingpagecontent.get(10)), "Yearly expense mismatch in Annual spending page");

		if (annualspendingpagecontent.size() == 13 && plannedcontributionamt.length() != 0 && (Double.parseDouble(plannedcontributionamt) != 0 || Double.parseDouble(plannedinvamt) != 0))
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(11), "Suggested affordability mismacth in Annual spending page in suggested text");
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(12), "Suggested affordability mismacth in Annual spending page in label legand");
		}
		else if (annualspendingpagecontent.size() == 13 && plannedcontributionamt.length() == 0)
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(11), "Suggested affordability mismacth in Annual spending page in suggested text");
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(12), "Suggested affordability mismacth in Annual spending page in label legand");
		}
		else if (annualspendingpagecontent.size() == 13 && (Double.parseDouble(plannedcontributionamt) == 0 && Double.parseDouble(plannedinvamt) == 0))
		{
			Assert.assertEquals("0", annualspendingpagecontent.get(11), "Suggested affordability mismacth in Annual spending page in suggested text");
			// Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), pagecontentAnnualSepnding.get(12), "Suggested affordability mismacth in Annual spending page in label legand");
		}
		else
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(11), "Suggested affordability mismacth in Annual spending page in label legand");
		}

		annualspending.signUp();
		//signup.fillSignUpDetails(username, email, pwd, repwd, testcaseid);
		System.out.println(testcaseid + " execution has been completed.");
	}

	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}