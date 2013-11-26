package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
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

/**********************************************************************
' Description		:This class contains test cases from requirement
' Requirement		:Multi region event
' Requirement Group	:Creating & managing Events  
� Product		    :EMResource v3.19
' Date			    :4/June/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class MultiRegionEvent {

	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.MultiRegionEvent");
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
	Selenium selenium,seleniumFirefox,seleniumPrecondition;
	String gstrTimeOut;

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

		seleniumFirefox = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444,propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
						.getProperty("urlEU"));
	
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
		

		selenium.start();
		selenium.windowMaximize();
		
		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try{
			selenium.close();
		}catch(Exception Ae){
			
		}
		try{
			seleniumFirefox.close();
		}catch(Exception Ae){
			
		}
		
		try{
			seleniumPrecondition.close();
		}catch(Exception Ae){
			
		}
		
		
		selenium.stop();
		seleniumFirefox.stop();
		seleniumPrecondition.stop();
		

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
	'Description		:Verify that a file can be attached to a multi region event.
	'Precondition		:1. User U1 has access to regions A and B.
						 2. User has the 'Maintain Events' right in both regions A and B
						 3. Multi region event template ET1 is created in region A.
						 4.Multi region event template ET2 is created in region B. 
	'Arguments		:None
	'Returns		:None
	'Date			:6/12/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS68598() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";


		Login objLogin = new Login();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Regions objRegions = new Regions();
		General objMail = new General();

		try {
			gstrTCID = "68598"; // Test Case Id
			gstrTO = " Verify that a file can be attached to a multi region event.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strRegionName = rdExcel.readData("Regions", 3, 5);

			String strTestData[] = new String[10];

			String strRegionValue[] = new String[2];
			strRegionValue[0] = "";
			strRegionValue[1] = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			String strTempName1 = "AutoET_1" + strTimeText;
			String strTempName2 = "AutoET_2" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			String strETValues[] = new String[2];

			String strEveName1 = "Eve_1" + strTimeText;
			String strEveName2 = "Eve_2" + strTimeText;
			String strEveName3 = "Eve_3" + strTimeText;

			String strEventValue[] = new String[3];
			String strEventVal = "";
			strEventValue[0] = "";
			strEventValue[1] = "";
			strEventValue[2] = "";

			/* 1. User U1 has access to regions A and B. */

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRegionValue[0] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegn);
				if (!strRegionValue[0].equals("")) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch region value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strRegionValue[1] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegionName);
				if (!strRegionValue[1].equals("")) {
					strFuncResult = "";

				} else {
					strFuncResult = "Faile to fetch region value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegionVal[] = { strRegionValue[1] };
				strFuncResult = objRegions.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName_1, strRegionVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/* 2. User has the 'Maintain Events' right in both regions A and B 
*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			/* * try { assertEquals("", strFuncResult); String[] strRegnName =
			 * {"css=input[name='userRegionID'][value='" + strRegionValue[0] +
			 * "']" }; strFuncResult =
			 * objRegions.editMultiRegnEventRites(selenium, strRegnName); }
			 * catch (AssertionError Ae) { gstrReason = strFuncResult;
			 * 
			 * }*/
			 

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

		/*	 3. Multi region event template ET1 is created in region B. */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = {};
				String[] strStatusTypeval = {};
				strFuncResult = objEventSetup.filMndfldsEveTempAsMultiEveTemp(
						seleniumPrecondition, strTempName2, strTempDef, strEveColor, true,
						true, strResTypeVal, strStatusTypeval, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValues[1] = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName2);
				if (strETValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String[] strRegnName = {
						"css=input[name='userRegionID'][value='"
								+ strRegionValue[1] + "']",
						"css=input[name='userRegionID'][value='"
								+ strRegionValue[0] + "']" };
				strFuncResult = objRegions.editMultiRegnEventRites(seleniumPrecondition,
						strRegnName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
		

			/* 4.Multi region event template ET2 is created in region A. 
*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objEventSetup.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = {};
				String[] strStatusTypeval = {};
				strFuncResult = objEventSetup.filMndfldsEveTempAsMultiEveTemp(
						seleniumPrecondition, strTempName1, strTempDef, strEveColor, true,
						true, strResTypeVal, strStatusTypeval, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValues[0] = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName1);
				if (strETValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/* * 2 Login as user U1 to region A, navigate to Event>>Event
			 * Management, click on 'Create new multi region event' 'Create
			 * Event' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
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
				blnLogin = true;
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*	
			 * 3 Create an event EVE1 selecting templates ET1 from region A and
			 * ET2 from region B and attaching an html file. 'Event Management'
			 * screen is displayed.
			 
*/
			try {
				assertEquals("", strFuncResult);
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadTxtFile_OpenPath");

				String[] strEvntTemplateNames = {
						"css=input[name='region-" + strRegionValue[0]
								+ "'][value='" + strETValues[0] + "']",
						"css=input[name='region-" + strRegionValue[1]
								+ "'][value='" + strETValues[1] + "']" };
				strFuncResult = objEventSetup
						.createMultiRegnEventMandatoryByAttachingFile(selenium,
								strEveName1, strTempDef, strEvntTemplateNames,
								strAutoFilePath, strAutoFileName,
								strUploadFilePath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertTrue(selenium.isElementPresent("//a[text()='"
							+ strEveName1 + "']"));
					log4j.info("Multi Event " + strEveName1
							+ "is displayed in the banner");
				} catch (AssertionError Ae) {
					log4j.info("Multi Event is NOT displayed in the banner");
					strFuncResult = "Multi Event is NOT displayed in the banner";
					gstrReason = "Multi Event is NOT displayed in the banner"
							+ Ae;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strEventVal = objEventSetup.fetchEventValueInEventList(
						selenium, strEveName1);
				if (strEventVal.compareTo("") != 0) {
					strFuncResult = "";
					strEventValue[0] = strEventVal;
				} else {
					strFuncResult = "Failed to fetch Event Value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/* * 4 Create an event EVE2 selecting templates ET1 from region A and
			 * ET2 from region B and attaching a text file. 'Event Management'
			 * screen is displayed.*/
			 
			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadHtmlFile_OpenPath");

				String[] strEvntTemplateNames = {
						"css=input[name='region-" + strRegionValue[0]
								+ "'][value='" + strETValues[0] + "']",
						"css=input[name='region-" + strRegionValue[1]
								+ "'][value='" + strETValues[1] + "']" };
				strFuncResult = objEventSetup
						.createMultiRegnEventMandatoryByAttachingFile(selenium,
								strEveName2, strTempDef, strEvntTemplateNames,
								strAutoFilePath, strAutoFileName,
								strUploadFilePath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertTrue(selenium.isElementPresent("//a[text()='"
							+ strEveName2 + "']"));
					log4j.info("Multi Event " + strEveName2
							+ "is displayed in the banner");
				} catch (AssertionError Ae) {
					log4j.info("Multi Event is NOT displayed in the banner");
					strFuncResult = "Multi Event is NOT displayed in the banner";
					gstrReason = "Multi Event is NOT displayed in the banner"
							+ Ae;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strEventVal = objEventSetup.fetchEventValueInEventList(
						selenium, strEveName2);
				if (strEventVal.compareTo("") != 0) {
					strFuncResult = "";
					strEventValue[1] = strEventVal;
				} else {
					strFuncResult = "Failed to fetch Event Value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/* * 5 Create an event EVE3 selecting templates ET1 from region A and
			 * ET2 from region B and attaching a text file. 'Event Management'
			 * screen is displayed.
			 
*/
			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadPdfFile_OpenPath");
				String[] strEvntTemplateNames = {
						"css=input[name='region-" + strRegionValue[0]
								+ "'][value='" + strETValues[0] + "']",
						"css=input[name='region-" + strRegionValue[1]
								+ "'][value='" + strETValues[1] + "']" };
				strFuncResult = objEventSetup
						.createMultiRegnEventMandatoryByAttachingFile(selenium,
								strEveName3, strTempDef, strEvntTemplateNames,
								strAutoFilePath, strAutoFileName,
								strUploadFilePath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertTrue(selenium.isElementPresent("//a[text()='"
							+ strEveName3 + "']"));
					log4j.info("Multi Event " + strEveName3
							+ "is displayed in the banner");
				} catch (AssertionError Ae) {
					log4j.info("Multi Event is NOT displayed in the banner");
					strFuncResult = "Multi Event is NOT displayed in the banner";
					gstrReason = "Multi Event is NOT displayed in the banner"
							+ Ae;

					objMail.refreshPage(selenium);
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

		/*	
			 * 6 Click on the event banner of EVE1 Attached file' link is
			 * displayed in the banner
			 

			 7 Click on 'Attached file' The attached file is opened. */

			try {
				assertEquals("", strFuncResult);

				String strText = "Automation event";
				strFuncResult = objEventSetup
						.checkAttachedFilesForEventsNewWithChangedElmentID(
								selenium, strEveName1, strText,
								strEventValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
/*
			
			 * 8 Click on the event banner of EVE2 'Attached file' link is
			 * displayed in the banner
			 
			 9 Click on 'Attached file' The attached file is opened. */

			try {
				assertEquals("", strFuncResult);

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				String strHtmlText = "Test EMResource Events";
				strFuncResult = objEventSetup
						.checkAttachedFilesForEventsNewWithChangedElmentID(
								selenium, strEveName2, strHtmlText,
								strEventValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*	
			 * 12 Login as user U1 to region B. 'Region Default' screen is
			 * displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_1,
						strInitPwd);
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
				blnLogin = true;
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strEventVal = objEventSetup.fetchEventValueInEventList(
						selenium, strEveName1);
				if (strEventVal.compareTo("") != 0) {
					strFuncResult = "";
					strEventValue[0] = strEventVal;
				} else {
					strFuncResult = "Failed to fetch Event Value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strEventVal = objEventSetup.fetchEventValueInEventList(
						selenium, strEveName2);
				if (strEventVal.compareTo("") != 0) {
					strFuncResult = "";
					strEventValue[1] = strEventVal;
				} else {
					strFuncResult = "Failed to fetch Event Value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*	
			 * 13 Click on the event banner of EVE1 Attached file' link is
			 * displayed in the banner
			 

			 14 Click on 'Attached file'. The attached file is opened. */

			try {
				assertEquals("", strFuncResult);

				String strText = "Automation event";
				strFuncResult = objEventSetup
						.checkAttachedFilesForEventsNewWithChangedElmentID(
								selenium, strEveName1, strText,
								strEventValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
/*
			
			 * 15 Click on the event banner of EVE2 'Attached file' link is
			 * displayed in the banner
			 
			 16 Click on 'Attached file' The attached file is opened. */

			try {
				assertEquals("", strFuncResult);

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				String strHtmlText = "Test EMResource Events";
				strFuncResult = objEventSetup
						.checkAttachedFilesForEventsNewWithChangedElmentID(
								selenium, strEveName2, strHtmlText,
								strEventValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";

				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName_1 + "/" + strInitPwd;
				strTestData[3] = strRegn;
				strTestData[4] = strRegionName;
				strTestData[5] = strEveName3;
				strTestData[6] = "Attach PDF file for the Event need to be opened";

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath,
						"EventAttachedFiles");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				gstrResult = "FAIL";
			}
			
			
			gstrResult = "PASS";
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-68598";
			gstrTO = "Verify that a file can be attached to a multi region event.";
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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}

	/***************************************************************
	'Description		:Verify that resources can be added to a multi region event.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:30-10-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS68595() throws Exception {

		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Regions objRegion = new Regions();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		StatusTypes objST = new StatusTypes();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();
		EventList objEventList = new EventList();
		MobileView objMobileView = new MobileView();
		ReadData objReadData = new ReadData();
		String strFuncResult = "";
		try {
			gstrTCID = "68595"; // Test Case Id
			gstrTO = "Verify that resources can be added to a multi region event.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTmText = dts.getCurrentDate("HHmm");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);

			String strRegn1 = rdExcel.readData("Login", 3, 4);
			String strRegn2 = rdExcel.readData("Regions", 3, 5);
			String strRegionValue[] = new String[2];

			// ST
			String strNSTValue = "Number";
			String strStatType1 = "Aa1_" + strTimeText;
			String strStatType2 = "Aa2_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strSTvalue[] = new String[2];
			// RT
			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strResrctTypName2 = "AutoRt2_" + strTimeText;
			String strRTValue[] = new String[2];
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strRSValue[] = new String[2];
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// sec data
			String strSection1 = "AB1_" + strTimeText;
			String strSection2 = "AB2_" + strTimeText;

			// event temp
			String strTempName1 = "AutoET_1" + strTimeText;
			String strTempName2 = "AutoET_2" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			String strETValues[] = new String[2];

			String strEveName1 = "Eve_1" + strTimeText;

			// /User
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = "Full_" + strUserName_1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRoleName1 = "AutoR1_" + strTimeText;
			String strRoleName2 = "AutoR2_" + strTimeText;
			String strRoleRights[][] = {};
			String[] strRoleValue = new String[2];

			/*
			 * STEP : Action:Precondition: 1. In region A, resource RS1 is
			 * created under resource type RT1 associated with status type ST1.
			 * Status type ST1 is in status type section SEC1. 2. Multi region
			 * event template ET1 is created in region A selecting RT1, ST1. 3.
			 * In region B, resource RS2 is created under resource type RT2
			 * associated with status type ST2. Status type ST2 is in status
			 * type section SEC2. 4. Multi region event template ET2 is created
			 * in region B selecting RT2, ST2. 5. Multi region event is created
			 * selecting event template ET1(created in region A), ET2(created in
			 * region B). 6. User U1 has access to regions A and B. 7. User U1
			 * in region A has: a. Role to view the status type ST1 b. 'View
			 * Resource' right on resource RS1 c. 'Maintain Events' right 8.
			 * User U1 in region A has: a. Role to view the status type ST2 b.
			 * 'View Resource' right on resource RS2 c. 'Maintain Events' right
			 * Expected Result:No Expected Result
			 */

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Fetach region values

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strRegionValue[0] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn1);
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
				strRegionValue[1] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn2);
				if (strRegionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strStatType1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strStatType1);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName1);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource1, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Section1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumFirefox,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox,
						strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strStatType1 };
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strStatTypeArr, strSection1, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRTValue[0] };
				String[] strStatusTypeval = { strSTvalue[0] };
				strFuncResult = objEventSetup.filMndfldsEveTempAsMultiEveTemp(
						seleniumPrecondition, strTempName1, strTempDef, strEveColor, true,
						true, strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strETValues[0] = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName1);
				if (strETValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdRts[] = {};
				String strViewRts[] = { strSTvalue[0] };
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName1, strRoleRights, strViewRts, true,
						strUpdRts, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName1);

				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue[0], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegionVal[] = { strRegionValue[1] };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName_1, strRegionVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strStatType2, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strStatType2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName2,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName2);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource2, strAbbrv, strResrctTypName2,
						strContFName, strContLName, strState, strCountry,
						strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource2);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Section2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumFirefox,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox,
						strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr1 = { strStatType2 };
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strStatTypeArr1, strSection2, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRTValue[1] };
				String[] strStatusTypeval = { strSTvalue[1] };
				strFuncResult = objEventSetup.filMndfldsEveTempAsMultiEveTemp(
						seleniumPrecondition, strTempName2, strTempDef, strEveColor, true,
						true, strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strETValues[1] = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName2);
				if (strETValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdRts1[] = {};
				String strViewRts1[] = { strSTvalue[1] };
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName2, strRoleRights, strViewRts1, true,
						strUpdRts1, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[1] = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName2);

				if (strRoleValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue[1], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource2, strRSValue[1], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			// select regions
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String[] strRegnName = { strRegn1, strRegn2 };
				strFuncResult = objCreateUsers
						.navEditMultiRegnEventRitesAndSelctRegions(seleniumPrecondition,
								strRegnName, strRegionValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserName_1,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Multi region event
			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = "Event Management screen is NOT displayed";
				assertTrue(objEventSetup.navToEventManagement(seleniumPrecondition));
				strFuncResult = "";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEvntTemplateNames = {
						"css=input[name='region-" + strRegionValue[0]
								+ "'][value='" + strETValues[0] + "']",
						"css=input[name='region-" + strRegionValue[1]
								+ "'][value='" + strETValues[1] + "']" };
				strFuncResult = objEventSetup
						.createMultiRegnEventMandatory(seleniumPrecondition, strEveName1,
								strTempDef, strEvntTemplateNames);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as user U1 to region A, navigate to
			 * Event>>Event Management, click on 'Edit' link associated with
			 * event EVE. Expected Result:'Edit Event' screen is displayed.
			 */
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select resource RS1 and save. Expected
			 * Result:'Event List' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource1, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the event banner of EVE Expected
			 * Result:Resource RS1 is displayed under RT1 along with ST1.
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strStatType1 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName1, strResrctTypName1, strResource1,
						strStatTypeArr);
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
			 * STEP : Action:Login as user U1 to region B, navigate to
			 * Event>>Event Management, click on 'Edit' link associated with
			 * event EVE. Expected Result:'Edit Event' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName_1,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select resource RS2 and save. Expected
			 * Result:'Event List' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource2, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on the event banner of EVE Expected
			 * Result:Resource RS2 is displayed under RT2 along with ST2.
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr1 = { strStatType2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName1, strResrctTypName2, strResource2,
						strStatTypeArr1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Mobile View' link on footer of the
			 * application. Expected Result:'Main Menu' screen is displayed.
			 */

			/*
			 * STEP : Action:Navigate to Events>>EVE>>Resources Expected
			 * Result:Resource RS2 is displayed on the 'Event Resources' screen
			 */

			String strIconSrc = objReadData.readInfoExcel("Event Temp Data", 2,
					6, strFILE_PATH);
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.navToEventDetailInMob(selenium,
						strEveName1, strIconSrc, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on RS2 Expected Result:ST2 is displayed in
			 * 'Resource Detail' screen
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr1 = { strStatType2 };
				strFuncResult = objMobileView.checkResouceDetail(selenium,
						strResource2, strSection2, strStatTypeArr1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate back to 'Main Menu' screen of Mobile View,
			 * click on 'Select Region' link Expected Result:'Pick Region'
			 * screen is displayed with regions A and B
			 */
			try {
				assertEquals("", strFuncResult);
				// click on back
				selenium.click("link=Back");
				selenium.waitForPageToLoad(gstrTimeOut);
				selenium.click("link=Back");
				selenium.waitForPageToLoad(gstrTimeOut);
				selenium.click("link=Events");
				selenium.waitForPageToLoad(gstrTimeOut);
				selenium.click("link=Main");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRegion = { strRegn1, strRegn2 };
				strFuncResult = objMobileView.navSelRgnPageAndverRgn(selenium,
						strRegion);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select region A and then navigate to Navigate to
			 * Events>>EVE>>Resources Expected Result:Resource RS1 is displayed
			 * on the 'Event Resources' screen
			 */
			try {
				assertEquals("", strFuncResult);
				selenium.click("link=" + strRegn1);
				selenium.waitForPageToLoad(gstrTimeOut);
				strFuncResult = objMobileView.navToEventDetailInMob(selenium,
						strEveName1, strIconSrc, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on RS1 Expected Result:ST1 is displayed in
			 * 'Resource Detail' screen
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strStatType1 };
				strFuncResult = objMobileView.checkResouceDetail(selenium,
						strResource1, strSection1, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String strTestData[] = new String[10];
			try {

				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName_1 + "/" + strInitPwd;
				strTestData[3] = strStatType1 + "/" + strStatType2;
				strTestData[5] = "Verify from 14th step";
				strTestData[6] = strResource1 + "/" + strResource2;
				strTestData[7] = strSection1;
				strTestData[8] = strEveName1;
				strTestData[9] = strRegn1 + "/" + strRegn2;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-68595";
			gstrTO = "Verify that resources can be added to a multi region event.";
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
			selenium.selectWindow("");
			strFuncResult = objLogin.logout(selenium);

			try {
				assertEquals("", strFuncResult);
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}
	
	/***************************************************************
	'Description		:Create a multi region event by selecting different templates ,T1 from region X and T2 from region Y and verify that: <br>
	a. The event banner is present for region X with the color and icon of template T1 <br>
	b. The event banner is present for region Y with the color and icon of template T2.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:23-11-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS68534() throws Exception {

		String strFuncResult = "";

		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Regions objRegion = new Regions();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		StatusTypes objST = new StatusTypes();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral =new General();
		EventList objEventList=new EventList();
		try {
			gstrTCID = "68534";
			gstrTO = "Create a multi region event by selecting different templates ,"
					+ "T1 from region X and T2 from region Y and verify that: <br>"
					+ "a. The event banner is present for region X with the color"
					+ " and icon of template T1 <br>"
					+ "b. The event banner is present for region Y with the color"
					+ " and icon of template T2.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTmText = dts.getCurrentDate("HHmm");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);

			String strRegionName1 = rdExcel.readData("Regions", 3, 5);
			String strRegionName2 = rdExcel.readData("Regions", 4, 5);
			String strRegionValue[] = new String[2];

			// event temp
			String strTempName1 = "AutoET_1" + strTimeText;
			String strTempName2 = "AutoET_2" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			String strETValues[] = new String[2];

			String strEveName1 = "Eve_1" + strTimeText;

			log4j.info("~~~~PRE-CONDITION- " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = "Full_" + strUserName_1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// ST
			String strNSTValue = "Number";
			String strStatType1 = "Aa1_" + strTimeText;
			String strStatType2 = "Aa2_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strSTvalue[] = new String[2];
			// RT
			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strResrctTypName2 = "AutoRt2_" + strTimeText;
			String strRTValue[] = new String[2];
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strRSValue[] = new String[2];
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			
			/* 1. User U1 has access to regions A and B. */

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegionName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objRegion.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegionName1);
				if (strFuncResult.compareTo("") != 0) {

					strRegionValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegionName2);
				if (strFuncResult.compareTo("") != 0) {

					strRegionValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegionVal[] = { strRegionValue[1] };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName_1, strRegionVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * 3. In region A, resource RS1 is created under resource type RT1
			 * associated with status type ST1.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strStatType1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strStatType1);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName1);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource1, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. In region B, resource RS2 is created under resource type RT2
			 * associated with status type ST2.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegionName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strStatType2, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strStatType2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName2,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName2);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource2, strAbbrv, strResrctTypName2,
						strContFName, strContLName, strState, strCountry,
						strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource2);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 2 Login to region A as RegAdmin, navigate to Setup >> Users,
			 * click on 'Edit' link associated with user U1. 'Edit User' screen
			 * is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegionName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 3 Navigate to 'Users Preferences' section, click on 'Multi-Region
			 * Event Rights' link. 'Edit Multi-Region Event Rights' screen is
			 * displayed with regions A and B.
			 */
			
			/*
			 * 4 Select regions A and B and save. 'Edit User' screen is
			 * displayed.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				String strRegion[] = { strRegionName1, strRegionName2 };
				strFuncResult = objCreateUsers
						.navEditMultiRegnEventRitesAndSelctRegions(seleniumPrecondition,
								strRegion, strRegionValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 5 Click on 'Save'. 'Users List' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Navigate to Event >> Event Setup, click on 'Create new event
			 * template' 'Create New Event Template' screen is displayed.
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 7 Create a template ET1 selecting the option 'Multi-Region',
			 * selecting status type ST1 and resource type RT1 and click on
			 * 'Save'. 'Event Template List' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRTValue[0] };
				String[] strStatusTypeval = { strSTvalue[0] };
				strFuncResult = objEventSetup.filMndfldsEveTempAsMultiEveTemp(
						seleniumPrecondition, strTempName1, strTempDef, strEveColor, true,
						true, strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strETValues[0] = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName1);
				if (strETValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 8 Login to region B as a user A (with right to setup event
			 * templates), Navigate to Event>>Event Setup, Click on 'Create New
			 * Event Template'. 'Create New Event Template' screen is displayed.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(selenium,
						strRegionName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 9 Create a template ET2 selecting the option 'Multi-Region',
			 * selecting status type ST2 and resource type RT2. 'Event Template
			 * List' screen is displayed.
			 */
			

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRTValue[1] };
				String[] strStatusTypeval = { strSTvalue[1] };
				strFuncResult = objEventSetup.filMndfldsEveTempAsMultiEveTemp(
						selenium, strTempName2, strTempDef, strEveColor, true,
						false, strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strETValues[1] = objEventSetup.fetchETInETList(selenium,
						strTempName2);
				if (strETValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 10 Login as user U1 in region A, navigate to Event>>Event
			 * Management, click on 'Create new multi region event' Create
			 * Multi-Region Event' screen is displayed.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName_1,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(selenium,
						strRegionName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertTrue(strFuncResult.equals(""));
				blnLogin = true;
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
				strFuncResult = "";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			try {
				assertEquals("", strFuncResult);

				String[] strEvntTemplateNames = {
						"css=input[name='region-" + strRegionValue[0]
								+ "'][value='" + strETValues[0] + "']",
						"css=input[name='region-" + strRegionValue[1]
								+ "'][value='" + strETValues[1] + "']" };

				strFuncResult = objEventSetup
						.createMultiRegnEventMandatoryNavMultiRegnConfrmtn(
								selenium, strEveName1, strTempDef,
								strEvntTemplateNames, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strStDate = "";
			try {
				assertEquals("", strFuncResult);
				String[] strArStDate = new String[6];
				strArStDate = objEventSetup.fetchEventStartDateValues(selenium);
				strFuncResult = strArStDate[0];
				strStDate = dts.converDateFormat(strArStDate[1]+"-"
						+ strArStDate[2]+"-" + strArStDate[3], "MMM-dd-yyyy",
						"MM/dd/yyyy");
				strStDate = strStDate + " " + strArStDate[4] + ":"
						+ strArStDate[5];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strEndDate = "";
			try {
				assertEquals("", strFuncResult);
				String[] strArEndDate = new String[6];
				strArEndDate = objEventSetup.fetchEventEndDateValues(selenium);
				strFuncResult = strArEndDate[0];
				strEndDate = dts.converDateFormat(strArEndDate[1]+"-"
						+ strArEndDate[2]+"-" + strArEndDate[3], "MMM-dd-yyyy",
						"MM/dd/yyyy");
				strEndDate = strEndDate + " " + strArEndDate[4] + ":"
						+ strArEndDate[5];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup
						.startAndNavMultiRegnConfrmtn(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRegnName = { strRegionName1, strRegionName2 };
				String[] strETNames = { strTempName1, strTempName2 };
				strFuncResult = objEventSetup
						.verfyElementsInMultiRegnEvntConfrmPgeNew(selenium,
								strEveName1, strTempDef, strStDate, strEndDate,
								"No file attached.", "N", "N", strRegnName,
								strETNames);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup
						.checkConfirmMsgInMultiRegEveConfm(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.navMultiRegnEvntStatus(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*try {
				assertEquals("", strFuncResult);
				String[] strRegnName = { strRegionName1, strRegionName2 };
				String[] strETNames = { strTempName1, strTempName2 };
				strFuncResult = objEventSetup
						.verfyElementsInMultiRegnEvntStatusPge(selenium,
								strEveName1, strTempDef, strStDate, strEndDate,
								"No file attached.", "N", "N", strRegnName,
								strETNames);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}*/
			try {
				assertEquals("", strFuncResult);
				String[] strRegnName = { strRegionName1, strRegionName2 };
				String[] strETNames = { strTempName1, strTempName2 };
				strFuncResult = objEventSetup
						.verfyEvenStatInMultiRegnEvntStatusPge(selenium,
								strRegnName, strETNames);
				selenium.click("css=input[value='Done']");
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
						+ strEveName1 + "']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					
				log4j.info("Event Management screen is displayed newly created event "
						+ strEveName1
						+ " is listed under it with the provided data.");
				}else
				{
					strFuncResult="Event Management screen is NOT displayed newly created event "
							+ strEveName1
							+ " is listed under it with the provided data.";
					log4j.info("Event Management screen is NOT displayed newly created event "
							+ strEveName1
							+ " is listed under it with the provided data.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strEveColor1 = "black eventNotSel";
				strFuncResult = objEventList.varfyEventColor(selenium,
						strEveName1, strEveColor1);
				
				if(strFuncResult.compareTo("")!=0){
					strFuncResult="";
					try {
						assertEquals("", strFuncResult);
						strEveColor = "black eventSingle";
						strFuncResult = objEventList.varfyEventColor(selenium,
								strEveName1, strEveColor1);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*	try {
				assertEquals("", strFuncResult);
				String strEveColor1 = "black eventNotSel";
				strFuncResult =objEventList.varfyEventColor(selenium,strEveName1, strEveColor1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}*/
			try {
				assertEquals("", strFuncResult);
				String strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
						strFILE_PATH);
				try {
					assertEquals(
							strIcon,
							selenium.getAttribute("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
									+ strEveName1
									+ "']/parent::tr/td[2]/img@src"));
					log4j.info(strIcon
							+ " is found in the Event Management/List table for the event "
							+ strEveName1);
				} catch (AssertionError ae) {
					log4j.info(strIcon
							+ " is NOT found in the Event Management/List table for the event "
							+ strEveName1);
					strFuncResult = strIcon
							+ " is NOT found in the Event Management/List table for the event "
							+ strEveName1;
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strTestData[] = new String[10];
			try {

				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName_1 + "/" + strInitPwd;
				strTestData[3] = strStatType1 + "/" + strStatType2;
				strTestData[5] = "Verify from 13th step";
				strTestData[6] = strResource1 + "/" + strResource2;
				strTestData[7] = "";
				strTestData[8] = strEveName1;
				strTestData[9] = strRegionName1 + "/" + strRegionName2;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-68534";
			gstrTO = "Create a multi region event by selecting different templates ,T1 from region X and T2 from region Y and verify that: <br>"
					+ "a. The event banner is present for region X with the color and icon of template T1 <br>"
					+ "b. The event banner is present for region Y with the color and icon of template T2.";
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
				gstrResult = "FAIL";
				gstrReason = Ae.toString();
			}
		}
	}
}
