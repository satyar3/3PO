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

//@Listeners(ExtentReporterNG.class)
public class OtherGoalUserSignUpJourneyMoreThan55Test extends TestBase{
	
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
	
	public OtherGoalUserSignUpJourneyMoreThan55Test() {

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
	public Object[][] getQuestionnaireTestData() {
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}
	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void otherGoalGoalMoreThan55(String annualincome, String zip, String age,
			String goalname, String purchaseyear, String goalamount, String goalduration, String riskfactor, 
			String plannedinvamt, String plannedcontributionamt,  String delayduration, 
			String username, String email, String pwd, String repwd, 
			String suggestedinvestment, String suggestedaffordability1, String suggestedcontribution, String suggestedaffordability2, 
			String suggestedaffordability3, String planneddelay, String checkbox, String key)
	{

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
		
		userprofile.userProfileQuestionnaire(annualincome, zip, age, key);
		selectgoal.goalChoice("Other Goal", key);		
		goalactivity.goalJourney(goalname, purchaseyear, goalamount, goalduration, key);		
		goalimportance.riskFactor(riskfactor, key);		
		
		ArrayList<Object> plannedinvpagecontent = plannedinv.getPageContentMoreThan55();

		Assert.assertEquals("Setting Up Your Other Goal", plannedinvpagecontent.get(0));
		Assert.assertEquals("Planned Investments", plannedinvpagecontent.get(1));
		Assert.assertEquals(true, plannedinvpagecontent.get(2));
		Assert.assertEquals("How much have you already saved for other goal?", plannedinvpagecontent.get(3));
		Assert.assertEquals("Planned Investments", plannedinvpagecontent.get(4));
		Assert.assertEquals(true, plannedinvpagecontent.get(5));
		Assert.assertEquals(TestUtil.convNum(suggestedinvestment), plannedinvpagecontent.get(6));

		plannedinv.setMonthlyInv(plannedinvamt, key, popup_flag_inv, goalamount);
		Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedinv.pagecontentafterclick.get(0));
		
		if(Double.parseDouble(plannedinvamt)<Double.parseDouble(suggestedinvestment) && Double.parseDouble(suggestedaffordability1) < Double.parseDouble(goalamount))
		{

			Assert.assertEquals(TestUtil.convNum(suggestedinvestment), plannedinv.pagecontentafterclick.get(1));
			ArrayList<Object> plannedcontributionpagecontent = plannedinvsuggestions.getPageContentMoreThan55();		
			
			Assert.assertEquals("Setting Up Your Other Goal", plannedcontributionpagecontent.get(0));
			Assert.assertEquals("Planned Contributions", plannedcontributionpagecontent.get(1));
			Assert.assertEquals(true, plannedcontributionpagecontent.get(2));
			Assert.assertEquals("Too much? Too Little?"+"\n"+"Let us know and we'll rerun the numbers", plannedcontributionpagecontent.get(3));
			Assert.assertEquals(true, plannedcontributionpagecontent.get(4));
			Assert.assertEquals(true, plannedcontributionpagecontent.get(5));
			Assert.assertEquals("Adjust my contributions with salary growth", plannedcontributionpagecontent.get(6));
			Assert.assertEquals(true, plannedcontributionpagecontent.get(7));
			Assert.assertEquals("Planned Monthly Contribution ($)", plannedcontributionpagecontent.get(8));	
			Assert.assertEquals(TestUtil.convNum(plannedinvamt), plannedcontributionpagecontent.get(9));
			Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedcontributionpagecontent.get(10));
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability1), plannedcontributionpagecontent.get(11));

			plannedinvsuggestions.setMonthlyContribution(plannedcontributionamt, key, popup_flag_contribution, checkbox, goalamount);
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability2), plannedinvsuggestions.pagecontentafterclick.get(0));
			
			if(Double.parseDouble(plannedcontributionamt)<Double.parseDouble(suggestedcontribution) && Double.parseDouble(suggestedaffordability2) < Double.parseDouble(goalamount)) 
			{
				if(Double.parseDouble(plannedcontributionamt) != 0 && Double.parseDouble(plannedinvamt) != 0) 
				{
					Assert.assertEquals(TestUtil.convNum(suggestedcontribution), plannedinvsuggestions.pagecontentafterclick.get(1));
					ArrayList<Object> delaypagecontent = delayingoal.getPageContent();

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

					delayingoal.setdelayInGoal(delayduration, key);
					Assert.assertEquals(TestUtil.convNum(suggestedaffordability3),delayingoal.pagecontentafterclick.get(0));
				}
			}
		}
		
		ArrayList<Object> annualspendingpagecontent = annualspending.getPageContent();
		
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
				
		if(annualspendingpagecontent.size() == 13)
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(11));
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(12));
		}
		else
		{
			Assert.assertEquals(TestUtil.convNum(suggestedaffordability3), annualspendingpagecontent.get(11));
		}
		
		annualspending.signUp();
		//signup.signUp(username, email, pwd, repwd, key);
		System.out.println(key+" execution has been completed. Please find the errors in the TestNG/Extent report if any.");
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}