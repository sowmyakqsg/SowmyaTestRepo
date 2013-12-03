package functionality;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class HeadlessDemo {

	@Test
	public void test() {
		WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_17);
		driver.get("http://iaautoqa63.dyndns.org/exemplar/exemplar/Portal.action");
		driver.manage().window().maximize();
		assertEquals(driver.getTitle(),"Connecture Sales Demo1");
		System.out.println("Script passed");
	}
}
