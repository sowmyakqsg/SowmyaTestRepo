package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.junit.Assert.*;

public class DashboardPage {
	static {
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	}
	public DashboardPage(WebDriver driver) throws Exception {
		int intCnt = 0;
		do {
			try {
				assertEquals(driver.getTitle(), "EMTrack ~ Dashboard");
				break;
			} catch (AssertionError e) {
				intCnt++;
				Thread.sleep(1000);
			}
		} while (intCnt < 100);
	}

	public void clickPreferenceLink(WebDriver driver) {
		WebElement preferencesLink = driver.findElement(By.linkText("Preferences"));
		preferencesLink.isDisplayed();
		preferencesLink.click();
	}
}