package com.ey.test3po.reguser;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ey.test3po.pages.LandingPage;
import com.ey.test3po.pages.RegUserSignIn;
import com.ey.test3po.testbase.TestBase;

public class LoginPageTest extends TestBase
{
	RegUserSignIn reguser;
	LandingPage landingpage;
	
	public LoginPageTest()
	{
		super();
	}
	
	@BeforeMethod
	public void setUp() throws MalformedURLException
	{
		initialization();
		reguser = new RegUserSignIn();
		landingpage = new LandingPage();
		
		reguser.signIn(prop.getProperty("email"), prop.getProperty("password"));
	}
	
	@Test
	public void verifyLoginAndLandingPage()
	{
		Assert.assertEquals(landingpage.validateUser(prop.getProperty("name")), "Satya", "Username mismatch in landing page");
		
		ArrayList<Object> menuelems = landingpage.presenceOfMenuElements();
		
		Assert.assertEquals(menuelems.get(0), true, "Hamberger menu is missing");
		Assert.assertEquals(menuelems.get(1), true, "Home menu is missing");
		Assert.assertEquals(menuelems.get(2), true, "Add goal menu is missing");
		Assert.assertEquals(menuelems.get(3), true, "Trade off menu is missing");
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}
