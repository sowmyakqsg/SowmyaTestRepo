package functionality;

import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumDriverTest {
	//Selenium selenium;
	
	WebDriver driver; 
	
/*
	@Test
	public void test() {
		selenium = new DefaultSelenium("localhost",4444,"*firefox","https://triptix.qa.intermedix.com/TripTixCDX/login/redirect");
		selenium.start();
		selenium.open("https://triptix.qa.intermedix.com/TripTixCDX/login/redirect");
		selenium.windowMaximize();
		selenium.click("//div[@id='sectionTemplate']/div[1]/section[3]/div/a");
		selenium.select("//html/body/div[2]/div[2]/div/div[1]/div/select", "ALABAMA");
		selenium.close();
		selenium.stop();
	}*/
	
	/*@Test
	public void testWebDriver() {
		driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		driver.get("https://triptix.qa.intermedix.com/TripTixCDX/login/redirect");
		driver.findElement(By.xpath("//div[@id='sectionTemplate']/div[1]/section[3]/div/a")).click();
		new Select(driver.findElement(By.xpath("//html/body/div[2]/div[2]/div/div[1]/div/select"))).selectByVisibleText("ALABAMA");
		driver.close();
	}*/
	
	@Test
	public void testSeleniumGrid() throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("firefox");
		this.driver = new RemoteWebDriver(new URL("http://localhost:4446/wd/hub"), cap);
		this.driver.manage().deleteAllCookies();
		this.driver.get("https://fleeteyes.qa.intermedix.com/user/login");
		driver.close();
	}
}
