package com.ey.test3po.userjourney;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ey.test3po.pages.GoalSelectionPage;
import com.ey.test3po.pages.LargePurchaseDelayInGoalPage;
import com.ey.test3po.pages.LargePurchaseGoalActivityQuestionnairePage;
import com.ey.test3po.pages.LargePurchaseGoalAnnualSpendingPage;
import com.ey.test3po.pages.LargePurchaseGoalImportancePage;
import com.ey.test3po.pages.LargePurchaseInvSuggestionsPage;
import com.ey.test3po.pages.LargePurchasePlannedInvPage;
import com.ey.test3po.pages.SignUpPage;
import com.ey.test3po.pages.UserProfilePage;
import com.ey.test3po.pages.WelcomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

//@Listeners(ExtentReporterNG.class)
public class LargePurchaseUserSignUpJourneyLessThan55Test extends TestBase {

	WelcomePage homepage;
	GoalSelectionPage selectgoal;
	UserProfilePage userprofile;
	LargePurchaseGoalActivityQuestionnairePage goalactivity;
	LargePurchaseGoalImportancePage goalimportance;
	LargePurchaseInvSuggestionsPage plannedinvsuggestions;
	LargePurchaseGoalAnnualSpendingPage annualspending;
	LargePurchasePlannedInvPage plannedinv;
	LargePurchaseDelayInGoalPage largepurchasedelayingoal;
	SignUpPage signup;
	String sheetName = "LargePurchase";

	boolean popup_flag_contribution;
	boolean popup_flag_inv;

	public LargePurchaseUserSignUpJourneyLessThan55Test() {

		super();
	}

	@BeforeMethod
	public void setUp() throws MalformedURLException {
		initialization();
		homepage = new WelcomePage();
		selectgoal = new GoalSelectionPage();
		userprofile = new UserProfilePage();
		goalactivity = new LargePurchaseGoalActivityQuestionnairePage();
		goalimportance = new LargePurchaseGoalImportancePage();
		plannedinvsuggestions = new LargePurchaseInvSuggestionsPage();
		annualspending = new LargePurchaseGoalAnnualSpendingPage();
		plannedinv = new LargePurchasePlannedInvPage();
		largepurchasedelayingoal = new LargePurchaseDelayInGoalPage();
		signup = new SignUpPage();
		homepage.getStartedButtonClcik();

		popup_flag_contribution = true;
		popup_flag_inv = true;
	}

	@DataProvider
	public Object[][] getQuestionnaireTestData() {
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}

	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void largePurchaseGoalLessThan55(String annualincome, String zip, String age, String goalname,
			String purchaseyear, String goalamount, String goalduration, String riskfactor,
			String plannedcontributionamt, String plannedinvamt, String delayduration, String username, String email,
			String pwd, String repwd, String suggestedcontribution, String suggestedaffordability1,
			String suggestedcurrentasset, String suggestedaffordability2, String suggestedaffordability3,
			String planneddelay, String checkbox, String key) 
	{

		try
		{
			if (Double.parseDouble(plannedcontributionamt) >= Double.parseDouble(suggestedcontribution)) 
			{
				popup_flag_contribution = false;
			} else if (Double.parseDouble(plannedinvamt) >= Double.parseDouble(suggestedcurrentasset)) 
			{
				popup_flag_inv = false;
			}
		}
		catch(NumberFormatException e) 
		{
			popup_flag_contribution = false;
		}

		userprofile.userProfileQuestionnaire(annualincome, zip, age, key);
		selectgoal.goalChoice("Large Purchase", key);

		ArrayList<Object> pagecontentQtn = goalactivity.getPageContent();
		
		Assert.assertEquals("Setting Up Your Large Purchase Goal", pagecontentQtn.get(0), "Goal header mismatch in the questionnaire page");
		Assert.assertEquals("We populated some answers based on what we know about you.", pagecontentQtn.get(1), "Goal sub header mismatch in the questionnaire page");
		Assert.assertEquals(true, pagecontentQtn.get(2), "Goal name not displayed in the questionnaire page");
		Assert.assertEquals(true, pagecontentQtn.get(3), "Purchasing year not displayed in the questionnaire page");
		Assert.assertEquals(true, pagecontentQtn.get(4), "Goal amount not displayed in the questionnaire page");
		Assert.assertEquals(true, pagecontentQtn.get(5), "Goal age not displayed in the questionnaire page");
		Assert.assertEquals("Goal Name", pagecontentQtn.get(6), "Goal name placeholder text mismatch in the questionnaire page");
		Assert.assertEquals("Purchasing Year", pagecontentQtn.get(7), "Purchasing year placeholder text mismatch in the questionnaire page");
		Assert.assertEquals("Goal Amount", pagecontentQtn.get(8), "Goal Amount placeholder text mismatch in the questionnaire page");
		Assert.assertEquals("Is this a recurring goal? If so, please specify the duration of your goal", pagecontentQtn.get(9), "Recurring goal placeholer text mismatch in the questionnaire page");

		goalactivity.goalJourney(goalname, purchaseyear, goalamount, goalduration, key);
		goalimportance.riskFactor(riskfactor, key);

		ArrayList<Object> pagecontentInvSuggestion = plannedinvsuggestions.getPageContent();

		Assert.assertEquals("Setting Up Your Large Purchase Goal", pagecontentInvSuggestion.get(0), "Goal header mismatch in inv suggestion page");
		Assert.assertEquals("Planned Contributions", pagecontentInvSuggestion.get(1), "Goal sub header mismatch in inv suggestion page");
		Assert.assertEquals(true, pagecontentInvSuggestion.get(2), "Suggested plan mismatch in in suggestion page");
		Assert.assertEquals("Too much? Too Little?" + "\n" + "Let us know and we'll rerun the numbers", pagecontentInvSuggestion.get(3), "sub text mismatch in inv suggestion page");
		Assert.assertEquals(true, pagecontentInvSuggestion.get(4), "Inv suggestion text box not displayed in planned inv suggestion page");
		Assert.assertEquals(true, pagecontentInvSuggestion.get(5), "Inv suggestion check box not displayed in inv suggestion page");
		Assert.assertEquals("Adjust my contributions with salary growth", pagecontentInvSuggestion.get(6), "text mismatch next to check box in inv suggestion page");
		Assert.assertEquals(true, pagecontentInvSuggestion.get(7), "image is issing in text field in inv suggestion page");
		Assert.assertEquals("Planned Monthly Contribution ($)", pagecontentInvSuggestion.get(8), "Placeholder text mismatch in text box in inv suggestion textbox");
		Assert.assertEquals(TestUtil.convNum(goalamount), pagecontentInvSuggestion.get(9), "Goal amount mismatch in inv suggestion text in inv suggestion page");
		Assert.assertEquals(TestUtil.convNum(suggestedcontribution), pagecontentInvSuggestion.get(10), "Suggested contribution amount mismatch in inv suggestion page");
		Assert.assertEquals(String.valueOf(Integer.parseInt(purchaseyear) - 1), pagecontentInvSuggestion.get(11), "Year mismatch in inv suggestion text in inv suggestion page");

		plannedinvsuggestions.setMonthlyContribution(plannedcontributionamt, key, popup_flag_contribution, checkbox, goalamount);
		Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinvsuggestions.pagecontentafterclick.get(0), "Assertion 40 failed");

		if (Double.parseDouble(plannedcontributionamt) < Double.parseDouble(suggestedcontribution) && Double.parseDouble(suggestedaffordability1) < Double.parseDouble(goalamount)) 
		{
			Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedinvsuggestions.pagecontentafterclick.get(1), "Assertion 42 failed");
			ArrayList<Object> pagecontentPlannedInv = plannedinv.getPageContent();

			Assert.assertEquals("Setting Up Your Large Purchase Goal", pagecontentPlannedInv.get(0), "Goal header mismatch in planned inv page");
			Assert.assertEquals("Planned Investments", pagecontentPlannedInv.get(1), "Goal sub header mismatch in planned inv page");
			Assert.assertEquals(true, pagecontentPlannedInv.get(2), "Goal suggestion text missing in planned inv page");
			Assert.assertEquals("How much have you already saved for large purchase?", pagecontentPlannedInv.get(3), "Goal suggestion sub text mismatch in planned inv page");
			Assert.assertEquals("Planned Investments", pagecontentPlannedInv.get(4), "Text field placeholder mismatch in planned inv page");
			Assert.assertEquals(true, pagecontentPlannedInv.get(5), "Text field is missing in planned inv page");
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), pagecontentPlannedInv.get(6), "Label legand mismatch in planned inv page");
			Assert.assertEquals(true, pagecontentPlannedInv.get(7), "Label legand text is missing in planned inv page");
			Assert.assertEquals(TestUtil.convNum(plannedcontributionamt), pagecontentPlannedInv.get(8), "Contribution amount mismatch in planned inv page");
			Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), pagecontentPlannedInv.get(9), "Suggested current asset mismatch in planned inv page");
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), pagecontentPlannedInv.get(10), "Suggested affordability mismatch before click in planned inv page");

			plannedinv.setMonthlyInv(plannedinvamt, key, popup_flag_inv, goalamount);
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), plannedinv.pagecontentafterclick.get(0), "Suggested affordability after click in planned inv page");

			if (Double.parseDouble(plannedinvamt) < Double.parseDouble(suggestedcurrentasset)) 
			{
				if(Double.parseDouble(suggestedaffordability2) != Double.parseDouble(goalamount) && Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamt) != 0)
				{
					Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), plannedinv.pagecontentafterclick.get(1), "Pop up text mismatch in planned inv page");
					ArrayList<Object> pagecontentDelay = largepurchasedelayingoal.getPageContent();

					Assert.assertEquals("Setting Up Your Large Purchase Goal", pagecontentDelay.get(0), "Goal header mismatch in delay page");
					Assert.assertEquals("Purchasing Year", pagecontentDelay.get(1), "Goal sub header mismatch in delay page");
					Assert.assertEquals(true, pagecontentDelay.get(2), "Delay suggestion text missing in delay page");
					Assert.assertEquals("When would you like to make your purchase?", pagecontentDelay.get(3), "Sub text mismatch in delay page");
					Assert.assertEquals("Number Of Years", pagecontentDelay.get(4), "Delay placeholder text mismatch in delay page");
					Assert.assertEquals(true, pagecontentDelay.get(5), "Delay drop down missing in delay page");
					Assert.assertEquals(true, pagecontentDelay.get(6), "Lable legand missing in delay page");
					Assert.assertEquals(true, pagecontentDelay.get(7), "Lable legand sub text missing in delay page");
					Assert.assertEquals(planneddelay, pagecontentDelay.get(8), "Delay period mismatch");
					Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), pagecontentDelay.get(9), "Suggested affordability before click mismatch in delay page");

					largepurchasedelayingoal.setdelayInGoal(delayduration, key);
					Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), largepurchasedelayingoal.pagecontentafterclick.get(0),"Suggested affordability after click mismatch in delay page");

				}
			}
		}

		ArrayList<Object> pagecontentAnnualSepnding = annualspending.getPageContent();

		Assert.assertEquals("Setting Up Your Large Purchase Goal", pagecontentAnnualSepnding.get(0), "Header mismatch in annual spending page");
		Assert.assertEquals("Annual Spending", pagecontentAnnualSepnding.get(1), "Sub-header mismatch in annual spending page");
		Assert.assertEquals(true, pagecontentAnnualSepnding.get(2), "Annual spending suggested plan not displayed");
		Assert.assertEquals(true, pagecontentAnnualSepnding.get(3), "Annual spending sub-text not displayed");
		Assert.assertEquals(true, pagecontentAnnualSepnding.get(4), "Label legand is not displayed in annual spending page");
		Assert.assertEquals(true, pagecontentAnnualSepnding.get(5), "Label legand sub-text is not displayed in annual spending page");
		Assert.assertEquals(true, pagecontentAnnualSepnding.get(6), "Phone dialier image is missing in annual spending page");
		Assert.assertEquals("Have questions?", pagecontentAnnualSepnding.get(7), "Have questions section is missing");
		Assert.assertEquals(" Call an Advisor", pagecontentAnnualSepnding.get(8), "Call an Advisor section is missing");
		Assert.assertEquals("Sign up and view your recommended asset allocation", pagecontentAnnualSepnding.get(9), "Asset allocation section is missing in annual spending page");
		Assert.assertEquals(TestUtil.convNum(goalamount), TestUtil.convNum((String) pagecontentAnnualSepnding.get(10)), "Yearly expense mismatch in annual spending page");
				
		if(pagecontentAnnualSepnding.size() == 13)
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), pagecontentAnnualSepnding.get(11), "Suggested affordability mismacth in annual spending page in suggested text");
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), pagecontentAnnualSepnding.get(12), "Suggested affordability mismacth in annual spending page in label legand");
		}
		else
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), pagecontentAnnualSepnding.get(11), "Suggested affordability mismacth in annual spending page in label legand");
		}
		annualspending.signUp();
		//signup.signUp(username, email, pwd, repwd, key);
		System.out.println(key + " execution has been completed. Please find the errors in the TestNG/Extent report if any.");
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}