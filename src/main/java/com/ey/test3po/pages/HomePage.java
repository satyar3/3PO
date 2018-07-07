package com.ey.test3po.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ey.test3po.testbase.TestBase;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class HomePage extends TestBase {

	public static boolean homePageActivity() throws InterruptedException 
	{
		
		boolean flag = true;
		//int start_time = LocalTime.now().getMinute();
		long start_time = System.currentTimeMillis();
		String currentactivity;
		//System.out.println(start_time);
		
		do
		{
			//int time_now = LocalTime.now().getMinute();
			long now = System.currentTimeMillis();
			//System.out.println(time_now);
			if((now - start_time)>60000)
			{
				//duration = time_now - start_time;
				System.out.println("Application has taken more than 1 minute !! Terminating the test !!");
				flag = false;
				break;
			}
			
			currentactivity = ((AndroidDriver<MobileElement>) driver).currentActivity();			
			
		}while(!(currentactivity.trim().equals(prop.getProperty("welcomePageActivity"))));		
		
		return flag;
	}
	
	public ArrayList<Object> welcomeScreen()
	{
		ArrayList<Object> welcomescreen = new ArrayList<Object>();
		
		welcomescreen.add(driver.findElement(By.xpath(prop.getProperty("welcomeScreenHeaderImage"))).getAttribute("enabled"));
		welcomescreen.add(driver.findElement(By.xpath(prop.getProperty("welcomeHeaderText"))).getAttribute("text"));
		welcomescreen.add(driver.findElement(By.xpath(prop.getProperty("subHeaderText"))).getAttribute("text"));
	
		return welcomescreen;
	}
	
	public ArrayList<Object> quickStart()
	{
		ArrayList<Object> quickstart = new ArrayList<Object>();
		
		quickstart.add(driver.findElement(By.xpath(prop.getProperty("quickStartImage"))).getAttribute("enabled"));
		quickstart.add(driver.findElement(By.xpath(prop.getProperty("quickStartInfo"))).getAttribute("text"));
		quickstart.add(driver.findElement(By.xpath(prop.getProperty("quickStartDetailedInfo"))).getAttribute("text"));
		
		return quickstart;
	}
	
	public ArrayList<Object> easyToUnderstand()
	{
		ArrayList<Object> easytounderstand = new ArrayList<Object>();
		
		easytounderstand.add(driver.findElement(By.xpath(prop.getProperty("easyToUnderstandImage"))).getAttribute("enabled"));
		easytounderstand.add(driver.findElement(By.xpath(prop.getProperty("easyToUnderstandText"))).getAttribute("text"));
		easytounderstand.add(driver.findElement(By.xpath(prop.getProperty("easyToUnderstandDetailedInfo"))).getAttribute("text"));
		
		return easytounderstand;
	} 
	
	public ArrayList<Object> flexible()
	{
		ArrayList<Object> flexible = new ArrayList<Object>();
		
		flexible.add(driver.findElement(By.xpath(prop.getProperty("flexibleImage"))).getAttribute("enabled"));
		flexible.add(driver.findElement(By.xpath(prop.getProperty("flexibleInfo"))).getAttribute("text"));
		flexible.add(driver.findElement(By.xpath(prop.getProperty("flexibleDetailedInfo"))).getAttribute("text"));
		
		return flexible;
	} 
	
	public ArrayList<Object> deliverResults()
	{
		ArrayList<Object> deliverresults = new ArrayList<Object>();
		
		deliverresults.add(driver.findElement(By.xpath(prop.getProperty("deliverResultsImage"))).getAttribute("enabled"));
		deliverresults.add(driver.findElement(By.xpath(prop.getProperty("deliverResultText"))).getAttribute("text"));
		deliverresults.add(driver.findElement(By.xpath(prop.getProperty("deliverResultDetailedText"))).getAttribute("text"));
		
		return deliverresults;
	}
	
	public ArrayList<String> fetchTestimonials() throws InterruptedException
	{
		List<WebElement> elements = driver.findElements(By.xpath(prop.getProperty("linerLayoutList")));
		ArrayList<String> testimonialarray = new ArrayList<String>();
		
		//Storing data after right arrow click
		for(int i = 0; i<elements.size(); i++)
		{
			testimonialarray.add((driver.findElement(By.id(prop.getProperty("testimonialDescription"))).getAttribute("text")));
			driver.findElement(By.id(prop.getProperty("rightArrow"))).click();
		}
		
		//Storing data after left arrow click
		for(int i = 0; i<elements.size(); i++)
		{
			testimonialarray.add((driver.findElement(By.id(prop.getProperty("testimonialDescription"))).getAttribute("text")));
			driver.findElement(By.id(prop.getProperty("leftArrow"))).click();
		}
		return testimonialarray;
	}
	
	public ArrayList<String> isButtonsPresent()
	{
		ArrayList<String> buttons = new ArrayList<String>();
		
		buttons.add(driver.findElement(By.xpath(prop.getProperty("getStartedButton"))).getAttribute("text"));
		buttons.add(driver.findElement(By.xpath(prop.getProperty("signInButton"))).getAttribute("text"));
		
		return buttons;
	}
	
	public void getStartedButtonClcik()
	{
		driver.findElement(By.xpath(prop.getProperty("getStartedButton"))).click();
	}
	
	public void signInButtonClcik()
	{
		//to bring the page to home page after clicking on Get Started button
		driver.navigate().back();
		driver.findElement(By.xpath(prop.getProperty("signInButton"))).click();
	}
	

}
