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


/*******************************************************************
' Description		:This class contains test cases from requirement
' Requirement Group	:Creating & managing users  
' Requirement 	    :Forgot User ID / Password
� Product		    :EMResource v3.24
' Date			    :06/Nov/2013
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/
public class EdgeCaseForgotUserIDOrPassword {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EdgeCaseForgotUserIDOrPassword");
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
	String gstrTimeOut = "";
	Selenium selenium, seleniumPrecondition;

	@Before
	public void setUp() throws Exception {

		gdtStartDate = new Date();
		gstrBrowserName = "IE 8";
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		Paths_Properties objPaths_Properties = new Paths_Properties();
		propElementAutoItDetails = objPaths_Properties.ReadAutoit_FilePath();
		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();
		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));
		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));
		selenium.start();
		selenium.windowMaximize();
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}
	@After
	public void tearDown() throws Exception {

		try {
			selenium.close();
		} catch (Exception e) {
		}
		selenium.stop();
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {
		}
		seleniumPrecondition.stop();
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
		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
	//start//testFTS127826//
	/***************************************************************
	'Description		:Verify that user cannot retrieve the username by providing pager address 
						 associated with his/her account
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:11/6/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/
	@Test
	public void testFTS127826() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		General objMail = new General();// object of class General.
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers.
		try {
			gstrTCID = "127826"; // Test Case Id
			gstrTO = " Verify that user cannot retrieve the username by providing pager address associated"
					+ " with his/her account";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// User
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strEMail = rdExcel.readData("WebMailUser", 3, 1);
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 3, 1);
			String strPassword = rdExcel.readData("WebMailUser", 3, 2);
			String strSubjName = "";
			String strFrom = "no-reply@emsystem.com";
			String strTo = strEMail;
			/*
			 * STEP : Action:Provide pager address for a user and check the
			 * notification is not received for the user when pager address is
			 * provided in 'forgot username?' email field Expected Result:No
			 * Expected Result
			 */
			// 664202

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			// Login
			try {
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// User creation
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
						seleniumPrecondition, strUserName_A, strInitPwd,
						strConfirmPwd, strUsrFulName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", "", "",
						strEMail, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");			
			log4j.info("~~~~~TESTCASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin
						.checkForgotUsernameAndPasswordLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.clickOnForgotUsernameLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.provideEmailIdInForgotUserIdPage(
						selenium, strEMail);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Verifying mail
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "forgot username email";
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");
					strMsg = objMail.seleniumGetText(selenium,
							"css=div.fixed.leftAlign", 160);
					if (strMsg.contains(strUserName_A)) {
						log4j.info(" User " + strUserName_A + " is retrived");
						strFuncResult = " User " + strUserName_A
								+ " is retrived";
						gstrReason = strFuncResult;
					} else {
						log4j.info(" User " + strUserName_A
								+ " is NOT retrived");
						strFuncResult = "";
					}
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad(gstrTimeOut);
					selenium.close();
					selenium.selectWindow("");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
			gstrTCID = "FTS-127826";
			gstrTO = "Verify that user cannot retrieve the username by providing pager address associated"
					+ " with his/her account";
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

	// end//testFTS127826//
	
	//start//testFTS127830//
	/***************************************************************
	'Description		:Verify that usernames are displayed ones in the 'forgot username email'
	 					 notification when user has access to multiple regions.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:11/8/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/
	@Test
	public void testFTS127830() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		General objMail = new General();// object of class General.
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers.
		try {
			gstrTCID = "127830"; // Test Case Id
			gstrTO = " Verify that usernames are displayed ones in the 'forgot username email' "
					+ "notification when user has access to multiple regions.";// Test// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// User
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strEMail = rdExcel.readData("WebMailUser", 3, 1);
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 3, 1);
			String strPassword = rdExcel.readData("WebMailUser", 3, 2);
			String strSubjName = "";
			String strFrom = "no-reply@emsystem.com";
			String strTo = strEMail;
			int strUserCount = 0;
			/*
			 * STEP : Action:Provide user access to 3 regions and check that
			 * username is displayed only ones in the notification received
			 * Expected Result:No Expected Result
			 */
			// 664213

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			// Login
			try {
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition,strRegn );
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// User creation
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
						seleniumPrecondition, strUserName_A, strInitPwd,
						strConfirmPwd, strUsrFulName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail, "",
						"", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(
						seleniumPrecondition, strUserName_A );
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(seleniumPrecondition,
						"2904", true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(seleniumPrecondition,
						"3003", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TESTCASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin
						.checkForgotUsernameAndPasswordLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.clickOnForgotUsernameLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.provideEmailIdInForgotUserIdPage(
						selenium, strEMail);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Verifying mail
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "forgot username email";
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");
					String[] strArrMsg = strMsg.split("\n");
					String[] strNewMsg = strArrMsg[4].split(",");
					for (String name : strNewMsg) {
						if (name.trim().equals(strUserName_A)) {
							strUserCount++;
							log4j.info("user "+ strUserName_A+" is displayed only "+strUserCount+" time");
						}
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objMail.backToMailInbox(selenium, false, true,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				if (strUserCount == 1) {
					gstrResult = "PASS";
				} else {
					gstrResult = "FAIL";
				}
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-127830";
			gstrTO = "Verify that usernames are displayed ones in the 'forgot username email' notification " +
					"when user has access to multiple regions.";
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
	// end//testFTS127830//
	
	//start//testFTS127896//
	/***************************************************************
	'Description		:Verify that appropriate message is displayed when an attempt 
						 is made to retrieve the password of the deactivated user account
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:11/11/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/
	@Test
	public void testFTS127896() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;	
		Login objLogin = new Login();// object of class Login		
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers.
		try {
			gstrTCID = "127896"; // Test Case Id
			gstrTO = " Verify that appropriate message is displayed when an attempt is made to retrieve"
					+ " the password of the deactivated user account";// Test// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// User
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 3, 1);
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			/*
			 * Verify that appropriate message is displayed when an attempt is
			 * made to retrieve the password of the deactivated user account
			 */

			log4j.info("~~~~~PRE - CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Creating user
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
						seleniumPrecondition, strUserName_A, strInitPwd,
						strConfirmPwd, strUsrFulName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "",
						strPrimaryEMail, "", "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.deactivateUser(
						seleniumPrecondition, strUserName_A, strUsrFulName_A,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TESTCASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin
						.checkForgotUsernameAndPasswordLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.clickOnForgotPasswordLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String ErrMSg="Not a valid account";
				strFuncResult = objLogin.provideUserIdAndVryErrMsgInResetPasswordPage(selenium,
						strUserName_A, ErrMSg);
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
			gstrTCID = "FTS-127896";
			gstrTO = "Verify that appropriate message is displayed when an attempt is made" +
					"to retrieve the password of the deactivated user account";
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
	// end//testFTS127896//	

	//start//testFTS127855//
	/***************************************************************
	'Description		:Verify that appropriate validation message is displayed when invalid data is
	 				 	 provided in the email field
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:11/13/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/
	@Test
	public void testFTS127855() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		try {
			gstrTCID = "127855"; // Test Case Id
			gstrTO = " Verify that appropriate validation message is displayed when invalid data is "
					+ "provided in the email field";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			/*
			 * Verify that appropriate validation message is displayed when
			 * invalid data is provided in the email field
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.vfyLoginPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin
						.checkForgotUsernameAndPasswordLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.clickOnForgotUsernameLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strEmail = "";
				String strErrMsg = "The Email field is required.";
				strFuncResult = objLogin
						.provideEmailIdAndVryErrMsgInForgotUserIdPage(selenium,
								strEmail, strErrMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strEmail = "abcd";
				String strErrMsg = "abcd is not a valid email address";
				strFuncResult = objLogin
						.provideEmailIdAndVryErrMsgInForgotUserIdPage(selenium,
								strEmail, strErrMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strEmail = "abcd@abc.com";
				String strErrMsg = "Active account not found for the email address you entered. "
						+ "Contact your administrator or click the Help link.";
				strFuncResult = objLogin
						.provideEmailIdAndVryErrMsgInForgotUserIdPage(selenium,
								strEmail, strErrMsg);
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
			gstrTCID = "FTS-127855";
			gstrTO = "Verify that appropriate validation message is displayed when invalid data is provided"
					+ " in the email field";
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

	// end//testFTS127855//
	
	//start//testFTS127894//
	/***************************************************************
	'Description		:Verify that challenge password entered for a user is not retained when the 
						 thick box is closed without saving
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:11/15/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/
	@Test
	public void testFTS127894() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;	
		General objMail = new General();// object of class General.
		Login objLogin = new Login();// object of class Login		
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers.
		Preferences objPreferences = new Preferences();// object of class Preferences.
		try {
			gstrTCID = "127894"; // Test Case Id
			gstrTO = " Verify that challenge password entered for a user is not retained "
					+ "when the thick box is closed without saving";// Test// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// User
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strQuestion = "What are the last 4 digits of your SSN?";
			String strAnswer = "444";
			/*
			 * STEP : Action:Login as a user and navigate to Preferences >
			 * Challenge Setup Expected Result:'Challenge Password' is opened in
			 * a thick box
			 */
			// 664290

			log4j.info("~~~~~PRE - CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Creating user
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
						seleniumPrecondition, strUserName_A, strInitPwd,
						strConfirmPwd, strUsrFulName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TESTCASE - " + gstrTCID
							+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences
						.navToPrefChallengeSetup(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.cancelChallengeSetup(selenium,
						strQuestion, strAnswer);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide mandatory data and click on 'Close'
			 * displayed at the top right corner of the thick box Expected
			 * Result:User is retained on the 'Challenge Setup'
			 * 
			 * 'If the Challenge Setup dialog box did not open automatically,
			 * set up your challenge by clicking here. ' is displayed on the
			 * screen
			 */
			// 665214
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				String strText = "Challenge Setup";
				String strElementID = "//div[@id='topSubNav']/h1";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strText
						+ " User is retained on the 'Challenge Setup' ");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				String strText = "If the Challenge Setup dialog box did not open automatically, "
						+ "set up your challenge by clicking here.";
				String strElementID = "//table/tbody/tr/td[@class='emsText']";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info("If the Challenge Setup dialog box did not open automatically, set up your challenge by clicking here.  is dispalyed");

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
			gstrTCID = "FTS-127894";
			gstrTO = "Verify that challenge password entered for a user is not retained when the" +
					" thick box is closed without saving";
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
	
	// end//testFTS127894//
}
