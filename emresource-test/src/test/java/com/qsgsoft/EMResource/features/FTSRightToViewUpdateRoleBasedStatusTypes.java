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

/**********************************************************************
' Description       :This class contains test cases from requirement
' Requirement Group :Setting up users
' Requirement       :Rights to view/update Role-Based status types
' Date		        :16-Jan-2013
' Author	        :QSG
'-------------------------------------------------------------------
' Modified Date                                         Modified By
' <Date>                           	                    <Name>
'*******************************************************************/
public class FTSRightToViewUpdateRoleBasedStatusTypes {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSRightToViewUpdateRoleBasedStatusTypes");
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
	
	Selenium selenium,seleniumFirefox,seleniumPrecondition;
	
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
		try {
			seleniumFirefox.close();
		} catch (Exception e) {

		}
		
		//kill the browser
		seleniumFirefox.stop();
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
		  gstrReason=gstrReason.replaceAll("'", " ");
		  objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
		    gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
		    gslsysDateTime, gstrBrowserName, gstrBuild,strSessionId);
	}

/*********************************************************************************************
'Description :Verify that when a user is provided ONLY �View Resource� right on resource RS
              and a role for viewing the associated status type ST (regardless of ST associated 
              at the resource level/resource type level), then the user can set status change
              preferences for ST.
'Arguments	 :None
'Returns	 :None
'Date		 :17/1/2013
'Author		 :QSG
'--------------------------------------------------------------------------------------------
'Modified Date				                                                Modified By
'Date					                                                     Name
**********************************************************************************************/
	@Test
	public void testFTS53021() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Login objLogin = new Login();// object of class Login
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Preferences objPreferences = new Preferences();
		Roles objRole = new Roles();
		try {
			gstrTCID = "53021"; // Test Case Id
			gstrTO = " Verify that when a user is provided ONLY �View Resource� right on resource RS"
					+ " and a role for viewing the associated status type ST (regardless of ST associated"
					+ " at the resource level/resource type level), then the user can set status change"
					+ " preferences for ST.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login", 3, 4);
			// Rol
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// ST
			String statTypeName1 = "AutoST_1" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValues[] = new String[1];
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Brown County";
			String strResVal = "";
			String strRSValues[] = new String[1];

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// search user data
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
	  Action:Preconditions:
			1. Status type ST1 is created selecting role R1 under 'Roles with view rights' section.
	        2. ST1 is associated with resource type RT.
	        3. Resource RS is created under resource type RT. 
	  Expected Result:No Expected Result
	*/
				log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
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
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = {};
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST1
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName1);
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
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName, strStatusTypeValues[0]);
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
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
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
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName, "FN",
						"LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STATRTS~~~~~");
		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to Setup >> Users. 
		  Expected Result:'Users List' screen is displayed.
		*/
			// USER A
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Create New User' 
		  Expected Result:'Create New User' screen is displayed. 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strResVal, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Create a User A, by filling all the mandatory data, select only 'View Resource' right
		         for resource RS. Provide 'Edit Status Change Notification Preferences' right from
		          'Advanced Options' section and click on 'Save'. 
		  Expected Result:'Users List' screen is displayed.
		*/	
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
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
		* STEP :
		  Action:Login as User A and navigate to Preferences >> Status Change Prefs. 
		  Expected Result:	'My Status Change Preferences' screen is displayed. 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
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
		/*
		* STEP :
		  Action:Click on 'Add', search for resource RS, select resource RS and click on 'Notifications'. 
		  Expected Result:	Status type ST1 is available in 'Edit My Status Change Preferences' screen. 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strResVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "0", "false", "false", "false" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName1, strNotifData);
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
			gstrTCID = "53021"; // Test Case Id
			gstrTO = " Verify that when a user is provided ONLY �View Resource� right on resource RS"
					+ " and a role for viewing the associated status type ST (regardless of ST associated"
					+ " at the resource level/resource type level), then the user can set status change"
					+ " preferences for ST.";// Test Objective
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
		if (blnLogin) {
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
	}
	
	/*************************************************************************************************
	'Description :User A has role R1 where R1 has the view right for status type ST1 and view & update
	              right for status type ST2. Role R2 does not have view/update rights for status types 
	              ST1 and ST2. Verify that when user A's role is changed from R1 to R2,user A cannot
	              view the status types ST1 and ST2 while adding status types to the custom view.
	'Arguments	 :None
	'Returns	 :None
	'Date		 :17/1/2013
	'Author		 :QSG
	'--------------------------------------------------------------------------------------------
	'Modified Date				                                                Modified By
	'Date					                                                     Name
	**********************************************************************************************/
	
	@Test
	public void testFTS49199() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Login objLogin = new Login();// object of class Login
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Preferences objPreferences = new Preferences();
		Roles objRole = new Roles();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		try {
			gstrTCID = "49199"; // Test Case Id
			gstrTO = "User A has role R1 where R1 has the view right for status type ST1 and view & update"
					+ " right for status type ST2. Role R2 does not have view/update rights for status types"
					+ " ST1 and ST2. Verify that when user A's role is changed from R1 to R2,user A cannot"
					+ "view the status types ST1 and ST2 while adding status types to the custom view.";// Test
																										// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// Rol
			String strRoleName1 = "AutoR_1" + strTimeText;
			String strRoleName2 = "AutoR_2" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strRoleVal = "";
			// ST
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValues[] = new String[2];
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Brown County";
			String strResVal = "";
			String strRSValues[] = new String[1];
			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			/*
			 * STEP : Action:Preconditions: 1.User A has 'View Custom View'
			 * right. 2.Role R1 has the view right for status type ST1 and view
			 * & update right for status type ST2. 3.Role R2 does not have
			 * view/update rights for status types ST1 and ST2. 4.Resource RS is
			 * created proving address under RT which is associated with status
			 * types ST1 and ST2. 5.User A is associated with role 'R1'. 6.User
			 * A has created a custom view selecting RS, ST1 and ST2. Expected
			 * Result:No Expected Result
			 */
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
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

			// ST1
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName1);
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

			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strViewRightValue = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				String[] updateRightValue = { strStatusTypeValues[1] };
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						selenium, strRoleName1, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName1);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Role 2
			try {
				assertEquals("", strFuncResult);
				String[] strViewRightValue = {};
				String[] updateRightValue = {};
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						selenium, strRoleName2, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRoleVal = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName2);
				if (strRoleVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
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
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName, "FN",
						"LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
			// USER A
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strResVal, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
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
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statTypeName1, statTypeName2 };
				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");
			/*
			 * STEP : Action:Login as RegAdmin, navigate to Setup >> Users,
			 * click on the edit link associated with User A, deselect role R1
			 * and select role R2 under 'User Type & Roles' section then click
			 * on save. Expected Result:User A is listed in 'Users List' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate user default region
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
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
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
			 * STEP : Action:Login in as User A and navigate to View >> Custom.
			 * Expected Result: Status types ST1 and ST2 are not displayed in
			 * 'Custom View - Map' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserNameA,
						strInitPwd);
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
				strFuncResult = objViewMap.verRSInFindResDropDown(selenium,
						strResource, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized View then
			 * click on 'Options'. Expected Result:Status type ST1 and ST2 are
			 * not displayed in 'Edit Custom View Options (Columns)' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statTypeName1, statTypeName2 };
				strFuncResult = objPreferences.checkSTsInEditCustViewOptions(
						selenium, strStatusType);
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
			gstrTCID = "49199"; // Test Case Id
			gstrTO = "User A has role R1 where R1 has the view right for status type ST1 and view & update"
					+ " right for status type ST2. Role R2 does not have view/update rights for status types"
					+ " ST1 and ST2. Verify that when user A's role is changed from R1 to R2,user A cannot"
					+ "view the status types ST1 and ST2 while adding status types to the custom view.";// Test
			// Objective
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
	}

	/***************************************************************************************************
	'Description  :Verify that when a user is provided ONLY 'View Resource' right on resource RS and a 
	               role for viewing the associated status type ST (regardless of ST associated at the 
	               resource level/resource type level), then the user can add ST to his/her custom view.
	'Arguments	  :None
	'Returns	  :None
	'Date		  :1/18/2013
	'Author		  :QSG
	'---------------------------------------------------------------------------------------------------
	'Modified Date				                                                        Modified By
	'Date					                                                            Name
	***************************************************************************************************/

	@Test
	public void testFTS52876() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;

		Login objLogin = new Login();// object of class Login
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Preferences objPreferences = new Preferences();
		Roles objRole = new Roles();
		try {
			gstrTCID = "52876"; // Test Case Id
			gstrTO = " Verify that when a user is provided ONLY 'View Resource' right on resource RS and a role"
					+ " for viewing the associated status type ST (regardless of ST associated at the resource "
					+ "level/resource type level), then the user can add ST to his/her custom view.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// Rol
			String strRoleName = "AutoR_1" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// ST
			String statTypeName1 = "AutoST_1" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValues[] = new String[1];
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Brown County";
			String strResVal = "";
			String strRSValues[] = new String[1];
			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			/*
			 * STEP : Action:Preconditions: 1. Status type ST1 is created
			 * selecting role R1 under 'Roles with view rights' section. 2. ST1
			 * is associated with resource type RT. 3. Resource RS is created
			 * under resource type RT. Expected Result:No Expected Result
			 */
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strViewRightValue = {};
				String[] updateRightValue = {};
				strFuncResult = objRole
						.CreateRoleWithAllFieldsCorrect(selenium, strRoleName,
								strRoleRights, strViewRightValue, false,
								updateRightValue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName);
				if (strRoleValue.compareTo("") != 0) {
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST1
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName1);
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
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName, strStatusTypeValues[0]);
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
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
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
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName, "FN",
						"LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");
			/*
			 * STEP : Action:Login as RegAdmin and navigate to Setup >> Users.
			 * Expected Result:'Users List' screen is displayed.
			 */
			// USER A
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Create New User' Expected Result:'Create
			 * New User' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create a User A, by filling all the mandatory data,
			 * select only 'View Resource' right for resource RS. Provide 'View
			 * Custom View' right from 'Advanced Options' section and click on
			 * 'Save'. Expected Result:'Users List' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strResVal, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
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
			 * STEP : Action:Login as User A and navigate to Preferences >>
			 * Customized View. Expected Result:'Edit Custom View' screen is
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Add More Resources', and search for RS.
			 * Expected Result:No Expected Result
			 */
			/*
			 * STEP : Action:Add resource RS to the Custom view. Expected
			 * Result:'Edit Custom View' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Options'. Expected Result:Status type ST1
			 * is displayed in 'Edit Custom View Options (Columns)' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strST = { statTypeName1 };
				strFuncResult = objPreferences
						.verifySTinEditCuctmViewOptionPge(selenium, strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select Status type ST1 and click on 'Save' Expected
			 * Result:Status type ST1 is displayed in the custom view.
			 */
			try {
				assertEquals("", strFuncResult);
				String[][] strSTValue = { { strStatusTypeValues[0], "true" } };
				strFuncResult = objPreferences.addSTInEditCustViewOptionPage(
						selenium, strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statTypeName1 };
				String[] strResources = { strResource };
				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrcTypName, strResources, strStatType);
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
			gstrTCID = "52876";
			gstrTO = "Verify that when a user is provided ONLY 'View Resource' right on resource RS and a role"
					+ " for viewing the associated status type ST (regardless of ST associated at the resource level"
					+ "/resource type level), then the user can add ST to his/her custom view.";
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
	}

	/*********************************************************************************************************
	'Description   :User A has role R1 where R1 has the view right for status type ST1 and view & update right 
	                for status type ST2. Role R2 does not have view/update rights for status types ST1 and ST2. 
	                Verify that when user A's role is changed from R1 to R2, user A:
					1. Cannot add status change preferences for both the status types ST1 and ST2
					2. Cannot edit status change preferences set for both the status types ST1 and ST2
	'Arguments		:None
	'Returns		:None
	'Date			:1/18/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------------------------
	'Modified Date				                                                                  Modified By
	'Date				                                                                          Name
	************************************************************************************************************/

	@Test
	public void testFTS46419() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;

		Login objLogin = new Login();// object of class Login
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Preferences objPreferences = new Preferences();
		Roles objRole = new Roles();
		try {
			gstrTCID = "46419"; // Test Case Id
			gstrTO = "User A has role R1 where R1 has the view right for status type ST1 and view & update right for "
					+ "status type ST2. Role R2 does not have view/update rights for status types ST1 and ST2. Verify "
					+ "that when user A's role is changed from R1 to R2, user A:"
					+ "1.Cannot add status change preferences for both the status types ST1 and ST2"
					+ "2.Cannot edit status change preferences set for both the status types ST1 and ST2";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// Rol
			String strRoleName1 = "AutoR_1" + strTimeText;
			String strRoleName2 = "AutoR_2" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strRoleVal = "";
			// ST
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValues[] = new String[2];
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Brown County";
			String strResVal = "";
			String strRSValues[] = new String[1];
			// user
			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			/*
			 * STEP : Action:Precondition: 1.User A has 'Edit Status Change
			 * Notification Preferences' right. 2.Role R1 has the view right for
			 * status type ST1 and view & update right for status type ST2.
			 * 3.Role R2 does not have view/update rights for status types ST1
			 * and ST2. 4.Resource RS is created proving address under RT which
			 * is associated with status types ST1 and ST2. 5.User A is
			 * associated with role 'R1'. 6.User A has set Status change
			 * preferences for ST1 and ST2 for RS. Expected Result:No Expected
			 * Result
			 */
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
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

			// ST1
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName1);
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

			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strViewRightValue = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				String[] updateRightValue = { strStatusTypeValues[1] };
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						selenium, strRoleName1, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName1);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Role 2
			try {
				assertEquals("", strFuncResult);
				String[] strViewRightValue = {};
				String[] updateRightValue = {};
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						selenium, strRoleName2, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRoleVal = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName2);
				if (strRoleVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
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
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName, "FN",
						"LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
			// USER A
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
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
								strResource, strResVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal, strStatusTypeValues[0],
								statTypeName1, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium, strResVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal, strStatusTypeValues[1],
								statTypeName2, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin, navigate to Setup >> Users,
			 * click on the edit link associated with User A, deselect role R1
			 * and select role R2 under 'User Type & Roles' section then click
			 * on save. Expected Result:User A is listed in 'Users List' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate user default region
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
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
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
			 * STEP : Action:Login in as User A and navigate to Preferences >>
			 * Status Change Prefs. Expected Result:'My Status Change
			 * Preferences' screen is displayed. Previously set status change
			 * notification preferences for status types ST1 and ST2 are not
			 * displayed. (ST1 and ST2 are not available on the screen)
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserNameA,
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
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statTypeName1, "", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "+statTypeName1+" is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statTypeName2, "", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Add', search for resource RS, select
			 * resource RS and click on 'Notifications' Expected Result:ST1 and
			 * ST2 are not displayed, message stating 'No visible status types
			 * are available' is displayed in 'Edit My Status Change
			 * Preferences' screen.
			 */

			try {
				assertEquals(
						" Status Type "+statTypeName2+" is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.varErrorMsgInEditMyStatChngPrefPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
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
			gstrTCID = "46419";
			gstrTO = "User A has role R1 where R1 has the view right for status type ST1 and view & update right for "
					+ "status type ST2. Role R2 does not have view/update rights for status types ST1 and ST2. Verify "
					+ "that when user A's role is changed from R1 to R2, user A:"
					+ "1.Cannot add status change preferences for both the status types ST1 and ST2"
					+ "2.Cannot edit status change preferences set for both the status types ST1 and ST2";
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
	}

	//start//testFTS108051//
	/*******************************************************************************************************
	'Description	:User A has role R1 where R1 has the view right for status type ST1 and view & update 
	                 right for status type ST2. Role R2 does not have view/update rights for status types ST1
	                  and ST2. Verify that when user A's role is changed from R1 to R2, the changes are 
	                  reflected on the following view screens:
					1. Region view
					2. Map
					3. Custom views (both table and map)
					4. Event detail
					5. View Resource detail
					6. Mobile view
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:8/5/2013
	'Author			:QSG
	'--------------------------------------------------------------------------------------------------------
	'Modified Date				                                                               Modified By
	'Date					                                                                   Name
	*********************************************************************************************************/

	@Test
	public void testFTS108051() throws Exception {

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
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		try {
			gstrTCID = "108051"; // Test Case Id
			gstrTO = " User A has role R1 where R1 has the view right for status type ST1 and view & update right for status type ST2. Role R2 does not have view/update rights for status types ST1 and ST2. Verify that when user A's role is changed from R1 to R2, the changes are reflected on the following view screens:"
					+ "1. Region view"
					+ "2. Map"
					+ "3. Custom views (both table and map)"
					+ "4. Event detail"
					+ "5. View Resource detail"
					+ "6. Mobile view";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimetake = dts.timeNow("hh:mm:ss");

			String strRegn, strStatusTypeValue, strStatTypDefn, strResrctTypName,
			strState, strCountry, strResource;

			/*
			 * STEP : Action:Precondition: <br> <br>1.Role R1 has the view right
			 * for status type ST1 and view & update right for status type ST2.
			 * <br> <br>2.R2 does not have view/update rights for status types
			 * ST1 and ST2. <br> <br>3.Resource RS is created proving address
			 * under RT which is associated with status types ST1 and ST2. <br>
			 * <br>4. Event Template is created selecting 'ST1','ST2' and 'RT'.
			 * <br> <br>5.Event 'EV' is created selecting resource RS and status
			 * types ST1 and ST2. <br> <br>6.View V1 is created selecting ST1,
			 * ST2 and RT. <br> <br>7.Status type section 'Sec' is created which
			 * contain ST1 and ST2 in 'Edit Resource Detail View Sections'. <br>
			 * <br>8.User A is associated with role 'R1' and has 'View custom
			 * view' right. <br> <br>9. User A has added resource RS and status
			 * types ST1 and ST2 to his/her custom view. Expected Result:No
			 * Expected Result
			 */
			// 610741

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			strRegn = rdExcel.readData("Login", 3, 4);

			// search user data
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

			String statTypeName1 = "ST_1" + strTimeText;
			String statTypeName2 = "ST_2" + strTimeText;
			strStatusTypeValue = "Number";
			strStatTypDefn = "Automation";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strSection1 = "AB_1" + strTimeText;

			String strArStatType1[] = { statTypeName1, statTypeName2 };
			String[] strEventStatType = {};
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

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

			String strRolesName_2 = "Role_2" + strTimeText;
			String strRoleValue2 = "";

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

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

				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. ST1 and ST2 are associated with resource type RT. */

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
				seleniumPrecondition
						.click("css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");

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

			/* 3. Resources RS is created under resource type RT with address. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strState = "Alabama";
				strCountry = "Barbour County";
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = { strSTvalue[1] };
				String[] strViewRightValue = { strSTvalue[0], strSTvalue[1] };
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRolesName_1, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);

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
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRolesName_2, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue2 = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_2);
				if (strRoleValue2.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
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
				strVewDescription = "Automation";
				strViewType = "Summary Plus (Resources as rows."
						+ " Status types and comments as columns.)";

				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName_1, strVewDescription, strViewType, true,
						false, strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 5. Event Template ET is created with ST1, ST2 and RT. */

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

				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1] };
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

			/* 6. Event E1 is created under ET selecting RS. */

			try {
				assertEquals("", strFuncResult);
				assertTrue(objEventSetup
						.navToEventManagement(seleniumPrecondition));
			} catch (AssertionError Ae) {
				strFuncResult = "Failed";
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

			/* 7. Status types ST1 and ST2 are under status type section S1. */

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
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strArStatType1, strSection1, true);
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
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
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
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9. User U1 has added resources RS and status types ST1 and ST2 to
			 * his/her custom view. No Exp
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
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName_1, strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences
						.navEditCustomViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences
						.createCustomViewNewWitTablOrMapOption(
								seleniumPrecondition, strRS, strResrctTypName,
								statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTValue[][] = { { strSTvalue[1], "true" } };
				strFuncResult = objPreferences.addSTInEditCustViewOptionPage(
						seleniumPrecondition, strSTValue, true);
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

			/*
			 * STEP 2:Login as RegAdmin, navigate to Setup >> Users, click on
			 * the edit link associatedwith User A, deselect role R1 and select
			 * role R2 under 'User Type & Roles' section then click on save.
			 * <-->:User A is listed in 'Users List' screen.
			 */

			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);

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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
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
			 * STEP 3:Login in as User A and navigate to View >> V1.<-->:Only
			 * ST2 is displayed in view 'V1' screen.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_1,
						strConfirmPwd);
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
				strFuncResult = objViews.checkStatusTypeInViewScreen(selenium,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatusTypeInViewScreen(selenium,
						statTypeName2);
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
				strFuncResult = objPreferences.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statTypeName1, statTypeName2 };
				strFuncResult = objPreferences.checkSTsInEditCustViewOptions(
						selenium, strRoleStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statTypeName1, statTypeName2 };		
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTablNewMapOrTableOption(selenium,
						strRTValue, strResource, strRoleStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals( "Resource '"
						+ strResource
						+ "' is NOT displayed on the 'Cusotm View table' screen under "
						+ strRTValue + " along with all the status types.", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verRSInFindResDropDown(selenium,
						strResource, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statTypeName1, statTypeName2 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(selenium,
						strResource, strEventStatType, strRoleStatType,
						false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				if (strFuncResult.equals("")) {

					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,"
									+ "'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName1 + "']"));
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,"
									+ "'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName2 + "']"));

					log4j.info("Status types " + statTypeName1 + " and"
							+ statTypeName2
							+ " is displayed in the resource pop up window");

				} else {
					log4j.info("Status types " + statTypeName1 + " and"
							+ statTypeName2
							+ " is displayed in the resource pop up window");
					gstrReason = "Status types " + statTypeName1 + " and"
							+ statTypeName2
							+ " is displayed in the resource pop up window";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statTypeName1, statTypeName2 };
				strFuncResult = objEventList.checkSTypesEvntBanner(selenium,
						strEveName, strResrctTypName, strRoleStatType, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Partial data
			String strTestData[] = new String[10];
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName_1 + "/" + strInitPwd;
				strTestData[3] = statTypeName1;
				strTestData[4] = strViewName_1;
				strTestData[5] = "Verify from 10th step";
				strTestData[6] = strResource + "/" + strResource;
				strTestData[7] = strSection1;
				strTestData[8] = strEveName;
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
			gstrTCID = "FTS-108051";
			gstrTO = " User A has role R1 where R1 has the view right for status type ST1 and view & update right for status type ST2. Role R2 does not have view/update rights for status types ST1 and ST2. Verify that when user A's role is changed from R1 to R2, the changes are reflected on the following view screens:"
					+ "1. Region view"
					+ "2. Map"
					+ "3. Custom views (both table and map)"
					+ "4. Event detail"
					+ "5. View Resource detail"
					+ "6. Mobile view";
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
			// end//testFTS108051//
		}
	}
	/**********************************************************************************************************
	 * 'Description :User A has role R1 where R1 has the view right for status type ST1 and view & update right 
	 * 				for status type ST2. Role R2 does not have view/update rights for status types ST1 and ST2. 
	 * 			Verify that when user A's role is changed from R1 to R2, user A does not receive expired status 
	 * 				notification for when the status of ST1 and ST2 expire.
	 * 'Arguments    :None  
	 * 'Returns 	 :None 
	 * 'Date 		 :01-Aug-2013
	 * 'Author		 :QSG
	 * '-------------------------------------------------------------------------------------------------------
	 * 'Modified Date									                                      Modified By 
	 * 													                                      <Name>
	 **********************************************************************************************************/

	@SuppressWarnings("unused")
	@Test
	public void testFTS49202() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		General objMail = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();

		try {
			gstrTCID = "49202 ";
			gstrTO = "User A has role R1 where R1 has the view right for status type ST1"
					+ " and view & update right for status type ST2. Role R2 does not have view/update "
					+ " rights for status types ST1 and ST2. Verify that when user A's role "
					+ "is changed from R1 to R2, user A does not receive"
					+ " expired status notification for when the status of ST1 and ST2 expire.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			// Admin User Name
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			int intPositionExpire = 0;

			// Status Type
			String strStatusTypeValue = "Number";
			String statTypeName_1 = "AutoNST_1" + strTimeText;
			String statTypeName_2 = "AutoNST_2" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatusTypeNumericValue = "";
			String strSTvalue[] = new String[2];
			String strExpHr = "00";
			String strExpMn = "05";

			// Role Names
			String strRoleName_1 = "AutoR_1" + strTimeText;
			String strRoleName_2 = "AutoR_2" + strTimeText;
			String strRoleName_3 = "AutoR_3" + strTimeText;

			String strRoleValue[] = new String[3];
			strRoleValue[0] = "";// Role 1
			strRoleValue[1] = "";// Role 2
			strRoleValue[2] = "";// Role 2

			String strRoleRights[][] = {};
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strResVal = "";
			String strRSValue[] = new String[1];

			// Search criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_Any = "AutoUsr_Any" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrFulName_Any = strUserName_Any;
			String strUsrFulName = "FN"+strUserName;

			String strUpdTime = "";
			String strCurDate = "";

			// Email sub Name
			String strSubjName = "EMResource Expired Status Notification: "
					+ strResource;

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			
		log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Navigate to status type list

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create ST1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName_1, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * Precondition 2.Status type 'ST1' is associated with the
		 * expiration time (say 'T1')
		 */

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

				seleniumPrecondition.click(propElementDetails
						.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeName_1 + "']"));

					log4j.info("Status type " + statTypeName_1
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

					try {
						assertEquals("", strFuncResult);
						strStatusTypeNumericValue = objST
								.fetchSTValueInStatTypeList(
										seleniumPrecondition, statTypeName_1);
						if (strStatusTypeNumericValue.compareTo("") != 0) {
							strFuncResult = "";
							strSTvalue[0] = strStatusTypeNumericValue;
						} else {
							strFuncResult = "Failed to fetch status type value";
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeName_1
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeName_1
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create ST2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName_2, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * Precondition 3.Status type 'ST2' is associated with the
		 * expiration time (say 'T1')
		 */

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

				seleniumPrecondition.click(propElementDetails
						.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeName_2 + "']"));

					log4j.info("Status type " + statTypeName_2
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

					try {
						assertEquals("", strFuncResult);
						strStatusTypeNumericValue = objST
								.fetchSTValueInStatTypeList(
										seleniumPrecondition, statTypeName_2);
						if (strStatusTypeNumericValue.compareTo("") != 0) {
							strFuncResult = "";
							strSTvalue[1] = strStatusTypeNumericValue;
						} else {
							strFuncResult = "Failed to fetch status type value";
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeName_2
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeName_2
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Navigate to Role list and create Role 1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName_1, strRoleRights,
						strSTvalue, true, strSTvalue, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * Precondition 4.Role R1 has the view right for status type ST1 and
		 * view & update right for status type ST2.
		 */

			try {
				assertEquals("", strFuncResult);
				String[][] stSTViewValue = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[0], "false" },
						{ strSTvalue[1], "true" } };
				strFuncResult = objRole.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, stSTViewValue,
						strSTUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName_1);

				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list and create Role 2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName_2, strRoleRights,
						strSTvalue, false, strSTvalue, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * Precondition 5.Role R2 does not have view/update rights for
			 * status types ST1 and ST2.
			 */

			try {
				assertEquals("", strFuncResult);
				String[][] stSTViewValue = { { strSTvalue[0], "false" },
						{ strSTvalue[1], "false" } };
				String[][] strSTUpdateValue = { { strSTvalue[0], "false" },
						{ strSTvalue[1], "false" } };
				strFuncResult = objRole.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, stSTViewValue,
						strSTUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[1] = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName_2);

				if (strRoleValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName_3, strRoleRights,
						strSTvalue, false, strSTvalue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] stSTViewValue = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" } };
				strFuncResult = objRole.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, stSTViewValue,
						strSTUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[2] = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName_3);

				if (strRoleValue[2].compareTo("") != 0) {
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
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
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

			
		 // Create Any User
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
						seleniumPrecondition, strUserName_Any, strInitPwd,
						strConfirmPwd, strUsrFulName_Any);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Associate Role Any
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[2], true);
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
						seleniumPrecondition, strUserName_Any, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create User A
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Associate Role Any
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[0], true);
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

		/*
		 * STEP 2: Login as any user with 'Update Status' right on resource
		 * RS and a role to update the status types ST1 and ST2<->No
		 * Expected Result
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_Any,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP 3: Navigate to View >> Map. Select resource 'RS' under 'Find
		 * Resource' dropdown.<-> Status types ST1 and ST2 are displayed in
		 * resource pop up window
		 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName_1, statTypeName_2 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

		/*
		 * STEP 4: Update status values of status types ST1 and ST2 from
		 * resource pop up window.<-> Status types 'ST1' and 'ST2' are
		 * updated and are displayed on resource pop up window.
		 */

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
						"10", strSTvalue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"20", strSTvalue[1], false, "", "");
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

				String strUpdatedDate = selenium
						.getText("//span[text()='10']/following-sibling::span[1]");

				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdTime = strLastUpdArr[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime, 5,
						"HH:mm");
				strUpdTime = strAdUpdTime;
				String strCurYear = dts.getCurrentDate("yyyy");
				String strCurDateMnth = strLastUpdArr[0] + strLastUpdArr[1]
						+ strCurYear;

				strCurDate = dts.converDateFormat(strCurDateMnth, "ddMMMyyyy",
						"MM/dd/yyyy");

				log4j.info("Expiration Time: " + strUpdTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP 5: Login as RegAdmin, navigate to Setup >> Users, click on
		 * the edit link associated with User A, deselect role R1 and select
		 * role R2 under 'User Type & Roles' section then click on save.
		 * User A is listed in 'Users List' screen.
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

			// Deselect Role R1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Select Role R2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue[1], true);
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

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_Any,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
				Thread.sleep(300000);

				intPositionExpire = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='3']/preceding-sibling::span")
						.intValue();
				intPositionExpire = intPositionExpire + 2;

				String strElementIdExpire = "//div[@class='emsCenteredLabel']"
						+ "[text()='" + strResource
						+ "']/following-sibling::div/span[" + intPositionExpire
						+ "][@class='overdue']";

				strFuncResult = objMail.waitForMailNotification(selenium, 30,
						strElementIdExpire);

				try {
					assertEquals("", strFuncResult);
					try {
						strFuncResult = objMail.refreshPage(selenium);
					} catch (Exception e) {

					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			int intResCntNotRecv = 0;

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSubjName = "EMResource Expired Status: " + strAbbrv;
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

					strSubjName = "EMResource Expired Status: " + strAbbrv;

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

				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad("90000");
				selenium.close();

				selenium.selectWindow("");
				selenium.selectWindow("");
				selenium.selectFrame("Data");

				selenium.selectWindow("");
				selenium.selectWindow("");
				selenium.selectFrame("Data");

				try {
					assertEquals("", strFuncResult);
					if (intResCntNotRecv == 3) {
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
			selenium.selectWindow("");
			gstrTCID = "49202";
			gstrTO = "User A has role R1 where R1 has the view right for status type ST1"
					+ " and view & update right for status type ST2. Role R2 does not have view/update "
					+ " rights for status types ST1 and ST2. Verify that when user A's role "
					+ "is changed from R1 to R2, user A does not receive"
					+ " expired status notification for when the status of ST1 and ST2 expire.";
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
				gstrReason = gstrReason + strFuncResult;
			}
		}
	}

	//start//testFTS46487//
	/***************************************************************
	'Description		:Verify that a role CANNOT be saved by selecting a status type ST under 'select the Status Types this Role may update:' section and without selecting the same status type ST under 'Select the Status Types this Role may view:' section.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:8/14/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS46487() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRoles = new Roles();
		try {
			gstrTCID = "46487"; // Test Case Id
			gstrTO = " Verify that a role CANNOT be saved by selecting a status type ST under 'select the Status Types this Role may update:' section and without selecting the same status type ST under 'Select the Status Types this Role may view:' section.";// Test
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

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNSTValue = "Number";
			String statrNumTypeName = "AutoNST" + strTimeText;
			String strStatTypDefn = "Automation";

			String strSTvalue[] = new String[3];
			String strRolesName = "AutoRol1" + strTimeText;

			/*
			 * STEP : Action:Login as RegAdmin navigate to Setup >> Status
			 * types, status type 'ST' is created by filling all the mandatory
			 * data then click on 'Save'. Expected Result:Status type 'ST' is
			 * displayed in 'Status Type List' screen.
			 */
			// 269156

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

			// Status Types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statrNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statrNumTypeName);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Roles. Expected Result:'Roles
			 * List' page is displayed.
			 */
			// 269158

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New Role'. Expected Result:'Create
			 * Role' page is displayed.
			 */
			// 269159

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navCreateRolePge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Create a role 'R1' by filling all the mandatory
			 * data do not select any status type under 'select the status Types
			 * this Role may view' section, select status type 'ST' under
			 * 'Select the Status Types this Role may update' section, then
			 * click on 'Save'. Expected Result:Warning message stating 'Each
			 * updatable status type must also be viewable' is displayed and
			 * user is retained in 'Create role' page.
			 */
			// 269160

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.roleMandtoryFlds(selenium,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.selectOrDeselectAllSTValue(selenium,
						strRolesName, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.vfyErrMsgForRoleMayView(selenium);
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
			gstrTCID = "FTS-46487";
			gstrTO = "Verify that a role CANNOT be saved by selecting a status type ST under 'select the Status Types this Role may update:' section and without selecting the same status type ST under 'Select the Status Types this Role may view:' section.";
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
				gstrReason = gstrReason + strFuncResult;
			}
		}
	}

	// end//testFTS46487//	
	
//start//testFTS124527
	/***************************************************************
	 * 'Description		:User U1 is assigned a role R1, where R1 has view rights for
	 *                   status types ST1, ST2 and update rights for ST2 only.
	 *                   Edit role R1, provide view rights for status types 
	 *                   ST3, ST2 and update rights for ST3 only.
	 *                   Verify that when the view rights of status types in a 
	 *                   role are edited as mentioned, for user U1,
	 *                   the changes are reflected on below view screens :
	 *                    1. Region view
	 *                    2. Map
	 *                    3. Custom views (both table and map)
	 *                    4. Event detail
	 *                    5. View Resource detail
	 * 'Precondition	:
	 * 'Arguments		:None
	 * 'Returns			:None
	 * 'Date			:8/21/2013 
	 * 'Author			:QSG 
	 * ---------------------------------------------------------------
	 * 'Modified Date                            Modified By  
	 * 'Date                                     Name
	 ***************************************************************/
	
	@Test
	public void testFTS124527() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup = new EventSetup();
		Views objViews = new Views();
		Preferences objPreferences = new Preferences();
		EventBanner objEventBanner = new EventBanner();

		try {
			gstrTCID = "124527"; // Test Case Id
			gstrTO = " User U1 is assigned a role R1, where R1 has view rights"
					+ " for status types ST1, ST2 and update rights for ST2 only."
					+ " Edit role R1, provide view rights for status types ST3, ST2 and update rights for ST3 only."
					+ " Verify that when the view rights of status types in a role are edited as mentioned, for user U1,"
					+ " the changes are reflected on below view screens :"
					+ "1. Region view" + "2. Map"
					+ "3. Custom views (both table and map)"
					+ "4. Event detail" + "5. View Resource detail";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// Search User criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strStatusTypeValue = "Number";
			String statTypeName1 = "Stat_1" + strTimeText;
			String statTypeName2 = "Stat_2" + strTimeText;
			String statTypeName3 = "Stat_3" + strTimeText;
			String strStatTypDefn = "Def";
			String strRoleName = "Rol_1" + strTimeText;
			String strResrcTypName = "Resty_1" + strTimeText;
			String strRSAbbr = "Abb";
			String strFName = "FN";
			String strLName = "LN";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strStdRT = "Ambulance";
			String strResource = "Res_1" + strTimeText;
			String strUserName = "User" + System.currentTimeMillis();
			String strUsrFulName = "UserFull";
			String strTempName = "EveTem" + strTimeText;
			String strTempDef = "Def";
			String strEveColor = "Red";
			String strEveName = "Eve" + strTimeText;
			String strInfo = "Info";
			String strViewName = "View_1" + strTimeText;
			String strVewDescription = "ViewDes" + strTimeText;
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			String strSection = "Sec" + strTimeText;
			String strSTvalue[] = new String[3];
			String strRTvalue[] = new String[1];
			String strRSValue[] = new String[1];
			String strROvalue[] = new String[1];

			/*
			 * STEP : Action:1. Create status types ST1,ST2,ST3 2. Create role
			 * 'R1' selecting view right for all 3 status types,Update right for
			 * only status types ST2, ST3 3. Create resource RS providing
			 * address under RT (Created selecting status types ST1,ST2,ST3) 4.
			 * User U1 selecting role R1 and 'View-custom View 'right and update
			 * right on resource RS 5. Create the following selecting status
			 * types ST1,ST2,ST3,Resource Type RT and resource RS a. Region View
			 * b. Custom view c. Event Template and Event under it d. Status
			 * type section Expected Result:Status types ST2 and ST3 hyper
			 * linked for updating on all mentioned screens, status type ST1 is
			 * not hyperlinked.
			 */
			// 660575
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// Login
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
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating ST1
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
						seleniumPrecondition, strStatusTypeValue,
						statTypeName1, strStatTypDefn, true);
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
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating ST2
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
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating ST3
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
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName3);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				String[] updateRightValue = { strSTvalue[1], strSTvalue[2] };
				String[][] strRoleRights = {};
				String[] strViewRightValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2] };
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strROvalue[0] = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);
				if (strROvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RT
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
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRTvalue[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Selecting ST2 and St3 in RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						seleniumPrecondition, strResrcTypName);
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

			// Creating RS

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
						seleniumPrecondition, strResource, strRSAbbr,
						strResrcTypName, strFName, strLName, strState,
						strCountry, strStdRT);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating User
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navToCreateUserPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strROvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
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
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating ET
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
				String strSTvalues[] = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2] };
				String strRTvalues[] = { strRTvalue[0] };
				strFuncResult = objEventSetup
						.fillMandfieldsEveTempNew(seleniumPrecondition,
								strTempName, strTempDef, strEveColor, true,
								strRTvalues, strSTvalues, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating EVE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
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

			// Creating View
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
				String[] strRSValues = { strRSValue[0] };
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalue, false, strRSValues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRTvalue[0], true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating Section
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeName = { statTypeName1, statTypeName2,
						statTypeName3 };
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strStatTypeName, strSection, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Logout
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

			// Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating Custom View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strStdRT, strRegn, strCountry,
						strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statTypeName1, statTypeName2,
						statTypeName3 };
				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Checking ST HyperLined in Custom View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatHyperLinkOrNotInViewScreens(
						selenium, strRSValue[0], strSTvalue[0], false,
						strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatHyperLinkOrNotInViewScreens(
						selenium, strRSValue[0], strSTvalue[1], true,
						strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatHyperLinkOrNotInViewScreens(
						selenium, strRSValue[0], strSTvalue[2], true,
						strResource, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Click on Eve Banner
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventBanner.clickEventWithFocusOnEveBanner(
						selenium, strEveName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Checking ST HyperLinked in Eve Banner
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatHyperLinkOrNotInViewScreens(
						selenium, strRSValue[0], strSTvalue[0], false,
						strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatHyperLinkOrNotInViewScreens(
						selenium, strRSValue[0], strSTvalue[1], true,
						strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatHyperLinkOrNotInViewScreens(
						selenium, strRSValue[0], strSTvalue[2], true,
						strResource, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Checking ST HyperLinked in User View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatHyperLinkOrNotInViewScreens(
						selenium, strRSValue[0], strSTvalue[0], false,
						strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatHyperLinkOrNotInViewScreens(
						selenium, strRSValue[0], strSTvalue[1], true,
						strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatHyperLinkOrNotInViewScreens(
						selenium, strRSValue[0], strSTvalue[2], true,
						strResource, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Checking ST HyperLinked in RS Detail
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
				strFuncResult = objViews.checkStatHyperLinkOrNotInViewScreens(
						selenium, strRSValue[0], strSTvalue[0], false,
						strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatHyperLinkOrNotInViewScreens(
						selenium, strRSValue[0], strSTvalue[1], true,
						strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatHyperLinkOrNotInViewScreens(
						selenium, strRSValue[0], strSTvalue[2], true,
						strResource, statTypeName3);
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
			gstrTCID = "FTS-124527";
			gstrTO = "User U1 is assigned a role R1, where R1 has view rights for status types ST1, ST2 and "
					+ "update rights for ST2 only. Edit role R1, provide view rights for status types ST3, ST2"
					+ " and update rights for ST3 only. Verify that when the view rights of status types in a role"
					+ " are edited as mentioned, for user U1, the changes are reflected on below view screens :"
					+ "1. Region view"
					+ "2. Map"
					+ "3. Custom views (both table and map)"
					+ "4. Event detail" + "5. View Resource detail";
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
	
//start//testFTS46626//
	/***************************************************************
	'Description	:For a role R1, select status type ST under
	                'select the Status Types this Role may view:'
	                 and 'Select the Status Types this Role may 
	                 update:' sections and verify that role R1
	                 is selected under 'Roles with view rights'
	                 and 'Roles with Update rights' sections
	                 in the 'Edit Status Type Screen' of  ST.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:8/28/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				              Modified By
	'Date					                  Name
	***************************************************************/

	@Test
	public void testFTS46626() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRoles = new Roles();
		Date_Time_settings dts = new Date_Time_settings();
		try {
			gstrTCID = "46626"; // Test Case Id
			gstrTO = " For a role R1, select status type ST under" +
					" 'select the Status Types this Role may view:'" +
					" and 'Select the Status Types this Role may update:" +
					"' sections and verify that role R1 is selected under" +
					" 'Roles with view rights' and 'Roles with Update rights'" +
					" sections in the 'Edit Status Type Screen' of  ST.";// Test
																																																																																	// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strStatusNumValue = "Number";
			String statTypeName1 = "Nst" + strTimeText;
			String strStatTypDefn = "Def";
			String strRoleName = "Rol_1" + strTimeText;
			String strSTvalue[] = new String[1];
			String strRoleValue[] = new String[1];

			/*
			 * STEP : Action:Login as RegAdmin, navigate to Setup >> Roles,
			 * click on 'Create New Role'. Expected Result:'Create Role' page is
			 * displayed.
			 */
			// 269956

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

			// Creating ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusNumValue, statTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTypeName1);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
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

			/*
			 * STEP : Action:Create a role 'R1' by filling all the mandatory
			 * data, and select status types ST under both 'select the status
			 * Types this Role may view' section, and 'Select the Status Types
			 * this Role may update' section. Expected Result:Role R1 is
			 * displayed in 'Roles List' page.
			 */
			// 269958

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue = { strSTvalue[0] };
				String[][] strRoleRightss = {};
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						selenium, strRoleName, strRoleRightss,
						updateRightValue, true, updateRightValue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(selenium,
						strRoleName);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Status types, click on the
			 * 'Edit' link associated with status type 'ST' Expected Result:Role
			 * R1 is selected under 'Roles with view rights' and 'Roles with
			 * Update rights' sections in the 'Edit Status Type Screen' of ST
			 */
			// 269963

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName1, strStatusNumValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Checking Role selected or not in Edit Status Type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.chkRolSelOrNotInEditSTPage(
						selenium, strRoleName, strRoleValue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.VerifySTInStatList(selenium,
						statTypeName1, true, true);
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
			gstrTCID = "FTS-46626";
			gstrTO = "For a role R1, select status type ST under 'select the Status Types this"
					+ " Role may view:' and 'Select the Status Types this Role may update:' sections and "
					+ "verify that role R1 is selected under 'Roles with view rights' and 'Roles with Update rights'"
					+ " sections in the 'Edit Status Type Screen' of  ST.";
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

	// end//testFTS46626//
	
//start//testFTS124530//
	/***************************************************************
	'Description		:User U1 is assigned a role R1, where R1 has 
	                     view rights for status types ST1, ST2 and update
	                     rights for ST2 only. Edit role R1, provide 
	                     view rights for status types ST3, ST2 and
	                     update rights for ST3 only. Verify that when
	                     the view rights of status types in a role are
	                     edited as mentioned, user U1 receives expired 
	                     status notifications only ST2 and ST3 and 
	                     not for ST1 when the status of these all status types expire.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:8/29/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date				               Modified By
	'Date					                   Name
	***************************************************************/

	@Test
	public void testFTS124530() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		General objMail = new General();
		try {
			gstrTCID = "124530"; // Test Case Id
			gstrTO = " User U1 is assigned a role R1, where R1 has view rights for status types ST1, ST2 and update rights for ST2 only. Edit role R1, provide view rights for status types ST3, ST2 and update rights for ST3 only. Verify that when the view rights of status types in a role are edited as mentioned, user U1 receives expired status notifications only ST2 and ST3 and not for ST1 when the status of these all status types expire.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strFILE_PATH = pathProps.getProperty("TestData_path");
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
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strExpHr = "00";
			String strExpMn = "05";
			String strStatusNumValue = "Number";
			String statTypeName1 = "Nst_1" + strTimeText;
			String statTypeName2 = "Nst_2" + strTimeText;
			String statTypeName3 = "Nst_3" + strTimeText;
			String strStatTypDefn = "Def";
			String strStandResType = "Aeromedical";
			String strResrcTypName = "Resty_1" + strTimeText;
			String strAbbrv = "Abb";
			String strResource = "Res" + strTimeText;
			String strRoleName = "Rol_1" + strTimeText;
			String strRoleName1 = "Rol_2" + strTimeText;
			String strViewName = "View_1" + strTimeText;
			String strVewDescription = "ViewDes" + strTimeText;
			String strContFName = "FA";
			String strContLName = "LA";
			String strViewType = "Resource (Resources and status types as rows."
					+ " Status, comments and timestamps as columns.)";
			String strUsrFulName = "AutoUser";
			String strUserName1 = "User_1" + System.currentTimeMillis();
			String strUserName2 = "User_2" + System.currentTimeMillis();
			String strUpdateValue1 = "20";
			String strUpdateValue = "10";
			String strUpdTime = "";
			String strCurDate = "";
			String strMsgBody2 = "";
			String strMsgBody1 = "";
			String strMsgBody1Another = "";
			String strMsgBody2Another = "";

			String strSubjName = "EMResource Expired Status Notification: ";

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			int intEMailRes = 0;
			int intPagerRes = 0;
			String strSTvalue[] = new String[3];
			String strRoleValue[] = new String[2];
			String strRTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			/*
			 * STEP : Action:1. Status types ST1,ST2,ST3 are created selecting
			 * expiration time 2. Resource Type RT is created selecting status
			 * types ST1,ST2,ST3 3. resource RS is created under RT 4. View 'V1'
			 * is created selecting status types ST1,ST2,ST3 and Resource Type
			 * RT 5. Create role R1 selecting view rights for status types ST1,
			 * ST2 and update rights for ST2 only 6. Edit role and provide view
			 * rights for status types ST3, ST2 and update rights for ST3 only
			 * 7. User U1 is created providing mail ID's,selecting role
			 * R1,providing update right on resource RS Expected Result:User U1
			 * receives expired status notifications only for status types ST2
			 * and ST3 and not for ST1 when the status of these all status types
			 * expire.
			 */
			// 660614

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// Login
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
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating ST1
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
						seleniumPrecondition, strStatusNumValue, statTypeName1,
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
			} catch (AssertionError Ae) {
				log4j
						.info(" selecting  Status Type with Expiration time failed");
				strFuncResult = "selecting  Status Type with Expiration time failed";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statTypeName1);
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
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusNumValue, statTypeName2,
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
			} catch (AssertionError Ae) {
				log4j
						.info(" selecting  Status Type with Expiration time failed");
				strFuncResult = "selecting  Status Type with Expiration time failed";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating ST3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusNumValue, statTypeName3,
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
			} catch (AssertionError Ae) {
				log4j
						.info(" selecting  Status Type with Expiration time failed");
				strFuncResult = "selecting  Status Type with Expiration time failed";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName3);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RT
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
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Selecting ST2 and St3 in RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						seleniumPrecondition, strResrcTypName);
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

			// Creating RS
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
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, strStandResType, strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue = { strSTvalue[1] };
				String[][] strRoleRightss = {};
				String[] strViewRightValue = { strSTvalue[0], strSTvalue[1] };
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRightss,
						strViewRightValue, true, updateRightValue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navEditRolesPge(seleniumPrecondition,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				String[][] strSTViewValue = { { strSTvalue[1], "true" },
						{ strSTvalue[2], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[2], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTViewValue,
						strSTUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating another Role

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue = { strSTvalue[1], strSTvalue[2] };
				String[][] strRoleRightss = {};
				String[] strViewRightValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2] };
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName1, strRoleRightss,
						strViewRightValue, true, updateRightValue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[1] = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName1);
				if (strRoleValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating U1 with associate rights

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navToCreateUserPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName);
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
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail,
						strEMail, strEMail, "");

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
						seleniumPrecondition, strUserName1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating U2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navToCreateUserPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						seleniumPrecondition, strUserName2, strInitPwd,
						strConfirmPwd, strUsrFulName);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName2, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating V1
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
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalue, false, strRSValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Logout
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

			// Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName2,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Updating ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
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

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdateValue, strSTvalue[1], "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Updating ST3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdateValue1, strSTvalue[2], "");
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
				String strLastUpdArr[] = selenium.getText(
						"//table/tbody/tr/td/a[text()='" + statTypeName2
								+ "']/ancestor::tr/td[last()]").split(" ");

				strUpdTime = strLastUpdArr[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime, 5,
						"HH:mm");
				strUpdTime = strAdUpdTime;
				String strCurYear = dts.getCurrentDate("yyyy");
				String strCurDateMnth = strLastUpdArr[0] + strLastUpdArr[1]
						+ strCurYear;

				strCurDate = dts.converDateFormat(strCurDateMnth, "ddMMMyyyy",
						"MM/dd/yyyy");
				Thread.sleep(360000);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Creating Mail body
			try {
				assertEquals("", strFuncResult);

				strMsgBody1 = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeName2
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "
						+ propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody1Another = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeName3
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "
						+ propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				// Creating Pager body
				strMsgBody2 = "EMResource expired status: " + strResource
						+ ". " + statTypeName2 + " status expired "
						+ strCurDate + " " + strUpdTime + ".";

				strMsgBody2Another = "EMResource expired status: "
						+ strResource + ". " + statTypeName3
						+ " status expired " + strCurDate + " " + strUpdTime
						+ ".";
				// Login to Mail

				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				// Checking Pager Notification of ST2 and ST3 in mail
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;

					for (int i = 0; i < 2; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = objMail.seleniumGetText(selenium,
									"css=div.fixed.leftAlign", 160);
							if (strMsg.equals(strMsgBody2)) {
								intPagerRes++;
								log4j
										.info(strMsgBody2
												+ " is displayed for Pager Notification");
							} else if (strMsg.equals(strMsgBody2Another)) {
								intPagerRes++;
								log4j
										.info(strMsgBody2Another
												+ " is displayed for Pager Notification");

							} else {
								log4j
										.info("Pager body  is NOT displayed for Pager Notification");
								strFuncResult = " Pager body is NOT displayed for Pager Notification";
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
					gstrReason = gstrReason + " " + strFuncResult;
				}

				// Checking ST2 and ST3 Mail Notification
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status Notification: "
							+ strResource;

					for (int i = 0; i < 4; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = objMail.seleniumGetText(selenium,
									"css=div.fixed.leftAlign", 160);
							if (strMsg.equals(strMsgBody1)) {
								intEMailRes++;
								log4j
										.info(strMsgBody1
												+ " is displayed for Email Notification");
							} else if (strMsg.equals(strMsgBody1Another)) {
								intEMailRes++;
								log4j
										.info(strMsgBody1Another
												+ " is displayed for Email Notification");
							} else {
								log4j
										.info("Email body  is NOT displayed for Email Notification");
								strFuncResult = "Email body is NOT displayed for Email Notification";
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
					gstrReason = gstrReason + " " + strFuncResult;
				}

				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad(gstrTimeOut);
				selenium.close();
				selenium.selectWindow("");
				Thread.sleep(2000);

				try {
					assertTrue(strFuncResult.equals(""));
					if (intEMailRes == 4 && intPagerRes == 2) {
						gstrResult = "PASS";
					}
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-124530";
			gstrTO = "User U1 is assigned a role R1, where R1 has view rights for status types ST1, ST2 and update rights for ST2 only. Edit role R1, provide view rights for status types ST3, ST2 and update rights for ST3 only. Verify that when the view rights of status types in a role are edited as mentioned, user U1 receives expired status notifications only ST2 and ST3 and not for ST1 when the status of these all status types expire.";
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

	// end//testFTS124530//
	//start//testFTS124529//
	/***************************************************************
	'Description		:User U1 is assigned a role R1, where R1 has view rights for status types ST1, ST2 and update rights for ST2 only. Edit role R1, provide view rights for status types ST3, ST2 and update rights for ST3 only. Verify that when the view rights of status types in a role are edited as mentioned, user U1 can only view ST2 and ST3 and not ST1 while adding status types to the custom view.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:8/30/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS124529() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRoles = new Roles();
		Date_Time_settings dts = new Date_Time_settings();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "124529"; // Test Case Id
			gstrTO = " User U1 is assigned a role R1, where R1 has view rights for status types ST1, ST2 and update rights for ST2 only. Edit role R1, provide view rights for status types ST3, ST2 and update rights for ST3 only. Verify that when the view rights of status types in a role are edited as mentioned, user U1 can only view ST2 and ST3 and not ST1 while adding status types to the custom view.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName1 = "Status_1" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Desc";

			String strSTvalue[] = new String[3];
			String strRTValue[] = new String[2];
			String strRSValue[] = new String[3];
			String strResVal = "";

			String statTypeName2 = "Status_2" + strTimeText;
			String statTypeName3 = "Status_3" + strTimeText;
			String strResrcTypName1 = "Resty_1" + strTimeText;
			String strResource1 = "Res_1" + strTimeText;
			String strAbbrv = "Ab";
			String strStandResType = "Aeromedical";
			String strContFName = "FN";
			String strContLName = "LN";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleValue = "";

			// Data for creating user1 with update resource right
			String strUserName1 = "auto1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strState = "Alabama";
			String strCountry = "Barbour County";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
		
			String strUsrFulName = strUserName1;

			/*
			 * STEP : Action:1. Create status types ST1,ST2,ST3 <br>2. Create
			 * role 'R1' selecting view right for ST1,ST2 status types,Update
			 * right for only status types ST2 <br>3. Create resource RS
			 * providing address under RT (Created selecting status types
			 * ST1,ST2,ST3) <br>4. User U1 selecting role R1 and 'View-custom
			 * view' right. <br>5. Login as user U1 navigate View >> Custom
			 * <br>6. Edit role R1 provide view rights for status types ST3, ST2
			 * and update rights for ST3 only. Expected Result:After Step5:on
			 * user login under Edit Custom View Options (Columns) Status type
			 * ST1 and ST2 are listed ST3 is not listed <br>After Step6: on user
			 * login under Edit Custom View Options (Columns) ST3 and ST2 are
			 * listed ST1 is not listed.
			 */
			// 660611

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			// Login
			try {
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User Default
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating ST1
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
						seleniumPrecondition, strStatusTypeValue,
						statTypeName1, strStatTypDefn, true);
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
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating ST2
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
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating ST3
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
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName3);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				String strRoleRights[][] = {};
				assertEquals("", strFuncResult);
				String[] strSTvalues = { strSTvalue[0], strSTvalue[1] };
				String[] UpdateRight = { strSTvalue[1] };
				strFuncResult = objRoles.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTvalues, true, UpdateRight, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName1, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 1; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(
							seleniumPrecondition, strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
						strResrcTypName1, strContFName, strContLName, strState,
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
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			// Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating Custom View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource1, strStandResType, strRegn,
						strCountry, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statTypeName3 };
				strFuncResult = objPreferences.checkSTsInEditCustViewOptions(
						selenium, strStatusType);
				try {
					assertTrue(selenium.isElementPresent("//b[text()='"
							+ statTypeName1 + "']"));
					log4j
							.info("Status Type "
									+ statTypeName1
									+ " is displayed in Edit Custom View Options (Columns)");
				} catch (AssertionError Ae) {
					strFuncResult = "Status Type "
							+ statTypeName1
							+ " is NOT displayed in Edit Custom View Options (Columns)";
					log4j
							.info("Status Type "
									+ statTypeName1
									+ " is NOT displayed in Edit Custom View Options (Columns)");
					gstrReason = strFuncResult;
				}
				try {
					assertTrue(selenium.isElementPresent("//b[text()='"
							+ statTypeName2 + "']"));
					log4j
							.info("Status Type "
									+ statTypeName2
									+ " is displayed in Edit Custom View Options (Columns)");
				} catch (AssertionError Ae) {
					strFuncResult = "Status Type "
							+ statTypeName2
							+ " is NOT displayed in Edit Custom View Options (Columns)";
					log4j
							.info("Status Type "
									+ statTypeName2
									+ " is NOT displayed in Edit Custom View Options (Columns)");
					gstrReason = strFuncResult;
				}
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
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Edit Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navEditRolesPge(selenium, strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strSTViewValue = { { strSTvalue[1], "true" }, { strSTvalue[2], "true" }};
				String[][] strSTUpdateValue = { { strSTvalue[2], "false" }};
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						selenium, false, false, strSTViewValue, strSTUpdateValue,
						true);

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
				strFuncResult = objLogin.login(	selenium, strUserName1,
						strInitPwd);
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
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource1, strStandResType, strRegn,
						strCountry, strState);
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
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statTypeName1 };
				strFuncResult = objPreferences.checkSTsInEditCustViewOptions(
						selenium, strStatusType);
				try {
					assertTrue(selenium.isElementPresent("//b[text()='"
							+ statTypeName2 + "']"));
					log4j
							.info("Status Type "
									+ statTypeName2
									+ " is displayed in Edit Custom View Options (Columns)");
				} catch (AssertionError Ae) {
					strFuncResult = "Status Type "
							+ statTypeName2
							+ " is NOT displayed in Edit Custom View Options (Columns)";
					log4j
							.info("Status Type "
									+ statTypeName2
									+ " is NOT displayed in Edit Custom View Options (Columns)");
					gstrReason = strFuncResult;
				}
				try {
					assertTrue(selenium.isElementPresent("//b[text()='"
							+ statTypeName3 + "']"));
					log4j
							.info("Status Type "
									+ statTypeName3
									+ " is displayed in Edit Custom View Options (Columns)");
				} catch (AssertionError Ae) {
					strFuncResult = "Status Type "
							+ statTypeName3
							+ " is NOT displayed in Edit Custom View Options (Columns)";
					log4j
							.info("Status Type "
									+ statTypeName3
									+ " is NOT displayed in Edit Custom View Options (Columns)");
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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-124529";
			gstrTO = "User U1 is assigned a role R1, where R1 has view rights for status types ST1, ST2 and update rights for ST2 only. Edit role R1, provide view rights for status types ST3, ST2 and update rights for ST3 only. Verify that when the view rights of status types in a role are edited as mentioned, user U1 can only view ST2 and ST3 and not ST1 while adding status types to the custom view.";
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

	// end//testFTS124529//
//start//testFTS124528//
	/***************************************************************
	'Description		:User U1 is assigned a role R1, where R1 has view rights for status types ST1, ST2 and update rights for ST2 only.
	                     Edit role R1, provide view rights for status types ST3, ST2 and update rights for ST3 only.
	                     Verify that when the view rights of status types in a role are edited as mentioned, user U1:
	                     1. Can add status change notification prefers for ST3 and ST2 and NOT for ST1.
	                     2. Can edit status change notification prefers set for ST3 and ST2 and NOT for ST1.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:9/2/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date				                Modified By
	'Date					                    Name
	***************************************************************/

	@Test
	public void testFTS124528() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		Preferences objPreferences = new Preferences();
		try {
			gstrTCID = "124528"; // Test Case Id
			gstrTO = " User U1 is assigned a role R1, where R1 has view rights for status types ST1, ST2 and update rights for ST2 only."
					+ " Edit role R1, provide view rights for status types ST3, ST2 and update rights for ST3 only."
					+ " Verify that when the view rights of status types in a role are edited as mentioned, user U1:"
					+ "1. Can add status change notification prefers for ST3 and ST2 and NOT for ST1."
					+ "2. Can edit status change notification prefers set for ST3 and ST2 and NOT for ST1.";// Test
																											// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strFILE_PATH = pathProps.getProperty("TestData_path");
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

			String strStatusNumValue = "Number";
			String statTypeName1 = "Nst_1" + strTimeText;
			String statTypeName2 = "Nst_2" + strTimeText;
			String statTypeName3 = "Nst_3" + strTimeText;
			String strStatTypDefn = "Def";
			String strStandResType = "Aeromedical";
			String strResrcTypName = "Resty_1" + strTimeText;
			String strAbbrv = "Abb";
			String strResource = "Res" + strTimeText;
			String strRoleName = "Rol_1" + strTimeText;
			String strState = "Alabama";
			String strCounty = "Barbour County";

			String strUsrFulName = "AutoUser";
			String strUserName1 = "User_1" + System.currentTimeMillis();

			String strContFName = "FA";
			String strContLName = "LA";
			String strSTvalue[] = new String[3];
			String strRoleValue[] = new String[1];
			String strRTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			/*
			 * STEP : Action:1. Create status types ST1,ST2,ST3 2. Create role
			 * 'R1' selecting view right for ST1,ST2 status types,Update right
			 * for only status types ST2 3. Create resource RS providing address
			 * under RT (Created selecting status types ST1,ST2,ST3) 4. User U1
			 * selecting role R1 and 'Edit-status change Notification 'right.
			 * 5.Login as user U1 navigate to status change notification 6. Edit
			 * role R1 provide view rights for status types ST3, ST2 and update
			 * rights for ST3 only. Expected Result:After Step5:on user login
			 * Status type ST1 and ST2 are listed ST3 is not listed >After
			 * Step6: on user login Can add/edit status change notification
			 * preference for ST3 and ST2 and NOT for ST1. (ST1 is not listed)
			 */
			// 660587

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			//Login
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
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating ST1
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
						seleniumPrecondition, strStatusNumValue, statTypeName1,
						strStatTypDefn, true);
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
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusNumValue, statTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating ST3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusNumValue, statTypeName3,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName3);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RT
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
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Selecting ST2 and ST3 in RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						seleniumPrecondition, strResrcTypName);
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

			// Creating RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadresNew(
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, strContFName, strContLName, strState,
						strCounty, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Creating R1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue = { strSTvalue[1] };
				String[][] strRoleRightss = {};
				String[] strViewRightValue = { strSTvalue[0], strSTvalue[1] };
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRightss,
						strViewRightValue, true, updateRightValue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Creating U1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navToCreateUserPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole,
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

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			//Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Navigating To Status Type change Preference
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Editing the Status Type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(
								selenium, strResource,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//Verifying ST1 and ST2
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statTypeName1, statTypeName2 };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strStatusType);
				System.out.println(strFuncResult);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Verifying ST3
			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statTypeName3 };
				strFuncResult = objPreferences
						.verifySTInEditMySTPrfPageInUncategorizedSectionFalseCond(
								selenium, strSTName);
				System.out.println(strFuncResult);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//Logout
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//Editing  R1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navEditRolesPge(selenium,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				String[][] strSTViewValue = { { strSTvalue[1], "true" },
						{ strSTvalue[2], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[2], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						selenium, false, false, strSTViewValue,
						strSTUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Logout
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strUserName1, strInitPwd);
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
           //Editing Status Type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(
								selenium, strResource,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Verifying  ST2 and ST3
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statTypeName2, statTypeName3 };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Verifying ST1
			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statTypeName1 };
				strFuncResult = objPreferences
						.verifySTInEditMySTPrfPageInUncategorizedSectionFalseCond(
								selenium, strSTName);
				System.out.println(strFuncResult);

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
			gstrTCID = "FTS-124528";
			gstrTO = "User U1 is assigned a role R1, where R1 has view rights for status types ST1, ST2 and update rights for ST2 only."
					+ " Edit role R1, provide view rights for status types ST3, ST2 and update rights for ST3 only. "
					+ "Verify that when the view rights of status types in a role are edited as mentioned, user U1:"
					+ "1. Can add status change notification prefers for ST3 and ST2 and NOT for ST1."
					+ "2. Can edit status change notification prefers set for ST3 and ST2 and NOT for ST1.";
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

	// end//testFTS124528//
}