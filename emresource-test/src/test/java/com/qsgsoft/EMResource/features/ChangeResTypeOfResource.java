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

/*******************************************************************
' Description		:This class contains test cases from requirement
' Requirement		:ChangeResTypeOfResource
' Requirement Group	:Setting up Resources
' Product		    :EMResource Base
' Date			    :02-06-2012
' Author		    :QSG
' -------------------------------------------------------------------
' Modified Date				                          Modified By
' Date					                              Name
'*******************************************************************/
public class ChangeResTypeOfResource {

	// Log4j object to write log entries to the Log files
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.ChangeResTypeOfResource");
	
	static{
		BasicConfigurator.configure();
	}
	
	// Objects to access the common functions
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	String gstrTCID, gstrTO, gstrResult, gstrReason;
	
	// Selenium Object
	Selenium selenium1, selenium2,seleniumPrecondition;
	
	public Properties propElementDetails; // Property variable for ElementID
	public Properties propEnvDetails;// Property variable for Environment data
	public Properties pathProps;
	public static Properties browserProps = new Properties();
	public static String gstrBrowserName;// Variable to store browser name
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;// Result
	//private String browser;
	double gdbTimeTaken; // Variable to store the time taken
	public static Date gdtStartDate;// Date variable

	@SuppressWarnings("unused")
	private String json;
	public static long sysDateTime;
	public static long gsysDateTime = 0;
	public static String gstrTimeOut = "";
	public static String sessionId_FF36, sessionId_FF20, sessionId_FF3,
			sessionId_FF35, sessionId_IE6, sessionId_IE7, sessionId_IE8,
			session_SF3, session_SF4, session_op9, session_op10, session_GC,
			StrSessionId = "", StrSessionId1, StrSessionId2;
	
	/**************************************************************************************
		* This function is called the setup() function which is executed before every test.
		*
		* The function will take care of creating a new selenium session for every test
		*
	****************************************************************************************/
	
	@Before
	public void setUp() throws Exception {

		// Create object to read environment properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();

		// Retrieve browser information
		//browser = propEnvDetails.getProperty("Browser");
		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		// Retrieve the value of page load time limit
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		// create an object to refer to Element ID properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		// Create a new selenium session
		selenium1 = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));

		selenium2 = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));
		// Start Selenium RC
		selenium1.start();
		// Maximize the window
		selenium1.windowMaximize();
		selenium1.setTimeout("");

		// Start Selenium RC
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		seleniumPrecondition.setTimeout("");
		
		// Start Selenium RC
		selenium2.start();
		// Maximize the window
		selenium2.windowMaximize();
		selenium2.setTimeout("");
		// Define object to call support functions to read excel, date etc
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();
	}
	/***********************************************************************************************************
	 	* This function is called the teardown() function which is executed after every test.
		*
		* The function will take care of stopping the selenium session for every test and writing the execution
		* result of the test. 
		*
	************************************************************************************************************/
	@After
	public void tearDown() throws Exception {

		try{
			selenium1.close();
		}catch(Exception e){
			
		}
		selenium1.stop();

		try{
			selenium2.close();
		}catch(Exception e){
			
		}
		selenium2.stop();
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
	'Description	:Verify that the resource type of a resource can be changed.
	'Arguments		:None
	'Returns		:None
	'Date			:02-06-2012
	'Author			:QSG
	'--------------------------------------------------------------------------
	'Modified Date				                                   Modified By
	'Date					                                       Name
	***************************************************************************/

	@Test
	public void testBQS69692() throws Exception {
		String strFuncResult = "";
		Login objLogin = new Login();
		Views objViews = new Views();
		Resources objResources = new Resources();
		General objGeneral = new General();
		ResourceTypes objResourceTypes = new ResourceTypes();		
		Roles objRoles = new Roles();
		StatusTypes objStatusTypes = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		
		try {
			gstrTCID = "69692"; // Test Case Id
			gstrTO = " Verify that the resource type of a resource can be changed.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
			// login details
			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
			
			// ST
			String strStatType1 = "NST_1" + strTimeText;
			String strStatType2 = "NST_2" + strTimeText;
			String str_roleStatusTypeValues[] = new String[2];
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			// RT
			String strResType1 = "AutoRT_1" + strTimeText;
			String strResType2 = "AutoRT_2" + strTimeText;
			String strRsTypeValues[] = new String[2];
			// Resource
			String strResource1 = "AutoRS_1" + strTimeText;
			String strResource2 = "AutoRS_2" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strStandResType = "Aeromedical";
			String strRSValue[] = new String[2];
			String strResVal1 = "";
			String strResVal2 = "";
			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";
			String[][] strRoleRights={};
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
			// View
			String strViewName = "AutoV_" +strTimeText;
			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";

			String strResourceArr1[] = { strResource1 };
			String strResourceArr2[] = { strResource2 };
			String strResourceArr3[] = { strResource1, strResource2 };
			
		/*
		 *Precondition:1.Status types 'ST1' and 'ST2 are created.
                       2.Resource type 'RT1' is associated with 'ST1'and resource type 'RT2'
                         is associated with 'ST2' 
                       3.Resource'RS1' is created under 'RT1' and resource 'RS2' is created under 'RT2'
                       4.View 'V1' is created selecting 'ST1', 'ST2', 'RS1'& 'RS2' <br>5. User 'A' has 
                        update right on 'RS1' & 'RS2' 
                       6. User 'A' is assigned to a role which has update right on 'ST1' and 'RS2'
                        Expected Result:No Expected Result
		 */
		 log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
		
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
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// status type
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
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, strStatType1);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
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
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, strStatType2);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes
						.resrcTypeMandatoryFldsNew(seleniumPrecondition, strResType1,
								str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResType1);
				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.resrcTypeMandatoryFldsNew(seleniumPrecondition, strResType2,
								str_roleStatusTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[1] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResType2);
				if (strRsTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv, strResType1, "FN",
						"LN", strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
			    strResVal1 = objResources.fetchResValueInResList(
			    		seleniumPrecondition, strResource1);
				if (strResVal1.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal1;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(seleniumPrecondition,
						strResource2, strAbbrv, strResType2, "FN", "LN",
						strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
			    strResVal2 = objResources.fetchResValueInResList(
			    		seleniumPrecondition, strResource2);
				if (strResVal2.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal2;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
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
				String[] strViewRightValue = {str_roleStatusTypeValues[0],str_roleStatusTypeValues[1]};
				String[] updateRightValue = {  str_roleStatusTypeValues[1]};
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(seleniumPrecondition,
						strResource1, strRSValue[0], false,
						true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(seleniumPrecondition,
						strResource2, strRSValue[1], false,
						true, false, true);
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
			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1] };
				String[] strReSValue = { strRSValue[0], strRSValue[1] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strReSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
		
		/*
		 * STEP:Action:Login as user 'A' and navigate to 'View >> V1' on browser 'B1'
		 * Expected Result:V1 page is displayed. Resource 'RS1' is displayed under 'RT1' and
		 * is associated with 'ST1' Resource 'RS2' is displayed under 'RT2' and is associated with 'ST2'
		 */
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium1,
						strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium1, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType={strStatType1};
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium1,
						strResType1, strResourceArr1, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType={strStatType2};
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium1,
						strResType2, strResourceArr2, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * STEP : Action:Login as RegAdmin and navigate to 'Setup >>
		 * Resources' on browser 'B2' Expected Result:'Resource List' screen
		 * is displayed.
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium2, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium2, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Select to edit resource 'RS1'. Edit the resource
		 * type from 'RT1' to 'RT2' and 'Save' Expected Result:The edited
		 * data is displayed under the 'Resource Type' column header in the
		 * 'Resource List' page.
		 */
		// 416853

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium2,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResource_FillAllFields(
						selenium2, strResource1, strAbbrv, strResType2,
						strStandResType, false, false, "", "", false, "", "",
						strState, "", strCountry, "", "FN", "LN", "", "", "",
						"", "", "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium2,
						strResource1, "No", " ", strAbbrv, strResType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:On browser 'B1', refresh the 'View' screen.
		 * Expected Result:Resource 'RS1' is displayed under 'RT2' along
		 * with resource 'RS2' and is associated only to status type 'ST2'
		 */
		// 416854

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objGeneral.refreshPageNew(selenium1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType={strStatType2};
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium1,
						strResType2, strResourceArr3, strStatType);
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
			gstrTCID = "BQS-69692";
			gstrTO = "Verify that the resource type of a resource can be changed.";
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
