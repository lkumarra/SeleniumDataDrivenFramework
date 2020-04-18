package com.w2a.DataDrivenFramework.listners;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.DataDrivenFramework.base.TestBase;

public class CustomListners extends TestBase implements ITestListener {

	public void onFinish(ITestContext arg0) {

	}

	public void onStart(ITestContext arg0) {

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	public void onTestFailure(ITestResult arg0) {

		System.setProperty("org.uncommons.reportng.escape-output", "false");
		System.out.println(System.getProperty("user.dir"));
		try {
			File Src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(Src,
					new File(System.getProperty("user.dir") + "\\test-output\\html\\" + arg0.getName() + ".png"));
		} catch (IOException e) {

		}
		String screenShotLink = arg0.getName() + ".png";
		Reporter.log("<a target = '_blank' href =" + screenShotLink + ">Screenshot</a>");
		test.log(LogStatus.FAIL, arg0.getName().toUpperCase() + " FAILED " + arg0.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(screenShotLink));
		reports.endTest(test);
		reports.flush();
	}

	public void onTestSkipped(ITestResult arg0) {

	}

	public void onTestStart(ITestResult arg0) {
		
		test = reports.startTest(arg0.getName().toUpperCase());

	}

	public void onTestSuccess(ITestResult arg0) {

		test.log(LogStatus.PASS, arg0.getName().toUpperCase() + " PASS");
		reports.endTest(test);
		reports.flush();

	}

}
