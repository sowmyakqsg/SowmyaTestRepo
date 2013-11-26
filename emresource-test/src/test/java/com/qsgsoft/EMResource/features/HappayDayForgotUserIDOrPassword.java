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
' Date			    :29/Oct/2013
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class HappayDayForgotUserIDOrPassword {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.HappayDayForgotUserIDOrPassword");
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

	//start//testBQS127799//
	/******************************************************************************
	'Description		:Verify that user cannot retrieve the username by providing 
	                     email address associated with his/her account
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/29/2013
	'Author				:QSG
	'------------------------------------------------------------------------------
	'Modified Date							                       Modified By
	'Date									                       Name
	*******************************************************************************/
	@Test
	public void testBQS127799() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		General objMail = new General();// object of class General.
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers.
		try {
			gstrTCID = "127799"; // Test Case Id
			gstrTO = " Verify that user cannot retrieve the username by providing email address associated "
					+ "with his/her account";// Test// Objective
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
			 * STEP : Action:Provide email address associated with a user and
			 * attempt to retrieve the username Expected Result:Appropriate
			 * validation message is displayed and the notifications are not
			 * received
			 */
			// 664215

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
						seleniumPrecondition, "", "", "", "", "", "", strEMail,
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
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
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
						log4j.info(" User "+strUserName_A+" is retrived");
						strFuncResult =" User "+strUserName_A+" is retrived";
						gstrReason = strFuncResult;
					} else {
						log4j.info(" User "+strUserName_A+" is NOT retrived");
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
			gstrTCID = "BQS-127799";
			gstrTO = "Verify that user cannot retrieve the username by providing email " +
					"address associated with his/her account";
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

	// end//testBQS127799//

   //start//testBQS127692//
	/***********************************************************************
	'Description	:Verify that user can retrieve the username by providing  
	                 primary email address associated with his/her account
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/29/2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	************************************************************************/

	@Test
	public void testBQS127692() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		General objMail = new General();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "127692"; // Test Case Id
			gstrTO = " Verify that user can retrieve the username by providing "
					+ "primary email address associated with his/her account";// TO
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
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 3, 1);
			String strPassword = rdExcel.readData("WebMailUser", 3, 2);
			String strSubjName = "";
			String strFrom = "no-reply@emsystem.com";
			String strTo = strPrimaryEMail;
		/*
		* STEP :
		  Action:Create a user providing valid primary email address
		  Provide the same email id by clicking on the 'forgot username?' field on login screen
		  Expected Result:'forgot username mail' is received for the user and the appropriate 
		  username is displayed in the notification received.
		*/
		//664115

           log4j.info("~~~~~PRE - CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			
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
			// user

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
			
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
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
						selenium, strPrimaryEMail);
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
						log4j.info(" User "+strUserName_A+" is retrived");
					} else {
						log4j.info(" User "+strUserName_A+" is NOT  retrived");
						strFuncResult="User "+strUserName_A+" is NOT  retrived";
						gstrReason = gstrReason + " " + strFuncResult;
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
			gstrTCID = "BQS-127692";
			gstrTO = "Verify that user can retrieve the username by providing primary"
					+ " email address associated with his/her account";
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

	// end//testBQS127692//
	
	//start//testBQS127825//
	/***************************************************************
	'Description		:Verify that web service user can retrieve the username by providing email address 
						 associated with his/her account
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/30/2013
	'Author				:Suhas
	'---------------------------------------------------------------
	'Modified Date								Modified By
	'Date										Name
	***************************************************************/
	@Test
	public void testBQS127825() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		General objMail = new General();// object of class General.
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers.
	try{	
		gstrTCID = "127825";	//Test Case Id	
		gstrTO = " Verify that web service user can retrieve the username by providing email " +
				"address associated with his/her account";//Test Objective
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
		String strUserName_B = "AutoUsr_B" + System.currentTimeMillis();
		String strUsrFulName_B = strUserName_B;
		String strInitPwd = rdExcel.readData("Login", 4, 2);
		String strConfirmPwd = rdExcel.readData("Login", 4, 2);
		String strEMail = rdExcel.readData("WebMailUser", 3, 1);
		String strEMail1 = rdExcel.readData("WebMailUser", 2, 1);
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
		String strLoginName1 = rdExcel.readData("WebMailUser", 2, 1);
		String strPassword1 = rdExcel.readData("WebMailUser", 2, 2);
		String strSubjName = "";
		String strFrom = "no-reply@emsystem.com";
		String strTo = strEMail;
		String strTo1 = strEMail1;
		/*
		* STEP :
		  Action:Create two web service users and provide primary email and email addresses for the users respectively
	
		User U1 is provided with only primary email id
		User U2 is provided with another id in email address
		  Expected Result:In the notification received, appropriate web service user names are retrieved
		*/
		//664200
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
					seleniumPrecondition, "", "", "", "", "",strEMail,"",
					"", "");
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.selAndDeselWebServicesUserCheckBox(seleniumPrecondition, true);
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
			strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
					seleniumPrecondition, strUserName_B, strInitPwd,
					strConfirmPwd, strUsrFulName_B);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
					seleniumPrecondition, "", "", "", "", "","",strEMail1,
					"", "");
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.selAndDeselWebServicesUserCheckBox(seleniumPrecondition, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
					seleniumPrecondition, strUserName_B, strByRole,
					strByResourceType, strByUserInfo, strNameFormat);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		log4j.info("~~~~~PRECONDITION - " + gstrTCID
				+ " EXECUTION ENDS~~~~~");
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.logout(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
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
					selenium, strEMail1);
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
					log4j.info(" User "+strUserName_A+" is retrived");					
					
				} else {
					log4j.info(" User "+strUserName_A+" is NOT retrived");
					strFuncResult =" User "+strUserName_A+" is NOT retrived";
					gstrReason = strFuncResult;
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
			selenium.selectWindow("");
			strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
					strLoginName1, strPassword1);
			try {
				assertTrue(strFuncResult.equals(""));
				strSubjName = "forgot username email";
				strFuncResult = objMail.verifyEmail(selenium, strFrom,
						strTo1, strSubjName);
				assertTrue(strFuncResult.equals(""));
				String strMsg = selenium.getText("css=div.fixed.leftAlign");
				strMsg = objMail.seleniumGetText(selenium,
						"css=div.fixed.leftAlign", 160);
				if (strMsg.contains(strUserName_B)) {
					log4j.info(" User "+strUserName_B+" is retrived");	
					strFuncResult =" User "+strUserName_A+" is retrived";
					gstrReason = strFuncResult;
					
				} else {
					log4j.info(" User "+strUserName_B+" is NOT retrived");					
					
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
		gstrTCID = "BQS-127825";
		gstrTO = "Verify that web service user can retrieve the username by providing email address associated with his/her account";
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

	//end//testBQS127825//	

	//start//testBQS127849//
	/***************************************************************
	'Description		:Verify that uploaded users can retrieve the username by providing primary
	 					 email address associated with his/her account
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:11/6/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date						Modified By
	'Date								Name
	***************************************************************/
	@Test
	public void testBQS127849() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Upload objUpload = new Upload();
		Roles objRoles = new Roles();
		General objMail = new General();
		try {
			gstrTCID = "127849"; // Test Case Id
			gstrTO = " Verify that uploaded users can retrieve the username by providing primary email" +
					 " address associated with his/her account";// Test	// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";
			// User
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUserName_B = "AutoUsr_B" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strUsrFulName_B = strUserName_B;
			String strInitPwd = rdExcel.readData("Login", 4, 2);			
			String strEMail = rdExcel.readData("WebMailUser", 3, 1);
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 3, 1);
			String strPassword = rdExcel.readData("WebMailUser", 3, 2);
			String strSubjName = "forgot username email";
			String strFrom = "no-reply@emsystem.com";
			String strTo = strEMail;
			/*
			 * STEP : Action:Upload users U1, U2 with primary email provided for
			 * U1, email and pager provided for U2 Provide primary email and
			 * check user U1's username is retrieved in the notification
			 * received Provide email and pager ids user U2 will not receive the
			 * notification and appropriate validation message is displayed
			 * Expected Result:No Expected Result
			 */
			// 664236
			// Login
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

			// Creating Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navCreateRolePge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.roleMandtoryFlds(selenium,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(selenium,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Downloading Upload Template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Path");

				String strDownloadPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp" + gstrTCID + strTimeText + ".xls";

				String strAutoFileName = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Name");

				strFuncResult = objUpload.downloadUploadTemplate(selenium,
						strAutoFilePath, strAutoFileName, strDownloadPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Downloading Upload Template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Path");

				String strDownloadPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp2" + gstrTCID + strTimeText + ".xls";

				String strAutoFileName = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Name");

				strFuncResult = objUpload.downloadUploadTemplate(selenium,
						strAutoFilePath, strAutoFileName, strDownloadPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Writing data in Template
			try {
				assertEquals("", strFuncResult);

				String[] strTestData = { "", "N", "", "", "", "", "", "", "N",
						"N", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", strUserName_A, strRoleValue, strRolesName,
						strInitPwd, strUsrFulName_A, "", "", "", "", "",
						strEMail, "", "", "", "", "" };

				String strWriteFilePath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp" + gstrTCID + strTimeText + ".xls";

				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 1);

			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
			// Writing data in Template
			try {
				assertEquals("", strFuncResult);

				String[] strTestData = { "", "N", "", "", "", "", "", "", "N",
						"N", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", strUserName_B, strRoleValue, strRolesName,
						strInitPwd, strUsrFulName_B, "", "", "", "", "", "",
						strEMail, strEMail, "", "", "" };

				String strWriteFilePath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp2" + gstrTCID + strTimeText + ".xls";

				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 1);

			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}

			// Uploading template by selecting test(Actual upload )
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				String strUploadFilePath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp" + gstrTCID + strTimeText + ".xls";

				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUploadFilePath, strAutoFileName,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Uploading template by selecting test(Actual upload )
			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				String strUploadFilePath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp2" + gstrTCID + strTimeText + ".xls";

				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUploadFilePath, strAutoFileName,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			log4j
					.info("~~~~~TESTCASE - " + gstrTCID
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");
					strMsg = objMail.seleniumGetText(selenium,
							"css=div.fixed.leftAlign", 160);
					if (strMsg.contains(strUserName_A)) {
						log4j.info(" User " + strUserName_A + " is retrived");

					} else {
						log4j.info(" User " + strUserName_A
								+ " is NOT retrived");
						strFuncResult = " User " + strUserName_A
								+ " is NOT retrived";
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objMail.backToMailInbox(selenium, true, false,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strSubjName = "forgot username email";
				strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
						strSubjName);
				assertTrue(strFuncResult.equals(""));
				String strMsg = selenium.getText("css=div.fixed.leftAlign");
				strMsg = objMail.seleniumGetText(selenium,
						"css=div.fixed.leftAlign", 160);
				if (strMsg.contains(strUserName_B)) {
					log4j.info(" User " + strUserName_B + " is retrived");
					strFuncResult = " User " + strUserName_A + " is retrived";
					gstrReason = strFuncResult;

				} else {
					log4j.info(" User " + strUserName_B + " is NOT retrived");

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
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-127849";
			gstrTO = "Verify that uploaded users can retrieve the username by providing primary " +
					"email address associated with his/her account";
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

	// end//testBQS127849//
	
	//start//testBQS127691//
	/***************************************************************
	'Description		:Verify that user can reset password by answering the challenge password 
						 questions set for the user.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:11/7/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/
	@Test
	public void testBQS127691() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		General objMail = new General();// object of class General.
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers.
		Preferences objPreferences = new Preferences();// object of class Preferences.
		try {
			gstrTCID = "127691"; // Test Case Id
			gstrTO = " Verify that user can reset password by answering the challenge password" +
					" uestions set for the user.";// Test// Objective
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
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 3, 1);
			String strPassword = rdExcel.readData("WebMailUser", 3, 2);
			String strSubjName = "";
			String strFrom = "no-reply@emsystem.com";
			String strTo = strPrimaryEMail;
			String strLink = "";
			String[] strMailRes = new String[1];
			String strQusetion = "What are the last 4 digits of your SSN?";
			String strAnswer = "444";
			String strNewPwd = "abc456";
			/*
			 * STEP : Action:Refer to the attached video Expected Result:No
			 * Expected Result
			 */
			// 664237
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
			// user

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
				strFuncResult = objPreferences
						.selQusAndProvideAnswerInChallengeSetup(selenium,
								strQusetion, strAnswer);
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
				strFuncResult = objLogin.provideUserIdInResetPasswordPage(
						selenium, strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "password reset email";
					strMailRes = objMail.verifyEmailAndFetchLink(selenium,
							strFrom, strTo, strSubjName, strLink);
					assertTrue(strMailRes[0].equals(""));
					strLink = strMailRes[1];
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
				strFuncResult = objLogin.provideAnswerInPasswordChallengePage(
						selenium, strAnswer, strQusetion, strLink);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.resetPassword(selenium, strUserName_A,
						strNewPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName_A,
						strNewPwd);
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
			gstrTCID = "BQS-127691";
			gstrTO = "Verify that user can reset password by answering the challenge password questions" +
						" set for the user.";
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

	// end//testBQS127691//	

	//start//testBQS127893//
	/***************************************************************
	'Description		:Verify that user can edit the challenge password questions set from
	 					 Preferences > Challenge Password
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
	public void testBQS127893() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		General objMail = new General();// object of class General.
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers.
		Preferences objPreferences = new Preferences();// object of class Preferences.
		try {
			gstrTCID = "127893"; // Test Case Id
			gstrTO = " Verify that user can edit the challenge password questions set from "
					+ "Preferences > Challenge Password";// Test Objective
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
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 3, 1);
			String strPassword = rdExcel.readData("WebMailUser", 3, 2);
			String strSubjName = "";
			String strFrom = "no-reply@emsystem.com";
			String strFrom1="no-reply@intermedix.com";
			String strTo = strPrimaryEMail;
			String strMsgBody = "";
			String strLink = "";
			String[] strMailRes = new String[1];
			String strQusetion = "What are the last 4 digits of your SSN?";
			String strAnswer = "444";
			String strQusetion1 = "What city were you born in?";
			String strAnswer1 = "Bangalore";
			String strNewPwd = "abc456";
			/*
			 * STEP : Action:Challenge setup question is set for user U1.
			 * 
			 * Login as user U1, edit the challenge setup question. Logout and
			 * click on 'forgot Password?' link on the login screen and provide
			 * user id.
			 * 
			 * User receives the 'Password reset email', click on the link and
			 * check that the user is provided with the edited (updated)
			 * question.
			 * 
			 * When answer is submitted, user is taken to the change password
			 * screen. After resetting the password, User receives 'Your
			 * password has changed' notification. Expected Result:No Expected
			 * Result
			 */
			// 664289
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
				strFuncResult = objPreferences
						.selQusAndProvideAnswerInChallengeSetup(selenium,
								strQusetion, strAnswer);
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
				strFuncResult = objLogin.login(selenium, strUserName_A,
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
				strFuncResult = objPreferences
						.selQusAndProvideAnswerInChallengeSetup(selenium,
								strQusetion1, strAnswer1);
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
				strFuncResult = objLogin.provideUserIdInResetPasswordPage(
						selenium, strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "password reset email";
					strMailRes = objMail.verifyEmailAndFetchLink(selenium,
							strFrom, strTo, strSubjName, strLink);
					assertTrue(strMailRes[0].equals(""));
					strLink = strMailRes[1];
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
				strFuncResult = objLogin.provideAnswerInPasswordChallengePage(
						selenium, strAnswer1, strQusetion1, strLink);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.resetPassword(selenium, strUserName_A,
						strNewPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName_A,
						strNewPwd);
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
				Thread.sleep(10000);
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				strSubjName = "Your password has changed";
				try {
					assertTrue(strFuncResult.equals(""));
					strMsgBody = "You are receiving this announcement to inform you that your "
						+ "Intermedix EMSystems password (for EMResource, EMTrack, Hospital-ICS, or Inventory Management)"
						+ " was recently changed. If you made this change, you don't need to do anything more.\n\n"
						+ "If your administrator or Intermedix Support reset your password, you may be required to change it the next time you log in "
						+ "to the product. Even if the product does not require you to do so, we recommend that you reset your password at this time.\n\n"
						+ "If you did not change your password and you believe your administrator did not reset it, contact the Intermedix Support Center:"
						+ "\nCall: 1-888-735-9559, press 1 for Client Application Support and then 6 for EMSystems"
						+ "\nEmail: support@intermedix.com"
						+ "\n\nRespectfully," + "\nIntermedix";
					strFuncResult = objMail.verifyEmail(selenium, strFrom1,
							strTo, strSubjName);
					String strMsg = selenium.getText("css=div.fixed.leftAlign");
					String[] strMails = objMail.enodeToUnicode(selenium,
							strMsg, 160);
					strMsg = strMails[1];
					if (strMsg.equals(strMsgBody)) {
						log4j.info(" password has changed notification is displayed ");
						strFuncResult = "";
					} else {
						log4j.info("password has changed notification is NOT displayed ");
						gstrReason = "password has changed notification is NOT displayed ";
						strFuncResult = gstrReason;
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
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-127893";
			gstrTO = "Verify that user can edit the challenge password questions set from "
					+ "Preferences > Challenge Password";
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
	// end//testBQS127893//
	
	//start//testBQS127884//
	/***************************************************************
	'Description		:Verify that when user has access to multiple regions the changes done to 
						 the challenge setup are reflected across all the regions
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:11/11/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date				  				Modified By
	'Date					  					Name
	***************************************************************/
	@Test
	public void testBQS127884() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		General objMail = new General();// object of class General.
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers.
		Preferences objPreferences = new Preferences();// object of class Preferences.
		try {
			gstrTCID = "127884"; // Test Case Id
			gstrTO = " Verify that when user has access to multiple regions the changes done to the"
					+ "challenge setup are reflected across all the regions";// Test// Objective
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
			String strRegn1 = rdExcel.readData("Login", 26, 4);
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
			String strQuestion1 = "What city were you born in?";
			String strAnswer1 = "Florida";
			String strQuestion = "What are the last 4 digits of your SSN?";
			String strAnswer = "123";
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 3, 1);
			String strPassword = rdExcel.readData("WebMailUser", 3, 2);
			String strSubjName = "";
			String strFrom = "no-reply@emsystem.com";
			String strFrom1 = "no-reply@intermedix.com";
			String strTo = strPrimaryEMail;
			String strMsgBody = "";
			String strLink = "";
			String[] strMailRes = new String[1];
			String strNewPwd = "abc789";
			/*
			 * STEP : Action:Create a user in RG1 and provide access to region
			 * RG2.
			 * 
			 * Login as user U1 in RG1, set a challenge setup question. Login as
			 * user U1 in RG2 and check if the challenge setup made for the user
			 * in RG1 is same as in RG2 system admin changes the pwd of user U1
			 * in region RG2. Expected Result:No Expected Result
			 */
			// 664286
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
				strFuncResult = objCreateUsers.navEditUserRegions(
						seleniumPrecondition, strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(
						seleniumPrecondition, "2904", true, true);
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_A,
						strInitPwd);
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
				blnLogin = true;
				strFuncResult = objPreferences
						.navToPrefChallengeSetup(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences
						.selQusAndProvideAnswerInChallengeSetup(selenium,
								strQuestion, strAnswer);
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
				strFuncResult = objLogin.login(selenium, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn1);
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
				strFuncResult = objPreferences.chkDataRetainedInChallengeSetup(
						selenium, strQuestion, strAnswer);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences
						.selQusAndProvideAnswerInChallengeSetup(selenium,
								strQuestion1, strAnswer1);
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
				strFuncResult = objLogin.provideUserIdInResetPasswordPage(
						selenium, strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "password reset email";
					strMailRes = objMail.verifyEmailAndFetchLink(selenium,
							strFrom, strTo, strSubjName, strLink);
					assertTrue(strMailRes[0].equals(""));
					strLink = strMailRes[1];
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
				strFuncResult = objLogin.provideAnswerInPasswordChallengePage(
						selenium, strAnswer1, strQuestion1, strLink);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.resetPassword(selenium, strUserName_A,
						strNewPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName_A,
						strNewPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn1);
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
				Thread.sleep(10000);
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				strSubjName = "Your password has changed";
				try {
					assertTrue(strFuncResult.equals(""));
					strMsgBody = "You are receiving this announcement to inform you that your "
							+ "Intermedix EMSystems password (for EMResource, EMTrack, Hospital-ICS, or Inventory Management)"
							+ " was recently changed. If you made this change, you don't need to do anything more.\n\n"
							+ "If your administrator or Intermedix Support reset your password, you may be required to change it the next time you log in "
							+ "to the product. Even if the product does not require you to do so, we recommend that you reset your password at this time.\n\n"
							+ "If you did not change your password and you believe your administrator did not reset it, contact the Intermedix Support Center:"
							+ "\nCall: 1-888-735-9559, press 1 for Client Application Support and then 6 for EMSystems"
							+ "\nEmail: support@intermedix.com"
							+ "\n\nRespectfully," + "\nIntermedix";
					strFuncResult = objMail.verifyEmail(selenium, strFrom1,
							strTo, strSubjName);
					String strMsg = selenium.getText("css=div.fixed.leftAlign");
					strMsg = objMail.seleniumGetText(selenium,
							"css=div.fixed.leftAlign", 160);
					String[] strMails = objMail.enodeToUnicode(selenium,
							strMsg, 160);
					strMsg = strMails[1];
					if (strMsg.equals(strMsgBody)) {
						log4j.info(" password has changed notification is displayed ");
						strFuncResult = "";
					} else {
						log4j.info("password has changed notification is NOT displayed ");
						gstrReason = "password has changed notification is NOT displayed ";
						strFuncResult = gstrReason;
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
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-127884";
			gstrTO = "Verify that when user has access to multiple regions the changes done "
					+ "to the challenge setup are reflected across all the regions";
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
	// end//testBQS127884//
}
