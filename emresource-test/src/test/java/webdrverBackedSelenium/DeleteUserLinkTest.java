package webdrverBackedSelenium;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.apache.log4j.Logger;

/*************************************************************
 * ' Description :This class includes DeleteUserLink requirement ' testcases '
 * Date :30-May-2012 ' Author :QSG
 * '------------------------------------------------------------- ' Modified
 * Date Modified By ' <Date> <Name> '
 *************************************************************/

public class DeleteUserLinkTest {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.DeleteUserLink");
	static {
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
				.setLevel(java.util.logging.Level.OFF);
	}

	String gstrTCID, gstrTO, gstrResult, gstrReason;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild,
			gstrBrowserName, strSessionId;
	double gdbTimeTaken;
	public static long gslsysDateTime;
	OfficeCommonFunctions objOFC;
	public Properties propEnvDetails;
	Properties propElementDetails;
	Properties propElementAutoItDetails;
	Properties pathProps;
	String gstrTimeOut = "";
	Selenium selenium;
	WebDriver driver;

	@Before
	public void setUp() throws Exception {

		gdtStartDate = new Date();
		gstrBrowserName = "IE 8";

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");

		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		Paths_Properties objPaths_Properties = new Paths_Properties();
		propElementAutoItDetails = objPaths_Properties.ReadAutoit_FilePath();

		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();

		/*DesiredCapabilities ieCapabilities = DesiredCapabilities
				.internetExplorer();
		ieCapabilities
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
		driver = new InternetExplorerDriver(ieCapabilities);*/

		//driver = new FirefoxDriver();
		//driver.manage().window().maximize();
		//driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
		/*driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
		driver.manage().timeouts().setScriptTimeout(1, TimeUnit.MINUTES);
		selenium = new WebDriverBackedSelenium(driver,
				propEnvDetails.getProperty("urlEU"));*/
		
		selenium = new DefaultSelenium("localhost",4444,"*iexplore",propEnvDetails.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();
			
		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		selenium.close();

		// kill browser
		selenium.stop();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testBQS85126() throws Exception {

		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");
		selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strLoginUserName = rdExcel.readData("Login", 3, 1);
		String strLoginPassword = rdExcel.readData("Login", 3, 2);

		selenium.open(propEnvDetails.getProperty("urlRel"));
		selenium.type(propElementDetails.getProperty("Login.UserName"),
				strLoginUserName);
		selenium.type(propElementDetails.getProperty("Login.Password"),
				strLoginPassword);
		selenium.click(propElementDetails.getProperty("Login.Submit"));
		selenium.waitForPageToLoad(gstrTimeOut);
		selenium.select(propElementDetails.getProperty("SelectRegion.Region"),
				"label=Z_FTS_Build_7626");

		selenium.click(propElementDetails.getProperty("SelectRegion.Next"));
		selenium.waitForPageToLoad(gstrTimeOut);
		
		selenium.selectWindow("");
		selenium.selectFrame("Data");
		selenium.click("//a[text()='Setup']");
		selenium.waitForPageToLoad(gstrTimeOut);
		Thread.sleep(10000);
		selenium.click("//a[text()='User Links']");
		selenium.waitForPageToLoad(gstrTimeOut);
		selenium.click("css=input[value='Create New User Link']");
		selenium.waitForPageToLoad(gstrTimeOut);
		File file = new File("C://Users//Admin//Desktop//GetLinkImage.jpg");
		selenium.type("css=input[name='document']",file.getAbsolutePath());
	}
	
	@Test
	public void testBQS() throws Exception {

		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");
		selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strLoginUserName = rdExcel.readData("Login", 3, 1);
		String strLoginPassword = rdExcel.readData("Login", 3, 2);
	}
}
