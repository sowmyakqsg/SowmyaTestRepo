package qaframework;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebElements extends ReadXML {

	WebDriver driver;

	public WebElements(WebDriver _driver, File xml) {
		super(xml);
		this.driver = _driver;
	}

	public WebElement getElement(String elementName, String locatorStrategy)
			throws Exception {
		WebElement element = null;
	
		//Fetch locator value
		String xmlValue = this.getElementXML(elementName, locatorStrategy);
		
		switch (locatorStrategy) {
		case "xpath":
			element = this.driver.findElement(By.xpath(xmlValue));
			break;
		case "css":
			element = this.driver.findElement(By.cssSelector(xmlValue));
			break;
		case "id":
			element = this.driver.findElement(By.id(xmlValue));
			break;
		case "name":
			element = this.driver.findElement(By.name(xmlValue));
			break;
		case "tagName":
			element = this.driver.findElement(By.tagName(xmlValue));
			break;
		case "linktext":
			element = this.driver.findElement(By.linkText(xmlValue));
			break;
		}
		return element;
	}
}