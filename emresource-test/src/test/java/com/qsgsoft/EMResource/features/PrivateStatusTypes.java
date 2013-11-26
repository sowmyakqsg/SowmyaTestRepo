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
' Description         :This class includes test cases related
' Requirement Group   :Setting up Status Types 		   
' Requirement         :Private Status types 
' Date		          :29-August-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                                  Modified By
' <Date>                           	               <Name>
'*******************************************************************/

public class PrivateStatusTypes {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.PrivateStatusTypes");
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
	String gstrTimeOut="";
	
	Selenium selenium,seleniumPrecondition,seleniumFirefox;
	

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
		

		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		selenium.start();
		selenium.windowMaximize();
		
		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");
		
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		seleniumPrecondition.setTimeout("");
		
		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try{
			selenium.close();
		}catch(Exception e){
			
		}
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
		}
		try{
			seleniumFirefox.close();
		}catch(Exception e){
			
		}
		
		selenium.stop();
		seleniumPrecondition.stop();
		seleniumFirefox.stop();

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
	

	/********************************************************************************
	'Description	:Create private status type ST, associate ST with resource RS at resource TYPE level and verify that users can view the status type only if they have any of the affiliated resource rights on RS from following screens:
					a. Region view
					b. Map
					c. Custom view
					d. View Resource Detail
					e. Event detail
	'Precondition	:1. Resource type RT is created selecting a role based status type ST
					2. Resource RS is created providing address under resource type RT
					3. View V1 is created selecting ST and RT
					4. Event template ET is created selecting ST and RT
					5. Event E1 is created selecting RS
					
					6. Users U1, U2, U3, U4 are created and has following rights:
					a) "View Custom View"
					b) "View Resource" right on RS
					c) Role to view the status type ST
					7. All the users have added resource RS and status types ST to their respective custom views 			
	'Arguments		:None
	'Returns		:None
	'Date	 		:27-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/

	@Test
	public void testBQS48884() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();
		ViewMap objViewMap = new ViewMap();
		Roles objRole = new Roles();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "BQS-48884 ";
			gstrTO = "Create private status type ST, associate ST with resource"
					+ " RS at resource TYPE level and verify that users can view "
					+ "the status type only if they have any of the affiliated"
					+ " resource rights on RS from following screens:a. Region view"
					+ "b. Map c. Custom view d. View Resource Detail e. Event detail";

			gstrResult = "FAIL";
			gstrReason = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

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

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;

			String strUserName_3 = "AutoUsr_3" + System.currentTimeMillis();
			String strUsrFulName_3 = strUserName_3;

			String strUserName_4 = "AutoUsr_4" + System.currentTimeMillis();
			String strUsrFulName_4 = strUserName_4;

			String statTypeName = "ST" + strTimeText;

			String statNumTypeName = "NST" + strTimeText;
			String statTextTypeName = "TST" + strTimeText;
			String statMultiTypeName = "MST" + strTimeText;
			String statSaturatTypeName = "SST" + strTimeText;

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[1];
			String strSTvalueOther[] = new String[4];

			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statNumTypeName, statTextTypeName,
					statMultiTypeName, statSaturatTypeName };

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";

			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "";
			String strViewType = "";

			String strRoleName = "AutoR1_" + strTimeText;
			String strRoleValue = "";

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

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
			 * 1. Resource type RT is created selecting a role based status type
			 * ST
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
						seleniumPrecondition, strNSTValue, statTypeName, strStatTypDefn,
						true);
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
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.rolesMandatoryFlds(seleniumPrecondition,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole
						.savAndVerifyRoles(seleniumPrecondition, strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
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
			 * 2. Resource RS is created providing address under resource type
			 * RT
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
			 * 3. View V1 is created selecting ST and RT
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strVewDescription = "Automation";
				strViewType = "Resource (Resources and status types as rows. Status,"
						+ " comments and timestamps as columns.)";

				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. Event template ET is created selecting ST and RT
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

			try {
				assertEquals("", strFuncResult);

				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeVal = { strSTvalue[0] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strETValue = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. Event E1 is created selecting RS
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(seleniumPrecondition,
						strTempName, strResource, strEveName, strInfo, true);
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

			/*
			 * 6. Users U1, U2, U3, U4 are created and has following rights: a)
			 * "View Custom View" b) "View Resource" right on RS c) Role to view
			 * the status type ST 7. All the users have added resource RS and
			 * status types ST to their respective custom views
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						false, true);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_3, strInitPwd, strConfirmPwd,
						strUsrFulName_3);

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
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_4, strInitPwd, strConfirmPwd,
						strUsrFulName_4);

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
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_4, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

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

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			// Custom view of User1
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objPreferences.navEditCustomViewPage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewNewWitTablOrMapOption(seleniumPrecondition,
						strRS, strResrctTypName, statTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Custom view of User2

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserName_2,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewNewWitTablOrMapOption(seleniumPrecondition,
						strRS, strResrctTypName, statTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Custom view of User3

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserName_3,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewNewWitTablOrMapOption(seleniumPrecondition,
						strRS, strResrctTypName, statTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Custom view of User4

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserName_4,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewNewWitTablOrMapOption(seleniumPrecondition,
						strRS, strResrctTypName, statTypeName);

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
			 * 2 Login as RegAdmin and navigate to Setup>>Status Types No
			 * Expected Result
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
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

			/*
			 * 3 Select to create a number status type, create number status
			 * type NST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save NST NST is listed in the
			 * "Status Type List"
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strNSTValue, statNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalueOther[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4 Select to create a multi status type, create multi status type
			 * MST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save MST MST is listed in the
			 * "Status Type List"
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strTSTValue, statTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTextTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalueOther[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Select to create a text status type, create text status type
			 * TST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save TST TST is listed in the
			 * "Status Type List"
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strSSTValue, statSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statSaturatTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalueOther[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Select to create a saturation score status type, create
			 * saturation score status type SST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save SST SST is listed in the
			 * "Status Type List"
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strMSTValue, statMultiTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statMultiTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalueOther[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Navigate to Setup>>Resource Types, edit resource type RT and
			 * select NST, MST, TST and SST and save RT is listed in the
			 * "Resource Type List" screen
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navToeditResrcTypepage(selenium,
						strResrctTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
						strSTvalueOther[0], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
						strSTvalueOther[1], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
						strSTvalueOther[2], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
						strSTvalueOther[3], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Navigate to Setup>>Resource Types, click on the "Users" link
			 * associated with RS. "Assign Users to "RS"" screen is displayed
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToAssignUsersOFRes(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9 Select the data as follows and save the page:
			 * 
			 * 1. "Update Status" right corresponding to user U1 2.
			 * "Run Reports" right corresponding to user U2 3. "Associated with"
			 * right corresponding to user U3
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.assignUsrToResource(selenium, false,
						true, false, true, strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.assignUsrToResource(selenium, false,
						false, true, true, strUsrFulName_2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.assignUsrToResource(selenium, true,
						false, false, true, strUsrFulName_3);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.savAndVerifyEditRSLevelPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 10 Navigate to Setup>>Views, select to edit view V1 and select
			 * status types NST, MST, TST and SST to view V1 and save V1 is
			 * listed in the "Region Views List"
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(selenium,
						strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strSTvalueOther[0], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strSTvalueOther[1], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strSTvalueOther[2], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strSTvalueOther[3], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToRgionViewList(selenium,
						strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 11 Click on 'Customize Resource Detail View' Screen, create a
			 * section 'Sec' then click on 'Uncategorized' section, drag and
			 * drop the status types NST, MST, TST and SST to section 'Sec' and
			 * click on 'Save' "Region Views List" screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumFirefox, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}



			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strArStatType1, strSection1, true);

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
				strSectionValue = objViews
						.fetchSectionID(seleniumFirefox, strSection1);
				if (strSectionValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 12 Navigate to Event>>Event Management and edit event E1, select
			 * status types NST, MST, TST and SST and save No Expected Result
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
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
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navEditEventPage(selenium,
						strEveName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strSTvalueOther[0], false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strSTvalueOther[1], false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strSTvalueOther[2], false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strSTvalueOther[3], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 13 Logout & login as user U1. Navigate to Views>>V1 Status types
			 * NST, MST, TST and SST are displayed in the view V1 screen
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

				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArStatType2[] = { statTypeName, statMultiTypeName,
						statNumTypeName, statSaturatTypeName, statTextTypeName };

				strFuncResult = objViews.chkSTInUserViewScreen(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 14 Navigate to Views>>Custom and navigate to "Custom View - Map"
			 * screen NST, MST, TST and SST are displayed in the resource pop-up
			 * window of RS.
			 */
/*
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navViewCustomTable(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}*/

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strArStatType1,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 15 Navigate to Views>>Map MST is displayed in the "Status Type"
			 * drop-down.
			 * 
			 * NST, MST, TST and SST are displayed in the resource pop-up window
			 * of RS.
			 */

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strArStatType1, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statMultiTypeName + "']"));

				log4j.info("status type " + statMultiTypeName
						+ " is displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statMultiTypeName
						+ " is NOT displayed in dropdown. ");
				strFuncResult = "status type " + statMultiTypeName
						+ " is NOT displayed in dropdown. ";
				gstrReason = strFuncResult;

			}

			/*
			 * 16 Click on the "View Info" link on the resource pop-up window of
			 * RS Status types NST, MST, TST and SST are displayed in the
			 * "View Resource Detail" screen under status type section "Sec"
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strArStatType2[] = { statNumTypeName, statTextTypeName,
						statMultiTypeName, statSaturatTypeName, };

				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User 2

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

				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArStatType2[] = { statTypeName, statMultiTypeName,
						statNumTypeName, statSaturatTypeName, statTextTypeName };

				strFuncResult = objViews.chkSTInUserViewScreen(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strArStatType1,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strArStatType1, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statMultiTypeName + "']"));

				log4j.info("status type " + statMultiTypeName
						+ " is displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statMultiTypeName
						+ " is NOT displayed in dropdown. ");
				strFuncResult = "status type " + statMultiTypeName
						+ " is NOT displayed in dropdown. ";
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

				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strArStatType2[] = { statNumTypeName, statTextTypeName,
						statMultiTypeName, statSaturatTypeName, };

				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// U3

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.login(selenium, strUserName_3,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArStatType2[] = { statTypeName, statMultiTypeName,
						statNumTypeName, statSaturatTypeName, statTextTypeName };

				strFuncResult = objViews.chkSTInUserViewScreen(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strArStatType1,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strArStatType1, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statMultiTypeName + "']"));

				log4j.info("status type " + statMultiTypeName
						+ " is displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statMultiTypeName
						+ " is NOT displayed in dropdown. ");
				strFuncResult = "status type " + statMultiTypeName
						+ " is NOT displayed in dropdown. ";
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

				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArStatType2[] = { statNumTypeName, statTextTypeName,
						statMultiTypeName, statSaturatTypeName, };

				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strArStatType2[] = { statNumTypeName, statTextTypeName,
						statMultiTypeName, statSaturatTypeName, };

				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 28 Logout & login as user U4. Navigate to Views>>V1 Status types
			 * NST, MST, TST and SST are NOT displayed in the view V1 screen for
			 * resource RS
			 */

			// U4

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.login(selenium, strUserName_4,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArStatType2[] = { statMultiTypeName, statNumTypeName,
						statSaturatTypeName, statTextTypeName };

				strFuncResult = objViews.chkSTInUserViewScreen(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Status Type " + statTextTypeName
						+ "is NOT displayed in the User view screen",
						strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String strArStatType3[] = { statNumTypeName, statTextTypeName,
						statMultiTypeName, statSaturatTypeName };

				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strArStatType3,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				String strMsg = " Role Based Status type " + statNumTypeName
						+ " is NOT displayed" + " Role Based Status type "
						+ statTextTypeName + " is NOT displayed"
						+ " Role Based Status type " + statMultiTypeName
						+ " is NOT displayed" + " Role Based Status type "
						+ statSaturatTypeName + " is NOT displayed";

				assertEquals(strMsg, strFuncResult);

				String strArStatType3[] = { statNumTypeName, statTextTypeName,
						statMultiTypeName, statSaturatTypeName };

				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strArStatType3, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				String strMsg = " Role Based Status type " + statNumTypeName
						+ " is NOT displayed" + " Role Based Status type "
						+ statTextTypeName + " is NOT displayed"
						+ " Role Based Status type " + statMultiTypeName
						+ " is NOT displayed" + " Role Based Status type "
						+ statSaturatTypeName + " is NOT displayed";
				assertEquals(strMsg, strFuncResult);
				strFuncResult = "";

				assertFalse(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statMultiTypeName + "']"));

				log4j.info("status type " + statMultiTypeName
						+ " is NOT displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statMultiTypeName
						+ " is displayed in dropdown. ");
				strFuncResult = "status type " + statMultiTypeName
						+ " is displayed in dropdown. ";
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

				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statNumTypeName + "']"));

					log4j
							.info("The Status Type "
									+ statNumTypeName
									+ " is NOT displayed on the view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j
							.info("The Status Type "
									+ statNumTypeName
									+ " is  displayed on the view resource detail screen. ");
					strFuncResult = "The Status Type "
							+ statNumTypeName
							+ " is  displayed on the view resource detail screen. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTextTypeName + "']"));

					log4j
							.info("The Status Type "
									+ statTextTypeName
									+ " is NOT displayed on the view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j
							.info("The Status Type "
									+ statTextTypeName
									+ " is  displayed on the view resource detail screen. ");
					strFuncResult = "The Status Type "
							+ statTextTypeName
							+ " is  displayed on the view resource detail screen. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statMultiTypeName + "']"));

					log4j
							.info("The Status Type "
									+ statMultiTypeName
									+ " is NOT displayed on the view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j
							.info("The Status Type "
									+ statMultiTypeName
									+ " is  displayed on the view resource detail screen. ");
					strFuncResult = "The Status Type "
							+ statMultiTypeName
							+ " is  displayed on the view resource detail screen. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statSaturatTypeName + "']"));

					log4j
							.info("The Status Type "
									+ statSaturatTypeName
									+ " is NOT displayed on the view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j
							.info("The Status Type "
									+ statSaturatTypeName
									+ " is  displayed on the view resource detail screen. ");
					strFuncResult = "The Status Type "
							+ statSaturatTypeName
							+ " is  displayed on the view resource detail screen. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strArStatType2[] = { statNumTypeName, statTextTypeName,
						statMultiTypeName, statSaturatTypeName, };

				strFuncResult = objEventList.checkInEventBannerNew(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Status type NOT displayed", strFuncResult);

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-48884";
			gstrTO = "Create private status type ST, associate ST with resource"
					+ " RS at resource TYPE level and verify that users can view "
					+ "the status type only if they have any of the affiliated"
					+ " resource rights on RS from following screens:a. Region view"
					+ "b. Map c. Custom view d. View Resource Detail e. Event detail";

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
		'Description	:Create private status type ST selecting a role R1 under �Roles with Update rights� section, associate ST with resource RS at resource TYPE level and verify that user with role R1 and with �Update Status� right on RS CAN update the status of ST from following screens:
						a. Region view
						b. Map
						c. Custom view
						d. View Resource Detail
						e. Event detail
		'Precondition	:1. Resource type RT is created selecting a role based status type ST
						2. Resource RS is created providing address under resource type RT
						3. View V1 is created selecting ST and RT
						4. Event template ET is created selecting ST and RT
						5. Event E1 is created selecting RS
						
						6. Users U1 is created and has following rights:
						a) "View Custom View"
						b) "Update Status" and "View Resource" rights on RS
						c) Role to view the status type ST
						7. User U1 has added resource RS and status types ST to his/her custom view 
		'Arguments		:None
		'Returns		:None
		'Date	 		:8-Oct-2012
		'Author			:QSG
		'-------------------------------------------------------------------------------
		'Modified Date                            Modified By
		'<Date>                                     <Name>
		**********************************************************************************/

	@Test
	public void testBQS48958() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "BQS-48958 ";
			gstrTO = "Create private status type ST selecting a role R1 under"
					+ " �Roles with Update rights� section, associate ST with "
					+ "resource RS at resource TYPE level and verify that user"
					+ " with role R1 and with �Update Status� right on RS CAN"
					+ " update the status of ST from following screens:a. Region view"
					+ "b. Map c. Custom view d. View Resource Detail e. Event detail";

			gstrResult = "FAIL";
			gstrReason = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName = "ST" + strTimeText;
			String statrNumTypeName = "RNST" + strTimeText;
			String statrTextTypeName = "RTST" + strTimeText;
			String statrMultiTypeName = "RMST" + strTimeText;
			String statrSaturatTypeName = "RSST" + strTimeText;

			String strStatusTypeValues[] = new String[4];
			String strSTVal = "";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			// RT data
			String strResrctTypName = "RT" + System.currentTimeMillis();
			String strRTVal = "";
			// Resource
			String strResource = "RS" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "";
			String strViewType = "";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";

			// Role
			String strRolesName = "Rol" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statrNumTypeName, statrTextTypeName,
					statrMultiTypeName, statrSaturatTypeName };

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTVal = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeName);

				if (strSTVal.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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
				String[][] strRoleRights = {};
				String[] updateRightValue = { strSTVal };
				String[] strViewRightValue = { strSTVal };
				strFuncResult = objRoles
						.CreateRoleWithAllFieldsCorrect(seleniumPrecondition, strRolesName,
								strRoleRights, strViewRightValue, true,
								updateRightValue, false, false);
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
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Resource type RT is created selecting a role based status type
			 * ST
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='" + strSTVal
								+ "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTVal = objResourceTypes.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource RS is created providing address under resource type
			 * RT
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
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

			/* 3. View V1 is created selecting ST and RT */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strVewDescription = "Automation";
				strViewType = "Summary Plus (Resources as rows."
						+ " Status types and comments as columns.)";
				String strSTValues[] = { strSTVal };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTValues, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 4. Event template ET is created selecting ST and RT */

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

				String[] strResTypeValue = { strRTVal };
				String[] strStatusTypeVal = { strSTVal };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strETValue = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 5. Event E1 is created selecting RS */

			try {
				assertEquals("", strFuncResult);

				strFuncResult=objEventSetup.navToEventManagementNew(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(seleniumPrecondition,
						strTempName, strResource, strEveName, strInfo, true);
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

			/*
			 * 6. Users U1 is created and has following rights: a)
			 * "View Custom View" b) "Update Status" and "View Resource" rights
			 * on RS c) Role to view the status type ST
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.CustomView"),
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

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserName,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7. User U1 has added resource RS and status types ST to his/her
			 * custom view
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewNewWitTablOrMapOption(seleniumPrecondition,
						strRS, strResrctTypName, statTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as RegAdmin and navigate to Setup>>Status Types No
			 * Expected Result
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

				strFuncResult = objStatusTypes.navStatusTypList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Select to create a number status type, create number status
			 * type NST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save NST NST is listed in the
			 * "Status Type List"
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strNSTValue, statrNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statrNumTypeName);
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Select to create a text status type, create text status type
			 * TST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save TST TST is listed in the
			 * "Status Type List"
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strTSTValue, statrTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statrTextTypeName);
				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Select to create a saturation score status type, create
			 * saturation score status type SST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save SST SST is listed in the
			 * "Status Type List"
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strSSTValue, statrSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statrSaturatTypeName);
				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Select to create a multi status type, create multi status type
			 * MST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save MST MST is listed in the
			 * "Status Type List"
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strMSTValue, statrMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statrMultiTypeName);
				if (strStatusTypeValues[3].compareTo("") != 0) {
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
						selenium, statrMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statrMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						selenium, statrMultiTypeName, strStatusName1);
				if (strStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[1] = objStatusTypes.fetchStatValInStatusList(
						selenium, statrMultiTypeName, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Navigate to Setup>>Resource Types, edit resource type RT and
			 * select NST, MST, TST and SST and save RT is listed in the
			 * "Resource Type List" screen
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Navigate to Setup>>Views, select to edit view V1 and select
			 * status types NST, MST, TST and SST to view V1 and save V1 is
			 * listed in the "Region Views List"
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToEditView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.saveAndNavToRgionViewList(selenium,
						strViewName_1);
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
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}



			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumPrecondition);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}

			/*
			 * 9 Click on 'Customize Resource Detail View' Screen, create a
			 * section 'Sec' then click on 'Uncategorized' section, drag and
			 * drop the status types NST, MST, TST and SST to section 'Sec' and
			 * click on 'Save' "Region Views List" screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumPrecondition,
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
				strSectionValue = objViews
						.fetchSectionID(seleniumPrecondition, strSection1);
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 10 Navigate to Event>>Event Management and edit event E1, select
			 * status types NST, MST, TST and SST and save No Expected Result
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navEditEventPage(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 11 Logout & login as user U1. Navigate to Views>>V1 Status types
			 * NST, MST, TST and SST are displayed in the view V1 screen
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
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strArStatType2 = { statTypeName, statrMultiTypeName,
						statrNumTypeName, statrSaturatTypeName,
						statrTextTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 12 Click on the key associated with RS NST, MST, TST and SST are
			 * displayed in the "Update Status" screen
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 13 Update the status of NST, MST, TST and SST by providing valid
			 * values for all 4 status types Updated values are displayed on the
			 * view V1 screen
			 */

			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"10", strStatusTypeValues[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"Text", strStatusTypeValues[1], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strStatusTypeValues[2]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strStatusTypeValues[3], false, "",
						"");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrNumTypeName, "10", "5");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrTextTypeName, "Text", "7");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrSaturatTypeName, "393", "6");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrMultiTypeName, strStatusName1, "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 14 Navigate to Preferences>>Customized View, click on "Options",
			 * add status types NST, MST, TST and SST to the custom view and
			 * save Status types NST, MST, TST and SST are displayed in the
			 * "Custom View - Table"
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewNewWitTablOrMapOption(selenium,
						strRS, strResrctTypName, statrNumTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewWithRSAndST(
						selenium, strRS, strResrctTypName, statrTextTypeName,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewWithRSAndST(
						selenium, strRS, strResrctTypName,
						statrSaturatTypeName, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewWithRSAndST(
						selenium, strRS, strResrctTypName, statrMultiTypeName,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Click on the key associated with RS NST, MST, TST and SST are
			 * displayed in the "Update Status" screen
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 16 Update the status of NST, MST, TST and SST by providing valid
			 * values for all 4 status types Updated status values are displayed
			 * for NST, MST, TST and SST in the "Custom View - Table"
			 */

			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"11", strStatusTypeValues[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"EText", strStatusTypeValues[1], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strStatusTypeValues[2]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strStatusTypeValues[3], false, "",
						"");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInCustomViewTabl(
						selenium, statrNumTypeName, "11", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInCustomViewTabl(
						selenium, statrTextTypeName, "EText", "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInCustomViewTabl(
						selenium, statrSaturatTypeName, "429", "6");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInCustomViewTabl(
						selenium, statrMultiTypeName, strStatusName2, "5");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 17 Click on "Custom View - Map" link Updated status values are
			 * displayed for NST, MST, TST and SST in the resource pop-up window
			 * of RS.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
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

			/*
			 * 18 Click on the "Update Status" link in the
			 * "Resource pop-up window 		NST, MST, TST and SST are displayed in the "
			 * Update Status" screen" +
			 */

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
						"10", strStatusTypeValues[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"Text", strStatusTypeValues[1], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strStatusTypeValues[2]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strStatusTypeValues[3], false, "",
						"");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
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

				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "10");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "Text");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "393");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strStatusName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 20 Navigate to Views>>Map MST is displayed in the "Status Type"
			 * drop-down.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strArStatType1, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statrMultiTypeName + "']"));

				log4j.info("status type " + statrMultiTypeName
						+ " is displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statrMultiTypeName
						+ " is NOT displayed in dropdown. ");
				strFuncResult = "status type " + statrMultiTypeName
						+ " is NOT displayed in dropdown. ";
				gstrReason = strFuncResult;

			}

			/*
			 * 21 Click on the "Update Status" link in the
			 * "Resource pop-up window 		NST, MST, TST and SST are displayed in the "
			 * Update Status" screen " +
			 */

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
						"11", strStatusTypeValues[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"EText", strStatusTypeValues[1], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strStatusTypeValues[2]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strStatusTypeValues[3], false, "",
						"");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
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

			/*
			 * 22 Update the status of NST, MST, TST and SST by providing valid
			 * values for all 4 status types Updated status values are displayed
			 * for NST, MST, TST and SST in the resource pop-up window of RS.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "11");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "EText");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "429");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strStatusName2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 23 Click on the "View Info" link on the resource pop-up window of
			 * RS Status types NST, MST, TST and SST are displayed in the
			 * "View Resource Detail" screen under status type section "Sec"
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 24 Click on the status cell of NST NST is displayed in the
			 * "Update Status" screen
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUpdStatusByFrmViewResDetail(
						selenium, strResVal, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 25 Click on "Show All Statuses" link in the "Update Status"
			 * screen MST, TST and SST are displayed in the "Update Status"
			 * screen along with NST
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToShowAllStatus(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 26 Update the status of NST, MST, TST and SST by providing valid
			 * values for all 4 status types Updated status values are displayed
			 * for NST, MST, TST and SST in the "View Resource Detail" screen
			 * under status type section "Sec" .
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"10", strStatusTypeValues[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"Text", strStatusTypeValues[1], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strStatusTypeValues[2]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strStatusTypeValues[3], false, "",
						"");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.verifyUpdSTValInResDetail(selenium,
						strSection1, statrNumTypeName, "10", "3");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.verifyUpdSTValInResDetail(selenium,
						strSection1, statrTextTypeName, "Text", "3");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.verifyUpdSTValInResDetail(selenium,
						strSection1, statrMultiTypeName, strStatusName1, "3");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.verifyUpdSTValInResDetail(selenium,
						strSection1, statrSaturatTypeName, "393", "3");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 27 Click on the event banner of E1 Status types NST, MST, TST and
			 * SST are displayed in the "Event Status" screen
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navEventStatusPage(selenium,
						strEveName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 28 Click on the key associated with RS NST, MST, TST and SST are
			 * displayed in the "Update Status" screen
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 29 Update the status of NST, MST, TST and SST by providing valid
			 * values for all 4 status types Updated status values are displayed
			 * for NST, MST, TST and SST in the "Event Status" screen.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"11", strStatusTypeValues[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"EText", strStatusTypeValues[1], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strStatusTypeValues[2]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strStatusTypeValues[3], false, "",
						"");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrNumTypeName, "11", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrTextTypeName, "EText", "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrSaturatTypeName, "429", "6");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrMultiTypeName, strStatusName2, "5");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-48958";
			gstrTO = "Create private status type ST selecting a role R1 under"
					+ " �Roles with Update rights� section, associate ST with "
					+ "resource RS at resource TYPE level and verify that user"
					+ " with role R1 and with �Update Status� right on RS CAN"
					+ " update the status of ST from following screens:a. Region view"
					+ "b. Map c. Custom view d. View Resource Detail e. Event detail";
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

/***************************************************************
'Description		:Create a private status type ST and verify that ST can be viewed by any user (non system
                     admin) in the following setup pages even if the user does not have any resource rights 
                     on the associated resource:
						a. Status Type List
						b. Create New /Edit Resource Type
						c. Create /Edit Role
						d. Edit Resource Level Status Types
						e. Create New/Edit/Copy View
						f. Edit Resource Detail View Sections
						g. Create/Edit Event Template
						e. Edit event
						f. While configuring the form
'Arguments		   :None
'Returns		   :None
'Date			   :8/30/2012
'Author			   :QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testBQS48712() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		Forms objForms = new Forms();
		try {
			gstrTCID = "48712"; // Test Case Id
			gstrTO = "Create a private status type ST and verify that ST can be viewed by any user (non system admin) in the following setup pages "
					+ "even if the user does not have any resource rights on the associated resource:"
					+ "a. Status Type List"
					+ "b. Create New /Edit Resource Type"
					+ "c. Create /Edit Role"
					+ "d. Edit Resource Level Status Types"
					+ "e. Create New/Edit/Copy View"
					+ "f. Edit Resource Detail View Sections"
					+ "g. Create/Edit Event Template"
					+ "e. Edit event"
					+ "f. While configuring the form";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String strStatusTypeValues[] = new String[5];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoSTn_2" + strTimeText;
			String statTypeName3 = "AutoSTm_3" + strTimeText;
			String statTypeName4 = "AutoSTt_4" + strTimeText;
			String statTypeName5 = "AutoSTs_5" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[2];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strHavBed = "No";
			String strResVal = "";
			String strRSValues[] = new String[1];

			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
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

			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			// Role
			String strRolesName = "AutoRol_" +strTimeText;
			String strRoleValue = "";

			String strTempName = "Autotmp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "Automation";

			// form
			String strFormTempTitle = "OF" + strTimeText;
			String strFormActiv = "As Certain Status Changes";
			String strFormDesc = "Automation";

	/*
	* STEP :
	  Action:Preconditions:
		1.User A is created with the following rights:
		a.Setup Status Types.
		b.Setup Resource Types.
		c.Setup Resources.
		d.Edit Resources Only.
		e.Setup Roles.
		f.Maintain Events.
		g.Maintain Event Templates.
		i.Configure region views.
		j.Form - User may create and modify forms
		2. Resource type RT is associated with status type ST1.
		3. Resource RS1 is created under resource Type RT. 
		4.Role R1 is created selecting ST1.
		5.View V1 is created selecting ST1 and RT.
		6.Event template ET is created selecting ST1 and RT.
		7.Event E1 is created under the template ET selecting RS.
		8. User A does has only "View Resource" right and not any of the affiliated resource rights (Update Status/Run Reports/Associated with).
			  Expected Result:No Expected Result
		*/
		//278578

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
				     + " EXECUTION STATRTS~~~~~");

			try {
				
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
			// ST1
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName1);
				if (strStatusTypeValues[0].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName, strStatusTypeValues[0]);
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

			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(seleniumPrecondition,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
						strResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValues[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// View

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { strStatusTypeValues[0] };
				String[] strRSValue = { strResVal };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(seleniumPrecondition, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(seleniumPrecondition,
						strRsTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event template
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
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try{assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(seleniumPrecondition,
						strEveName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = { strStatusTypeValues[0]};
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName, strRoleRights, strViewRightValue, true,
						updateRightValue, true, false);
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
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER
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
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpRoles");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strResVal, false, false, false,
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

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
	/*
	* STEP :
	  Action:Login as User A and Navigate to Setup >> Status Type
	  Expected Result:No Expected Result
	*/
	//278579
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Create number status type NST by selecting the option "Only users affiliated with specific resources may view or update this status type"
	  Expected Result:NST is listed in the "Status Type List" screen
	*/
	//278580
			
			// NST
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selStatusTypesAndCreatePrivateST(
						selenium, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName2);
				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Create multi status type MST by selecting the option "Only users affiliated with specific resources may view or update this status type"
	  Expected Result:MST is listed in the "Status Type List" screen
	*/
	//278581

			// MST
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selStatusTypesAndCreatePrivateST(
						selenium, strStatusTypeValue, statTypeName3,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName3);
				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Create text status type TST by selecting the option "Only users affiliated with specific resources may view or update this status type"
	  Expected Result:TST is listed in the "Status Type List" screen
	*/
	//278582
			// TST
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selStatusTypesAndCreatePrivateST(
						selenium, strStatusTypeValue, statTypeName4,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName4);
				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Create saturation score status type SST by selecting the option "Only users affiliated with specific resources may view or update this status type"
	  Expected Result:SST is listed in the "Status Type List" screen
	*/
	//278583
			// SST
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Saturation Score";
				strFuncResult = objStatusTypes.selStatusTypesAndCreatePrivateST(
						selenium, strStatusTypeValue, statTypeName5,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName5);
				if (strStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Navigate to Setup >> Resource Types.
	  Expected Result:'Resource Type List' screen is displayed
	*/
	//278584
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Click on 'Edit' link associated with 'RT' resource type, and select status types NST, MST, TST and SST and click on 'Save'.
	  Expected Result:Resource Type 'RT' is displayed in 'Resource Type List' screen
	*/
	//278585
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(selenium, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(selenium, strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Select to create new resource type
	  Expected Result:Status Types NST, MST, TST and SST are displayed
	*/
	//278586
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToCreateNewResrcTypePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.checkSTInEditResTypePage(selenium, statTypeName2,
						 strStatusTypeValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.checkSTInEditResTypePage(selenium, statTypeName3,
						 strStatusTypeValues[2], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.checkSTInEditResTypePage(selenium, statTypeName4,
						 strStatusTypeValues[3], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.checkSTInEditResTypePage(selenium, statTypeName5,
						 strStatusTypeValues[4], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Navigate to Setup >> Roles and click on 'Edit' link associated with role 'R1'.
	  Expected Result:Status Types NST, MST, TST and SST are displayed under following sections:
		a.Select the Status Types this Role may view 
		b.Select the Status Types this Role may update
			  By default the checkbox associated with NST, MST, TST and SST are 
			  disabled under "Select the Status Types this Role may view." section
			   and are enabled under "Select the Status Types this Role may update." section.
	*/
	//278587
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navEditRolesPge(selenium, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTPresentOrNotInEditRolePage(selenium,  strStatusTypeValues[1], true,
						strStatusTypeValues[1], true, statTypeName2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTPresentOrNotInEditRolePage(selenium,  strStatusTypeValues[2], true,
						strStatusTypeValues[2], true, statTypeName3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTPresentOrNotInEditRolePage(selenium,  strStatusTypeValues[3], true,
						strStatusTypeValues[3], true, statTypeName4, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTPresentOrNotInEditRolePage(selenium,  strStatusTypeValues[4], true,
						strStatusTypeValues[4], true, statTypeName5, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Disability
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTDisabilityInEditRolePgeForViewSec(selenium,
						strStatusTypeValues[1], false, statTypeName2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTDisabilityInEditRolePgeForViewSec(selenium,
						strStatusTypeValues[2], false, statTypeName3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTDisabilityInEditRolePgeForViewSec(selenium,
						strStatusTypeValues[3], false, statTypeName4, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTDisabilityInEditRolePgeForViewSec(selenium,
						strStatusTypeValues[4], false, statTypeName5, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//enability
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTDisabilityInEditRolePgeForUpdateSec(selenium,
						strStatusTypeValues[1], true, statTypeName2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTDisabilityInEditRolePgeForUpdateSec(selenium,
						strStatusTypeValues[2], true, statTypeName3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTDisabilityInEditRolePgeForUpdateSec(selenium,
						strStatusTypeValues[3], true, statTypeName4, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTDisabilityInEditRolePgeForUpdateSec(selenium,
						strStatusTypeValues[4], true, statTypeName5, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Navigate to Setup >> Resource and click on 'Edit Status Types' link associated with resource 'RS'.
	  Expected Result:Status Types NST, MST, TST and SST are displayed under 'Additional Status Types' section
	*/
	//278588
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium, statTypeName2,
						strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium, statTypeName3,
						strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium, statTypeName4,
						strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium, statTypeName5,
						strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Navigate to Setup >> Views and select to create a new view
	  Expected Result:'Create New View' screen is displayed and status types NST, MST,
	   TST and SST are displayed under 'Select Status Types' section.
	*/
	//278589
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToCreateViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium, true, strStatusTypeValues[1], statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium, true, strStatusTypeValues[2], statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium, true, strStatusTypeValues[3], statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium, true, strStatusTypeValues[4], statTypeName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Click on 'Edit' link associated with view 'V1'
	  Expected Result:'Edit View' screen is displayed and status types NST, MST, TST and SST are displayed under 'Select Status Types' section.
	*/
	//278590
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium, true, strStatusTypeValues[1], statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium, true, strStatusTypeValues[2], statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium, true, strStatusTypeValues[3], statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium, true, strStatusTypeValues[4], statTypeName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Click on 'Copy' link associated with view 'V1'
	  Expected Result:'Edit View' screen is displayed and status types NST, MST, TST and SST are displayed under 'Select Status Types' section.
	*/
	//278591
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditViewByCopyLink(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium, true, strStatusTypeValues[1], statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium, true, strStatusTypeValues[2], statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium, true, strStatusTypeValues[3], statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium, true, strStatusTypeValues[4], statTypeName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Navigate to Setup >> Views and Click on 'Customize Resource Detail View'
	  Expected Result:Status types NST, MST, TST and SST are displayed under 'Uncategorized' section on 'Edit Resource Detail View Sections' screen
	*/
	//278592
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTEditResDetailViewSec(selenium, true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTEditResDetailViewSec(selenium, true, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTEditResDetailViewSec(selenium, true, statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTEditResDetailViewSec(selenium, true, statTypeName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


	/*
	* STEP :
	  Action:Navigate to Event >> Event Setup and click on 'Create New Event Template' button
	  Expected Result:Status types NST, MST, TST and SST are displayed under 'Status Types' section
	*/
	//278593
			
			try {
				assertEquals("", strFuncResult);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(selenium, 
						strStatusTypeValues[1], true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(selenium, 
						strStatusTypeValues[2], true, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(selenium, 
						strStatusTypeValues[3], true, statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(selenium, 
						strStatusTypeValues[4], true, statTypeName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Navigate to Event >> Event Setup and click on 'Edit' link associated with event template 'ET'
	  Expected Result:'Edit Event Template' screen is displayed with the status types NST, MST, TST and SST under 'Status Types' section.
	*/
	//278594
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.editEventTemplate(selenium, strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(selenium, 
						strStatusTypeValues[1], true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(selenium, 
						strStatusTypeValues[2], true, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(selenium, 
						strStatusTypeValues[3], true, statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(selenium, 
						strStatusTypeValues[4], true, statTypeName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Select NST, MST, TST and SST and click on 'Save'
	  Expected Result:'Event Template List' screen is displayed with the event template 'ET'
	*/
	//278595
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectSTInEditEventPage(selenium,
						strStatusTypeValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectSTInEditEventPage(selenium,
						strStatusTypeValues[2], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectSTInEditEventPage(selenium,
						strStatusTypeValues[3], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectSTInEditEventPage(selenium,
						strStatusTypeValues[4], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(selenium, strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Navigate to Event >> Event Management,click on 'Edit' link associated with event 'E1'
	  Expected Result:'Edit Event' screen is displayed with status types NST, MST, TST and SST under 'Status types' section.
	*/
	//278596
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium, strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventPage(selenium, strStatusTypeValues[1],
						true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventPage(selenium, strStatusTypeValues[2],
						true, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventPage(selenium, strStatusTypeValues[3],
						true, statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventPage(selenium, strStatusTypeValues[4],
						true, statTypeName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Navigate to Forms >> Configure Forms and click on 'Create New Form Template'
	  Expected Result:'Create New Form Template' screen is displayed.
	*/
	//278597
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);				
				strFuncResult=objForms.navTocreateNewFormTemplate(selenium);			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Provide form title and description and select 'As Status changes' from 'Form Activation'
	   drop-down list and click on 'Next'.
	  Expected Result:Status types NST, MST and SST are displayed under 'Form Activation Status Type' section.
	*/
	//278598
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.filAllFldsInCreatNewFormAsStatusChanges(selenium,
								strFormTempTitle, strFormDesc, strFormActiv,
								false, false, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.checkSTInFormActivationStPage(
						selenium, statTypeName2, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.checkSTInFormActivationStPage(
						selenium, statTypeName3, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.checkSTInFormActivationStPage(
						selenium, statTypeName5, strStatusTypeValues[4], true);
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
	gstrTCID = "BQS-48712";
	gstrTO = "Create a private status type ST and verify that ST can be viewed by any user (non system admin) in the following setup pages " +
			"even if the user does not have any resource rights on the associated resource:"+
				"a. Status Type List"+
				"b. Create New /Edit Resource Type"+
				"c. Create /Edit Role"+
				"d. Edit Resource Level Status Types"+
				"e. Create New/Edit/Copy View"+
				"f. Edit Resource Detail View Sections"+
				"g. Create/Edit Event Template"+
				"e. Edit event"+
				"f. While configuring the form";
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
