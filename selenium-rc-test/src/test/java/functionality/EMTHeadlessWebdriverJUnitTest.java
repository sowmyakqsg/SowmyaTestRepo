package functionality;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import pages.BrowserPreparationPage;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PreferencesPage;
import pages.UserAdministrationPage;

public class EMTHeadlessWebdriverJUnitTest{
	WebDriver driver;
	static {
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	}
	
	@Before
	public void setup() {
		BrowserPreparationPage objBrowserPreparationPage = new BrowserPreparationPage();
		driver = objBrowserPreparationPage.browserPreparationPage(driver);
	}
	
	@After
	public void closeBrowser() {
		driver.close();
	}

	@Test
	public void test() throws Exception {
		LoginPage objLoginPage = new LoginPage(driver);
		objLoginPage.login(driver, "autoregadmin4", "abc123");
		DashboardPage objDashboardPage = new DashboardPage(driver);
		objDashboardPage.clickPreferenceLink(driver);
		PreferencesPage objPreferencesPage = new PreferencesPage(driver);
		objPreferencesPage.clickUsersLink(driver);
		UserAdministrationPage objUserAdministrationPage = new UserAdministrationPage(driver);
		objUserAdministrationPage.clickNewUserLink(driver);
	}
}