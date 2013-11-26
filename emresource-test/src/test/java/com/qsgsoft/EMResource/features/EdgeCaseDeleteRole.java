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
' Requirement		:Delete Role
' Requirement Group	:Create & manage roles 
� Product		    :EMResource v3.24
' Date			    :06/Nov/2013
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/
public class EdgeCaseDeleteRole {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EdgeCaseDeleteRole");
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
	Selenium selenium, seleniumPrecondition;
	String gstrTimeOut;

	/***********************************************************************************
	 * This function is called the setup() function which is executed before
	 * every test.
	 * 
	 * The function will take care of creating a new selenium session for every
	 * test
	 * 
	 ************************************************************************************/

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

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

    /************************************************************************************
    * This function is called the teardown() function which is executed after every test.
	* The function will take care of stopping the selenium session for every test and 
	* writing the execution result of the test. 
    *************************************************************************************/

	@After
	public void tearDown() throws Exception {

		try {
			selenium.close();
		} catch (Exception e) {
		}
		selenium.stop();

		try {
			seleniumPrecondition.close();
		} catch (Exception e) {
		}
		seleniumPrecondition.stop();

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
	
	//start//testFTS127931//
	/*****************************************************************************************
	'Description	:Verify that user cannot be uploaded by providing role id that is deleted
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:11/6/2013
	'Author			:QSG
	'-----------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	******************************************************************************************/
	
	@Test
	public void testFTS127931() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Upload objUpload = new Upload();
	
		try {
			gstrTCID = "127931"; // Test Case Id
			gstrTO = " Verify that user cannot be uploaded by providing role id that is deleted";//TO
			gstrReason = "";
			gstrResult = "FAIL";
	
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
	
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
	
			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName = "AutoST" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];
	
			// USER
			String strUserID = "1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "UserFul" + System.currentTimeMillis();
		/*
		* STEP :
		  Action:Create a role in a region and note down the role id.
	             Delete the role and attempt to upload a role providing a id of the deleted role.
	             test for test upload and a real upload 
		  Expected Result:No Expected Result
		*/
		//664290
	
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			// Creating Role
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
	
			// Creating Status Type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeName);
	
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			// Creating Resource Type
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
						seleniumPrecondition, strResrcTypName,
						strStatusTypeValues[0]);
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
	
			// Logout
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			log4j.info("~~~~~Precondition" + gstrTCID + " ENDS~~~~~");
	
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");
	
			// Login
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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.deleteRole(selenium, strRolesName);			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Downloading Upload Template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Path");
	
				String strDownloadPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp"
						+ gstrTCID + strTimeText + ".xls";
				String strAutoFileName = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Name");
	
				strFuncResult = objUpload.downloadUploadTemplate(selenium,
						strAutoFilePath, strAutoFileName, strDownloadPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			// Writing data in Template
			try {
				assertEquals("", strFuncResult);
	
				String[] strTestData = { "", "N", "", "", "7868",
						"utuyt", "106", "Hospital", "N", "N", "", "",
						"", "", "", "", "FN", "LN", "", "", "", "", "", "",
						strUserID, strRoleValue, strRolesName, strInitPwd, strUsrFulName,
						"", "", "", "", "", "", "", "", "", "", "" };
				String strWriteFilePath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp"
						+ gstrTCID + strTimeText + ".xls";
	
				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 1);
	
			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
	
			// Uploading template by selecting test(Actual upload )
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
	
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				String strUploadFilePath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp"
						+ gstrTCID + strTimeText + ".xls";
	
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUploadFilePath, strAutoFileName,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			// Verifying upload details
			try {
				assertEquals("", strFuncResult);
				String[] strColHeaders = { "Row", "Resource ID",
						"Resource Name", "Failed", "Geocode",
						"Resource Comments", "User ID", "Full Name", "Failed",
						"User Comments" };
				String[] pStrArrUplData = { "1", "", "", "No", "",
						"no resource data provided", strUserID, strUsrFulName,
						"Yes", "invalid RoleID" };
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
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
			gstrTCID = "FTS-127931";
			gstrTO = "Verify that user cannot be uploaded by providing role id that is deleted";
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
	 // end//testFTS127931//
 
	//start//testFTS127930//
	/***********************************************************************************
	'Description	:Verify that role provided for the user can be edited by deselecting  
	                 the default role and selecting a different role for a user
	'Arguments		:None
	'Returns		:None
	'Date			:11/6/2013
	'Author			:QSG
	'------------------------------------------------------------------------------------
	'Modified Date				                                            Modified By
	'Date					                                                Name
	*************************************************************************************/
	
	@Test
	public void testFTS127930() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		General objGen =new General();
		EventSetup objEventSetup=new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();	
		try {
			gstrTCID = "127930"; // Test Case Id
			gstrTO = "Verify that role provided for the user can be edited by deselecting the default " +
					"role and selecting a different role for a user";//TO
			gstrReason = "";
			gstrResult = "FAIL";
	
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
	
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
	
			// Role
			String strRolesName1 = "AutoRol_1" + strTimeText;
			String strRolesName2 = "AutoRol_2" + strTimeText;
			String[] strRoleValue = new String[2];

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			String strOptions = "";

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
		  Action:Set a default role, while creating/editing a user, the default role can be 
		  deselected and a new role can be provided for a user
          Navigate to Setup > Roles and check the default role is unchanged
          Login as user and check that user has all the rights selected in a role provided for a user by editing 
		  Expected Result:No Expected Result
		*/
		//664290
			log4j.info("~~~~~TESTCASE " + gstrTCID + "EXECUTION STATRTS~~~~~");

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
			
			// Role 2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName1);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Role 2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[1] = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName2);
				if (strRoleValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.setRoleAsDefaultRole(
						seleniumPrecondition, strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// USER
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
				strFuncResult = objCreateUsers.chkRoleSelectedOrNot(seleniumPrecondition,
						true, strRoleValue[0], strRolesName1);
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
	
			log4j.info("~~~~~PRECONDITION" + gstrTCID + " ENDS~~~~~");
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");
	
			// Login
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
				strFuncResult = objCreateUsers
						.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue[0], false);
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
						selenium, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			//Check default role changed or not
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table/tbody"
						+ "/tr/td[3][text()='" + strRolesName1
						+ "']/parent::tr/td[2][text()='Yes']";
				strFuncResult = objGen.CheckForElements(selenium, strElementID);
				log4j.info("The default role " + strRolesName1
						+ " is unchanged ");
			} catch (AssertionError ae) {
				log4j.info("The default role " + strRolesName1 + " is changed ");
				strFuncResult = "The default role " + strRolesName1
						+ " is changed ";
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("Event.CreateEvent");
				strFuncResult = objGen
						.CheckForElements(selenium, strElementID);
				log4j.info("'Create New Event' button is displayed at top left of the screen."
						+ strUserName_1 + "'. ");
			} catch (AssertionError ae) {
				log4j.info("'Create New Event' button is Not displayed at top left of the screen."
						+ strUserName_1 + "'.");
				strFuncResult = "'Create New Event' button is Not displayed at top left of the screen."
						+ strUserName_1 + "'.";
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
			gstrTCID = "127930"; // Test Case Id
			gstrTO = "Verify that role provided for the user can be edited by deselecting the default " +
					"role and selecting a different role for a user";//TO
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
	 //end//testFTS127930//
	
//start//testFTS127928//
	/****************************************************************************************
	'Description	:Verify that only one role can be set as 'Default' on 'Roles List' screen
	'Arguments		:None
	'Returns		:None
	'Date			:11/6/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                              Modified By
	'Date					                                                  Name
	*****************************************************************************************/
	
	@Test
	public void testFTS127928() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		Roles objRoles = new Roles();
		try {
			gstrTCID = "127928"; // Test Case Id
			gstrTO = "Verify that only one role can be set as 'Default' on 'Roles List'screen";//TO
			gstrReason = "";
			gstrResult = "FAIL";
	
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
	
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
	
			// Role
			String strRolesName1 = "AutoRol_1" + strTimeText;
			String strRolesName2 = "AutoRol_2" + strTimeText;
			String strRolesName3 = "AutoRol_3" + strTimeText;
			String[] strRoleValue = new String[3];
			
		/*
		* STEP :
		  Action:Set a default role, while creating/editing a user, the default role can be 
		  deselected and a new role can be provided for a user
          Navigate to Setup > Roles and check the default role is unchanged
          Login as user and check that user has all the rights selected in a role provided for a user by editing 
		  Expected Result:No Expected Result
		*/
		//664290
			log4j.info("~~~~~TESTCASE " + gstrTCID + "EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Role 2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						selenium, strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						selenium, strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(
						selenium, strRolesName1);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.chkDefaultLinkAvailOrNotForRole(
						selenium, strRolesName1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.chkYesOrNoForRoleInDefaultCol(
						selenium, strRolesName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Role 2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						selenium, strRolesName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						selenium, strRolesName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[1] = objRoles.fetchRoleValueInRoleList(
						selenium, strRolesName2);
				if (strRoleValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.chkDefaultLinkAvailOrNotForRole(
						selenium, strRolesName2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.chkYesOrNoForRoleInDefaultCol(
						selenium, strRolesName2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Role 3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						selenium, strRolesName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						selenium, strRolesName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[2] = objRoles.fetchRoleValueInRoleList(
						selenium, strRolesName3);
				if (strRoleValue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.chkDefaultLinkAvailOrNotForRole(
						selenium, strRolesName3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.chkYesOrNoForRoleInDefaultCol(
						selenium, strRolesName3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.setRoleAsDefaultRole(
						selenium, strRolesName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.chkDefaultLinkAvailOrNotForRole(
						selenium, strRolesName2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.chkYesOrNoForRoleInDefaultCol(
						selenium, strRolesName2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.chkDefaultLinkAvailOrNotForRole(
						selenium, strRolesName1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.chkYesOrNoForRoleInDefaultCol(
						selenium, strRolesName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.chkDefaultLinkAvailOrNotForRole(
						selenium, strRolesName3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.chkYesOrNoForRoleInDefaultCol(
						selenium, strRolesName3, false);
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
			gstrTCID = "127928"; // Test Case Id
			gstrTO = "Verify that only one role can be set as 'Default' on 'Roles List' screen";//TO
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
	 //end//testFTS127928//
}
