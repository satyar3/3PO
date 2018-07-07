package com.ey.test3po.testbase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class TestBase {
	
	protected static WebDriver driver;
	protected static Properties prop;
	protected static FileInputStream fs;
	protected static DesiredCapabilities capabilities;
	protected static int duration;
	
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
	
	public void initialization() throws MalformedURLException
	{
		capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, prop.getProperty("platformName"));
		capabilities.setCapability(CapabilityType.VERSION, prop.getProperty("PlatformVersion"));
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true); 
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, prop.getProperty("DeviceName"));		
		capabilities.setCapability("appPackage", prop.getProperty("appPackage"));
		capabilities.setCapability("appActivity", prop.getProperty("appActivity"));
		
		
		driver = new AndroidDriver(new URL(prop.getProperty("url")), capabilities);
		
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(prop.getProperty("implicitlyWait")), TimeUnit.SECONDS);
	}
	
	public static void waitToView()
	{
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String findCurrentActivity()
	{
		String currentactivity = ((AndroidDriver<MobileElement>) driver).currentActivity();
		
		return currentactivity;
	}
}
