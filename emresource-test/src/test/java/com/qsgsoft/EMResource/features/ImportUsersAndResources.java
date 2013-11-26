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
' Requirement		:Import Users and Resources
' Requirement Group	:Setting up Resources
' Product		    :EMResource v3.20
' Date			    :12-11-2012
' Author		    :QSG
' ------------------------------------------------------------------
' Modified Date				Modified By
' Date					Name
'*******************************************************************/


public class ImportUsersAndResources  {

//Log4j object to write log entries to the Log files
static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.features.ImportUsersAndResources");
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

	//Create object to read environment properties file
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	propEnvDetails = objReadEnvironment.readEnvironment();
	
	Paths_Properties objPathsProps = new Paths_Properties();
	propAutoITDetails=objPathsProps.ReadAutoit_FilePath();
	
	propPathDetails = objPathsProps.Read_FilePath();
	//Retrieve browser information
	gstrBrowserName=propEnvDetails.getProperty("Browser").substring(1)+" "+propEnvDetails.getProperty("BrowserVersion");
	//Retrieve the value of page load time limit
	gstrTimeOut=propEnvDetails.getProperty("TimeOut");
	//create an object to refer to Element ID properties file

	//Create a new selenium session
	selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
		4444, propEnvDetails.getProperty("Browser"), propEnvDetails
		.getProperty("urlEU"));
	//Start Selenium RC
	selenium.start();
	//Maximize the window
	selenium.windowMaximize();
	
	seleniumPrecondition = new DefaultSelenium(propEnvDetails
			.getProperty("Server"), 4444, propEnvDetails
			.getProperty("BrowserPrecondition"), propEnvDetails
			.getProperty("urlEU"));
	
	seleniumPrecondition.start();
	seleniumPrecondition.windowMaximize();
	
	selenium.setTimeout(propEnvDetails.getProperty("TimeOut"));
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
	try {
		seleniumPrecondition.close();
	} catch (Exception e) {

	}
	seleniumPrecondition.stop();
	
	selenium.close();
	selenium.stop();
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
					gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetaken, gstrdate,
					sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);

}
	/***************************************************************
	'Description		:Verify that both resources and users can be imported at a time.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:16-11-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS103541() throws Exception {
		try {
			gstrTCID = "103541"; // Test Case Id
			gstrTO = " Verify that both resources and users can be imported at a time.";// Test
																						// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			gstrTimetaken = dts.timeNow("HH:mm:ss");
			General objGen = new General();

			String strFuncResult = "";
			String strAutoFilePath = propAutoITDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strUplPath = propPathDetails
					.getProperty("UploadResFile_Path");

			String strAutoFileName = propAutoITDetails
					.getProperty("CreateEve_UploadFile_FileName");

			Login objLogin = new Login();// object of class Login

			Roles objRoles = new Roles();

			CreateUsers objCreateUsers = new CreateUsers();

			Upload objUpload = new Upload();

			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";

			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strNStatType1 = "AutoNSt1_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";

			String strNSTvalue[] = new String[1];
			String strRTValue[] = new String[1];
	

			String strStatValue = "";

			String strResource1 = "AutoRs1_" + strTimeText;
			String strAbbrv = "Av";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strUsrFulName = "Full" + System.currentTimeMillis();
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			/*
			 * STEP : Action:User has 'View' right to the resource uploaded.
			 * Expected Result:No Expected Result
			 */
			// 595236

			/*
			 * STEP : Action:Precondition: <br>1. Status type 'ST' is created
			 * and is associated to resource type 'RT' in region 'X' <br>2.
			 * Resource type ID for 'RT' is noted. <br>3. Role 'R' is created in
			 * region 'X' <br>4. Role ID for for role 'R' is noted. <br>5.
			 * Following data is provided in the standard template 'X': <br>1.
			 * ResourceName <br>2. Abbrev <br>3. ResourceTypeID <br>4. HAvBED
			 * <br>5. Shared <br>6. ContactFirstName <br>7. ContactLastNAme
			 * <br>8. UserID <br>9. RoleID <br>10. Password <br>11. FullName
			 * Expected Result:No Expected Result
			 */
			// 595239

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
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
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navCreateRolePge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.roleMandtoryFlds(seleniumPrecondition,
						strRolesName);
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
				String[] strTestData = { "", "N",strResource1, strAbbrv,
						strRTValue[0], "", "101", "", "N", "N", "", "", "", "",
						"", "", "", strContFName, strContLName, "", "", "", "",
						"", strUserName, strRoleValue, "", strUsrPassword,
						strUsrFulName, "", "", "", "", "", "", "", "", "", "",
						"" };

				objOFC.writeResultDataToParticularRow(strTestData, strUplPath,
						"Resource", 1);
			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as RegAdmin and navigate to region 'X'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 595248
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
			// 595250
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
			// 595281
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
			// 595282
			boolean blnTest = false;
			boolean blnSave = true;
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
			 * Result:'Upload Details' screen is displayed. Following fields are
			 * displayed with appropriate value and is disabled. <br>1.
			 * Spreadsheet: File path <br>2. Test?: Is deselected <br>3. User:
			 * RegAdmin <br>4. Date: Current Date Appropriate values are present
			 * under the following column headers: <br>1. Row- 1 <br>2. Resource
			 * ID- An appropriate value (Hyper linked) <br>3. Resource Name- As
			 * provided in the template <br>4. Failed- No <br>5. Geocode- None
			 * <br>6. Resource Comments- None <br>7. User ID- As provided (Hyper
			 * linked) <br>8. Full Name- As Provided <br>9. Failed- No <br>10.
			 * User Comments- None 'Return' button is available.
			 */
			// 595301

			

			boolean blnDisabled = true;
			String strDate = "";
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
				String strExcelName = "D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
						+ "UploadFiles\\ImportResource.xls";
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
					"", strUserName, strUsrFulName, "No", "" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the 'Resource ID' Expected Result:'Edit
			 * Resource' screen of the uploaded resource is displayed. The
			 * values provided while uploading the resource is retained.
			 */
			// 595354
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePageFrmUplDetails(
						selenium, strResource1);
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
			 * STEP : Action:Navigate to 'Setup >> Resources' Expected
			 * Result:Uploaded resource is present in the 'Resource List' screen
			 * under appropriate column headers with values provided while
			 * uploading resource.
			 */
			// 595362
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
			 * values provided while uploading the resource is retained.
			 */
			// 595363
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(selenium,
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
			 * STEP : Action:Navigate to 'Setup >> Users' Expected
			 * Result:Uploaded user is present in the 'User List' screen under
			 * appropriate column headers with values provided while uploading
			 * resource.
			 */
			// 595374
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUserDetailsInUserList(
						selenium, strUserName, strUsrFulName, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select to edit the user Expected Result:The values
			 * provided while uploading the user is retained. Checkbox for role
			 * 'R' is selected User has view right to all the resources in that
			 * region along with the resource that was uploaded along with it.
			 */
			// 595375
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUsrMandValuesWithRole(
						selenium, strUserName, strUsrFulName, strRoleValue,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyViewResourceRight(
						selenium, "SELECT_ALL", "viewRight", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select 'Users' link for the uploaded resource
			 * Expected Result:'View Resource' right is selected for the user
			 * uploaded along with it
			 */
			// 595402
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToAssignUsersOFRes(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource1, strUserName, false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to 'Setup >> Upload' Expected
			 * Result:'Upload List' screen is displayed.
			 */
			// 595531
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select 'View Details' for the recently uploaded
			 * resource and user Expected Result:'Upload Details' screen is
			 * displayed.
			 */
			// 595534
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload
						.navToUploadDetailsPageForRecentRes(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select the 'UserID' Expected Result:'Edit User'
			 * screen is displayed of the uploaded user The values provided
			 * while uploading the user is retained.
			 */
			// 595535
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToEditUserPageFrmUplDetails(
						selenium, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUsrMandValuesWithRole(
						selenium, strUserName, strUsrFulName, strRoleValue,
						true);
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
			gstrTCID = "BQS-103541";
			gstrTO = "Verify that both resources and users can be imported at a time.";
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
	'Description		:Verify that resources and users can be uploaded providing any of the affiliated  right on the resources for the users.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:12-11-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS103573() throws Exception {
		try {
			gstrTCID = "103573"; // Test Case Id
			gstrTO = " Verify that resources and users can be uploaded providing"
					+ " any of the affiliated  right on the resources for the users.";
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			gstrTimetaken = dts.timeNow("HH:mm:ss");

			String strFuncResult = "";
			String strAutoFilePath = propAutoITDetails
					.getProperty("CreateEve_UploadFile_Path");

			String strManyResUplPath = propPathDetails
					.getProperty("UploadManyUsrResFile_Path");

			String strAutoFileName = propAutoITDetails
					.getProperty("CreateEve_UploadFile_FileName");

			Login objLogin = new Login();// object of class Login

			Roles objRoles = new Roles();

			CreateUsers objCreateUsers = new CreateUsers();

			Upload objUpload = new Upload();

			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";

			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strNStatType1 = "AutoNSt1_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";

			String strNSTvalue[] = new String[1];
			String strRTValue[] = new String[1];
			String strRSValue[] = new String[3];

			String strStatValue = "";

			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strResource3 = "AutoRs3_" + strTimeText;

			String strAbbrv1 = "Av";
			String strAbbrv2 = "As";
			String strAbbrv3 = "Ab";

			String strContFName = "auto";
			String strContLName = "qsg";
			String strUsrFulName1 = "Full1" + System.currentTimeMillis();
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strUsrFulName2 = "Full2" + System.currentTimeMillis();
			String strUserName2 = "AutoUsr2" + System.currentTimeMillis();

			String strUsrFulName3 = "Full3" + System.currentTimeMillis();
			String strUserName3 = "AutoUsr3" + System.currentTimeMillis();
			/*
			 * STEP : Action:Precondition: <br>1. Role 'R' is created in region
			 * 'X' <br>2. Role ID for role 'R' is noted in region 'X'. <br>3.
			 * Resource type 'RT' which is associated to 'ST' <br>4. Resource
			 * type ID for 'RT' is noted. <br>5. Following data is provided in
			 * the standard template 'X' for: <br>1. ResourceName <br>2. Abbrev
			 * <br>3. ResourceTypeID <br>4. HAvBED <br>5. Shared <br>6.
			 * ContactFirstName <br>7. ContactLastNAme <br>8. UserID <br>9.
			 * RoleID <br>10. Password <br>11. FullName <br>6. User U1 is
			 * provided with 'Associated' right for RS1 <br>7. User U2 is
			 * provided with 'Update' right for RS2 <br>8. User U3 is provided
			 * with 'Reports' right for RS3 Expected Result:No Expected Result
			 */
			// 595477

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
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navCreateRolePge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.roleMandtoryFlds(seleniumPrecondition,
						strRolesName);
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
				String[] strTestData = { "", "N", strResource1, strAbbrv1,
						strRTValue[0], "", "101", "", "N", "N", "", "", "", "",
						"", "", "", strContFName, strContLName, "", "", "", "",
						"", strUserName1, strRoleValue, "", strUsrPassword,
						strUsrFulName1, "", "", "", "", "", "", "", "", "Y",
						"", "" };

				objOFC.writeResultDataToParticularRow(strTestData,
						strManyResUplPath, "ResourceUsr", 1);
			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strTestData = { "", "N",strResource2, strAbbrv2,
						strRTValue[0], "", "101", "", "N", "N", "", "", "", "",
						"", "", "", strContFName, strContLName, "", "", "", "",
						"", strUserName2, strRoleValue, "", strUsrPassword,
						strUsrFulName2, "", "", "", "", "", "", "", "", "",
						"Y", "" };

				objOFC.writeResultDataToParticularRow(strTestData,
						strManyResUplPath, "ResourceUsr", 2);
			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strTestData = { "", "N", strResource3, strAbbrv3,
						strRTValue[0], "", "101", "", "N", "N", "", "", "", "",
						"", "", "", strContFName, strContLName, "", "", "", "",
						"", strUserName3, strRoleValue, "", strUsrPassword,
						strUsrFulName3, "", "", "", "", "", "", "", "", "", "",
						"Y" };

				objOFC.writeResultDataToParticularRow(strTestData,
						strManyResUplPath, "ResourceUsr", 3);
			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as RegAdmin and navigate to region 'X'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 595479
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
			// 595480
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
			// 595481
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
			// 595482
			boolean blnTest = false;
			boolean blnSave = true;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strManyResUplPath, strAutoFileName,
						blnTest, blnSave);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Deselect the option 'Test?' and 'Save' Expected
			 * Result:'Upload Details' screen is displayed. Rows from 1 to 3 is
			 * displayed with appropriate values are present under the following
			 * column headers: <br>1. Row- <br>2. Resource ID <br>3. Resource
			 * Name <br>4. Failed <br>5. Geocode <br>6. Resource Comments <br>7.
			 * User ID <br>8. Full Name <br>9. Failed <br>10. User Comments
			 * 'Return' button is available.
			 */
			// 595489
			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "1", "\\d+", strResource1, "No", "",
					"", strUserName1, strUsrFulName1, "No", "" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[][] pStrArrUplData1 = {
					{ "1", "\\d+", strResource1, "No", "", "", strUserName1,
							strUsrFulName1, "No", "" },
					{ "2", "\\d+", strResource2, "No", "", "", strUserName2,
							strUsrFulName2, "No", "" },
					{ "3", "\\d+", strResource3, "No", "", "", strUserName3,
							strUsrFulName3, "No", "" }, };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload
						.verifyUplDetailsInUpldDetailsPge_MultipleData(
								selenium, pStrArrUplData1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select 'Return' Expected Result:'Upload List'
			 * screen is displayed
			 */
			// 595491
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to 'Setup >> Users' Expected Result:User
			 * 'U1' is uploaded which has 'Associate' right on resource 'RS1'
			 * and only 'View' right on other resources (RS2 and RS3). User 'U2'
			 * is uploaded which has 'Update' right on resource 'RS2' and only
			 * 'View' right on other resources (RS1 and RS3). User 'U3' is
			 * uploaded which has 'Reports' right on resource 'RS3' and only
			 * 'View' right on other resources (RS1 and RS2).
			 */
			// 595497
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
				String strResVal = objRs.fetchResValueInResList(selenium,
						strResource3);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[2] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
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
				strFuncResult = objCreateUsers.verifyUsrInUserListPage(
						selenium, strUserName1, true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// U1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource1, strRSValue[0], true, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource2, strRSValue[1], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource3, strRSValue[2], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// U2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource2, strRSValue[1], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource1, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource3, strRSValue[2], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// U3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource3, strRSValue[2], false, false,
						true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource1, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource2, strRSValue[1], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to 'Setup >> Resources' and select 'Users'
			 * link for a resource 'RS1' Expected Result:User 'U1' has associate
			 * right on RS1
			 */
			// 595499
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToAssignUsersOFRes(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource1, strUserName1, true, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select 'Users' link for a resource 'RS2' Expected
			 * Result:User 'U2' has update right on RS2
			 */
			// 595506
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToAssignUsersOFRes(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource2, strUserName2, false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select 'Users' link for a resource 'RS3' Expected
			 * Result:User 'U3' has reports right on RS3
			 */
			// 595507
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToAssignUsersOFRes(selenium,
						strResource3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource3, strUserName3, false, false,
						true, true);
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
			gstrTCID = "BQS-103573";
			gstrTO = "Verify that resources and users can be uploaded providing any of the affiliated  right on the resources for the users.";
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
	'Description		:Verify that resources and users can be uploaded providing all the affiliated right on the resources for the users.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:12-11-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS103585() throws Exception {
		try {
			gstrTCID = "103585"; // Test Case Id
			gstrTO = " Verify that resources and users can be uploaded providing"
					+ " all the affiliated right on the resources for the users.";// Test
																					// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			gstrTimetaken = dts.timeNow("HH:mm:ss");

			String strFuncResult = "";
			String strAutoFilePath = propAutoITDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strUplPath = propPathDetails
					.getProperty("UploadResFile_Path");

			String strAutoFileName = propAutoITDetails
					.getProperty("CreateEve_UploadFile_FileName");

			Login objLogin = new Login();// object of class Login

			Roles objRoles = new Roles();

			CreateUsers objCreateUsers = new CreateUsers();

			Upload objUpload = new Upload();

			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";

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

			String strResource1 = "AutoRs1_" + strTimeText;
			String strAbbrv = "Av";

			String strContFName = "auto";
			String strContLName = "qsg";
			String strUsrFulName = "Full" + System.currentTimeMillis();
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			/*
			 * STEP : Action:Precondition: <br>1. Role 'R' is created in region
			 * 'X' <br>2. Role ID for role 'R' is noted in region 'X'. <br>3.
			 * Resource type 'RT' which is associated to 'ST' <br>4. Resource
			 * type ID for 'RT' is noted. <br>5. Following data is provided in
			 * the standard template 'X' for: <br>1. ResourceName <br>2. Abbrev
			 * <br>3. ResourceTypeID <br>4. HAvBED <br>5. Shared <br>6.
			 * ContactFirstName <br>7. ContactLastNAme <br>8. UserID <br>9.
			 * RoleID <br>10. Password <br>11. FullName <br>6. User U1 is
			 * provided with 'Associated', 'Update' and 'Reports' right for RS
			 * in the template X Expected Result:No Expected Result
			 */
			// 595542

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
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
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navCreateRolePge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.roleMandtoryFlds(seleniumPrecondition,
						strRolesName);
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
				String[] strTestData = { "", "N", strResource1, strAbbrv,
						strRTValue[0], "", "101", "", "N", "N", "", "", "", "",
						"", "", "", strContFName, strContLName, "", "", "", "",
						"", strUserName, strRoleValue, "", strUsrPassword,
						strUsrFulName, "", "", "", "", "", "", "", "", "Y",
						"Y", "Y" };

				objOFC.writeResultDataToParticularRow(strTestData, strUplPath,
						"Resource", 1);
			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as RegAdmin and navigate to region 'X'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 595543
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
			// 595544

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
			// 595545
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
			// 595546
			boolean blnTest = false;
			boolean blnSave = true;
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
			 * Result:'Upload Details' screen is displayed. Rows from 1 to 3 is
			 * displayed with appropriate values are present under the following
			 * column headers: <br>1. Row- <br>2. Resource ID <br>3. Resource
			 * Name <br>4. Failed <br>5. Geocode <br>6. Resource Comments <br>7.
			 * User ID <br>8. Full Name <br>9. Failed <br>10. User Comments
			 * 'Return' button is available.
			 */
			// 595547
			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "1", "\\d+", strResource1, "No", "",
					"", strUserName, strUsrFulName, "No", "" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select 'Return' Expected Result:'Upload List'
			 * screen is displayed
			 */
			// 595548
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to 'Setup >> Users' Expected Result:User
			 * 'U1' is uploaded which has 'Associate', 'Update' and 'Reports'
			 * right on resource 'RS' User U1 has only 'View' right on other
			 * resources
			 */
			// 595549
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
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUsrInUserListPage(
						selenium, strUserName, true, false);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource1, strRSValue[0], true, true,
						true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to 'Setup >> Resources' and select 'Users'
			 * link for a resource 'RS' Expected Result:User 'U1' has
			 * 'Associate', 'Update', 'Reports' and 'View' right on 'RS'
			 */
			// 595550
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToAssignUsersOFRes(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(
						selenium, strResource1, strUserName, true, true, true,
						true);
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
			gstrTCID = "BQS-103585";
			gstrTO = "Verify that resources and users can be uploaded providing all the affiliated right on the resources for the users.";
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
