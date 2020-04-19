package com.w2a.DataDrivenFramework.testCases;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.DataDrivenFramework.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	public static Logger log = LogManager.getLogger(BankManagerLoginTest.class);

	@Test
	public void bankManagerLoginTest(Method m) {
		log.debug("Inside Login Test");
		click("bmlBtn_CSS");
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCusBtn_CSS"))));
		log.debug("Login successfully executed");
	}

}
