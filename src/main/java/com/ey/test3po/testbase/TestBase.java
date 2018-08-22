package com.ey.test3po.testbase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class TestBase
{

	@SuppressWarnings("rawtypes")
	protected static AndroidDriver driver;
	// protected static WebDriver driver;
	protected static Properties prop;
	protected static FileInputStream fs;
	protected static DesiredCapabilities capabilities;
	protected static int duration;
	protected static String testcasenum;

	// AppiumDriverLocalService service;
	// AppiumServiceBuilder builder;

	Logger log = Logger.getLogger(TestBase.class);

	public TestBase()
	{
		try
		{
			fs = new FileInputStream("src\\main\\java\\com\\ey\\test3po\\config\\config.properties");
			prop = new Properties();
			prop.load(fs);

		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void initialization() throws MalformedURLException
	{

		capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, prop.getProperty("platformName"));
		capabilities.setCapability(CapabilityType.VERSION, prop.getProperty("PlatformVersion"));
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, prop.getProperty("DeviceName"));
		capabilities.setCapability("appPackage", prop.getProperty("appPackage"));
		capabilities.setCapability("appActivity", prop.getProperty("appActivity"));
		capabilities.setCapability("unicodeKeyboard", "true");
		capabilities.setCapability("resetKeyboard", "true");

		/*
		 * builder = new AppiumServiceBuilder(); builder.withIPAddress("0.0.0.0");
		 * builder.usingPort(4723); builder.withCapabilities(capabilities);
		 * builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		 * builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
		 * 
		 * //Start the server with the builder service =
		 * AppiumDriverLocalService.buildService(builder); service.start();
		 */

		driver = new AndroidDriver(new URL(prop.getProperty("url")), capabilities);

		driver.manage().timeouts().implicitlyWait(Integer.parseInt(prop.getProperty("implicitlyWait")), TimeUnit.SECONDS);
	}

	public String findCurrentActivity()
	{
		@SuppressWarnings("unchecked")
		String currentactivity = ((AndroidDriver<MobileElement>) driver).currentActivity();

		return currentactivity;
	}

	public void explicitWait(WebElement element)
	{
		new WebDriverWait(driver, 3).ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOf(element));
	}
}
