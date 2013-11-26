package com.qsgsoft.EMResource.features;

import java.util.Date;
import java.util.Properties;
import static org.junit.Assert.assertEquals;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.CreateUsers;
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

/*******************************************************************
' Description		:This class contains test cases from requirement
' Requirement		:Activate System notice
' Requirement Group	:Setting up Regions 
' Product		    :EMResource v3.19
' Date			    :2/June/2012
' Author		    :QSG
' ------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class ActivateSystemNotice {

	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.ActivateSystemNotice");
	static{
		BasicConfigurator.configure();
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
	Selenium selenium;
	String gstrTimeOut;

	/**************************************************************************************
		* This function is called the setup() function which is executed before every test.
		*
		* The function will take care of creating a new selenium session for every test
		*
	***************************************************************************************/

	@Before
	public void setUp() throws Exception {

		dtStartDate = new Date();
		gstrBrowserName = "IE 8";
		gstrBuild = "";

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		Paths_Properties objPaths_Properties = new Paths_Properties();
		propElementAutoItDetails = objPaths_Properties.ReadAutoit_FilePath();

		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();

		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();
	}

	/*****************************************************************************************
	    * This function is called the teardown() function which is executed after every test.
		* The function will take care of stopping the selenium session for every test and
		*  writing the execution  result of the test. 
		*
	*******************************************************************************************/

	@After
	public void tearDown() throws Exception {

		try {
			selenium.close();
		} catch (Exception e) {

		}
		selenium.stop();

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

		gdbTimeTaken = objOFC.TimeTaken(dtStartDate);// and execution time
		String FILE_PATH = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		FILE_PATH = pathProps.getProperty("Resultpath");
		Date_Time_settings dts = new Date_Time_settings();
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");

		// gstrBuild=PropEnvDetails.getProperty("Build");
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");
		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
		
	/***************************************************************
	'Description		:Verify that system notice can be activated.
	'Arguments		    :None
	'Returns		    :None
	'Date			    :6/2/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date			                    	Modified By
	'Date					                        Name
	***************************************************************/

	@Test
	public void testBQS64790() throws Exception {
		try {

			Login objLogin = new Login();
			CreateUsers objCreateUsers = new CreateUsers();
			Regions objRegions = new Regions();

			gstrTCID = "64790"; // Test Case Id
			gstrTO = " Verify that system notice can be activated.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strTitle = "Intermedix";
			String strDescriptn = "Automation";
			String strHoverText = "Maintainance";

		/*
		 * STEP : Action:Login as RegAdmin to any region and navigate to
		 * Setup>>Regions Expected Result:'Region List' screen is displayed
		 */
		
			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
			
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navRegnListPge(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on 'Manage System Notification' Expected
		 * Result:'Edit System Notification' page is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToEditSysNotification(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Enter data in mandatory fields and select 'Active'
		 * checkbox and save. Expected Result:'Region List' screen is
		 * displayed. System notice is activated. 'System Notice' link is
		 * displayed at the top right corner below Menu header.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editAndSaveNotification(selenium,
						strTitle, strDescriptn, strHoverText, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on 'System notice' Expected Result:Thick box
		 * containing description of system notice is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToSystemNotice(selenium,
						strDescriptn, strTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", gstrReason);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason =  strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-64790";
			gstrTO = "Verify that system notice can be activated.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	
	
	/***************************************************************
	'Description		:Verify that system notice can be deactivated
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :6/2/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date			                    	Modified By
	'Date					                        Name
	***************************************************************/

	@Test
	public void testBQS64794() throws Exception {
		try {

			Login objLogin = new Login();
			CreateUsers objCreateUsers = new CreateUsers();
			Regions objRegions = new Regions();

			gstrTCID = "64794"; // Test Case Id
			gstrTO = " Verify that system notice can be deactivated";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strTitle = "IntermedixSystem";
			String strDescriptn = "AutomationScript";
			String strHoverText = "MaintainanceTeam";

		/*
		 * STEP : Action:Login as RegAdmin to any region and navigate to
		 * Setup>>Regions Expected Result:'Region List' screen is displayed
		 */
				
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navRegnListPge(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on 'Manage System Notification' Expected
		 * Result:'Edit System Notification' page is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToEditSysNotification(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Enter data in mandatory fields and do not select
		 * 'Active' checkbox and save. Expected Result:'Region List' screen
		 * is displayed. System notice is activated. 'System Notice' link is
		 * displayed at the top right corner below Menu header.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editAndSaveNotification(selenium,
						strTitle, strDescriptn, strHoverText, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", gstrReason);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-64794";
			gstrTO = "Verify that system notice can be deactivated";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}	
}


