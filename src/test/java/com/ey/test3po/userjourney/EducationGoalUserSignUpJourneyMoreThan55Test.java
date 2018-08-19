package com.ey.test3po.userjourney;

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


//@Listeners(ExtentReporterNG.class)
public class EducationGoalUserSignUpJourneyMoreThan55Test extends TestBase{
	
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
	
	public EducationGoalUserSignUpJourneyMoreThan55Test() {

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
	public Object[][] getQuestionnaireTestData() {
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}
	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void educationGoalMoreThan55(String annualincome, String zip, String age, 
			String goalname, String  startyear, String collegename, String yearlyexp, String collegeduration, 
			String riskfactor,String plannedinvamt, String plannedcontributionamt, String username, String email, String pwd, String repwd, 
			String suggestedcurrentasset, String suggestedaffordability1, String suggestedcontribution, String suggestedaffordability2, String checkbox, String key)
	{
		
		try
		{			
			if(Double.parseDouble(plannedinvamt)>=Double.parseDouble(suggestedcurrentasset)) 
			{
				popup_flag_inv = false;
			}
			else if(Double.parseDouble(plannedcontributionamt)>=Double.parseDouble(suggestedcontribution)) 
			{
				popup_flag_contribution = false;
			}
		}
		catch(NumberFormatException e)
		{
			popup_flag_contribution = false;;
		}
		
		userprofile.userProfileQuestionnaire(annualincome, zip, age, key);
		selectgoal.goalChoice("Education", key);
		goalactivity.goalJourney(goalname, startyear, collegename, yearlyexp, collegeduration, key);
		goalimportance.riskFactor(riskfactor, key);		
				
		ArrayList<Object> monthlyinvestmentpagecontent = plannedinv.getPageContentMoreThan55();

		Assert.assertEquals("Setting Up Your Education Goal", monthlyinvestmentpagecontent.get(0),"Header mismatch in investment page");
		Assert.assertEquals("Planned Investments", monthlyinvestmentpagecontent.get(1),"Sub-header mismatch in investment suggestion page");
		Assert.assertEquals(true, monthlyinvestmentpagecontent.get(2),"Suggested plan not displayed in the investment page");
		Assert.assertEquals("How much have you already saved for education?", monthlyinvestmentpagecontent.get(3),"Investment subtext mismatch");
		Assert.assertEquals("Planned Investments", monthlyinvestmentpagecontent.get(4),"Planned investment placeholder text mismatch");
		Assert.assertEquals(true, monthlyinvestmentpagecontent.get(5),"Investment inputbox is missing");
		Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), monthlyinvestmentpagecontent.get(6),"Suggested current asset mismatch");

		plannedinv.setMonthlyInv(plannedinvamt, key, age, popup_flag_inv, yearlyexp);
		Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinv.pagecontentafterclick.get(0),"Updated label legand amount mismatch in investment page");
		
		if(Double.parseDouble(plannedinvamt)<Double.parseDouble(suggestedcurrentasset) && Double.parseDouble(suggestedaffordability1) < Double.parseDouble(yearlyexp)) 
		{
			Assert.assertEquals(TestUtil.convNum(suggestedcurrentasset), plannedinv.pagecontentafterclick.get(1),"Pop up amount mismatch");
			ArrayList<Object> plannedinvsuggestionpagecontent = plannedinvsuggestions.getPageContentMoreThan55();

			Assert.assertEquals("Setting Up Your Education Goal", plannedinvsuggestionpagecontent.get(0),"Header mismatch in investment suggestion page");
			Assert.assertEquals("Planned Contributions", plannedinvsuggestionpagecontent.get(1),"Sub-header mismatch in investment suggestion page");
			Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(2),"Investment suggestion text is missing");
			Assert.assertEquals("Too much? Too Little?" + "\n" + "Let us know and we'll rerun the numbers",	plannedinvsuggestionpagecontent.get(3),"Sub-text mismmatch in investment page");
			Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(4),"Contribution input field in missing");
			Assert.assertEquals("Planned Monthly Contribution ($)", plannedinvsuggestionpagecontent.get(5),"Contribution input field placeholder text mismatch");
			Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(6),"Check box is missning");
			Assert.assertEquals("Adjust my contributions with salary growth", plannedinvsuggestionpagecontent.get(7),"Contribution suggestion is missing");
			Assert.assertEquals(true, plannedinvsuggestionpagecontent.get(8),"image is missing");
			Assert.assertEquals(TestUtil.convNum(plannedinvamt), plannedinvsuggestionpagecontent.get(9),"Goal amount mismatch in contribution suggestion page");
			Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedinvsuggestionpagecontent.get(10),"Suggested contribution mismatch in contribution suggestion page");
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinvsuggestionpagecontent.get(11),"Year value mismatch in contribution suggestion page");

			plannedinvsuggestions.setMonthlyContribution(plannedcontributionamt, key, popup_flag_contribution,age,checkbox, yearlyexp);
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), plannedinvsuggestions.pagecontentafterclick.get(0),"Label legand amount mismatch");

		}
	
		ArrayList<Object> annualspendingpagecontent = annualspending.getPageContent();
		
		Assert.assertEquals("Setting Up Your Education Goal", annualspendingpagecontent.get(0),"Header mismatch in annual spending page");
		Assert.assertEquals("Annual Spending", annualspendingpagecontent.get(1),"Sub-header mismatch in annual spending page");
		Assert.assertEquals(true, annualspendingpagecontent.get(2),"Annual spending suggested plan not displayed");
		Assert.assertEquals(true, annualspendingpagecontent.get(3),"Annual spending sub-text not displayed");
		Assert.assertEquals(true, annualspendingpagecontent.get(4),"Label legand is not displayed in annual spending page");
		Assert.assertEquals(true, annualspendingpagecontent.get(5),"Label legand sub-text is not displayed in annual spending page");
		Assert.assertEquals(true, annualspendingpagecontent.get(6),"Phone dialier image is missing in annual spending page");
		Assert.assertEquals("Have questions?", annualspendingpagecontent.get(7),"Have questions section is missing");
		Assert.assertEquals(" Call an Advisor", annualspendingpagecontent.get(8),"Call an Advisor section is missing");
		Assert.assertEquals("Sign up and view your recommended asset allocation", annualspendingpagecontent.get(9),"Asset allocation section is missing in annual spending page");
		Assert.assertEquals(TestUtil.convNum(yearlyexp), TestUtil.convNum((String) annualspendingpagecontent.get(10)),"Yearly expense mismatch in annual spending page");
			
		if(annualspendingpagecontent.size() == 13)
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), annualspendingpagecontent.get(11),"Suggested affordability mismacth in annual spending page in suggested text");
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), annualspendingpagecontent.get(12),"Suggested affordability mismacth in annual spending page in label legand");
		}
		else
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), annualspendingpagecontent.get(11),"Suggested affordability mismacth in annual spending page in label legand");
		}
		
		annualspending.signUp();
		System.out.println(key+" execution has been completed. Please find the errors in the TestNG/Extent report if any.");
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}