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
' Description       :This class includes requirement test cases
' Requirement Group :Setting up users
' Requirement       :Refine status types
' Date		        :2nd-Jan-2013
' Author	        :QSG
'-------------------------------------------------------------------
' Modified Date                                         Modified By
' <Date>                           	                     <Name>
'*******************************************************************/

public class FTSRefineStatusTypes {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSRefineStatusTypes");
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
	String gstrTimeOut = "";
	Selenium selenium,seleniumPrecondition;
	
	@Before
	public void setUp() throws Exception {

		gdtStartDate = new Date();
		gstrBrowserName = "IE 8";

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");

		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		Paths_Properties objPaths_Properties = new Paths_Properties();
		propElementAutoItDetails = objPaths_Properties.ReadAutoit_FilePath();

		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();

		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
						.getProperty("urlEU"));
			
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try{
			selenium.close();
		}catch(Exception Ae){			
		}
		
		try{
			seleniumPrecondition.close();
		}catch(Exception Ae){			
		}
				
		selenium.stop();
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
		String FILE_PATH = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		FILE_PATH = pathProps.getProperty("Resultpath");
		// and execution time
		gdbTimeTaken = objOFC.TimeTaken(gdtStartDate);
		Date_Time_settings dts = new Date_Time_settings();
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");
		gstrBuild = propEnvDetails.getProperty("Build");
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");

		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
/********************************************************************************
'Description	: For a user U1, deselect a status type in the 'Refine Visible Status Types'
                  window for a resource RS and save, verify that the user cannot add the
                  deselected status type for his/her custom view only for resource RS.
'Arguments		: None
'Returns		: None
'Date	 		: 2nd-Jan-2013
'Author			: QSG
'-------------------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
**********************************************************************************/
	@Test
	public void testFTS52574() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		Preferences objPreferences = new Preferences();
		
		try {
			gstrTCID = "52574";
			gstrTO = "For a user U1, deselect a status type in the 'Refine Visible Status Types'"
					+ "window for a resource RS and save, verify that the user cannot add the"
					+ "deselected status type for his/her custom view only for resource RS.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// role based
			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String statrNumTypeName1 = "NST1_" + strTimeText;
			String statrNumTypeName2 = "NST2_" + strTimeText;
			String str_roleStatusTypeValues[] = new String[2];

			// RT data
			String strResrcTypName = "RT" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS" + strTimeText;
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";
			// Role
			String strRolesName = "Rol" + strTimeText;
			String strRoleValue = "";

			// USER
			String strUserName1 = "Auto_U1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserName1;

			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
	
		/*
		  Preconditions:
			1. Status types ST1 and ST2 are created selecting role R1 under 'Roles with view rights' section.
			2. ST1 and ST2 are associated with resource type RT.
			3. Resource RS is created under resource type RT.
			4. User U1 has the following rights:
			a. 'View Custom View'.
			b. 'View Resource Right' on RS
			5. User U1 is associated with Role R1. 
		 */
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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
				strFuncResult = objRoles
						.CreateRoleWithAllFieldsCorrect(selenium, strRolesName,
								strRoleRights, strViewRightValue, true,
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statrNumTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statrNumTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statrNumTypeName1);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

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
						selenium, strNSTValue, statrNumTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statrNumTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statrNumTypeName2);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						selenium, strResrcTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[0] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[1] + "']");

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

			// Resources
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// /RSa
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strResVal, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");		
		/*
		* STEP : Action :Login as RegAdmin and navigate to Setup >> Users. 
		  Expected Result: 	'Users List' screen is displayed. 
		*/			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}					
		/*
		* STEP : Click on 'Edit' link associated with User A. 
		  Expected Result: 'Edit User' screen is displayed. 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :Click on 'Refine' link associated with resource RS. 
		  Expected Result:'Refine Visible Status Types' window is popped up. 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP : Action :Deselect Status type ST1 and click on 'Save Changes'. 
		  Expected Result: 	 	'Edit User' screen is displayed. 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(selenium,
						str_roleStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveChangesInRefineSTAndVerifyEditUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :Login as User U1 and navigate to Preferences >> Customized View. 
		  Expected Result:'Edit Custom View' screen is displayed. 
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
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP : Action :Click on 'Add More Resources', and search for RS. 
		  Expected Result: 	No Expected Result 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navToFindResPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String[] strResources = { strResource };
				strFuncResult = objPreferences.findResources(selenium,
						strResources, strCategory, strRegion, strCityZipCd,
						strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :Add resource RS to the Custom view. 
		  Expected Result:'Edit Custom View' screen is displayed. 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.AddResourceToCustomView(
						selenium, strResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		* STEP :Click on 'Options'. 
		  Expected Result:Only status type ST2 is displayed in 'Edit Custom View Options (Columns)' screen. 
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
				String[] strSTName = { statrNumTypeName2 };
				strFuncResult = objPreferences.verifySTinEditCuctmViewOptionPge(selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrNumTypeName1 };
				strFuncResult = objPreferences.checkSTsInEditCustViewOptions(selenium, strSTName);
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
			gstrTCID = "52574";
			gstrTO = "For a user U1, deselect a status type in the 'Refine Visible Status Types'"+
                     "window for a resource RS and save, verify that the user cannot add the"+
                    " deselected status type for his/her custom view only for resource RS.";
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
	'Description	: Verify that private status types are not listed in the 'Refine 
	'				Visible Status Types' screen.
	'Precondition	:1. Status types ST1 is created selecting 'Private' option.
					2. ST1 is associated with resource type RT.
					3. Resource RS is created under resource type RT.
					4. User U1 is created with role 'Rol'. 
	'Arguments		: None
	'Returns		: None
	'Date	 		: 3rd-Jan-2013
	'Author			: QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	@Test
	public void testFTS52531() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		

		try {
			gstrTCID = "52531";
			gstrTO = "Verify that private status types are not listed in the "
					+ "'Refine Visible Status Types' screen.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statSTName1 = "ST1_" + strTimeText;
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strSTvalue[] = new String[1];
			
			String strResrcTypName = "RT" + strTimeText;

			String strResource = "AutoRS" + strTimeText;
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			
			// Role
			String strRolesName = "Rol" + strTimeText;
			String strRoleValue = "";

			// USER
			String strUserName1 = "Auto_U1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserName1;

			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/*
			 * 1. Status types ST1 is created selecting 'Private' option.
			 */
			
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
			
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createPrivateSTWitMandFlds(
						selenium, strNSTValue, statSTName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statSTName1);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. ST1 is associated with resource type RT.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(selenium,
						strResrcTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");

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
			 * 3. Resource RS is created under resource type RT.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrcTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium, strResource);

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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights={};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);
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
			
			
			/*
			 * 4. User U1 is created with role 'Rol'.
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
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
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
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			/*
			 * 2 Login as RegAdmin navigate to Setup >> Users. 'Users List'
			 * screen is displayed.
			 */		
			/*
			 * 3 Click on 'Edit' link associated with User U1. 'Edit User'
			 * screen is displayed.
			 */
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 4 Click on 'Refine' link associated with resource RS. Private
			 * status type ST1 is not listed in the 'Refine Visible Status
			 * Types' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifySTPresenceInRefineVisibleST(selenium,
								statSTName1, strSTvalue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "52531";
			gstrTO = "Verify that private status types are not listed in the "
					+ "'Refine Visible Status Types' screen.";
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
	'Description	:Verify that when the user have multiple roles, all the visible 
	'				status types of all the roles are displayed in the 'Refine Visible 
	'				Status Types' window.
	'Precondition	1. Status type ST1, ST2, ST3 and ST4 are created.
					2. Role R1 is created by selecting only status types ST1 and ST2 under 'Select the Status Types this Role may view' section.
					3. Role R2 is created by selecting only status types ST3 and ST4 under 'Select the Status Types this Role may view' section.
					4. User U1 is associated with role R1 and R2.
					5. Status types ST1, ST2, ST3 and ST4 are associated with resource type RT. 
	'Arguments		: None
	'Returns		: None
	'Date	 		: 3rd-Jan-2013
	'Author			: QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	@Test
	public void testFTS52558() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();

		try {
			gstrTCID = "52558";
			gstrTO = "Verify that when the user have multiple roles, all the visible status"
					+ " types of all the roles are displayed in the 'Refine Visible Status Types'"
					+ " window.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statSTName1 = "ST1_" + strTimeText;
			String statSTName2 = "ST2_" + strTimeText;
			String statSTName3 = "ST3_" + strTimeText;
			String statSTName4 = "ST4_" + strTimeText;

			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strSTvalue[] = new String[4];

			String strResrcTypName = "RT" + strTimeText;

			String strResource = "AutoRS" + strTimeText;
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";

			// Role
			String strRolesName1 = "Rol1" + strTimeText;
			String strRolesName2 = "Rol2" + strTimeText;
			String strRoleValue[] = new String[2];

			// USER
			String strUserName1 = "Auto_U1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserName1;

			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/*
			 * 1. Status type ST1, ST2, ST3 and ST4 are created.
			 */

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

			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statSTName1, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statSTName1);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

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
						selenium, strNSTValue, statSTName2, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statSTName2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST3

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statSTName3, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statSTName3);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST4

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statSTName4, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[3] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statSTName4);
				if (strSTvalue[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. Status types ST1, ST2, ST3 and ST4 are associated with
			 * resource type RT.
			 */

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
								+ strSTvalue[0] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[1] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[2] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[3] + "']");

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
			 * 6. Resource RS is created under resource type RT.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Role R1 is created by selecting only status types ST1 and ST2
			 * under 'Select the Status Types this Role may view' section.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strSTViewValue = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[0], "false" },
						{ strSTvalue[1], "false" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						selenium, false, false, strSTViewValue,
						strSTUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName1);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Role R2 is created by selecting only status types ST3 and ST4
			 * under 'Select the Status Types this Role may view' section.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strSTViewValue = { { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[2], "false" },
						{ strSTvalue[3], "false" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						selenium, false, false, strSTViewValue,
						strSTUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[1] = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName2);
				if (strRoleValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 4. User U1 is associated with role R1 and R2. */

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue[0], true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * 2 Login as RegAdmin navigate to Setup >> Users. 'Users List'
			 * screen is displayed.
			 */
			/*
			 * 3 Click on 'Edit' link associated with User U1. 'Edit User'
			 * screen is displayed.
			 */

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Click on 'Refine' link associated with resource RS. Status
			 * types ST1, ST2, ST3 and ST4 are listed in the 'Refine Visible
			 * Status Types' screen.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < strSTvalue.length; i++) {
				String statSTNames[] = { statSTName1, statSTName2, statSTName3,
						statSTName4 };
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.verifySTPresenceInRefineVisibleST(selenium,
									statSTNames[i], strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "52558";
			gstrTO = "Verify that when the user have multiple roles, all the visible "
					+ "status types of all the roles are displayed in the 'Refine Visible"
					+ " Status Types' window.";
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
	'Description	: Verify that, an appropriate message is displayed when clicked on the 'Refine' link while creating a new user.
	'Precondition	:1. ST1 is associated with resource type RT.
					 2. Resource RS is created under resource type RT. 		 
	'Arguments		: None
	'Returns		: None
	'Date	 		: 3rd-Jan-2013
	'Author			: QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	@Test
	public void testFTS52528() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();

		try {
			gstrTCID = "52528";
			gstrTO = "Verify that, an appropriate message is displayed when "
					+ "clicked on the 'Refine' link while creating a new user.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statSTName1 = "ST1_" + strTimeText;
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strSTvalue[] = new String[1];

			String strResrcTypName = "RT" + strTimeText;

			String strResource = "AutoRS" + strTimeText;
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";

			// USER
			String strUserName1 = "Auto_U1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserName1;

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/*
			 * 1. ST1 is associated with resource type RT.
			 */

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

			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statSTName1, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statSTName1);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. ST1 is associated with resource type RT.
			 */

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
								+ strSTvalue[0] + "']");

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
			 * 2. Resource RS is created under resource type RT.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID+ " EXECUTION ENDS~~~~~");
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
		/*
		 * 2 Login as RegAdmin navigate to Setup >> Users. 'User List'
		 * screen is displayed.
		 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 3 Click on 'Create New User'. 'Create New User' screen is
		 * displayed.
		 */
		
		/*
		 * 4 Fill all the mandatory data, and click on 'Refine' link
		 * associated with resource 'RS'. 'Refine Visible Status Types'
		 * window is popped up and 'There are no visible status types to
		 * further refine. Please check the roles for this user' message is
		 * displayed.
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strErrorMsg = "There are no visible status types to further refine. Please check the roles for this user.";
				strFuncResult = objCreateUsers
						.verifyErrorMsgInRefineVisibiltyScreenNew(selenium,
								strErrorMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "52528";
			gstrTO = "Verify that, an appropriate message is displayed when clicked on the 'Refine' link while creating a new user.";
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
	
	/********************************************************************************
	'Description	:Verify that when a status type is deselected in the 'Refine Visible 
	'				 Status Types' window for one resource, the status type is not deselected
	'				 in the 'Refine Visible Status Types' window for another resource.
	'Precondition	1. Role R1 has view rights for status types ST1 and ST2
					2. Resource RS1 and RS2 are created 
	'Arguments		: None
	'Returns		: None
	'Date	 		: 4th-Jan-2013
	'Author			: QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	@Test
	public void testFTS48409() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();

		try {
			gstrTCID = "48409";
			gstrTO = "Verify that when a status type is deselected in the "
					+ "'Refine Visible Status Types' window for one resource, "
					+ "the status type is not deselected in the 'Refine Visible"
					+ " Status Types' window for another resource."
					+ " window.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statSTName1 = "ST1_" + strTimeText;
			String statSTName2 = "ST2_" + strTimeText;

			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strSTvalue[] = new String[2];

			String strResrcTypName = "RT" + strTimeText;

			String strResource1 = "AutoRS1" + strTimeText;
			String strResource2 = "AutoRS2" + strTimeText;
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";

			// Role
			String strRolesName1 = "Rol1" + strTimeText;
			String strRoleValue = "";

			// USER
			String strUserNameUserAcnt = "AutoUser"
					+ System.currentTimeMillis();
			String strUserName1 = "Auto_U1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserName1;
			String strUsrFulNameUserAcnt = strUserNameUserAcnt;

			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/*
			 * 1. Status type ST1, ST2, ST3 and ST4 are created.
			 */

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

			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statSTName1, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statSTName1);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

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
						selenium, strNSTValue, statSTName2, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statSTName2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. Status types ST1, ST2, ST3 and ST4 are associated with
			 * resource type RT.
			 */

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
								+ strSTvalue[0] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[1] + "']");

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
			 * 6. Resource RS is created under resource type RT.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource1, strAbbrv, strResrcTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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

			// Resource2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource2, strAbbrv, strResrcTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Role R1 has view rights for status types ST1 and ST2
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strSTViewValue = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" } };

				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						selenium, false, false, strSTViewValue, strSTViewValue,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName1);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 4. User U1 is associated with role R1 and R2. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUsrFulNameUserAcnt, strInitPwd, strConfirmPwd,
						strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUsrFulNameUserAcnt, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * 2 Login as any user with 'User - Setup User Accounts right' and
			 * navigate to Setup>>Users 'Users List' screen is displayed
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUsrFulNameUserAcnt, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 3 Select to create a new user and create user U1 with role R1 U1
			 * is listed in the 'Users List' screen
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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

				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Select to edit user U1, click on the 'Refine' link
			 * corresponding to resource RS1 under 'Resource Rights' section
			 * Status types ST1 and ST2 are displayed in the 'Refine Visible
			 * Status Types' window and are selected by default.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifySTPresenceInRefineVisibleST(selenium,
								statSTName1, strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifySTPresenceInRefineVisibleST(selenium,
								statSTName1, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 5 Deselect status type ST1 and save the changes User is retained
			 * on the 'Edit User' screen
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Click on the 'Refine' link corresponding to resource RS2 Status
			 * types ST1 and ST2 are displayed in the 'Refine Visible Status
			 * Types' window and are selected by default.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifySTPresenceInRefineVisibleST(selenium,
								statSTName1, strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifySTPresenceInRefineVisibleST(selenium,
								statSTName1, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 5 Deselect status type ST1 and save the changes User is retained
			 * on the 'Edit User' screen
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Save the user U1 'Users List' screen is displayed
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9 Select to edit user U1, click on the 'Refine' link
			 * corresponding to resource RS1 Status type ST1 remains unchecked
			 * and ST2 remains checked.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statSTName1, strSTvalue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statSTName1, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statSTName1, strSTvalue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statSTName1, strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "48409";
			gstrTO = "Verify that when a status type is deselected in the 'Refine Visible"
					+ " Status Types' window for one resource, the status type is not "
					+ "deselected in the 'Refine Visible Status Types' window for another resource.";
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
/**********************************************************************************
'Description	:Verify that inactive status types are not displayed in the 'Refine
                 Visible Status Types' window.
'Arguments		:None
'Returns		:None
'Date			:1/7/2013
'Author			:QSG
'----------------------------------------------------------------------------------
'Modified Date				                                          Modified By
'Date					                                              Name
************************************************************************************/
	@Test
	public void testFTS52822() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;

		Login objLogin = new Login();// object of class Login
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();

		try {
			gstrTCID = "52822"; // Test Case Id
			gstrTO = " Verify that inactive status types are not displayed in the 'Refine Visible "
					+ "Status Types' window.";// Test Objective
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

			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// ST
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValues[] = new String[2];
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

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
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
		/* STEP :
		  Action:Preconditions: 
				1. Status types ST1 is created by deselecting 'Active' checkbox and selecting role R1 under 
				'Roles with view rights' section. 
				2.Status types ST2 is created selecting role R1 under 'Roles with view rights' section. 
				3. ST1 and ST2 are associated with resource type RT. 
				4. Resource RS is created under resource type RT.
		  Expected Result:No Expected Result
		*/
		
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

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
				String[] strSTvalue = {};
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
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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
			try {
				assertEquals("", strFuncResult);
				String strSTtype = "Number";
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName1, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.activateAndDeactivateST(
						selenium, statTypeName1, false, true);
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
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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
						selenium, strResrcTypName, strStatusTypeValues[1]);
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
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName, "FN",
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strResVal, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STATRTS~~~~~");
		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to Setup >> Users.
		  Expected Result:'Users List' screen is displayed.
		*/
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.navUserListPge(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
		/*
		* STEP :
		  Action:Click on 'Edit' link associated with User A.
		  Expected Result:'Edit User' screen is displayed
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
		/*
		* STEP :
		  Action:Click on 'Refine' link associated with resource RS.
		  Expected Result:'Refine Visible Status Types' window is popped up.
				  Status type ST1 is not displayed, where as status type ST2 is displayed.
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifySTPresenceInRefineVisibleST(selenium,
								statTypeName1, strStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifySTPresenceInRefineVisibleST(selenium,
								statTypeName2, strStatusTypeValues[1], true);
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
			gstrTCID = "52822";
			gstrTO = "Verify that inactive status types are not displayed in the 'Refine Visible Status Types' window.";
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

/**********************************************************************************
'Description	:Verify that when a status type is deselected in a role R1, the status type is
                 not listed in the 'Refine Visible Status Types' window for a user having role R1. 
'Arguments		:None
'Returns		:None
'Date			:1/7/2013
'Author			:QSG
'----------------------------------------------------------------------------------
'Modified Date				                             Modified By
'Date					                                 Name
************************************************************************************/

	@Test
	public void testFTS52830() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;

		Login objLogin = new Login();// object of class Login
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();

		try {
			gstrTCID = "52830"; // Test Case Id
			gstrTO = "Verify that when a status type is deselected in a role R1, the status type is not listed in"
					+ " the 'Refine Visible Status Types' window for a user having role R1. ";// Test
																								// objective
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
			String strRegn =rdExcel.readData("Login", 3, 4);

			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// ST
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValues[] = new String[2];
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

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
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

		/* STEP :
		  Action:Preconditions: 
				1. Status types ST1 is created without selecting role R1 under 'Roles with view rights' section. 
				2. Status types ST2 is created selecting role R1 under 'Roles with view rights' section. 
				3. ST1 and ST2 are associated with resource type RT. 
				4. Resource RS is created under resource type RT. 
				5. User U1 has the following rights: 
				   a. 'User - Setup User Accounts'. 
				   b. 'View Resource Right' on RS.
				6. User U1 is associated with Role R1.  
		  Expected Result:No Expected Result
		*/
		
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

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
				String[] strSTvalue = {};
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
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = {};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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

			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName, "FN",
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strResVal, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STATRTS~~~~~");
		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to Setup >> Users.
		  Expected Result:'Users List' screen is displayed.
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Edit' link associated with User A.
		  Expected Result:'Edit User' screen is displayed
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
		/*
		* STEP :
		  Action:Click on 'Refine' link associated with resource RS.
		  Expected Result:'Refine Visible Status Types' window is popped up.
				  Status type ST1 is not displayed, where as status type ST2 is displayed.
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifySTPresenceInRefineVisibleST(selenium,
								statTypeName1, strStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifySTPresenceInRefineVisibleST(selenium,
								statTypeName2, strStatusTypeValues[1], true);
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
			gstrTCID = "52830"; // Test Case Id
			gstrTO = "Verify that when a status type is deselected in a role R1, the status type is not listed in"
					+ " the 'Refine Visible Status Types' window for a user having role R1. ";// Test
																								// objective
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
/*********************************************************************************************
'Description :For a user U1, deselect a status type in the 'Refine Visible Status Types'window 
              for a resource RS and save, verify that the user cannot set/edit status change
              preferences for the deselected status type only for resource RS.
'Arguments   :None
'Returns	 :None
'Date		 :1/7/2013
'Author		 :QSG
'---------------------------------------------------------------------------------------------
'Modified Date				                                                    Modified By
'Date					                                                        Name
**********************************************************************************************/
	@Test
	public void testFTS108033() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Login objLogin = new Login();// object of class Login
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Preferences objPreferences = new Preferences();
		Roles objRole = new Roles();

		try {
			gstrTCID = "108033"; // Test Case Id
			gstrTO = "For a user U1, deselect a status type in the 'Refine Visible Status Types' window for"
					+ " a resource RS and save, verify that the user cannot set/edit status change preferences"
					+ " for the deselected status type only for resource RS.";// Test
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

			// Rol
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// ST
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValues[] = new String[2];
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

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
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
			/*
			 * STEP : Action:Preconditions: 1. Status types ST1 and ST2 are
			 * created selecting role R1 under 'Roles with view rights' section.
			 * 2. ST1 and ST2 are associated with resource type RT. 3. Resource
			 * RS is created under resource type RT. 4. User U1 has the
			 * following rights: a. 'Edit Status Change Notification
			 * Preferences'. b. 'View Resource Right' on RS. 5. User U1 is
			 * associated with Role R1. 6. Status Change Preferences are set for
			 * user U1 selecting resources RS and status types ST1 and ST2.
			 * Expected Result:No Expected Result
			 */

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

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
				String[] strSTvalue = {};
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
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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

			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName, "FN",
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
			// USER A
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strResVal, false, false, false,
						true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
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
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
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
								strResource, strResVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal, strStatusTypeValues[0],
								statTypeName1, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium, strResVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal, strStatusTypeValues[1],
								statTypeName2, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");
			/*
			 * STEP : Action:Login as RegAdmin and navigate to Setup >> Users.
			 * Expected Result:'Users List' screen is displayed.
			 */
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
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit' link associated with User A.
			 * Expected Result:'Edit User' screen is displayed
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Refine' link associated with resource RS.
			 * Expected Result:'Refine Visible Status Types' window is popped
			 * up.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Deselect Status type ST1 and click on 'Save
			 * Changes'. Expected Result:'Edit User' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
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
			 * STEP : Action:Login as User U1 and navigate to Preferences >>
			 * Status Change Prefs Expected Result:'My Status Change
			 * Preferences' screen is displayed with only status type ST2.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.login(selenium, strUserNameA,
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
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statTypeName2, "Above: 0", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statTypeName1, "Above: 0", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'edit' link associated with resource RS.
			 * Expected Result:Only status type ST2 is displayed in 'Edit My
			 * Status Change Preferences' screen.
			 */
			try {
				assertEquals(
						gstrReason
								+ " Status Type "
								+ statTypeName1
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objPreferences.navToEditMyStatusChangePrefs(
						selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "0", "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName2, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "0", "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName1, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals(
						gstrReason
								+ " Status Type "
								+ statTypeName1
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = "";
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "108033"; // Test Case Id
			gstrTO = "For a user U1, deselect a status type in the 'Refine Visible Status Types' window for"
					+ " a resource RS and save, verify that the user cannot set/edit status change preferences"
					+ " for the deselected status type only for resource RS.";// Test
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
		if (blnLogin) {
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
	}
	
	
 /******************************************************************************************
 'Description    :For a user U1, deselect a status type in the 'Refine Visible Status Types'
                  window for resource RS and save, verify that another user U2 cannot set/edit
                  status change preferences for the deselected status type for user U1 for
                  resource RS from Setup>>Users.
 'Arguments		:None
 'Returns		:None
 'Date			:1/7/2013
 'Author			:QSG
 '---------------------------------------------------------------
 'Modified Date				Modified By
 'Date					Name
 ********************************************************************************************/
	@Test
	public void testFTS52669() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Login objLogin = new Login();// object of class Login
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "52669"; // Test Case Id
			gstrTO = "For a user U1, deselect a status type in the 'Refine Visible Status Types' window for"
					+ "resource RS and save, verify that another user U2 cannot set/edit status change "
					+ "preferences for the deselected status type for user U1 for resource RS from Setup>>Users.";// Test
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
			String strRegn =rdExcel.readData("Login", 3, 4);

			// Rol
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// ST
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValues[] = new String[2];
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
 	/*
 	* STEP :
 	  Action:Preconditions: 
 		1. Status types ST1 and ST2 are created selecting role R1 under 'Roles with view rights' section. 
 		2. ST1 and ST2 are associated with resource type RT. 
 		3. Resource RS is created under resource type RT. 
 		4. User U1 and U2 has the following rights: 
 		a. 'Edit Status Change Notification Preferences'. 
 		b. 'View Resource Right' on RS. 
 		5. User U2 has 'User - Setup User Accounts' right.
 		6. User U1 and U2 is associated with Role R1. 
 		7. Status Change Preferences are set for user U1 selecting resources RS and status types ST1 and ST2.
 	  Expected Result:No Expected Result
 	*/

 			log4j.info("---------------Precondtion for test case " + gstrTCID
 					+ " starts----------");

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
				String[] strSTvalue = {};
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
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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

			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName, "FN",
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
			// USER A
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strResVal, false, false, false,
						true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER B
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameB, strInitPwd, strConfirmPwd, strUsrFulName);
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
						selenium, strResource, strResVal, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
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
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strResVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal,
								strStatusTypeValues[0], statTypeName1,
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strResVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal,
								strStatusTypeValues[1], statTypeName2,
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STATRTS~~~~~");
 		/*
 		* STEP :
 		  Action:Login as RegAdmin and navigate to Setup >> Users.
 		  Expected Result:'Users List' screen is displayed.
 		*/	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
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
 		  Action:Click on 'Edit' link associated with User A.
 		  Expected Result:'Edit User' screen is displayed
 		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 		/*
 		* STEP :
 		  Action:Click on 'Refine' link associated with resource RS.
 		  Expected Result:'Refine Visible Status Types' window is popped up.
 		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 		/*
 		* STEP :
 		  Action:Deselect Status type ST1 and click on 'Save Changes'.
 		  Expected Result:'Edit User' screen is displayed.
 		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
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
 		  Action:Login as User U2 and navigate to Setup >> Users.
 		  Expected Result:Users List' screen is displayed.
 		*/
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
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
 		* STEP :
 		  Action:Click on 'Edit' link associated with User A.
 		  Expected Result:'Edit User' screen is displayed.
 		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 		/*
 		* STEP :
 		  Action:Click on 'Status Change Notification Preferences' link from 'User Preferences' section.
 		  Expected Result:'My Status Change Preferences' screen is displayed with only status type ST2.
 		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navStatusChangeNotiFrmEditUserPge(selenium,
								strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statTypeName2, "Above: 0", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statTypeName1, "Above: 0", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 		/*
 		* STEP :
 		  Action:Click on 'edit' link associated with resource RS.
 		  Expected Result:Only status type ST2 is displayed in 'Edit My Status Change Preferences' screen.
 		*/
			try {
				assertEquals(
						gstrReason
								+ " Status Type "
								+ statTypeName1
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objPreferences.navToEditMyStatusChangePrefsForUser(
						selenium, strResource,strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "0", "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName2, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "0", "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName1, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals(
						gstrReason
								+ " Status Type "
								+ statTypeName1
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult="";
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "52669"; // Test Case Id
			gstrTO = "For a user U1, deselect a status type in the 'Refine Visible Status Types' window for"
					+ "resource RS and save, verify that another user U2 cannot set/edit status change "
					+ "preferences for the deselected status type for user U1 for resource RS from Setup>>Users.";// Test
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
	}

 /*******************************************************************************************************
 'Description	:Verify that the when the status type is added back in the 'Refine Visible Status Types'
                  window for a resource, the user can set status change preferences on that resource.
 'Arguments		:None
 'Returns		:None
 'Date			:1/7/2013
 'Author			:QSG
 '-------------------------------------------------------------------------------------------------------
 'Modified Date				                                                               Modified By
 'Date					                                                                   Name
 ********************************************************************************************************/
	@Test
	public void testFTS52829() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Login objLogin = new Login();// object of class Login
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Preferences objPreferences = new Preferences();
		Roles objRole = new Roles();
		try {
			gstrTCID = "52829"; // Test Case Id
			gstrTO = " Verify that the when the status type is added back in the 'Refine Visible Status Types'" +
					" window for a resource, the user can set status change preferences on that resource.";// Test
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
			String strRegn =rdExcel.readData("Login", 3, 4);

			// Rol
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// ST
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValues[] = new String[2];
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

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
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
 	/*
 	* STEP :
 	  Action:Preconditions: 
 		1. Status types ST1 and ST2 are created selecting role R1 under 'Roles with view rights' section. 
 		2. ST1 and ST2 are associated with resource type RT. 
 		3. Resource RS is created under resource type RT. 
 		4. User U1 has the following rights: 
 		a. 'Edit Status Change Notification Preferences'. 
 		b. 'View Resource Right' on RS. 
 		5. User U1 is associated with Role R1. 
 		6. Status Change Preferences are set for user U1 selecting resources RS and status types ST1 and ST2.
 	  Expected Result:No Expected Result
 	*/
 			log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");

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
				String[] strSTvalue = {};
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
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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

			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName, "FN",
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
			// USER A
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strResVal, false, false, false,
						true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
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
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
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
								strResource, strResVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal, strStatusTypeValues[0],
								statTypeName1, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium, strResVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal, strStatusTypeValues[1],
								statTypeName2, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STATRTS~~~~~");
 	/*
 	* STEP :
 	  Action:Login as RegAdmin and navigate to Setup >> Users.
 	  Expected Result:'Users List' screen is displayed.
 	*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
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
 	  Action:Click on 'Edit' link associated with User A.
 	  Expected Result:'Edit User' screen is displayed.
 	*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 	/*
 	* STEP :
 	  Action:Click on 'Refine' link associated with resource RS.
 	  Expected Result:'Refine Visible Status Types' window is popped up.
 	*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 	/*
 	* STEP :
 	  Action:Deselect Status type ST1 and click on 'Save Changes'.
 	  Expected Result:'Edit User' screen is displayed.
 	*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
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
 	  Action:Logout and login as User U1 and navigate to Preferences >> Status Change Prefs
 	  Expected Result:'My Status Change Preferences' screen is displayed with only status type ST2.
 	*/
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.login(selenium, strUserNameA,
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
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statTypeName2, "Above: 0", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statTypeName1, "Above: 0", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 	/*
 	* STEP :
 	  Action:Click on 'edit' link associated with resource RS.
 	  Expected Result:Only status type ST2 is displayed in 'Edit My Status Change Preferences' screen.
 	*/
			try {
				assertEquals(
						gstrReason
								+ " Status Type "
								+ statTypeName1
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objPreferences.navToEditMyStatusChangePrefs(
						selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "0", "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName2, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "0", "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName1, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 	
 	/*
 	* STEP :
 	  Action:Logout and login as RegAdmin and navigate to Setup >> Users.
 	  Expected Result:'Users List' screen is displayed.
 	*/
			try {
				assertEquals(
						gstrReason
								+ " Status Type "
								+ statTypeName1
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
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
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 	/*
 	* STEP :
 	  Action:Click on 'Edit' link associated with User A.
 	  Expected Result:'Edit User' screen is displayed.
 	*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 	/*
 	* STEP :
 	  Action:Click on 'Refine' link associated with resource RS.
 	  Expected Result:'Refine Visible Status Types' window is popped up.
 	*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 	/*
 	* STEP :
 	  Action:Select Status type ST1 and click on 'Save Changes'.
 	  Expected Result:'Edit User' screen is displayed.
 	*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
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
 	  Action:Logout and login as User U1 and navigate to Preferences >> Status Change Prefs
 	  Expected Result:'My Status Change Preferences' screen is displayed with only status type ST2.
 	*/
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.login(selenium, strUserNameA,
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
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statTypeName2, "Above: 0", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statTypeName1, "Above: 0", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 	/*
 	* STEP :
 	  Action:Click on 'edit' link associated with resource RS.
 	  Expected Result:Status types ST1 and ST2 are displayed in 'Edit My Status Change Preferences' screen.
 	*/
			try {
				assertEquals("",strFuncResult);
				strFuncResult = objPreferences.navToEditMyStatusChangePrefs(
						selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "0", "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName2, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "0", "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName1, strNotifData);
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
			gstrTCID = "52829";
			gstrTO = "Verify that the when the status type is added back in the 'Refine Visible Status Types' window for a resource, the user can set status change preferences on that resource.";
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
//start//testFTS108031//
	/***************************************************************
	'Description		:Deselect a status type in the 'Refine Visible Status Types' window for a resource 
						 and verify that expired status notifications are not received when the status 
						 of that status type expires.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/23/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/

	@Test
	public void testFTS127547() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Date_Time_settings dts = new Date_Time_settings();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		General objMail = new General();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		ViewMap objMap = new ViewMap();
		try {
			gstrTCID = "127547"; // Test Case Id
			gstrTO = " Deselect a status type in the 'Refine Visible Status Types' window for a resource "
					+ "and verify that expired status notifications are not received when"
					+ " the status of that status type expires.";//TO
			gstrReason = "";
			gstrResult = "FAIL";			
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			int intShiftTime = Integer.parseInt(propEnvDetails
					.getProperty("ShiftTime"));
			// ST
			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;
			String strNumStatType1 = "AutoNSt_2" + strTimeText;
			String strTxtStatType1 = "AutoTSt_2" + strTimeText;
			String strSatuStatType1 = "AutoScSt_2" + strTimeText;
			String strMultiStatType1 = "AutoMSt_2" + strTimeText;
			String strStatusName3 = "St1" + strTimeText;
			String strStatusName4 = "St2" + strTimeText;
			String strSTvalue1[] = new String[4];
			String strStatusValue1[] = new String[2];
			String strStatTypDefn = "Auto";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strSTvalue[] = new String[5];
			String strStatusValue[] = new String[2];
			String strGeneralNSTType = "Nst_1" + strTimeText;
			// RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue[] = new String[1];
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			// Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			// Search user criteria
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

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strExpHr = "00";
			String strExpMn = "05";
			String StatusTime = "";
			String strUpdTime_Shift = "";
			String strMsgBody1 = "";
			String strMsgBody2 = "";
			String strMsgBody3 = "";
			String strMsgBody4 = "";
			String strMsgBody5 = "";
			String strMsgBody6 = "";
			String strMsgBody7 = "";
			String strMsgBody8 = "";
			String strMsgBody1Another = "";
			String strMsgBody2Another = "";
			String strMsgBody3Another = "";
			String strMsgBody4Another = "";
			String strMsgBody5Another = "";
			String strMsgBody6Another = "";
			String strMsgBody7Another = "";
			String strMsgBody8Another = "";
			String strSubjName = "EMResource Expired Status Notification: "
					+ strResource;
			String strCurDate = "";
			String strUpdTime = "";
			int intEMailRes = 0;
			int intPagerRes = 0;
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strNSTShiftTime = "";
			String strMSTShiftTime = "";
			String strTSTShiftTime = "";
			String strSSTShiftTime = "";

			/*
			 * STEP : Action:Preconditions:
			  1.Status types NST1(number), MST1(Multi), TXT1(Text) and
			  SAT1(Saturation) are created by associating the expiration time
			  (say 'T1') and selecting role 'R1' under view and update right
			  for the status type.
			  
			  2.Status types NST2(number), MST2(Multi), TXT2(Text) and
			  SAT2(Saturation) created by associating the shift time (say 'S1')
			  and selecting role 'R1' under view and update right for the
			  status type.
			  
			  3.Status Type ST(number) is created selecting role 'R1' under
			  view and update right for the status type.
			  
			  4.Resource 'RS' is created by proving address under the resource
			  type 'RT', associating ST, NST1, MST1, TXT1, SAT1, NST2, MST2,
			  TXT2, SAT2 at the 'RT' level.
			  
			  5.User A has subscribed to receive expired status notifications
			  via E-mail and pager and is associated with any role R1.
			  
			  6.User A has 'Update Status' right on resource 'RS' Expected
			  Result:No Expected Result
			 / */
			// 610680		
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
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

			/*
			 * 1 Preconditions:
			 * 
			 * 1.Status types 'NST', 'MST', 'SST' and 'TST' are created
			 * selecting expiration time (ex:5min) and role R1 under view
			 * ,update right for status types.
			 */

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
						seleniumPrecondition, strNSTValue, strNumStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire"));
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Hours"),
						"label=" + strExpHr);
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Mins"),
						"label=" + strExpMn);

				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, strNumStatType);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, strMultiStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMultiStatType);
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
				strFuncResult = objStatusTypes
						.createSTWithinMultiTypeSTExpTimeWithoutChangeStatus(
								seleniumPrecondition, strMultiStatType,
								strStatusName1, strMSTValue, strStatTypeColor,
								strExpHr, strExpMn, "", true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.createSTWithinMultiTypeSTExpTime(seleniumPrecondition,
								strMultiStatType, strStatusName2, strMSTValue,
								strStatTypeColor, strExpHr, strExpMn,
								strStatusName1, "", true);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, strTxtStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire"));
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Hours"),
						"label=" + strExpHr);
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Mins"),
						"label=" + strExpMn);

				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, strTxtStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTxtStatType);
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire"));
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Hours"),
						"label=" + strExpHr);
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Mins"),
						"label=" + strExpMn);

				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, strSatuStatType);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strGeneralNSTType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, strGeneralNSTType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[4] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strGeneralNSTType);

				if (strSTvalue[4].compareTo("") != 0) {
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
						seleniumPrecondition, strNSTValue, strNumStatType1,
						strStatTypDefn, false);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				strNSTShiftTime = strUpdTime_Shift;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes
							.provideShiftTimeForStatusTypes(
									seleniumPrecondition, strStatusTime[0],
									strStatusTime[1]);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, strNumStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue1[0] = strStatValue;
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
						seleniumPrecondition, strMSTValue, strMultiStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue1[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMultiStatType1);
				if (strSTvalue1[1].compareTo("") != 0) {
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
						seleniumPrecondition, strMultiStatType1,
						strStatusName3, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType1,
						strStatusName4, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusValue1[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								strMultiStatType1, strStatusName3);
				if (strStatusValue1[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusValue1[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								strMultiStatType1, strStatusName4);
				if (strStatusValue1[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(
						seleniumPrecondition, strMultiStatType1, "Multi");

				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				strMSTShiftTime = strUpdTime_Shift;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes
							.provideShiftTimeForStatusTypes(
									seleniumPrecondition, strStatusTime[0],
									strStatusTime[1]);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, strMultiStatType1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, strTxtStatType1,
						strStatTypDefn, false);
				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				strTSTShiftTime = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes
							.provideShiftTimeForStatusTypes(
									seleniumPrecondition, strStatusTime[0],
									strStatusTime[1]);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, strTxtStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTxtStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue1[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStatType1,
						strStatTypDefn, false);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				strSSTShiftTime = strUpdTime_Shift;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes
							.provideShiftTimeForStatusTypes(
									seleniumPrecondition, strStatusTime[0],
									strStatusTime[1]);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, strSatuStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strSatuStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue1[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
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

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue1[0], "true" },
						{ strSTvalue1[1], "true" }, { strSTvalue1[2], "true" },
						{ strSTvalue1[3], "true" }, { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" }, { strSTvalue[4], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_1);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
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
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrctTypName, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue1[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue1[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue1[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue1[3], true);
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
				strFuncResult = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrctTypName);
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
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);
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
				strFuncResult = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource, false, true, false,
						true);
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
				strFuncResult = objCreateUsers
						.provideExpirdStatusNotificatcion(seleniumPrecondition);

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
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION  - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			/*
			 * STEP : Action:Login as User A, navigate to View >> Map. Select
			 * resource 'RS' under 'Find Resource' dropdown and update status
			 * values of status types NST1, MST1, TXT1, SAT1, NST2, MST2, TXT2,
			 * and SAT2 from resource pop up window. Expected Result:Status
			 * types NST1, MST1, TXT1, SAT1, NST2, MST2, TXT2, and SAT2 are
			 * updated and are displayed on 'resource pop up window' screen
			 */
			// 610681
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMap.navResPopupWindowNew(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"1", strSTvalue[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"1", strSTvalue[2], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strUpdateValue1, strSTvalue[3]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strSTvalue[1], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"1", strSTvalue1[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"1", strSTvalue1[2], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strUpdateValue1, strSTvalue1[3]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue1[0], strSTvalue1[1], false, "", "");

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

				strFuncResult = objMap.navResPopupWindowNew(selenium,
						strResource);

				String strLastUpdArr[] = selenium.getText(
						"//span[@id='statusTime']").split(" ");
				strUpdTime = strLastUpdArr[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime, 5,
						"HH:mm");
				strUpdTime = strAdUpdTime;
				String strCurYear = dts.getCurrentDate("yyyy");
				String strCurDateMnth = strLastUpdArr[0] + strLastUpdArr[1]
						+ strCurYear;

				strCurDate = dts.converDateFormat(strCurDateMnth, "ddMMMyyyy",
						"MM/dd/yyyy");

				log4j.info("Status types NST1, MST1, TXT1, SAT1, NST2, MST2, TXT2, and SAT2 are"
						+ " updated and are displayed on 'resource pop up window' screen");
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
			 * STEP : Action:Before Expiration time (T1) and Shift time (S1),
			 * login as RegAdmin and navigate to Setup >> Users. Expected
			 * Result:'Users List' screen is displayed.
			 */
			// 610682
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
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit' link associated with User A.
			 * Expected Result:'Edit User' screen is displayed
			 */
			// 610683
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Refine' link associated with resource RS.
			 * Expected Result:'Refine Visible Status Types' window is popped
			 * up.
			 */
			// 610684
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Deselect Status types NST1, MST1, TXT1, SAT1, NST2,
			 * MST2, TXT2, and SAT2 and click on 'Save Changes'. Expected
			 * Result:'Edit User' screen is displayed.
			 */
			// 610685
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[3], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue1[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue1[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue1[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue1[3], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Save' on 'Edit User' screen Expected
			 * Result:'Users List' screen is displayed.
			 */
			// 610688
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
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
			 * STEP : Action:After time 'T1'. Expected Result:E-mail and pager
			 * notifications are not received by User A.
			 */
			// 610686
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(360000);

				strMsgBody1 = "EMResource expired status: " + strResource
						+ ". " + strNumStatType + "" + " status expired "
						+ strCurDate + " " + strUpdTime + ".";

				strMsgBody2 = "EMResource expired status: " + strResource
						+ ". " + strNumStatType1 + "" + " status expired "
						+ strCurDate + " " + strNSTShiftTime + ".";

				strMsgBody3 = "EMResource expired status: " + strResource
						+ ". " + strMultiStatType + "" + " status expired "
						+ strCurDate + " " + strUpdTime + ".";

				strMsgBody4 = "EMResource expired status: " + strResource
						+ ". " + strMultiStatType1 + "" + " status expired "
						+ strCurDate + " " + strMSTShiftTime + ".";

				strMsgBody5 = "EMResource expired status: " + strResource
						+ ". " + strTxtStatType + "" + " status expired "
						+ strCurDate + " " + strUpdTime + ".";

				strMsgBody6 = "EMResource expired status: " + strResource
						+ ". " + strTxtStatType1 + "" + " status expired "
						+ strCurDate + " " + strTSTShiftTime + ".";

				strMsgBody7 = "EMResource expired status: " + strResource
						+ ". " + strSatuStatType + "" + " status expired "
						+ strCurDate + " " + strUpdTime + ".";

				strMsgBody8 = "EMResource expired status: " + strResource
						+ ". " + strSatuStatType1 + "" + " status expired "
						+ strCurDate + " " + strSSTShiftTime + ".";

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				strSubjName = "EMResource Expired Status: " + strAbbrv;

				try {
					assertTrue(strFuncResult.equals(""));
					for (int i = 0; i < 8; i++) {

						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = objMail.seleniumGetText(selenium,
									"css=div.fixed.leftAlign", 160);

							String[] strMails = objMail.enodeToUnicode(
									selenium, strMsg, 160);
							strMsg = strMails[1];
							if (strMsg.equals(strMsgBody1)
									|| strMsg.equals(strMsgBody2)
									|| strMsg.equals(strMsgBody3)
									|| strMsg.equals(strMsgBody4)
									|| strMsg.equals(strMsgBody5)
									|| strMsg.equals(strMsgBody6)
									|| strMsg.equals(strMsgBody7)
									|| strMsg.equals(strMsgBody8)) {
								intPagerRes++;
								log4j.info(strMsg+" Pager body is displayed");
							} else {
								log4j.info("Pager body  is NOT displayed for Pager Notification");
								strFuncResult = "Pager body is NOT displayed for Pager Notification";
								gstrReason = gstrReason + " " + strFuncResult;
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
					gstrReason = strFuncResult;
				}

				strMsgBody1Another = "For "
						+ strUserName_1
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ strNumStatType
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "
						+ propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody2Another = "For "
						+ strUserName_1
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ strNumStatType1
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strNSTShiftTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "
						+ propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody3Another = "For "
						+ strUserName_1
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ strMultiStatType
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "
						+ propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody4Another = "For "
						+ strUserName_1
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ strMultiStatType1
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strMSTShiftTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "
						+ propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody5Another = "For "
						+ strUserName_1
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ strTxtStatType
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "
						+ propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody6Another = "For "
						+ strUserName_1
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ strTxtStatType1
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strTSTShiftTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "
						+ propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody7Another = "For "
						+ strUserName_1
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ strSatuStatType
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "
						+ propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody8Another = "For "
						+ strUserName_1
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ strSatuStatType1
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strSSTShiftTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "
						+ propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				try {
					assertTrue(strFuncResult.equals(""));

					for (int i = 0; i < 8; i++) {
						
						strSubjName = "EMResource Expired Status Notification: "
								+ strResource;

						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							String[] strMails = objMail.enodeToUnicode(
									selenium, strMsg, 160);
							strMsg = strMails[1];

							if (strMsg.equals(strMsgBody1Another)
									|| strMsg.equals(strMsgBody2Another)
									|| strMsg.equals(strMsgBody3Another)
									|| strMsg.equals(strMsgBody4Another)
									|| strMsg.equals(strMsgBody5Another)
									|| strMsg.equals(strMsgBody6Another)
									|| strMsg.equals(strMsgBody7Another)
									|| strMsg.equals(strMsgBody8Another)) {
								intEMailRes++;
								log4j.info(strMsg+" EMAIL body is displayed.");
							} else {
								log4j.info("Email body not displayed.");
								strFuncResult = "Email body not displayed.";
								gstrReason = gstrReason + " " + strFuncResult;
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
					gstrReason = strFuncResult;
				}

				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad("90000");
				selenium.close();

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				if (intEMailRes == 8 && intPagerRes == 8) {
					gstrResult = "PASS";
				} else {
					gstrResult = "FAIL";
				}
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-127547";
			gstrTO = "Deselect a status type in the 'Refine Visible Status Types' window for a resource and "
					+ "verify that expired status notifications are not received when the status of that status "
					+ "type expires.";
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

	// end//testFTS108031//
		
}
