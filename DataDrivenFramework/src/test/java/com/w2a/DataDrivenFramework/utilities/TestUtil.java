package com.w2a.DataDrivenFramework.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;

import com.w2a.DataDrivenFramework.base.TestBase;

public class TestUtil extends TestBase {

	/**
	 * 
	 * @param screenShotName
	 * @return
	 */
	public static String captureScreenShot(String screenShotName) {
		Date date = new Date();
		SimpleDateFormat dateFormate = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String formatedDate = dateFormate.format(date);
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		System.out.println(System.getProperty("user.dir"));
		try {
			File Src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(Src, new File(
					System.getProperty("user.dir") + "\\test-output\\html\\" + screenShotName + formatedDate + ".png"));
		} catch (IOException e) {

		}
		String screenShotLink = screenShotName + formatedDate + ".png";
		Reporter.log("<a target = '_blank' href =" + screenShotLink + ">Screenshot</a>");
		return screenShotLink;
	}

	/**
	 * Return the data in the form of object array.
	 * 
	 * @return Object array
	 */
	@DataProvider(name = "dp")
	public Object[][] getData(Method m) {
		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		Object[][] data = new Object[rows - 1][1];
		Hashtable<String, String> table = null;
		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			table = new Hashtable<String, String>();
			for (int colNum = 0; colNum < cols; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
			}
		}
		return data;
	}

	/**
	 * 
	 * @param testName
	 * @param excel
	 * @return
	 */
	public static boolean isRunnable(String testName, ExcelReader excel) {
		String sheetName = "test_suite";
		int rowNum = excel.getRowCount(sheetName);
		for (int row = 2; row <= rowNum; row++) {
			String data = excel.getCellData(sheetName, "TCID", row);
			if (data.equalsIgnoreCase(testName)) {
				String runmode = excel.getCellData(sheetName, "Runmode", row);
				if (runmode.equalsIgnoreCase("Y")) {
					return true;
				}else {
					return false;
				}
			}
		}
		return false;
	}
}
