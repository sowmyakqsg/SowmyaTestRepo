package qaframework.lib.webElements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import qaframework.WaitTimeConstants;

public class WebElements {

	public By webDrivryBy;
	WebDriver driver;

	public WebElements(ElementList elementList, By by) {
		this.driver = elementList.driver;
		this.webDrivryBy = by;
	}

	/**
	 * Verifies whether element present or not
	 * @param elementName
	 * @param locatorStrategy
	 * @returns true for at least one match or false if no matches found.
	 * @throws Exception
	 */
	public WebElement getOne() throws Exception {
		WebElement element = this.driver.findElement(this.webDrivryBy);
		return element;
	}
	
	/**
	 * 
	 * @return the element which is visible on screen
	 * @throws Exception
	 */
	public WebElement getDisplayedOne() throws Exception {
		WebElement element = this.driver.findElement(this.webDrivryBy);
		WebDriverWait wait = new WebDriverWait(this.driver, WaitTimeConstants.WAIT_TIME_TOO_SMALL);
		wait.until(ExpectedConditions.visibilityOfElementLocated(this.webDrivryBy));
		return element;
	}
	
	public List<WebElement> get() throws Exception {
		return this.driver.findElements(webDrivryBy);
	}
	
	public List<WebElement> getDisplayedAll() throws Exception {
		this.driver.findElements(webDrivryBy);
		WebDriverWait wait = new WebDriverWait(this.driver, WaitTimeConstants.WAIT_TIME_SMALL);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(this.webDrivryBy));
		return this.driver.findElements(webDrivryBy);
	}
	
	/**
	 * Function finds the element and clear the input value and returns the
	 * WebElement
	 * 
	 * @param elementName
	 * @param locatorStrategy
	 * @return WebElements
	 * @throws Exception
	 */
	public List<WebElement> clearInputsValue() throws Exception {
		List<WebElement> element = this.get();
		for (WebElement _element : element) {
			_element.clear();
			if (!_element.getAttribute("value").equals("")) {
				throw new Exception("Input value is not cleared");
			}
		}
		return element;
	}
	
	/**
	 * Function finds the element and clear the input value and returns the
	 * WebElement
	 * 
	 * @param elementName
	 * @param locatorStrategy
	 * @return single WebElement
	 * @throws Exception
	 */
	public WebElement clearInputValue() throws Exception {
		WebElement element = this.getDisplayedOne();
		element.clear();
		if (!element.getAttribute("value").equals("")) {
			throw new Exception("Input value is not cleared");
		}
		return element;
	}
	
	/**
	 * Refreshes the page 
	 */
	public void pageRefresh() {
		this.driver.findElement(By.xpath("//body")).sendKeys(Keys.F5);
	}
	
	public void waitForPageToLoad() throws Exception {
		@SuppressWarnings("unused")
		boolean blnPageLoaded;
		do {
			blnPageLoaded = ((JavascriptExecutor) driver).executeScript(
					"return document.readyState").equals("complete");
		} while (blnPageLoaded = false);
	}

	/**
	 * Performs mouse over action
	 * @param elementName
	 * @param locatorStrategy
	 * @throws Exception
	 */
	public void mouseOver() throws Exception {
		WebElement element = this.getDisplayedOne();
		// Mouse over using selenium webdriver

		/*Actions action = new Actions(this.driver);
		action.moveToElement(element).build().perform();
		*/
		
		// Mouse over using java script directly
		String javaScript = "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
				+ "arguments[0].dispatchEvent(evObj);";
		((JavascriptExecutor) driver).executeScript(javaScript, element);

	}

	/**
	 * Function to convert element of type WebElement to Select
	 * 
	 * @param elementName
	 * @param locatorStrategy
	 * @return Select
	 * @throws Exception
	 */
	public Select webElementToSelect() throws Exception {
		WebElement element = this.getDisplayedOne();
		return new Select(element);
	}

	/**
	 * Double click action on web element
	 * @param elementName
	 * @param locatorStrategy
	 * @throws Exception
	 */
	public void doubleClick()throws Exception {
		Actions actions = new Actions(this.driver);
		actions.doubleClick(this.getDisplayedOne()).build().perform();
	}
	
	// Drag and Drop action
	public void dragAndDrop(WebElement target) throws Exception {
		WebElement source = this.getDisplayedOne();
		Actions action = new Actions(this.driver);
		action.dragAndDrop(source, target).build().perform();
	}

	/**
	 * Verifies whether element present or not
	 * @param elementName
	 * @param locatorStrategy
	 * @returns true for at least one match or false if no matches found.
	 * @throws Exception
	 */
	public boolean isElementPresent()throws Exception {
		boolean present = true;
		if(this.get().size() == 0){
			present = false;
		}
		return present;
	}
	
	public void scrollDownTillElement() throws Exception {
		WebElement element = this.getDisplayedOne();
		((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public int getXpathCount() throws Exception {
		return this.get().size();
	}
	
	public int getCssCount() throws Exception {
		return this.get().size();
	}
}