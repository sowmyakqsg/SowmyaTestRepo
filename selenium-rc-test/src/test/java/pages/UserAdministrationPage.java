package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.junit.Assert.*;

public class UserAdministrationPage {
	static {
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	}
	public UserAdministrationPage(WebDriver driver) throws Exception {
		int intCnt = 0;
		do {
			try {
				assertEquals(driver.getTitle(), "EMTrack ~Preferences - User Administration");
				break;
			} catch (AssertionError e) {
				intCnt++;
				Thread.sleep(1000);
			}
		} while (intCnt < 100);
	}

	public void clickNewUserLink(WebDriver driver) {
		WebElement NewUserLink = driver.findElement(By.linkText("New User"));
		//NewUserLink.isDisplayed();
		NewUserLink.click();
	}
}