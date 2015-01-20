package qaframework.lib.webElements;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ElementList extends ReadXML{

	public WebDriver driver;

	public ElementList(WebDriver _driver, File xml) {
		super(xml);
		this.driver = _driver;
	}

	public WebElements element(String elementName, String locatorStrategy)
			throws Exception {
		return new WebElements(this, this.getWebDriverBy(elementName,
				locatorStrategy));
	}
	
	/**
	 * Functions fetch element and returns WebdriverWait element
	 * @param elementName
	 * @param locatorStrategy
	 * @return
	 * @throws Exception
	 */
	public WebdriverWait element_wait(String elementName, String locatorStrategy)
			throws Exception {
		return new WebdriverWait(this, this.getWebDriverBy(elementName,
				locatorStrategy));
	}
	
	public WebElements dynamicElement(String elementName, String locator,String locatorStrategy)
			throws Exception {
		return new WebElements(this, this.getWebDriverBy(elementName, locator,
				locatorStrategy));
	}
        
	public enum type {
		xpath, css, id, name, tagName, linktext;
	}

	public By getWebDriverBy(String elementName, String locatorStrategy)
			throws Exception {
		By webdriverby = null;
		
		// Fetch locator value
		String xmlValue = this.getElementXML(elementName, locatorStrategy);

		switch (type.valueOf(locatorStrategy)) {
		case xpath:
			webdriverby = By.xpath(xmlValue);
			break;
		case css:
			webdriverby = By.cssSelector(xmlValue);
			break;
		case id:
			webdriverby = By.id(xmlValue);
			break;
		case name:
			webdriverby = By.name(xmlValue);
			break;
		case tagName:
			webdriverby = By.tagName(xmlValue);
			break;
		case linktext:
			webdriverby = By.linkText(xmlValue);
			break;
		}
		return webdriverby;
	}
	
	public By getWebDriverBy(String elementName, String locator,String locatorStrategy)
			throws Exception {
		
		By webdriverby = null;
	
		switch (type.valueOf(locatorStrategy)) {
		case xpath:
			webdriverby = By.xpath(locator);
			break;
		case css:
			webdriverby = By.cssSelector(locator);
			break;
		case id:
			webdriverby = By.id(locator);
			break;
		case name:
			webdriverby = By.name(locator);
			break;
		case tagName:
			webdriverby = By.tagName(locator);
			break;
		case linktext:
			webdriverby = By.linkText(locator);
			break;
		}
		return webdriverby;
	}
}