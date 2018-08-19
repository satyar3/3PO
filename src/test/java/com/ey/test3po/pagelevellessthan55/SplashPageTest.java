package com.ey.test3po.pagelevellessthan55;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ey.test3po.pages.SplashPage;
import com.ey.test3po.testbase.TestBase;
import com.ey.test3po.util.TestListener;

//@Listeners(TestListener.class)
public class SplashPageTest extends TestBase {

	SplashPage landingpage;

	public SplashPageTest() throws IOException {
		super();

	}

	@BeforeMethod
	public void setUp() throws IOException {
		initialization();
		landingpage = new SplashPage();
	}

	@Test(priority = 1)
	public void splashPageT() 
	{
		//ExtentTestManager.getTest().setDescription("Splash screen test");
		boolean flag = landingpage.appLogo();		
		Assert.assertTrue(flag);
	}

	@AfterMethod
	public void tearDown() throws InterruptedException
	{
		driver.quit();
	}

}
