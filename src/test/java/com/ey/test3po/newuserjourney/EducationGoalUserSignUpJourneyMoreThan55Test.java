package com.ey.test3po.newuserjourney;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ey.test3po.pages.EducationGoalActivityQuestionnairePage;
import com.ey.test3po.pages.EducationGoalAnnualSpendingPage;
import com.ey.test3po.pages.EducationGoalImportancePage;
import com.ey.test3po.pages.EducationGoalInvSuggestionsPage;
import com.ey.test3po.pages.EducationPlannedInvPage;
import com.ey.test3po.pages.GoalSelectionPage;
import com.ey.test3po.pages.UserProfilePage;
import com.ey.test3po.pages.WelcomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class EducationGoalUserSignUpJourneyMoreThan55Test extends TestBase
{

	WelcomePage homepage;
	GoalSelectionPage selectgoal;
	UserProfilePage userprofile;
	EducationGoalActivityQuestionnairePage goalactivity;
	EducationGoalImportancePage goalimportance;
	EducationGoalInvSuggestionsPage plannedinvsuggestions;
	EducationGoalAnnualSpendingPage annualspending;
	EducationPlannedInvPage plannedinv;
	String sheetName = "Education>55";

	boolean popup_flag_contribution;
	boolean popup_flag_inv;

	public EducationGoalUserSignUpJourneyMoreThan55Test()
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
		goalactivity = new EducationGoalActivityQuestionnairePage();
		goalimportance = new EducationGoalImportancePage();
		plannedinvsuggestions = new EducationGoalInvSuggestionsPage();
		annualspending = new EducationGoalAnnualSpendingPage();
		plannedinv = new EducationPlannedInvPage();
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
	public void educationGoalMoreThan55(String annualincome, String zip, String age, String goalname, String startyear, String collegename, String yearlyexp, String collegeduration, String riskfactor, String plannedinvamt, String plannedcontributionamt, String username, String email, String pwd, String repwd, String suggestedcurrentasset, String suggestedaffordability1, String suggestedcontribution, String suggestedaffordability2, String checkbox, String testcaseid, String enabled)
	{

		testcasenum = testcaseid;
		System.out.println(testcaseid + " execution has been started.");

		try
		{
			if (Double.parseDouble(plannedinvamt) >= Double.parseDouble(suggestedcurrentasset))
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
		selectgoal.goalChoice("Education", testcaseid);
		goalactivity.fillGoalQuestionnaire(goalname, startyear, collegename, yearlyexp, collegeduration, testcaseid);
		goalimportance.riskFactor(riskfactor, testcaseid);

		if (plannedcontributionamt.length() != 0)
		{
			if ((Double.parseDouble(plannedinvamt) != 0 && Double.parseDouble(plannedcontributionamt) != 0) || (Double.parseDouble(plannedinvamt) != 0 && Double.parseDouble(plannedcontributionamt) == 0))
			{

				ArrayList<Object> monthlyinvestmentpagecontent = plannedinv.getPageContentOfPlannedInvestmentMoreThan55();

				Assert.assertEquals("Setting up your education goal", monthlyinvestmentpagecontent.get(0), "Header mismatch in investment page");
				Assert.assertEquals("Planned investments", monthlyinvestmentpagecontent.get(1), "Sub-header mismatch in investment suggestion page");
				Assert.assertEquals(true, monthlyinvestmentpagecontent.get(2), "Suggested plan not displayed in the investment page");
				Assert.assertEquals("How much have you already saved for education?", monthlyinvestmentpagecontent.get(3), "Investment subtext mismatch");
				Assert.assertEquals("Planned investments", monthlyinvestmentpagecontent.get(4), "Planned investment placeholder text mismatch");
				Assert.assertEquals(true, monthlyinvestmentpagecontent.get(5), "Investment inputbox is missing");
				Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), monthlyinvestmentpagecontent.get(6), "Suggested current asset mismatch");

				plannedinv.setMonthlyInvestmentAmount(plannedinvamt, testcaseid, age, popup_flag_inv, yearlyexp, plannedcontributionamt, plannedinvamt);
				Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinv.pagecontentafterclick.get(0), "Updated label legand amount mismatch in investment page");

				if (Double.parseDouble(plannedinvamt) < Double.parseDouble(suggestedcurrentasset) && Double.parseDouble(suggestedaffordability1) < Double.parseDouble(yearlyexp))
				{
					Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), plannedinv.pagecontentafterclick.get(1), "Pop up amount mismatch");
					ArrayList<Object> plannedinvsuggestionpagecontent = plannedinvsuggestions.getPageContentOfInvestmentSuggestionMoreThan55(plannedinvamt);

					Assert.assertEquals("Setting up your education goal", plannedinvsuggestionpagecontent.get(0), "Header mismatch in investment suggestion page");
					Assert.assertEquals("Planned contributions", plannedinvsuggestionpagecontent.get(1), "Sub-header mismatch in investment suggestion page");
					Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(2), "Investment suggestion text is missing");
					Assert.assertEquals("Too much? Too little?" + "\n" + "Let us know and we'll rerun the numbers", plannedinvsuggestionpagecontent.get(3), "Sub-text mismmatch in investment page");
					Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(4), "Contribution input field in missing");
					Assert.assertEquals("Planned monthly contribution ($)", plannedinvsuggestionpagecontent.get(5), "Contribution input field placeholder text mismatch");
					Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(6), "Check box is missning");
					Assert.assertEquals("Adjust my contributions with salary growth", plannedinvsuggestionpagecontent.get(7), "Contribution suggestion is missing");
					Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(8), "image is missing");
					Assert.assertEquals(TestUtil.convNum(plannedinvamt), plannedinvsuggestionpagecontent.get(9), "Goal amount mismatch in contribution suggestion page");
					Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedinvsuggestionpagecontent.get(10), "Suggested contribution mismatch in contribution suggestion page");
					Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinvsuggestionpagecontent.get(11), "Year value mismatch in contribution suggestion page");

					plannedinvsuggestions.setMonthlyContribution(plannedcontributionamt, testcaseid, popup_flag_contribution, age, checkbox, yearlyexp, plannedcontributionamt, plannedinvamt);
					Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), plannedinvsuggestions.pagecontentafterclick.get(0), "Label legand amount mismatch");

				}
			}
			else if(Double.parseDouble(plannedinvamt) == 0 && Double.parseDouble(plannedcontributionamt) != 0)
			{
				ArrayList<Object> monthlyinvestmentpagecontent = plannedinv.getPageContentOfPlannedInvestmentMoreThan55();

				Assert.assertEquals("Setting up your education goal", monthlyinvestmentpagecontent.get(0), "Header mismatch in investment page");
				Assert.assertEquals("Planned investments", monthlyinvestmentpagecontent.get(1), "Sub-header mismatch in investment suggestion page");
				Assert.assertEquals(true, monthlyinvestmentpagecontent.get(2), "Suggested plan not displayed in the investment page");
				Assert.assertEquals("How much have you already saved for education?", monthlyinvestmentpagecontent.get(3), "Investment subtext mismatch");
				Assert.assertEquals("Planned investments", monthlyinvestmentpagecontent.get(4), "Planned investment placeholder text mismatch");
				Assert.assertEquals(true, monthlyinvestmentpagecontent.get(5), "Investment inputbox is missing");
				Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), monthlyinvestmentpagecontent.get(6), "Suggested current asset mismatch");

				plannedinv.setMonthlyInvestmentAmount(plannedinvamt, testcaseid, age, popup_flag_inv, yearlyexp, plannedcontributionamt, plannedinvamt);
				//Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinv.pagecontentafterclick.get(0), "Updated label legand amount mismatch in investment page");

				//if (Double.parseDouble(plannedinvamt) < Double.parseDouble(suggestedcurrentasset) && Double.parseDouble(suggestedaffordability1) < Double.parseDouble(yearlyexp))
				//{
					Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), plannedinv.pagecontentafterclick.get(0), "Pop up amount mismatch");
					ArrayList<Object> plannedinvsuggestionpagecontent = plannedinvsuggestions.getPageContentOfInvestmentSuggestionMoreThan55(plannedinvamt);

					Assert.assertEquals("Setting up your education goal", plannedinvsuggestionpagecontent.get(0), "Header mismatch in investment suggestion page");
					Assert.assertEquals("Planned contributions", plannedinvsuggestionpagecontent.get(1), "Sub-header mismatch in investment suggestion page");
					Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(2), "Investment suggestion text is missing");
					Assert.assertEquals("Too much? Too little?" + "\n" + "Let us know and we'll rerun the numbers", plannedinvsuggestionpagecontent.get(3), "Sub-text mismmatch in investment page");
					Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(4), "Contribution input field in missing");
					Assert.assertEquals("Planned monthly contribution ($)", plannedinvsuggestionpagecontent.get(5), "Contribution input field placeholder text mismatch");
					Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(6), "Check box is missning");
					Assert.assertEquals("Adjust my contributions with salary growth", plannedinvsuggestionpagecontent.get(7), "Contribution suggestion is missing");
					Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(8), "image is missing");
					Assert.assertEquals(TestUtil.convNum(plannedinvamt), plannedinvsuggestionpagecontent.get(9), "Goal amount mismatch in contribution suggestion page");
					Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedinvsuggestionpagecontent.get(10), "Suggested contribution mismatch in contribution suggestion page");
					//Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinvsuggestionpagecontent.get(11), "Year value mismatch in contribution suggestion page");

					plannedinvsuggestions.setMonthlyContribution(plannedcontributionamt, testcaseid, popup_flag_contribution, age, checkbox, yearlyexp, plannedcontributionamt, plannedinvamt);
					Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), plannedinvsuggestions.pagecontentafterclick.get(0), "Label legand amount mismatch");

				//}			
			}
			
			else
			{

				ArrayList<Object> monthlyinvestmentpagecontent = plannedinv.getPageContentOfPlannedInvestmentMoreThan55();

				Assert.assertEquals("Setting up your education goal", monthlyinvestmentpagecontent.get(0), "Header mismatch in investment page");
				Assert.assertEquals("Planned investments", monthlyinvestmentpagecontent.get(1), "Sub-header mismatch in investment suggestion page");
				Assert.assertEquals(true, monthlyinvestmentpagecontent.get(2), "Suggested plan not displayed in the investment page");
				Assert.assertEquals("How much have you already saved for education?", monthlyinvestmentpagecontent.get(3), "Investment subtext mismatch");
				Assert.assertEquals("Planned investments", monthlyinvestmentpagecontent.get(4), "Planned investment placeholder text mismatch");
				Assert.assertEquals(true, monthlyinvestmentpagecontent.get(5), "Investment inputbox is missing");
				Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), monthlyinvestmentpagecontent.get(6), "Suggested current asset mismatch");

				plannedinv.setMonthlyInvestmentAmount(plannedinvamt, testcaseid, age, popup_flag_inv, yearlyexp, plannedcontributionamt, plannedinvamt);
				//Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinv.pagecontentafterclick.get(0), "Updated label legand amount mismatch in investment page");

				//if (Double.parseDouble(plannedinvamt) < Double.parseDouble(suggestedcurrentasset) && Double.parseDouble(suggestedaffordability1) < Double.parseDouble(yearlyexp))
				//{
					Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), plannedinv.pagecontentafterclick.get(0), "Pop up amount mismatch");
					ArrayList<Object> plannedinvsuggestionpagecontent = plannedinvsuggestions.getPageContentOfInvestmentSuggestionMoreThan55(plannedinvamt);

					Assert.assertEquals("Setting up your education goal", plannedinvsuggestionpagecontent.get(0), "Header mismatch in investment suggestion page");
					Assert.assertEquals("Planned contributions", plannedinvsuggestionpagecontent.get(1), "Sub-header mismatch in investment suggestion page");
					Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(2), "Investment suggestion text is missing");
					Assert.assertEquals("Too much? Too little?" + "\n" + "Let us know and we'll rerun the numbers", plannedinvsuggestionpagecontent.get(3), "Sub-text mismmatch in investment page");
					Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(4), "Contribution input field in missing");
					Assert.assertEquals("Planned monthly contribution ($)", plannedinvsuggestionpagecontent.get(5), "Contribution input field placeholder text mismatch");
					Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(6), "Check box is missning");
					Assert.assertEquals("Adjust my contributions with salary growth", plannedinvsuggestionpagecontent.get(7), "Contribution suggestion is missing");
					Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(8), "image is missing");
					Assert.assertEquals(TestUtil.convNum(plannedinvamt), plannedinvsuggestionpagecontent.get(9), "Goal amount mismatch in contribution suggestion page");
					Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedinvsuggestionpagecontent.get(10), "Suggested contribution mismatch in contribution suggestion page");
					//Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinvsuggestionpagecontent.get(11), "Year value mismatch in contribution suggestion page");

					plannedinvsuggestions.setMonthlyContribution(plannedcontributionamt, testcaseid, popup_flag_contribution, age, checkbox, yearlyexp, plannedcontributionamt, plannedinvamt);
					//Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), plannedinvsuggestions.pagecontentafterclick.get(0), "Label legand amount mismatch");

				//}			
			
			}
		}
		
		else
		{

			ArrayList<Object> monthlyinvestmentpagecontent = plannedinv.getPageContentOfPlannedInvestmentMoreThan55();

			Assert.assertEquals("Setting up your education goal", monthlyinvestmentpagecontent.get(0), "Header mismatch in investment page");
			Assert.assertEquals("Planned investments", monthlyinvestmentpagecontent.get(1), "Sub-header mismatch in investment suggestion page");
			Assert.assertEquals(true, monthlyinvestmentpagecontent.get(2), "Suggested plan not displayed in the investment page");
			Assert.assertEquals("How much have you already saved for education?", monthlyinvestmentpagecontent.get(3), "Investment subtext mismatch");
			Assert.assertEquals("Planned investments", monthlyinvestmentpagecontent.get(4), "Planned investment placeholder text mismatch");
			Assert.assertEquals(true, monthlyinvestmentpagecontent.get(5), "Investment inputbox is missing");
			Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), monthlyinvestmentpagecontent.get(6), "Suggested current asset mismatch");

			plannedinv.setMonthlyInvestmentAmount(plannedinvamt, testcaseid, age, popup_flag_inv, yearlyexp, plannedcontributionamt, plannedinvamt);
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinv.pagecontentafterclick.get(0), "Updated label legand amount mismatch in investment page");

		}
		
		ArrayList<Object> annualspendingpagecontent = annualspending.getPageContentOfAnnualSpending(age,plannedcontributionamt, plannedinvamt);

		Assert.assertEquals("Setting up your education goal", annualspendingpagecontent.get(0), "Header mismatch in Annual spending page");
		Assert.assertEquals("Annual spending", annualspendingpagecontent.get(1), "Sub-header mismatch in Annual spending page");
		Assert.assertEquals(true, annualspendingpagecontent.get(2), "Annual spending suggested plan not displayed");
		Assert.assertEquals(true, annualspendingpagecontent.get(3), "Annual spending sub-text not displayed");
		Assert.assertEquals(true, annualspendingpagecontent.get(4), "Label legand is not displayed in Annual spending page");
		Assert.assertEquals(true, annualspendingpagecontent.get(5), "Label legand sub-text is not displayed in Annual spending page");
		Assert.assertEquals(true, annualspendingpagecontent.get(6), "Phone dialier image is missing in Annual spending page");
		Assert.assertEquals("Have questions?", annualspendingpagecontent.get(7), "Have questions section is missing");
		Assert.assertEquals(" Call an Advisor", annualspendingpagecontent.get(8), "Call an Advisor section is missing");
		Assert.assertEquals("Sign up and view your recommended asset allocation", annualspendingpagecontent.get(9), "Asset allocation section is missing in Annual spending page");
		Assert.assertEquals(TestUtil.convNum(yearlyexp), TestUtil.convNum((String) annualspendingpagecontent.get(10)), "Yearly expense mismatch in Annual spending page");

		
		if (annualspendingpagecontent.size() == 13 && plannedcontributionamt.length() == 0)
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), annualspendingpagecontent.get(11), "Suggested affordability mismacth in Annual spending page suggested plan");
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), annualspendingpagecontent.get(12), "Suggested affordability mismacth in Annual spending page label legand");
		}
		else if (annualspendingpagecontent.size() == 13 && plannedcontributionamt.length() != 0 && (Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamt) != 0) || (Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamt) == 0))
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), annualspendingpagecontent.get(11), "Suggested affordability mismacth in Annual spending page suggested plan");
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), annualspendingpagecontent.get(12), "Suggested affordability mismacth in Annual spending page label legand");
		}
		else if (annualspendingpagecontent.size() == 13 && plannedcontributionamt.length() != 0 && (Double.parseDouble(plannedcontributionamt) == 0 && Double.parseDouble(plannedinvamt) != 0))
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), annualspendingpagecontent.get(11), "Suggested affordability mismacth in Annual spending page suggested plan");
		}
		else if (annualspendingpagecontent.size() == 13 && (Double.parseDouble(plannedcontributionamt) == 0 && Double.parseDouble(plannedinvamt) == 0))
		{
			Assert.assertEquals("0", annualspendingpagecontent.get(11), "Suggested affordability mismacth in Annual spending page suggested plan");
		}
		else
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), annualspendingpagecontent.get(11), "Suggested affordability mismacth in Annual spending page in label legand");
		}

		annualspending.signUp();
		System.out.println(testcaseid + " execution has been completed.");
	}

	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}