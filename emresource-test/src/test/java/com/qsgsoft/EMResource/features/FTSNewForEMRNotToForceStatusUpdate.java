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
' Description		:This class contains test cases from   requirement
' Requirement Group	:Setting up Status types   
� Requirement		:New for EMR 3.15- Not to force status update.
' Date			    :29/June/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/


public class FTSNewForEMRNotToForceStatusUpdate {
	

	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSNewForEMRNotToForceStatusUpdate");
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
	Selenium selenium;
	String gstrTimeOut;
	



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
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));
		

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}


/****************************************************************************************************************	*
    * This function is called the teardown() function which is executed after every test.
	* The function will take care of stopping the selenium session for every test and writing the execution
	* result of the test. 
	*
****************************************************************************************************************/

	@After
	public void tearDown() throws Exception {
		
		try {
			selenium.close();
		} catch (Exception e) {

		}

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
	'Description		:Edit a number status type, select 'Exempt from Must Update' checkbox and verify that user is NOT prompted to update status.
	'Precondition		:1. User U1 is created with the right 'User must update overdue status'.
					     2. Number status type NST is created without selecting 'Exempt from Must Update' checkbox and is associated with Resource type RT.
					     3. Resource RS is created under resource type RT.
					     4. U1 has update status right on resource RS and a role to update the status type NST.
					     5. View V1 is created selecting RS and NST.
					     6. NST is not updated.
	'Arguments		:None
	'Returns		:None
	'Date			:6/29/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23567() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
	try{	
			gstrTCID = "23567"; // Test Case Id
			gstrTO = " Edit a number status type, select 'Exempt from Must Update' checkbox and verify that" +
					" user is NOT prompted to update status.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn =rdExcel.readData("Login", 3,4);
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName1 = "AutoST1" + strTimeText;
			String strStatTypDefn = "Automation";
			String strSTtype = "Number";
			// RT data
			String strResrcTypName = "AutoRT" + strTimeText;

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			// Role
			String strRolesName = "Rol299" + strTimeText;
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// View
			String strViewName = "AutoV_" + strTimeText;

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";
				
		/*
		* STEP :
		  Action:Precondition:
	     1. User U1 is created with the right 'User must update overdue status'.
	     2. Number status type NST is created without selecting 'Exempt from Must Update' checkbox and is associated with Resource type RT.
	     3. Resource RS is created under resource type RT.
	     4. U1 has update status right on resource RS and a role to update the status type NST.
	     5. View V1 is created selecting RS and NST.
	     6. NST is not updated.
		  Expected Result:No Expected Result
		*/
		//132423
		
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium, statTypeName1);
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
			
			
			//ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {strStatusTypeValues[0]};
				String[] strViewRightValue = {strStatusTypeValues[0]};
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
			
			
		
		//USER
		
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
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
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
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

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
				String[] strSTvalue = { strStatusTypeValues[0]};
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

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
		  Action:Login as a user with the right "Setup Status Types" and navigate to 'Setup >> Status Types'.
		  Expected Result:No Expected Result
		*/
		//132424
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
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
		  Action:Edit the number status type NST, select the checkbox 'Exempt from Must Update' and save
		  Expected Result:Status type NST is saved.
		*/
		//132442
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium, statTypeName1, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectAndDeselectExcemptFromUpdate(selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium, statTypeName1);
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
		  Action:Login as user U1.
		  Expected Result:Status update prompt is NOT displayed.
		*/
		//132428
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName1);
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
		gstrTCID = "23567";
		gstrTO = "Edit a number status type, select 'Exempt from Must Update' checkbox and verify that user is NOT prompted to update status.";
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
	'Description		:Edit a saturation score status type, select 'Exempt from Must Update' checkbox and verify that user is NOT prompted to update status.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/3/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23656() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
	try{

			gstrTCID = "23656"; // Test Case Id
			gstrTO = " Edit a saturation score status type, select 'Exempt from Must Update' checkbox and " +
					"verify that user is NOT prompted to update status.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn =rdExcel.readData("Login", 3,4);
			// ST
			String strStatusTypeValues[] = new String[4];
			String statTypeName1 = "AutoST_" +strTimeText;
			String strStatTypDefn = "Automation";
			String strSTtype = "Saturation Score";
			// RT data
			String strResrcTypName = "AuotoRT_" +strTimeText;

			// Resource
			String strResource = "AutoRS_" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			// Role
			String strRolesName = "AutoRol_" +strTimeText;
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";

		/*
		* STEP :
		  Action:Precondition:
	      1. Saturation score status type SST is created without selecting 'Exempt from Must Update' check box and is associated with Resource type RT.
          2. Resource RS is created under resource type RT.
          3. U1 has update status right on resource RS, a role to update the status type SST and right 'User must update overdue status'.	
          4. View V1 is created selecting RS and SST.
	      5. SST is not updated.
		  Expected Result:No Expected Result
		*/
		//135173
		
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
				String strStatusTypeValue = "Saturation Score";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium, statTypeName1);
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
			
			//Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {strStatusTypeValues[0]};
				String[] strViewRightValue = {strStatusTypeValues[0]};
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
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
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
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

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
				String[] strSTvalue = { strStatusTypeValues[0] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

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
		  Action:Login as a user with the right "Setup Status Types" and navigate to 'Setup >> Status Types'.
		  Expected Result:No Expected Result
		*/
		//135174
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
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
		  Action:Edit the saturation score status type SST, select the checkbox 'Exempt from Must Update' and save
		  Expected Result:Status type SST is saved.
		*/
		//135175
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium, statTypeName1, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectAndDeselectExcemptFromUpdate(selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium, statTypeName1);
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
		  Action:Login as user U1.
		  Expected Result:Status update prompt is NOT displayed.
		*/
		//135176
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName1);
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
		gstrTCID = "23656";
		gstrTO = "Edit a saturation score status type, select 'Exempt from Must Update' checkbox and verify that user is NOT prompted to update status.";
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
	'Description		:Edit a text status type, select 'Exempt from Must Update' checkbox and verify that user is NOT prompted to update status.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/3/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23657() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
	try{	
			gstrTCID = "23657"; // Test Case Id
			gstrTO = " Edit a text status type, select 'Exempt from Must Update' checkbox and verify that" +
					" user is NOT prompted to update status.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn =rdExcel.readData("Login", 3,4);
			// ST
			String strStatusTypeValues[] = new String[4];
			String statTypeName1 = "AutoST_" + strTimeText;
			String strStatTypDefn = "Automation";
			String strSTtype = "Text";
			// RT data
			String strResrcTypName = "AutoRT_" +strTimeText;

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			// Role
			String strRolesName = "AutoRol_" +strTimeText;
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// View
			String strViewName = "AutoV_" +strTimeText;

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";

		/*
		* STEP :
		  Action:Precondition:
		1. Text status type TST is created without selecting 'Exempt from Must Update' checkbox and is associated with Resource type RT.
		2. Resource RS is created under resource type RT.
		3. U1 has update status right on resource RS, a role to update the status type TST and right 'User must update overdue status'
		4. View V1 is created selecting RS and TST.
		5. TST is not updated.
			  Expected Result:No Expected Result
		*/
		//135185
		
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
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium, statTypeName1);
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
				String[] updateRightValue = {strStatusTypeValues[0]};
				String[] strViewRightValue = {strStatusTypeValues[0]};
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
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
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
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

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
				String[] strSTvalue = { strStatusTypeValues[0] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

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
		  Action:Login as a user with the right "Setup Status Types" and navigate to 'Setup >> Status Types'.
		  Expected Result:No Expected Result
		*/
		//135186
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
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
		  Action:Edit the text status type TST, select the checkbox 'Exempt from Must Update' and save
		  Expected Result:Status type TST is saved.
		*/
		//135187
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName1, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, true,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium, statTypeName1);
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
		  Action:Login as user U1.
		  Expected Result:Status update prompt is NOT displayed.
		*/
		//135188
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName1);
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
		gstrTCID = "23657";
		gstrTO = "Edit a text status type, select 'Exempt from Must Update' checkbox and verify that user is NOT prompted to update status.";
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
	'Description		:Edit a multi status type, select 'Exempt from Must Update' checkbox and verify that user is NOT prompted to update status.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/3/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23655() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
	try{
			gstrTCID = "23655"; // Test Case Id
			gstrTO = " Edit a multi status type, select 'Exempt from Must Update' checkbox and verify that user is NOT prompted to update status.";// Test
																																					// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn =rdExcel.readData("Login", 3,4);
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName1 = "AutoST_" + strTimeText;
			String strStatTypDefn = "Automation";
			String strSTtype = "Multi";
			// RT data
			String strResrcTypName = "AutoRT_" +strTimeText;

			// Resource
			String strResource = "AutoRS_" +strTimeText;
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			// Role
			String strRolesName = "AutoRol_" +strTimeText;
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// View
			String strViewName = "AutoV_" + strTimeText;

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";

		/*
		* STEP :
		  Action:Precondition:
		1. Multi status type MST is created without selecting 'Exempt from Must Update' checkbox and is associated with Resource type RT.
		2. Resource RS is created under resource type RT.
		3. U1 has update status right on resource RS, a role to update MST and the right 'User must update overdue status'. 
		4. View V1 is created selecting RS and MST.
		5. MST is not updated.
			  Expected Result:No Expected Result
		*/
		//135147
			

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
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, true);
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
				String[] updateRightValue = {strStatusTypeValues[0]};
				String[] strViewRightValue = {strStatusTypeValues[0]};
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
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
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
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

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
				String[] strSTvalue = { strStatusTypeValues[0] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

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
		  Action:Login as a user with the right "Setup Status Types" and navigate to 'Setup >> Status Types'.
		  Expected Result:No Expected Result
		*/
		//135148
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
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
			  Action:Edit the multi status type MST, select the checkbox 'Exempt from Must Update' and save
			  Expected Result:Status type MST is saved.
			*/
			//135149

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName1, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, true,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium, statTypeName1);
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
		  Action:Login as user U1.
		  Expected Result:Status update prompt is NOT displayed.
		*/
		//135150

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName1);

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
		gstrTCID = "23655";
		gstrTO = "Edit a multi status type, select 'Exempt from Must Update' checkbox and verify that user is NOT prompted to update status.";
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
	'Description		:Edit a number status type, deselect 'Exempt from Must Update' checkbox and verify that user is prompted to update status.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/3/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23568() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
	try{

			gstrTCID = "23568"; // Test Case Id
			gstrTO = " Edit a number status type, deselect 'Exempt from Must Update' checkbox and verify that " +
					"user is prompted to update status.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn =rdExcel.readData("Login", 3,4);
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName1 = "AutoST1_" + strTimeText;
			String strStatTypDefn = "Automation";
			String strSTtype = "Number";
			// RT data
			String strResrcTypName = "AutoRT_" +strTimeText;

			// Resource
			String strResource = "AutoRS_" +strTimeText;
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			// Role
			String strRolesName = "AutoRol" + strTimeText;
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUserName1 = "Acm" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// View
			String strViewName = "AutoV_" +strTimeText;

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";

		/*
		* STEP :
		  Action:Precondition:
		1. Number Status type NST is created selecting 'Exempt from Must Update' check box.
		2. Associate NST with resource type RT and a resource RS is created under RT.
		3. U1 has update status right on resource RS, a role to update all the status types and the right 'User must update overdue status'.
		4. View V1 is created selecting all status types and RS.
		5. Status type NST is not updated.
		  Expected Result:No Expected Result
		*/
		//132575
		
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
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, true,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium, statTypeName1);
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
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user 2

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
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = {};
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, true, strSTvalue,
						false, strRSValue);

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
		  Action:Login as a user with the right "Setup Status Types" and navigate to 'Setup >> Status Types'.
		  Expected Result:No Expected Result
		*/
		//132576
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
		  Action:Edit the number status type NST, deselect the checkbox 'Exempt from Must Update' and save
		  Expected Result:Status type NST is saved.
		*/
		//132577
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName1, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, false,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium, statTypeName1);
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
		  Action:Login as user U1.
		  Expected Result:Status update prompt with NST is displayed.
		*/
		//132578

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Do not update the status and click on 'Cancel'.
		  Expected Result:The update status prompt is redisplayed.
				  User is not allowed to use the application until he updates the status of NST.
		*/
		//132579

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.cancelAndcheckUpdateStatPrompt(
						selenium, strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Regional Map View screen is NOT displayed", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}
			
		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "23568";
		gstrTO = "Edit a number status type, deselect 'Exempt from Must Update' checkbox and verify that user is prompted to update status.";
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
	'Description		:Edit a multi status type, deselect 'Exempt from Must Update' checkbox and verify that user is prompted to update status.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/4/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23659() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		try {

			gstrTCID = "23659"; // Test Case Id
			gstrTO = " Edit a saturation score status type, deselect 'Exempt from" +
					" Must Update' checkbox and verify that user is prompted to " +
					"update status.";// Test
																																							// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName1 = "SST" + strTimeText;
			String strStatTypDefn = "Automation";
			String strSTtype = "Saturation Score";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			// Role
			String strRolesName = "AutoRol_" + strTimeText;
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUserName1 = "Acm" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// View
			String strViewName = "AutoV_" + strTimeText;

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";

			/*
			 * STEP : Action:Precondition: 1. Saturation score status type SST
			 * is created selecting 'Exempt from Must Update' check box. 2.
			 * Associate MST with resource type RT and a resource RS is created
			 * under RT. 3. U1 has update status right on resource RS, a role to
			 * update the status type MST and right 'User must update overdue
			 * status' 4. View V1 is created selecting all status types and RS.
			 * 5. Status type MST is not updated. Expected Result:No Expected
			 * Result
			 */
			// 135104

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
				String strStatusTypeValue = "Saturation Score";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, true,
								false);
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
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user 2

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
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = {};
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, true, strSTvalue,
						false, strRSValue);

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
			 * STEP : Action:Login as a user with the right "Setup Status Types"
			 * and navigate to 'Setup >> Status Types'. Expected Result:No
			 * Expected Result
			 */
			// 135105
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
			 * STEP : Action:Edit the saturation score status type SST, deselect
			 * the checkbox 'Exempt from Must Update' and save Expected
			 * Result:Status type SST is saved.
			 */
			// 135106

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName1, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, false,
								false);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user U1. Expected Result:Status update
			 * prompt with SST is displayed.
			 */
			// 135107

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Do not update the status and click on 'Cancel'.
			 * Expected Result:The update status prompt is redisplayed. User is
			 * not allowed to use the application until he updates the status.
			 */
			// 135108

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.cancelAndcheckUpdateStatPrompt(
						selenium, strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Regional Map View screen is NOT displayed",
						strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "23659";
			gstrTO = "Edit a saturation score status type, deselect 'Exempt from Must Update' checkbox and verify that user is prompted to update status.";
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
	'Description		:Edit a multi status type, deselect 'Exempt from Must Update' checkbox and verify that user is prompted to update status.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/4/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23658() throws Exception {
		
		boolean blnLogin=false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
	try{
		

	
		gstrTCID = "23658";	//Test Case Id	
		gstrTO = " Edit a multi status type, deselect 'Exempt from Must Update' checkbox and verify that user is prompted to update status.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");
		String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strLoginUserName = "";
		String strLoginPassword = "";
		String strRegn =rdExcel.readData("Login", 3,4);
		// ST
		String strStatusTypeValues[] = new String[1];
		String statTypeName1 = "MSTs" +strTimeText;
		String strStatTypDefn = "Automation";
		String strSTtype = "Multi";
		// RT data
		String strResrcTypName = "AutoRT_" +strTimeText;

		// Resource
		String strResource = "AutoRS_" + strTimeText;
		String strHavBed = "No";
		String strAbbrv = "abb";
		String strResVal = "";
		String strRSValue[] = new String[1];

		// Role
		String strRolesName = "AutoRol_" +strTimeText;
		String strRoleValue = "";

		// USER
		String strUserName = "AutoUsr" + System.currentTimeMillis();
		String strUserName1 = "Acm" + System.currentTimeMillis();
		String strInitPwd = rdExcel.readData("Login", 4, 2);
		String strConfirmPwd = rdExcel.readData("Login", 4, 2);
		String strUsrFulName = "autouser";
		// View
		String strViewName = "AutoV_" +strTimeText;

		String strVewDescription = "";
		String strViewType = "Resource (Resources and status "
				+ "types as rows. Status, comments and timestamps as columns.)";

		/*
		* STEP :
		  Action:Precondition:
			1. Multi status type MST is created selecting 'Exempt from Must Update' check box.
			2. Associate MST with resource type RT and a resource RS is created under RT.
			3. U1 has update status right on resource RS, a role to update the status type MST and right 'User must update overdue status'
			4. View V1 is created selecting all status types and RS.
			5. Status type MST is not updated.
				  Expected Result:No Expected Result
		*/
		//135096
		

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
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, true,
								true);
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
				String strStatTypeColor="Black";
				String strStatusName="status1";
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(selenium, statTypeName1, strStatusName, strSTtype, strStatTypeColor, true);
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
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user 2

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
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = {};
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, true, strSTvalue,
						false, strRSValue);

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
		  Action:Login as a user with the right "Setup Status Types" and navigate to 'Setup >> Status Types'.
		  Expected Result:No Expected Result
		*/
		//135097
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
		  Action:Edit the multi status type MST, deselect the checkbox 'Exempt from Must Update' and save
		  Expected Result:Status type MST is saved.
		*/
		//135098
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName1, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, false,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium, statTypeName1);
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
		  Action:Login as user U1.
		  Expected Result:Status update prompt with MST is displayed.
		*/
		//135099
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Do not update the status and click on 'Cancel'.
		  Expected Result:The update status prompt is redisplayed again.
				  User is not allowed to use the application until he updates the status.
		*/
		//135100

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.cancelAndcheckUpdateStatPrompt(
						selenium, strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Regional Map View screen is NOT displayed",
						strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}
		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "23658";
		gstrTO = "Edit a multi status type, deselect 'Exempt from Must Update' checkbox and verify that user is prompted to update status.";
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
	'Description		:Edit a text status type, deselect 'Exempt from Must Update' checkbox and verify that user is prompted to update status.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/4/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23660() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
	try{
		
			gstrTCID = "23660"; // Test Case Id
			gstrTO = " Edit a text status type, deselect 'Exempt from Must Update' checkbox and verify that user " +
					"is prompted to update status.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn =rdExcel.readData("Login", 3,4);
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName1 = "AutoST_" +strTimeText;
			String strStatTypDefn = "Automation";
			String strStandardSTtype = "";
			String strDescription = "Automation";
			String strStatues = "";
			String strSTtype = "Text";
			// RT data
			String strResrcTypName = "AutoRT_" +strTimeText;

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			// Role
			String strRolesName = "AutoRol_" + strTimeText;
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUserName1 = "Acm" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// View
			String strViewName = "AutoV_" +strTimeText;

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";

		/*
		* STEP :
		  Action:Precondition:
			1. Text status type TST is created selecting 'Exempt from Must Update' check box.
			2. Associate TST with resource type RT and a resource RS is created under RT.
			3. User U1 has update status right on resource RS, a role to update the status type TST and right 'User must update overdue status'
		    4. View V1 is created selecting all status types and RS.
			5. Status type TST is not updated.
		    Expected Result:No Expected Result
		*/
		//135116
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
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, true,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyST(selenium,
						statTypeName1, strSTtype, strStandardSTtype,
						strDescription, strStatues);
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
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user 2

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
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = {};
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, true, strSTvalue,
						false, strRSValue);

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
		  Action:Login as a user with the right "Setup Status Types" and navigate to 'Setup >> Status Types'.
		  Expected Result:No Expected Result
		*/
		//135117
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
		  Action:Edit the text status type TST, deselect the checkbox 'Exempt from Must Update' and save
		  Expected Result:Status type TST is saved.
		*/
		//135118
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName1, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, false,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyST(selenium,
						statTypeName1, strSTtype, strStandardSTtype,
						strDescription, strStatues);
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
		  Action:Login as user U1.
		  Expected Result:Status update prompt with TST is displayed.
		*/
		//135119

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Do not update the status and click on 'Cancel'.
		  Expected Result:The update status prompt is redisplayed.
				  User is not allowed to use the application until he updates the status.
		*/
		//135120
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.cancelAndcheckUpdateStatPrompt(
						selenium, strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Regional Map View screen is NOT displayed",
						strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "23660";
		gstrTO = "Edit a text status type, deselect 'Exempt from Must Update' checkbox and verify that user is prompted to update status.";
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
	'Description		:Verify that user is not prompted to update status when the number status type is created selecting 'Exempt from Must Update' checkbox and reset value upon expiration.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/4/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23569() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
	try{

		gstrTCID = "23569";	//Test Case Id	
		gstrTO = " Verify that user is not prompted to update status when the number status type is created selecting 'Exempt from Must Update' checkbox and reset value upon expiration.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");
		String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		String strLoginUserName = "";
		String strLoginPassword = "";
		String strRegn =rdExcel.readData("Login", 3,4);
		// ST
		String strStatusTypeValues[] = new String[1];
		String strSTypeValue[] = new String[1];
		String statTypeName1 = "ST1" +strTimeText;
		String statTypeName2 = "NST" + strTimeText;
		String strStatTypDefn = "Automation";
		String strUpdateValue="1";
		// RT data
		String strResrcTypName = "AutoRT_" +strTimeText;

		// Resource
		String strResource = "AutoRS_" +strTimeText;
		String strHavBed = "No";
		String strAbbrv = "abb";
		String strResVal = "";
		String strReSValue[] = new String[1];

		// Role
		String strRolesName = "AutoRol_" + strTimeText;
		String strRoleValue = "";

		// USER
		String strUserName = "AutoUsr" + System.currentTimeMillis();
		String strUserName1 = "Acm" + System.currentTimeMillis();
		String strInitPwd = rdExcel.readData("Login", 4, 2);
		String strConfirmPwd = rdExcel.readData("Login", 4, 2);
		String strUsrFulName = "autouser";
		// View
		String strViewName = "AutoV_" +strTimeText;

		String strVewDescription = "";
		String strViewType = "Resource (Resources and status "
				+ "types as rows. Status, comments and timestamps as columns.)";
			
		/*
		* STEP :
		  Action:Precondition:
	     1. Status type ST is created without selecting 'Exempt from Must Update' check box.
	     2. Associate ST with resource type RT and a resource RS is created under RT.
	     3. U1 has update status right on resource RS, a role to update all the status types and right 'User must update overdue status'.
	     4. View V1 is created selecting all status types and RS.
	     5. Status type ST is updated with a value.
		  Expected Result:No Expected Result
		*/
		//132733
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
			strFuncResult = objStatusTypes.savAndVerifySTNew(selenium, statTypeName1);
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
				strReSValue[0] = strResVal;
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
			strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
					selenium, strResource,strReSValue[0], false, true,
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
									.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
							true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			selenium.click(propElementDetails
					.getProperty("CreateNewUsr.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		// user 2

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
									.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
							true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		// view1

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews.navRegionViewsList(selenium);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String[] strSTvalue = {};
			strFuncResult = objViews.createView(selenium, strViewName,
					strVewDescription, strViewType, true, true, strSTvalue,
					false,strReSValue);

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
			strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
					strInitPwd);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews.fillAndSavUpdateStatus(selenium, strUpdateValue, strStatusTypeValues[0], false, "", "");
			selenium.click("css=input[value='Save']");
			  selenium.waitForPageToLoad(gstrTimeOut);
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
		  Action:Login as a user with the right 'Setup Status Types' and navigate to 'Setup >> Status Types'.
		  Expected Result:No Expected Result
		*/
		//132734
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
		  Action:Click on 'Create New Status Type', select number type and proceed.
		  Expected Result:'Create Number Status Type' screen is displayed.
		*/
		//132735
		/*
		* STEP :
		  Action:Provide mandatory data, select the checkbox 'Exempt from Must Update',
		   provide a value for expiration time (Ex 5 min), select 
		   'Reset value upon expiration' checkbox and save.
		  Expected Result:Status type NST is created.
		*/
		//132736

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				String strExpHr = "00";
				String strExpMn = "05";
				strFuncResult = objStatusTypes
						.selectSTypesAndFilMandFldsWithExTime(selenium,
								strStatusTypeValue, statTypeName2,
								strStatTypDefn, strExpHr, strExpMn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.SelectAndDeselectResetValueForST(selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTypeValue[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName2);

				if (strSTypeValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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
			
		/*
		* STEP :
		  Action:Associate NST with resource type RT and save.
		  Expected Result:No Expected Result
		*/
		//132737
			
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
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(selenium, strSTypeValue[0], true);
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
				String[] strSTvalue = {};
				String[][] strRSValue={};
				strFuncResult = objViews.fillViewFields(selenium, strViewName, strVewDescription, strViewType, false, true, strSTvalue, false, strRSValue);
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
		  Action:Login as user U1.
		  Expected Result:Status update prompt is NOT displayed.
		*/
		//132738
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Update NST from view screen.
		  Expected Result:Updated value is displayed in view screen.
		*/
		//132740
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strNavElement="css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium, strUpdateValue, strSTypeValue[0], false, "", "");
				selenium.click("css=input[value='Save']");
				  selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateStatusType(selenium, strResrcTypName, statTypeName2, strUpdateValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

		/*
		* STEP :
		  Action:Wait till the status expires (5 min).
		  Expected Result:Status value of NST is cleared in view screen.
				  User does NOT receive update status prompt.
		*/
		//132741
			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(500000);
				strFuncResult = objViews.verifyUpdateStatusType(selenium,
						strResrcTypName, statTypeName2, strUpdateValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("The status value " + strUpdateValue
							+ " is NOT updated and displayed on the view screen", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName2);
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
		gstrTCID = "23569";
		gstrTO = "Verify that user is not prompted to update status when the number status type is created selecting 'Exempt from Must Update' checkbox and reset value upon expiration.";
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
'Description		:Verify that user is not required to update status in the update status 
                     prompt when the text status type is created selecting 'Exempt from Must Update' 
                     checkbox and reset value upon expiration.
'Precondition		:
'Arguments		    :None
'Returns		    :None
'Date			    :7/5/2012
'Author			    :QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testFTS23663() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
	try{	
		gstrTCID = "23663";	//Test Case Id	
		gstrTO = " Verify that user is not required to update status in the update status prompt when the text status type is created selecting 'Exempt from Must Update' checkbox and reset value upon expiration.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");
		String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strLoginUserName = "";
		String strLoginPassword = "";
		String strRegn =rdExcel.readData("Login", 3, 4);
		// ST
		String strStatusTypeValues[] = new String[1];
		String strSTypeValue[] = new String[1];
		String statTypeName1 = "ST1" + System.currentTimeMillis();
		String statTypeName2 = "TST" + System.currentTimeMillis();
		String strStatTypDefn = "Automation";
		String strUpdateValue="1";
		// RT data
		String strResrcTypName = "AutoRT_" +strTimeText;

		// Resource
		String strResource = "AutoRS_" +strTimeText;
		String strHavBed = "No";
		String strAbbrv = "abb";
		String strResVal = "";
		String strReSValue[] = new String[1];

		// Role
		String strRolesName = "AutoRol_" +strTimeText;
		String strRoleValue = "";

		// USER
		String strUserName = "AutoUsr" + System.currentTimeMillis();
		String strUserName1 = "Acm" + System.currentTimeMillis();
		String strInitPwd = rdExcel.readData("Login", 4, 2);
		String strConfirmPwd = rdExcel.readData("Login", 4, 2);
		String strUsrFulName = "autouser";
		// View
		String strViewName = "AutoV_" +strTimeText;

		String strVewDescription = "";
		String strViewType = "Resource (Resources and status "
				+ "types as rows. Status, comments and timestamps as columns.)";
			

		/*
		* STEP :
		  Action:Precondition:
         1. Status type ST is created without selecting 'Exempt from Must Update' check box.
	     2. Associate ST with resource type RT and a resource RS is created under RT.
	     3. U1 has update status right on resource RS, a role to update all the status types and the right 'User must update overdue status'.
	     4. View V1 is created selecting all status types and RS.
         5. Status type ST is updated with a value.
		  Expected Result:No Expected Result
		*/
		//135061
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
					strReSValue[0] = strResVal;
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strReSValue[0], false, true,
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
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user 2

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
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
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

			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = {};
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, true, strSTvalue,
						false, strReSValue);

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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdateValue, strStatusTypeValues[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
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
		  Action:Login as a user with the right 'Setup Status Types' and navigate to 'Setup >> Status Types'.
		  Expected Result:No Expected Result
		*/
		//135062
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
		  Action:Click on 'Create New Status Type', select text type and proceed.
		  Expected Result:No Expected Result
		*/
		//135063
		/*
		* STEP :
		  Action:Provide mandatory data, select the checkbox 'Exempt from Must Update', provide a value for expiration time (Ex 5 min), select 'Reset value upon expiration' checkbox and save.
		  Expected Result:Status type TXT is created.
		*/
		//135064
			
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				String strExpHr = "00";
				String strExpMn = "05";
				strFuncResult = objStatusTypes
						.selectSTypesAndFilMandFldsWithExTime(selenium,
								strStatusTypeValue, statTypeName2,
								strStatTypDefn, strExpHr, strExpMn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, true,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.SelectAndDeselectResetValueForST(selenium, true, false);
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
				strSTypeValue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTypeName2);

				if (strSTypeValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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

		/*
		* STEP :
		  Action:Associate TXT with resource type RT and save.
		  Expected Result:No Expected Result
		*/
		//135065
			
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
			// RT
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
						selenium, strSTypeValue[0], true);
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
				String[] strSTvalue = {};
				String[][] strRSValue = {};
				strFuncResult = objViews.fillViewFields(selenium, strViewName,
						strVewDescription, strViewType, false, true,
						strSTvalue, false, strRSValue);
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
		  Action:Login as user U1.
		  Expected Result:Status update prompt is NOT displayed.
		*/
		//135066
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Update TXT from view screen.
		  Expected Result:Updated value is displayed in view screen.
		*/
		//135067
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
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
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdateValue, strSTypeValue[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateStatusType(selenium,
						strResrcTypName, statTypeName2, strUpdateValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Wait till the status expires (5 min).
		  Expected Result:User does NOT receive update status prompt.
		*/
		//135068
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(500000);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName2);
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
		gstrTCID = "23663";
		gstrTO = "Verify that user is not required to update status in the update status prompt when the text status type is created selecting 'Exempt from Must Update' checkbox and reset value upon expiration.";
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
	'Description		:Verify that user is not prompted to update status when the saturation score status type is created selecting 'Exempt from Must Update' checkbox and reset value upon expiration.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/5/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23662() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
	try{	
		gstrTCID = "23662";	//Test Case Id	
		gstrTO = " Verify that user is not prompted to update status when the saturation score status type is created selecting 'Exempt from Must Update' checkbox and reset value upon expiration.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	
		
		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");
		String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strLoginUserName = "";
		String strLoginPassword = "";
		String strRegn =rdExcel.readData("Login", 3,4);
		// ST
		String strStatusTypeValues[] = new String[1];
		String strSTypeValue[] = new String[1];
		String statTypeName1 = "ST1" +strTimeText;
		String statTypeName2 = "SST" + strTimeText;
		String strStatTypDefn = "Automation";
		
		// RT data
		String strResrcTypName = "AutoRT_" +strTimeText;

		// Resource
		String strResource = "AutoRS_" + strTimeText;
		String strHavBed = "No";
		String strAbbrv = "abb";
		String strResVal = "";
		String strReSValue[] = new String[1];

		// Role
		String strRolesName = "AutoRol_" +strTimeText;
		String strRoleValue = "";

		// USER
		String strUserName = "AutoUsr" + System.currentTimeMillis();
		String strUserName1 = "Acm" + System.currentTimeMillis();
		String strInitPwd = rdExcel.readData("Login", 4, 2);
		String strConfirmPwd = rdExcel.readData("Login", 4, 2);
		String strUsrFulName = "autouser";
		// View
		String strViewName = "AutoV_" +strTimeText;

		String strVewDescription = "";
		String strViewType = "Resource (Resources and status "
				+ "types as rows. Status, comments and timestamps as columns.)";
			
		/*
		* STEP :
		  Action:Precondition:
			1. Status type ST is created without selecting 'Exempt from Must Update' check box.
			2. Associate ST with resource type RT and a resource RS is created under RT.
			3. U1 has update status right on resource RS, a role to update all the status types and right 'User must update overdue status'.
			4. View V1 is created selecting all status types and RS.
			5. Status type ST is updated with a value.
				  Expected Result:No Expected Result
		*/
		//135052
		
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
					strReSValue[0] = strResVal;
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strReSValue[0], false, true,
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
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user 2

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
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
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

			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = {};
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, true, strSTvalue,
						false, strReSValue);

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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdateValue="1";
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdateValue, strStatusTypeValues[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
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
		  Action:Login as a user with the right 'Setup Status Types' and navigate to 'Setup >> Status Types'.
		  Expected Result:No Expected Result
		*/
		//135053
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
		  Action:Click on 'Create New Status Type', select saturation score type and proceed.
		  Expected Result:No Expected Result
		*/
		//135054
		/*
		* STEP :
		  Action:Provide mandatory data, select the checkbox 'Exempt from Must Update', provide a value for expiration time (Ex 5 min), select 'Reset value upon expiration' checkbox and save.
		  Expected Result:Status type SST is created.
		*/
		//135055
			
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Saturation Score";
				String strExpHr = "00";
				String strExpMn = "05";
				strFuncResult = objStatusTypes
						.selectSTypesAndFilMandFldsWithExTime(selenium,
								strStatusTypeValue, statTypeName2,
								strStatTypDefn, strExpHr, strExpMn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, true,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.SelectAndDeselectResetValueForST(selenium, true, false);
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
				strSTypeValue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTypeName2);

				if (strSTypeValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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

		/*
		* STEP :
		  Action:Associate SST with resource type RT and save.
		  Expected Result:No Expected Result
		*/
		//135056
			
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
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(selenium, strSTypeValue[0], true);
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
				String[] strSTvalue = {};
				String[][] strRSValue={};
				strFuncResult = objViews.fillViewFields(selenium, strViewName, strVewDescription, strViewType, false, true, strSTvalue, false, strRSValue);
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
		  Action:Login as user U1.
		  Expected Result:Status update prompt is NOT displayed.
		*/
		//135057
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Update SST from view screen.
		  Expected Result:Updated value is displayed in view screen.
		*/
		//135058
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strNavElement="css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue={"1","1","1","1","1","1","1","1","1"};
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium, strUpdateValue, strSTypeValue[0]);
				selenium.click("css=input[value='Save']");
				  selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strUpdateValue="700";
				strFuncResult = objViews.verifyUpdateStatusType(selenium, strResrcTypName, statTypeName2, strUpdateValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

		/*
		* STEP :
		  Action:Wait till the status expires (5 min).
		  Expected Result:Status value of SST is cleared in view screen.
				  User does NOT receive update status prompt.
		*/
		//135059
			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(500000);
				String strUpdateValue="700";
				strFuncResult = objViews.verifyUpdateStatusType(selenium, strResrcTypName, statTypeName2, strUpdateValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("The status value " + 700
				+ " is NOT updated and displayed on the view screen", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName2);
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
		gstrTCID = "23662";
		gstrTO = "Verify that user is not prompted to update status when the saturation score status type is created selecting 'Exempt from Must Update' checkbox and reset value upon expiration.";
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
	'Description		:Create a number status type selecting 'Exempt from Must Update' check box and verify that user is not prompted to update status.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/6/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23562() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		General objGeneral=new General();
	try{
		
		gstrTCID = "23562";	//Test Case Id	
		gstrTO = " Create a number status type selecting 'Exempt from Must Update' check box and verify that " +
				"user is not prompted to update status.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");
		String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
		String strFILE_PATH = pathProps.getProperty("TestData_path");
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strLoginUserName = rdExcel.readData("Login", 3, 1);
		String strLoginPassword = rdExcel.readData("Login", 3, 2);
		String strRegn =rdExcel.readData("Login", 3,4);
		// ST
		String strStatusTypeValues[] = new String[1];
		String strSTypeValue[] = new String[1];
		String statTypeName1 = "ST1" +strTimeText;
		String statTypeName2 = "NST" + strTimeText;
		String strStatTypDefn = "Automation";
		
		// RT data
		String strResrcTypName = "AutoRT_" + strTimeText;

		// Resource
		String strResource = "AutoRS_" +strTimeText;
		String strHavBed = "No";
		String strAbbrv = "abb";
		String strResVal = "";
		String strReSValue[] = new String[1];

		// Role
		String strRolesName = "AutoRol" + strTimeText;
		String strRoleValue = "";

		// USER
		String strUserName = "AutoUsr" + System.currentTimeMillis();
		String strUserName1 = "Acm" + System.currentTimeMillis();
		String strInitPwd = rdExcel.readData("Login", 4, 2);
		String strConfirmPwd = rdExcel.readData("Login", 4, 2);
		String strUsrFulName = "autouser";
		
		String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
				strFILE_PATH);
		String strByResourceType = rdExcel.readInfoExcel("User_Template",
				7, 12, strFILE_PATH);
		String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
				13, strFILE_PATH);
		String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
				14, strFILE_PATH);
		// View
		String strViewName = "AutoV_" + strTimeText;

		String strVewDescription = "";
		String strViewType = "Resource (Resources and status "
				+ "types as rows. Status, comments and timestamps as columns.)";
			

		/*
		* STEP :
		  Action:Precondition:
		1. User U1 is created with the right 'User must update overdue status'.
		2. Status type ST is created without selecting 'Exempt from Must Update' check box.
		3. Associate ST with resource type RT and a resource RS is created under RT.
		4. U1 has update status right on resource RS and a role to update all the status types.
		5. View V1 is created selecting all status types and RS.
		6. Status type ST is updated with a value.
			  Expected Result:No Expected Result
		*/
		//125734

			try {
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
					strReSValue[0] = strResVal;
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strReSValue[0], false, true,
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
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user 2

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
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
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

			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = {};
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, true, strSTvalue,
						false, strReSValue);

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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdateValue = "1";
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdateValue, strStatusTypeValues[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");

		/*
		* STEP :
		  Action:Login as a user with the right 'Setup Status Types' and navigate to 'Setup >> Status Types'.
		  Expected Result:No Expected Result
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:	Click on 'Create New Status Type', select Number type and proceed. 
		  Expected Result:'Create Text Status Type' screen is displayed.
		*/
			
		/*
		* STEP :
		  Action:Provide mandatory data, select the checkbox 'Exempt from Must Update',
		   select to expire at time T1 and save.
		  Expected Result:Status type NST is created.
		*/
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				String strExpHr = "00";
				String strExpMn = "05";
				strFuncResult = objStatusTypes
						.selectSTypesAndFilMandFldsWithExTime(selenium,
								strStatusTypeValue, statTypeName2,
								strStatTypDefn, strExpHr, strExpMn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTypeValue[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName2);
				if (strSTypeValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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
		/*
		* STEP :
		  Action:Associate NST with resource type RT and save.
		  Expected Result:No Expected Result
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
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(selenium, strSTypeValue[0], true);
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
				String[] strSTvalue = {};
				String[][] strRSValue={};
				strFuncResult = objViews.fillViewFields(selenium, strViewName, strVewDescription, strViewType, false, true, strSTvalue, false, strRSValue);
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
		  Action:Login as user U1.
		  Expected Result:Status update prompt is NOT displayed.
		*/

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Update NST.
		  Expected Result:Updated value is displayed in view screen.
		*/

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strNavElement="css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strUpdateValue="0";
				strFuncResult = objViews.fillAndSavUpdateStatus(
						selenium, strUpdateValue, strSTypeValue[0], false,
						"", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdateValue1="0";
				strFuncResult = objViews.verifyUpdatedValueOfRowWiseSTInViewScreen(selenium,
						strResrcTypName, statTypeName2, strUpdateValue1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:At time T1.
		  Expected Result:User does NOT receive update status prompt for NST expiration.
		*/		
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(500000);
				strFuncResult = objViews.varUpdateStatPrompt(selenium,
						strResource, statTypeName2);
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
		  Action:Deactivate TST and then Reactivate it.
		  Expected Result:No Expected Result
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strSTtype="Number";
				strFuncResult = objStatusTypes.editStatusTypePage(selenium, statTypeName2, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.activateAndDeactivateST(selenium, statTypeName2, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDesectIncludeInactiveST(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strSTtype="Number";
				strFuncResult = objStatusTypes.editStatusTypePage(selenium, statTypeName2, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.activateAndDeactivateST(selenium, statTypeName2, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDesectIncludeInactiveST(selenium, false);
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
		  Action:Refresh view screen V1.
		  Expected Result:Update status prompt is NOT displayed.
		*/
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
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
				strFuncResult =objGeneral.refreshPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Update NST from view screen.
		  Expected Result:No Expected Result
		*/

			try {
				assertEquals("", strFuncResult);
				String strNavElement="css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strUpdateValue="1";
				strFuncResult = objViews.fillAndSavUpdateStatus(
						selenium, strUpdateValue, strSTypeValue[0], false,
						"", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
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
		  Action:Deactivate RT and then Reactivate it.
		  Expected Result:No Expected Result
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(selenium, strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selectDesectIncludeInactiveRT(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(selenium, strResrcTypName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selectDesectIncludeInactiveRT(selenium, false);
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
		  Action:Refresh view screen V1.
		  Expected Result:Update status prompt is displayed with ST expanded.
				  NST is not displayed in the prompt.
		*/
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Update TST from view screen.
		  Expected Result:No Expected Result
		*/
			try {
				assertEquals("'Update Status' prompt for "+statTypeName2+" is NOT displayed or it is not expanded",
						strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				Thread.sleep(1000);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strNavElement="css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strUpdateValue="0";
				strFuncResult = objViews.fillAndSavUpdateStatus(
						selenium, strUpdateValue, strSTypeValue[0], false,
						"", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
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
		  Action:Deselect NST from RT and then Reselect it.
		  Expected Result:No Expected Result
		*/
				// TST Deselect
			
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
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(selenium, strSTypeValue[0], false);
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
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(selenium, strSTypeValue[0], true);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Refresh view screen v1.
		  Expected Result:Update status prompt is NOT displayed.
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
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
				strFuncResult =objGeneral.refreshPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName2);
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
		gstrTCID = "23562";
		gstrTO = "Create a number status type selecting 'Exempt from Must Update' check box and verify that user is not prompted to update status.";
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
	'Description		:Create a Saturation score status type selecting 'Exempt from Must Update' 
	                     check box and verify that user is not prompted to update status.
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :7/6/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				                         Modified By
	'Date					                              Name
	***************************************************************/

	@Test
	public void testFTS23650() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		General objGeneral = new General();

		try {
			gstrTCID = "23650";
			gstrTO = "Create a saturation score status type selecting 'Exempt from Must Update' "
					+ "check box and verify that user is not prompted to update status.";// Test
																							// objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[1];
			String strSTypeValue[] = new String[1];
			String statTypeName1 = "NST" + strTimeText;
			String statTypeName2 = "SST" + strTimeText;
			String strStatTypDefn = "Automation";

			// RT data
			String strResrcTypName = "AutoRT_1" + strTimeText;

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strReSValue[] = new String[1];

			// Role
			String strRolesName = "AutoRol_1" + strTimeText;
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUserName1 = "Acm" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";

			/*
			 * STEP : Action:Precondition: 1. Status type ST is created without
			 * selecting 'Exempt from Must Update' check box. 2. U1 has update
			 * status right on resource RS, a role to update all the status
			 * types and the right 'User must update overdue status'. 3. U1 has
			 * update status right on resource RS and a role to update all the
			 * status types. 4. View V1 is created selecting all status types
			 * and RS. 5. Status type ST is updated with a value. Expected
			 * Result:No Expected Result
			 */
			// 125734
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
			try {
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
					strReSValue[0] = strResVal;
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strReSValue[0], false, true,
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
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user 2

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
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
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

			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = {};
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, true, strSTvalue,
						false, strReSValue);

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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdateValue = "1";
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdateValue, strStatusTypeValues[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			/*
			 * STEP : Action:Login as a user with the right 'Setup Status Types'
			 * and navigate to 'Setup >> Status Types'. Expected Result:No
			 * Expected Result
			 */
			// 125736
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
			 * STEP : Action:Click on 'Create New Status Type', select
			 * saturation score type and proceed. Expected Result:'Create
			 * Saturation Score Status Type' screen is displayed.
			 */
			// 125754
			/*
			 * STEP : Action:Provide mandatory data, select the checkbox 'Exempt
			 * from Must Update', select to expire at time T1 and save. Expected
			 * Result:Status type SST is created.
			 */
			// 125755
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Saturation Score";
				String strExpHr = "00";
				String strExpMn = "05";
				strFuncResult = objStatusTypes
						.selectSTypesAndFilMandFldsWithExTime(selenium,
								strStatusTypeValue, statTypeName2,
								strStatTypDefn, strExpHr, strExpMn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, true,
								false);
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
				strSTypeValue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTypeName2);
				if (strSTypeValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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
			/*
			 * STEP : Action: Associate SST with resource type RT and save.
			 * Expected Result:No Expected Result
			 */
			// 125757

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
			// RT
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
						selenium, strSTypeValue[0], true);
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
				String[] strSTvalue = {};
				String[][] strRSValue = {};
				strFuncResult = objViews.fillViewFields(selenium, strViewName,
						strVewDescription, strViewType, false, true,
						strSTvalue, false, strRSValue);
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
			 * STEP : Action:Login as user U1. Expected Result:Status update
			 * prompt is NOT displayed.
			 */
			// 125774
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium,
						strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action: Update SST. Expected Result:Updated value is
			 * displayed in view screen.
			 */
			// 127634
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
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
				String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTypeValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdateValue1 = "393";
				strFuncResult = objViews
						.verifyUpdatedValueOfRowWiseSTInViewScreen(selenium,
								strResrcTypName, statTypeName2, strUpdateValue1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:At time T1. Expected Result:User does NOT receive
			 * update status prompt for NST expiration.
			 */
			// 127685
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(500000);
				
				try {
					objGeneral.refreshPage(selenium);
				} catch (Exception e) {
					log4j.info("Refresh failed for Ist time " + e);

					try {
						objGeneral.refreshPage(selenium);
					} catch (Exception e1) {
						log4j.info("Refresh failed for 2nd time " + e1);

						try {
							objGeneral.refreshPage(selenium);
						} catch (Exception e2) {
							log4j.info("Refresh failed for 3rd time " + e2);
						}
					}
				}
				strFuncResult = objViews.varUpdateStatPrompt(selenium,
						strResource, statTypeName1);
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
			 * STEP : Action:Deactivate SST and then Reactivate it. Expected
			 * Result:No Expected Result
			 */
			// 127688
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

			try {
				assertEquals("", strFuncResult);
				String strSTtype = "Saturation Score";
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDesectIncludeInactiveST(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTtype = "Saturation Score";
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
						selenium, false);
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
			 * STEP : Action:Refresh view screen V1. Expected Result:Update
			 * status prompt is NOT displayed.
			 */
			// 127935

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
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
				try {
					objGeneral.refreshPage(selenium);
				} catch (Exception e) {
					log4j.info("Refresh failed for Ist time " + e);

					try {
						objGeneral.refreshPage(selenium);
					} catch (Exception e1) {
						log4j.info("Refresh failed for 2nd time " + e1);

						try {
							objGeneral.refreshPage(selenium);
						} catch (Exception e2) {
							log4j.info("Refresh failed for 3rd time " + e2);
						}
					}
				}
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium,
						strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action: Update SST from view screen. Expected Result:No
			 * Expected Result
			 */
			// 127733

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
				String strScUpdValue2[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue2, strSTypeValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
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
			 * STEP : Action:Deactivate RT and then Reactivate it. Expected
			 * Result:No Expected Result
			 */
			// 127776

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
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						selenium, strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selectDesectIncludeInactiveRT(
						selenium, true);
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
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						selenium, strResrcTypName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selectDesectIncludeInactiveRT(
						selenium, false);
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
			 * STEP : Action:Refresh view screen V1. Expected Result:Update
			 * status prompt is displayed with ST expanded. SST is not displayed
			 * in the prompt.
			 */
			// 127777

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Update SST from view screen. Expected Result:No
			 * Expected Result
			 */
			// 127779

			try {
				assertEquals("'Update Status' prompt for " + statTypeName2
						+ " is NOT displayed or it is not expanded",
						strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				Thread.sleep(1000);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
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
				String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTypeValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
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
			 * STEP : Action:Deselect SST from RT and then Reselect it. Expected
			 * Result:No Expected Result
			 */
			// 127780
			// NST Deselect

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
						selenium, strSTypeValue[0], false);
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

			// NST select

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
						selenium, strSTypeValue[0], true);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Refresh view screen v1. Expected Result:Update
			 * status prompt is NOT displayed.
			 */
			// 127781

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
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
				try {
					objGeneral.refreshPage(selenium);
				} catch (Exception e) {
					log4j.info("Refresh failed for Ist time " + e);

					try {
						objGeneral.refreshPage(selenium);
					} catch (Exception e1) {
						log4j.info("Refresh failed for 2nd time " + e1);

						try {
							objGeneral.refreshPage(selenium);
						} catch (Exception e2) {
							log4j.info("Refresh failed for 3rd time " + e2);
						}
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium,
						strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "23650";
			gstrTO = " Create a saturation score status type selecting 'Exempt from Must Update' check box and verify that user is not prompted to update status.";
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
	'Description		:Create a text status type selecting 'Exempt from Must Update' check box and verify 
	                     that user is not prompted to update status.
	'Precondition	    :
	'Arguments		    :None
	'Returns		    :None
	'Date			    :7/6/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23651() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		General objGeneral = new General();
		try {

			gstrTCID = "23651"; // Test Case Id
			gstrTO = "Create a text status type selecting 'Exempt from Must Update' check box and verify that user is not prompted to update status.";// Test
																																						// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[1];
			String strSTypeValue[] = new String[1];
			String statTypeName1 = "NST" + strTimeText;
			String statTypeName2 = "TST" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strReSValue[] = new String[1];

			// Role
			String strRolesName = "AutoRol_" + strTimeText;
			String strRoleValue = "";

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUserName1 = "Acm" + System.currentTimeMillis();
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
			// View
			String strViewName = "AutoV_" + strTimeText;

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";

			/*
			 * STEP : Action:Precondition: 1. Status type ST is created without
			 * selecting 'Exempt from Must Update' check box. 2. Associate ST
			 * with resource type RT and a resource RS is created under RT. 3.
			 * U1 has update status right on resource RS, a role to update all
			 * the status types and the right 'User must update overdue status'.
			 * 4. View V1 is created selecting all status types and RS. 5.
			 * Status type ST is updated with a value. Expected Result:No
			 * Expected Result
			 */
			try {
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
					strReSValue[0] = strResVal;
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strReSValue[0], false, true,
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
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user 2

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
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
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

			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = {};
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, true, strSTvalue,
						false, strReSValue);

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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdateValue = "1";
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdateValue, strStatusTypeValues[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");

			/*
			 * STEP : Action:Login as a user with the right 'Setup Status Types'
			 * and navigate to 'Setup >> Status Types'. Expected Result:No
			 * Expected Result
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action: Click on 'Create New Status Type', select text
			 * type and proceed. Expected Result:'Create Text Status Type'
			 * screen is displayed.
			 */

			/*
			 * STEP : Action:Provide mandatory data, select the checkbox 'Exempt
			 * from Must Update', select to expire at time T1 and save. Expected
			 * Result:Status type TST is created.
			 */
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				String strExpHr = "00";
				String strExpMn = "05";
				strFuncResult = objStatusTypes
						.selectSTypesAndFilMandFldsWithExTime(selenium,
								strStatusTypeValue, statTypeName2,
								strStatTypDefn, strExpHr, strExpMn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, true,
								false);
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
				strSTypeValue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTypeName2);
				if (strSTypeValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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
			/*
			 * STEP : Action:Associate TST with resource type RT and save.
			 * Expected Result:No Expected Result
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
			// RT
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
						selenium, strSTypeValue[0], true);
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
				String[] strSTvalue = {};
				String[][] strRSValue = {};
				strFuncResult = objViews.fillViewFields(selenium, strViewName,
						strVewDescription, strViewType, false, true,
						strSTvalue, false, strRSValue);
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
			 * STEP : Action:Login as user U1. Expected Result:Status update
			 * prompt is NOT displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium,
						strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Update TST. Expected Result:Updated value is
			 * displayed in view screen.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
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
				String strUpdateValue = "0";
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdateValue, strSTypeValue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdateValue1 = "0";
				strFuncResult = objViews
						.verifyUpdatedValueOfRowWiseSTInViewScreen(selenium,
								strResrcTypName, statTypeName2, strUpdateValue1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:At time T1. Expected Result:User does NOT receive
			 * update status prompt for TST expiration.
			 */
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(500000);
				
				try {
					objGeneral.refreshPage(selenium);
				} catch (Exception e) {
					log4j.info("Refresh failed for Ist time " + e);

					try {
						objGeneral.refreshPage(selenium);
					} catch (Exception e1) {
						log4j.info("Refresh failed for 2nd time " + e1);

						try {
							objGeneral.refreshPage(selenium);
						} catch (Exception e2) {
							log4j.info("Refresh failed for 3rd time " + e2);
						}
					}
				}
				
				strFuncResult = objViews.varUpdateStatPrompt(selenium,
						strResource, statTypeName2);
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
			 * STEP : Action:Deactivate TST and then Reactivate it. Expected
			 * Result:No Expected Result
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTtype = "Text";
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDesectIncludeInactiveST(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTtype = "Text";
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
						selenium, false);
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
			 * STEP : Action:Refresh view screen V1. Expected Result:Update
			 * status prompt is NOT displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
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
				try {
					objGeneral.refreshPage(selenium);
				} catch (Exception e) {
					log4j.info("Refresh failed for Ist time " + e);

					try {
						objGeneral.refreshPage(selenium);
					} catch (Exception e1) {
						log4j.info("Refresh failed for 2nd time " + e1);

						try {
							objGeneral.refreshPage(selenium);
						} catch (Exception e2) {
							log4j.info("Refresh failed for 3rd time " + e2);
						}
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium,
						strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Update TST from view screen. Expected Result:No
			 * Expected Result
			 */

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
				String strUpdateValue = "1";
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdateValue, strSTypeValue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
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
			 * STEP : Action:Deactivate RT and then Reactivate it. Expected
			 * Result:No Expected Result
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
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
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						selenium, strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selectDesectIncludeInactiveRT(
						selenium, true);
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
				strFuncResult = objResourceTypes.activateAndDeactivateRT(
						selenium, strResrcTypName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selectDesectIncludeInactiveRT(
						selenium, false);
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
			 * STEP : Action:Refresh view screen V1. Expected Result:Update
			 * status prompt is displayed with ST expanded. TST is not displayed
			 * in the prompt.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Update TST from view screen. Expected Result:No
			 * Expected Result
			 */
			try {
				assertEquals("'Update Status' prompt for " + statTypeName2
						+ " is NOT displayed or it is not expanded",
						strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				Thread.sleep(1000);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
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
				String strUpdateValue = "0";
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdateValue, strSTypeValue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
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
			 * STEP : Action:Deselect TST from RT and then Reselect it. Expected
			 * Result:No Expected Result
			 */
			// TST Deselect

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
						selenium, strSTypeValue[0], false);
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
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strSTypeValue[0], true);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Refresh view screen v1. Expected Result:Update
			 * status prompt is NOT displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
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
				try {
					objGeneral.refreshPage(selenium);
				} catch (Exception e) {
					log4j.info("Refresh failed for Ist time " + e);

					try {
						objGeneral.refreshPage(selenium);
					} catch (Exception e1) {
						log4j.info("Refresh failed for 2nd time " + e1);

						try {
							objGeneral.refreshPage(selenium);
						} catch (Exception e2) {
							log4j.info("Refresh failed for 3rd time " + e2);
						}
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium,
						strResource, statTypeName2);
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
			gstrTCID = "23651"; // Test Case Id
			gstrTO = "Create a text status type selecting 'Exempt from Must Update' check box and verify that "
					+ "user is not prompted to update status.";// Test Objective
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
	'Description		: Create a multi status type selecting 'Exempt from Must Update' check box and verify
	                      that user is not prompted to update status.
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :7/6/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				                         Modified By
	'Date					                              Name
	***************************************************************/

	@Test
	public void testFTS23649() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		General objGeneral=new General();
		
	try{		
		gstrTCID = "23649";
		gstrTO = "Create a multi status type selecting 'Exempt from Must Update' check box and verify"+
	              " that user is not prompted to update status.";//Test objective
		gstrReason = "";
		gstrResult = "FAIL";	
		
		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");
		String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
		String strTimeTxt = dts.getCurrentDate("MMddyyyy");
		String strFILE_PATH = pathProps.getProperty("TestData_path");
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strLoginUserName = rdExcel.readData("Login", 3, 1);
		String strLoginPassword = rdExcel.readData("Login", 3, 2);
		String strRegn =rdExcel.readData("Login", 3,4);
		// ST
		String strStatusTypeValues[] = new String[1];
		String strSTypeValue[] = new String[1];
		String statTypeName1 = "NST" + strTimeText;
		String statTypeName2 = "MST" + strTimeText;
		String strStatTypDefn = "Automation";
        String strMSTValue="Multi";
        
		String strStatTypeColor = "Black";
		String str_MultiStatusName1 = "Msa" + strTimeTxt;
		String str_MultiStatusName2 = "Msb" + strTimeTxt;
		String str_MultiStatusValue[] = new String[2];
		str_MultiStatusValue[0] = "";
		str_MultiStatusValue[1] = "";
		
		
		// RT data
		String strResrcTypName = "AutoRT_1" + strTimeText;

		// Resource
		String strResource = "AutoRS_" +strTimeText;
		String strHavBed = "No";
		String strAbbrv = "abb";
		String strResVal = "";
		String strReSValue[] = new String[1];

		// Role
		String strRolesName = "AutoRol_1" +strTimeText;
		String strRoleValue = "";

		// USER
		String strUserName = "AutoUsr" + System.currentTimeMillis();
		String strUserName1 = "Acm" + System.currentTimeMillis();
		String strInitPwd = rdExcel.readData("Login", 4, 2);
		String strConfirmPwd = rdExcel.readData("Login", 4, 2);
		String strUsrFulName = "autouser";
		
		String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
				strFILE_PATH);
		String strByResourceType = rdExcel.readInfoExcel("User_Template",
				7, 12, strFILE_PATH);
		String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
				13, strFILE_PATH);
		String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
				14, strFILE_PATH);
		// View
		String strViewName = "AutoV_" +strTimeText;
		String strVewDescription = "";
		String strViewType = "Resource (Resources and status "
				+ "types as rows. Status, comments and timestamps as columns.)";
			
		/*
		* STEP :
		  Action:Precondition:
		    1.Status type ST is created without selecting 'Exempt from Must Update' check box.
			2.Associate ST with resource type RT and a resource RS is created under RT.
			3.U1 has update status right on resource RS, a role to update all the status types 
			  and the right 'User must update overdue status'.
			4.View V1 is created selecting all status types and RS.
			5.Status type ST is updated with a value.
			Expected Result:No Expected Result
		*/
		//125734
		log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			try {
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
					strReSValue[0] = strResVal;
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strReSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.advancedOptns(
								selenium,propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium, strUserName, strByRole, 
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user 2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium, strUserName1, strByRole, 
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = {};
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, true, strSTvalue,
						false, strReSValue);

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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdateValue="1";
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdateValue, strStatusTypeValues[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
		/*
		* STEP :
		  Action:Login as a user with the right 'Setup Status Types' and navigate to 'Setup >> Status Types'.
		  Expected Result:No Expected Result
		*/
		//125736
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
		  Action:Click on 'Create New Status Type', select multi type and proceed. 
		  Expected Result:'Create Multi Status Type' screen is displayed. 
		*/
		
		/*
		* STEP :
		  Action:Provide mandatory data, select the checkbox 'Exempt from Must Update', select to expire at 
		  time T1 and save. 
		  Expected Result:Status type MST is created. 
		*/
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectStatusTypesAndFilMandFlds(selenium,
								strMSTValue, statTypeName2,
								strStatTypDefn,  false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectAndDeselectExcemptFromUpdate(selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTypeValue[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName2);
				if (strSTypeValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/* 	Add statuses to MST. */
			
			try {
				assertEquals("", strFuncResult);
				String strExpHr = "00";
				String strExpMn = "05";
				strFuncResult = objStatusTypes.createSTWithinMultiTypeSTExpTime(selenium,
						statTypeName2, str_MultiStatusName1, strMSTValue, strStatTypeColor,
						strExpHr, strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strExpHr = "00";
				String strExpMn = "05";
				strFuncResult = objStatusTypes.createSTWithinMultiTypeSTExpTime(selenium,
						statTypeName2, str_MultiStatusName2, strMSTValue, strStatTypeColor,
						strExpHr, strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_MultiStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(selenium, statTypeName2,
								str_MultiStatusName1);
				if (str_MultiStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_MultiStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(selenium, statTypeName2,
								str_MultiStatusName2);
				if (str_MultiStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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
		/*
		* STEP :
		  Action: 	Associate MST with resource type RT and save. 
		  Expected Result:No Expected Result
		*/
		//125757
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin =true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(selenium, strSTypeValue[0], true);
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
				String[] strSTvalue = {};
				String[][] strRSValue={};
				strFuncResult = objViews.fillViewFields(selenium, strViewName, strVewDescription, strViewType, false, true, strSTvalue, false, strRSValue);
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
		  Action:Login as user U1.
		  Expected Result:Status update prompt is NOT displayed.
		*/
		//125774
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Update MST.
		  Expected Result:Updated value is displayed in view screen.
		*/

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strNavElement="css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("",strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium, str_MultiStatusValue[0],
						strSTypeValue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdateValue1=str_MultiStatusName1;
				strFuncResult = objViews.verifyUpdatedValueOfRowWiseSTInViewScreen(selenium,
						strResrcTypName, statTypeName2, strUpdateValue1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:At time T1.
		  Expected Result:User does NOT receive update status prompt for MST expiration.
		*/
		//127685		
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(500000);
				strFuncResult = objViews.varUpdateStatPrompt(selenium,
						strResource, statTypeName2);
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
		  Action:Deactivate MST and then Reactivate it. 
		  Expected Result:No Expected Result
		*/
		//127688
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium, statTypeName2, strMSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.activateAndDeactivateST(selenium, statTypeName2, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDesectIncludeInactiveST(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium, statTypeName2, strMSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.activateAndDeactivateST(selenium, statTypeName2, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDesectIncludeInactiveST(selenium, false);
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
		  Action:Refresh view screen V1.
		  Expected Result:Update status prompt is NOT displayed.
		*/
		//127935
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
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
				strFuncResult =objGeneral.refreshPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action: 	Update MST from view screen. 
		  Expected Result:No Expected Result
		*/
		//127733

			try {
				assertEquals("", strFuncResult);
				String strNavElement="css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium, str_MultiStatusValue[1],
						strSTypeValue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
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
		  Action:Deactivate RT and then Reactivate it.
		  Expected Result:No Expected Result
		*/
		//127776
			
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
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(selenium, strResrcTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selectDesectIncludeInactiveRT(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRT(selenium, strResrcTypName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selectDesectIncludeInactiveRT(selenium, false);
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
		  Action:Refresh view screen V1.
		  Expected Result:Update status prompt is displayed with ST expanded.
  	  		              MST is not displayed in the prompt. 
		*/
		//127777
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Update MST from view screen. 
		  Expected Result:No Expected Result
		*/
		//127779
			
			try {
				assertEquals("'Update Status' prompt for "+statTypeName2+" is NOT displayed or it is not expanded", 
						strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				Thread.sleep(1000);
				strFuncResult = objViews.navToUserView(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strNavElement="css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium, str_MultiStatusValue[0],
						strSTypeValue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
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
		  Action:Deselect MST from RT and then Reselect it. 
		  Expected Result:No Expected Result
		*/
		//127780
			// NST Deselect
			
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
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(selenium, strSTypeValue[0], false);
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
			
			// NST select
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(selenium, strSTypeValue[0], true);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Refresh view screen v1.
		  Expected Result:Update status prompt is NOT displayed.
		*/
		//127781
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
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
				strFuncResult =objGeneral.refreshPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.varUpdateStatPrompt(selenium, strResource, statTypeName2);
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
		   gstrTCID = "23649";
			gstrTO = "Create a multi status type selecting 'Exempt from Must Update' check box and verify"+
		              " that user is not prompted to update status.";//Test objective
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

}
