package phantomjs;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class EMTHeadlessWebdriverJUnitTest {
	WebDriver driver;

	public static final File PHANTOMJS_EXE = new File(
			System.getProperty("basedir"),
			"phantomjs-1.9.2-windows/phantomjs.exe");
	
	static {
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
				.setLevel(java.util.logging.Level.OFF);
	}

	@Test
	public void test() throws Exception {
		DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true); 
        System.out.println(PHANTOMJS_EXE.getAbsolutePath());
        caps.setCapability(
        PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,PHANTOMJS_EXE.getAbsolutePath());
        driver = new PhantomJSDriver(caps);
        driver.get("http://www.google.com");

        String Logintext = driver.findElement(By.linkText("Maps")).getText();
        System.out.println(Logintext);

		driver.get("https://emtrack-ng.qa.intermedix.com/app");
		assertEquals(driver.getTitle(),"EMTrack ~ Login");
		driver.close();
	}
}