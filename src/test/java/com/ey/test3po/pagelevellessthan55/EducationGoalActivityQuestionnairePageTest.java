package com.ey.test3po.pagelevellessthan55;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ey.test3po.pages.EducationGoalActivityQuestionnairePage;
import com.ey.test3po.pages.GoalSelectionPage;
import com.ey.test3po.pages.UserProfilePage;
import com.ey.test3po.pages.WelcomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class EducationGoalActivityQuestionnairePageTest extends TestBase{
	
	WelcomePage homepage;
	GoalSelectionPage selectgoal;
	UserProfilePage userprofile;	
	EducationGoalActivityQuestionnairePage goalactivity;
	String sheetName = "Education";	
	
	public EducationGoalActivityQuestionnairePageTest() {

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
		homepage.getStartedButtonClcik();			
	}
	
	@DataProvider
	public Object[][] getQuestionnaireTestData() {
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}
	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void educationGoalActivityQuestionnaire(String annualincome, String zip, String age, 
			String goalname, String  startyear, String collegename, String yearlyexp, String collegeduration, 
			String riskfactor,String plannedcontributionamt,String plannedinvamt, String username, String email, String pwd, String repwd, 
			String suggestedcontribution, String suggestedaffordability1, String suggestedcurrentasset, String suggestedaffordability2, String checkbox, String key)
	{
		userprofile.userProfileQuestionnaire(annualincome, zip, age, key);
		selectgoal.goalChoice("Education", key);
		
		ArrayList<Object> pagecontent = goalactivity.getPageContent();		
		
		Assert.assertEquals("Setting up your Education Goal", pagecontent.get(0));
		Assert.assertEquals("We populated some answers based on what we know about you.", pagecontent.get(1));
		Assert.assertEquals("Goal Name", pagecontent.get(2));
		Assert.assertEquals("When will the freshman year start?", pagecontent.get(3));
		Assert.assertEquals("College Name", pagecontent.get(4));
		Assert.assertEquals("Yearly College expense", pagecontent.get(5));		
		Assert.assertEquals("College Duration", pagecontent.get(6));
		Assert.assertEquals(true, pagecontent.get(7));
		Assert.assertEquals(true, pagecontent.get(8));
		Assert.assertEquals(true, pagecontent.get(9));
		Assert.assertEquals(true, pagecontent.get(10));
		Assert.assertEquals(true, pagecontent.get(11));
		
		goalactivity.goalJourney(goalname, startyear, collegename, yearlyexp, collegeduration, key);		
		//Assert.assertEquals(".activity.DashBoardActivity", findCurrentActivity());
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}