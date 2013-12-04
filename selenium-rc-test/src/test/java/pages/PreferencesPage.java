package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.junit.Assert.*;

public class PreferencesPage {
	static {
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	}
	public PreferencesPage(WebDriver driver) throws Exception {
		int intCnt = 0;
		do {
			try {
				assertEquals(driver.getTitle(), "EMTrack ~Preferences");
				break;
			} catch (AssertionError e) {
				intCnt++;
				Thread.sleep(1000);
			}
		} while (intCnt < 100);
	}

	public void clickUsersLink(WebDriver driver) {
		WebElement preferencesLink = driver.findElement(By.linkText("Users"));
		preferencesLink.isDisplayed();
		preferencesLink.click();
	}
}