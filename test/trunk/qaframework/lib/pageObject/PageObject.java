package qaframework.lib.pageObject;

import java.io.File;

import org.openqa.selenium.WebDriver;

import qaframework.lib.webElements.ElementList;

public abstract class PageObject {
	
	protected WebDriver driver;
	public ElementList page;
	
	public PageObject(WebDriver _driver) throws Exception{
		this.driver = _driver;
		File xmlFile = this.getXMLFile();
		this.page = new ElementList(this.driver,xmlFile);
	}
	
	public String getTitle() throws Exception {
		return this.driver.getTitle();
	}
	
	abstract protected File getXMLFile() throws Exception;
}
