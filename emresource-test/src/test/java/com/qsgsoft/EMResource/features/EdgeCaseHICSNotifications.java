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

public class EdgeCaseHICSNotifications {
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
	
  //start//testFTS125751//
	/***********************************************************************************************
	'Description	:Verify that User Preferences section is not available while creating a new user
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/19/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------------
	'Modified Date				                                                        Modified By
	'Date					                                                            Name
	***********************************************************************************************/

	@Test
	public void testBQS125751() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();//Object of class CreateUsers
		try {
			gstrTCID = "125751"; // Test Case Id
			gstrTO = " Verify that User Preferences section is not available while creating a new user";// TO
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
		/*
		* STEP :
		  Action:Create an incident in HICS selecting facility 'X' that is mapped with EMR.
          Create user in EMR with email and pager addresses, view resource right on 'X' and ICS
           notifications selected, (do not provide override viewing restrictions right)
		  Expected Result:No Expected Result
		*/
		//662070
			
			log4j.info("-----Precondtion for test case " + gstrTCID+ " starts------");

			strFuncResult = objLogin.login(selenium,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// user Creation
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						selenium, strUserName_A, strInitPwd,
						strConfirmPwd, strUsrFulName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strUserPref = selenium
						.getText("//div[@id='mainContainer']/form/table[6]/"
								+ "thead/tr/th");
				if (strUserPref == "5. User Preferences") {
					log4j.info("User Preferences section is NOT available while creating a new user");
				} else {
					log4j.info("User Preferences section is available while creating a new user");
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
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
			gstrTCID = "125751";	//Test Case Id	
			gstrTO = " Verify that User Preferences section is not available while creating a new user";//TO
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

	// end//testFTS125751//
	
	// start//testFTS125774//
	/***************************************************************
	 * 'Description 	:Verify that users with appropriate right does not receive
	 *                   ICS notifications when an incident is created in HICS deselecting 'Allow
	 *                   State/Region to view this incident' check box 
	 * 'Precondition 	: 
	 * 'Arguments		:None 
	 * 'Returns 		:None 
	 * 'Date 			:9/19/2013 
	 * 'Author 			:Suhas
	 * '---------------------------------------------------------------
	 * 'Modified Date                         Modified By
	 * 'Date                                  Name
	 ***************************************************************/
	@Test
	public void testFTS125774() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Resources objRs = new Resources();
		General objMail = new General();
		CreateUsers objCreateUsers = new CreateUsers();
		HICSNotificationsFunctions objHICS_Functions = new HICSNotificationsFunctions();
		SearchUserByDiffCrteria objSearchUser = new SearchUserByDiffCrteria();
		try {
			gstrTCID = "125774"; // Test Case Id
			gstrTO = " Verify that users with appropriate right does not receive ICS notifications when an incident is created in HICS"
					+ "deselecting 'Allow State/Region to view this incident' check box";// Test// Objective
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
			// RS
			String strExistingResource = rdExcel.readData("HICS", 11, 3);
			String strHICSResource = rdExcel.readData("HICS", 11, 4);
			String[] strRSValue = new String[1];
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
			String pStrIncName = "Inc" + strTimeText;
			String strIncDesc = "Inccreation" + strTimeText;
			String pStrIRGName = rdExcel.readData("HICS", 11, 5);
			
			/*
			 * STEP : Action:Verify that users with appropriate right does not
			 * receive ICS notifications when an incident is created in HICS
			 * deselecting 'Allow State/Region to view this incident' check box
			 * Expected Result:No Expected Result
			 */
			// 662078

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			//Login
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
             //Fetching resource value
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			//Login to HICS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.loginAsSystemAdmin(selenium,
						pStrUserName, pStrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Creating incident by de-selecting 'Allow State/Region to view this incident� 
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
						.createIncidentWithAllowStateOrRegion(selenium,
								pStrIRGName, pStrIncName, strIncDesc, false);
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
			
			// Verifying mail
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "Incident Alert for " + strExistingResource;
					strFuncResult = objMail.verifyEmailSubName(selenium,
							strSubjName);
					if (strFuncResult.equals("The mail with subject "
							+ strSubjName + " is NOT present in the inbox")) {
						log4j.info("The mail with subject " + strSubjName
								+ " is NOT present in the inbox");
						strFuncResult = "";
					} else {
						log4j.info("The mail with subject " + strSubjName
								+ " is present in the inbox");
						strFuncResult = "The mail with subject " + strSubjName
								+ " is present in the inbox";
						gstrReason = strFuncResult;
					}
					assertTrue(strFuncResult.equals(""));
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
			
			//Login to HICS
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
						.LogoutWithConfirmation(selenium);
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
					strSubjName = "Incident Ended for " + strExistingResource;
					strFuncResult = objMail.verifyEmailSubName(selenium,
							strSubjName);
					if (strFuncResult.equals("The mail with subject "
							+ strSubjName + " is NOT present in the inbox")) {
						log4j.info("The mail with subject " + strSubjName
								+ " is NOT present in the inbox");
						strFuncResult = "";
					} else {
						log4j.info("The mail with subject " + strSubjName
								+ " is present in the inbox");
						strFuncResult = "The mail with subject " + strSubjName
								+ " is present in the inbox";
						gstrReason = strFuncResult;
					}
					assertTrue(strFuncResult.equals(""));
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
			gstrTCID = "FTS-125774";
			gstrTO = "Verify that users with appropriate right does not receive ICS notifications when an incident is created " +
					 "in HICS deselecting 'Allow State/Region to view this incident' check box";
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

	// end//testFTS125774//	

	//start//testFTS125773//
	/***************************************************************
	'Description		:Verify that user provided with only pager address receives only the pager ICS notifications
	                     when an incident is created in HICS
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:9/20/2013
	'Author				:Suhas
	'---------------------------------------------------------------
	'Modified Date				              Modified By
	'Date					                  Name
	***************************************************************/

	@Test
	public void testFTS125773() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Resources objRs = new Resources();
		General objMail = new General();
		CreateUsers objCreateUsers = new CreateUsers();	
		HICSNotificationsFunctions objHICS_Functions = new HICSNotificationsFunctions();
		SearchUserByDiffCrteria objSearchUser = new SearchUserByDiffCrteria();
		try {
			gstrTCID = "125773"; // Test Case Id
			gstrTO = " Verify that user provided with only pager address receives only the pager ICS notifications when an" +
					 " incident is created in HICS";// Test	// Objective
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
			// RS
			String strExistingResource = rdExcel.readData("HICS", 10, 3);
			String strHICSResource = rdExcel.readData("HICS", 10, 4);
			String[] strRSValue = new String[1];
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
			String pStrIncName = "Inc" + strTimeText;
			String strIncDesc = "Inccreation" + strTimeText;
			String pStrIRGName = rdExcel.readData("HICS", 10, 5);
			String strMsgBody1 = "";
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			/*
			 * STEP : Action:Verify that user provided with only pager address
			 * receives only the pager ICS notifications when an incident is
			 * created in HICS Expected Result:No Expected Result
			 */
			// 662077
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			//Login
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
			//RS
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
			// user creation
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
			//Selecting  pager 
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			//Login to HICS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.loginAsSystemAdmin(selenium,
						pStrUserName, pStrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Creating incident
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
						.createIncidentWithAllowStateOrRegion(selenium,
								pStrIRGName, pStrIncName, strIncDesc, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Logout
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.LogoutWithConfirmation(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
            //Checking email
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
							log4j.info("pager ICS notification is displayed");
						} else {
							log4j.info(strMsgBody1
									+ " is NOT displayed in Notification");
							strFuncResult = strMsgBody1
									+ " is NOT displayed in Notification";
						}
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					assertTrue(strFuncResult.equals(""));
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
			gstrTCID = "FTS-125773";
			gstrTO = "Verify that user provided with only pager address receives only the pager ICS notifications when an " +
					 "incident is created in HICS";
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

	// end//testFTS125773//
	
	//start//testFTS125772//
	/*********************************************************************************************
	'Description		:Verify that user provided with only email address receives only the email 
						 ICS notifications when an incident is created in HICS
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :9/23/2013
	'Author			    :QSG
	'---------------------------------------------------------------------------------------------
	'Modified Date							                                       Modified By
	'Date									                                       Name
	**********************************************************************************************/
	@Test
	public void testFTS125772() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// Object of class Login
		Resources objRs = new Resources();//Object of class Resource
		General objMail = new General();//Object of class General	
		CreateUsers objCreateUsers = new CreateUsers();//Object of class CreateUsers
		SearchUserByDiffCrteria objSearchUser = new SearchUserByDiffCrteria();// object of class SearchUserByDiffCrteria
		HICSNotificationsFunctions objHICS_Functions = new HICSNotificationsFunctions();//Object of class HICSNotificationsFunctions
		try {
			gstrTCID = "125772"; // Test Case Id
			gstrTO = " Verify that user provided with only email address receives only the email ICS " +
					"notifications when an incident is created in HICS";// Test Objective
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
			String strExistingResource = rdExcel.readData("HICS", 9, 3);
			String strHICSResource = rdExcel.readData("HICS", 9, 4);
			String[] strRSValue = new String[1];
			
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
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
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strMsgBody1 = "";
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			String pStrIncName = "Inc" + strTimeText;
			String strIncDesc = "Inccreation" + strTimeText;
			String pStrIRGName = rdExcel.readData("HICS", 9, 5);
			
			/*
			 * STEP : Action:Verify that user provided with only email address
			 * receives only the email ICS notifications when an incident is
			 * created in HICS Expected Result:No Expected Result
			 */
			// 662076

			log4j.info("-----Precondtion for test case " + gstrTCID
					+ " starts------");
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
						seleniumPrecondition, "", "", "", "", "", "",
						strEMail, "", "");
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
					for (int i = 0; i < 1; i++) {
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
			gstrTCID = "FTS-125772";
			gstrTO = "Verify that user provided with only email address receives only the email ICS notifications when an incident is created in HICS";
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

	// end//testFTS125772//	

	//start//testFTS125769//
	/***************************************************************
	'Description		:Verify that ICS notifications can be set for a user via the user edit screen
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/28/2013
	'Author				:Suhas
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/
	@Test
	public void testFTS125769() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Resources objRs = new Resources();// object of class Resource
		General objMail = new General();// object of class General	
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers
		HICSNotificationsFunctions objHICS_Functions = new HICSNotificationsFunctions();// object of class HICSNotificationsFunctions
		SearchUserByDiffCrteria objSearchUser = new SearchUserByDiffCrteria();// object of class SearchUserByDiffCrteria
		try {
			gstrTCID = "125769"; // Test Case Id
			gstrTO = " Verify that ICS notifications can be set for a user via the user edit screen";// Test Objective
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
			// RS
			String strExistingResource = rdExcel.readData("HICS", 8, 3);
			String strHICSResource = rdExcel.readData("HICS", 8, 4);
			String[] strRSValue = new String[1];
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
			String pStrIncName = "Inc" + strTimeText;
			String strIncDesc = "Inccreation" + strTimeText;
			String pStrIRGName = rdExcel.readData("HICS", 8, 5);
			String strMsgBody1 = "";
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			
			/*
			 * STEP : Action:Set ICS notification preferences from Edit user
			 * screen Expected Result:No Expected Result
			 */
			// 662073
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// Login
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

			// RS
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

			// user creation
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

			// Selecting pager
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
				strFuncResult = objCreateUsers.savAndNavToSySNotiPrefPage(
						seleniumPrecondition, strUserName_A);
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
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// Login to HICS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions.loginAsSystemAdmin(selenium,
						pStrUserName, pStrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating incident
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
						.createIncidentWithAllowStateOrRegion(selenium,
								pStrIRGName, pStrIncName, strIncDesc, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Logout
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objHICS_Functions
						.LogoutWithConfirmation(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Checking email
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
						+ strIncDesc
						+ ". "
						+ "\n"
						+ " * Contact your facility with any questions. Do not reply directly to this email.";
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "Incident Alert for " + strExistingResource;
					for (int i = 0; i < 1; i++) {
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
			gstrTCID = "FTS-125769";
			gstrTO = "Verify that ICS notifications can be set for a user via the user edit screen";
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

	// end//testFTS125769//
}
