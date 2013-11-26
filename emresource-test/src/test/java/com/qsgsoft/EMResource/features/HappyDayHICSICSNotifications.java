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
' Requirement Group	:Notifications 
' Requirement 	    :ICS Notifications
� Product		    :EMResource v3.24
' Date			    :17/Sep/2013
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class HappyDayHICSICSNotifications {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.HICS_ICSNotifications");
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
	Selenium selenium,seleniumPrecondition;

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
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
						.getProperty("urlEU"));

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

  //start//testBQS125746//
	/*****************************************************************************************
	'Description	:Verify that user with update status right on a resource receives the ICS 
	                 notification when an incident is created in HICS selecting that facility.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/11/2013
	'Author			:QSG
	'-----------------------------------------------------------------------------------------
	'Modified Date				                                               Modified By
	'Date					                                                   Name
	******************************************************************************************/
	@Test
	public void testBQS125746() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Resources objRs = new Resources();//Object of class Resources
		General objMail = new General();//Object of class General
		EventNotification objEventNotification = new EventNotification();//Object of class EventNotification
		CreateUsers objCreateUsers = new CreateUsers();//Object of class CreateUsers
		HICSNotificationsFunctions objHICS_Functions = new HICSNotificationsFunctions();//Object of class HICSNotificationsFunctions
		try {
			gstrTCID = "125746"; // Test Case Id
			gstrTO = "Verify that user with update status right on a resource receives the ICS notification"
					+ " when an incident is created in HICS selecting that facility.";// TO Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 14, 4);
			String pStrUserName = rdExcel.readData("HICS", 2, 1);
			String pStrPassword = rdExcel.readData("HICS", 2, 2);
			String strExistingResource = rdExcel.readData("HICS", 4, 3);
			String strHICSResource = rdExcel.readData("HICS", 4, 4);
			String[] strRSValue = new String[1];
			//User
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
			String strMsgBody1 = "";
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			String pStrIncName = "Inc"+strTimeText;
			String strIncDesc = "Inccreation"+strTimeText;
			String pStrIRGName = rdExcel.readData("HICS", 4, 5);			

		/*
		 * STEP:Action:Create an incident in HICS selecting facility 'X' that is mapped with EMR.
           Create user in EMR with email and pager addresses, update right on 'X' and ICS notifications selected 		 
		 * Expected Result:No Expected Result 
		 */
		// 660729

			log4j.info("-----Precondtion for test case " + gstrTCID+ " starts------");

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strExistingResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user Creation
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
						seleniumPrecondition, "", "", "", "", "", strEMail,
						strEMail, strEMail, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strExistingResource,
						strRSValue[0], false, true, false, true);
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
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						seleniumPrecondition, strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselWebInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.loginAsSystemAdmin(selenium,
						pStrUserName, pStrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.EndIncidentForFacility(
						selenium, strHICSResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.navToCreatIncidentPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.selFacilityAndVerIRGPresent(
						selenium, strHICSResource, pStrIRGName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.CreateIncidentWithIncidentName(selenium, pStrIRGName,
								pStrIncName, strIncDesc);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.LogoutWithConfirmation(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				strMsgBody1 = "*** THIS IS AN ACTUAL INCIDENT *** "
					+ "\n"
					+ " This message is to inform you that "
					+ strExistingResource
					+ " is experiencing "
					+ pStrIncName
					+ ". "
					+ "\n"
					+ " "
					+ strIncDesc+". "+"\n"
					+ " * Contact your facility with any questions. Do not reply directly to this email.";
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));					
					strSubjName = "Incident Alert for " + strExistingResource;	
					for (int i = 0; i < 3; i++) {
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);
						if (strMsg.equals(strMsgBody1)) {
							log4j.info(strMsgBody1
									+ " is displayed in Notification");
						} else {
							log4j.info(strMsgBody1
									+ " is NOT displayed in Notification");
							strFuncResult = strMsgBody1
									+ " is NOT displayed in Notification";
						}
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");
				}
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad(gstrTimeOut);
					selenium.close();
					selenium.selectWindow("");
					Thread.sleep(2000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_A, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);				
				String strDesc ="*** THIS IS AN ACTUAL INCIDENT *** This message is to inform you" +
						" that "+strExistingResource+" is experiencing " +
						pStrIncName+". "+strIncDesc+". * Contact your facility with any questions. Do not reply directly to this email.";				
				strFuncResult = objEventNotification.ackHicsWebNotification(
						selenium, strSubjName, strDesc);
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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
				strFuncResult = objCreateUsers.navEditUserPge(
						selenium, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						selenium, strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						selenium, "ICS", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						selenium, "ICS", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselWebInSysNotfPref(
						selenium, "ICS", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(selenium,strUserName_A);
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
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "125746"; // Test Case Id
			gstrTO = "Verify that user with update status right on a resource receives the ICS notification"
					+ " when an incident is created in HICS selecting that facility.";// TO
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

	// end//testBQS125746//

	//start//testBQS125747//
	/*****************************************************************************************
	'Description	:Verify that user with run report right on a resource receives the ICS  
	                 notification when an incident is created in HICS selecting that facility.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/13/2013
	'Author			:QSG
	'-----------------------------------------------------------------------------------------
	'Modified Date				                                                 Modified By
	'Date					                                                     Name
	*****************************************************************************************/
	@Test
	public void testBQS125747() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Resources objRs = new Resources();//object of class Resources
		EventNotification objEventNotification = new EventNotification();//Object of class EventNotification
		General objMail = new General();//object of class General
		CreateUsers objCreateUsers = new CreateUsers();//object of class Users
		HICSNotificationsFunctions objHICS_Functions = new HICSNotificationsFunctions();//object of class HICSNotificationsFunctions
		SearchUserByDiffCrteria objSearchUser = new SearchUserByDiffCrteria();//object of class SearchUserByDiffCrteria
		try {
			gstrTCID = "125747"; // Test Case Id
			gstrTO = "Verify that user with run report right on a resource receives the ICS " +
					"notification when an incident is created in HICS selecting that facility.";// TO
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 14, 4);
			String pStrUserName = rdExcel.readData("HICS", 2, 1);
			String pStrPassword = rdExcel.readData("HICS", 2, 2);
			String strExistingResource = rdExcel.readData("HICS", 5, 3);
			String strHICSResource = rdExcel.readData("HICS", 5, 4);
			String[] strRSValue = new String[1];
			//User 
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
			String strMsgBody1 = "";
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			String pStrIncName = "Inc"+strTimeText;
			String strIncDesc = "Inccreation"+strTimeText;
			String pStrIRGName = rdExcel.readData("HICS", 5, 5);
		/*
		 * STEP:Action:Create an incident in HICS selecting facility 'X' that is mapped with EMR.
           Create user in EMR with email and pager addresses, update right on 'X' and ICS notifications selected 		 
		 * Expected Result:No Expected Result 
		 */
		// 660729
			log4j.info("-----Precondtion for test case " + gstrTCID+ " starts------");

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strExistingResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user Creation
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
						seleniumPrecondition, "", "", "", "", "", strEMail,
						strEMail, strEMail, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strExistingResource,
						strRSValue[0], false, false, true, true);
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
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						seleniumPrecondition, strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselWebInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

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
				strFuncResult = objHICS_Functions.loginAsSystemAdmin(selenium,
						pStrUserName, pStrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.EndIncidentForFacility(
						selenium, strHICSResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.navToCreatIncidentPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.selFacilityAndVerIRGPresent(
						selenium, strHICSResource, pStrIRGName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.CreateIncidentWithIncidentName(selenium, pStrIRGName,
								pStrIncName, strIncDesc);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.LogoutWithConfirmation(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				strMsgBody1 = "*** THIS IS AN ACTUAL INCIDENT *** "
					+ "\n"
					+ " This message is to inform you that "
					+ strExistingResource
					+ " is experiencing "
					+ pStrIncName
					+ ". "
					+ "\n"
					+ " "
					+ strIncDesc+". "+"\n"
					+ " * Contact your facility with any questions. Do not reply directly to this email.";
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "Incident Alert for " + strExistingResource;					
					for (int i = 0; i < 3; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							strMsg = objMail.seleniumGetText(selenium,
									"css=div.fixed.leftAlign", 160);

							if (strMsg.equals(strMsgBody1)) {
								log4j.info(strMsgBody1
										+ " is displayed in Notification");
							} else {
								log4j.info(strMsgBody1
										+ " is NOT displayed in Notification");
								gstrReason = strMsgBody1
										+ " is NOT displayed in Notification";
								strFuncResult=gstrReason;
							}
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}
						selenium.selectFrame("relative=up");
						selenium.selectFrame("horde_main");
						// click on Back to Inbox
						selenium.click("link=Back to Inbox");
						selenium.waitForPageToLoad("90000");
					}
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad(gstrTimeOut);
					selenium.close();
					selenium.selectWindow("");
					Thread.sleep(2000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_A, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);				
				String strDesc ="*** THIS IS AN ACTUAL INCIDENT *** This message is to inform you" +
						" that "+strExistingResource+" is experiencing " +
						pStrIncName+". "+strIncDesc+". * Contact your facility with any questions. Do not reply directly to this email.";				
				strFuncResult = objEventNotification.ackHicsWebNotification(
						selenium, strSubjName, strDesc);
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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
				strFuncResult = objSearchUser.searchUserByDifCriteria(selenium,
						strUserName_A, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.deactivateUser(selenium,
						strUserName_A, strUsrFulName_A, true);
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
			gstrTCID = "125747"; // Test Case Id
			gstrTO = "Verify that user with run report right on a resource receives the ICS notification when an "
					+ "incident is created in HICS selecting that facility.";// TO
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
	// end//testBQS125747//	
	
//start//testBQS125746//
	/*****************************************************************************************
	'Description	:Verify that user with associate with right on a resource receives the ICS 
	                  notification when an incident is created under that facility in HICS
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/11/2013
	'Author			:QSG
	'-----------------------------------------------------------------------------------------
	'Modified Date				                                               Modified By
	'Date					                                                   Name
	******************************************************************************************/
	@Test
	public void testBQS125748() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login.
		Resources objRs = new Resources();//object of class Resources.
		General objMail = new General();//object of class General.
		EventNotification objEventNotification = new EventNotification();//Object of class EventNotification
		CreateUsers objCreateUsers = new CreateUsers();//object of class CreateUsers.
		HICSNotificationsFunctions objHICS_Functions = new HICSNotificationsFunctions();//object of class HICSNotificationsFunctions.
		SearchUserByDiffCrteria objSearchUser = new SearchUserByDiffCrteria();//object of class SearchUserByDiffCrteria.
		try {
			gstrTCID = "125748"; // Test Case Id
			gstrTO = "Verify that user with associate with right on a resource receives the ICS notification" +
					" when an incident is created under that facility in HICS";// TO
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 14, 4);
			String pStrUserName = rdExcel.readData("HICS", 2, 1);
			String pStrPassword = rdExcel.readData("HICS", 2, 2);
			String strExistingResource = rdExcel.readData("HICS", 6, 3);
			String strHICSResource = rdExcel.readData("HICS", 6, 4);
			String[] strRSValue = new String[1];
			//User
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
			String strMsgBody1 = "";
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			String pStrIncName = "Inc"+strTimeText;
			String strIncDesc = "Inccreation"+strTimeText;
			String pStrIRGName = rdExcel.readData("HICS", 6, 5);

		/*
		 * STEP:Action:Create an incident in HICS selecting facility 'X' that is mapped with EMR.
           Create user in EMR with email and pager addresses, associate with right on 'X' and ICS 
           notifications selected 
		 * Expected Result:No Expected Result 
		 */
		// 660729
			log4j.info("-----Precondtion for test case " + gstrTCID+ " starts------");

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strExistingResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user Creation
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
						seleniumPrecondition, "", "", "", "", "", strEMail,
						strEMail, strEMail, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strExistingResource,
						strRSValue[0], true, false, false, true);
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
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						seleniumPrecondition, strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselWebInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.loginAsSystemAdmin(selenium,
						pStrUserName, pStrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.EndIncidentForFacility(
						selenium, strHICSResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.navToCreatIncidentPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.selFacilityAndVerIRGPresent(
						selenium, strHICSResource, pStrIRGName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.CreateIncidentWithIncidentName(selenium, pStrIRGName,
								pStrIncName, strIncDesc);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.LogoutWithConfirmation(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				strMsgBody1 = "*** THIS IS AN ACTUAL INCIDENT *** "
					+ "\n"
					+ " This message is to inform you that "
					+ strExistingResource
					+ " is experiencing "
					+ pStrIncName
					+ ". "
					+ "\n"
					+ " "
					+ strIncDesc+". "+"\n"
					+ " * Contact your facility with any questions. Do not reply directly to this email.";
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));					
					strSubjName = "Incident Alert for " + strExistingResource;					
					for (int i = 0; i < 3; i++) {
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);

						if (strMsg.equals(strMsgBody1)) {
							log4j.info(strMsgBody1
									+ " is displayed in Notification");
						} else {
							log4j.info(strMsgBody1
									+ " is NOT displayed in Notification");
							gstrReason = strMsgBody1
									+ " is NOT displayed in Notification";
							strFuncResult=gstrReason;
						}
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");
				}
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad(gstrTimeOut);
					selenium.close();
					selenium.selectWindow("");
					Thread.sleep(2000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_A, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);				
				String strDesc ="*** THIS IS AN ACTUAL INCIDENT *** This message is to inform you" +
						" that "+strExistingResource+" is experiencing " +
						pStrIncName+". "+strIncDesc+". * Contact your facility with any questions. Do not reply directly to this email.";				
				strFuncResult = objEventNotification.ackHicsWebNotification(
						selenium, strSubjName, strDesc);
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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
				strFuncResult = objSearchUser.searchUserByDifCriteria(selenium,
						strUserName_A, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.deactivateUser(selenium,
						strUserName_A, strUsrFulName_A, true);
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
			gstrTCID = "125748"; // Test Case Id
			gstrTO = "Verify that user with associate with right on a resource receives the ICS notification" +
					" when an incident is created under that facility in HICS";// TO
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

	// end//testBQS125748//
	
	//start//testBQS125749//
	/*****************************************************************************************
	'Description	:Verify that user with Override viewing restrictions right receives the
	                 ICS notification when an incident is created under that facility in HICS
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/18/2013
	'Author			:QSG
	'-----------------------------------------------------------------------------------------
	'Modified Date									Modified By
	'Date											Name
	******************************************************************************************/
	@Test
	public void testBQS125749() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Resources objRs = new Resources();//object of class Resources.
		EventNotification objEventNotification = new EventNotification();//Object of class EventNotification
		General objMail = new General();//object of class General.
		CreateUsers objCreateUsers = new CreateUsers();//object of class CreateUsers.
		HICSNotificationsFunctions objHICS_Functions = new HICSNotificationsFunctions();//object of class HICSNotificationsFunctions.
		SearchUserByDiffCrteria objSearchUser = new SearchUserByDiffCrteria();//object of class SearchUserByDiffCrteria.
		try {
			gstrTCID = "125749"; // Test Case Id
			gstrTO = " Verify that user with Override viewing restrictions right receives the ICS notification " +
					"when an incident is created under that facility in HICS";//TO 																						// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 14, 4);
			String pStrUserName = rdExcel.readData("HICS", 2, 1);
			String pStrPassword = rdExcel.readData("HICS", 2, 2);
			String strExistingResource = rdExcel.readData("HICS", 7, 3);
			String strHICSResource = rdExcel.readData("HICS", 7, 4);
			String[] strRSValue = new String[1];
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
			String strMsgBody1 = "";
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			String pStrIncName = "Inc" + strTimeText;
			String strIncDesc = "Inccreation" + strTimeText;
			String pStrIRGName = rdExcel.readData("HICS", 7, 5);

			/*
			 * STEP : Action:Create an incident in HICS selecting facility 'X'
			 * that is mapped with EMR. Create user in EMR with email and pager
			 * addresses, view resource right on 'X' and ICS notifications
			 * selected, Override viewing restrictions right Expected Result:No
			 * Expected Result
			 */
			// 662069

			log4j.info("-----Precondtion for test case " + gstrTCID
					+ " starts------");

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strExistingResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user Creation
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
						seleniumPrecondition, "", "", "", "", "", strEMail,
						strEMail, strEMail, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strExistingResource,
						strRSValue[0], false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.OverrideViewingRestrictions");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						seleniumPrecondition, strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselWebInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
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
				strFuncResult = objHICS_Functions.loginAsSystemAdmin(selenium,
						pStrUserName, pStrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.EndIncidentForFacility(
						selenium, strHICSResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.navToCreatIncidentPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.selFacilityAndVerIRGPresent(
						selenium, strHICSResource, pStrIRGName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.CreateIncidentWithIncidentName(selenium, pStrIRGName,
								pStrIncName, strIncDesc);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.LogoutWithConfirmation(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				strMsgBody1 = "*** THIS IS AN ACTUAL INCIDENT *** "
					+ "\n"
					+ " This message is to inform you that "
					+ strExistingResource
					+ " is experiencing "
					+ pStrIncName
					+ ". "
					+ "\n"
					+ " "
					+ strIncDesc+". "+"\n"
					+ " * Contact your facility with any questions. Do not reply directly to this email.";
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "Incident Alert for " + strExistingResource;
					for (int i = 0; i < 3; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							strMsg = objMail.seleniumGetText(selenium,
									"css=div.fixed.leftAlign", 160);
							if (strMsg.equals(strMsgBody1)) {
								log4j.info(strMsgBody1
										+ " is displayed in Notification");
							} else {
								log4j.info(strMsgBody1
										+ " is NOT displayed in Notification");
								gstrReason = strMsgBody1
										+ " is NOT displayed in Notification";
								strFuncResult=gstrReason;
							}
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}
						selenium.selectFrame("relative=up");
						selenium.selectFrame("horde_main");
						// click on Back to Inbox
						selenium.click("link=Back to Inbox");
						selenium.waitForPageToLoad("90000");
					}
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad(gstrTimeOut);
					selenium.close();
					selenium.selectWindow("");
					Thread.sleep(2000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_A, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);				
				String strDesc ="*** THIS IS AN ACTUAL INCIDENT *** This message is to inform you" +
						" that "+strExistingResource+" is experiencing " +
						pStrIncName+". "+strIncDesc+". * Contact your facility with any questions. Do not reply directly to this email.";				
				strFuncResult = objEventNotification.ackHicsWebNotification(
						selenium, strSubjName, strDesc);
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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
				strFuncResult = objSearchUser.searchUserByDifCriteria(selenium,
						strUserName_A, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.deactivateUser(selenium,
						strUserName_A, strUsrFulName_A, true);
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
			gstrTCID = "BQS-125749";
			gstrTO = "Verify that user with Override viewing restrictions right receives the ICS notification when an incident is created under that facility in HICS";
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

	// end//testBQS125749//
	
    //start//testBQS125750//
	/**************************************************************************************************
	'Description	:Verify that user without any of the affiliated rights on a resource cannot receive
	                  the ICS notification when an incident is created in HICS selecting that facility.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/18/2013
	'Author			:QSG
	'--------------------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************************************************/
	@Test
	public void testBQS125750() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login.
		Resources objRs = new Resources();// object of class Resources.
		General objMail = new General();// object of class General.
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers.
		HICSNotificationsFunctions objHICS_Functions = new HICSNotificationsFunctions();// object of class HICSNotificationsFunctions.
		SearchUserByDiffCrteria objSearchUser = new SearchUserByDiffCrteria();// object of class SearchUserByDiffCrteria.
		try {
			gstrTCID = "125750"; // Test Case Id
			gstrTO = " Verify that user without any of the affiliated rights on a resource cannot receive the "
					+ "ICS notification when an incident is created in HICS selecting that facility.";// TO
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 14, 4);
			String pStrUserName = rdExcel.readData("HICS", 2, 1);
			String pStrPassword = rdExcel.readData("HICS", 2, 2);
			String strExistingResource = rdExcel.readData("HICS", 2, 3);
			String strHICSResource = rdExcel.readData("HICS", 2, 4);
			String[] strRSValue = new String[1];
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
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			String pStrIncName = "Inc" + strTimeText;
			String strIncDesc = "Inccreation" + strTimeText;
			String pStrIRGName = rdExcel.readData("HICS", 2, 5);

			/*
			 * STEP : Action:Create an incident in HICS selecting facility 'X' that is mapped with EMR.
                      Create user in EMR with email and pager addresses, view resource right on 'X' and 
                      ICS notifications selected, (do not provide override viewing restrictions right) 
               Expected Result:No Expected Result
			 */
			// 662069

			log4j.info("-----Precondtion for test case " + gstrTCID
					+ " starts------");
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strExistingResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user Creation
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
						seleniumPrecondition, "", "", "", "", "", strEMail,
						strEMail, strEMail, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strExistingResource,
						strRSValue[0], false, false, false, false);
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
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						seleniumPrecondition, strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselWebInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
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
				strFuncResult = objHICS_Functions.loginAsSystemAdmin(selenium,
						pStrUserName, pStrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.EndIncidentForFacility(
						selenium, strHICSResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.navToCreatIncidentPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.selFacilityAndVerIRGPresent(
						selenium, strHICSResource, pStrIRGName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.CreateIncidentWithIncidentName(selenium, pStrIRGName,
								pStrIncName, strIncDesc);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.LogoutWithConfirmation(selenium);
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

					strSubjName = "Incident Alert for Cass� Regional Medical Center";
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							
							if (strFuncResult.equals("The mail with subject "
									+ strSubjName
									+ " is NOT present in the inbox")) {
								strFuncResult = "";
								log4j.info("The mail with subject "
										+ strSubjName
										+ " is  NOT present in the inbox");
							} else {
								log4j.info("The mail with subject "
										+ strSubjName
										+ " is  present in the inbox");
								gstrReason = "The mail with subject "
										+ strSubjName
										+ " is  present in the inbox";
								strFuncResult=gstrReason;
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
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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
				strFuncResult = objSearchUser.searchUserByDifCriteria(selenium,
						strUserName_A, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.deactivateUser(selenium,
						strUserName_A, strUsrFulName_A, true);
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
			gstrTCID = "125750"; // Test Case Id
			gstrTO = " Verify that user without any of the affiliated rights on a resource cannot receive the "
					+ "ICS notification when an incident is created in HICS selecting that facility.";// TO
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

	// end//testBQS125750//
	
	//start//testBQS125754//
	/***************************************************************************************************
	'Description		:Verify that user with any of the affiliated rights on a resource receives the 
						 ICS notification when a Exercise/Drill incident is created in HICS
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :9/19/2013
	'Author			    :QSG
	'---------------------------------------------------------------------------------------------------
	'Modified Date																			Modified By
	'Date																					Name
	****************************************************************************************************/
	@Test
	public void testBQS125754() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Resources objRs = new Resources();//object of class Resources.
		General objMail = new General();//object of class General.
		CreateUsers objCreateUsers = new CreateUsers();//object of class CreateUsers.
		HICSNotificationsFunctions objHICS_Functions = new HICSNotificationsFunctions();//object of class HICSNotificationsFunctions.
		try {
			gstrTCID = "125754"; // Test Case Id
			gstrTO = " Verify that user with any of the affiliated rights on a resource receives the ICS notification when a Exercise/Drill incident is created in HICS";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 14, 4);
			String pStrUserName = rdExcel.readData("HICS", 2, 1);
			String pStrPassword = rdExcel.readData("HICS", 2, 2);
			String strExistingResource = rdExcel.readData("HICS", 12, 3);
			String strHICSResource = rdExcel.readData("HICS", 12, 4);
			String[] strRSValue = new String[1];
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
			String strMsgBody1 = "";
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			String pStrIncName = "Inc" + strTimeText;
			String strIncDesc = "Inccreation" + strTimeText;
			String pStrIRGName = rdExcel.readData("HICS", 12, 5);

			/*
			 * STEP : Action:Create a drill incident in HICS and check that user
			 * receives the ICS notifications in EMR Expected Result:No Expected
			 * Result
			 */
			// 662072

			log4j.info("-----Precondtion for test case " + gstrTCID
					+ " starts------");

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strExistingResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user Creation
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
						seleniumPrecondition, "", "", "", "", "", strEMail,
						strEMail, strEMail, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strExistingResource,
						strRSValue[0], false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.OverrideViewingRestrictions");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						seleniumPrecondition, strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						seleniumPrecondition, "ICS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
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
				strFuncResult = objHICS_Functions.loginAsSystemAdmin(selenium,
						pStrUserName, pStrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.EndIncidentForFacility(
						selenium, strHICSResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.navToCreatIncidentPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.selFacilityAndVerIRGPresent(
						selenium, strHICSResource, pStrIRGName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.createDrillIncident(selenium,
						pStrIRGName, pStrIncName, strIncDesc);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.LogoutWithConfirmation(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				strMsgBody1 = "*** THIS IS AN ACTUAL INCIDENT ***"
						+ "This message is to inform you that "
						+ strExistingResource
						+ " is experiencing ."
						+ "* Contact your facility with any questions. Do not reply directly to this email.";
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "Incident Alert for " + strExistingResource;
					for (int i = 0; i < 3; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							strMsg = objMail.seleniumGetText(selenium,
									"css=div.fixed.leftAlign", 160);
							if (strMsg.equals(strMsgBody1)) {
								log4j.info(strMsgBody1
										+ " is displayed in Notification");
							} else {
								log4j.info(strMsgBody1
										+ " is NOT displayed in Notification");
								gstrReason = strMsgBody1
										+ " is NOT displayed in Notification";
							}
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}
						selenium.selectFrame("relative=up");
						selenium.selectFrame("horde_main");
						// click on Back to Inbox
						selenium.click("link=Back to Inbox");
						selenium.waitForPageToLoad("90000");
					}
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad(gstrTimeOut);
					selenium.close();
					selenium.selectWindow("");
					Thread.sleep(2000);
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
			gstrTCID = "BQS-125754";
			gstrTO = "Verify that user with any of the affiliated rights on a resource receives the ICS notification when a Exercise/Drill incident is created in HICS";
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

	// end//testBQS125754//
}
