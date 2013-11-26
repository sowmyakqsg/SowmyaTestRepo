package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Properties;
import org.junit.*;

import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/*******************************************************************
' Description		:This class contains test cases from requirement
' Requirement Group	:Preferences>> Status Change Preferences 
' Requirement 	    :Add status change preferences
� Product		    :EMResource v3.23
' Date			    :30/April/2013
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class HappyDayAddStatusChangePreferences {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.HappyDay_AddStatusChangePreferences");
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
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

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

	/********************************************************************************
	 * 'Description 	:Verify that user can set the status change preferences for
	 * 					 a Text ' status type and receives the notifications when the status
	 * 					 changes 
	 * 'Precondition 	:
	 * 'Arguments 		:None 
	 * 'Returns 		:None 
	 * 'Date   			:30-April-2013
	 * 'Author 			:QSG
	 * '------------------------------------------------------------------------------
	 * -'Modified Date 									Modified By
	 *  'Date 											Name
	 **********************************************************************************/
	@Test
	public void testHapyDay118054() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers
		StatusTypes objStatusTypes = new StatusTypes();// object of class  StatusTypes
		ResourceTypes objRT = new ResourceTypes();// object of class ResourceTypes
		Resources objRs = new Resources();// object of class Resources
		Views objViews = new Views();// object of class Views
		Roles objRoles = new Roles();// object of class Roles
		Preferences objPreferences = new Preferences();// object of class Preferences
		EventNotification objEventNotification = new EventNotification(); // object of class EventNotification
		General objMail = new General();// object of class General
		EventSetup objEventSetup = new EventSetup();// object of class EventSetup
		EventList objEventList = new EventList();// object of class  EventList
		try {
			gstrTCID = "118054";
			gstrTO = "Verify that user can set the status change preferences"
					+ " for a Text status type and receives the notifications"
					+ " when the status changes";
			gstrResult = "FAIL";
			gstrReason = "";
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTmText = dts.getCurrentDate("HHmm");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			//User
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			//ST
			String statrTxtTypeNameRole = "rTST" + strTimeText;
			String statrTxtTypeNamePrivate = "pTST" + strTimeText;
			String statrTxtTypeNameEvent = "eTST" + strTimeText;
			String strStatTypDefn = "Automation";
			String strTSTValue = "Text";
			String strSTvalue[] = new String[3];
			String strRSValue[] = new String[1];
			//RT
			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";
			//RS
			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			//Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1_Role = "";
			String strMsgBody2_Role = "";
			String strMsgBody1_Private = "";
			String strMsgBody2_Private = "";
			String strMsgBody1_Event = "";
			String strMsgBody2_Event = "";
			String strSubjName = "";
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";
			String strCurrDate = "";
			String strCurrDate2 = "";
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "This is an automation event";
			String strETValue = "";
			String strEventValue = "";
			String strUpdate1 = "Update1";
			String strUpdate2 = "Update2";
			String strUpdate3 = "Update3";
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
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

			/*
			 * 1. Test user has created status types 'NST', 'MST' & 'SST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// TST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue,
						statrTxtTypeNameRole, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTxtTypeNameRole);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Private TST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createPrivateSTWitMandFlds(
						seleniumPrecondition, strTSTValue,
						statrTxtTypeNamePrivate, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTxtTypeNamePrivate);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event

			// TST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue,
						statrTxtTypeNameEvent, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTxtTypeNameEvent);

				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 3. Resource type 'RT' is created and is associated with all the
			 * three status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[2], true);

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

			/* 4. Resource 'RS' is created under 'RT' */

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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
			 * 5. Role R1 is created selecting status type ST under view and
			 * update.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_1);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. User 'A' has update right on 'RS' and is assigned a role 'R'
			 * which has update right on all four status types.
			 */

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6. User 'A' is provided with 'Email' and 'Pager' address.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName,
						strLastName, strOrganization, strPhoneNo,
						strPrimaryEMail, strEMail, strPagerValue,
						strAdminComments);

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
				blnLogin = true;
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRTValue };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeVal, strSTvalue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(
						seleniumPrecondition, strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventNotification
						.selectAndDeselectEventNotifForAll(
								seleniumPrecondition, strTempName, false,
								false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * try { assertEquals("", strFuncResult); strFuncResult =
			 * objEventSetup.createEventMandFlds(selenium, strTempName,
			 * strEveName, strInfo, true); } catch (AssertionError Ae) {
			 * gstrReason = strFuncResult; }
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventWitManyRS(
						seleniumPrecondition, strTempName, strRSValue,
						strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strEventValue = objEventSetup.fetchEventValueInEventList(
						seleniumPrecondition, strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("--------------- test case " + gstrTCID
					+ " execution starts----------");
			/*
			 * 2 Login as user 'A' and navigate to 'Preferences>>Status Change
			 * Prefs' 'My Status Change Preferences' page is displayed.
			 */

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
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Select 'Add' 'Find Resources' page is displayed.
			 */

			/* 4 Search for resource 'RS' Resource 'RS' is retrieved. */

			/*
			 * 5 Select the checkbox associated with 'RS' and select
			 * 'Notifications' 'Edit My Status Change Preferences' is displayed.
			 * Sub header is displayed as 'RT-RS' is displayed. Status types
			 * 'NST', 'MST' & 'SST' are displayed under 'Uncategorized'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=#r_name";
				String strText = strResrctTypName + "�" + strResource;
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Sub header is displayed as 'RT-RS'");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "Sub header is NOT displayed as 'RT-RS'";
				log4j.info("Sub header is NOT displayed as 'RT-RS'");
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrTxtTypeNameRole };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 For 'NST', provide value 'A' for 'Above' and 'B' for 'Below'.
			 * Select 'Email', 'Pager' and 'Web' for both the values. 'My Status
			 * Change Preferences' page is displayed. 'X' is displayed under
			 * 'Email', 'Pager' and 'Web' for both 'Above' and 'Below' values.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusTypeNew(selenium,
								 strRSValue[0], strSTvalue[0],
								 true, true, true,false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusTypeNew(selenium,
								 strRSValue[0], strSTvalue[1],
								 true, true, true,false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusTypeNew(selenium,
								 strRSValue[0], strSTvalue[2],
								 true, true, true,true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9 Navigate to view 'V1' and select to update the status clicking
			 * the 'Key' symbol. 'Update Status' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.clickEventBanner(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate1, strSTvalue[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objMail.refreshPageNew(selenium);
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}
		

			try {
				assertEquals("", strFuncResult);
				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);
				String strDate[] = strUpdatedDate.split(" ");
				for (String s : strDate) {
					log4j.info(s);
				}
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);
				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);
				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");
				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");
				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;
				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");
				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;
				String strSTDateTime = strStartDate;
				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrTxtTypeNameRole + " status from -- to "
						+ strUpdate1 + ".";
				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTxtTypeNameRole + " status from -- to "
						+ strUpdate1 + ".";
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");
				log4j.info(strAddedDtTime);
				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);
				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Role = statrTxtTypeNameRole + " from -- to "
					+ strUpdate1 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_Role = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statrTxtTypeNameRole
					+ " status from -- "
					+ "to "
					+ strUpdate1
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNameRole
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.clickEventBanner(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate2, strSTvalue[1], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objMail.refreshPageNew(selenium);
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);
				String strDate[] = strUpdatedDate.split(" ");
				for (String s : strDate) {
					log4j.info(s);
				}
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);
				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);
				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");
				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");
				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;
				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");
				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;
				String strSTDateTime = strStartDate;
				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrTxtTypeNamePrivate + " status from -- to "
						+ strUpdate2 + ".";
				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTxtTypeNamePrivate + " status from -- to "
						+ strUpdate2 + ".";
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");
				log4j.info(strAddedDtTime);
				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);
				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Private = statrTxtTypeNamePrivate + " from -- to "
					+ strUpdate2 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_Private = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statrTxtTypeNamePrivate
					+ " status from -- "
					+ "to "
					+ strUpdate2
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNamePrivate
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.clickEventBanner(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate3, strSTvalue[2], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objMail.refreshPageNew(selenium);
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);
				String strDate[] = strUpdatedDate.split(" ");
				for (String s : strDate) {
					log4j.info(s);
				}
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);
				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);
				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");
				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");
				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;
				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");
				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;
				String strSTDateTime = strStartDate;
				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrTxtTypeNameEvent + " status from -- to "
						+ strUpdate3 + ".";
				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTxtTypeNameEvent + " status from -- to "
						+ strUpdate3 + ".";
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");
				log4j.info(strAddedDtTime);
				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);
				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Event = statrTxtTypeNameEvent + " from -- to "
					+ strUpdate3 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_Event = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statrTxtTypeNameEvent
					+ " status from -- "
					+ "to "
					+ strUpdate3
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNameEvent
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);
				strSubjName = "Change for " + strAbbrv;
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));

					for (int i = 0; i < 3; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody2_Role)
									|| strMsg.equals(strMsgBody2_Private)
									|| strMsg.equals(strMsgBody2_Event)) {
								intPagerRes++;
								log4j.info("Pager body is displayed");
							} else {
								log4j.info("Email and Pager body is NOT displayed");
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

					strSubjName = "Status Change for " + strResource;
					for (int i = 0; i < 6; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody1_Role)
									|| strMsg.equals(strMsgBody1_Private)
									|| strMsg.equals(strMsgBody1_Event)) {
								intEMailRes++;
								log4j.info("Email body is displayed");
							} else {
								log4j.info("Email and Pager body is NOT displayed");
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
					selenium.waitForPageToLoad("90000");
					selenium.close();
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertTrue(strFuncResult.equals(""));
				if (intResCnt == 1 && intEMailRes == 6 && intPagerRes == 3) {
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118054";
			gstrTO = "Verify that user can set the status change preferences for"
					+ " a Text status type and receives the notifications when "
					+ "the status changes";
			gstrResult = "FAIL";
			gstrReason = "";

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
	
	/********************************************************************************
	 * 'Description 	:Verify that user with appropriate right can set status change 
	 * '			 	 notifications preferences for other users in a region 
	 * 'Precondition 	: 
	 * 'Arguments 		:None 
	 * 'Returns 	 	:None 
	 * 'Date   		 	:30-April-2013
	 * 'Author 		 	:QSG
	 * '------------------------------------------------------------------------------
	 * 'Modified Date									 Modified By 
	 * '<Date> 												<Name>
	 **********************************************************************************/

	@Test
	public void testHapyDay118096() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login 
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers
		StatusTypes objStatusTypes = new StatusTypes();// object of class StatusTypes
		ResourceTypes objRT = new ResourceTypes();// object of class ResourceTypes
		Resources objRs = new Resources();// object of class Resources
		Views objViews = new Views();// object of class Views
		Roles objRoles = new Roles();// object of class Roles
		Preferences objPreferences = new Preferences();// object of class Preferences
		EventNotification objEventNotification = new EventNotification();// object of class EventNotification
		General objMail = new General();// object of class General 
		EventSetup objEventSetup = new EventSetup();// object of class EventSetup
		EventList objEventList = new EventList();// object of class EventList
		try {
			gstrTCID = "118096";
			gstrTO = "Verify that user with appropriate right can set " +
					"status change notifications preferences for other users in a region ";
			gstrResult = "FAIL";
			gstrReason = "";
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTmText = dts.getCurrentDate("HHmm");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;			
			String strUserName_RecvNoti = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String statrTxtTypeNameRole = "rTST" + strTimeText;
			String statrTxtTypeNamePrivate = "pTST" + strTimeText;
			String statrTxtTypeNameEvent = "eTST" + strTimeText;
			String strStatTypDefn = "Automation";
			String strTSTValue = "Text";
			String strSTvalue[] = new String[3];
			String strRSValue[] = new String[1];
			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";
			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRolesName_View = "Role_V" + strTimeText;
			String strRolesName_Update = "Role_U" + strTimeText;
			String strRoleValue_View = "";
			String strRoleValue_Update = "";
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1_Role = "";
			String strMsgBody2_Role = "";
			String strMsgBody1_Private = "";
			String strMsgBody2_Private = "";
			String strMsgBody1_Event = "";
			String strMsgBody2_Event = "";
			String strSubjName = "";
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";
			String strCurrDate = "";
			String strCurrDate2 = "";
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "This is an automation event";
			String strETValue = "";
			String strEventValue = "";
			String strUpdate1 = "Update1";
			String strUpdate2 = "Update2";
			String strUpdate3 = "Update3";
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
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

			/*
			 * 1. Test user has created status types 'NST', 'MST' & 'SST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// TST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue,
						statrTxtTypeNameRole, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTxtTypeNameRole);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Private TST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createPrivateSTWitMandFlds(
						seleniumPrecondition, strTSTValue,
						statrTxtTypeNamePrivate, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTxtTypeNamePrivate);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event

			// TST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue,
						statrTxtTypeNameEvent, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTxtTypeNameEvent);

				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 3. Resource type 'RT' is created and is associated with all the
			 * three status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[2], true);

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

			/* 4. Resource 'RS' is created under 'RT' */

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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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

			//Role to View
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_View);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" } };
				
				String strSTvaluesUpdate[][] = { { strSTvalue[0], "false" },
						{ strSTvalue[1], "false" }, { strSTvalue[2], "false" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvaluesUpdate, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_View);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue_View = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_View);

				if (strRoleValue_View.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		//Role to update

			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_Update);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_Update);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue_Update = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_Update);

				if (strRoleValue_Update.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
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
						seleniumPrecondition, strUserName_A, strInitPwd,
						strConfirmPwd, strUsrFulName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue_Update, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6. User 'A' is provided with 'Email' and 'Pager' address.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName,
						strLastName, strOrganization, strPhoneNo,
						strPrimaryEMail, strEMail, strPagerValue,
						strAdminComments);

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

			
			//User who receives notification
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName_RecvNoti, strInitPwd,
						strConfirmPwd, strUsrFulName_A);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue_View, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, false, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName,
						strLastName, strOrganization, strPhoneNo,
						strPrimaryEMail, strEMail, strPagerValue,
						strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_RecvNoti, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRTValue };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeVal, strSTvalue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(
						seleniumPrecondition, strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * try { assertEquals("", strFuncResult); strFuncResult =
			 * objEventSetup.createEventMandFlds(selenium, strTempName,
			 * strEveName, strInfo, true); } catch (AssertionError Ae) {
			 * gstrReason = strFuncResult; }
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventWitManyRS(
						seleniumPrecondition, strTempName, strRSValue,
						strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strEventValue = objEventSetup.fetchEventValueInEventList(
						seleniumPrecondition, strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("--------------- test case " + gstrTCID
					+ " execution starts----------");

			/*
			 * 2 Login as user 'A' and navigate to 'Preferences>>Status Change
			 * Prefs' 'My Status Change Preferences' page is displayed.
			 */

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
				strFuncResult =objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_RecvNoti, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navStatusChangeNotiFrmEditUserPge(selenium,
								strUserName_RecvNoti);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Select 'Add' 'Find Resources' page is displayed.
			 */

			/* 4 Search for resource 'RS' Resource 'RS' is retrieved. */

			/*
			 * 5 Select the checkbox associated with 'RS' and select
			 * 'Notifications' 'Edit My Status Change Preferences' is displayed.
			 * Sub header is displayed as 'RT-RS' is displayed. Status types
			 * 'NST', 'MST' & 'SST' are displayed under 'Uncategorized'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=#r_name";
				String strText = strResrctTypName + "�" + strResource;
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Sub header is displayed as 'RT-RS'");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "Sub header is NOT displayed as 'RT-RS'";
				log4j.info("Sub header is NOT displayed as 'RT-RS'");
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrTxtTypeNameRole };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 For 'NST', provide value 'A' for 'Above' and 'B' for 'Below'.
			 * Select 'Email', 'Pager' and 'Web' for both the values. 'My Status
			 * Change Preferences' page is displayed. 'X' is displayed under
			 * 'Email', 'Pager' and 'Web' for both 'Above' and 'Below' values.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(selenium,
								strResource, strRSValue[0], strSTvalue[0],
								statrTxtTypeNameRole, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(selenium,
								strResource, strRSValue[0], strSTvalue[1],
								statrTxtTypeNamePrivate, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(selenium,
								strResource, strRSValue[0], strSTvalue[2],
								statrTxtTypeNameEvent, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9 Navigate to view 'V1' and select to update the status clicking
			 * the 'Key' symbol. 'Update Status' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.clickEventBanner(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate1, strSTvalue[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objMail.refreshPageNew(selenium);
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}
		

			try {
				assertEquals("", strFuncResult);
				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);
				String strDate[] = strUpdatedDate.split(" ");
				for (String s : strDate) {
					log4j.info(s);
				}
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);
				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);
				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");
				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");
				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;
				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");
				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;
				String strSTDateTime = strStartDate;
				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrTxtTypeNameRole + " status from -- to "
						+ strUpdate1 + ".";
				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTxtTypeNameRole + " status from -- to "
						+ strUpdate1 + ".";
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");
				log4j.info(strAddedDtTime);
				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);
				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Role = statrTxtTypeNameRole + " from -- to "
					+ strUpdate1 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_Role = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statrTxtTypeNameRole
					+ " status from -- "
					+ "to "
					+ strUpdate1
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNameRole
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.clickEventBanner(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate2, strSTvalue[1], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objMail.refreshPageNew(selenium);
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);
				String strDate[] = strUpdatedDate.split(" ");
				for (String s : strDate) {
					log4j.info(s);
				}
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);
				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);
				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");
				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");
				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;
				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");
				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;
				String strSTDateTime = strStartDate;
				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrTxtTypeNamePrivate + " status from -- to "
						+ strUpdate2 + ".";
				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTxtTypeNamePrivate + " status from -- to "
						+ strUpdate2 + ".";
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");
				log4j.info(strAddedDtTime);
				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);
				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Private = statrTxtTypeNamePrivate + " from -- to "
					+ strUpdate2 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_Private = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statrTxtTypeNamePrivate
					+ " status from -- "
					+ "to "
					+ strUpdate2
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNamePrivate
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.clickEventBanner(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate3, strSTvalue[2], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objMail.refreshPageNew(selenium);
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);
				String strDate[] = strUpdatedDate.split(" ");
				for (String s : strDate) {
					log4j.info(s);
				}
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);
				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);
				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");
				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");
				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;
				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");
				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;
				String strSTDateTime = strStartDate;
				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrTxtTypeNameEvent + " status from -- to "
						+ strUpdate3 + ".";
				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTxtTypeNameEvent + " status from -- to "
						+ strUpdate3 + ".";
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Event = statrTxtTypeNameEvent + " from -- to "
					+ strUpdate3 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_Event = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statrTxtTypeNameEvent
					+ " status from -- "
					+ "to "
					+ strUpdate3
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNameEvent
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);						
				strSubjName = "Change for " + strAbbrv;
				try {
					assertTrue(strFuncResult.equals(""));
					for (int i = 0; i < 3; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody2_Role)
									|| strMsg.equals(strMsgBody2_Private)
									|| strMsg.equals(strMsgBody2_Event)) {
								intPagerRes++;
								log4j.info("Pager body is displayed");
							} else {
								log4j
										.info("Email and Pager body is NOT displayed");
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
					strSubjName = "Status Change for " + strResource;
					for (int i = 0; i < 6; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							if (strMsg.equals(strMsgBody1_Role)
									|| strMsg.equals(strMsgBody1_Private)
									|| strMsg.equals(strMsgBody1_Event)) {
								intEMailRes++;
								log4j.info("Email body is displayed");
							} else {
								log4j
										.info("Email and Pager body is NOT displayed");
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
					selenium.waitForPageToLoad("90000");
					selenium.close();
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertTrue(strFuncResult.equals(""));
				if (intResCnt == 1 && intEMailRes == 6 && intPagerRes == 3) {
					gstrResult = "PASS";
				}

			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118096";
			gstrTO = "Verify that user with appropriate right can set status change " +
					"notifications preferences for other users in a region ";
			gstrResult = "FAIL";
			gstrReason = "";

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
	
	
	/********************************************************************************
	 * 'Description 	:Verify that status change preference notification are not received
	 * '				 by the user when the status changes and when the status change 
	 * '				 preferences are not set for a text status type. 
	 * 'Precondition 	:
	 * 'Arguments 		:None 
	 * 'Returns 		:None 
	 * 'Date   			:2-May-2013
	 * 'Author 			:QSG
	 * '------------------------------------------------------------------------------
	 * 'Modified Date 									Modified By 
	 * '<Date> 											  <Name>
	 **********************************************************************************/
	@Test
	public void testHapyDay118149() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers
		StatusTypes objStatusTypes = new StatusTypes();// object of class StatusTypes
		ResourceTypes objRT = new ResourceTypes();// object of class ResourceTypes
		Resources objRs = new Resources();// object of class Resources
		Views objViews = new Views();// object of class Views
		Roles objRoles = new Roles();// object of class Roles
		Preferences objPreferences = new Preferences();// object of class Preferences
		EventNotification objEventNotification = new EventNotification();// object of class EventNotification
		General objMail = new General();// object of class General General
		EventSetup objEventSetup = new EventSetup();// object of class EventSetup
		EventList objEventList = new EventList();// object of class EventList
		try {
			gstrTCID = "118149";
			gstrTO = "Verify that user can set the status change preferences"
					+ " for a Text status type and receives the notifications"
					+ " when the status changes";
			gstrResult = "FAIL";
			gstrReason = "";
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTmText = dts.getCurrentDate("HHmm");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			//User
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			//ST
			String statrTxtTypeNameRole = "rTST" + strTimeText;
			String strStatTypDefn = "Automation";
			String strTSTValue = "Text";
			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];
			//RT
			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";
			//RS
			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";
			String strCurrDate = "";
			//EVE
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "This is an automation event";
			String strETValue = "";
			String strEventValue = "";
			String strUpdate1 = "Update1";
			int intResCntNotRecv=0;
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
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

			/*
			 * 1. Test user has created status types 'NST', 'MST' & 'SST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// TST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue,
						statrTxtTypeNameRole, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTxtTypeNameRole);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			/*
			 * 3. Resource type 'RT' is created and is associated with all the
			 * three status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
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

			/* 4. Resource 'RS' is created under 'RT' */

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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
			 * 5. Role R1 is created selecting status type ST under view and
			 * update.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_1);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. User 'A' has update right on 'RS' and is assigned a role 'R'
			 * which has update right on all four status types.
			 */

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6. User 'A' is provided with 'Email' and 'Pager' address.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName,
						strLastName, strOrganization, strPhoneNo,
						strPrimaryEMail, strEMail, strPagerValue,
						strAdminComments);

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
				blnLogin = true;
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRTValue };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeVal, strSTvalue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(
						seleniumPrecondition, strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * try { assertEquals("", strFuncResult); strFuncResult =
			 * objEventSetup.createEventMandFlds(selenium, strTempName,
			 * strEveName, strInfo, true); } catch (AssertionError Ae) {
			 * gstrReason = strFuncResult; }
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventWitManyRS(
						seleniumPrecondition, strTempName, strRSValue,
						strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strEventValue = objEventSetup.fetchEventValueInEventList(
						seleniumPrecondition, strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				}
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("--------------- test case " + gstrTCID
					+ " execution starts----------");

			/*
			 * 2 Login as user 'A' and navigate to 'Preferences>>Status Change
			 * Prefs' 'My Status Change Preferences' page is displayed.
			 */

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
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Select 'Add' 'Find Resources' page is displayed.
			 */

			/* 4 Search for resource 'RS' Resource 'RS' is retrieved. */

			/*
			 * 5 Select the checkbox associated with 'RS' and select
			 * 'Notifications' 'Edit My Status Change Preferences' is displayed.
			 * Sub header is displayed as 'RT-RS' is displayed. Status types
			 * 'NST', 'MST' & 'SST' are displayed under 'Uncategorized'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=#r_name";
				String strText = strResrctTypName + "�" + strResource;
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Sub header is displayed as 'RT-RS'");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "Sub header is NOT displayed as 'RT-RS'";
				log4j.info("Sub header is NOT displayed as 'RT-RS'");
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrTxtTypeNameRole };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 For 'NST', provide value 'A' for 'Above' and 'B' for 'Below'.
			 * Select 'Email', 'Pager' and 'Web' for both the values. 'My Status
			 * Change Preferences' page is displayed. 'X' is displayed under
			 * 'Email', 'Pager' and 'Web' for both 'Above' and 'Below' values.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.savAndNavigate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statrTxtTypeNameRole, "", "X", "X", "X");
				if (strFuncResult
						.equals(" Status Type "
								+ statrTxtTypeNameRole
								+ " is NOT displayed with appropriate notification methods")) {
					strFuncResult = "";
					log4j
							.info(" Status Type "
									+ statrTxtTypeNameRole
									+ " is NOT displayed with appropriate notification methods");
				} else {
					log4j
							.info(" Status Type "
									+ statrTxtTypeNameRole
									+ " is displayed with appropriate notification methods");
					strFuncResult = " Status Type "
							+ statrTxtTypeNameRole
							+ " is displayed with appropriate notification methods";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			/*
			 * 9 Navigate to view 'V1' and select to update the status clicking
			 * the 'Key' symbol. 'Update Status' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.clickEventBanner(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate1, strSTvalue[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(5000);
				strFuncResult =objMail.refreshPageNew(selenium);
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);
				String strDate[] = strUpdatedDate.split(" ");
				for (String s : strDate) {
					log4j.info(s);
				}		
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);
				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");
				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);				

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strSTDateTime = strStartDate;
				String strDesc = "On " + strCurrDate + " " + strResource
						+ " changed " + statrTxtTypeNameRole + " status from -- to "+strUpdate1+".";
				strFuncResult = objEventNotification.ackSTWebNotificationNew(
						selenium, strResource, strSTDateTime, strDesc);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals(
						"Web Notification is NOT displayed",
						strFuncResult);
				intResCntNotRecv++;
				Thread.sleep(30000);
				strSubjName = "Change for " + strAbbrv;				
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));

						intResCntNotRecv++;

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					strSubjName = "Status Change for " + strResource;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));

						intResCntNotRecv++;
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));

						intResCntNotRecv++;
						strFuncResult = "";
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}					

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			selenium.selectWindow("Mail");
			selenium.selectFrame("horde_main");
			selenium.click("link=Log out");
			selenium.waitForPageToLoad("90000");
			selenium.close();		
			selenium.selectWindow("");
			selenium.selectWindow("");
			selenium.selectFrame("Data");			
			try {
				assertEquals("", strFuncResult);
				if(intResCntNotRecv==4){
					gstrResult = "PASS";
				}
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
		} catch (Exception e) {
			gstrTCID = "118149";
			gstrTO = "Verify that user can set the status change preferences for"
					+ " a Text status type and receives the notifications when "
					+ "the status changes";
			gstrResult = "FAIL";
			gstrReason = "";
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
	
	/********************************************************************************
	 * 'Description 	:Verify that user can set the status change preferences for
	 * 					a NEDOC ' status type and receives the notifications when the status
	 * 					changes 
	 * 'Precondition 	:
	 * 'Arguments 		:None 
	 * 'Returns 		:None 
	 * 'Date			:31/05/2013
	 * 'Author			:QSG
	 * '------------------------------------------------------------------------------
	 * - 'Modified	Date					Modified  By 
	 *   'Date 								Name
	 **********************************************************************************/
	@Test
	public void testHapyDay119702() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers
		StatusTypes objStatusTypes = new StatusTypes();// object of class StatusTypes
		ResourceTypes objRT = new ResourceTypes();// object of class ResourceTypes
		Resources objRs = new Resources();// object of class Resources
		Views objViews = new Views();// object of class Views
		Roles objRoles = new Roles();// object of class Roles
		Preferences objPreferences = new Preferences();// object of class Preferences
		EventNotification objEventNotification = new EventNotification();// object of class EventNotification
		General objMail = new General();// object of class General 
		EventSetup objEventSetup = new EventSetup();// object of class EventSetup
		EventList objEventList = new EventList();// object of class EventList
		try {
			gstrTCID = "119702";
			gstrTO = "Verify that user can set the status change preferences"
					+ " for a NEDOCS status type and thereafter receives the "
					+ "notifications when the status changes";
			gstrResult = "FAIL";
			gstrReason = "";
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTmText = dts.getCurrentDate("HHmm");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			//User
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			//ST
			String statrTxtTypeNameRole = "rTST" + strTimeText;
			String statrTxtTypeNamePrivate = "pTST" + strTimeText;
			String statrTxtTypeNameEvent = "eTST" + strTimeText;
			String statrTxtTypeNameShared = "sTST" + strTimeText;
			String strStatTypDefn = "Automation";
			String strTSTValue = "NEDOCS Calculation";
			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			//RT
			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";
			//RS
			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			//Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1_Role = "";
			String strMsgBody2_Role = "";
			String strMsgBody1_Private = "";
			String strMsgBody2_Private = "";
			String strMsgBody1_Event = "";
			String strMsgBody2_Event = "";
			String strMsgBody1_Shared = "";
			String strMsgBody2_Shared = "";
			String strSubjName = "";
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";
			String strCurrDate = "";
			String strCurrDate2 = "";
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "This is an automation event";
			String strETValue = "";
			String strEventValue = "";
		/*	String strUpdate1 = "241 - Disaster";
			String strUpdate2 = "241 - Disaster";
			String strUpdate3 = "241 - Disaster";
			String strUpdate4 = "241 - Disaster";*/			
			String strUpdate1 = "241";
			String strUpdate2 = "241";
			String strUpdate3 = "241";
			String strUpdate4 = "241";			
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
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

			/*
			 * 1. Test user has created status types 'NST', 'MST' & 'SST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// TST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue,
						statrTxtTypeNameRole, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTxtTypeNameRole);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Private TST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createPrivateSTWitMandFlds(
						seleniumPrecondition, strTSTValue,
						statrTxtTypeNamePrivate, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTxtTypeNamePrivate);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event

			// TST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue,
						statrTxtTypeNameEvent, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTxtTypeNameEvent);

				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue,
						statrTxtTypeNameShared, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statrTxtTypeNameShared, "SHARED",
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[3] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTxtTypeNameShared);

				if (strSTvalue[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 3. Resource type 'RT' is created and is associated with all the
			 * three status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[2], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[3], true);

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

			/* 4. Resource 'RS' is created under 'RT' */

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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
			 * 5. Role R1 is created selecting status type ST under view and
			 * update.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_1);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. User 'A' has update right on 'RS' and is assigned a role 'R'
			 * which has update right on all four status types.
			 */

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6. User 'A' is provided with 'Email' and 'Pager' address.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName,
						strLastName, strOrganization, strPhoneNo,
						strPrimaryEMail, strEMail, strPagerValue,
						strAdminComments);

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
				blnLogin = true;
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRTValue };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeVal, strSTvalue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(
						seleniumPrecondition, strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * try { assertEquals("", strFuncResult); strFuncResult =
			 * objEventSetup.createEventMandFlds(selenium, strTempName,
			 * strEveName, strInfo, true); } catch (AssertionError Ae) {
			 * gstrReason = strFuncResult; }
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventWitManyRS(
						seleniumPrecondition, strTempName, strRSValue,
						strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strEventValue = objEventSetup.fetchEventValueInEventList(
						seleniumPrecondition, strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("--------------- test case " + gstrTCID
					+ " execution starts----------");

			/*
			 * 2 Login as user 'A' and navigate to 'Preferences>>Status Change
			 * Prefs' 'My Status Change Preferences' page is displayed.
			 */

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
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Select 'Add' 'Find Resources' page is displayed.
			 */

			/* 4 Search for resource 'RS' Resource 'RS' is retrieved. */

			/*
			 * 5 Select the checkbox associated with 'RS' and select
			 * 'Notifications' 'Edit My Status Change Preferences' is displayed.
			 * Sub header is displayed as 'RT-RS' is displayed. Status types
			 * 'NST', 'MST' & 'SST' are displayed under 'Uncategorized'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=#r_name";
				String strText = strResrctTypName + "�" + strResource;
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Sub header is displayed as 'RT-RS'");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "Sub header is NOT displayed as 'RT-RS'";
				log4j.info("Sub header is NOT displayed as 'RT-RS'");
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrTxtTypeNameRole };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 For 'NST', provide value 'A' for 'Above' and 'B' for 'Below'.
			 * Select 'Email', 'Pager' and 'Web' for both the values. 'My Status
			 * Change Preferences' page is displayed. 'X' is displayed under
			 * 'Email', 'Pager' and 'Web' for both 'Above' and 'Below' values.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageNew(selenium,
								strResource, strRSValue[0], strSTvalue[0],
								statrTxtTypeNameRole, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageNew(selenium,
								strResource, strRSValue[0], strSTvalue[1],
								statrTxtTypeNamePrivate, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageNew(selenium,
								strResource, strRSValue[0], strSTvalue[2],
								statrTxtTypeNameEvent, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageNew(selenium,
								strResource, strRSValue[0], strSTvalue[3],
								statrTxtTypeNameEvent, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9 Navigate to view 'V1' and select to update the status clicking
			 * the 'Key' symbol. 'Update Status' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.clickEventBanner(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(selenium,
						strScUpdValue1, strSTvalue[0], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.refreshPageNew(selenium);
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);
				String strDate[] = strUpdatedDate.split(" ");
				for (String s : strDate) {
					log4j.info(s);
				}
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);
				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);
				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");
				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");
				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;
				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");
				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;
				String strSTDateTime = strStartDate;
				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrTxtTypeNameRole + " status from 0 to "
						+ strUpdate1 + ".";
				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTxtTypeNameRole + " status from 0 to "
						+ strUpdate1 + ".";
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");
				log4j.info(strAddedDtTime);
				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);
				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Role = statrTxtTypeNameRole + " from 0 to "
					+ strUpdate1 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_Role = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statrTxtTypeNameRole
					+ " status from 0 "
					+ "to "
					+ strUpdate1
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNameRole
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.clickEventBanner(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(selenium,
						strScUpdValue1, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.refreshPageNew(selenium);
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);
				String strDate[] = strUpdatedDate.split(" ");
				for (String s : strDate) {
					log4j.info(s);
				}
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);
				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);
				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");
				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");
				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;
				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");
				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;
				String strSTDateTime = strStartDate;
				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrTxtTypeNamePrivate + " status from 0 to "
						+ strUpdate2 + ".";
				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTxtTypeNamePrivate + " status from 0 to "
						+ strUpdate2 + ".";
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");
				log4j.info(strAddedDtTime);
				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);
				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Private = statrTxtTypeNamePrivate + " from 0 to "
					+ strUpdate2 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_Private = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statrTxtTypeNamePrivate
					+ " status from 0 "
					+ "to "
					+ strUpdate2
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNamePrivate
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.clickEventBanner(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(selenium,
						strScUpdValue1, strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.refreshPageNew(selenium);
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);
				String strDate[] = strUpdatedDate.split(" ");
				for (String s : strDate) {
					log4j.info(s);
				}
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);
				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);
				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");
				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");
				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;
				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");
				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;
				String strSTDateTime = strStartDate;
				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrTxtTypeNameEvent + " status from 0 to "
						+ strUpdate3 + ".";
				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTxtTypeNameEvent + " status from 0 to "
						+ strUpdate3 + ".";
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");
				log4j.info(strAddedDtTime);
				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);
				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Event = statrTxtTypeNameEvent + " from 0 to "
					+ strUpdate3 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_Event = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statrTxtTypeNameEvent
					+ " status from 0 "
					+ "to "
					+ strUpdate3
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNameEvent
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.clickEventBanner(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(selenium,
						strScUpdValue1, strSTvalue[3], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.refreshPageNew(selenium);
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);
				String strDate[] = strUpdatedDate.split(" ");
				for (String s : strDate) {
					log4j.info(s);
				}
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);
				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);
				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");
				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");
				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;
				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");
				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;
				String strSTDateTime = strStartDate;
				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrTxtTypeNameShared + " status from 0 to "
						+ strUpdate4 + ".";
				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTxtTypeNameShared + " status from 0 to "
						+ strUpdate4 + ".";
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");
				log4j.info(strAddedDtTime);
				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);
				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Shared = statrTxtTypeNameShared + " from 0 to "
					+ strUpdate4 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_Shared = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statrTxtTypeNameShared
					+ " status from 0 "
					+ "to "
					+ strUpdate4
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNameShared
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					for (int i = 0; i < 4; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody2_Role)
									|| strMsg.equals(strMsgBody2_Private)
									|| strMsg.equals(strMsgBody2_Event)
									|| strMsg.equals(strMsgBody2_Shared)) {
								intPagerRes++;
								log4j.info("Pager body is displayed");
							} else {
								log4j
										.info("Email and Pager body is NOT displayed");
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

					strSubjName = "Status Change for " + strResource;
					for (int i = 0; i < 8; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody1_Role)
									|| strMsg.equals(strMsgBody1_Private)
									|| strMsg.equals(strMsgBody1_Event)
									|| strMsg.equals(strMsgBody1_Shared)) {
								intEMailRes++;
								log4j.info("Email body is displayed");
							} else {
								log4j
										.info("Email and Pager body is NOT displayed");
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
					selenium.waitForPageToLoad("90000");
					selenium.close();
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertTrue(strFuncResult.equals(""));
				if (intResCnt == 1 && intEMailRes == 8 && intPagerRes == 4) {
					gstrResult = "PASS";
				}

			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
		} catch (Exception e) {
			gstrTCID = "119702";
			gstrTO = "Verify that user can set the status change preferences for"
					+ " a NEDOCS status type and thereafter receives the notifications "
					+ "when the status changes";
			gstrResult = "FAIL";
			gstrReason = "";

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
