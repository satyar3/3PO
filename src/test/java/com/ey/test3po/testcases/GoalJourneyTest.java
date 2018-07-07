package com.ey.test3po.testcases;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ey.test3po.pages.GoalJourney;
import com.ey.test3po.pages.HomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.ExtentReporterListener;
import com.ey.test3po.util.TestUtil;



@Listeners(ExtentReporterListener.class)
public class GoalJourneyTest extends TestBase {

	HomePage homepage;
	GoalJourney goalJourney;
	String questionnaire_sheetName = "GetStartedQuestionnaire";
	String user_choice_sheetName = "Choice";
	String goalquestionnaire_sheetName = "GoalQuestionnaire";

	public GoalJourneyTest() {
		// TODO Auto-generated constructor stub
		super();
	}

	@BeforeTest
	public void setUp() throws MalformedURLException, InterruptedException {
		initialization();
		homepage = new HomePage();
		goalJourney = new GoalJourney();

		boolean flag = homepage.homePageActivity();
		if (flag == false) {
			//System.out.println("Application had taken more than 1 minute to lauch !! Terminating the test for Goals !!");
			Assert.fail();
		}
		else
		{
			homepage.getStartedButtonClcik();
		}
	}

	@Test(priority = 1)
	public void getCurrentActityForQuestionnaireTest() throws InterruptedException {
		
		//homepage.getStarted();
		Assert.assertEquals(".activity.UserInfoGpsActivity", findCurrentActivity());
		
	}

	// DataProvider for Questionnaire
	@DataProvider
	public Object[][] getQuestionnaireTestData() {
		Object ob[][] = TestUtil.getTestData(questionnaire_sheetName);
		return ob;
	}

	@Test(priority = 2, dependsOnMethods = "getCurrentActityForQuestionnaireTest", dataProvider = "getQuestionnaireTestData")
	public void fillQuestionnaireTest(String annualincome, String zip, String age, String key)
			throws InterruptedException {
		goalJourney.fillQuestionnaire(annualincome, zip, age, key);
		
		Assert.assertEquals(".activity.DashBoardActivity", findCurrentActivity());
	}

	// DataProvider to select Goal
	@DataProvider
	public Object[][] getGoalAndRiskfactor() {
		Object ob[][] = TestUtil.getTestData(user_choice_sheetName);
		return ob;
	}

	@Test(priority = 3, dependsOnMethods = "fillQuestionnaireTest", dataProvider = "getGoalAndRiskfactor")
	public void selectGoal(String goal, String riskfactor, String key) throws InterruptedException {
		GoalJourney.userChoice(goal, riskfactor, key);
		
	}

	// DataProvider for selected Goal
	@DataProvider
	public Object[][] getGoalSetTestData() {
		Object ob[][] = TestUtil.getTestData(goalquestionnaire_sheetName);
		return ob;
	}

	@Test(priority = 4, dependsOnMethods = "selectGoal", dataProvider = "getGoalSetTestData")
	public void goalJourneyTest(String goalname, String purchasingyear, String goalamount, String duration,
			String key)  {
		// Assert.assertEquals(".activity.LargePurchaseGoalActivity",
		// findCurrentActivity());
		goalJourney.goalJourney(goalname, purchasingyear, goalamount, duration, key);
		
		Assert.assertEquals(".activity.GoalImportanceActivity", findCurrentActivity());

	}

	@Test(priority = 5, dependsOnMethods = "goalJourneyTest")
	public void riskFactorTest()  
	{
		goalJourney.riskFactor();		
		//Assert.assertEquals("", findCurrentActivity());
	}

	@AfterTest
	public void tearDown() throws InterruptedException {
		waitToView();
		driver.quit();
	}

}
