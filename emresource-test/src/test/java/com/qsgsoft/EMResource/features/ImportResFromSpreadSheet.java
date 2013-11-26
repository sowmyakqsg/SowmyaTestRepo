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
/**********************************************************************
' Description		:This class contains test cases from 'Req7457'  requirement
' Requirement		:Import Resources from Spreadsheet
' Requirement Group	:Setting up Resources
' Product		:EMResource v3.20
' Date			:05-11-2012
' Author		:QSG
' Modified Date				Modified By
' Date					Name
'*******************************************************************/

@SuppressWarnings({ "unused" })
public class ImportResFromSpreadSheet {

//Log4j object to write log entries to the Log files
static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.features.ImportResFromSpreadSheet");
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
private String browser;
double gdbTimeTaken; //Variable to store the time taken

public static Date gdtStartDate;// Date variable

private String json;
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

		// Create object to read environment properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		Paths_Properties objPathsProps = new Paths_Properties();
		propAutoITDetails = objPathsProps.ReadAutoit_FilePath();

		propPathDetails = objPathsProps.Read_FilePath();

		// Retrieve browser information
		browser = propEnvDetails.getProperty("Browser");
		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		// Retrieve the value of page load time limit
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		// create an object to refer to Element ID properties file
		ElementId_properties objelementProp = new ElementId_properties();
		// Create a new selenium session
		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));
		// Start Selenium RC
		selenium.start();
		// Maximize the window
		selenium.windowMaximize();
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
		selenium.setTimeout(propEnvDetails.getProperty("TimeOut"));
		// Define object to call support functions to read excel, date etc
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
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
		
		selenium.close();
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

		gstrReason=gstrReason.replaceAll("'", " ");
		// Write Result of the test.
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetaken, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild, StrSessionId);

	}


/***************************************************************
'Description		:Verify that only RegAdmin (EMSystems Administrator) can import resources.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:05-11-2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testBQS102968() throws Exception {
		try {
			gstrTCID = "102968"; // Test Case Id
			gstrTO = " Verify that only RegAdmin (EMSystems Administrator) can import resources.";
			gstrReason = "";
			gstrResult = "FAIL";

			gstrTimetaken = dts.timeNow("HH:mm:ss");
			String strLoginUserName, strLoginPassword, strRegn, strUserName, strInitPwd,
			strConfirmPwd, strUsrFulName;
			String strFuncResult = "";

			Login objLogin = new Login();
			CreateUsers objCreateUsers = new CreateUsers();
			Upload objUpload = new Upload();

			strRegn = objrdExcel.readData("Login", 3, 4);

			strLoginUserName = objrdExcel.readData("Login", 3, 1);
			strLoginPassword = objrdExcel.readData("Login", 3, 2);
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
			strUsrFulName = "autouser";
			strUserName = "AutoUsr" + System.currentTimeMillis();
			strInitPwd = "abc123";
			strConfirmPwd = strInitPwd;
			/*
			 * STEP : Action:Precondition: <br>1. User 'X' is created in region
			 * 'A' providing all the rights (Select all the rights). Expected
			 * Result:No Expected Result
			 */
			// 592835
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.selectAllAdvancedRights(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUserName, strByRole, strByResourceType,
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

			/*
			 * STEP : Action:Login as 'RegAdmin' and navigate to region 'A'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 592836

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

			/*
			 * STEP : Action:Navigate to 'Setup >> Upload' Expected
			 * Result:'Upload List' screen is displayed. 'Upload Resources'
			 * option is available.
			 */
			// 592838

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select 'Upload Resources' Expected Result:'New
			 * Upload' screen is displayed with options to browse for a file
			 * along with 'Save' and 'Cancel' button.
			 */
			// 592840

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user 'X' Expected Result:Option 'Upload'
			 * is not available under 'Setup'
			 */
			// 592843

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
				strFuncResult = objUpload.checkOptionUploadInSetup(selenium,
						false);
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
			gstrTCID = "BQS-102968";
			gstrTO = "Verify that only RegAdmin (EMSystems Administrator) can import resources.";
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
'Description		:Verify that latitude and longitude are calculated accordingly 
'					while importing resources for which address is provided.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:06-11-2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testBQS103104() throws Exception {
		try {
			gstrTCID = "103104"; // Test Case Id
			gstrTO = " Verify that latitude and longitude are calculated " +
					"accordingly while importing resources for which address is provided.";													// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			gstrTimetaken = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strLoginUserName, strLoginPassword, strRegn;

			String strFuncResult = "";

			Login objLogin = new Login();
			Upload objUpload = new Upload();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strNStatType1 = "AutoNSt1_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";

			String strNSTvalue[] = new String[1];
			String strRTValue[] = new String[1];
			String strRSValue[] = new String[1];

			String strStatValue = "";
			String strAutoFilePath = propAutoITDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strUplPath = propPathDetails
					.getProperty("UploadResFile_Path");

			String strAutoFileName = propAutoITDetails
					.getProperty("CreateEve_UploadFile_FileName");
			String strResource1 = "AutoRs1_" + strTimeText;
			String strAbbrv = "Av";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			/*
			 * STEP : Action:Precondition: <br>1. Status type 'ST' is created
			 * and is associated to resource type 'RT' in region 'X' <br>2.
			 * Resource type ID for 'RT' is noted. <br>3. Provide mandatory data
			 * in the standard template 'X' along with the address. Expected
			 * Result:No Expected Result
			 */
			// 593141


			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			strRegn = objrdExcel.readData("Login", 3, 4);
			strLoginUserName = objrdExcel.readData("Login", 3, 1);
			strLoginPassword = objrdExcel.readData("Login", 3, 2);

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

			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strNStatType1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strNStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strNSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
						strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ strNSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName1);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String strCity = "NewYork";
			String strState = "Colorado";
			String[] strTestData = { "","N", strResource1, strAbbrv, strRTValue[0],
					"", "101", "", "N", "N", "", "", "", strCity, "CO", "", "",
					strContFName, strContLName, "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "" };
			try {
				assertEquals("", strFuncResult);
				objOFC.writeResultDataToParticularRow(strTestData, strUplPath,
						"Resource", 1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as RegAdmin and navigate to region 'X'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 593142
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
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

			/*
			 * STEP : Action:Navigate to 'Setup >> Upload' Expected
			 * Result:'Upload List' screen is displayed.
			 */
			// 593143

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select 'Upload Resources' Expected Result:'New
			 * Upload' screen is displayed.
			 */
			// 593144

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select 'Browse' and add file 'X' Expected
			 * Result:File 'X' is populated in the 'Spreadsheet' field.
			 */
			// 593145
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Deselect the option 'Test?' and 'Save' Expected
			 * Result:'Upload Details' screen is displayed. Appropriate values
			 * are present under the following column headers: <br>1. Row- 1
			 * <br>2. Resource ID- An appropriate value <br>3. Resource Name- As
			 * provided in the template <br>4. Failed- No <br>5. Geocode- A
			 * value 'X' is displayed <br>6. Resource Comments- None <br>7. User
			 * ID- None <br>8. Full Name- None <br>9. Failed- No <br>10. User
			 * Comments- No user data provided 'Return' button is available.
			 */
			// 593146
			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "1", "\\d+", strResource1, "No", "4",
					"", "", "", "No", "no user data provided" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select 'Return' Expected Result:'Upload List'
			 * screen is displayed.
			 */
			// 593147
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to 'Setup >> Resources' Expected
			 * Result:Uploaded resource is present in the 'Resource List' screen
			 * under appropriate column headers with values provided while
			 * uploading resource.
			 */
			// 593148
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.verifyResourceInfoInResList(selenium,
						strResource1, "No", "", strAbbrv, strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select to edit the resource. Expected Result:The
			 * values provided while uploading the resource is retained (State
			 * and City also). <br> <br>Appropriate value for
			 * 'Latitude/Longitude' is populated.
			 */
			// 593149

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			String strLatVal = "40.714353";
			String strLongVal = "-74.005973";
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.chkValueForLatitudeAndLongitude(selenium,
						strLatVal, strLongVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.verifyResMandValuesWithCityAndStInEditRes(selenium,
								strResource1, strAbbrv, strCity,
								strResrctTypName1, strStandResType, strState,
								strContFName, strContLName);
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
			gstrTCID = "BQS-103104";
			gstrTO = "Verify that latitude and longitude are calculated accordingly while importing resources for which address is provided.";
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
'Description		:Verify that user can 'Test' prior to uploading resources.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:07-11-2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testBQS102965() throws Exception {
		try {
			gstrTCID = "102965"; // Test Case Id
			gstrTO = " Verify that user can 'Test' prior to uploading resources.";
			gstrReason = "";
			gstrResult = "FAIL";

			
			gstrTimetaken = dts.timeNow("HH:mm:ss");

			String strLoginUserName, strLoginPassword, strRegn,
			strStatusTypeValue, strStatTypDefn, strStatusType,
			strResrctTypName, strStstTypLabl, strResrcTypName,
			strAutoFilePath, strAutoFileName, strExcelName, strUser, strDate;
			boolean blnSav, blnTest, blnSave, blnDisabled;

			String strFuncResult = "";

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Upload objUpload = new Upload();
			Resources objResources = new Resources();
			General objGen = new General();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			CreateUsers objUsr = new CreateUsers();

			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strNStatType1 = "AutoNSt1_" + strTimeText;
			String strNSTValue = "Number";
			strStatTypDefn = "Auto";

			String strNSTvalue[] = new String[1];
			String strRTValue[] = new String[1];
			String strRSValue[] = new String[1];

			String strStatValue = "";
			strAutoFilePath = propAutoITDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strUplPath = propPathDetails
					.getProperty("UploadResFile_Path");

			strAutoFileName = propAutoITDetails
					.getProperty("CreateEve_UploadFile_FileName");
			String strResource1 = "AutoRs1_" + strTimeText;
			String strAbbrv = "Av";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			// Search user criteria
			String strFILE_PATH = propPathDetails.getProperty("TestData_path");
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			/*
			 * STEP : Action:Precondition: <br>1. Status type 'ST' is created
			 * and is associated to resource type 'RT' in region 'X' <br>2.
			 * Resource type ID for 'RT' is noted. <br>3. Following data is
			 * provided in the standard template 'X': <br>1. ResourceName <br>2.
			 * Abbrev <br>3. ResourceTypeID <br>4. HAvBED <br>5. Shared <br>6.
			 * ContactFirstName <br>7. ContactLastNAme Expected Result:No
			 * Expected Result
			 */
			// 593130

			strRegn = objrdExcel.readData("Login", 3, 4);

			strLoginUserName = objrdExcel.readData("Login", 3, 1);
			strLoginPassword = objrdExcel.readData("Login", 3, 2);

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

			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strNStatType1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strNStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strNSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
						strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ strNSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName1);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUsr.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUsr.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strUsrPassword, strUsrPassword,
						strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUsr.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUserName, strByRole, strByResourceType,
					      strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strTestData = { "","N", strResource1, strAbbrv, strRTValue[0],
					"", "101", "", "N", "N", "", "", "", "", "", "", "",
					strContFName, strContLName, "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "" };

			try {
				assertEquals("", strFuncResult);
				objOFC.writeResultDataToParticularRow(strTestData, strUplPath,
						"Resource", 1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as RegAdmin and navigate to region 'X'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 593131

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
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

			/*
			 * STEP : Action:Navigate to 'Setup >> Upload' Expected
			 * Result:'Upload List' screen is displayed.
			 */
			// 593132

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select 'Upload Resources' Expected Result:'New
			 * Upload' screen is displayed.
			 */
			// 593133

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select 'Browse' and add file 'X' Expected
			 * Result:File 'X' is populated in the 'Spreadsheet' field.
			 */
			// 593134

			blnTest = true;
			blnSave = true;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, blnTest,
						blnSave);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Retain the selected value for the option 'Test?'
			 * and 'Save' Expected Result:'Upload Details' screen is displayed.
			 * <br> <br>Following fields are displayed with appropriate value
			 * and is disabled. <br>1. Spreadsheet: File path <br>2. Test?: Is
			 * selected <br>3. User: RegAdmin <br>4. Date: Current Date
			 * Appropriate values are present under the following column
			 * headers: <br>1. Row- 1 <br>2. Resource ID- An appropriate value
			 * <br>3. Resource Name- As provided in the template <br>4. Failed-
			 * No <br>5. Geocode- None <br>6. Resource Comments- None <br>7.
			 * User ID- None <br>8. Full Name- None <br>9. Failed- No <br>10.
			 * User Comments- No user data provided 'Return' button is
			 * available.
			 */
			// 593135

			

			blnDisabled = true;
			strDate = "";
			String strDateTime = "";
			try {
				assertEquals("", strFuncResult);
				String strCurYear = dts.getCurrentDate("yyyy");
				String[] strArFunRes = new String[5];
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strDate=dts.getTimeOfParticularTimeZone("CST", "MM/dd/yyyy");
				/*strDate = dts.converDateFormat(strArFunRes[0] + strArFunRes[1]
						+ strCurYear, "ddMMMyyyy", "MM/dd/yyyy");*/
				strDateTime = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"yyyy-MM-dd");
				strDateTime = strDateTime + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload
						.verifyElmentsInUploadDetailsPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strExcelName = "D:\\eclipse\\com.qsgsoft.EMResource\\"
						+ "SupportFiles\\UploadFiles\\ImportResource.xls";
				strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(
						selenium, strExcelName, strLoginUserName, strDate,
						blnTest, blnDisabled);
				if (strFuncResult.compareTo("") != 0) {
					strExcelName = "ImportResource.xls";
					strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(
							selenium, strExcelName, strLoginUserName, strDate,
							blnTest, blnDisabled);
				}

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "1", "", strResource1, "No", "", "",
					"", "", "No", "no user data provided" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select 'Return' Expected Result:'Upload List'
			 * screen is displayed with appropriate values under following
			 * column headers. <br>1. Action: View Details <br>2. Test?: Yes
			 * <br>3. Date: Resource upload date and time <br>4. User: RegAdmin
			 */
			// 593136

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strUpldColHeaders = { "Action", "Test?", "Date",
						"User" };
				String[] pStrArrUplListData = { "View Details", "Yes",
						strDateTime, strLoginUserName };
				strFuncResult = objUpload.verifyUplDetailsInUpldListPgeWithVaryingMin(
						selenium, strUpldColHeaders, pStrArrUplListData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to 'Setup >> Resources' Expected
			 * Result:Uploaded resource is NOT present in the 'Resource List'
			 * screen.
			 */
			// 593137

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
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

			/*
			 * STEP : Action:Navigate to 'Setup >> Users' and edit user 'A'
			 * Expected Result:Uploaded resource is not listed under 'Resource
			 * Rights' section.
			 */
			// 593140

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUsr.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUsr.navToEditUserPage(selenium, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUsr.verifyResourceInResRights(selenium,
						strResource1, false);
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
			gstrTCID = "BQS-102965";
			gstrTO = "Verify that user can 'Test' prior to uploading resources.";
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
'Description		:Verify that user can upload resources providing data in the mandatory field.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:08-11-2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testBQS102966() throws Exception {
		try {
			gstrTCID = "102966"; // Test Case Id
			gstrTO = " Verify that user can upload resources providing " +
					"data in the mandatory field.";
			gstrReason = "";
			gstrResult = "FAIL";

			
			gstrTimetaken = dts.timeNow("HH:mm:ss");

			String strFuncResult = "";

			String strLoginUserName, strLoginPassword, strRegn, 
			strStatusTypeValue, strStatTypDefn, strStatusType, 
			strResrctTypName, strStstTypLabl, strResrcTypName,
			strAutoFilePath, strAutoFileName, strExcelName, strUser, strDate;
			boolean blnSav, blnTest, blnSave, blnDisabled;

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Upload objUpload = new Upload();
			Resources objResources = new Resources();
			General objGen = new General();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			CreateUsers objUsr = new CreateUsers();

			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strNStatType1 = "AutoNSt1_" + strTimeText;
			String strNSTValue = "Number";
			strStatTypDefn = "Auto";

			String strNSTvalue[] = new String[1];
			String strRTValue[] = new String[1];
			String strRSValue[] = new String[1];

			String strStatValue = "";
			strAutoFilePath = propAutoITDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strUplPath = propPathDetails
					.getProperty("UploadResFile_Path");

			strAutoFileName = propAutoITDetails
					.getProperty("CreateEve_UploadFile_FileName");
			String strResource1 = "AutoRs1_" + strTimeText;
			String strAbbrv = "Av";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			// Search user criteria
			String strFILE_PATH = propPathDetails.getProperty("TestData_path");
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			/*
			 * STEP : Action:Precondition: <br>1. Status type 'ST' is created
			 * and is associated to resource type 'RT' in region 'X' <br>2.
			 * Resource type ID for 'RT' is noted. <br>3. Following data is
			 * provided in the standard template 'X': <br>1. ResourceName <br>2.
			 * Abbrev <br>3. ResourceTypeID <br>4. HAvBED <br>5. Shared <br>6.
			 * ContactFirstName <br>7. ContactLastNAme Expected Result:No
			 * Expected Result
			 */
			// 592851
			strRegn = objrdExcel.readData("Login", 3, 4);

			strLoginUserName = objrdExcel.readData("Login", 3, 1);
			strLoginPassword = objrdExcel.readData("Login", 3, 2);

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

			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strNStatType1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strNStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strNSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
						strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ strNSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName1);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUsr.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUsr.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strUsrPassword, strUsrPassword,
						strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUsr.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUserName, strByRole, strByResourceType,
					      strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strTestData = { "","N", strResource1, strAbbrv, strRTValue[0],
					"", "101", "", "N", "N", "", "", "", "", "", "", "",
					strContFName, strContLName, "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "" };
			try {
				assertEquals("", strFuncResult);
				objOFC.writeResultDataToParticularRow(strTestData, strUplPath,
						"Resource", 1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as RegAdmin and navigate to region 'X'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 592853
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
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
			/*
			 * STEP : Action:Navigate to 'Setup >> Upload' Expected
			 * Result:'Upload List' screen is displayed.
			 */
			// 592854
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select 'Upload Resources' Expected Result:'New
			 * Upload' screen is displayed. <br> <br>Field 'Spreadsheet' is
			 * displayed as mandatory with a 'Browse' button. An instruction
			 * 'Only .xls files are allowed. Maximum file size is 5 megabytes
			 * (MB).' is displayed. <br> <br>Option 'Test?' is available with a
			 * checkbox which is selected by default. An instruction 'If
			 * selected, then the system will not actually create Resources.' is
			 * displayed.
			 */
			// 592868
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload
						.verifyElmentsInNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select 'Browse' and add file 'X' Expected
			 * Result:File 'X' is populated in the 'Spreadsheet' field.
			 */
			// 592869

			blnTest = false;
			blnSave = true;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, blnTest,
						blnSave);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Deselect the option 'Test?' and 'Save' Expected
			 * Result:'Upload Details' screen is displayed. <br> <br>Following
			 * fields are displayed with appropriate value and is disabled.
			 * <br>1. Spreadsheet: File path <br>2. Test?: Is deselected <br>3.
			 * User: RegAdmin <br>4. Date: Current Date Appropriate values are
			 * present under the following column headers: <br>1. Row- 1 <br>2.
			 * Resource ID- An appropriate value <br>3. Resource Name- As
			 * provided in the template <br>4. Failed- No <br>5. Geocode- None
			 * <br>6. Resource Comments- None <br>7. User ID- None <br>8. Full
			 * Name- None <br>9. Failed- No <br>10. User Comments- No user data
			 * provided 'Return' button is available.
			 */
			// 592874

			blnDisabled = true;
			strDate = "";
			String strDateTime = "";
			try {
				assertEquals("", strFuncResult);
				String strCurYear = dts.getCurrentDate("yyyy");
				String[] strArFunRes = new String[5];
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strDate=dts.getTimeOfParticularTimeZone("CST", "MM/dd/yyyy");
				/*strDate = dts.converDateFormat(strArFunRes[0] + strArFunRes[1]
						+ strCurYear, "ddMMMyyyy", "MM/dd/yyyy");*/
				strDateTime = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"yyyy-MM-dd");
				strDateTime = strDateTime + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload
						.verifyElmentsInUploadDetailsPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strExcelName = "D:\\eclipse\\com.qsgsoft.EMResource\\"
						+ "SupportFiles\\UploadFiles\\ImportResource.xls";
				strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(
						selenium, strExcelName, strLoginUserName, strDate,
						blnTest, blnDisabled);

				if (strFuncResult.compareTo("") != 0) {
					strExcelName = "ImportResource.xls";
					strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(
							selenium, strExcelName, strLoginUserName, strDate,
							blnTest, blnDisabled);

				}
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "1", "\\d+", strResource1, "No", "",
					"", "", "", "No", "no user data provided" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select 'Return' Expected Result:'Upload List'
			 * screen is displayed with appropriate values under following
			 * column headers. <br>1. Action: View Details <br>2. Test?: No
			 * <br>3. Date: Resource upload date and time <br>4. User: RegAdmin
			 */
			// 592896

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strUpldColHeaders = { "Action", "Test?", "Date",
						"User" };
				String[] pStrArrUplListData = { "View Details", "No",
						strDateTime, strLoginUserName };
				strFuncResult = objUpload.verifyUplDetailsInUpldListPge(
						selenium, strUpldColHeaders, pStrArrUplListData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to 'Setup >> Resources' Expected
			 * Result:Uploaded resource is present in the 'Resource List' screen
			 * under appropriate column headers with values provided while
			 * uploading resource.
			 */
			// 592965
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyResourceInfoInResList(
						selenium, strResource1, "No", "", strAbbrv,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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
			/*
			 * STEP : Action:Select to edit the resource. Expected Result:The
			 * values provided while uploading the resource is retained.
			 */
			// 592983

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.verifyResMandValuesWithCityAndStInEditRes(selenium,
								strResource1, strAbbrv, "", strResrctTypName1,
								strStandResType, "", strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select 'Edit Status Types' Expected Result:'ST' is
			 * selected and is disabled.
			 */
			// 592985
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifySTisCheckedOrNot(selenium,
						strNSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifySTisEditableOrNot(selenium,
						strNSTvalue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to 'Setup >> Users' and edit user 'A'
			 * Expected Result:Resource is listed under 'Resource Rights'
			 * section. <br> <br>User has none of the rights for the resource.
			 */
			// 592986
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUsr.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUsr.navToEditUserPage(selenium, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUsr.verifyResourceInResRights(selenium,
						strResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUsr
						.checkResourceRightsWitRSValues(selenium, strResource1,
								strRSValue[0], false, false, false, false);
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
			gstrTCID = "BQS-102966";
			gstrTO = "Verify that user can upload resources providing data in the mandatory field.";
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
'Description		:Verify that user cannot upload duplicate resources.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:08-11-2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testBQS102967() throws Exception {
		try {
			gstrTCID = "102967"; // Test Case Id
			gstrTO = " Verify that user cannot upload duplicate resources.";
			gstrReason = "";
			gstrResult = "FAIL";

			
			gstrTimetaken = dts.timeNow("HH:mm:ss");

			String strFuncResult = "";

			String strLoginUserName, strLoginPassword, strRegn, strStatusTypeValue,
			strStatTypDefn, strStatusType, strResrctTypName, strStstTypLabl, 
			strResrcTypName, strAutoFilePath, strAutoFileName, strExcelName, strUser, strDate;
			boolean blnSav, blnTest, blnSave, blnDisabled;

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Upload objUpload = new Upload();
			Resources objResources = new Resources();
			General objGen = new General();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			CreateUsers objUsr = new CreateUsers();

			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strNStatType1 = "AutoNSt1_" + strTimeText;
			String strNSTValue = "Number";
			strStatTypDefn = "Auto";

			String strNSTvalue[] = new String[1];
			String strRTValue[] = new String[1];
			String strRSValue[] = new String[3];

			String strStatValue = "";
			strAutoFilePath = propAutoITDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strUplPath = propPathDetails
					.getProperty("UploadResFile_Path");
			String strManyResUplPath = propPathDetails
					.getProperty("UploadManyResFile_Path");

			strAutoFileName = propAutoITDetails
					.getProperty("CreateEve_UploadFile_FileName");
			String strResource1 = "AutoRs1_" + strTimeText;
			String strAbbrv1 = "Av";
			String strAbbrv2 = "Avs";
			String strAbbrv3 = "Avc";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strResource2 = "AutoRs2_" + strTimeText;
			String strResource3 = "AutoRs3_" + strTimeText;

			/*
			 * STEP : Action:Precondition: <br>1. Resource type ID for 'RT' is
			 * noted and is associated with all the resources mentioned below.
			 * <br>2. Resource 'RS1' has been uploaded to region 'R' from the
			 * standard template 'X' <br>3. Resource 'RS2' has been created in
			 * region 'R' <br>4. Standard template has the following values:
			 * <br>1. RS1 <br>2. RS2 <br>3. RS3 <br>4. RS3 (Duplicates the above
			 * resource) Expected Result:No Expected Result
			 */

			// 593047
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strRegn = objrdExcel.readData("Login", 3, 4);

			strLoginUserName = objrdExcel.readData("Login", 3, 1);
			strLoginPassword = objrdExcel.readData("Login", 3, 2);

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

			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNSTValue, strNStatType1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strNStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strNSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ strNSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
						strResrctTypName1);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strTestData = { "", "N",strResource1, strAbbrv1,
						strRTValue[0], "", "101", "", "N", "N", "", "", "", "",
						"", "", "", strContFName, strContLName, "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "" };
				objOFC.writeResultDataToParticularRow(strTestData, strUplPath,
						"Resource", 1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			blnTest = false;
			blnSave = true;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, blnTest,
						blnSave);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(selenium,
						strResource2, strAbbrv2, strResrctTypName1,
						strStandResType, strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(selenium,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(selenium,
						strResource2, "No", "", strAbbrv2, strResrctTypName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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
				String[] strTestData = { "","N", strResource1, strAbbrv1,
						strRTValue[0], "", "101", "", "N", "N", "", "", "", "",
						"", "", "", strContFName, strContLName, "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "" };
				objOFC.writeResultDataToParticularRow(strTestData,
						strManyResUplPath, "ResourceUsr", 1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strTestData = { "","N", strResource2, strAbbrv2,
						strRTValue[0], "", "101", "", "N", "N", "", "", "", "",
						"", "", "", strContFName, strContLName, "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "" };
				objOFC.writeResultDataToParticularRow(strTestData,
						strManyResUplPath, "ResourceUsr", 2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strTestData = { "","N", strResource3, strAbbrv3,
						strRTValue[0], "", "101", "", "N", "N", "", "", "", "",
						"", "", "", strContFName, strContLName, "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "" };
				objOFC.writeResultDataToParticularRow(strTestData,
						strManyResUplPath, "ResourceUsr", 3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strTestData = { "", "N",strResource3, strAbbrv3,
						strRTValue[0], "", "101", "", "N", "N", "", "", "", "",
						"", "", "", strContFName, strContLName, "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "" };
				objOFC.writeResultDataToParticularRow(strTestData,
						strManyResUplPath, "ResourceUsr", 4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as RegAdmin and navigate to region 'X'
			 * Expected Result:No Expected Result
			 */

			// 593048
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

			/*
			 * STEP : Action:Navigate to 'Setup >> Upload' Expected
			 * Result:'Upload List' screen is displayed.
			 */

			// 593050
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Once again select to upload the resources RS1, RS2,
			 * RS3 and RS3 from template X Expected Result:'Upload Details'
			 * screen is displayed with the following resource comments for each
			 * row. <br> <br>Row1: duplicates resource (&lt;resource id of
			 * RS1&gt;) and 'Yes' is displayed for column 'Failed' <br>Row2:
			 * duplicates resource (&lt;resource id of RS2&gt;) and 'Yes' is
			 * displayed for column 'Failed' <br>Row3: No resource comment and
			 * 'No' is displayed for column 'Failed' <br>Row4: duplicates
			 * resource in row 3 and 'Yes' is displayed for column 'Failed'
			 */

			// 593084

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			blnTest = false;
			blnSave = true;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strManyResUplPath, strAutoFileName,
						blnTest, blnSave);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "1", "", strResource1, "Yes", "",
					"duplicates resource (" + strRSValue[0] + ")", "", "",
					"No", "no user data provided" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[][] pStrArrUplData1 = {
					{ "1", "", strResource1, "Yes", "",
							"duplicates resource (" + strRSValue[0] + ")", "",
							"", "No", "no user data provided" },
					{ "2", "", strResource2, "Yes", "",
							"duplicates resource (" + strRSValue[1] + ")", "",
							"", "No", "no user data provided" },
					{ "3", "\\d+", strResource3, "No", "", "", "", "", "No",
							"no user data provided" },
					{ "4", "", strResource3, "Yes", "",
							"duplicates resource in row 3", "", "", "No",
							"no user data provided" }, };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload
						.verifyUplDetailsInUpldDetailsPge_MultipleData(
								selenium, pStrArrUplData1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to 'Setup >> Resources' Expected
			 * Result:Only RS3 is uploaded. <br> <br>Duplicates of RS1, RS2 and
			 * RS3 is not uploaded
			 */

			// 593129
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.VerifyResource(selenium, strResource3,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.checkDuplicateOfResNotPresent(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.checkDuplicateOfResNotPresent(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.checkDuplicateOfResNotPresent(selenium,
						strResource3);
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
			gstrTCID = "BQS-102967";
			gstrTO = "Verify that user cannot upload duplicate resources.";
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
