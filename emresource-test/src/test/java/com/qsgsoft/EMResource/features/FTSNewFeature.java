package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**********************************************************************
' Description		:This class contains test cases from   requirement
' Requirement		:Creating & managing users 
' Requirement Group	:New feature for 3.15
� Product		    :EMResource v3.19
' Date			    :10/July/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class FTSNewFeature {
	
		Date dtStartDate;
		ReadData rdExcel;
		static Logger log4j = Logger
				.getLogger("com.qsgsoft.EMResource.features.FTSNewFeature");
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
		Selenium selenium;
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
		

	/***************************************************************
	'Description		:Verify that only RegAdmin has the ability to assign/unassign 
	                     the right 'Form - User may create and modify forms' to users.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/10/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS21521() throws Exception {
		try {

			Login objLogin = new Login();// object of class Login
			CreateUsers objCreateUsers = new CreateUsers();// object of class
			gstrTCID = "21521"; // Test Case Id
			gstrTO = " Verify that only RegAdmin has the ability to assign/unassign the right 'Form - User may create and modify forms' to users.";// Test
																																					// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
			String strFuncResult = "";
			String strLoginUserName = "";
			String strLoginPassword = "";
			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login", 3, 4);

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			/*
			* STEP :
			  Action:Login as any user with the right 'User - Setup User Accounts'.
			  Expected Result:No Expected Result
			*/
			//126869
			
			try {
				assertEquals("", strFuncResult);
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"),
								true);
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
			* STEP :
			  Action:Navigate to 'Setup >> Users' and click on 'Create New User' and expand the 'Advanced Options'.
			  Expected Result:'Form - User may create and modify forms' right is not available.
			*/
			//126870
			
			try {
				assertEquals("", strFuncResult);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToCreateUserPage(selenium);
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
				strFuncResult = objCreateUsers.cancelAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			* STEP :
			  Action:Click on 'Edit' associated with any user and expand the 'Advanced Options'.
			  Expected Result:'Form - User may create and modify forms' right is not available.
			*/
			//126871
			
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
				strFuncResult = objCreateUsers.cancelAndNavToUsrListPage(selenium);
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
			  Action:Login as RegAdmin and Navigate to 'Setup >> Users'.
			  Expected Result:No Expected Result
			*/
			//103942
			try {
				assertEquals("", strFuncResult);
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
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

			/*
			* STEP :
			  Action:Click on 'Create New User' and expand the 'Advanced Options'.
			  Expected Result:'Form - User may create and modify forms' right is available with the checkbox to select/deselect.
			*/
			//103943
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToCreateUserPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strOptions=propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				String strRight="'Form - User may create and modify forms'";
				strFuncResult = objCreateUsers.chkRightAdvancedOptnsSelectedOrNot(selenium, strOptions, false,strRight);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strOptions =propElementDetails
				.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				String strRight="'Form - User may create and modify forms'";
				strFuncResult = objCreateUsers.chkRightAdvancedOptnsPresentOrNot(selenium, strOptions, true, strRight);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.cancelAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			* STEP :
			  Action:Click on 'Edit' associated with any user and expand the 'Advanced Options'.
			  Expected Result:'Form - User may create and modify forms' right is available with the checkbox to select/deselect.
			*/
			//103944
			
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
				String strOptions=propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				String strRight="'Form - User may create and modify forms'";
				strFuncResult = objCreateUsers.chkRightAdvancedOptnsSelectedOrNot(selenium, strOptions, false,strRight);
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
			gstrTCID = "21521";
			gstrTO = "Verify that only RegAdmin has the ability to assign/unassign the right 'Form - User may create and modify forms' to users.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
			+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		   }
		}		

}
