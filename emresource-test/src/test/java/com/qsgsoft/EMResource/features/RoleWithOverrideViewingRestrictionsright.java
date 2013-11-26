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
' Description		:This class contains test cases from requirement
' Requirement Group	:Setting up Roles 
' Requirement 	    : Role with 'Override Viewing restrictions� right
� Product		    :EMResource v3.18
' Date			    :9th/Nov/2012
'*******************************************************************/

public class RoleWithOverrideViewingRestrictionsright {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.RoleWithOverrideViewingRestrictionsright");
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
	Selenium selenium,seleniumFirefox,seleniumPrecondition;
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

		try {
			selenium.close();
		} catch (Exception e) {

		}
		try{
			seleniumFirefox.close();
		}catch(Exception e){
			
		}
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
		}
		selenium.stop();	
		seleniumFirefox.stop();
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

		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
	/***************************************************************
	'Description		:Verify that user can be provided a role in which the only right
	                     'Override Viewing restrictions' right is selected
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :11/8/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS81344() throws Exception {

		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		EventList objEventList = new EventList();
		MobileView objMobileView = new MobileView();
		Roles objRole = new Roles();
		String strFuncResult = "";
		try {
			gstrTCID = "81344"; // Test Case Id
			gstrTO = " Verify that user can be provided a role in which the only"
					+ " right 'Override Viewing restrictions' right is selected"; // Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTmText = dts.getCurrentDate("HHmm");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String strStatusTypeValues[] = new String[4];
			String statTypeNameNST = "AutoNST_" + strTimeText;
			String statTypeNameTST = "AutoTST_" + strTimeText;
			String statTypeNameSST = "AutoSST_" + strTimeText;
			String statTypeNameMST = "AutoMST_" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "RT" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strRSValue[] = new String[1];
			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strTempName = "Autotmp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "Automation";
			// USER
			String strUserName1 = "AutoU_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserName1;
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// sec data
			String strSection1 = "AB1_" + strTimeText;
			// Role
			String strRoleName1 = "AutoR1_" + strTimeText;
			String[] strRoleValue = new String[1];

			/*
			 * STEP : Action:Precondition: 1. Test user has created status type
			 * NST, MST, TST, SST which is associated to resource type 'RT' 2.
			 * Resource 'RS' is created under 'RT' providing address. 3. View
			 * 'V1' is created selecting status types mentioned above along with
			 * resource RS. 4. Section 'S1' is created selecting status types
			 * mentioned above. 5. Event template 'ET' is created selecting 'RT'
			 * along with all the status types mentioned above. 6. Event 'Eve1'
			 * is created under 'ET' selecting 'RS' 7. User 'U1' is created
			 * without providing any view right on resource RS. Expected
			 * Result:No Expected Result
			 */
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
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

			// ST2
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeNameNST,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeNameNST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeNameNST);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST3
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeNameTST,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeNameTST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeNameTST);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST4
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Saturation Score";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeNameSST,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeNameSST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeNameSST);

				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST5
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeNameMST,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeNameMST);

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
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[3], true);
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
				String strState = "Alabama";
				String strCountry = "Barbour County";
				String strStandResType = "Aeromedical";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrcTypName, "FN",
						"LN", strState, strCountry, strStandResType);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { strStatusTypeValues[0],
						strStatusTypeValues[1], strStatusTypeValues[2],
						strStatusTypeValues[3] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// event template

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
				String[] strStatusTypeval = { strStatusTypeValues[0],
						strStatusTypeValues[1], strStatusTypeValues[2],
						strStatusTypeValues[3] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// event
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
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Section1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumFirefox,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
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
				String[] strStatTypeArr = { statTypeNameNST, statTypeNameTST,
						statTypeNameSST, statTypeNameMST };
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strStatTypeArr, strSection1, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			/*
			 * STEP : Action:Login as RegAdmin and create a role 'R1' selecting
			 * 'Override viewing restrictions' right Expected Result:Role 'R1'
			 * is created and is listed in the 'Roles List' screen.
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
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.rolesMandatoryFlds(selenium,
						strRoleName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.OverrideViewingRestrictions");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.savAndVerifyRoles(selenium,
						strRoleName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName1);

				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Edit user 'U1' and assign role 'R1' to it. Expected
			 * Result:No Expected Result
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
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(selenium);
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
			 * STEP : Action:Login as user 'U1' and navigate to 'View >> V1'
			 * Expected Result:Status types NST, MST, TST, SST are displayed
			 * under resource type 'RT'
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
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
				String[] strStatTypeArr = { statTypeNameMST, statTypeNameNST,
						statTypeNameSST, statTypeNameTST };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on resource 'RS' Expected Result:'View
			 * Resource Detail' screen is displayed. Status types NST, MST, TST
			 * and SST are displayed under section 'S1'
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeNameNST, statTypeNameTST,
						statTypeNameSST, statTypeNameMST };
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to 'View >> Map', select resource 'RS'
			 * from the 'Find Resources' drop down. Expected Result:All the
			 * status types are displayed in the 'Resource Info' pop up window.
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strStatTypeArr = { statTypeNameMST, statTypeNameNST,
						statTypeNameSST, statTypeNameTST };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strStatTypeArr, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the event banner 'Eve' Expected Result:All
			 * the status types are displayed under resource type 'RT'
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeNameNST, statTypeNameTST,
						statTypeNameSST, statTypeNameMST };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select the 'Mobile View' option on the web.
			 * Navigate to 'Resources >> V1 >> RS' Expected Result:All the
			 * status types are displayed in the 'Resource Detail' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.navToViewsInMobileView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.navToUserViewsInMob(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeNameNST, statTypeNameTST,
						statTypeNameSST, statTypeNameMST };
				strFuncResult = objMobileView.checkResouceDetail(selenium,
						strResource, strSection1, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String strTestData[] = new String[10];
			try {

				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName1 + "/" + strInitPwd;
				strTestData[3] = statTypeNameNST + "/" + statTypeNameTST + "/"
						+ statTypeNameSST + "/" + statTypeNameMST;
				strTestData[4] = strViewName;
				strTestData[5] = "Verify 9th step";
				strTestData[6] = strResource;
				strTestData[7] = strSection1;
				strTestData[8] = strEveName;
				strTestData[9] = strRegn;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-81344";
			gstrTO = "Verify that user can be provided a role in which the only "
					+ "right 'Override Viewing restrictions' right is selected";
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
			selenium.selectWindow("");
			strFuncResult = objLogin.logout(selenium);

			try {
				assertEquals("", strFuncResult);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				gstrResult = "FAIL";
			}
		}
	}
	
	/***************************************************************
	'Description		:Verify that user with 'Override Viewing restrictions' right can view all the refined status type on <br>
	1.	Region View screen  <br>
	2.	View Resource Detail screen  <br>
	3.	Map screen  <br>
	4.	Event Status screen  <br>
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:11/19/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS104569() throws Exception {

		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		EventList objEventList = new EventList();
		MobileView objMobileView = new MobileView();
		Roles objRole = new Roles();
		String strFuncResult = "";
		try {
			gstrTCID = "104569"; // Test Case Id
			gstrTO = "Verify that user with 'Override Viewing restrictions' right " +
					"can view all the refined status type on"
					+ "1.	Region View screen"
					+ "2.	View Resource Detail screen"
					+ "3.	Map screen"
					+ "4.	Event Status screen";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTmText = dts.getCurrentDate("HHmm");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeNameNST1 = "AutoNST_1" + strTimeText;
			String statTypeNameNST2 = "AutoNST_2" + strTimeText;

			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName1 = "AutoRT_1" + strTimeText;
			String strResrcTypName2 = "AutoRT_2" + strTimeText;
			String strRsTypeValues[] = new String[2];

			// Resource
			String strResource1 = "AutoRS_1" + strTimeText;
			String strResource2 = "AutoRS_2" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strRSValue[] = new String[2];
			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strTempName = "Autotmp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "Automation";
			// USER
			String strUserName1 = "AutoU_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserName1;
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// sec data
			String strSection1 = "AB1_" + strTimeText;
			// Role
			String strRoleName1 = "AutoR1_" + strTimeText;
			String[] strRoleValue = new String[1];
			/*
			 * STEP : Action:Precondition: 1. Test user has created status
			 * typeST1 and ST2 which is associated to resource type 'RT1' and
			 * 'RT2' 2. Resource 'RS1' is created under 'RT1' and resource 'RS2'
			 * is created under 'RT2' providing address. 3. View 'V1' is created
			 * selecting ST1, ST2, RS1 and RS2. 4. Section 'S1' is created
			 * selecting RS1, ST1 and ST2. Section 'S2' is created selecting
			 * RS2, ST1 and ST2. 5. Event template 'ET' is created selecting
			 * 'RT1' and RT2 along with the status types mentioned above. 6.
			 * Event 'Eve1' is created under 'ET' selecting RS1 and RS2 7. User
			 * 'U1' is created without providing any view right on resource RS1
			 * and RS2. Expected Result:No Expected Result
			 */

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
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

			// ST1
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeNameNST1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeNameNST1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeNameNST1);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeNameNST2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeNameNST2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeNameNST2);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName1, strStatusTypeValues[0]);
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
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName1);

				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName2, strStatusTypeValues[0]);
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
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[1] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName2);

				if (strRsTypeValues[1].compareTo("") != 0) {
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
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				String strStandResType = "Aeromedical";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv, strResrcTypName1,
						"FN", "LN", strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
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

			// RS2

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
				String strStandResType = "Aeromedical";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource2, strAbbrv, strResrcTypName2,
						"FN", "LN", strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
						strResource2);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

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
				String[] strSTvalue = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// event template

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
				String[] strResTypeVal = { strRsTypeValues[0],
						strRsTypeValues[1] };
				String[] strStatusTypeval = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// event
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
						seleniumPrecondition, strResource1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResource2, true, false);
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
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0], false, false,
						false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource2, strRSValue[1], false, false,
						false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Section1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumFirefox,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
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
				String[] strStatTypeArr = { statTypeNameNST1, statTypeNameNST2 };
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strStatTypeArr, strSection1, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			/*
			 * STEP : Action:Login as RegAdmin and create a role 'R1' selecting
			 * 'Override viewing restrictions' right Expected Result:Role 'R1'
			 * is created and is listed in the 'Roles List' screen.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.rolesMandatoryFlds(selenium,
						strRoleName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.OverrideViewingRestrictions");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.savAndVerifyRoles(selenium,
						strRoleName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName1);

				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Edit user 'U1' and assign role 'R1' to it. For
			 * resource 'RS1' select 'Refine' and deselect the check box for
			 * 'ST1' and save the changes. Expected Result:No Expected Result
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
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strStatusTypeValues[0], false);
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
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(selenium);
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
			 * STEP : Action:Login as user 'U1' and navigate to 'View >> V1'
			 * Expected Result:Status type 'ST1' and 'ST2' are displayed under
			 * resource type 'RT1' and 'RT2'
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
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
				String[] strStatType = { statTypeNameNST1, statTypeNameNST2 };
				String[] strResource = { strResource1 };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrcTypName1, strResource, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statTypeNameNST1, statTypeNameNST2 };
				String[] strResource = { strResource2 };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrcTypName2, strResource, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource 'RS' Expected Result:'View
			 * Resource Detail' screen is displayed. Status type 'ST1' and 'ST2'
			 * are displayed under section 'S1' and 'S2'
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeNameNST1, statTypeNameNST2 };
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to 'View >> Map', select resource 'RS1'
			 * from the 'Find Resources' drop down. Expected Result:Status types
			 * ST1 and ST2 are displayed in the 'Resource Info' pop up window.
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strStatTypeArr = { statTypeNameNST1, statTypeNameNST2 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strStatTypeArr, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select resource 'RS2' from the 'Find Resources'
			 * drop down. Expected Result:Status types ST1 and ST2 are displayed
			 * in the 'Resource Info' pop up window.
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strStatTypeArr = { statTypeNameNST1, statTypeNameNST2 };
				strFuncResult = objViewMap.verSTInResourcePopup(selenium,
						strResource2, strEventStatType, strStatTypeArr, false,
						true);
				gstrReason = strFuncResult;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on the event banner 'Eve' Expected
			 * Result:Status type 'ST1' and 'ST2' are displayed under resource
			 * type 'RT1' and 'RT2'
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeNameNST1, statTypeNameNST2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName1, strResource1,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeNameNST1, statTypeNameNST2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName2, strResource2,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select the 'Mobile View' option on the web.
			 * Navigate to 'Resources >> V1 >> RS' Expected Result:Status type
			 * 'ST1' and 'ST2' are displayed under resource type 'RT1' and 'RT2'
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.navToViewsInMobileView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.navToUserViewsInMob(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeNameNST1, statTypeNameNST2 };
				strFuncResult = objMobileView.checkResouceDetail(selenium,
						strResource1, strSection1, strStatTypeArr);
				// click on back
				selenium.click("link=Back");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeNameNST1, statTypeNameNST2 };
				strFuncResult = objMobileView.checkResouceDetail(selenium,
						strResource2, strSection1, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String strTestData[] = new String[10];
			try {

				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName1 + "/" + strInitPwd;
				strTestData[3] = statTypeNameNST1 + "/" + statTypeNameNST2;
				strTestData[4] = strViewName;
				strTestData[5] = "Verify 10th step";
				strTestData[6] = strResource1 + "/" + strResource2;
				strTestData[7] = strSection1;
				strTestData[8] = strEveName;
				strTestData[9] = strRegn;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-104569";
			gstrTO = "Verify that user with 'Override Viewing restrictions' right can"
					+ " view all the refined status type on"
					+ "1.	Region View screen"
					+ "2.	View Resource Detail screen"
					+ "3.	Map screen"
					+ "4.	Event Status screen";// Test Objective
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
			selenium.selectWindow("");
			strFuncResult = objLogin.logout(selenium);

			try {
				assertEquals("", strFuncResult);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				gstrResult = "FAIL";
			}
		}
	}

}
