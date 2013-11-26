package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;

import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.MobileView;
import com.qsgsoft.EMResource.shared.ResourceTypes;
import com.qsgsoft.EMResource.shared.Resources;
import com.qsgsoft.EMResource.shared.Roles;
import com.qsgsoft.EMResource.shared.StatusTypes;
import com.qsgsoft.EMResource.shared.ViewMap;
import com.qsgsoft.EMResource.shared.Views;
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
' Requirement		:Create resource type
' Requirement Group	:Setting up Resource types  
� Product		    :EMResource v3.19
' Date			    :4/June/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class CreateResourceType {
	
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.CreateResourceType");
	static{
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
	

   /****************************************************************************************************************
	* This function is called the setup() function which is executed before every test.
	*
	* The function will take care of creating a new selenium session for every test
	*
    ****************************************************************************************************************/

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

		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
		selenium.start();
		selenium.windowMaximize();

		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}


    /****************************************************************************************************************
    * This function is called the teardown() function which is executed after every test.
	* The function will take care of stopping the selenium session for every test and writing the execution
	* result of the test. 
	*
    ****************************************************************************************************************/

		@After
		public void tearDown() throws Exception {
			
		selenium.close();
		selenium.stop();
		
		seleniumFirefox.stop();

		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
		try {
			selenium.close();
		} catch (Exception e) {

		}
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
	'Description		:Verify that a resource type can be created.
	'Precondition		:Status type ST is created in region RG1. 
	'Arguments		    :None
	'Returns		    :None
	'Date			    :4/June/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				                        Modified By
	'Date					                            Name
	***************************************************************/
		
	@Test
	public void testBQS69388() throws Exception {
		try {

			Login objLogin = new Login();
			ResourceTypes objResourceTypes = new ResourceTypes();
			StatusTypes objST = new StatusTypes();
			gstrTCID = "69388"; // Test Case Id
			gstrTO = " Verify that a resource type can be created.";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrReason="";
			String strFuncResult = "";
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// Data for creating ResourceType
			String strResrcTypName = "AutoRST_" + strTimeText;

			String strStatType = "AutoSt_" + strTimeText;

			String strTxtStatTypeValue = "Number";

			String strTxtStatTypDefn = "Auto";
			String[] strST = new String[1];

			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);
			/*
			 * STEP : Action:Login as RegAdmin, navigate to Setup >> Resource
			 * Type Expected Result:'Resource Type List' screen is displayed.
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

			// Number ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTxtStatTypeValue, strStatType, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			
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

			/*
			 * STEP : Action:Click on 'Create New Resource Type' button Expected
			 * Result:'Create New Resource Type' page is displayed. .
			 */
			/*
			 * STEP : Action:Enter mandatory data, select status type 'ST' and
			 * click on 'Save'. Expected Result:Resource Type is listed on the
			 * 'Resource Type List' page under appropriate column header.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName, strST[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69380";
			gstrTO = "Verify that a view can be edited from the view screen.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

	
	/***************************************************************
	'Description		:Verify that a Multi status type can be selected as the default status type of a resource type.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:18-09-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS69390() throws Exception {
		try {
			gstrTCID = "69390";
			gstrTO = " Verify that a Multi status type can be selected "
					+ "as the default status type of a resource type."; // Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName, strLoginPassword, strRegn, strStatusTypeValue, strStatTypDefn, strStatusName1, strStatusName2, strStatTypeColor, strRoleName, strUserName, strInitPwd, strConfirmPwd, strUsrFulName, strRoleValue = "", strResrctTypName, strResName, strRSAbbr, strFName, strLName, strState, strCountry, strStdRT, strViewName, strVewDescription, strViewType, strSection;
			boolean blnSav, blnViewRight, blnUpdateRight, blnSave, blnselectRole, blnVisibleToAllUsers, blnShowAllStatusTypes, blnShowAllResourceTypes, blnCreateSec, blnAssocWith, blnUpdStat, blnRunReport, blnViewRes;

			String statTypeName;
			String strFuncResult = "";

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			Roles objRoles = new Roles();
			CreateUsers objCreateUsers = new CreateUsers();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			Views objViews = new Views();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strSTValue[] = new String[1];
			String strStatVal[] = new String[2];

			strUserName = "AutoUsr" + System.currentTimeMillis();
			strInitPwd = rdExcel.readData("Login", 4, 2);
			strConfirmPwd = rdExcel.readData("Login", 4, 2);
			strUsrFulName = strUserName;
			strResrctTypName = "AutoRt_" + strTimeText;
			strRoleName = "AutoR_" + strTimeText;
			strSection = "AB_" + strTimeText;

			// Search criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			/*
			 * STEP : Action:Precondition: <br>1. Multi status type 'MST' is
			 * created with Status 'S1' under it where color is selected as
			 * 'Green'. <br>2. User U1 is created with role R1 to view and
			 * update MST. Expected Result:No Expected Result
			 */
			// 415423
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			strRegn = rdExcel.readData("Login", 3, 4);

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
			statTypeName = "A1_" + strTimeText;
			strStatusTypeValue = "Multi";
			strStatTypDefn = "AutoTest";
			blnSav = true;
			String strArStatType[] = { statTypeName };

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, blnSav);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strStatusName1 = "Sta" + strTimeText;
			strStatusTypeValue = "Multi";
			strStatTypeColor = "Green";
			blnSav = true;

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName, strStatusName1,
						strStatusTypeValue, strStatTypeColor, blnSav);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strStatusName2 = "Stb" + strTimeText;
			strStatusTypeValue = "Multi";
			strStatTypeColor = "Green";
			blnSav = true;

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName, strStatusName2,
						strStatusTypeValue, strStatTypeColor, blnSav);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatVal[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatVal[1] = strStatValue;
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

			String[][] strRoleRights = {};
			blnViewRight = true;
			blnUpdateRight = true;
			blnSave = true;

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTValue, blnViewRight,
						strSTValue, blnUpdateRight, blnSave);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
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

			blnselectRole = true;

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, blnselectRole);
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
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			/*
			 * STEP : Action:Login as RegAdmin, navigate to 'Setup >> Resource
			 * Type' Expected Result:'Resource Type List' screen is displayed.
			 */
			// 415424

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

			/*
			 * STEP : Action:Click on 'Create New Resource Type', provide
			 * mandatory data('RT' as name), select check box 'MST' status type
			 * and select 'MST' from 'Default Status Type' drop down. <br>
			 * <br>Click on Save Expected Result:'RT' is listed on the 'Resource
			 * Type List' screen.
			 */
			// 415425

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						selenium, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTValue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.selectDefaultSTInCreateResType(selenium, statTypeName);
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
			 * STEP : Action:Navigate to Setup >> Resources, click on 'Create
			 * New Resource', provide mandatory data('RS' as name), provide
			 * address, select resource type 'RT' under Resource Type drop down
			 * and click on 'Save'. Expected Result:Resource RS is listed on
			 * 'Resource List' screen
			 */
			// 415434

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strFName = "qsg";
			strLName = "auto";
			strState = "Rhode Island";
			strCountry = "Newport County";
			strStdRT = "Aeromedical";
			strResName = "AutoRs_" + strTimeText;
			strRSAbbr = "Rs";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResName, strRSAbbr, strResrctTypName,
						strFName, strLName, strState, strCountry, strStdRT);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strRSValue = new String[1];
			try {
				assertEquals("", strFuncResult);
				String strResVal = objResources.fetchResValueInResList(
						selenium, strResName);

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
			 * STEP : Action:Navigate to Setup >> View,provide name as 'V1'
			 * select 'MST' and 'RS' under appropriate sections and click on
			 * 'Save'. Expected Result:V1 is listed on 'Regions View List'
			 * screen.
			 */
			// 415435

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strViewType = "Resource (Resources and status types as rows. Status, comments and timestamps as columns.)";
			blnVisibleToAllUsers = true;
			blnShowAllStatusTypes = false;
			blnShowAllResourceTypes = false;
			strVewDescription = "";
			strViewName = "AutoV_" + strTimeText;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, blnVisibleToAllUsers,
						blnShowAllStatusTypes, strSTValue,
						blnShowAllResourceTypes, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Customize View Resource Detail' button,
			 * create a section 'S1' with status type 'MST'. Expected
			 * Result:Section S1 is displayed on 'Edit Resource Detail View
			 * Sections' screen with status type MST under it.
			 */
			// 415436

			
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			blnCreateSec = true;

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strArStatType, strSection, blnCreateSec);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
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

			/*
			 * STEP : Action:Click on 'Save' Expected Result:'Regions View List'
			 * screen is displayed.
			 */
			// 449352

			/*
			 * STEP : Action:Navigate to Setup >> Users, click on 'Edit' link
			 * next to U1, provide 'Update Status' right for resource RS and
			 * click on 'Save'. Expected Result:'User List' screen is displayed.
			 */
			// 449419

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			blnAssocWith = false;
			blnUpdStat = true;
			blnRunReport = false;
			blnViewRes = true;

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(selenium,
						strResName, blnAssocWith, blnUpdStat, blnRunReport,
						blnViewRes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user U1, Update the status of 'MST' from
			 * the view screen. Expected Result:Updated values are displayed on
			 * the view V1 screen.
			 */
			// 415437

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strConfirmPwd);
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
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatVal[0], strSTValue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.savAndVerifyUpdateST(selenium,
						strViewName, strResrctTypName, statTypeName,
						strStatusName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserName + "/" + strInitPwd,
						statTypeName + "," + strStatusName1 + ","
								+ strStatusName2, strViewName,
						"From 10th Step to 15", strResName, strSection, "", "",
						strRegn, strResrctTypName, strRoleName, "Color: green" };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69390";
			gstrTO = "Verify that a Multi status type can be selected as "
					+ "the default status type of a resource type.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	
	/***************************************************************
	'Description		:Verify that a Multi status type can be selected as the default status type of a resource type.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:18-09-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Ignore
	public void testBQS69390Old() throws Exception {
		try {
			gstrTCID = "69390"; // Test Case Id
			gstrTO = " Verify that a Multi status type can be selected " +
					"as the default status type of a resource type.";												// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName, strLoginPassword, strRegn, strStatusTypeValue, strStatTypDefn, strStatusName1, strStatusName2, strStatTypeColor, strRoleName, strUserName, strInitPwd, strConfirmPwd, strUsrFulName, strRoleValue = "", strResrctTypName, strResName, strRSAbbr, strFName, strLName, strState, strCountry, strStdRT, strViewName, strVewDescription, strViewType, strSection;
			boolean blnSav, blnViewRight, blnUpdateRight, blnSave, blnselectRole, blnVisibleToAllUsers, blnShowAllStatusTypes, blnShowAllResourceTypes, blnCreateSec, blnAssocWith, blnUpdStat, blnRunReport, blnViewRes;
			String statTypeName;
			String strFuncResult = "";

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			Roles objRoles = new Roles();
			CreateUsers objCreateUsers = new CreateUsers();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			Views objViews = new Views();
			ViewMap objMap = new ViewMap();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strSTValue[] = new String[1];
			MobileView objMob = new MobileView();
			String strStatVal[] = new String[2];

			strUserName = "AutoUsr" + System.currentTimeMillis();
			strInitPwd = rdExcel.readData("Login", 4, 2);
			strConfirmPwd = rdExcel.readData("Login", 4, 2);
			strUsrFulName = strUserName;
			strResrctTypName = "AutoRt_" + strTimeText;
			strRoleName = "AutoR_" + strTimeText;
			strSection = "AB_" + strTimeText;

			// Search criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			/*
			 * STEP : Action:Precondition: <br>1. Multi status type 'MST' is
			 * created with Status 'S1' under it where color is selected as
			 * 'Green'. <br>2. User U1 is created with role R1 to view and
			 * update MST. Expected Result:No Expected Result
			 */
			// 415423

			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			strRegn = rdExcel.readData("Login", 3, 4);

			try {
				assertEquals("", strFuncResult);
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
			statTypeName = "A1_" + strTimeText;
			strStatusTypeValue = "Multi";
			strStatTypDefn = "AutoTest";
			blnSav = true;
			String strArStatType[] = { statTypeName };

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName,
						strStatTypDefn, blnSav);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strStatusName1 = "Sta" + strTimeText;
			strStatusTypeValue = "Multi";
			strStatTypeColor = "Green";
			blnSav = true;

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statTypeName, strStatusName1,
						strStatusTypeValue, strStatTypeColor, blnSav);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strStatusName2 = "Stb" + strTimeText;
			strStatusTypeValue = "Multi";
			strStatTypeColor = "Green";
			blnSav = true;

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statTypeName, strStatusName2,
						strStatusTypeValue, strStatTypeColor, blnSav);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, statTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatVal[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, statTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatVal[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[][] strRoleRights = {};
			blnViewRight = true;
			blnUpdateRight = true;
			blnSave = true;

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTValue, blnViewRight,
						strSTValue, blnUpdateRight, blnSave);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
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
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			blnselectRole = true;

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, blnselectRole);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
			 * STEP : Action:Login as RegAdmin, navigate to 'Setup >> Resource
			 * Type' Expected Result:'Resource Type List' screen is displayed.
			 */
			// 415424

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

			/*
			 * STEP : Action:Click on 'Create New Resource Type', provide
			 * mandatory data('RT' as name), select check box 'MST' status type
			 * and select 'MST' from 'Default Status Type' drop down. <br>
			 * <br>Click on Save Expected Result:'RT' is listed on the 'Resource
			 * Type List' screen.
			 */
			// 415425

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						selenium, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTValue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.selectDefaultSTInCreateResType(selenium, statTypeName);
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
			 * STEP : Action:Navigate to Setup >> Resources, click on 'Create
			 * New Resource', provide mandatory data('RS' as name), provide
			 * address, select resource type 'RT' under Resource Type drop down
			 * and click on 'Save'. Expected Result:Resource RS is listed on
			 * 'Resource List' screen
			 */
			// 415434

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strFName = "qsg";
			strLName = "auto";
			strState = "Rhode Island";
			strCountry = "Newport County";
			strStdRT = "Aeromedical";
			strResName = "AutoRs_" + strTimeText;
			strRSAbbr = "Rs";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResName, strRSAbbr, strResrctTypName,
						strFName, strLName, strState, strCountry, strStdRT);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strRSValue = new String[1];
			try {
				assertEquals("", strFuncResult);
				String strResVal = objResources.fetchResValueInResList(
						selenium, strResName);

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
			 * STEP : Action:Navigate to Setup >> View,provide name as 'V1'
			 * select 'MST' and 'RS' under appropriate sections and click on
			 * 'Save'. Expected Result:V1 is listed on 'Regions View List'
			 * screen.
			 */
			// 415435

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strViewType = "Resource (Resources and status types as rows. Status, comments and timestamps as columns.)";
			blnVisibleToAllUsers = true;
			blnShowAllStatusTypes = false;
			blnShowAllResourceTypes = false;
			strVewDescription = "";
			strViewName = "AutoV_" + strTimeText;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, blnVisibleToAllUsers,
						blnShowAllStatusTypes, strSTValue,
						blnShowAllResourceTypes, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Customize View Resource Detail' button,
			 * create a section 'S1' with status type 'MST'. Expected
			 * Result:Section S1 is displayed on 'Edit Resource Detail View
			 * Sections' screen with status type MST under it.
			 */
			// 415436

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			blnCreateSec = true;

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(selenium,
						strArStatType, strSection, blnCreateSec);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Save' Expected Result:'Regions View List'
			 * screen is displayed.
			 */
			// 449352

			/*
			 * STEP : Action:Navigate to Setup >> Users, click on 'Edit' link
			 * next to U1, provide 'Update Status' right for resource RS and
			 * click on 'Save'. Expected Result:'User List' screen is displayed.
			 */
			// 449419

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			blnAssocWith = false;
			blnUpdStat = true;
			blnRunReport = false;
			blnViewRes = true;

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(selenium,
						strResName, blnAssocWith, blnUpdStat, blnRunReport,
						blnViewRes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user U1, Update the status of 'MST' from
			 * the view screen. Expected Result:Updated values are displayed on
			 * the view V1 screen.
			 */
			// 415437

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strConfirmPwd);
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
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatVal[0], strSTValue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.savAndVerifyUpdateST(selenium,
						strViewName, strResrctTypName, statTypeName,
						strStatusName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> Map Expected Result:The
			 * resource icon is displayed in the updated status color (i.e.
			 * Green)
			 */
			// 415438

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String strIconSrc = propEnvDetails.getProperty("MailURL")+"/icon/map/heli_green.png";
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMap.checkResIconAndColor(selenium,
						strResName, strIconSrc);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * STEP : Action:Navigate to 'View >> V1' and select resource 'RS'
			 * Expected Result:The resource icon is displayed in the updated
			 * status color (i.e. Green)
			 */
			// 415447
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMap.checkResIconAndColorInViewResDet(
						selenium, strResName, strIconSrc);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * STEP : Action:Click on 'Mobile View' link at the footer of the
			 * application. Expected Result:'Main Menu' screen is displayed in
			 * the separate window
			 */
			// 415448
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMob.navToViewsInMobileView(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * STEP : Action:Click on Resources >> V1(view name) Expected
			 * Result:V1 view screen is displayed. <br> <br>Resource RS is
			 * displayed with colored ball (i.e. green) on view V1 screen.
			 */
			// 449383
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMob.navToUserViewsInMob(selenium,
						strViewName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMob
						.checkResIconAndColorInMob(selenium, strResName,
								""+propEnvDetails.getProperty("MailURL")+"/image/mobile/green.png");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * STEP : Action:Launch EMResource on mobile device (ipod touch)
			 * Expected Result:'Main Menu' screen is displayed in the separate
			 * window
			 */
			// 449398

			/*
			 * STEP : Action:Click on Resources >> V1(view name) Expected
			 * Result:V1 view screen is displayed. <br> <br>Resource RS is
			 * displayed with colored ball (i.e. green) on view V1 screen.
			 */
			// 449399

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, strUserName + "/" + strInitPwd, "",
						strViewName, "From 14th Step to 15", strResName,
						strSection, "Color: green" };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69390";
			gstrTO = "Verify that a Multi status type can be selected as " +
					"the default status type of a resource type.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

}
