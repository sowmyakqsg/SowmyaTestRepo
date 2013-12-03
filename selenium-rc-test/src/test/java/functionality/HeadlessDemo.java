package functionality;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;


public class HeadlessDemo {
	String applicationName = "Netscape";
    String applicationVersion = "5.0 (Windows)";
    String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";
    int browserVersionNumeric = 25;

    BrowserVersion browser = new BrowserVersion(applicationName, applicationVersion, userAgent, browserVersionNumeric) {
        public boolean hasFeature(BrowserVersionFeatures property) {

            // change features here
            return BrowserVersion.FIREFOX_10.hasFeature(property);
        }
    };
    
	@Test
	public void test() {
		//WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_17);
		WebDriver driver = new FirefoxDriver();
		driver.get("http://iaautoqa63.dyndns.org/exemplar/exemplar/Portal.action");
		((HtmlUnitDriver) driver).setJavascriptEnabled(true);
		driver.manage().window().maximize();
		assertEquals(driver.getTitle(),"Connecture Sales Demo");
		System.out.println("Script passed");
		System.out.println(((HtmlUnitDriver) driver).executeScript("return navigator.userAgent",""));
		driver.close();
	}
	
	@Test
	public void testDefaultBrowser() {
		/*BrowserVersion browser = new BrowserVersion(applicationName, applicationVersion, userAgent, browserVersionNumeric) {
	          public boolean hasFeature(BrowserVersionFeatures property) {

	              // change features here
	              return BrowserVersion.FIREFOX_10.hasFeature(property);
	          }
	      };
		BrowserVersion p = new BrowserVersion()*/
		WebDriver driver = new HtmlUnitDriver(browser);
		BrowserVersion.setDefault(browser);
		System.out.println(BrowserVersion.getDefault());
		
	}
}
