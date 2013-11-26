package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;
import org.junit.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
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

public class EdgeCaseAddStatusChangePreferences {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EdgeCase_AddStatusChangePreferences");
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

	/***********************************************************************************
	'Description	:From user U1, set the status change preferences for a Text status
	                 type associated with multiple resources and verify that appropriate
	                 details are displayed in the notifications received. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:2-Nov-2012
	'Author			:QSG
	'----------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***********************************************************************************/

	@Test
	public void testBugEdgeCase118102() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();

		try {
			gstrTCID = "118102";
			gstrTO = "From user U1, set the status change preferences for a Text status" +
					"type associated with multiple resources and verify that appropriate " +
					"details are displayed in the notifications received ";
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

			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String statrTextTypeName = "TST" + strTimeText;
			String strTSTValue = "Text";
			String strStatTypDefn = "Automation";
			String strSTvalue[] = new String[1];
			// RT
			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";
			// RS
			String strResource = "AutoRs_1" + strTimeText;
			String strResource2 = "AutoRs_2" + strTimeText;
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strResValue = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[2];
            //Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			
			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";
			
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			String strCurrDate = "";
			String strCurrDate2 = "";

			int intResCnt = 0;
			int intEMailRes = 0;
			int intPagerRes = 0;

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			
			String strMsgBody1_RS1 = "";
			String strMsgBody2_RS1 = "";			
			String strMsgBody1_RS2 = "";
			String strMsgBody2_RS2 = "";
			String strSubjName = "";		
			String strUpdate1="Update1";
			String strUpdate2="Update2";
				
	  log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 1. Test user has created status types 'NST', 'MST' & 'SST'
		 */	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statrTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTextTypeName);
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

			/*4. Resource 'RS' is created under 'RT' */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource);
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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource2, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResValue = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource2);
				if (strResValue.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResValue;
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
				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = { { strSTvalue[0], "true" }};
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(seleniumPrecondition, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName_1);
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
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_A, strInitPwd, strConfirmPwd,
						strUsrFulName_A);
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource2, strRSValue[1], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6. User 'A' is provided with 'Email' and 'Pager' address.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_A, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7. View 'V1' is created selecting 'NST', 'MST' & 'SST' and 'RS'
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTValues[] = { strSTvalue[0]};
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTValues, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");						
		log4j.info("--------------- test case " + gstrTCID+ " execution starts----------");
					
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
				String[] strSTName = { statrTextTypeName};
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(selenium,
								strResource, strRSValue[0], strSTvalue[0],
								statrTextTypeName, true, true, true);
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
			
			//RS2		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource2, strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrTextTypeName};
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(selenium,
								strResource2, strRSValue[1], strSTvalue[0],
								statrTextTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 9 Navigate to view 'V1' and select to update the status clicking
		 * the 'Key' symbol. 'Update Status' screen is displayed.
		 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
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
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 10 Provide the following values: 1. TST: A value above 'A' 
		 */

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
						+ statrTextTypeName + " status from -- to "+strUpdate1+".";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTextTypeName + " status from -- to "+strUpdate1+".";

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

			
			strMsgBody2_RS1 = statrTextTypeName + " from -- to "+strUpdate1+"; "
					+ "Reasons: \nSummary at " + strCurrDate +" "+"\n"
					+ "Region: " + strRegn + "";
			
			strMsgBody1_RS1 = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statrTextTypeName
					+ " status from -- "
					+ "to "+strUpdate1+".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrTextTypeName
					+ " status:"
					+ "\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			//RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate2, strSTvalue[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * String str = strDate[0].substring(1, 3); log4j.info(str);
		 */
			
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
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 10 Provide the following values: 1. TST: A value above 'A' 
		 */

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
						+ statrTextTypeName + " status from -- to "+strUpdate2+".";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTextTypeName + " status from -- to "+strUpdate2+".";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource2,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);
				strMsgBody2_RS2 = statrTextTypeName + " from -- to "+strUpdate2+"; "
						+ "Reasons: \nSummary at " + strCurrDate +" "+ "\n"
						+ "Region: " + strRegn + "";
		
				strMsgBody1_RS2 = "Status Update for "
						+ strUsrFulName_A
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource2
						+ " "
						+ "changed "
						+ statrTextTypeName
						+ " status from -- "
						+ "to "+strUpdate2+".\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrTextTypeName
						+ " status:"
						+ "\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				Writer output = null;
				String text = strMsgBody1_RS1;
				File file = new File(
						"C:\\Documents and Settings\\All Users\\Desktop\\Input.txt");
				output = new BufferedWriter(new FileWriter(file));
				output.write(text);
				output.close();

				selenium.selectWindow("");
				strSubjName = "Change for " + strAbbrv;
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
						String[] strMails = objMail.enodeToUnicode(
								selenium, strMsg, 160);
						strMsg = strMails[1];
						
						if (strMsg.equals(strMsgBody2_RS1)
								|| strMsg.equals(strMsgBody2_RS2)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
							gstrReason="Email and Pager body is NOT displayed";
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
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
						String[] strMails = objMail.enodeToUnicode(
								selenium, strMsg, 160);	
						strMsg = strMails[1];
						
						if (strMsg.equals(strMsgBody2_RS1)
								|| strMsg.equals(strMsgBody2_RS2)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
							gstrReason="Email and Pager body is NOT displayed";
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");


					strSubjName = "Status Change for " + strResource;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						output = null;
						text = strMsg;
						file = new File(
								"C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1_RS1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						
						} else {
							log4j.info("Email and Pager body is NOT displayed");
							gstrReason="Email and Pager body is NOT displayed";
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
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						output = null;
						text = strMsg;
						file = new File(
								"C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1_RS1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						
						} else {
							log4j.info("Email and Pager body is NOT displayed");
							gstrReason="Email and Pager body is NOT displayed";
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");


					strSubjName = "Status Change for " + strResource2;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						output = null;
						text = strMsg;
						file = new File(
								"C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1_RS2)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						
						} else {
							log4j.info("Email and Pager body is NOT displayed");
							gstrReason="Email and Pager body is NOT displayed";
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
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						output = null;
						text = strMsg;
						file = new File(
								"C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1_RS2)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						
						} else {
							log4j.info("Email and Pager body is NOT displayed");
							gstrReason="Email and Pager body is NOT displayed";
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();

					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);
					
					// check Email, pager notification
					if (intEMailRes == 4 && intPagerRes == 2 && intResCnt == 1) {
						gstrResult = "PASS";
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
			gstrTCID = "118102";
			gstrTO = "From user U1, set the status change preferences for a Text status" +
					"type associated with multiple resources and verify that appropriate " +
					"details are displayed in the notifications received ";
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
	
	/******************************************************************************
	'Description	:Verify that �--�is displayed in the status change notification 
	                 when the status of the Text status type is updated from blank
	                  to a status value.
	'Arguments		:None
	'Returns		:None
	'Date	 		:2-Nov-2012
	'Author			:QSG
	'------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	*******************************************************************************/

	@Test
	public void testBugEdgeCase118150() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();

		try {
			gstrTCID = "118150";
			gstrTO = "Verify that �--�is displayed in the status change notification when the " +
					"status of the Text status type is updated from blank to a status value. ";
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

			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String statrTextTypeName = "TST" + strTimeText;
			String strTSTValue = "Text";
			String strStatTypDefn = "Automation";
			String strSTvalue[] = new String[1];
			// RT
			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";
			// RS
			String strResource = "AutoRs_1" + strTimeText;
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
            //Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			
			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";
			
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			String strCurrDate = "";
			String strCurrDate2 = "";

			int intResCnt = 0;
			int intEMailRes = 0;
			int intPagerRes = 0;

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1 = "";
			String strMsgBody2 = "";
			String strSubjName = "";
				
	  log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 1. Test user has created status types 'NST', 'MST' & 'SST'
		 */	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//NST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statrTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTextTypeName);

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

			/*4. Resource 'RS' is created under 'RT' */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource);
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
				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = { { strSTvalue[0], "true" }};
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(seleniumPrecondition, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName_1);
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
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_A, strInitPwd, strConfirmPwd,
						strUsrFulName_A);
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6. User 'A' is provided with 'Email' and 'Pager' address.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_A, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7. View 'V1' is created selecting 'NST', 'MST' & 'SST' and 'RS'
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTValues[] = { strSTvalue[0]};
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTValues, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");						
		log4j.info("--------------- test case " + gstrTCID+ " execution starts----------");
					
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
				String[] strSTName = { statrTextTypeName};
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(selenium,
								strResource, strRSValue[0], strSTvalue[0],
								statrTextTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			
		/*
		 * 9 Navigate to view 'V1' and select to update the status clicking
		 * the 'Key' symbol. 'Update Status' screen is displayed.
		 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
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
						"101", strSTvalue[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}						
		/*
		 * String str = strDate[0].substring(1, 3); log4j.info(str);
		 */
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
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 10 Provide the following values: 1. TST: A value above 'A' 
		 */

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
						+ statrTextTypeName + " status from -- to 101.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTextTypeName + " status from -- to 101.";
				
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

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrTextTypeName + " from -- to 101; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName_A
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource
						+ " "
						+ "changed "
						+ statrTextTypeName
						+ " status from -- "
						+ "to 101.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrTextTypeName
						+ " status:"
						+ "\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				Writer output = null;
				String text = strMsgBody1;
				File file = new File(
						"C:\\Documents and Settings\\All Users\\Desktop\\Input.txt");
				output = new BufferedWriter(new FileWriter(file));
				output.write(text);
				output.close();

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
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

					strSubjName = "Status Change for " + strResource;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						output = null;
						text = strMsg;
						file = new File(
								"C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						output = null;
						text = strMsg;
						file = new File(
								"C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();

					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);
					
					// check Email, pager notification
					if (intEMailRes == 2 && intPagerRes == 1 && intResCnt == 1) {
						gstrResult = "PASS";
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
			gstrTCID = "118150";
			gstrTO = "Verify that �--�is displayed in the status change notification when the " +
					"status of the Text status type is updated from blank to a status value. ";
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
		
	/***************************************************************
	'Description		:Verify that status change notification for Text 
	'					 status type can be received for region RG1's resource 
	'					 from region RG2, when RG1 and RG2 have other region view agreement. 
	'Arguments		:None
	'Returns		:None
	'Date			:2/May/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testEdgeCase118104() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Resources objResources = new Resources();
		Views objViews = new Views();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Preferences objPreferences = new Preferences();
		EventSetup objEventSetup = new EventSetup();
		Roles objRoles = new Roles();
		General objMail = new General();
		EventList objEventList = new EventList();
		EventNotification objEventNotification = new EventNotification();
		try {
			gstrTCID = "118104"; // Test Case Id
			gstrTO = "Verify that status change notification for Text status "
					+ "type can be received for region RG1's resource from region RG2,"
					+ " when RG1 and RG2 have other region view agreement. ";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTmText = dts.getCurrentDate("HHmm");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);

			String strRegionName1 = "Arkansas";
			String strRegionName2 = "Statewide Oklahoma";

			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// Data for creating user
			String strUserNameRG1 = "AutoUsr1" + System.currentTimeMillis();
			String strUserNameRG2 = "AutoUsr2" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameRG1 = "FN" + strUserNameRG1;
			String strUsrFulNameRG2 = "FN" + strUserNameRG2;
			String strOptions = "";

			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";

			String strRoleTST = "rTST" + strTimeText;
			String statPivateTST = "pTST" + strTimeText;
			String statSharedTST = "sTST" + strTimeText;
			String statEventSharedTST = "esTST" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValues[] = new String[4];

			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strCountry = "Brown County";
			String strResVal = "";
			String strRSValues[] = new String[1];

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "This is an automation event";
			String strETValue = "";
			String strEventValue = "";

			// Search RS
			String strState = "Indiana";
		

			String strRoleValue_Update = "";
			String strRolesName_Update = "Role" + strTimeText;

			String strUpdate1 = "Update1";
			String strUpdate2 = "Update2";
			String strUpdate3 = "Update3";
			String strUpdate4 = "Update4";

			String strStartDate = "";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			int intEMailRes = 0;
			int intPagerRes = 0;

			int intResCnt = 0;
			int intResCntNotRecv = 0;
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			String strMsgBody1_Shared = "";
			String strMsgBody2_Shared = "";

			String strMsgBody1_Event = "";
			String strMsgBody2_Event = "";
			String strSubjName = "";

			String strCurrDate = "";
			String strCurrDate2 = "";

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");
			try {

				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegionName1);
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

			// Role based status type
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, strRoleTST,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								strRoleTST);
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Private status type
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.createPrivateSTWitMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statPivateTST, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statPivateTST);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Shared status type
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statSharedTST, strStatTypDefn, false);
				seleniumPrecondition
						.click("css=input['name=visibility'][value='SHARED']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statSharedTST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statSharedTST);

				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event only Shared status type
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statEventSharedTST, strStatTypDefn, false);
				seleniumPrecondition
						.click("css=input['name=visibility'][value='SHARED']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statEventSharedTST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statEventSharedTST);

				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT
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
						seleniumPrecondition, strResrcTypName,
						strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 1; i < 4; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition, strStatusTypeValues[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.createResourceWitLookUPadresSharWitRgn(
								seleniumPrecondition, strResource, strAbbrv,
								strResrcTypName, true, "FN", "LN", strState,
								strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValues[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

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

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_Update);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strStatusTypeValues[0], "true" },
						{ strStatusTypeValues[1], "true" },
						{ strStatusTypeValues[2], "true" },
						{ strStatusTypeValues[3], "true" } };
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
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strRsTypeValues, strStatusTypeValues);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventWitManyRS(
						seleniumPrecondition, strTempName, strRSValues,
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
						seleniumPrecondition, strUserNameRG1, strInitPwd,
						strConfirmPwd, strUsrFulNameRG1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRegionvalue = "2153";// Arkansas
				strFuncResult = objCreateUsers.slectAndDeselectOtherRegion(
						seleniumPrecondition, strRegionvalue, true, false);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValues[0],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameRG1, strByRole,
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
						seleniumPrecondition, strRegionName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER
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
						seleniumPrecondition, strUserNameRG2, strInitPwd,
						strConfirmPwd, strUsrFulNameRG2);
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
				String strRegionvalue = "2155";// Arkansas
				strFuncResult = objCreateUsers.slectAndDeselectOtherRegion(
						seleniumPrecondition, strRegionvalue, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameRG2, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameRG2,
						strInitPwd);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValues[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=#r_name";
				String strText = strResrcTypName + "�" + strResource;
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
				String[] strSTName = { strRoleTST };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPage(
								selenium, strSTName);
				if (strFuncResult
						.equals(strRoleTST+" is NOT displayed under section Uncategorized "
								+ "in the 'Edit My Status Change Preferences' screen. ")) {
					strFuncResult = "";
					log4j.info(" Status Type " + strRoleTST
							+ "is NOT displayed under section Uncategorized");
				} else {
					log4j.info(" Status Type " + strRoleTST
							+ "is displayed under section Uncategorized");
					strFuncResult = " Status Type " + strRoleTST
							+ "is displayed under section Uncategorized";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statPivateTST };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPage(
								selenium, strSTName);

				if (strFuncResult
						.equals(statPivateTST+" is NOT displayed under section Uncategorized "
								+ "in the 'Edit My Status Change Preferences' screen. ")) {
					strFuncResult = "";
					log4j.info(" Status Type " + statPivateTST
							+ "is NOT displayed under section Uncategorized");
				} else {
					log4j.info(" Status Type " + statPivateTST
							+ "is displayed under section Uncategorized");
					strFuncResult = " Status Type " + statPivateTST
							+ "is displayed under section Uncategorized";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statSharedTST };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statEventSharedTST };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(selenium,
								strResource, strRSValues[0],
								strStatusTypeValues[2], statSharedTST, true,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(selenium,
								strResource, strRSValues[0],
								strStatusTypeValues[3], statEventSharedTST,
								true, true, true);
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameRG1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
						strUpdate1, strStatusTypeValues[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate3, strStatusTypeValues[2], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
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
				strFuncResult = objLogin.logout(selenium);
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserNameRG2, strInitPwd);
			} catch (Exception Ae) {
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
						+ statSharedTST + " status from -- to " + strUpdate3
						+ ".";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statSharedTST + " status from -- to " + strUpdate3
						+ ".";

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

			strMsgBody2_Shared = statSharedTST + " from -- to " + strUpdate3
					+ "; " + "Reasons: \nSummary at " + strCurrDate + " \n"
					+ "Region: " + strRegionName1 + "";

			strMsgBody1_Shared = "Status Update for "
					+ strUsrFulNameRG2
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statSharedTST
					+ " status from -- "
					+ "to "
					+ strUpdate3
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegionName1
					+ "\n\nOther "
					+ strResrcTypName
					+ "s "
					+ "in the region report the following "
					+ statSharedTST
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserNameRG1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
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
						strUpdate2, strStatusTypeValues[1], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate4, strStatusTypeValues[3], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (Exception Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserNameRG2, strInitPwd);
			} catch (Exception Ae) {
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
						+ statEventSharedTST + " status from -- to "
						+ strUpdate4 + ".";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statEventSharedTST + " status from -- to "
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

			strMsgBody2_Event = statEventSharedTST + " from -- to " + strUpdate4
					+ "; " + "Reasons: \nSummary at " + strCurrDate + " \n"
					+ "Region: " + strRegionName1 + "";

			strMsgBody1_Event = "Status Update for "
					+ strUsrFulNameRG2
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statEventSharedTST
					+ " status from -- "
					+ "to "
					+ strUpdate4
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegionName1
					+ "\n\nOther "
					+ strResrcTypName
					+ "s "
					+ "in the region report the following "
					+ statEventSharedTST
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(30000);

				strSubjName = "Change for " + strAbbrv;

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));

					for (int i = 0; i < 2; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {

							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							if (strMsg.equals(strMsgBody2_Shared)
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

					for (int i = 0; i < 4; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							if (strMsg.equals(strMsgBody1_Shared)
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

					try {
						strSubjName = "Change for " + strAbbrv;

						for (int j = 0; j < 2; j++) {
							assertTrue(strFuncResult.equals(""));
							strFuncResult = objMail.verifyEmail(selenium,
									strFrom, strTo, strSubjName);

							try {
								assertTrue(strFuncResult
										.equals("The mail with subject "
												+ strSubjName
												+ " is NOT present in the inbox"));
								strFuncResult="";
								intResCntNotRecv++;

							} catch (AssertionError Ae) {
								gstrReason = gstrReason + " " + strFuncResult;
							}
						}

						strSubjName = "Status Change for " + strResource;

						for (int j = 0; j < 4; j++) {
							strFuncResult = objMail.verifyEmail(selenium,
									strFrom, strTo, strSubjName);

							try {
								assertTrue(strFuncResult
										.equals("The mail with subject "
												+ strSubjName
												+ " is NOT present in the inbox"));
								strFuncResult="";
								intResCntNotRecv++;
							} catch (AssertionError Ae) {
								gstrReason = gstrReason + " " + strFuncResult;
							}

							
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
				selenium.selectFrame("Data");
				Thread.sleep(4000);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", gstrReason);

				if (intResCntNotRecv == 6 && intPagerRes == 2
						&& intEMailRes == 4 && intResCnt==1)
					gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118104";
			gstrTO = "Verify that status change notification for Text status type can be"
					+ " received for region RG1's resource from region RG2, when RG1"
					+ " and RG2 have other region view agreement. ";
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
	
	/*****************************************************************************
	 * 'Description :Verify that user with a role to view the Text status type can 
	 *               set and receive the status change preferences notification
	 * 'Arguments   :None 
	 * 'Returns     :None 
	 * 'Date        :03-May-2013
	 * 'Author      :QSG
	 * '--------------------------------------------------------------------------
	 * 'Modified Date                                                 Modified By
     * '<Date>                                                        <Name>
	 *****************************************************************************/

	@Test
	public void testEdgeCase118097() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();// object of class General
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();

		try {
			gstrTCID = "118097";
			gstrTO = "Verify that user with appropriate right can set "
					+ "status change notifications preferences for other users in a region ";
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

			String strUserName_RecvNoti = "AutoUsr"
					+ System.currentTimeMillis();
			// String strUsrFulName_RecvNoti = strUserName_RecvNoti;

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

			log4j.info("----Precondtion for test case " + gstrTCID+ " starts----");

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
			 * 1. Role based rTST, private pTST and event related ETST status
			 * types are created.
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

			// Role to View

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
				String strSTvalues[][] = {};
				String strSTvaluesUpdate[][] = {};

				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, true, false, strSTvalues,
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

			// Role to update

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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User who receives notification

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
						false, true, false, true);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("----Precondtion for test case " + gstrTCID+ " ends----");
			log4j.info("----test case " + gstrTCID+ " execution starts----");

			/*
			 * 2 Login as user 'A' and navigate to 'Preferences>>Status Change
			 * Prefs' 'My Status Change Preferences' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_RecvNoti, strInitPwd);
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
			 * 3. Select 'Add' 'Find Resources' page is displayed. 4. Search for
			 * resource 'RS' Resource 'RS' is retrieved. 5.Select the checkbox
			 * associated with 'RS' and select 'Notifications' 'Edit My Status
			 * Change Preferences' is displayed. Sub header is displayed as
			 * 'RT-RS' is displayed. Status types 'NST', 'MST' & 'SST' are
			 * displayed under 'Uncategorized'.
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_RecvNoti,
						strInitPwd);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_RecvNoti,
						strInitPwd);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_RecvNoti,
						strInitPwd);
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
			gstrTCID = "118097";
			gstrTO = "Verify that user with appropriate right can set "
					+ "status change notifications preferences for other users in a region ";
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
	
	/*********************************************************************************
	 * 'Description :Verify that user can set the status change preferences for a Text
	 *             'status type and receives the notifications when the status changes 
	 * 'Arguments   : None
	 * 'Returns     : None 
	 * 'Date        : 06-May-2013
	 * 'Author      : QSG
	 * '------------------------------------------------------------------------------
	 *  'Modified Date                                                    Modified By 
	 *   '<Date>                                                          <Name>
	 *********************************************************************************/

	@Test
	public void testBugEdgeCase118489() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();


		try {
			gstrTCID = "118489";
			gstrTO = "Verify that status change notifications are not received when the "
					+ "status for a text status type is updated with the same value";
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
			// User
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// Staus type
			String statrTxtTypeNameRole = "rTST" + strTimeText;
			String strStatTypDefn = "Automation";
			String strTSTValue = "Text";
			String strSTvalue[] = new String[3];
			// RT and RS
			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";
			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];

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
			String strSubjName = "";
			String strCurrDate = "";
			String strCurrDate2 = "";
			String strUpdate1 = "Update1";

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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
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
			 * 3 Select 'Add' 'Find Resources' page is displayed. 4 Search for
			 * resource 'RS' Resource 'RS' is retrieved. 5 Select the checkbox
			 * associated with 'RS' and select 'Notifications' 'Edit My Status
			 * Change Preferences' is displayed. Sub header is displayed as
			 * 'RT-RS' is displayed. Status types 'TST' displayed under
			 * 'Uncategorized'.
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
			/*
			 * 9 Navigate to view 'V1' and select to update the status clicking
			 * the 'Key' symbol. 'Update Status' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
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

			strMsgBody1_Role = statrTxtTypeNameRole + " from -- to "
					+ strUpdate1 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody2_Role = "Status Update for "
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
				intResCnt++;
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

						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1_Role)) {
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

					strSubjName = "Status Change for " + strResource;

					for (int i = 0; i < 2; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							if (strMsg.equals(strMsgBody2_Role)) {
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
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad("90000");

				selenium.selectWindow("");
				selenium.selectFrame("Data");
				Thread.sleep(4000);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Verify that status change notifications are not received when the
			// status for
			// a text status type is updated with the same value

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
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
						+ statrTxtTypeNameRole + " status from "+ strUpdate1 + " to "
						+ strUpdate1 + ".";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTxtTypeNameRole + " status from "+ strUpdate1 + " to "
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
				
				
				if(strFuncResult.equals("Web Notification is NOT displayed")){
					log4j.info("Web Notification is NOT displayed");
					strFuncResult="";
					intResCnt++;
				}else{
					log4j.info("Web Notification is displayed");
					gstrReason="Web Notification is displayed";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(30000);
				strSubjName = "Change for " + strAbbrv;

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

			} catch (AssertionError Ae) {
				gstrReason =  gstrReason + " " + strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.verifyEmailSubName(selenium, strSubjName);

				try {
					assertTrue(strFuncResult
							.equals("The mail with subject " + strSubjName
									+ " is NOT present in the inbox"));
					intResCnt++;

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				strSubjName = "Status Change for " + strResource;

				strFuncResult = objMail.verifyEmailSubName(selenium, strSubjName);

				try {
					assertTrue(strFuncResult
							.equals("The mail with subject " + strSubjName
									+ " is NOT present in the inbox"));
					intResCnt++;
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				strFuncResult = objMail.verifyEmailSubName(selenium, strSubjName);

				try {
					assertTrue(strFuncResult
							.equals("The mail with subject " + strSubjName
									+ " is NOT present in the inbox"));
					intResCnt++;
					strFuncResult = "";
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}
				

				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad("90000");
				selenium.close();

				selenium.selectWindow("");
				selenium.selectWindow("");
				selenium.selectFrame("Data");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertTrue(strFuncResult.equals(""));
				if (intResCnt == 5 && intEMailRes == 2 && intPagerRes == 1) {
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}
		
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118489";
			gstrTO = "Verify that status change notifications are not received when the "
					+ "status for a text status type is updated with the same value";
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
	/*********************************************************************
	'Description   :Verify that user can set status change preferences for 
	                a Text status type associated with a sub-resource
	'Arguments	   :None
	'Returns	   :None
	'Date		   :7/May/2013
	'Author		   :QSG
	'---------------------------------------------------------------------
	'Modified Date				                               Modified By
	'2/25/2013					                               Name
	**********************************************************************/

	@Test
	public void testEdgeCase118486() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		ResourceTypes objRT = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objRs = new Resources();
		General objMail = new General();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRoles = new Roles();
		Views objViews = new Views();
		EventNotification objEventNotification = new EventNotification();
		Preferences objPreferences = new Preferences();
		
		try {
			gstrTCID = "118486";
			gstrTO = "Verify that user can set status change preferences for a Text status type " +
					"associated with a sub-resource";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTmText = dts.getCurrentDate("HHmm");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strTextStatType = "AutoTSt_" + strTimeText;
			String statrTxtTypeNameRole = "rTST" + strTimeText;
			String statrTxtTypeNamePrivate = "pTST" + strTimeText;
			String statrTxtTypeNameShared = "sTST" + strTimeText;
			String strStatTypDefn = "Auto";	
			String strTSTValue = "Text";			
			String strSTvalue[] = new String[4];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTvalue[] = new String[2];

			String strResource = "AutoRs_" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[2];

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";

			String strRolesName1 = "Rol_" + strTimeText;
			String strRoleValue = "";

			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
						
			String strSection1 = "AB_1" + strTimeText;
			String strSection2 = "AB_2" + strTimeText;
			String strSection3 = "AB_3" + strTimeText;
			String[] strSectionValue = new String[3];
			
			String strUpdate1="Update1";
			String strUpdate2="Update2";
			String strUpdate3="Update3";
			
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			String strCurrDate = "";
			String strCurrDate2 = "";

			int intResCnt = 0;
			int intEMailRes = 0;
			int intPagerRes = 0;

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			String strMsgBody1_Role = "";
			String strMsgBody2_Role = "";

			String strMsgBody1_Private = "";
			String strMsgBody2_Private = "";

			String strMsgBody1_Shared = "";
			String strMsgBody2_Shared = "";
			
			String strsubResource=strResource+": "+strSubResource;

		log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);
			
			try {
				assertEquals("", strFuncResult);				
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, strTextStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTextStatType);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// rTST

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
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTxtTypeNameRole);
				if (strSTvalue[1].compareTo("") != 0) {
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
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTxtTypeNamePrivate);

				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// shared TST
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
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statrTxtTypeNameShared, strVisibilty, true);
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
		 * 3. Resource type 'RT' is created and is associated with ST .
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
				strRTvalue[0] = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
             //Sub Resource type
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
								+ strSTvalue[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[3] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSubResourceType(seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTvalue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
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
			 * 6. Sub-Resource 'RS1' is created under parent resource 'RS'
			 * selecting sub-resource type 'RT1'
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
		   //Role 1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strSTUpdateValue = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTUpdateValue,
						strSTUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName1);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 2. rTST , pTST, sTST, status types are under status type section SECTIONS.
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArStatType1 = { statrTxtTypeNameRole };
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strArStatType1, strSection1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSectionValue[0] = objViews.fetchSectionID(seleniumPrecondition,
						strSection1);
				if (strSectionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strArStatType1 = { statrTxtTypeNamePrivate };
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strArStatType1, strSection2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSectionValue[1] = objViews.fetchSectionID(seleniumPrecondition,
						strSection2);
				if (strSectionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strArStatType1 = { statrTxtTypeNameShared };
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strArStatType1, strSection3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSectionValue[2] = objViews.fetchSectionID(seleniumPrecondition,
						strSection3);
				if (strSectionValue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		/*
		 * 7. User U1 is created selecting the following rights: a. 'View
		 * Resource' and 'Update resource' right on RS. b. Role R1 to view
		 * and update NST , MST, SST, TST,ST1,ST2 status types. c.
		 * 'Configure Region View' right. d. 'View Custom view' right. e.
		 * 'Maintain Events' right.
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
						seleniumPrecondition, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);				
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
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}					
		/*
		 * 10. View 'V1' is created selecting status types NST,MST,TST,SST
		 * and resource type RT.
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTValue = { strSTvalue[0],strSTvalue[1],strSTvalue[2],strSTvalue[3] };
				String[] strRSvalue = { strRSValue[0] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTValue, false, strRSvalue);
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
		 *  user can set status change preferences for a Text status type associated with a sub-resource
		 */
				
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
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=#r_name";
				String strText = strSubResrctTypName + "�" + strResource+": "+strSubResource;
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
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(selenium,
								strsubResource, strRSValue[1], strSTvalue[1],
								statrTxtTypeNameRole, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(selenium,
								strsubResource, strRSValue[1], strSTvalue[2],
								statrTxtTypeNamePrivate, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(selenium,
								strsubResource, strRSValue[1], strSTvalue[3],
								statrTxtTypeNameShared, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Add three STatus types RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditSubResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { strSTvalue[1], strSTvalue[2],strSTvalue[3] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								selenium, strSubResrctTypName, strRTvalue[1],
								strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Update all three status types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(
						selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate1, strSTvalue[1], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
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
				
				String strDesc1 = "On " + strCurrDate + " " + strResource
						+ ": " + strSubResource + "" + " changed "
						+ statrTxtTypeNameRole + " status from -- to "
						+ strUpdate1 + ".";

				String strDesc2 = "On " + strCurrDate2 + " " + strResource
						+ ": " + strSubResource + "" + " changed "
						+ statrTxtTypeNameRole + " status from -- to "
						+ strUpdate1 + ".";			
				
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strsubResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Role = statrTxtTypeNameRole + " from -- to "
					+ strUpdate1 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_Role = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource+": "+strSubResource
					+ " "
					+ "changed "
					+ statrTxtTypeNameRole
					+ " status from -- "
					+ "to "
					+ strUpdate1
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(
						selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate2, strSTvalue[2], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
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
				
				String strDesc1 ="On " + strCurrDate + " " + strResource
						+ ": " + strSubResource + "" + " changed "
						+ statrTxtTypeNamePrivate + " status from -- to "
						+ strUpdate2 + ".";

				String strDesc2 = "On " + strCurrDate + " " + strResource
						+ ": " + strSubResource + "" + " changed "
						+ statrTxtTypeNamePrivate + " status from -- to "
						+ strUpdate2 + ".";
								
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strsubResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Private = statrTxtTypeNamePrivate + " from -- to "
					+ strUpdate2 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";
			
			strMsgBody1_Private = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource+": "+strSubResource
					+ " "
					+ "changed "
					+ statrTxtTypeNamePrivate
					+ " status from -- "
					+ "to "
					+ strUpdate2
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";
			
			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(
						selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate3, strSTvalue[3], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
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

				String strDesc1 = "On " + strCurrDate + " " + strResource
						+ ": " + strSubResource + "" + " changed "
						+ statrTxtTypeNameShared + " status from -- to "
						+ strUpdate3 + ".";
				
				String strDesc2 = "On " + strCurrDate + " " + strResource
						+ ": " + strSubResource + "" + " changed "
						+ statrTxtTypeNameShared + " status from -- to "
						+ strUpdate3 + ".";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strsubResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Shared = statrTxtTypeNameShared + " from -- to "
					+ strUpdate3 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";
			
			strMsgBody1_Shared = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ ": "
					+ strSubResource
					+ " "
					+ "changed "
					+ statrTxtTypeNameShared
					+ " status from -- "
					+ "to "
					+ strUpdate3
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\nPlease do not reply to this email message."
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
							
							String[] strMails = objMail.enodeToUnicode(selenium,
									strMsg, 160);
							strMsg = strMails[1];
							
							if (strMsg.equals(strMsgBody2_Role)
									|| strMsg.equals(strMsgBody2_Private)
									|| strMsg.equals(strMsgBody2_Shared)) {
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

					strSubjName = "Status Change for "+strResource+": "+strSubResource;

					for (int i = 0; i < 6; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
 							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							
							String[] strMails = objMail.enodeToUnicode(selenium,
									strMsg, 160);
							strMsg = strMails[1];
							
							if (strMsg.equals(strMsgBody1_Role)
									|| strMsg.equals(strMsgBody1_Private)
									|| strMsg.equals(strMsgBody1_Shared)) {
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
			gstrTCID = "118486";
			gstrTO = "Verify that user can set status change preferences for a Text status type "
					+ "associated with a sub-resource";
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
	/********************************************************************************
	'Description	:Verify that status change preferences cannot be received/set for 
	                 a Text status type that is refined for a particular resource
	'Arguments		:None
	'Returns		:None
	'Date	 		:2-Nov-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/

	@Test
	public void testEdgeCase118099() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();

		try {
			gstrTCID = "118099";
			gstrTO = "Verify that status change preferences cannot be received/set for a Text"
					+ " status type that is refined for a particular resource";
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

			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String statrTextTypeName = "TST" + strTimeText;
			String strTSTValue = "Text";
			String strStatTypDefn = "Automation";
			String strSTvalue[] = new String[1];
			// RT
			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";
			// RS
			String strResource1 = "AutoRs_1" + strTimeText;
			String strResource2 = "AutoRs_2" + strTimeText;
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strResValue = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[2];
			// Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

			String strUpdate1 = "Update1";

			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			String strCurrDate = "";
			String strCurrDate2 = "";

			int intResCnt = 0;
			int intEMailRes = 0;
			int intPagerRes = 0;

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			String strMsgBody1_Role = "";
			String strMsgBody2_Role = "";

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

			// NST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statrTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrTextTypeName);

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

			/*4. Resource 'RS' is created under 'RT' */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResource1);
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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResValue = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strResValue.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResValue;
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource2, strRSValue[1],
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");						
			log4j.info("--------------- test case " + gstrTCID+ " execution starts----------");
					
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource1, strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrTextTypeName };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(selenium,
								strResource1, strRSValue[0], strSTvalue[0],
								statrTextTypeName, true, true, true);
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

			// RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource2, strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrTextTypeName };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(selenium,
								strResource2, strRSValue[1], strSTvalue[0],
								statrTextTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * Text status type that is refined for a particular resource
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_A, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_A, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 9 Navigate to view 'V1' and select to update the status clicking
		 * the 'Key' symbol. 'Update Status' screen is displayed.
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource1);
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
						+ statrTextTypeName + " status from -- to "
						+ strUpdate1 + ".";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrTextTypeName + " status from -- to "
						+ strUpdate1 + ".";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody1_Role = statrTextTypeName + " from -- to " + strUpdate1
					+ "; " + "Reasons: \nSummary at " + strCurrDate + " \n"
					+ "Region: " + strRegn + "";

			strMsgBody2_Role = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource1
					+ " "
					+ "changed "
					+ statrTextTypeName
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
					+ statrTextTypeName
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
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

						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1_Role)) {
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

					strSubjName = "Status Change for " + strResource1;

					for (int i = 0; i < 2; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							if (strMsg.equals(strMsgBody2_Role)) {
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
					assertTrue(strFuncResult.equals(""));
					if (intResCnt == 1 && intEMailRes == 2 && intPagerRes == 1) {
						gstrResult = "PASS";
					}
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118099";
			gstrTO = "Verify that status change preferences cannot be received/set for a Text"
					+ " status type that is refined for a particular resource";
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
	/***************************************************************************
	'Description	:Verify that status change notification for Text status type 
	                 can be received for region RG1's resource from region RG2, 
	                 when the user has access to both RG1 and RG2.
	'Arguments		:None
	'Returns		:None
	'Date			:2/May/2013
	'Author			:QSG
	'---------------------------------------------------------------------------
	'Modified Date				                                   Modified By
	'Date					                                       Name
	****************************************************************************/

	@Test
	public void testEdgeCase118103() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Preferences objPreferences = new Preferences();
		Roles objRoles = new Roles();
		General objMail = new General();
		EventNotification objEventNotification = new EventNotification();
		EventSetup objEventSetup = new EventSetup();
		Regions objRegions = new Regions();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		
		try {
			gstrTCID = "118103";
			gstrTO = "Verify that status change notification for Text status "
					+ "type can be received for region RG1's resource from region"
					+ " RG2, when the user has access to both RG1 and RG2.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");	
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.open(propEnvDetails.getProperty("urlRel"));//relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTmText = dts.getCurrentDate("HHmm");

			// Login details 
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegionName1 = rdExcel.readData("Login", 3, 4);
			String strRegionName2 = rdExcel.readData("Regions", 4, 5);
			String strRegVal = "";

			// Data for creating user
			String strUserNameRG1 = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameRG1 = "FN" + strUserNameRG1;
			String strOptions = "";

			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";
			
			// Status Types for normal Resource
			String statrTxtTypeNameRole = "rTST" + strTimeText;
			String statrTxtTypeNamePrivate = "pTST" + strTimeText;
			String statrTxtTypeNameEvent = "eTST" + strTimeText;
			String statrTxtTypeNameShared = "sTST" + strTimeText;
			
			// Status Types for Shared Resource
			String statrTxtTypeNameRole2 = "rTST2" + strTimeText;
			String statrTxtTypeNamePrivate2 = "pTST2" + strTimeText;
			String statrTxtTypeNameEvent2 = "eTST2" + strTimeText;
			String statrTxtTypeNameShared2 = "sTST2" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValues[] = new String[8];
			
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			// RT for shared Resource
			String strResrcTypName_Shared = "AutoRT_s" + strTimeText;
			String strRsTypeValues[] = new String[2];
			
			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strCountry = "Brown County";
			String strResVal = "";
			String strState = "Indiana";
			// Resource Shared
			String strResource_Shared = "AutoRS_s" + strTimeText;
			String strRSValues[] = new String[2];
			
			// Role			
			String strRolesName_Update = "Role" + strTimeText;
			String strRoleValue_Update = "";

			// Event Template 
			String strTempName = "ET" + strTimeText;
			String strTempDef = "Automation";

			// Event
			String strEveName = "Event" + strTimeText;
			String strInfo = "This is an automation event";
			String strETValue = "";
			String strEventValue = "";
			String strUpdate1 = "Update1";
			String strUpdate2 = "Update2";
			String strUpdate3 = "Update3";
			String strUpdate4 = "Update4";
			String strUpdate5 = "Update5";
			String strUpdate6 = "Update6";
			String strUpdate7 = "Update7";
			String strUpdate8 = "Update8";

			String strStartDate = "";
			String strCurrDate = "";
			String strCurrDate2 = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strSubjName = "";
			
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			String strMsgBody1_Role = "";
			String strMsgBody2_Role = "";

			String strMsgBody1_Private = "";
			String strMsgBody2_Private = "";

			String strMsgBody1_Shared = "";
			String strMsgBody2_Shared = "";

			String strMsgBody1_Event = "";
			String strMsgBody2_Event = "";

			String strMsgBody1_Role_2 = "";
			String strMsgBody2_Role_2 = "";

			String strMsgBody1_Private_2 = "";
			String strMsgBody2_Private_2 = "";

			String strMsgBody1_Shared_2 = "";
			String strMsgBody2_Shared_2 = "";

			String strMsgBody1_Event_2 = "";
			String strMsgBody2_Event_2 = "";
			
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;

	  log4j.info("~~~~~PRE-CONDITIION " + gstrTCID+ " EXECUTION STATRTS~~~~~");
	  		
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegionName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult =objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRegVal =objRegions.fetchRegionValue(seleniumPrecondition, strRegionName2);
				if (strRegVal.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Region value";
				}
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

			// Role based status type
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statrTxtTypeNameRole,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTxtTypeNameRole);
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Private status type
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.createPrivateSTWitMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statrTxtTypeNamePrivate, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTxtTypeNamePrivate);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			// Event only Shared status type
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statrTxtTypeNameEvent, strStatTypDefn, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTxtTypeNameEvent);

				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Event only Shared status type
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statrTxtTypeNameShared, strStatTypDefn, false);
				seleniumPrecondition
				.click("css=input['name=visibility'][value='SHARED']");				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrTxtTypeNameShared);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTxtTypeNameShared);
				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/**************************************************************************************/
					
			// Role based status type
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statrTxtTypeNameRole2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTxtTypeNameRole2);
				if (strStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Private status type
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.createPrivateSTWitMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statrTxtTypeNamePrivate2, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTxtTypeNamePrivate2);
				if (strStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			// Event only Shared status type
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statrTxtTypeNameEvent2, strStatTypDefn, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[6] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTxtTypeNameEvent2);
				if (strStatusTypeValues[6].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Event only Shared status type
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statrTxtTypeNameShared2, strStatTypDefn, false);
				seleniumPrecondition
				.click("css=input['name=visibility'][value='SHARED']");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrTxtTypeNameShared2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[7] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTxtTypeNameShared2);
				if (strStatusTypeValues[7].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
	/******************************************************************************/
						
		 // RT for non shared resource
			
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
						seleniumPrecondition, strResrcTypName,
						strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 1; i < 4; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition, strStatusTypeValues[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
/****************************************************************************************************/
			
			// RT for shared resource
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
						seleniumPrecondition, strResrcTypName_Shared,
						strStatusTypeValues[4]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 5; i < 8; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition, strStatusTypeValues[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName_Shared);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[1] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName_Shared);
				if (strRsTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

/************************************************************************************************/
			// Non shared RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, "FN", "LN", strState,
						strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValues[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.createResourceWitLookUPadresSharWitRgn(
								seleniumPrecondition, strResource_Shared, strAbbrv,
								strResrcTypName_Shared, true, "FN", "LN", strState,
								strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource_Shared);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValues[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

/**********************************************************************************************************/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_Update);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				String strSTvalues[][] = { { strStatusTypeValues[0], "true" },
						{ strStatusTypeValues[1], "true" },
						{ strStatusTypeValues[2], "true" },
						{ strStatusTypeValues[3], "true" },
						{ strStatusTypeValues[4], "true" },
						{ strStatusTypeValues[5], "true" },
						{ strStatusTypeValues[6], "true" },
						{ strStatusTypeValues[7], "true" } };
				
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
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strRsTypeValues, strStatusTypeValues);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventWitManyRS(
						seleniumPrecondition, strTempName, strRSValues,
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
			//User creation
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
						seleniumPrecondition, strUserNameRG1, strInitPwd,
						strConfirmPwd, strUsrFulNameRG1);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValues[0],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource_Shared, strRSValues[1],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameRG1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(seleniumPrecondition,
						strUserNameRG1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(seleniumPrecondition,
						strRegVal, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(seleniumPrecondition,
						strRegVal, true, true);
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
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegionName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserNameRG1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName,
						strLastName, strOrganization, strPhoneNo,
						strPrimaryEMail, "", strPagerValue,
						strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameRG1, strByRole,
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

		log4j.info("~~~~~PRE-CONDITIION " + gstrTCID+ " EXECUTION STATRTS~~~~~");			
		log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameRG1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						selenium, strRegionName2);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=#r_name";
				String strText = strResrcTypName + "�" + strResource;
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
				String[] strSTName = { statrTxtTypeNameRole,statrTxtTypeNamePrivate,
						statrTxtTypeNameEvent,statrTxtTypeNameShared };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < 4; i++) {
				String[] strSTName = { statrTxtTypeNameRole,
						statrTxtTypeNamePrivate, statrTxtTypeNameEvent,
						statrTxtTypeNameShared };
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objPreferences
							.selectSTChangeNotifforTxtStatusType(selenium,
									strResource, strRSValues[0],
									strStatusTypeValues[i], strSTName[i], true,
									true, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				if (i != 3) {
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objPreferences
								.navEditMySatPrefPgeOfPartcularRS(selenium,
										strRSValues[0]);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			}
			
			//Shared Resources
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource_Shared, strRSValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=#r_name";
				String strText = strResrcTypName_Shared + "�" + strResource_Shared;
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
				String[] strSTName = { statrTxtTypeNameRole2,statrTxtTypeNamePrivate2,
						statrTxtTypeNameEvent2,statrTxtTypeNameShared2 };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 4; i < 8; i++) {
				String[] strSTName = { "","","","",statrTxtTypeNameRole2,
						statrTxtTypeNamePrivate2, statrTxtTypeNameEvent2,
						statrTxtTypeNameShared2 };
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objPreferences
							.selectSTChangeNotifforTxtStatusType(selenium,
									strResource_Shared, strRSValues[1],
									strStatusTypeValues[i], strSTName[i], true,
									true, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				if (i != 7) {
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objPreferences
								.navEditMySatPrefPgeOfPartcularRS(selenium,
										strRSValues[1]);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			}		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

/***************************************************************************************************/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserNameRG1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						selenium, strRegionName1);
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
				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate1, strStatusTypeValues[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate2, strStatusTypeValues[1], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate3, strStatusTypeValues[2], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate4, strStatusTypeValues[3], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
			} catch (AssertionError Ae) {
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

				String strDesc1[] = {
						"On " + strCurrDate + " changed " + statrTxtTypeNameRole
								+ " status from -- to " + strUpdate1 + ".",

						"On " + strCurrDate + " changed " + statrTxtTypeNamePrivate
								+ " status from -- to " + strUpdate2 + ".",

						"On " + strCurrDate + " changed " + statrTxtTypeNameEvent
								+ " status from -- to " + strUpdate3 + ".",

						"On " + strCurrDate + " changed " + statrTxtTypeNameShared
								+ " status from -- to " + strUpdate4 + "." };

				String strDesc2[] = {
						"On " + strCurrDate2 + " changed " + statrTxtTypeNameRole
								+ " status from -- to " + strUpdate1 + ".",

						"On " + strCurrDate2 + " changed " + statrTxtTypeNamePrivate
								+ " status from -- to " + strUpdate2 + ".",

						"On " + strCurrDate2 + " changed " + statrTxtTypeNameEvent
								+ " status from -- to " + strUpdate3 + ".",

						"On " + strCurrDate2 + " changed " + statrTxtTypeNameShared
								+ " status from -- to " + strUpdate4 + "." };

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationForManyStatusTypes(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			strMsgBody2_Role = statrTxtTypeNameRole + " from -- to "
					+ strUpdate1 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegionName1 + "";

			strMsgBody1_Role = "Status Update for "
					+ strUsrFulNameRG1
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
					+ strRegionName1
					+ "\n\nOther "
					+ strResrcTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNameRole
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			
			strMsgBody1_Private = statrTxtTypeNamePrivate + " from -- to "
					+ strUpdate2 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegionName1 + "";

			strMsgBody1_Private = "Status Update for "
					+ strUsrFulNameRG1
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
					+ strRegionName1
					+ "\n\nOther "
					+ strResrcTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNamePrivate
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";
					
			strMsgBody2_Event = statrTxtTypeNameEvent + " from -- to "
					+ strUpdate3 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegionName1 + "";

			strMsgBody1_Event = "Status Update for "
					+ strUsrFulNameRG1
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
					+ strRegionName1
					+ "\n\nOther "
					+ strResrcTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNameEvent
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";
			
			strMsgBody2_Shared = statrTxtTypeNameShared + " from -- to "
					+ strUpdate4 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegionName1 + "";

			strMsgBody1_Shared = "Status Update for "
					+ strUsrFulNameRG1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statrTxtTypeNameShared
					+ " status from -- "
					+ "to "
					+ strUpdate4
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegionName1
					+ "\n\nOther "
					+ strResrcTypName
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNameShared
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";
			
/***************************************************************************************************/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource_Shared);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource_Shared);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate5, strStatusTypeValues[4], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate6, strStatusTypeValues[5], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate7, strStatusTypeValues[6], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate8, strStatusTypeValues[7], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
			} catch (AssertionError Ae) {
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

				String strDesc1[] = {
						"On " + strCurrDate + " changed " + statrTxtTypeNameRole2
								+ " status from -- to " + strUpdate5 + ".",

						"On " + strCurrDate + " changed " + statrTxtTypeNamePrivate2
								+ " status from -- to " + strUpdate6 + ".",

						"On " + strCurrDate + " changed " + statrTxtTypeNameEvent2
								+ " status from -- to " + strUpdate7 + ".",

						"On " + strCurrDate + " changed " + statrTxtTypeNameShared2
								+ " status from -- to " + strUpdate8 + "." };

				String strDesc2[] = {
						"On " + strCurrDate2 + " changed " + statrTxtTypeNameRole2
								+ " status from -- to " + strUpdate5 + ".",

						"On " + strCurrDate2 + " changed " + statrTxtTypeNamePrivate2
								+ " status from -- to " + strUpdate6 + ".",

						"On " + strCurrDate2 + " changed " + statrTxtTypeNameEvent2
								+ " status from -- to " + strUpdate7 + ".",

						"On " + strCurrDate2 + " changed " + statrTxtTypeNameShared2
								+ " status from -- to " + strUpdate8 + "." };

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationForManyStatusTypes(selenium, strResource_Shared,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_Role_2 = statrTxtTypeNameRole2 + " from -- to "
					+ strUpdate5 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegionName1 + "";

			strMsgBody1_Role_2 = "Status Update for "
					+ strUsrFulNameRG1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource_Shared
					+ " "
					+ "changed "
					+ statrTxtTypeNameRole2
					+ " status from -- "
					+ "to "
					+ strUpdate5
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegionName1
					+ "\n\nOther "
					+ strResrcTypName_Shared
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNameRole2
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			strMsgBody1_Private_2 = statrTxtTypeNamePrivate2 + " from -- to "
					+ strUpdate6 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegionName1 + "";

			strMsgBody1_Private_2 = "Status Update for "
					+ strUsrFulNameRG1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource_Shared
					+ " "
					+ "changed "
					+ statrTxtTypeNamePrivate2
					+ " status from -- "
					+ "to "
					+ strUpdate6
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegionName1
					+ "\n\nOther "
					+ strResrcTypName_Shared
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNamePrivate2
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			strMsgBody2_Event_2 = statrTxtTypeNameEvent2 + " from -- to "
					+ strUpdate7 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegionName1 + "";

			strMsgBody1_Event_2 = "Status Update for "
					+ strUsrFulNameRG1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource_Shared
					+ " "
					+ "changed "
					+ statrTxtTypeNameEvent2
					+ " status from -- "
					+ "to "
					+ strUpdate7
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegionName1
					+ "\n\nOther "
					+ strResrcTypName_Shared
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNameEvent2
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";
			
			strMsgBody2_Shared_2 = statrTxtTypeNameShared2 + " from -- to "
					+ strUpdate8 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegionName1 + "";

			strMsgBody1_Shared_2 = "Status Update for "
					+ strUsrFulNameRG1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource_Shared
					+ " "
					+ "changed "
					+ statrTxtTypeNameShared2
					+ " status from -- "
					+ "to "
					+ strUpdate8
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegionName1
					+ "\n\nOther "
					+ strResrcTypName_Shared
					+ "s "
					+ "in the region report the following "
					+ statrTxtTypeNameShared2
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

					for (int i = 0; i < 8; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {

							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							
							String[] strMails=objMail.enodeToUnicode(selenium, strMsg, 160);
							strMsg=strMails[1];
							
							if (strMsg.equals(strMsgBody2_Role)
									|| strMsg.equals(strMsgBody2_Private)
									|| strMsg.equals(strMsgBody2_Event)
									|| strMsg.equals(strMsgBody2_Shared)

									|| strMsg.equals(strMsgBody2_Role_2)
									|| strMsg.equals(strMsgBody2_Private_2)
									|| strMsg.equals(strMsgBody2_Event_2)
									|| strMsg.equals(strMsgBody2_Shared_2)) {
								
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

					for (int i = 0; i < 4; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							String[] strMails=objMail.enodeToUnicode(selenium, strMsg, 160);
							strMsg=strMails[1];
							
							if (strMsg.equals(strMsgBody1_Role)
									|| strMsg.equals(strMsgBody1_Private)
									|| strMsg.equals(strMsgBody1_Event)
									|| strMsg.equals(strMsgBody1_Shared)) {
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

					strSubjName = "Status Change for " + strResource_Shared;

					for (int i = 0; i < 4; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							String[] strMails=objMail.enodeToUnicode(selenium, strMsg, 160);
							strMsg=strMails[1];
							
							if (strMsg.equals(strMsgBody1_Role_2)
									|| strMsg.equals(strMsgBody1_Private_2)
									|| strMsg.equals(strMsgBody1_Event_2)
									|| strMsg.equals(strMsgBody1_Shared_2)) {
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
				if (intResCnt == 1 && intEMailRes == 8 && intPagerRes == 6) {
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118103";
			gstrTO = "Verify that status change notification for Text status type "
					+ "can be received for region RG1's resource from region RG2, "
					+ "when the user has access to both RG1 and RG2.";
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
}
