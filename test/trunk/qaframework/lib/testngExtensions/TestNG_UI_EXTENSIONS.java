package qaframework.lib.testngExtensions;

import org.testng.annotations.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import qaframework.WaitTimeConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestNG_UI_EXTENSIONS {

	public WebDriver driver;
	public String wd_host;
	public String wd_browser;

	public TestNG_UI_EXTENSIONS() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config.properties");
			// load a properties file
			prop.load(input);
			wd_host = prop.getProperty("wd_host");
			wd_browser = prop.getProperty("wd_browser");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName(wd_browser);
		cap.setJavascriptEnabled(true);
		driver = new RemoteWebDriver(new URL(wd_host), cap);
		this.driver.manage().deleteAllCookies();
		
		this.driver.get("https://fleeteyes.qa.intermedix.com/user/login");
		this.driver
				.manage()
				.timeouts()
				.implicitlyWait(WaitTimeConstants.WAIT_TIME_LONG, TimeUnit.SECONDS);
		this.driver
				.manage()
				.timeouts()
				.pageLoadTimeout(WaitTimeConstants.WAIT_TIME_LONG, TimeUnit.SECONDS);
		this.driver.manage().window().maximize();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() throws Exception {
		this.driver.quit();
		//this.driver = null;
	}
}