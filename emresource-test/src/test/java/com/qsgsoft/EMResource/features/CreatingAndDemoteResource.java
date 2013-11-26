package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/********************************************************************
' Description		:This class contains test cases from  requirement
' Requirement		:Creating and Demote Resource
' Requirement Group	:Resource Hierarchies
' Product		    :EMResource v3.21
' Date			    :7/8/2013
' Author		    :QSG
' ------------------------------------------------------------------
' Modified Date				                       Modified By
' Date					                           Name
'*******************************************************************/

public class CreatingAndDemoteResource {

	// Log4j object to write log entries to the Log files
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.CreatingAndDemoteResource");
	static {
		BasicConfigurator.configure();
	}
	// Objects to access the common functions
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	String gstrTCID, gstrTO, gstrResult, gstrReason;
	// Selenium Object
	Selenium selenium, seleniumPrecondition;
	// Object for date time settings
	Date_Time_settings dts = new Date_Time_settings();

	public Properties propElementDetails;
	public Properties propEnvDetails;
	public Properties propPathDetails;
	public Properties propAutoITDetails;
	public static Properties browserProps = new Properties();
	public static String gstrBrowserName;
	public static String gstrTimetaken, gstrdate, gstrtime, gstrBuild;
	double gdbTimeTaken;
	public static Date gdtStartDate;
	public static long sysDateTime;
	public static long gsysDateTime = 0;
	public static String gstrTimeOut = "";
	public static String sessionId_FF36, sessionId_FF20, sessionId_FF3,
			sessionId_FF35, sessionId_IE6, sessionId_IE7, sessionId_IE8,
			session_SF3, session_SF4, session_op9, session_op10, session_GC,
			StrSessionId = "", StrSessionId1, StrSessionId2;

	/******************************************************************
	 * This function is called the setup() function which is executed 
	 * before every test. 
	 * The function will take care of creating a new selenium session
	 *  for every test
	 *****************************************************************/
	@Before
	public void setUp() throws Exception {

		Paths_Properties objPathsProps = new Paths_Properties();
		propAutoITDetails = objPathsProps.ReadAutoit_FilePath();

		propPathDetails = objPathsProps.Read_FilePath();

		// Create object to read environment properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		// Retrieve the value of page load time limit
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		// create an object to refer to Element ID properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		// Create a new selenium session
		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));
		// Start Selenium RC
		selenium.start();
		// Maximize the window
		selenium.windowMaximize();
		selenium.setTimeout(propEnvDetails.getProperty("TimeOut"));

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		// Define object to call support functions to read excel, date etc
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();

	}
	
	/*****************************************************************************************
	 * 	* This function is called the teardown() function which is executed after every test.
	 * The function will take care of stopping the selenium session for every test and writing
	 * the execution result of the test. 
	 *
	 ******************************************************************************************/

	@After
	public void tearDown() throws Exception {
		// kill browser
		selenium.close();
		selenium.stop();
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

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

		// Retrieve the path of the Result file
		String FILE_PATH = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		FILE_PATH = pathProps.getProperty("Resultpath");
		// Retrieve the total execution time
		gdbTimeTaken = objOFC.TimeTaken(gdtStartDate);
		Date_Time_settings dts = new Date_Time_settings();
		// Get the current date
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");
		// Get the Build ID of the application
		gstrBuild = propEnvDetails.getProperty("Build");
		// Check if result should be written to Excel or Test Management Tool
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");
		// Write Result of the test.
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetaken, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild, StrSessionId);
	}

	//start//testBQS107519//
	/***************************************************************
	'Description	:Verify that only RegAdmin can Demote a resource.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/8/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				                     Modified By
	'Date				                           	 Name
	***************************************************************/

	@Test
	public void testBQS107519() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		General objGeneral = new General();
		try {
			gstrTCID = "107519"; // Test Case Id
			gstrTO = " Verify that only RegAdmin can Demote a resource.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetaken = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = propPathDetails.getProperty("TestData_path");

			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strStatValue = "";
			String strSTvalue[] = new String[2];

			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strRTValue[] = new String[2];
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strResVal = "";
			String strRSValue[] = new String[3];

			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Preconditions:
			 * 1. Status type ST1 and ST2 are created in region RG1.
			 * 2. Resource Type (Normal) RT1 is created selecting status typeST1.
			 * 3. Resource RS1 is created selecting RT1.
			 * 4. Resource RS2 is created selecting RT1.
			 * 5. Sub-Resource SRT1 is created selecting status type ST2.
			 * 6. User 'U1' is created in region 'RG1' with 'Setup resources'
			 * right. Expected Result:No Expected Result
			 */
			// 609407
			
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				blnLogin = true;
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
						seleniumPrecondition, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
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
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
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
			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
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
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
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
				strFuncResult = objResources
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource2);
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
			// SRT
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
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {
					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin to region 'RG1' Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 609408
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
				blnLogin=true;
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
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 * 
			 * 'Edit' , 'Status Types' , 'Users' , 'Sub-Resources' , 'Demote'
			 * link are present next to resources.
			 * 
			 * 'Create New Resource' button is present at top left corner.
			 */
			// 609409
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyLinksUndrActionInRSListPge(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateRes.CreateNewResource")));
				log4j.info("'Create New Resource' button is present at top left corner");
			} catch (AssertionError Ae) {
				strFuncResult="'Create New Resource' button is Not present at top left corner ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Demote' link associated with resource
			 * 'RS1' Expected Result:'Demote Resource -- < Resource Name >'
			 * screen is displayed with following fields:
			 * Name: (Selected resource Name is displayed by default and field
			 * is disabled)
			 * Resource Type: (Drop down with 'Sub-resource' types under it)
			 * Select the parent resource:
			 * (Table with list of Resource Name and associated Resource Type
			 * and check box associated with each resource name)
			 */
			// 609410
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToDemoteResourcePage(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyFieldsInDemoteResScreen(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select Sub-Resource 'SRT1' from 'Resource Type'
			 * drop down and select check box corresponding to resource 'RS2'
			 * Click on 'Save'. Expected Result:'Resource List' screen is
			 * displayed.
			 * 
			 * Resource 'RS1' is not listed under it.
			 */
			// 609411
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchRSByDifCriteriaInDemoteResourcePage(selenium,
								strSubResrctTypName, "(Any Resource Type)",
								"Resource Name", "Equals", strResource2,
								strRSValue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(selenium,
						strResource1);
				if (strFuncResult.equals("Resource " + strResource1
						+ " is NOT displayed in Resource List page ")) {
					strFuncResult = "";
					log4j.info("Resource '" + strResource1
							+ "' is NOT listed under it. ");
				} else {
					strFuncResult = "Resource '" + strResource1
							+ "' is listed under it. ";
					log4j.info("Resource '" + strResource1
							+ "' is listed under it. ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
						+ strResource2 + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Sub-Resources' link associated with
			 * resource 'RS2'. Expected Result:'Sub-Resource List for Resource <
			 * Resource Name >' screen is displayed.
			 * 
			 * Sub-Resource 'RS1' is displayed under parent Resource 'RS2'.
			 * 
			 * 'Create New Sub-Resource' button present at top left corner
			 */
			// 609412
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table"
						+ "[@summary='Sub-Resources']/tbody/tr/td[3]"
						+ "[text()='" + strResource1
						+ "']/following-sibling::td[2]" + "[text()='"
						+ strSubResrctTypName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Sub-Resource '" + strResource1
							+ "' is displayed under parent Resource 'RS2'. ");
				} else {
					strFuncResult = "Sub-Resource '"
							+ strResource1
							+ "' is NOT displayed under parent Resource 'RS2'. ";
					log4j
					.info("Sub-Resource '"
							+ strResource1
							+ "' is NOT displayed under parent Resource 'RS2'. ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateResource.ValuesubResource")));
				log4j.info("'Create New Sub Resource' button is present at top left corner");
			} catch (AssertionError Ae) {
				strFuncResult="'Create New Sub Resource' button is Not present at top left corner ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as user 'U1' Expected Result:'Region Default'
			 * screen is displayed.
			 */
			// 609417
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resources List' screen is displayed.
			 * 
			 * 'Demote' link is not available for resources.
			 * 
			 * 'Edit' and 'Sub-Resource' link is associated with resources.
			 */
			// 609418
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/thead"
								+ "/tr/th[1][text()='Action']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource2
								+ "']/parent::tr/td[1]/a[1][text()='Edit']"));
				log4j.info("Edit link is present under Action coloumn for the resources");
			} catch (AssertionError Ae) {
				strFuncResult="'Edit link is Not present under Action coloumn for the resources";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource2
								+ "']/parent::tr/td[1]/a[text()='Sub-Resources']"));
				log4j.info("SubResources link is present under Action coloumn");
			} catch (AssertionError Ae) {
				strFuncResult="SubResources link is Not present under Action coloumn";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource2
								+ "']/parent::tr/td[1]/a[5][text()='Demote']"));

				log4j.info("Demote link is Not present under Action coloumn");
			} catch (AssertionError Ae) {
				strFuncResult="Demote link is present under Action coloumn ";
				log4j.info(strFuncResult);
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
			gstrTCID = "BQS-107519";
			gstrTO = "Verify that only RegAdmin can Demote a resource.";
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

	//end//testBQS107519//
	
	//start//testBQS107525//
	/**********************************************************************************************
	'Description	:Verify that sub-resources are not available while creating/editing a resource.
	'Arguments		:None
	'Returns		:None
	'Date			:7/11/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------------
	'Modified Date			                                                   	       Modified By
	'Date					                                                           Name
	 **********************************************************************************************/

	@Test
	public void testBQS123342() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		try {
			gstrTCID = "123342"; // Test Case Id
			gstrTO = " Verify that sub-resources are not available while creating/editing a resource.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetaken = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strStatValue = "";
			String strSTvalue[] = new String[2];

			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strRTValue[] = new String[2];
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strResVal = "";
			String strRSValue[] = new String[3];

			String strState = "Alabama";
			String strCountry = "Barbour County";

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION STARTS~~~~~");
		
		/*
		 * STEP : Action:1. Status type ST1 and ST2 are created in region RG1.
		 * 2. Resource Type (Normal) 'RT1'is created selecting status type 'ST1' and 'ST2'.
		 * 3. Sub-Resource Type 'SRT1' is created selecting status type 'ST2'.
		 * 4. Resource 'RS1' is created under 'RT1'
		 * 5. Sub-resource 'SRS1' is created selecting sub-resource type
		 * 'SRT1' under parent resource 'RS1' Expected Result:No Expected
		 * Result
		 */
		// 609594

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				blnLogin = true;
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
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
						seleniumPrecondition, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
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
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
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
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[1], true);
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
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SRT
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
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {
					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SRS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchSubResValueInResList(
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE" + gstrTCID + " EXECUTION STARTS~~~~~");
			
		/*
		 * STEP : Action:Login as RegAdmin to region 'RG1' Expected
		 * Result:'Region Default' screen is displayed.
		 */
		// 609602
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
				blnLogin=true;
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
		 * STEP : Action:Navigate to Setup >> Resources Expected
		 * Result:'Resource List' screen is displayed.
		 * Resource 'RS1' is listed under it, a. Edit b. Status Types c.
		 * Users d. Sub-Resources 
		 * links are associated with resource 'RS1' under 'Action' column.
		 * 'Create New Resource' button is present at top left corner.
		 */
		// 609603
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyLinksUndrActionInRSListPge(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("Demote link is NOT present under Action coloumn", strFuncResult);
				strFuncResult="";
				assertEquals("", strFuncResult);
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateRes.CreateNewResource")));
				log4j.info("'Create New Resource' button is present at top left corner");
			} catch (AssertionError Ae) {
				strFuncResult="'Create New Resource' button is Not present at top left corner ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on 'Edit' link associated with resource
		 * 'RS1'. Expected Result:'Edit Resource' screen is displayed.
		 * 
		 * Sub-Resource Type 'SRT1'is not listed under 'Resource Type' drop
		 * down.
		 */
		// 609605
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verRTInResTypeDropDownFalse(
						selenium, strRTValue[1], strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 */
			// 609606
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Create New Resource' button Expected
			 * Result:'Create New Resource' screen is displayed.
			 */
			// 609612
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Resource Type' drop down. Expected
			 * Result:Sub-Resource Type 'SRT1'is not listed under 'Resource
			 * Type' drop down.
			 */
			// 609613
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verRTInResTypeDropDownFalse(
						selenium, strRTValue[1], strSubResrctTypName);
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
			gstrTCID = "BQS-123342";
			gstrTO = "Verify that sub-resources are not available while creating/editing a resource.";
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

	//end//testBQS107525//

	//start//testBQS107521//
	/*************************************************************
	'Description	:Verify that a resource that already has sub 
	                 resources created under it cannot be demoted.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/11/2013
	'Author			:QSG
	'--------------------------------------------------------------
	'Modified Date			                            Modified By
	'Date					                            Name
	***************************************************************/

	@Test
	public void testBQS107521() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		General objGeneral = new General();
		try {
			gstrTCID = "107521"; // Test Case Id
			gstrTO = " Verify that a resource that already has sub resources created under"
					+ " it cannot be demoted.";//TO 
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetaken = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strStatValue = "";
			String strSTvalue[] = new String[2];

			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strRTValue[] = new String[2];
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strResource3 = "AutoRs3_" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strResVal = "";
			String strRSValue[] = new String[5];

			String strState = "Alabama";
			String strCountry = "Barbour County";

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION STARTS~~~~~");
		
		/*
		 * STEP : Action:Preconditions:
		 * 1. Status type 'ST1' and 'ST2' are created in region RG1.
		 * 2. Resource Type (Normal) 'RT1' is created selecting status typeST1.
		 * 3. Resource 'RS1' , 'RS2' and 'RS3' are created selecting 'RT1'.
		 * 5. Sub-Resource 'SRT1' is created selecting status type ST2.
		 * 6. Sub-resource 'SRS' is created under parent resource 'RS3'
		 * selecting sub-resource type 'SRT1' Expected Result:No Expected Result
		 */
		// 609420
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				blnLogin = true;
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
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
						seleniumPrecondition, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
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
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
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
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource1);
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
						seleniumPrecondition, strResource2, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RS3
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
						seleniumPrecondition, strResource3, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource3);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[2] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SRT
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
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {
					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SRS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(
						seleniumPrecondition, strResource3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateSubResourcePage(
						seleniumPrecondition, strResource3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource, strResource3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[3] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
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
			
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE" + gstrTCID + " EXECUTION STARTS~~~~~");
		

			/*
			 * STEP : Action:Login as RegAdmin to region 'RG1' Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 609421
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
				blnLogin=true;
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
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 * Resources 'RS1' , 'RS2' and 'RS3' are listed under it.
			 * 'Edit' , 'Status Types' , 'Users' , 'Sub-Resources' , 'Demote'
			 * link present next to resource 'RS1' and 'RS2'
			 * 'Edit' , 'Status Types' , 'Users'and 'Sub-Resources' link present
			 * next to resource 'RS3'
			 * 'Create New Resource' button is present at top left corner.
			 */
			// 609422
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium, strResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium, strResource2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium, strResource3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyLinksUndrActionInRSListPge(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyLinksUndrActionInRSListPge(selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyLinksUndrActionInRSListPge(selenium, strResource3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("Demote link is NOT present under Action coloumn", strFuncResult);
				strFuncResult="";
				assertEquals("", strFuncResult);
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateRes.CreateNewResource")));
				log4j.info("'Create New Resource' button is present at top left corner");
			} catch (AssertionError Ae) {
				strFuncResult="'Create New Resource' button is Not present at top left corner ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Demote' link associated with resource
			 * 'RS1' Expected Result:'Demote Resource -- < Resource Name >'
			 * screen is displayed with following fields:
			 * Name: (Selected resource Name is displayed by default and field
			 * is disabled)
			 * Resource Type: (Drop down with 'Sub-resource' types under it)
			 * Select the parent resource:
			 * (Table with list of Resource Name and associated Resource Type
			 * and check box associated with each resource name)
			 */
			// 609423
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToDemoteResourcePage(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyFieldsInDemoteResScreen(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select Sub-Resource 'SRT1' from 'Resource Type'
			 * drop down and select check box corresponding to resource 'RS2'
			 * Click on 'Save'. Expected Result:'Resource List' screen is
			 * displayed.
			 * Resource 'RS1' is not listed under it.
			 * 'Demote' link for resources 'RS2' and 'RS3' is not displayed. (
			 * Resource that already has sub resources created under it cannot
			 * be demoted)
			 */
			// 609424
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchRSByDifCriteriaInDemoteResourcePage(selenium,
								strSubResrctTypName, "(Any Resource Type)",
								"Resource Name", "Equals", strResource2,
								strRSValue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(selenium,
						strResource1);
				if (strFuncResult.equals("Resource " + strResource1
						+ " is NOT displayed in Resource List page ")) {
					strFuncResult = "";
					log4j.info("Resource '" + strResource1
							+ "' is NOT listed under it. ");
				} else {
					strFuncResult = "Resource '" + strResource1
							+ "' is listed under it. ";
					log4j.info("Resource '" + strResource1
							+ "' is listed under it. ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource2
								+ "']/parent::tr/td[1]/a[5][text()='Demote']"));

				log4j.info("Demote link is Not present under Action coloumn for RS2: "+strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				strFuncResult="Demote link is present under Action coloumn for RS2: "+strResource2;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource3
								+ "']/parent::tr/td[1]/a[5][text()='Demote']"));

				log4j.info("Demote link is Not present under Action coloumn for RS3: "+strResource3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				strFuncResult="Demote link is present under Action coloumn for RS3: "+strResource3;
			}
			/*
			 * STEP : Action:Click on 'Sub-Resources' link associated with
			 * resource 'RS2'. Expected Result:'Sub-Resource List for Resource <
			 * Resource Name >' screen is displayed.
			 * Sub-Resource 'RS1' is displayed under parent Resource 'RS2'.
			 * 'Create New Sub-Resource' button present at top left corner
			 */
			// 609425
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table"
						+ "[@summary='Sub-Resources']/tbody/tr/td[3]"
						+ "[text()='" + strResource1
						+ "']/following-sibling::td[2]" + "[text()='"
						+ strSubResrctTypName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Sub-Resource '" + strResource1
							+ "' is displayed under parent Resource 'RS2'. ");
				} else {
					strFuncResult = "Sub-Resource '"
							+ strResource1
							+ "' is NOT displayed under parent Resource 'RS2'. ";
					log4j
					.info("Sub-Resource '"
							+ strResource1
							+ "' is NOT displayed under parent Resource 'RS2'. ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateResource.ValuesubResource")));
				log4j.info("'Create New Sub Resource' button is present at top left corner");
			} catch (AssertionError Ae) {
				strFuncResult="'Create New Sub Resource' button is Not present at top left corner ";
				log4j.info(strFuncResult);
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
			gstrTCID = "BQS-107521";
			gstrTO = "Verify that a resource that already has sub resources created under it cannot be demoted.";
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

	//end//testBQS107521//

	//start//testBQS107524//
	/***************************************************************
	'Description	:Verify that user can demote a resource that is included in a region view.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/12/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS107524() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		ResourceTypes objRT = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objRs = new Resources();
		General objGeneral=new General();
		Views objViews=new Views();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria =new SearchUserByDiffCrteria();
		try {
			gstrTCID = "107524"; // Test Case Id
			gstrTO = " Verify that user can demote a resource that is included in a region view.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetaken = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;
			String strStatTypeName1 = "St1_" + strTimeText;
			String strStatTypeName2 = "St2_" + strTimeText;
			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strTSTValue = "Number";
			String strMSTValue = "Multi";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strStatusValue[] = new String[2];
			String strSTvalue[] = new String[6];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTvalue[] = new String[2];

			String strResource = "AutoRs_1" + strTimeText;
			String strResource2 = "AutoRs_2" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[3];

			// Data for creating View
			String strViewName = "autoView" + strTimeText;
			String strVewDescription = "";
			String strViewType = "";

			/*
			 * STEP : Action:Preconditions:
			 * 1. Status types NST, MST, SST, TST,ST1,ST2 are created.
			 * 2. Resource type RT is created selecting NST,MST,TST,SST.
			 * 3. Sub Resource Type RT1 is created selecting ST1,ST2.
			 * 4. Resources 'RS1' and 'RS2' are created under 'RT'
			 * 5. View 'V1' is created selecting status type NST,MST,SST,TST
			 * ,'RT' and resource 'RS1' Expected Result:No Expected Result
			 */
			// 609517
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
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

		/*
		 * 1. Status types NST, MST, SST, TST,ST1,ST2 are created.
		 */

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

			// TST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//MST
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
					strSTvalue[2] = strStatValue;
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
			//SST
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
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strStatTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[4] = strStatValue;
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
						seleniumPrecondition, strNSTValue, strStatTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[5] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2. Resource type RT is created selecting NST,MST,TST,SST.
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
			 * 4. Sub Resource Type 'sRT1' is created selecting ST1,ST2.
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
						strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[4] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
						strSTvalue[5], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
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
			/*. Resource 'RS'1 is created under 'RT'.
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
			/*. Resource 'RS'2 is created under 'RT'.
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
						strResource2, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource2);

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
			 * 7.View 'V1' is created selecting status type NST,MST,SST,TST
			 * ,'RT' and resource 'RS1' Expected Result:No Expected Result
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
				strViewType = "Summary Plus (Resources as rows."
						+ " Status types and comments as columns.)";
				String strSTValues[] = { strSTvalue[0], strSTvalue[1],strSTvalue[2],strSTvalue[3] };
				String strRSValues[]={strRSValue[0]};
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTValues, false, strRSValues);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE" + gstrTCID + " EXECUTION STARTS~~~~~");
		

			/*
			 * STEP : Action:Login as RegAdmin to region 'RG1' Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 609518
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
				blnLogin=true;
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
			 * STEP : Action:Navigate to View >> 'V1' Expected Result:RS1 is
			 * displayed under RT along with status types NST,MST,SST,TST.
			 */
			// 609567
			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				String strArResource[]={strResource};
				String strStatTypeArr[]={strMultiStatType,strNumStatType,strSatuStatType,strTxtStatType};
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strArResource, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 * 'Create New Resource' button is present at top left corner.
			 */
			// 609519
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateRes.CreateNewResource")));
				log4j.info("'Create New Resource' button is present at top left corner");
			} catch (AssertionError Ae) {
				strFuncResult="'Create New Resource' button is Not present at top left corner ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Demote' link associated with resource
			 * 'RS1' Expected Result:'Demote Resource -- < Resource Name >'
			 * screen is displayed with following fields:
			 * Name: (Selected resource Name is displayed by default and field
			 * is disabled)
			 * Resource Type: (Drop down with 'Sub-resource' types under it)
			 * Select the parent resource:
			 * (Table with list of Resource Name and associated Resource Type
			 * and check box associated with each resource name)
			 */
			// 609520
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToDemoteResourcePage(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.VerifyFieldsInDemoteResScreen(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select Sub-Resource 'SRT1' from 'Resource Type'
			 * drop down and select check box corresponding to resource 'RS2'
			 * Click on 'Save'. Expected Result:'Resource List' screen is
			 * displayed.
			 * 
			 * Resource 'RS1' is not listed under it.
			 */
			// 609521
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchRSByDifCriteriaInDemoteResourcePage(selenium,
								strSubResrctTypName, "(Any Resource Type)",
								"Resource Name", "Equals", strResource2,
								strRSValue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(selenium,
						strResource);
				if (strFuncResult.equals("Resource " + strResource
						+ " is NOT displayed in Resource List page ")) {
					strFuncResult = "";
					log4j.info("Resource RS1'" + strResource
							+ "' is not listed under it. ");
				} else {
					strFuncResult = "Resource '" + strResource
							+ "' is listed under it. ";
					log4j.info("Resource '" + strResource
							+ "' is listed under it. ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Sub-Resources' link associated with
			 * resource 'RS2'. Expected Result:'Sub-Resource List for Resource <
			 * Resource Name >' screen is displayed.
			 * 
			 * Sub-Resource 'RS1' is displayed under parent Resource 'RS2'.
			 * 
			 * 'Create New Sub-Resource' button present at top left corner
			 */
			// 609522
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table"
						+ "[@summary='Sub-Resources']/tbody/tr/td[3]"
						+ "[text()='" + strResource
						+ "']/following-sibling::td[2]" + "[text()='"
						+ strSubResrctTypName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Sub-Resource '" + strResource
							+ "' is displayed under parent Resource 'RS2'. ");
				} else {
					strFuncResult = "Sub-Resource '"
							+ strResource
							+ "' is NOT displayed under parent Resource 'RS2'. ";
					log4j
					.info("Sub-Resource '"
							+ strResource
							+ "' is NOT displayed under parent Resource 'RS2'. ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateResource.ValuesubResource")));
				log4j.info("'Create New Sub Resource' button is present at top left corner");
			} catch (AssertionError Ae) {
				strFuncResult="'Create New Sub Resource' button is Not present at top left corner ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to View >> 'V1' Expected Result:Resource
			 * 'RS1' is not displayed on view screen.
			 */
			// 609585
			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				assertFalse(selenium
							.isElementPresent("//table/tbody/tr/td/a[text()='" + strResource+ "']"));
				log4j.info("'RS1' "+strResource+" is not displayed on view screen. ");
				
				
			} catch (AssertionError Ae) {
				log4j.info("'RS1' "+strResource+" is displayed on view screen. ");
				strFuncResult="'RS1' "+strResource+" is displayed on view screen. ";
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-107524";
			gstrTO = "Verify that user can demote a resource that is included in a region view.";
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

	//end//testBQS107524//

	//start//testBQS105693//
	/***************************************************************
	'Description	:Verify that RegAdmin can demote a resource to be a sub resource of another resource.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/12/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS105693() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objResources = new Resources();
		General objGeneral=new General();
		CreateUsers objCreateUsers=new CreateUsers();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria =new SearchUserByDiffCrteria();
		try {
			gstrTCID = "105693"; // Test Case Id
			gstrTO = " Verify that RegAdmin can demote a resource to be a sub resource of another resource.";// Test
																												// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetaken = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strStatValue = "";
			String strSTvalue[] = new String[2];

			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strRTValue[] = new String[2];
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;

			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strResVal = "";
			String strRSValue[] = new String[5];

			String strFILE_PATH = propPathDetails.getProperty("TestData_path");
			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION STARTS~~~~~");
		
			/*
			 * STEP : Action:Preconditions:
			 * Status type ST1 and ST2 are created in region RG1.
			 * Resource Type (Normal) RT1 is created selecting status type ST1.
			 * Resource RS1 is created selecting RT1.
			 * Resource RS2 is created selecting RT1.
			 * Sub-Resource SRT1 is created selecting status type ST2.
			 * User 'U1' is created in region 'RG1'. Expected Result:No Expected
			 * Result
			 */
			// 604400
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				blnLogin = true;
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//ST1
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
						seleniumPrecondition, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
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
			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
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
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);

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
			//RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
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
						seleniumPrecondition, strResource2, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource2);

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
			// SRT
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
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE" + gstrTCID + " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as RegAdmin to region 'RG1' Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 604401
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
				blnLogin=true;
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
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 * 
			 * 'Create New Resource' button is present at top left corner.
			 */
			// 604402
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateRes.CreateNewResource")));
				log4j.info("'Create New Resource' button is present at top left corner");
			} catch (AssertionError Ae) {
				strFuncResult="'Create New Resource' button is Not present at top left corner ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Demote' link associated with resource
			 * 'RS1' Expected Result:'Demote Resource -- < Resource Name >'
			 * screen is displayed with following fields:
			 * Name: (Selected resource Name is displayed by default and field
			 * is disabled)
			 * Resource Type: (Drop down with 'Sub-resource' types under it)
			 * Select the parent resource:
			 * (Table with list of Resource Name and associated Resource Type
			 * and check box associated with each resource name)
			 */
			// 604403
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToDemoteResourcePage(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyFieldsInDemoteResScreen(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select Sub-Resource 'SRT1' from 'Resource Type'
			 * drop down and select check box corresponding to resource 'RS2'
			 * Click on 'Save'. Expected Result:'Resource List' screen is
			 * displayed.
			 * Resource 'RS1' is not listed under it.
			 */
			// 604404
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchRSByDifCriteriaInDemoteResourcePage(selenium,
								strSubResrctTypName, "(Any Resource Type)",
								"Resource Name", "Equals", strResource2,
								strRSValue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(selenium,
						strResource1);
				if (strFuncResult.equals("Resource " + strResource1
						+ " is NOT displayed in Resource List page ")) {
					strFuncResult = "";
					log4j.info("Resource RS1'" + strResource1
							+ "' is NOT listed under it. ");
				} else {
					strFuncResult = "Resource '" + strResource1
							+ "' is listed under it. ";
					log4j.info("Resource '" + strResource1
							+ "' is listed under it. ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Sub-Resources' link associated with
			 * resource 'RS2'. Expected Result:'Sub-Resource List for Resource <
			 * Resource Name >' screen is displayed.
			 * Sub-Resource 'RS1' is displayed under parent Resource 'RS2'.
			 * 'Create New Sub-Resource' button present at top left corner
			 */
			// 604405
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table"
						+ "[@summary='Sub-Resources']/tbody/tr/td[3]"
						+ "[text()='" + strResource1
						+ "']/following-sibling::td[2]" + "[text()='"
						+ strSubResrctTypName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Sub-Resource '" + strResource1
							+ "' is displayed under parent Resource 'RS2'. ");
				} else {
					strFuncResult = "Sub-Resource '"
							+ strResource1
							+ "' is NOT displayed under parent Resource 'RS2'. ";
					log4j
					.info("Sub-Resource '"
							+ strResource1
							+ "' is NOT displayed under parent Resource 'RS2'. ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateResource.ValuesubResource")));
				log4j.info("'Create New Sub Resource' button is present at top left corner");
			} catch (AssertionError Ae) {
				strFuncResult="'Create New Sub Resource' button is Not present at top left corner ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Users Expected Result:'Users
			 * List' screen is displayed.
			 * All the users in region are listed under it.
			 */
			// 604406
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.VrfyUserWithSearchUser(selenium, strUserName_1, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit' link associated with user 'U1'
			 * Expected Result:'Edit User' screen is displayed. Resource 'RS1'
			 * is not displayed under 'Resource Rights' section.
			 */
			// 604407
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(
						selenium, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyResourceInResRights(selenium, strResource1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Users Expected Result:'Users
			 * List' screen is displayed.
			 * 
			 * 'Create New User' button is present at top left corner.
			 */
			// 604439
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try{
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("CreateNewUsrLink")));
				log4j.info("'Create New User' button is present at top left");
			}catch(AssertionError Ae){
				gstrReason = "'Create New User' button is Not present at top left";
				log4j.info("'Create New User' button is Not present at top left");
			}
			/*
			 * STEP : Action:Click on 'Create New User' button. Expected
			 * Result:'Create New User' screen is displayed.
			 * 
			 * Resource 'RS1' is not displayed under 'Resource Rights' section.
			 */
			// 604442
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToCreateUserPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyResourceInResRights(selenium, strResource1, false);
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
			gstrTCID = "BQS-105693";
			gstrTO = "Verify that RegAdmin can demote a resource to be a sub resource of another resource.";
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

	//end//testBQS105693//
	
	//start//testBQS107522//
	/***************************************************************
	'Description	:Verify that user cannot demote a resource that is participating in an event.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/15/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS107522() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objResources = new Resources();
		EventSetup objEventSetup=new EventSetup();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria =new SearchUserByDiffCrteria();
		try {
			gstrTCID = "107522"; // Test Case Id
			gstrTO = " Verify that user cannot demote a resource that is participating in an event.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetaken = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";
			
			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;

			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";

			String strStatValue = "";
			String strSTvalue[] = new String[2];

			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;

			String strRTValue[] = new String[2];
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;

			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strResVal = "";
			String strRSValue[] = new String[5];

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION STARTS~~~~~");
		
			/*
			 * STEP : Action:Preconditions:
			 * 1. Status type 'ST1' and 'ST2' are created in region RG1.
			 * 2. Resource Type (Normal) 'RT1' is created selecting status type'ST1'.
			 * 3. Resource 'RS1' and 'RS2' are created selecting 'RT1'.
			 * 4. Sub-Resource 'SRT1' is created selecting status type ST2.
			 * 5. Event Template 'ET' is created selecting status type 'ST1' and
			 * resource type 'RT1'.
			 * 6. Event 'EVE' is created selecting resource 'RS1' Expected
			 * Result:No Expected Result
			 */
			// 609502
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				blnLogin = true;
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//ST1
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
						seleniumPrecondition, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
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
			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
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
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);

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
			//RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
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
						seleniumPrecondition, strResource2, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource2);

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
			// SRT
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
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//ET1
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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0]};
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

			//. Event E1 is created under ET selecting RS.

			try {
				assertEquals("", strFuncResult);

				strFuncResult=objEventSetup.navToEventManagementNew(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(seleniumPrecondition,
						strTempName, strResource1, strEveName, strInfo, true);
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
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE" + gstrTCID + " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as RegAdmin to region 'RG1' Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 609503
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
				blnLogin=true;
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
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 * Resource 'RS1' and 'RS2' are listed under it.
			 */
			// 609504
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium, strResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium, strResource2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Demote' link associated with resource
			 * 'RS1' Expected Result:'Demote Resource -- < Resource Name >'
			 * screen is displayed with following fields:
			 * Name: (Selected resource Name is displayed by default and field
			 * is disabled)
			 * Resource Type: (Drop down with 'Sub-resource' types under it)
			 * Select the parent resource:
			 * (Table with list of Resource Name and associated Resource Type
			 * and check box associated with each resource name)
			 */
			// 609505
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToDemoteResourcePage(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyFieldsInDemoteResScreen(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select Sub-Resource 'SRT1' from 'Resource Type'
			 * drop down and select check box corresponding to resource 'RS2'
			 * Click on 'Save'. Expected Result:'Demote Resource -- < Resource
			 * Name > screen is displayed.
			 * 
			 * Resource 'RS1' is not demoted.
			 * 
			 * 'The following error occurred on this page: �You may not demote a
			 * resource that is in an ongoing event.' error message is
			 * displayed.
			 */
			// 609506
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchRSByDifCriteriaInDemoteResourcePage(selenium,
								strSubResrctTypName, "(Any Resource Type)",
								"Resource Name", "Equals", strResource2,
								strRSValue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkErrMsgForOnGoingEveInDemote(selenium);
						
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Sub-Resources' link associated with
			 * resource 'RS2'. Expected Result:'Sub-Resource List for Resource <
			 * Resource Name >' screen is displayed.
			 * 
			 * Sub-Resource 'RS1' is not displayed under Resource 'RS2'.
			 * 
			 * 'Create New Sub-Resource' button present at top left corner
			 */
			// 609507
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table"
						+ "[@summary='Sub-Resources']/tbody/tr/td[3]"
						+ "[text()='" + strResource1
						+ "']/following-sibling::td[2]" + "[text()='"
						+ strSubResrctTypName + "']";
				assertFalse(selenium.isElementPresent(strElementID));
				log4j
				.info("Sub-Resource '"
						+ strResource1
						+ "' is not displayed under parent Resource 'RS2'. ");
				
			} catch (AssertionError Ae) {
				strFuncResult="Sub-Resource '" + strResource1
						+ "' is displayed under parent Resource 'RS2'. ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateResource.ValuesubResource")));
				log4j.info("'Create New Sub Resource' button is present at top left corner");
			} catch (AssertionError Ae) {
				strFuncResult="'Create New Sub Resource' button is Not present at top left corner ";
				log4j.info(strFuncResult);
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
			gstrTCID = "BQS-107522";
			gstrTO = "Verify that user cannot demote a resource that is participating in an event.";
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
	//end//testBQS107522//
	
	//start//testBQS122271//
		/*******************************************************************************************
		'Description	:Verify that status change preferences set for a demoted resource is retained.
		'Arguments		:None
		'Returns		:None
		'Date			:7/17/2013
		'Author			:QSG
		'-------------------------------------------------------------------------------------------
		'Modified Date				Modified By
		'Date					Name
		*********************************************************************************************/

	@Test
	public void testBQS122271() throws Exception {

		boolean blnLogin = false;// keep track of logout of application
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		Roles objRole = new Roles();
		Preferences objPreferences = new Preferences();
		CreateUsers objCreateUsers = new CreateUsers();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();// object of class General
		Resources objResources = new Resources();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

		try {
			gstrTCID = "122271"; // Test Case Id
			gstrTO = " Verify that status change preferences set for a demoted resource is retained."; // Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			String strTmText = dts.getCurrentDate("HHmm");
			String strFILE_PATH = propPathDetails.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetaken = dts.timeNow("HH:mm:ss");

			// login
			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strStatTypeColor = "Black";
			String strSSTValue = "Saturation Score";

			String statrNumTypeName = "NST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;

			String strStatusTypeValues[] = new String[3];
			String strStatusName1 = "S1_" + strTimeTxt;
			String strStatusName2 = "S2_" + strTimeTxt;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTValue[] = new String[2];
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strAbbrv = "A" + strTmText;
			String strRSValue[] = new String[2];

			// user
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUserFullName_1 = "FN" + strUserName_1;
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strRoleValue = "";
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = objrdExcel.readData("WebMailUser", 2, 1);
			String strEMail = objrdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = objrdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";

			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};

			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			// add res data
			String strSearchCategory = "(Any)";
			String strRegion = "";
			String strSearchWhere = "";
			String strSearchState = "(Any)";

			String strAbove = "100";
			String strBelow = "50";

			// mail Verification
			String strStartDate = "";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			int intEMailRes = 0;
			int intPagerRes = 0;

			// Web mail user details
			String strLoginName = objrdExcel.readData("WebMailUser", 2, 1);
			String strPassword = objrdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1, strMsgBody2, strMsgBody3, strMsgBody4, strMsgBody5, strMsgBody6;
			String strSubjName = "";

			String strCurrDate = "";
			String strCurrDate2 = "";

			String strUpdatTxtValue = "101";
			String strUpdatSSTValue = "393";
			/*
			 * STEP : Action:Precondition: 1. Created status types 'NST', 'MST',
			 * 'SST' , 2. Resource type 'RT' is created selecting status types
			 * 'NST', 'MST' and 'SST'. 3. Sub Resource Type 'SRT' is created
			 * selecting status types 'sNST', 'pMST' and 'rSST. 4. Resource 'RS'
			 * is created under 'RT' 5. Sub-resource 'SRS1' , 'SRS2' are created
			 * under parent resource 'RS' selecting 'SRT' sub-resource type. 6.
			 * User 'U1' is created selecting 'View Resource' and 'Update
			 * Resource' right on resource 'RS'. Expected Result:No Expected
			 * Result
			 */
			// 658013

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						strStatusName2, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[1] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statrMultiTypeName,
						strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
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
								+ strStatusTypeValues[0] + "']");
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
				strFuncResult = objRs
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
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

			// RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strRSValue[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strStatusTypeValues[0] + "']");
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
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
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
				String strStatus[] = { strStatusTypeValues[0],
						strStatusTypeValues[1], strStatusTypeValues[2] };
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRights,
						strStatus, true, strStatus, true, true);
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
						seleniumPrecondition, strUserName_1, strInitPwd,
						strInitPwd, strUserFullName_1);

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);
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
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
				log4j.info("~~~~~TESTCASE" + gstrTCID + " EXECUTION ENDS~~~~~");
			/*
			 * STEP : Action:Login as User 'U1',Navigate to 'Preferences>>Status
			 * Change Prefs'. Expected Result:'Status Change Prefs' page is
			 * displayed.
			 */
			// 658014
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
				strFuncResult = objPreferences.navTOFindResourcesPage(selenium,
						strResource1, strSearchCategory, strRegion,
						strSearchWhere, strSearchState, strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				int intCnt = 0;
				do {
					try {

						assertEquals("Edit My Status Change Preferences",
								selenium.getText(propElementDetails
										.getProperty("Header.Text")));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				assertEquals("Edit My Status Change Preferences",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));

				log4j.info("Edit My Status Change Preferences page is  displayed");

			} catch (AssertionError Ae) {
				log4j.info("Edit My Status Change Preferences page is NOT displayed"
						+ Ae);
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=#r_name";
				String strText = strResrctTypName + "�" + strResource1;
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
				String[] strSTName = { statrNumTypeName, statrMultiTypeName,
						statrSaturatTypeName };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			/*
			 * For 'NST' and 'SST', provide value 'X' for 'Above' and 'Y' for 'Below'. 
			 * Select 'Email', 'Pager' and 'Web' for both the values. 'My Status
			 * Change Preferences' page is displayed 'X' is displayed under
			 * 'Email', 'Pager' and 'Web' for both 'Above' and 'Below' values.
			 */
					//NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource1, strRSValue[0],
								strStatusTypeValues[0], statrNumTypeName, true,
								true, true);
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
								strResource1, strRSValue[0],
								strStatusTypeValues[0], statrNumTypeName, true,
								true, true);
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
								strStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST

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
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource1, strRSValue[0],
								strStatusTypeValues[2], statrSaturatTypeName,
								true, true, true);
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
								strResource1, strRSValue[0],
								strStatusTypeValues[2], statrSaturatTypeName,
								true, true, true);
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
								strStatusTypeValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST

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
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource1, strRSValue[0],
								strStatusTypeValues[1], statrMultiTypeName,
								strStatusValue[0], true, true, true);
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
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource1, strRSValue[0],
								strStatusTypeValues[1], statrMultiTypeName,
								strStatusValue[1], true, true, true);
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
								strStatusTypeValues[2], true, true);
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

				/*
				 * String str = strDate[0].substring(1, 3); log4j.info(str);
				 */

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

			// Navigate to view >> 'V1',
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium,
						strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strStatusTypeValues[1], false, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.fillAndSavUpdateStatusTST(selenium, strUpdatTxtValue,
								strStatusTypeValues[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strUpdateValue, strStatusTypeValues[2]);
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
			
		/*
		 * Provide the following values: 1. NST: A value above 'A' 2.
		 * MST: Select status 'S2' 3. SST: A value below 'Y' User A receives
		 * the following: 1. 'Email', 'Pager' and 'Web' for 'NST' 2.
		 * 'Email', 'Pager' and 'Web' for 'MST' 3. 'Email', 'Pager' and
		 * 'Web' for 'SST'
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

				String strDesc1[] = {

						"On " + strCurrDate + " changed "
								+ statrSaturatTypeName + " status from 0 to "
								+ strUpdatSSTValue + ".",

						"On " + strCurrDate + " changed " + statrNumTypeName
								+ " status from 0 to " + strUpdatTxtValue + ".",

						"On " + strCurrDate + " changed " + statrMultiTypeName
								+ " status from -- to " + strStatusName1 + "." };

				String strDesc2[] = {
						"On " + strCurrDate2 + " changed "
								+ statrSaturatTypeName + " status from 0 to "
								+ strUpdatSSTValue + ".",

						"On " + strCurrDate2 + " changed " + statrNumTypeName
								+ " status from 0 to " + strUpdatTxtValue + ".",

						"On " + strCurrDate2 + " changed " + statrMultiTypeName
								+ " status from -- to " + strStatusName1 + "." };

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationForManyStatusTypes(selenium,
								strResource1, strSTDateTime, strAddedDtTime,
								strDesc1, strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			

			strMsgBody1 = statrNumTypeName + " from 0 to " + strUpdatTxtValue
					+ "; " + "Reasons: \nSummary at " + strCurrDate + " "+strAbbrv+"[0]"
					+ "\n" + "Region: " + strRegn + "";

			strMsgBody2 = "Status Update for "
					+ strUserFullName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource1
					+ " "
					+ "changed "
					+ statrNumTypeName
					+ " status from 0 "
					+ "to "
					+ strUpdatTxtValue
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrNumTypeName
					+ " status:"+"\n"+strResource2+" = 0"
					+ "\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			// =======
			strMsgBody3 = statrMultiTypeName + " from -- to " + strStatusName1
					+ "; " + "Reasons: \nSummary at " + strCurrDate + " --: "+strAbbrv
					+ "\n" + "Region: " + strRegn + "";

			strMsgBody4 = "Status Update for "
					+ strUserFullName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource1
					+ " "
					+ "changed "
					+ statrMultiTypeName
					+ " status from -- "
					+ "to "
					+ strStatusName1
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrMultiTypeName
					+ " status:"+"\n"+strResource2+" = --"
					+ "\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";
			//
			strMsgBody5 = statrSaturatTypeName + " from 0 to "
					+ strUpdatSSTValue + "; " + "Reasons: \nSummary at "
					+ strCurrDate + " "+strAbbrv+"[0]" + "\n" + "Region: " + strRegn + "";

			strMsgBody6 = "Status Update for "
					+ strUserFullName_1
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource1
					+ " "
					+ "changed "
					+ statrSaturatTypeName
					+ " status from 0 "
					+ "to "
					+ strUpdatSSTValue
					+ ".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrSaturatTypeName
					+ " status:"+"\n"+strResource2+" = 0"
					+ "\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			// Login To main
			selenium.selectWindow("");
			strSubjName = "Change for " + strAbbrv;
			strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
					strLoginName, strPassword);

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
						strSubjName);
				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");

					String[] strMails = objMail.enodeToUnicode(selenium,
							strMsg, 160);
					strMsg = strMails[1];

					if (strMsg.equals(strMsgBody1)
							|| strMsg.equals(strMsgBody3)
							|| strMsg.equals(strMsgBody5)) {
						intPagerRes++;
						log4j.info("Pager body is displayed");
					} else {
						log4j.info("Email and Pager body is NOT displayed");
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
				strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
						strSubjName);
				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");

					String[] strMails = objMail.enodeToUnicode(selenium,
							strMsg, 160);
					strMsg = strMails[1];

					if (strMsg.equals(strMsgBody1)
							|| strMsg.equals(strMsgBody3)
							|| strMsg.equals(strMsgBody5)) {
						intPagerRes++;
						log4j.info("Pager body is displayed");
					} else {
						log4j.info("Email and Pager body is NOT displayed");
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
				strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
						strSubjName);
				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");

					String[] strMails = objMail.enodeToUnicode(selenium,
							strMsg, 160);
					strMsg = strMails[1];

					if (strMsg.equals(strMsgBody1)
							|| strMsg.equals(strMsgBody3)
							|| strMsg.equals(strMsgBody5)) {
						intPagerRes++;
						log4j.info("Pager body is displayed");
					} else {
						log4j.info("Email and Pager body is NOT displayed");
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

				strSubjName = "Status Change for " + strResource1;

				for (int i = 0; i < 6; i++) {

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody2)
								|| strMsg.equals(strMsgBody4)
								|| strMsg.equals(strMsgBody6)) {
							intEMailRes++;
							log4j.info("Email body is displayed");

						} else {
							log4j.info("Email and Pager body is NOT displayed");
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

				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad("90000");
				selenium.close();

				selenium.selectWindow("");				
				Thread.sleep(4000);

				// check Email, pager notification
				if (intEMailRes == 6 && intPagerRes == 3) {
					strFuncResult = "";
				} else {
					strFuncResult = "All mail and pager not dislayed";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
		/*
		 * STEP : Action:Login as RegAdmin to region 'RG1' Expected
		 * Result:'Region Default' screen is displayed.
		 */
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
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 * Resource 'RS1' and 'RS2' are listed under it.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on 'Demote' link associated with resource
		 * 'RS1' Expected Result:'Demote Resource -- < Resource Name >'
		 * screen is displayed with following fields:
		 * Name: (Selected resource Name is displayed by default and field
		 * is disabled)
		 * Resource Type: (Drop down with 'Sub-resource' types under it)
		 * Select the parent resource:
		 * (Table with list of Resource Name and associated Resource Type
		 * and check box associated with each resource name)
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToDemoteResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyFieldsInDemoteResScreen(
						selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Select Sub-Resource 'SRT1' from 'Resource Type'
		 * drop down and select check box corresponding to resource 'RS2'
		 * Click on 'Save'. 
		 * Expected Result:Resource List' screen is displayed.
          Resource 'RS1' is not listed under it. 
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchRSByDifCriteriaInDemoteResourcePage(selenium,
								strSubResrctTypName, "(Any Resource Type)",
								"Resource Name", "Equals", strResource2,
								strRSValue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavRSList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource1, false);
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
		 * STEP : Action:Login as user 'U1' ,
		 *  Navigate to 'Preferences>>Status Change Prefs' 
		 * Expected Result:'My Status Change Preferences' is displayed.
			Sub header is displayed as 'RT1-RS2:RS1' is displayed.
			Status types 'NST', 'MST' and 'SST are displayed under 'Uncategorized'.
			Previously selected preferences for resource before demoting it are retained. 
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName_1,
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
				String strResource=strResource2+": "+strResource1;
				strFuncResult = objPreferences
						.navToEditMyStatusChangePrefs(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=#r_name";
				
				String strText = strSubResrctTypName + "�" + strResource2 + ":"
						+ strResource1;
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Sub header is displayed as ' "+strSubResrctTypName + "�" + strResource2 + ":"
						+ strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "Sub header is NOT displayed as ' "+strSubResrctTypName + "�" + strResource2 + ":"
						+ strResource1;
				log4j.info("Sub header is NOT displayed as ' "+strSubResrctTypName + "�" + strResource2 + ":"
						+ strResource1);
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrNumTypeName, statrMultiTypeName,
						statrSaturatTypeName };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "100", "true", "true", "true" },
						{ "50", "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefsNew(selenium,
								statrNumTypeName, strNotifData);
				if (strFuncResult.equals("")) {
					log4j.info("Previously selected preferences for resource before demoting it are retained.");
				} else {
					log4j.info("Previously selected preferences for resource before demoting it are NOT retained.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "100", "true", "true", "true" },
						{ "50", "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefsNew(selenium,
								statrSaturatTypeName, strNotifData);
				if (strFuncResult.equals("")) {
					log4j.info("Previously selected preferences for resource before demoting it are retained.");
				} else {
					log4j.info("Previously selected preferences for resource before demoting it are NOT retained.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = {
						{ strStatusName1, "true", "true", "true" },
						{ strStatusName2, "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefsNew(selenium,
								statrMultiTypeName, strNotifData);
				if (strFuncResult.equals("")) {
					log4j.info("Previously selected preferences for resource before demoting it are retained.");
				} else {
					log4j.info("Previously selected preferences for resource before demoting it are NOT retained.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// check Email, pager notification
				if (intEMailRes == 6 && intPagerRes == 3) {
				}
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-122271";
			gstrTO = "Verify that status change preferences set for a demoted resource is retained.";
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
		//end//testBQS122271//
}

