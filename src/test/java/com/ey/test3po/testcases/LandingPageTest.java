package com.ey.test3po.testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ey.test3po.pages.LandingPage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.ExtentReporterListener;

@Listeners(ExtentReporterListener.class)
public class LandingPageTest extends TestBase {

	LandingPage landingpage;

	public LandingPageTest() throws IOException {
		super();

	}

	@BeforeTest
	public void setUp() throws IOException {
		initialization();
		landingpage = new LandingPage();
	}

	@Test(priority = 1)
	public void landingPageLogoTest() 
	{
		boolean flag = landingpage.appLogo();
		
		Assert.assertTrue(flag);
	}

	@AfterTest
	public void tearDown() throws InterruptedException
	{
		driver.quit();
	}

}
