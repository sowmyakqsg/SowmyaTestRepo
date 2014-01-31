import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class SeleniumTest1 {
	WebDriver driver;
	
	@Test
	public void test() throws Exception {
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("firefox");
		cap.setVersion("14.0.1");
		cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
		
		driver = new RemoteWebDriver(new URL("http://192.168.27.53:5555/wd/hub"),cap);
		/*
		WebDriver driver = new RemoteWebDriver(
                new URL("http://localhost:4444/wd/hub"), 
                DesiredCapabilities.internetExplorer());

		driver.get("http://www.google.com");*/
	}
}
