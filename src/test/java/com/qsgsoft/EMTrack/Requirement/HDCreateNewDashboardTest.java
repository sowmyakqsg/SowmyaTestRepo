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
import com.qsgsoft.EMTrack.Shared.IncidentsPage;
import com.qsgsoft.EMTrack.Shared.LoginPage;
import com.qsgsoft.EMTrack.Support.Date_Time_settings;
import com.qsgsoft.EMTrack.Support.ElementId_properties;
import com.qsgsoft.EMTrack.Support.OfficeCommonFunctions;
import com.qsgsoft.EMTrack.Support.Paths_Properties;
import com.qsgsoft.EMTrack.Support.ReadData;
import com.qsgsoft.EMTrack.Support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/****************************************************************************************
 * Description       :This class contains test cases from 'HDCreateNewDashboard' requirement '
 * Requirement       :Create New Dashboard '
 * Requirement Group :Dashboard Managment '
 * Product           :EMTrack 3.0 ' 
 * Date              :05/11/2013 ' 
 * Author            :Rahul 
 ****************************************************************************************/
public class HDCreateNewDashboardTest {
	
	// Log4j object to write log entries to the Log files
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMTrack.features.HDCreateNewDashboard");

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

	//start//testBQS114393//
	/*************************************************************************************
	'Description	:Verify that user can create a new Dashboard 
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:05/11/2013
	'Author			:Rahul
	**************************************************************************************/
	@Test
	public void testBQS114393() throws Exception {
		
		String strFuncResult = "";

		LoginPage objLoginEMTrack = new LoginPage();
		FiltersPage objFiltersPage = new FiltersPage();
		DashBoardPage objDashBoardPage = new DashBoardPage();
		
		try {
			gstrTCID = "114393"; // Test Case Id
			gstrTO = "Verify that user can create a new Dashboard ";// Test  Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strFilterName = "Auto Filter " + System.currentTimeMillis();
			String strFilterDescription = "Testing save Filter";
			String strCriteria = "Tracking Status";
			String strDashboardName = "Auto Dashboard "
					+ System.currentTimeMillis();
			String strDashBoardDescription = "Testing save DashBoard";
			
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
			gstrTCID = "BQS-114393";
			gstrTO = "Verify that user can create a new Dashboard";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS114393//
	
	//start//testBQS115037//
	/***********************************************************************************************************
	'Description	:Verify that Dashboard can be created by selecting All the Gadgets in the 'Gadget Directory' 
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:05/11/2013
	'Author			:Rahul
	************************************************************************************************************/
	@Test
	public void testBQS115037() throws Exception {
		
		String strFuncResult = "";

		LoginPage objLoginEMTrack = new LoginPage();
		FiltersPage objFiltersPage = new FiltersPage();
		DashBoardPage objDashBoardPage = new DashBoardPage();
		
		try {
			gstrTCID = "115037"; // Test Case Id
			gstrTO = "Verify that Dashboard can be created by selecting All the Gadgets in the 'Gadget Directory' ";// Test  Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strFilterName = "Auto Filter " + System.currentTimeMillis();
			String strFilterDescription = "Testing save Filter";
			String strCriteria = "Tracking Status";
			String groupByOption = "Current Location";
			String summaryTypeOption = "Bar Chart";
			String locationOption = "Alpena Regional Medical Center";
			String strDashboardName = "Auto Dashboard "
					+ System.currentTimeMillis();
			String strDashBoardDescription = "Testing save DashBoard";
			
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
						.selectAddGadgetOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selectActiveInciForProviderOptionInGadgetDirectory(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selLocationFrActiveInciFrProviderGadget(selenium,
								locationOption);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.clkActiveInciFrProviderApplyBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.vfyActiveInciFrProviderGadgetTitle(selenium,
								locationOption);
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
						.selectActiveIncidentsInRegionOptionInGadgetDirectory(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.verifyActiveIncidentsInRegionGadgetTitle(selenium);
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
			gstrTCID = "BQS-115037";
			gstrTO = "Verify that Dashboard can be created by selecting All the Gadgets in the 'Gadget Directory' ";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS115037//
	
	//start//testBQS115047//
	/************************************************************************************
	'Description	:Verify that user can edit dashboard gadget using edit option 
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:06/11/2013
	'Author			:Rahul
	*************************************************************************************/
	@Test
	public void testBQS115047() throws Exception {
		
		String strFuncResult = "";

		LoginPage objLoginEMTrack = new LoginPage();
		FiltersPage objFiltersPage = new FiltersPage();
		DashBoardPage objDashBoardPage = new DashBoardPage();
		
		try {
			gstrTCID = "115047"; // Test Case Id
			gstrTO = "Verify that user can edit dashboard gadget using edit option";// Test Objective																	
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String strFilterName = "Auto Filter " + System.currentTimeMillis();
			String strFilterDescription = "Testing save Filter";
			String strCriteria = "Tracking Status";
			String groupByOption = "Current Location";
			String summaryTypeOption = "Bar Chart";
			String locationOption = "Alpena Regional Medical Center";
			String strDashboardName = "Auto Dashboard "
					+ System.currentTimeMillis();
			String strDashBoardDescription = "Testing save DashBoard";
			String strCriteria2 = "Triage Category";
			String strFilterName1 = "Auto new Filter"
					+ System.currentTimeMillis();
			String strFilterDescription1 = "Testing new save Filter";
			String groupByOption1 = "Triage Category";
			String summaryTypeOption1 = "Pie Chart";
			String locationOption1 = "Justin Hospital";
			String strDashboardName1 = "Auto Edited Dashboard "
					+ System.currentTimeMillis();
			String strDashBoardDescription1 = "Testing Edited DashBoard";
			
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
						.selectAddGadgetOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selectActiveInciForProviderOptionInGadgetDirectory(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selLocationFrActiveInciFrProviderGadget(selenium,
								locationOption);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.clkActiveInciFrProviderApplyBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.vfyActiveInciFrProviderGadgetTitle(selenium,
								locationOption);
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
						.selectActiveIncidentsInRegionOptionInGadgetDirectory(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.verifyActiveIncidentsInRegionGadgetTitle(selenium);
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
				strFuncResult = objFiltersPage.selSearchCriteria(selenium,
						strCriteria2);
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
						.clkClientListConfigureOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.selFilterInClientListGadget(
						selenium, strFilterName1);
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
								strFilterName1);
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
				strFuncResult = objDashBoardPage.selFilterInClientSummaryGadget(
						selenium, strFilterName1, groupByOption1,
						summaryTypeOption1);
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
						selenium, strFilterName1, groupByOption1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.clkActiveInciFrProviderConfigureOption(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.selLocationFrActiveInciFrProviderGadget(selenium,
								locationOption1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.clkActiveInciFrProviderApplyBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage
						.vfyActiveInciFrProviderGadgetTitle(selenium,
								locationOption1);
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
						strDashboardName1, strDashBoardDescription1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDashBoardPage.verifyCurrentDashBoardInput(
						selenium, strDashboardName1);
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
			gstrTCID = "BQS-115047";
			gstrTO = "Verify that user can edit dashboard gadget using edit option";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS115047//
	
	//start//testBQS115109//
	/***********************************************************************************************************************
	'Description	:Create a new incident on EMTrack Web and verify that created incident is displayed Incident mode drop 
	                 down when 'Incident Mode' button is selected' 
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:07-11-2013
	'Author			:Rahul
	************************************************************************************************************************/
	@Test
	public void testBQS115109() throws Exception {

		LoginPage loginPage = new LoginPage();
		IncidentsPage incidentsPage = new IncidentsPage();
		DashBoardPage dashBoardPage = new DashBoardPage();

		String strFuncResult = "";
		
		try {
			gstrTCID = "115109";// Test Case Id
			gstrTO = "Create a new incident on EMTrack Web and verify that created incident is displayed Incident mode " +
					 " drop down when 'Incident Mode' button is selected' .";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			String varUserName = objrdExcel.ReadData("Login", 2, 1);
			String varPassword = objrdExcel.ReadData("Login", 2, 2);
			String incidentType = "Special Event";
			String incidentName = "Auto Incident "+System.currentTimeMillis();
			String incidentDescription = "Creating Incident for Testing";
			String incidentApplication = "Test";

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
				strFuncResult = dashBoardPage.navigateToDashboardPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage.clkOnIncidentModeBtn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = dashBoardPage.vfyCreatedIncidentPresentInInciModeDrpDwn(selenium, incidentName);
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
			gstrTCID = "BQS-115109";
			gstrTO = "Create a new incident on EMTrack Web and verify that created incident is displayed Incident mode drop down when 'Incident Mode' button is selected'.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	// end//testBQS115109//
}