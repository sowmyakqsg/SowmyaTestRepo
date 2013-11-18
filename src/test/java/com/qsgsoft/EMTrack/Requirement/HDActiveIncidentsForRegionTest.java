package com.qsgsoft.EMTrack.Requirement;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMTrack.Shared.DashBoardPage;
import com.qsgsoft.EMTrack.Shared.FiltersPage;
import com.qsgsoft.EMTrack.Shared.IncidentsPage;
import com.qsgsoft.EMTrack.Shared.LoginPage;
import com.qsgsoft.EMTrack.Shared.PatientPage;
import com.qsgsoft.EMTrack.Support.Date_Time_settings;
import com.qsgsoft.EMTrack.Support.ElementId_properties;
import com.qsgsoft.EMTrack.Support.OfficeCommonFunctions;
import com.qsgsoft.EMTrack.Support.Paths_Properties;
import com.qsgsoft.EMTrack.Support.ReadData;
import com.qsgsoft.EMTrack.Support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
/**********************************************************************
' Description		:This class contains test cases from 'Req9319'  requirement
' Requirement		:Active Incidents for Region
' Requirement Group	:Gadget Directory
' Product		    :EMTrack 3.0
' Date			    :29-10-13
' Author		    :Manasa
'*******************************************************************/
public class HDActiveIncidentsForRegionTest{

	// Log4j object to write log entries to the Log files
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMTrack.features.Req9319");

	static {
		System.setProperty("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.SimpleLog");
		System.setProperty(
				"org.apache.commons.logging.simplelog.log.org.apache.http",
				"warn");
		BasicConfigurator.configure();
	}

	// Objects to access the common functions
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;

	/*
	 * Global variables to store the test case details – TestCaseID, Test
	 * Objective,Result, Reason for failure
	 */
	String gstrTCID, gstrTO, gstrResult, gstrReason;

	// Selenium Object
	Selenium selenium;

	// Object for date time settings
	Date_Time_settings dts = new Date_Time_settings();

	public Properties propElementDetails; // Property variable for ElementID file										
	public Properties propEnvDetails;// Property variable for Environment data
	public Properties propPathDetails; // Property variable for Path information
	public Properties propAutoITDetails;// Property variable for AutoIT file details							
	public static Properties browserProps = new Properties();
	public static String gstrBrowserName;// Variable to store browser name
	public static String gstrTimetaken, gstrdate, gstrtime, gstrBuild;// Result Variables																	
	@SuppressWarnings("unused")
	private String browser;
	double gdbTimeTaken; // Variable to store the time taken
	public static Date gdtStartDate;// Date variable
	@SuppressWarnings("unused")
	private String json;
	public static long sysDateTime;
	public static long gsysDateTime = 0;
	public static String gstrTimeOut = "";
	public static String sessionId_FF36, sessionId_FF20, sessionId_FF3,
			sessionId_FF35, sessionId_IE6, sessionId_IE7, sessionId_IE8,
			session_SF3, session_SF4, session_op9, session_op10, session_GC,
			StrSessionId = "", StrSessionId1, StrSessionId2;
	
	/****************************************************************************************************************
		* This function is called the setup() function which is executed before every test.
		*
		* The function will take care of creating a new selenium session for every test
		*
	****************************************************************************************************************/
	@Before
	public void setUp() throws Exception {

		Paths_Properties objPathsProps = new Paths_Properties();
		propAutoITDetails = objPathsProps.ReadAutoit_FilePath();

		propPathDetails = objPathsProps.Read_FilePath();

		// Create object to read environment properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		
		// Retrieve browser information
		browser = propEnvDetails.getProperty("Browser");
		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		
		// Retrieve the value of page load time limit
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		// create an object to refer to Element ID properties file
		@SuppressWarnings("unused")
		ElementId_properties objelementProp = new ElementId_properties();
		
		// Create a new selenium session
		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));
		
		// Start Selenium RC
		selenium.start();
		
		// Maximize the window
		selenium.windowMaximize();
		selenium.setTimeout(propEnvDetails.getProperty("TimeOut"));
		
		// Define object to call support functions to read excel, date etc
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();
	}

	/****************************************************************************************************************	
	    * This function is called the teardown() function which is executed after every test. 
		*
		* The function will take care of stopping the selenium session for every test and writing the execution
		* result of the test. 
		*
	****************************************************************************************************************/
	@After
	public void tearDown() throws Exception {
		// kill browser
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

		// Retrieve the path of the Result file
		String FILE_PATH = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		FILE_PATH = pathProps.getProperty("Resultpath");
		
		// Retrieve the total execution time
		gdbTimeTaken = objOFC.TimeTaken(gdtStartDate);
		Date_Time_settings dts = new Date_Time_settings();
		
		// Get the current date
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");
		
		// Get the Build ID of the application
		gstrBuild = propEnvDetails.getProperty("Build");
		
		// Check if result should be written to Excel or Test Management Tool
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");
		// Write Result of the test.
		
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetaken, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild, StrSessionId);
	}

	//start//testBQS127835//
	/**********************************************************************************************************
	'Description		: Verify that Ended Incident  is not displayed in 'Active Incidents in region'  gadget.
	'Precondition		:
	'Arguments			: None
	'Returns			: None
	'Date				: 30-10-13
	'Author				: Manasa
	***********************************************************************************************************/
	@Test
	public void testBQS127835() throws Exception {
		String strFuncResult = "";
		
		LoginPage objLoginPage = new LoginPage();
		IncidentsPage objIncidentsPage = new IncidentsPage();
		DashBoardPage objDashBoardPage = new DashBoardPage();

		try {
			gstrTCID = "127835"; // Test Case Id
			gstrTO = " Verify that Ended Incident  is not displayed in 'Active Incidents in region'  gadget.";// Test Objective																									
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strIncidentType = "AutoIncTyp1";
			String strIncidentName = "AutoInci" + System.currentTimeMillis();
			String pStrDescription = "Description";
			String strApplcnVal = "Actual";
			String pStrIncidentStatus = "Complete";

			/*
			 * STEP : Action:Login to EMTrack Expected Result:verify that ended
			 * incident is not displayed on gadget
			 */
			// 664216

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginPage.login(selenium, varUserName,
						varPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage
						.clickOnCreateIncidentBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.selIncidentType(selenium,
						strIncidentType, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.createIncident(selenium,
						strIncidentName, pStrDescription, strApplcnVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.vfyCreatedIncident(selenium,
						strIncidentName, strIncidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.endCreatedIncident(selenium,
						strIncidentName, strIncidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.selTheIncidentStatus(selenium,
						pStrIncidentStatus);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.vfyTheIncidentStatus(selenium,
						strIncidentName, strIncidentType, pStrIncidentStatus);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.navigateToDashboardPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Click on 'Configure' button and select 'New Dashboard' option.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selectNewDashboardOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Click on 'Configure' button and select 'New Dashboard' option.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selectAddGadgetOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Select 'Active Incidents For Region' option in 'Gadget
			// Directory'.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selectActiveIncidentsInRegionOptionInGadgetDirectory(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// verify 'Active Incidents In Region' Gadget Title.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.verifyActiveIncidentsInRegionGadgetTitle(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// verify created incident is displayed in 'Active Incidents In
			// Region' Gadget.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.vfyCreatedInciNotPresInActInciInRgnGdgt(selenium,
								strIncidentName, strApplcnVal);
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-127835";
			gstrTO = "Verify that Ended Incident  is not displayed in 'Active Incidents in region'  gadget.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS127835//

	//start//testBQS127837//
	/*********************************************************************************************
	'Description		:Verify that ended incidents are not displayed in Incident mode drop down
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :29-10-13
	'Author			    :Manasa
	*********************************************************************************************/
	@Test
	public void testBQS127837() throws Exception {

		LoginPage objLoginPage = new LoginPage();
		IncidentsPage objIncidentsPage = new IncidentsPage();
		DashBoardPage objDashBoardPage = new DashBoardPage();
		
		String strFuncResult = "";

		try {
			gstrTCID = "127837"; // Test Case Id
			gstrTO = " Verify that ended incidents are not displayed in Incident mode drop down";// Test Objective																				
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strIncidentType = "AutoIncTyp1";
			String strIncidentName = "AutoInci" + System.currentTimeMillis();
			String pStrDescription = "Description";
			String strApplcnVal = "Actual";
			String pStrIncidentStatus = "Complete";

			/*
			 * STEP : Action:Login to EMTrack Expected Result:verify that ended
			 * incidents are not displayed on Incident mode drop down
			 */
			// 664217

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginPage.login(selenium, varUserName,
						varPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage
						.clickOnCreateIncidentBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.selIncidentType(selenium,
						strIncidentType, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.createIncident(selenium,
						strIncidentName, pStrDescription, strApplcnVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.vfyCreatedIncident(selenium,
						strIncidentName, strIncidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.endCreatedIncident(selenium,
						strIncidentName, strIncidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.selTheIncidentStatus(selenium,
						pStrIncidentStatus);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.vfyTheIncidentStatus(selenium,
						strIncidentName, strIncidentType, pStrIncidentStatus);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.navigateToDashboardPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.clkOnIncidentModeBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.vfyIncidentNotPresentInInciModeDrpDwn(selenium,
								strIncidentName);
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-127837";
			gstrTO = "Verify that ended incidents are not displayed in Incident mode drop down";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS127837//
	
	//start//testBQS127469//
	/***********************************************************************************************************************
	'Description	:Verify that created incident is displayed on the dashboard gadget when Exercise/Drill incident is added.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:29-10-13
	'Author			:Rahul
	************************************************************************************************************************/
	@Test
	public void testBQS127469() throws Exception {
		String strFuncResult = "";
		
		LoginPage loginPage = new LoginPage();
		IncidentsPage incidentsPage = new IncidentsPage();
		DashBoardPage dashBoardPage = new DashBoardPage();
		
		try {
			gstrTCID = "127469";// Test Case Id
			gstrTO = "Verify that created incident is displayed on the dashboard gadget when Exercise/Drill incident is added.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String incidentType = "Special Event";
			String incidentName = "Auto"+System.currentTimeMillis();
			String incidentDescription = "Creating Incident for Testing";
			String incidentApplication = "Exercise/Drill";
			
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// Login to EMTrack Application.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = loginPage.login(selenium, varUserName,
						varPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigation to 'Incidents' Page.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Click on Create Incident Button.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage
						.clickOnCreateIncidentBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Click on 'Incident Type' input and select an incident type.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.selIncidentType(selenium,
						incidentType, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create incident.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.createIncident(selenium,
						incidentName, incidentDescription, incidentApplication,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// verify created incident in incidents page.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.vfyCreatedIncident(selenium,
						incidentName, incidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigation to 'Dashboard' Page.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage.navigateToDashboardPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Click on 'Configure' button and select 'New Dashboard' option.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage
						.selectNewDashboardOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Click on 'Configure' button and select 'New Dashboard' option.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage.selectAddGadgetOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Select 'Active Incidents For Region' option in 'Gadget
			// Directory'.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage
						.selectActiveIncidentsInRegionOptionInGadgetDirectory(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// verify 'Active Incidents In Region' Gadget Title.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage
						.verifyActiveIncidentsInRegionGadgetTitle(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// verify created incident is displayed in 'Active Incidents In
			// Region' Gadget.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage
						.verifyCreatedIncidentInActiveIncidentsInRegionGadget(
								selenium, incidentName, incidentApplication);
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-127469";
			gstrTO = "Verify that created incident is displayed on the dashboard gadget when Exercise/Drill incident is added.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS 127469//

	//start//testBQS127471//
	/**************************************************************************************************************
	'Description	:Verify that created incident is displayed on the dashboard gadget when Test incident is added.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:30-10-13
	'Author			:Manasa
	***************************************************************************************************************/
	@Test
	public void testBQS127471() throws Exception {
		String strFuncResult = "";
		
		LoginPage loginPage = new LoginPage();
		IncidentsPage incidentsPage = new IncidentsPage();
		DashBoardPage dashBoardPage = new DashBoardPage();

		try {
			gstrTCID = "127471"; // Test Case Id
			gstrTO = " Verify that created incident is displayed on the dashboard gadget when Test incident is added.";// Test Objective																										
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String incidentType = "AutoIncTyp1";
			String incidentName = "Auto1"+System.currentTimeMillis();
			String incidentDescription = "Creating Incident for Testing";
			String incidentApplication = "Test";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// Login to EMTrack Application.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = loginPage.login(selenium, varUserName,
						varPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigation to 'Incidents' Page.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Click on Create Incident Button.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage
						.clickOnCreateIncidentBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Click on 'Incident Type' input and select an incident type.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.selIncidentType(selenium,
						incidentType, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create incident.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.createIncident(selenium,
						incidentName, incidentDescription, incidentApplication,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// verify created incident in incidents page.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.vfyCreatedIncident(selenium,
						incidentName, incidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigation to 'Dashboard' Page.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage.navigateToDashboardPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Click on 'Configure' button and select 'New Dashboard' option.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage
						.selectNewDashboardOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Click on 'Configure' button and select 'New Dashboard' option.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage.selectAddGadgetOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Select 'Active Incidents For Region' option in 'Gadget
			// Directory'.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage
						.selectActiveIncidentsInRegionOptionInGadgetDirectory(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// verify 'Active Incidents In Region' Gadget Title.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage
						.verifyActiveIncidentsInRegionGadgetTitle(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// verify created incident is displayed in 'Active Incidents In
			// Region' Gadget.
			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage
						.verifyCreatedIncidentInActiveIncidentsInRegionGadget(
								selenium, incidentName, incidentApplication);
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-127471";
			gstrTO = "Verify that created incident is displayed on the dashboard gadget when Test incident is added.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS127471//
	
	//start//testBQS115116//
	/*****************************************************************************************************************************************
	'Description		: Select Incident X in Incident mode drop down and verify that dashboard gadgets displays data related to incident X.
	'Precondition		:
	'Arguments			: None
	'Returns			: None
	'Date				: 08-11-2013
	'Author				: Rahul
	******************************************************************************************************************************************/
	@Test
	public void testBQS115116() throws Exception {
		String strFuncResult = "";

		LoginPage objLoginPage = new LoginPage();
		FiltersPage objFiltersPage = new FiltersPage();
		IncidentsPage objIncidentsPage = new IncidentsPage();
		PatientPage objPatientPage = new PatientPage();
		DashBoardPage objDashBoardPage = new DashBoardPage();

		try {
			gstrTCID = "115116"; // Test Case Id
			gstrTO = " Select Incident X in Incident mode drop down and verify that dashboard gadgets displays data related to incident X.";// Test Objective																																
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strFilterName = "Auto Filter" + System.currentTimeMillis();
			String strFilterDescription = "Testing save Filter";
			String strCriteria = "Tracking Status";
			String strIncidentType = "AutoIncTyp1";
			String strIncidentName = "AutoIncident"
					+ System.currentTimeMillis();
			String pStrDescription = "Creating Incident for Testing";
			String strApplcnVal = "Actual";
			String strPatientID = "AutoPatientID" + System.currentTimeMillis();
			String strGender = "Male";
			String strDestination = "Alpena Regional Medical Center";
			String strProvider = "Blackhawk Helicopter";
			String strUnit = "123";
			String strETA = "5";
			String strGroupBy = "Active Incident";
			String strSummaryType = "Pie Chart";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginPage.login(selenium, varUserName,
						varPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage.navToFiltersPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage.selSearchCriteria(selenium,
						strCriteria);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage.clkSaveAsBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage.saveFilter(selenium,
						strFilterName, strFilterDescription);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage.verifyCreatedFilter(selenium,
						strFilterName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage
						.clickOnCreateIncidentBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.selIncidentType(selenium,
						strIncidentType, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.createIncident(selenium,
						strIncidentName, pStrDescription, strApplcnVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objIncidentsPage.vfyCreatedIncident(selenium,
						strIncidentName, strIncidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPatientPage.navigateToPatientPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPatientPage.addDailyPatientMandatoryData(
						selenium, strPatientID, strGender, strIncidentName,
						strDestination, strProvider, strUnit, strETA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selectNewDashboardOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.selFilterInClientListGadget(
						selenium, strFilterName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.clkClientListApplyBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.vfyFilterNameInClientListGadgetTitle(selenium,
								strFilterName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selectAddGadgetOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selectClientSummaryOptionInGadgetDirectory(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selFilterInClientSummaryGadget(selenium,
								strFilterName, strGroupBy, strSummaryType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.clkClientSummaryApplyBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.clkOnIncidentModeBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selCreatedIncidentInInciModeDrpDwn(selenium,
								strIncidentName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.clkClientListMagnifierIcon(
						selenium, strPatientID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginPage.logOut(selenium);
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-115116";
			gstrTO = " Select Incident X in Incident mode drop down and verify that dashboard gadgets displays data related to incident X.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS115116//
}