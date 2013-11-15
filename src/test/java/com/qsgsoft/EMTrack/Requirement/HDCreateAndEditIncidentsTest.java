package com.qsgsoft.EMTrack.Requirement;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMTrack.Shared.DashBoardPage;
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

/*************************************************************************************************
 * Description       :This class contains test cases from 'HDCreateAndEditIncidents' requirement '
 * Requirement       :Create and edit Incidents'
 * Requirement Group :Manage Incidents '
 * Product           :EMTrack 3.0 ' 
 * Date              :13/11/2013 ' 
 * Author            :Rahul 
 *************************************************************************************************/
public class HDCreateAndEditIncidentsTest {
	
	// Log4j object to write log entries to the Log files
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMTrack.features.HDCreateAndEditIncidents");

	static {
		System.setProperty("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.SimpleLog");
		System.setProperty(
				"org.apache.commons.logging.simplelog.log.org.apache.http",
				"warn");
		PropertyConfigurator
				.configure("D:/Selenium/com.qsgsoft.EMTrack3.0/src/test/resources/PropertiesFiles/log4j.properties");
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

	/***************************************************************************************************************************
	 * * This function is called the teardown() function which is executed after every test.
	 * 
	 * The function will take care of stopping the selenium session for every  test and writing the execution result of the test.
	 * 
	 ***************************************************************************************************************************/
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
	
	//start//testBQS127981//
/***********************************************************************************************************************
'Description	:Verify that Incident can be created at region level.
'Precondition	:
'Arguments		:None
'Returns		:None
'Date			:13-11-2013
'Author			:Rahul
************************************************************************************************************************/
	@Test
	public void testBQS127981() throws Exception {
		String strFuncResult = "";

		LoginPage loginPage = new LoginPage();
		IncidentsPage incidentsPage = new IncidentsPage();

		try {
			gstrTCID = "127981";// Test Case Id
			gstrTO = "Verify that Incident can be created at region level .";// Test Objective															
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String incidentType = "AutoIncTyp1";
			String incidentName = "Auto" + System.currentTimeMillis();
			String incidentDescription = "Creating Incident for Testing";
			String incidentApplication = "Test";
			String locationName = "Justin Hospital";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = loginPage.login(selenium, varUserName,
						varPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage
						.clickOnCreateIncidentBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.selIncidentType(selenium,
						incidentType, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.createIncident(selenium,
						incidentName, incidentDescription, incidentApplication,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.vfyCreatedIncident(selenium,
						incidentName, incidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.changeLocation(selenium,
						locationName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage
						.vfyCreatedIncidentNotPresentInOtherLocation(selenium,
								incidentName, incidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = loginPage.logOut(selenium);
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
			gstrTCID = "BQS-127981";
			gstrTO = "Verify that Incident can be created at region level .";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS127981//
	
//start//testBQS127998//
/***********************************************************************************************************************
'Description	:Verify that incident can be created at provider level.
'Precondition	:
'Arguments		:None
'Returns		:None
'Date			:13-11-2013
'Author			:Rahul
************************************************************************************************************************/
	@Test
	public void testBQS127998() throws Exception {
		String strFuncResult = "";

		LoginPage loginPage = new LoginPage();
		IncidentsPage incidentsPage = new IncidentsPage();

		try {
			gstrTCID = "127998";// Test Case Id
			gstrTO = "Verify that incident can be created at provider level.";// Test Objective
																				
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String incidentType = "AutoIncTyp1";
			String incidentName = "Auto" + System.currentTimeMillis();
			String incidentDescription = "Creating Incident for Testing";
			String incidentApplication = "Test";
			String locationName = "Justin Hospital";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = loginPage.login(selenium, varUserName,
						varPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage
						.clickOnCreateIncidentBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.selIncidentType(selenium,
						incidentType, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.createIncidentWithProvider(
						selenium, incidentName, incidentDescription,
						incidentApplication, true, locationName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.vfyCreatedIncident(selenium,
						incidentName, incidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.changeLocation(selenium,
						locationName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.vfyCreatedIncident(selenium,
						incidentName, incidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = loginPage.logOut(selenium);
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
			gstrTCID = "BQS-127998";
			gstrTO = "Verify that incident can be created at provider level.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS127998//
	
	//start//testBQS116130//
	/*********************************************************************************************
	'Description		:Verify that Active Incident can be ended manually 
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :14-11-2013
	'Author			    :Rahul
	*********************************************************************************************/
	@Test
	public void testBQS116130() throws Exception {

		LoginPage objLoginPage = new LoginPage();
		IncidentsPage objIncidentsPage = new IncidentsPage();
		PatientPage objPatientPage = new PatientPage();

		String strFuncResult = "";

		try {
			gstrTCID = "116130"; // Test Case Id
			gstrTO = " Verify that Active Incident can be ended manually";// Test Objective																					
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
				strFuncResult = objPatientPage.navigateToPatientPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPatientPage
						.vfyEndIncidentNotPresentInaddDailyPatientPage(
								selenium, strIncidentName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPatientPage
						.vfyEndIncidentNotPresentInAddEvacueePatientPage(
								selenium, strIncidentName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPatientPage
						.vfyEndIncidentNotPresentInVaccinationPatientPage(
								selenium, strIncidentName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPatientPage
						.vfyEndIncidentNotPresentInMCITriagePatientPage(
								selenium, strIncidentName);
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
			gstrTCID = "BQS-116130";
			gstrTO = " 	Verify that Active Incident can be ended manually ";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS116130//
	
	//start//testBQS116131//
	/***********************************************************************************************************************
	'Description	:Verify that owner of the incident can update Participation level of the incident.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:14-11-2013
	'Author			:Rahul
	************************************************************************************************************************/
	@Test
	public void testBQS116131() throws Exception {
		String strFuncResult = "";

		LoginPage loginPage = new LoginPage();
		IncidentsPage incidentsPage = new IncidentsPage();

		try {
			gstrTCID = "116131";// Test Case Id
			gstrTO = "Verify that owner of the incident can update Participation level of the incident.";// Test
																											// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String incidentType = "AutoIncTyp1";
			String incidentName = "Auto" + System.currentTimeMillis();
			String incidentDescription = "Creating Incident for Testing";
			String incidentApplication = "Test";
			String locationName = "Justin Hospital";
			String regionName = "Anywhere, USA";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = loginPage.login(selenium, varUserName,
						varPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage
						.clickOnCreateIncidentBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.selIncidentType(selenium,
						incidentType, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.createIncident(selenium,
						incidentName, incidentDescription, incidentApplication,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.vfyCreatedIncident(selenium,
						incidentName, incidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.changeLocation(selenium,
						locationName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage
						.vfyCreatedIncidentNotPresentInOtherLocation(selenium,
								incidentName, incidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.switchBackFrmLocToRegion(
						selenium, locationName, regionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage
						.editCreatedIncidentAndProvideAccess(selenium,
								incidentName, locationName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.changeLocation(selenium,
						locationName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.vfyCreatedIncident(selenium,
						incidentName, incidentType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = loginPage.logOut(selenium);
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
			gstrTCID = "BQS-116131";
			gstrTO = "Verify that owner of the incident can update Participation level of the incident.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS116131//
	//start//testBQS116132//
/***********************************************************************************************************************
'Description	:Verify that same number of incidents are displayed on the Incident Summary dropdown and Incident List pages 
'Precondition	:
'Arguments		:None
'Returns		:None
'Date			:15-11-2013
'Author			:Manasa
************************************************************************************************************************/
	@Test
	public void testBQS116132() throws Exception {
		String strFuncResult = "";

		LoginPage loginPage = new LoginPage();
		IncidentsPage incidentsPage = new IncidentsPage();
		DashBoardPage objDashBoardPage = new DashBoardPage();
		try {
			gstrTCID = "116132";// Test Case Id
			gstrTO = "Verify that same number of incidents are displayed on the Incident Summary dropdown and Incident List pages ";// Test
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strNumOfInciInInciPge = "";
			String strNumOfInciInDashBrd = "";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = loginPage.login(selenium, varUserName,
						varPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage.navToIncidentsPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = incidentsPage
						.getTheNumOfIncidentsInIncidentPage(selenium);
				strNumOfInciInInciPge = strFuncResult;
				strFuncResult = "";
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
						.getTheNumOfIncidentsInDashbrdPage(selenium);
				strNumOfInciInDashBrd = strFuncResult;
				strFuncResult = "";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.vfyTheDashBrdAndIncidentPgeInciCnt(selenium,
								strNumOfInciInInciPge, strNumOfInciInDashBrd);
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
			gstrTCID = "BQS-116132";
			gstrTO = "Verify that same number of incidents are displayed on the Incident Summary dropdown and Incident List pages ";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS116132//
}