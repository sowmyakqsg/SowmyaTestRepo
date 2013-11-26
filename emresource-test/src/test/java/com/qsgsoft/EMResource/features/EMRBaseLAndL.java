package com.qsgsoft.EMResource.features;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.General;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.Regions;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

public class EMRBaseLAndL{

	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.CreateTextStatusType");
	static {
		BasicConfigurator.configure();
	}
	String gstrTCID, gstrTO, gstrResult, gstrReason;	
	Selenium selenium,seleniumPrecondition;	
	double gdbTimeTaken;
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	public static Date gdtStartDate;
	public Properties propElementDetails;
	public static String gstrBrowserName;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;
	public Properties propEnvDetails,propAutoItDetails;
	public static Properties browserProps = new Properties();
	public Properties pathProps;
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
			
		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();
		
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");		
		
		browser=propEnvDetails.getProperty("Browser");
		gstrBrowserName=propEnvDetails.getProperty("Browser").substring(1)+" "+propEnvDetails.getProperty("BrowserVersion");
		//create an object to refer to properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		if (propEnvDetails.getProperty("Server").equals("saucelabs.com")) {
		         
	        
			selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.json, propEnvDetails.getProperty("urlEU"));
			
	       
		} else {
			selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.browser, propEnvDetails.getProperty("urlEU"));			
		
		}		
			
		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
		selenium.start();
		selenium.windowMaximize();
		selenium.setTimeout("");

		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();		
		
	}

	@After
	public void tearDown() throws Exception {	
		
		// kill browser
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
		
		selenium.close();
		selenium.stop();
		
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
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");
		gstrBuild = propEnvDetails.getProperty("Build");
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");

		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);
	
	}
	

	//start//testBQS126261//
	/***************************************************************
	'Description		:Verify that user can login to EMResource providing valid login credentials
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:10/8/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS126261() throws Exception {

		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		CreateUsers objCreateUsers = new CreateUsers();
		Regions objRegion = new Regions();
		General objGeneral = new General();

		try {
			gstrTCID = "126261"; // Test Case Id
			gstrTO = " Verify that user can login to EMResource providing valid login credentials";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);

			String strRegn1 = objrdExcel.readData("Login", 3, 4);
			String strRegn2 = objrdExcel.readData("Regions", 3, 5);
			String strRegionValue[] = new String[2];

			// User
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName;

			log4j.info("~~~~~~~~~~~ Precondtion for test case " + gstrTCID
					+ " starts ~~~~~~~~~");
			/*
			 * STEP : Action:Preconditions: 1. User 'U1' has a account in
			 * EMResource. Expected Result:No Expected Result
			 */
			// 662686

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fetch region values
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegionValue[0] = objRegion.fetchRegionValue(
						seleniumPrecondition, strRegn1);
				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strRegionValue[1] = objRegion.fetchRegionValue(
						seleniumPrecondition, strRegn2);
				if (strRegionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// User
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navToCreateUserPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.CustomView"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUser(
						seleniumPrecondition, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRegionValues = { strRegionValue[0] };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName, strRegionValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~~~~~~~ Precondtion for test case " + gstrTCID
					+ " ENDS ~~~~~~~~~");

			/*
			 * STEP : Action:Launch EMResource on the browser using the URL
			 * (https://emresource-test.emsystem.com) Expected Result:EMResource
			 * login page is displayed with: 'Intermedix emsystems' and
			 * EMResource as Titles Username and Password text fields. 'Log In'
			 * button 'Help' Link 'www.intermedix.com' link with copyright
			 * edition
			 */
			// 662687

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.vfyLoginPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Enter valid Username and Password and click on 'Log
			 * In' button Expected Result:'Select Region' page is displayed with
			 * a message 'You have access to multiple regions. Please select
			 * which one you would like to view.' message 'Region' drop down
			 * with the list of all the regions to which the user has access.
			 * 'Next' button is displayed Following are displayed at the footer
			 * of the application: Copyright edition '� 1998-2013 Intermedix /
			 * EMSystems (Terms & Conditions)' is displayed with (Terms &
			 * conditions) hyperlinked EMResource version and build number
			 * 'EMSystem Admin' (logged in user's full name) 'intermedix
			 * emsystems' logo is displayed at the top right corner and is
			 * hyperlinked 'EMResource' app switcher is displayed at the top
			 * left of the application.
			 */
			// 662688

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToSelectRegionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionListForUser(selenium,
						strRegionValue[0], true, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionListForUser(selenium,
						strRegionValue[1], true, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.vfySelRegnPage(selenium,
						strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on the app switcher Expected Result:'Account
			 * Management' is listed.
			 */
			// 662689

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.vfyAppSwitcherIsDisabledOrNotInSelRegPge(selenium,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on the 'intermedix emsystems' logo present at
			 * the right corner of the application Expected Result:'Intermedix
			 * Emsystems' website is opened in a new window.
			 */
			// 662690

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objGeneral
						.ChkOnIntrmdxLogoAndVfyPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Terms & Conditions' Link Expected
			 * Result:'TERMS AND CONDITIONS OF USE' page is opened in a new
			 * window
			 */
			// 662691

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objGeneral.clkOnTermsAndConditionLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user 'U1' Expected Result:'Region Default'
			 * screen is displayed.
			 */
			// 662692

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-126261";
			gstrTO = "Verify that user can login to EMResource providing valid login credentials";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
		if (blnLogin) {
			strFuncResult = objLogin.logout(selenium);

			try {
				assertEquals("", strFuncResult);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
	}

	// end//testBQS126261//
}
