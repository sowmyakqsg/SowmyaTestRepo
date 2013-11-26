	package com.qsgsoft.EMResource.features;
	import static org.junit.Assert.*;

import java.util.Date;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.EventList;
import com.qsgsoft.EMResource.shared.EventSetup;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.Preferences;
import com.qsgsoft.EMResource.shared.ResourceTypes;
import com.qsgsoft.EMResource.shared.Resources;
import com.qsgsoft.EMResource.shared.Roles;
import com.qsgsoft.EMResource.shared.SearchUserByDiffCrteria;
import com.qsgsoft.EMResource.shared.StatusTypes;
import com.qsgsoft.EMResource.shared.ViewMap;
import com.qsgsoft.EMResource.shared.Views;
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
	' Description		:This class contains test cases from ‘Req5387’  requirement
	' Requirement		:'View Resource' right
	' Requirement Group	:Setting up users
	� Product			:EMResource v3.17
	' Date				:29-05-2012
	' Author			:QSG
	-----------------------------------------------------------------------
	' Modified Date							Modified By
	' Date									Name
	'*******************************************************************/
	public class ViewResourceRight {
		// Log4j object to write log entries to the Log files
		static Logger log4j = Logger
				.getLogger("com.qsgsoft.EMResource.features.ViewResourceRight");
		static {
			BasicConfigurator.configure();
		}
	//Objects to access the common functions
	OfficeCommonFunctions objOFC;
		ReadData objrdExcel;
	
	/*Global variables to store the test case details – TestCaseID, Test Objective,Result,
	Reason for failure */
	String gstrTCID, gstrTO, gstrResult, gstrReason;
	
	//Selenium Object
	Selenium selenium;
	public Properties propElementDetails; //Property variable for ElementID file
	public Properties propEnvDetails;//Property variable for Environment data
	public Properties pathProps;
	public static Properties browserProps = new Properties();
	public static String gstrBrowserName;//Variable to store browser name
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;//Result Variables
	private String browser;
	Selenium seleniumPrecondition;
	double gdbTimeTaken; //Variable to store the time taken
	
	public static Date gdtStartDate;// Date variable
	
	public static long sysDateTime;
	public static long gsysDateTime=0;
	public static String gstrTimeOut="";
	public static String sessionId_FF36,sessionId_FF20,sessionId_FF3,sessionId_FF35,sessionId_IE6,sessionId_IE7,
	    sessionId_IE8,session_SF3,session_SF4,session_op9,session_op10,session_GC,StrSessionId="",StrSessionId1,StrSessionId2;
	/****************************************************************************************************************
		* This function is called the setup() function which is executed before every test.
		*
		* The function will take care of creating a new selenium session for every test
		*
	****************************************************************************************************************/
	@Before
	public void setUp() throws Exception {

	//Create object to read environment properties file
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	propEnvDetails = objReadEnvironment.readEnvironment();
	
	Paths_Properties objAP = new Paths_Properties();
	pathProps = objAP.Read_FilePath();
	
	//create an object to refer to properties file
	ElementId_properties objelementProp = new ElementId_properties();
	propElementDetails = objelementProp.ElementId_FilePath();
	
	//Retrieve browser information
	browser=propEnvDetails.getProperty("Browser");
	gstrBrowserName=propEnvDetails.getProperty("Browser").substring(1)+" "+propEnvDetails.getProperty("BrowserVersion");
	//Retrieve the value of page load time limit
	gstrTimeOut=propEnvDetails.getProperty("TimeOut");
	
	selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
			4444, this.browser, propEnvDetails.getProperty("urlEU"));	
	
	seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
			4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails.getProperty("urlEU"));	
	
	//create an object to refer to Element ID properties file
	//Start Selenium RC
	selenium.start();
	//Maximize the window
	selenium.windowMaximize();
	selenium.setTimeout("");
	
	seleniumPrecondition.start();
	seleniumPrecondition.windowMaximize();
	seleniumPrecondition.setTimeout("");
	//Define object to call support functions to read excel, date etc
	gdtStartDate = new Date();
	objOFC = new OfficeCommonFunctions();
	objrdExcel = new ReadData();

   }
	/****************************************************************************************************************	* This function is called the teardown() function which is executed after every test.
		*
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
	selenium.stop();
	
	try{
		seleniumPrecondition.close();
	}catch(Exception e){
		
	}
	seleniumPrecondition.stop();
	
	// determine log message
	if (gstrResult.toUpperCase().equals("PASS"))
	{
		log4j.info("-------------------Test Case Execution " + gstrTCID
		+ " has PASSED------------------");
	} else if (gstrResult.toUpperCase().equals("SKIP"))
	{
		log4j.info("-------------------Test Case Execution " + gstrTCID
		+ " was SKIPPED------------------");
	} else
	{
		log4j.info("-------------------Test Case Execution " + gstrTCID
						+ " has FAILED------------------");
	}

	//Retrieve the path of the Result file
	String FILE_PATH = "";
	Paths_Properties objAP = new Paths_Properties();
	Properties pathProps = objAP.Read_FilePath();
	FILE_PATH = pathProps.getProperty("Resultpath");
	// Retrieve the total execution time
	gdbTimeTaken = objOFC.TimeTaken(gdtStartDate);
	Date_Time_settings dts = new Date_Time_settings();
	//Get the current date
	gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");
	//Get the Build ID of the application
	gstrBuild = propEnvDetails.getProperty("Build");
	//Check if result should be written to Excel or Test Management Tool
	String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
	boolean blnwriteres = blnresult.equals("true");

	gstrReason=gstrReason.replaceAll("'", " ");
	//Write Result of the test.
	objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
					gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
					sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);

  }



	/***************************************************************
	'Description	:Verify that a user CANNOT be saved by selecting only the 'Update Status'/'Run Reports'/'
	                 Associated With' right on a resource without the ‘View Resource’ right.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:29-05-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	@Test
	public void testBQS63726() throws Exception {
		try {
			gstrTCID = "63726"; // Test Case Id
			gstrTO = " Verify that a user CANNOT be saved by selecting only the 'Update Status'/'Run Reports'/'" +
					"Associated With' right on a resource without the ‘View Resource’ right.";// Test																																															// Objective
			gstrReason = "";
			gstrResult = "FAIL";			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();			
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			ReadData objReadData = new ReadData();
			Login objLogin = new Login();// object of class Login
			CreateUsers objCreateUsr = new CreateUsers();
			Resources objRes = new Resources();
			SearchUserByDiffCrteria objSearchUsr = new SearchUserByDiffCrteria();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = objReadData.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = objReadData.readInfoExcel(
					"Precondition", 3, 10, strFILE_PATH);
			String strByUserInfo = objReadData.readInfoExcel("Precondition", 3,
					11, strFILE_PATH);
			String strNameFormat = objReadData.readInfoExcel("Precondition", 4,
					12, strFILE_PATH);
			// login details
			String strLoginUserName ="AutoLUsr"+System.currentTimeMillis();
			String strPassword = "abc123";
			String strInitialPwd =strPassword;
			String strUsrFulName = "AutoTestUser";	
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strResRights[] = { "association", "updateRight",
					"reportRight", "viewRight" };
			String strStatusTypeValue = "Number";
			String statTypeName = "AutoNSt_" + strTimeText;
			String strStatTypDefn = "Auto";			
			String strFailMsg="";
			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];			
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";			
			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
			/*
			 * STEP : Action:User U1 has the following rights <br>1. Users-Setup
			 * User Accounts <br>2. Setup Resources <br>3. More than one
			 * resource exists in the test region Expected Result:No Expected
			 * Result
			 */
			// 375380
				
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			/*
			 * STEP : Action:Login as user U1 and navigate to Setup>>Users
			 * Expected Result:No Expected Result
			 */
			// 375381
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(seleniumPrecondition, strAdmUserName,
						strAdmPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition, strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResource);

				if (strResVal.compareTo("") != 0) {
					strFailMsg = "";
					strRSValue[0] = strResVal;
				} else {
					strFailMsg = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strLoginUserName, strPassword, strPassword,
						strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.Advoptn.SetUPResources"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strLoginUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.newUsrLogin(selenium, strLoginUserName,
						strPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}			
		
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			/*
			 * STEP : Action:Select to create a new user Expected Result:'Create
			 * New User' screen is displayed By default, the 'View Resource'
			 * checkbox is selected corresponding to all existing resources in
			 * that region.
			 */
			// 375382			

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.fillUsrMandatoryFlds(selenium,
						strUserName, strInitialPwd, strInitialPwd,
						strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			/*
			 * STEP : Action:Provide mandatory data, under the 'Resource Rights'
			 * section, select only 'Update Status' checkbox corresponding to
			 * resource RS and save the user Expected Result:An appropriate
			 * error is displayed stating,
			 * "The following error occurred on this page: A user that has any of Associated With, Update Status, or Run Reports for a resource must have View Resource."
			 */
			// 375383
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.verifyViewResourceRight(selenium,
						"SELECT_ALL", "viewRight", true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectAndDeselectAllResRight(
						selenium, "viewRight", false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			// select Update Status
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectResourceRights(selenium,
						strResource, false, true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.saveAndCheckErrMsgForUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			/*
			 * STEP : Action:Deselect previously selected 'Update Status' right
			 * and select only 'Run Reports' checkbox corresponding to resource
			 * RS and save the user Expected Result:An appropriate error is
			 * displayed stating,
			 * "The following error occurred on this page: A user that has any of Associated With, Update Status, or Run Reports for a resource must have View Resource."
			 */
			// 375384
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.fillOnlyCreateUsrMandatFlds(selenium,
						strUserName, strInitialPwd, strInitialPwd,
						strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectAndDeselectAllResRight(
						selenium, "viewRight", false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			// select Run Report
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectResourceRights(selenium,
						strResource, false, false, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.saveAndCheckErrMsgForUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			/*
			 * STEP : Action:Deselect previously selected 'Run Reports' right
			 * and select only 'Associated with' checkbox corresponding to
			 * resource RS and save the user Expected Result:An appropriate
			 * error is displayed stating,
			 * "The following error occurred on this page: A user that has any of Associated With, Update Status, or Run Reports for a resource must have View Resource."
			 */
			// 375385
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.fillOnlyCreateUsrMandatFlds(selenium,
						strUserName, strInitialPwd, strInitialPwd,
						strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectAndDeselectAllResRight(
						selenium, "viewRight", false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			// select Run Report
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectResourceRights(selenium,
						strResource, true, false, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.saveAndCheckErrMsgForUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			/*
			 * STEP : Action:Select all the 3; 'Update Status', 'Run Reports'
			 * and 'Associated with' checkboxes corresponding to resource RS and
			 * save the user Expected Result:An appropriate error is displayed
			 * stating,
			 * "The following error occurred on this page: A user that has any of Associated With, Update Status, or Run Reports for a resource must have View Resource."
			 */
			// 375386
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.fillOnlyCreateUsrMandatFlds(selenium,
						strUserName, strInitialPwd, strInitialPwd,
						strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectAndDeselectAllResRight(
						selenium, "viewRight", false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			// select All 3
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectResourceRights(selenium,
						strResource, true, true, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.saveAndCheckErrMsgForUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			/*
			 * STEP : Action:Select all the 4; 'Update Status', 'Run Reports',
			 * 'Associated with' and 'View Resource' checkboxes corresponding to
			 * resource RS and save the user Expected Result:User is saved and
			 * is listed in the 'Users List' screen.
			 */
			// 375387
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.fillOnlyCreateUsrMandatFlds(selenium,
						strUserName, strInitialPwd, strInitialPwd,
						strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectAndDeselectAllResRight(
						selenium, "viewRight", false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectResourceRights(selenium,
						strResource, true, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objCreateUsers.savVrfyUserWithSearchUser(
							selenium, strUserName, strByRole, strByResourceType,
							strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
			}
			

			/*
			 * STEP : Action:Navigate to Setup>>Users, select to edit any user
			 * U1 Expected Result:'Edit User' screen is displayed.
			 */
			// 375388
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objSearchUsr.searchUserByDifCriteria(selenium,
						strLoginUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.navToEditUserPage(selenium,
						strLoginUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			/*
			 * STEP : Action:Select only 'Update Status' checkbox corresponding
			 * to resource RS under the 'Resource Rights' section and save the
			 * user Expected Result:An appropriate error is displayed stating,
			 * "The following error occurred on this page: A user that has any of Associated With, Update Status, or Run Reports for a resource must have View Resource."
			 */
			// 375389

			// select Update Status
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectResourceRights(selenium,
						strResource, false, true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.saveAndCheckErrMsgForUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			/*
			 * STEP : Action:Deselect previously selected 'Update Status' right
			 * and select only 'Run Reports' checkbox corresponding to resource
			 * RS and save the user Expected Result:An appropriate error is
			 * displayed stating,
			 * "The following error occurred on this page: A user that has any of Associated With, Update Status, or Run Reports for a resource must have View Resource."
			 */
			// 375390

			// select Run Report
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectResourceRights(selenium,
						strResource, false, false, true, false);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.saveAndCheckErrMsgForUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			/*
			 * STEP : Action:Deselect previously selected 'Run Reports' right
			 * and select only 'Associated with' checkbox corresponding to
			 * resource RS and save the user Expected Result:An appropriate
			 * error is displayed stating,
			 * "The following error occurred on this page: A user that has any of Associated With, Update Status, or Run Reports for a resource must have View Resource."
			 */
			// 375391

			// select Associated with
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectResourceRights(selenium,
						strResource, true, false, false, false);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.saveAndCheckErrMsgForUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			/*
			 * STEP : Action:Select all the 3; 'Update Status', 'Run Reports'
			 * and 'Associated with' checkboxes corresponding to resource RS and
			 * save the user Expected Result:An appropriate error is displayed
			 * stating,
			 * "The following error occurred on this page: A user that has any of Associated With, Update Status, or Run Reports for a resource must have View Resource."
			 */
			// 375392
			// select All 3
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectResourceRights(selenium,
						strResource, true, true, true, false);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.saveAndCheckErrMsgForUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			/*
			 * STEP : Action:Select all the 4; 'Update Status', 'Run Reports',
			 * 'Associated with' and 'View Resource' checkboxes corresponding to
			 * resource RS and save the user Expected Result:User is saved and
			 * is listed in the 'Users List' screen.
			 */
			// 375393
			// select All 4
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectResourceRights(selenium,
						strResource, true, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.savVrfyUserWithSearchUser(selenium, strLoginUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			/*
			 * STEP : Action:Navigate to Setup>>Resources, click on 'Users' link
			 * corresponding to resource RS Expected Result:All the 4 rights
			 * ('Update Status', 'Run Reports', 'Associated with' and 'View
			 * Resource') are selected.
			 */
			// 375394

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objRes.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.navToUsersFromResourceListPage(
						selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			// verify all 4 are selected
			for (String strRes : strResRights) {
				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg = objCreateUsr.verifyViewResourceRight(selenium,
							strRes, strLoginUserName, true);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFailMsg;
				}
			}
			/*
			 * STEP : Action:Navigate to Setup>>Users, select to edit user U1
			 * Expected Result:All the 4 rights ('Update Status', 'Run Reports',
			 * 'Associated with' and 'View Resource') remains selected.
			 */
			// 375395
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objSearchUsr.searchUserByDifCriteria(selenium,
						strLoginUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.navToEditUserPage(selenium,
						strLoginUserName);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			// verify all 4 are selected
			for (String strRes : strResRights) {
				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg = objCreateUsr.verifyViewResourceRight(selenium,
							strRes, strRSValue[0], true);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFailMsg;
				}
			}
			/*
			 * STEP : Action:Select only 'Update Status' and 'View Resource'
			 * checkboxes corresponding to resource RS and save the user
			 * Expected Result:User is saved
			 */
			// 375396

			// select Update Status and View Resource
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectResourceRights(selenium,
						strResource, false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.savVrfyUserWithSearchUser(selenium, strLoginUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			/*
			 * STEP : Action:Select to edit user U1 again and select only 'Run
			 * Reports' and 'View Resource' checkboxes corresponding to resource
			 * RS and save the user Expected Result:User is saved
			 */
			// 375397
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objSearchUsr.searchUserByDifCriteria(selenium,
						strLoginUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.navToEditUserPage(selenium,
						strLoginUserName);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}

			// select Run Reports and View Resource
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectResourceRights(selenium,
						strResource, false, false, true, true);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.savVrfyUserWithSearchUser(selenium, strLoginUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			/*
			 * STEP : Action:Select to edit user U1 again and select only
			 * 'Associated with' and 'View Resource' checkboxes corresponding to
			 * resource RS and save the user Expected Result:User is saved
			 */
			// 375398
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objSearchUsr.searchUserByDifCriteria(selenium,
						strLoginUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.navToEditUserPage(selenium,
						strLoginUserName);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}

			// select Associated with and View Resource
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.selectResourceRights(selenium,
						strResource, true, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objCreateUsr.savVrfyUserWithSearchUser(selenium, strLoginUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-63726";
			gstrTO = "Verify that a user CANNOT be saved by selecting only the 'Update Status'/'Run Reports'/'Associated With' right on a resource without the ‘View Resource’ right.";
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
	'Description		:Verify that when the 'View Resource' right on a resource is removed for a
	                      user, the user cannot view the resource on all the view screens.
	'Precondition		:1. User U1 is assigned to view other region view of RG1 from RG2.
	                     2. User U1 has 'Setup resources' right in region RG2.
                         3. In region RG1, RS1 is created under RT1 providing address and is shared.
						 4. In RG1, the region default view includes RS1. 		
	'Arguments			:None
	'Returns			:None
	'Date				:6/7/2012
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	@Test
	public void testBQS48268() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		EventSetup objEventSetup = new EventSetup();
		Preferences objPreferences = new Preferences();
		EventList objEventList = new EventList();
		try {
			gstrTCID = "48268"; // Test Case Id
			gstrTO = "Verify that when the 'View Resource' right on a resource is removed for a"
					+ "user, the user cannot view the resource on all the view screens.";
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");			
			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[2];
			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Brown County";
			String strResVal = "";
			String strRSValues[] = new String[1];
			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUserName1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			String strOptions = "";
			// search user data
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";
			String strTempName = "Autotmp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "Automation";
			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";
			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "";
			String strViewType = "";

			/*
			 * Preconditions: 1. Resource RS is created providing address. 2. Rs
			 * is associated with a role based status type ST1 and a private
			 * status type ST2. 3. User U1 has 'View Resource' and affiliated
			 * resource rights on RS. 4. User U1 has view rights to view all the
			 * associated status types of resource RS. 5. Resource RS and status
			 * types ST1 and ST2 are added to region view V1. 6. User U1 has
			 * added RS and status types ST1 and ST2 to his/her custom view. 7.
			 * Event E1 is created with resource RS and status types ST1 and
			 * ST2. .
			 */

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
			// ST2
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
				seleniumPrecondition.click("css=input['name=visibility'][value='PRIVATE']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName2);
				if (strStatusTypeValues[1].compareTo("") != 0) {
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

			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrcTypName, "FN",
						"LN", strState, strCountry, "Hospital");
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

				strVewDescription = "Automation";
				strViewType = "Resource (Resources and status types as rows. Status,"
						+ " comments and timestamps as columns.)";

				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strStatusTypeValues, false, strRSValues);

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
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName, strRoleRights, strViewRightValue, false,
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strRSValue = strResVal;
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue, true, false, false,
						true);
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
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
				String[] strStatusTypeval = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
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
				String[] strStatusType = { statTypeName2 };
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

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * Login as any user with 'User - Setup User Accounts right' and
			 * navigate to Setup>>Users 'Users List' screen is displayed
			 */
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
			/*
			 * STEP : Select to edit user U1 'Edit User' screen is displayed
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * Deselect the 'View Resource' (and all other affiliated resource
			 * rights) checkbox corresponding to resource RS, and save the user
			 * 'Users List' screen is displayed
			 */
			try {
				assertEquals("", strFuncResult);
				String strRSValue = strResVal;
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue, false, false, false,
						false);
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
			 * Login as user U1 and navigate to View>>V1 Resource RS is not
			 * displayed on the view screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
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

				String[] strResources = { strResource };
				strFuncResult = objViews.checkResTypeAndResInUserViewFalseCond(
						selenium, strViewName_1, strResrcTypName, strResources,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : navigate to View>>Custom Resource RS is not displayed on
			 * the 'Custom view - Table' screen.
			 */
			// 425617

			/*try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navViewCustomTable(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}*/

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = {};
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTabl(
						selenium, strResrcTypName, strResource, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Click on 'Show Map' RS is not displayed on the Map area
			 * and in the 'Find Resource' dropdown
			 */
			
			try {
				assertEquals("Resource '"
							+ strResource
							+ "' is NOT displayed on the 'Cusotm View table' screen under "
							+ strResrcTypName + " along with all the status types.", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			/*try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap
						.navToCustomViewMapFromShowMap(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}*/

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verRSInFindResDropDown(selenium,
						strResource, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Click on the 'Customize' link at the top right of the
			 * screen RS is not displayed in the resource section
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
				assertFalse(selenium.isTextPresent(strResource));
				log4j.info(strResource
						+ "  is not displayed in the resource section ");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Click on 'Options' ST1 and ST2 are not displayed in the
			 * 'Edit Custom View Options (Columns)' screen
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
				strFuncResult = objViews.checkSTInEditCustViewOptionsfalseCond(
						selenium, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSTInEditCustViewOptionsfalseCond(
						selenium, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : navigate to View>>Map RS is not displayed on the Map area
			 * and in the 'Find Resource' dropdown
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
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
			 * STEP : Click on the event banner of E1 RS is not displayed on the
			 * 'Event Status' screen
			 */

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = {};
				strFuncResult = objEventList.chkRSAndSTInEventBannerfalseCndtn(
						selenium, strEveName, strResrcTypName, strResource,
						strStatTypeArr);
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
				strTestData[2] = strUserName1 + "/" + strInitPwd + ""
						+ strUserName + "/" + strInitPwd;
				strTestData[3] = statTypeName1 + statTypeName2;
				strTestData[4] = strViewName_1;
				strTestData[5] = "Verify from 12th step";
				strTestData[6] = strResource;
				strTestData[8] = strEveName;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "48268"; // Test Case Id
			gstrTO = "Verify that when the 'View Resource' right on a resource is removed for a"
					+ "user, the user cannot view the resource on all the view screens.";// Test
																							// Objective
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

