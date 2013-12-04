package pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.gargoylesoftware.htmlunit.BrowserVersion;


@SuppressWarnings("unused")
public class BrowserPreparationPage {
	static {
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	}
	
	public WebDriver browserPreparationPage(WebDriver driver) {
		DesiredCapabilities ieCapabilities = DesiredCapabilities
				.internetExplorer();
		ieCapabilities
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);

		driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_9);
		((HtmlUnitDriver) driver).setJavascriptEnabled(true);
		//driver = new InternetExplorerDriver(ieCapabilities);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.MINUTES);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
		return driver;
	}
}