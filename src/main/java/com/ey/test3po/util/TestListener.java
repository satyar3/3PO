package com.ey.test3po.util;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.ey.test3po.extentreport.ExtentManager;
import com.ey.test3po.extentreport.ExtentTestManager;
import com.ey.test3po.testbase.TestBase;
import com.relevantcodes.extentreports.LogStatus;

public class TestListener extends TestBase implements ITestListener {

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	// Before starting all tests, below method runs.
	public void onStart(ITestContext iTestContext) {
		System.out.println("onStart method " + iTestContext.getName());
		iTestContext.setAttribute("WebDriver", TestBase.driver);
	}

	// After ending all tests, below method runs.
	public void onFinish(ITestContext iTestContext) {
		System.out.println("onFinish method " + iTestContext.getName());
		// Do tier down operations for extentreports reporting!
		ExtentTestManager.endTest();
		ExtentManager.getReporter().flush();
	}

	public void onTestStart(ITestResult iTestResult) {
		System.out.println("onTestStart method " + getTestMethodName(iTestResult) + " start");
		// Start operation for extentreports.
		ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(), "");
	}

	public void onTestSuccess(ITestResult iTestResult) {
		System.out.println("onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
		// Extentreports log operation for passed tests.
		ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
	}

	public void onTestFailure(ITestResult iTestResult) {
		System.out.println("onTestFailure method " + getTestMethodName(iTestResult) + " failed");

		// Get driver from TestBase and assign to local webdriver variable.
		// Object testClass = iTestResult.getInstance();
		WebDriver webDriver = TestBase.driver;

		// Take base64Screenshot screenshot.
		String base64Screenshot = "data:image/png;base64,"
				+ ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);

		// Extentreports log and screenshot operations for failed tests.
		ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed",
				ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));

		ExtentTestManager.getTest().log(LogStatus.FAIL, iTestResult.getThrowable());
		TestUtil.captureScreenshot(iTestResult.getMethod().getMethodName());
	}

	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
		// Extentreports log operation for skipped tests.
		ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}
}