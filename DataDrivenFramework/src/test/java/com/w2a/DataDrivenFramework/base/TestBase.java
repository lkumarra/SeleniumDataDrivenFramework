package com.w2a.DataDrivenFramework.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.w2a.DataDrivenFramework.utilities.ExcelReader;

public class TestBase {

	/**
	 * Webdriver Properties Logs ExtentReports DB Excel Mail
	 */
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream configFile;
	public static FileInputStream orFile;
	public Logger log;
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"/src/test/resources/Excel/TestData.xlsx");

	@BeforeSuite
	public void setUp() {
		log = LogManager.getLogger(getClass().getName());
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
		}

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

	@AfterSuite
	public void testDown() {

		if (!(driver == null)) {
			driver.quit();
		}

		log.debug("Test Execution completed");

	}

}
