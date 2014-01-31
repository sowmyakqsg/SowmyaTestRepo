import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class SeleniumTest {
	WebDriver driver;
	
	@Test
	public void testIE() throws Exception {
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("iexplore");
		cap.setVersion("8");
		cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
		
		driver = new RemoteWebDriver(new URL("http://192.168.27.53:5551/wd/hub"),cap);
		
		driver.get("http://www.google.com");
	}
}
