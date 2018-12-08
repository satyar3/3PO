package com.ey.test3po.testbase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class TestBase
{

	@SuppressWarnings("rawtypes")
	protected static AndroidDriver driver;
	// protected static WebDriver driver;
	protected static Properties prop;
	protected static Properties prop1;
	protected static Properties prop2;
	protected static FileInputStream fs;
	protected static FileInputStream fs1;
	protected static FileInputStream fs2;
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
			fs1 = new FileInputStream("src\\main\\java\\com\\ey\\test3po\\config\\config.properties");
			prop1 = new Properties();
			prop1.load(fs1);
			
			fs2 = new FileInputStream("src\\main\\java\\com\\ey\\test3po\\config\\OR.properties");
			prop2 = new Properties();
			prop2.load(fs2);
			
			prop = new Properties();
			prop.putAll(prop1);
			prop.putAll(prop2);

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void initialization() throws MalformedURLException
	{
		
		//service = AppiumDriverLocalService.buildDefaultService();
		//service.start();
		
		// try
		// {
		// AppiumServerStartStop.appiumStart();
		// }
		// catch (Exception e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		
		capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, prop.getProperty("platformName"));
		capabilities.setCapability(CapabilityType.VERSION, prop.getProperty("PlatformVersion"));
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, prop.getProperty("DeviceName"));
		
		//Pattern lock and pattern number in sequence
		//capabilities.setCapability("unlockType", "pattern");
		//capabilities.setCapability("unlockKey", "12487");		
				
		capabilities.setCapability("appPackage", prop.getProperty("appPackage"));
		capabilities.setCapability("appActivity", prop.getProperty("appActivity"));
		capabilities.setCapability("unicodeKeyboard", "true");
		capabilities.setCapability("resetKeyboard", "true");

		driver = new AndroidDriver(new URL(prop.getProperty("url")), capabilities);
		
		if(driver.isDeviceLocked())
		{
			driver.unlockDevice();
		}		
		
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(prop.getProperty("implicitlyWait")), TimeUnit.SECONDS);
	}

	public String findCurrentActivity()
	{
		@SuppressWarnings("unchecked")
		String currentactivity = ((AndroidDriver<MobileElement>) driver).currentActivity();

		return currentactivity;
	}

	/*public void explicitWait(WebElement element)
	{
		new WebDriverWait(driver, 3).ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOf(element));
	}*/	
	
}
