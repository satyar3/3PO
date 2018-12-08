package com.ey.test3po.reguser;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ey.test3po.newuserjourney.OtherGoalUserSignUpJourneyLessThan55Test;
import com.ey.test3po.pages.GoalSelectionPage;
import com.ey.test3po.pages.LandingPage;
import com.ey.test3po.pages.OtherGoalActivityQuestionnairePage;
import com.ey.test3po.pages.OtherGoalAnnualSpendingPage;
import com.ey.test3po.pages.OtherGoalDelayInGoalPage;
import com.ey.test3po.pages.OtherGoalImportancePage;
import com.ey.test3po.pages.OtherGoalInvSuggestionsPage;
import com.ey.test3po.pages.OtherGoalPlannedInvPage;
import com.ey.test3po.pages.RegUserSignIn;
import com.ey.test3po.pages.SignUpPage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class AddOtherGoal extends TestBase
{

	RegUserSignIn reguser;
	LandingPage landingpage;
	GoalSelectionPage selectgoal;
	OtherGoalUserSignUpJourneyLessThan55Test othergoalless55;
	
	String sheetName = "OtherGoal";
	
	public AddOtherGoal()
	{
		super();
	}
	
	@BeforeMethod
	public void setUp() throws MalformedURLException
	{
		initialization();
		reguser = new RegUserSignIn();
		landingpage = new LandingPage();
		selectgoal = new GoalSelectionPage();	
		othergoalless55 = new OtherGoalUserSignUpJourneyLessThan55Test();
		
		othergoalless55.goalactivity = new OtherGoalActivityQuestionnairePage();
		othergoalless55.goalimportance = new OtherGoalImportancePage();
		othergoalless55.plannedinvsuggestions = new OtherGoalInvSuggestionsPage();
		othergoalless55.annualspending = new OtherGoalAnnualSpendingPage();
		othergoalless55.plannedinv = new OtherGoalPlannedInvPage();
		othergoalless55.delayingoal = new OtherGoalDelayInGoalPage();
		othergoalless55.signup = new SignUpPage();
		
		
		OtherGoalUserSignUpJourneyLessThan55Test.popup_flag_contribution = true;
		OtherGoalUserSignUpJourneyLessThan55Test.popup_flag_inv = true;
		OtherGoalUserSignUpJourneyLessThan55Test.signInUser = true;
		
		
		reguser.signIn(prop.getProperty("email"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getQuestionnaireTestData()
	{
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}
	
	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void addOtherGoal(String annualincome, String zip, String age, String goalname, String purchaseyear, String goalamount, String goalduration, String riskfactor, String plannedcontributionamt, String plannedinvamt, String delayduration, String username, String email, String pwd, String repwd, String suggestedcontribution, String suggestedaffordability1, String suggestedcurrentasset, String suggestedaffordability2, String suggestedaffordability3, String planneddelay, String checkbox, String testcaseid, String enabled)
	{
		
		
		testcasenum = testcaseid;
		driver.findElement(By.id(prop.getProperty("addgoalmenu"))).click();
		selectgoal.goalChoice("Other goal", testcaseid);
		
		othergoalless55.otherGoalGoalLessThan55(annualincome, zip, age, goalname, purchaseyear, goalamount, goalduration, riskfactor, plannedcontributionamt, plannedinvamt, delayduration, username, email, pwd, repwd, suggestedcontribution, suggestedaffordability1, suggestedcurrentasset, suggestedaffordability2, suggestedaffordability3, planneddelay, checkbox, testcaseid, enabled);
	
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}

}
