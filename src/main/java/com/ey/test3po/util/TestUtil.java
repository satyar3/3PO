package com.ey.test3po.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.Reporter;

import com.ey.test3po.testbase.TestBase;

public class TestUtil extends TestBase
{

	static String TESTDATA_SHEET_PATH = "src\\main\\java\\com\\ey\\test3po\\testdata\\TestData.xlsx";
	static Workbook book;
	static Sheet sheet;
	
	

	public static Object[][] getTestData(String sheetName)
	{

		FileInputStream file = null;
		try
		{
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			book = WorkbookFactory.create(file);
		}
		catch (InvalidFormatException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);

		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		for (int i = 0; i < sheet.getLastRowNum(); i++)
		{
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++)
			{

				try
				{
					data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				}
				catch (Exception n)
				{
					data[i][k] = "";
				}
			}

		}

		int dataCol = data[0].length - 1;

		// System.out.println(data[0][oldCol]);

		int newDataRow = 0;
		for (int i = 0; i < data.length; i++)
		{
			if (data[i][dataCol].toString().equals("Y"))
			{
				newDataRow = newDataRow + 1;
			}
		}

		// System.out.println(newRow);

		Object newdata[][] = new Object[newDataRow][data[0].length];
		int row = 0;
		int col = 0;

		for (int i = 0; i < data.length; i++)
		{
			if (data[i][dataCol].toString().equals("Y"))
			{
				for (int j = 0; j < data[0].length; j++)
				{

					newdata[row][col] = data[i][j];
					//System.out.println(newdata[row][col]);
					col++;
				}
				col = 0;
				row++;
			}
		}

		return newdata;

	}

	public static void captureScreenshot(String testFunctionName)
	{
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MMM.dd.HH.mm.ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		String imagePath = currentDir + "/Failed_screenshots/" + testcasenum + "_" + testFunctionName + "_failed_" + sdf.format(timestamp) + ".png";
		File fname = new File(imagePath);
		try
		{
			FileUtils.copyFile(scrFile, fname);
		}
		catch (IOException e)
		{

			e.printStackTrace();
		}
		String filePath = fname.toString();
		String path = "<img src=\"file://" + filePath + "\" alt=\"\"/>";
		Reporter.log(path);

	}

	public static void captureScreenShotForEachStep(String callermethod, String currentmethod)
	{
		try
		{
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(System.getProperty("user.dir") + "/TC_screenshots/" + "/" + callermethod + "/"+"/" + LocalDate.now() + "/" + testcasenum + "_" + currentmethod + "_" + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis())) + ".png"));
		}
		catch (WebDriverException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public static Object convNum(String num)
	{
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		if (num.length() < 10 && !num.contains(","))
		{
			Object newnum = numberFormat.format(Integer.parseInt(String.valueOf(num)));
			return newnum;
		}
		else if (num.length() >= 10 && !num.contains(","))
		{
			Object newnum = numberFormat.format(Double.parseDouble(String.valueOf(num)));
			return newnum;
		}
		else if (num.equals(""))
		{
			Object newnum = "";
			return newnum;
		}
		else
		{
			Object newnum = num;
			return newnum;
		}
	}
}