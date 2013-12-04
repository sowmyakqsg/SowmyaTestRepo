package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.junit.Assert.*;

public class LoginPage {
	static {
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	}
	public LoginPage(WebDriver driver) throws Exception {
		driver.get("https://emtrack-ng.qa.intermedix.com/app");
		int intCnt = 0;
		do {
			try {
				assertEquals(driver.getTitle(), "EMTrack ~ Login");
				break;
			} catch (AssertionError e) {
				intCnt++;
				Thread.sleep(1000);
			}
		} while (intCnt < 100);
	}

	public void login(WebDriver driver,String strUsername, String strPassword) {
		WebElement username = driver.findElement(By.id("name"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement loginBttn = driver.findElement(By.id("loginBttn"));
		
		username.isDisplayed();
		password.isDisplayed();
		loginBttn.isDisplayed();
		
		username.sendKeys(strUsername);
		password.sendKeys(strPassword);
		
		assertEquals(username.getAttribute("value"),strUsername);
		assertEquals(password.getAttribute("value"),strPassword);
		loginBttn.click();
	}
}