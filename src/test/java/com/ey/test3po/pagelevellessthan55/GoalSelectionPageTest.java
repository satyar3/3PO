package com.ey.test3po.pagelevellessthan55;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ey.test3po.pages.GoalSelectionPage;
import com.ey.test3po.pages.UserProfilePage;
import com.ey.test3po.pages.WelcomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class GoalSelectionPageTest extends TestBase
{

	WelcomePage homepage;
	GoalSelectionPage selectgoal;
	UserProfilePage userprofile;
	String sheetName = "GoalChoice";

	public GoalSelectionPageTest()
	{
		super();
	}

	@BeforeMethod()
	public void setUp() throws MalformedURLException, InterruptedException
	{

		initialization();
		homepage = new WelcomePage();
		selectgoal = new GoalSelectionPage();
		homepage.getStartedButtonClcik();
		userprofile = new UserProfilePage();
	}

	@DataProvider
	public Object[][] getQuestionnaireTestData()
	{
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}

	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void goalSelection(String annualincome, String zip, String age, String goal, String key, String enabled)
	{
		userprofile.fillUserProfileQuestionnaire(annualincome, zip, age, key);

		ArrayList<Object> pagecontent = selectgoal.getPageContentOfGoalSelectionPage();

		Assert.assertEquals("Select Your Goal", pagecontent.get(0));
		Assert.assertEquals("If you had an extra dollar, how would you spend it?", pagecontent.get(1));
		Assert.assertEquals("Emergency Fund", pagecontent.get(2));
		Assert.assertEquals("Education", pagecontent.get(3));
		Assert.assertEquals("Retirement", pagecontent.get(4));
		Assert.assertEquals("Other Goal", pagecontent.get(5));
		Assert.assertEquals("Large Purchase", pagecontent.get(6));
		Assert.assertEquals("Do you have a big purchase to save for? Maybe a house, a car, or that vacation you've been wanting to take?", pagecontent.get(7));
		Assert.assertEquals("Emergency Fund", pagecontent.get(8));
		Assert.assertEquals("Make sure you've got enough in the bank for those rainy days!", pagecontent.get(9));
		Assert.assertEquals("Education", pagecontent.get(10));
		Assert.assertEquals("Need to pay for your kid's education or save up for yourself?", pagecontent.get(11));
		Assert.assertEquals("Retirement", pagecontent.get(12));
		Assert.assertEquals("Let's make sure you've got enough money saved, so you don't have to worry about working forever!", pagecontent.get(13));
		Assert.assertEquals("Other Goal", pagecontent.get(14));
		Assert.assertEquals("If you've got a special, unique goal, we can help you save for it!", pagecontent.get(15));
		// Assert.assertEquals(".activity.DashBoardActivity", findCurrentActivity());

		selectgoal.goalChoice(goal, key);
	}

	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}