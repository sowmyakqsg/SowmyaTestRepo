package com.qsgsoft.EMResource.features;

import java.util.Date;
import java.util.Properties;
import static org.junit.Assert.assertEquals;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**********************************************************************
' Description		:This class contains FTS test cases from   requirement
' Requirement		:Activate System notice
' Requirement Group	:Setting up Regions 
� Product		    :EMResource v3.19
' Date			    :2/June/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/
public class FTSActivateSystemNotice {
	
		
		Date dtStartDate;
		ReadData rdExcel;
		static Logger log4j = Logger
				.getLogger("com.qsgsoft.EMResource.features.FTSActivateSystemNotice");
		static {
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



	/****************************************************************************************************************
		* This function is called the setup() function which is executed before every test.
		*
		* The function will take care of creating a new selenium session for every test
		*
	****************************************************************************************************************/

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
					4444, propEnvDetails.getProperty("Browser"), propEnvDetails
							.getProperty("urlEU"));
			

			selenium.start();
			selenium.windowMaximize();

			objOFC = new OfficeCommonFunctions();
			rdExcel = new ReadData();

		}


	/****************************************************************************************************************	*
	    * This function is called the teardown() function which is executed after every test.
		* The function will take care of stopping the selenium session for every test and writing the execution
		* result of the test. 
		*
	****************************************************************************************************************/

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
			gstrReason=gstrReason.replaceAll("'", " ");
			objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
					gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
					gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
		}
	
	/***************************************************************
	'Description		:Verify that System Notice cannot be activated without entering mandatory data.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:6/27/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS66674() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Regions objRegions = new Regions();
		try {
			gstrTCID = "66674"; // Test Case Id
			gstrTO = " Verify that System Notice cannot be activated without entering mandatory data.";// Test
																										// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			/*
			 * STEP : Action:Login as RegAdmin to any region and navigate to
			 * Setup>>Regions Expected Result:'Region List' screen is displayed
			 */
			// 395067

			try {
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Manage System Notification' Expected
			 * Result:'Edit System Notification' page is displayed.
			 */
			// 395068

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToEditSysNotification(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Do not provide data in the mandatory fields, click
			 * on save. Expected Result:Appropriate error message 'The following
			 * errors occurred on this page: A title is required. A description
			 * is required. Hovertext is required.' is displayed. User is
			 * retained on the 'Edit System Notification' page.
			 */
			// 395069

			try {
				assertEquals("", strFuncResult);
				String strTitle = "";
				String strDescriptn = "";
				String strHoverText = "";
				strFuncResult = objRegions.fillFieldsOfManagSyseNotfctn(
						selenium, strTitle, strDescriptn, strHoverText, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.checkErrorMsgInSysNotPage(selenium);
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
			gstrTCID = "66674";
			gstrTO = "Verify that System Notice cannot be activated without entering mandatory data.";
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
	/***************************************************************
	'Description		:Click on 'System Notice' alert link and verify that the the thick box
	                     displays title and description correctly.
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :6/28/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS66682() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Regions objRegions=new Regions();
		try{	

			gstrTCID = "66682";	//Test Case Id	
			gstrTO = " Click on 'System Notice' alert link and verify that the the thick box displays title and description correctly.";//Test Objective
			gstrReason = "";
			gstrResult = "FAIL";	

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strTitle="Intermedix";
			String strDescriptn="Automation";
			String strHoverText="Maintainance";


			/*
			 * STEP :
		  Action:Login as RegAdmin to any region and navigate to Setup>>Regions
		  Expected Result:'Region List' screen is displayed
			 */
			//395141

			try {
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult =objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP :
		  Action:Click on 'Manage System Notification'
		  Expected Result:'Edit System Notification' page is displayed.
			 */
			//395142
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToEditSysNotification(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP :
		  Action:Enter data in mandatory fields and select 'Active' checkbox and save.
		  Expected Result:'Region List' screen is displayed.
	      System notice is activated.
	      'System Notice' link is displayed at the top right corner below Menu header.
			 */
			//395143

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editAndSaveNotification(selenium, strTitle, strDescriptn, strHoverText, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP :
		  Action:Click on 'System notice'.
		  Expected Result:Thick box containing description of system notice is displayed. 
	      Title and Description are displayed with the correct data provided at the time of activation of 'System Notice'.
			 */
			//395145

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToSystemNotice(selenium, strDescriptn,strTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try{
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			}
			catch (AssertionError Ae)
			{
				gstrResult = "FAIL";
				gstrReason = gstrReason+" "+strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "66682";
			gstrTO = "Click on 'System Notice' alert link and verify that the the thick box displays title and description correctly.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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

	/***************************************************************
	'Description		:Cancel the process of saving 'System Notice' and verify that the changes are not saved.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:6/28/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS66686() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Regions objRegions = new Regions();
		try {

			gstrTCID = "66686"; // Test Case Id
			gstrTO = " Cancel the process of saving 'System Notice' and verify that the changes are not saved.";// Test
																												// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strTitle = "Intermedix";
			String strDescriptn = "Automation";
			String strHoverText = "Maintainance";

			/*
			 * STEP : Action:Precondition: 'System Notice' is activated for
			 * region RG1. Expected Result:No Expected Result
			 */
			// 395157

			/*
			 * STEP : Action:Login as RegAdmin to region RG1 and navigate to
			 * Setup>>Regions. Expected Result:'Region List' screen is
			 * displayed.
			 */
			// 395158

			try {
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToEditSysNotification(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editAndSaveNotification(selenium,
						strTitle, strDescriptn, strHoverText, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Manage System Notification' Expected
			 * Result:'Edit System Notification' page is displayed.
			 */
			// 395159

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToEditSysNotification(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Edit data in all the fields and click on 'Cancel'.
			 * Expected Result:'Region List' screen is displayed.
			 */
			// 395172

			try {
				assertEquals("", strFuncResult);
				strTitle = "IntermedixSystem";
				strDescriptn = "AutomationTeam";
				strHoverText = "MaintainanceSystem";
				strFuncResult = objRegions.editAndCancelNotification(selenium,
						strTitle, strDescriptn, strHoverText, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Manage System Notification'. Expected
			 * Result:Changes done are not retained. Previously set data are
			 * retained on 'Edit System Notification'.
			 */
			// 395173

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToEditSysNotification(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strTitle = "IntermedixSystem";
				strDescriptn = "AutomationTeam";
				strHoverText = "MaintainanceSystem";
				strFuncResult = objRegions.verifyFieldsOfNotification(selenium,
						strTitle, strDescriptn, strHoverText, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strTitle = "Intermedix";
				strDescriptn = "Automation";
				strHoverText = "Maintainance";
				strFuncResult = objRegions.verifyFieldsOfNotification(selenium,
						strTitle, strDescriptn, strHoverText, true, true);
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
			gstrTCID = "66686";
			gstrTO = "Cancel the process of saving 'System Notice' and verify that the changes are not saved.";
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

	/***************************************************************
	'Description		:Activate System notice and verify that:
	                     1. 'System notice' alert is displayed at the top right corner of the screen.
	                     2. 'System notice' alert is displayed for users other than RegAdmin.
	                     3. 'System notice' alert is displayed for users in all the regions.
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :6/28/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS66675() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Regions objRegions = new Regions();
		CreateUsers objCreateUsers = new CreateUsers();
		try {

			gstrTCID = "66675"; // Test Case Id
			gstrTO = "Activate System notice and verify that:"
					+ "1. 'System notice' alert is displayed at the top right corner of the screen."
					+ "2. 'System notice' alert is displayed for users other than RegAdmin."
					+ " 3. 'System notice' alert is displayed for users in all the regions.";
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Region data
			String strRegionName = "Region" + System.currentTimeMillis();
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "FN";
			String strContLN = "LN";
			String strEmailFrequency = "Never";
			String strAlertFrequency = "15";
			String strRegionValues[] = new String[2];
			// USER
			String strUserName = "Rgnusr1" + System.currentTimeMillis();
			String strUserName1 = "Rgnusr2" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// Notice
			String strTitle = "IntermedixSystem";
			String strDescriptn = "AutomationTeam";
			String strHoverText = "MaintainanceGroup";
			
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			/*
			 * STEP : Action:Preconditions: 1. User U1 is created in region RG1.
			 * 2. User U2 is created in the region RG2. Expected Result:No
			 * Expected Result
			 */
			// 395119

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.fillAllRegnFields(selenium,
						strRegionName, strTimeZone, strContFN, strContLN, "",
						"", "", "", "", "", strEmailFrequency,
						strAlertFrequency);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.saveAndVerifyRegion(selenium,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(selenium,
						strRegionName);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(selenium,
						strRegn);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as RegAdmin to region RG1 and navigate to
			 * Setup>>Regions Expected Result:'Region List' screen is displayed
			 */
			// 395090

			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Manage System Notification' Expected
			 * Result:'Edit System Notification' page is displayed.
			 */
			// 395091
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
			// 395092

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
			// 395094

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToSystemNotice(selenium,
						strDescriptn, strTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user U1 (user created in region RG1).
			 * Expected Result:'Region Default' screen is displayed. 'System
			 * Notice' link is displayed at the top right corner below Menu
			 * header.
			 */
			// 395129

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.checkForSystemNoticeLink(selenium,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'System notice' Expected Result:Thick box
			 * containing description of system notice is displayed.
			 */
			// 395130

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToSystemNotice(selenium,
						strDescriptn, strTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user U2 (user created in region RG2).
			 * Expected Result:'Region Default' screen is displayed. 'System
			 * Notice' link is displayed at the top right corner below Menu
			 * header.
			 */
			// 395131

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.checkForSystemNoticeLink(selenium,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'System notice'. Expected Result:Thick box
			 * containing description of system notice is displayed.
			 */
			// 395132

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToSystemNotice(selenium,
						strDescriptn, strTitle);
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
			gstrTCID = "66675";
			gstrTO = "Activate System notice and verify that:"
					+ "1. 'System notice' alert is displayed at the top right corner of the screen."
					+ "2. 'System notice' alert is displayed for users other than RegAdmin."
					+ " 3. 'System notice' alert is displayed for users in all the regions.";
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

}


