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

/*********************************************************************
 * ' Description       :This class contains test cases from requirement 
 * ' Requirement       :Inactivate resource type 
 * ' Requirement Group :Setting up Resource types '
 * ' Product           :EMResource Base 
 * ' Date              :05-06-2012 
 * ' Author            :QSG 
 * -------------------------------------------------------------------
 * Modified Date                                            Date
 * Modified By                                              Name 
 *********************************************************************/

public class InactivateResourceType {

	// Log4j object to write log entries to the Log files
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.InactivateResourceType");
	static {
		BasicConfigurator.configure();
	}
	// Objects to access the common functions
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;

	/*
	 * Global variables to store the test case details – TestCaseID, Test
	 * Objective,Result, Reason for failure
	 */
	String gstrTCID, gstrTO, gstrResult, gstrReason;

	// Selenium Object
	Selenium selenium1, selenium2,seleniumPrecondition;

	// Object for date time settings
	Date_Time_settings dts = new Date_Time_settings();

	public Properties propElementDetails; // Property variable for ElementID //
											// file
	public Properties propEnvDetails;// Property variable for Environment data
	Properties pathProps;
	Properties propElementAutoItDetails;
	public static Properties browserProps = new Properties();
	public static String gstrBrowserName;// Variable to store browser name
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;// Result
	double gdbTimeTaken; // Variable to store the time taken
	public static Date gdtStartDate;// Date variable
	public static long sysDateTime;
	public static long gsysDateTime = 0;
	public static String gstrTimeOut = "";
	public static String sessionId_FF36, sessionId_FF20, sessionId_FF3,
			sessionId_FF35, sessionId_IE6, sessionId_IE7, sessionId_IE8,
			session_SF3, session_SF4, session_op9, session_op10, session_GC,
			StrSessionId = "", StrSessionId1, StrSessionId2;

	/************************************************************************************
	 * This function is called the setup() function which is executed before
	 * every test.
	 * 
	 * The function will take care of creating a new selenium session for every
	 * test
	 * 
	 ************************************************************************************/
	@Before
	public void setUp() throws Exception {

		// Create object to read environment properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		// Retrieve browser information

		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		// Retrieve the value of page load time limit
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		// create an object to refer to Element ID properties file
		@SuppressWarnings("unused")
		ElementId_properties objelementProp = new ElementId_properties();
		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();
		propElementAutoItDetails = objAP.ReadAutoit_FilePath();
		// Create a new selenium session
		
		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		selenium1 = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));

		selenium2 = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));
		
		// Start Selenium RC
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		seleniumPrecondition.setTimeout("");

		// Start Selenium RC
		selenium1.start();
		selenium1.windowMaximize();
		selenium1.setTimeout("");

		// Start Selenium RC
		selenium2.start();
		selenium2.windowMaximize();
		selenium2.setTimeout("");

		// Define object to call support functions to read excel, date etc
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();

	}

	/****************************************************************************************
	 * * This function is called the teardown() function which is executed after
	 * every test. The function will take care of stopping the selenium session
	 * for every test and writing the execution result of the test.
	 * 
	 ******************************************************************************************/
	@After
	public void tearDown() throws Exception {

		// kill browser
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
		}
		seleniumPrecondition.stop();
		try {
			selenium1.close();
		} catch (Exception e) {

		}
		selenium1.stop();

		try {
			selenium2.close();
		} catch (Exception e) {

		}
		selenium2.stop();
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
		gstrdate = dts.getCurrentDate(selenium1, "yyyy-MM-dd");
		// Get the Build ID of the application
		gstrBuild = propEnvDetails.getProperty("Build");
		// Check if result should be written to Excel or Test Management Tool
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");

		gstrReason = gstrReason.replaceAll("'", " ");
		// Write Result of the test.
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild, StrSessionId);

	}

	/***************************************************************************
	'Description	:Verify that user can deactivate a resource type displayed
	                 in the Other Region view.
	'Arguments		:None
	'Returns		:None
	'Date			:02-06-2012
	'Author			:QSG
	'--------------------------------------------------------------------------
	'Modified Date				                                   Modified By
	'Date					                                       Name
	***************************************************************************/

	@Test
	public void testBQS69778() throws Exception {

		String strFuncResult = "";
		Login objLogin = new Login();
		Regions objRegions = new Regions();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		General objGen = new General();
		Roles objRoles = new Roles();
		Views objViews = new Views();
		CreateUsers objCreateUsers = new CreateUsers();

		try {
			gstrTCID = "69778"; // Test Case Id
			gstrTO = " Verify that user can deactivate a resource type displayed in the Other Region view.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// login details
			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strOthRegion = objrdExcel.readData("Login", 7, 4);
			String strRegion1 = objrdExcel.readData("Login", 8, 4);
			// ST
			String statrNumTypeName = "NST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[1];
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			// RT
			String strResrctTypName = "AutoRT" + strTimeText;
			String strRsTypeValues[] = new String[1];
			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strStandResType = "Aeromedical";
			String strRSValue[] = new String[1];
			String strResVal = "";
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";
			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			String strByRole = objrdExcel.readInfoExcel("Precondition", 3,
					9, strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"Precondition", 3, 10, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("Precondition",
					3, 11, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("Precondition",
					4, 12, strFILE_PATH);
		/*
		 * STEP : Action:Precondition: <br>1. For Region A (i.e. Statewide
		 * Oklahoma) <br>a. Create shared status types 'ST' <br>b. Associate
		 * 'ST' to resource type 'RT' <br>c. Create a shared resource 'RS'
		 * under 'RT' <br>d. Add 'ST' and 'RS' to region default view of
		 * Statewide Oklahoma <br>2. For Region B (i.e. Arkansas) <br>a.
		 * User 'U1' is created which is associated to role 'R' <br>b. For
		 * user 'U1' 'Statewide Oklahoma' is selected for 'View Other
		 * Regions' option Expected Result:No Expected Result
		 */
			
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION STATRTS~~~~~");
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strOthRegion);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// status type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statrNumTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrNumTypeName);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
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
						seleniumPrecondition, strResrctTypName,
						str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrctTypName);
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
				strFuncResult = objResources
						.createResourceWitLookUPadresSharWitRgn(seleniumPrecondition,
								strResource, strAbbrv, strResrctTypName, true,
								"FN", "LN", strState, strCountry,
								strStandResType);
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

			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strViewName = "Region Default (default)";
				strFuncResult = objViews
						.navEditViewPage(seleniumPrecondition, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strViewName = "Region Default (default)";
				strFuncResult = objViews.selAndDeselShowAllStAndShowAllRTInViewPage(seleniumPrecondition,
						strViewName, true, true, true);
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
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegion1);
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
			// user
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
				String strRegionvalue = "2153";
				strFuncResult = objCreateUsers.slectAndDeselectOtherRegion(
						seleniumPrecondition, strRegionvalue, true, false);
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
			
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");
		/*
		 * STEP : Action:Login as user 'U1' and navigate to 'Other Region >>
		 * Statewide Oklahoma' on browser 'B1' Expected Result:Resource 'RS'
		 * and status type 'ST' is displayed.
		 */
		// 417066

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium1, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToRegionFrmOtherRegions(
						selenium1, strOthRegion);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={statrNumTypeName};
				String strResources[]={strResource};
				strFuncResult = objRegions.checkAllSTRTAndRSInRegionView(
						selenium1, strResrctTypName, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Login as RegAdmin to region 'Statewide Oklahoma' on
		 * browser 'B2' Expected Result:No Expected Result
		 */
		// 417067

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium2, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium2,
						strOthRegion);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate to Setup >> Resource Types, deselect the
		 * 'Active' checkbox for 'RT' Expected Result:RT is deactivated and
		 * not displayed in the 'Resource Type List' screen.
		 */
		// 417068

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.editResrcTypeMandatoryFlds_new(selenium2,
								strResrctTypName, strResrctTypName,
								statrNumTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						selenium2, strResrctTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:On browser 'B1' for user 'U1' refresh the 'Region
		 * Default' view of Statewide Oklahoma Expected Result:Resource 'RT'
		 * and the associated resource is not displayed.
		 */
		// 417072

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objGen.refreshPageNew(selenium1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrNumTypeName };
				String strResources[] = { strResource };

				strFuncResult = objRegions.checkAllSTRTAndRSInRegionView(
						selenium1, strResrctTypName, strResources, strStatType);

				if (strFuncResult.compareTo("Resource type '"
						+ strResrctTypName
						+ "' is NOT displayed in the User view Detail screen'") == 0) {
					strFuncResult = "";
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69778";
			gstrTO = "Verify that user can deactivate a resource type displayed in the Other Region view.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

	/***************************************************************************
	'Description	:Verify that user can reactivate a status type which was 
	                  displayed in the Other Region view.
	'Arguments		:None
	'Returns		:None
	'Date			:02-06-2012
	'Author			:QSG
	'--------------------------------------------------------------------------
	'Modified Date				                                   Modified By
	'Date					                                       Name
	***************************************************************************/


	@Ignore
	public void testBQS69811() throws Exception {

		String strFuncResult = "";
		Login objLogin = new Login();
		Regions objRegions = new Regions();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		General objGen = new General();
		Roles objRoles = new Roles();
		Views objViews = new Views();
		CreateUsers objCreateUsers = new CreateUsers();
		try {

			gstrTCID = "69811"; // Test Case Id
			gstrTO = " Verify that user can reactivate a status type which"
					+ " was displayed in the Other Region view.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// login details
			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strOthRegion = objrdExcel.readData("Login", 7, 4);
			String strRegion1 = objrdExcel.readData("Login", 8, 4);
			// ST
			String statrNumTypeName = "NST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[1];
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			// RT
			String strResrctTypName = "AutoRT" + strTimeText;
			String strRsTypeValues[] = new String[1];
			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strStandResType = "Aeromedical";
			String strRSValue[] = new String[1];
			String strResVal = "";
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";
			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;

		/*
		 * STEP : Action:Precondition:1. For Region A (i.e. Statewide
		 * Oklahoma) <br>a. Create shared status types 'ST' <br>b. Associate
		 * 'ST' to resource type 'RT' <br>c. Create a shared resource 'RS'
		 * under 'RT' <br>d. Add 'ST' and 'RS' to region default view of
		 * Statewide Oklahoma <br>2. For Region B (i.e. Arkansas) <br>a.
		 * User 'U1' is created which is associated to role 'R' <br>b. For
		 * user 'U1' 'Statewide Oklahoma' is selected for 'View Other
		 * Regions' option <br>3. After setting up all the preconditions,
		 * deactivate the resource types 'RT' Expected Result:No Expected
		 * Result
		 */
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strOthRegion);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// status type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statrNumTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrNumTypeName);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
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
						seleniumPrecondition, strResrctTypName,
						str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrctTypName);
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
				strFuncResult = objResources
						.createResourceWitLookUPadresSharWitRgn(seleniumPrecondition,
								strResource, strAbbrv, strResrctTypName, true,
								"FN", "LN", strState, strCountry,
								strStandResType);
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

			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strViewName = "Region Default (default)";
				strFuncResult = objViews
						.navEditViewPage(seleniumPrecondition, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						seleniumPrecondition, str_roleStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRSInEditView(seleniumPrecondition,
						strResVal, true, true);
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
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegion1);
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
			// user
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
				String strRegionvalue = "2153";
				strFuncResult = objCreateUsers.slectAndDeselectOtherRegion(
						seleniumPrecondition, strRegionvalue, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strByRole = objrdExcel.readInfoExcel("Precondition", 3,
						9, strFILE_PATH);
				String strByResourceType = objrdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = objrdExcel.readInfoExcel("Precondition",
						3, 11, strFILE_PATH);
				String strNameFormat = objrdExcel.readInfoExcel("Precondition",
						4, 12, strFILE_PATH);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium1, strUserName, strByRole, strByResourceType,
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strOthRegion);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.editResrcTypeMandatoryFlds_new(seleniumPrecondition,
								strResrctTypName, strResrctTypName,
								statrNumTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						seleniumPrecondition, strResrctTypName, false);
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
			 * STEP : Action:Login as user 'U1' and navigate to 'Other Region >>
			 * Statewide Oklahoma' on browser 'B1' Expected Result:Resource type
			 * 'RT' and the associated 'RS' and 'ST' are not displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium1, strUserName,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToRegionFrmOtherRegions(
						selenium1, strOthRegion);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrNumTypeName };
				String strResources[] = { strResource };
				strFuncResult = objRegions.checkAllSTRTAndRSInRegionView(
						selenium1, strResrctTypName, strResources, strStatType);
				if (strFuncResult.compareTo("Resource type '"
						+ strResrctTypName
						+ "' is NOT displayed in the User view Detail screen'") == 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Resource type '"
							+ strResrctTypName
							+ "' is still displayed in the User view Detail screen'";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as RegAdmin to region 'Statewide Oklahoma' on
			 * browser 'B2' Expected Result:No Expected Result
			 */
			// 417075
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium2, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium2,
						strOthRegion);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Resource types, reactivate the
			 * resource type 'RT'. Expected Result:'RT' is displayed in the
			 * 'Resource Type list' screen.
			 */
			// 417077
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.editResrcTypeMandatoryFlds_new(selenium2,
								strResrctTypName, strResrctTypName,
								statrNumTypeName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						selenium2, strResrctTypName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:On browser 'B1' for user 'U1' refresh the 'Region
			 * Default' view of Statewide Oklahoma Expected Result:Resource type
			 * 'RT' and the associated 'RS' and 'ST' are displayed.
			 */
			// 417079
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objGen.refreshPageNew(selenium1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrNumTypeName };
				String[] strResources = { strResource };
				strFuncResult = objRegions.checkAllSTRTAndRSInRegionView(
						selenium1, strResrctTypName, strResources, strStatType);

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
			gstrTCID = "BQS-69811";
			gstrTO = "Verify that user can reactivate a status type which was displayed in the Other Region view.";
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
