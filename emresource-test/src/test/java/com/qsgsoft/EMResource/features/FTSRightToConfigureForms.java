package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.Roles;
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
' Requirement		:RightTConfigureForms
' Requirement Group	:Setting up users  
� Product		    :EMResource v3.19
' Date			    :4/June/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class FTSRightToConfigureForms{
	
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.settingUpRegions");
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
	Selenium selenium,seleniumPrecondition;
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
		
		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
	
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}
	
	/****************************************************************************************************************	*
	    * This function is called the teardown() function which is executed after every test.
		* The function will take care of stopping the selenium session for every test and writing the execution
		* result of the test. 
		*
	****************************************************************************************************************/

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
			seleniumPrecondition.stop();
			selenium.stop();
			
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
		

	//start//testFTS53052//
	/******************************************************************************************************
	'Description		:As a system admin, select the right 'Form - User may create and modify forms' 
						 for a role R1 and save. From another user U2 with 'Setup Roles' right, save the 
						 'Edit Role' screen of R1. Verify that when system admin opens role R1 for editing,
						 'Form - User may create and modify forms' right remains selected.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/15/2013
	'Author				:QSG
	'------------------------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	*******************************************************************************************************/

	@Test
	public void testFTS53052() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRoles = new Roles();

		try {
			gstrTCID = "53052"; // Test Case Id
			gstrTO = " As a system admin, select the right 'Form - User may create and modify forms' for a role R1 and save. From another user U2 with 'Setup Roles' right, save the 'Edit Role' screen of R1. Verify that when system admin opens role R1 for editing, 'Form - User may create and modify forms' right remains selected.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyhhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// USER
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
			// Role
			String strRolesName = "AutoRol_" + strTimeText;
			String strRoleValue = "";

			/*
			 * STEP : Action:Preconditions: 1. User U2 is created with 'Setup
			 * Roles' right. Expected Result:No Expected Result
			 */
			// 313432

			log4j.info("~~~~~TEST-CASE" + gstrTCID
					+ " PRECONDITION STARTS~~~~~");

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
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpRoles");
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
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin and navigate to Setup >> Roles.
			 * Expected Result:'Roles List' page is displayed.
			 */
			// 313457

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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New Role'. Expected Result:'Create
			 * Role' page is displayed.
			 */
			// 313458

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(
						selenium, strRolesName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Create a role R1 by selecting 'Form - User may
			 * create and modify forms' right from 'Select the Rights for this
			 * Role' section, then click on 'Save'. Expected Result:'Roles List'
			 * page is displayed
			 */
			// 313465

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(selenium,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
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
			 * STEP : Action:Logout and login as User U2 and navigate to Setup
			 * >> Roles. Expected Result:'Roles List' page is displayed.
			 */
			// 313467

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit' link associated with role R1.
			 * Expected Result:'Edit Role' page is displayed.
			 */
			// 313478

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles
						.navEditRolesPge(selenium, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Do not make any changes and save the 'Edit Role'
			 * page Expected Result:'Roles List' page is displayed
			 */
			// 313479

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals("Roles List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Roles List page is displayed");
				} catch (AssertionError Ae) {
					log4j.info("Roles List page is NOT displayed");
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Logout and login as RegAdmin and navigate to Setup
			 * >> Roles. Expected Result:'Roles List' page is displayed.
			 */
			// 313480

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit' link associated with role R1.
			 * Expected Result:'Edit Role' page is displayed. 'Form - User may
			 * create and modify forms' right remains selected.
			 */
			// 313481

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles
						.navEditRolesPge(selenium, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRight = "Form - User may create and modify forms";
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				strFuncResult = objCreateUsers
						.chkRightAdvancedOptnsSelectedOrNot(selenium,
								strOptions, true, strRight);
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
			gstrTCID = "FTS-53052";
			gstrTO = "As a system admin, select the right 'Form - User may create and modify forms' for a role R1 and save. From another user U2 with 'Setup Roles' right, save the 'Edit Role' screen of R1. Verify that when system admin opens role R1 for editing, 'Form - User may create and modify forms' right remains selected.";
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

	// end//testFTS53052//
	
	//start//testFTS53063//
	/*************************************************************************************
	'Description	:Verify that only a system admin user can assign the right 'Form -
				     User may create and modify forms' to users from 'Setup>>Setup Users'.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/15/2013
	'Author			:QSG
	'-------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************************************/

	@Test
	public void testFTS53063() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "53063";
			gstrTO = "Verify that only a system admin user can assign the right 'Form -"
					+ " User may create and modify forms' to users from 'Setup>>Setup Users'.";
			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// USER
			String strUserNameA = "AutoUsrA" + System.currentTimeMillis();
			String strUserNameB = "AutoUsrB" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserNameA;
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
		  Action:Preconditions:1. User U2 is created with 'User-Setup User Accounts' right.
		  Expected Result:No Expected Result
		*/
		//313495
			log4j.info("~~~~~TEST-CASE" + gstrTCID
					+ " PRECONDITION STARTS~~~~~");
	
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
						seleniumPrecondition, strUserNameA, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserNameB, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameB, strByRole,
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
		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to Setup >> Users.
		  Expected Result:'Users List' page is displayed.
		*/
		//313502
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
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Edit' link associated with User A. 
		  Expected Result:'Edit User' page is displayed.
		In the 'Advanced Options' section, 'Form - User may create and modify forms' right is available. 
		*/
		//313508
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserNameA,
						strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions =propElementDetails
				.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				String strRight = "'Form - User may create and modify forms'";
				strFuncResult = objCreateUsers
						.chkRightAdvancedOptnsPresentOrNot(selenium,
								strOptions, true, strRight);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Cancel'
		  Expected Result:'Users List' page is displayed.
		*/
		//313514
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.cancelAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Logout and login as User U2 and navigate to Setup >> Users.
		  Expected Result:'Users List' page is displayed.
		*/
		//313515
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Click on 'Edit' link associated with User A. 
		  Expected Result:Edit User' page is displayed.
		In the 'Advanced Options' section, 'Form - User may create and modify forms' right is not available.
	
		*/
		//313516
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
				String strOptions =propElementDetails
				.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				String strRight = "'Form - User may create and modify forms'";
				strFuncResult = objCreateUsers
						.chkRightAdvancedOptnsPresentOrNot(selenium,
								strOptions, false, strRight);
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
			gstrTCID = "FTS-53063";
			gstrTO = "Verify that only a system admin user can assign the right 'Form -"
					+ " User may create and modify forms' to users from 'Setup>>Setup Users'.";
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
	
	// end//testFTS53063//
	
	//start//testFTS53060//
	/***************************************************************
	'Description		:As a system admin, select the right 'Form - User may create and modify forms' for a user U1 and save. From another user U2 with 'User-Setup User Accounts' right, save the 'Edit User' screen of U1. Verify that when system admin opens user U1's account for editing, 'Form - User may create and modify forms' right remains selected.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:10/15/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS53060() throws Exception {
	
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
				
		try{	
		gstrTCID = "53060";	//Test Case Id	
		gstrTO = " As a system admin, select the right 'Form - User may create and modify forms' for a user U1 and save. From another user U2 with 'User-Setup User Accounts' right, save the 'Edit User' screen of U1. Verify that when system admin opens user U1's account for editing, 'Form - User may create and modify forms' right remains selected.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

		String strFILE_PATH = pathProps.getProperty("TestData_path");
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");
		
		String strLoginUserName = rdExcel.readData("Login", 3, 1);
		String strLoginPassword = rdExcel.readData("Login", 3, 2);
		String strRegn = rdExcel.readData("Login", 3, 4);

		// USER
		String strUserName = "AutoUsr" + System.currentTimeMillis();
		String strInitPwd = rdExcel.readData("Login", 4, 2);
		String strConfirmPwd = rdExcel.readData("Login", 4, 2);
		String strUsrFulName = "Auto" + strUserName;
		String strUserName1 = "AutoUsr" + System.currentTimeMillis();
		String strUsrFulName1 = "Auto" + strUserName1;
		
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
		1. User U2 is created with 'User-Setup User Accounts' right.
		  Expected Result:No Expected Result
		*/
		//313495

		log4j.info("~~~~~TEST-CASE" + gstrTCID
				+ " PRECONDITION STARTS~~~~~");
		


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
					seleniumPrecondition, strUserName, strInitPwd,
					strConfirmPwd, strUsrFulName);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String strOptions = propElementDetails
					.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
			strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
					strOptions, true);
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

		log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
		
			
		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to Setup >> Users.
		  Expected Result:'Users List' page is displayed.
		*/
		//313502

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

		/*
		* STEP :
		  Action:Click on 'Create New User'.
		  Expected Result:'Create New User' screen is displayed
		*/
		//313508

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
			
		/*
		* STEP :
		  Action:User U1 is created by filling all the mandatory data, select 'Form - User may create and modify forms' from  'Advanced Options' section, and click on 'Save'.
		  Expected Result:'Users List' page is displayed.
		*/
		//313514

		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
					strUserName1, strInitPwd, strConfirmPwd, strUsrFulName1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
			
		
		try {
			assertEquals("", strFuncResult);
			String strOptions = propElementDetails
					.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
			strFuncResult = objCreateUsers.advancedOptns(selenium,
					strOptions, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);

			strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
					selenium, strUserName1, strByRole, strByResourceType,
					strByUserInfo, strNameFormat);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		
		
		/*
		* STEP :
		  Action:Logout and login as User U2 and navigate to Setup >> Users.
		  Expected Result:'Users List' page is displayed.
		*/
		//313515

		try {

			assertEquals("", strFuncResult);
			strFuncResult = objLogin.logout(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			blnLogin = false;

			strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
					strInitPwd);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		/*
		* STEP :
		  Action:Click on 'Edit' link associated with User U1.
		  Expected Result:'Edit User' page is displayed.
		*/
		//313516

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navEditUserPge(selenium,
					strUserName1, strByRole, strByResourceType,
					strByUserInfo, strNameFormat);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
				
		/*
		* STEP :
		  Action:Do not make any changes and save the 'Edit User' page.
		  Expected Result:'Users List' page is displayed.
		*/
		//313517

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers
					.saveAndNavToUsrListPage(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		/*
		* STEP :
		  Action:Logout and login as RegAdmin and navigate to Setup >> Users.
		  Expected Result:'Users List' page is displayed.
		*/
		//313518

		
		try {

			assertEquals("", strFuncResult);
			strFuncResult = objLogin.logout(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

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
		
				
		/*
		* STEP :
		  Action:Click on 'Edit' link associated with User U1.
		  Expected Result:'Edit User' page is displayed.
				  'Form - User may create and modify forms' right remains selected from 'Advanced Options'.
		*/
		//313519
		
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
			String strRight = "Form - User may create and modify forms";
			String strOptions = propElementDetails
					.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
			strFuncResult = objCreateUsers
					.chkRightAdvancedOptnsSelectedOrNot(selenium,
							strOptions, true, strRight);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try{
			assertEquals("", strFuncResult);
			gstrResult = "PASS";
		}
		catch (AssertionError Ae)
		{
			gstrResult = "FAIL";
			gstrReason = gstrReason+" "+strFuncResult;
		}

		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "FTS-53060";
		gstrTO = "As a system admin, select the right 'Form - User may create and modify forms' for a user U1 and save. From another user U2 with 'User-Setup User Accounts' right, save the 'Edit User' screen of U1. Verify that when system admin opens user U1's account for editing, 'Form - User may create and modify forms' right remains selected.";
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

	//end//testFTS53060//	
}
