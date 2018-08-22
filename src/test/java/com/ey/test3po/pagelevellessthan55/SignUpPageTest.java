package com.ey.test3po.pagelevellessthan55;

import java.net.MalformedURLException;

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

public class SignUpPageTest extends TestBase
{

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

	public SignUpPageTest()
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
	public Object[][] getQuestionnaireTestData()
	{
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}

	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void largePurchaseSignUpLessThan55(String annualincome, String zip, String age, String goalname, String purchaseyear, String goalamount, String goalduration, String riskfactor, String plannedcontributionamt, String plannedinvamt, String delayduration, String username, String email, String pwd, String repwd, String suggestedcontribution, String suggestedaffordability1, String suggestedcurrentasset, String suggestedaffordability2, String suggestedaffordability3, String planneddelay, String checkbox, String key)
	{

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

		userprofile.fillUserProfileQuestionnaire(annualincome, zip, age, key);
		selectgoal.goalChoice("Large Purchase", key);
		goalactivity.fillGoalQuestionnaire(goalname, purchaseyear, goalamount, goalduration, key);
		goalimportance.riskFactor(riskfactor, key);
		plannedinvsuggestions.setMonthlyContribution(plannedcontributionamt, key, popup_flag_contribution, checkbox, goalamount, plannedcontributionamt, plannedinvamt, age);
		plannedinv.setMonthlyInvestment(plannedinvamt, key, popup_flag_inv, goalamount, plannedinvamt, plannedcontributionamt, age);
		largepurchasedelayingoal.setGoalDelayDuration(delayduration, key);
		annualspending.signUp();
		signup.fillSignUpDetails(username, email, pwd, repwd, key);
	}

	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}