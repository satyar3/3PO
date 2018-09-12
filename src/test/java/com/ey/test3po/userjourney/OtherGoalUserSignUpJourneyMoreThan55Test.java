package com.ey.test3po.userjourney;

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

public class OtherGoalUserSignUpJourneyMoreThan55Test extends TestBase
{

	WelcomePage homepage;
	GoalSelectionPage selectgoal;
	UserProfilePage userprofile;
	OtherGoalActivityQuestionnairePage goalactivity;
	OtherGoalImportancePage goalimportance;
	OtherGoalInvSuggestionsPage plannedinvsuggestions;
	OtherGoalAnnualSpendingPage annualspending;
	OtherGoalPlannedInvPage plannedinv;
	OtherGoalDelayInGoalPage delayingoal;
	SignUpPage signup;
	String sheetName = "OtherGoal>55";

	boolean popup_flag_contribution;
	boolean popup_flag_inv;

	public OtherGoalUserSignUpJourneyMoreThan55Test()
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
	}

	@DataProvider
	public Object[][] getQuestionnaireTestData()
	{
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}

	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void otherGoalGoalMoreThan55(String annualincome, String zip, String age, String goalname, String purchaseyear, String goalamount, String goalduration, String riskfactor, String plannedinvamt, String plannedcontributionamt, String delayduration, String username, String email, String pwd, String repwd, String suggestedinvestment, String suggestedaffordability1, String suggestedcontribution, String suggestedaffordability2, String suggestedaffordability3, String planneddelay, String checkbox, String testcaseid, String enabled)
	{
		testcasenum = testcaseid;
		System.out.println(testcaseid + " execution has been started.");

		try
		{
			if (Double.parseDouble(plannedinvamt) >= Double.parseDouble(suggestedinvestment))
			{
				popup_flag_inv = false;
			}
			else if (Double.parseDouble(plannedcontributionamt) >= Double.parseDouble(suggestedcontribution))
			{
				popup_flag_contribution = false;
			}
		}
		catch (NumberFormatException e)
		{
			popup_flag_contribution = false;
		}

		userprofile.fillUserProfileQuestionnaire(annualincome, zip, age, testcaseid);
		selectgoal.goalChoice("Other Goal", testcaseid);
		goalactivity.fillGoalQuestionnaire(goalname, purchaseyear, goalamount, goalduration, testcaseid);
		goalimportance.riskFactor(riskfactor, testcaseid);

		if (plannedcontributionamt.length() != 0)
		{
			if ((Double.parseDouble(plannedinvamt) != 0 && Double.parseDouble(plannedcontributionamt) != 0) || (Double.parseDouble(plannedinvamt) != 0 && Double.parseDouble(plannedcontributionamt) == 0))
			{
				ArrayList<Object> plannedinvpagecontent = plannedinv.getPageContentOfPlannedInvestmentMoreThan55();

				Assert.assertEquals("Setting Up Your Other Goal", plannedinvpagecontent.get(0));
				Assert.assertEquals("Planned Investments", plannedinvpagecontent.get(1));
				Assert.assertEquals(true, plannedinvpagecontent.get(2));
				Assert.assertEquals("How much have you already saved for other goal?", plannedinvpagecontent.get(3));
				Assert.assertEquals("Planned Investments", plannedinvpagecontent.get(4));
				Assert.assertEquals(true, plannedinvpagecontent.get(5));
				Assert.assertEquals(TestUtil.convNum(suggestedinvestment), plannedinvpagecontent.get(6));

				plannedinv.setMonthlyInvestment(plannedinvamt, testcaseid, popup_flag_inv, goalamount, plannedinvamt, plannedcontributionamt, age);
				Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinv.pagecontentafterclick.get(0));

				if (Double.parseDouble(plannedinvamt) < Double.parseDouble(suggestedinvestment) && Double.parseDouble(suggestedaffordability1) < Double.parseDouble(goalamount))
				{

					Assert.assertEquals(TestUtil.convNum(suggestedinvestment), plannedinv.pagecontentafterclick.get(1));
					ArrayList<Object> plannedcontributionpagecontent = plannedinvsuggestions.getPageContentOfInvestmentSuggestionMoreThan55(plannedinvamt);

					Assert.assertEquals("Setting Up Your Other Goal", plannedcontributionpagecontent.get(0));
					Assert.assertEquals("Planned Contributions", plannedcontributionpagecontent.get(1));
					Assert.assertEquals(true, plannedcontributionpagecontent.get(2));
					Assert.assertEquals("Too much? Too Little?" + "\n" + "Let us know and we'll rerun the numbers", plannedcontributionpagecontent.get(3));
					Assert.assertEquals(true, plannedcontributionpagecontent.get(4));
					Assert.assertEquals(true, plannedcontributionpagecontent.get(5));
					Assert.assertEquals("Adjust my contributions with salary growth", plannedcontributionpagecontent.get(6));
					Assert.assertEquals(true, plannedcontributionpagecontent.get(7));
					Assert.assertEquals("Planned Monthly Contribution ($)", plannedcontributionpagecontent.get(8));
					Assert.assertEquals(TestUtil.convNum(plannedinvamt), plannedcontributionpagecontent.get(9));
					Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedcontributionpagecontent.get(10));
					Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedcontributionpagecontent.get(11));

					plannedinvsuggestions.setMonthlyContribution(plannedcontributionamt, testcaseid, popup_flag_contribution, checkbox, goalamount, plannedcontributionamt, plannedinvamt, age);
					Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), plannedinvsuggestions.pagecontentafterclick.get(0));

					if (Double.parseDouble(plannedcontributionamt) < Double.parseDouble(suggestedcontribution) && Double.parseDouble(suggestedaffordability2) < Double.parseDouble(goalamount))
					{
						if (Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamt) != 0)
						{
							Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedinvsuggestions.pagecontentafterclick.get(1));
							ArrayList<Object> delaypagecontent = delayingoal.getPageContentOfDelayGoalScreen(plannedcontributionamt, plannedinvamt, age);

							Assert.assertEquals("Setting Up Your Other Goal", delaypagecontent.get(0));
							Assert.assertEquals("Start Year", delaypagecontent.get(1));
							Assert.assertEquals(true, delaypagecontent.get(2));
							Assert.assertEquals("When would you like to make your purchase?", delaypagecontent.get(3));
							Assert.assertEquals("Number Of Years", delaypagecontent.get(4));
							Assert.assertEquals(true, delaypagecontent.get(5));
							Assert.assertEquals(true, delaypagecontent.get(6));
							Assert.assertEquals(true, delaypagecontent.get(7));
							Assert.assertEquals(planneddelay, delaypagecontent.get(8));
							Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), delaypagecontent.get(9));

							delayingoal.setGoalDelayDuration(delayduration, testcaseid);
							Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), delayingoal.pagecontentafterclick.get(0));
						}
						else if (Double.parseDouble(plannedinvamt) != 0 && Double.parseDouble(plannedcontributionamt) == 0)
						{
							Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedinvsuggestions.pagecontentafterclick.get(1));
							ArrayList<Object> pagecontentDelay = delayingoal.getPageContentOfDelayGoalScreen(plannedcontributionamt, plannedinvamt, age);

							Assert.assertEquals("Setting Up Your Other Goal", pagecontentDelay.get(0), "Goal header mismatch in delay page");
							Assert.assertEquals("Start Year", pagecontentDelay.get(1), "Goal sub header mismatch in delay page");
							Assert.assertEquals(true, pagecontentDelay.get(2), "Delay suggestion text missing in delay page");
							Assert.assertEquals("When would you like to make your purchase?", pagecontentDelay.get(3), "Sub text mismatch in delay page");
							Assert.assertEquals("Number Of Years", pagecontentDelay.get(4), "Delay placeholder text mismatch in delay page");
							Assert.assertEquals(true, pagecontentDelay.get(5), "Delay drop down missing in delay page");
							// Assert.assertEquals(true, pagecontentDelay.get(6),"Lable legand missing in delay page");
							// Assert.assertEquals(true, pagecontentDelay.get(7),"Lable legand text missing in delay page");
							Assert.assertEquals(planneddelay, pagecontentDelay.get(6), "Delay period mismatch");
							// Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), pagecontentDelay.get(9),"Suggested affordability before click mismatch in delay page");

							delayingoal.setGoalDelayDuration(delayduration, testcaseid);
							Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), delayingoal.pagecontentafterclick.get(0), "Suggested affordability after click mismatch in delay page");
						}
					}
				}
			}
			else if (Double.parseDouble(plannedinvamt) == 0 && Double.parseDouble(plannedcontributionamt) == 0)
			{

				ArrayList<Object> plannedinvpagecontent = plannedinv.getPageContentOfPlannedInvestmentMoreThan55();

				Assert.assertEquals("Setting Up Your Other Goal", plannedinvpagecontent.get(0));
				Assert.assertEquals("Planned Investments", plannedinvpagecontent.get(1));
				Assert.assertEquals(true, plannedinvpagecontent.get(2));
				Assert.assertEquals("How much have you already saved for other goal?", plannedinvpagecontent.get(3));
				Assert.assertEquals("Planned Investments", plannedinvpagecontent.get(4));
				Assert.assertEquals(true, plannedinvpagecontent.get(5));
				Assert.assertEquals(TestUtil.convNum(suggestedinvestment), plannedinvpagecontent.get(6));

				plannedinv.setMonthlyInvestment(plannedinvamt, testcaseid, popup_flag_inv, goalamount, plannedinvamt, plannedcontributionamt, age);
				// Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinv.pagecontentafterclick.get(0));

				// if(Double.parseDouble(plannedinvamt)<Double.parseDouble(suggestedinvestment) && Double.parseDouble(suggestedaffordability1) < Double.parseDouble(goalamount))
				// {

				Assert.assertEquals(TestUtil.convNum(suggestedinvestment), plannedinv.pagecontentafterclick.get(0));
				ArrayList<Object> plannedcontributionpagecontent = plannedinvsuggestions.getPageContentOfInvestmentSuggestionMoreThan55(plannedinvamt);

				Assert.assertEquals("Setting Up Your Other Goal", plannedcontributionpagecontent.get(0));
				Assert.assertEquals("Planned Contributions", plannedcontributionpagecontent.get(1));
				Assert.assertEquals(true, plannedcontributionpagecontent.get(2));
				Assert.assertEquals("Too much? Too Little?" + "\n" + "Let us know and we'll rerun the numbers", plannedcontributionpagecontent.get(3));
				Assert.assertEquals(true, plannedcontributionpagecontent.get(4));
				Assert.assertEquals(true, plannedcontributionpagecontent.get(5));
				Assert.assertEquals("Adjust my contributions with salary growth", plannedcontributionpagecontent.get(6));
				Assert.assertEquals(true, plannedcontributionpagecontent.get(7));
				Assert.assertEquals("Planned Monthly Contribution ($)", plannedcontributionpagecontent.get(8));
				Assert.assertEquals(TestUtil.convNum(plannedinvamt), plannedcontributionpagecontent.get(9));
				Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedcontributionpagecontent.get(10));
				// Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedcontributionpagecontent.get(11));

				plannedinvsuggestions.setMonthlyContribution(plannedcontributionamt, testcaseid, popup_flag_contribution, checkbox, goalamount, plannedcontributionamt, plannedinvamt, age);
			}
			else if (Double.parseDouble(plannedinvamt) == 0 && Double.parseDouble(plannedcontributionamt) != 0)
			{

				ArrayList<Object> plannedinvpagecontent = plannedinv.getPageContentOfPlannedInvestmentMoreThan55();

				Assert.assertEquals("Setting Up Your Other Goal", plannedinvpagecontent.get(0));
				Assert.assertEquals("Planned Investments", plannedinvpagecontent.get(1));
				Assert.assertEquals(true, plannedinvpagecontent.get(2));
				Assert.assertEquals("How much have you already saved for other goal?", plannedinvpagecontent.get(3));
				Assert.assertEquals("Planned Investments", plannedinvpagecontent.get(4));
				Assert.assertEquals(true, plannedinvpagecontent.get(5));
				Assert.assertEquals(TestUtil.convNum(suggestedinvestment), plannedinvpagecontent.get(6));

				plannedinv.setMonthlyInvestment(plannedinvamt, testcaseid, popup_flag_inv, goalamount, plannedinvamt, plannedcontributionamt, age);
				// Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinv.pagecontentafterclick.get(0));

				// if(Double.parseDouble(plannedinvamt)<Double.parseDouble(suggestedinvestment) && Double.parseDouble(suggestedaffordability1) < Double.parseDouble(goalamount))
				// {

				Assert.assertEquals(TestUtil.convNum(suggestedinvestment), plannedinv.pagecontentafterclick.get(0));
				ArrayList<Object> plannedcontributionpagecontent = plannedinvsuggestions.getPageContentOfInvestmentSuggestionMoreThan55(plannedinvamt);

				Assert.assertEquals("Setting Up Your Other Goal", plannedcontributionpagecontent.get(0));
				Assert.assertEquals("Planned Contributions", plannedcontributionpagecontent.get(1));
				Assert.assertEquals(true, plannedcontributionpagecontent.get(2));
				Assert.assertEquals("Too much? Too Little?" + "\n" + "Let us know and we'll rerun the numbers", plannedcontributionpagecontent.get(3));
				Assert.assertEquals(true, plannedcontributionpagecontent.get(4));
				Assert.assertEquals(true, plannedcontributionpagecontent.get(5));
				Assert.assertEquals("Adjust my contributions with salary growth", plannedcontributionpagecontent.get(6));
				Assert.assertEquals(true, plannedcontributionpagecontent.get(7));
				Assert.assertEquals("Planned Monthly Contribution ($)", plannedcontributionpagecontent.get(8));
				Assert.assertEquals(TestUtil.convNum(plannedinvamt), plannedcontributionpagecontent.get(9));
				Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedcontributionpagecontent.get(10));
				// Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedcontributionpagecontent.get(11));

				plannedinvsuggestions.setMonthlyContribution(plannedcontributionamt, testcaseid, popup_flag_contribution, checkbox, goalamount, plannedcontributionamt, plannedinvamt, age);
				Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), plannedinvsuggestions.pagecontentafterclick.get(0));

				if (Double.parseDouble(plannedcontributionamt) < Double.parseDouble(suggestedcontribution) && Double.parseDouble(suggestedaffordability2) < Double.parseDouble(goalamount))
				{
					// if(Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamt) != 0)
					// {
					Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedinvsuggestions.pagecontentafterclick.get(1));
					ArrayList<Object> delaypagecontent = delayingoal.getPageContentOfDelayGoalScreen(plannedcontributionamt, plannedinvamt, age);

					Assert.assertEquals("Setting Up Your Other Goal", delaypagecontent.get(0));
					Assert.assertEquals("Start Year", delaypagecontent.get(1));
					Assert.assertEquals(true, delaypagecontent.get(2));
					Assert.assertEquals("When would you like to make your purchase?", delaypagecontent.get(3));
					Assert.assertEquals("Number Of Years", delaypagecontent.get(4));
					Assert.assertEquals(true, delaypagecontent.get(5));
					Assert.assertEquals(true, delaypagecontent.get(6));
					Assert.assertEquals(true, delaypagecontent.get(7));
					Assert.assertEquals(planneddelay, delaypagecontent.get(8));
					Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), delaypagecontent.get(9));

					delayingoal.setGoalDelayDuration(delayduration, testcaseid);
					Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), delayingoal.pagecontentafterclick.get(0));
					
					// }
				}
				// }
			}
		}
		else
		{
			ArrayList<Object> plannedinvpagecontent = plannedinv.getPageContentOfPlannedInvestmentMoreThan55();

			Assert.assertEquals("Setting Up Your Other Goal", plannedinvpagecontent.get(0));
			Assert.assertEquals("Planned Investments", plannedinvpagecontent.get(1));
			Assert.assertEquals(true, plannedinvpagecontent.get(2));
			Assert.assertEquals("How much have you already saved for other goal?", plannedinvpagecontent.get(3));
			Assert.assertEquals("Planned Investments", plannedinvpagecontent.get(4));
			Assert.assertEquals(true, plannedinvpagecontent.get(5));
			Assert.assertEquals(TestUtil.convNum(suggestedinvestment), plannedinvpagecontent.get(6));

			plannedinv.setMonthlyInvestment(plannedinvamt, testcaseid, popup_flag_inv, goalamount, plannedinvamt, plannedcontributionamt, age);
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinv.pagecontentafterclick.get(0));
		}

		ArrayList<Object> annualspendingpagecontent = annualspending.getPageContentOfAnnualSpending(plannedcontributionamt, plannedinvamt);

		Assert.assertEquals("Setting Up Your Other Goal", annualspendingpagecontent.get(0));
		Assert.assertEquals("Annual Spending", annualspendingpagecontent.get(1));
		Assert.assertEquals(true, annualspendingpagecontent.get(2));
		Assert.assertEquals(true, annualspendingpagecontent.get(3));
		Assert.assertEquals(true, annualspendingpagecontent.get(4));
		Assert.assertEquals(true, annualspendingpagecontent.get(5));
		Assert.assertEquals(true, annualspendingpagecontent.get(6));
		Assert.assertEquals("Have questions?", annualspendingpagecontent.get(7));
		Assert.assertEquals(" Call an Advisor", annualspendingpagecontent.get(8));
		Assert.assertEquals("Sign up and view your recommended asset allocation", annualspendingpagecontent.get(9));
		Assert.assertEquals(TestUtil.convNum(goalamount), TestUtil.convNum((String) annualspendingpagecontent.get(10)));

		if (annualspendingpagecontent.size() == 13 && plannedcontributionamt.length() != 0 && (Double.parseDouble(plannedcontributionamt) != 0 || Double.parseDouble(plannedinvamt) != 0))
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(11), "Suggested affordability mismacth in annual spending page in suggested text");
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(12), "Suggested affordability mismacth in annual spending page in label legand");
		}
		else if (annualspendingpagecontent.size() == 13 && plannedcontributionamt.length() == 0)
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(11), "Suggested affordability mismacth in annual spending page in suggested text");
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(12), "Suggested affordability mismacth in annual spending page in label legand");
		}
		else if (annualspendingpagecontent.size() == 13 && (Double.parseDouble(plannedcontributionamt) == 0 && Double.parseDouble(plannedinvamt) == 0))
		{
			Assert.assertEquals("0", annualspendingpagecontent.get(11), "Suggested affordability mismacth in annual spending page in suggested text");
			// Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(12), "Suggested affordability mismacth in annual spending page in label legand");
		}
		else
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(11), "Suggested affordability mismacth in annual spending page in label legand");
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