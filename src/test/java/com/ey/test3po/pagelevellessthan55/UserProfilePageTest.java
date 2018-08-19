package com.ey.test3po.pagelevellessthan55;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ey.test3po.pages.UserProfilePage;
import com.ey.test3po.pages.WelcomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestUtil;

public class UserProfilePageTest extends TestBase{
	
	UserProfilePageTest()
	{
		super();
	}
	
	WelcomePage homepage;
	UserProfilePage userProfile;
	String sheetName = "UserProfile";
		
	@BeforeMethod
	public void setUp() throws IOException {
		initialization();
		homepage = new WelcomePage();
		userProfile = new UserProfilePage();
		homepage.getStartedButtonClcik();
	}
	
	@DataProvider
	public Object[][] getQuestionnaireTestData() {
		Object ob[][] = TestUtil.getTestData(sheetName);
		return ob;
	}	
	@Test(priority = 1, dataProvider = "getQuestionnaireTestData")
	public void setUserprofile(String annualincome, String zip, String age, String key)
	{
		ArrayList<Object> screencontent = userProfile.getPageContent();
		
		Assert.assertEquals("Let's get to know you!", screencontent.get(0));
		Assert.assertEquals("Tell us a bit about yourself.", screencontent.get(1));
		Assert.assertEquals(true, screencontent.get(2));
		Assert.assertEquals(true, screencontent.get(3));
		Assert.assertEquals(true, screencontent.get(4));
		Assert.assertEquals(true, screencontent.get(5));		
		Assert.assertEquals("Annual Income ($)", screencontent.get(6));
		Assert.assertEquals("Location(Zip)", screencontent.get(7));
		Assert.assertEquals("Age", screencontent.get(8));		
		
		userProfile.userProfileQuestionnaire(annualincome, zip, age, key);
		//Assert.assertEquals(".activity.DashBoardActivity", findCurrentActivity());
	}
	
	@AfterMethod
	public void tearDwon()
	{
		driver.quit();
	}

}
