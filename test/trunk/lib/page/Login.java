package lib.page;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import qaframework.WaitTimeConstants;
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
		this.page.element("login", "css").getDisplayedOne().click();
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

	public Login enterCompanyName(String company) throws Exception {
		this.page.element("company", "id").clearInputValue().sendKeys(company);
		return this;
	}

	public Vehicle loginSuccess(String company, String username,
			String passwords) throws Exception {
		this.enterCompanyName(company);
		this.enterUserName(username);
		this.enterPassword(passwords);
		this.clickLogin();
		//waiting for last loading element after login
		this.page.element("time", "css").getDisplayedOne();
		return new Vehicle(this.driver);
	}

	public Login loginFailure(String company, String username, String passwords)
			throws Exception {
		this.enterCompanyName(company);
		this.enterUserName(username);
		this.enterPassword(passwords);
		this.clickLogin();
		return new Login(this.driver);
	}
	
	public boolean verifyUsernameField() throws Exception{
		boolean condition = true;
		
		try{
			this.page.element("username", "id").getDisplayedOne();
		}catch(Exception e){
			condition =  false;
		}
		return condition;
	}
	
	public Vehicle loginSuccessively(String company, String username,
			String password) throws Exception {
		int count = 0;
		this.driver
				.manage()
				.timeouts()
				.implicitlyWait(WaitTimeConstants.WAIT_TIME_TOO_SMALL,
						TimeUnit.SECONDS);

		while (this.verifyUsernameField() && count < WaitTimeConstants.LOGIN_COUNT) {	
			this.driver.manage().deleteAllCookies();
			this.loginFailure(company, username, password);
			count++;
			//Login is possible only after 30 seconds after previous login
			Thread.sleep(20000);
		}

		if (count >= WaitTimeConstants.LOGIN_COUNT && this.verifyUsernameField()) {
			throw new Exception("User failed to login");
		}

		this.driver
				.manage()
				.timeouts()
				.implicitlyWait(WaitTimeConstants.WAIT_TIME_LONG,
						TimeUnit.SECONDS);
		return new Vehicle(this.driver);
	}
}