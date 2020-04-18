package com.w2a.DataDrivenFramework.testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.DataDrivenFramework.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	public static Logger log = LogManager.getLogger(BankManagerLoginTest.class);

	@Test
	public void loginAsBankManager() {
		
		log.debug("Inside Login Test");
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCusBtn"))));
		log.debug("Login successfully executed");
		
	}

}
