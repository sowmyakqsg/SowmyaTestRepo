package functionality;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.thoughtworks.selenium.Selenium;

public class ComboboxExample {

	WebDriver driver;
	Selenium selenium;

	@Before
	public void setUp() {
		DesiredCapabilities ieCapabilities = DesiredCapabilities
				.internetExplorer();
		ieCapabilities
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
		driver = new InternetExplorerDriver(ieCapabilities);
	}

	@Test
	public void testCombo1() {
		driver.manage().window().maximize();
		String baseUrl = "https://emtrack-ng.qa.intermedix.com/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);

		// selenium.start();
		selenium.open("/app");
		selenium.type("//input[@id='name']", "autoregadmin2");
		selenium.type("//input[@id='password']", "abc123 ");
		selenium.click("//input[@id='loginBttn']");
		try {
			selenium.waitForPageToLoad("60000");
		} catch (Exception e) {

		}
		selenium.click("//a[@href='/PreferencesFunctionSummary.html']");
		try {
			selenium.waitForPageToLoad("60000");
		} catch (Exception e) {

		}
		selenium.click("//a[@id='PageLink_4']");
		try {
			selenium.waitForPageToLoad("60000");
		} catch (Exception e) {

		}
		selenium.click("//a[@id='DirectLink_0']");
		try {
			selenium.waitForPageToLoad("60000");
		} catch (Exception e) {

		}

		selenium.select("//select[@id='divisionSelection']", "ACC 1");
		selenium.close();
		selenium.stop();
	}

	@Test
	public void testAssert() {

		try {
			assertTrue(10 > 2);
			System.out.println("Assertion worked");
		} catch (AssertionError Ae) {
			System.out.println("Assertion not worked");
		}
	}
	
	
	@Test
	public void testDragAndDrop() {
		driver.manage().window().maximize();
		String baseUrl = "https://emtrack-ng.qa.intermedix.com/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);

		// selenium.start();
		selenium.open("/app");
		selenium.type("//input[@id='name']", "autoregadmin2");
		selenium.type("//input[@id='password']", "abc123 ");
		selenium.click("//input[@id='loginBttn']");
		try {
			selenium.waitForPageToLoad("60000");
		} catch (Exception e) {

		}
		
		selenium.dragAndDrop("//span[@id='clientlistportlet-1038_header_hd-textEl']", "527,16");
		selenium.close();
		selenium.stop();
	}
}
