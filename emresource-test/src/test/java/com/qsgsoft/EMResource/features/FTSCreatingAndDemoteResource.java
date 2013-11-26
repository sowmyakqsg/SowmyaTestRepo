package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
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
' Description		:This class contains test cases from requirement
' Requirement		:Creating and Demote Resource
' Requirement Group	:Resource Hierarchies
' Product		    :EMResource v3.21
' Date			    :7/8/2013
' Author		    :QSG
'*******************************************************************/

public class FTSCreatingAndDemoteResource{

	//Log4j object to write log entries to the Log files
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.features.FTSCreatingAndDemoteResource");
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
	Selenium selenium,seleniumPrecondition;

	//Object for date time settings
	Date_Time_settings dts = new Date_Time_settings();	

	public Properties propElementDetails; //Property variable for ElementID file
	public Properties propEnvDetails;//Property variable for Environment data
	public Properties propPathDetails; //Property variable for Path information
	public Properties propAutoITDetails;//Property variable for AutoIT file details
	public static Properties browserProps = new Properties();
	public static String gstrBrowserName;//Variable to store browser name
	public static String gstrTimetaken, gstrdate, gstrtime, gstrBuild;//Result Variables
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

		Paths_Properties objPathsProps = new Paths_Properties();
		propAutoITDetails=objPathsProps.ReadAutoit_FilePath();

		propPathDetails = objPathsProps.Read_FilePath();

		//Create object to read environment properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrBrowserName=propEnvDetails.getProperty("Browser").substring(1)+" "+propEnvDetails.getProperty("BrowserVersion");
		//Retrieve the value of page load time limit
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		//create an object to refer to Element ID properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		//Create a new selenium session
		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
				.getProperty("urlEU"));
		//Start Selenium RC
		selenium.start();
		//Maximize the window
		selenium.windowMaximize();
		selenium.setTimeout(propEnvDetails.getProperty("TimeOut"));

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

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
		// kill browser
		selenium.close();
		selenium.stop();
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

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
		//Write Result of the test.
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetaken, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);

	}
	//start//testFTS107906//
	/***************************************************************
	'Description	:Verify that appropriate validation message is displayed when attempted to demote a resource that is Shared.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/18/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS107906() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria=new SearchUserByDiffCrteria();
		try {
			gstrTCID = "107906"; // Test Case Id
			gstrTO = " Verify that appropriate validation message is displayed when attempted" +
					" to demote a resource that is Shared.";//TO																	// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			gstrTimetaken = dts.timeNow("hh:mm:ss");
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
			String strRSValue[] = new String[3];
			String strState = "Alabama";
			String strCountry = "Autauga County";

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION STARTS~~~~~");
	
			/*
			 * STEP : Action:Preconditions:
			 * 1. Status type ST1 and ST2 are created in region RG1.
			 * 2. Resource Type (Normal) RT1 is created selecting status typeST1.
			 * 3. Resource RS1 is created selecting RT1.
			 * 4. Resource RS2 is created under RT1 selecting 'Share with Other
			 * Regions' check box.
			 * 5. Sub-Resource SRT1 is created selecting status type ST2.
			 * Expected Result:No Expected Result
			 */
			// 610367
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
				strFuncResult = objResources.createResourceWitLookUPadresSharWitRgn(seleniumPrecondition, strResource2, 
						strAbbrv, strResrctTypName, true, strContFName, strContLName, strState, strCountry, strStandResType);
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
			// 610368
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
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
			 */
			// 610369
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Demote' link associated with resource
			 * 'RS2' Expected Result:'Demote Resource -- < Resource Name >'
			 * screen is displayed.
			 */
			// 610370
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToDemoteResourcePage(selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select Resource type 'SRT1' under resource type
			 * drop down and select resource 'RS1' under 'select the parent
			 * resource:' table and click on 'Save'. Expected Result:RegAdmin is
			 * retained on 'Demote Resource -- < Resource Name >' screen
			 * 
			 * Following error message is displayed:
			 * 
			 * 'The following error occurred on this page: �You may not demote a
			 * resource that is shared with other regions.'
			 */
			// 610371
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchRSByDifCriteriaInDemoteResourcePage(selenium,
								strSubResrctTypName, "(Any Resource Type)",
								"Resource Name", "Equals", strResource1,
								strRSValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkErrMsgForSharedRSInDemote(selenium);
						
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 * 
			 * Resources 'RS1' and 'RS2' are displayed under it.
			 */
			// 610372
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
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-107906";
			gstrTO = "Verify that appropriate validation message is displayed when attempted to demote a resource that is Shared.";
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

	//end//testFTS107906//


	//start//testFTS107905//
	/***************************************************************
	'Description		:Verify that appropriate validation message is displayed when attempted to demote a resource that reports HAvBED.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/18/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS107905() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria=new SearchUserByDiffCrteria();
		try {
			gstrTCID = "107905"; // Test Case Id
			gstrTO = " Verify that appropriate validation message is displayed when attempted to demote a resource that reports HAvBED.";// Test
																																			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			gstrTimetaken = dts.timeNow("hh:mm:ss");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetaken = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative
			// URL
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
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strResVal = "";
			String strRSValue[] = new String[3];

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION STARTS~~~~~");
	
				/*
				 * STEP : Action:Preconditions:
				 * 1. Status type ST1 and ST2 are created in region RG1.
				 * 2. Resource Type (Normal) RT1 is created selecting status typeST1.
				 * 3. Resource RS1 is created selecting RT1.
				 * 4. Resource RS2 is created under RT1 selecting 'Reports HAvBED
				 * data' check box.
				 * 5. Sub-Resource SRT1 is created selecting status type ST2.
				 * Expected Result:No Expected Result
				 */
				// 610350
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
				strFuncResult = objResources.createResource_FillAllFields(seleniumPrecondition, strResource2, strAbbrv, strResrctTypName, strStandResType, true, false, 
						"", "", false, "", "", strState, "", strCountry, "",
						strContFName, strContLName, "", "", "", "", "", "", "");
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
			// 610351
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
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
			 */
			// 610352
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Demote' link associated with resource
			 * 'RS2' Expected Result:'Demote Resource -- < Resource Name >'
			 * screen is displayed.
			 */
			// 610353
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToDemoteResourcePage(selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select Resource type 'SRT1' under resource type
			 * drop down and select resource 'RS1' under 'select the parent
			 * resource:' table and click on 'Save'. Expected Result:RegAdmin is
			 * retained on 'Demote Resource -- < Resource Name >' screen
			 * 
			 * Following error message is displayed:
			 * 
			 * 'You may not demote a resource that reports HAvBED.'
			 */
			// 610354
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchRSByDifCriteriaInDemoteResourcePage(selenium,
								strSubResrctTypName, "(Any Resource Type)",
								"Resource Name", "Equals", strResource1,
								strRSValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkErrMsgForHAvBEDRSInDemote(selenium);
						
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 * 
			 * Resources 'RS1' and 'RS2' are displayed under it.
			 */
			// 610355
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
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-107905";
			gstrTO = "Verify that appropriate validation message is displayed when attempted to demote a resource that reports HAvBED.";
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

	//end//testFTS107905//
}
