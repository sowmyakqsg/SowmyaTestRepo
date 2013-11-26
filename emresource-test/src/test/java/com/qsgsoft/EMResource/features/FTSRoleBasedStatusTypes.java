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

/*********************************************************************
' Description		:This class contains test cases from requirement
' Requirement		:Role Based Status types
' Requirement Group	:Setting up Status Types
� Product		    :EMResource v3.18
' Date			    :4/June/2012
' Author		    :QSG
' -----------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'**********************************************************************/

public class FTSRoleBasedStatusTypes {

	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSRoleBasedStatusTypes");
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

   /***********************************************************************************
	* This function is called the setup() function which is executed before every test.
	* The function will take care of creating a new selenium session for every test
	* 
    ***********************************************************************************/

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

		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,

				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}

	/********************************************************************************
	'Description	:Verify that a status type cannot be saved by selecting a role R1
	                 under 'Roles with update rights' section and without selecting
	                 the same role R1 under 'Roles with view rights' section.
	'Arguments		:None
	'Returns		:None
	'Date			:8/2/2012
	'Author			:QSG
	'--------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	**********************************************************************************/

	@Test
	public void testFTS44288() throws Exception {
		try {
			Login objLogin = new Login();
			Roles objRoles = new Roles();
			StatusTypes objStatusTypes = new StatusTypes();

			gstrTCID = "44288"; // Test Case Id
			gstrTO = " Verify that a status type cannot be saved by selecting a role R1 under 'Roles with " +
					"update rights' section and without selecting the same role R1 under 'Roles with" +
					" view rights' section.";//TO
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
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue[] = new String[1];

			// ST
			String statTypeName = "AutoSTn1" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			/*
			* STEP :
			  Action:Login as RegAdmin and Navigate to Setup >> Roles, create a role 'R1' by filling all the mandatory data.
			  Expected Result:Role 'R1' is listed in 'Roles List' screen.
			*/
			//258986
		log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
			
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
			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue ={};
				String[] strViewRightValue ={};
				String[][] strRoleRights={};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, true,
						updateRightValue, true, false);
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
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			* STEP :
			  Action:Navigate to Setup >> Status Type,and Click on 'Create New Status Types' button.
			  Expected Result:'Select Status Type' screen is displayed.
			*/
			//258961
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			* STEP :
			  Action:Select 'Number','Multi','Text' or 'Saturation' from the 'Select Type' drop-down list(If 'Number' is selected from 'Select Type' drop-down list) and then click on 'Next'
			  Expected Result:'Create Number Status Type' screen is displayed
			*/
			//258969			
			// ST1
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			* STEP :
			  Action:Create a status type 'ST' by filling all the mandatory data and select a role 'R1' 
			  under 'Roles with update rights' sections and not select role 'R1' under 'Roles with view rights', then click on 'Save'
			  Expected Result:'Each role that may update this status type must also be able to view it' 
			  warning message is displayed in 'Create Number Status Type' screen.
			*/
			//258977

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue={};
				String[][] strRoleUpdateValue={{strRoleValue[0],"true"}};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(selenium, false, false, strRoleViewValue, strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.varErrorMsgInCreateST(selenium);
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
			gstrTCID = "44288";
			gstrTO = "Verify that a status type cannot be saved by selecting a role R1 under 'Roles with update rights' section and without selecting the same role R1 under 'Roles with view rights' section.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
			+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		   }
		}
		
		
	/*********************************************************************************************
	'Description	:For a status type ST select role R1 under 'Roles with view rights' and 'Roles
	                with Update rights' sections and verify that status type ST is selected under 
	                'select the Status Types this Role may view:' and 'Select the Status Types this
	                 Role may update:' sections in the 'Edit Role' screen of R1.
	'Arguments		:None
	'Returns		:None
	'Date			:8/2/2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------------
	'Modified Date				                                                   Modified By
	'Date					                                                         Name
	***********************************************************************************************/

	@Test
	public void testFTS44385() throws Exception {
		try {

			Login objLogin = new Login();
			Roles objRoles = new Roles();
			StatusTypes objStatusTypes = new StatusTypes();

			gstrTCID = "44385"; // Test Case Id
			gstrTO = " For a status type ST select role R1 under 'Roles with view rights' and 'Roles with "
					+ "Update rights' sections and verify that status type ST is selected under 'select"
					+ " the Status Types this Role may view:' and 'Select the Status Types this Role"
					+ " may update:' sections in the 'Edit Role' screen of R1.";// TO
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
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue[] = new String[1];

			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName = "AutoSTn1" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";

			/*
			* STEP :
			  Action:Login as RegAdmin and Navigate to Setup >> Roles, create a role 'R1' by filling all the mandatory data.
			  Expected Result:Role 'R1' is listed in 'Roles List' screen.
			*/
			//259856
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION STARTS~~~~~");
			
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
			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue ={};
				String[] strViewRightValue ={};
				String[][] strRoleRights={};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, true,
						updateRightValue, true, false);
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
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName);

				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			* STEP :
			  Action:Navigate to Setup >> Status Type,and Click on 'Create New Status Types' button.
			  Expected Result:Select Status Type' screen is displayed.
			*/
			//259835
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			* STEP :
			  Action:Select 'Number','Multi','Text' or 'Saturation' from the 'Select Type' drop-down list(If 'Number' is selected from 'Select Type' drop-down list) and then click on 'Next'
			  Expected Result:'Create Number Status Type' screen is displayed '
			*/
			//259842
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			* STEP :
			  Action:Create a status type 'ST' by filling all the mandatory data and select a role 'R1' under both 'Roles with view rights' and 'Roles with update rights' sections, then click on 'Save.
			  Expected Result:Status type 'ST' is displayed in 'Status Type List' screen.
			*/
			//259855		
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue={{strRoleValue[0],"true"}};
				String[][] strRoleUpdateValue={{strRoleValue[0],"true"}};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(selenium, false, false, strRoleViewValue, strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName);
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			* STEP :
			  Action:Navigate to Setup >> Roles, click on 'Edit' link associated with role 'R1'.
			  Expected Result:Status type ST is selected under 'select the Status Types this Role may view' 
			  and 'Select the Status Types this Role may update' sections in the 'Edit Role' screen of R1.
			*/
			//259907
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navEditRolesPge(selenium, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTInEditRolePage(selenium, strStatusTypeValues[0], true, strStatusTypeValues[0], true, statTypeName, strRolesName, true);
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
			gstrTCID = "44385";
			gstrTO = "For a status type ST select role R1 under 'Roles with view rights' and 'Roles with Update rights' sections and verify that status type ST is selected under 'select the Status Types this Role may view:' and 'Select the Status Types this Role may update:' sections in the 'Edit Role' screen of R1.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
			+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		   }
		}


	/*********************************************************************************************
	'Description	:Create status type ST without selecting any roles (view/update), associate ST 
	                with resource RS at resource TYPE level and verify that ST is displayed on 
	                following screens for system admin:
					a. Region view
					b. Map (in status type dropdown & in resource pop up window)
					d. View Resource Detail
					e. Custom view
					f. Event details
					g. Mobile view
					h. While configuring form
					i. Edit My Status Change Preferences
					j. Create/Edit Interfaces
	'Date			:11/26/2012
	'Author			:QSG
	'-----------------------------------------------------------------------------------------------
	'Modified Date				                                                       Modified By
	'Date					                                                           Name
	*************************************************************************************************/

	@Test
	public void testFTS43693() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Preferences objPreferences = new Preferences();
		EventList objEventList = new EventList();
		try {
			gstrTCID = "43693"; // Test Case Id
			gstrTO = " Create status type ST without selecting any roles (view/update), associate ST with resource "
					+ "RS at resource TYPE level and verify that ST is displayed on following screens for system admin:"
					+ "a. Region view"
					+ "b. Map (in status type dropdown & in resource pop up window)"
					+ "d. View Resource Detail"
					+ "e. Custom view"
					+ "f. Event details"
					+ "g. Mobile view"
					+ "h. While configuring form"
					+ "i. Edit My Status Change Preferences"
					+ "j. Create/Edit Interfaces";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";

			// Shared
			String statNumTypeName = "NST" + strTimeText;
			String statMultiTypeName = "MST" + strTimeText;
			String str_statTypeValue[] = new String[2];

			String strMSTValue = "Multi";
			String strStatTypeColor = "Black";
			String str_MultiStatusName1 = "rSa" + strTimeText;
			String str_multiStatusValue[] = new String[2];
			str_multiStatusValue[0] = "";

			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];
			// RS
			String strResource = "AutoRs1_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];

			String strTempName = "Autotmp1" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			// sec data
			String strSection1 = "AB1_" + strTimeText;
			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

	/*
	* STEP :
	  Action:Precondition: 
		1.Resource 'RS1' is created with proving the address under resource Type 'RT1' selecting 'ST1'. 
		2.Event template 'ET1' is created selecting 'RT1' and 'ST1'.
		3.Event 'EV' is created under the template 'ET1' selecting 'RT1'.
	  Expected Result:No Expected Result
	*/
        log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

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
				String strNSTValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_statTypeValue[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statNumTypeName);

				if (str_statTypeValue[0].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrcTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_statTypeValue[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName);
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

			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { str_statTypeValue[0] };

				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef,
						strEveColor, true, strResTypeVal, strStatusTypeval,
						true, true);
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
	* STEP :
	  Action:Login as RegAdmin and Navigate to Setup >> Status Type,and Click on 'Create New Status Types' 
	  button Select 'Multi' from the 'Select Type' dropdown list and then click on 'Next'
	  Expected Result:'Create Multi Status Type' screen is displayed.
	*/
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			/*
	* STEP :
	  Action:Create a status type 'ST' by selecting a standard status type 'SST' and fill all the mandatory data and DO NOT select any role under 'Roles with view rights' and 'Roles with update rights' sections, then click on 'Save'
	  Expected Result:Status type 'ST' is displayed in 'Status Type List' screen.
	*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, statMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStandardST="Damage: Windows";
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(selenium, strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue={};
				String[][] strRoleViewValue={};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(selenium, true,
						true, strRoleViewValue, strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium, statMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_statTypeValue[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statMultiTypeName);
				if (str_statTypeValue[1].compareTo("") != 0) {
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
						selenium, statMultiTypeName, str_MultiStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_multiStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(selenium, statMultiTypeName,
								str_MultiStatusName1);
				if (str_multiStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Navigate to Setup >> Resource Type, and click on 'Edit' link associated with 'RT1'
	   and select 'ST' under 'Status Types' section and click on 'Save'.
	  Expected Result:'RT1' is diaplayed in 'Resource Type List' screen
	*/
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
						selenium, str_statTypeValue[1], true);
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
	* STEP :
	  Action:Navigate to Setup >> views, create a view 'V1' selecting 'ST' and resource 'RS1' and click on 'Save'
	  Expected Result:View 'V1' is diaplayed in 'Region Views List' screen.
	*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { str_statTypeValue[1] };
				String[] strRSValues = { strRSValue[0] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Click on 'Customize Resource Detail View' Screen, create a section 'Sec' then 
	  click on 'Uncategorized' section, Drag and drop the status type 'ST' to section 'Sec' and click on 'Save'
	  Expected Result:'Region Views List' screen is displayed.
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
				strFuncResult = objLogin.login(seleniumFirefox, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox, strRegn);
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
				String[] strStatTypeArr = { statMultiTypeName };
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
	* STEP :
	  Action:Navigate to View >> V1
	  Expected Result:Status type 'ST' is displayed with the resource 'RS1'
	*/
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
				String[] strStatType = { statMultiTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Click on Resource 'RS1'
	  Expected Result:Status type 'ST' is displayed under the section 'Sec' in 'View Resource Detail' section.
	*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statMultiTypeName };
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strStatTypep);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Navigate to View >> Map, select resource 'RS1' under 'Find Resource' dropdown
	  Expected Result:Status type 'ST' is displayed on 'resource pop up window' screen
	*/
			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statMultiTypeName };
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Click on 'Status Type (to color icons)' dropdown list
	  Expected Result:Status Type 'ST' is displayed in the dropdown list
	*/
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statMultiTypeName + "']"));

				log4j.info("status type " + statMultiTypeName
						+ " is displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statMultiTypeName
						+ " is NOT displayed in dropdown. ");
				strFuncResult = "status type " + statMultiTypeName
						+ " is NOT displayed in dropdown. ";
				gstrReason = strFuncResult;

			}
	/*
	* STEP :
	  Action:Navigate to Preferences >> Customized View,add resources 'RS1' and status type 'ST'
	   to custom level and click on 'Save'
	  Expected Result:Status type 'ST' is displayed in 'Custom View - Table' screen.
	*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String strCategory = "(Any)";
				String strCityZipCd = "";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { "7172" };
				strFuncResult = objPreferences.editCustomViewOptionsForStdStatuses(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { "Damage: Windows" };
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTablNew(
						selenium, strResrcTypName, strResource, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Navigate to the Map format of custom view,Select resource 'RS1' in the 'Find Resource 'dropdown' list.
	  Expected Result:Status type 'ST' is displayed in 'Custom View - Map' screen.
	*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strStatusType = { statMultiTypeName };
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strStatusType,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Navigate to Event >> Event Setup and Click on 'Edit' link associated with event template 'ET1' 
	  ('RT1' is already been selected in the earlier steps) select 'ST' and click on 'Save'
	  Expected Result:'Event Template List' screen is displayed with the event template 'ET1'
	*/
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
						.selectAndDeselectSTInEditEventTemplatePage(selenium,
								strTempName, true, str_statTypeValue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Navigate to Event >> Event Management, create an event 'EV' under 'ET1' selecting 'RS1'
	   and click on 'Save' now click on the event banner 'EV' which is displayed at the top.
	  Expected Result:Status Type 'ST' is displayed in the 'Event Status' screen
	*/
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
						selenium, strResource, true, false);
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
				String[] strStatusType = { statMultiTypeName };
				strFuncResult = objEventList
						.checkInEventBanner(selenium, strEveName,
								strResrcTypName, strResource, strStatusType);
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
				strTestData[2] = strLoginUserName + "/" + strLoginPassword;
				strTestData[3] = statNumTypeName + statMultiTypeName;
				strTestData[4] = strViewName;
				strTestData[5] = "Verify from 12th step";
				strTestData[6] = strResource;
				strTestData[7] = strSection1;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "43693";
			gstrTO = " Create status type ST without selecting any roles (view/update), associate ST with resource "
					+ "RS at resource TYPE level and verify that ST is displayed on following screens for system admin:"
					+ "a. Region view"
					+ "b. Map (in status type dropdown & in resource pop up window)"
					+ "d. View Resource Detail"
					+ "e. Custom view"
					+ "f. Event details"
					+ "g. Mobile view"
					+ "h. While configuring form"
					+ "i. Edit My Status Change Preferences"
					+ "j. Create/Edit Interfaces";// Test Objective
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

/*******************************************************************************************************
'Description	:Create status type ST without selecting any roles (view/update), add ST to resource RS 
            at the resource level and verify that ST is displayed on following screens for system admin:
				a. Region view
				b. Map (in status type dropdown & in resource pop up window)
				d. View Resource Detail
				e. Custom view
				f. Event details
				g. Mobile view
				h. While configuring form
				i. Edit My Status Change Preferences
				j. Create/Edit Interfaces
'Arguments		:None
'Returns		:None
'Date			:11/26/2012
'Author			:QSG
'--------------------------------------------------------------------------------------------------------
'Modified Date				                                                                 Modified By
'Date					                                                                     Name
**********************************************************************************************************/

	@Test
	public void testFTS44637() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		Roles objRole = new Roles();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Preferences objPreferences = new Preferences();
		EventList objEventList = new EventList();
		try {
			gstrTCID = "44637"; // Test Case Id
			gstrTO = " Create status type ST without selecting any roles (view/update), add ST to resource RS at the "
					+ "resource level and verify that ST is displayed on following screens for system admin:"
					+ "a. Region view "
					+ "b. Map (in status type dropdown & in resource pop up window)"
					+ "d. View Resource Detail"
					+ "e. Custom view"
					+ "f. Event details"
					+ "g. Mobile view"
					+ "h. While configuring form"
					+ "i. Edit My Status Change Preferences"
					+ "j. Create/Edit Interfaces";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String statNumTypeName = "NST" + strTimeText;
			String statMultiTypeName = "MST" + strTimeText;
			String str_statTypeValue[] = new String[2];

			String strMSTValue = "Multi";
			String strStatTypeColor = "Black";
			String str_MultiStatusName1 = "rSa" + strTimeText;
			String str_multiStatusValue[] = new String[2];
			str_multiStatusValue[0] = "";

			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];
			// RS
			String strResource = "AutoRs1_A" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];

			String strTempName = "Autotmp1" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			// sec data
			String strSection1 = "AB1_" + strTimeText;
			//Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";	
	/*
	* STEP :
	  Action:Precondition: 
      1.Resource 'RS1' is created with proving the address under resource Type 'RT1' selecting 'ST1'. 
      2.Event template 'ET1' is created selecting 'RT1' and 'ST1'.
      3.Event 'EV' is created under the template 'ET1' selecting 'RT1'.
	  Expected Result:No Expected Result
	*/
	//266313
            log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

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
				String strNSTValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_statTypeValue[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statNumTypeName);

				if (str_statTypeValue[0].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrcTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_statTypeValue[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName);
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

			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { str_statTypeValue[0] };

				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef,
						strEveColor, true, strResTypeVal, strStatusTypeval,
						true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Role creation
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

			try {
				assertEquals("", strFuncResult);
				String[] str_statTypeValues={};
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, str_statTypeValues, false,
						str_statTypeValues, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
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
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
	/*
	* STEP :
	  Action:Login as RegAdmin and Navigate to Setup >> Status Type,and Click on 'Create New Status Types' 
	  button Select 'Multi' from the 'Select Type' dropdown list and then click on 'Next'
	  Expected Result:'Create Multi Status Type' screen is displayed.
	*/
	//266314
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
	/*
	* STEP :
	  Action:Create a status type 'ST' by selecting a standard status type 'SST' and fill all the mandatory data 
	  and DO NOT select any role under 'Roles with view rights' and 'Roles with update rights' sections then click on 'Save'
	  Expected Result:Status type 'ST' is displayed in 'Status Type List' screen.
	*/
	//266315
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, statMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStandardST = "Damage: Windows";
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(
						selenium, strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = {{strRoleValue,"true"}};
				String[][] strRoleViewValue = {{strRoleValue,"true"}};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, true, true, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						statMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_statTypeValue[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statMultiTypeName);
				if (str_statTypeValue[1].compareTo("") != 0) {
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
						selenium, statMultiTypeName, str_MultiStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_multiStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(selenium, statMultiTypeName,
								str_MultiStatusName1);
				if (str_multiStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Navigate to Setup >> Resources, and click on 'Edit Status Types' link associated with 'RS1'
	   and select 'ST' under 'Additional Status Types' section and click on 'Save'.
	  Expected Result:'RT1' is diaplayed in 'Resource Type List' screen
	*/
	//266316
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage_LinkChanged(
						selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,
						str_statTypeValue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Navigate to Setup >> views, create a view 'V1' selecting 'ST' and resource 'RS1' and click on 'Save'
	  Expected Result:View 'V1' is diaplayed in 'Region Views List' screen.
	*/
	//266317
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { str_statTypeValue[1] };
				String[] strRSValues = { strRSValue[0] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Click on 'Customize Resource Detail View' Screen, create a section 'Sec' then click 
	  on 'Uncategorized' section, Drag and drop the status type 'ST' to section 'Sec' and click on 'Save'
	  Expected Result:'Region Views List' screen is displayed.
	*/
	//266318
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
				String[] strStatTypeArr = { statMultiTypeName };
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
	* STEP :
	  Action:Navigate to View >> V1
	  Expected Result:Status type 'ST' is displayed with the resource 'RS1'
	*/
	//266319
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
				String[] strStatType = { statMultiTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Click on Resource 'RS1'
	  Expected Result:Status type 'ST' is displayed under the section 'Sec' in 'View Resource Detail' section.
	*/
	//266320
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statMultiTypeName };
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strStatTypep);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Navigate to View >> Map, select resource 'RS1' under 'Find Resource' dropdown
	  Expected Result:Status type 'ST' is displayed on 'resource pop up window' screen
	*/
	//266321
			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statMultiTypeName };
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Click on 'Status Type (to color icons)' dropdown list
	  Expected Result:Status Type 'ST' is displayed in the dropdown list
	*/
	//266322
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statMultiTypeName + "']"));

				log4j.info("status type " + statMultiTypeName
						+ " is displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statMultiTypeName
						+ " is NOT displayed in dropdown. ");
				strFuncResult = "status type " + statMultiTypeName
						+ " is NOT displayed in dropdown. ";
				gstrReason = strFuncResult;

			}
	/*
	* STEP :
	  Action:Navigate to Preferences >> Customized View,add resources 'RS1' and status type 'ST' to 
	  custom level and click on 'Save'
	  Expected Result:Status type 'ST' is displayed in 'Custom View - Table' screen.
	*/
	//266323
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String strCategory = "(Any)";
				String strCityZipCd = "";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { "7172" };
				strFuncResult = objPreferences.editCustomViewOptionsForStdStatuses(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { "Damage: Windows" };
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTablNew(
						selenium, strResrcTypName, strResource, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Navigate to the Map format of custom view,Select resource 'RS1' in the 'Find Resource 'dropdown' list.
	  Expected Result:Status type 'ST' is displayed in 'Custom View - Map' screen.
	*/
	//266324
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strStatusType = { statMultiTypeName };
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strStatusType,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Navigate to Event >> Event Setup and Click on 'Edit' link associated with event template 'ET1' ('RT1' is already been selected in the earlier steps) select 'ST' and click on 'Save'
	  Expected Result:'Event Template List' screen is displayed with the event template 'ET1'
	*/
	//266325
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
						.selectAndDeselectSTInEditEventTemplatePage(selenium,
								strTempName, true, str_statTypeValue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Navigate to Event >> Event Management, create an event 'EV' under 'ET1' selecting 'RS1' and click on 'Save' now click on the event banner 'EV' which is displayed at the top.
	  Expected Result:Status Type 'ST' is displayed in the 'Event Status' screen
	*/
	//266326
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
						selenium, strResource, true, false);
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
				String[] strStatusType = { statMultiTypeName };
				strFuncResult = objEventList
						.checkInEventBanner(selenium, strEveName,
								strResrcTypName, strResource, strStatusType);
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
				strTestData[2] = strLoginUserName + "/" + strLoginPassword;
				strTestData[3] = statNumTypeName + statMultiTypeName;
				strTestData[4] = strViewName;
				strTestData[5] = "Verify from 12th step";
				strTestData[6] = strResource;
				strTestData[7] = strSection1;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "44637";
			gstrTO = " Create status type ST without selecting any roles (view/update), add ST to resource RS at the "
					+ "resource level and verify that ST is displayed on following screens for system admin:"
					+ "a. Region view "
					+ "b. Map (in status type dropdown & in resource pop up window)"
					+ "d. View Resource Detail"
					+ "e. Custom view"
					+ "f. Event details"
					+ "g. Mobile view"
					+ "h. While configuring form"
					+ "i. Edit My Status Change Preferences"
					+ "j. Create/Edit Interfaces";// Test Objective
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
	/*************************************************************************
	'Description	:Verify that when the view permissions of status type are
	                 edited, the changes are reflected on all the view screens.
	'Arguments		:None
	'Returns		:None
	'Date			:11/29/2012
	'Author			:QSG
	'-------------------------------------------------------------------------
	'Modified Date				                                Modified By
	'Date					                                    Name
	***************************************************************************/

	@Test
	public void testFTS46075() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		StatusTypes objStatusTypes = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Preferences objPreferences = new Preferences();
		EventList objEventList = new EventList();
		try {
			gstrTCID = "46075"; // Test Case Id
			gstrTO = " Verify that when the view permissions of status type are edited, the changes are " +
					"reflected on all the view screens.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// user
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName1;
			
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// Role
			String strRolesName = "Rol" + strTimeText;
			String strRoleValue = "";
			// Status types
			String strNSTValue = "Number";
			String strStatTypDefn = "Automation";
			String statNumTypeName1 = "NST_1" + strTimeText;
			String statNumTypeName2 = "NST_2" + strTimeText;
			String statNumTypeName3 = "NST_3" + strTimeText;
			String str_statTypeValue[] = new String[3];

			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];
			// RS
			String strResource = "AutoRs1_A" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];

			String strTempName = "Autotmp1" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			// sec data
			String strSection1 = "AB1_" + strTimeText;
			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			
			/*
			 * STEP : Action:Precondition: 1.Provide user A with the following
			 * rights: a.Setup Status Types. b.View Custom View. 2.Status type
			 * 'ST1' is created selecting role 'R1' under both 'Roles with view
			 * rights' and 'Roles with update rights' sections. 3.Status type
			 * 'ST2' is created selecting role 'R1' only under 'Roles with view
			 * rights' sections. 4.Status type 'ST3' is created and without
			 * selecting any role. 5.Role 'R1' is associated with User A.
			 * 6.Resource 'RS1' is created under resource Type 'RT1' selecting
			 * ST1,ST2 and ST3 at 'RT1' level. 7.View 'V1' is created selecting
			 * ST1,ST2 and ST3 and 'RT1'. 8.Create a status type section 'Sec'
			 * containing ST1,ST2 and ST3 in 'Edit Resource Detail View
			 * Sections' 9.Event 'EV' is created selecting 'RT1' and ST1,ST2 and
			 * ST3. Expected Result:No Expected Result
			 */

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Role

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
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, false);

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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// status type(ST1)
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statNumTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_statTypeValue[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statNumTypeName1);
				if (str_statTypeValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// status type(ST2)
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				String[][] strRoleUpdateValue = {};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statNumTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_statTypeValue[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statNumTypeName2);
				if (str_statTypeValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// status type(ST3)
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName3,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = {};
				String[][] strRoleUpdateValue = {};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statNumTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_statTypeValue[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statNumTypeName3);
				if (str_statTypeValue[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Resource Type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrcTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_statTypeValue[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_statTypeValue[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_statTypeValue[2] + "']");
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

			// Resources
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// /RSa
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrcTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
			// View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { str_statTypeValue[0],
						str_statTypeValue[1], str_statTypeValue[2] };
				String[] strRSValues = { strRSValue[0] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(seleniumPrecondition, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(seleniumPrecondition,
						strRsTypeValues[0], true, true);
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
				String[] strStatusTypeval = { str_statTypeValue[0],
						str_statTypeValue[1], str_statTypeValue[2] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName, strEveName, strTempDef, false);
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
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Section
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
				String[] strStatTypeArr = { statNumTypeName1, statNumTypeName2,
						statNumTypeName3 };
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
			/*
			 * STEP : Action:Login as user A and navigate to View >> V1.
			 * Expected Result:Status type 'ST1' and 'ST2' are displayed and
			 * 'ST3' is not displayed in view 'V1' screen.
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
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statNumTypeName1, statNumTypeName2,
						statNumTypeName3 };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on Resource 'RS1'. Expected Result:Status
			 * type 'ST1' and 'ST2' are displayed and 'ST3' is not displayed
			 * under the section 'Sec' in 'View Resource Detail' screen.
			 */

			try {
				assertEquals("Status Type " + statNumTypeName3
						+ " is NOT displayed in the User view screen",
						strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statNumTypeName1, statNumTypeName2 };
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strStatTypep);
				try {
					assertEquals("", strFuncResult);
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,"
									+ "'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statNumTypeName3 + "']"));
					log4j.info("The Status Type " + statNumTypeName3
							+ " is NOT displayed on the "
							+ "view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The Status Type " + statNumTypeName3
							+ " is displayed on"
							+ " the view resource detail screen. ");
					strFuncResult = "The Status Type " + statNumTypeName3
							+ " is displayed on "
							+ "the view resource detail screen. ";
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Preferences >> Customized View,add
			 * resources 'RS1' to custom view and then click on 'Options'.
			 * Expected Result:Only status types ST1 and ST2 are available.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String strCategory = "(Any)";
				String strCityZipCd = "";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
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
				String[] strStatusType = { statNumTypeName1, statNumTypeName2 };
				strFuncResult = objPreferences
						.chkSTPresenceInEditCustViewOptionPage(selenium,
								strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statNumTypeName3 };
				strFuncResult = objPreferences.checkSTsInEditCustViewOptions(
						selenium, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Add ST1 and ST2 to the custom view and click on
			 * 'Save'. Expected Result:Only status types ST1 and ST2 are
			 * displayed in 'Custom View - Table' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statNumTypeName1, statNumTypeName2 };
				strFuncResult = objPreferences.editCustomViewWith4Options(
						selenium, strStatusType, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statNumTypeName1, statNumTypeName2 };
				String[] strResources = { strResource };
				strFuncResult = objPreferences
						.verifyRTSTAndRSInCustView(selenium, strResrcTypName,
								strResources, strStatTypeArr);
				try {
					assertEquals("", strFuncResult);
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'rgt_')][@summary='Status for "
									+ strResrcTypName
									+ "']/thead/tr/th[3]/a[text()='"
									+ statNumTypeName3 + "']"));
					log4j
							.info("Status Type "
									+ statNumTypeName3
									+ " is NOT displayed in the Custom View Table screen.");
				} catch (AssertionError ae) {
					log4j
							.info("Status Type is "
									+ statNumTypeName3
									+ " is displayed in the Custom View Table screen .");
					strFuncResult = " Status Type  " + statNumTypeName3
							+ " is displayed in the Custom View Table screen .";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to the Map format of custom view,Select
			 * resource 'RS1' in the 'Find Resource 'dropdown' list. Expected
			 * Result:Status type ST1 and ST2 are displayed but not ST3 in the
			 * 'Custom View - Map' screen.
			 */
			// 266808
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strStatusType = { statNumTypeName1, statNumTypeName2 };
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strStatusType,
								false, true);
				String strStatType = selenium.getText(propElementDetails
						.getProperty("ViewMap.ResPopup.StatTypeList"));
				try {
					assertEquals("", strFuncResult);
					assertFalse(strStatType.contains(statNumTypeName3));
					log4j.info("Role Based Status type " + statNumTypeName3
							+ " is NOT displayed");
				} catch (AssertionError ae) {
					log4j.info("Role Based Status type " + statNumTypeName3
							+ " is still displayed");
					strFuncResult = " Role Based Status type "
							+ statNumTypeName3 + " is still displayed";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the event banner 'EV' which is displayed
			 * at the top. Expected Result:Only status Type ST1 and ST2 are
			 * displayed in the 'Event Status' screen.
			 */
			// 266833
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statNumTypeName1, statNumTypeName2 };
				strFuncResult = objEventList.checkInEventBannerNew(selenium,
						strEveName, strResrcTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statNumTypeName3 };
				strFuncResult = objEventList.checkSTypesEvntBanner(selenium,
						strEveName, strResrcTypName, strStatTypeArr, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Status Type,and Click on
			 * 'Edit' link associated with 'ST1', deselect the role 'R1' from
			 * both view and update rights, and click on 'Save'. Expected
			 * Result:Status type 'ST1' is displayed in 'Status Type List'
			 * screen.
			 */
			// 266676
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statNumTypeName1, strNSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = {};
				String[][] strRoleUpdateValue = {};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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

			/*
			 * STEP : Action:Click on 'Edit' link associated with 'ST2',select
			 * role 'R1' for both view and update rights, and click on 'Save'.
			 * Expected Result:Status type 'ST2' is displayed in 'Status Type
			 * List' screen.
			 */
			// 266677
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statNumTypeName2, strNSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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

			/*
			 * STEP : Action:Click on 'Edit' link associated with 'ST3',select
			 * role 'R1' only for view right, and click on 'Save'. Expected
			 * Result:Status type 'ST3' is displayed in 'Status Type List'
			 * screen.
			 */
			// 266678
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statNumTypeName3, strNSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				String[][] strRoleUpdateValue = {};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statNumTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Logout and login as user A and navigate to View >>
			 * V1. Expected Result:Status type 'ST2' and 'ST3' are displayed and
			 * 'ST1' is not displayed in view 'V1' screen.
			 */
			// 266679
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
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statNumTypeName2, statNumTypeName3,
						statNumTypeName1 };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on Resource 'RS1'. Expected Result:Status
			 * type 'ST2' and 'ST3' are displayed and 'ST1' is not displayed
			 * under the section 'Sec' in 'View Resource Detail' screen.
			 */
			// 266857
			try {
				assertEquals("Status Type " + statNumTypeName1
						+ " is NOT displayed in the User view screen",
						strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statNumTypeName2, statNumTypeName3 };
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strStatTypep);
				try {
					assertEquals("", strFuncResult);
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,"
									+ "'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statNumTypeName1 + "']"));
					log4j.info("The Status Type " + statNumTypeName1
							+ " is NOT displayed on the "
							+ "view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The Status Type " + statNumTypeName1
							+ " is displayed on"
							+ " the view resource detail screen. ");
					strFuncResult = "The Status Type " + statNumTypeName1
							+ " is displayed on "
							+ "the view resource detail screen. ";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized View, add
			 * resources 'RS1' to custom view Click on 'Options'. Expected
			 * Result:Only status type ST2 and ST3 is displayed but not ST1 in
			 * 'Custom View - Table' screen.
			 */
			// 266865
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String strCategory = "(Any)";
				String strCityZipCd = "";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
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
				String[] strStatusType = { statNumTypeName2, statNumTypeName3 };
				strFuncResult = objPreferences
						.chkSTPresenceInEditCustViewOptionPage(selenium,
								strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statNumTypeName1 };
				strFuncResult = objPreferences.checkSTsInEditCustViewOptions(
						selenium, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Add ST2 and ST3 to the custom view and click on
			 * 'Save'. Expected Result:Only status types ST2 and ST3 are
			 * displayed in 'Custom View - Table' screen.
			 */
			// 267385

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statNumTypeName2, statNumTypeName3 };
				strFuncResult = objPreferences.editCustomViewWith4Options(
						selenium, strStatusType, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statNumTypeName2, statNumTypeName3 };
				String[] strResources = { strResource };
				strFuncResult = objPreferences
						.verifyRTSTAndRSInCustView(selenium, strResrcTypName,
								strResources, strStatTypeArr);
				try {
					assertEquals("", strFuncResult);
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'rgt_')][@summary='Status for "
									+ strResrcTypName
									+ "']/thead/tr/th[1]/a[text()='"
									+ statNumTypeName1 + "']"));
					log4j
							.info("Status Type "
									+ statNumTypeName1
									+ " is NOT displayed in the Custom View Table screen.");
				} catch (AssertionError ae) {
					log4j
							.info("Status Type is "
									+ statNumTypeName1
									+ " is displayed in the Custom View Table screen .");
					strFuncResult = " Status Type  " + statNumTypeName1
							+ " is displayed in the Custom View Table screen .";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to the Map format of custom view,Select
			 * resource 'RS1' in the 'Find Resource 'dropdown' list. Expected
			 * Result:Status type ST2 and ST3 are displayed but not ST1 in the
			 * 'Custom View - Map' screen.
			 */
			// 266885
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strStatusType = { statNumTypeName2, statNumTypeName3 };
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strStatusType,
								false, true);
				String strStatType = selenium.getText(propElementDetails
						.getProperty("ViewMap.ResPopup.StatTypeList"));
				try {
					assertEquals("", strFuncResult);
					assertFalse(strStatType.contains(statNumTypeName1));
					log4j.info("Role Based Status type " + statNumTypeName1
							+ " is NOT displayed");
				} catch (AssertionError ae) {
					log4j.info("Role Based Status type " + statNumTypeName1
							+ " is still displayed");
					strFuncResult = " Role Based Status type "
							+ statNumTypeName1 + " is still displayed";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the event banner 'EV' which is displayed
			 * at the top. Expected Result:Only status Type ST2 and ST3 are
			 * displayed in the 'Event Status' screen.
			 */
			// 267229
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statNumTypeName2, statNumTypeName3 };
				strFuncResult = objEventList.checkInEventBannerNew(selenium,
						strEveName, strResrcTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statNumTypeName1 };
				strFuncResult = objEventList.checkSTypesEvntBanner(selenium,
						strEveName, strResrcTypName, strStatTypeArr, false);
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
			gstrTCID = "46075";
			gstrTO = "Verify that when the view permissions of status type" +
					" are edited, the changes are reflected on all the view screens.";
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

/*************************************************************************************************************
'Description  :Status type ST is associated with resource RS at the resource level. Verify that a user
               with �Update Status� right on resource RS but without view/update permissions for status
               type ST does not receive update status prompt when the status of ST is overdue for resource RS.
'Arguments    :None
'Returns	  :None
'Date		  :12/11/2012
'Author		  :QSG
'-------------------------------------------------------------------------------------------------------------
'Modified Date				                Modified By
'Date					                      Name
*************************************************************************************************************/

	@Test
	public void testFTS44614() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();
		try {
			gstrTCID = "44614"; // Test Case Id
			gstrTO = "Status type ST is associated with resource RS at the resource level. Verify that a user with "
					+ "�Update Status� right on resource RS but without view/update permissions for status type ST "
					+ "does not receive update status prompt when the status of ST is overdue for resource RS.";// Test
																												// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status Type
			String statTypeName = "NST_" + strTimeText;
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;
			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;
			String strSTvalue[] = new String[3];
			String strStatTypDefn = "Auto";

			String strExpHr = "00";
			String strExpMn = "05";
			String StatusTime = "";
			String strUpdTime_Shift = "";
			int intShiftTime = 20;

			String strStatTypeColor = "Black";
			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			strStatusValueShift[0] = "";
			strStatusValueShift[1] = "";

			strStatusValueExpire[0] = "";
			strStatusValueExpire[1] = "";

			// RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			String strResVal = "";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// Rol
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
	/*
	* STEP :
	  Action:Precondition:			
			1.Status type 'ST1' (Multi status type) is created by associating the expiration time (say 'T1') and 
			selecting only role 'R1' under view and update right for the status type.
			2.Status type 'ST2' (Multi status type) is created by associating the shift time (say 'S1') and
			 selecting only role 'R1' under view and update right for the status type.
			3.Resource 'RS' is created by proving address under the resource type 'RT', 
			  associating 'ST1' and 'ST2' at the 'RS' level.
			4.User B is associated with role 'R1'.
			5.User A has 'User must update overdue status' right and is not associated with any role.
			6.User A and User B has �Update Status� right on resource 'RS'. 
			 Expected Result:No Expected Result
	*/
        log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);
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
			// Navigate to status type list

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue="Number";
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(selenium, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeName);
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(selenium,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeNameExpire);
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
						selenium, statTypeNameExpire, strStatusNameExpire1,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						selenium, statTypeNameExpire, strStatusNameExpire2,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameExpire, strStatusNameExpire1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameExpire, strStatusNameExpire2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				StatusTime = selenium.getText("css=#statusTime");
				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				// Select shift time
				selenium.click(propElementDetails
						.getProperty("StatusType.CreateStatType.ShiftTime1"));
				selenium.select(
						propElementDetails
								.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
						strStatusTime[0]);
				selenium.select(
						propElementDetails
								.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
						strStatusTime[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(selenium,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeNameShift);
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
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						statTypeNameShift, strStatusNameShift1,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						statTypeNameShift, strStatusNameShift2,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameShift, strStatusNameShift1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameShift, strStatusNameShift2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
            //RT
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
								+ strSTvalue[0] + "']");
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
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);
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
				strFuncResult = objRs.navToEditResLevelSTPage_LinkChanged(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(selenium, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium, strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Create User A

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//User B
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameB, strInitPwd, strConfirmPwd, strUsrFulName);
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium, strUserNameB, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
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
	  Action:Login as User B, navigate to View >> Map. Select resource 'RS' under 'Find Resource' dropdown 
	  and update status values of status types 'ST1' and 'ST2' from resource pop up window. 
	  Expected Result:Status type 'ST1' and 'ST2' are updated and are displayed on 'resource pop up window' screen. 
	*/

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[1],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[2],
						strStatusNameShift1, strStatusValueShift[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
	/*
	* STEP :
	  Action:After time 'T1'. 
	  Expected Result: User A do not receive status update prompt with ST1 and ST2 expanded. 
	*/			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(360000);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeNameShift);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:After time 'S1'. 
	  Expected Result:User A do not receive status update prompt with ST1 and ST2 expanded.
	*/

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(60000);
				strFuncResult = objViews.varUpdateStatPrompt(selenium,
						strResource, statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium,
						strResource, statTypeNameShift);
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
			gstrTCID = "44614"; // Test Case Id
			gstrTO = "Status type ST is associated with resource RS at the resource level. Verify that a user with "
					+ "�Update Status� right on resource RS but without view/update permissions for status type ST "
					+ "does not receive update status prompt when the status of ST is overdue for resource RS.";// Test
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

/**********************************************************************************************************
'Description  :Status type ST is associated with resource RS at the resource level. Verify that a user with 
              �Update Status� right on resource RS does not receive update status prompt when the status of a 
               status type to which the user has view only permission is overdue for resource RS.
'Arguments    :None
'Returns	  :None
'Date		  :12/11/2012
'Author		  :QSG
'------------------------------------------------------------------------------------------------------------
'Modified Date				                Modified By
'Date					                      Name
*************************************************************************************************************/

@Test
 public void testFTS44615() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		
		try {
			gstrTCID = "44615"; // Test Case Id
			gstrTO = "Status type ST is associated with resource RS at the resource level. Verify that a user with"+ 
                     "�Update Status� right on resource RS does not receive update status prompt when the status of a"+ 
                     "status type to which the user has view only permission is overdue for resource RS.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status Type
			String statTypeName="NST_"+strTimeText;
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;
			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;
			String strSTvalue[] = new String[3];
			String strStatTypDefn = "Auto";

			String strExpHr = "00";
			String strExpMn = "05";
			String StatusTime = "";
			String strUpdTime_Shift = "";
			int intShiftTime = 20;

			String strStatTypeColor = "Black";
			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			strStatusValueShift[0] = "";
			strStatusValueShift[1] = "";

			strStatusValueExpire[0] = "";
			strStatusValueExpire[1] = "";

			// RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			String strResVal = "";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// Rol
			String strRoleName1 = "AutoR1_" + strTimeText;
			String strRoleName2 = "AutoR2_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue1 = "";
			String strRoleValue2 = "";
		/*
		 * STEP :
		  Action:Precondition:							
			1.Status type 'ST1' (Multi status type) is created by associating the expiration time (say 'T1') and
			 selecting role 'R1' under view and update right and role 'R2' only under view right for the status type.
			2.Status type 'ST2' (Multi status type) is created by associating the shift time (say 'S1') and
			 selecting role 'R1' under view and update right and role 'R2' only under view right for the status type.
			3.Resource 'RS' is created by proving address under the resource type 'RT', 
			associating 'ST1' and 'ST2' at the 'RS' level.
			4.User B is associated with role 'R1'.
			5.User A has 'User must update overdue status' right and is associated with role 'R1'.
			6.User A and User B has �Update Status� right on resource 'RS'.
				 Expected Result:No Expected Result
	     */
	        log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
					
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName1, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue1 = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName1);
				if (strRoleValue1.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName2, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue2 = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName2);
				if (strRoleValue2.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Navigate to status type list

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objST
						.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
								strStatusTypeValue, statTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = {{ strRoleValue1, "true" },{ strRoleValue2, "true" } };
				String[][] strRoleUpdateValue = {{ strRoleValue1, "true" } };
				strFuncResult = objST.slectAndDeselectRoleInSTNew(seleniumPrecondition, false,
						false, strRoleViewValue, strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeNameExpire);
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
						seleniumPrecondition, statTypeNameExpire, strStatusNameExpire1,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						seleniumPrecondition, statTypeNameExpire, strStatusNameExpire2,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameExpire, strStatusNameExpire1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameExpire, strStatusNameExpire2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = {{ strRoleValue1, "true" },{ strRoleValue2, "true" } };
				String[][] strRoleUpdateValue = {{ strRoleValue1, "true" } };
				strFuncResult = objST.slectAndDeselectRoleInSTNew(seleniumPrecondition, false,
						false, strRoleViewValue, strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");
				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				// Select shift time
				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.ShiftTime1"));
				seleniumPrecondition.select(
						propElementDetails
								.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
						strStatusTime[0]);
				seleniumPrecondition.select(
						propElementDetails
								.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
						strStatusTime[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeNameShift);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift1,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift2,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//RT
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage_LinkChanged(seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(seleniumPrecondition, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition, strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create User A

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User B
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserNameB, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameB, strByRole, strByResourceType,
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
		* STEP :
		  Action:Login as User B, navigate to View >> Map. Select resource 'RS' under 'Find Resource' dropdown 
		  and update status values of status types 'ST1' and 'ST2' from resource pop up window. 
		  Expected Result:Status type 'ST1' and 'ST2' are updated and are displayed on 'resource pop up window' screen. 
		*/
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[1],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[2],
						strStatusNameShift1, strStatusValueShift[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		* STEP :
		  Action:After time 'T1'. 
		  Expected Result: User A do not receive status update prompt with ST1 and ST2 expanded. 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(360000);
				try {
					 assertFalse(selenium.isElementPresent("//div[@class='statusTitle clearFix']" +
					 		"/label[contains(text(),'"+strStatusTypeValueExpire+"')]" +
					 		"/span[text()='(Required/Overdue)']"));
					log4j.info("'Update Status' prompt for "+strStatusTypeValueExpire+" is  NOT displayed.");
				
				} catch (AssertionError Ae) {
					gstrReason = "'Update Status' prompt for "+strStatusTypeValueExpire+" is displayed.";
					log4j.info("'Update Status' prompt for "+strStatusTypeValueExpire+" is displayed.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(360000);
				try {
					 assertFalse(selenium.isElementPresent("//div[@class='statusTitle clearFix']" +
					 		"/label[contains(text(),'"+statTypeNameShift+"')]" +
					 		"/span[text()='(Required/Overdue)']"));
					log4j.info("'Update Status' prompt for "+statTypeNameShift+" is  NOT displayed.");
				
				} catch (AssertionError Ae) {
					gstrReason = "'Update Status' prompt for "+statTypeNameShift+" is displayed.";
					log4j.info("'Update Status' prompt for "+statTypeNameShift+" is displayed.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:After time 'S1'. 
		  Expected Result:User A do not receive status update prompt with ST1 and ST2 expanded.
		*/

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(60000);
				try {
					 assertFalse(selenium.isElementPresent("//div[@class='statusTitle clearFix']" +
					 		"/label[contains(text(),'"+strStatusTypeValueExpire+"')]" +
					 		"/span[text()='(Required/Overdue)']"));
					log4j.info("'Update Status' prompt for "+strStatusTypeValueExpire+" is  NOT displayed.");
				
				} catch (AssertionError Ae) {
					gstrReason = "'Update Status' prompt for "+strStatusTypeValueExpire+" is displayed.";
					log4j.info("'Update Status' prompt for "+strStatusTypeValueExpire+" is displayed.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				try {
					 assertFalse(selenium.isElementPresent("//div[@class='statusTitle clearFix']" +
					 		"/label[contains(text(),'"+statTypeNameShift+"')]" +
					 		"/span[text()='(Required/Overdue)']"));
					log4j.info("'Update Status' prompt for "+statTypeNameShift+" is  NOT displayed.");
				
				} catch (AssertionError Ae) {
					gstrReason = "'Update Status' prompt for "+statTypeNameShift+" is displayed.";
					log4j.info("'Update Status' prompt for "+statTypeNameShift+" is displayed.");
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
			gstrTCID = "44615"; // Test Case Id
			gstrTO = "Status type ST is associated with resource RS at the resource level. Verify that a user with"
					+ "�Update Status� right on resource RS does not receive update status prompt when the status of a"
					+ "status type to which the user has view only permission is overdue for resource RS.";//TO
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
 /************************************************************************************************************
 'Description  :Status type ST is associated with resource RS at the resource level. Verify that a user
                with �Update Status� right on resource RS but without view/update permissions for status
                type ST does not receive update status prompt when the status of ST is overdue for resource RS.
 'Arguments    :None
 'Returns	  :None
 'Date		  :12/11/2012
 'Author		  :QSG
 '------------------------------------------------------------------------------------------------------------
 'Modified Date				                                                                   Modified By
 'Date					                                                                        Name
 *************************************************************************************************************/

	@Test
	public void testFTS44604() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();
		try {
			gstrTCID = "44604"; // Test Case Id
			gstrTO = "Status type ST is associated with resource RS at the resource type level. Verify that a"
					+ " user with �Update Status� right on resource RS but without view/update permissions for status"
					+ " type ST does not receive update status prompt when the status of ST is overdue for resource";// Test
																														// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status Type
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;
			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;
			String strSTvalue[] = new String[2];
			String strStatTypDefn = "Auto";

			String strExpHr = "00";
			String strExpMn = "05";
			String StatusTime = "";
			String strUpdTime_Shift = "";
			int intShiftTime = 20;

			String strStatTypeColor = "Black";
			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			strStatusValueShift[0] = "";
			strStatusValueShift[1] = "";

			strStatusValueExpire[0] = "";
			strStatusValueExpire[1] = "";

			// RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			String strResVal = "";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// Rol
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			strSTvalue[0] = "";
			strSTvalue[1] = "";		
 	/*
 	* STEP :
 	  Action:Precondition:			
 			1.Status type 'ST1' (Multi status type) is created by associating the expiration time (say 'T1')
 			 and selecting only role 'R1' under view and update right for the status type.
			2.Status type 'ST2' (Multi status type) is created by associating the shift time (say 'S1')
			 and selecting only role 'R1' under view and update right for the status type.
			3.Resource 'RS' is created by proving address under the resource type 'RT', 
			associating 'ST1' and 'ST2' at the 'RT' level.
			4.User B is associated with role 'R1'.
			5.User A has 'User must update overdue status' right and is not associated with any role.
			6.User A and User B has �Update Status� right on resource 'RS'. 
 			 Expected Result:No Expected Result
 	*/
         log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
 			
 			strFuncResult = objLogin.login(selenium, strLoginUserName,
 					strLoginPassword);

 			// Navigate user default region
 			try {
 				assertEquals("", strFuncResult);
 				blnLogin = true;
 				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
 			} catch (AssertionError Ae) {

 				gstrReason = strFuncResult;

 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objRole.navRolesListPge(selenium);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;

 			}
 			// Navigate to Role list
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
 						strRoleName, strRoleRights, strSTvalue, false,
 						strSTvalue, false, true);
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
 			// Navigate to status type list

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objST.navStatusTypList(selenium);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			// Fill mandatory fields

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
 						strStatusTypeValueExpire, statTypeNameExpire,
 						strStatTypDefn, false);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				String[] strRolesValue = { strRoleValue };
 				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
 						false, strRolesValue, false);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objST.savAndVerifyMultiST(selenium,
 						statTypeNameExpire);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
 						statTypeNameExpire);
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
 				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
 						selenium, statTypeNameExpire, strStatusNameExpire1,
 						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
 						strExpMn, "", "", true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
 						selenium, statTypeNameExpire, strStatusNameExpire2,
 						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
 						strExpMn, "", "", true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strStatValue = objST.fetchStatValInStatusList(selenium,
 						statTypeNameExpire, strStatusNameExpire1);
 				if (strStatValue.compareTo("") != 0) {
 					strFuncResult = "";
 					strStatusValueExpire[0] = strStatValue;
 				} else {
 					strFuncResult = "Failed to fetch status value";
 				}

 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strStatValue = objST.fetchStatValInStatusList(selenium,
 						statTypeNameExpire, strStatusNameExpire2);
 				if (strStatValue.compareTo("") != 0) {
 					strFuncResult = "";
 					strStatusValueExpire[1] = strStatValue;
 				} else {
 					strFuncResult = "Failed to fetch status value";
 				}

 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			// Fill mandatory fields

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
 						strStatusTypeValueShift, statTypeNameShift,
 						strStatTypDefn, false);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				String[] strRolesValue = { strRoleValue };
 				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
 						false, strRolesValue, false);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				
 				StatusTime = selenium.getText("css=#statusTime");
 				String strStatusTime[] = StatusTime.split(" ");

 				strUpdTime_Shift = strStatusTime[2];
 				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
 						intShiftTime, "HH:mm");
 				strUpdTime_Shift = strAdUpdTime;
 				log4j.info(strUpdTime_Shift);

 				strStatusTime = strUpdTime_Shift.split(":");

 				// Select shift time
 				selenium.click(propElementDetails
 						.getProperty("StatusType.CreateStatType.ShiftTime1"));
 				selenium.select(
 						propElementDetails
 								.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
 						strStatusTime[0]);
 				selenium.select(
 						propElementDetails
 								.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
 						strStatusTime[1]);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objST.savAndVerifyMultiST(selenium,
 						statTypeNameExpire);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
 						statTypeNameShift);
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
 				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
 						statTypeNameShift, strStatusNameShift1,
 						strStatusTypeValueShift, strStatTypeColor, true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
 						statTypeNameShift, strStatusNameShift2,
 						strStatusTypeValueShift, strStatTypeColor, true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strStatValue = objST.fetchStatValInStatusList(selenium,
 						statTypeNameShift, strStatusNameShift1);
 				if (strStatValue.compareTo("") != 0) {
 					strFuncResult = "";
 					strStatusValueShift[0] = strStatValue;
 				} else {
 					strFuncResult = "Failed to fetch status value";
 				}

 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strStatValue = objST.fetchStatValInStatusList(selenium,
 						statTypeNameShift, strStatusNameShift2);
 				if (strStatValue.compareTo("") != 0) {
 					strFuncResult = "";
 					strStatusValueShift[1] = strStatValue;
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
 						strResrctTypName,
 						"css=input[name='statusTypeID'][value='"
 								+ strSTvalue[0] + "']");
 				selenium.click("css=input[name='statusTypeID'][value='"
 						+ strSTvalue[1] + "']");
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
 				strFuncResult = objRs.navResourcesList(selenium);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				String strState = "Alabama";
 				String strCountry = "Barbour County";
 				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
 						strResource, strAbbrv, strResrctTypName, strContFName,
 						strContLName, strState, strCountry, strStandResType);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strResVal = objRs.fetchResValueInResList(selenium, strResource);
 				if (strResVal.compareTo("") != 0) {
 					strFuncResult = "";
 					strRSValue[0] = strResVal;
 				} else {
 					strFuncResult = "Failed to fetch Resource value";
 				}
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			// Create User A

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.navUserListPge(selenium);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
 						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);
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
 				String strOptions = propElementDetails
 						.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus");
 				strFuncResult = objCreateUsers.advancedOptns(selenium,
 						strOptions, true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium, strUserNameA, strByRole,
 						strByResourceType, strByUserInfo, strNameFormat);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			
 			//User B
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
 						strUserNameB, strInitPwd, strConfirmPwd, strUsrFulName);
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
 				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
 						strRoleValue, true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium, strUserNameB, strByRole,
 						strByResourceType, strByUserInfo, strNameFormat);
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
 	  Action:Login as User B, navigate to View >> Map. Select resource 'RS' under 'Find Resource' dropdown 
 	  and update status values of status types 'ST1' and 'ST2' from resource pop up window. 
 	  Expected Result:Status type 'ST1' and 'ST2' are updated and are displayed on 'resource pop up window' screen. 
 	*/

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
 						strInitPwd);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				blnLogin = true;
 				String[] strEventStatType = {};
 				String[] strRoleStatType = { statTypeNameExpire,
 						statTypeNameShift };
 				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
 						selenium, strResource, strEventStatType,
 						strRoleStatType, false, true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;

 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objViewMap.updateMultiStatusType(selenium,
 						strResource, statTypeNameExpire, strSTvalue[0],
 						strStatusNameExpire1, strStatusValueExpire[0]);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;

 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objViewMap.updateMultiStatusType(selenium,
 						strResource, statTypeNameShift, strSTvalue[1],
 						strStatusNameShift1, strStatusValueShift[0]);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}			
 	/*
 	* STEP :
 	  Action:After time 'T1'. 
 	  Expected Result: User A do not receive status update prompt with ST1 and ST2 expanded. 
 	*/			
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objLogin.logout(selenium);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
 						strInitPwd);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				Thread.sleep(360000);
 				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeNameExpire);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeNameShift);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 	/*
 	* STEP :
 	  Action:After time 'S1'. 
 	  Expected Result:User A do not receive status update prompt with ST1 and ST2 expanded.
 	*/

 			try {
 				assertEquals("", strFuncResult);
 				Thread.sleep(60000);
 				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeNameExpire);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeNameShift);
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
    	gstrTCID = "44604";	//Test Case Id	
     	gstrTO = "Status type ST is associated with resource RS at the resource type level. Verify that a" +
     			" user with �Update Status� right on resource RS but without view/update permissions for status" +
     			" type ST does not receive update status prompt when the status of ST is overdue for resource";//Test Objective
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

 /***********************************************************************************************************
 'Description  :Status type ST is associated with resource RS at the resource level. Verify that a user with 
               �Update Status� right on resource RS does not receive update status prompt when the status of a 
                status type to which the user has view only permission is overdue for resource RS.
 'Arguments    :None
 'Returns	  :None
 'Date		  :12/11/2012
 'Author		  :QSG
 '------------------------------------------------------------------------------------------------------------
 'Modified Date				                Modified By
 'Date					                      Name
 ************************************************************************************************************/

@Test
  public void testFTS44609() throws Exception {

 		boolean blnLogin = false;
 		String strFuncResult = "";
 		Login objLogin = new Login();// object of class Login
 		StatusTypes objST = new StatusTypes();
 		ResourceTypes objRT = new ResourceTypes();
 		Resources objRs = new Resources();
 		CreateUsers objCreateUsers = new CreateUsers();
 		Roles objRole = new Roles();
 		ViewMap objViewMap = new ViewMap();
 		Views objViews = new Views();
 		try {
 			gstrTCID = "44609"; // Test Case Id
 			gstrTO = " Status type ST is associated with resource RS at the resource type level. Verify that a " +
 					"user with �Update Status� right on resource RS does not receive update status prompt when the" +
 					" status of a status type to which the user has view only permission is overdue for resource RS.";// Test Objective
 			gstrReason = "";
 			gstrResult = "FAIL";

 			Date_Time_settings dts = new Date_Time_settings();
 			gstrTimetake = dts.timeNow("HH:mm:ss");
 			String strFILE_PATH = pathProps.getProperty("TestData_path");
 			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
 			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
 			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

 			String strLoginUserName = rdExcel.readData("Login", 3, 1);
 			String strLoginPassword = rdExcel.readData("Login", 3, 2);
 			String strRegn = rdExcel.readData("Login", 3, 4);

 			// Status Type
 			String strStatusTypeValueExpire = "Multi";
 			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;
 			String strStatusTypeValueShift = "Multi";
 			String statTypeNameShift = "AutoMSTShift_" + strTimeText;
 			String strSTvalue[] = new String[2];
 			String strStatTypDefn = "Auto";

 			String strExpHr = "00";
 			String strExpMn = "05";
 			String StatusTime = "";
 			String strUpdTime_Shift = "";
 			int intShiftTime = 20;

 			String strStatTypeColor = "Black";
 			String strStatusNameExpire1 = "SaE" + strTimeText;
 			String strStatusNameExpire2 = "SbE" + strTimeText;

 			String strStatusNameShift1 = "SaS" + strTimeText;
 			String strStatusNameShift2 = "SbS" + strTimeText;

 			String strStatusValueExpire[] = new String[2];
 			String strStatusValueShift[] = new String[2];

 			strStatusValueShift[0] = "";
 			strStatusValueShift[1] = "";

 			strStatusValueExpire[0] = "";
 			strStatusValueExpire[1] = "";

 			// RT
 			String strResrctTypName = "AutoRt_" + strTimeText;
 			String strStatValue = "";
 			// RS
 			String strResource = "AutoRs_" + strTimeText;
 			String strTmText = dts.getCurrentDate("HHmm");
 			String strAbbrv = "A" + strTmText;
 			String strStandResType = "Aeromedical";
 			String strContFName = "auto";
 			String strContLName = "qsg";
 			String strRSValue[] = new String[1];
 			String strResVal = "";

 			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
 			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
 			String strInitPwd = rdExcel.readData("Login", 4, 2);
 			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
 			String strUsrFulName = "autouser";
 			// search user data
 			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
 					strFILE_PATH);
 			String strByResourceType = rdExcel.readInfoExcel("User_Template",
 					7, 12, strFILE_PATH);
 			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
 					13, strFILE_PATH);
 			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
 					14, strFILE_PATH);
 			// Rol
 			String strRoleName1 = "AutoR1_" + strTimeText;
 			String strRoleName2 = "AutoR2_" + strTimeText;
 			String strRoleRights[][] = {};
 			String strRoleValue1 = "";
 			String strRoleValue2 = "";
 			strSTvalue[0] = "";
 			strSTvalue[1] = "";
 		/*
 		 * STEP :
 		  Action:Precondition:							
 			1.Status type 'ST1' (Multi status type) is created by associating the expiration time (say 'T1') and
 			 selecting role 'R1' under view and update right and role 'R2' only under view right for the status type.
 			2.Status type 'ST2' (Multi status type) is created by associating the shift time (say 'S1') and
 			 selecting role 'R1' under view and update right and role 'R2' only under view right for the status type.
 			3.Resource 'RS' is created by proving address under the resource type 'RT', 
 			associating 'ST1' and 'ST2' at the 'RS' level.
 			4.User B is associated with role 'R1'.
 			5.User A has 'User must update overdue status' right and is associated with role 'R1'.
 			6.User A and User B has �Update Status� right on resource 'RS'.
 				 Expected Result:No Expected Result
 	     */
 	        log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
 					
 			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
 					strLoginPassword);

 			// Navigate user default region
 			try {
 				assertEquals("", strFuncResult);
 				blnLogin = true;
 				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName1, strRoleRights,
						strSTvalue, false, strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue1 = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName1);
				if (strRoleValue1.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName2, strRoleRights,
						strSTvalue, false, strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue2 = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName2);
				if (strRoleValue2.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 			// Navigate to status type list

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			// Fill mandatory fields

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
 						strStatusTypeValueExpire, statTypeNameExpire,
 						strStatTypDefn, false);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = {{ strRoleValue1, "true" },{ strRoleValue2, "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue1, "true" },{ strRoleValue2, "false" }};
				strFuncResult = objST.slectAndDeselectRoleInSTNew(seleniumPrecondition, false,
						false, strRoleViewValue, strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
 						statTypeNameExpire);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
 						statTypeNameExpire);
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
 				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
 						seleniumPrecondition, statTypeNameExpire, strStatusNameExpire1,
 						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
 						strExpMn, "", "", true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
 						seleniumPrecondition, statTypeNameExpire, strStatusNameExpire2,
 						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
 						strExpMn, "", "", true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
 						statTypeNameExpire, strStatusNameExpire1);
 				if (strStatValue.compareTo("") != 0) {
 					strFuncResult = "";
 					strStatusValueExpire[0] = strStatValue;
 				} else {
 					strFuncResult = "Failed to fetch status value";
 				}

 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
 						statTypeNameExpire, strStatusNameExpire2);
 				if (strStatValue.compareTo("") != 0) {
 					strFuncResult = "";
 					strStatusValueExpire[1] = strStatValue;
 				} else {
 					strFuncResult = "Failed to fetch status value";
 				}

 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValueShift,
						statTypeNameShift, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue1, "true" },
						{ strRoleValue2, "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue1, "true" },
						{ strRoleValue2, "false" } };
				strFuncResult = objST.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

 			try {
 				assertEquals("", strFuncResult);

 				StatusTime = seleniumPrecondition.getText("css=#statusTime");
 				String strStatusTime[] = StatusTime.split(" ");

 				strUpdTime_Shift = strStatusTime[2];
 				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
 						intShiftTime, "HH:mm");
 				strUpdTime_Shift = strAdUpdTime;
 				log4j.info(strUpdTime_Shift);

 				strStatusTime = strUpdTime_Shift.split(":");

 				// Select shift time
 				seleniumPrecondition.click(propElementDetails
 						.getProperty("StatusType.CreateStatType.ShiftTime1"));
 				seleniumPrecondition.select(
 						propElementDetails
 								.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
 						strStatusTime[0]);
 				seleniumPrecondition.select(
 						propElementDetails
 								.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
 						strStatusTime[1]);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
 						statTypeNameExpire);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
 						statTypeNameShift);
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
 				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
 						statTypeNameShift, strStatusNameShift1,
 						strStatusTypeValueShift, strStatTypeColor, true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
 						statTypeNameShift, strStatusNameShift2,
 						strStatusTypeValueShift, strStatTypeColor, true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
 						statTypeNameShift, strStatusNameShift1);
 				if (strStatValue.compareTo("") != 0) {
 					strFuncResult = "";
 					strStatusValueShift[0] = strStatValue;
 				} else {
 					strFuncResult = "Failed to fetch status value";
 				}

 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
 						statTypeNameShift, strStatusNameShift2);
 				if (strStatValue.compareTo("") != 0) {
 					strFuncResult = "";
 					strStatusValueShift[1] = strStatValue;
 				} else {
 					strFuncResult = "Failed to fetch status value";
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
 						strResrctTypName,
 						"css=input[name='statusTypeID'][value='"
 								+ strSTvalue[0] + "']");
 				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
 						+ strSTvalue[1] + "']");
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
 						strResrctTypName);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

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

 			// Create User A

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
 						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
 						strRoleValue2, true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
 						seleniumPrecondition, strResource, strRSValue[0], false, true,
 						false, true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				String strOptions = propElementDetails
 						.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus");
 				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
 						strOptions, true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
 						seleniumPrecondition, strUserNameA, strByRole, strByResourceType,
 						strByUserInfo, strNameFormat);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			// User B
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
 						strUserNameB, strInitPwd, strConfirmPwd, strUsrFulName);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
 						seleniumPrecondition, strResource, strRSValue[0], false, true,
 						false, true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
 						strRoleValue1, true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
 						seleniumPrecondition, strUserNameB, strByRole, strByResourceType,
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
 		* STEP :
 		  Action:Login as User B, navigate to View >> Map. Select resource 'RS' under 'Find Resource' dropdown 
 		  and update status values of status types 'ST1' and 'ST2' from resource pop up window. 
 		  Expected Result:Status type 'ST1' and 'ST2' are updated and are displayed on 'resource pop up window' screen. 
 		*/

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
 						strInitPwd);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				blnLogin = true;
 				String[] strEventStatType = {};
 				String[] strRoleStatType = { statTypeNameExpire,
 						statTypeNameShift };
 				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
 						selenium, strResource, strEventStatType,
 						strRoleStatType, false, true);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;

 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objViewMap.updateMultiStatusType(selenium,
 						strResource, statTypeNameExpire, strSTvalue[0],
 						strStatusNameExpire1, strStatusValueExpire[0]);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;

 			}

 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objViewMap.updateMultiStatusType(selenium,
 						strResource, statTypeNameShift, strSTvalue[1],
 						strStatusNameShift1, strStatusValueShift[0]);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}		
 		/*
 		* STEP :
 		  Action:After time 'T1'. 
 		  Expected Result: User A do not receive status update prompt with ST1 and ST2 expanded. 
 		*/
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objLogin.logout(selenium);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
 						strInitPwd);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				Thread.sleep(360000);
 				strFuncResult = objViews.varUpdateStatPrompt(selenium,
 						strResource, statTypeNameExpire);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objViews.varUpdateStatPrompt(selenium,
 						strResource, statTypeNameShift);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 		/*
 		* STEP :
 		  Action:After time 'S1'. 
 		  Expected Result:User A do not receive status update prompt with ST1 and ST2 expanded.
 		*/

 			try {
 				assertEquals("", strFuncResult);
 				Thread.sleep(60000);
 				strFuncResult = objViews.varUpdateStatPrompt(selenium,
 						strResource, statTypeNameExpire);
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}
 			try {
 				assertEquals("", strFuncResult);
 				strFuncResult = objViews.varUpdateStatPrompt(selenium,
 						strResource, statTypeNameShift);
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
 			gstrTCID = "44609"; // Test Case Id
 			gstrTO = " Status type ST is associated with resource RS at the resource type level. Verify that a " +
 					"user with �Update Status� right on resource RS does not receive update status prompt when the" +
 					" status of a status type to which the user has view only permission is overdue for resource RS.";// Test Objective
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
  /***************************************************************
  'Description	:Create a multi status type MST, make it as default status type of resource type RT and verify that
                 when the resource of RT is updated with a status value of MST, the resource icon is NOT displayed
                 in the updated status color for a user who does not have view permissions for status type MST.
  'Arguments    :None
  'Returns		:None
  'Date			:12/18/2012
  'Author		:QSG
  '---------------------------------------------------------------
  'Modified Date				Modified By
  'Date					Name
  ***************************************************************/

	@Test
	public void testFTS46172() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		ViewMap objViewMap = new ViewMap();
		try {
			gstrTCID = "46172"; // Test Case Id
			gstrTO = " Create a multi status type MST, make it as default status type of resource"
					+ " type RT and verify that when the resource of RT is updated with a status value"
					+ " of MST, the resource icon is NOT displayed in the updated status color for a user "
					+ "who does not have view permissions for status type MST.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String statrNumTypeName = "NST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[2];
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			// Statuses
			String strStatTypeColor = "Green";
			String str_roleStatusName1 = "rSa" + strTimeTxt;

			String str_roleStatusValue[] = new String[1];
			str_roleStatusValue[0] = "";
			// RT
			String strResrctTypName = "AutoRT" + strTimeText;
			String strRsTypeValues[] = new String[1];
			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValues[] = new String[1];
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";
			// USER
			String strUserNameA = "AutoUsrA" + System.currentTimeMillis();
			String strUserNameB = "AutoUsrB" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

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
  	  Action:Precondition:
            1.Resource 'RS' is created with address under resource Type 'RT' associating 'ST1' at the 'RT' level. 
			2.Role 'R1' is associated with User A. 
			3.User A is having 'Update Status' right on resource 'RS'.
			4.User B not associated with any roles.
	  Expected Result:No Expected Result
  	*/
  
			try {
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

			//status type
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
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statrNumTypeName);
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
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrctTypName, str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrctTypName);
				if (strRsTypeValues[0].compareTo("") != 0) {
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
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
 				assertEquals("", strFuncResult);
 				String strState = "Alabama";
 				String strCountry = "Barbour County";
 				strFuncResult = objResources.createResourceWitLookUPadres(selenium,
 						strResource, strAbbrv, strResrctTypName, "LN",
 						"LN", strState, strCountry, "Hospital");
 			} catch (AssertionError Ae) {
 				gstrReason = strFuncResult;
 			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
				String[] strViewRightValue = { };				
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
			//user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(selenium, strResource, 
						strResVal, false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium, strUserNameA,
						strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameB, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium, strUserNameB,
						strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
  	/*
  	* STEP :
  	  Action:Login as RegAdmin and Navigate to Setup >> Status Type,and Click on 'Create New Status Types' button Select 'Multi' from the 'Select Type' dropdown list and then click on 'Next'.
  	  Expected Result:'Create Multi Status Type' screen is displayed.
  	*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
  	/*
  	* STEP :
  	  Action:Create a status type 'ST' fill all the mandatory data and select a role 'R1' under 'Roles with view rights' and role 'R1' under 'Roles with update rights' sections, then click on 'Save'.
  	  Expected Result:Status type 'ST' is displayed in 'Status Type List' screen.
  	*/
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
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statrMultiTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
  	/*
  	* STEP :
  	  Action:Click on 'statuses' link associated with 'ST' and create statuses 'STS' by filling all the mandatory data and selecting a color (say green) and click on 'Save'.
  	  Expected Result:Status type 'ST' is displayed in 'Status Type List' screen with statuses 'STS' associated with it.
  	*/
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statrMultiTypeName, str_roleStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(selenium, statrMultiTypeName,
								str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
  	/*
  	* STEP :
  	  Action:Navigate to Setup >> Resource type, and click on 'Edit' link associated with 'RT' and select 'ST' under 'Status Types' section and 'Default Status Type' dropdown and click on 'Save'
  	  Expected Result:'RT' is displayed in 'Resource Type List' screen
  	*/

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(selenium, str_roleStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selectDefaultSTInCreateResType(selenium, statrMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium, strResrctTypName);
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
  	  Action:Login as User A and navigate to View >> Map, select resource 'RS' under 'Find Resource' dropdown and update the status value of Status type 'ST' on 'resource pop up window' screen.
  	  Expected Result:The resource icon of 'RS' is displayed in the updated status color in 'Regional Map View' screen.
  	*/	

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				String[] strEventStatType = {};
				String[] strRoleStatType = { statrNumTypeName,
						statrMultiTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statrMultiTypeName,
						str_roleStatusTypeValues[1], str_roleStatusName1,
						str_roleStatusValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
	/*
	* STEP :
	  Action:Login as User B and navigate to View >> Map, select resource 'RS' under 'Find Resource' dropdown.
	  Expected Result:The resource icon of 'RS' is displayed in white color  and not in the updated status color in 'Regional Map View' screen.
	*/

			String strTestData[] = new String[15];
			try {

				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserNameA + "/" + strUserNameB + "/"
						+ strInitPwd;
				strTestData[3] = statrNumTypeName + statrMultiTypeName;
				strTestData[5] = "Verify 6th and 7th step";
				strTestData[6] = strResource;
				strTestData[9] = str_roleStatusName1;
				strTestData[10] = strStatTypeColor;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;

			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

  } catch (Exception e) {
	  gstrTCID = "46172";
	  gstrTO = "Create a multi status type MST, make it as default status type of resource type RT and verify that when the resource of RT is updated with a status value of MST, the resource icon is NOT displayed in the updated status color for a user who does not have view permissions for status type MST.";
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
  /********************************************************************************************************
	'Description		:Create a status type ST selecting a role R1 under 'Roles with view rights' and 
                         Roles with update rights' sections, associate ST with resource RS at the resource 
	                     type level and verify that the user with role R1 and 'Run Report' right on resource 
	                     RS DOES NOT receive expired status notification for resource RS when the status 
	                     of ST expires.
	'Arguments		    :None
	'Returns		    :None
	'Date			    :12/31/2012
	'Author			    :QSG
	'-----------------------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					    Name
	*******************************************************************************************************/

	@Test
	public void testFTS44523() throws Exception {

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		General objMail = new General();
		Preferences objPreferences = new Preferences();
		try {
			gstrTCID = "44523"; // Test Case Id
			gstrTO = "Create a status type ST selecting a role R1 under 'Roles with view rights' and "
					+ "'Roles with update rights' sections, associate ST with resource RS at the resource "
					+ "type level and verify that the user with role R1 and 'Run Report' right on resource "
					+ "RS DOES NOT receive expired status notification for resource RS when the status of "
					+ "ST expires.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";

			int intShiftTime = 15;

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String StatusTime = "";

			String strUpdTime_Shift = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strStatusTypeST = "Number";
			String statTypeNameST = "ST_" + strTimeText;

			// Status Type
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;

			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;

			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";
			String strTmText = dts.getCurrentDate("HHmm");
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strExpHr = "00";
			String strExpMn = "05";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			String strUsrFulNameB = strUserNameB;

			String strRoleValue = "";

			String strSTvalue[] = new String[3];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";
			strSTvalue[1] = "";
			strSTvalue[2] = "";

			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			// time data
			String[] strArFunRes = new String[5];

			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";

			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";

			int intEMailRes_Expire = 0;
			int intPagerRes_Expire = 0;
			int intEMailRes_Shift = 0;
			int intPagerRes_Shift = 0;
			int intResCnt = 0;

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STATRTS~~~~~");
			/*
			 * STEP 1: Action:Preconditions:
			 * 
			 * 1.Status type 'ST1' (Multi status type) is created by associating
			 * the expiration time (say 'T1') and selecting role 'R1' under both
			 * view and update right on the status type.
			 * 
			 * 2.Status type 'ST2' (Multi status type) is created by associating
			 * the shift time (say 'S1') and selecting role 'R1' under both view
			 * and update right on the status type.
			 * 
			 * 3.Resource 'RS' is created by proving address under the resource
			 * type 'RT', associating 'ST1' and 'ST2' at the 'RT' level.
			 * 
			 * 4.User A has subscribed to receive expired status notifications
			 * via E-mail and pager and is associated with role 'R1'.
			 * 
			 * 5.User A has 'Run Report' right on resource 'RS'.
			 * 
			 * 6.User B is also associated with role 'R1' and has 'Update
			 * Status' right on resource 'RS'. Expected Result:No Expected
			 * Result
			 */
			// 260283

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Navigate to status type list

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeNameExpire);
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
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						seleniumPrecondition, statTypeNameExpire, strStatusNameExpire1,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						seleniumPrecondition, statTypeNameExpire, strStatusNameExpire2,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameExpire, strStatusNameExpire1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameExpire, strStatusNameExpire2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				// Select shift time
				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.ShiftTime1"));
				seleniumPrecondition
						.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
								strStatusTime[0]);
				seleniumPrecondition
						.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
								strStatusTime[1]);

				// Save status type
				seleniumPrecondition.click(propElementDetails.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				seleniumPrecondition.click("link=Return to Status Type List");
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeNameShift + "']"));

					log4j.info("Status type " + statTypeNameShift
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								seleniumPrecondition, statTypeNameShift);
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
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeNameShift,
								strStatusNameShift1, strStatusTypeValueShift,
								strStatTypeColor, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeNameShift,
								strStatusNameShift2, strStatusTypeValueShift,
								strStatTypeColor, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
								statTypeNameShift, strStatusNameShift1);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueShift[0] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
								statTypeNameShift, strStatusNameShift2);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueShift[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeNameShift
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeNameShift
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(seleniumPrecondition);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeST, statTypeNameST, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeNameST);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT
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
								+ strSTvalue[2] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
						strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
						strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

			// Create User A
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");
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
				seleniumPrecondition.click("css=input[name=expiredStatusEmailInd]");
				seleniumPrecondition.click("css=input[name=expiredStatusPagerInd]");
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID+ " EXECUTION STATRTS~~~~~");

			/*
			 * STEP 2: Action:Login as User B, navigate to View >> Map. Select
			 * resource 'RS' under 'Find Resource' dropdown and update status
			 * values of status types 'ST1' and 'ST2' from resource pop up
			 * window. Expected Result:Status type 'ST1' and 'ST2' are updated
			 * and are displayed on 'resource pop up window' screen
			 */
			// 260284

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[0],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[1],
						strStatusNameShift1, strStatusValueShift[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 3: Action:After time 'T1'. Expected Result:E-mail and pager
			 * notifications are not received by User A.
			 */
			// 260285

			/*
			 * STEP 4: Action:After time 'S1'. Expected Result:E-mail and pager
			 * notifications are not received by User A.
			 */
			// 260286

			try {
				assertEquals("", strFuncResult);

				String strElementId = "//span[text()='" + strStatusNameExpire1
						+ "']/following-sibling::span[@class='overdue'][1]";
				strFuncResult = objMail.waitForMailNotification(selenium, 310,
						strElementId);
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

				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intPagerRes_Expire++;

						strSubjName = "EMResource Expired Status Notification: "
								+ strResource;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Expire++;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Expire++;

						strFuncResult = objMail.backToMailInbox(selenium,
								false, true, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult.equals(""));

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// check Email, pager notification
					if (intEMailRes_Expire == 2 && intPagerRes_Expire == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs_1 = strArFunRes[2];
				strGenTimeMin_1 = strArFunRes[3];
				strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				int intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
						strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
						60000);

				if ((intShiftTime - intTimeDiffOutPut) > 0) {
					intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
					intTimeDiffOutPut = intTimeDiffOutPut + 1;
					intTimeDiffOutPut = intTimeDiffOutPut * 60;
					try {
						assertEquals("", strFuncResult);

						String strElementId = "//span[text()='"
								+ strStatusNameShift1
								+ "']/following-sibling::span[@class='overdue'][1]";
						strFuncResult = objMail.waitForMailNotification(
								selenium, intTimeDiffOutPut, strElementId);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intPagerRes_Shift++;

						strSubjName = "EMResource Expired Status Notification: "
								+ strResource;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Shift++;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Shift++;

						strFuncResult = objMail.backToMailInbox(selenium,
								false, true, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult.equals(""));

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// check Email, pager notification
					if (intEMailRes_Shift == 2 && intPagerRes_Shift == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(2000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertEquals("", gstrReason);
				if (intResCnt == 2) {
					gstrResult = "PASS";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "44523";
			gstrTO = "Create a status type ST selecting a role R1 under 'Roles with view rights' and 'Roles "
					+ "with update rights' sections, associate ST with resource RS at the resource type level "
					+ "and verify that the user with role R1 and 'Run Report' right on resource RS DOES NOT "
					+ "receive expired status notification for resource RS when the status of ST expires.";
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

	/*********************************************************************************************************
	* 'Description :Status type ST is associated with resource RS at the resource level. Verify that a 
	*               user with �Run Report� right on resource RS but without view/update permissions for status 
	*               type ST DOES NOT receive expired status notifications when the status of ST expires.

	 'Arguments	   :None 
	 'Returns 	   :None 
	 'Date 		   :31-Dec-2012 
	 'Author       :QSG
	 '-----------------------------------------------------------------------------------------
	 'Modified Date 							Modified By 	
	 '											 <Name> 
	 ***************************************************************************************/
	@Test
	public void testFTS44613() throws Exception {

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		General objMail = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();

		try {
			gstrTCID = "44613";
			gstrTO = "Status type ST is associated with resource RS at the resource level."
					+ " Verify that a user with �Run Report� right on resource RS but without"
					+ " view/update permissions for status type ST DOES NOT receive expired "
					+ "status notifications when the status of ST expires";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			int intShiftTime = 15;

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String StatusTime = "";

			String strUpdTime_Shift = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strStatusTypeST = "Number";
			String statTypeNameST = "ST_" + strTimeText;

			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;

			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;

			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";
			String strTmText = dts.getCurrentDate("HHmm");
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strExpHr = "00";
			String strExpMn = "05";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			String strUsrFulNameB = strUserNameB;

			String strRoleValue = "";

			String strSTvalue[] = new String[3];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};

			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			int intEMailRes_Expire = 0;
			int intPagerRes_Expire = 0;
			int intEMailRes_Shift = 0;
			int intPagerRes_Shift = 0;
			int intResCnt = 0;

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";

			// time data
			String[] strArFunRes = new String[5];

			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";

			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");
		/*
		 * Preconditions:1.Status type 'ST1' (Multi status type) is created by associating the expiration 
		 * time (say 'T1') and selecting only role 'R1' under view and update right for the status type.		
			2.Status type 'ST2' (Multi status type) is created by associating the shift time (say 'S1') 
			and selecting only role 'R1' under view and update right for the status type.		
			3.Resource 'RS' is created by proving address under the resource type 'RT', associating 'ST1' 
			and 'ST2' at the 'RS' level.		
			4.User B is associated with role 'R1'.		
			5.User A has subscribed to receive expired status notifications via E-mail and pager and is 
			not associated with any role.		
			6.User A has �Run Report� right on resource 'RS'.		
			7.User B has �Update Status� right on resource 'RS'. 
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
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);
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
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(selenium,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeNameExpire);
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
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						selenium, statTypeNameExpire, strStatusNameExpire1,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						selenium, statTypeNameExpire, strStatusNameExpire2,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameExpire, strStatusNameExpire1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameExpire, strStatusNameExpire2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				// Select shift time
				selenium.click(propElementDetails
						.getProperty("StatusType.CreateStatType.ShiftTime1"));
				selenium
						.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
								strStatusTime[0]);
				selenium
						.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
								strStatusTime[1]);

				// Save status type
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				selenium.click("link=Return to Status Type List");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeNameShift + "']"));

					log4j.info("Status type " + statTypeNameShift
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								selenium, statTypeNameShift);
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
						strFuncResult = objST.createSTWithinMultiTypeST(
								selenium, statTypeNameShift,
								strStatusNameShift1, strStatusTypeValueShift,
								strStatTypeColor, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(
								selenium, statTypeNameShift,
								strStatusNameShift2, strStatusTypeValueShift,
								strStatTypeColor, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(selenium,
								statTypeNameShift, strStatusNameShift1);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueShift[0] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(selenium,
								statTypeNameShift, strStatusNameShift2);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueShift[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeNameShift
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeNameShift
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeST, statTypeNameST, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeNameST);
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
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[2] + "']");

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
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,
						strSTvalue[0], true);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,
						strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

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
						selenium, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create User A
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);
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
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click("css=input[name=expiredStatusEmailInd]");
				selenium.click("css=input[name=expiredStatusPagerInd]");
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/*
			 * 2 Login as User B, navigate to View >> Map. Select resource 'RS'
			 * under 'Find Resource' dropdown and update status values of status
			 * types 'ST1' and 'ST2' from resource pop up window. Status type
			 * 'ST1' and 'ST2' are updated and are displayed on 'resource pop up
			 * window' screen
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[0],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[1],
						strStatusNameShift1, strStatusValueShift[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementId = "//span[text()='" + strStatusNameExpire1
						+ "']/following-sibling::span[@class='overdue'][1]";
				strFuncResult = objMail.waitForMailNotification(selenium, 310,
						strElementId);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intPagerRes_Expire++;

						strSubjName = "EMResource Expired Status Notification: "
								+ strResource;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Expire++;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Expire++;

						strFuncResult = objMail.backToMailInbox(selenium,
								false, true, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult.equals(""));

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// check Email, pager notification
					if (intEMailRes_Expire == 2 && intPagerRes_Expire == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs_1 = strArFunRes[2];
				strGenTimeMin_1 = strArFunRes[3];
				strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				int intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
						strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
						60000);

				if ((intShiftTime - intTimeDiffOutPut) > 0) {
					intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
					intTimeDiffOutPut = intTimeDiffOutPut + 1;
					intTimeDiffOutPut = intTimeDiffOutPut * 60;
					try {
						assertEquals("", strFuncResult);

						String strElementId = "//span[text()='"
								+ strStatusNameShift1
								+ "']/following-sibling::span[@class='overdue'][1]";
						strFuncResult = objMail.waitForMailNotification(
								selenium, intTimeDiffOutPut, strElementId);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intPagerRes_Shift++;

						strSubjName = "EMResource Expired Status Notification: "
								+ strResource;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Shift++;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Shift++;

						strFuncResult = objMail.backToMailInbox(selenium,
								false, true, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult.equals(""));

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// check Email, pager notification
					if (intEMailRes_Shift == 2 && intPagerRes_Shift == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(2000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertEquals("", gstrReason);
				if (intResCnt == 2) {
					gstrResult = "PASS";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "44613";
			gstrTO = "Status type ST is associated with resource RS at the resource level."
					+ " Verify that a user with �Run Report� right on resource RS but without"
					+ " view/update permissions for status type ST DOES NOT receive expired "
					+ "status notifications when the status of ST expires";
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
	/****************************************************************************************************
	'Description	:Create a status type ST selecting a role R1 under 'Roles with view rights' section, 
	'				 associate ST with resource RS at the resource type level and verify that the user 
	'				 with role R1 and 'Run Report' right on resource RS DOES NOT receive expired status 
	'                notification for resource RS when the status of ST expires.
	'Arguments		:None
	'Returns		:None
	'Date			:12/31/2012
	'Author			:QSG
	'--------------------------------------------------------------------------------------------------
	'Modified Date				                                                        Modified By
	'Date					                                                            Name
	***************************************************************************************************/

	@Test
	public void testFTS44571() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		General objMail=new General();
		Roles objRole = new Roles();
		
		try{	
			gstrTCID = "44571";	//Test Case Id	
			gstrTO = "Create a status type ST selecting a role R1 under 'Roles with view rights' section, " +
					 "associate ST with resource RS at the resource type level and verify that the user " +
					 "with role R1 and 'Run Report' right on resource RS DOES NOT receive expired status " +
					 "notification for resource RS when the status of ST expires.";//Test Objective
			gstrReason = "";
			gstrResult = "FAIL";	
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel")); // relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			
			int intShiftTime = 25;

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
		    String strRegn = rdExcel.readData("Login", 3, 4);

			String StatusTime = "";

			String strUpdTime_Shift = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);

			String strStatusTypeST = "Number";
			String statTypeNameST = "ST_" + strTimeText;
			
			// Status Type
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;

			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;

			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";
			String strStatValue = "";
			String strTmText = dts.getCurrentDate("HHmm");
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "A"+strTmText;
			String strExpHr = "00";
			String strExpMn = "05";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			String strUsrFulNameB = strUserNameB;

			String strRoleValue[] = new String[2];

			String strSTvalue[] = new String[3];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName1 = "AutoR1_" + strTimeText;
			String strRoleName2 = "AutoR2_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";
			strSTvalue[1] = "";
			strSTvalue[2] = "";
			
			// time data
			String[] strArFunRes = new String[5];

			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";

			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";

			int intEMailRes_Expire = 0;
			int intPagerRes_Expire = 0;
			int intEMailRes_Shift = 0;
			int intPagerRes_Shift = 0;
			int intResCnt = 0;
			
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
								
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");
			
		/*Preconditions:1.Status type 'ST1' (Multi status type) is created by associating the expiration time 
			   (say 'T1') and selecting only role 'R1' under view right and role 'R2' under both view 
			   and update right section.
			 2.Status type 'ST2' (Multi status type) is created by associating the shift time (say 'S1') 
			   and selecting only role 'R1' under view right and role 'R2' under both view and update right section.
			 3.Resource 'RS' is created by proving address under the resource type 'RT', associating 'ST1' 
			   and 'ST2' at the 'RT' level.
			 4.User B is associated with role 'R2'.
			 5.User A has subscribed to receive expired status notifications via E-mail and pager and is 
			   associated with a role 'R1'.
			 6.User B has 'Update Status' right on resource 'RS'.
			 7.User A has 'Run Report' right on resource 'RS'			 
		  Expected Result:No Expected Result
		  	 */
			
			 strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);			
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//R1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName1, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName1);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			//R2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName2, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[1] = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName2);
				if (strRoleValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Navigate to status type list

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//ST1
			// Fill mandatory fields
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//selecting only role 'R1' under view right 
			//role 'R2' under both view and update right section
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = {{ strRoleValue[1],"true"}};
				String[][] strRoleUpdateValue = {{strRoleValue[1],"true"}};
				strFuncResult = objST.slectAndDeselectRoleInSTNew(selenium, false,
						false, strRoleViewValue,strRoleUpdateValue, false);
				selenium.click("css=input[name='roleView'][value='"
						+ strRoleValue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(selenium,statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, statTypeNameExpire);
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
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						selenium, statTypeNameExpire,
						strStatusNameExpire1, strStatusTypeValueExpire,
						strStatTypeColor, strExpHr, strExpMn, "", "",
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						selenium, statTypeNameExpire,
						strStatusNameExpire2, strStatusTypeValueExpire,
						strStatTypeColor, strExpHr, strExpMn, "", "",
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameExpire, strStatusNameExpire1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameExpire, strStatusNameExpire2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//ST2
			// Fill mandatory fields
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//selecting only role 'R1' under view right 
			//role 'R2' under both view and update right section
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = {{ strRoleValue[1],"true"}};
				String[][] strRoleUpdateValue = {{strRoleValue[1],"true"}};
				strFuncResult = objST.slectAndDeselectRoleInSTNew(selenium, false,
						false, strRoleViewValue,strRoleUpdateValue, false);
				selenium.click("css=input[name='roleView'][value='"
						+ strRoleValue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				// Select shift time
				selenium.click(propElementDetails
						.getProperty("StatusType.CreateStatType.ShiftTime1"));
				selenium
						.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
								strStatusTime[0]);
				selenium
						.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
								strStatusTime[1]);

				// Save status type
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				selenium.click("link=Return to Status Type List");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeNameShift + "']"));

					log4j.info("Status type " + statTypeNameShift
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								selenium, statTypeNameShift);
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
						strFuncResult = objST.createSTWithinMultiTypeST(
								selenium, statTypeNameShift,
								strStatusNameShift1, strStatusTypeValueShift,
								strStatTypeColor, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(
								selenium, statTypeNameShift,
								strStatusNameShift2, strStatusTypeValueShift,
								strStatTypeColor, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(selenium,
								statTypeNameShift, strStatusNameShift1);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueShift[0] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(selenium,
								statTypeNameShift, strStatusNameShift2);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueShift[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeNameShift
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeNameShift
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeST, statTypeNameST, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, statTypeNameST);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//RT
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
								+ strSTvalue[2] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(selenium, strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(selenium, strSTvalue[1], true);
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
					strFuncResult = "Failed to fetch RT value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//RS
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
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Create User A
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				selenium.click("css=input[name=expiredStatusEmailInd]");
				selenium.click("css=input[name=expiredStatusPagerInd]");
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue[1], true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
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
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID+ " EXECUTION STATRTS~~~~~");
			
		/* STEP 2:
		 * Action:Login as User B, navigate to View >> Map. Select resource 'RS' under 'Find Resource'
		   dropdown and update status values of status types 'ST1' and 'ST2' from resource pop up window.
		   Expected Result:Status type 'ST1' and 'ST2' are updated and are  displayed on 'resource pop up
		   window' screen
		*/
		//260878

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[0],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[1],
						strStatusNameShift1, strStatusValueShift[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			* STEP 3:
			  Action:After time 'T1'.
			  Expected Result:E-mail and pager notifications are not received by User A.
			*/
			//260879
	
			/*
			* STEP 4:
			  Action:After time 'S1'.
			  Expected Result:E-mail and pager notifications are not received by User A.
			*/
			//260880
			
			try {
				assertEquals("", strFuncResult);
				String strElementId = "//span[text()='" + strStatusNameExpire1
						+ "']/following-sibling::span[@class='overdue']";
				strFuncResult = objMail.waitForMailNotification(selenium, 310,
						strElementId);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intPagerRes_Expire++;
						strSubjName = "EMResource Expired Status Notification: "
								+ strResource;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Expire++;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Expire++;
						strFuncResult = objMail.backToMailInbox(selenium,
								false, true, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// check Email, pager notification
					if (intEMailRes_Expire == 2 && intPagerRes_Expire == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenTimeHrs_1 = strArFunRes[2];
				strGenTimeMin_1 = strArFunRes[3];
				strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				int intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
						strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
						60000);
				if ((intShiftTime - intTimeDiffOutPut) > 0) {
					intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
					intTimeDiffOutPut = intTimeDiffOutPut + 1;
					intTimeDiffOutPut = intTimeDiffOutPut * 60;
					try {
						assertEquals("", strFuncResult);
						String strElementId = "//span[text()='"
								+ strStatusNameShift1
								+ "']/following-sibling::span[@class='overdue']";
						strFuncResult = objMail.waitForMailNotification(
								selenium, intTimeDiffOutPut, strElementId);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intPagerRes_Shift++;
						strSubjName = "EMResource Expired Status Notification: "
								+ strResource;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Shift++;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Shift++;
						strFuncResult = objMail.backToMailInbox(selenium,
								false, true, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// check Email, pager notification
					if (intEMailRes_Shift == 2 && intPagerRes_Shift == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.refreshPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertEquals("", gstrReason);
				if (intResCnt == 2) {
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "44571";
			gstrTO = "Create a status type ST selecting a role R1 under 'Roles with view rights' section, "
					+ "associate ST with resource RS at the resource type level and verify that the user with "
					+ "role R1 and 'Run Report' right on resource RS DOES NOT receive expired status notification "
					+ "for resource RS when the status of ST expires.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
		if (blnLogin) {
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
	}
 /**********************************************************************************************************
 'Description  :Status type ST is associated with resource RS at the resource type level.Verify that
                a user with �Run Report� right on resource RS but without view/update permissions for
                status type ST DOES NOT receive expired status notifications when the status of ST expires.
 'Arguments    :None
 'Returns	   :None
 'Date		   :12/11/2012
 'Author	   :QSG
 '----------------------------------------------------------------------------------------------------------
 'Modified Date				                                                                 Modified By
 'Date					                                                                     Name
 **********************************************************************************************************/

	@Test
	public void testFTS44601() throws Exception {

		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		General objMail = new General();
		
		try {
			gstrTCID = "44601"; // Test Case Id
			gstrTO = "Status type ST is associated with resource RS at the resource type level.Verify that"
					+ "a user with �Run Report� right on resource RS but without view/update permissions for"
					+ "status type ST DOES NOT receive expired status notifications when the status of ST expires.";// Test
																													// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status Type
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;
			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;
			String strSTvalue[] = new String[2];
			String strStatTypDefn = "Auto";

			String strExpHr = "00";
			String strExpMn = "05";
			String StatusTime = "";
			String strUpdTime_Shift = "";
			int intShiftTime = 15;

			String strStatTypeColor = "Black";
			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			strStatusValueShift[0] = "";
			strStatusValueShift[1] = "";

			strStatusValueExpire[0] = "";
			strStatusValueExpire[1] = "";

			// RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			String strResVal = "";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// Rol
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			strSTvalue[0] = "";
			strSTvalue[1] = "";

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			// time data
			String[] strArFunRes = new String[5];

			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";

			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";

			int intEMailRes_Expire = 0;
			int intPagerRes_Expire = 0;
			int intEMailRes_Shift = 0;
			int intPagerRes_Shift = 0;
			int intResCnt = 0;
	 	/*
	 	* STEP :
	 	  Action:Precondition:	
	 	  	1.Status type 'ST1' (Multi status type) is created by associating the expiration time (say 'T1')
	 	  	 and selecting only role 'R1' under view and update right for the status type.
			2.Status type 'ST2' (Multi status type) is created by associating the shift time (say 'S1')
			 and selecting only role 'R1' under view and update right for the status type.
			3.Resource 'RS' is created by proving address under the resource type 'RT', associating 'ST1'
			 and 'ST2' at the 'RT' level.
			4.User B is associated with role 'R1'.
			5.User A has subscribed to receive expired status notifications via E-mail and pager and is
			 not associated with any role.
			6.User A has �Run Report� right on resource 'RS'.
			7.User B has �Update Status� right on resource 'RS'.

	 			 Expected Result:No Expected Result
	 	*/
	      log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);
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
			// Navigate to status type list

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(selenium,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeNameExpire);
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
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						selenium, statTypeNameExpire, strStatusNameExpire1,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						selenium, statTypeNameExpire, strStatusNameExpire2,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameExpire, strStatusNameExpire1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameExpire, strStatusNameExpire2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				StatusTime = selenium.getText("css=#statusTime");
				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				// Select shift time
				selenium.click(propElementDetails
						.getProperty("StatusType.CreateStatType.ShiftTime1"));
				selenium.select(
						propElementDetails
								.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
						strStatusTime[0]);
				selenium.select(
						propElementDetails
								.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
						strStatusTime[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(selenium,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeNameShift);
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
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						statTypeNameShift, strStatusNameShift1,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						statTypeNameShift, strStatusNameShift2,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameShift, strStatusNameShift1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameShift, strStatusNameShift2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[1] = strStatValue;
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
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[1] + "']");
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
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create User A

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click("css=input[name=expiredStatusEmailInd]");
				selenium.click("css=input[name=expiredStatusPagerInd]");
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User B
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameB, strInitPwd, strConfirmPwd, strUsrFulName);
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
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
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID+ " EXECUTION STATRTS~~~~~");
	 	/*
	 	* STEP :
	 	  Action: 	Login as User B, navigate to View >> Map. Select resource 'RS' under 'Find Resource' 
	 	  dropdown and update status values of status types 'ST1' and 'ST2' from resource pop up window. 
	 	  Expected Result:Status type 'ST1' and 'ST2' are updated and are displayed on 'resource pop up window' screen 
	 	*/

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[0],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[1],
						strStatusNameShift1, strStatusValueShift[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
	 	/*
	 	* STEP :
	 	  Action:After time 'T1'. 
	 	  Expected Result:E-mail and pager notifications are not received by User A. 
	 	*/			

	 	/*
	 	* STEP :
	 	  Action:After time 'S1'. 
	 	  Expected Result:E-mail and pager notifications are not received by User A. 
	 	*/

			try {
				assertEquals("", strFuncResult);
				String strElementId = "//span[text()='" + strStatusNameExpire1
						+ "']/following-sibling::span[@class='overdue']";
				strFuncResult = objMail.waitForMailNotification(selenium, 310,
						strElementId);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intPagerRes_Expire++;
						strSubjName = "EMResource Expired Status Notification: "
								+ strResource;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Expire++;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Expire++;
						strFuncResult = objMail.backToMailInbox(selenium,
								false, true, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// check Email, pager notification
					if (intEMailRes_Expire == 2 && intPagerRes_Expire == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenTimeHrs_1 = strArFunRes[2];
				strGenTimeMin_1 = strArFunRes[3];
				strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				int intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
						strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
						60000);
				if ((intShiftTime - intTimeDiffOutPut) > 0) {
					intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
					intTimeDiffOutPut = intTimeDiffOutPut + 1;
					intTimeDiffOutPut = intTimeDiffOutPut * 60;
					try {
						assertEquals("", strFuncResult);
						String strElementId = "//span[text()='"
								+ strStatusNameShift1
								+ "']/following-sibling::span[@class='overdue']";
						strFuncResult = objMail.waitForMailNotification(
								selenium, intTimeDiffOutPut, strElementId);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intPagerRes_Shift++;
						strSubjName = "EMResource Expired Status Notification: "
								+ strResource;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Shift++;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Shift++;
						strFuncResult = objMail.backToMailInbox(selenium,
								false, true, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// check Email, pager notification
					if (intEMailRes_Shift == 2 && intPagerRes_Shift == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.refreshPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertEquals("", gstrReason);
				if (intResCnt == 2) {
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		 			
	 		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
	 		log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "44601"; // Test Case Id
			gstrTO = "Status type ST is associated with resource RS at the resource type level.Verify that"
					+ "a user with �Run Report� right on resource RS but without view/update permissions for"
					+ "status type ST DOES NOT receive expired status notifications when the status of ST expires.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}

	}

	
	/**********************************************************************************************************
	 'Description  :Create a status type ST selecting a role R1 under �Roles with view rights� and �Roles with
	                update rights� sections, associate ST with resource RS at the resource level and verify 
	                that the user with role R1 and �Run Report� right on resource RS DOES NOT receive
	                expired status notification for resource RS when the status of ST expires.
	 'Arguments    :None
	 'Returns	   :None
	 'Date		   :12/11/2012
	 'Author	   :QSG
	 '---------------------------------------------------------------
	 'Modified Date				                Modified By
	 'Date					                      Name
	 **********************************************************************************************************/

	@Test
	public void testFTS44610() throws Exception {

		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		General objMail = new General();
		try {
			gstrTCID = "44610"; // Test Case Id
			gstrTO = "Create a status type ST selecting a role R1 under �Roles with view rights� and �Roles with"+
	                "update rights� sections, associate ST with resource RS at the resource level and verify "+
	                "that the user with role R1 and �Run Report� right on resource RS DOES NOT receive"+
	                "expired status notification for resource RS when the status of ST expires.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status Type
			String strStatusTypeST = "Number";
			String statTypeNameST = "ST_" + strTimeText;
			
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;
			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;
			String strSTvalue[] = new String[3];
			String strStatTypDefn = "Auto";

			String strExpHr = "00";
			String strExpMn = "05";
			String StatusTime = "";
			String strUpdTime_Shift = "";
			int intShiftTime = 20;

			String strStatTypeColor = "Black";
			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			strStatusValueShift[0] = "";
			strStatusValueShift[1] = "";

			strStatusValueExpire[0] = "";
			strStatusValueExpire[1] = "";

			// RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			String strResVal = "";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// Rol
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			strSTvalue[1] = "";
			strSTvalue[2] = "";	
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			// time data
			String[] strArFunRes = new String[5];

			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";

			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";

			int intEMailRes_Expire = 0;
			int intPagerRes_Expire = 0;
			int intEMailRes_Shift = 0;
			int intPagerRes_Shift = 0;
			int intResCnt = 0;
	 	/*
	 	* STEP :
	 	  Action:Precondition:	
	 	  	    1.Status type 'ST1' (Multi status type) is created by associating the expiration time (say 'T1')
	 	  	      and selecting role 'R1' under both view and update right on the status type.
              2.Status type 'ST2' (Multi status type) is created by associating the shift time (say 'S1')
                and selecting role 'R1' under both view and update right on the status type.
              3.Resource 'RS' is created by proving address under the resource type 'RT', associating 'ST1'
                and 'ST2' at the 'RS' level.
              4.User A has subscribed to receive expired status notifications via E-mail and pager
                and is associated with role 'R1'.
              5.User A has �Run Report� right on resource 'RS'.
              6.User B is also associated with role 'R1' and has �Update Status� right on resource 'RS'. 
	 	  Expected Result:No Expected Result
	 	*/
	      log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
	 			
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);
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
			// Navigate to status type list

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeST, statTypeNameST,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(selenium, statTypeNameST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeNameST);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//MST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(selenium,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeNameExpire);
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
						selenium, statTypeNameExpire, strStatusNameExpire1,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						selenium, statTypeNameExpire, strStatusNameExpire2,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameExpire, strStatusNameExpire1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameExpire, strStatusNameExpire2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			// MST2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				StatusTime = selenium.getText("css=#statusTime");
				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				// Select shift time
				selenium.click(propElementDetails
						.getProperty("StatusType.CreateStatType.ShiftTime1"));
				selenium.select(
						propElementDetails
								.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
						strStatusTime[0]);
				selenium.select(
						propElementDetails
								.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
						strStatusTime[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(selenium,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeNameShift);
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
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						statTypeNameShift, strStatusNameShift1,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						statTypeNameShift, strStatusNameShift2,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameShift, strStatusNameShift1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameShift, strStatusNameShift2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[1] = strStatValue;
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
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
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
          //RS
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
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);
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
				strFuncResult = objRs.navToEditResLevelSTPage(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(selenium, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium, strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Create User A

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
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
				selenium.click("css=input[name=expiredStatusEmailInd]");
				selenium.click("css=input[name=expiredStatusPagerInd]");
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User B
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameB, strInitPwd, strConfirmPwd, strUsrFulName);
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
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
	 	* STEP :
	 	  Action: 	Login as User B, navigate to View >> Map. Select resource 'RS' under 'Find Resource' 
	 	  dropdown and update status values of status types 'ST1' and 'ST2' from resource pop up window. 
	 	  Expected Result:Status type 'ST1' and 'ST2' are updated and are displayed on 'resource pop up window' screen 
	 	*/

	 			try {
	 				assertEquals("", strFuncResult);
	 				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
	 						strInitPwd);
	 			} catch (AssertionError Ae) {
	 				gstrReason = strFuncResult;
	 			}
	 			try {
	 				assertEquals("", strFuncResult);
	 				String[] strEventStatType = {};
	 				String[] strRoleStatType = { statTypeNameExpire,
	 						statTypeNameShift };
	 				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
	 						selenium, strResource, strEventStatType,
	 						strRoleStatType, false, true);
	 			} catch (AssertionError Ae) {
	 				gstrReason = strFuncResult;

	 			}

	 			try {
	 				assertEquals("", strFuncResult);
	 				strFuncResult = objViewMap.updateMultiStatusType(selenium,
	 						strResource, statTypeNameExpire, strSTvalue[1],
	 						strStatusNameExpire1, strStatusValueExpire[0]);
	 			} catch (AssertionError Ae) {
	 				gstrReason = strFuncResult;

	 			}

	 			try {
	 				assertEquals("", strFuncResult);
	 				strFuncResult = objViewMap.updateMultiStatusType(selenium,
	 						strResource, statTypeNameShift, strSTvalue[2],
	 						strStatusNameShift1, strStatusValueShift[0]);
	 			} catch (AssertionError Ae) {
	 				gstrReason = strFuncResult;
	 			}			
	 	/*
	 	* STEP :
	 	  Action:After time 'T1'. 
	 	  Expected Result:E-mail and pager notifications are not received by User A. 
	 	*/			

	 	/*
	 	* STEP :
	 	  Action:After time 'S1'. 
	 	  Expected Result:E-mail and pager notifications are not received by User A. 
	 	*/

	 			try {
					assertEquals("", strFuncResult);
					String strElementId = "//span[text()='" + strStatusNameExpire1
							+ "']/following-sibling::span[@class='overdue']";
					strFuncResult = objMail.waitForMailNotification(selenium, 310,
							strElementId);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
							strLoginName, strPassword);
					try {
						assertTrue(strFuncResult.equals(""));
						strSubjName = "EMResource Expired Status: " + strAbbrv;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFuncResult
									.equals("The mail with subject " + strSubjName
											+ " is NOT present in the inbox"));
							intPagerRes_Expire++;
							strSubjName = "EMResource Expired Status Notification: "
									+ strResource;
							strFuncResult = objMail.verifyEmail(selenium, strFrom,
									strTo, strSubjName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertTrue(strFuncResult
									.equals("The mail with subject " + strSubjName
											+ " is NOT present in the inbox"));
							intEMailRes_Expire++;
							strFuncResult = objMail.verifyEmail(selenium, strFrom,
									strTo, strSubjName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertTrue(strFuncResult
									.equals("The mail with subject " + strSubjName
											+ " is NOT present in the inbox"));
							intEMailRes_Expire++;
							strFuncResult = objMail.backToMailInbox(selenium,
									false, true, false);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertTrue(strFuncResult.equals(""));
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						// check Email, pager notification
						if (intEMailRes_Expire == 2 && intPagerRes_Expire == 1) {
							intResCnt++;
						}
						selenium.selectWindow("");
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strArFunRes = objMail.getSnapTime(selenium);
					strFuncResult = strArFunRes[4];
					strGenTimeHrs_1 = strArFunRes[2];
					strGenTimeMin_1 = strArFunRes[3];
					strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					int intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
							strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
							60000);
					if ((intShiftTime - intTimeDiffOutPut) > 0) {
						intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
						intTimeDiffOutPut = intTimeDiffOutPut + 1;
						intTimeDiffOutPut = intTimeDiffOutPut * 60;
						try {
							assertEquals("", strFuncResult);
							String strElementId = "//span[text()='"
									+ strStatusNameShift1
									+ "']/following-sibling::span[@class='overdue']";
							strFuncResult = objMail.waitForMailNotification(
									selenium, intTimeDiffOutPut, strElementId);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
							strLoginName, strPassword);
					try {
						assertTrue(strFuncResult.equals(""));
						strSubjName = "EMResource Expired Status: " + strAbbrv;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult
									.equals("The mail with subject " + strSubjName
											+ " is NOT present in the inbox"));
							intPagerRes_Shift++;
							strSubjName = "EMResource Expired Status Notification: "
									+ strResource;
							strFuncResult = objMail.verifyEmail(selenium, strFrom,
									strTo, strSubjName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertTrue(strFuncResult
									.equals("The mail with subject " + strSubjName
											+ " is NOT present in the inbox"));
							intEMailRes_Shift++;
							strFuncResult = objMail.verifyEmail(selenium, strFrom,
									strTo, strSubjName);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertTrue(strFuncResult
									.equals("The mail with subject " + strSubjName
											+ " is NOT present in the inbox"));
							intEMailRes_Shift++;
							strFuncResult = objMail.backToMailInbox(selenium,
									false, true, false);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertTrue(strFuncResult.equals(""));
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						// check Email, pager notification
						if (intEMailRes_Shift == 2 && intPagerRes_Shift == 1) {
							intResCnt++;
						}
						selenium.selectWindow("");
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objMail.refreshPage(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					assertEquals("", gstrReason);
					if (intResCnt == 2) {
						gstrResult = "PASS";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
	 			
	 		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
	 		log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "44610"; // Test Case Id
			gstrTO = "Create a status type ST selecting a role R1 under �Roles with view rights� and �Roles with"
					+ "update rights� sections, associate ST with resource RS at the resource level and verify "
					+ "that the user with role R1 and �Run Report� right on resource RS DOES NOT receive"
					+ "expired status notification for resource RS when the status of ST expires.";// Test
																									// Objective
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}

	}
	/***********************************************************************************************************
	 'Description  :Create a status type ST selecting a role R1 under �Roles with view rights� section,associate
	                ST with resource RS at the resource level and verify that the user with role R1 and �Run
	                Report� right on resource RS DOES NOT receive expired status notification for resource 
	                RS when the status of ST expires.
	 'Arguments    :None
	 'Returns	   :None
	 'Date		   :25/07/2013
	 'Author	   :QSG
	 '---------------------------------------------------------------------------------------------------------
	 'Modified Date				                                                            Modified By
	 'Date					                                                                Name
	 **********************************************************************************************************/

	@Test
	public void testFTS44612() throws Exception {

		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		General objMail = new General();
		try {
			gstrTCID = "44612"; // Test Case Id
			gstrTO = "Create a status type ST selecting a role R1 under �Roles with view rights� section, "
					+ "associate ST with resource RS at the resource level and verify that the user with role R1 "
					+ "and �Run Report� right on resource RS DOES NOT receive expired status notification for"
					+ " resource RS when the status of ST expires.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status Type
			String strStatusTypeST = "Number";
			String strStatTypDefn = "Auto";
			String statTypeNameST = "ST_" + strTimeText;
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;
			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;
			String strSTvalue[] = new String[3];
			strSTvalue[1] = "";
			strSTvalue[2] = "";

			String strExpHr = "00";
			String strExpMn = "05";
			String StatusTime = "";
			String strUpdTime_Shift = "";
			int intShiftTime = 20;

			String strStatTypeColor = "Black";
			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;
			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;
			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];
			strStatusValueShift[0] = "";
			strStatusValueShift[1] = "";
			strStatusValueExpire[0] = "";
			strStatusValueExpire[1] = "";

			// RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			String strResVal = "";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// Rol
			String strRoleName1 = "AutoR1_" + strTimeText;
			String strRoleName2 = "AutoR2_" + strTimeText;
			String strRoleRights[][] = {};
			String[] strRoleValue = new String[2];

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			// time data
			String[] strArFunRes = new String[5];
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";
			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";

			int intEMailRes_Expire = 0;
			int intPagerRes_Expire = 0;
			int intEMailRes_Shift = 0;
			int intPagerRes_Shift = 0;
			int intResCnt = 0;
	 	/*
	 	* STEP :
	 	  Action:Precondition:	
			1.Status type 'ST1' (Multi status type) is created by associating the expiration time (say 'T1')
			   and selecting role 'R1' under view right and role 'R2' under both view and update right section.
			2.Status type 'ST2' (Multi status type) is created by associating the shift time (say 'S1') and 
			   selecting role 'R1' under view right and role 'R2' under both view and update right section.
			3.Resource 'RS' is created by proving address under the resource type 'RT', associating 'ST1' 
			   and 'ST2' at the 'RS' level.
			4.User B is associated with role 'R2'.
			5.User A has subscribed to receive expired status notifications via E-mail and pager and is 
			   associated with a role 'R1'.
			6.User B has �Update Status� right on resource 'RS'.
			7.User A has �Run Report� right on resource 'RS'  
	 	  Expected Result:No Expected Result
	 	*/
	      log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
	 			
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			// Role R1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName1, strRoleRights,
						strSTvalue, false, strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName1);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role R2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName2, strRoleRights,
						strSTvalue, false, strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[1] = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName2);
				if (strRoleValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Navigate to status type list

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeST, statTypeNameST,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
						statTypeNameST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeNameST);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValueExpire,
						statTypeNameExpire, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue[0], "true" },
						{ strRoleValue[1], "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue[1], "true" } };
				strFuncResult = objST.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeNameExpire);
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
						seleniumPrecondition, statTypeNameExpire,
						strStatusNameExpire1, strStatusTypeValueExpire,
						strStatTypeColor, strExpHr, strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						seleniumPrecondition, statTypeNameExpire,
						strStatusNameExpire2, strStatusTypeValueExpire,
						strStatTypeColor, strExpHr, strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statTypeNameExpire,
						strStatusNameExpire1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statTypeNameExpire,
						strStatusNameExpire2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(seleniumPrecondition);
				strFuncResult = strArFunRes[4];
				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValueShift,
						statTypeNameShift, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue[0], "true" },
						{ strRoleValue[1], "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue[1], "true" } };
				strFuncResult = objST.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");
				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				// Select shift time
				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.ShiftTime1"));
				seleniumPrecondition
						.select(propElementDetails
								.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
								strStatusTime[0]);
				seleniumPrecondition
						.select(propElementDetails
								.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
								strStatusTime[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeNameShift);
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
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeNameShift,
						strStatusNameShift1, strStatusTypeValueShift,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeNameShift,
						strStatusNameShift2, strStatusTypeValueShift,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statTypeNameShift,
						strStatusNameShift1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statTypeNameShift,
						strStatusNameShift2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
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
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
				strFuncResult = objRs.navToEditResLevelSTPage(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(
						seleniumPrecondition, strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Create User A

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
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail,
						strEMail, strEMail, "");
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, false, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				seleniumPrecondition
						.click("css=input[name=expiredStatusEmailInd]");
				seleniumPrecondition
						.click("css=input[name=expiredStatusPagerInd]");
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User B
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[1], true);
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
			
		log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
		log4j.info("~~~~~TESTCASE-EXECUTION" + gstrTCID + " EXECUTION STARTS~~~~~");
	 	/*
	 	* STEP :
	 	  Action: 	Login as User B, navigate to View >> Map. Select resource 'RS' under 'Find Resource' 
	 	  dropdown and update status values of status types 'ST1' and 'ST2' from resource pop up window. 
	 	  Expected Result:Status type 'ST1' and 'ST2' are updated and are displayed on 'resource pop up window' screen 
	 	*/

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[1],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[2],
						strStatusNameShift1, strStatusValueShift[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
	 	/*
	 	* STEP :
	 	  Action:After time 'T1'. 
	 	  Expected Result:E-mail and pager notifications are not received by User A. 
	 	*/			
	 			
	 	/*
	 	* STEP :
	 	  Action:After time 'S1'. 
	 	  Expected Result:E-mail and pager notifications are not received by User A. 
	 	*/

			try {
				assertEquals("", strFuncResult);
				String strElementId = "//span[text()='" + strStatusNameExpire1
						+ "']/following-sibling::span[@class='overdue']";
				strFuncResult = objMail.waitForMailNotification(selenium, 310,
						strElementId);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intPagerRes_Expire++;
						strSubjName = "EMResource Expired Status Notification: "
								+ strResource;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Expire++;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Expire++;
						strFuncResult = objMail.backToMailInbox(selenium,
								false, true, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// check Email, pager notification
					if (intEMailRes_Expire == 2 && intPagerRes_Expire == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenTimeHrs_1 = strArFunRes[2];
				strGenTimeMin_1 = strArFunRes[3];
				strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				int intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
						strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
						60000);
				if ((intShiftTime - intTimeDiffOutPut) > 0) {
					intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
					intTimeDiffOutPut = intTimeDiffOutPut + 1;
					intTimeDiffOutPut = intTimeDiffOutPut * 60;
					try {
						assertEquals("", strFuncResult);
						String strElementId = "//span[text()='"
								+ strStatusNameShift1
								+ "']/following-sibling::span[@class='overdue']";
						strFuncResult = objMail.waitForMailNotification(
								selenium, intTimeDiffOutPut, strElementId);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intPagerRes_Shift++;
						strSubjName = "EMResource Expired Status Notification: "
								+ strResource;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Shift++;
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						intEMailRes_Shift++;
						strFuncResult = objMail.backToMailInbox(selenium,
								false, true, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// check Email, pager notification
					if (intEMailRes_Shift == 2 && intPagerRes_Shift == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
					selenium.selectFrame("Data");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertEquals("", gstrReason);
				if (intResCnt == 2) {
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "44612"; // Test Case Id
			gstrTO = "Create a status type ST selecting a role R1 under �Roles with view rights� section, "
					+ "associate ST with resource RS at the resource level and verify that the user with role R1 "
					+ "and �Run Report� right on resource RS DOES NOT receive expired status notification for"
					+ " resource RS when the status of ST expires.";// TO
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
