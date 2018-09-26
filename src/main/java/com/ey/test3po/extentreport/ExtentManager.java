package com.ey.test3po.extentreport;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

	private static ExtentReports extent;

	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			// Set HTML reporting file location
			String workingDir = System.getProperty("user.dir");
			extent = new ExtentReports(workingDir + "\\ExtentReports\\"+"ExtentReportResults"+"_"+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()))+".html", true);
		}
		return extent;
	}
}