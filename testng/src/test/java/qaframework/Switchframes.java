package qaframework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Switchframes {
	
	public WebDriver driver;
	WebElement element;
	
	public void switchToFrameById(String frameName, String locator,WebDriver driver)
			throws Exception {
			driver.switchTo().window("");
			driver.switchTo().frame(driver.findElement(By.id(frameName)));
	}
	
	public void switchToFrameByName(String frameName, String locator,WebDriver driver)throws Exception{
			driver.switchTo().window("");
			driver.switchTo().frame(driver.findElement(By.name(frameName)));
	}

	public void switchFrame(String frameName,WebDriver driver) throws Exception {
		driver.switchTo().frame(frameName);
	}
}