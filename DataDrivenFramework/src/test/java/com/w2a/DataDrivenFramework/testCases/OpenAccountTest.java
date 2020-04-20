package com.w2a.DataDrivenFramework.testCases;

import java.util.Hashtable;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.DataDrivenFramework.base.TestBase;
import com.w2a.DataDrivenFramework.utilities.TestUtil;

public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void openAccountTest(Hashtable<String, String> data) throws InterruptedException {
		log.debug("Test case: Open Account Test is started");
		click("openABtn_CSS");
		select("cusSele_CSS", data.get("customer"));
		click("curSele_Css");
		if (data.get("currency").equals("Rupee")) {
			click("curOptRu_CSS");
		} else if (data.get("currency").equals("Dollar")) {
			click("curOptDo_CSS");
		} else if (data.get("currency").equals("Pound")) {
			click("curOptPo_CSS");
		}
		click("proBtn_CSS");
		wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(getAlertTextAndAccept().contains(data.get("alertText")));
		log.debug("Test case: Open account Test is executed");
	}

}
