package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/***************************************************************
' Description       :This class includes  requirement testcases
' Requirement Group :Setting up users
' Requirement       :Refine status types
' Date		        :28-May-2012
' Author	        :QSG
'---------------------------------------------------------------
' Modified Date                                     Modified By
' <Date>                           	                    <Name>
'***************************************************************/

@SuppressWarnings("unused")
public class RefineStatusTypes {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.RefineStatusTypes");
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

	Selenium selenium, seleniumFirefox,seleniumPrecondition;

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

		seleniumFirefox = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserFirefox"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserFirefox"),
				propEnvDetails.getProperty("urlEU"));
		
		selenium.start();
		selenium.windowMaximize();

		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		seleniumPrecondition.setTimeout("");

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try{
			selenium.close();
		}catch(Exception e){
			
		}
		try{
			seleniumFirefox.close();
		}catch(Exception e){
			
		}
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
		}
		

		// kill browser
		selenium.stop();
		seleniumFirefox.stop();
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
		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
	/********************************************************************************
	'Description	:Verify that the list of status types changes in the 'Refine 
	'				 Visible Status Types' window when a role is changed for the user.
	'Precondition	:1. Role R1 has view rights for status types ST1 and ST2
	'				 2. Role R2 has view rights for status types ST2 and ST3 
	'Arguments		:None
	'Returns		:None
	'Date	 		:28-May-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS48322() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class	
		StatusTypes objStatusTypes = new StatusTypes();// CreateUsers
		Roles objRoles=new Roles();
		ResourceTypes objResourceTypes=new ResourceTypes();
		Resources objResources = new Resources();
		
		try {
			gstrTCID = "BQS-48322 ";
			gstrTO = "Verify that the list of status types changes in the " +
					"'Refine Visible Status Types' window when a role is " +
					"changed for the user.";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			// ST
			String strStatusTypeValues[] = new String[3];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String statTypeName3 = "AutoST_3" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[2];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strResVal = "";
			String strHavBed = "No";
			String strRSValue[] = new String[1];
			// Role
			String strRolesName1 = "AutoRol_1" +strTimeText;
			String strRolesName2 = "AutoRol_2" +strTimeText;
			String strRoleValue1 = "";
			String strRoleValue2 = "";

			// User mandatory fields
			// USER
			String strUserName = "AutoUsr_1" + System.currentTimeMillis();
			String strUserName1 = "AutoUsr_2" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "fn"+strUserName;

			//search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			/*Preconditions:
			1. Role R1 has view rights for status types ST1 and ST2
			2. Role R2 has view rights for status types ST2 and ST3 */
			
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID+ " EXECUTION STATRTS~~~~~");
						
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
			
			//ST2
			
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
            //ST3
			
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName3,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName3);
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
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(seleniumPrecondition,  strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(seleniumPrecondition,  strStatusTypeValues[2], true);
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
				strFuncResult = objResources.navToCreateResourcePage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(seleniumPrecondition,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

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
			
			// ROLE1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition, strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strSTUpdateValue = {};
				String[][] strSTViewValue = {{ strStatusTypeValues[0],"true"},{strStatusTypeValues[1],"true"}};
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(seleniumPrecondition, false, 
						false, strSTViewValue, strSTUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue1 = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName1);
				if (strRoleValue1.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// ROLE2
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition, strRolesName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strSTUpdateValue = {};
				String[][] strSTViewValue = {{strStatusTypeValues[1],"true"},{strStatusTypeValues[2],"true"}};
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(seleniumPrecondition, false, 
						false, strSTViewValue, strSTUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue2 = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName2);
				if (strRoleValue2.compareTo("") != 0) {
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
				strFuncResult = objCreateUsers.advancedOptns(
								seleniumPrecondition,propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"),true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			 log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
				     + " EXECUTION ENDS~~~~~");
				log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");

			/*
			 * 2 Login as any user with 'User - Setup User Accounts right' and
			 * navigate to Setup>>Users 'Users List' screen is displayed
			 */
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
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
			 * 3 Select to create a new user and create user U1 with role R1 U1
			 * is listed in the 'Users List' screen
			 */

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "'Users List' page is NOT displayed. ";
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,strRoleValue1 , true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium, strUserName1,
						strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 4 Select to edit user U1, click on the 'Refine' link
			 * corresponding to any resource RS under 'Resource Rights' section
			 * Only ST1 and ST2 are displayed in the 'Refine Visible Status
			 * Types' window and are selected by default.
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
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statTypeName1, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statTypeName2, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.chkSaveAndCloseinRefineVisibleST(selenium, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			/*
			 * 5 Close the 'Refine Visible Status Types' window User is retained
			 * on the 'Edit User' screen
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.closeRefineSTAndVerifyEditUser(selenium);
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
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 6 Under 'User Type & Roles' section, deselect role R1 and select
			 * role R2, and click on 'Refine' link corresponding to resource RS
			 * 'Refine Visible Status Types' window is displayed with an
			 * appropriate message stating
			 * "You must save any changes made to this user's roles before refining status type visibility."
			 * 
			 * Option to close the window is available.
			 */
			
			try {
				assertEquals("", strFuncResult);				
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
						
			try {
				assertEquals("", strFuncResult);				
				strFuncResult=objCreateUsers.navToRefineVisibleST(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
								
			try {
				assertEquals("", strFuncResult);
				String strErrorMsg = "You must save any changes made to this user's"
						+ " roles before refining status type visibility.";
				strFuncResult = objCreateUsers
						.verifyErrorMsgInRefineVisibiltyScreen(selenium,
								strErrorMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * 7 Close the 'Refine Visible Status Types' window User is retained
			 * on the 'Edit User' screen
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.closeRefineSTAndVerifyEditUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/* 8 Save the user 'Users List' screen is displayed */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 9 Select to edit user U1, click on the 'Refine' link
			 * corresponding to resource RS Only ST2 and ST3 are displayed in
			 * the 'Refine Visible Status Types' window and are selected by
			 * default.
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
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statTypeName2, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statTypeName3, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 10 Deselect status type ST2 and save the changes User is retained
			 * on the 'Edit User' screen
			 */
			//Deselect the ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strStatusTypeValues[1], false);
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
			
			/* 11 Save the user 'Users List' screen is displayed */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
						
			/*
			 * 12 Select to edit user U1, click on the 'Refine' link
			 * corresponding to resource RS Only ST2 and ST3 are displayed in
			 * the 'Refine Visible Status Types' window and ST2 remains
			 * deselected.
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
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statTypeName2, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statTypeName3, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 13 Close the 'Refine Visible Status Types' window, under 'User
			 * Type & Roles' section; deselect role R2 and select role R1 and
			 * save the user 'Users List' screen is displayed
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.closeRefineSTAndVerifyEditUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			

			//Deselect Role R2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			//Select Role R1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 14 Select to edit user U1, click on the 'Refine' link
			 * corresponding to resource RS Only ST1 and ST2 are displayed in
			 * the 'Refine Visible Status Types' window and ST2 remains
			 * deselected.
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
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}			
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium,statTypeName1, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);								
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statTypeName2, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 15 Close the 'Refine Visible Status Types' window, under 'User
			 * Type & Roles' section; select role R2 also and save the user
			 * 'Users List' screen is displayed
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.closeRefineSTAndVerifyEditUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 16 Select to edit user U1, click on the 'Refine' link
			 * corresponding to resource RS All ST1, ST2 and ST3 are displayed
			 * in the 'Refine Visible Status Types' window and ST2 remains
			 * deselected.
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
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium,statTypeName1, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);								
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statTypeName2, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium,statTypeName3, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 17 Select ST2 and save the changes User is retained on the 'Edit
			 * User' screen
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strStatusTypeValues[1], true);
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
			
			/* 18 Save the user 'Users List' screen is displayed */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * 19 Select to edit user U1, click on the 'Refine' link
			 * corresponding to resource RS All ST1, ST2 and ST3 are displayed
			 * in the 'Refine Visible Status Types' window and are selected.
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
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium,statTypeName1, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);								
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statTypeName2, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium,statTypeName3, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * 20 Deselect all three status types, save the changes, deselect
			 * both roles R1 and R2 and save the user 'Users List' screen is
			 * displayed
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
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strStatusTypeValues[2], false);
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 21 Select to edit user U1, select both the roles R1 and R2 and
			 * save the user 'Users List' screen is displayed
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
					
			/*
			 * 22 Select to edit user U1, click on the 'Refine' link
			 * corresponding to resource RS under 'Resource Rights' section All
			 * ST1, ST2 and ST3 are displayed in the 'Refine Visible Status
			 * Types' window and are deselected by default.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium,statTypeName1, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);								
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium, statTypeName2, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(
						selenium,statTypeName3, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.closeRefineSTAndVerifyEditUser(selenium);
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
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-48322";
			gstrTO = "Verify that the list of status types changes in the "
					+ "'Refine Visible Status Types' window when a role is"
					+ " changed for the user.";
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
				gstrResult = "FAIL";
			}
		}
	}
	
	/********************************************************************************
	'Description	:For a user U1, deselect a status type in the 'Refine Visible 
	'				 Status Types' window for a resource and save, verify that the 
	'				 user cannot view the deselected status type from all the view 
	'				 screens for that particular resource. 
	'Precondition	:1. Role-based status types ST1 and ST2 are created selecting
	'				 role 'R1' under 'Roles with view rights' section.
	'				 2. ST1 and ST2 are associated with resource type RT.
	'				 3. Resources RS1 and RS2 are created under resource type RT with address.
	'				 4. View V1 has status type ST1, ST2 and resource RS1 and RS2.
	'				 5. Event Template ET is created with ST1, ST2 and RT.
	'				 6. Event E1 is created under ET selecting RS1 and RS2.
	'				 7. Status types ST1 and ST2 are under status type section S1.
    ' 
	'				8. User U1 has the following rights:
	'				a) Role R1
	'				b) 'View Resource' right on resource RS.
	'				c) 'View Custom View' right.
	'				9. User U1 has added resources RS1, RS2 and status types ST1 and ST2 to his/her custom view. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:28-May-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS48417() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList=new EventList();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles=new Roles();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "BQS-48417 ";
			gstrTO = "	For a user U1, deselect a status type in the 'Refine Visible"
					+ " Status Types' window for a resource and save, verify that "
					+ "the user cannot view the deselected status type from all"
					+ " the view screens for that particular resource. ";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");


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

			String strUserName_Any = "AutoUsr_Any" + System.currentTimeMillis();
			String strUsrFulName_Any = strUserName_Any;
			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String statTypeName1 = "A1" + strTimeText;
			String statTypeName2 = "A2" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[2];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource1 = "AutoRsa_" + strTimeText;
			String strResource2 = "AutoRsb_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statTypeName1, statTypeName2 };


			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";
			
			//Data for creating View
			String strViewName_1="autoView_1" + strTimeText;
			String strVewDescription="";
			String strViewType="";
			
			String  strRolesName_1="Role_1"+ strTimeText;
			String strRoleValue="";

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

			
			/*
			 * 1. Role-based status types ST1 and ST2 are created selecting role
			 * 'R1' under both 'Roles with view rights' and 'Roles with update
			 * rights' sections.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult =objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objRoles.rolesMandatoryFlds(seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objRoles.savAndVerifyRoles(seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				
				strRoleValue =objRoles.fetchRoleValueInRoleList(seleniumPrecondition, strRolesName_1);
				
				if(strRoleValue.compareTo("")!=0){
					strFuncResult="";
				}else{
					strFuncResult="Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//ST1
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName1);
				
				if(strSTvalue[0].compareTo("")!=0){
					strFuncResult="";
				}else{
					strFuncResult="Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//ST2
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName2);
				
				if(strSTvalue[1].compareTo("")!=0){
					strFuncResult="";
				}else{
					strFuncResult="Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
		/*	2. ST1 and ST2 are associated with resource type RT. */
			
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
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*3. Resources RS is created under resource type RT with address. */
			
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
						strResource1, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource1);

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
			
			/*4. View V1 has status type ST1, ST2 and resource RS. */
			
			
			
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
				
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/* 5. Event Template ET is created with ST1, ST2 and RT. */
			
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

				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1] };
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

			/*6. Event E1 is created under ET selecting RS. */
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult=objEventSetup.navToEventManagementNew(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventEndNever(seleniumPrecondition,
						strTempName, strResource1, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResource2, true, true);
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

			/*7. Status types ST1 and ST2 are under status type section S1. */
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
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
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}

			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strArStatType1, strSection1, true);

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

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//User with user set up account right
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_Any, strInitPwd, strConfirmPwd, strUsrFulName_Any);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_Any, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

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
						seleniumPrecondition, strResource1, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource2, strRSValue[0], false, true,
						false, true);

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
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9. User U1 has added resources RS and status types ST1 and ST2 to
			 * his/her custom view. No Exp
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource1 };
				strFuncResult = objPreferences
						.createCustomViewWitTablOrMapOption(selenium, strRS,
								strResrctTypName, statTypeName1);

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

				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTValue[][] = { { strSTvalue[1], "true" } };
				strFuncResult = objPreferences
						.addSTInEditCustViewOptionPageTablOrMap(selenium,
								strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//RS2
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource2 };
				strFuncResult = objPreferences
						.createCustomViewWitTablOrMapOption(selenium, strRS,
								strResrctTypName, statTypeName1);

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

				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTValue[][] = { { strSTvalue[1], "true" } };
				strFuncResult = objPreferences
						.addSTInEditCustViewOptionPageTablOrMap(selenium,
								strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
			
			/*
			 * 2 Login as any user with 'User - Setup User Accounts right' and
			 * navigate to Setup>>Users 'Users List' screen is displayed
			 */
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_Any,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Select to edit user U1, click on the 'Refine' link
			 * corresponding to resource RS under 'Resource Rights' section
			 * 'Refine Visible Status Types' window is displayed.
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
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
			
			/*
			 * 4 Deselect status type ST1 and save the changes and save the user
			 * U1 U1 is listed in the 'Users List' screen
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 5 Logout and login as user U1 and navigate to View>>V1 Only ST2
			 * is displayed for resource RS.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 5 Logout and login as user U1 and navigate to View>>V1 N/A is
			 * displayed under status type ST1 column for resource RS1 and not
			 * for resource RS2.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult =objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.chkSTAssoOrNotInViewScreenNew(selenium,
						statTypeName1, false, strResource1,1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.chkSTAssoOrNotInViewScreenNew(selenium,
						statTypeName1, true, strResource2,1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Navigate to View>>Custom N/A is displayed under status type ST1
			 * column for resource RS1 and not for resource RS2.
			 */
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.chkSTAssoOrNotInCustomViewScreen(selenium,
						statTypeName1, false, strResource1,1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.chkSTAssoOrNotInCustomViewScreen(selenium,
						statTypeName1, true, strResource2,1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			/*
			 * 7 Navigate to the map view of custom view Only ST2 is displayed
			 * in the resource pop up window of RS1.
			 * 
			 * Both ST1 and ST2 are displayed in the resource pop up window of
			 * RS2.
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
				String[] strRoleStatType = {statTypeName1};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource1, strEventStatType,
								strRoleStatType, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = {statTypeName2};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource1, strEventStatType,
								strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = {statTypeName1,statTypeName2};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource2, strEventStatType,
								strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 Navigate to View>>Map Only ST2 is displayed in the resource pop
			 * up window of RS1.
			 * 
			 * Both ST1 and ST2 are displayed in the resource pop up window of
			 * RS2.
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName2 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1,statTypeName2 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource2, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 9 Click on the event banner of E1 N/A is displayed under status
			 * type ST1 column for resource RS1 and not for resource RS2.
			 */
			
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navEventStatusPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.chkSTAssoOrNotInViewScreenNew(selenium,
						statTypeName1, false, strResource1,1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.chkSTAssoOrNotInViewScreenNew(selenium,
						statTypeName1, true, strResource2,1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 10 Click on the name of resource RS1 Only ST2 is displayed on the
			 * 'View Resource Detail' screen under section S1.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String[] strStatTypep = { statTypeName1 };
				strFuncResult = objViews.verifySTInViewResDetailNew(selenium,
						strSection1, strSectionValue, strStatTypep);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("The Status Type is NOT displayed on "
								+ "the view resource detail screen. ", strFuncResult);

				String[] strStatTypep = { statTypeName2 };
				strFuncResult = objViews.verifySTInViewResDetailNew(selenium,
						strSection1, strSectionValue, strStatTypep);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 11 Click on the event banner of E1, click on the name of resource
			 * RS2 Both ST1 and ST2 are displayed on the 'View Resource Detail'
			 * screen under section S1.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navEventStatusPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String[] strStatType = { statTypeName1,statTypeName2 };
				strFuncResult = objViews.verifySTInViewResDetailNew(selenium,
						strSection1, strSectionValue, strStatType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

			
				gstrResult = "PASS";
				
				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserName_1 + "/" + strInitPwd,
						statTypeName1+","+statTypeName2,
						strViewName_1,
						"From 12th Step",
						strResource1+","+strResource2,
						strSection1,
						strEveName};

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-48417";
			gstrTO = "	For a user U1, deselect a status type in the 'Refine Visible"
					+ " Status Types' window for a resource and save, verify that the"
					+ " user cannot view the deselected status type from all the view "
					+ "screens for that particular resource. ";
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
				gstrResult = "FAIL";
			}
		}
	}
	
	/***************************************************************
	'Description    :Verify that when the status type is added back in 'Refine Visible Status Types' window for a resource, the changes are reflected on all view screens for the user.
	'Precondition   :1. Role-based status types ST1 and ST2 are created selecting role 'R1' under 'Roles with view rights' section.
					 2. ST1 and ST2 are associated with resource type RT.
					 3. Resources RS is created under resource type RT with address.
					
					 4. View V1 has status type ST1, ST2 and resource RS.
					 5. Event Template ET is created with ST1, ST2 and RT.
					 6. Event E1 is created under ET selecting RS.
					 7. Status types ST1 and ST2 are under status type section S1.
					
					 8. User U1 has the following rights:
					 a) Role R1
					 b) 'View Resource' right on resource RS.
					 c) 'View Custom View' right.

	9. User U1 has added resources RS and status types ST1 and ST2 to his/her custom view. 
	'Arguments		:None
	'Returns		:None
	'Date			:5/28/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				                        Modified By
	'Date					                            Name
	***************************************************************/

	@Test
	public void testBQS48614() throws Exception {
		try {

			boolean blnLogin = false;

			String strFuncResult = "";
			Login objLogin = new Login();// object of class Login
			CreateUsers objCreateUsers = new CreateUsers();// object of class
			// CreateUser
			Views objViews = new Views();// object of class event list
			EventList objEventList = new EventList();
			ViewMap objViewMap = new ViewMap();// object of class viewMap
			Preferences objPreferences = new Preferences();
			Roles objRoles = new Roles();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			EventSetup objEventSetup = new EventSetup();

			gstrTCID = "48614"; // Test Case Id
			gstrTO = " Verify that when the status type is added back in "
					+ "'Refine Visible Status Types' window for a resource,"
					+ " the changes are reflected on all view screens for the user.";
			gstrReason = "";
			gstrResult = "FAIL";

			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

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

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_Any = "AutoUsr_Any" + System.currentTimeMillis();
			String strUsrFulName_Any = strUserName_Any;

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String statTypeName1 = "ST_1" + strTimeText;
			String statTypeName2 = "ST_2" + strTimeText;
			String[] strStatType = { statTypeName1, statTypeName2 };
			String[] strStatTypeArr = { statTypeName1, statTypeName2 };
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statTypeName1, statTypeName2 };

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";

			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "";
			String strViewType = "";

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

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

			/*
			 * 1. Role-based status types ST1 and ST2 are created selecting role
			 * 'R1' under both 'Roles with view rights' and 'Roles with update
			 * rights' sections.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName_1);

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

				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST1
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statTypeName1, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objST.slectAndDeselectRoleInSTNew(seleniumPrecondition,
						false, false, strRoleViewValue, strRoleUpdateValue,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[0] = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeName1);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST2
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statTypeName2, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objST.slectAndDeselectRoleInSTNew(seleniumPrecondition,
						false, false, strRoleViewValue, strRoleUpdateValue,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[1] = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeName2);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. ST1 and ST2 are associated with resource type RT. */

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
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Resources RS is created under resource type RT with address. */

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

			/* 4. View V1 has status type ST1, ST2 and resource RS. */

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

				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 5. Event Template ET is created with ST1, ST2 and RT. */

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

				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1] };
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

			/* 6. Event E1 is created under ET selecting RS. */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventEndNever(seleniumPrecondition,
						strTempName, strResource, strEveName, strInfo, true);
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

			/* 7. Status types ST1 and ST2 are under status type section S1. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
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
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}

			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strArStatType1, strSection1, true);

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
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 8. User U1 has the following rights: a) Role R1 b) 'Update
			 * Status' and 'View Resource' rights on resource RS. c) 'View
			 * Custom View' right.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User with user set up account right
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_Any, strInitPwd, strConfirmPwd,
						strUsrFulName_Any);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_Any, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

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
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						false, true);

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
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9. User U1 has added resources RS and status types ST1 and ST2 to
			 * his/her custom view. No Exp
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewNewWitTablOrMapOption(selenium,
						strRS, strResrctTypName, statTypeName1);

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

				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTValue[][] = { { strSTvalue[1], "true" } };
				strFuncResult = objPreferences.addSTInEditCustViewOptionPage(
						selenium, strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as any user with 'User - Setup User Accounts right' and
			 * navigate to Setup>>Users 'Users List' screen is displayed
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_Any,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Select to edit user U1, click on the 'Refine' link
			 * corresponding to resource RS under 'Resource Rights' section
			 * 'Refine Visible Status Types' window is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

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

			/*
			 * 4 Deselect status type ST1 and save the changes and save the user
			 * U1 U1 is listed in the 'Users List' screen
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select to edit user U1 again, click on the 'Refine'
			 * link corresponding to resource RS under 'Resource Rights' section
			 * Expected Result:'Refine Visible Status Types' window is
			 * displayed.
			 */
			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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

			/*
			 * STEP : Action:Reselect status type ST1 and save the changes and
			 * save the user U1 Expected Result:U1 is listed in the 'Users List'
			 * screen
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[0], true);
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
				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Logout and login as user U1 and navigate to
			 * View>>V1 Expected Result:Both status types ST1 and ST2 are
			 * displayed for resource RS.
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
				strFuncResult = objLogin.login(selenium, strUserName_1,
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
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View>>Custom Expected Result:Both
			 * status types ST1 and ST2 are displayed for resource RS.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navViewCustomTable(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews
						.checkResTypeRSAndSTInViewCustTabl(selenium,
								strResrctTypName, strResource, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to the map view of custom view Expected
			 * Result:Both ST1 and ST2 are displayed in the resource pop-up
			 * window of RS.
			 */

			/*try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = {};
				strFuncResult = objViewMap.navToCustomViewMapFromShowMap(
						selenium, strResource, strEventStatType,
						strRoleStatType, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}*/
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
				String[] strRoleStatType = { statTypeName1, statTypeName2 };
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strRoleStatType,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View>>Map Expected Result:Both ST1 and
			 * ST2 are displayed in the resource pop-up window of RS.
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1, statTypeName2 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on the event banner of E1 Expected
			 * Result:Both status types ST1 and ST2 are displayed for resource
			 * RS.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on the name of resource RS Expected
			 * Result:Both status types ST1 and ST2 are displayed on the 'View
			 * Resource Detail' screen under section S1.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navAndCheckInViewResourceDetail(
						selenium, strSection1, strStatType, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				String strTestData[] = new String[10];

				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName_1 + "/" + "abc123";
				strTestData[3] = Arrays.toString(strStatTypeArr);
				strTestData[4] = strViewName_1;
				strTestData[5] = "Launch in Mobile application";
				strTestData[6] = strResource;
				strTestData[7] = strSection1;
				strTestData[8] = strEveName;
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
			gstrTCID = "BQS-48614";
			gstrTO = "Verify that when the status type is added back in "
					+ "'Refine Visible Status Types' window for a resource,"
					+ " the changes are reflected on all view screens for the user.";
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
	'Description	:For a user U1, verify that when a status type is deselected in the
					 'Refine Visible Status Types' window for a resource RS, the user cannot update the
					  status of that status type for resource RS.
	'Precondition	:1. Role-based status types ST1 and ST2 are created selecting role 'R1' under both 'Roles with view rights' and 'Roles with update rights' sections.
					2. ST1 and ST2 are associated with resource type RT.
					3. Resources RS is created under resource type RT with address.
					
					4. View V1 has status type ST1, ST2 and resource RS.
					5. Event Template ET is created with ST1, ST2 and RT.
					6. Event E1 is created under ET selecting RS.
					7. Status types ST1 and ST2 are under status type section S1.
					
					8. User U1 has the following rights:
					a) Role R1
					b) 'Update Status' and 'View Resource' rights on resource RS.
					c) 'View Custom View' right.
					
					9. User U1 has added resources RS and status types ST1 and ST2 to his/her custom view. 		
	'Arguments		:None
	'Returns		:None
	'Date	 		:16-Oct-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/

	@Test
	public void testBQS48545() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "BQS-48545 ";
			gstrTO = "For a user U1, verify that when a status type is"
					+ " deselected in the 'Refine Visible Status Types' "
					+ "window for a resource RS, the user cannot update "
					+ "the status of that status type for resource RS.";
			gstrResult = "FAIL";
			gstrReason = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

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

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_Any = "AutoUsr_Any" + System.currentTimeMillis();
			String strUsrFulName_Any = strUserName_Any;

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String statTypeName1 = "ST_1" + strTimeText;
			String statTypeName2 = "ST_2" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statTypeName1, statTypeName2 };

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";

			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "";
			String strViewType = "";

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

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

			/*
			 * 1. Role-based status types ST1 and ST2 are created selecting role
			 * 'R1' under both 'Roles with view rights' and 'Roles with update
			 * rights' sections.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName_1);

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

				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST1
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
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
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST2
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
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
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. ST1 and ST2 are associated with resource type RT. */

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
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Resources RS is created under resource type RT with address. */

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

			/* 4. View V1 has status type ST1, ST2 and resource RS. */

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

				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 5. Event Template ET is created with ST1, ST2 and RT. */

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

				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1] };
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

			/* 6. Event E1 is created under ET selecting RS. */

			try {
				assertEquals("", strFuncResult);

				strFuncResult=objEventSetup.navToEventManagementNew(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(seleniumPrecondition,
						strTempName, strResource, strEveName, strInfo, true);
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

			/* 7. Status types ST1 and ST2 are under status type section S1. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
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
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}

			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strArStatType1, strSection1, true);

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
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 8. User U1 has the following rights: a) Role R1 b) 'Update
			 * Status' and 'View Resource' rights on resource RS. c) 'View
			 * Custom View' right.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User with user set up account right
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_Any, strInitPwd, strConfirmPwd,
						strUsrFulName_Any);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_Any, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

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
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9. User U1 has added resources RS and status types ST1 and ST2 to
			 * his/her custom view. No Exp
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewNewWitTablOrMapOption(selenium,
						strRS, strResrctTypName, statTypeName1);

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

				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTValue[][] = { { strSTvalue[1], "true" } };
				strFuncResult = objPreferences.addSTInEditCustViewOptionPage(
						selenium, strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as any user with 'User - Setup User Accounts right' and
			 * navigate to Setup>>Users 'Users List' screen is displayed
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_Any,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Select to edit user U1, click on the 'Refine' link
			 * corresponding to resource RS under 'Resource Rights' section
			 * 'Refine Visible Status Types' window is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

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

			/*
			 * 4 Deselect status type ST1 and save the changes and save the user
			 * U1 U1 is listed in the 'Users List' screen
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Logout and login as user U1 and navigate to View>>V1 Only ST2
			 * is displayed for resource RS.
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
				strFuncResult = objLogin.login(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strArStatType2 = { statTypeName2 };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.checkStatusTypeInViewScreen(selenium,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Click on the key icon for resource RS Only ST2 is displayed in
			 * the 'Update Status' screen.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName2 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Click on 'Show All Statuses' link in the 'Update Status' screen
			 * Only ST2 is displayed in the 'Update Status' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToShowAllStatus(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName2 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName1 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Navigate to View>>Custom Only ST2 is displayed for resource RS.
			 */

			try {
				assertEquals(statTypeName1 + " is NOT displayed", strFuncResult);

				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objViews
						.checkResTypeRSAndSTInViewCustTabl(selenium,
								strResrctTypName, strResource, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strStatTypeArr = { statTypeName1, statTypeName2 };
				strFuncResult = objViews
						.checkResTypeRSAndSTInViewCustTabl(selenium,
								strResrctTypName, strResource, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9 Click on the key icon for resource RS Only ST2 is displayed in
			 * the 'Update Status' screen.
			 */

			try {
				assertEquals(
						"Resource '"
								+ strResource
								+ "' is NOT displayed on the 'Cusotm View table' screen under "
								+ strResrctTypName
								+ " along with all the status types.",
						strFuncResult);

				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName2 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 10 Click on 'Show All Statuses' link in the 'Update Status'
			 * screen Only ST2 is displayed in the 'Update Status' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToShowAllStatus(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName2 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName1 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 11 Navigate to the map view of custom view Only ST2 is displayed
			 * in the pop-up window for resource RS.
			 */

			try {
				assertEquals(statTypeName1 + " is NOT displayed", strFuncResult);

				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName2 };
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strRoleStatType,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1 };
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strRoleStatType,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 12 Click on 'Update Status' link on resource pop-up window of RS
			 * Only ST2 is displayed in the 'Update Status' screen.
			 */

			try {
				assertEquals(" Role Based Status type " + statTypeName1
						+ " is NOT displayed", strFuncResult);

				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName2 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 13 Click on 'Show All Statuses' link in the 'Update Status'
			 * screen Only ST2 is displayed in the 'Update Status' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName1 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 14 Navigate to View>>Map Only ST2 is displayed in the pop-up
			 * window for resource RS.
			 */

			try {
				assertEquals(statTypeName1 + " is NOT displayed", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName2 };

				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1 };

				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 15 Click on 'Update Status' link on resource pop-up window of RS
			 * Only ST2 is displayed in the 'Update Status' screen.
			 */

			try {
				assertEquals(" Role Based Status type " + statTypeName1
						+ " is NOT displayed", strFuncResult);

				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName2 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 16 Click on 'Show All Statuses' link in the 'Update Status'
			 * screen Only ST2 is displayed in the 'Update Status' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName1 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 17 Click on the event banner of E1 Only ST2 is displayed in the
			 * pop-up window for resource RS.
			 */

			try {
				assertEquals(statTypeName1 + " is NOT displayed", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArStatType2[] = { statTypeName2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strArStatType2[] = { statTypeName1 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 18 Click on the key icon for resource RS Only ST2 is displayed in
			 * the 'Update Status' screen.
			 */

			try {
				assertEquals(
						"Resource '"
								+ strResource
								+ "' is NOT displayed on the 'Event Status' screen under Resource Type "
								+ strResrctTypName
								+ " along with all the status types.",
						strFuncResult);

				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName2 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 19 Click on 'Show All Statuses' link in the 'Update Status'
			 * screen Only ST2 is displayed in the 'Update Status' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToShowAllStatus(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName2 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName1 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 11 Navigate to the map view of custom view Only ST2 is displayed
			 * in the pop-up window for resource RS.
			 */

			try {
				assertEquals(statTypeName1 + " is NOT displayed", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArStatType2[] = { statTypeName2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 21 Click on the name of resource RS Only ST2 is displayed on the
			 * 'View Resource Detail' screen under section S1.
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
				String strArStatType2[] = { statTypeName2 };
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArStatType2[] = { statTypeName1 };
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 22 Click on the status cell of ST2 Only ST2 is displayed in the
			 * 'Update Status' screen.
			 */
			try {
				assertEquals("The Status Type " + statTypeName1
						+ " is NOT displayed on "
						+ "the view resource detail screen. ", strFuncResult);

				strFuncResult = objViews.navToUpdStatusByFrmViewResDetail(
						selenium, strRSValue[0], strSTvalue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName2 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 23 Click on 'Show All Statuses' link in the 'Update Status'
			 * screen Only ST2 is displayed in the 'Update Status' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToShowAllStatus(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName2 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strST = { statTypeName1 };
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(statTypeName1 + " is NOT displayed", strFuncResult);

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-48545";
			gstrTO = "For a user U1, verify that when a status type is deselected "
					+ "in the 'Refine Visible Status Types' window for a resource RS,"
					+ " the user cannot update the status of that status type for resource RS.";
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
				gstrResult = "FAIL";
			}
		}
	}

}

	
