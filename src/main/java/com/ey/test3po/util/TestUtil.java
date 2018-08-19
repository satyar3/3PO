package com.ey.test3po.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;

import com.ey.test3po.testbase.TestBase;

public class TestUtil extends TestBase {

	static String TESTDATA_SHEET_PATH = "src\\main\\java\\com\\ey\\test3po\\testdata\\TestData.xlsx";
	static Workbook book;
	static Sheet sheet;

	public static Object[][] getTestData(String sheetName) {

		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);

		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {

				try {
					data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				} catch (Exception n) {
					data[i][k] = "";
				}
			}
		}

		return data;
	}

	public static void captureScreenshot(String testFunctionName) {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		String imagePath = currentDir + "/screenshots/" + testFunctionName + "_" + sdf.format(timestamp) + ".png";
		File fname = new File(imagePath);
		try {
			FileUtils.copyFile(scrFile, fname);
		} catch (IOException e) {

			e.printStackTrace();
		}
		String filePath = fname.toString();
		String path = "<img src=\"file://" + filePath + "\" alt=\"\"/>";
		Reporter.log(path);

	}

	public static Object convNum(String num) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		if (num.length() < 10 && !num.contains(",")) {
			Object newnum = numberFormat.format(Integer.parseInt(String.valueOf(num)));
			return newnum;
		} else if (num.length() >= 10 && !num.contains(",")) {
			Object newnum = numberFormat.format(Double.parseDouble(String.valueOf(num)));
			return newnum;
		} else {
			return num;
		}

	}
}