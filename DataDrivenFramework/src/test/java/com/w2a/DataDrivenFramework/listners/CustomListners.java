package com.w2a.DataDrivenFramework.listners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.DataDrivenFramework.base.TestBase;
import com.w2a.DataDrivenFramework.utilities.TestUtil;

public class CustomListners extends TestBase implements ITestListener {

	public void onFinish(ITestContext arg0) {

	}

	public void onStart(ITestContext arg0) {

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	public void onTestFailure(ITestResult arg0) {
		String screenShotLink = TestUtil.captureScreenShot(arg0.getName());
		test.log(LogStatus.FAIL, arg0.getName().toUpperCase() + " FAILED " + arg0.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(screenShotLink));
		reports.endTest(test);
		reports.flush();
	}

	public void onTestSkipped(ITestResult arg0) {

		test.log(LogStatus.SKIP, "Test Case " + arg0.getName().toUpperCase() + " is skipped");
		reports.endTest(test);
		reports.flush();

	}

	public void onTestStart(ITestResult arg0) {

		test = reports.startTest(arg0.getName().toUpperCase());
		// Runnable
		if (!TestUtil.isRunnable(arg0.getName(), excel)) {
			throw new SkipException("Skipping the test " + arg0.getName().toUpperCase() + " as the run mode is NO");
		}

	}

	public void onTestSuccess(ITestResult arg0) {

		test.log(LogStatus.PASS, arg0.getName().toUpperCase() + " PASS");
		reports.endTest(test);
		reports.flush();

	}

}
