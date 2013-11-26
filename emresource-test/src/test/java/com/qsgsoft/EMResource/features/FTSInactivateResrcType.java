package com.qsgsoft.EMResource.features;

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
import static org.junit.Assert.*;

/*******************************************************************
' Description       :This class includes requirement test cases from
' Requirement Group :Creating & managing users
' Requirement       :Edit user
' Date		        :24-April-2012
' Author	        :QSG
'-------------------------------------------------------------------
' Modified Date                                        Modified By
' <Date>                           	                   <Name>
'*******************************************************************/

public class FTSInactivateResrcType{

	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSEditUser");
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
	Selenium selenium, seleniumPrecondition;
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
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		selenium.close();
		selenium.stop();

		seleniumPrecondition.close();
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

		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
	
	//start//testFTS108114//
	/***************************************************************
	'Description		:Verify that resource associated with deactivated resource type  does not receive expired status notification.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:9/10/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS108114() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		Date_Time_settings dts = new Date_Time_settings();
		General objMail = new General();

		try {
			gstrTCID = "108114"; // Test Case Id
			gstrTO = " Verify that resource associated with deactivated resource type  does not receive expired status notification.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn1 = rdExcel.readData("Login", 3, 4);

			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserName;

			String strUserName1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName1 = strUserName1;

			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			String strExpHr = "00";
			String strExpMn = "05";

			// Status types
			String strStatTypDefn = "Automation";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strSSTValue = "Saturation Score";

			String statrNumTypeName = "rNST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			String statsMultiTypeName = "sMST" + strTimeText;

			// Status
			String str_MultiStatusName1 = "Msa" + strTimeText;
			String strStatTypeColor = "Black";

			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue[] = new String[1];

			String strStatValue = "";
			String strSTvalue[] = new String[3];
			String str_MultiStatusValue[] = new String[2];

			// RT
			String strResrcTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";

			// RS

			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTimeText;
			String strResVal = "";

			
			String strState = "Alabama";
			String strCountry = "Coosa County";

			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);

			// Mail
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";

			int intEMailRes_Expire = 0;
			int intPagerRes_Expire = 0;
			int intEMailRes_Shift = 0;
			int intPagerRes_Shift = 0;
			int intResCnt = 0;
			/*
			 * STEP : Action:Preconditions: <br> <br>1. Status types 'NST1'(role
			 * based),'MST1'(shared),'SST1' (private) are created selecting
			 * expiration time of time 'T1' (Ex: 5 min) and role 'R1' under view
			 * rights,update rights section. <br> <br>2. Resource type 'RT' is
			 * created selecting status types 'MST1','SST1' <br> <br>3. Resource
			 * 'RS' is created under 'RT' providing address and associating
			 * status type 'SST1' at resource level. <br> <br>4. User 'U1' is
			 * created providing E-mail and pager address has subscribed to
			 * receive expired status notifications and is associated with a
			 * role 'R1'. <br> <br>5. User 'U1' and 'U2' have view and update
			 * resource right on resource 'RS' and are associated with role 'R1'
			 * Expected Result:No Expected Result
			 */
			// 611624

			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.savAndVerifyRoles(seleniumPrecondition,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status types

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// rNST

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
				String[][] strRoleViewValue = { { strRoleValue[0], "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue[0], "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrNumTypeName);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// private SST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createPrivateSTWitMandFlds(
						seleniumPrecondition, strSSTValue,
						statpSaturatTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue[0], "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue[0], "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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
						seleniumPrecondition, statpSaturatTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statpSaturatTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Shared MST

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statsMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statsMultiTypeName, strVisibilty,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue[0], "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue[0], "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, statsMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statsMultiTypeName);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* Add statuses to MST. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.createSTWithinMultiTypeSTExpTime(seleniumPrecondition,
								statsMultiTypeName, str_MultiStatusName1,
								strMSTValue, strStatTypeColor, strExpHr,
								strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_MultiStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statsMultiTypeName, str_MultiStatusName1);
				if (str_MultiStatusValue[0].compareTo("") != 0) {
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
						seleniumPrecondition, strResrcTypName, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[2], true);
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
				strRTValue = objResourceTypes.fetchRTValueInRTList(
						seleniumPrecondition, strResrcTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Resource

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
						strResrcTypName, "FN", "LN", strState, strCountry,
						"Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User U1

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
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "",
						strPrimaryEMail, strEMail, strPagerValue, "");
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

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strResVal, false,
						true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strRoleValues = strRoleValue[0];
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValues, true);

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

			// User U2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRoleValues = strRoleValue[0];
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValues, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strResVal, false,
						true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUsrFulName1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user 'U2' Expected Result:'Region Default'
			 * screen is displayed.
			 */
			// 611625

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> Map. Select resource 'RS' under
			 * 'Find Resource' drop down and update status values of status
			 * types 'NST1','MST1'and 'SST1' from resource pop up window.
			 * Expected Result:Status types 'NST1','MST1'and 'SST1'updated and
			 * are displayed on 'resource pop up window' screen
			 */
			// 611626

			try {
				assertEquals("", strFuncResult);

				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statrNumTypeName,
						statpSaturatTypeName, statsMultiTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatus(selenium,
						"link=Update Status");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", strSTvalue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						str_MultiStatusValue[0], strSTvalue[2], false, "", "");
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
				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "101");
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
						str_MultiStatusName1);
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
			 * STEP : Action:Before expiration time 'T1',Login as RegAdmin,
			 * navigate to 'Setup >> Resource Types' and deactivate the resource
			 * type 'RT' Expected Result:'RT' is deactivated and is not
			 * displayed in the 'Resource Type List' screen. (i.e. when 'include
			 * inactive resource types' is not checked)
			 */
			// 611627

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						selenium, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.editResrcTypeMandatoryFlds_new(selenium,
								strResrcTypName, strResrcTypName,
								statrNumTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						selenium, strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * STEP : Action:Wait until status expiration time 'T1' (Ex: 5 min)
			 * Expected Result:E-mail and pager notifications are not received
			 * for User 'U1'.
			 */
			// 611628

			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(300000);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intPagerRes_Expire++;
						strSubjName = "EMResource Expired Status Notification: "
								+ strResource;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Expire++;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Expire++;
						strFuncResult = objMail.backToMailInbox(selenium,
								false, true, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// check Email, pager notification
					if (intEMailRes_Expire == 2 && intPagerRes_Expire == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intPagerRes_Shift++;
						strSubjName = "EMResource Expired Status Notification: "
								+ strResource;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Shift++;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Shift++;
						strFuncResult = objMail.backToMailInbox(selenium,
								false, true, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// check Email, pager notification
					if (intEMailRes_Shift == 2 && intPagerRes_Shift == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
					selenium.selectFrame("Data");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertEquals("", gstrReason);
				if (intResCnt == 2) {
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-108114";
			gstrTO = "Verify that resource associated with deactivated resource type  does not receive expired status notification.";
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

	// end//testFTS108114//
	//start//testFTS108116//
	/***************************************************************
	'Description		:Reactivate resource type and verify that resource associated with inactive resource type are displayed on 'Status change preferences' screen
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:9/10/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS108116() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		Preferences objPreferences = new Preferences();
		General objMail = new General();

		try {
			gstrTCID = "108116"; // Test Case Id
			gstrTO = " Reactivate resource type and verify that resource associated with inactive resource type are displayed on 'Status change preferences' screen";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNSTValue = "Number";
			String strNumStatType = "AutoNSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strSTvalue[] = new String[1];

			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strRTValue[] = new String[2];

			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTimeText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strRSValue[] = new String[1];

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserName;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strSaturAbove = "400";
			String strSaturBelow = "200";

			String strCategory = "(Any)";
			String strCityZipCd = "";

			/*
			 * STEP : Action:Preconditions: <br> <br>1. Status types 'ST1' is
			 * created. <br> <br>2. Resource type 'RT' is created selecting
			 * 'ST1'. <br> <br>3. Resource 'RS' is created under 'RT'. <br>
			 * <br>4. User U1 is created selecting following rights: <br>a.
			 * Setup-Resource Type <br>b. Edit Status Change Notification
			 * Preferences. <br>c. View right on resource 'RS' <br>d. User-setup
			 * user Account <br> <br>5. Status Change Preferences are set for
			 * user U1 in region selecting resources RS and status type 'ST1'
			 * <br> <br>6. Resource Type 'RT' is deactivated after setting
			 * status change preferences for 'RS' Expected Result:No Expected
			 * Result
			 */
			// 611697

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumStatType);
				if (strSTvalue[0].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
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

			// Roles

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalues = { strSTvalue[0] };
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTvalue, false, strSTvalues, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
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
						seleniumPrecondition, strResource, strRSValue[0],
						false, false, false, true);

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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status change preferences
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(
								seleniumPrecondition, strResource, strResVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(
								seleniumPrecondition, strResource, strResVal,
								strSTvalue[0], strNumStatType, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(
								seleniumPrecondition, strResource, strResVal,
								strSTvalue[0], strNumStatType, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(
								seleniumPrecondition, strSaturAbove,
								strSaturBelow, strResVal, strSTvalue[0], true,
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

			try {
				assertEquals("", strFuncResult);

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
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.editResrcTypeMandatoryFlds_new(seleniumPrecondition,
								strResrctTypName, strResrctTypName,
								strNumStatType, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						seleniumPrecondition, strResrctTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as user 'U1', Navigate to Preferences >>
			 * Status change prefs Expected Result:Previously set preferences
			 * for resource 'RS' are not retained. <br> <br>'Add' button is
			 * displayed.
			 */
			// 611702

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.login(selenium, strUserName,
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.vfyPrefrnceSetValuesNotRetained(
						selenium, strNumStatType, "Above: 400", "X", "X", "X",
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Add' and search for resource 'RS'
			 * Expected Result:Resource 'RS' is not retrieved.
			 */
			// 611703

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navToFindResourcesPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String[] strResources = { strResource };
				strFuncResult = objPreferences.findResources(selenium,
						strResources, strCategory, strRegion, strCityZipCd,
						strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Resource " + strResource + " is NOT found",
						strFuncResult);
				strFuncResult = objPreferences
						.vfyResourceNotPresInFindResrcPge(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to setup >> Users , Click on 'Edit' link
			 * associated with user 'U1' ,navigate to 'User Preferences' section
			 * and click on 'Status Change Notification Preferences' link
			 * Expected Result:Previously set preferences for resource 'RS' are
			 * not retained. <br> <br>'Add' button is displayed.
			 */
			// 611704

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navStatusChangeNotiFrmEditUserPgeNew(selenium);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.vfyPrefrnceSetValuesNotRetained(
						selenium, strNumStatType, "Above: 400", "X", "X", "X",
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Add' and search for resource 'RS'
			 * Expected Result:Resource 'RS' is not retrieved.
			 */

			// 611705

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navToFindResourcesPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String[] strResources = { strResource };
				strFuncResult = objPreferences.findResources(selenium,
						strResources, strCategory, strRegion, strCityZipCd,
						strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Resource " + strResource + " is NOT found",
						strFuncResult);
				strFuncResult = objPreferences
						.vfyResourceNotPresInFindResrcPge(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 
			 * STEP : Action:Navigate to 'Setup >> Resource Types' and
			 * reactivate resource type 'RT' Expected Result:'RT' is displayed
			 * in the 'Resource Type List' screen. <br> <br>'Active' is
			 * displayed under Active column.
			 */
			// 611701

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
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.editResrcTypeMandatoryFlds_new(selenium,
								strResrctTypName, strResrctTypName,
								strNumStatType, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						selenium, strResrctTypName, true);
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

				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Preferences >> Status change prefs
			 * Expected Result:'My Status Change Preferences' screen is
			 * displayed. <br> <br>Previously set preferences for resource 'RS'
			 * is displayed appropriately.
			 */
			// 611698

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

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
						+ strResource + "']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info(" Resource name is displayed");
				} else {
					strFuncResult = "Resource name is NOT in the displayed";
					log4j
							.info(" Resource name is NOT displayed in the screen.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								strNumStatType, "Above: 400", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								strNumStatType, "Below: 200", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to setup >> Users , Click on 'Edit' link
			 * associated with user 'U1' Expected Result:'Edit User' screen is
			 * displayed.
			 */
			// 611699

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to 'User Preferences' section and click on
			 * 'Status Change Notification Preferences' link Expected Result:'My
			 * Status Change preferences' screen is displayed. <br>
			 * <br>Previously set preferences for resource 'RS' is displayed
			 * appropriately.
			 */
			// 611700

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navStatusChangeNotiFrmEditUserPgeNew(selenium);

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

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
						+ strResource + "']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info(" Resource name is displayed");
				} else {
					strFuncResult = "Resource name is NOT in the displayed";
					log4j
							.info(" Resource name is NOT displayed in the screen.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								strNumStatType, "Above: 400", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								strNumStatType, "Below: 200", "X", "X", "X");
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
			gstrTCID = "FTS-108116";
			gstrTO = "Reactivate resource type and verify that resource associated with inactive resource type are displayed on 'Status change preferences' screen";
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

	// end//testFTS108116//
	// start//testFTS108115//
	/***************************************************************
	 * 'Description :Verify that resource associated with inactive resource type
	 * are not displayed on 'Status change preferences' screen 'Precondition :
	 * 'Arguments :None 'Returns :None 'Date :9/11/2013 'Author :QSG
	 * '---------------------------------------------------------------
	 * 'Modified Date Modified By 'Date Name
	 ***************************************************************/

	@Test
	public void testFTS108115() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		Preferences objPreferences = new Preferences();
		General objMail = new General();

		try {
			gstrTCID = "108115"; // Test Case Id
			gstrTO = " Verify that resource associated with inactive resource type are not displayed on 'Status change preferences' screen";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNSTValue = "Number";
			String strNumStatType = "AutoNSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strSTvalue[] = new String[1];

			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strRTValue[] = new String[2];

			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTimeText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strRSValue[] = new String[1];

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserName;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strSaturAbove = "400";
			String strSaturBelow = "200";

			String strCategory = "(Any)";
			String strCityZipCd = "";

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			/*
			 * STEP : Action:Preconditions: 1. Status types 'ST1' is created. 2.
			 * Resource type 'RT' is created selecting 'ST1'. 3. Resource 'RS'
			 * is created under 'RT'. 4. User U1 is created selecting following
			 * rights: a. Setup-Resource Type b. Edit Status Change Notification
			 * Preferences. c. View right on resource 'RS' d. User-setup user
			 * Account 5. Status Change Preferences are set for user U1 in
			 * region selecting resources RS and status type 'ST1' Expected
			 * Result:No Expected Result
			 */
			// 611636

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumStatType);
				if (strSTvalue[0].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
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

			// Roles

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalues = { strSTvalue[0] };
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTvalue, false, strSTvalues, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
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
						seleniumPrecondition, strResource, strRSValue[0],
						false, false, false, true);

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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status change preferences
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(
								seleniumPrecondition, strResource, strResVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(
								seleniumPrecondition, strResource, strResVal,
								strSTvalue[0], strNumStatType, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(
								seleniumPrecondition, strResource, strResVal,
								strSTvalue[0], strNumStatType, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(
								seleniumPrecondition, strSaturAbove,
								strSaturBelow, strResVal, strSTvalue[0], true,
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

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as user 'U1', Navigate to Preferences >>
			 * Status change prefs Expected Result:'My Status Change
			 * Preferences' screen is displayed.
			 * 
			 * Preferences set for resource 'RS' is displayed appropriately.
			 */
			// 611637

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.login(selenium, strUserName,
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

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
						+ strResource + "']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info(" Resource name is displayed");
				} else {
					strFuncResult = "Resource name is NOT in the displayed";
					log4j
							.info(" Resource name is NOT displayed in the screen.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								strNumStatType, "Above: 400", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								strNumStatType, "Below: 200", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to setup >> Users , Click on 'Edit' link
			 * associated with user 'U1' Expected Result:'Edit User' screen is
			 * displayed.
			 */
			// 611643

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to 'User Preferences' section and click on
			 * 'Status Change Notification Preferences' link Expected Result:'My
			 * Status Change preferences' screen is displayed. Preferences set
			 * for resource 'RS' is displayed appropriately.
			 */
			// 611654

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navStatusChangeNotiFrmEditUserPgeNew(selenium);

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

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
						+ strResource + "']";
				strFuncResult = objMail.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info(" Resource name is displayed");
				} else {
					strFuncResult = "Resource name is NOT in the displayed";
					log4j
							.info(" Resource name is NOT displayed in the screen.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								strNumStatType, "Above: 400", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								strNumStatType, "Below: 200", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to 'Setup >> Resource Types' and
			 * deactivate resource type 'RT' Expected Result:'RT' is deactivated
			 * and is not displayed in the 'Resource Type List' screen. (i.e.
			 * when 'include inactive resource types' is not checked)
			 */
			// 611655

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
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.editResrcTypeMandatoryFlds_new(selenium,
								strResrctTypName, strResrctTypName,
								strNumStatType, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						selenium, strResrctTypName, false);
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
			 * STEP : Action:Login as user 'U1', Navigate to Preferences >>
			 * Status change prefs Expected Result:Previously set preferences
			 * for resource 'RS' are not retained. 'Add' button is displayed.
			 */
			// 611672

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.login(selenium, strUserName,
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.vfyPrefrnceSetValuesNotRetained(
						selenium, strNumStatType, "Above: 400", "X", "X", "X",
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Add' and search for resource 'RS'
			 * Expected Result:Resource 'RS' is not retrieved.
			 */
			// 611680

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navToFindResourcesPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String[] strResources = { strResource };
				strFuncResult = objPreferences.findResources(selenium,
						strResources, strCategory, strRegion, strCityZipCd,
						strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Resource " + strResource + " is NOT found",
						strFuncResult);
				strFuncResult = objPreferences
						.vfyResourceNotPresInFindResrcPge(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to setup >> Users , Click on 'Edit' link
			 * associated with user 'U1' ,navigate to 'User Preferences' section
			 * and click on 'Status Change Notification Preferences' link
			 * Expected Result:Previously set preferences for resource 'RS' are
			 * not retained. 'Add' button is displayed.
			 */
			// 611688

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navStatusChangeNotiFrmEditUserPgeNew(selenium);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.vfyPrefrnceSetValuesNotRetained(
						selenium, strNumStatType, "Above: 400", "X", "X", "X",
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Add' and search for resource 'RS'
			 * Expected Result:Resource 'RS' is not retrieved.
			 */
			// 611689

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navToFindResourcesPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String[] strResources = { strResource };
				strFuncResult = objPreferences.findResources(selenium,
						strResources, strCategory, strRegion, strCityZipCd,
						strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Resource " + strResource + " is NOT found",
						strFuncResult);
				strFuncResult = objPreferences
						.vfyResourceNotPresInFindResrcPge(selenium, strResource);
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
			gstrTCID = "FTS-108115";
			gstrTO = "Verify that resource associated with inactive resource type are not displayed on 'Status change preferences' screen";
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

	// end//testFTS108115//
	//start//testFTS108090//
	/***************************************************************
	'Description		:Verify that inactive resource Type is not displayed on following screens: <br>
	a. Create/Edit view.  <br>
	b. Create/Edit event template.    <br> 
	c. Resource Type drop down while searching user/resource.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:9/16/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS108090() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		RegionalInfo objRegionalInfo = new RegionalInfo();

		try {
			gstrTCID = "108090"; // Test Case Id
			gstrTO = " Verify that inactive resource Type is not displayed on following screens:"
					+ "	a. Create/Edit view."
					+ "  b. Create/Edit event template. "
					+ "  c. Resource Type drop down while searching user/resource.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValue = "Number";

			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";

			// RT2
			String strResrcTypName2 = "AutoRT_2" + strTimeText;
			String strRTValue[] = new String[2];
			String strResrcTypName = "AutoRT_1" + strTimeText;

			// RS
			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTimeText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strRSValue[] = new String[1];

			// Views
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";

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

			// ET
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			/*
			 * STEP : Action:Precondition: 1. Status types 'ST1','ST2' are
			 * created in region 'RG1' 2. User 'U1' is created in region 'RG1'
			 * 3. Resource 'RS' is created selecting resource type 'RT2' 4. View
			 * 'V1' is created selecting status types 'ST1','ST2' and 'RT2' 5.
			 * Event Template 'ET' is created selecting status types 'ST1'
			 * ,'ST2' and 'RT2' (Note: more then 15 users and resource are
			 * available in region) Expected Result:No Expected Result
			 */
			// 611413

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeName1);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName2, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeName2);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
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
				String[] updateRightValue = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				String[] strViewRightValue = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objRoles.CreateRoleWithAllFields(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, false, updateRightValue, false,
						false);
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
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT2

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
						seleniumPrecondition, strResrcTypName2,
						strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName2);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
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
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName2, strContFName, strContLName, strState,
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
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName_1, strVewDescription, strViewType, true,
						false, strStatusTypeValues, false, strRSValue, false);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ET1
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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin to region 'RG1' Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 611414

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
				blnLogin = true;
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
			 * STEP : Action:Navigate to Setup >> Resource Types and click on
			 * 'Create New Resource Type' Expected Result:'Create New Resource
			 * Type' screen is displayed.
			 */
			// 611417

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Create inactive Resource Type 'RT' selecting status
			 * types 'ST1','ST2' <br> <br>(include inactive resource type check
			 * box is not selected) Expected Result:Resource Type 'RT' is not
			 * listed under 'Resource Type' list screen.
			 */
			// 611925

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName, strStatusTypeValues[0]);
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
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						selenium, strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.varDeactivatedRT(selenium,
						strResrcTypName, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select include inactive resource type check box on
			 * 'Resource Type List' screen Expected Result:Resource Type 'RT' is
			 * displayed under 'Resource Type List' screen. <br> <br>'Disabled'
			 * is displayed under 'Active' column.
			 */
			// 611926

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selectDesectIncludeInactiveRT(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.vrfyResTypePresentOrNotInRTList(selenium,
								strResrcTypName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName);
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
				strFuncResult = objResourceTypes
						.verActiveAndInActivateResrcType(selenium,
								strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views , click on 'Create New
			 * View', scroll down to 'Select Resources' section Expected
			 * Result:Resource type 'RT' is not displayed.
			 */
			// 611415

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
				strFuncResult = objViews.vfyResrcTypePresOrNotPresInViewPge(
						selenium, strRTValue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views ,Click on 'Edit' link
			 * associated with V1 (view name),Navigate to select resources
			 * section Expected Result:Resource type 'RT' is not displayed.
			 */
			// 611955

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
				strFuncResult = objViews.vfyResrcTypePresOrNotPresInViewPge(
						selenium, strRTValue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Setup Expected
			 * Result:'Event Template List' screen is displayed.
			 */
			// 611420

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New Event Template' button.
			 * Expected Result:'Create New Event Template' screen is displayed.
			 * <br> <br>Under 'Resource Type' section 'RT' is not displayed.
			 */
			// 611421

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkRTInCreatEventTemplatePage(
						selenium, strRTValue[1], false, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event Setup,Click on 'Edit'
			 * link next to event template 'ET'. Expected Result:'RT' is
			 * displayed under 'Resource Type' section on 'Edit Event Template'
			 * screen
			 */
			// 611956

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkRTInCreatEventTemplatePage(
						selenium, strRTValue[1], false, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Users Expected Result:'User
			 * List' screen is displayed.
			 */
			// 611423

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Resource Type' drop down under search
			 * criteria. Expected Result:Resource type 'RT' is not displayed.
			 */
			// 611424

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.vrfyRTPresOrNotInUserPge(
						selenium, strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Regional Info >> Users Expected
			 * Result:'User List' screen is displayed.
			 */
			// 611425

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.navRegionalInfoUsrPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Resource Type' drop down under search
			 * criteria. Expected Result:Resource type 'RT' is not displayed.
			 */
			// 611426

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.vrfyRTPresOrNotInUserPge(
						selenium, strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> users Expected Result:'Users
			 * List' screen is displayed.
			 */
			// 611447

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit' link associated with user 'U1',
			 * navigate to 'Resource Right' section , select resource type drop
			 * down. Expected Result:Resource type 'RT' is not displayed.
			 */
			// 611448

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.vrfyRTInSearchCriteriaOfEditUserPage(selenium,
								strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 */
			// 611449

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Users' link associated with any resource.
			 * Expected Result:'Assign users to < Resource Name >' screen is
			 * displayed. <br> <br>Resource type 'RT' is not displayed under
			 * Resource Type drop down.
			 */
			// 611450

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToAssignUsersOFRes(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.vrfyRTInSearchCriteriaOfEditUserPage(selenium,
								strResrcTypName, false);
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
			gstrTCID = "FTS-108090";
			gstrTO = " Verify that inactive resource Type is not displayed on following screens:"
					+ "	a. Create/Edit view."
					+ "  b. Create/Edit event template. "
					+ "  c. Resource Type drop down while searching user/resource.";
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

	// end//testFTS108090//
	
//start//testFTS108092//
	/************************************************************************************************
	'Description	:Reactivate a Resource Type and verify that it is displayed on following screens: 
					  a. Create/Edit view.
					  b. Create/Edit event template.   
					  c. Resource Type drop down while searching user/resource.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/12/2013
	'Author			:Ajanta
	'------------------------------------------------------------------------------------------------
	'Modified Date				                                                        Modified By
	'Date					                                                            Name
	*************************************************************************************************/

	@Test
	public void testFTS108092() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Resources objResources = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		RegionalInfo objRegionalInfo = new RegionalInfo();

		try {
			gstrTCID = "108092"; // Test Case Id
			gstrTO = " Reactivate a Resource Type and verify that it is displayed on following screens: " +
					"a. Create/Edit view." +
					"b. Create/Edit event template.  " +
					" c. Resource Type drop down while searching user/resource.";//TO																																						// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strNSTValue = "Number";
			String statrNumTypeName = "rNST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[4];
			String[] strRsTypeValues = new String[2];

			String strResourceA = "AutoRs1_A" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[3];
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			String strStatTypDefn = "Automation";
			String strResrcTypName = "RT" + System.currentTimeMillis();
			String strState = "Texas";
			String strCountry = "Bell County";
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";
			String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
			String strUsrFulName1 = "FN" + strUserName1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strExternalId = "Exterid";
			String strStreetAddr = "Saddrs";
			String strCity = "Scity";
			String strZipCode = "11122";
			String strWebSite = "";
			String strTitle = "Title";
			String strContAddr = "Cadd";
			String strContPh1 = "";
			String strContPh2 = "";
			String strContFax = "";
			String strContEmail = "autoemr@qsgsoft.com";
			String AHAID = "";
			String strNotes = "Notes";
				/*
				* STEP :
				  Action:Preconditions:
		1. Status types 'ST1' is created.
		2. Resource type 'RT' is created selecting 'ST1'
		3. Resource 'RS' is created providing address and selecting 'RT'
		4. View V1' is created selecting 'ST1' & 'RS'
		5. Event template 'ET' is created selecting 'ST1' and 'RT'
		6. User 'U1' is created selecting following rights:
		a. Configure Region Views.
		b. Maintain Event Template.
		c. User-Setup User Accounts. 
		7. After creating view and event template deactivate the resource type 'RT'
		(Note: more then 15 users and resource are available in region)
				  Expected Result:No Expected Result
				*/
				//611465

			log4j.info("-----Precondtion for test case " + gstrTCID+ " Starts----------");
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

			// status type
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

			// Resource Type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrcTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[0] + "']");
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

			// Resources
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// /RSa
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResource_FillAllFields(
						seleniumPrecondition, strResourceA, strAbbrv,
						strResrcTypName, strStandResType, true, false, AHAID,
						strExternalId, false, strStreetAddr, strCity, strState,
						strZipCode, strCountry, strWebSite, strContFName,
						strContLName, strTitle, strContAddr, strContPh1,
						strContPh2, strContFax, strContEmail, strNotes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(seleniumPrecondition,
						strResourceA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(seleniumPrecondition, strResourceA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(
						seleniumPrecondition, strResourceA);

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
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { str_roleStatusTypeValues[0] };
				String[] strRSValue1 = { strRSValue[0] };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalue, false, strRSValue1);
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
				String[] strSTvalue = { str_roleStatusTypeValues[0] };
				String[] strRsTypeValue = { strRsTypeValues[0] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strRsTypeValue, strSTvalue);
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
			// User
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
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName1);
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
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// This need to check
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.VewUsrInfoNly");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
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

			// deactivate Resource type RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						seleniumPrecondition, strResrcTypName, false);
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
			log4j.info("---------------Test case execution" + gstrTCID
					+ " Starts----------");

		/*
		* STEP :
		  Action:Login as RegAdmin, navigate to 'Setup >> Resource Types' and select the check box 'include inactive resource types'
		  Expected Result:'RT' is displayed in the 'Resource Type List' screen.
		*/
		//611466
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.varDeactivatedRT(selenium,
						strResrcTypName, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Edit' link associated with resource type 'RT',select 'Active' check box and click on 'Save'
		  Expected Result:'RT' is displayed as 'Active' in the 'Resource Type List' screen.
		*/
		//611467
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						selenium, strResrcTypName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.varDeactivatedRT(selenium,
						strResrcTypName, false, false);
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
		* STEP :
		  Action:Login as user 'U1' ,Navigate to Setup >> Views
		  Expected Result:'Region Views List' screen is displayed.
          View 'V1' is listed under it.
		*/
		//611468
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Create New View',Navigate to select resources section
		  Expected Result:Resource type 'RT' and resource 'RS' are displayed.
		*/
		//611469
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
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, str_roleStatusTypeValues[0], statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Views, click on 'Edit' link associated with view 'V1'
		  Expected Result:'Edit View' screen is displayed.
		*/
		//611470

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
		/*
		* STEP :
		  Action:Navigate to Event >> Event Setup ,Click on 'Create New Event Template' button.
		  Expected Result:'Create New Event Template' screen is displayed.
          Under 'Resource Type' section 'RT' is displayed.
		*/
		//611471
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
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, str_roleStatusTypeValues[0], true,
						statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		* STEP :
		  Action:Navigate to Event >> Event Setup ,Click on 'Edit' link associated with event template 'ET'.
		  Expected Result:'Edit Event Template' screen is displayed.
          Under 'Resource Type' section 'RT' is displayed.
		*/
		//611472
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, str_roleStatusTypeValues[0], true,
						statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Setup >> Users,Click on 'Resource Type' drop down under search criteria.
		  Expected Result:Resource type 'RT' is displayed.
		*/
		//611473
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.vrfyRTInSearchCriteriaOfUser(
						selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/* 
		* STEP :
		  Action:Navigate to Regional Info >> Users
		  Expected Result:'User List' screen is displayed.
		*/
		//611474
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.navRegionalInfoUsrPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
		/*
		* STEP :
		  Action:Click on 'Resource Type' drop down under search criteria.
		  Expected Result:Resource type 'RT' is displayed.
		*/
		//611475
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.vrfyRTInSearchCriteriaOfUser(
						selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> users
		  Expected Result:'Users List' screen is displayed.
		*/
		//611476
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Edit' link associated with user 'U1', navigate to 'Resource Right' section , select resource type drop down.
		  Expected Result:Resource type 'RT' is displayed.
		*/
		//611477
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.vrfyRTInSearchCriteriaOfEditUserPage(selenium,
								strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Resources ,Click on 'Users' link associated with resource 'RS'.
		  Expected Result:'Assign users to < Resource Name >' screen is displayed.
          Resource type 'RT' is displayed under Resource Type drop down.
		*/
		//611478
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToAssignUsersOFRes(selenium,
						strResourceA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.vrfyRTInSearchCriteriaOfEditUserPage(selenium,
								strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Login as user 'U1' to mobile device, Navigate to Views >> V1
		  Expected Result:Resource Type 'RT' and resource 'RS' are displayed under view 'V1' screen
		*/
		//611959
			String strTestData[] = new String[20];
			try {

				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName1 + "/" + strInitPwd + "";
				strTestData[3] = statrNumTypeName;
				strTestData[4] = strViewName;
				strTestData[5] = "Verify from 15th step";
				strTestData[6] = strResourceA;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				strTestData[14] = "First Name " + strContFName + "/ Last name "
						+ strContLName + " ";
				strTestData[15] = strContAddr;
				strTestData[16] = strNotes;
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-108092";
			gstrTO = "Reactivate a Resource Type and verify that it is displayed on following screens: <br> a. Create/Edit view.<br>b. Create/Edit event template.   <br>    c. Resource Type drop down while searching user/resource.";
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

	// end//testFTS108092//
	

	//start//testFTS108097//
	/***************************************************************
	'Description		:Verify that deactivated resource type can be activated.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/1/2013
	'Author				:Suhas
	'---------------------------------------------------------------
	'Modified Date				               Modified By
	'Date					                   Name
	***************************************************************/

	@Test
	public void testFTS108097() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Forms objForms = new Forms();
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		RegionalInfo objRegionalInfo = new RegionalInfo();
		SearchUserByDiffCrteria objSearchUser = new SearchUserByDiffCrteria();
		General objGen = new General();
		EventBanner objEventBanner = new EventBanner();
		EventList objEventList = new EventList();
		Preferences objPreference = new Preferences();
		try {
			gstrTCID = "108097"; // Test Case Id
			gstrTO = " Verify that deactivated resource type can be activated.";// Test	// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// RT
			String strRTValue[] = new String[1];
			String strResrcTypName = "AutoRT_1" + strTimeText;

			// ST
			String strStatusTypeValues[] = new String[3];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String statTypeName3 = "AutoST_3" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValue = "Number";

			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";

			// RS
			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTimeText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strRSValue[] = new String[1];

			// Views
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";

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
			// ET
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";
			// EVE
			String strEveName = "EventName" + strTimeText;
			String strInfo = "Info";
			// Form
			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormActiv = "User Initiate & Other To Fill Out";
			String strComplFormDel = "User To Individual Resources";
			String strDescription = "Description";
			// Find Resource
			String strCategory = "(Any)";
			String strRegion = "(Any)";
			String strCityZipCd = "";

			/*
			 * STEP : Action:Preconditions: 1. Status types 'ST1','ST2','ST3'
			 * are created. 2. Resource type 'RT' is created selecting
			 * 'ST1','ST2','ST3' 3. Resource 'RS' is created providing address
			 * and selecting 'RT' 4. View V1' is created selecting
			 * 'ST1','ST2','ST3' & 'RS' 5. Event template 'ET' is created
			 * selecting 'ST1','ST2','ST3' and 'RT' 6. Event 'Eve' is created
			 * selecting 'ET' 7.User U1 has following rights: a. Configure
			 * Region Views. b. Maintain Event Template. c. User-Setup User
			 * Accounts. d. Role R1 to view and update status type ST1. e.
			 * 'Update Status' and 'View Resource' right on resource RS. f. Form
			 * - User may create and modify forms g. Maintain Events. 8. After
			 * creating 'View' and 'Event' deactivate resource type 'RT'
			 * Expected Result:No Expected Result
			 */
			// 611521

			/*
			 * STEP : Action:Login as user 'U1', navigate to 'Setup >> Resource
			 * Types' and select the check box 'include inactive resource types'
			 * Expected Result:'RT' is displayed in the 'Resource Type List'
			 * screen.
			 */
			// 611522

			log4j.info("~~~~~PRECONDITION  - " + gstrTCID
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
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeName1);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName2, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeName2);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST3

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName3, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeName3);

				if (strStatusTypeValues[2].compareTo("") != 0) {
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[2], true);
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
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
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
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, strContFName, strContLName, strState,
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
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName_1, strVewDescription, strViewType, true,
						false, strStatusTypeValues, false, strRSValue, false);
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

			// ET
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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strStatusTypeValues[0],
						strStatusTypeValues[1], strStatusTypeValues[2] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
			// EVE

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(
						seleniumPrecondition, strTempName, strEveName, strInfo,
						false);
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
				strFuncResult = objEventSetup.savAndVerifyEvent(
						seleniumPrecondition, strEveName);
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
				String[] updateRightValue = { strStatusTypeValues[0] };
				String[] strViewRightValue = { strStatusTypeValues[0],
						strStatusTypeValues[1], strStatusTypeValues[2] };
				strFuncResult = objRoles.CreateRoleWithAllFields(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, false);
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
					strFuncResult = "Failed to fetch Role value";
				}

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
				strFuncResult = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource, false, true, false,
						true);

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
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.VewUsrInfoNly");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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
			// deactivate Resource type RT

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						seleniumPrecondition, strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRECONDITION  - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Click on 'Edit' link associated with resource type
			 * 'RT',select 'Active' check box and click on 'Save' Expected
			 * Result:'RT' is displayed as 'Active' in the 'Resource Type List'
			 * screen.
			 */
			// 611523
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selectDesectIncludeInactiveRT(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.vrfyResTypePresentOrNotInRTList(selenium,
								strResrcTypName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						selenium, strResrcTypName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Views , click on 'Create New
			 * View' button. Expected Result:'Create New View' screen is
			 * displayed. <br> <br>Resource Type 'RT' and resource 'RS' are
			 * displayed under 'Select Resources' section.
			 */
			// 611525

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
				strFuncResult = objViews.vfyResrcTypePresOrNotPresInViewPge(
						selenium, strRTValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[name='resourceID'][value='"
						+ strRSValue[0] + "']";
				strFuncResult = objGen.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("Resource " + strResource
							+ "is displayed under 'Select Resources' section.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.cancelAndNavToViewListpage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Cancel' button on 'Create New View'
			 * screen , click on 'Edit' link associated with view 'V1' Expected
			 * Result:'Edit View' screen is displayed. <br> <br>Resource Type
			 * 'RT' and resource 'RS' are displayed under 'Select Resources'
			 * section.
			 */
			// 611526
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(selenium,
						strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.vfyResrcTypePresOrNotPresInViewPge(
						selenium, strRTValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[name='resourceID'][value='"
						+ strRSValue[0] + "']";
				strFuncResult = objGen.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("Resource " + strResource
							+ "is displayed under 'Select Resources' section.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> Map Expected Result:'Regional
			 * Map View' screen is displayed. <br> <br>Resource 'RS' is
			 * displayed under 'Find resource' drop down.
			 */
			// 611543
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verRSInFindResDropDown(selenium,
						strResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Users,Click on 'Resource Type'
			 * drop down under search criteria. Expected Result:Resource type
			 * 'RT' is displayed under resource types drop down.
			 */
			// 611529
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.vrfyRTPresOrNotInUserPge(
						selenium, strResrcTypName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> users , select resource name
			 * drop down under search criteria, provide resource 'RS' name in
			 * search field and click on 'Search' Expected Result:User 'U1' is
			 * not retrieved.
			 */
			// 612015
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUser.searchUserByDifCriteria(selenium,
						strResource, strByRole, strByResourceType,
						"Resource Name", strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUsrInUserListPage(
						selenium, strUserName_1, true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Resources ,Click on 'Users'
			 * link associated with resource 'RS'. Expected Result:'Assign users
			 * to < Resource Name >' screen is displayed. <br> <br>Resource type
			 * 'RT' is displayed under Resource Type drop down.
			 */
			// 611540
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

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//select[@id='tbl_association_RESOURCE_TYPE']/option[text()='"
						+ strResrcTypName + "']";
				strFuncResult = objGen.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("Resource Type  " + strResrcTypName
							+ "is displayed under Resource Type drop down. ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> users Expected Result:'Users
			 * List' screen is displayed.
			 */
			// 611538
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New User' button Expected
			 * Result:Resource 'RS' is displayed under 'Resource Rights' section
			 * on 'Create New User' screen.
			 */
			// 612041
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToCreateUserPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyResourceInResRights(
						selenium, strResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Cancel' on 'Create New User' screen
			 * ,Click on 'Edit' link associated with user 'U1', navigate to
			 * 'Resource Right' section , select resource type drop down
			 * Expected Result:Resource type 'RT' is displayed under resource
			 * type drop down.
			 */
			// 611539
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.cancelAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//select[@id='tbl_association_RESOURCE_TYPE']/option[text()='"
						+ strResrcTypName + "']";
				strFuncResult = objGen.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("Resource Type  " + strResrcTypName
							+ "is displayed under Resource Type drop down. ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Regional Info >> users , select
			 * resource name drop down under search criteria, provide resource
			 * 'RS' name in search field and click on 'Search' Expected
			 * Result:User 'U1' is not retrieved.
			 */
			// 612017
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.navRegionalInfoUsrPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUser.searchUserByDifCriteria(selenium,
						strResource, strByRole, strByResourceType,
						"Resource Name", strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUsrInUserListPage(
						selenium, strUserName_1, true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Regional Info >> Find resources,
			 * provide resource 'RS' name in search criteria and click on
			 * 'Search' Expected Result:Resource 'RS' is retrieved.
			 */
			// 611533

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo
						.navToRegionalInfoFindResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreference.RetriveResourceInAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'search' link displayed at the top right
			 * corner of the screen and provide resource 'RS' name in search
			 * criteria and click on 'Search' Expected Result:Resource 'RS' is
			 * retrieved.
			 */
			// 611534

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreference
						.navToFindResPageBySearchLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreference.RetriveResourceInAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event Setup,Click on 'Create
			 * New Event Template' button. Expected Result:'Create New Event
			 * Template' screen is displayed. <br> <br>Resource Type 'RT' is
			 * listed under 'Resource Types' section.
			 */
			// 611527
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

				strFuncResult = objEventSetup.chkRTInCreatEventTemplatePage(
						selenium, strRTValue[0], true, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Cancel' button on 'Create New Event
			 * Template' screen ,Click on 'Edit' link associated with event
			 * template 'ET' Expected Result:'Edit Event Template' screen is
			 * displayed. <br> <br>Resource Type 'RT' is listed under 'Resource
			 * Types' section.
			 */
			// 611528
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.cancelAndNavToEventTemplatePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkRTInEditEventTemplatePage(
						selenium, strRTValue[0], true, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event Management, Click on
			 * 'Create New Event' and select event template 'ET' Expected
			 * Result:'Create New Event' screen is displayed. <br> <br>Resource
			 * section is displayed,resource 'RS' is displayed under it.
			 */
			// 611532

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[0], true, strResource, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Cancel' button on 'Create New Event'
			 * screen , Click on 'Edit' link associated with event 'EVE'
			 * Expected Result:'Edit Event' screen is displayed. <br>
			 * <br>Resource section is displayed and resource 'RS' is listed
			 * under it and check box corresponding to it is checked.
			 */
			// 611531
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.cancelAndNavToEventManagementScreen(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEvent(selenium, strEveName,
						"", "", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[0], true, strResource, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkRSselOrNotInEditEvntPage(
						selenium, strRSValue[0], true, strResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on event banner of event 'EVE' Expected
			 * Result:Resource 'RS' is displayed under 'RT' with status types
			 * 'ST1','ST2','ST3'
			 */
			// 611541

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventBanner.clickEventWithFocusOnEveBanner(
						selenium, strEveName);
			} catch (AssertionError Ae) {
				;
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Form >> Configure form , Click on
			 * 'Create New Form Template' button. Expected Result:'Create New
			 * Form Template' screen is displayed.
			 */
			// 611535

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2,
						statTypeName3 };
				strFuncResult = objEventList.checkOnlyDetailsInEventBanner(
						selenium, strEveName, strResrcTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				;
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Create form template selecting 'User to initiate
			 * and others to fill out' for 'Form activation' and Users to
			 * individual resources for 'Completed form Delivery' field click on
			 * next. Expected Result:'Users to fill out Form' screen is
			 * displayed.
			 */
			// 611536
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.filAllFieldsInCreatNewFormForHimORHer(
						selenium, strFormTempTitleOF, strDescription,
						strFormActiv, strComplFormDel, true, true, true, false,
						true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Next' on 'Users to fill out Form' screen
			 * Expected Result:'Resources to Fill Form' screen is displayed.
			 * <br> <br>Resource 'RS' is displayed under it.
			 */
			// 611537
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForForm(selenium, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
						+ strResource + "']";
				strFuncResult = objGen.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("Resource"
							+ strResource
							+ "is displayed under 'Resources to Fill Form' screen ");
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
			gstrTCID = "FTS-108097";
			gstrTO = "Verify that deactivated resource type can be activated.";
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

	// end//testFTS108097//
	

	//start//testFTS108091//
	/***************************************************************
	'Description		:Deactivated 'Resource Type' is not displayed on following screens: 
						 a. Create/Edit view.  
						 b. Create/Edit event template.
						 c. Resource Type drop down while searching user/resource.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/4/2013
	'Author				:Suhas
	'---------------------------------------------------------------
	'Modified Date				               Modified By
	'Date					                   Name
	***************************************************************/

	@Test
	public void testFTS108091() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		RegionalInfo objRegionalInfo = new RegionalInfo();
		General objGen = new General();
		try {
			gstrTCID = "108091"; // Test Case Id
			gstrTO = " Deactivated 'Resource Type' is not displayed on following screens: "
					+ "a. Create/Edit view."
					+ "b. Create/Edit event template."
					+ "c. Resource Type drop down while searching user/resource.";// Test
																					// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValue = "Number";
			// RT
			String strRTValue[] = new String[1];
			String strResrcTypName = "AutoRT_1" + strTimeText;
			// RS
			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTimeText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strRSValue[] = new String[1];

			// Views
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";

			// ET
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";
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
			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";

			/*
			 * STEP : Action:Preconditions: <br> <br>1. Status types 'ST1' is
			 * created. <br>2. Resource type 'RT' is created selecting 'ST1'
			 * <br>3. Resource 'RS' is created providing address and selecting
			 * 'RT' <br>4. View V1' is created selecting 'ST1' & 'RS' <br>5.
			 * Event template 'ET' is created selecting 'ST1' and 'RT' <br>6.
			 * User U1 is created selecting following rights: <br> <br>a.
			 * 'Update Status' and 'View Resource' right on resource RS. <br>b.
			 * Role R1 to view and update right on ST1. <br>c. Maintain Event
			 * Template. <br>d. Configure Region Views. <br>e. User - View User
			 * Information Only. Expected Result:No Expected Result
			 */
			// 611427
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
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
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeName1);

				if (strStatusTypeValues[0].compareTo("") != 0) {
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[0], true);
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
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
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
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, strContFName, strContLName, strState,
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
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName_1, strVewDescription, strViewType, true,
						false, strStatusTypeValues, false, strRSValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.SaveAndVerifyView(
						seleniumPrecondition, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ET
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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
				String[] updateRightValue = { strStatusTypeValues[0] };
				String[] strViewRightValue = { strStatusTypeValues[0] };
				strFuncResult = objRoles.CreateRoleWithAllFields(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
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
					strFuncResult = "Failed to fetch Role value";
				}

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
				strFuncResult = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource, false, true, false,
						true);

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
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.VewUsrInfoNly");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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
			//Logout
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			/*
			 * STEP : Action:Login as RegAdmin, navigate to 'Setup >> Resource
			 * Types' and deactivate the resource type 'RT' Expected Result:'RT'
			 * is deactivated and is not displayed in the 'Resource Type List'
			 * screen. (i.e. when 'include inactive resource types' is not
			 * checked)
			 */
			// 611428
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
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						selenium, strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to 'View >> V1' Expected Result:Resource
			 * type 'RT' and resource 'RS' are not displayed. <br> <br>Message
			 * stating 'There are no resources to display in this view' is
			 * displayed.
			 */
			// 611429

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResInUserViewFalseCond(
						selenium, strViewName_1, strResrcTypName, strRS,
						strRTValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views , click on 'Create New
			 * View' button. Expected Result:'Create New View' screen is
			 * displayed. <br> <br>Resource Type 'RT' and resource 'RS' are not
			 * displayed under 'Select Resources' section.
			 */
			// 611436
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
				strFuncResult = objViews.vfyResrcTypePresOrNotPresInViewPge(
						selenium, strRTValue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[name='resourceID'][value='"
						+ strRSValue[0] + "']";
				strFuncResult = objGen.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("Element NOT Present")) {
					log4j.info("Resource "
							+ strResource
							+ "is NOT displayed under 'Select Resources' section.");
					strFuncResult = "";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views , click on 'Edit' link
			 * associated with view 'V1' Expected Result:'Edit View' screen is
			 * displayed. <br> <br>Resource Type 'RT' and resource 'RS' are not
			 * displayed under 'Select Resources' section.
			 */
			// 611437
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
				strFuncResult = objViews.vfyResrcTypePresOrNotPresInViewPge(
						selenium, strRTValue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[name='resourceID'][value='"
						+ strRSValue[0] + "']";
				strFuncResult = objGen.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("Element NOT Present")) {
					log4j.info("Resource "
							+ strResource
							+ "is NOT displayed under 'Select Resources' section.");
					strFuncResult = "";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event Setup Expected
			 * Result:'Event Template List' screen is displayed. <br> <br>Event
			 * template 'ET' is listed under it.
			 */
			// 611438
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.verETempPresentOrNot(selenium,
						strTempName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New Event Template' button.
			 * Expected Result:'Create New Event Template' screen is displayed.
			 * <br> <br>Resource Type 'RT' is not listed under 'Resource Types'
			 * section.
			 */
			// 611439
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.chkRTInCreatEventTemplatePage(
						selenium, strRTValue[0], false, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event Setup ,Click on 'Edit'
			 * link associated with event template 'ET' Expected Result:'Edit
			 * Event Template' screen is displayed. <br> <br>Resource Type 'RT'
			 * is not listed under 'Resource Types' section.
			 */
			// 611440
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkRTInEditEventTemplatePage(
						selenium, strRTValue[0], false, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Users Expected Result:'User
			 * List' screen is displayed.
			 */
			// 611441

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUsrInUserListPage(
						selenium, strUserName_1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Resource Type' drop down under search
			 * criteria. Expected Result:Resource type 'RT' is not displayed.
			 * <br> <br>(Only active resource types are listed under drop down.)
			 */
			// 611442
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.vrfyRTPresOrNotInUserPge(
						selenium, strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit' link associated with user 'U1',
			 * navigate to 'Resource Right' section , select resource type drop
			 * down. Expected Result:Resource type 'RT' is not displayed. <br>
			 * <br>Only active resource types are listed under drop down.
			 */
			// 611444

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Regional Info >> Users Expected
			 * Result:'User List' screen is displayed.
			 */
			// 611982

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.navRegionalInfoUsrPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Resource Type' drop down under search
			 * criteria. Expected Result:Resource type 'RT' is not displayed.
			 */
			// 611983
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.vrfyRTPresOrNotInUserPge(
						selenium, strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 */
			// 611445
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Users' link associated with resource
			 * 'RS'. Expected Result:'Assign users to < Resource Name >' screen
			 * is displayed. <br> <br>Resource type 'RT' is not displayed under
			 * Resource Type drop down.
			 */
			// 611446
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@class='displayTable striped border sortable']/tbody/tr[1]/td/a[text()='Users']";
				strFuncResult = objGen.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					try {
						selenium.click("//table[@class='displayTable striped border sortable']/tbody/tr[1]/td/a[text()='Users']");
						selenium.waitForPageToLoad(gstrTimeOut);
						log4j.info("'Assign users to < Resource Name >' screen is displayed. ");
					} catch (AssertionError Ae) {
						gstrReason = "Failed to display Assign users to < Resource Name >' screen"
								+ Ae;
					}

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//select[@id='tbl_association_RESOURCE_TYPE']/option[text()='"
						+ strResrcTypName + "']";
				strFuncResult = objGen.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("Element NOT Present")) {
					log4j.info("Resource Type  "
							+ strResrcTypName
							+ "is NOT displayed under Resource Type drop down. ");
					strFuncResult = "";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user 'U1' to mobile device and navigate to
			 * Views >> V1 Expected Result:Resource 'RS' is not displayed on
			 * view 'V1' screen.
			 */
			// 611957

			String strTestData[] = new String[20];
			try {

				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName_1 + "/" + strInitPwd + "";
				strTestData[3] = statTypeName1;
				strTestData[4] = strViewName_1;
				strTestData[5] = "Verify from 16th step";
				strTestData[6] = strResource;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");				
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-108091";
			gstrTO = "Deactivated 'Resource Type' is not displayed on following screens:"
					+ " b. Create/Edit event template."
					+ "c. Resource Type drop down while searching user/resource.";
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

	// end//testFTS108091//

}
