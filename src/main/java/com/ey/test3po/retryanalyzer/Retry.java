package com.ey.test3po.retryanalyzer;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.ey.test3po.extentreport.ExtentTestManager;
import com.ey.test3po.testbase.TestBase;
import com.relevantcodes.extentreports.LogStatus;

public class Retry extends TestBase implements IRetryAnalyzer
{

	private int count = 0;
	private static int maxTry = 0; // Run the failed test 2 times

	public boolean retry(ITestResult iTestResult)
	{
		if (!iTestResult.isSuccess())
		{
			if (count < maxTry)
			{
				count++;
				iTestResult.setStatus(ITestResult.FAILURE);
				extendReportsFailOperations(iTestResult);
				return true;
			}
		}
		else
		{
			iTestResult.setStatus(ITestResult.SUCCESS);
		}
		return false;
	}

	public void extendReportsFailOperations(ITestResult iTestResult)
	{
		@SuppressWarnings("unused")
		Object testClass = iTestResult.getInstance();

		String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed", ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
	}
}