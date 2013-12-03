package pages;

import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

public class LoginPage {
	
	LoginPage(Selenium selenium){
		selenium.open("/");
		selenium.waitForCondition("selenium.isElementPresent(\"//div[@id='ctl00_contentSection_LoginPanel']/h2\")", "60000");
	}
	
	public void login(Selenium selenium, String strUsername,String strPassword){
		selenium.type("//input[@id='name']", strUsername);
		selenium.type("//input[@id='password']", strPassword);
		assertEquals(selenium.getValue("//input[@id='name']"),strUsername);
		assertEquals(selenium.getValue("//input[@id='password']"),strPassword);
		selenium.click("//input[@id='loginBttn']");
	}
}
