package com.ey.test3po.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class GetData
{

	public static String fromExcelbyIndex(/* String filename, */ String sheetName, int rowIndex, int cellIndex)
	{
		String data = null;
		File f = new File("src\\main\\java\\com\\ey\\test3po\\testdata\\TestData.xlsx");
		try
		{
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet st = wb.getSheet(sheetName);
			Row r = st.getRow(rowIndex);
			Cell c = r.getCell(cellIndex);
			data = c.toString();
		}
		catch (Exception ref)
		{
			System.err.println("You have selected blank cell");
		}

		return data;
	}

}
