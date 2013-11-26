package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;


import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.General;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.ResourceTypes;
import com.qsgsoft.EMResource.shared.Resources;
import com.qsgsoft.EMResource.shared.Roles;
import com.qsgsoft.EMResource.shared.StatusTypes;
import com.qsgsoft.EMResource.shared.Upload;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/********************************************************************
' Description		:This class contains test cases from requirement
' Requirement Group :Setting up resources
' Requirement       :Import Resources from Spreadsheet
' Product		    :EMResource v3.20
' Date			    :12-11-2012
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date				                         Modified By
' Date					                             Name
'*******************************************************************/
public class FTSImportResourcesFromSpreadsheet {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features." +
					"FTSImportResourcesFromSpreadsheet");
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
	 * This function is called the setup() function which is executed before every test.
	 * 
	 * The function will take care of creating a new selenium session for every test
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

		selenium.start();
		selenium.windowMaximize();
		selenium.setTimeout("");

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		seleniumPrecondition.setTimeout("");

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
		try{
			seleniumPrecondition.close();
		}catch(Exception e){

		}
		seleniumPrecondition.stop();
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
	
  //start//testFTS124560//
	/***********************************************************************
	'Description	:Verify that import can be cancelled from the test mode.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/11/2013
	'Author			:QSG
	'------------------------------------------------------------------------
	'Modified Date				                                Modified By
	'Date					                                     Name
	**************************************************************************/

	@Test
	public void testFTS124560() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		General objGeneral = new General();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objResources = new Resources();
		Upload objUpload = new Upload();

		try {
			gstrTCID = "124560"; // Test Case Id
			gstrTO = "Verify that import can be cancelled from the test mode.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative
																			// URL
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
			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Crawford County";
			String strResVal = "";
			String strRSValue[] = new String[1];
			// USER
			String strUserID = "1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "UserFul" + System.currentTimeMillis();
			String strGenDateFormatNew = "";
			String strRegGenTime = "";
		
		/*
		* STEP :
		  Action:Cancel import on 'New Upload' screen 
		  Expected Result:Resource upload is cancelled
		  Upload entry for 'Test' / 'Actual' is not created 
		*/
		//660729

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, "FN", "LN", strState, strCountry,
						"Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~Precondition" + gstrTCID + " ENDS~~~~~");
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");

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

			try {
				assertEquals("", strFuncResult);

				String[] strTestData = { "", "", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", strUserID, strRoleValue, "", strInitPwd,
						strUsrFulName, "", "", "", "", "", "", "", "", "", "",
						"" };

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
						false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArFunRes[] = objGeneral.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				String strCurYear = dts.getFutureYear(0, "yyyy");
				strGenDateFormatNew = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"yyyy-MM-dd");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
				strGenDateFormatNew = strGenDateFormatNew + " " + strRegGenTime;

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] pStrArrUplData = { "View Details", "Yes",
						strGenDateFormatNew, strLoginUserName };
				strFuncResult = objUpload.cancelAndNavToUploadListPage(
						selenium, pStrArrUplData);
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
			gstrTCID = "124560"; // Test Case Id
			gstrTO = "Verify that import can be cancelled from the test mode.";// TO
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

	//end//testFTS124560//
	
	//start//testFTS124561//
	/********************************************************************************************
	'Description	:Verify that error message is displayed if the column headers in the attached
	                 template does not match with the standard template column names.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/11/2013
	'Author			:QSG
	'--------------------------------------------------------------------------------------------
	'Modified Date				                                               Modified By
	'Date					                                                   Name
	*********************************************************************************************/

	@Test
	public void testFTS124561() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objResources = new Resources();
		Upload objUpload = new Upload();

		try {
			gstrTCID = "124561"; // Test Case Id
			gstrTO = " Verify that error message is displayed if the column headers in the "
					+ "attached template does not match with the standard template column names.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
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
			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Crawford County";
			String strResVal = "";
			String strRSValue[] = new String[1];
			// USER
			String strUserID = "1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "UserFul" + System.currentTimeMillis();

			/*
			 * STEP : Action:1. Alter the column header in standard template 2.
			 * Upload resource from template Expected Result:Appropriate error
			 * message is displayed
			 */
			// 660729

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, "FN", "LN", strState, strCountry,
						"Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~Precondition" + gstrTCID + " ENDS~~~~~");
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");

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

			try {
				assertEquals("", strFuncResult);

				String[] strTestData = {"ResourceID", "SubResource_Edit",
						"ResourceName_Edit", "Abbrev_Edit", "ResourceTypeID_Edit",
						"ResourceType_Edit", "StandardResourceTypeID_Edit",
						"StandardResourceType", "HAvBED", "Shared", "AhaID","ExternalID",
						"StreetAddress", "City", "State", "ZipCode", "County",
						"ContactFirstName", "ContactLastName",
						"ContactAddress", "ContactPhone1", "ContactPhone2",
						"ContactFax", "ContactEMail", "UserID", "RoleID",
						"RoleName", "Password", "FullName", "FirstName",
						"MiddleName", "LastName", "Organization", "WorkPhone",
						"PrimaryEMail", "AlternateEMails", "TextPager",
						"Associated", "Update", "Reports" };

				String strWriteFilePath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp"
						+ gstrTCID + strTimeText + ".xls";

				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 0);

			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strTestData = { strRSValue[0], "N", "", strAbbrv,
						strRsTypeValues[0], strResrcTypName, "106",
						"Hospital", "N", "N", "", "", "", "", "PR", "", "",
						"", "", "", "", "", "", "", strUserID, strRoleValue,
						strRolesName, strInitPwd, strUsrFulName, "", "", "",
						"", "", "", "", "", "", "", "" };

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
						false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.SavAndVerifyErrorMsgfillNewUpload(
						selenium, true);
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
			gstrTCID = "FTS-124561";
			gstrTO = "Verify that error message is displayed if the column headers in the attached template does not match with the standard template column names.";
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

	// end//testFTS124561//
	
 //start//testFTS124562//
	/*******************************************************************************************************
	'Description	:Verify that appropriate validation messages are displayed while importing the resources.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/11/2013
	'Author			:QSG
	'------------------------------------------------------------------------------------------------------
	'Modified Date				                                               Modified By
	'Date					                                                   Name
	********************************************************************************************************/

	@Test
	public void testFTS124562() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objResources = new Resources();
		Upload objUpload = new Upload();
		
		try {
			gstrTCID = "124562"; // Test Case Id
			gstrTO = "Verify that appropriate validation messages are displayed while importing the resources.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";
			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strNDSTValue = "NEDOCS Calculation";

			// role based
			String statrNumTypeName = "AutoNST" + strTimeText;
			String statrTextTypeName = "AutoTST" + strTimeText;
			String statrMultiTypeName = "AutoMST" + strTimeText;
			String statrSaturatTypeName = "AutoSST" + strTimeText;
			String statrNedocTypeName = "AutoNEDOC" + strTimeText;
			String str_roleStatusTypeValues[] = new String[5];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String str_roleStatusName2 = "rSb" + strTimeTxt;
			String str_roleStatusValue[] = new String[2];

			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			// USER
			String strUserID = "1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "UserFul" + System.currentTimeMillis();
		
		/*
		* STEP :
		  Action:1. Resource type RT is created selecting all 5 status types.(Note down the resource type ID)
				2. Download the standard Upload Template from 'Upload List' screen
				3. Fill all the mandatory data for resource upload, donot provide resource name.
				4. Upload resources 
		  Expected Result:Appropriate validation message is displayed and resource is not uploaded 
		*/
		//660729
			
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

			// Status Types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role based status type
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNumTypeName);
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
						seleniumPrecondition, strTSTValue, statrTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrTextTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
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
						seleniumPrecondition, strSSTValue,
						statrSaturatTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrSaturatTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName);
				if (str_roleStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statrMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, statrMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (str_roleStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName1, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName2, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
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
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNedocTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNedocTypeName);
				if (str_roleStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
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
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName,
						str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 1; intST < str_roleStatusTypeValues.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition,
							str_roleStatusTypeValues[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.createResourceWitLookUPadresSharWitRgn(
								seleniumPrecondition, strResource, strAbbrv,
								strResrcTypName, true, strContFName,
								strContLName, strState, strCountry,
								strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources.fetchResValueInResList(
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			 log4j.info("~~~~~Precondition" + gstrTCID + " ENDS~~~~~");
			 log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");
			  
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

			try {
				assertEquals("", strFuncResult);

				String[] strTestData = { "", "N", "", strAbbrv,
						strRsTypeValues[0], strResrcTypName, "101",
						strStandResType, "N", "N", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", strUserID, strRoleValue,
						strRolesName, strInitPwd, strUsrFulName, "", "", "",
						"", "", "", "", "", "", "", "" };

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

			try {
				assertEquals("", strFuncResult);
				String[] strColHeaders = { "Row", "Resource ID",
						"Resource Name", "Failed", "Geocode",
						"Resource Comments", "User ID", "Full Name", "Failed",
						"User Comments" };
				String[] pStrArrUplData = { "1", "", "", "No", "",
						"no resource data provided", strUserID, strUsrFulName,
						"No", "" };
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
			gstrTCID = "124562"; // Test Case Id
			gstrTO = "Verify that appropriate validation messages are displayed while importing the resources.";// TO
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

	//end//testFTS124562//	
	

	//start//testFTS124583//
	/***************************************************************
	'Description		:Verify that validation error is displayed when an attempt is made to import users without providing mandatory data.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:9/23/2013
	'Author				:Suhas
	'---------------------------------------------------------------
	'Modified Date				            Modified By
	'Date					                Name
	***************************************************************/

	@Test
	public void testFTS124583() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Upload objUpload = new Upload();
		try {
			gstrTCID = "124583"; // Test Case Id
			gstrTO = " Verify that validation error is displayed when an attempt is made to import"
					+ " users without providing mandatory data.";// Test//
																	// Objective
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
			 * STEP : Action:1. Download standard upload template 2. Do not
			 * provide one mandatory data in standard template attempt to upload
			 * Expected Result:Appropriate validation message is displayed
			 * (Check for all validations)
			 */
			// 660743

			// Login
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

				String[] strTestData = { "", "N", "", "", strRsTypeValues[0],
						strResrcTypName, "106", "Hospital", "N", "N", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "",
						strUserID, "", strRolesName, strInitPwd, strUsrFulName,
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
						"Yes", "RoleID is not a number" };
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
			gstrTCID = "FTS-124583";
			gstrTO = "Verify that validation error is displayed when an attempt is made to import users without providing mandatory data.";
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

	// end//testFTS124583//
	
	// start//testFTS124599//
		/***************************************************************
		 * 'Description 	:Verify that user with 'Setup resources' right do not have
		 *              	 the option to import the resources from add/edit resource page.
		 * 'Precondition 	:
		 * 'Arguments 		:None
		 * 'Returns 		:None 
		 * 'Date 			:10/3/2013 
		 * 'Author			:QSG 
		 * ---------------------------------------------------------------
		 * 'Modified Date Modified By 'Date Name
		 ***************************************************************/

	@Test
	public void testFTS124599() throws Exception {
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		General objGen = new General();
		boolean blnLogin = false;
		try {
			gstrTCID = "124599"; // Test Case Id
			gstrTO = " Verify that user with 'Setup resources' right do not have the option to import the resources from add/edit resource page.";// Test//
																																					// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;

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
			 * STEP : Action:1. Create user 'U1' providing 'Setup resources'
			 * right Expected Result:User cannot upload resources
			 */
			// 660777
			log4j.info("~~~~~PRECONDITION  - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			//Login
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
			//Creating user
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
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
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
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Logout
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
			//Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Verifying User cannot upload resources.
			try {
				assertEquals("", strFuncResult);
				String strElementID = "link=Upload";
				strFuncResult = objGen.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("Element NOT Present")) {
					log4j.info("User cannot upload resources ");
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
			gstrTCID = "FTS-124599";
			gstrTO = "Verify that user with 'Setup resources' right do not have the option to import the resources from add/edit resource page.";
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

	// end//testFTS124599//

	//start//testFTS124565//
	/***************************************************************
	'Description		:Verify that resources can be imported by providing all the data in the standard template.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/4/2013
	'Author				:Suhas
	'---------------------------------------------------------------
	'Modified Date				                   Modified By
	'Date					                       Name
	***************************************************************/

	@Test
	public void testFTS124565() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Upload objUpload = new Upload();
		General objGen = new General();
		Resources objRes=new Resources();
	try{	
		gstrTCID = "124565";	//Test Case Id	
		gstrTO = " Verify that resources can be imported by providing all the data in the standard template.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

		
		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");
		seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative
																		// URL
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

		String strLoginUserName = rdExcel.readData("Login", 3, 1);
		String strLoginPassword = rdExcel.readData("Login", 3, 2);
		String strRegn = rdExcel.readData("Login", 3, 4);
		// ST
		String strStatusTypeValues[] = new String[1];
		String statTypeName = "AutoST" + strTimeText;
		String strStatTypDefn = "Automation";
 	     // RT data
		String strResrcTypName = "AutoRT_" + strTimeText;
		String strRsTypeValues[] = new String[1];
		// RS
		String strResource = "AutoRs_" + strTimeText;
		String strAbbrv = "Rs";
		String strContFName = "auto";
		String strContLName = "qsg";	

		/*
		* STEP :
		  Action:1. Download 'Upload Template' from 'Upload List' screen
		2. Provide data in  mandatory fields of standard resource template 
		3. Upload template
		  Expected Result:Resource is uploaded successfully.
	
	 	Resource name is hyperlinked  on 'Upload Details' screen
		*/
		//660736
		log4j.info("~~~~~PRECONDITION - "+gstrTCID+" EXECUTION STARTS~~~~~");


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
		}
		catch (AssertionError Ae)
		{
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
		log4j.info("~~~~~PRECONDITION - " + gstrTCID
				+ " EXECUTION ENDS~~~~~");
		log4j.info("~~~~~TEST CASE - " + gstrTCID
				+ " EXECUTION STARTS~~~~~");
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

			String[] strTestData = { "", "N", strResource, strAbbrv,
					strRsTypeValues[0], strResrcTypName, "106", "Hospital",
					"N", "N", "", "", "", "", "", "", "", strContFName,
					strContLName, "", "", "", "", "", "", "", "", "", "",
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
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objGen.checkForAnElements(selenium,
					"//table[@summary='Upload Details']/"
							+ "tbody/tr/td[3][text()='" + strResource
							+ "']" + "/preceding-sibling::td[1]/a");

			if (strFuncResult.equals("")) {
				strFuncResult = "";
				log4j.info("'Resource ID' is  hyper linked. ");
			} else {
				log4j.info("'Resource ID' is NOT hyper linked. ");
				strFuncResult = "'Resource ID' is NOT hyper linked. ";

			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.navToEditResourcePageFromResourceID(selenium, strResource);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			String strStandResType="Hospital";
			strFuncResult = objRes.verifyResMandValuesWithCityAndStInEditRes(selenium,
					strResource, strAbbrv, "", strResrcTypName, strStandResType, "", strContFName, strContLName);
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
		gstrTCID = "FTS-124565";
		gstrTO = "Verify that resources can be imported by providing all the data in the standard template.";
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

	//end//testFTS124565//
	//start//testFTS124563//
		/***************************************************************
		'Description		:Verify that resources that are in the standard template format can be imported.
		'Precondition		:
		'Arguments			:None
		'Returns			:None
		'Date				:10/4/2013
		'Author				:Suhas
		'---------------------------------------------------------------
		'Modified Date				               Modified By
		'Date					                   Name
		***************************************************************/

		@Test
		public void testFTS124563() throws Exception {
			String strFuncResult = "";
			boolean blnLogin = false;
			Login objLogin = new Login();// object of class Login
			ResourceTypes objResourceTypes = new ResourceTypes();
			StatusTypes objStatusTypes = new StatusTypes();
			Resources objResources = new Resources();
			Upload objUpload = new Upload();
			try {
				gstrTCID = "124563"; // Test Case Id
				gstrTO = " Verify that resources that are in the standard template format can be imported.";// Test// Objective
				gstrReason = "";
				gstrResult = "FAIL";
				Date_Time_settings dts = new Date_Time_settings();
				gstrTimetake = dts.timeNow("HH:mm:ss");
				String strTimeTxt = dts.getCurrentDate("MMddyyyy");
				seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative// URL
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
				String strLoginUserName = rdExcel.readData("Login", 3, 1);
				String strLoginPassword = rdExcel.readData("Login", 3, 2);
				String strRegn = rdExcel.readData("Login", 3, 4);
				// Status types
				String strStatTypDefn = "Automation";
				String strNSTValue = "Number";
				String strMSTValue = "Multi";
				String strTSTValue = "Text";
				String strSSTValue = "Saturation Score";
				String strNDSTValue = "NEDOCS Calculation";
				// role based
				String statrNumTypeName = "AutoNST" + strTimeText;
				String statrTextTypeName = "AutoTST" + strTimeText;
				String statrMultiTypeName = "AutoMST" + strTimeText;
				String statrSaturatTypeName = "AutoSST" + strTimeText;
				String statrNedocTypeName = "AutoNEDOC" + strTimeText;
				String str_roleStatusTypeValues[] = new String[5];
				String strStatTypeColor = "Black";
				String str_roleStatusName1 = "rSa" + strTimeTxt;
				String str_roleStatusName2 = "rSb" + strTimeTxt;
				String str_roleStatusValue[] = new String[2];
				// RT
				String strResrcTypName = "AutoRt1_" + strTimeText;
				String[] strRsTypeValues = new String[1];
				// RS
				String strResource = "AutoRs_" + strTimeText;
				String strAbbrv = "Rs";
				String strContFName = "auto";
				String strContLName = "qsg";

				/*
				 * STEP : Action:1. Resource type RT is created selecting all 5
				 * status types.(Note down the resource type ID) 2. Download the
				 * standard Upload Template from 'Upload List' screen 3. Fill all
				 * the mandatory data for resource upload. 4. Upload resources
				 * Expected Result:When test is selected resource not uploaded
				 * actually When uploaded deselecting test resource are uploaded.
				 */
				// 660727
				log4j.info("~~~~~PRECONDITION - " + gstrTCID
						+ " EXECUTION STARTS~~~~~");
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);

				try {
					assertEquals("", strFuncResult);
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

				// Role based status type
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
					strFuncResult = objStatusTypes.savAndVerifySTNew(
							seleniumPrecondition, statrNumTypeName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					str_roleStatusTypeValues[0] = objStatusTypes
							.fetchSTValueInStatTypeList(seleniumPrecondition,
									statrNumTypeName);
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
							seleniumPrecondition, strTSTValue, statrTextTypeName,
							strStatTypDefn, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.savAndVerifySTNew(
							seleniumPrecondition, statrTextTypeName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					str_roleStatusTypeValues[1] = objStatusTypes
							.fetchSTValueInStatTypeList(seleniumPrecondition,
									statrTextTypeName);
					if (str_roleStatusTypeValues[1].compareTo("") != 0) {
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
							seleniumPrecondition, strSSTValue,
							statrSaturatTypeName, strStatTypDefn, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.savAndVerifySTNew(
							seleniumPrecondition, statrSaturatTypeName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					str_roleStatusTypeValues[2] = objStatusTypes
							.fetchSTValueInStatTypeList(seleniumPrecondition,
									statrSaturatTypeName);
					if (str_roleStatusTypeValues[2].compareTo("") != 0) {
						strFuncResult = "";

					} else {
						strFuncResult = "Failed to fetch status type  value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strMSTValue, statrMultiTypeName,
							strStatTypDefn, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.savAndVerifyMultiST(
							seleniumPrecondition, statrMultiTypeName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					str_roleStatusTypeValues[3] = objStatusTypes
							.fetchSTValueInStatTypeList(seleniumPrecondition,
									statrMultiTypeName);
					if (str_roleStatusTypeValues[3].compareTo("") != 0) {
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
							seleniumPrecondition, statrMultiTypeName,
							str_roleStatusName1, strMSTValue, strStatTypeColor,
							true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
							seleniumPrecondition, statrMultiTypeName,
							str_roleStatusName2, strMSTValue, strStatTypeColor,
							true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					str_roleStatusValue[0] = objStatusTypes
							.fetchStatValInStatusList(seleniumPrecondition,
									statrMultiTypeName, str_roleStatusName1);
					if (str_roleStatusValue[0].compareTo("") != 0) {
						strFuncResult = "";

					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					str_roleStatusValue[1] = objStatusTypes
							.fetchStatValInStatusList(seleniumPrecondition,
									statrMultiTypeName, str_roleStatusName2);
					if (str_roleStatusValue[1].compareTo("") != 0) {
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
							seleniumPrecondition, strNDSTValue, statrNedocTypeName,
							strStatTypDefn, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.savAndVerifySTNew(
							seleniumPrecondition, statrNedocTypeName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					str_roleStatusTypeValues[4] = objStatusTypes
							.fetchSTValueInStatTypeList(seleniumPrecondition,
									statrNedocTypeName);
					if (str_roleStatusTypeValues[4].compareTo("") != 0) {
						strFuncResult = "";

					} else {
						strFuncResult = "Failed to fetch status type  value";
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
					strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
							seleniumPrecondition, strResrcTypName,
							str_roleStatusTypeValues[0]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				for (int intST = 1; intST < str_roleStatusTypeValues.length; intST++) {
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
								seleniumPrecondition,
								str_roleStatusTypeValues[intST], true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
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

				log4j.info("~~~~~PRECONDITION - " + gstrTCID
						+ " EXECUTION ENDS~~~~~");
				log4j.info("~~~~~TEST CASE - " + gstrTCID
						+ " EXECUTION STARTS~~~~~");
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

					String[] strTestData = { "", "N", strResource, strAbbrv,
							strRsTypeValues[0], strResrcTypName, "106", "Hospital",
							"N", "N", "", "", "", "", "", "", "", strContFName,
							strContLName, "", "", "", "", "", "", "", "", "", "",
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
							true, true);
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
				gstrTCID = "FTS-124563";
				gstrTO = "Verify that resources that are in the standard template format can be imported.";
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

		// end//testFTS124563//
		

		//start//testFTS124564//
		/***************************************************************
		'Description		:Verify that resources cannot be imported if data for the mandatory fields in the
		                     application are not provided in the standard template.
		'Precondition		:
		'Arguments			:None
		'Returns			:None
		'Date				:10/8/2013
		'Author				:Suhas
		'---------------------------------------------------------------
		'Modified Date				             Modified By
		'Date					                 Name
		***************************************************************/

		@Test
		public void testFTS124564() throws Exception {
			String strFuncResult = "";
			boolean blnLogin = false;
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Upload objUpload = new Upload();
			Resources objResources = new Resources();
		try{	
			gstrTCID = "124564";	//Test Case Id	
			gstrTO = " Verify that resources cannot be imported if data for the mandatory fields" +
					" in the application are not provided in the standard template.";//Test Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative	// URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName = "AutoST" + strTimeText;
			String strStatTypDefn = "Automation";
	 	     // RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strContFName = "auto";				

			/*
			* STEP :
			  Action:1. Down load standard upload template
		<br>2. Do not provide data in one mandatory field
			  Expected Result:Resource is not uploaded when mandatory field is blank.
			*/
			//660731

			log4j.info("~~~~~PRECONDITION - "+gstrTCID+" EXECUTION STARTS~~~~~");


			try{
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			}
			catch (AssertionError Ae)
			{
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
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
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

			// Writing data to Template
			try {
				assertEquals("", strFuncResult);

				String[] strTestData = { "", "N", strResource, strAbbrv,
						strRsTypeValues[0], strResrcTypName, "106", "Hospital",
						"N", "N", "", "", "", "", "", "", "", strContFName,
						"", "", "", "", "", "", "", "", "", "", "",
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
			gstrTCID = "FTS-124564";
			gstrTO = "Verify that resources cannot be imported if data for the mandatory fields in the application are not provided in the standard template.";
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

		//end//testFTS124564//
		
	
	
}
