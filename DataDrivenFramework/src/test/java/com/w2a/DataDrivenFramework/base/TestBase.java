package com.w2a.DataDrivenFramework.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.DataDrivenFramework.utilities.ExcelReader;
import com.w2a.DataDrivenFramework.utilities.ExtentManager;
import com.w2a.DataDrivenFramework.utilities.TestUtil;

public class TestBase {

	/**
	 * Webdriver Properties Logs ExtentReports DB Excel Mail
	 */
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream configFile;
	public static FileInputStream orFile;
	public static Logger log = LogManager.getLogger(TestBase.class);
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "/src/test/resources/Excel/TestData.xlsx");
	public static WebDriverWait wait;
	public ExtentReports reports = ExtentManager.getInstance();
	public static ExtentTest test;
	static WebElement dropdown;
	public static String browser;

	/**
	 * 
	 */
	@BeforeSuite
	public void setUp() {
		if (driver == null) {
			try {
				configFile = new FileInputStream(
						System.getProperty("user.dir") + "/src/test/resources/Properties/Config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				orFile = new FileInputStream(
						System.getProperty("user.dir") + "/src/test/resources/Properties/OR.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				config.load(configFile);
				log.debug("Config file loaded !!!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (System.getenv(browser) != null && !System.getenv(browser).isEmpty()) {
				browser = System.getenv(browser);
			} else {
				browser = config.getProperty("browser");
			}
			config.setProperty("browser", browser);
			try {
				OR.load(orFile);
				log.debug("OR file loaded !!!");
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (config.getProperty("browser").equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/test/resources/Executables/chromedriver.exe");
				System.setProperty("webdriver.chrome.silentOutput", "true");
				driver = new ChromeDriver();
				log.debug("Chrome launched !!!");
			} else if (config.getProperty("browser").equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/src/test/resources/Executables/geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (config.getProperty("browser").equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "/src/test/resources/Executables/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated to :" + config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);
		}

	}

	/**
	 * Perfrom click action
	 * 
	 * @param locator key of locator on which click action have to performed.
	 */
	public void click(String locator) {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO, "Clicking on :" + locator);
	}

	/**
	 * Enter the keys in the given locator.
	 * 
	 * @param locator Key of locator.
	 * @param value   Value tobe entered.
	 */
	public void type(String locator, String value) {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		}
		test.log(LogStatus.INFO, "Typing in :" + locator + " entered value as " + value);
	}

	/**
	 * Select the value from dropdown
	 * 
	 * @param locator Locator from which value to be selected.
	 * @param value   Value to be selected.
	 */
	public void select(String locator, String value) {
		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		}
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
	}

	/**
	 * Switch to alert and get text and accept.
	 * 
	 * @return Text of alert.
	 */
	public String getAlertTextAndAccept() {
		Alert alert = driver.switchTo().alert();
		String text = alert.getText();
		alert.accept();
		return text;
	}

	/**
	 * Verify webelement is present on page.
	 * 
	 * @param element Weblement to be checked
	 * @return Return true if webelement is present on page otherwise retun false.
	 */
	public boolean isElementPresent(By by) {

		try {
			driver.findElement(by);

			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public static void verifyEquals(String expected, String actual, Method m) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (Throwable t) {
			String screenshotLink = TestUtil.captureScreenShot(m.getName());
			// ReportNg
			Reporter.log("<br>" + "Verification failure: " + t.getMessage() + "<br>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Reports
			test.log(LogStatus.FAIL, m.getName().toUpperCase() + " FAILED " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(screenshotLink));

		}
	}

	/**
	 * 
	 */
	@AfterSuite
	public void testDown() {

		if (!(driver == null)) {
			driver.quit();
		}

		log.debug("Test Execution completed");

	}

}
