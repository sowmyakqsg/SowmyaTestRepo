package qaframework;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Configuration {
	public WebDriver driver;

	//@BeforeMethod
	//@Parameters({"browser", "url","wd_host"}) /* this annotation is used to insert parameter in test*/
	/*public void browser(String browser, String url,String wd_host) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName(browser);
		cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);

		this.driver = new RemoteWebDriver(new URL(wd_host),cap);
		this.driver.manage().deleteAllCookies();
		this.driver.get(url);
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		this.driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}*/

	@BeforeMethod
	public void manageTimeOut() throws Exception{
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		this.driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	@BeforeMethod
	public void deleteCookies() throws Exception{
		this.driver.manage().deleteAllCookies();
	}
	
	@BeforeMethod
	public void browser() throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("firefox");
		cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);

		this.driver = new RemoteWebDriver(new URL("http://192.168.27.53:4446/wd/hub"),cap);
		this.driver.get("");
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		this.driver.close();
		this.driver.quit();
		this.driver=null;
	}
}