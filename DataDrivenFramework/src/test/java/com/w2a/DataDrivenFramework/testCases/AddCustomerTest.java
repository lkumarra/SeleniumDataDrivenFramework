package com.w2a.DataDrivenFramework.testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.DataDrivenFramework.base.TestBase;

public class AddCustomerTest extends TestBase {
	public static Logger log = LogManager.getLogger(AddCustomerTest.class);
	@Test(dataProvider = "getData")
	public void addCustomer(String firstName, String lastName, String postCode, String alertText) {
		log.debug("TestCase Add New Customer Started");
		driver.findElement(By.cssSelector(OR.getProperty("addCusBtn"))).click();
		driver.findElement(By.cssSelector(OR.getProperty("firstName"))).sendKeys(firstName);
		driver.findElement(By.cssSelector(OR.getProperty("lastName"))).sendKeys(lastName);
		driver.findElement(By.cssSelector(OR.getProperty("postCode"))).sendKeys(postCode);
		driver.findElement(By.cssSelector(OR.getProperty("addCust"))).click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		String text = alert.getText();
		alert.accept();
		Assert.assertTrue(text.contains(alertText));
		log.debug("TestCase Add New Customer Executed");
	}

	@DataProvider
	public Object[][] getData() {

		String sheetName = "AddCustomerTest";
		int rows = excel.getRowCount(sheetName);
		System.out.println(rows);
		int cols = excel.getColumnCount(sheetName);
		System.out.println(cols);
		Object[][] data = new Object[rows - 1][cols];

		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			for (int colNum = 0; colNum < cols; colNum++) {
				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum).toString();
				System.out.println(data[rowNum - 2][colNum]);
			}
		}
		System.out.println(data);
		return data;

	}

}
