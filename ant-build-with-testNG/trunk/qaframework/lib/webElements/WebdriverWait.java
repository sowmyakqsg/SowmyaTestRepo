package qaframework.lib.webElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import qaframework.WaitTimeConstants;

public class WebdriverWait {
	
	public By webDriverBy;
	WebDriver driver;

	public WebdriverWait(ElementList elementList, By by) {
		this.driver = elementList.driver;
		this.webDriverBy = by;
	}
	
	public boolean waitForInvisibilityOfElement(){
		WebDriverWait wait = new WebDriverWait(this.driver, WaitTimeConstants.WAIT_TIME_SMALL);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(this.webDriverBy));
		return false;
	}
}
