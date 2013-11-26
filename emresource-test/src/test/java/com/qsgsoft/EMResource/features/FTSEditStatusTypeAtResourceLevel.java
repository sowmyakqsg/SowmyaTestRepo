package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;

import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**********************************************************************
' Description		:This class contains test cases from requirement
' Requirement		:User Assignment for viewing the resource and status types
' Requirement Group	:Setting up Resources 
� Product		    :EMResource v3.19
' Date			    :4/June/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class FTSEditStatusTypeAtResourceLevel {

	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSEditStatusTypeAtResourceLevel");
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
	String gstrTimeOut;

	Selenium selenium, seleniumFirefox, seleniumPrecondition;

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
					4444, propEnvDetails.getProperty("Browser"),
					propEnvDetails.getProperty("urlEU"));
			seleniumFirefox = new DefaultSelenium(
					propEnvDetails.getProperty("Server"), 4444,
					propEnvDetails.getProperty("BrowserFirefox"),
					propEnvDetails.getProperty("urlEU"));

			seleniumPrecondition = new DefaultSelenium(
					propEnvDetails.getProperty("Server"), 4444,
					propEnvDetails.getProperty("BrowserPrecondition"),
					propEnvDetails.getProperty("urlEU"));

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


    /****************************************************************************************************************
    * This function is called the teardown() function which is executed after every test.
	* The function will take care of stopping the selenium session for every test and writing the execution
	* result of the test. 
	*
    ****************************************************************************************************************/

		@After
		public void tearDown() throws Exception {
			
			try {
				seleniumFirefox.close();
			} catch (Exception e) {

			}
			try {
				selenium.close();
			} catch (Exception e) {

			}
			try {
				seleniumPrecondition.close();
			} catch (Exception e) {

			}
			seleniumPrecondition.stop();

			seleniumFirefox.stop();

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
	'Description		:Add a status type ST to resource RS, verify that an event template CANNOT be saved by selecting status type ST and a resource type other than resource RS's resource type.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/13/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS36843() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			CreateUsers objCreateUsers = new CreateUsers();
			Roles objRoles = new Roles();
			gstrTCID = "36843"; // Test Case Id
			gstrTO = " Add a status type ST to resource RS, verify that an event template CANNOT be saved by selecting status type ST and a resource type other than resource RS's resource type.";// Test
																																																	// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[3];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String statTypeName3 = "AutoST3" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";

			// RT data
			String strResrcTypName1 = "AutoRT1" + System.currentTimeMillis();
			String strResrcTypName2 = "AutoRT2" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[2];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];

			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strTempName = "temp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";
			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1 and not with status type ST2. 2. Resources
			 * RS1 and RS2 are created under RT. 3. Status type ST2 is not
			 * associated with any other resource types 4. User U1 has following
			 * rights: Setup Resource Types Maintain Event Templates Maintain
			 * Events Expected Result:No Expected Result
			 */
			// 219735
			// ST1
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

			// ST3
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName3,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName3);

				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName1, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName1);

				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName2, strStatusTypeValues[2]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[1] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName2);

				if (strRsTypeValues[1].compareTo("") != 0) {
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName1,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveAndVerifyResource(selenium, strResource1,
								strHavBed, "", strAbbrv, strResrcTypName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource2, strAbbrv, strResrcTypName1,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveAndVerifyResource(selenium, strResource2,
								strHavBed, "", strAbbrv, strResrcTypName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
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

			// USER

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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
			 * STEP : Action:Login as user U1 and navigate to Setup>>Resource
			 * page Expected Result:'Resource List' page is displayed
			 */
			// 219736
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS1 Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 219737
			/*
			 * STEP : Action:Select status type ST2 and save Expected
			 * Result:user is returned to the 'Resource List' page
			 */
			// 219738
			try {
				assertEquals("", strFuncResult);
				String[] strSTSval = { strStatusTypeValues[1] };
				strFuncResult = objResources.navToEditStatTypeResLevelSelectST(
						selenium, strResource2, strSTSval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event>>Event Setup, select to create
			 * new event template Expected Result:'Create New Event Template '
			 * page is displayed.
			 */
			// 219739
			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide mandatory data, select resource type other
			 * than RT1, select status type ST2 and click on Save Expected
			 * Result:Error message is displayed stating that the status type
			 * ST2 is not associated with selected resource type. Event template
			 * is not created.
			 */
			// 219740
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[1] };
				String[] strStatusTypeval = { strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, false,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				// assertEquals("The template name "+strTempName+" is NOT displayed in the 'Event Template List' screen ",
				// strFuncResult);
				strFuncResult = objEventSetup.chkErrMsgInCreatNewEventTemplate(
						selenium, strResrcTypName2, statTypeName2);
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
			gstrTCID = "36843";
			gstrTO = "Add a status type ST to resource RS, verify that an "
					+ "event template CANNOT be saved by selecting status "
					+ "type ST and a resource type other than resource RS's"
					+ " resource type.";
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
	'Description		:Verify that editing status types at the resource level can be canceled.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/13/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS36937() throws Exception {
		try {

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();
			gstrTCID = "36937"; // Test Case Id
			gstrTO = " Verify that editing status types at the resource level can be canceled.";// Test
																								// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[3];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String statTypeName3 = "AutoST3" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "RT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS1" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			/*
			 * STEP : Action:Precondition: 1. Resource type RT is associated
			 * with status types ST1 and ST2 2. Resource RS is created under
			 * resource type RT Expected Result:No Expected Result
			 */
			// 219925
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
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

			// ST3
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName3,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName3);

				if (strStatusTypeValues[2].compareTo("") != 0) {
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			// USER

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
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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
			/*
			 * STEP : Action:Logout and login as user who has 'Setup Resource
			 * Types' right, and navigate to Setup>>Resources screen Expected
			 * Result:Resource list screen is displayed with the list of all
			 * resources in the region.
			 */
			// 219927
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed Status types associated with the resource
			 * type RT cannot be removed. i.e. Check boxes corresponding to
			 * status types ST1 and ST2 are selected and disabled. Other status
			 * types in the region are available to be added to the resource RS.
			 * i.e. check boxes corresponding to all other status types are
			 * unchecked and enabled.
			 */
			// 219928
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkSTInEditResLevelPage(selenium,
						strResrcTypName, strResource, strStatusTypeValues[0],
						statTypeName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkSTInEditResLevelPage(selenium,
						strResrcTypName, strResource, strStatusTypeValues[1],
						statTypeName2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkSTInEditResLevelPage(selenium,
						strResrcTypName, strResource, strStatusTypeValues[2],
						statTypeName3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select ST3 and click on 'Cancel' Expected
			 * Result:User is taken to Resource List screen.
			 */
			// 219929
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.cancelAndNavToRSListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS Expected Result:ST3 is NOT selected and is enabled in
			 * the 'Edit Resource-Level Status Types' screen
			 */
			// 219930

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkSTInEditResLevelPage(selenium,
						strResrcTypName, strResource, strStatusTypeValues[2],
						statTypeName3, true);
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
			gstrTCID = "36937";
			gstrTO = "Verify that editing status types at the resource level can be canceled.";
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
	'Description		:Verify that appropriate error is displayed when user creates a new event selecting two resources RS1 and RS2, where only RS1 is associated with ST1 at the Event template level.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/16/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS46257() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			gstrTCID = "46257"; // Test Case Id
			gstrTO = " Verify that appropriate error is displayed when user creates a new event selecting two resources RS1 and RS2, where only RS1 is associated with ST1 at the Event template level.";// Test
																																																			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];

			String strTempName = "temp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + System.currentTimeMillis();
			String strInfo = "Automation";

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1 and not with status type ST2. 2. Resources
			 * RS1 and RS2 are created under RT. 3. ST2 is associated to
			 * resource RS2 at the resource level. Expected Result:No Expected
			 * Result
			 */
			// 267988
			// ST1
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource1, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource2, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource2, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as RegAdmin, navigate to Event >> Event
			 * Setup, select to create new event template Expected
			 * Result:'Create New Event Template ' page is displayed.
			 */
			// 267989
			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide mandatory data, select resource type RT,
			 * select status type ST2 and click on save Expected Result:Event
			 * template ET1 is created.
			 */
			// 267990
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event Management, click on
			 * button 'Create New Event', create new event selecting event
			 * template ET Expected Result:'Create New Event' screen is
			 * displayed. Resources RS1, RS2 are listed under the 'Resources to
			 * Participate in This Event:' section.
			 */
			// 267991
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[0], true, strResource1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[1], true, strResource2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide mandatory data, create event EVE1 selecting
			 * resources RS1, RS2 and save Expected Result:"The following error
			 * occurred on this page: � The following resources are not
			 * associated with any status types: Resource1" Error message is
			 * displayed.
			 */
			// 267992
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource2, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("'Event Management' screen is NOT displayed.",
						strFuncResult);
				strFuncResult = objEventSetup.chkErrMsgCreateEventPage(
						selenium, strResource1);
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
			gstrTCID = "46257";
			gstrTO = "Verify that appropriate error is displayed when user creates a new event selecting two resources RS1 and RS2, where only RS1 is associated with ST1 at the Event template level.";
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
	'Description		:Add status type ST2 to resource RS1 at resource level under resource type RT1, where: 
	                    1. ST1 is associated with resource types RT1 and RT2
	                    2. No resource is created under resource type RT2
	                    3. View V1 is created selecting resource types RT1, RT2 and status types ST1 and ST2. 
	                	Change the resource RS1's resource type and verify that the changes are reflected on 
	                	the view screen of V1.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/16/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS42002() throws Exception {
		try {

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			Views objViews = new Views();
			gstrTCID = "42002"; // Test Case Id
			gstrTO = " Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. No resource is created under resource type RT2"
					+ "3. View V1 is created selecting resource types RT1, RT2 and status types ST1 and ST2."
					+ "Change the resource RS1's resource type and verify that the changes are reflected on the view screen of V1.";// Test
																																	// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[3];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName1 = "AutoRT1" + System.currentTimeMillis();
			String strResrcTypName2 = "AutoRT2" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[2];
			// Resource
			String strResource = "AutoRS1" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";
			/*
			 * STEP : Action:Preconditions: 1. Resource type RT1 is associated
			 * with status type ST1. 2. Resource type RT2 is associated with
			 * status type ST1. 3. Resource RS1 is created under resource type
			 * RT1. Expected Result:No Expected Result
			 */
			// 252752
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
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
						selenium, strResrcTypName1, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName1);

				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName2, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[1] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName2);

				if (strRsTypeValues[1].compareTo("") != 0) {
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName1,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			/*
			 * STEP : Action:Login as RegAdmin, Navigate to Setup >> Resources
			 * screen, click on 'Edit Status Types' link corresponding to
			 * resource RS1. Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 252755
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select check box corresponding to ST2 and save the
			 * page Expected Result:User is returned to the 'Resource List'
			 * screen
			 */
			// 252756
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Views, Create a view V1
			 * selecting resource type RT1, status types ST1 and ST2 Expected
			 * Result:No Expected Result
			 */
			// 252758
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1 and ST2
			 */
			// 252760
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName1, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTInUserViewScreen(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource, click on the edit
			 * link associated with resource RS1, change the resource type from
			 * RT1 to RT2 and save Expected Result:'Resources List' page is
			 * displayed.
			 */
			// 252763
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName2,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT2 along with status types
			 * ST1 and ST2.
			 */
			// 252764
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName2, strRS,
						strRsTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTInUserViewScreen(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource, click on the edit
			 * link associated with resource RS1, change the resource type RT2
			 * to RT1 and save Expected Result:'Resources List' page is
			 * displayed.
			 */
			// 252766
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName1,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1 and ST2
			 */
			// 252768

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName1, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTInUserViewScreen(selenium,
						strStatType);
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
			gstrTCID = "42002";
			gstrTO = " Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. No resource is created under resource type RT2"
					+ "3. View V1 is created selecting resource types RT1, RT2 and status types ST1 and ST2."
					+ "Change the resource RS1's resource type and verify that the changes are reflected on the view screen of V1.";// Test
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
	}	

	/***************************************************************
	'Description		:Verify that appropriate error is displayed, when a user edits an event with a resource and adds another resource that does not have a corresponding status type at the event template level.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/16/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS46262() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			gstrTCID = "46262"; // Test Case Id
			gstrTO = " Verify that appropriate error is displayed, when a user edits an event with a resource and adds another resource that does not have a corresponding status type at the event template level.";// Test
																																																						// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "RT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];

			String strTempName = "temp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "Eve_" + System.currentTimeMillis();
			String strInfo = "Automation";

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1 and not with status type ST2. 2. Resources
			 * RS1 and RS2 are created under RT. 3. ST2 is associate dto
			 * resource RS2 at the resource level. Expected Result:No Expected
			 * Result
			 */
			// 268009
			// ST1
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource1, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource2, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource2, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as RegAdmin, navigate to Event >> Event
			 * Setup, select to create new event template Expected
			 * Result:'Create New Event Template ' page is displayed.
			 */
			// 268010
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide mandatory data, select resource type RT,
			 * select status type ST2 and click on save Expected Result:Event
			 * template ET1 is created.
			 */
			// 268011
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Management, click on
			 * button 'Create New Event', create new event selecting event
			 * template ET Expected Result:'Create New Event' screen is
			 * displayed. Resources RS1, RS2 are listed under the 'Resources to
			 * Participate in This Event:' section.
			 */
			// 268012
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[0], true, strResource1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[1], true, strResource2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide mandatory data, create event EVE1 selecting
			 * resource RS2 and save Expected Result:Event created EVE1 is
			 * listed in the 'Event Management' page.
			 */
			// 268013
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource2, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEveName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit' link associated with EVE1 Expected
			 * Result:'Edit Event' page is displayed. Check box associated with
			 * ST2 is selected and disabled. Checkbox associated with ST1 is
			 * deselected but enabled.
			 */
			// 268029
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTSelectedOrNotEditEventPage(
						selenium, strStatusTypeValues[1], true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTSelectedOrNotEditEventPage(
						selenium, strStatusTypeValues[0], false, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select resource RS1 and click on Save Expected
			 * Result:"The following error occurred on this page: �The following
			 * resources are not associated with any status types: Resource1"
			 * Error message is displayed.
			 */
			// 268055
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource1, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("'Event Management' screen is NOT displayed.",
						strFuncResult);
				strFuncResult = objEventSetup.chkErrMsgInEditEventPage(
						selenium, strResource1);
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
			gstrTCID = "46262";
			gstrTO = "Verify that appropriate error is displayed, when a user edits an event with a resource and adds another resource that does not have a corresponding status type at the event template level.";
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
	'Description		:Verify that appropriate error is displayed, when a user creates a new event with a resource that does not have a corresponding status type at the event template level.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/16/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS46249() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			gstrTCID = "46249"; // Test Case Id
			gstrTO = " Verify that appropriate error is displayed, when a user "
					+ "creates a new event with a resource that does not have a "
					+ "corresponding status type at the event template level.";// Test
																				// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];

			String strTempName = "Autotemp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + System.currentTimeMillis();
			String strInfo = "Automation";
			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1 and not with status type ST2. 2. Resources
			 * RS1 and RS2 are created under RT. 3. ST2 is associate dto
			 * resource RS2 at the resource level. Expected Result:No Expected
			 * Result
			 */
			// 267887
			// ST1
			
			
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource1, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource2, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource2, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as RegAdmin, navigate to Event >> Event
			 * Setup, select to create new event template Expected
			 * Result:'Create New Event Template ' page is displayed.
			 */
			// 267898
			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide mandatory data, select resource type RT,
			 * select status type ST2 and click on save Expected Result:Event
			 * template ET1 is created.
			 */
			// 267899
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Management, click on
			 * button 'Create New Event', create new event selecting event
			 * template ET Expected Result:'Create New Event' screen is
			 * displayed. Resources RS1, RS2 are listed under the 'Resources to
			 * Participate in This Event:' section.
			 */
			// 267936

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[0], true, strResource1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[1], true, strResource2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide mandatory data, create event EVE1 selecting
			 * resource RS1 and save Expected Result:"The following error
			 * occurred on this page: � The following resources are not
			 * associated with any status types: Resource1" Error message is
			 * displayed.
			 */
			// 267952

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource1, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("'Event Management' screen is NOT displayed.",
						strFuncResult);
				strFuncResult = objEventSetup.chkErrMsgCreateEventPage(
						selenium, strResource1);
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
			gstrTCID = "46249";
			gstrTO = "Verify that appropriate error is displayed, "
					+ "when a user creates a new event with a resource"
					+ " that does not have a corresponding status type "
					+ "at the event template level.";
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
	'Description		:Verify that a status type added at the resource 
	'					 level can be removed at the resource level.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/17/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS36713() throws Exception {
		try {

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			gstrTCID = "36713"; // Test Case Id
			gstrTO = " Verify that a status type added at the resource level can be removed at the resource level.";// Test
																													// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[3];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String statTypeName3 = "AutoST3" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS1" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			/*
			 * STEP : Action:Precondition: 1. Resource type RT is associated
			 * with status types ST1 and ST2 2. Resource RS is created under
			 * resource type RT Expected Result:No Expected Result
			 */
			// 218426
			// ST1
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
			// ST3
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName3,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName3);

				if (strStatusTypeValues[2].compareTo("") != 0) {
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
			/*
			 * STEP : Action:Login as RegAdmin and navigate to Setup>>Resources
			 * screen Expected Result:Resource list screen is displayed with the
			 * list of all resources in the region. 'Edit', 'Users' and 'Edit
			 * Status Types' link is available for all the resources listed.
			 */
			// 218428
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.varFieldsInResListPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed
			 */
			// 218429
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select ST3 and click on 'Save' Expected Result:User
			 * is taken to Resource List screen.
			 */
			// 218430
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS Expected Result:Check box corresponding to status
			 * type ST3 is selected and enabled in the 'Edit Resource-Level
			 * Status Types' screen.
			 */
			// 218431
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkSTSelectedOrNotEditRsLevelPage(
						selenium, strStatusTypeValues[2], statTypeName3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Deselect the check box corresponding to status type
			 * ST3 and Save Expected Result:User is taken to Resource List
			 * screen.
			 */
			// 219423
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS Expected Result:Check box corresponding to status
			 * type ST3 is deselected and enabled in the 'Edit Resource-Level
			 * Status Types' screen.
			 */
			// 219424
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkSTSelectedOrNotEditRsLevelPage(
						selenium, strStatusTypeValues[2], statTypeName3, false);
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
			gstrTCID = "36713";
			gstrTO = "Verify that a status type added at the resource "
					+ "level can be removed at the resource level.";
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
	'Description		:Add status type ST2 to resource RS1 at resource level under resource type RT1, where:
	                    1.ST1 is associated with resource types RT1 and RT2
						2.No resource is created under resource type RT2
						3.View V1 is created selecting RT1, RT2 and ST1, ST2.
						Deactivate and reactivate the status type ST2 at the resource level and verify that the 
						changes are reflected on the view screen of V1.
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :7/17/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS88861() throws Exception {
		try {

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			Views objViews = new Views();
			gstrTCID = "88861"; // Test Case Id
			gstrTO = "Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. No resource is created under resource type RT2"
					+ "3. View V1 is created selecting RT1, RT2 and ST1, ST2."
					+ "Deactivate and reactivate the status type ST2 at the resource level and verify that the"
					+ "changes are reflected on the view screen of V1.";// Test
																		// objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "RT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS1" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";
			/*
			 * STEP : Action:Preconditions: 1. Resource type RT1 is associated
			 * with status type ST1. 2. Resource RS1 is created under resource
			 * type RT1. 3. Status type ST2 is created and is not associated
			 * with any Resource Type or Resource. Expected Result:No Expected
			 * Result
			 */
			// 540742
			// ST1
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
			/*
			 * STEP : Action:Login as RegAdmin, Navigate to Setup >> Resources
			 * screen, click on 'Edit Status Types' link corresponding to
			 * resource RS1. Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 540743
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select check box corresponding to ST2 and save the
			 * page Expected Result:User is returned to the 'Resource List'
			 * screen
			 */
			// 540744
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Views, Create a view V1
			 * selecting resource type RT1, status types ST1 and ST2 Expected
			 * Result:No Expected Result
			 */
			// 540745
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1 and ST2
			 */
			// 540746
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTInUserViewScreen(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Status type, deactivate status
			 * type ST2 and click on save Expected Result:'Status type List'
			 * page is displayed.
			 */
			// 540747
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSTtype = "Number";
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName2, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.activateAndDeactivateST(
						selenium, statTypeName2, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1.
			 */
			// 540748
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statTypeName1 };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Status type, activate status
			 * type ST2 and click on save Expected Result:No Expected Result
			 */
			// 540749
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDesectIncludeInactiveST(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSTtype = "Number";
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName2, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.activateAndDeactivateST(
						selenium, statTypeName2, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDesectIncludeInactiveST(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1 and ST2
			 */
			// 540750
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTInUserViewScreen(selenium,
						strStatType);
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
			gstrTCID = "88861";
			gstrTO = "Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. No resource is created under resource type RT2"
					+ "3. View V1 is created selecting RT1, RT2 and ST1, ST2."
					+ "Deactivate and reactivate the status type ST2 at the resource level and verify that the"
					+ "changes are reflected on the view screen of V1.";
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

	/***********************************************************************************************
	'Description	:Verify that an event can be edited multiple times to create a valid combination 
	                of resource RS and status type ST where ST is not selected at the template level
	'Arguments		:None
	'Returns		:None
	'Date			:7/17/2012
	'Author			:QSG 
	'-----------------------------------------------------------------------------------------------
	'Modified Date				                                                        Modified By
	'Date					                                                            Name
	************************************************************************************************/

	@Test
	public void testFTS46285() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			
			gstrTCID = "46285"; // Test Case Id
			gstrTO = " Verify that an event can be edited multiple times to create a valid combination"
					+ " of resource RS and status type ST where ST is not selected at the template level";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];
			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];

			String strTempName = "temp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + System.currentTimeMillis();
			String strInfo = "Automation";

		/*
		 * STEP : Action:Preconditions: 1. Status types ST1 and ST2 are
		 * created. 2. Resource type RT is created selecting ST1. 3.
		 * Resources RS1, RS2 are created under RT 4. ST2 is associated with
		 * resource RS1 at the resource level. Expected Result:No Expected
		 * Result
		 */
		// 268150

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION STATRTS~~~~~");
			
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
						strResrcTypName, "Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(seleniumPrecondition,
						strResource1, strHavBed, "", strAbbrv, strResrcTypName);
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
				strFuncResult = objResources.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource2, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(seleniumPrecondition,
						strResource2, strHavBed, "", strAbbrv, strResrcTypName);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE - CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TESTCASE-" + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * STEP : Action:Login as RegAdmin, navigate to Event >> Event Setup
			 * provide mandatory data, select resource type RT, select status
			 * type ST2 and click on save Expected Result:Event template ET1 is
			 * created.
			 */
			// 268153
		
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Management, click on
			 * button 'Create New Event', create new event selecting event
			 * template ET. Expected Result:'Create New Event' screen is
			 * displayed. Resources RS1, RS2 are listed under the 'Resources to
			 * Participate in This Event:' section.
			 */
			// 268159

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[0], true, strResource1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[1], true, strResource2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide mandatory data, create event EVE1 selecting
			 * event template ET, resource RS1 and save Expected Result:Event is
			 * listed in the 'Event Management' screen
			 */
			// 268177
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEveName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on edit link associated with the EVE1
			 * Expected Result:'Edit Event' page is displayed. ST2 is selected
			 * and disabled.
			 */
			// 268181
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTSelectedOrNotEditEventPage(
						selenium, strStatusTypeValues[1], true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Deselect RS1, select RS2 and status type ST1 on the
			 * 'Edit Event' page and save Expected Result:No Expected Result
			 */
			// 268187
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource1, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource2, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								strStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on edit link associated with event EVE1
			 * Expected Result:RS2 is selected and enabled. ST1 is selected and
			 * disabled on 'Edit Event' page.
			 */
			// 268194
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkRSselOrNotInEditEvntPage(
						selenium, strRSValue[1], true, strResource2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTSelectedOrNotEditEventPage(
						selenium, strStatusTypeValues[0], true, statTypeName2);
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
			gstrTCID = "46285";
			gstrTO = "Verify that an event can be edited multiple times to create a valid combination of resource RS and status type ST where ST is not selected at the template level";
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
	'Description		:Verify that a user with only 'Edit Resources'
	'					 right cannot edit the status types at the resource level.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/18/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS36711() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();
			gstrTCID = "36711"; // Test Case Id
			gstrTO = " Verify that a user with only 'Edit Resources' right"
					+ " cannot edit the status types at the resource level.";// Test
																				// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[3];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "RT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS1" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			// USER
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strUserName2 = "AutoUsr2" + System.currentTimeMillis();
			String strUserName3 = "AutoUsr3" + System.currentTimeMillis();
			String strUserName4 = "AutoUsr4" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			/*
			 * STEP : Action:Precondition: 1. Resource type RT is associated
			 * with status types ST1 and ST2 2. Resource RS is created under
			 * resource type RT Expected Result:No Expected Result
			 */
			// 218154

			// ST1
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
			/*
			 * STEP : Action:Login as RegAdmin to EMResource and create
			 * following users with the rights mentioned below: 1. User U1
			 * selecting only the right 'Edit Resources Only' and without any
			 * resource rights. 2. User U2 selecting only the right 'Edit
			 * Resources Only' and only with Update Status right on resource RS.
			 * 3. User U3 selecting only the right 'Edit Resources Only' and
			 * only with Run Report right on resource RS. 4. User U4 selecting
			 * only the right 'Edit Resources Only' and with only Associated
			 * With right on resource RS. Expected Result:No Expected Result
			 */
			// 218155

			// USER1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly"),
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
			// USER2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName2, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly"),
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

			// USER3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName3, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly"),
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
			// USER4
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName4, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], true, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly"),
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
			/*
			 * STEP : Action:Logout and login as user U1, and navigate to
			 * Setup>>Resources screen Expected Result:No resources are listed
			 * under Resource List screen.
			 */
			// 218156
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
				gstrReason = strFuncResult;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Logout and login as user U2, and navigate to
			 * Setup>>Resources screen Expected Result:Only resource RS is
			 * displayed in the list. 'Edit Status Types' and 'Users' links are
			 * NOT available corresponding to resource RS. Only 'Edit' link is
			 * available corresponding to resource RS. 'Create New Resource'
			 * button is NOT available.
			 */
			// 218357
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName2,
						strInitPwd);
				gstrReason = strFuncResult;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.varFieldsInResListPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Logout and login as user U3, and navigate to
			 * Setup>>Resources screen Expected Result:No resources are listed
			 * under Resource List screen.
			 */
			// 218416
			try {
				assertEquals(
						"'Users'link is NOT available corresponding to resource"
								+ strResource + "", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName3,
						strInitPwd);
				gstrReason = strFuncResult;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Logout and login as user U4, and navigate to
			 * Setup>>Resources screen Expected Result:No resources are listed
			 * under Resource List screen.
			 */
			// 218417
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName4,
						strInitPwd);
				gstrReason = strFuncResult;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource, false);
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
			gstrTCID = "36711";
			gstrTO = "Verify that a user with only 'Edit Resources' right cannot edit the status types at the resource level.";
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
	'Description		:Add a status type to resource RS, create an event template ET selecting newly added status type & the resource's resource type and verify that event can be created under ET selecting resource RS.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/18/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS36757() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			CreateUsers objCreateUsers = new CreateUsers();
			Roles objRoles = new Roles();
			EventList objEventList = new EventList();
			gstrTCID = "36757"; // Test Case Id
			gstrTO = " Add a status type to resource RS, create an event "
					+ "template ET selecting newly added status type & the"
					+ " resource's resource type and verify that event can"
					+ " be created under ET selecting resource RS.";// Test
																	// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strTempName = "Autotmp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + System.currentTimeMillis();
			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1 and not with status type ST2. 2. Resources
			 * RS1 and RS2 are created under RT. 3. User U1 has following
			 * rights: Setup Resource Types Setup Resources Maintain Event
			 * Templates Maintain Events Expected Result:No Expected Result
			 */
			// 218949
			// ST1
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource1, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource2, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource2, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
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
			// USER
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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
			 * STEP : Action:Login as user U1 and navigate to Setup>>Resource
			 * page Expected Result:'Resource List' page is displayed
			 */
			// 219101

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS1 Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 219102
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select status type ST2 and save Expected
			 * Result:user is returned to the 'Resource List' page
			 */
			// 219104
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event>>Event Setup, select to create
			 * new event template Expected Result:'Create New Event Template '
			 * page is displayed.
			 */
			// 219123
			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide mandatory data, select resource type RT,
			 * select status type ST2 and click on Save Expected Result:Event
			 * template ET1 is created.
			 */
			// 219131
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, false,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event>>Event Management, and select to
			 * create new event, select to create event under event template ET1
			 * Expected Result:'Create New Event' screen is displayed and only
			 * resource RS1 is listed under the 'Resources to Participate in
			 * This Event:' section.
			 */
			// 219135
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide mandatory data, create event EVE1 selecting
			 * resource RS1 Expected Result:Event EVE1 is created and is listed
			 * on the 'Event Management' screen Status type ST2 is displayed for
			 * resource RS1 on the 'Event Status' screen of EVE1.
			 */
			// 219136

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.createEvent(selenium, strTempName, strResource1,
								strEveName, strTempDef, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName, strResource1,
						strStatTypeArr);
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
			gstrTCID = "36757";
			gstrTO = "Add a status type to resource RS, create an event template "
					+ "ET selecting newly added status type & the resource's resource"
					+ " type and verify that event can be created under ET selecting"
					+ " resource RS.";
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
	'Description		:Add a status type to resource RS and verify 
	'					that an already existing event template can be
	'					 saved by selecting newly added status type.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/18/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS36778() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			CreateUsers objCreateUsers = new CreateUsers();
			Roles objRoles = new Roles();
			EventList objEventList = new EventList();
			gstrTCID = "36778"; // Test Case Id
			gstrTO = " Add a status type to resource RS and verify that an already existing event "
					+ "template can be saved by selecting newly added status type.";// Test
																					// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strTempName = "Autotmp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + System.currentTimeMillis();
			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1 and not with status type ST2. 2. Resources
			 * RS1 and RS2 are created under RT. 3. User U1 has following
			 * rights: Setup Resource Types Setup Resources Maintain Event
			 * Templates Maintain Events Expected Result:No Expected Result
			 */
			// 219190
			// ST1
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource1, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource2, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource2, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
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
			// USER
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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

			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
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
			 * STEP : Action:Login as user U1 and navigate to Setup>>Resource
			 * page Expected Result:'Resource List' page is displayed
			 */
			// 219191

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS1 Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 219192

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select status type ST2 and save Expected
			 * Result:user is returned to the 'Resource List' page
			 */
			// 219193
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event>>Event Setup, select to edit
			 * event template ET Expected Result:'Edit Event Template ' page is
			 * displayed.
			 */
			// 219194
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select status type ST2 and click on Save Expected
			 * Result:Event template ET is saved and listed under 'Event
			 * Template List'
			 */
			// 219195
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								strStatusTypeValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(
						selenium, strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event>>Event Management, and select to
			 * create new event, select to create event under event template ET
			 * Expected Result:'Create New Event' screen is displayed and both
			 * resources RS1 and RS2 are listed under the 'Resources to
			 * Participate in This Event:' section.
			 */
			// 219196
			// event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[0], true, strResource1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[1], true, strResource2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide mandatory data, create event EVE1 selecting
			 * resource RS1 and RS2 Expected Result:Event EVE1 is created and is
			 * listed on the 'Event Management' screen
			 */
			// 219197
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource2, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEveName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the event banner of EVE1 Expected
			 * Result:Status types ST1 and ST2 are displayed for Resource RS1
			 * and only status type ST1 is displayed corresponding to resource
			 * RS2.
			 */
			// 219219
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName, strResource1,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.chkResourceInEventBanner(selenium,
						strResource2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objEventList.chkSTInEventBanners(selenium,
						strStatTypeArr, true, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objEventList.chkSTInEventBanners(selenium,
						strStatTypeArr, false, strResource2);
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
			gstrTCID = "36778";
			gstrTO = "Add a status type to resource RS and verify that an already existing event template can be saved by selecting newly added status type.";
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
	'Description		:Add a status type to resource RS and verify that when an ongoing event saved by selecting newly added status type, the status type is associated with only resource RS.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/18/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS36779() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			CreateUsers objCreateUsers = new CreateUsers();
			Roles objRoles = new Roles();
			EventList objEventList = new EventList();
			gstrTCID = "36779"; // Test Case Id
			gstrTO = " Add a status type to resource RS and verify that"
					+ " when an ongoing event saved by selecting newly added status type,"
					+ " the status type is associated with only resource RS.";// Test
																				// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strTempName = "Autotmp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + System.currentTimeMillis();

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1 and not with status type ST2. 2. Resources
			 * RS1 and RS2 are created under RT. 3. Event template ET is created
			 * by selecting RT and ST1 4. Event EVE1 is created under event
			 * template ET selecting resources RS1 and RS2 3. User U1 has
			 * following rights: Setup Resource Types Setup Resources Maintain
			 * Event Templates Maintain Event Expected Result:No Expected Result
			 */
			// 219220
			// ST1
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource1, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource2, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource2, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
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
			// USER
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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

			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource2, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEveName, true);
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
			 * STEP : Action:Login as user U1 and navigate to Setup>>Resource
			 * page Expected Result:'Resource List' page is displayed
			 */
			// 219221
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS1 Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 219222

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select status type ST2 and save Expected
			 * Result:user is returned to the 'Resource List' page
			 */
			// 219223
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event>>Event Setup, select to edit
			 * event template ET Expected Result:'Edit Event Template ' page is
			 * displayed. ST2 is listed under the status type section along with
			 * ST1 selected.
			 */
			// 219224
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.chkSTSelOrNotEditEvntTemplatePage(selenium,
								strStatusTypeValues[0], true, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventPage(selenium,
						strStatusTypeValues[1], true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select status type ST2 and click on Save Expected
			 * Result:Event template ET is saved and listed under 'Event
			 * Template List'
			 */
			// 219225

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								strStatusTypeValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(
						selenium, strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event>>Event Management, and select to
			 * edit event EVE1 Expected Result:'Edit event' screen is displayed.
			 * Status type ST2 is displayed for 'Status Types' field.
			 */
			// 219229
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventPage(selenium,
						strStatusTypeValues[1], true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select ST2 and click on Save Expected Result:User
			 * is returned to 'Event Management' screen
			 */
			// 219230
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								strStatusTypeValues[1], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the event banner of EVE1 Expected
			 * Result:Status types ST1 and ST2 are displayed for Resource RS1.
			 * Only status type ST1 is displayed for resource RS2.
			 */
			// 219232
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName, strResource1,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.chkResourceInEventBanner(selenium,
						strResource2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objEventList.chkSTInEventBanners(selenium,
						strStatTypeArr, true, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objEventList.chkSTInEventBanners(selenium,
						strStatTypeArr, false, strResource2);
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
			gstrTCID = "36779";
			gstrTO = "Add a status type to resource RS and verify that when an ongoing event saved by selecting newly added status type, the status type is associated with only resource RS.";
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
	'Description		:Verify that when a status type at the resource level is added to the resource type, the status type is associated with all other resources that are created under that resource type.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/19/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS43760() throws Exception {
		try {

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			Views objViews = new Views();
			gstrTCID = "43760"; // Test Case Id
			gstrTO = " Verify that when a status type at the resource level is added to the resource type, "
					+ "the status type is associated with all other resources that are created under that resource type.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();

			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT1 is associated
			 * with status type ST1. 2. Resources RS1 and RS2 are created under
			 * resource type RT1. Expected Result:No Expected Result
			 */
			// 257250
			// ST1
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource1, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource2, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource2, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
			/*
			 * STEP : Action:Login as RegAdmin, Navigate to Setup >> Resources
			 * screen, click on 'Edit Status Types' link corresponding to
			 * resource RS1. Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 257251

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select check box corresponding to ST2 and save the
			 * page Expected Result:User is returned to the 'Resource List'
			 * screen
			 */
			// 257252
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views, Create a view V1
			 * selecting resource type RT1, status types ST1 and ST2 Expected
			 * Result:No Expected Result
			 */
			// 257253
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(selenium,
						strRsTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1 and ST2.(-- is displayed under ST1 and ST2) Resource RS2 is
			 * displayed under resource type RT1 along with status type ST1. (--
			 * is displayed for ST1 and N/A is displayed for status type ST2)
			 */
			// 257254
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource1 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, true, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource2 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, true, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, false, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Resource type, select edit
			 * associated with resource type RT2, select check box for ST2 and
			 * click on save Expected Result:'Resource type List' page is
			 * displayed.
			 */
			// 257255
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrcTypName);
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

			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1 and ST2.(-- is displayed under ST1 and ST2) Resource RS2 is
			 * displayed under resource type RT1 along with status types ST1 and
			 * ST2. (-- is displayed for ST1 and ST2)
			 */
			// 257256
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource1 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, true, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource2 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, true, strResource2);
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
			gstrTCID = "43760";
			gstrTO = "Verify that when a status type at the resource level is added to the resource type, the status type is associated with all other resources that are created under that resource type.";
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

	/*************************************************************************************
	'Description	:Verify that a resource cannot be selected back to the event, when the  
	                 resource does not have a valid status type associated with the event.
	'Arguments		:None
	'Returns		:None
	'Date			:7/20/2012
	'Author			:QSG
	'-------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	**************************************************************************************/

	@Test
	public void testFTS46286() throws Exception {
		try {

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			gstrTCID = "46286"; // Test Case Id
			gstrTO = " Verify that a resource cannot be selected back to the event, when the "
					+ "resource does not have a valid status type associated with the event.";
			gstrReason = "";
			gstrResult = "FAIL";

		
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName1 = "AutoRT1" + System.currentTimeMillis();
			String strResrcTypName2 = "AutoRT2" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[2];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strResource3 = "AutoRS3" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[3];

			String strTempName = "Autotmp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + System.currentTimeMillis();
			
			/*
			 * STEP : Action:Preconditions: 1. Status types ST1 and ST2 are
			 * created. 2. Resource type RT1 is created selecting ST1. 3.
			 * Resource type RT2 is created selecting ST2. 4. Resources RS1, RS2
			 * are created under RT1 5. ST2 is associated with resource RS2 at
			 * the resource level. 6. Resource RS3 is created under RT2 Expected
			 * Result:No Expected Result
			 */
			// 269009
			// ST1

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID+ " EXECUTION ENDS~~~~~");
			
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

			//ST1
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

			// RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName1, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName1);

				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName2, strStatusTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[1] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName2);

				if (strRsTypeValues[1].compareTo("") != 0) {
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
				strFuncResult = objResources.navToCreateResourcePage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource1, strAbbrv, strResrcTypName1,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				 assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveAndVerifyResource(seleniumPrecondition, strResource1,
								strHavBed, "", strAbbrv, strResrcTypName1);

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
				strFuncResult = objResources.navToCreateResourcePage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource2, strAbbrv, strResrcTypName1,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveAndVerifyResource(seleniumPrecondition, strResource2,
								strHavBed, "", strAbbrv, strResrcTypName1);

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

			// RS3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource3, strAbbrv, strResrcTypName2,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveAndVerifyResource(seleniumPrecondition, strResource3,
								strHavBed, "", strAbbrv, strResrcTypName2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
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
				strFuncResult = objResources.navToEditResLevelSTPage(seleniumPrecondition,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveAndVerifyResource(seleniumPrecondition, strResource2,
								strHavBed, "", strAbbrv, strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as RegAdmin, navigate to Event >> Event Setup
			 * provide mandatory data, select resource types RT1 and RT2, select
			 * status type ST2 and click on save Expected Result:Event template
			 * ET1 is created.
			 */
			// 269010
			// event template

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
				String[] strResTypeVal = { strRsTypeValues[0],
						strRsTypeValues[1] };
				String[] strStatusTypeval = { strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
			
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
			 * STEP : Action:Navigate to Event >> Event Management, click on
			 * button 'Create New Event', create new event selecting event
			 * template ET. Expected Result:'Create New Event' screen is
			 * displayed. Resources RS1, RS2 and RS3 are listed under the
			 * 'Resources to Participate in This Event:' section.
			 */
			// 269014
			
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[0], true, strResource1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[1], true, strResource2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[2], true, strResource3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource2, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide mandatory data, create event EVE1 selecting
			 * event template ET1, resource RS2, ST2 and save Expected
			 * Result:Event is listed in the 'Event Management' screen
			 */
			// 269016

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								strStatusTypeValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEveName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on edit link associated with the EVE1
			 * Expected Result:'Edit Event' page is displayed. ST2 is selected
			 * and disabled. ST1 is enabled and not selected.
			 */
			// 269017
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTSelectedOrNotEditEventPage(
						selenium, strStatusTypeValues[1], true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTSelectedOrNotEditEventPage(
						selenium, strStatusTypeValues[0], false, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Add ST1 to the event and save Expected
			 * Result:'Event Management' screen is displayed.
			 */
			// 269038
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								strStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on edit link associated with the EVE1
			 * Expected Result:'Edit Event' page is displayed.
			 */
			// 269039
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Add resource RS3 to the event EVE1 and save
			 * Expected Result:'Event Management' screen is displayed.
			 */
			// 269040
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource3, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on edit link associated with event EVE1,
			 * remove RS2 from the event and save. Expected Result:'Event
			 * Management' screen is displayed.
			 */
			// 269041
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource2, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on edit link associated with event EVE1, add
			 * RS1 to the event and save. Expected Result:"The following error
			 * occurred on this page: � The following resources are not
			 * associated with any status types: Resource1" Error message is
			 * displayed.
			 */
			// 269042
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource1, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkErrMsgInEditEventPage(
						selenium, strResource1);
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
			gstrTCID = "46286";
			gstrTO = "Verify that a resource cannot be selected back to the event, when the resource does not have a valid status type associated with the event.";
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
	'Description		:Add a status type to resource RS, create an event template ET selecting newly added status type & the resource's resource type and verify that event can be created under ET selecting resource RS.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/18/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS36747() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();
			gstrTCID = "36747"; // Test Case Id
			gstrTO = " Add a status type to a resource and verify that changing the resource type of the resource changes the associated status types when the 'Edit Resource-Level Status Types' screen is saved.";// Test
																																																					// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[3];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String statTypeName3 = "AutoST3" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName1 = "AutoRT1" + System.currentTimeMillis();
			String strResrcTypName2 = "AutoRT2" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[2];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];

			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			/*
			 * STEP : Action:Preconditions: 1. Resources RS1 is created under
			 * RT1 associated with ST1 and ST3. 2. Resource RS2 is created under
			 * RT2 associated with ST2 and ST3. 3. User U1 has 'Setup Resource
			 * Type' and 'Setup Resources' rights Expected Result:No Expected
			 * Result
			 */

			// ST1
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

			// ST3
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName3,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName3);

				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName1, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName1);

				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName2, strStatusTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[1] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName2);

				if (strRsTypeValues[1].compareTo("") != 0) {
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName1,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveAndVerifyResource(selenium, strResource1,
								strHavBed, "", strAbbrv, strResrcTypName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource2, strAbbrv, strResrcTypName2,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveAndVerifyResource(selenium, strResource2,
								strHavBed, "", strAbbrv, strResrcTypName2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			// USER
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
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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
			 * STEP : Action:Login as user U1 and navigate to Setup>>Resources
			 * screen, click on 'Edit Status Types' link corresponding to
			 * resource RS1 Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
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

			/*
			 * STEP : Action:Select check box corresponding to ST2 and save the
			 * page Expected Result: User is returned to the 'Resource List'
			 * screen
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup>>Resource, edit resource RS1 and
			 * change the resource type form RT1 to RT2 and Save. No Expected
			 * Result
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName2,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on the 'Edit Resource-Level Status Types'
			 * screen Expected Result:Check boxes corresponding to Status Types
			 * ST2 and ST3 are checked and disabled.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResources.chkSTInEditResLevelPage(selenium,
						strResrcTypName2, strResource1, strStatusTypeValues[1],
						statTypeName2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkSTInEditResLevelPage(selenium,
						strResrcTypName2, strResource1, strStatusTypeValues[2],
						statTypeName3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on Save without making any changes Expected
			 * Result: User is returned to Resource List screen
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup>>Resource, edit resource RS1 and
			 * change the resource type form RT2 to RT1 and Save. No Expected
			 * Result
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName1,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on the 'Edit Resource-Level Status Types'
			 * screen Expected Result:Check box corresponding to Status Type: 1.
			 * ST2 is NOT selected and is enabled. 2. ST1 and ST3 are selected
			 * and disabled.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResources.chkSTInEditResLevelPage(selenium,
						strResrcTypName1, strResource1, strStatusTypeValues[1],
						statTypeName2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkSTInEditResLevelPage(selenium,
						strResrcTypName1, strResource1, strStatusTypeValues[0],
						statTypeName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkSTInEditResLevelPage(selenium,
						strResrcTypName1, strResource1, strStatusTypeValues[2],
						statTypeName3, false);
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
			gstrTCID = "36747";
			gstrTO = " Add a status type to a resource and verify that changing the resource type of the resource changes"
					+ " the associated status types when the 'Edit Resource-Level Status Types' screen is saved.";
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
	'Description		:Remove a status type ST that was added at the resource level from resource RS and verify that the view which was created selecting only ST and RS cannot be edited and saved.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/23/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS38557() throws Exception {
		try {

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			Views objViews = new Views();
			CreateUsers objCreateUsers = new CreateUsers();
			gstrTCID = "38557"; // Test Case Id
			gstrTO = " Remove a status type ST that was added at the resource level from resource RS and verify that the view which was created selecting only ST and RS cannot be edited and saved.";// Test
																																																		// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[5];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String statTypeName3 = "AutoST3" + System.currentTimeMillis();
			String statTypeName4 = "AutoST4" + System.currentTimeMillis();
			String statTypeName5 = "AutoST5" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "RT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();

			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserName;
			/*
			 * STEP : Action:Preconditions 1. Resource type RT is associated
			 * with status type ST. 2. Resources RS is created under RT 3.
			 * Status types NST (number), MST (multi), TST (text) and SST
			 * (saturation score) are added for resource RS1 at the resource
			 * level. 4. View V1 is created selecting NST, MST, TST, SST,
			 * resource type RT. 5. User U1 has 'Setup Resource Type', 'Setup
			 * Resources' and 'Configure Region Views' rights Expected Result:No
			 * Expected Result
			 */
			// 225079

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
			// ST3
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName3,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName3);

				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST4
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Saturation Score";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName4,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName4);

				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST5
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName5,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName5);

				if (strStatusTypeValues[4].compareTo("") != 0) {
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

			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { strStatusTypeValues[1],
						strStatusTypeValues[2], strStatusTypeValues[3],
						strStatusTypeValues[4] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(selenium,
						strRsTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER

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
										.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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

			/*
			 * STEP : Action:Login as user U1 and navigate to Setup>>Resources
			 * screen, click on 'Edit Status Types' link corresponding to
			 * resource RS Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 225087
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Deselect check boxes corresponding to NST, MST, TST
			 * and SST and save the page Expected Result:User is returned to the
			 * 'Resource List' screen
			 */
			// 225088
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[3], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[4], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup>>Views, click on 'Edit link
			 * corresponding to view V1 Expected Result:'Edit View' screen is
			 * displayed. Status types NST, MST, TST and SST are selected.
			 * Resource RS is deselected.
			 */
			// 225099
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkRSSelOrNotForView(selenium, false,
						strResVal, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTSelOrNotForView(selenium, true,
						strStatusTypeValues[1], statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTSelOrNotForView(selenium, true,
						strStatusTypeValues[2], statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTSelOrNotForView(selenium, true,
						strStatusTypeValues[3], statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTSelOrNotForView(selenium, true,
						strStatusTypeValues[4], statTypeName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select resource RS and click on 'Save' Expected
			 * Result:Error message 'No status types are associated at the
			 * resource level or with the resources of type RT' is displayed.
			 * View V1 is not saved.
			 */
			// 225110
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRSInEditView(selenium,
						strRSValue[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkErrMsgEditViewPage(selenium,
						strResrcTypName, true, strViewName);
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
			gstrTCID = "38557";
			gstrTO = "Remove a status type ST that was added at the resource level from resource RS and verify that the view which was created selecting only ST and RS cannot be edited and saved.";
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
	'Description		:Remove status type ST from resource RS, save the edit event template page, add status type ST for resource RS again and verify that status type ST is listed but not selected in the 'Edit Event Template' screen.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/23/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS37098() throws Exception {
		try {

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			CreateUsers objCreateUsers = new CreateUsers();
			Roles objRoles = new Roles();
			gstrTCID = "37098"; // Test Case Id
			gstrTO = " Remove status type ST from resource RS, DO NOT save the edit event template page, add status type ST for resource RS again and verify that status type ST is"
					+ " listed and selected in the 'Edit Event Template' screen";// Test
																					// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS1" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strTempName = "Autotmp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1 and not with status type ST2. 2. Resource RS
			 * is created under RT. 3. Status type ST2 is added at the resource
			 * level for resource RS 4. Event template ET is created by
			 * selecting RT, ST1 and ST2 5. Status type ST2 is not associated
			 * with any other resource/resource type 6. User U1 has following
			 * rights: Setup Resource Types Setup Resource Maintain Event
			 * Templates Maintain Events Expected Result:No Expected Result
			 */
			// 219990

			// ST1
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
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
			// USER
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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

			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
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
			 * STEP : Action:Login as user U1 and navigate to Setup>>Resource
			 * Expected Result:'Resource List' page is displayed
			 */
			// 219991
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 219992

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Deselect status type ST2 and save Expected
			 * Result:User is returned to the 'Resource List' page
			 */
			// 219993
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event>>Event Setup, select to edit
			 * event template ET Expected Result:'Edit Event Template ' page is
			 * displayed. Status type ST2 is NOT displayed in the screen.
			 */
			// 219994
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[1], false, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Cancel' Expected Result:User is returned
			 * to 'Event Template List' screen
			 */
			// 219995

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.cancelAndNavToEventTemplatePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup>>Resources, click on 'Edit Status
			 * Types' link corresponding to resource RS, select status type ST2
			 * again and save Expected Result:No Expected Result
			 */
			// 219996

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event>>Event Setup, select to edit
			 * event template ET Expected Result:'Edit Event Template ' page is
			 * displayed. Status type ST2 is selected.
			 */
			// 219997

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.chkSTSelOrNotEditEventTemplatePage(selenium,
								strStatusTypeValues[1], true, statTypeName2);
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
			gstrTCID = "37098";
			gstrTO = "Remove status type ST from resource RS, save the edit event template page, add status type ST for resource RS again and verify that status type ST is listed but not selected in the 'Edit Event Template' screen.";
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
	'Description		:Remove status type ST from resource RS, save the edit event template page, add status type ST for resource RS again and verify that status type ST is listed but not selected in the 'Edit Event Template' screen.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/23/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS37097() throws Exception {
		try {

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			CreateUsers objCreateUsers = new CreateUsers();
			Roles objRoles = new Roles();
			gstrTCID = "37097"; // Test Case Id
			gstrTO = " Remove status type ST from resource RS, save the edit"
					+ " event template page, add status type ST for resource RS"
					+ " again and verify that status type ST is listed but not"
					+ " selected in the 'Edit Event Template' screen.";// Test
																		// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS1" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strTempName = "Autotmp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";

			/*
			 * STEP : Action:Preconditions 1. Resource type RT is associated
			 * with status type ST1 and not with status type ST2. 2. Resource RS
			 * is created under RT. 3. Event template ET is created by selecting
			 * RT and ST1 4. Status type ST2 is not associated with any other
			 * resource type 5. Status type ST2 is added at the resource level
			 * for resource RS 6. User U1 has following rights: Setup Resource
			 * Types Maintain Event Templates Maintain Events Expected Result:No
			 * Expected Result
			 */
			// 219953

			// ST1
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
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
			// USER
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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

			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
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
			 * STEP : Action:Login as user U1 and navigate to Setup>>Resource
			 * Expected Result:'Resource List' page is displayed
			 */
			// 219954
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 219955

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Deselect status type ST2 and save Expected
			 * Result:User is returned to the 'Resource List' page
			 */
			// 219956
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event>>Event Setup, select to edit
			 * event template ET Expected Result:'Edit Event Template ' page is
			 * displayed. Status type ST2 is NOT displayed in the screen.
			 */
			// 219957
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[1], false, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Save the 'Edit Event Template' screen without
			 * making any changes Expected Result:User is returned to 'Event
			 * Template List' screen
			 */
			// 219982
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(
						selenium, strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup>>Resources, click on 'Edit Status
			 * Types' link corresponding to resource RS, select status type ST2
			 * again and save Expected Result:No Expected Result
			 */
			// 219984
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event>>Event Setup, select to edit
			 * event template ET Expected Result:'Edit Event Template ' page is
			 * displayed Status type ST2 is displayed in the screen but not
			 * selected.
			 */
			// 219987
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[1], true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.chkSTSelOrNotEditEventTemplatePage(selenium,
								strStatusTypeValues[1], false, statTypeName2);
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
			gstrTCID = "37097";
			gstrTO = "Remove status type ST from resource RS, save"
					+ " the edit event template page, add status type "
					+ "ST for resource RS again and verify that status"
					+ " type ST is listed but not selected in the 'Edit Event Template' screen.";
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
	'Description		:Verify that a status type is not listed under 'Status Type' list on 'Edit Template'
	                     screen, unless it is associated with a resource/resource type.
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :7/24/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS37956() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			CreateUsers objCreateUsers = new CreateUsers();
			Roles objRoles = new Roles();
			gstrTCID = "37956"; // Test Case Id
			gstrTO = " Verify that a status type is not listed under 'Status Type' list on 'Create New Template' "
					+ "screen, unless it is associated with a resource/resource type.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[10];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoSTn2" + System.currentTimeMillis();
			String statTypeName3 = "AutoSTn3" + System.currentTimeMillis();
			String statTypeName4 = "AutoSTx4" + System.currentTimeMillis();
			String statTypeName5 = "AutoSTx5" + System.currentTimeMillis();
			String statTypeName6 = "AutoSTs6" + System.currentTimeMillis();
			String statTypeName7 = "AutoSTs7" + System.currentTimeMillis();
			String statTypeName8 = "AutoSTm8" + System.currentTimeMillis();
			String statTypeName9 = "AutoSTm9" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS1" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1. 2. Resource RS is created under resource
			 * type RT. 3. Status types MST1(Multi), MST2(Multi), NST1(Number),
			 * NST2(Number), TST1(Text), TST2(Text), SST1(Saturation Score) and
			 * SST2(Saturation Score) are created newly and are not associated
			 * with any resource/resource type. 4. User U1 has following rights
			 * a. Setup Resource Types b. Maintain Event Templates Expected
			 * Result:No Expected Result
			 */
			// 222658

			// ST1
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
			// ST3
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName3,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName3);

				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST4
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName4,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName4);

				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST5
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName5,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName5);

				if (strStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST6
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Saturation Score";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName6,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName6);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName6);

				if (strStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST7
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Saturation Score";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName7,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName7);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[6] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName7);

				if (strStatusTypeValues[6].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST8
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName8,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[7] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName8);

				if (strStatusTypeValues[7].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST9
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName9,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[8] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName9);

				if (strStatusTypeValues[8].compareTo("") != 0) {
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

			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
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
			// USER
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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
			 * STEP : Action:Login as user U1 and navigate to Event>>Event Setup
			 * Expected Result:'Event Template List' screen is displayed.
			 */
			// 222662
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[1], false, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[3], false, statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[5], false, statTypeName6);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[7], false, statTypeName8);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup>>Resources Expected
			 * Result:'Resource List' screen is displayed.
			 */
			// 222681
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select the link 'Edit status types' corresponding
			 * to resource RS, select status type NST1, MST1, TST1 and SST1 and
			 * Save Expected Result:User is returned to 'Resource List' screen.
			 */
			// 222677
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctOnlySTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctOnlySTInEditRSLevelPage(
						selenium, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctOnlySTInEditRSLevelPage(
						selenium, strStatusTypeValues[5], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[7], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event>>Event Setup and select to create
			 * a new event template Expected Result:Status types MST1, NST1,
			 * TST1 and SST1 are displayed under 'Status Type' list in the
			 * 'Create New Event Template' screen.
			 */
			// 222678
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[1], true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[3], true, statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[5], true, statTypeName6);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[7], true, statTypeName8);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup>>Resource Types Expected
			 * Result:'Resource Type List' screen is displayed.
			 */
			// 222682
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select the link 'Edit' corresponding to resource
			 * type RT, select status type NST2, MST2, TST2 and SST2 and Save
			 * Expected Result:User is returned to 'Resource Type List' screen.
			 */
			// 222679
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[6], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[8], true);
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

			/*
			 * STEP : Action:Navigate to Event>>Event Setup and select to create
			 * a new event template Expected Result:Status types MST2, NST2,
			 * TST2 and SST2 are displayed under 'Status Type' list in the
			 * 'Create New Event Template' screen.
			 */
			// 222680
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[2], true, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[4], true, statTypeName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[6], true, statTypeName7);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[8], true, statTypeName9);
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
			gstrTCID = "37956";
			gstrTO = "Verify that a status type is not listed under 'Status Type' list on 'Create "
					+ "New Template' screen, unless it is associated with a resource/resource type.";// Test
			// objective
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
	'Description		:Verify that a status type is not listed under 'Status Type' list on 'Edit Template'
	                     screen, unless it is associated with a resource/resource type.
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :7/24/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS37957() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			CreateUsers objCreateUsers = new CreateUsers();
			Roles objRoles = new Roles();
			gstrTCID = "37957";
			gstrTO = " Verify that a status type is not listed under 'Status Type'"
					+ " list on 'Edit Template' screen, unless it is associated with"
					+ " a resource/resource type.";
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[10];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoSTn2" + System.currentTimeMillis();
			String statTypeName3 = "AutoSTn3" + System.currentTimeMillis();
			String statTypeName4 = "AutoSTx4" + System.currentTimeMillis();
			String statTypeName5 = "AutoSTx5" + System.currentTimeMillis();
			String statTypeName6 = "AutoSTs6" + System.currentTimeMillis();
			String statTypeName7 = "AutoSTs7" + System.currentTimeMillis();
			String statTypeName8 = "AutoSTm8" + System.currentTimeMillis();
			String statTypeName9 = "AutoSTm9" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS1" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strTempName = "Autotmp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";
			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1. 2. Resource RS is created under resource
			 * type RT 3. Status types MST1(Multi), MST2(Multi), NST1(Number),
			 * NST2(Number), TST1(Text), TST2(Text), SST1(Saturation Score) and
			 * SST2(Saturation Score) are created newly and are not associated
			 * with any resource/resource type. 4. Event Template ET is created
			 * selecting ST1 and RT 5. User U1 has following rights a. Setup
			 * Resource Types b. Maintain Event Templates Expected Result:No
			 * Expected Result
			 */
			// 222692

			// ST1
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
			// ST3
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName3,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName3);

				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST4
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName4,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName4);

				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST5
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName5,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName5);

				if (strStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST6
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Saturation Score";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName6,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName6);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName6);

				if (strStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST7
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Saturation Score";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName7,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName7);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[6] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName7);

				if (strStatusTypeValues[6].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST8
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName8,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[7] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName8);

				if (strStatusTypeValues[7].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST9
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName9,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[8] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName9);

				if (strStatusTypeValues[8].compareTo("") != 0) {
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

			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
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
			// USER
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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

			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
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
			 * STEP : Action:Login as user U1 and navigate to Event>>Event Setup
			 * Expected Result:'Event Template List' screen is displayed.
			 */
			// 222662
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[1], false, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[3], false, statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[5], false, statTypeName6);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[7], false, statTypeName8);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup>>Resources Expected
			 * Result:'Resource List' screen is displayed.
			 */
			// 222681
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select the link 'Edit status types' corresponding
			 * to resource RS, select status type NST1, MST1, TST1 and SST1 and
			 * Save Expected Result:User is returned to 'Resource List' screen.
			 */
			// 222677
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctOnlySTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctOnlySTInEditRSLevelPage(
						selenium, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctOnlySTInEditRSLevelPage(
						selenium, strStatusTypeValues[5], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[7], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event>>Event Setup and select edit
			 * event template Expected Result:Status types MST1, NST1, TST1 and
			 * SST1 are displayed under 'Status Type' list in the 'Create New
			 * Event Template' screen.
			 */
			// 222678

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[1], true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[3], true, statTypeName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[5], true, statTypeName6);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[7], true, statTypeName8);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup>>Resource Types Expected
			 * Result:'Resource Type List' screen is displayed.
			 */
			// 222682
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select the link 'Edit' corresponding to resource
			 * type RT, select status type NST2, MST2, TST2 and SST2 and Save
			 * Expected Result:User is returned to 'Resource Type List' screen.
			 */
			// 222679
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[6], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[8], true);
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

			/*
			 * STEP : Action:Navigate to Event>>Event Setup and select edit
			 * event template Expected Result:Status types MST2, NST2, TST2 and
			 * SST2 are displayed under 'Status Type' list in the 'Create New
			 * Event Template' screen.
			 */
			// 222680

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[2], true, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[4], true, statTypeName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[6], true, statTypeName7);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[8], true, statTypeName9);
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
			gstrTCID = "37957"; // Test Case Id
			gstrTO = "  Verify that a status type is not listed under 'Status Type' list on "
					+ "'Edit Template' screen, unless it is associated with a resource/resource type.";// Test
																										// objective
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
	'Description		:Add status type ST2 to resource RS1 at resource level under resource type RT1, where:
	                    1. ST1 is associated with resource types RT1 and RT2
	                    2. RS2 is created under resource type RT2
	                    3. Event template ET is created selecting ST1, ST2 and RT1 and RT2. <br/> <br/>
	                    Change the resource RS1's resource type and verify that the changes are reflected on the Event EVE screen.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/26/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS40221() throws Exception {
		try {

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			EventList objEventList = new EventList();
			gstrTCID = "40221"; // Test Case Id
			gstrTO = "Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. RS2 is created under resource type RT2"
					+ "3. Event template ET is created selecting ST1, ST2 and RT1 and RT2."
					+ "Change the resource RS1's resource type and verify that the changes are reflected on the Event EVE screen.";// Test
																																	// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName1 = "AutoRT1" + System.currentTimeMillis();
			String strResrcTypName2 = "AutoRT2" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[2];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];

			String strTempName = "temp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";

			// event

			String strEveName = "event" + System.currentTimeMillis();

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT1 is associated
			 * with status type ST1. 2. Resource type RT2 is associated with
			 * status type ST1. 3. Resource RS1 is created under resource type
			 * RT1. 4. Resource RS2 is created under resource type RT2. 5. Event
			 * template ET is created, selecting status types ST1, ST2, resource
			 * types RT1 and RT2. Expected Result:No Expected Result
			 */
			// 235508

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
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

			// RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName1, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName1);

				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName2, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[1] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName2);
				if (strRsTypeValues[1].compareTo("") != 0) {
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName1,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveAndVerifyResource(selenium, strResource1,
								strHavBed, "", strAbbrv, strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource2, strAbbrv, strResrcTypName2,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveAndVerifyResource(selenium, strResource2,
								strHavBed, "", strAbbrv, strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			/*
			 * STEP : Action:Login as RegAdmin, Navigate to Setup >> Resources
			 * screen, click on 'Edit Status Types' link corresponding to
			 * resource RS1. Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 235509
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

			/*
			 * STEP : Action:Select check box corresponding to ST2 and save the
			 * page Expected Result:User is returned to the 'Resource List'
			 * screen
			 */
			// 235510
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event Setup, Create Event
			 * template selecting resource types RT1, RT2 and status types ST1
			 * and ST2 Expected Result: Event template created is displayed on
			 * the 'Event Template List' screen.
			 */
			// 235512

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0],
						strRsTypeValues[1] };
				String[] strStatusTypeval = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event Management, create an
			 * event EVE selecting event template ET, resources RS1 and RS2.
			 * Expected Result:Event EVE created is listed on the 'Event
			 * Management' screen.
			 */
			// 235512
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource2, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEveName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on the EVE event banner Expected
			 * Result:Resource RS1 is displayed under resource type RT1 along
			 * with status types ST1 and ST2 Resource RS2 is displayed under
			 * resource type RT2 along with status type ST1
			 */
			// 235513
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName1, strResource1,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName2, strResource2,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource, click on the edit
			 * link associated with resource RS1, change the resource type RT1
			 * to RT2 and save Expected Result:'Resources List' page is
			 * displayed.
			 */
			// 235514
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName2,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on EVE event banner Expected Result:Resource
			 * RS1 is displayed under resource type RT2 along with status types
			 * ST1 and ST2 Resource RS2 is displayed under resource type RT2
			 * along with status type ST1
			 */
			// 235515
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName2, strResource1,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName2, strResource2,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource, click on the edit
			 * link associated with resource RS1, change the resource type RT2
			 * to RT1 and save Expected Result:'Resources List' page is
			 * displayed.
			 */
			// 235516
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName1,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on EVE event banner Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1 and ST2 Resource RS2 is displayed under resource type RT2
			 * along with status type ST1
			 */
			// 235517
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName1, strResource1,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName2, strResource2,
						strStatTypeArr);
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
			gstrTCID = "40221";
			gstrTO = "Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. RS2 is created under resource type RT2"
					+ "3. Event template ET is created selecting ST1, ST2 and RT1 and RT2."
					+ "Change the resource RS1's resource type and verify that the changes are reflected on the Event EVE screen.";
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
	'Description		:Add status type ST2 to resource RS1 at resource level under resource type RT1, where:
	                     1. ST1 is associated with resource types RT1 and RT2
	                     2. RS2 is created under resource type RT2
	                     3. View V1 is created selecting RS1, ST1, ST2 and RT2 (master check box)
	                     Change the resource RS1's resource type and verify that the changes 
	                     are reflected on the view screen of V1.
	'Arguments		    :None
	'Returns		    :None
	'Date			    :7/26/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS40106() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			Views objViews = new Views();
			gstrTCID = "40106"; // Test Case Id
			gstrTO = "Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. RS2 is created under resource type RT"
					+ "3. View V1 is created selecting RS1, ST1, ST2 and RT2 (master check box)"
					+ "Change the resource RS1's resource type and verify that the changes are reflected on the view screen of V1.";// Test
																																	// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName1 = "AutoRT1" + System.currentTimeMillis();
			String strResrcTypName2 = "AutoRT2" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[2];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValues[] = new String[2];
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();

			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT1 is associated
			 * with status type ST1. 2. Resource type RT2 is associated with
			 * status type ST1. 3. Resource RS1 is created under resource type
			 * RT1. 4. Resource RS2 is created under resource type RT2. 5. View
			 * V1 is created, selecting status types ST1, ST2, resource RS1 and
			 * resource type RT2(master check box) Expected Result:No Expected
			 * Result
			 */
			// 234383
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
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

			// RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName1, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName1);

				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName2, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[1] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName2);
				if (strRsTypeValues[1].compareTo("") != 0) {
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName1,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveAndVerifyResource(selenium, strResource1,
								strHavBed, "", strAbbrv, strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
						strResource1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValues[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource2, strAbbrv, strResrcTypName2,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveAndVerifyResource(selenium, strResource2,
								strHavBed, "", strAbbrv, strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
						strResource2);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValues[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				String[] strRSValue = { strRSValues[0] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(selenium,
						strRsTypeValues[1], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as RegAdmin, Navigate to Setup >> Resources
			 * screen, click on 'Edit Status Types' link corresponding to
			 * resource RS1. Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 234384
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
			/*
			 * STEP : Action:Select check box corresponding to ST2 and save the
			 * page Expected Result:User is returned to the 'Resource List'
			 * screen
			 */
			// 234385
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1 and ST2 Resource RS2 is displayed under resource type RT2
			 * along with status type ST1
			 */
			// 234390
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource1 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName1, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, true, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource2 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName2, strRS,
						strRsTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, true, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource, change the resource
			 * type RT1 to RT2 of resource RS1 and save Expected
			 * Result:'Resources List' page is displayed.
			 */
			// 234391
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName2,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT2 along with status types
			 * ST1 and ST2 Resource RS2 is displayed under resource type RT2
			 * along with status type ST1
			 */
			// 234393
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource1 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName2, strRS,
						strRsTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, true, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource2 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName2, strRS,
						strRsTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, true, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Resource, change the resource
			 * type RT2 to RT1 of resource RS1 and save Expected
			 * Result:'Resources List' page is displayed.
			 */
			// 234934
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName1,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1 and ST2 Resource RS2 is displayed under resource type RT2
			 * along with status type ST1
			 */
			// 234935

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource1 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName1, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, true, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource2 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName2, strRS,
						strRsTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, true, strResource2);
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
			gstrTCID = "40106";
			gstrTO = "Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. RS2 is created under resource type RT"
					+ "3. View V1 is created selecting RS1, ST1, ST2 and RT2 (master check box)"
					+ "Change the resource RS1's resource type and verify that the changes are reflected on the view screen of V1.";
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
	'Description		:From resource RS, remove a status type that was added at the resource level and verify that the status type is not included for events that are created newly
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/30/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS36845() throws Exception {
		try {
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			CreateUsers objCreateUsers = new CreateUsers();
			Roles objRoles = new Roles();
			EventList objEventList = new EventList();
			gstrTCID = "36845"; // Test Case Id
			gstrTO = " From resource RS, remove a status type that was added at the resource level and verify that the status type is not included for events that are created newly";// Test
																																														// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strTempName1 = "Autotmp1" + System.currentTimeMillis();
			String strTempName2 = "Autotmp2" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName1 = "AutoEve_1" + System.currentTimeMillis();
			String strEveName2 = "AutoEve_2" + System.currentTimeMillis();
			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1 and not with status type ST2. 2. Resources
			 * RS1 and RS2 are created under RT. 3. Status type ST2 is added at
			 * the resource level for resource RS1 4. Event template ET1 is
			 * created by selecting RT and ST2 5. Event template ET2 is created
			 * by selecting RT and ST1, ST2 6. User U1 has following rights:
			 * Setup Resource Types Setup Resources Maintain Event Templates
			 * Maintain Events Expected Result:No Expected Result
			 */
			// 220015

			// ST1
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource1, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource2, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource2, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
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
			// USER
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName1, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName2, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
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
			 * STEP : Action:Login as user U1 and navigate to Setup>>Resource
			 * page, click on 'Edit Status Types' link corresponding to resource
			 * RS1 Expected Result:No Expected Result
			 */
			// 224002
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
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

			/*
			 * STEP : Action:Deselect check box corresponding to status type ST2
			 * and save Expected Result:User is returned to the 'Resource List'
			 * page
			 */
			// 234860

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event>>Event Management, and select to
			 * create new event, select to create event under event template ET1
			 * Expected Result:'Create New Event' screen is displayed and NO
			 * resources are listed under 'Resources to Participate in This
			 * Event:' section.
			 */
			// 220023
			/*
			 * STEP : Action:Provide mandatory data, create event EVE1 Expected
			 * Result:Event EVE1 is created and is listed on the 'Event
			 * Management' screen
			 */
			// 220024
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName1, strEveName1, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertFalse(selenium
						.isTextPresent("Resources to Participate in This Event"));
				log4j
						.info("NO resources are listed under 'Resources to Participate in This Event:' section.");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEveName1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select to create new event again, select to create
			 * event under event template ET2 Expected Result:'Create New Event'
			 * screen is displayed and both resources RS1 and RS2 are listed
			 * under 'Resources to Participate in This Event:' section.
			 */
			// 220026
			/*
			 * STEP : Action:Provide mandatory data, select resources RS1 and
			 * RS2 and save the event EVE2 Expected Result:Event EVE2 is created
			 * and is listed on the 'Event Management' screen
			 */
			// 220027
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName2, strEveName2, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[0], true, strResource1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[1], true, strResource1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource2, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEveName2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on the event banner of EVE2 Expected
			 * Result:Resources RS1 and RS2 are displayed. Only ST1 is displayed
			 * for both the resources on 'Event Status' screen.
			 */
			// 220031
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName2, strResrcTypName, strResource1,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objEventList.chkSTInEventBanners(selenium,
						strStatTypeArr, true, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName2, strResrcTypName, strResource2,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objEventList.chkSTInEventBanners(selenium,
						strStatTypeArr, true, strResource2);
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
			gstrTCID = "36845";
			gstrTO = "From resource RS, remove a status type that was added at the resource level and verify that the status type is not included for events that are created newly";
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

	/********************************************************************************
	 'Description :From resource RS, remove a status type that was added at the resource level and verify that the expired status notifications are not received by the user for the removed status type
	 'Precondition :None
	 'Arguments  :None
	 'Returns  :None
	 'Date    :30-July-2012
	 'Author   :QSG
	 '-------------------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 **********************************************************************************/
	 

	@Test
	public void testFTS36846() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		General objMail = new General();
		Views objViews=new Views();
		try {
			gstrTCID = "36846";
			gstrTO = "From resource RS, remove a status type that was " +
					"added at the resource level and verify that the" +
					" expired status notifications are not received by " +
					"the user for the removed status type";

			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);


			String strUpdatTxtValue1 = "a";
			//String strUpdatTxtValue2 = "b";

			String strUpdatNumValue1 = "1";
			//String strUpdatNumValue2 = "2";

			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7",
					"8" };
			//String strScUpdValue2[] = { "1", "2", "3", "4", "5", "6", "7", "8","9" };

			//String strUpdateValue1 = "393";
			//String strUpdateValue2 = "429";

			String strAbbrv = "Atexp";

			String strState = "Alabama";
			String strCountry = "Autauga County";

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
		
			String strUsrName1 = "AutoUserA" + System.currentTimeMillis();

			String strFulName = "Full" + strUsrName1;

			String strUsrName2 = "AutoUsrB" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Full" + strUsrName2;
			
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strStatusTypeValue2 = "Multi";
			String statTypeName2 = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strStatusTypeValue1 = "Number";
			String statTypeName1 = "AutoNSt_" + strTimeText;

			String strStatusTypeValue3 = "Text";
			String statTypeName3 = "AutoTSt_" + strTimeText;

			String strStatusTypeValue4 = "Saturation Score";
			String statTypeName4 = "AutoSSt_" + strTimeText;

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];

			String strStatusValue[] = new String[2];

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();

			String strStatTypeAsgnVal = "Number";
			String strStatTypeAsgn = "AutoNsts_" + strTimeText;
			String strSTAsnVal[] = new String[1];

			// Admin login credential
			String strMailUserName = rdExcel.readData("WebMailUser", 2, 1);
			String strMailPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strExpHr = "00";
			String strExpMn = "05";

			String strSubjName = "EMResource Expired Status Notification: "
					+ strResource;

			int intEMailRes = 0;
			int intPagerRes = 0;
			String strFrom = "notification@emsystems.com";
			String strTo = strMailUserName;

			/*
			 * 
			 * 
			 * Preconditions:
			 * 
			 * 1. Resource type RT is associated with only status type ST1
			 * 
			 * 2. Status Types NST (Number status type), MST (Multi status
			 * type), TST (Text Status Type) and SST (Saturation score status
			 * type) are created providing expiration time.
			 * 
			 * 2. Resources RS1 and RS2 are created under RT.
			 * 
			 * 3. Status types NST, MST, TST and SST are added for resource RS1
			 * at the resource level.
			 * 
			 * 4. User U1 has the right 'Setup Resource Types'
			 * 
			 * 5. User U2 has following rights:
			 * 
			 * Role to update status types: NST, MST, TST and SST 'Update
			 * Status' right on the resource RS1
			 * 
			 * and user U2 has provided email and pager addresses and has opted
			 * to receive expired status notifications
			 */
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatTypeAsgnVal, strStatTypeAsgn, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strStatTypeAsgn);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTAsnVal[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectSTypesAndFilMandFldsWithExTime(
						selenium, strStatusTypeValue1, statTypeName1,
						strStatTypDefn, strExpHr, strExpMn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, statTypeName1);
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
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectSTypesAndFilMandFldsWithExTime(
						selenium, strStatusTypeValue3, statTypeName3,
						strStatTypDefn, strExpHr, strExpMn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, statTypeName3);
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
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectSTypesAndFilMandFldsWithExTime(
						selenium, strStatusTypeValue4, statTypeName4,
						strStatTypDefn, strExpHr, strExpMn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, statTypeName4);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValue2, statTypeName2, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, statTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						selenium, statTypeName2, strStatusName1,
						strStatusTypeValue2, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						selenium, statTypeName2, strStatusName2,
						strStatusTypeValue2, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeName2, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeName2, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
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
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTAsnVal[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium, strResType);
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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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
				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intRes = 0; intRes < strSTvalue.length; intRes++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
							selenium, strSTvalue[intRes], true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.savAndVerifyEditRSLevelPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

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

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUsrName2, strInitPwd, strConfirmPwd, strFulName);
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
				strFuncResult = objCreateUsers.selectResourceRights(selenium,
						strResource, false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.provideExpirdStatusNotificatcion(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				/*	selenium.click("css=input[name=expiredStatusEmailInd]");
				selenium.click("css=input[name=expiredStatusPagerInd]");*/

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUsrName2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
						strUsrName1, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUsrName1, strByRole, strByResourceType,
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
			 * 2 login as user U2 and update the status of status types NST,
			 * MST, TST and SST <-> No Expected Result
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.newUsrLogin(selenium, strUsrName2,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName2, statTypeName1,
						statTypeName4, statTypeName3 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

		/*	try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType_ChangeVal(
						selenium, strResource, statTypeName2, strSTvalue[1],
						strStatusName1, strStatusValue[0], strStatusName2,
						strStatusValue[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName2, statTypeName1,
						statTypeName4, statTypeName3 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateNumStatusType(selenium,
						strResource, statTypeName3, strSTvalue[2],
						strUpdatTxtValue1, strUpdatTxtValue2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName2, statTypeName1,
						statTypeName4, statTypeName3 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateNumStatusType(selenium,
						strResource, statTypeName1, strSTvalue[0],
						strUpdatNumValue1, strUpdatNumValue2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName2, statTypeName1,
						statTypeName4, statTypeName3 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateSatuScoreStatusType(selenium,
						strResource, statTypeName4, strSTvalue[3],
						strScUpdValue1, strScUpdValue2, strUpdateValue1,
						strUpdateValue2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
 
			}

			*/
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUpdateStatus(selenium,
						propElementDetails
								.getProperty("StatusType.UpdateStatusLink"));
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[3]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdatNumValue1, strSTvalue[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdatTxtValue1, strSTvalue[2], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strSTvalue[1], false, "", "");

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

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 3 Logout and login as user U1 and navigate to Setup>>Resource
			 * page, click on 'Edit Status Types' link corresponding to resource
			 * RS1 <-> 'Edit Resource-Level Status Types' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.newUsrLogin(selenium, strUsrName1,
						strConfirmPwd);
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
				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4 Deselect check boxes corresponding to status types NST, MST,
			 * TST and SST and save <-> User is returned to the 'Resource List'
			 * page
			 */

			for (int intRes = 0; intRes < strSTvalue.length; intRes++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
							selenium, strSTvalue[intRes], false);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.savAndVerifyEditRSLevelPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				/*
				 * 5 Wait till the expiration time (that was specified for the
				 * status types NST, MST, TST and SST) is reached <-> User U2
				 * does not receive expired status notifications of NST, MST,
				 * TST and SST for resource RS1
				 */
				Thread.sleep(360000);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strMailUserName, strMailPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intPagerRes++;
						log4j.info("pager notification is not recieved");
					} catch (AssertionError Ae) {
						log4j.info("pager notification is recieved");
						gstrReason = gstrReason + " " + strFuncResult
								+ " pager notification is recieved";
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");

					selenium.click("link=Inbox");
					selenium.waitForPageToLoad("90000");
					strSubjName = "EMResource Expired Status Notification: "
							+ strResource;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes++;
						log4j.info("Email notification is not recieved");
					} catch (AssertionError Ae) {
						log4j.info("Email notification is recieved");
						gstrReason = gstrReason + " " + strFuncResult
								+ " Email notification is recieved";
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");

					selenium.click("link=Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes++;
						log4j.info("Email notification is not recieved");

					} catch (AssertionError Ae) {
						log4j.info("Email notification is recieved");
						gstrReason = gstrReason + " " + strFuncResult
								+ " Email notification is recieved";
					}
					// check Email, pager notification
					if (intEMailRes == 2 && intPagerRes == 1) {
						gstrResult = "PASS";
					}
					selenium.selectWindow("");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "36846";
			gstrTO = "From resource RS, remove a status type that was added at the resource level and verify that the expired status notifications are not received by the user for the removed status type";
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
	
	 /***************************************************************
	 'Description		:Add status type ST2 to resource RS1 at resource level under resource type RT1, where:
	                    1. ST1 is associated with resource types RT1 and RT2
	                    2. No resource is created under resource type RT2
	                    3. View V1 is created selecting RT1, RT2 and ST1, ST2.
	                    Change the association of status types and verify that the changes are reflected on the view screen of V1.
	 'Precondition		:
	 'Arguments		    :None
	 'Returns		    :None
	 'Date			    :7/31/2012
	 'Author			:QSG
	 '---------------------------------------------------------------
	 'Modified Date				Modified By
	 'Date					Name
	 ***************************************************************/

	@Test
	public void testFTS88877() throws Exception {
		try {

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			Views objViews = new Views();
			gstrTCID = "88877"; // Test Case Id
			gstrTO = " Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. No resource is created under resource type RT2"
					+ "3. View V1 is created selecting RT1, RT2 and ST1, ST2."
					+ "Change the association of status types and verify that the changes are reflected on the view screen of V1.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST data
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "RT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource data
			String strResource = "AutoRS1" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT1 is associated
			 * with status type ST1. 2. Resource RS1 is created under resource
			 * type RT1. 3. Status type ST2 is created and is not associated to
			 * any Resource Type or Resource Expected Result:No Expected Result
			 */
			// 540825

			// ST1
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
			/*
			 * STEP : Action:Login as RegAdmin, Navigate to Setup >> Resources
			 * screen, click on 'Edit Status Types' link corresponding to
			 * resource RS1. Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 540826

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select check box corresponding to ST2 and save the
			 * page Expected Result:User is returned to the 'Resource List'
			 * screen
			 */
			// 540827

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Views, Create a view V1
			 * selecting resource type RT1, status types ST1 and ST2 Expected
			 * Result:No Expected Result
			 */
			// 540828
			// View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(selenium,
						strRsTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1 and ST2
			 */
			// 540829
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, true, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource Types, click on the
			 * edit link associated with resource RT1, select ST2 and save
			 * Expected Result:'Resource Type List' screen is displayed.
			 */
			// 540830
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrcTypName);
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

			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1 and ST2.
			 */
			// 540831
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, true, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource, click on the Edit
			 * status type link associated with resource RS1. Expected
			 * Result:'Edit Resource-Level Status Types' page is displayed Check
			 * box related to ST2 is selected and disabled. Check box related to
			 * ST1 is selected and disabled.
			 */
			// 540832

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkSTInEditResLevelPage(selenium,
						strResrcTypName, strResource, strStatusTypeValues[0],
						statTypeName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkSTInEditResLevelPage(selenium,
						strResrcTypName, strResource, strStatusTypeValues[1],
						statTypeName2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Cancel ' button Expected Result:'Resource
			 * List' screen is displayed.
			 */
			// 540833
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.cancelAndNavToRSListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource Types, click on the
			 * edit link associated with resource RT1, deselect ST2 and save
			 * Expected Result:'Resource Type List' screen is displayed.
			 */
			// 540834
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[1], false);
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

			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1 and ST2.
			 */
			// 540835
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium,
						strStatTypeArr, true, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource, click on the Edit
			 * status type link associated with resource RS1. Expected
			 * Result:'Edit Resource-Level Status Types' page is displayed Check
			 * box related to ST2 is selected and enabled. Check box related to
			 * ST1 is selected and disabled.
			 */
			// 540836

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkSTSelectedOrNotEditRsLevelPage(
						selenium, strStatusTypeValues[1], statTypeName2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.chkSTInEditResLevelPage(selenium,
						strResrcTypName, strResource, strStatusTypeValues[0],
						statTypeName1, false);
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
			gstrTCID = "88877";
			gstrTO = " Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. No resource is created under resource type RT2"
					+ "3. View V1 is created selecting RT1, RT2 and ST1, ST2."
					+ "Change the association of status types and verify that the changes are reflected on the view screen of V1.";// Test
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
	}

	 /***************************************************************
	 'Description		:Verify that number status types associated with same standard status type can be associated with a single resource.
	 'Precondition		:
	 'Arguments		:None
	 'Returns		:None
	 'Date			:7/31/2012
	 'Author			:QSG
	 '---------------------------------------------------------------
	 'Modified Date				Modified By
	 'Date					Name
	 ***************************************************************/

	@Test
	public void testFTS38411() throws Exception {
		try {

			Login objLogin = new Login();
			Resources objResources = new Resources();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			gstrTCID = "38411"; // Test Case Id
			gstrTO = " Verify that number status types associated with same"
					+ " standard status type can be associated with a single resource.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[3];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoSTn2" + System.currentTimeMillis();
			String statTypeName3 = "AutoSTn3" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS1" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with only status type ST. 2.Resources RS1 is created under RT.
			 * Expected Result:No Expected Result
			 */
			// 250540
			// ST1
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
			/*
			 * STEP : Action:Login as RegAdmin and Navigate to >> Setup >>
			 * Status Types Expected Result:'Status Type List' page is
			 * displayed.
			 */
			// 250544

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New Status Type' button Expected
			 * Result:'Select Status Type' page is displayed
			 */
			// 250546
			/*
			 * STEP : Action:Select 'Number' in 'Select Type' drop down list,
			 * and click on 'next' button Expected Result:'Create Number Status
			 * Type' page is displayed.
			 */
			// 250549

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
			/*
			 * STEP : Action:Fill the mandatory data, name the status type as
			 * ST1 and select the standard status type say 'X', and click on
			 * 'Save' button Expected Result:'Status Type List' page is
			 * displayed and ST1 is displayed in the list
			 */
			// 250550
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
			/*
			 * STEP : Action:Navigate to >> Setup >> Status Types Expected
			 * Result:'Status Type List' page is displayed.
			 */
			// 250552

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New Status Type' button Expected
			 * Result:'Select Status Type' page is displayed
			 */
			// 250553
			/*
			 * STEP : Action:Select 'Number' in 'Select Type' drop down list,
			 * and click on 'next' button Expected Result:'Create Number Status
			 * Type' page is displayed.
			 */
			// 250555
			// ST3
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName3,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Fill the mandatory data, name the status type as
			 * ST2 and select the standard status type say 'X', and click on
			 * 'Save' button Expected Result:'Status Type List' page is
			 * displayed and ST2 is displayed in the list
			 */
			// 250557

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName3);

				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to >> Setup >> Resources Expected
			 * Result:'Resource List' is displayed
			 */
			// 250560
			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit Status Types' link associated with
			 * 'RS' Expected Result:'Edit Resource-Level Status Types' screen is
			 * displayed
			 */
			// 250563
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select ST1 and ST2 from 'Additional Status Types'
			 * list and click on 'Save' button Expected Result:'Resource List'
			 * screen is displayed
			 */
			// 250564

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.savAndVerifyEditRSLevelPage(selenium);
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
			gstrTCID = "38411";
			gstrTO = "Verify that number status types associated with same standard status type can be associated with a single resource.";
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
	 'Description		:From resource RS, remove a status type that was added at the resource level and add it back, verify that the changes are reflected for the running event
	 'Precondition		:
	 'Arguments		:None
	 'Returns		:None
	 'Date			:7/31/2012
	 'Author			:QSG
	 '---------------------------------------------------------------
	 'Modified Date				Modified By
	 'Date					Name
	 ***************************************************************/

	@Test
	public void testFTS36844() throws Exception {
		try {

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			EventSetup objEventSetup = new EventSetup();
			CreateUsers objCreateUsers = new CreateUsers();
			Roles objRoles = new Roles();
			EventList objEventList = new EventList();
			gstrTCID = "36844"; // Test Case Id
			gstrTO = " From resource RS, remove a status type that was "
					+ "added at the resource level and add it back, verify"
					+ " that the changes are reflected for the running event";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1" + System.currentTimeMillis();
			String statTypeName2 = "AutoST2" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT" + System.currentTimeMillis();
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource1 = "AutoRS1" + System.currentTimeMillis();
			String strResource2 = "AutoRS2" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strTempName = "Autotmp" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + System.currentTimeMillis();
			/*
			 * STEP : Action:Preconditions 1. Resource type RT is associated
			 * with status type ST1 and not with status type ST2. 2. Resources
			 * RS1 and RS2 are created under RT. 3. ST2 is added at the resource
			 * level for resource RS1. 4. Event template ET is created by
			 * selecting RT, status types ST1 and ST2. 5. Event EVE1 is created
			 * under event template ET selecting resources RS1 and RS2 6. User
			 * U1 has following rights: Setup Resource Types Maintain Event
			 * Templates Maintain Events Expected Result:No Expected Result
			 */
			// 219425

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
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource1, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource2, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource2, strHavBed, "", strAbbrv, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
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
			// USER
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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

			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource2, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEveName, true);
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
			 * STEP : Action:Login as user U1 and navigate to Setup>>Resource
			 * page Expected Result:'Resource List' page is displayed
			 */
			// 219426
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS1 Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 219427
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Deselect status type ST2 and save Expected
			 * Result:User is returned to the 'Resource List' page ST2 is NOT
			 * displayed on the 'Event Status Screen' of event EVE1
			 */
			// 219428
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objEventList.checkSTypesEvntBanner(selenium,
						strEveName, strResrcTypName, strStatTypeArr, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event>>Event Management, and select to
			 * edit event EVE1 Expected Result:'Edit event' screen is displayed.
			 * Status type ST2 is NOT displayed for 'Status Types' field.
			 */
			// 219431
			// event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventPage(selenium,
						strStatusTypeValues[1], false, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Cancel' Expected Result:User is returned
			 * to 'Event Management' screen
			 */
			// 219432
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.cancelAndNavToEventManagementScreen(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup>>Resource page, click on 'Edit
			 * Status Types' link corresponding to resource RS1 Expected
			 * Result:'Edit Resource-Level Status Types' screen is displayed.
			 * Status type ST2 is displayed in the list.
			 */
			// 219453

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
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium,
						statTypeName2, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select status type ST2 and save Expected
			 * Result:User is returned to the 'Resource List' page ST2 is
			 * displayed on the 'Event Status Screen' of event EVE1.
			 */
			// 219454
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objEventList.checkSTypesEvntBanner(selenium,
						strEveName, strResrcTypName, strStatTypeArr, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event>>Event Management, and select to
			 * edit event EVE1 Expected Result:'Edit event' screen is displayed.
			 * Status type ST2 is selected and disabled under 'Status Types'
			 * field.
			 */
			// 219459
			// event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTSelectedOrNotEditEventPage(
						selenium, strStatusTypeValues[1], true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup>>Resource page, click on 'Edit
			 * Status Types' link corresponding to resource RS1, deselect status
			 * type ST2 and save Expected Result:'Resource List' page is
			 * displayed ST2 is NOT displayed on the 'Event Status Screen' of
			 * event EVE1
			 */
			// 219460

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
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objEventList.checkSTypesEvntBanner(selenium,
						strEveName, strResrcTypName, strStatTypeArr, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event>>Event Management, and select to
			 * edit event EVE1 Expected Result:'Edit event' screen is displayed.
			 * Status type ST2 is NOT displayed for 'Status Types' field.
			 */
			// 219461

			// event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventPage(selenium,
						strStatusTypeValues[1], false, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Save' Expected Result:User is returned to
			 * 'Event Management' screen
			 */
			// 219462
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.saveAndNavToEventManagementScreen(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup>>Resource page, click on 'Edit
			 * Status Types' link corresponding to resource RS1, select status
			 * type ST2 and save Expected Result:'Resource List' page is
			 * displayed ST2 is NOT displayed on the 'Event Status Screen' of
			 * event EVE1
			 */
			// 219465

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
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objEventList.checkSTypesEvntBanner(selenium,
						strEveName, strResrcTypName, strStatTypeArr, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event>>Event Management, and select to
			 * edit event EVE1 Expected Result:'Edit event' screen is displayed.
			 * Status type ST2 is deselected and is enabled for 'Status Types'
			 * field.
			 */
			// 219467

			// event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTSelectedOrNotEditEventPage(
						selenium, strStatusTypeValues[1], true, statTypeName2);
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
			gstrTCID = "36844";
			gstrTO = "From resource RS, remove a status type that was added at the resource level and add it back, verify that the changes are reflected for the running event";
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
'Description		:Add a status type to resource RS and verify that a view can be created selecting newly added status type and only the associated resource RS.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:11/26/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testFTS36780() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		MobileView objMobileView = new MobileView();
		try {
			gstrTCID = "36780"; // Test Case Id
			gstrTO = " Add a status type to resource RS and verify that a view can be created selecting" +
					" newly added status type and only the associated resource RS.";//TO																				// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			// user
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName1;
			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";

			// Shared
			String statNumTypeName1 = "NST1" + strTimeText;
			String statNumTypeName2 = "NST2" + strTimeText;
			String str_statNumTypeName[] = new String[4];

			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];
			// RS
			String strResourceA = "AutoRs1_A" + strTimeText;
			String strResourceB = "AutoRs1_B" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[2];
			// Role
			String strRolesName = "Rol" + strTimeText;
			String strRoleValue = "";
			// sec data
			String strSection1 = "AB1_" + strTimeText;
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1 and not with status type ST2. 2. Resources
			 * RS1 and RS2 are created under RT. 3. User U1 has following
			 * rights: Setup Resource Types Configure Region Views Expected
			 * Result:No Expected Result
			 */
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statNumTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statNumTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_statNumTypeName[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statNumTypeName1);

				if (str_statNumTypeName[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statNumTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statNumTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_statNumTypeName[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statNumTypeName2);

				if (str_statNumTypeName[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						selenium, strResrcTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_statNumTypeName[0] + "']");

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

			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadresSharWitRgn(
						selenium, strResourceA, strAbbrv, strResrcTypName,
						true, strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
						strResourceA);

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
				strFuncResult = objRs.createResourceWitLookUPadresSharWitRgn(
						selenium, strResourceB, strAbbrv, strResrcTypName,
						true, strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
						strResourceB);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};

				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						selenium, strRolesName, strRoleRights,
						strViewRightValue, false, updateRightValue, false,
						false);

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

			// user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
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
						selenium, strResourceA, strRSValue[0], false, false,
						false, true);
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

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
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
				blnLogin = true;
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
				String[] strStatTypeArr = { statNumTypeName1, statNumTypeName2 };
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strStatTypeArr, strSection1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			/*
			 * STEP : Action:Login as user U1 and navigate to Setup>>Resource
			 * page Expected Result:'Resource List' page is displayed
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS1 Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage_LinkChanged(
						selenium, strResourceA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select status type ST2 and save Expected
			 * Result:user is returned to the 'Resource List' page
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,
						str_statNumTypeName[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup>>Views and select to create new
			 * region view Expected Result:'Create New View' screen is displayed
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToCreateViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide mandatory data, select status type ST2 and
			 * resource RS2 and save the view V1 Expected Result:Error message
			 * stating that status type ST2 is not associated with resource RS2
			 * is displayed and the view is not saved
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { str_statNumTypeName[1] };
				String[] strRSValues = { strRSValue[1] };
				strFuncResult = objViews.createViewNew(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkErrMsgEditViewPage(selenium,
						strResrcTypName, false, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select status type ST2 and resource RS1 and save
			 * the view V1 Expected Result:View V1 is saved and is listed in the
			 * 'Region Views List' screen
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRSInEditView(selenium,
						strRSValue[1], false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRSInEditView(selenium,
						strRSValue[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.SaveAndVerifyView(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View>>V1 Expected Result:Resource RS1
			 * is displayed with only ST2 on the view screen
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResourceA };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statNumTypeName2, statNumTypeName1 };
				strFuncResult = objViews.chkSTInUserViewScreen(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the 'Mobile View' link displayed at the
			 * footer Expected Result:'Resources' option is displayed in the
			 * 'Main Menu' screen
			 */
			try {
				assertEquals("Status Type " + statNumTypeName1
						+ "is NOT displayed in the User view screen",
						strFuncResult);
				strFuncResult = objMobileView.navToViewsInMobileView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the 'Resources' link on the 'Main Menu'
			 * screen Expected Result:View V1 is displayed in the 'Resources'
			 * screen
			 */

			/*
			 * STEP : Action:Click on view V1 Expected Result:Resource RS1 is
			 * displayed
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.navToUserViewsInMob(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource name RS1 Expected Result:Status
			 * types ST1 and ST2 are displayed in the details page of RS1
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statNumTypeName1, statNumTypeName2 };
				strFuncResult = objMobileView.checkResouceDetail(selenium,
						strResourceA, strSection1, strStatTypeArr);
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
				strTestData[2] = strUserName1 + "/" + strInitPwd;
				strTestData[3] = statNumTypeName1 + statNumTypeName2;
				strTestData[4] = strViewName;
				strTestData[5] = "Verify from 12th step";
				strTestData[6] = strResourceA + "/" + strResourceB;
				strTestData[7] = strSection1;
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
			gstrTCID = "36780";
			gstrTO = "Add a status type to resource RS and verify that a view can be created selecting newly added status type and only the associated resource RS.";
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

/********************************************************************************
	'Description	Add a status type to resource RS and verify that the logged in user 
	'				can set status change notification preferences for the newly added status type for only resource RS.
	'Precondition	:1. Resource type RT is associated with status type ST1 and NOT with status types NST (Number status type), MST (Multi status type) and SST (Saturation score status type).
						2. Resources RS1 and RS2 are created under RT.
						3. Status Types NST, MST and SST are added under status type section SEC
						4. User U1 has following rights:
						Setup Resource Types
						Edit Status Change Notification Preferences 
	'Arguments		:None
	'Returns		:None
	'Date	 		:26-Nov-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/

	@Test
	public void testFTS105310() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		General objMail = new General();// object of class General
		ViewMap objViewMap = new ViewMap();
		EventNotification objEventNotification = new EventNotification();

		try {
			gstrTCID = "105310 ";
			gstrTO = "Add a status type to resource RS and verify that the logged in user"
					+ " can set status change notification preferences for the newly added"
					+ " status type for only resource RS.";
			gstrResult = "FAIL";
			gstrReason = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTmText = dts.getCurrentDate("HHmm");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strUserName_Update = "UpdateUsr"
					+ System.currentTimeMillis();
			String strUsrFulName_Update = strUserName_Update;

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String statStatusTypeName = "ST" + strTimeText;
			String statrNumTypeName = "NST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;
			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strSSTValue = "Saturation Score";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[2];

			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";

			String strResource1 = "AutoRs_1" + strTimeText;
			String strResource2 = "AutoRs_2" + strTimeText;

			String strAbbrv = "A" + strTmText;
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

			String strAbove = "100";
			String strBelow = "50";

			String strSaturAbove = "400";
			String strSaturBelow = "200";

			String strStartDate = "";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			int intEMailRes = 0;
			int intPagerRes = 0;

			int intEMailRes_1 = 0;
			int intPagerRes_1 = 0;

			int intEMailRes_2 = 0;
			int intPagerRes_2 = 0;

			int intResCnt = 0;

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1 = "";
			String strMsgBody2 = "";

			String strMsgBodyEmail = "";
			String strMsgBodyPager = "";

			String strSubjName = "";

			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";

			String strCurrDate = "";
			String strCurrDate2 = "";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statrNumTypeName, statrMultiTypeName,
					statrSaturatTypeName };

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

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

				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statStatusTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[3] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statStatusTypeName);

				if (strSTvalue[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
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
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, statrSaturatTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statrSaturatTypeName);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, statrMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statrMultiTypeName);

				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Statuses 'S1' & 'S2' are created under 'MST'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statrMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statrMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						selenium, statrMultiTypeName, strStatusName1);
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
				strStatusValue[1] = objStatusTypes.fetchStatValInStatusList(
						selenium, statrMultiTypeName, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Resource type RT is associated with status type ST1 and NOT
			 * with status types NST (Number status type), MST (Multi status
			 * type) and SST (Saturation score status type).
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[3] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(selenium,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resources RS1 and RS2 are created under RT.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS1
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource1, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(selenium, strResource1);

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
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource2, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(selenium, strResource2);

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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" } };
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(selenium, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(selenium,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName_1);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Status Types NST, MST and SST are added under status type
			 * section SEC
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
				blnLogin = true;
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
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSectionValue = objViews.fetchSectionID(seleniumFirefox,
						strSection1);
				if (strSectionValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

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
			 * 4. User U1 has following rights:
			 * 
			 * Setup Resource Types Edit Status Change Notification Preferences
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

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
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

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

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);

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

			// User for updating status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_Update, strInitPwd, strConfirmPwd,
						strUsrFulName_Update);

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
						selenium, strResource1, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_Update, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("--------------- test case " + gstrTCID
					+ " execution starts----------");

			/*
			 * 2 Login as user U1 and navigate to Setup>>Resource page 'Resource
			 * List' page is displayed
			 */

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
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Click on 'Edit Status Types' link corresponding to resource RS1
			 * 'Edit Resource-Level Status Types' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage_LinkChanged(
						selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Select status types NST, MST and SST and save user is returned
			 * to the 'Resource List' page
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						selenium, strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						selenium, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,
						strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Navigate to Preferences>>Status Change Prefs, and click on
			 * 'Add' button 'Find Resources' screen is displayed
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Provide search criteria as RS in the search filter and click on
			 * 'Search' Resources RS1 and RS2 are retrieved and displayed
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navToFindResourcesPageInEditSTChangeNotifPref(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResourceVerify = { strResource1, strResource2 };
				strFuncResult = objPreferences.vrfyRSandAddRS(selenium,
						strResourceVerify, strResourceVerify, "", true, true,
						false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifySTInSectionInEditMySTPrfPage(selenium,
								strSectionValue, strSection1, strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='mainContainer']/center[1]/h2";
				String strText = "Editing 1 of 2 selected records";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("'Editing 1 of 2 selected records' page is displayed ");

				String strElementID = "css=#r_name";
				String strText = strResrctTypName + "�" + strResource1;
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.savAndNavigate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='mainContainer']/center[1]/h2";
				String strText = "Editing 2 of 2 selected records";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("'Editing 2 of 2 selected records' page is displayed ");

				String strElementID = "css=#r_name";
				String strText = strResrctTypName + "�" + strResource2;
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArStatType = { statStatusTypeName };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Set status change notification preference
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
								strResource1, strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource1, strRSValue[0], strSTvalue[0],
								statrNumTypeName, true, true, true);
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
								strResource1, strRSValue[0], strSTvalue[0],
								statrNumTypeName, true, true, true);
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
								strSTvalue[0], true, true);
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
								strResource1, strRSValue[0], strSTvalue[1],
								statrSaturatTypeName, true, true, true);
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
								strResource1, strRSValue[0], strSTvalue[1],
								statrSaturatTypeName, true, true, true);
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
								strSaturAbove, strSaturBelow, strRSValue[0],
								strSTvalue[1], true, true);
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
								strResource1, strRSValue[0], strSTvalue[2],
								statrMultiTypeName, strStatusValue[0], true,
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
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource1, strRSValue[0], strSTvalue[2],
								statrMultiTypeName, strStatusValue[1], true,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// For Resource 2

			// Set status change notification preference
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
								strResource2, strRSValue[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource2, strRSValue[1], strSTvalue[3],
								statStatusTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource2, strRSValue[1], strSTvalue[3],
								statStatusTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[1],
								strSTvalue[3], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 10 From a user who has update status right on resource RS1,
			 * change the status values of NST, MST and SST User U1 receives
			 * notifications appropriately for the change of status values
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
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_Update, strInitPwd);
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
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", strSTvalue[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource1);
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrNumTypeName + " status from 0 to 101.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrNumTypeName + " status from 0 to 101.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(80000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrNumTypeName + " from 0 to 101; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";

				strMsgBodyPager = statrNumTypeName + " from 0 to 101; "
						+ "Reasons: \nSummary at " + strCurrDate2 + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName_1
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName
						+ " status from 0 "
						+ "to 101.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				strMsgBodyEmail = "Status Update for "
						+ strUsrFulName_1
						+ ": "
						+ "\nOn "
						+ strCurrDate2
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName
						+ " status from 0 "
						+ "to 101.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)
								|| strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)
								|| strMsg.equals(strMsgBodyPager)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)
								|| strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)
								|| strMsg.equals(strMsgBodyPager)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)
								|| strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)
								|| strMsg.equals(strMsgBodyPager)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes == 2 && intPagerRes == 1) {
						intResCnt++;
					}

					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();

					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
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
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_Update,
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
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[1]);
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource1);
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrSaturatTypeName + " status from 0 to 429.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrSaturatTypeName + " status from 0 to 429.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrSaturatTypeName + " from 0 to 429; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";

				strMsgBodyPager = statrSaturatTypeName + " from 0 to 429; "
						+ "Reasons: \nSummary at " + strCurrDate2 + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName_1
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrSaturatTypeName
						+ " status from 0 "
						+ "to 429.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrSaturatTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				strMsgBodyEmail = "Status Update for "
						+ strUsrFulName_1
						+ ": "
						+ "\nOn "
						+ strCurrDate2
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrSaturatTypeName
						+ " status from 0 "
						+ "to 429.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrSaturatTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				Writer output = null;
				String text = strMsgBody1;
				File file = new File(
						"C:\\Documents and Settings\\All Users\\Desktop\\Input1.txt");
				output = new BufferedWriter(new FileWriter(file));
				output.write(text);
				output.close();

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)
								|| strMsg.equals(strMsgBodyEmail)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)
								|| strMsg.equals(strMsgBodyPager)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						output = null;
						text = strMsg;
						file = new File(
								"C:\\Documents and Settings\\All Users\\Desktop\\Mail1.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1)
								|| strMsg.equals(strMsgBodyEmail)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)
								|| strMsg.equals(strMsgBodyPager)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						output = null;
						text = strMsg;
						file = new File(
								"C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1)
								|| strMsg.equals(strMsgBodyEmail)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)
								|| strMsg.equals(strMsgBodyPager)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes_1 == 2 && intPagerRes_1 == 1) {
						intResCnt++;
					}
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();

					selenium.selectWindow("");
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
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
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_Update,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
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
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strSTvalue[2], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource1);
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrMultiTypeName + " status from -- to "
						+ strStatusName2 + ".";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrMultiTypeName + " status from -- to "
						+ strStatusName2 + ".";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrMultiTypeName + " from -- to "
						+ strStatusName2 + "; " + "Reasons: \nSummary at "
						+ strCurrDate + " \n" + "Region: " + strRegn + "";

				strMsgBodyPager = statrMultiTypeName + " from -- to "
						+ strStatusName2 + "; " + "Reasons: \nSummary at "
						+ strCurrDate2 + " \n" + "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName_1
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
						+ strStatusName2
						+ ".\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrMultiTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				strMsgBodyEmail = "Status Update for "
						+ strUsrFulName_1
						+ ": "
						+ "\nOn "
						+ strCurrDate2
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrMultiTypeName
						+ " status from -- "
						+ "to "
						+ strStatusName2
						+ ".\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrMultiTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)
								|| strMsg.equals(strMsgBodyEmail)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)
								|| strMsg.equals(strMsgBodyPager)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)
								|| strMsg.equals(strMsgBodyEmail)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)
								|| strMsg.equals(strMsgBodyPager)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)
								|| strMsg.equals(strMsgBodyEmail)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)
								|| strMsg.equals(strMsgBodyPager)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();
					// check Email, pager notification
					if (intEMailRes_2 == 2 && intPagerRes_2 == 1) {
						intResCnt++;
					}

					selenium.selectWindow("");
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				if (intResCnt == 6) {
					gstrResult = "PASS";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "105310";
			gstrTO = "Add a status type to resource RS and verify that the logged in user can"
					+ " set status change notification preferences for the newly added status "
					+ "type for only resource RS.";
			gstrResult = "FAIL";
			gstrReason = "";

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
	//start//testFTS124552//
	/***************************************************************
	'Description		:Verify that multi status types associated with same standard status type cannot be associated with a single resource.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:8/12/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS124552() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		

		try {
			gstrTCID = "124552"; // Test Case Id
			gstrTO = " Verify that multi status types associated with same standard status type cannot be associated with a single resource.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			
			String strMSTValue = "Multi";
			String strMultiStatType1 = "MST1" + strTimeText;
			String strMultiStatType2 = "MST2" + strTimeText;
			String strStatTypDefn = "Auto";

			String strStandardST = "Contact: Title";
			String strSTValues[] = new String[4];

			String strSTvalue[] = new String[2];

			String strStatusName1 = "ST1" + strTimeText;
			String strStatusName2 = "ST2" + strTimeText;
			String strStatTypeColor = "Black";

			String str_sharedStatusValue[] = new String[2];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";

			String strNSTValue = "Number";
			String statrNumTypeName = "NST" + strTimeText;
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRTvalue = new String[1];

			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strResVal[] = new String[2];
			/*
			 * STEP : Action:1. Multi Status types MST1,MST2 are created
			 * selecting same standard status types. <br>2. Resource RS is
			 * created under RT (created selecting different status types)
			 * <br>3. Add status types MST1 and MST2 to resource RS at 'Resource
			 * Level' Expected Result:Resource is not saved and an appropriate
			 * error message should be displayed.
			 */
			// 660688

			try {
				assertEquals("", strFuncResult);
				objLogin.login(selenium, strLoginUserName, strLoginPassword);
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
						strNSTValue, statrNumTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTValues[0] = objST.fetchSTValueInStatTypeList(selenium,
						statrNumTypeName);

				if (strSTValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create MST1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMSTValue, strMultiStatType1, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStandardSTInCreateST(selenium,
						strStandardST);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);

				selenium.click("link=Return to Status Type List");
				selenium.waitForPageToLoad(gstrTimeOut);

				strSTvalue[0] = objST.fetchSTValueInStatTypeList(selenium,
						strMultiStatType1);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMultiStatType1, strStatusName1, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create MST2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMSTValue, strMultiStatType2, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStandardSTInCreateST(selenium,
						strStandardST);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);

				selenium.click("link=Return to Status Type List");
				selenium.waitForPageToLoad(gstrTimeOut);
				strSTvalue[1] = objST.fetchSTValueInStatTypeList(selenium,
						strMultiStatType2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMultiStatType2, strStatusName2, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(selenium,
						strResrcTypName, strSTValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[0] = objRT.fetchResTypeValueInResTypeList(selenium,
						strResrcTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Resource RS

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrcTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal[0] = objRs.fetchResValueInResList(selenium,
						strResource);

				if (strResVal[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.savandVerifyErrorMsgForMultiResource(selenium);
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
			gstrTCID = "FTS-124552";
			gstrTO = "Verify that multi status types associated with same standard status type cannot be associated with a single resource.";
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

		// end//testFTS124552//
	}
	
	//start//testFTS124553//
	/***************************************************************
	'Description		:Verify that text status types associated with same standard status type cannot be associated with a single resource.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:8/13/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS124553() throws Exception {
		
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		
		try{	
		gstrTCID = "124553";	//Test Case Id	
		gstrTO = " Verify that text status types associated with same standard status type cannot be associated with a single resource.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

		log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
		Date_Time_settings dts = new Date_Time_settings();
		String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

		String strLoginUserName = rdExcel.readData("Login", 3, 1);
		String strLoginPassword = rdExcel.readData("Login", 3, 2);
		String strRegn = rdExcel.readData("Login", 3, 4);

		String strNSTValue = "Number";
		String statrNumTypeName = "NST" + strTimeText;
		String strResrcTypName = "AutoRt1_" + strTimeText;
		String[] strRTvalue = new String[1];
		String strStatTypDefn = "Auto";
		String strSTValues[] = new String[4];
		
		String strTSTValue = "Text";
		String strTxtStatType1 = "AutoTSt_1" + strTimeText;
		String strStandardST = "Account Type";
		String[] strTSTValues=new String[2];
		
		String strTxtStatType2 = "AutoTSt_2" + strTimeText;
		String strStandResType = "Aeromedical";
		String strContFName = "auto";
		String strContLName = "qsg";
		String strResource = "AutoRS_" + strTimeText;
		String strAbbrv = "abb";
		String strResVal[] = new String[2];
		/*
		* STEP :
		  Action:1. Text status types TST1,TST2 are created selecting same standard status types.
	<br>2. Resource RS is created under RT (created selecting different status types)
	<br>3. Add status types TST1 and TST2 to resource RS at 'Resource Level'
		  Expected Result:Resource should not be saved and an appropriate error message should be displayed.
		*/
		//660690



		try {
			assertEquals("", strFuncResult);
			objLogin.login(selenium, strLoginUserName, strLoginPassword);
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
					strNSTValue, statrNumTypeName, strStatTypDefn, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);

			strSTValues[0] = objST.fetchSTValueInStatTypeList(selenium,
					statrNumTypeName);

			if (strSTValues[0].compareTo("") != 0) {
				strFuncResult = "";
			} else {
				strFuncResult = "Failed to fetch Status tpe value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		//Creating TST1
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objST.navStatusTypList(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objST.selectStatusTypesAndFilMandFlds(
					selenium, strTSTValue, strTxtStatType1,
					strStatTypDefn, false);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objST.selectStandardSTInCreateST(selenium,
					strStandardST);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objST.savAndVerifySTNew(selenium,
					strTxtStatType1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);

			strTSTValues[0] = objST.fetchSTValueInStatTypeList(selenium,
					strTxtStatType1);

			if (strTSTValues[0].compareTo("") != 0) {
				strFuncResult = "";
			} else {
				strFuncResult = "Failed to fetch Status tpe value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		//Creating TST2
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objST.selectStatusTypesAndFilMandFlds(
					selenium, strTSTValue, strTxtStatType2,
					strStatTypDefn, false);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objST.selectStandardSTInCreateST(selenium,
					strStandardST);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objST.savAndVerifySTNew(selenium,
					strTxtStatType2);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);

			strTSTValues[1] = objST.fetchSTValueInStatTypeList(selenium,
					strTxtStatType2);

			if (strTSTValues[1].compareTo("") != 0) {
				strFuncResult = "";
			} else {
				strFuncResult = "Failed to fetch Status tpe value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		// Create RT
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT.navResourceTypList(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT.resrcTypeMandatoryFldsNew(selenium,
					strResrcTypName, strSTValues[0]);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT.saveAndvrfyResType(selenium,
					strResrcTypName);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strRTvalue[0] = objRT.fetchResTypeValueInResTypeList(selenium,
					strResrcTypName);
			if (strRTvalue[0].compareTo("") != 0) {
				strFuncResult = "";
			} else {
				strFuncResult = "Failed to fetch status value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		// Resource RS

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRs.navResourcesList(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String strState = "Alabama";
			String strCountry = "Barbour County";
			strFuncResult = objRs.createResourceWitLookUPadres(selenium,
					strResource, strAbbrv, strResrcTypName, strContFName,
					strContLName, strState, strCountry, strStandResType);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strResVal[0] = objRs.fetchResValueInResList(selenium,
					strResource);

			if (strResVal[0].compareTo("") != 0) {
				strFuncResult = "";

			} else {
				strFuncResult = "Failed to fetch Resource value";
			}

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRs.navToEditResLevelSTPage(selenium,
					strResource);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		for (int intST = 0; intST < strTSTValues.length; intST++) {
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
						strTSTValues[intST], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT
					.savandVerifyErrorMsgForTextResource(selenium);
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
		gstrTCID = "FTS-124553";
		gstrTO = "Verify that text status types associated with same standard status type cannot be associated with a single resource.";
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

	//end//testFTS124553//
	//start//testFTS40195//
	/***************************************************************
	'Description		:Add status types ST2 and ST3 to resource RS1 at resource level under resource type RT1, where:
	1. ST1 is associated with resource type RT1.
	2. RS3 is created under resource type RT1.
	3. ST2 is associated with resource type RT2.
	4. RS2 is created under resource type RT2.
	5. View V1 is created selecting RT1, RT2, ST1, ST2 and ST3
	Change RS1's resource type and verify that changes are reflected on view V1
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:8/16/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS40195() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objResources = new Resources();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();

		try {
			gstrTCID = "40195"; // Test Case Id
			gstrTO = " Add status types ST2 and ST3 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource type RT1."
					+ "2. RS3 is created under resource type RT1."
					+ "3. ST2 is associated with resource type RT2."
					+ "4. RS2 is created under resource type RT2."
					+ "5. View V1 is created selecting RT1, RT2, ST1, ST2 and ST3"
					+ "	Change RS1's resource type and verify that changes are reflected on view V1";// Test
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

			String strStatType1 = "AutoSt1_" + strTimeText;
			String strStatType2 = "AutoSt2_" + strTimeText;
			String strStatType3 = "AutoSt3_" + strTimeText;
			String strNumStatTypeValue = "Number";
			String strTxtStatTypDefn = "Auto";
			String[] strST = new String[3];
			String[] strRT = new String[3];
			String strResrcTypName1 = "AutoRt1" + strTimeText;
			String strResrcTypName2 = "AutoRt2" + strTimeText;

			String strResource1 = "AutoRs1" + strTimeText;
			String strResource2 = "AutoRs2" + strTimeText;
			String strResource3 = "AutoRs3" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";
			String strRSValue[] = new String[3];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strViewName_1 = "autoView_1" + strTimeText;
			String strViewType = "";

			/*
			 * STEP : Action:Preconditions: <br> <br>1. Resource type RT1 is
			 * associated with status type ST1. <br>2. Resource type RT2 is
			 * associated with status type ST1 and ST2. <br>3. Resource RS1 and
			 * RS3 are created under resource type RT1. <br>4. Resource RS2 is
			 * created under resource type RT2. <br>5. View V1 is created,
			 * selecting status types ST1, ST2, ST3 and resource types RT1 and
			 * RT2 (master check box) Expected Result:No Expected Result
			 */
			// 235026

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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

			// Status Types
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNumStatTypeValue,
						strStatType1, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNumStatTypeValue,
						strStatType2, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST3

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNumStatTypeValue,
						strStatType3, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType3);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName1, strST[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
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
				strRT[0] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName1);

				if (strRT[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrcTypName2,
						"css=input[name='statusTypeID'][value='" + strST[0]
								+ "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strST[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRT[1] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName2);

				if (strRT[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create RS1

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

			// Create RS2
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrcTypName2, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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

			// Create RS3
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource3, strAbbrv,
						strResrcTypName1, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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

				String strVewDescription = "Automation";
				strViewType = "Summary Plus (Resources as rows."
						+ " Status types and comments as columns.)";
				String strSTValues[] = { strST[0], strST[1], strST[2] };
				String strRSValues[] = {};
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName_1, strVewDescription, strViewType, true,
						false, strSTValues, false, strRSValues, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRT[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRT[1], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~PRE-CONDITION" + gstrTCID
							+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE" + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin, Navigate to Setup >> Resources
			 * screen, click on 'Edit Status Types' link corresponding to
			 * resource RS1. Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 235027

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.varFieldsInResListPage(selenium,
						strResource1);
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

			/*
			 * STEP : Action:Select check box corresponding to ST2, ST3 and save
			 * the page Expected Result:User is returned to the 'Resource List'
			 * screen
			 */
			// 235028

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						selenium, strST[0], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						selenium, strST[1], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						selenium, strST[2], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavRSList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1, ST2 and ST3. Resource RS3is displayed under resource type
			 * RT1 along with status type ST1. Resource RS2 is displayed under
			 * resource type RT2 along with status types ST1 and ST2.
			 */
			// 235029

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { strStatType1, strStatType2,
						strStatType3 };
				String[] strResources = { strResource1, strResource3 };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrcTypName1, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { strStatType1, strStatType2 };
				String[] strResources = { strResource2 };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrcTypName2, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Resource, change the resource
			 * type RT1 to RT2 of resource RS1 and save Expected
			 * Result:'Resources List' page is displayed.
			 */
			// 235030

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResource_FillAllFields(selenium,
						strResource1, strAbbrv, strResrcTypName2,
						strStandResType, false, false, "", "", false, "", "",
						"Alabama", "", "Autauga County", "", "FN", "LN", "",
						"", "", "", "", "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavRSList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT2 along with status types
			 * ST2 and ST3. Resource RS2 is displayed under resource type RT2
			 * along with status type ST1 and ST2 Resource RS3 is displayed
			 * under resource type RT1 along with status type ST1
			 */
			// 235031

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { strStatType1, strStatType2,
						strStatType3 };
				String[] strResources = { strResource1, strResource2 };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrcTypName2, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTAssoOrNotInViewScreenNew(
						selenium, strStatType3, false, strResource2, 3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { strStatType1 };
				String[] strResources = { strResource3 };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrcTypName1, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Resource, change the resource
			 * type RT2 to RT1 of resource RS1 and save Expected
			 * Result:'Resources List' page is displayed.
			 */
			// 235032

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResource_FillAllFields(selenium,
						strResource1, strAbbrv, strResrcTypName1,
						strStandResType, false, false, "", "", false, "", "",
						"Alabama", "", "Autauga County", "", "FN", "LN", "",
						"", "", "", "", "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavRSList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1, ST2 and ST3. Resource RS3 is displayed under resource type
			 * RT1 along with status type ST1. Resource RS2 is displayed under
			 * resource type RT2 along with status type ST1 and ST2.
			 */
			// 235033

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { strStatType1, strStatType2,
						strStatType3 };
				String[] strResources = { strResource1, strResource3 };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrcTypName1, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { strStatType1, strStatType2 };
				String[] strResources = { strResource2 };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrcTypName2, strResources, strStatType);
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
			gstrTCID = "FTS-40195";
			gstrTO = " Add status types ST2 and ST3 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource type RT1."
					+ "2. RS3 is created under resource type RT1."
					+ "3. ST2 is associated with resource type RT2."
					+ "4. RS2 is created under resource type RT2."
					+ "5. View V1 is created selecting RT1, RT2, ST1, ST2 and ST3"
					+ "	Change RS1's resource type and verify that changes are reflected on view V1";
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
	
	//start//testFTS124548//
	/***************************************************************************************************************************
	'Description: Add status type ST2 to resource RS1 at resource level under resource type RT1, where:
		          1. ST1 is associated with resource types RT1 and RT2
				  2. RS2 is created under resource type RT2
				  3. Custom View CV1 is created selecting RS1, ST1 and ST2
		          Change the resource RS1's resource type and verify that the changes are reflected on Custom view screen of CV1.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:8/19/2013
	'Author			:QSG
	'---------------------------------------------------------------------------------------------------------------------------
	'Modified Date				                          Modified By
	'Date					                              Name
	****************************************************************************************************************************/

	@Test
	public void testFTS124548() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Views objViews = new Views();

		try {
			gstrTCID = "124548"; // Test Case Id
			gstrTO = " Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. RS2 is created under resource type RT2"
					+ "3. Custom View CV1 is created selecting RS1, ST1 and ST2"
					+ "Change the resource RS1's resource type and verify that the changes are reflected on Custom view screen of CV1.";// Test
																																		// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoSt1_" + strTimeText;
			String strStatType2 = "AutoSt2_" + strTimeText;
			String strNumStatTypeValue = "Number";
			String strTxtStatTypDefn = "Auto";
			String[] strST = new String[3];
			String[] strRT = new String[3];
			String strResrcTypName1 = "AutoRt1" + strTimeText;
			String strResrcTypName2 = "AutoRt2" + strTimeText;

			String strResource1 = "AutoRs1" + strTimeText;
			String strResource2 = "AutoRs2" + strTimeText;

			String strAbbrv = "AB";
			String strResVal = "";
			String strRSValue[] = new String[3];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Role
			String strRolesName = "AutoRol_1" + strTimeText;
			String strRoleValue = "";
			// user
			String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 = "FN" + strUserName1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strCategory = "(Any)";
			String strCityZipCd = "";
			String strRegion = "(Any)";
			String strState = "Alabama";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType[] = { strStatType1, strStatType2 };
			/*
			 * STEP : Action:Preconditions: <br> <br>1. Resource type RT1 is
			 * associated with status type ST1. <br>2. Resource type RT2 is
			 * associated with status type ST1. <br>3. Resource RS1 is created
			 * under resource type RT1. <br>4. Resource RS2 is created under
			 * resource type RT2. <br>5. Create user U1 with the following
			 * rights, <br>a. Setup Resource type <br>b. Setup Resource <br>c.
			 * View Custom view Expected Result:No Expected Result
			 */
			// 660649

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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

			// Status Types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNumStatTypeValue,
						strStatType1, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNumStatTypeValue,
						strStatType2, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName1, strST[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
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
				strRT[0] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName1);

				if (strRT[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName2, strST[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRT[1] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName2);

				if (strRT[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create RS1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCountry = "Barbour County";
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

			// Create RS2
			try {
				assertEquals("", strFuncResult);

				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrcTypName2, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
				strFuncResult = objRoles.CreateRoleWithAllFields(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, false, updateRightValue, false,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName);
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
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName1);
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
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
				blnLogin = true;
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumPrecondition, strArStatType, strSection1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
						strSection1);
				if (strSectionValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~~~~~ PRE-CONDITION OF " + gstrTCID
					+ " ENDS ~~~~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as user U1, Navigate to Setup >> Resources
			 * screen, click on 'Edit Status Types' link corresponding to
			 * resource RS1. Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			// 660650

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
				strFuncResult = objRs.navToEditResLevelSTPage_LinkChanged(
						selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select check box corresponding to ST2 and save the
			 * page Expected Result:User is returned to the 'Resource List'
			 * screen
			 */
			// 660651

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,
						strST[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Preferences >> Customized View Expected
			 * Result:'Edit Custom View' screen is displayed.
			 */
			// 660652

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on button 'Add more Resources' Expected
			 * Result:'Find Resources' screen is displayed.
			 */
			// 660653

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navToFindResPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Enter resource RS1 in the 'Name' field and click on
			 * 'Search' Expected Result:Resource RS1 is listed on the 'Find
			 * Resource' page
			 */
			// 660654

			try {
				assertEquals("", strFuncResult);
				String[] strResourc = { strResource1 };
				strFuncResult = objPreferences.findResources(selenium,
						strResourc, strCategory, strRegion, strCityZipCd,
						strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.AddResourceToCustomView(
						selenium, strResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select check box associated with resource RS1 and
			 * click on button 'Add to custom View' Expected Result:'Edit Custom
			 * View' screen is displayed. Resource RS1 is displayed along with
			 * resource type RT1 under the section (created as the name given to
			 * the resource type)
			 */
			// 660655

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.verRSAndRTInEditCustomViewPage(
						selenium, strResource1, strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on button 'Option' Expected Result:'Edit
			 * Custom View Options (Columns)'screen is displayed. Status types
			 * ST1 and ST2 are displayed in the appropriate sections
			 */
			// 660656

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { strStatType1, strStatType2 };
				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select the check boxes ST1 and ST2, select among
			 * the four check boxes accordingly and click on Save Expected
			 * Result:'Custom View-Table' screen is displayed Resource RS1 is
			 * displayed under resource type RT1 along with status types ST1 and
			 * ST2
			 */
			// 660657

			try {
				assertEquals("", strFuncResult);
				String[][] strSTValue = { { strST[0], "true" },
						{ strST[1], "true" } };
				strFuncResult = objPreferences.addSTInEditCustViewOptionPage(
						selenium, strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String[] strResource = { strResource1 };
				String[] strStatType = { strStatType1, strStatType2 };
				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrcTypName1, strResource, strStatType);
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
			 * STEP : Action:Login as RegAdmin, Navigate to Setup >> Resource,
			 * click on the edit link associated with resource RS1, change the
			 * resource type RT1 to RT2 and save Expected Result:'Resources
			 * List' page is displayed.
			 */
			// 660658

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
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(selenium,
						strResource1, strAbbrv, strResrcTypName2,
						strStandResType, strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.savAndVerifyEditRSLevelPage(selenium);
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
			 * STEP : Action:Login as user U1, Navigate to Views >> Custom
			 * Expected Result:Resource RS1 is displayed under Resource type RT1
			 * along with Status type ST1 and ST2
			 */
			// 660659

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navViewCustomTable(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResource = { strResource1 };
				String[] strStatType = { strStatType1, strStatType2 };
				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrcTypName1, strResource, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Preferences >> Customized View Expected
			 * Result:Section name (Resource Type name) is retained as the name
			 * of resource type 1. Resource name RS1 is displayed with the
			 * resource type RT2
			 */
			// 660660

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verRSAndRTInEditCustomViewPageNew(selenium,
								strResource1, strResrcTypName1,
								strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on button 'Option' Expected Result:'Edit
			 * Custom View Options (Columns)'screen is displayed. Status type
			 * ST1 and ST2 are displayed in the appropriate section
			 */
			// 660661

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as RegAdmin, Navigate to Setup >> Resource,
			 * click on the edit link associated with resource RS1, change the
			 * resource type RT2 to RT1 and save Expected Result:'Resources
			 * List' page is displayed.
			 */
			// 660662

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
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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
				strFuncResult = objRs.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(selenium,
						strResource1, strAbbrv, strResrcTypName1,
						strStandResType, strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as User U1, Navigate to Views >> Custom
			 * Expected Result:'Custom View-Table' screen is displayed Resource
			 * RS1 is displayed under resource type RT1 along with status types
			 * ST1 and ST2
			 */
			// 660663

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navViewCustomTable(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResource = { strResource1 };
				String[] strStatType = { strStatType1, strStatType2 };
				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrcTypName1, strResource, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Preferences >> Customized View Expected
			 * Result:Resource name RS1 is displayed with the resource type RT1
			 */
			// 660664

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.verRSAndRTInEditCustomViewPage(
						selenium, strResource1, strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on button 'Option' Expected Result:'Edit
			 * Custom View Options (Columns)'screen is displayed. Status types
			 * ST1 and ST2 are displayed in the appropriate sections
			 */
			// 660665

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strArStatType);
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
			gstrTCID = "FTS-124548";
			gstrTO = " Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. RS2 is created under resource type RT2"
					+ "3. Custom View CV1 is created selecting RS1, ST1 and ST2"
					+ "Change the resource RS1's resource type and verify that the changes are reflected on Custom view screen of CV1.";
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

	// end//testFTS124548//
	//start//testFTS124549//
	/***************************************************************
	'Description		:Add status type ST2 to resource RS1 at resource level under resource type RT1, where: 
	<br/> <br/>
	1. ST1 is associated with resource types RT1 and RT2 
	<br/>
	2. RS2 is created under resource type RT2
	<br/> 
	3. Custom View V1 is created selecting RS1, RS2, status types ST1, ST2 under one section.
	<br/> <br/>
	Change the resource RS1's resource type and verify that the changes are reflected on the custom view screen of V1.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:8/19/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS126283() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Views objViews = new Views();

		try {
			gstrTCID = "126283"; // Test Case Id
			gstrTO = " Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. RS2 is created under resource type RT2"
					+ "3. Custom View V1 is created selecting RS1, RS2, status types ST1, ST2 under one section."
					+ "Change the resource RS1's resource type and verify that the changes are reflected on the custom view screen of V1.";// Test
																																			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoSt1_" + strTimeText;
			String strStatType2 = "AutoSt2_" + strTimeText;
			String strNumStatTypeValue = "Number";
			String strTxtStatTypDefn = "Auto";
			String[] strST = new String[3];
			String[] strRT = new String[3];
			String strResrcTypName1 = "AutoRt1" + strTimeText;
			String strResrcTypName2 = "AutoRt2" + strTimeText;

			String strResource1 = "AutoRs1" + strTimeText;
			String strResource2 = "AutoRs2" + strTimeText;

			String strAbbrv = "AB";
			String strResVal = "";
			String strRSValue[] = new String[3];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Role
			String strRolesName = "AutoRol_1" + strTimeText;
			String strRoleValue = "";
			// user
			String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 = "FN" + strUserName1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strCategory = "(Any)";
			String strCityZipCd = "";
			String strRegion = "(Any)";
			String strState = "Alabama";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType[] = { strStatType1, strStatType2 };

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION STARTS~~~~~");
		/*
		* STEP :
		  Action:Preconditions:
		1. Resource type RT1 is associated with status type ST1.
		2. Resource type RT2 is associated with status type ST1.
		3. Resource RS1 is created under resource type RT1.
		4. Resource RS2 is created under resource type RT2.
		5. Create user U1 with the following rights,
		a. Setup Resource type
		b. Setup Resource
		c. View Custom view
		  Expected Result:No Expected Result
		*/
		//660666
		
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

			// Status Types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNumStatTypeValue,
						strStatType1, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNumStatTypeValue,
						strStatType2, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName1, strST[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
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
				strRT[0] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName1);
				if (strRT[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		// RT2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName2, strST[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRT[1] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName2);
				if (strRT[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create RS1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCountry = "Barbour County";
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

			// Create RS2
			try {
				assertEquals("", strFuncResult);
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrcTypName2, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
				strFuncResult = objRoles.CreateRoleWithAllFields(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, false, updateRightValue, false,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName);
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
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName1);
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
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
				blnLogin = true;
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumPrecondition, strArStatType, strSection1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
						strSection1);
				if (strSectionValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		log4j.info("~~~~~~~~~ PRE-CONDITION OF " + gstrTCID
				+ " ENDS ~~~~~~~~");
		log4j.info("~~~~~TEST CASE - " + gstrTCID
				+ " EXECUTION STARTS~~~~~");

		/*
		* STEP :
		  Action:Login as user U1, Navigate to Setup >> Resources screen, click on 'Edit Status Types'
		   link corresponding to resource RS1. 
		  Expected Result:'Edit Resource-Level Status Types' screen is displayed. 
		*/
		//660667

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
				strFuncResult = objRs.navToEditResLevelSTPage_LinkChanged(
						selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Select check box corresponding to ST2 and save the page
		  Expected Result:User is returned to the 'Resource List' screen
		*/
		//660668

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,
						strST[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		/*
		* STEP :
		  Action:Navigate to Preferences >> Customized View
		  Expected Result:'Edit Custom View' screen is displayed.
		*/
		//660669

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on button 'Add more Resources'
		  Expected Result:'Find Resources' screen is displayed.
		*/
		//660670

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navToFindResPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Enter resource RS in the 'Name' field and click on 'Search'
		  Expected Result:Resource RS1 and RS2 are listed on the 'Find Resource' page
		*/
		//660671
			
			try {
				assertEquals("", strFuncResult);
				String[] strResourc = { "AutoRs" };
				strFuncResult = objPreferences.findResources(selenium,
						strResourc, strCategory, strRegion, strCityZipCd,
						strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.AddResourceToCustomView(
						selenium, strResource1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Select check box associated with resources RS1, RS2 and click on button 'Add to custom View' 
		  Expected Result:Edit Custom View' screen is displayed.
	  	  Resource RS1 is displayed along with resource type RT1 under the section
	  	  (created as the name given to the resource type RT1)
	  	  Resource RS2 is displayed along with resource type RT2 under the section
	  	  (created as the name given to the resource type RT2) 
		*/
		//660672

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.AddResourceToCustomView(
						selenium, strResource2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.verRSAndRTInEditCustomViewPage(
						selenium, strResource1, strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.verRSAndRTInEditCustomViewPage(
						selenium, strResource2, strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Move Resource RS2 from second section to the first section which is created by the 
		  default resource type name RT1 
		  Expected Result:Resource RS1 with resource type RT1 is displayed under the first section. 
		  (section default name is taken as the resource type name RT1)
	  	  Resource RS2 with resource type RT2 is displayed under the first section. (section default
	  	   name is taken as the resource type name RT1) 
		*/
		//660673
		
			String strRsc = strResource2 + " [" + strResrcTypName2 + "]";
			String strResource[] = { strRsc };

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.dragAndDropRSFormOneRTToAnother(
						selenium, strResource, strResrcTypName2,
						strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		

		/*
		* STEP :
		  Action:Click on button 'Option'
		  Expected Result:'Edit Custom View Options (Columns)'screen is displayed.
				  Status types ST1 and ST2 are displayed in the appropriate sections
		*/
		//660674

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
				String[] strSTName = { strStatType1, strStatType2 };
				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		/*
		* STEP :
		  Action:Select the check boxes ST1 and ST2, select among the four check boxes accordingly 
		  and click on Save 
		  Expected Result:'Custom View-Table' screen is displayed
	  	  		Resources RS1 and RS2 are displayed under resource type RT1 along with status types ST1 and ST2
	  	  		For resource 'RS2' N/A is displayed under status column 'ST1' 
		*/
		//660675

			try {
				assertEquals("", strFuncResult);
				String[][] strSTValue = { { strST[0], "true" },
						{ strST[1], "true" } };
				strFuncResult = objPreferences.addSTInEditCustViewOptionPage(
						selenium, strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource1, strResource2 };
				String[] strStatType = { strStatType1, strStatType2 };
				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrcTypName1, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strStatType1 };
				strFuncResult = objPreferences.chkSTAssoOrNotInCustomViewTable(
						selenium, strResource1, strStatTypeArr, false,
						strRSValue[1], strRT[1]);
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
		  Action:From 'qsg' user,Navigate to Setup >> Resource, click on the edit link 
		  associated with resource RS1, change the resource type RT1 to RT2 and save 
		  Expected Result:	'Resources List' page is displayed. 
		*/
		//660676

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
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(selenium,
						strResource1, strAbbrv, strResrcTypName2,
						strStandResType, strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.savAndVerifyEditRSLevelPage(selenium);
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
		  Action:From user 'U1', Navigate to Views >> Custom 
		  Expected Result:Resource RS1 and RS2 are displayed under Resource type RT1 along with Status type ST2 
		*/
		//660677

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navViewCustomTable(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource1, strResource2 };
				String[] strStatType = { strStatType2 };
				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrcTypName1, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		/*
		* STEP :
		  Action:Navigate to Preferences >> Customized View
		  Expected Result:Section name (Resource Type name RT1) is retained.
	  	  		Resource name RS1 and RS2 are displayed with the resource type RT2 
		*/
		//660678

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verRSAndRTInEditCustomViewPageNew(selenium,
								strResource1, strResrcTypName1,
								strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verRSAndRTInEditCustomViewPageNew(selenium,
								strResource2, strResrcTypName1,
								strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on button 'Option'
		  Expected Result:'Edit Custom View Options (Columns)'screen is displayed.
	  	  		Status type ST2 is displayed in the appropriate section 
		*/
		//660679

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { strStatType2 };
				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:From 'qsg' user, Navigate to Setup >> Resource, click on the edit link associated with
		   resource RS1, change the resource type RT2 to RT1 and save 
		  Expected Result:'Resources List' page is displayed.
		*/
		//660680

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
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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
				strFuncResult = objRs.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(selenium,
						strResource1, strAbbrv, strResrcTypName1,
						strStandResType, strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:From user 'U1',Navigate to Views >> Custom
		  Expected Result:'Custom View-Table' screen is displayed
	  	  		Resources RS1 and RS2 are displayed under resource type RT1 along with status types ST1 and ST2
	  	  		For resource 'RS2' N/A is displayed under status column 'ST1' 
		*/
		//660681

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navViewCustomTable(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource1 };
				String[] strStatType = { strStatType1, strStatType2 };
				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrcTypName1, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strStatType1 };
				strFuncResult = objPreferences.chkSTAssoOrNotInCustomViewTable(
						selenium, strResource1, strStatTypeArr, false,
						strRSValue[1], strRT[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Preferences >> Customized View
		  Expected Result:Section name (Resource Type name RT1) is retained.
	  	  		Resource name RS1 is displayed with the resource type RT1
	  	  		Resource name RS2 is displayed with the resource type RT2 
		*/
		//660682

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.verRSAndRTInEditCustomViewPage(
						selenium, strResource1, strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verRSAndRTInEditCustomViewPageNew(selenium,
								strResource2, strResrcTypName1,
								strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		/*
		* STEP :
		  Action:Click on button 'Option'
		  Expected Result:'Edit Custom View Options (Columns)'screen is displayed.
				  Status types ST1 and ST2 are displayed in the appropriate sections
		*/
		//660683

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strArStatType);
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
			gstrTCID = "FTS-126283";
			gstrTO = " Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. RS2 is created under resource type RT2"
					+ "3. Custom View V1 is created selecting RS1, RS2, status types ST1, ST2 under one section."
					+ "Change the resource RS1's resource type and verify that the changes are reflected on the custom view screen of V1.";// Test
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

	//end//testFTS126283//

	//start//testFTS124551//
	/*************************************************************************************
	'Description		:Add a status type ST to resource RS and verify that status change 
	                     notification preferences can be set for a user with view right for
	                      status type ST and resource RS.
	'Arguments			:None
	'Returns			:None
	'Date				:9/12/2013
	'Author				:QSG
	'-------------------------------------------------------------------------------------
	'Modified Date				                                            Modified By
	'Date					                                                Name
	**************************************************************************************/

	@Test
	public void testFTS124551() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Preferences objPreferences = new Preferences();
		ViewMap objViewMap = new ViewMap();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();
		try {
			gstrTCID = "124551"; // Test Case Id
			gstrTO = " Add a status type ST to resource RS and verify that status change notification "
					+ "preferences can be set for a user with view right for status type ST and resource RS.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			// User
			String strUserName = "User" + System.currentTimeMillis();
			String strUsrFulName = "UserName";
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

			// ST
			String strStatusNumValue = "Number";
			String statTypeName1 = "Nst_1" + strTimeText;
			String statTypeName2 = "Nst_2" + strTimeText;
			String strStatTypDefn = "Def";
			String strSTvalue[] = new String[2];
			// RT
			String strResrcTypName1 = "Resty_1" + strTimeText;
			String strRTvalue[] = new String[1];

			// RS
			String strStandResType = "Aeromedical";
			String strResource1 = "Res_1" + strTimeText;
			String strAbbrv = "Abb";
			String strContFName = "FN";
			String strContLName = "LN";
			String strState = "Alabama";
			String strCounty = "Barbour County";
			String strRSValue[] = new String[1];
			// Role
			String strRoleName = "Rol_1" + strTimeText;
			String strRoleValue[] = new String[1];

			// Section
			String strSection = "Sec_1" + strTimeText;

			String strAbove = "10";
			String strBelow = "20";

			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			String strCurrDate = "";
			String strCurrDate2 = "";

			// Email
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strSubjName = "Change for";
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody2 = "";
			String strMsgBody1 = "";
			int intPagerRes = 0;
			int intEMailRes = 0;

		/*
		 * STEP : Action:1. Resource RS is created under RT(created
		 * selecting ST1) 2. Edit resource RS and add ST at resource level.
		 * 3. Add ST to section S1. 4. Provide user U1 right to edit status
		 * change notification preference. 5. From user U1 set status change
		 * notification preference for ST 6. Update the status of ST
		 * accordingly Expected Result:User receives status change
		 * notifications emails as set
		 */
		// 660686
		log4j.info("~~~~~PRECONDITION - " + gstrTCID
				+ " EXECUTION STARTS~~~~~");
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
					seleniumPrecondition, strResrcTypName1, strSTvalue[0]);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResourceTypes.saveAndvrfyResType(
					seleniumPrecondition, strResrcTypName1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strRTvalue[0] = objResourceTypes
					.fetchResTypeValueInResTypeList(seleniumPrecondition,
							strResrcTypName1);
			if (strRTvalue[0].compareTo("") != 0) {
				strFuncResult = "";
			} else {
				strFuncResult = "Failed to fetch resource type value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
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
					seleniumPrecondition, strResource1, strAbbrv,
					strResrcTypName1, strContFName, strContLName, strState,
					strCounty, strStandResType);

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
			strRSValue[0] = objResources.fetchResValueInResList(
					seleniumPrecondition, strResource1);
			if (strRSValue[0].compareTo("") != 0) {
				strFuncResult = "";
			} else {
				strFuncResult = "Failed to fetch resource type value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResources.navToEditResLevelSTPage(
					seleniumPrecondition, strResource1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
					seleniumPrecondition, strSTvalue[1], true);
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
			String[] updateRightValue = { strSTvalue[1] };
			String[][] strRoleRightss = {};
			strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
					seleniumPrecondition, strRoleName, strRoleRightss,
					updateRightValue, false, updateRightValue, true, false);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRoles.savAndVerifyRoles(
					seleniumPrecondition, strRoleName);
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
					seleniumPrecondition, strUserName, strInitPwd,
					strConfirmPwd, strUsrFulName);
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
			strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
					seleniumPrecondition, "", "", "", "", "", strEMail,
					strEMail, strEMail, "");

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
					seleniumPrecondition, strUserName, strByRole,
					strByResourceType, strByUserInfo, strNameFormat);
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
			strFuncResult = objViews
					.navToEditResDetailViewSections(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String[] strStatTypeName = { statTypeName2 };
			strFuncResult = objViews
					.dragAndDropSTtoSection(seleniumPrecondition,
							strStatTypeName, strSection, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

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

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
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
							strResource1, strRSValue[0]);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objPreferences
					.selectSTChangeNotifInEditMySTNotifPageNew(selenium,
							strResource1, strRSValue[0], strSTvalue[1],
							statTypeName2, true, true, true);
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
					.selectSTChangeNotifInEditMySTNotifPageBelowNew(
							selenium, strResource1, strRSValue[0],
							strSTvalue[1], statTypeName2, true, true, true);
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
							strSTvalue[1], true, true);
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
					strResource1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
					strResource1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
					"101", strSTvalue[1], "");
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
			strFuncResult = objViewMap.navResPopupWindow(selenium,
					strResource1);
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

			String strDesc1 = "On " + strCurrDate + " changed "
					+ statTypeName2 + " status from 0 to 101.";

			String strDesc2 = "On " + strCurrDate2 + " changed "
					+ statTypeName2 + " status from 0 to 101.";

			String[] strTime = strSTDateTime.split(" ");
			String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
					"HH:mm");

			log4j.info(strAddedDtTime);

			strAddedDtTime = strTime[0] + " " + strAddedDtTime;
			log4j.info(strAddedDtTime);

			strFuncResult = objEventNotification
					.ackSTWebNotificationCorrect(selenium, strResource1,
							strSTDateTime, strAddedDtTime, strDesc1,
							strDesc2);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);

			strMsgBody2 = statTypeName2 + " from 0 to 101; "
					+ "Reasons: \nSummary at " + strCurrDate + " \n"
					+ "Region: " + strRegn + "";

			strMsgBody1 = "Status Update for "
					+ strUsrFulName
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strResource1
					+ " "
					+ "changed "
					+ statTypeName2
					+ " status from 0 "
					+ "to 101.\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrcTypName1
					+ "s "
					+ "in the region report the following "
					+ statTypeName2
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";

			strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
					strLoginName, strPassword);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertTrue(strFuncResult.equals(""));
			strSubjName = "Status Change for " + strResource1;
			for (int i = 0; i < 2; i++) {
				strFuncResult = objMail.verifyEmail(selenium, strFrom,
						strTo, strSubjName);
				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = objMail.seleniumGetText(selenium,
							"css=div.fixed.leftAlign", 160);
					String strEmail[] = objMail.enodeToUnicode(selenium,
							strMsg, 160);
					strMsg = strEmail[1];
					if (strMsg.equals(strMsgBody1)) {
						intEMailRes++;
						log4j.info(strMsgBody1
								+ " is displayed for Email Notification");
					} else {
						log4j.info("Email body  is NOT displayed for Email Notification");
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
		try {
			assertEquals("", strFuncResult);
			strSubjName = "Change for " + strAbbrv;
			strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
					strSubjName);
			try {
				assertTrue(strFuncResult.equals(""));
				String strMsg = objMail.seleniumGetText(selenium,
						"css=div.fixed.leftAlign", 160);
				String strPager[] = objMail.enodeToUnicode(selenium,
						strMsg, 160);
				strMsg = strPager[1];
				if (strMsg.equals(strMsgBody2)) {
					intPagerRes++;
					log4j.info(strMsgBody2
							+ " is displayed for Pager Notification");
				} else {
					log4j.info("Pager body  is NOT displayed for Pager Notification");
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

		} catch (AssertionError Ae) {
			gstrReason = gstrReason + " " + strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			if (intEMailRes == 2 && intPagerRes == 1) {
				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad(gstrTimeOut);
				selenium.close();
				selenium.selectWindow("");
				Thread.sleep(2000);
				gstrResult = "PASS";
			}

		} catch (AssertionError Ae) {
			gstrResult = "FAIL";
			gstrReason = gstrReason + " " + strFuncResult;
		}

		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
		log4j.info("----------------------------------------------------------");

	} catch (Exception e) {
		gstrTCID = "FTS-124551";
		gstrTO = "Add a status type ST to resource RS and verify that status change notification preferences can be set for a user with view right for status type ST and resource RS.";
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

// end//testFTS124551//

}