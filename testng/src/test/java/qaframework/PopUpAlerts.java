package qaframework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PopUpAlerts {
	
	public WebDriver driver;
	
	public PopUpAlerts(WebDriver _driver) {
		this.driver = _driver;
	}
		
	public void acceptAlert(WebDriver driver) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 300);
		if (wait.until(ExpectedConditions.alertIsPresent()) == null) {
			System.out.println("alert was not present");
		} else {
			driver.switchTo().alert().accept();
		}
	}

	public void dismissAlert(WebDriver driver) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 300);
		if (wait.until(ExpectedConditions.alertIsPresent()) == null) {
			System.out.println("alert was not present");
		} else {
			driver.switchTo().alert().dismiss();
		}
	}
}