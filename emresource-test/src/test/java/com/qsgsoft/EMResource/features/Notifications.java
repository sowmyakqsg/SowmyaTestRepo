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

/**********************************************************************
 * ' Description :This class includes Notifications requirement ' testcases '
 * Precondition: ' Date :17-Oct-2012 ' Author :QSG
 * '------------------------------------------------------------------- '
 * Modified Date Modified By ' <Date> <Name> '
 *******************************************************************/

public class Notifications {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.Notifications");
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
		seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
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

		try{
			selenium.close();
		}catch(Exception e){
			
		}
		
		selenium.stop();
		
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
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
		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}

	/********************************************************************************
	 * 'Description :Verify that status change preferences for resources of
	 * other regions can be deleted. 'Precondition :1. Regions RG1 and RG2 have
	 * mutual agreement for example (State Wide Oklahoma and Arkansas)
	 * 
	 * 2. Shared Status type ST (number/multi/saturation score) is created in
	 * RG1.
	 * 
	 * 3. Resource type RT is created in RG1 with status type ST.
	 * 
	 * 4. Resource RS1 is created under RT by selecting option 'Share with Other
	 * Regions' in RG1 providing a valid address.
	 * 
	 * 5. Role R1 is created selecting status type ST under view and update.
	 * 
	 * 6. User U2 is created with following rights: a. 'View Resource' and
	 * 'Update Status' right on resource RS1. b. Role R1.
	 * 
	 * 7. User U1 is created in region RG2, providing email and pager addresses,
	 * selecting 'Edit Status Change Notification Preferences' right and has
	 * right to view other region view of RG1.
	 * 
	 * 8. User U1 has set Status change preferences (email, web and pager) for
	 * ST for RS1 in region RG2. 'Arguments :None 'Returns :None 'Date
	 * :17-Oct-2012 'Author :QSG
	 * '------------------------------------------------------------------------------
	 * - 'Modified Date Modified By '<Date> <Name>
	 **********************************************************************************/

	@Test
	public void testBQS84635() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();// object of class General

		try {
			gstrTCID = "BQS-84635 ";
			gstrTO = "Verify that status change preferences "
					+ "for resources of other regions can be deleted.";
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
			String strRegn1 = rdExcel.readData("Login", 3, 9);
			String strRegn2 = rdExcel.readData("Login", 4, 9);

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

			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String statTypeName1 = "ST_1" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResrctTypName1 = "AutoRt_1" + strTimeText;
			String strRTValue = "";

			String strResource1 = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

			String strAbove = "2";
			String strBelow = "5";

			String strStartDate = "";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			int intResCnt = 0;

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";

			String strCurrDate = "";
			/*
			 * 1. Regions RG1 and RG2 have mutual agreement for example (State
			 * Wide Oklahoma and Arkansas)
			 */
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Shared Status type ST (number/multi/saturation score) is
			 * created in RG1.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statTypeName1, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Resource type RT is created in RG1 with status type ST. */

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
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName1);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. Resource RS1 is created under RT by selecting option 'Share
			 * with Other Regions' in RG1 providing a valid address.
			 */

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
				strFuncResult = objRs.createShardResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv, strResrctTypName1,
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
			 * 6. User U2 is created with following rights: a. 'View Resource'
			 * and 'Update Status' right on resource RS1. b. Role R1.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User with user set up account right
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_2, strInitPwd, strConfirmPwd,
						strUsrFulName_2);

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7. User U1 is created in region RG2, providing email and pager
			 * addresses, selecting 'Edit Status Change Notification
			 * Preferences' right and has right to view other region view of
			 * RG1.
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
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn2);

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
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegionvalue = rdExcel.readData("Login", 3, 10);
				strFuncResult = objCreateUsers.slectAndDeselectOtherRegion(
						seleniumPrecondition, strRegionvalue, true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");

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

			/*
			 * 8. User U1 has set Status change preferences (email, web and
			 * pager) for ST for RS1 in region RG2.
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
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource1, strRSValue[0], strSTvalue[0],
								statTypeName1, true, true, true);
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
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource1, strRSValue[0], strSTvalue[0],
								statTypeName1, true, true, true);
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
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[0],
								strSTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user U1 and navigate to Preferences>>Status Change
			 * Prefs 'My Status Change Preferences' screen is displayed.
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
				strFuncResult = objLogin.login(selenium, strUserName_1,
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
				strFuncResult = objPreferences.deleteStaPrefOfPartcularRS(
						selenium, strRSValue[0], strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Login as user U2, navigate to Views>>Map and using the 'Update
			 * status' link on resource pop up window of RS, change the status
			 * value of ST according to the preferences set earlier (before
			 * deleting). Notifications (email, web and pager) are not received
			 * for user U1.
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_2,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViewMap.navToRegionalMapViewWithoutVisible(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindowNew1(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateNumStatusType1(selenium,
						strResource1, statTypeName1, strSTvalue[0], "2", "3");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strUpdatedDate = selenium.getText("css=span.time");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				String str = strDate[0].substring(1, 3);
				log4j.info(str);

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = str;
				strFndStYear = dts.getTimeOfParticularTimeZone("CST", "yyyy");;

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Login as user U2, navigate to Views>>Map and using the 'Update
			 * status' link on resource pop up window of RS, change the status
			 * value of ST according to the preferences set earlier (before
			 * deleting). Notifications (email, web and pager) are not received
			 * for user U1.
			 */

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
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				String strSTDateTime = strStartDate;

				String strDesc = "On " + strCurrDate + " " + strResource1
						+ " changed " + statTypeName1 + " status from 0 to 2.";

				strFuncResult = objEventNotification.ackSTWebNotificationNew(
						selenium, strResource1, strSTDateTime, strDesc);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Web Notification is NOT displayed", strFuncResult);
				intResCnt++;
				Thread.sleep(30000);

				strSubjName = "Change for " + strAbbrv;
/*
				strMsgBody2 = statTypeName1 + " from 0 to 2; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn1 + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName_1
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statTypeName1
						+ " status from 0 "
						+ "to 2.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn1
						+ "\n\nOther "
						+ strResrctTypName1
						+ "s "
						+ "in the region report the following "
						+ statTypeName1
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";*/

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

						intResCnt++;

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					strSubjName = "Status Change for " + strResource1;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));

						intResCnt++;
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));

						intResCnt++;
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

			try {
				assertEquals("", strFuncResult);
				if (intResCnt == 4) {
					gstrResult = "PASS";
				}

			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-84635";
			gstrTO = "Verify that status change preferences "
					+ "for resources of other regions can be deleted.";
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
	 * 'Description :Verify that status change preferences for resources of
	 * other regions can be edited. 'Precondition :1. Regions RG1 and RG2 have
	 * mutual agreement for example (State Wide Oklahoma and Arkansas)
	 * 
	 * 2. Shared Status type ST (number/multi/saturation score) is created in
	 * RG1.
	 * 
	 * 3. Resource type RT is created in RG1 with status type ST.
	 * 
	 * 4. Resource RS1 is created under RT by selecting option 'Share with Other
	 * Regions' in RG1 providing a valid address.
	 * 
	 * 5. Role R1 is created selecting status type ST under view and update.
	 * 
	 * 6. User U2 is created with following rights: a. 'View Resource' and
	 * 'Update Status' right on resource RS1. b. Role R1.
	 * 
	 * 7. User U1 is created in region RG2, providing email and pager addresses,
	 * selecting 'Edit Status Change Notification Preferences' right and has
	 * right to view other region view of RG1.
	 * 
	 * 8. User U1 has set Status change preferences (email, web and pager) for
	 * ST for RS1 in region RG2. N 'Arguments :None 'Returns :None 'Date
	 * :18-Oct-2012 'Author :QSG
	 * '------------------------------------------------------------------------------
	 * - 'Modified Date Modified By '<Date> <Name>
	 **********************************************************************************/

	@Test
	public void testBQS84631() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();// object of class General

		try {
			gstrTCID = "BQS-84631 ";
			gstrTO = "Verify that status change preferences for resources"
					+ " of other regions can be edited.";
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
			String strRegn1 = rdExcel.readData("Login", 3, 9);
			String strRegn2 = rdExcel.readData("Login", 4, 9);

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

			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String statTypeName1 = "ST_1" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResrctTypName1 = "AutoRt_1" + strTimeText;
			String strRTValue = "";

			String strResource1 = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

			String strAbove = "100";
			String strBelow = "50";

			String strEditAbove = "200";
			String strEditBelow = "40";

			String strStartDate = "";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

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

			String strCurrDate = "";
			String strCurrDate2 = "";
			/*
			 * 1. Regions RG1 and RG2 have mutual agreement for example (State
			 * Wide Oklahoma and Arkansas)
			 */
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Shared Status type ST (number/multi/saturation score) is
			 * created in RG1.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statTypeName1, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Resource type RT is created in RG1 with status type ST. */

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
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName1);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. Resource RS1 is created under RT by selecting option 'Share
			 * with Other Regions' in RG1 providing a valid address.
			 */

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
				strFuncResult = objRs.createShardResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv, strResrctTypName1,
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
			 * 6. User U2 is created with following rights: a. 'View Resource'
			 * and 'Update Status' right on resource RS1. b. Role R1.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User with user set up account right
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_2, strInitPwd, strConfirmPwd,
						strUsrFulName_2);

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7. User U1 is created in region RG2, providing email and pager
			 * addresses, selecting 'Edit Status Change Notification
			 * Preferences' right and has right to view other region view of
			 * RG1.
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
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn2);

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
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegionvalue = rdExcel.readData("Login", 3, 10);
				strFuncResult = objCreateUsers.slectAndDeselectOtherRegion(
						seleniumPrecondition, strRegionvalue, true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");

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

			/*
			 * 8. User U1 has set Status change preferences (email, web and
			 * pager) for ST for RS1 in region RG2.
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
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource1, strRSValue[0], strSTvalue[0],
								statTypeName1, true, true, true);
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
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource1, strRSValue[0], strSTvalue[0],
								statTypeName1, true, true, true);
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
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[0],
								strSTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user U1 and navigate to Preferences>>Status Change
			 * Prefs 'My Status Change Preferences' screen is displayed.
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
				strFuncResult = objLogin.login(selenium, strUserName_1,
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
			 * 3 Click on 'edit' link for resource RS on 'My Status Change
			 * Preferences' screen 'Edit My Status Change Preferences' screen is
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Change the preferences set and save the screen 'My Status
			 * Change Preferences' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strEditAbove, strEditBelow, strRSValue[0],
								strSTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Click on 'edit' link for resource RS on 'My Status Change
			 * Preferences' screen 'Edit My Status Change Preferences' screen is
			 * displayed and the edited data is retained.
			 */

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
						.verifyProvideSTAboveBelowRangeInEditMySTNotifPageisRetained(
								selenium, strEditAbove, strEditBelow,
								strRSValue[0], strSTvalue[0], true, true);
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_2,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViewMap.navToRegionalMapViewWithoutVisible(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateNumStatusTypeNew(selenium,
						strResource1, statTypeName1, strSTvalue[0], "101",
						"102");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=span.time");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				String str = strDate[0].substring(1, 3);
				log4j.info(str);

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = str;
				strFndStYear = dts.getTimeOfParticularTimeZone("CST", "yyyy");;

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViewMap.navToRegionalMapViewWithoutVisible(selenium);

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
			 * 6 Login as user U2, navigate to Views>>Map and using the 'Update
			 * status' link on resource pop up window of RS, change the status
			 * value of ST according to the preferences set earlier (before
			 * editing). Notifications are not received for user U1.
			 */

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
						+ statTypeName1 + " status from 0 to 101.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statTypeName1 + " status from 0 to 101.";

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
		
			try {
				assertEquals("Web Notification is NOT displayed", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statTypeName1 + " from 0 to 101; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn1 + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName_1
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statTypeName1
						+ " status from 0 "
						+ "to 101.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn1
						+ "\n\nOther "
						+ strResrctTypName1
						+ "s "
						+ "in the region report the following "
						+ statTypeName1
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

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

						intResCnt++;

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					strSubjName = "Status Change for " + strResource1;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));

						intResCnt++;
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));

						intResCnt++;
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_2,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViewMap.navToRegionalMapViewWithoutVisible(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Update the status of RS1 according to the preferences set
			 * second time (after editing). Notifications are received for user
			 * U1.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateNumStatusTypeNew(selenium,
						strResource1, statTypeName1, strSTvalue[0], "201",
						"202");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=span.time");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				String str = strDate[0].substring(1, 3);
				log4j.info(str);

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = str;
				strFndStYear = dts.getTimeOfParticularTimeZone("CST", "yyyy");;

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

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
						+ statTypeName1 + " status from 101 to 201.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statTypeName1 + " status from 101 to 201.";

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

		
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statTypeName1 + " from 101 to 201; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn1 + "";
				
				strMsgBody1 = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource1
					+ " "
					+ "changed "
					+ statTypeName1
					+ " status from 101 "
					+ "to 201.\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn1
					+ "\n\nOther "
					+ strResrctTypName1
					+ "s "
					+ "in the region report the following "
					+ statTypeName1
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

				
				
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

					strSubjName = "Status Change for " + strResource1;

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

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
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
					// check Email, pager notification
					if (intEMailRes == 2 && intPagerRes == 1) {
						intResCnt++;
					}

					// check Email, pager notification
					if (intResCnt == 6) {
						gstrResult = "PASS";
					}

					selenium.selectWindow("");
					selenium.selectFrame("Data");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-84631";
			gstrTO = "Verify that status change preferences for resources"
					+ " of other regions can be edited.";
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

//start//testBQS107475//
	/******************************************************************
	'Description	:Verify that user can set status change preferences   
	                 for status types associated with a sub resource.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:8/20/2013
	'Author			:QSG
	'----------------------------------------------------------------
	'Modified Date				                       Modified By
	'Date					                           Name
	*****************************************************************/

	@Test
	public void testBQS107475() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		ResourceTypes objRT = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objRs = new Resources();
		General objMail = new General();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Views objViews = new Views();
		EventNotification objEventNotification =new EventNotification();
		try {
			gstrTCID = "107475"; // Test Case Id
			gstrTO = " Verify that user can set status change preferences for" +
					" status types associated with a sub resource.";//TO
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

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strNumStatType1 = "AutoNS1_" + strTimeText;
			String strSatuStatType1 = "AutoScS1_" + strTimeText;
			String strMultiStatType1 = "AutoMS1_" + strTimeText;

			String strStatTypDefn = "Auto";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusName1_1 = "S1a" + strTimeText;
			String strStatusName2_1 = "S1b" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strStatusValue[] = new String[2];
			String strStatusValue_1[] = new String[2];

			String strSTvalue[] = new String[3];
			String strSTvalue_1[] = new String[3];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTvalue[] = new String[2];

			String strResource = "AutoRs_" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strAbbrv =  "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[2];

			String strRoleValue = "";
			String strRolesName = "Role" + strTimeText;

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
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";

			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";

			String strAbove = "100";
			String strBelow = "50";
			String strSaturAbove = "100";
			String strSaturBelow = "50";

			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			String strUpdate1="200";
			String strUpdate2="393";
			String strUpdate3=strStatusName2_1;
			
			String strSubjName = "";
			String strCurrDate = "";
			String strCurrDate2 = "";
			
			String strMsgBody2_NST="";
			String strMsgBody2_MST="";
			String strMsgBody2_SST="";
						
			String strMsgBody1_NST="";
			String strMsgBody1_MST="";
			String strMsgBody1_SST="";
			
			String RSandSRS="";
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			
			// sec data
			String strSection1 = "ABSec_" + strTimeText;
			String strSectionValue = "";
			
		/*
		* STEP :
		  Action:Precondition:
		1. Created status types 'NST', 'MST', 'SST' , 'pMST'(private), sNST(shared),rSST (role based)statuses 'S1' & 'S2' are created under 'MST' and 'pMST'.
		2. Resource type 'RT' is created selecting status types 'NST', 'MST' and 'SST.
		3. Sub Resource Type 'SRT' is created selecting status types 'sNST', 'pMST' and 'rSST.
		4. Resource 'RS' is created under 'RT'
		5. Sub-resource 'SRS' is created under parent resource 'RS' selecting 'SRT' sub-resource type.
		6. User 'U1' is created selecting following rights:
			a. View and update right on 'RS' 
			b. Role 'R1' to view and update all status types.
			c. 'Email' and 'Pager' address.
			d. 'Edit Status Change Notification Preferences' right
			e. 'Configure Region View' right.
		7. View 'V1' is created selecting status types 'NST', 'MST', 'SST' and resource type 'RT'
		  Expected Result:No Expected Result
		*/
		//609262
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STARTS~~~~~");
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

			/* 1. Status types 'NST', 'MST', 'SST'are created. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumStatType);
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(seleniumPrecondition,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/****************************************************************************************/
			// NST1,SST1,MST1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType1, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						strNumStatType1, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue_1[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, strMultiStatType1,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMultiStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue_1[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType1, strStatusName1_1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType1, strStatusName2_1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType1, strStatusName1_1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue_1[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType1, strStatusName2_1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue_1[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strSatuStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue_1[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
            
			//Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue_1[0], "true" },
						{ strSTvalue_1[1], "true" },
						{ strSTvalue_1[2], "true" } };
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(seleniumPrecondition, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName);
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
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/****************************************************************************************/

			/*
			 * 2. Resource type 'RT' is created selecting status types 'NST',
			 * 'MST' and 'SST.
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

			for (int intST = 1; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
							strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[0] = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Sub Resource Type 'SRT' is created selecting status types
			 * 'NST1', 'MST1' and 'SST1.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue_1[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 1; intST < strSTvalue_1.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
							strSTvalue_1[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSubResourceType(seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRTvalue[1] = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strSubResrctTypName);
				if (strRTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
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
			 * 5. Sub-resource 'SRS' is created under parent resource 'RS'
			 * selecting 'SRT' sub-resource type.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(seleniumPrecondition,
						strSubResource, strAbbrv, strSubResrctTypName,
						strStandResType, false, strContFName, strContLName, "",
						"");
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
				strResVal = objRs.fetchSubResValueInResList(seleniumPrecondition, strSubResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			/*
			 * 6. User 'U1' is created selecting following rights: a. View and
			 * update right on 'RS' b. Role 'R1' to view and update all status
			 * types. c. 'Email' and 'Pager' address. d. 'Edit Status Change
			 * Notification Preferences' right e. 'Configure Region View' right.
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], true, true,
						false, true);
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTValues[] = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2] };
				String strRSValues[]={strRSValue[0]};
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTValues, false, strRSValues);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strMultiStatType, strNumStatType, strSatuStatType};
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumPrecondition, strStatTypeArr, strSection1, true);
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
				strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
						strSection1);
				if (strSectionValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		log4j.info("------Precondtion for test case " + gstrTCID+ " ends-----");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		log4j.info("-------test case " + gstrTCID+ " execution starts-------");
		/*
		* STEP :
		  Action:Login as User 'U1' navigate to Setup >> Views
		  Expected Result:'Region Views List' screen is displayed.
		*/
		//609263
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
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Click on 'Customize Resource Detail View' button
		  Expected Result:'Edit Resource Detail View Sections' screen is displayed.
			'Save','Cancel' and 'Sub-resource' buttons are displayed on bottom left of the screen.
			'Sort all' button at bottom right of the screen.
		*/
		//609264
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[value='Save']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sub-resources']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sort All']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j
							.info("'Save','Cancel' and 'Sub-resource' buttons are" +
									" displayed on bottom left of the screen. ");
					log4j
							.info("'Sort all' button at bottom right of the screen. ");
				} else {
					strFuncResult = "'Save','Cancel' and 'Sub-resource' buttons are " +
							"NOT displayed on bottom left of the screen. ";
					log4j
							.info("'Save','Cancel' and 'Sub-resource' buttons are " +
									"NOT displayed on bottom left of the screen. ");
					log4j
							.info("'Sort all' button at bottom right of the screen NOT displayed ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		* STEP :
		  Action:Click on 'Sub-resources' button
		  Expected Result:'Edit Sub Resource Detail View Sections' screen is displayed.
          Sub-Resource Type 'SRT' is listed under it.
		*/
		//609265
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditSubResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		* STEP :
		  Action:Select Check box associated with 'SRT' and select status types 'sNST', 'pMST',
		   'rSST and click on 'Save'
		  Expected Result:'Region Views List' screen is displayed.
		*/
		//609266
			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { strSTvalue_1[0], strSTvalue_1[1],strSTvalue_1[2] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								selenium, strSubResrctTypName, strRTvalue[1],
								strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		* STEP :
		  Action:Navigate to 'Preferences>>Status Change Prefs'
		  Expected Result:'My Status Change Preferences' page is displayed. 
          'Add' button is displayed at top right of screen.
		*/
		//609267
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("Preferences.StatusChangePref.Add");
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info(" 'Add' button is displayed at top right of screen.");
				} else {
					strFuncResult = "'Add' button is NOT displayed at top right of screen.";
					log4j.info(" 'Add' button is NOT displayed at top right of screen.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Add' button
		  Expected Result:'Find Resources' page is displayed. 
			Search field is displayed.
			'Search' and 'Cancel' buttons are displayed at bottom left of screen.
		*/
		//609268
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navToFindResourcesPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[value='Search']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Search' and 'Cancel' buttons are displayed.");
				} else {
					strFuncResult = "'Search' and 'Cancel' buttons are NOT displayed.  ";
					log4j.info("'Search' and 'Cancel' buttons are NOT displayed.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide sub-resource ('SRS') name in search field and click on 'Search'
		  Expected Result:Sub-resource 'SRS' is retrieved and displayed under 'Find Resources'screen.
          Resource name:Sub-resource name >  is displayed under 'Resource Name' column.
		*/
		//609269
			try {
				assertEquals("", strFuncResult);
				String strResources[] = { strSubResource };
				String strCategory = "(Any)";
				String strCityZipCd = "";
				String strRegion = "(Any)";
				String strState="(Any)";
				strFuncResult = objPreferences.findResources(selenium,
						strResources, strCategory, strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				 String strResource1 = strResource + ": " + strSubResource;
				strFuncResult = objPreferences.verifyResourceInFindResPage(
						selenium, strSubResource, strResource1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Select check box corresponding to < Resource name:Sub-resource name >
		   and click on 'Notifications' button.
		  Expected Result:'Edit My Status Change Preferences' is displayed.
		Sub header is displayed as 'RT-RS:SRS' is displayed.
		Status types  'sNST', 'pMST' and 'rSST are displayed under 'Uncategorized'.
		*/
		//609270
			try {
				assertEquals("", strFuncResult);
				String strResource1 = strResource + ": " + strSubResource;
				strFuncResult = objPreferences
						.AddResourceToCustomViewNotification(selenium, strResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=#r_name";
				String strText = strSubResrctTypName + "�" + strResource + ": "
						+ strSubResource;
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				if(strFuncResult.equals("")){
					log4j
					.info("Sub header is displayed as 'RT-RS:SRS' is displayed. ");
					
				}else{
					log4j
					.info("Sub header is NOT displayed as 'RT-RS:SRS' is displayed. ");
					strFuncResult="Sub header is NOT displayed as 'RT-RS:SRS' is displayed. ";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { strNumStatType1, strMultiStatType1,
						strSatuStatType1 };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:For 'sNST' and 'rSST', provide value 'X' for 'Above' and 'Y' for 'Below'.
          Select 'Email', 'Pager' and 'Web' for both the values. 
          For 'MST', select 'Email', 'Pager' and 'Web' for both 'S1' and 'S2'
		  Expected Result:'My Status Change Preferences' page is displayed.
         'X' is displayed under 'Email', 'Pager' and 'Web' for both 'Above' and 'Below' values.
		*/
		//609271
			try {
				assertEquals("", strFuncResult);
				String strRSMergName=strResource+": "+strSubResource;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strRSMergName, strRSValue[1], strSTvalue_1[0],
								strNumStatType1, true, true, true);
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
				String strRSMergName=strResource+": "+strSubResource;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strRSMergName, strRSValue[1], strSTvalue_1[0],
								strNumStatType1, true, true, true);
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
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[1],
								strSTvalue_1[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST

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
				String strRSMergName=strResource+": "+strSubResource;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strRSMergName, strRSValue[1], strSTvalue_1[2],
								strSatuStatType1, true, true, true);
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
				String strRSMergName=strResource+": "+strSubResource;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strRSMergName, strRSValue[1], strSTvalue_1[2],
								strSatuStatType1, true, true, true);
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
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strSaturAbove, strSaturBelow, strRSValue[1],
								strSTvalue_1[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST

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
				String strRSMergName=strResource+": "+strSubResource;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strRSMergName, strRSValue[1], strSTvalue_1[1],
								strMultiStatType1, strStatusValue_1[0], true,
								true, true);
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
				String strRSMergName=strResource+": "+strSubResource;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strRSMergName, strRSValue[1], strSTvalue_1[1],
								strMultiStatType1, strStatusValue_1[1], true,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to view 'V1'
		  Expected Result:RS is displayed under RT along with status types NST,MST,SST
          Key icon is displayed next to resource 'RS'
		*/
		//609272
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName_1, strResrctTypName, strResources,
						strRTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = {strMultiStatType, strNumStatType, 
						strSatuStatType };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "+strResrctTypName+"']" +
						"/tbody/tr/td/a[text()='"+strResource+"']/parent::td/" +
						"preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Key icon is displayed next to resource 'RS' ");

				} else {
					strFuncResult = "Key icon is NOT displayed next to resource 'RS' ";
					log4j.info("Key icon is NOT displayed next to resource 'RS' ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on resource 'RS'
		  Expected Result:'View Resource Detail' screen for resource 'RS' is displayed.
			Status types NST,MST,SST are displayed under section 'SEC1'
			'Sub-resources' section is displayed.
			Sub-resource 'SRS' is displayed under 'SRT' along with status types 'sNST', 'pMST' and 'rSST
			Key icon is displayed next to Sub-resource 'SRS'
			'Contacts' section is displayed.
		*/
		//609273
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strMultiStatType, strNumStatType, strSatuStatType};
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strMultiStatType1, strNumStatType1, strSatuStatType1};
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypeArr,
								strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "+strSubResrctTypName+"']" +
						"/tbody/tr/td/a[text()='"+strSubResource+"']/parent::td/" +
						"preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Key icon is displayed next to resource 'SRS' ");

				} else {
					strFuncResult = "Key icon is NOT displayed next to resource 'SRS' ";
					log4j.info("Key icon is NOT displayed next to resource 'SRS' ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='contacts']/tbody/tr/td[text"
						+ "()='" + strUsrFulName_1 + "']";
				strFuncResult = objMail.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("User with associated right on that resource are listed under it.");
				} else {
					log4j.info("User with associated right on that resource are NOT listed under it.");
					strFuncResult = "User with associated right on that resource are NOT listed under it.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Click on key icon associated with sub-resource 'SRS'
		  Expected Result:'Update Status' screen is displayed.
		*/
		//609274
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Update the status for status types 'sNST', 'pMST' and 'rSST  providing following values.
			1. sNST: A value above 'X'
			2. pMST: Select status 'S2'
			3. rSST: A value below 'Y' and click on 'Save'.
		  Expected Result:User 'U1' receives the following notifications:
			1. 'Email', 'Pager' and 'Web' for 'sNST'
			2. 'Email', 'Pager' and 'Web' for 'pMST'
			3. 'Email', 'Pager' and 'Web' for 'rSST'
			Notifications display < 'Parent resource name : sub resource name' >
		*/
		//609275
		
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = {"0", "1", "2", "3", "4", "5", "6", "7",
						"8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue_1[2]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate1, strSTvalue_1[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue_1[1], strSTvalue_1[1], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			String strUpdatedDate="";
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
				strUpdatedDate = selenium.getText("css=#statusTime");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				
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

				RSandSRS=strResource+": "+strSubResource;
				
				String strDesc1[] = {"On " + strCurrDate + " "+RSandSRS+" changed "
						+ strNumStatType1 + " status from 0 to "
						+ strUpdate1 + ".",
						
						"On " + strCurrDate + " "+RSandSRS+ " changed "
						+ strSatuStatType1 + " status from 0 to "
						+ strUpdate2 + ".",
						
						"On " + strCurrDate + " "+RSandSRS+ " changed "
						+ strMultiStatType1 + " status from -- to "
						+ strUpdate3 + "."};

				String strDesc2[] = {"On " + strCurrDate2+ " "+RSandSRS + " changed "
						+ strNumStatType1 + " status from 0 to "
						+ strUpdate1 + ".",
						
						"On " + strCurrDate2 + " "+RSandSRS+ " changed "
						+ strSatuStatType1 + " status from 0 to "
						+ strUpdate2 + ".",
						
						"On " + strCurrDate2 + " "+RSandSRS+ " changed "
						+ strMultiStatType1 + " status from -- to "
						+ strUpdate3 + "."};

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationForManyStatusTypes(selenium, strResource+": "+strSubResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			strMsgBody2_NST = strNumStatType1 + " from 0 to " + strUpdate1
					+ "; " + "Reasons: \nSummary at " + strCurrDate + " \n"
					+ "Region: " + strRegn + "";


			strMsgBody1_NST = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ RSandSRS
					+ " "
					+ "changed "
					+ strNumStatType1
					+ " status from 0 "
					+ "to "
					+ strUpdate1
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			strMsgBody2_SST = strSatuStatType1 + " from 0 to "
					+ strUpdate2 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_SST = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ RSandSRS
					+ " "
					+ "changed "
					+ strSatuStatType1
					+ " status from 0 "
					+ "to "
					+ strUpdate2
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			strMsgBody2_MST = strMultiStatType1 + " from -- to "
					+ strUpdate3 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_MST = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ RSandSRS
					+ " "
					+ "changed "
					+ strMultiStatType1
					+ " status from -- "
					+ "to "
					+ strUpdate3
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(30000);

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "Status Change for " + RSandSRS;
					for (int i = 0; i < 6; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {

							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							if (strMsg.equals(strMsgBody1_NST)) {
								intEMailRes++;

								log4j.info("Mail body is displayed");
							} else if (strMsg.equals(strMsgBody1_SST)) {
								intEMailRes++;
								log4j.info("Mail body is displayed");
							} else if (strMsg.equals(strMsgBody1_MST)) {
								intEMailRes++;
								log4j.info("Mail body is displayed");
							} else {
								log4j
										.info("Email and Pager body is NOT displayed");
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

					}
					strSubjName = "Change for " + strAbbrv;

					for (int i = 0; i < 3; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							if (strMsg.equals(strMsgBody2_NST)) {
								intPagerRes++;
								log4j.info("Email body is displayed");
							} else if(strMsg.equals(strMsgBody2_SST)){
								intPagerRes++;
								log4j.info("Email body is displayed");
							
							}else if(strMsg.equals(strMsgBody2_MST)){
								intPagerRes++;
								log4j.info("Email body is displayed");
							}
							else {
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
			
			try {
				assertEquals("", strFuncResult);		
				if(intPagerRes==3&&intEMailRes==6&&intResCnt==1)
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-107475";
			gstrTO = "Verify that user can set status change preferences for status types associated with a sub resource.";
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

	//end//testBQS107475//
	
	//start//testBQS124975//
	/***************************************************************
	'Description		:Verify that user can delete status change preferences set for status types associated with a sub resource.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:8/20/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS124975() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Views objViews = new Views();
		General objGeneral = new General();
		General objMail = new General();
		EventNotification objEventNotification = new EventNotification();
		try {
			gstrTCID = "124975"; // Test Case Id
			gstrTO = " Verify that user can delete status change preferences set for status types associated with a sub resource.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strStatTypeColor = "Black";
			String strSSTValue = "Saturation Score";

			String statrNumTypeName = "NST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;
			String statsNumTypeName = "sNST" + strTimeText;
			String statrSaturatTypeName1 = "rSST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;

			String strStatusTypeValues[] = new String[6];
			String strStatusName1 = "S1_" + strTimeTxt;
			String strStatusName2 = "S2_" + strTimeTxt;
			String strStatusName3 = "S3_" + strTimeTxt;
			String strStatusValue[] = new String[3];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			strStatusValue[2] = "";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strUpdatedDate = "";
			String strResrcTypName1 = "AutoRt1" + strTimeText;
			String strRTValue[] = new String[2];
			String strState = "Alabama";
			String strCountry = "Autauga County";

			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource1 = "AutoSRs1_" + strTimeText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[4];
			String strSubResrctTypName = "AutoSRt_" + strTimeText;

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strRolesName = "Role" + strTimeText;
			String strRoleValue = "";

			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);

			String strOptions = "";

			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strCategory = "(Any)";
			String strCityZipCd = "";
			String strRegion = "(Any)";

			String strAbove = "100";
			String strBelow = "50";

			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			String strUpdate1 = "200";
			String strUpdate2 = "393";
			String strUpdate3 = strStatusName2;

			String strSubjName = "";
			String strCurrDate = "";
			String strCurrDate2 = "";

			String strMsgBody2_NST = "";
			String strMsgBody2_MST = "";
			String strMsgBody2_SST = "";

			String strMsgBody1_NST = "";
			String strMsgBody1_MST = "";
			String strMsgBody1_SST = "";

			String RSandSRS = "";
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			// Search user criteria

			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			/*
			 * STEP : Action:Precondition: 1. Created status types 'NST', 'MST',
			 * 'SST' , 'pMST'(private), sNST(shared),rSST (role based)statuses
			 * 'S1' & 'S2' are created under 'MST' and 'pMST'. 2. Status types
			 * 'NST', 'MST' and 'SST' are under section SEC1 3. Resource type
			 * 'RT' is created selecting status types 'NST', 'MST' and 'SST. 4.
			 * Sub Resource Type 'SRT' is created selecting status types 'sNST',
			 * 'pMST' and 'rSST. 5. Resource 'RS' is created under 'RT' 6.
			 * Sub-resource 'SRS' is created under parent resource 'RS'
			 * selecting 'SRT' sub-resource type. 7. User 'U1' is created
			 * selecting following rights: a. View and update right on 'RS' b.
			 * Role 'R1' to view and update all status types. c. 'Email' and
			 * 'Pager' address. d. 'Edit Status Change Notification Preferences'
			 * right e. 'Configure Region View' right. 8. View 'V1' is created
			 * selecting status types 'NST', 'MST', 'SST' and resource type 'RT'
			 * Expected Result:No Expected Result
			 */
			// 661438

			log4j.info("~~~~~PRE-CONDITION  " + gstrTCID
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
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNumTypeName);
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statrMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						strStatusName1, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statrMultiTypeName,
						strStatusName1);
				if (strStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue,
						statrSaturatTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName);
				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create Section

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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

			String strArStatType[] = { statrNumTypeName, statrMultiTypeName,
					statrSaturatTypeName };
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumPrecondition, strArStatType, strSection1, true);
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
				strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
						strSection1);
				if (strSectionValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
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

			// sNST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statsNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statsNumTypeName, strVisibilty,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsNumTypeName);
				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// pMST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpMultiTypeName);
				if (strStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// S1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName,
						strStatusName2, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[1] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statpMultiTypeName,
						strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// S2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName,
						strStatusName3, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[2] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statpMultiTypeName,
						strStatusName3);
				if (strStatusValue[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// rSST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue,
						statrSaturatTypeName1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName1);
				if (strStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName1,
						strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRTValue[0] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName1);
				if (strRTValue[0].compareTo("") != 0) {

					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrcTypName1, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SRT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strStatusTypeValues[3] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[5], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSubResourceType(
						seleniumPrecondition, true);
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
				strFuncResult = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SRS1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource1, strAbbrv,
						strSubResrctTypName, strStandResType, false,
						strContFName, strContLName, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource1, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strResVal = "";
			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Users

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strStatusTypeValues[0], "true" },
						{ strStatusTypeValues[2], "true" },
						{ strStatusTypeValues[1], "true" },
						{ strStatusTypeValues[3], "true" },
						{ strStatusTypeValues[4], "true" },
						{ strStatusTypeValues[5], "true" } };

				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Users
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "",
						strPrimaryEMail, strEMail, strPagerValue, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						true, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
				// click on save
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

			// View

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
						.navToCreateViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTValue = { strStatusTypeValues[0],
						strStatusTypeValues[1], strStatusTypeValues[2] };
				String[] strRSvalue = {};
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName_1, strVewDescription, strViewType, true,
						false, strSTValue, false, strRSvalue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRTValue[0], true, true);
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
					.info("~~~~~PRE-CONDITION" + gstrTCID
							+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as User 'U1', Navigate to
			 * 'Preferences>>Status Change Prefs' Expected Result:'My Status
			 * Change Preferences' page is displayed. 'Add' button is displayed
			 * at top right of screen.
			 */
			// 661439

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
				blnLogin = true;
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
				String strElementID = "css=input[value='Save']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sub-resources']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sort All']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Save','Cancel' and 'Sub-resource' buttons are"
							+ " displayed on bottom left of the screen. ");
					log4j
							.info("'Sort all' button at bottom right of the screen. ");
				} else {
					strFuncResult = "'Save','Cancel' and 'Sub-resource' buttons are "
							+ "NOT displayed on bottom left of the screen. ";
					log4j
							.info("'Save','Cancel' and 'Sub-resource' buttons are "
									+ "NOT displayed on bottom left of the screen. ");
					log4j
							.info("'Sort all' button at bottom right of the screen NOT displayed ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditSubResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { strStatusTypeValues[3],
						strStatusTypeValues[4], strStatusTypeValues[5] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								selenium, strSubResrctTypName, strRTValue[1],
								strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Nav to status type list
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
				String strElementID = propElementDetails
						.getProperty("Preferences.StatusChangePref.Add");
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j
							.info(" 'Add' button is displayed at top right of screen.");
				} else {
					strFuncResult = "'Add' button is NOT displayed at top right of screen.";
					log4j
							.info(" 'Add' button is NOT displayed at top right of screen.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Add' button Expected Result:'Find
			 * Resources' page is displayed. <br> <br>Search field is displayed.
			 * <br> <br>'Search' and 'Cancel' buttons are displayed at bottom
			 * left of screen..
			 */
			// 661440

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navToFindResourcesPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[value='Search']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Search' and 'Cancel' buttons are displayed.");
				} else {
					strFuncResult = "'Search' and 'Cancel' buttons are NOT displayed.  ";
					log4j
							.info("'Search' and 'Cancel' buttons are NOT displayed.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide sub-resource ('SRS') name in search field
			 * and click on 'Search' Expected Result:Sub-resource 'SRS' is
			 * retrieved and displayed under 'Find Resources'screen. <br> <br><
			 * Resource name:Sub-resource name > is displayed under 'Resource
			 * Name' column.
			 */
			// 661441

			try {
				assertEquals("", strFuncResult);
				String strResource[] = { strSubResource1 };
				strFuncResult = objPreferences.findResources(selenium,
						strResource, strCategory, strRegion, strCityZipCd,
						"(Any)");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResource = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences.verifyResourceInFindResPage(
						selenium, strSubResource1, strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select check box corresponding to < Resource
			 * name:Sub-resource name > and click on 'Notifications' button.
			 * Expected Result:'Edit My Status Change Preferences' is displayed.
			 * <br> <br>Sub header is displayed as 'RT-RS:SRS' is displayed.
			 * <br> <br>Status types 'sNST', 'pMST' and 'rSST are displayed
			 * under 'Uncategorized'.
			 */
			// 661442

			try {
				assertEquals("", strFuncResult);
				String strResource = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.AddResourceToCustomViewNotification(selenium,
								strResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=#r_name";
				String strText = strSubResrctTypName + "�" + strResource1
						+ ": " + strSubResource1;
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				if (strFuncResult.equals("")) {
					log4j
							.info("Sub header is displayed as 'RT-RS:SRS' is displayed. ");

				} else {
					log4j
							.info("Sub header is NOT displayed as 'RT-RS:SRS' is displayed. ");
					strFuncResult = "Sub header is NOT displayed as 'RT-RS:SRS' is displayed. ";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statsNumTypeName, statpMultiTypeName,
						statrSaturatTypeName1 };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:For 'sNST' and 'rSST', provide value 'X' for
			 * 'Above' and 'Y' for 'Below'. <br>Select 'Email', 'Pager' and
			 * 'Web' for both the values. <br> <br>For 'MST', select 'Email',
			 * 'Pager' and 'Web' for both 'S1' and 'S2' Expected Result:'My
			 * Status Change Preferences' page is displayed. <br>'X' is
			 * displayed under 'Email', 'Pager' and 'Web' for both 'Above' and
			 * 'Below' values.
			 */
			// 661443

			// sNST

			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[3], statsNumTypeName, true,
								true, true);
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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[3], statsNumTypeName, true,
								true, true);
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
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[1],
								strStatusTypeValues[3], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// rSST

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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[5], statrSaturatTypeName1,
								true, true, true);
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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[5], statrSaturatTypeName1,
								true, true, true);
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
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[1],
								strStatusTypeValues[5], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// pMST

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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[4], statpMultiTypeName,
								strStatusValue[1], true, true, true);
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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[4], statpMultiTypeName,
								strStatusValue[2], true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to view 'V1' Expected Result:RS is
			 * displayed under RT along with status types NST,MST,SST <br>
			 * <br>Key icon is displayed next to resource 'RS'
			 */
			// 661444

			// Navigate to view >> 'V1',
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource1 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName_1, strResrcTypName1,
						strResources, strRTValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrMultiTypeName, statrNumTypeName,
						statrSaturatTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "
						+ strResrcTypName1
						+ "']"
						+ "/tbody/tr/td/a[text()='"
						+ strResource1
						+ "']/parent::td/"
						+ "preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Key icon is displayed next to resource 'RS' ");

				} else {
					strFuncResult = "Key icon is NOT displayed next to resource 'RS' ";
					log4j
							.info("Key icon is NOT displayed next to resource 'RS' ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource 'RS' Expected Result:'View
			 * Resource Detail' screen for resource 'RS' is displayed. <br>
			 * <br>Status types NST,MST,SST are displayed under section 'SEC1'
			 * <br> <br>'Sub-resources' section is displayed. <br>Sub-resource
			 * 'SRS' is displayed under 'SRT' along with status types 'sNST',
			 * 'pMST' and 'rSST <br> <br>Key icon is displayed next to
			 * Sub-resource 'SRS' <br> <br>'Contacts' section is displayed.
			 */
			// 661445

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statrNumTypeName, statrMultiTypeName,
						statrSaturatTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypep);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statsNumTypeName,
						statrSaturatTypeName1, statpMultiTypeName };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypep,
								strSubResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "
						+ strSubResrctTypName
						+ "']"
						+ "/tbody/tr/td/a[text()='"
						+ strSubResource1
						+ "']/parent::td/"
						+ "preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Key icon is displayed next to resource 'SRS' ");

				} else {
					strFuncResult = "Key icon is NOT displayed next to resource 'SRS' ";
					log4j
							.info("Key icon is NOT displayed next to resource 'SRS' ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='contacts']/tbody/tr/td[text"
						+ "()='" + strUsrFulName_1 + "']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j
							.info("User with associated right on that resource are listed under it.");
				} else {
					log4j
							.info("User with associated right on that resource are NOT listed under it.");
					strFuncResult = "User with associated right on that resource are NOT listed under it.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on key icon associated with sub-resource
			 * 'SRS' Expected Result:'Update Status' screen is displayed.
			 */
			// 661446

			/*
			 * STEP : Action:Update the status for status types 'sNST', 'pMST'
			 * and 'rSST providing following values. <br> <br>1. sNST: A value
			 * above 'X' <br>2. pMST: Select status 'S2' <br>3. rSST: A value
			 * below 'Y' and click on 'Save'. Expected Result:User 'U1' receives
			 * the following notifications: <br>1. 'Email', 'Pager' and 'Web'
			 * for 'sNST' <br>2. 'Email', 'Pager' and 'Web' for 'pMST' <br>3.
			 * 'Email', 'Pager' and 'Web' for 'rSST' Notifications display <
			 * 'Parent resource name : sub resource name' >
			 */
			// 661447

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strSubResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// pMST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strStatusTypeValues[4], false, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// sNST

			String strUpdatTxtValue = "200";
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.fillAndSavUpdateStatusTST(selenium, strUpdatTxtValue,
								strStatusTypeValues[3], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// rSST

			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strUpdateValue, strStatusTypeValues[5]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
				strUpdatedDate = selenium.getText("css=#statusTime");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//

			try {
				assertEquals("", strFuncResult);

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

				RSandSRS = strResource1 + ": " + strSubResource1;

				String strDesc1[] = {
						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statsNumTypeName + " status from 0 to "
								+ strUpdate1 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statrSaturatTypeName1 + " status from 0 to "
								+ strUpdate2 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statpMultiTypeName + " status from -- to "
								+ strUpdate3 + "." };

				String strDesc2[] = {
						"On " + strCurrDate2 + " " + RSandSRS + " changed "
								+ statsNumTypeName + " status from 0 to "
								+ strUpdate1 + ".",

						"On " + strCurrDate2 + " " + RSandSRS + " changed "
								+ statrSaturatTypeName1 + " status from 0 to "
								+ strUpdate2 + ".",

						"On " + strCurrDate2 + " " + RSandSRS + " changed "
								+ statpMultiTypeName + " status from -- to "
								+ strUpdate3 + "." };

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationForManyStatusTypes(selenium,
								strResource1 + ": " + strSubResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_NST = statsNumTypeName + " from 0 to " + strUpdate1
					+ "; " + "Reasons: \nSummary at " + strCurrDate + " \n"
					+ "Region: " + strRegn + "";

			strMsgBody1_NST = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ RSandSRS
					+ " "
					+ "changed "
					+ statsNumTypeName
					+ " status from 0 "
					+ "to "
					+ strUpdate1
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			strMsgBody2_SST = statrSaturatTypeName1 + " from 0 to "
					+ strUpdate2 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_SST = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ RSandSRS
					+ " "
					+ "changed "
					+ statrSaturatTypeName1
					+ " status from 0 "
					+ "to "
					+ strUpdate2
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			strMsgBody2_MST = statpMultiTypeName + " from -- to " + strUpdate3
					+ "; " + "Reasons: \nSummary at " + strCurrDate + " \n"
					+ "Region: " + strRegn + "";

			strMsgBody1_MST = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ RSandSRS
					+ " "
					+ "changed "
					+ statpMultiTypeName
					+ " status from -- "
					+ "to "
					+ strUpdate3
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(30000);

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "Status Change for " + RSandSRS;
					for (int i = 0; i < 6; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {

							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							if (strMsg.equals(strMsgBody1_NST)) {
								intEMailRes++;

								log4j.info("Mail body is displayed");
							} else if (strMsg.equals(strMsgBody1_SST)) {
								intEMailRes++;
								log4j.info("Mail body is displayed");
							} else if (strMsg.equals(strMsgBody1_MST)) {
								intEMailRes++;
								log4j.info("Mail body is displayed");
							} else {
								log4j
										.info("Email and Pager body is NOT displayed");
								gstrReason = "Email and Pager body is NOT displayed";
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
					strSubjName = "Change for " + strAbbrv;

					for (int i = 0; i < 3; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody2_NST)) {
								intPagerRes++;
								log4j.info("Email body is displayed");
							} else if (strMsg.equals(strMsgBody2_SST)) {
								intPagerRes++;
								log4j.info("Email body is displayed");

							} else if (strMsg.equals(strMsgBody2_MST)) {
								intPagerRes++;
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

			try {
				assertEquals("", strFuncResult);
				if (intPagerRes == 3 && intEMailRes == 6 && intResCnt == 1)
					strFuncResult = "";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to 'Preferences>>Status Change Prefs'
			 * Expected Result:'My Status Change Preferences' page is displayed.
			 * <br> <br>Sub header is displayed as 'RT-RS:SRS'. <br> <br>The
			 * provided values for 'sNST', 'pMST' and 'rSST are displayed
			 * appropriately. <br> <br>'Edit' and 'Delete notifications' links
			 * are present next to sub-resource.
			 */
			// 661448

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST
			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strRSMergName, statsNumTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strRSMergName, statsNumTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strRSMergName, statpMultiTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strRSMergName, statpMultiTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strRSMergName, statrSaturatTypeName1, true,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strRSMergName, statrSaturatTypeName1, true,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td/a[contains(text(),'edit')]";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Edit' link is present next to sub-resource. ");

				} else {
					strFuncResult = "'Edit' link is not present next to sub-resource.";
					log4j
							.info("'Edit' link is not present next to sub-resource.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td/a[contains(text(),'delete notifications')]";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j
							.info("'Delete notifications' link is present next to sub-resource. ");

				} else {
					strFuncResult = "'Delete notifications' link is not present next to sub-resource.";
					log4j
							.info("'Delete notifications' link is not present next to sub-resource.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select 'Delete notifications' corresponding to
			 * sub-resource 'RS:SRS' Expected Result:A pop up message 'Are you
			 * sure you would like to delete all status notification preferences
			 * for the selected Resource?' is displayed.
			 */
			// 661449

			/*
			 * STEP : Action:Click on 'OK' on popup window. Expected
			 * Result:Status notification preferences are deleted and not
			 * displayed in the 'My Status Change Preferences' page.
			 */
			// 661450

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.deleteStaPrefOfPartcularRS(
						selenium, strResVal, strSubResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to view 'V1',Click on resource 'RS'
			 * Expected Result:'View Resource Detail' screen for resource 'RS'
			 * is displayed. <br> <br>Status types NST,MST,SST are displayed
			 * under section 'SEC1' <br> <br>'Sub-resources' section is
			 * displayed. <br>Sub-resource 'SRS' is displayed under 'SRT' along
			 * with status types 'sNST', 'pMST' and 'rSST <br> <br>Key icon is
			 * displayed next to Sub-resource 'SRS'
			 */
			// 661451

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statsNumTypeName,
						statrSaturatTypeName1, statpMultiTypeName };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatType,
								strSubResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "
						+ strSubResrctTypName
						+ "']"
						+ "/tbody/tr/td/a[text()='"
						+ strSubResource1
						+ "']/parent::td/"
						+ "preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Key icon is displayed next to resource 'SRS' ");

				} else {
					strFuncResult = "Key icon is NOT displayed next to resource 'SRS' ";
					log4j
							.info("Key icon is NOT displayed next to resource 'SRS' ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Update the status for status types for both 'Above'
			 * and 'Below' combinations set previously. Expected Result:User
			 * 'U1' does not receive 'Email' ,'pager' and 'Web' notifications
			 * for status types 'sNST', 'pMST' and 'rSST'.
			 */
			// 661452

			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium,
						strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// pMST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[2], strStatusTypeValues[4], false, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// sNST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"102", strStatusTypeValues[3], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// rSST

			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue = { "10", "11", "12", "13", "14", "15",
						"16", "17", "18" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strUpdateValue, strStatusTypeValues[5]);
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
				strUpdatedDate = selenium.getText("css=#statusTime");
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

				RSandSRS = strResource1 + ": " + strSubResource1;

				String strDesc1[] = {
						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statsNumTypeName + " status from 0 to "
								+ strUpdate1 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statrSaturatTypeName1 + " status from 0 to "
								+ strUpdate2 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statpMultiTypeName + " status from -- to "
								+ strUpdate3 + "." };

				String strDesc2[] = {
						"On " + strCurrDate2 + " " + RSandSRS + " changed "
								+ statsNumTypeName + " status from 0 to "
								+ strUpdate1 + ".",

						"On " + strCurrDate2 + " " + RSandSRS + " changed "
								+ statrSaturatTypeName1 + " status from 0 to "
								+ strUpdate2 + ".",

						"On " + strCurrDate2 + " " + RSandSRS + " changed "
								+ statpMultiTypeName + " status from -- to "
								+ strUpdate3 + "." };

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationForManyStatusTypes(selenium,
								strResource1 + ": " + strSubResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Web Notification is NOT displayed", strFuncResult);
				intResCnt++;
				Thread.sleep(30000);

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "Status Change for " + RSandSRS;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						strFuncResult = "";

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
						log4j.info("The mail with subject " + strSubjName
								+ " is present in the inbox");

					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
					log4j.info("The mail with subject " + strSubjName
							+ " is present in the inbox");
				}

				try {
					strSubjName = "Change for " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						strFuncResult = "";

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
						log4j.info("The mail with subject " + strSubjName
								+ " is present in the inbox");

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

			try {
				assertEquals("", strFuncResult);
				if (intPagerRes == 3 && intEMailRes == 6 && intResCnt == 2)
					gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-124975";
			gstrTO = "Verify that user can delete status change preferences set for status types associated with a sub resource.";
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

	// end//testBQS124975//

	// start//testBQS124974//
	/***************************************************************
	 * 'Description :Verify that user can edit the status change preferences set
	 * for status types associated with a sub resource. 'Precondition :
	 * 'Arguments :None 'Returns :None 'Date :8/26/2013 'Author :QSG
	 * '---------------------------------------------------------------
	 * 'Modified Date Modified By 'Date Name
	 ***************************************************************/

	@Test
	public void testBQS124974() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Views objViews = new Views();
		General objGeneral = new General();
		General objMail = new General();
		EventNotification objEventNotification = new EventNotification();

		try {
			gstrTCID = "124974"; // Test Case Id
			gstrTO = " Verify that user can edit the status change preferences set for status types associated with a sub resource.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strStatTypeColor = "Black";
			String strSSTValue = "Saturation Score";

			String statrNumTypeName = "NST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;
			String statsNumTypeName = "sNST" + strTimeText;
			String statrSaturatTypeName1 = "rSST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;

			String strStatusTypeValues[] = new String[6];
			String strStatusName1 = "S1_" + strTimeTxt;
			String strStatusName2 = "S2_" + strTimeTxt;
			String strStatusName3 = "S3_" + strTimeTxt;
			String strStatusValue[] = new String[3];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			strStatusValue[2] = "";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strUpdatedDate = "";
			String strResrcTypName1 = "AutoRt1" + strTimeText;
			String strRTValue[] = new String[2];
			String strState = "Alabama";
			String strCountry = "Autauga County";

			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource1 = "AutoSRs1_" + strTimeText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[4];
			String strSubResrctTypName = "AutoSRt_" + strTimeText;

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strRolesName = "Role" + strTimeText;
			String strRoleValue = "";

			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);

			String strOptions = "";

			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strCategory = "(Any)";
			String strCityZipCd = "";
			String strRegion = "(Any)";

			String strAbove = "100";
			String strBelow = "50";

			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			String strUpdate1 = "200";
			String strUpdate2 = "393";
			String strUpdate3 = strStatusName2;
			String strUpdate4 = strStatusName3;

			String strSubjName = "";
			String strCurrDate = "";
			String strCurrDate2 = "";

			String strMsgBody2_NST = "";
			String strMsgBody2_MST = "";
			String strMsgBody2_SST = "";

			String strMsgBody1_NST = "";
			String strMsgBody1_MST = "";
			String strMsgBody1_SST = "";

			String RSandSRS = "";
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			// Search user criteria

			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			/*
			 * STEP : Action:Precondition:
			 * 
			 * 1. Created status types 'NST', 'MST', 'SST' , 'pMST'(private),
			 * sNST(shared),rSST (role based)statuses 'S1' & 'S2' are created
			 * under 'MST' and 'pMST'.
			 * 
			 * 2. Status types 'NST', 'MST' and 'SST' are under section SEC1
			 * 
			 * 3. Resource type 'RT' is created selecting status types 'NST',
			 * 'MST' and 'SST.
			 * 
			 * 4. Sub Resource Type 'SRT' is created selecting status types
			 * 'sNST', 'pMST' and 'rSST.
			 * 
			 * 5. Resource 'RS' is created under 'RT'
			 * 
			 * 6. Sub-resource 'SRS' is created under parent resource 'RS'
			 * selecting 'SRT' sub-resource type.
			 * 
			 * 7. User 'U1' is created selecting following rights: a. View and
			 * update right on 'RS' b. Role 'R1' to view and update all status
			 * types. c. 'Email' and 'Pager' address. d. 'Edit Status Change
			 * Notification Preferences' right e. 'Configure Region View' right.
			 * 
			 * 8. View 'V1' is created selecting status types 'NST', 'MST',
			 * 'SST' and resource type 'RT' Expected Result:No Expected Result
			 */
			// 661421

			log4j.info("~~~~~PRE-CONDITION  " + gstrTCID
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
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNumTypeName);
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statrMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						strStatusName1, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statrMultiTypeName,
						strStatusName1);
				if (strStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue,
						statrSaturatTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName);
				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create Section

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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

			String strArStatType[] = { statrNumTypeName, statrMultiTypeName,
					statrSaturatTypeName };
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumPrecondition, strArStatType, strSection1, true);
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
				strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
						strSection1);
				if (strSectionValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
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

			// sNST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statsNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statsNumTypeName, strVisibilty,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsNumTypeName);
				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// pMST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpMultiTypeName);
				if (strStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// S1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName,
						strStatusName2, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[1] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statpMultiTypeName,
						strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// S2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName,
						strStatusName3, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[2] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statpMultiTypeName,
						strStatusName3);
				if (strStatusValue[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// rSST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue,
						statrSaturatTypeName1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName1);
				if (strStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName1,
						strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRTValue[0] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName1);
				if (strRTValue[0].compareTo("") != 0) {

					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrcTypName1, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SRT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strStatusTypeValues[3] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[5], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSubResourceType(
						seleniumPrecondition, true);
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
				strFuncResult = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SRS1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource1, strAbbrv,
						strSubResrctTypName, strStandResType, false,
						strContFName, strContLName, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource1, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strResVal = "";
			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Users

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strStatusTypeValues[0], "true" },
						{ strStatusTypeValues[2], "true" },
						{ strStatusTypeValues[1], "true" },
						{ strStatusTypeValues[3], "true" },
						{ strStatusTypeValues[4], "true" },
						{ strStatusTypeValues[5], "true" } };

				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Users
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "",
						strPrimaryEMail, strEMail, strPagerValue, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						true, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
				// click on save
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

			// View

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
						.navToCreateViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTValue = { strStatusTypeValues[0],
						strStatusTypeValues[1], strStatusTypeValues[2] };
				String[] strRSvalue = {};
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName_1, strVewDescription, strViewType, true,
						false, strSTValue, false, strRSvalue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRTValue[0], true, true);
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
					.info("~~~~~PRE-CONDITION" + gstrTCID
							+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as User 'U1', Navigate to
			 * 'Preferences>>Status Change Prefs' Expected Result:'My Status
			 * Change Preferences' page is displayed.
			 * 
			 * 'Add' button is displayed at top right of screen.
			 */
			// 661422

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
				blnLogin = true;
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
				String strElementID = "css=input[value='Save']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sub-resources']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sort All']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Save','Cancel' and 'Sub-resource' buttons are"
							+ " displayed on bottom left of the screen. ");
					log4j
							.info("'Sort all' button at bottom right of the screen. ");
				} else {
					strFuncResult = "'Save','Cancel' and 'Sub-resource' buttons are "
							+ "NOT displayed on bottom left of the screen. ";
					log4j
							.info("'Save','Cancel' and 'Sub-resource' buttons are "
									+ "NOT displayed on bottom left of the screen. ");
					log4j
							.info("'Sort all' button at bottom right of the screen NOT displayed ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditSubResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { strStatusTypeValues[3],
						strStatusTypeValues[4], strStatusTypeValues[5] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								selenium, strSubResrctTypName, strRTValue[1],
								strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Nav to status type list
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
				String strElementID = propElementDetails
						.getProperty("Preferences.StatusChangePref.Add");
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j
							.info(" 'Add' button is displayed at top right of screen.");
				} else {
					strFuncResult = "'Add' button is NOT displayed at top right of screen.";
					log4j
							.info(" 'Add' button is NOT displayed at top right of screen.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Add' button Expected Result:'Find
			 * Resources' page is displayed.
			 * 
			 * Search field is displayed.
			 * 
			 * 'Search' and 'Cancel' buttons are displayed at bottom left of
			 * screen..
			 */
			// 661423

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navToFindResourcesPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[value='Search']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Search' and 'Cancel' buttons are displayed.");
				} else {
					strFuncResult = "'Search' and 'Cancel' buttons are NOT displayed.  ";
					log4j
							.info("'Search' and 'Cancel' buttons are NOT displayed.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide parent resource sub-resource ('RS SRS')
			 * name in search field and click on 'Search' Expected
			 * Result:Sub-resource 'SRS' is retrieved and displayed under 'Find
			 * Resources'screen.
			 * 
			 * Resource name:Sub-resource name > is displayed under 'Resource
			 * Name' column.
			 */
			// 661424

			try {
				assertEquals("", strFuncResult);
				String strResource[] = { strSubResource1 };
				strFuncResult = objPreferences.findResources(selenium,
						strResource, strCategory, strRegion, strCityZipCd,
						"(Any)");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResource = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences.verifyResourceInFindResPage(
						selenium, strSubResource1, strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select check box corresponding to < Resource
			 * name:Sub-resource name > and click on 'Notifications' button.
			 * Expected Result:'Edit My Status Change Preferences' is displayed.
			 * 
			 * Sub header is displayed as 'RT-RS:SRS' is displayed.
			 * 
			 * Status types 'sNST', 'pMST' and 'rSST are displayed under
			 * 'Uncategorized'.
			 */
			// 661425

			try {
				assertEquals("", strFuncResult);
				String strResource = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.AddResourceToCustomViewNotification(selenium,
								strResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=#r_name";
				String strText = strSubResrctTypName + "�" + strResource1
						+ ": " + strSubResource1;
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				if (strFuncResult.equals("")) {
					log4j
							.info("Sub header is displayed as 'RT-RS:SRS' is displayed. ");

				} else {
					log4j
							.info("Sub header is NOT displayed as 'RT-RS:SRS' is displayed. ");
					strFuncResult = "Sub header is NOT displayed as 'RT-RS:SRS' is displayed. ";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statsNumTypeName, statpMultiTypeName,
						statrSaturatTypeName1 };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:For 'sNST' and 'rSST', provide value 'X' for
			 * 'Above' and 'Y' for 'Below'. Select 'Email', 'Pager' and 'Web'
			 * for both the values.
			 * 
			 * For 'MST', select 'Email', 'Pager' and 'Web' for both 'S1' and
			 * 'S2' Expected Result:'My Status Change Preferences' page is
			 * displayed. 'X' is displayed under 'Email', 'Pager' and 'Web' for
			 * both 'Above' and 'Below' values.
			 */
			// 661426

			// sNST

			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[3], statsNumTypeName, true,
								true, true);
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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[3], statsNumTypeName, true,
								true, true);
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
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[1],
								strStatusTypeValues[3], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// rSST

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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[5], statrSaturatTypeName1,
								true, true, true);
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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[5], statrSaturatTypeName1,
								true, true, true);
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
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[1],
								strStatusTypeValues[5], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// pMST

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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[4], statpMultiTypeName,
								strStatusValue[1], true, true, true);
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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[4], statpMultiTypeName,
								strStatusValue[2], true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to view 'V1' Expected Result:RS is
			 * displayed under RT along with status types NST,MST,SST <br>
			 * <br>Key icon is displayed next to resource 'RS'
			 */
			// 661427

			// Navigate to view >> 'V1',
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource1 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName_1, strResrcTypName1,
						strResources, strRTValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrMultiTypeName, statrNumTypeName,
						statrSaturatTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "
						+ strResrcTypName1
						+ "']"
						+ "/tbody/tr/td/a[text()='"
						+ strResource1
						+ "']/parent::td/"
						+ "preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Key icon is displayed next to resource 'RS' ");

				} else {
					strFuncResult = "Key icon is NOT displayed next to resource 'RS' ";
					log4j
							.info("Key icon is NOT displayed next to resource 'RS' ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource 'RS' Expected Result:'View
			 * Resource Detail' screen for resource 'RS' is displayed.
			 * 
			 * Status types NST,MST,SST are displayed under section 'SEC1'
			 * 
			 * 'Sub-resources' section is displayed. Sub-resource 'SRS' is
			 * displayed under 'SRT' along with status types 'sNST', 'pMST' and
			 * 'rSST
			 * 
			 * Key icon is displayed next to Sub-resource 'SRS'
			 * 
			 * 'Contacts' section is displayed.
			 */
			// 661428

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statrNumTypeName, statrMultiTypeName,
						statrSaturatTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypep);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statsNumTypeName,
						statrSaturatTypeName1, statpMultiTypeName };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypep,
								strSubResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "
						+ strSubResrctTypName
						+ "']"
						+ "/tbody/tr/td/a[text()='"
						+ strSubResource1
						+ "']/parent::td/"
						+ "preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Key icon is displayed next to resource 'SRS' ");

				} else {
					strFuncResult = "Key icon is NOT displayed next to resource 'SRS' ";
					log4j
							.info("Key icon is NOT displayed next to resource 'SRS' ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='contacts']/tbody/tr/td[text"
						+ "()='" + strUsrFulName_1 + "']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j
							.info("User with associated right on that resource are listed under it.");
				} else {
					log4j
							.info("User with associated right on that resource are NOT listed under it.");
					strFuncResult = "User with associated right on that resource are NOT listed under it.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on key icon associated with sub-resource
			 * 'SRS' Expected Result:'Update Status' screen is displayed.
			 */
			// 661429

			/*
			 * STEP : Action:Update the status for status types 'sNST', 'pMST'
			 * and 'rSST providing following values. <br> <br>1. sNST: A value
			 * above 'X' <br>2. pMST: Select status 'S2' <br>3. rSST: A value
			 * below 'Y' and click on 'Save'. Expected Result:User 'U1' receives
			 * the following notifications: <br>1. 'Email', 'Pager' and 'Web'
			 * for 'sNST' <br>2. 'Email', 'Pager' and 'Web' for 'pMST' <br>3.
			 * 'Email', 'Pager' and 'Web' for 'rSST' Notifications display <
			 * 'Parent resource name : sub resource name' >
			 */
			// 661430

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strSubResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// pMST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strStatusTypeValues[4], false, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// sNST

			String strUpdatTxtValue = "200";
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.fillAndSavUpdateStatusTST(selenium, strUpdatTxtValue,
								strStatusTypeValues[3], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// rSST

			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strUpdateValue, strStatusTypeValues[5]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
				strUpdatedDate = selenium.getText("css=#statusTime");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//

			try {
				assertEquals("", strFuncResult);

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

				RSandSRS = strResource1 + ": " + strSubResource1;

				String strDesc1[] = {
						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statsNumTypeName + " status from 0 to "
								+ strUpdate1 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statrSaturatTypeName1 + " status from 0 to "
								+ strUpdate2 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statpMultiTypeName + " status from -- to "
								+ strUpdate3 + "." };

				String strDesc2[] = {
						"On " + strCurrDate2 + " " + RSandSRS + " changed "
								+ statsNumTypeName + " status from 0 to "
								+ strUpdate1 + ".",

						"On " + strCurrDate2 + " " + RSandSRS + " changed "
								+ statrSaturatTypeName1 + " status from 0 to "
								+ strUpdate2 + ".",

						"On " + strCurrDate2 + " " + RSandSRS + " changed "
								+ statpMultiTypeName + " status from -- to "
								+ strUpdate3 + "." };

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationForManyStatusTypes(selenium,
								strResource1 + ": " + strSubResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_NST = statsNumTypeName + " from 0 to " + strUpdate1
					+ "; " + "Reasons: \nSummary at " + strCurrDate + " \n"
					+ "Region: " + strRegn + "";

			strMsgBody1_NST = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ RSandSRS
					+ " "
					+ "changed "
					+ statsNumTypeName
					+ " status from 0 "
					+ "to "
					+ strUpdate1
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			strMsgBody2_SST = statrSaturatTypeName1 + " from 0 to "
					+ strUpdate2 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_SST = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ RSandSRS
					+ " "
					+ "changed "
					+ statrSaturatTypeName1
					+ " status from 0 "
					+ "to "
					+ strUpdate2
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			strMsgBody2_MST = statpMultiTypeName + " from -- to " + strUpdate3
					+ "; " + "Reasons: \nSummary at " + strCurrDate + " \n"
					+ "Region: " + strRegn + "";

			strMsgBody1_MST = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ RSandSRS
					+ " "
					+ "changed "
					+ statpMultiTypeName
					+ " status from -- "
					+ "to "
					+ strUpdate3
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(30000);

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "Status Change for " + RSandSRS;
					for (int i = 0; i < 6; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {

							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							if (strMsg.equals(strMsgBody1_NST)) {
								intEMailRes++;

								log4j.info("Mail body is displayed");
							} else if (strMsg.equals(strMsgBody1_SST)) {
								intEMailRes++;
								log4j.info("Mail body is displayed");
							} else if (strMsg.equals(strMsgBody1_MST)) {
								intEMailRes++;
								log4j.info("Mail body is displayed");
							} else {
								log4j
										.info("Email and Pager body is NOT displayed");
								gstrReason = "Email and Pager body is NOT displayed";
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
					strSubjName = "Change for " + strAbbrv;

					for (int i = 0; i < 3; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody2_NST)) {
								intPagerRes++;
								log4j.info("Email body is displayed");
							} else if (strMsg.equals(strMsgBody2_SST)) {
								intPagerRes++;
								log4j.info("Email body is displayed");

							} else if (strMsg.equals(strMsgBody2_MST)) {
								intPagerRes++;
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

			try {
				assertEquals("", strFuncResult);
				if (intPagerRes == 3 && intEMailRes == 6 && intResCnt == 1)
					strFuncResult = "";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to 'Preferences>>Status Change Prefs'
			 * Expected Result:'My Status Change Preferences' page is displayed.
			 * <br> <br>Sub header is displayed as 'RT-RS:SRS'. <br> <br>The
			 * provided values for 'sNST', 'pMST' and 'rSST are displayed
			 * appropriately. <br> <br>'Edit' and 'Delete notifications' links
			 * are present next to sub-resource.
			 */
			// 661431

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST
			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strRSMergName, statsNumTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strRSMergName, statsNumTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strRSMergName, statpMultiTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strRSMergName, statpMultiTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strRSMergName, statrSaturatTypeName1, true,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strRSMergName, statrSaturatTypeName1, true,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td/a[contains(text(),'edit')]";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Edit' link is present next to sub-resource. ");

				} else {
					strFuncResult = "'Edit' link is not present next to sub-resource.";
					log4j
							.info("'Edit' link is not present next to sub-resource.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td/a[contains(text(),'delete notifications')]";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j
							.info("'Delete notifications' link is present next to sub-resource. ");

				} else {
					strFuncResult = "'Delete notifications' link is not present next to sub-resource.";
					log4j
							.info("'Delete notifications' link is not present next to sub-resource.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select 'Edit' corresponding to sub-resource
			 * 'RS:SRS' Expected Result:'Edit My Status Change Preferences' page
			 * is displayed.
			 */
			// 661432

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:For 'sNST' and 'rSST', select to receive only
			 * 'Email' and 'Web' for value above 'X' and below 'Y' <br> <br>For
			 * 'pMST', select to receive 'Pager' and 'Web' for both 'S1' and
			 * 'S2' <br> <br>Click on 'save' Expected Result:'My Status Change
			 * Preferences' page is displayed with updated values.
			 */
			// 661433

			// sNST

			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageNew(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[3], statsNumTypeName, true,
								false, true);
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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelowNew(
								selenium, strRSMergName, strRSValue[1],
								strStatusTypeValues[3], statsNumTypeName, true,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// rSST

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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageNew(selenium,
								strRSMergName, strRSValue[1],
								strStatusTypeValues[5], statrSaturatTypeName1,
								true, false, true);
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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelowNew(
								selenium, strRSMergName, strRSValue[1],
								strStatusTypeValues[5], statrSaturatTypeName1,
								true, false, true);
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

			// pMST

			try {
				assertEquals("", strFuncResult);
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMSTNew(
								selenium, strRSMergName, strRSValue[1],
								strStatusTypeValues[4], statpMultiTypeName,
								strStatusValue[1], false, true, true);
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
				String strRSMergName = strResource1 + ": " + strSubResource1;
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMSTNew(
								selenium, strRSMergName, strRSValue[1],
								strStatusTypeValues[4], statpMultiTypeName,
								strStatusValue[2], false, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to view 'V1',Click on resource 'RS'
			 * Expected Result:'View Resource Detail' screen for resource 'RS'
			 * is displayed. <br> <br>Status types NST,MST,SST are displayed
			 * under section 'SEC1' <br> <br>'Sub-resources' section is
			 * displayed. <br>Sub-resource 'SRS' is displayed under 'SRT' along
			 * with status types 'sNST', 'pMST' and 'rSST <br> <br>Key icon is
			 * displayed next to Sub-resource 'SRS'
			 */
			// 661434

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statsNumTypeName,
						statrSaturatTypeName1, statpMultiTypeName };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatType,
								strSubResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "
						+ strSubResrctTypName
						+ "']"
						+ "/tbody/tr/td/a[text()='"
						+ strSubResource1
						+ "']/parent::td/"
						+ "preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Key icon is displayed next to resource 'SRS' ");

				} else {
					strFuncResult = "Key icon is NOT displayed next to resource 'SRS' ";
					log4j
							.info("Key icon is NOT displayed next to resource 'SRS' ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Update the status for status types for both 'Above'
			 * and 'Below' combinations. Expected Result:User 'U1' receives
			 * 'Email' and 'Web' notifications for status types 'sNST' and
			 * 'rSST'. <br> <br>'Pager' and 'Web' notifications for status type
			 * 'pMST'
			 */
			// 661435

			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium,
						strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// pMST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[2], strStatusTypeValues[4], false, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// sNST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"102", strStatusTypeValues[3], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// rSST

			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue = { "10", "11", "12", "13", "14", "15",
						"16", "17", "18" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strUpdateValue, strStatusTypeValues[5]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
				strUpdatedDate = selenium.getText("css=#statusTime");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

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

				RSandSRS = strResource1 + ": " + strSubResource1;

				String strDesc1[] = {
						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statsNumTypeName + " status from 200 to 102.",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statrSaturatTypeName1
								+ " status from 393 to 454.",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statpMultiTypeName + " status from "
								+ strUpdate3 + " to " + strUpdate4 + "." };

				String strDesc2[] = {
						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statsNumTypeName + " status from 200 to 102.",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statrSaturatTypeName1
								+ " status from 393 to 454.",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statpMultiTypeName + " status from "
								+ strUpdate3 + " to " + strUpdate4 + "." };

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationForManyStatusTypes(selenium,
								strResource1 + ": " + strSubResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strUpdate5 = "102";
			strUpdate1 = "200";
			strUpdate2 = "393";
			String strUpdate6 = "454";

			strMsgBody2_NST = statsNumTypeName + " from " + strUpdate1 + " to "
					+ strUpdate5 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_NST = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ RSandSRS
					+ " "
					+ "changed "
					+ statsNumTypeName
					+ " status from "
					+ strUpdate1
					+ " "
					+ "to "
					+ strUpdate5
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			strMsgBody2_SST = statrSaturatTypeName1 + " from " + strUpdate2
					+ " to " + strUpdate6 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			strMsgBody1_SST = "Status Update for "
					+ strUsrFulName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ RSandSRS
					+ " "
					+ "changed "
					+ statrSaturatTypeName1
					+ " status from "
					+ strUpdate2
					+ " "
					+ "to "
					+ strUpdate6
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			strMsgBody2_MST = statpMultiTypeName + " from " + strUpdate3
					+ " to " + strUpdate4 + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " \n" + "Region: " + strRegn + "";

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(30000);

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "Status Change for " + RSandSRS;
					for (int i = 0; i < 4; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {

							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							if (strMsg.equals(strMsgBody1_NST)) {
								intEMailRes++;

								log4j.info("Mail body is displayed");
							} else if (strMsg.equals(strMsgBody1_SST)) {
								intEMailRes++;
								log4j.info("Mail body is displayed");
							} else if (strMsg.equals(strMsgBody1_MST)) {
								intEMailRes++;
								log4j.info("Mail body is displayed");
							} else {
								log4j
										.info("Email and Pager body is NOT displayed");
								gstrReason = "Email and Pager body is NOT displayed";
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

					strSubjName = "Change for " + strAbbrv;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						if (strMsg.equals(strMsgBody2_MST)) {
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

			try {
				assertEquals("", strFuncResult);
				if (intPagerRes == 3 && intEMailRes == 10 && intResCnt == 2)
					gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-124974";
			gstrTO = "Verify that user can edit the status change preferences set for status types associated with a sub resource.";
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

	// end//testBQS124974//
	// start//testBQS120502//
	/***************************************************************
	 * 'Description :Verify that expired status notification is received for a
	 * sub resource. 'Precondition : 'Arguments :None 'Returns :None 'Date
	 * :8/27/2013 'Author :QSG
	 * '---------------------------------------------------------------
	 * 'Modified Date Modified By 'Date Name
	 ***************************************************************/

	@SuppressWarnings("unused")
	@Test
	public void testBQS120502() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRoles = new Roles();

		Views objViews = new Views();

		General objMail = new General();

		try {
			gstrTCID = "120502"; // Test Case Id
			gstrTO = " Verify that expired status notification is received for a sub resource.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strNDSTValue = "NEDOCS Calculation";
			String strStatType1 = "NST_1" + strTimeText;

			String statrNumTypeName = "AutoNST" + strTimeText;
			String statrTextTypeName = "AutoTST" + strTimeText;
			String statrMultiTypeName = "AutoMST" + strTimeText;
			String statrSaturatTypeName = "AutoSST" + strTimeText;
			String statrNedocTypeName = "AutoNEDOC" + strTimeText;
			String str_roleStatusTypeValues[] = new String[6];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String str_roleStatusValue[] = new String[2];
			String str_roleStatusName2 = "rSb" + strTimeTxt;

			String strExpHr = "00";
			String strExpMn = "05";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusValue[] = new String[2];
			String strResrctTypName = "AutoRt_1" + strTimeText;

			String strResource1 = "AutoRs_1" + strTimeText;

			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[2];

			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTValue[] = new String[2];
			String strSubResource1 = "AutoSRs1_" + strTimeText;

			String strRolesName = "Role" + strTimeText;
			String strRoleValue = "";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);

			String strOptions = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strMsgBody1_NST = "";
			String strMsgBody1_SST = "";
			String strMsgBody1_MST = "";
			String strMsgBody1_TST = "";
			String strMsgBody1_NEDOC = "";
			String strUpdatedDate = "";

			String strMsgBody2_NST = "";
			String strMsgBody2_MST = "";
			String strMsgBody2_SST = "";
			String strMsgBody2_TST = "";
			String strMsgBody2_NEDOC = "";

			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			String strSubjName = "";
			String strCurrDate = "";
			String strCurrDate2 = "";
			String RSandSRS = "";

			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			/*
			 * STEP : Action:Create all 5 status types (number, multi, text,
			 * saturation score and nedoc) selecting 5 mins expiration time and
			 * associate them to a sub-resource type and create a sub-resource
			 * under it. <br>Update the status types associated with a sub
			 * resource and check user receives 'Expire status notification'
			 * after all the status type expires. Expected Result:No Expected
			 * Result
			 */
			// 661457

			log4j.info("~~~~~PRE-CONDITION  " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

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

			// NST
			// Role based status type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire"));
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Hours"),
						"label=" + strExpHr);
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Mins"),
						"label=" + strExpMn);

				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNumTypeName);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statrTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire"));
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Hours"),
						"label=" + strExpHr);
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Mins"),
						"label=" + strExpMn);

				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrTextTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue,
						statrSaturatTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire"));
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Hours"),
						"label=" + strExpHr);
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Mins"),
						"label=" + strExpMn);

				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrSaturatTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName);
				if (str_roleStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statrMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, statrMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (str_roleStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.createSTWithinMultiTypeSTExpTime(seleniumPrecondition,
								statrMultiTypeName, strStatusName1,
								strMSTValue, strStatTypeColor, strExpHr,
								strExpMn, "", "", true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statrMultiTypeName,
						strStatusName1);
				if (strStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NEDOC
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire"));
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Hours"),
						"label=" + strExpHr);
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Mins"),
						"label=" + strExpMn);

				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNedocTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNedocTypeName);
				if (str_roleStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								strStatType1);
				if (str_roleStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT

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
								+ str_roleStatusTypeValues[5] + "']");
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
				strRTValue[0] = objRT.fetchRTValueInRTList(
						seleniumPrecondition, strResrctTypName);
				if (strRTValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SRT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSubResourceType(
						seleniumPrecondition, true);
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
				strFuncResult = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Resource

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
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Sub Resource

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource1, strAbbrv,
						strSubResrctTypName, strStandResType, false,
						strContFName, strContLName, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource1, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strResVal = "";
			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Roles

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = {
						{ str_roleStatusTypeValues[0], "true" },
						{ str_roleStatusTypeValues[2], "true" },
						{ str_roleStatusTypeValues[1], "true" },
						{ str_roleStatusTypeValues[3], "true" },
						{ str_roleStatusTypeValues[4], "true" },
						{ str_roleStatusTypeValues[5], "true" } };

				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Users
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "",
						strPrimaryEMail, strEMail, strPagerValue, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						true, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
				// click on save
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus");
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
				strFuncResult = objCreateUsers
						.provideExpirdStatusNotificatcion(seleniumPrecondition);

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

			// View

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
						.navToCreateViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTValue = { str_roleStatusTypeValues[5] };
				String[] strRSvalue = { strRSValue[0] };
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName_1, strVewDescription, strViewType, true,
						false, strSTValue, false, strRSvalue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRTValue[0], true, true);
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
				String strElementID = "css=input[value='Save']";
				strFuncResult = objMail.checkForAnElements(
						seleniumPrecondition, strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objMail.checkForAnElements(
						seleniumPrecondition, strElementID);

				strElementID = "css=input[value='Sub-resources']";
				strFuncResult = objMail.checkForAnElements(
						seleniumPrecondition, strElementID);

				strElementID = "css=input[value='Sort All']";
				strFuncResult = objMail.checkForAnElements(
						seleniumPrecondition, strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Save','Cancel' and 'Sub-resource' buttons are"
							+ " displayed on bottom left of the screen. ");
					log4j
							.info("'Sort all' button at bottom right of the screen. ");
				} else {
					strFuncResult = "'Save','Cancel' and 'Sub-resource' buttons are "
							+ "NOT displayed on bottom left of the screen. ";
					log4j
							.info("'Save','Cancel' and 'Sub-resource' buttons are "
									+ "NOT displayed on bottom left of the screen. ");
					log4j
							.info("'Sort all' button at bottom right of the screen NOT displayed ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditSubResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],
						str_roleStatusTypeValues[4] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								seleniumPrecondition, strSubResrctTypName,
								strRTValue[1], strstValues, true);
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * try { assertEquals("", strFuncResult); strFuncResult =
			 * objViews.navToUserView(selenium, strViewName_1); } catch
			 * (AssertionError Ae) { gstrReason = strFuncResult; }
			 * 
			 * try { assertEquals("", strFuncResult); strFuncResult = objViews
			 * .navToViewResourceDetailPageWitoutWaitForPgeLoad( selenium,
			 * strResource1); } catch (AssertionError Ae) { gstrReason =
			 * strFuncResult; }
			 * 
			 * try { assertEquals("", strFuncResult); strFuncResult =
			 * objViews.navToUpdateStatusByKey(selenium, strSubResource1); }
			 * catch (AssertionError Ae) { gstrReason = strFuncResult; }
			 */

			// ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"100", str_roleStatusTypeValues[5], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", str_roleStatusTypeValues[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", str_roleStatusTypeValues[1], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], str_roleStatusTypeValues[3], false,
						"", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strUpdateValue, str_roleStatusTypeValues[2]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NEDOC

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(selenium,
						strScUpdValue1, str_roleStatusTypeValues[4], true);
				strUpdatedDate = selenium.getText("css=#statusTime");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

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

			String strCurDate = "";
			strCurDate = dts.converDateFormat(strFndStYear + strFndStMnth
					+ strFndStDay, "yyyyMMMdd", "MM/dd/yyyy");

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "MM/dd/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 5, "HH:mm");
				log4j.info(strFndStMinu2);

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				RSandSRS = strResource1 + ": " + strSubResource1;

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 5,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strUpdatedDate = strFndStMinu2;

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody2_NST = "EMResource expired status: " + RSandSRS + ". "
					+ statrNumTypeName + " status expired " + strCurDate + " "
					+ strUpdatedDate + ".";

			strMsgBody1_NST = "For "
					+ strUsrFulName_1
					+ "\nRegion: "
					+ strRegn
					+ "\n\nThe "
					+ statrNumTypeName
					+ " status for "
					+ RSandSRS
					+ " expired "
					+ strCurDate
					+ " "
					+ strUpdatedDate
					+ ".\n\nClick the link below to go to the EMResource login screen:"
					+

					"\n\n        "
					+ propEnvDetails.getProperty("MailURL")
					+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
					+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

			strMsgBody2_SST = "EMResource expired status: " + RSandSRS + ". "
					+ statrSaturatTypeName + " status expired " + strCurDate
					+ " " + strUpdatedDate + ".";

			strMsgBody1_SST = "For "
					+ strUsrFulName_1
					+ "\nRegion: "
					+ strRegn
					+ "\n\nThe "
					+ statrSaturatTypeName
					+ " status for "
					+ RSandSRS
					+ " expired "
					+ strCurDate
					+ " "
					+ strUpdatedDate
					+ ".\n\nClick the link below to go to the EMResource login screen:"
					+

					"\n\n        "
					+ propEnvDetails.getProperty("MailURL")
					+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
					+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

			strMsgBody2_MST = "EMResource expired status: " + RSandSRS + ". "
					+ statrMultiTypeName + " status expired " + strCurDate
					+ " " + strUpdatedDate + ".";

			strMsgBody1_MST = "For "
					+ strUsrFulName_1
					+ "\nRegion: "
					+ strRegn
					+ "\n\nThe "
					+ statrMultiTypeName
					+ " status for "
					+ RSandSRS
					+ " expired "
					+ strCurDate
					+ " "
					+ strUpdatedDate
					+ ".\n\nClick the link below to go to the EMResource login screen:"
					+

					"\n\n        "
					+ propEnvDetails.getProperty("MailURL")
					+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
					+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

			strMsgBody2_TST = "EMResource expired status: " + RSandSRS + ". "
					+ statrTextTypeName + " status expired " + strCurDate + " "
					+ strUpdatedDate + ".";

			strMsgBody1_TST = "For "
					+ strUsrFulName_1
					+ "\nRegion: "
					+ strRegn
					+ "\n\nThe "
					+ statrTextTypeName
					+ " status for "
					+ RSandSRS
					+ " expired "
					+ strCurDate
					+ " "
					+ strUpdatedDate
					+ ".\n\nClick the link below to go to the EMResource login screen:"
					+

					"\n\n        "
					+ propEnvDetails.getProperty("MailURL")
					+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
					+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

			strMsgBody2_NEDOC = "EMResource expired status: " + RSandSRS + ". "
					+ statrNedocTypeName + " status expired " + strCurDate
					+ " " + strUpdatedDate + ".";

			strMsgBody1_NEDOC = "For "
					+ strUsrFulName_1
					+ "\nRegion: "
					+ strRegn
					+ "\n\nThe "
					+ statrNedocTypeName
					+ " status for "
					+ RSandSRS
					+ " expired "
					+ strCurDate
					+ " "
					+ strUpdatedDate
					+ ".\n\nClick the link below to go to the EMResource login screen:"
					+

					"\n\n        "
					+ propEnvDetails.getProperty("MailURL")
					+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
					+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

			try {
				assertEquals("", strFuncResult);
				intResCnt++;

				Thread.sleep(360000);
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				strSubjName = "EMResource Expired Status: " + strAbbrv;

				for (int i = 0; i < 5; i++) {
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						if (strMsg.equals(strMsgBody2_NST)) {
							intPagerRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2_SST)) {
							intPagerRes++;
							log4j.info("Email body is displayed");

						} else if (strMsg.equals(strMsgBody2_MST)) {
							intPagerRes++;
							log4j.info("Email body is displayed");

						} else if (strMsg.equals(strMsgBody2_TST)) {
							intPagerRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2_NEDOC)) {
							intPagerRes++;
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

				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status Notification: "
							+ RSandSRS;
					for (int i = 0; i < 5; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {

							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							if (strMsg.equals(strMsgBody1_NST)) {
								intEMailRes++;

								log4j.info("Mail body is displayed");
							} else if (strMsg.equals(strMsgBody1_SST)) {
								intEMailRes++;
								log4j.info("Mail body is displayed");
							} else if (strMsg.equals(strMsgBody1_MST)) {
								intEMailRes++;
								log4j.info("Mail body is displayed");
							} else if (strMsg.equals(strMsgBody1_TST)) {
								intEMailRes++;
								log4j.info("Mail body is displayed");
							} else if (strMsg.equals(strMsgBody1_NEDOC)) {
								intEMailRes++;
								log4j.info("Mail body is displayed");
							} else {
								log4j
										.info("Email and Pager body is NOT displayed");
								gstrReason = "Email and Pager body is NOT displayed";
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

			try {
				assertEquals("", strFuncResult);
				if (intPagerRes == 5 && intEMailRes == 5 && intResCnt == 1)
					strFuncResult = "";
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
			gstrTCID = "BQS-120502";
			gstrTO = "Verify that expired status notification is received for a sub resource.";
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

	// end//testBQS120502//
}
