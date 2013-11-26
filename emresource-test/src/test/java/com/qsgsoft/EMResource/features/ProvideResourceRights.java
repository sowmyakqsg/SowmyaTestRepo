package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/********************************************************************
' Description		:This class contains test cases from  requirement
' Requirement		:Provide resource rights
' Requirement Group	:Setting up Resources
' Product		    :EMResource Base
' Date			    :04-06-2012
' Author		    :QSG
' -------------------------------------------------------------------
' Modified Date				                       Modified By
' Date					                           Name
'*******************************************************************/

public class ProvideResourceRights {

	// Log4j object to write log entries to the Log files
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.ProvideResourceRights");
	static {
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
	Selenium selenium, seleniumPrecondition;

	// Object for date time settings
	Date_Time_settings dts = new Date_Time_settings();

	public Properties propElementDetails; // Property variable for ElementID
											// file
	public Properties propEnvDetails;// Property variable for Environment data
	public static Properties browserProps = new Properties();
	public Properties pathProps;
	public static String gstrBrowserName;// Variable to store browser name
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;// Result
																		// Variables
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
	 * This function is called the setup() function which is executed before
	 * every test.
	 * 
	 * The function will take care of creating a new selenium session for every
	 * test
	 * 
	 ****************************************************************************************************************/
	@Before
	public void setUp() throws Exception {

		// Create object to read environment properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();
		// Retrieve browser information
		browser = propEnvDetails.getProperty("Browser");
		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		// Retrieve the value of page load time limit
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		// create an object to refer to Element ID properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		// Create a new selenium session
		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));
		// Start Selenium RC
		selenium.start();
		// Maximize the window
		selenium.windowMaximize();
		selenium.setTimeout("");

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		// Define object to call support functions to read excel, date etc
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {
		// kill browser
		try {
			selenium.close();
		} catch (Exception e) {

		}
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}

		seleniumPrecondition.stop();
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
		gstrReason = gstrReason.replaceAll("'", " ");
		// Write Result of the test.
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild, StrSessionId);

	}

/***************************************************************************
  'Description  :Verify that a user with 'Update status' right on a resource
                 (given from Setup>>Resources)receives the expired status 
                 notification when the status of the resource expires.
  'Arguments    :None
  'Returns      :None
  'Date         :6/8/2012
  'Author       :QSG
  '-------------------------------------------------------------------------
  'Modified Date                                                Modified By
  'Date                                                         Name
  **************************************************************************/

	@Test
	public void testBQS69697() throws Exception {
		
		Login objLogin = new Login();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		Resources objResources = new Resources();
		Views objViews = new Views();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		General objMail = new General();
		boolean blnLogin = false;
		String strFuncResult = "";
		ViewMap objViewMap = new ViewMap();
		try {



			gstrTCID = "69697"; // Test Case Id
			gstrTO = " Verify that a user with 'Update status' right"
					+ " on a resource (given from Setup>>Resources) "
					+ "receives the expired status notification when the status of the resource expires.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTmText = dts.getCurrentDate("HHmm");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			int intResCnt = 0;
			// Web mail user details
			String strLoginName = objrdExcel.readData("WebMailUser", 2, 1);
			String strPassword = objrdExcel.readData("WebMailUser", 2, 2);

			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			// To create ST details
			String statTypeName = "AutoSt_" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Auto";
			String strExpMn = "05";
			String strExpHr = "00";
			String strStatValue = "";
			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];
			String strEMail = objrdExcel.readData("WebMailUser", 2, 1);
			String strUpdateValue = "0";

			String strUpdTime = "";
			String strCurDate = "";

			String strMsgBody2 = "";
			String strMsgBody1 = "";
			String strMsgBody2Another = "";
			String strMsgBody1Another = "";

			int intEMailRes = 0;
			int intPagerRes = 0;
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			// To create ResourceType Details
			String strResrcTypName = "AutoRt_" + strTimeText;
			String strRoleName = "AutoRole_" + strTimeText;
			String[][] strRoleRights = {};
			// To Create user Details
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulName = "FN" + strUserName;

			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// to create view Details
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Resource (Resources and status types as rows. "
					+ "Status, comments and timestamps as columns.)";
			// To create Resource Details
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Hospital";
			String strContFName = "FN";
			String strContLName = "LN";
			String strHavBed = "No";
			String strAHAId = "";

			String strRoleValue = "";
			String strResVal = "";
			String strSubjName = "EMResource Expired Status Notification: "
					+ strResource;
			
		/*
		 * 1Precondition: 1. Status type 'ST' is created providing an
		 * expiration time of 5 minutes and is associated to resource type
		 * 'RT' 2. User 'U1' is assigned to role 'R' which has update right
		 * on 'ST' 3. User U1 has 'User Must Update Overdue Status' right.
		 */
		/*
		 * STEP : Action:Login as Test user and navigate to 'Setup >>
		 * Resource' Expected Result:'Resource List' screen is displayed.
		 */
		// 416683
			
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectSTypesAndFilMandFldsWithExTime(
								seleniumPrecondition, strStatusTypeValue,
								statTypeName, strStatTypDefn, strExpHr,
								strExpMn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTvalue, true, strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail,
						strEMail, strEMail, "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navEditUserPge(seleniumPrecondition,
								strUserName, strByRole,
								strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.clkOnSysNotfinPrefrences(seleniumPrecondition,
								strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillRecvExpStatusNotifinEditUsr(
								seleniumPrecondition, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
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
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select to create a new resource, fill mandatory
			 * data and save. Expected Result:'Assign users to RS' page is
			 * displayed.
			 */
			// 416684

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
						strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * STEP : Action:Select the check box for 'View Resource' and
		 * 'Update Status' for user 'U1' and Save. Expected Result:Resource
		 * 'RS' is created and displayed in the 'Resource List' screen.
		 */
		// 416685

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToAssignUsersOFRes(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUsrName = strUserName;
				strFuncResult = objResources.assignUsrToResource(selenium,
						false, true, false, true, strUsrName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, strAHAId, strAbbrv,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate to 'Setup >> View' and create a view 'V1'
		 * selecting 'ST' and 'RS' Expected Result:No Expected Result
		 */
		// 416686

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
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
			 * STEP : Action:Login as user U1 and navigate to View>>V1 Expected
			 * Result:Update Status prompt is displayed.
			 */
			// 416687

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.updateStatusWithoutStatus(selenium,
						strResource, statTypeName, strSTvalue[0],
						strUpdateValue, "");
				

				String strLastUpdArr[] = selenium.getText(
						"//table/tbody/tr/td/a[text()='" + strResource
								+ "']/ancestor::tr/td[last()]").split(" ");
				strUpdTime = strLastUpdArr[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime, 5,
						"HH:mm");
				strUpdTime = strAdUpdTime;
				String strCurYear = dts.getCurrentDate("yyyy");
				String strCurDateMnth = strLastUpdArr[0] + strLastUpdArr[1]
						+ strCurYear;

				strCurDate = dts.converDateFormat(strCurDateMnth, "ddMMMyyyy",
						"MM/dd/yyyy");
				Thread.sleep(360000);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navToRegionalMapView(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navResPopupWindow(selenium, strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				int intPositionExpire = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strUpdateValue
								+ "']/preceding-sibling::span").intValue();
				intPositionExpire = intPositionExpire + 2;

				String strElementIdExpire = "//div[@class='emsCenteredLabel']"
						+ "[text()='" + strResource
						+ "']/following-sibling::div/span[" + intPositionExpire
						+ "][@class='overdue']";

				strFuncResult = objMail.waitForMailNotification(selenium, 60,
						strElementIdExpire);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Update status and save Expected Result:Status is
			 * updated properly on the view screen
			 */
			// 416689
			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				String strAdUpdTimeShift = dts.addTimetoExisting(
						strUpdTime, 1, "HH:mm");
				
				strMsgBody1 = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeName
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody1Another = "For "
					+ strUsrFulName
					+ "\nRegion: "
					+ strRegn
					+ "\n\nThe "
					+ statTypeName
					+ " status for "
					+ strResource
					+ " expired "
					+ strCurDate
					+ " "
					+ strAdUpdTimeShift
					+ ".\n\nClick the link below to go to the EMResource login screen:"
					+

					"\n\n        "+propEnvDetails.getProperty("MailURL")
					+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
					+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody2 = "EMResource expired status: " + strResource
						+ ". " + statTypeName + " status expired " + strCurDate
						+ " " + strUpdTime + ".";
				
				strMsgBody2Another = "EMResource expired status: " + strResource
				+ ". " + statTypeName + " status expired " + strCurDate
				+ " " + strAdUpdTimeShift + ".";

				
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);
						if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2Another)) {
							intPagerRes++;
							log4j.info(strMsgBody2
									+ " is displayed for Pager Notification");
						} else {
							log4j
									.info(strMsgBody2
											+ " is NOT displayed for Pager Notification");
							gstrReason = strMsgBody2
									+ " is NOT displayed for Pager Notification";
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");
					strSubjName = "EMResource Expired Status Notification: "
							+ strResource;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);
						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1Another)) {
							intEMailRes++;
							log4j.info(strMsgBody1
									+ " is displayed for Email Notification");
						} else {
							log4j
									.info(strMsgBody1
											+ " is NOT displayed for Email Notification");
							gstrReason = strMsgBody1
									+ " is NOT displayed for Email Notification";
						}
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);
						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1Another)) {
							intEMailRes++;
							log4j.info(strMsgBody1
									+ " is displayed for Email Notification");
						} else {
							log4j
									.info(strMsgBody1
											+ " is NOT displayed for Email Notification");
							gstrReason = strMsgBody1
									+ " is NOT displayed for Email Notification";
						}
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad(gstrTimeOut);
					selenium.close();
					selenium.selectWindow("");
					Thread.sleep(2000);
					
					// check Email, pager notification
					if (intEMailRes == 2 && intPagerRes == 1) {
						intResCnt++;
					}

					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						if (intResCnt == 1)
							gstrResult = "PASS";
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	


			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69697";
			gstrTO = "Verify that a user with 'Update status' right on a resource (given from Setup>>Resources) receives the expired status notification when the status of the resource expires.";
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
	 'Description  :Verify that a user with 'Update status' right on a resource (given from Setup>>Resources) 
	                      receives the status update promp when the status of the resource expires.
	 'Precondition  :1. Status type 'ST' is created providing an expiration time of 5 minutes and is associated to resource type 'RT'
	       2. User 'U1' is assigned to role 'R' which has update right on 'ST'
	       3. User U1 has 'User Must Update Overdue Status' right.
	 'Arguments      :None
	 'Returns      :None
	 'Date       :6/8/2012
	 'Author       :QSG
	 '---------------------------------------------------------------
	 'Modified Date    Modified By
	 'Date     Name
	 ***************************************************************/

	@Test
	public void testBQS69698() throws Exception {
		Login objLogin = new Login();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		Resources objResources = new Resources();
		Views objViews = new Views();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		General objMail=new General();
		String strFuncResult = "";
		boolean blnLogin = false;
		try {


			gstrTCID = "69698";
			gstrTO = " Verify that a user with 'Update status' right on a"
					+ " resource (given from Setup>>Resources) receives the"
					+ " status update promp when the status of the resource expires."; // Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTmText = dts.getCurrentDate("HHmm");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
		

			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			// To create ST details
			String statTypeName = "AutoSt_" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Auto";
			String strExpMn = "05";
			String strExpHr = "00";
			String strStatValue = "";
			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strUpdateValue = "0";

			String strResrcTypName = "AutoRt_" + strTimeText;
			String strRoleName = "AutoRole_" + strTimeText;
			String[][] strRoleRights = {};

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulName = strUserName;

			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Hospital";
			String strContFName = "FN";
			String strContLName = "LN";
			String strHavBed = "No";
			String strAHAId = "";

			String strRoleValue = "";
			String strResVal = "";

			/*
			 * 1Precondition: 1. Status type 'ST' is created providing an
			 * expiration time of 5 minutes and is associated to resource type
			 * 'RT' 2. User 'U1' is assigned to role 'R' which has update right
			 * on 'ST' 3. User U1 has 'User Must Update Overdue Status' right.
			 */
			/*
			 * STEP : Action:Login as Test user and navigate to 'Setup >>
			 * Resource' Expected Result:'Resource List' screen is displayed.
			 */
			// 416683

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectSTypesAndFilMandFldsWithExTime(seleniumPrecondition,
								strStatusTypeValue, statTypeName,
								strStatTypDefn, strExpHr, strExpMn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName);

				if (strRoleValue.compareTo("") != 0) {
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
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MustOverDue"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
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

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
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
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select to create a new resource, fill mandatory
			 * data and save. Expected Result:'Assign users to RS' page is
			 * displayed.
			 */
			// 416684

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResource_FillAllFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						strStandResType, false, false, "", "", false, "", "",
						"", "", "", "", strContFName, strContLName, "", "", "",
						"", "", "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select the check box for 'View Resource' and
			 * 'Update Status' for user 'U1' and Save. Expected Result:Resource
			 * 'RS' is created and displayed in the 'Resource List' screen.
			 */
			// 416685

			try {
				assertEquals("", strFuncResult);
				String strUsrName = strUserName;
				strFuncResult = objResources.assignUsrToResource(selenium,
						false, true, false, true, strUsrName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, strAHAId, strAbbrv,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
						strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to 'Setup >> View' and create a view 'V1'
			 * selecting 'ST' and 'RS' Expected Result:No Expected Result
			 */
			// 416686

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
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
			 * STEP : Action:Login as user U1 and navigate to View>>V1 Expected
			 * Result:Update Status prompt is displayed.
			 */
			// 416687

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			/*
			 * STEP : Action:Update status and save Expected Result:Status is
			 * updated properly on the view screen
			 */
			// 416689
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdateValue, strSTvalue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.verifyUpdateST(selenium,
						strResrcTypName, statTypeName, strUpdateValue);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				Thread.sleep(300000);
				strFuncResult = objMail.waitForMailNotification(selenium, 60,
						"//span[@class='overdue'][text()='Status is Overdue']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				

				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			/*
			 * STEP : Action:Wait for the expiration time. Expected Result:At
			 * the expiration time Update Status prompt is displayed again.
			 */
			// 416690

			try {
				assertEquals("", gstrReason);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69698";
			gstrTO = "Verify that a user with 'Update status' right on a resource (given from Setup>>Resources) receives the status update promp when the status of the resource expires.";
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
	'Description	:Verify that a user with 'Update status' right on a resource 
	           (given from Setup>>Resources) can update the status of a resource.
	'Arguments		:None
	'Returns		:None
	'Date			:16-10-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS69696() throws Exception {
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		boolean blnLogin = false;

		try {
			gstrTCID = "69696"; // Test Case Id
			gstrTO = " Verify that a user with 'Update status' "
					+ "right on a resource (given from Setup>>Resources) "
					+ "can update the status of a resource.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strRegn = objrdExcel.readData("Login", 3, 4);
			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);

			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String statTypeName1 = "ST_1" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strRolesName = "Role" + strTimeText;
			String strRoleValue = "";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);

			String strResource = "AutoRes" + System.currentTimeMillis();
			String strAbbrv = "qs";
			String strStandResType = "Aeromedical";
			String strContFName = "Auto", strContLName = "qsg";
			String strHavBed = "No", strAHAId = "";

			boolean blnVisibleToAllUsers = true;
			boolean blnShowAllStatusTypes = false;
			boolean blnShowAllResourceTypes = false;

			String strViewName = "AutoV" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			String strUpdValue = "0";

			String strStatType[] = { statTypeName1 };

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Status type 'ST' is created which is associated to resource
			 * type 'RT'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
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
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. User 'U1' is assigned a role 'R' which has view and update
			 * right on 'ST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navCreateRolePge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.roleMandtoryFlds(seleniumPrecondition,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strSTViewValue = { { strSTvalue[0], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[0], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTViewValue,
						strSTUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName);
				if (strRoleValue.compareTo("") != 0) {
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
						strRoleValue, true);

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

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * 2 Login as RegAdmin, navigate to Setup >> Resources 'Resource
			 * List' screen is displayed.
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New Resource', fill mandatory data
			 * and save. Expected Result:'Assign users to < Resource name >'
			 * page is displayed.
			 */
			// 416654

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(selenium,
						strResource, strAbbrv, strResrctTypName,
						strStandResType, strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select the check box for 'View Resource' and
			 * 'Update Status' for user 'U1' and Save. Expected Result:Resource
			 * 'RS' is created and displayed in the 'Resource List' screen.
			 */
			// 416655

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.assignUsrToResource(selenium, false,
						true, false, true, strUserName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(selenium,
						strResource, strHavBed, strAHAId, strAbbrv,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(selenium,
						strResource);
				if (strRSValue[0].compareTo("") == 0) {
					strFuncResult = "Failed to fetch Resource Value in Resource List page";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to 'Setup >> View' and create a view 'V1'
			 * selecting 'ST' and 'RS' Expected Result:No Expected Result
			 */
			// 416656

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, blnVisibleToAllUsers,
						blnShowAllStatusTypes, strSTvalue,
						blnShowAllResourceTypes, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user U1 and navigate to View>>V1 Expected
			 * Result:RS is displayed under RT along with ST on 'V1' page.
			 */
			// 416657
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResources[] = { strResource };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.updateStatusWithoutStatus(selenium,
						strResource, statTypeName1, strSTvalue[0], strUpdValue,
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69696";
			gstrTO = "Verify that a user with 'Update status' right on a resource (given from Setup>>Resources) can update the status of a resource.";
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
