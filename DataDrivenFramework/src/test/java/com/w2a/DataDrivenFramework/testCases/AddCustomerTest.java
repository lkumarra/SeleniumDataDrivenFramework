package com.w2a.DataDrivenFramework.testCases;

import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.DataDrivenFramework.base.TestBase;
import com.w2a.DataDrivenFramework.utilities.TestUtil;

public class AddCustomerTest extends TestBase {
	public static Logger log = LogManager.getLogger(AddCustomerTest.class);

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void addCustomerTest(Hashtable<String, String> data) {

		if (!data.get("runmode").equals("Y")) {
			test.log(LogStatus.INFO, "Skipping the test case as run mode for set data is NO");
			throw new SkipException("Skipping the test case as run mode for set data is NO");
		}
		log.debug("TestCase Add New Customer Started");
		click("addCusBtn_CSS");
		type("firstName_CSS", data.get("firstname"));
		type("lastName_XPATH", data.get("lastname"));
		type("postCode_CSS", data.get("postcode"));
		click("addCust_CSS");
		wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(getAlertTextAndAccept().contains(data.get("alertText")));
		log.debug("TestCase Add New Customer Executed");
	}

}
