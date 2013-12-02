package functionality;

import org.junit.Test;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumFuncSpec {
	Selenium selenium;

	@Test
	public void test() {
		selenium = new DefaultSelenium("localhost",4444,"*firefox","http://iaautoqa63.dyndns.org/exemplar/exemplar/Portal.action");
		selenium.start();
		selenium.open("http://iaautoqa63.dyndns.org/exemplar/exemplar/Portal.action");
		selenium.windowMaximize();
		selenium.click("//a[@id='rLogin']");
		selenium.waitForPageToLoad("120000");
		System.out.println(selenium.getText("//div[6]/label"));
		//System.out.println(selenium.getText("//td[@align='left']//a[2]"));
		selenium.close();
		selenium.stop();
	}
}
