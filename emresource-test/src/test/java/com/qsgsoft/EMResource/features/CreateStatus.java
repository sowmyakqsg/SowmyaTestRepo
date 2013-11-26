package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;

import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class CreateStatus {

	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.CreateStatus");
	static{
		BasicConfigurator.configure();
	}
	
	String gstrTCID, gstrTO, gstrResult, gstrReason;	
	Selenium selenium1,selenium2,selenium3,seleniumMain;	
	double gdbTimeTaken;
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	public static Date gdtStartDate;
	public Properties propElementDetails;
	public static String gstrBrowserName;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;
	public Properties propEnvDetails,propAutoItDetails;
	public static Properties browserProps = new Properties();
	
	private String browser="";
	
	private String json;
	public static long sysDateTime;	
	public static long gsysDateTime;
	public static String sessionId_FF36,sessionId_FF20,sessionId_FF3,sessionId_FF35,sessionId_IE6,sessionId_IE7,
    sessionId_IE8,session_SF3,session_SF4,session_op9,session_op10,session_GC,StrSessionId,StrSessionId1,StrSessionId2;
	public static String gstrTimeOut="";
	
	@Before
	public void setUp() throws Exception {
		
		
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
					
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");		
		
		browser=propEnvDetails.getProperty("Browser");
		gstrBrowserName=propEnvDetails.getProperty("Browser").substring(1)+" "+propEnvDetails.getProperty("BrowserVersion");
		//create an object to refer to properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		if (propEnvDetails.getProperty("Server").equals("saucelabs.com")) {
		         
	        
			selenium1 = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.json, propEnvDetails.getProperty("urlEU"));
			
	       
		} else {
			selenium1 = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.browser, propEnvDetails.getProperty("urlEU"));		
			selenium2 = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.browser, propEnvDetails.getProperty("urlEU"));		
			selenium3 = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.browser, propEnvDetails.getProperty("urlEU"));		
			seleniumMain = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.browser, propEnvDetails.getProperty("urlEU"));		
		
		}		
			
		selenium1.start();
		selenium1.windowMaximize();
		selenium1.setTimeout("");

		selenium2.start();
		selenium2.windowMaximize();
		selenium2.setTimeout("");
		
		selenium3.start();
		selenium3.windowMaximize();
		selenium3.setTimeout("");
		
		seleniumMain.start();
		seleniumMain.windowMaximize();
		seleniumMain.setTimeout("");
		
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();		
		
	}

	@After
	public void tearDown() throws Exception {	
		
		// kill browser
		selenium1.close();
		selenium1.stop();
		
		selenium2.close();
		selenium2.stop();
		
		selenium3.close();
		selenium3.stop();
		
		seleniumMain.close();
		seleniumMain.stop();
		
		// determine log message
		if (gstrResult.toUpperCase().equals("PASS")) {
			log4j.info("-------------------Test Case Execution " + gstrTCID
					+ " has PASSED------------------");
		} else if (gstrResult.toUpperCase().equals("SKIP")) {
			log4j.info("-------------------Test Case Execution " + gstrTCID
					+ " was SKIPPED------------------");
		} else {
			log4j.info("-------------------Test Case Execution " + gstrTCID
					+ " has FAILED------------------");
		}
		String FILE_PATH = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		FILE_PATH = pathProps.getProperty("Resultpath");
		// and execution time
		gdbTimeTaken = objOFC.TimeTaken(gdtStartDate);
		Date_Time_settings dts = new Date_Time_settings();
		gstrdate = dts.getCurrentDate(selenium1, "yyyy-MM-dd");
		gstrBuild = propEnvDetails.getProperty("Build");
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");

		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);
	
	}
	
	@SuppressWarnings("unused")
	@Ignore
	public void testBQS69299()throws Exception{
		try{
			gstrTCID = "69299";			
			gstrTO = "Verify that a status can be deactivated";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");
			
			Login objLogin = new Login();// object of class Login
						
			StatusTypes objStatTyp=new StatusTypes();
			ReadData objReadData = new ReadData ();
			Views objView=new Views();
			General objRef=new General();
			
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			
			String strFILE_PATH=pathProps.getProperty("TestData_path");
			
			//login details
			String strUserName = objReadData.readData("Login", 30, 1);
			String strPassword = objReadData.readData("Login", 30, 2);
						
			String strRegn=objReadData.readData("Login", 3, 4);

			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword =objReadData.readData("Login", 3, 2);
			
			String strViewName=objReadData.readInfoExcel("StatusType Details", 2, 6, strFILE_PATH);
			String strStatType=objReadData.readInfoExcel("StatusType Details", 2, 2, strFILE_PATH);
			String strResource=objReadData.readInfoExcel("StatusType Details", 2, 5, strFILE_PATH);
			String[] strStatuses=objReadData.readInfoExcel("StatusType Details", 2, 3, strFILE_PATH).split(",");
			//String strSection=objReadData.readInfoExcel("StatusType Details", 2, 7, strFILE_PATH);
			
			String strComent="";
			
						
			int intResCnt=0;
			/*
			 * Step 2: Login as user 'A' and navigate to Setup>>Status types <-> 'Status Type List' page is displayed. 
			 *
			 */
			selenium1.open(propEnvDetails.getProperty("urlRel"));
			String strFailMsg = objLogin.login(selenium1, strUserName,
					strPassword);	
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg=objView.navToUserView(selenium1, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg=objView.updateStatusOfStatusType(selenium1, strStatType, strStatuses[0], strComent);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			selenium2.open(propEnvDetails.getProperty("urlRel"));
			strFailMsg = objLogin.login(selenium2, strUserName,
					strPassword);	
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg=objView.navToUserView(selenium2, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}			
						
			try {
				assertTrue(strFailMsg.equals(""));	
				//click on resource link
				selenium2.click("//div[@id='mainContainer']/div/table/tbody/tr/td[2]/a[text()='"+strResource+"']");
				selenium2.waitForPageToLoad(gstrTimeOut);
				

				try{
					assertEquals("View Resource Detail", selenium2.getText(propElementDetails
							.getProperty("Header.Text")));
					log4j.info("View Resource Detail screen is displayed");
					intResCnt++;
				}catch(AssertionError ae){
					log4j.info("View Resource Detail screen is NOT displayed");
					gstrReason="View Resource Detail screen is NOT displayed";
				}
				
			
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
					
	
			selenium3.open(propEnvDetails.getProperty("urlRel"));
			strFailMsg = objLogin.login(selenium3, strUserName,
					strPassword);	
			
			try {
				assertTrue(strFailMsg.equals(""));	
				selenium3.selectWindow("");
				selenium3.selectFrame("Data");
				//View menu
				selenium3.mouseOver("link=View");
				//click on Map link
				selenium3.click("link=Map");
				selenium3.waitForPageToLoad(gstrTimeOut);
				
				try{
					assertEquals("Regional Map View", selenium3.getText("css=h1"));
					log4j.info("Regional Map View screen is displayed");
					intResCnt++;
				}catch(AssertionError ae){
					log4j.info("Regional Map View screen is NOT displayed");
					gstrReason="Regional Map View screen is NOT displayed";
				}
			
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
					
			seleniumMain.open(propEnvDetails.getProperty("urlRel"));
			//Login as Region Admin
			 strFailMsg = objLogin.login(seleniumMain, strLoginUserName,
					 strLoginPassword);
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//nav to default region
				strFailMsg = objLogin.navUserDefaultRgn(seleniumMain, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg=objStatTyp.navStatusTypList(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg=objStatTyp.navToStatusListForStatusType(seleniumMain, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg=objStatTyp.activeStatusUnderStatusType(seleniumMain, strStatType, strStatuses[0], false);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				
				/*
				 * Step 6: Refresh the View 'V1' screen on browser 'B1' <->	NST is no longer displayed

				<-> 'There are no resources to display in this view.' message is displayed.
				 */
				strFailMsg=objRef.refreshPage(selenium1);
				try {
					assertTrue(strFailMsg.equals(""));
			
					try{
						assertFalse(selenium1.isElementPresent("//div[@id='mainContainer']/div/table/tbody/tr/td[3]/a[text()='"+strStatuses[0]+"']"));
					
						log4j.info("Status "+strStatType+" is retained in the view screen");
						
						// click update status image
						selenium1.click("//div[@id='mainContainer']/div/table/tbody/"
								+ "tr/td[1]/a/img");
						selenium1.waitForPageToLoad(gstrTimeOut);
						
						try{
							assertFalse(selenium1.isElementPresent("//div[@class='multiStatus']/div/span/label[text()='"+strStatuses[0]+"']"));						
							log4j.info("Status "+strStatuses[0]+" is not available in the update status screen");
							intResCnt++;
						}catch(AssertionError ae){
							log4j.info("Status "+strStatuses[0]+" is still available in the update status screen");
							gstrReason="Status "+strStatuses[0]+" is still available in the update status screen";
						}					
											
					}catch(AssertionError ae){
						log4j.info("Status "+strStatType+" is NOT retained in the view screen");
						gstrReason=gstrReason+" "+"Status "+strStatType+" is NOT retained in the view screen";
					}
				
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				
				/*
				 * Step 7: Refresh the 'View Resource Detail' on browser 'B2' <-> NST is no longer displayed
				 */
				strFailMsg=objRef.refreshPage(selenium2);
				try {
					assertTrue(strFailMsg.equals(""));
					try{
						assertFalse(selenium2.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[3]/a[text()='"+strStatuses[0]+"']"));
						log4j.info("Status "+strStatuses[0]+" is retained in the View Resource Detail screen");
						
						// click update status image
						selenium2.click("//table[starts-with(@id,'stGroup')]/tbody/tr/td[3][text()='"+strStatuses[0]+"']");						
						selenium2.waitForPageToLoad(gstrTimeOut);
						
						try{
							assertFalse(selenium2.isElementPresent("//div[@class='multiStatus']/div/span/label[text()='"+strStatuses[0]+"']"));						
							log4j.info("Status "+strStatuses[0]+" is not available in the update status screen");
							intResCnt++;
						}catch(AssertionError ae){
							log4j.info("Status "+strStatuses[0]+" is still available in the update status screen");
							gstrReason="Status "+strStatuses[0]+" is still available in the update status screen";
						}					
						
					}catch(AssertionError ae){
						log4j.info("Status "+strStatuses[0]+" is NOT retained in the View Resource Detail screen");
						gstrReason=gstrReason+" "+"Status "+strStatuses[0]+" is NOT retained in the View Resource Detail screen";
					}
				
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				/*
				 * Step 8: Refresh the 'Regional Map View' on browser 'B3' <->	NST is no longer displayed.

					<-> ST is displayed on the resource RS pop up window.
				 */
				strFailMsg=objRef.refreshPage(selenium3);
				try {
					assertTrue(strFailMsg.equals(""));
					
					//select the Find Resource option
					selenium3.select(propElementDetails.getProperty("ViewMap.FindResource"), "label="+strResource);
								
					//Wait for resource pop up to present
					int intCnt=0;
					while(selenium3.isVisible(propElementDetails.getProperty("ViewMap.ResPopup"))==false&&intCnt<20){
						Thread.sleep(1000);
						intCnt++;
					}
					
					if(selenium3.isVisible(propElementDetails.getProperty("ViewMap.ResPopup"))){
						try{
							assertTrue(selenium3.getText(propElementDetails.getProperty("ViewMap.ResPopup.StatTypeList")).contains(strStatType+": "+strStatuses[0]));						
							log4j.info(strStatType+" is displayed in the pop-up window with the value updated "+strStatuses[0]);
							
							selenium3.click("link=Update Status");
							selenium3.waitForPageToLoad(gstrTimeOut);
							
							try{
								assertFalse(selenium2.isElementPresent("//div[@class='multiStatus']/div/span/label[text()='"+strStatuses[0]+"']"));						
								log4j.info("Status "+strStatuses[0]+" is not available in the update status screen");
								intResCnt++;
							}catch(AssertionError ae){
								log4j.info("Status "+strStatuses[0]+" is still available in the update status screen");
								gstrReason="Status "+strStatuses[0]+" is still available in the update status screen";
							}		
						}catch(AssertionError ae){
							log4j.info(strStatType+" is displayed in the pop-up window with the value updated "+strStatuses[0]);
							gstrReason=gstrReason+" "+strStatType+" is NOT displayed in the pop-up window with the value updated "+strStatuses[0];
						}
					}
					
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "69299";
			gstrTO = "Verify that a status can be deactivated";
			gstrResult = "FAIL";
			excReason = null;
	
			log4j.info(e);
			log4j.info("========== Test Case '"+gstrTCID+"' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();	
		
		}			
	}

}
