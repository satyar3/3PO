package com.ey.test3po.userjourney;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ey.test3po.pages.GoalSelectionPage;
import com.ey.test3po.pages.RetirementDelayInGoalPage;
import com.ey.test3po.pages.RetirementGoalActivityQuestionnairePage;
import com.ey.test3po.pages.RetirementGoalAnnualSpendingPage;
import com.ey.test3po.pages.RetirementGoalImportancePage;
import com.ey.test3po.pages.RetirementGoalInvSuggestionsPage;
import com.ey.test3po.pages.RetirementGoalPlannedInvPage;
import com.ey.test3po.pages.UserProfilePage;
import com.ey.test3po.pages.WelcomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

//@Listeners(TestListener.class)
public class RetirementGoalUserSignUpJourneyMoreThan55Test extends TestBase{
	
	WelcomePage homepage;
	GoalSelectionPage selectgoal;
	UserProfilePage userprofile;	
	RetirementGoalActivityQuestionnairePage goalactivity;
	RetirementGoalImportancePage goalimportance;
	RetirementGoalInvSuggestionsPage plannedinvsuggestions;
	RetirementGoalAnnualSpendingPage annualspending;
	RetirementGoalPlannedInvPage plannedinv;
	RetirementDelayInGoalPage delayingoal;
	String sheetName = "Retirement>55";
	
	boolean popup_flag_contribution;
	boolean popup_flag_inv;
	
	public RetirementGoalUserSignUpJourneyMoreThan55Test() {

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
		goalimportance = new RetirementGoalImportancePage();
		plannedinvsuggestions = new RetirementGoalInvSuggestionsPage();
		annualspending = new RetirementGoalAnnualSpendingPage();
		plannedinv = new RetirementGoalPlannedInvPage();
		delayingoal = new RetirementDelayInGoalPage();
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
	public void retirementGoalMoreThan55(String annualincome, String zip, String age, String goalname,	String retage, 
			String expperyr, String endyr, String incomepostret, String riskfactor, String plannedinvamt, String plannedcontributionamt,
			String delayduration, String username, String email, String pwd, String repwd, 
			String suggestedcurrentasset, String suggestedaffordability1, String suggestedcontribution, String suggestedaffordability2, String suggestedaffordability3, String expctedretgae, String checkbox, String key)
	{
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
		
		userprofile.userProfileQuestionnaire(annualincome, zip, age, key);
		selectgoal.goalChoice("Retirement", key);
		
		ArrayList<Object> goalactivityquestionnairepagecontent = goalactivity.getPageContent();			
		
		Assert.assertEquals("Setting Up Your Retirement Goal", goalactivityquestionnairepagecontent.get(0),"Retirement Goal Header mismatch in questionnare page");
		Assert.assertEquals("We populated some answers based on what we know about you.", goalactivityquestionnairepagecontent.get(1),"Retirement goal sub header mismatch in questionnaire page");
		Assert.assertEquals("Goal Name", goalactivityquestionnairepagecontent.get(2),"Goal name field place holder text");
		Assert.assertEquals("At what age would you like to/did you retire?", goalactivityquestionnairepagecontent.get(3),"what age you want to retie field placeholder text");
		Assert.assertEquals("How much do you want to spend per year after retirement?", goalactivityquestionnairepagecontent.get(4),"Post retirement income placeholder text");
		Assert.assertEquals("End year (Analysis)", goalactivityquestionnairepagecontent.get(5),"End year placeholder text");		
		Assert.assertEquals("What is your expected income per year after retirement? (optional)", goalactivityquestionnairepagecontent.get(6),"Expected income post retirement");
		Assert.assertEquals(true, goalactivityquestionnairepagecontent.get(7),"Goal name text field");
		Assert.assertEquals(true, goalactivityquestionnairepagecontent.get(8),"Goal age text field");
		Assert.assertEquals(true, goalactivityquestionnairepagecontent.get(9),"Per year exp text field");
		Assert.assertEquals(true, goalactivityquestionnairepagecontent.get(10),"Goal duration text field");
		Assert.assertEquals(true, goalactivityquestionnairepagecontent.get(11),"Post retirement income text field");
		
		goalactivity.goalJourney(goalname, retage, expperyr, endyr, incomepostret, key);
		goalimportance.riskFactor(riskfactor, key);	
		
		ArrayList<Object> plannedinvpagecontent = plannedinv.getPageContentMoreThan55();

		Assert.assertEquals("Setting Up Your Retirement Goal", plannedinvpagecontent.get(0),"Goal header mismatch in planned inv page");
		Assert.assertEquals("Planned Investments", plannedinvpagecontent.get(1),"Goal sub header mismatch in planned inv page");
		Assert.assertEquals(true, plannedinvpagecontent.get(2),"Goal suggestion text missing in planned inv page");
		Assert.assertEquals("How much have you already saved for retirement?", plannedinvpagecontent.get(3),"Goal suggestion sub text mismatch in planned inv page");
		Assert.assertEquals("Planned Investments", plannedinvpagecontent.get(4),"Text field placeholder mismatch in planned inv page");
		Assert.assertEquals(true, plannedinvpagecontent.get(5),"Text field is missing in planned inv page");
		Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), TestUtil.convNum((String) plannedinvpagecontent.get(6)),"Suggested current asset mismatch in planned inv page");

		plannedinv.setMonthlyInv(plannedinvamt, key, popup_flag_inv,expperyr);
		Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinv.pagecontentafterclick.get(0),"Suggested affordability mismatch ater page refresh in planned inv page");		
				
		if (Double.parseDouble(plannedinvamt) < Double.parseDouble(suggestedcurrentasset) && Double.parseDouble(suggestedaffordability1) < Double.parseDouble(expperyr))
		{
			Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), plannedinv.pagecontentafterclick.get(1),"pop up text mismatch in planned inv page");			
			ArrayList<Object> investmentsuggestionpagecontent = plannedinvsuggestions.getPageContentMoreThan55();

			Assert.assertEquals("Setting Up Your Retirement Goal", investmentsuggestionpagecontent.get(0),"Goal header in investment suggestion page");
			Assert.assertEquals("Planned Contributions", investmentsuggestionpagecontent.get(1),"Goal sub header in investment suggestion page");
			Assert.assertEquals(true, investmentsuggestionpagecontent.get(2),"Goal suggestion text is not displayed in investment suggestion page");
			Assert.assertEquals("Too much? Too Little?" + "\n" + "Let us know and we'll rerun the numbers",	investmentsuggestionpagecontent.get(3),"Goal suggested sub text is not displayed in investment suggestion page");
			Assert.assertEquals(true, investmentsuggestionpagecontent.get(4),"Text field is not displayed in investment suggestion page");
			Assert.assertEquals("Planned Monthly Contribution ($)", investmentsuggestionpagecontent.get(5),"Place holder text mismatch in text field in investment suggestion page");
			Assert.assertEquals(true, investmentsuggestionpagecontent.get(6),"Check box is not displayed in investment suggestion page");
			Assert.assertEquals("Adjust my contributions with salary growth", investmentsuggestionpagecontent.get(7),"Adjust contribution based on salary growth text mismatch in investment suggestion page");
			Assert.assertEquals(true, investmentsuggestionpagecontent.get(8),"Image not dispayed next to text field in investment suggestion page");
			Assert.assertEquals(TestUtil.convNum(plannedinvamt), investmentsuggestionpagecontent.get(9), "Goal amount mismatch in the investment suggestion text in investment suggestion page");
			Assert.assertEquals(TestUtil.convNum(suggestedcontribution), investmentsuggestionpagecontent.get(10), "Suggested contribution mismatch in suggesion text in investment suggestion page");
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), investmentsuggestionpagecontent.get(11), "Suggested affordability before click mismatch in suggesion text in investment suggestion page");
			//Ignored the previous labellegand
			
			plannedinvsuggestions.setMonthlyContribution(plannedcontributionamt, key, popup_flag_contribution, checkbox, expperyr);
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), plannedinvsuggestions.pagecontentafterclick.get(0),"Label legand amount mismatch in investment suggestion page");
			
			if (Double.parseDouble(plannedcontributionamt) < Double.parseDouble(suggestedcontribution) && Double.parseDouble(suggestedaffordability2) < Double.parseDouble(expperyr))
			{
				if(Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamt) != 0)
				{
					Assert.assertEquals(TestUtil.convNum(suggestedcontribution),plannedinvsuggestions.pagecontentafterclick.get(1),"pop up amount mismatch in investment suggestion page");
					ArrayList<Object> pagecontentDelay = delayingoal.getPageContent();

					Assert.assertEquals("Setting Up Your Retirement Goal", pagecontentDelay.get(0), "Goal header mismatch in delay page");
					Assert.assertEquals("Retirement Age", pagecontentDelay.get(1), "Goal sub header mismatch in delay page");
					Assert.assertEquals(true, pagecontentDelay.get(2), "Delay suggestion text missing in delay page");
					Assert.assertEquals("At what age are you willing to retire?", pagecontentDelay.get(3), "Sub text mismatch in delay page");
					Assert.assertEquals("Retirement Age", pagecontentDelay.get(4), "Retirement age placeholder text mismatch in delay page");
					Assert.assertEquals(true, pagecontentDelay.get(5), "Retirement age drop down missing in delay page");
					Assert.assertEquals(true, pagecontentDelay.get(6), "Lable legand missing in delay page");
					Assert.assertEquals(true, pagecontentDelay.get(7), "Lable legand sub text missing in delay page");
					Assert.assertEquals(expctedretgae, pagecontentDelay.get(8), "Retirement age in suggestion mismatch");
					Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), pagecontentDelay.get(9), "Suggested affordability before page refresh mismatch in delay page");
										
					delayingoal.setdelayInGoal(delayduration, key);
					Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), delayingoal.pagecontentafterclick.get(0),"Suggested affordability mismatch after page refresh");
				}
			}
		}
		
		ArrayList<Object> pagecontentannualSepnding = annualspending.getPageContent();		
		
		Assert.assertEquals("Setting Up Your Retirement Goal", pagecontentannualSepnding.get(0),"Header mismatch in annual spending page");
		Assert.assertEquals("Annual Spending", pagecontentannualSepnding.get(1),"Sub-header mismatch in annual spending page");
		Assert.assertEquals(true, pagecontentannualSepnding.get(2),"Annual spending suggested plan not displayed");
		Assert.assertEquals(true, pagecontentannualSepnding.get(3),"Annual spending sub-text not displayed");
		Assert.assertEquals(true, pagecontentannualSepnding.get(4),"Label legand is not displayed in annual spending page");
		Assert.assertEquals(true, pagecontentannualSepnding.get(5),"Label legand sub-text is not displayed in annual spending page");
		Assert.assertEquals(true, pagecontentannualSepnding.get(6),"Phone dialier image is missing in annual spending page");
		Assert.assertEquals("Have questions?", pagecontentannualSepnding.get(7),"Have questions section is missing");
		Assert.assertEquals(" Call an Advisor", pagecontentannualSepnding.get(8),"Call an Advisor section is missing");
		Assert.assertEquals("Sign up and view your recommended asset allocation", pagecontentannualSepnding.get(9),"Asset allocation section is missing in annual spending page");
		Assert.assertEquals(TestUtil.convNum(expperyr), TestUtil.convNum((String) pagecontentannualSepnding.get(10)),"Yearly expense mismatch in annual spending page");

		if(pagecontentannualSepnding.size() == 13)
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), pagecontentannualSepnding.get(11),"Suggested affordability mismacth in annual spending page");
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), pagecontentannualSepnding.get(12),"Suggested affordability mismacth in label legand in annual spending page");
		}
		else
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), pagecontentannualSepnding.get(11),"Suggested affordability mismacth in annual spending page");
		}
		
		System.out.println(key+" Execution has been completed. Please refer TestNG/Extent report for errors if any.");		
		annualspending.signUp();
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}