package com.ey.test3po.util;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.ey.test3po.extentreport.ExtentManager;
import com.ey.test3po.extentreport.ExtentTestManager;
import com.ey.test3po.testbase.TestBase;
import com.relevantcodes.extentreports.LogStatus;

public class TestListener extends TestBase implements ITestListener
{

	private static String getTestMethodName(ITestResult iTestResult)
	{
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	public void onStart(ITestContext iTestContext)
	{
		System.out.println("Scenario Started : " + iTestContext.getName());
		iTestContext.setAttribute("WebDriver", TestBase.driver);
	}

	public void onFinish(ITestContext iTestContext)
	{
		System.out.println("Scenario Finished : " + iTestContext.getName());
		ExtentTestManager.endTest();
		ExtentManager.getReporter().flush();
	}

	public void onTestStart(ITestResult iTestResult)
	{
		System.out.println(getTestMethodName(iTestResult) + " test started");
		ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(), "");
	}

	public void onTestSuccess(ITestResult iTestResult)
	{
		System.out.println(getTestMethodName(iTestResult) + " test succeed");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
	}

	public void onTestFailure(ITestResult iTestResult)
	{
		System.out.println(getTestMethodName(iTestResult) + " test failed");

		try
		{
			//To add the screenshot in extent report
			String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed", ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
			
			//To Add the error in extent report
			ExtentTestManager.getTest().log(LogStatus.FAIL, iTestResult.getThrowable());
			
			//To Store the Screenshot in local
			TestUtil.captureScreenshot(iTestResult.getMethod().getMethodName());
		}
		catch (NullPointerException e)
		{
			System.out.println("*********************************************" 
					+ "\nNo active screen present to capture !!" + "\n1- Verify the device connection" 
					+ "\n2- Verify the Appium Server status\n*********************************************");
		}
	}

	public void onTestSkipped(ITestResult iTestResult)
	{
		System.out.println(getTestMethodName(iTestResult) + " test skipped");
		ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult)
	{
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}
}