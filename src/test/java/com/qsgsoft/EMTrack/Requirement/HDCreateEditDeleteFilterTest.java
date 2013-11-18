package com.qsgsoft.EMTrack.Requirement;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMTrack.Shared.DashBoardPage;
import com.qsgsoft.EMTrack.Shared.FiltersPage;
import com.qsgsoft.EMTrack.Shared.LoginPage;
import com.qsgsoft.EMTrack.Support.Date_Time_settings;
import com.qsgsoft.EMTrack.Support.ElementId_properties;
import com.qsgsoft.EMTrack.Support.OfficeCommonFunctions;
import com.qsgsoft.EMTrack.Support.Paths_Properties;
import com.qsgsoft.EMTrack.Support.ReadData;
import com.qsgsoft.EMTrack.Support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/*************************************************************************************************
 * Description       :This class contains test cases from 'HDCreateEditDeleteFilter' requirement '
 * Requirement       :Create/Edit/Delete Filter'
 * Requirement Group :Filter Management '
 * Product           :EMTrack 3.0 ' 
 * Date              :05/11/2013 ' 
 * Author            :Rahul 
 *************************************************************************************************/
public class HDCreateEditDeleteFilterTest{
	
	// Log4j object to write log entries to the Log files
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMTrack.features.HDCreateEditDeleteFilter");

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
	
	//start//testBQS114848//
	/*************************************************************************************
	'Description	:Verify that user can create a filter
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:05/11/2013
	'Author			:Rahul
	**************************************************************************************/
	@Test
	public void testBQS114848() throws Exception {
		String strFuncResult = "";

		LoginPage objLoginEMTrack = new LoginPage();
		FiltersPage objFiltersPage = new FiltersPage();
		
		try {
			gstrTCID = "114848"; // Test Case Id
			gstrTO = "Verify that user is able to create a filter";// Test Objective													
			gstrReason = "";
			gstrResult = "FAIL";
			
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strFilterName = "Auto Filter " + System.currentTimeMillis();
			String strFilterDescription = "Testing save Filter";
			String strCriteria = "Tracking Status";
			
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginEMTrack.login(selenium, varUserName,
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
				strFuncResult = objLoginEMTrack.logOut(selenium);
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
			gstrTCID = "BQS-114848";
			gstrTO = "Verify that user is able to create a filter ";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS114848//
	
	//start//testBQS114932//
	/********************************************************************************************************
	'Description	:Verify that Filters created is displayed is Filter drop down when adding a new dashboard 
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:05/11/2013
	'Author			:Rahul
	*********************************************************************************************************/
	@Test
	public void testBQS114932() throws Exception {
		String strFuncResult = "";

		LoginPage objLoginEMTrack = new LoginPage();
		FiltersPage objFiltersPage = new FiltersPage();
		DashBoardPage objDashBoardPage = new DashBoardPage();
		
		try {
			gstrTCID = "114932"; // Test Case Id
			gstrTO = "Verify that Filters created is displayed is Filter drop down when adding a new dashboard ";// Test Objective																							
			gstrReason = "";
			gstrResult = "FAIL";
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strFilterName = "Auto Filter " + System.currentTimeMillis();
			String strFilterDescription = "Testing save Filter";
			String strCriteria = "Tracking Status";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginEMTrack.login(selenium, varUserName,
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
				strFuncResult = objDashBoardPage
						.navigateToDashboardPage(selenium);
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
				strFuncResult = objDashBoardPage
						.vfyCreatedFilterInClientListFilterDropDown(selenium,
								strFilterName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginEMTrack.logOut(selenium);
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
			gstrTCID = "BQS-114932";
			gstrTO = "Verify that Filters created is displayed is Filter drop down when adding a new dashboard ";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS114932//
	
	//start//testBQS114986//
	/*************************************************************************************
	'Description	:Verify that filter can be created using multiple search criterion. 
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:05/11/2013
	'Author			:Rahul
	**************************************************************************************/
	@Test
	public void testBQS114986() throws Exception {
		String strFuncResult = "";

		LoginPage objLoginEMTrack = new LoginPage();
		FiltersPage objFiltersPage = new FiltersPage();
		DashBoardPage objDashBoardPage = new DashBoardPage();
		
		try {
			gstrTCID = "114986"; // Test Case Id
			gstrTO = "Verify that filter can be created using multiple search criterion.";// Test Objective																
			gstrReason = "";
			gstrResult = "FAIL";
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strFilterName = "Auto Filter "+ System.currentTimeMillis();
			String strFilterDescription = "Testing save Filter";
			String strCriteria = "Tracking Status";
			String strCriteria1 = "Gender";
			String strCriteria2 = "Triage Category";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginEMTrack.login(selenium, varUserName,
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
				strFuncResult = objFiltersPage
						.clkTrackingStatusBtnAndSelAtLocationCheckBox(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage.selSearchCriteria(selenium,
						strCriteria1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage
						.clkGenderBtnAndSelMaleCheckBox(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage.selSearchCriteria(selenium,
						strCriteria2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage
						.clkTriageCategoryBtnAndSelRedCheckBox(selenium);
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
				strFuncResult = objDashBoardPage
						.navigateToDashboardPage(selenium);
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
				strFuncResult = objLoginEMTrack.logOut(selenium);
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
			gstrTCID = "BQS-114986";
			gstrTO = "Verify that filter can be created using multiple search criterion.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS114986//
	
	//start//testBQS115082//
	/**********************************************************************************************
	'Description	:Verify that filter cannot be created without filling all the mandatory fields 
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:05/11/2013
	'Author			:Rahul
	**********************************************************************************************/
	@Test
	public void testBQS115082() throws Exception {
		String strFuncResult = "";

		LoginPage objLoginEMTrack = new LoginPage();
		FiltersPage objFiltersPage = new FiltersPage();
		
		try {
			gstrTCID = "115082"; // Test Case Id
			gstrTO = "Verify that filter cannot be created without filling all the mandatory fields .";// Test Objective																						
			gstrReason = "";
			gstrResult = "FAIL";
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strCriteria = "Tracking Status";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginEMTrack.login(selenium, varUserName,
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
				strFuncResult = objFiltersPage.clkSaveAsWindowSaveBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginEMTrack.logOut(selenium);
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
			gstrTCID = "BQS-115082";
			gstrTO = "Verify that filter cannot be created without filling all the mandatory fields .";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS115082//
	
	//start//testBQS115825//
	/*******************************************************************************************************************
	'Description	:Edit the filter name and verify that edited name is reflected in client summary >> filter drop down  
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:05/11/2013
	'Author			:Rahul
	********************************************************************************************************************/
	@Test
	public void testBQS115825() throws Exception {
		String strFuncResult = "";

		LoginPage objLoginEMTrack = new LoginPage();
		FiltersPage objFiltersPage = new FiltersPage();
		DashBoardPage objDashBoardPage = new DashBoardPage();
		
		try {
			gstrTCID = "115825"; // Test Case Id
			gstrTO = "Edit the filter name and verify that edited name is reflected in client summary >> filter drop down";//Test  Objective																												
			gstrReason = "";
			gstrResult = "FAIL";
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strFilterName = "Auto Filter " + System.currentTimeMillis();
			String strFilterDescription = "Testing save Filter";
			String strCriteria = "Tracking Status";
			String groupByOption = "Current Location";
			String summaryTypeOption = "Bar Chart";
			String strDashboardName = "Auto Dashboard "
					+ System.currentTimeMillis();
			String strDashBoardDescription = "Testing save DashBoard";
			String strFilterName1 = "Edited Filter "
					+ System.currentTimeMillis();
			String strFilterDescription1 = "Testing Edited Filter";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginEMTrack.login(selenium, varUserName,
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
				strFuncResult = objDashBoardPage
						.navigateToDashboardPage(selenium);
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
				strFuncResult = objDashBoardPage.selFilterInClientSummaryGadget(
						selenium, strFilterName, groupByOption,
						summaryTypeOption);
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
				strFuncResult = objDashBoardPage.vfyClientSummaryGadgetTitle(
						selenium, strFilterName, groupByOption);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selectSaveDashboardOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.saveDashBoard(selenium,
						strDashboardName, strDashBoardDescription);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.verifyCurrentDashBoardInput(
						selenium, strDashboardName);
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
				strFuncResult = objFiltersPage.clickCreatedFilter(selenium,
						strFilterName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage.clickSaveButton(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage.saveFilter(selenium,
						strFilterName1, strFilterDescription1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage.verifyCreatedFilter(selenium,
						strFilterName1);
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
				strFuncResult = objDashBoardPage
						.vfyFilterNameInClientListGadgetTitle(selenium,
								strFilterName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.clkClientListConfigureOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.vfyCreatedFilterInClientListFilterDropDown(selenium,
								strFilterName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.vfyClientSummaryGadgetTitle(
						selenium, strFilterName1, groupByOption);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.clkClientSummaryConfigureOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.vfyCreatedFilterInClientSummaryFilterDropDown(
								selenium, strFilterName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginEMTrack.logOut(selenium);
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
			gstrTCID = "BQS-115825";
			gstrTO = "Edit the filter name and verify that edited name is reflected in client summary >> filter drop down";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS115825//
	
	//start//testBQS127838//
	/*************************************************************************************
	'Description	:Verify that user can remove the added criterion. 
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:05/11/2013
	'Author			:Rahul
	**************************************************************************************/
	@Test
	public void testBQS127838() throws Exception {
		String strFuncResult = "";

		LoginPage objLoginEMTrack = new LoginPage();
		FiltersPage objFiltersPage = new FiltersPage();
		DashBoardPage objDashBoardPage = new DashBoardPage();
		
		try {
			gstrTCID = "127838"; // Test Case Id
			gstrTO = "Verify that user can remove the added criterion.";// Test  Objective													
			gstrReason = "";
			gstrResult = "FAIL";
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strFilterName = "Auto Filter " + System.currentTimeMillis();
			String strFilterDescription = "Testing save Filter";
			String strCriteria = "Tracking Status";
			String strCriteria1 = "Gender";
			String strCriteria2 = "Triage Category";
			String strDashboardName = "Auto Dashboard "
					+ System.currentTimeMillis();
			String strDashBoardDescription = "Testing save DashBoard";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginEMTrack.login(selenium, varUserName,
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
				strFuncResult = objFiltersPage
						.clkTrackingStatusBtnAndSelAtLocationCheckBox(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage.selSearchCriteria(selenium,
						strCriteria2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage
						.clkTriageCategoryBtnAndSelRedCheckBox(selenium);
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
				strFuncResult = objDashBoardPage
						.navigateToDashboardPage(selenium);
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
						.selectSaveDashboardOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.saveDashBoard(selenium,
						strDashboardName, strDashBoardDescription);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.verifyCurrentDashBoardInput(
						selenium, strDashboardName);
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
				strFuncResult = objFiltersPage.clickCreatedFilter(selenium,
						strFilterName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage
						.removeTriageCategoryButton(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage.selSearchCriteria(selenium,
						strCriteria1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage
						.clkGenderBtnAndSelMaleCheckBox(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objFiltersPage.clickSaveButton(selenium);
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
				strFuncResult = objDashBoardPage
						.navigateToDashboardPage(selenium);
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
				strFuncResult = objLoginEMTrack.logOut(selenium);
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
			gstrTCID = "BQS-127838";
			gstrTO = "Verify that user can remove the added criterion.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS127838//
	
	//start//testBQS127773//
	/***************************************************************
	'Description	:Verify that user can delete the filter.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:05/11/2013
	'Author			:Rahul
	***************************************************************/
	@Test
	public void testBQS127773() throws Exception {
		String strFuncResult = "";

		LoginPage objLoginEMTrack = new LoginPage();
		FiltersPage objFiltersPage = new FiltersPage();
		DashBoardPage objDashBoardPage = new DashBoardPage();
		
		try {
			gstrTCID = "127773"; // Test Case Id
			gstrTO = "Verify that user can delete the filter.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strFilterName = "Auto Filter " + System.currentTimeMillis();
			String strFilterDescription = "Testing save Filter";
			String strCriteria = "Tracking Status";
			String strDashboardName = "Auto Dashboard "
					+ System.currentTimeMillis();
			String strDashBoardDescription = "Testing save DashBoard";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginEMTrack.login(selenium, varUserName,
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
				strFuncResult = objDashBoardPage
						.navigateToDashboardPage(selenium);
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
						.selectSaveDashboardOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.saveDashBoard(selenium,
						strDashboardName, strDashBoardDescription);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.verifyCurrentDashBoardInput(
						selenium, strDashboardName);
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
				strFuncResult = objFiltersPage.deleteFilter(selenium,
						strFilterName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginEMTrack.logOut(selenium);
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
			gstrTCID = "BQS-127773";
			gstrTO = "Verify that user can delete the filter .";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// start//testBQS127773//
	
	//start//testBQS127769//
	/**************************************************************************************************
	'Description	:Verify that deleted filter is not available for selection in client summary gadget.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:05/11/2013
	'Author			:Rahul
	***************************************************************************************************/
	@Test
	public void testBQS127769() throws Exception {
		String strFuncResult = "";

		LoginPage objLoginEMTrack = new LoginPage();
		FiltersPage objFiltersPage = new FiltersPage();
		DashBoardPage objDashBoardPage = new DashBoardPage();
		
		try {
			gstrTCID = "127769"; // Test Case Id
			gstrTO = "Verify that deleted filter is not available for selection in client summary gadget.";// Test Objective																								
			gstrReason = "";
			gstrResult = "FAIL";
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strFilterName = "Auto Filter " + System.currentTimeMillis();
			String strFilterDescription = "Testing save Filter";
			String strCriteria = "Tracking Status";
			String groupByOption = "Current Location";
			String summaryTypeOption = "Bar Chart";
			String strDashboardName = "Auto Dashboard "
					+ System.currentTimeMillis();
			String strDashBoardDescription = "Testing save DashBoard";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginEMTrack.login(selenium, varUserName,
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
				strFuncResult = objDashBoardPage
						.navigateToDashboardPage(selenium);
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
				strFuncResult = objDashBoardPage.selFilterInClientSummaryGadget(
						selenium, strFilterName, groupByOption,
						summaryTypeOption);
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
				strFuncResult = objDashBoardPage.vfyClientSummaryGadgetTitle(
						selenium, strFilterName, groupByOption);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selectSaveDashboardOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.saveDashBoard(selenium,
						strDashboardName, strDashBoardDescription);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.verifyCurrentDashBoardInput(
						selenium, strDashboardName);
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
				strFuncResult = objFiltersPage.deleteFilter(selenium,
						strFilterName);
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
				strFuncResult = objDashBoardPage
						.vfyDeletedFilterNotPresentInClientListFilterDropDown(
								selenium, strFilterName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.vfyDeletedFilterNotPresentInClientSummaryFilterDropDown(
								selenium, strFilterName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLoginEMTrack.logOut(selenium);
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
			gstrTCID = "BQS-127769";
			gstrTO = "Verify that deleted filter is not available for selection in client summary gadget.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// start//testBQS127769//
}