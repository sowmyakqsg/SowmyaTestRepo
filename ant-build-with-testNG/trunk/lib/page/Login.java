package lib.page;

import java.io.File;
import org.openqa.selenium.WebDriver;
import qaframework.lib.pageObject.PageObject;

public class Login extends PageObject {

	WebDriver driver;

	public Login(WebDriver _driver) throws Exception {
		super(_driver);
		this.driver = _driver;
	}
	
	protected File getXMLFile() throws Exception {
		return new File(System.getProperty("user.dir")+"/../lib/xml/login_elements.xml");
	}
	
	public Login clickLogin() throws Exception {
		this.page.element("login", "css").getOne().click();
		return this;
	}

	public Login enterUserName(String username) throws Exception {
		this.page.element("username", "id").clearInputValue().sendKeys(username);
		return this;
	}

	public Login enterPassword(String passwords) throws Exception {
		this.page.element("password", "id").clearInputValue().sendKeys(passwords);
		return this;
	}

	public Login loginFailure(String username, String passwords)
			throws Exception {
		this.enterUserName(username);
		this.enterPassword(passwords);
		this.clickLogin();
		return new Login(this.driver);
	}
}