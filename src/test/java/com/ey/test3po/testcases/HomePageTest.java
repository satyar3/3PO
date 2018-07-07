package com.ey.test3po.testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ey.test3po.pages.HomePage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.ExtentReporterListener;


@Listeners(ExtentReporterListener.class)
public class HomePageTest extends TestBase {

	HomePage homepage;

	public HomePageTest() throws IOException {
		super();
	}

	@BeforeTest
	public void setUp() throws IOException {
		initialization();
		homepage = new HomePage();
		//System.out.println(driver.getTitle());
	
	}

	@Test(priority = 1)
	public void isOnHomePage() throws InterruptedException  {
		// System.out.println("In testAppLauch()");
		boolean flag = homepage.homePageActivity();		
		Assert.assertTrue(flag);
	}
	
	/*@Test(priority = 2, dependsOnMethods = "isOnHomePage", description="this test is to validate")
	public void welcomeSectionTest() throws IOException
	{
		ArrayList<Object> welcomescreen = homepage.welcomeScreen();
		
		Assert.assertEquals("true", welcomescreen.get(0));
		Assert.assertEquals("Welcome", welcomescreen.get(1));
		Assert.assertEquals("We're here to help you achieve your goals", welcomescreen.get(2));
	}
	
	
	@Test(priority = 3, dependsOnMethods = "isOnHomePage")
	public void quickStartTest() throws IOException
	{
		ArrayList<Object> quickstartelements = homepage.quickStart();
		
		Assert.assertEquals("true", quickstartelements.get(0));
		Assert.assertEquals("Quick Start", quickstartelements.get(1));
		Assert.assertEquals("We won't waste your time.Get investment advice in 3 clicks.", quickstartelements.get(2));
	}
	
	@Test(priority = 4, dependsOnMethods = "isOnHomePage")
	public void easyToUnderstandTest() throws IOException
	{
		ArrayList<Object> easytounderstandTest = homepage.easyToUnderstand();
		
		Assert.assertEquals("true", easytounderstandTest.get(0));
		Assert.assertEquals("Easy to Understand", easytounderstandTest.get(1));
		Assert.assertEquals("We speak your language. No confusing financial jargon.", easytounderstandTest.get(2));
	}
	
	@Test(priority = 5, dependsOnMethods = "isOnHomePage")
	public void flexibleTest() throws IOException
	{
		ArrayList<Object> flexible = homepage.flexible();
		
		Assert.assertEquals("true", flexible.get(0));
		Assert.assertEquals("Flexible", flexible.get(1));
		Assert.assertEquals("We allow you to review your goals, anytime, anywhere.", flexible.get(2));
	}
	
	@Test(priority = 6, dependsOnMethods = "isOnHomePage")
	public void deliverResultsTest() throws IOException
	{
		ArrayList<Object> deliverresults = homepage.deliverResults();
		
		Assert.assertEquals("true", deliverresults.get(0));
		Assert.assertEquals("Deliver Results", deliverresults.get(1));
		Assert.assertEquals("We know the value of a plan. It's never too late to get started.", deliverresults.get(2));
	}

	@Test(priority = 7, dependsOnMethods = "isOnHomePage")
	public void testimonialArrowTest() throws InterruptedException  
	{
		ArrayList<String> testimonials = homepage.fetchTestimonials();
		
		
		//Left Click
		Assert.assertEquals("\"Great product! Made my life easy, and helped me feel better about my retirement plans.\"", testimonials.get(0));
		Assert.assertEquals("\"This app helped me over the first hurdle of actually setting up goals for my life. I greatly recommend it to every one.\"", testimonials.get(1));
		Assert.assertEquals("\"Good App! I use it all the Time for my investment solutions\"", testimonials.get(2));
		Assert.assertEquals("\"Fantastic App. I greatly recommend it to every one.\"", testimonials.get(3));
		Assert.assertEquals("\"Great App! User friendly.\"", testimonials.get(4));
		Assert.assertEquals("\"Great App! It's very user friendly. It helped me  plan my retirement goals.\"", testimonials.get(5));
		Assert.assertEquals("\"I like the app! Made my life easy, and helped me feel better about my retirement plans. Cheers!\"", testimonials.get(6));
		Assert.assertEquals("\"This app helped me over the first hurdle of actually setting up goals for my life. I like the app.\"", testimonials.get(7));
		Assert.assertEquals("\"Nice App! It helped me feel better about my retirement plans. I use it very often.\"", testimonials.get(8));
		Assert.assertEquals("\"This app helped me over the first hurdle of actually setting up goals for my life. I greatly recommend it to every one.\"", testimonials.get(9));

		
		//Right Click
		Assert.assertEquals("\"Great product! Made my life easy, and helped me feel better about my retirement plans.\"", testimonials.get(19));
		Assert.assertEquals("\"This app helped me over the first hurdle of actually setting up goals for my life. I greatly recommend it to every one.\"", testimonials.get(18));
		Assert.assertEquals("\"Good App! I use it all the Time for my investment solutions\"", testimonials.get(17));
		Assert.assertEquals("\"Fantastic App. I greatly recommend it to every one.\"", testimonials.get(16));
		Assert.assertEquals("\"Great App! User friendly.\"", testimonials.get(15));
		Assert.assertEquals("\"Great App! It's very user friendly. It helped me  plan my retirement goals.\"", testimonials.get(14));
		Assert.assertEquals("\"I like the app! Made my life easy, and helped me feel better about my retirement plans. Cheers!\"", testimonials.get(13));
		Assert.assertEquals("\"This app helped me over the first hurdle of actually setting up goals for my life. I like the app.\"", testimonials.get(12));
		Assert.assertEquals("\"Nice App! It helped me feel better about my retirement plans. I use it very often.\"", testimonials.get(11));
		Assert.assertEquals("\"This app helped me over the first hurdle of actually setting up goals for my life. I greatly recommend it to every one.\"", testimonials.get(10));

	}
	
	@Test(priority = 8, dependsOnMethods = "isOnHomePage")
	public void isButtonsPresentTest()  {
				
		ArrayList<String> buttons = homepage.isButtonsPresent();
		Assert.assertEquals("Get Started", buttons.get(0));
		Assert.assertEquals("Sign In", buttons.get(1));
	}
	
	@Test(priority = 9, dependsOnMethods = "isOnHomePage")
	public void getStartedButtonClcikTest()  {
		homepage.getStartedButtonClcik();		
		Assert.assertEquals(".activity.UserInfoGpsActivity", findCurrentActivity());
	}
	
	@Test(priority = 10, dependsOnMethods = "isOnHomePage")
	public void signInButtonClcikTest()  {
		homepage.signInButtonClcik();		
		Assert.assertEquals("", findCurrentActivity());
	}*/
	@Test(priority = 11, dependsOnMethods = "isOnHomePage")
	public void connectivityTest()
	{
		Map params = new HashMap<>(); // Set the "wifi" value to turn off the Wifi
		params.put("wifi", "disabled");
		
		//driver.setConnection(Connection.WIFI);
		//assertEquals(Connection.WIFI, driver.getConnection());
	}

	@AfterTest
	public void tearDown() throws InterruptedException {
		driver.quit();
	}

}
