package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

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

/*****************************************************************
' Description         : This class includes test cases related to
' Requirement Group   : Smoke Test Suite
' Requirement         : Import Resources 
' Date		          : 5th-March-2013
' Author	          : QSG
'-----------------------------------------------------------------
' Modified Date                                      Modified By
' <Date>                           	                    <Name>
'*****************************************************************/

public class Smoke_ImportResources {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.Smoke_ImportResources");
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
	
	String gstrTimeOut="";
	
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
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();

		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
	
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		seleniumPrecondition.setTimeout("");
		
		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

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
		  gstrReason=gstrReason.replaceAll("'", " ");
		  objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
		    gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
		    gslsysDateTime, gstrBrowserName, gstrBuild,strSessionId);
	}
	
	/*********************************************************************************
	'Description	:Verify that RegAdmin can upload resource providing mandatory data.
	'Arguments		:None
	'Returns		:None
	'Date			:8th-March-2013
	'Author			:QSG
	'---------------------------------------------------------------------------------
	'Modified Date				                                        Modified By
	'Date					                                            Name
	**********************************************************************************//*

	@Test
	public void testSmoke108931() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Upload objUpload = new Upload();
		General objGen = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		
		
		try {
			gstrTCID = "108931"; // Test Case Id
			gstrTO = "Verify that RegAdmin can upload resource providing mandatory data.";//TO																				
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
			String strLoginUserName = rdExcel.ReadData("Login", 3, 1);
			String strLoginPassword = rdExcel.ReadData("Login", 3, 2);
			String strRegn = rdExcel.ReadData("Login", 3, 4);
            //ST;
			String strNStatType1 = "AutoNSt1_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strNSTvalue[] = new String[1];
			String strStatValue = "";
			//RT
			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strRTValue[] = new String[1];
			//RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strAbbrv = "Av";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strUplPath = pathProps
					.getProperty("UploadResFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");
			boolean  blnTest, blnSave, blnDisabled;
			
			String strDate="";
			
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
				
				String[] strTestData = { "","N", strResource1, strAbbrv, strRTValue[0],
						"", "101", "", "N", "N", "", "", "", "", "", "", "",
						strContFName, strContLName, "", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "" };
				
				objOFC.writeResultDataToParticularRow(strTestData, strUplPath,
						"Resource", 1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			
			 * 3 Navigate to 'Setup >> Upload' 'Upload List' screen is
			 * displayed.
			 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
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
				strFuncResult = objUpload
						.verifyElmentsInNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			 * 5 Select 'Browse' and add file 'X' File 'X' attached path is
			 * displayed in the 'Spreadsheet' field.
			 
			
			
			blnTest = false;
			blnSave = true;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, blnTest,
						blnSave);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			blnDisabled = true;
			strDate = "";
			String strDateTime = "";
			try {
				assertEquals("", strFuncResult);
				String strCurYear = dts.getCurrentDate("yyyy");
				String[] strArFunRes = new String[5];
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strDate=dts.getTimeOfParticularTimeZone("CST", "MM/dd/yyyy");
				
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
			
			
			String strExcelName="";
			try {
				assertEquals("", strFuncResult);
				strExcelName = "D:\\eclipse\\com.qsgsoft.EMResource\\"
						+ "SupportFiles\\UploadFiles\\ImportResource.xls";
				strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(
						selenium, strExcelName, strLoginUserName, strDate,
						blnTest, blnDisabled);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "1", "\\d+", strResource1, "No", "",
					"", "", "", "No", "no user data provided" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToEditResourcePageFromResourceID(
						selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strElementID=propElementDetails.getProperty("CreateResource.Name");
				strFuncResult = objGen.assertEQUALSValue(selenium, strElementID, strResource1);
				if(strFuncResult.equals("")){
					log4j.info("Resource Name provided in the standard template while " +
							"uploading the resource is displayed appropriately for " +
							"the respective fields");
				}else{
					log4j.info("Resource Name provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields");
					strFuncResult="Resource Name provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.Abbreviation");
				strFuncResult = objGen.assertEQUALSValue(selenium,
						strElementID, strAbbrv);
				
				if(strFuncResult.equals("")){
					log4j.info("Abbrevation provided in the standard template while " +
							"uploading the resource is displayed appropriately for " +
							"the respective fields");
				}else{
					log4j.info("Abbrevation provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields");
					strFuncResult="Abbrevation provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.ResourceType");
				strFuncResult = objGen.assertEQUALSgetSelectedLabel(selenium,
						strElementID, strResrctTypName1);
				if(strFuncResult.equals("")){
					log4j.info("Resource type provided in the standard template while " +
							"uploading the resource is displayed appropriately for " +
							"the respective fields");
				}else{
					log4j.info("Resource type provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields");
					strFuncResult="Resource type provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.StandResType");
				strFuncResult = objGen.assertEQUALSgetSelectedLabel(selenium,
						strElementID, strStandResType);
				
				if(strFuncResult.equals("")){
					log4j.info("Standard Resource type provided in the standard template while " +
							"uploading the resource is displayed appropriately for " +
							"the respective fields");
				}else{
					log4j.info("Standard Resource type provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields");
					strFuncResult="Standard Resource type provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			 * 9 Click on 'Cancel' 'Resource List' screen is displayed.
			 * 
			 * Uploaded resource is listed, appropriate values are displayed.
			 * 
			 * 'Edit', 'Status Types', 'Users','Sub-resources' and 'Demote' link
			 * are available corresponding to the uploaded resource.
			 
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.cancelAndNavToRSListPage(selenium);
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
			

			
			
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
			
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "108931"; // Test Case Id
			gstrTO = "Verify that RegAdmin can upload resource providing mandatory data.";// TO
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
	}*/
	
	/********************************************************************************
	'Description	:Verify that user can be uploaded providing mandatory data in the 
	'				 standard template used for uploading.
	'				data in the template.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:11-Mar-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	@Test
	public void testSmoke108932() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		General objGeneral = new General();
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Resources objResources = new Resources();
		Upload objUpload = new Upload();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

		try {
			gstrTCID = "108932";
			gstrTO = "Verify that user can be uploaded providing mandatory data"
					+ " in the standard template used for uploading.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

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

			String strGenDate = "";
			String strGenDateFormatNew = "";
			//String strDate = "";
			String strUploadFilePath = "";
			boolean blnTest = false;

			String strRegGenTime = "";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/*
			 * 1 Precondition: 1. Role 'R' is created in region 'X' 2. Role ID
			 * for for role 'R' is noted. 3. Resource 'RS' is created under 'RT'
			 * which is associated to 'ST' 3. Following data is provided in the
			 * standard template 'X': 1. User ID 2. Role ID 3. Password 4. Full
			 * Name
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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navCreateRolePge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.roleMandtoryFlds(selenium,
						strRolesName);
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

			/*
			 * 3. Resource 'RS' is created under 'RT' which is associated to
			 * 'ST'
			 */

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
						selenium, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
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
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Following data is provided in the standard template 'X': 1.
			 * User ID 2. Role ID 3. Password 4. Full Name
			 */

			try {
				assertEquals("", strFuncResult);

				String[] strTestData = { "", "N", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", strUserID, strRoleValue, "", strInitPwd,
						strUsrFulName, "", "", "", "", "", "", "", "", "", "",
						"" };

				String strWriteFilePath = pathProps
						.getProperty("ImportDataExcelPath");

				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 1);

			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
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
			 * 2 Login as RegAdmin and navigate to region 'X' 'Region Default'
			 * screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
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
			 * 3 Navigate to 'Setup >> Upload' 'Upload List' screen is
			 * displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Select 'Upload Resources' 'New Upload' screen is displayed.
			 */
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
			 * 5 Select 'Browse' and add file 'X' File 'X' is populated in the
			 * 'Spreadsheet' field.
			 */

			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				strUploadFilePath = pathProps
						.getProperty("ImportDataExcelPath");
				blnTest = false;

				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUploadFilePath, strAutoFileName,
						blnTest, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strArFunRes[] = objGeneral.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				String strCurYear = dts.getFutureYear(0, "yyyy");
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				log4j.info(strGenDate);
				strGenDateFormatNew = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"yyyy-MM-dd");
				//strDate = dts.getTimeOfParticularTimeZone("CST", "MM/dd/yyyy");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
				strGenDateFormatNew = strGenDateFormatNew + " " + strRegGenTime;

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Deselect the option 'Test?' and 'Save' 'Upload Details' screen
			 * is displayed. Following fields are displayed with appropriate
			 * value and is disabled. 1. Spreadsheet: File path 2. Test?: Is
			 * deselected 3. User: RegAdmin 4. Date: Current Date Appropriate
			 * values are present under the following column headers: 1. Row- 1
			 * 2. Resource ID- None 3. Resource Name- None 4. Failed- No 5.
			 * Geocode- None 6. Resource Comments- No resource data provided 7.
			 * User ID- As provided 8. Full Name- As provided 9. Failed- No 10.
			 * User Comments- None 'Return' button is available.
			 */


		
		
			/*
			 * 7 Select 'Return' 'Upload List' screen is displayed with
			 * appropriate values under following column headers. 1. Action:
			 * View Details 2. Test?: No 3. Date: Resource upload date and time
			 * 4. User: RegAdmin
			 */

			try {
				assertEquals("", strFuncResult);

				log4j
						.info("Following fields are displayed with appropriate value and"
								+ " is disabled.2. 4. Date: Current Date ");

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
				strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strColHeaders = { "Action", "Test?", "Date", "User" };
				String[] pStrArrUplData = { "View Details", "No",
						strGenDateFormatNew, strLoginUserName };
				strFuncResult = objUpload.verifyUplDetailsInUpldListPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUpload
						.navToUploadDetailsPageFromUploadListPageOfRecentUpload(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			
			/*
			 * 9 Select to edit the user The values provided while uploading the
			 * user is retained. Checkbox for role 'R' is selected User has view
			 * right to all the resources in that region.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult =objUpload.navToEditUserPageFromUserID(selenium, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.chkRoleSelectedOrNot(selenium,
						true, strRoleValue, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = propElementDetails
						.getProperty("CreateNewUsr.UserName");
				strFuncResult = objGeneral.assertEQUALSValue(selenium,
						strElementID, strUserID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = propElementDetails
						.getProperty("CreateNewUsr.FulUsrName");
				strFuncResult = objGeneral.assertEQUALSValue(selenium,
						strElementID, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9 Click on 'Cancel' 'User List' screen is displayed. 'Resource
			 * List' screen is displayed.
			 * 
			 * Uploaded user is listed, appropriate values are displayed under
			 * respective columns.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.cancelAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserID,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				try {

					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/thead/tr/th[2]/"
									+ "a[text()='Username']/ancestor::table/tbody/tr/td[2]"
									+ "[text()='" + strUserID + "']"));

					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/thead/tr/th[3]"
									+ "/a[text()='Full Name']/ancestor::table/tbody/tr/"
									+ "td[3][text()='" + strUsrFulName + "']"));

					log4j
							.info("Uploaded "
									+ strUserID
									+ " is present in the 'User List' screen under "
									+ "appropriate column headers with values provided while uploading resource. ");

				} catch (AssertionError Ae) {
					strFuncResult = "Uploaded "
							+ strUserID
							+ " is NOT present in the 'User List' screen under "
							+ "appropriate column headers with values provided while uploading resource."
							+ "" + Ae;
					log4j
							.info("Uploaded "
									+ strUserID
									+ " is NOT present in the 'User List' screen under "
									+ "appropriate column headers with values provided while uploading resource."
									+ "" + Ae);
				}
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
			gstrTCID = "108932";
			gstrTO = "Verify that user can be uploaded providing mandatory"
					+ " data in the standard template used for uploading.";
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
	'Description		:Verify that both resource and user can be uploaded to web.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:12-March-2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testSmoke108934() throws Exception {
		try {
			gstrTCID = "108934"; // Test Case Id
			gstrTO = "Verify that both resource and user can be uploaded to web.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			General objGen = new General();

			String strFuncResult = "";
			
			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();
			
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strUplPath = pathProps
					.getProperty("UploadResFile_Path");

			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");

			Login objLogin = new Login();// object of class Login

			Roles objRoles = new Roles();

			CreateUsers objCreateUsers = new CreateUsers();

			Upload objUpload = new Upload();

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

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
			gstrTCID = "BQS-108934";
			gstrTO = "Verify that both resource and user can be uploaded to web.";
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
	'Description		:Verify that 'Test' can be performed prior 
	'					to uploading both resources and users.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:13-March-2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testSmoke109023() throws Exception {
		try {
			gstrTCID = "109023"; // Test Case Id
			gstrTO = "Verify that 'Test' can be performed prior to" +
					" uploading both resources and users.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			General objGen = new General();

			String strFuncResult = "";
			
			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();
			
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strUplPath = pathProps
					.getProperty("UploadResFile_Path");

			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");

			Login objLogin = new Login();// object of class Login

			Roles objRoles = new Roles();
			CreateUsers objCreateUsers = new CreateUsers();
			Upload objUpload = new Upload();

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

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
			String strAbbrv = "Abbv";
			
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
			boolean blnTest = true;
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
			String[] pStrArrUplData = { "1", "", strResource1, "No", "",
					"", strUserName, strUsrFulName, "No", "" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 7 Hover mouse on the 'Resource ID' 'Resource ID' is not hyper
			 * linked.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objGen.checkForAnElements(selenium,
						"//div[@id='mainContainer']/form/table[2]/tbody/tr/td[text()='"
								+ strResource1 + "']/parent::tr/td[2]/a");

				if (strFuncResult.equals("Element NOT Present")) {
					strFuncResult = "";
					log4j.info("Resource ID is NOT hyperlinked");
				} else {
					log4j.info("Resource ID is  hyperlinked");
					strFuncResult = "Resource ID is hyperlinked";

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 Navigate to 'Setup >> Resources' Uploaded resource is not
			 * present in the 'Resource List' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.VerifyResource(selenium, strResource1,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 9 Navigate to 'Setup >> Users' Uploaded user is not present in
			 * the 'User List' screen
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objGen.checkForAnElements(selenium,
						"//table[@id='tblUsers']/tbody/tr/" + "td[2][text()='"
								+ strUserName + "']");
				if (strFuncResult.equals("Element NOT Present")) {
					strFuncResult = "";
					log4j
							.info("Uploaded user is NOT present in the 'User List' screen ");

				} else {
					log4j
							.info("Uploaded user is present in the 'User List' screen ");
					strFuncResult = "Uploaded user is present in the 'User List' screen ";

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 10 Navigate to 'Setup >> Upload' 'Upload List' screen is
			 * displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 11 Select 'View Details' for the recently uploaded resource and
			 * user 'Upload Details' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadDetailsPageFromUploadListPageOfRecentUpload(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objGen.checkForAnElements(selenium,
						"//table[@summary='Upload Details']/"
								+ "tbody/tr/td[8][text()='" + strUsrFulName
								+ "']" + "/preceding-sibling::td[1]/a");

				if (strFuncResult.equals("Element NOT Present")) {
					strFuncResult = "";
					log4j.info("'User ID' is NOT hyper linked. ");
				} else {
					log4j.info("'User ID' is hyper linked. ");
					strFuncResult = "'User ID' is hyper linked. ";

				}
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
			gstrTCID = "BQS-109023";
			gstrTO = "Verify that 'Test' can be performed prior " +
					"to uploading both resources and users.";
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
	'Description		:Verify that user can import sub resource providing mandatory data.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:13-March-2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testSmoke108933() throws Exception {
		try {
			gstrTCID = "108933"; 
			gstrTO = "Verify that user can import sub resource providing mandatory data.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			General objGen = new General();

			String strFuncResult = "";
			
			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();
			
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strUplPath = pathProps
					.getProperty("ImportDataExcelPath");

			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");

			Login objLogin = new Login();
			Upload objUpload = new Upload();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
	
			String strSubResrctTypName1 = "SubRt_" + strTimeText;
			String strResrctTypName = "AutoRt_" + strTimeText;
			
			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strSTvalue[] = new String[2];
			
			String strRTValue[] = new String[1];

			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource = "Sub" + strTimeText;
			
			String strAbbrv = "Av";
			String strResVal="";
			String strRSValue[]=new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
		
		

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			/*
			 * Precondition:
			 * 
			 * 1. Status type 'ST1' and 'ST2' are created and is associated to
			 * resource type 'RT' in region 'RG1'
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
						strNSTValue, strStatType1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strSTvalue[0] = objST.fetchSTValueInStatTypeList(selenium,
						strStatType1);
				if (strSTvalue[0].equals("")) {
					strFuncResult = "Failed to fetch Status type value";
					log4j.info("Failed to fetch Status type value");

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNSTValue, strStatType2, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strSTvalue[1] = objST.fetchSTValueInStatTypeList(selenium,
						strStatType2);
				if (strSTvalue[1].equals("")) {
					strFuncResult = "Failed to fetch Status type value";
					log4j.info("Failed to fetch Status type value");

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2. Sub-Resource type 'SRT1' is created selecting status type
			 * 'ST2'
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objRT.navResourceTypList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strSubResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSubResourceType(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium, strSubResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRTValue[0] = objRT.fetchResTypeValueInResTypeList(selenium,
						strSubResrctTypName1);
				if (strRTValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//RT
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
				strFuncResult = objRT.saveAndvrfyResType(selenium, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3. Resource 'RS' is created under 'RT'.
			 */
			
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
						strResource1, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource1);

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
			 * 6. Following data is provided in the standard template 'RG1':
			 * 
			 * a. ResourceID (Resource ID of the parent resource) b.
			 * Sub-resource (provide as 'Y') c. Resource Name (Sub-resource
			 * name) d. Abbrev e. ResourceTypeID (Sub-resource type ID) f.
			 * Standard Resource Type ID (selected standard sub-resource type
			 * ID) g. HAvBED (N for the field) h. Shared (N for the field) i.
			 * ContactFirstName j. ContactLastNAme
			 */
				

			try {
				assertEquals("", strFuncResult);

				String[] strTestData = { strRSValue[0], "Y", strSubResource,
						strAbbrv,strRTValue[0],"", "101", "", "N",
						"N","", "", "", "", "", "", "",
						strContFName, strContLName, "", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", ""  };

				String strWriteFilePath = pathProps
				.getProperty("ImportDataExcelPath");

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
				strFuncResult = objUpload
						.verifyElmentsInNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			boolean blnTest = true;
			boolean blnSave = true;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, blnTest,
						blnSave);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
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

			String strExcelName="";
			try {
				assertEquals("", strFuncResult);
				strExcelName = "D:\\eclipse\\com.qsgsoft.EMResource\\"
						+ "SupportFiles\\ImoprtFiles\\Import.xls";
				strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(
						selenium, strExcelName, strLoginUserName, strDate,
						blnTest, blnDisabled);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
			
			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "1", "\\d+", strSubResource, "No", "",
					"", "", "", "No", "no user data provided" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*7 	Click on 'Return', click on 'Upload Resource', select the standard template, deselect 'Test' check box and click on 'Save'. 		'Upload Details' screen is displayed.

			Following fields are displayed with appropriate value and are disabled.
			1. Spreadsheet: File path
			2. Test?: Check box is deselected
			3. User: RegAdmin
			4. Date: Current Date

			Appropriate values are present under the following column headers:
			1. Row- 1
			2. Resource ID- auto generate id for sub resource uploaded and is hyperlinked
			3. Resource Name- As provided in the template
			4. Failed- No
			5. Geocode- None
			6. Resource Comments- None
			7. User ID- None
			8. Full Name- None
			9. Failed- No
			10. User Comments- No user data provided*/

			
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
			
			blnTest = false;
			blnSave = true;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, blnTest,
						blnSave);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			blnDisabled = true;
			strDate = "";
			strDateTime = "";
			try {
				assertEquals("", strFuncResult);
				String strCurYear = dts.getCurrentDate("yyyy");
				String[] strArFunRes = new String[5];
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strDate=dts.getTimeOfParticularTimeZone("CST", "MM/dd/yyyy");
				
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

			strExcelName="";
			try {
				assertEquals("", strFuncResult);
				strExcelName = "D:\\eclipse\\com.qsgsoft.EMResource\\"
						+ "SupportFiles\\ImoprtFiles\\Import.xls";
				strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(
						selenium, strExcelName, strLoginUserName, strDate,
						blnTest, blnDisabled);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
			
			strColHeaders = new String[]{ "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			pStrArrUplData = new String[]{ "1", "\\d+", strSubResource, "No", "",
					"", "", "", "No", "no user data provided" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 Click on the auto generated sub resource id. 'Edit
			 * Sub-resource' screen is displayed.
			 */
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePageFrmUplDetails(
						selenium, strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 9 Click on 'Cancel' 'Resource List' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.cancelAndNavToRSListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 10 Click on 'Sub-resource' link corresponding to the parent
			 * resource id provided in the template. 'Sub-resource List for <
			 * Resource name >' screen is displayed.
			 * 
			 * Sub-resource uploaded is listed and the details are displayed
			 * appropriately under the respective columns.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Sub-Resources']/thead/"
						+ "tr/th[3]/a[text()='Name']/ancestor::table/tbody/tr/"
						+ "td[3][text()='" + strSubResource + "']";
				strFuncResult = objGen.checkForAnElements(selenium,
						strElementID);

				strElementID = "//table[@summary='Sub-Resources']/thead/tr/th[4]"
						+ "/a[text()='Abbreviation']/ancestor::table/tbody/tr/td"
						+ "[3][text()='"
						+ strSubResource
						+ "']/following-sibling::"
						+ "td[1][text()='"
						+ strAbbrv + "']";
				strFuncResult = objGen.checkForAnElements(selenium,
						strElementID);

				strElementID = "//table[@summary='Sub-Resources']/thead/tr/th[4]"
						+ "/a[text()='Abbreviation']/ancestor::table/tbody/tr/td"
						+ "[3][text()='"
						+ strSubResource
						+ "']/following-sibling::"
						+ "td[2][text()='"
						+ strSubResrctTypName1 + "']";

				strFuncResult = objGen.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Sub-resource uploaded is listed and the "
							+ "details are displayed appropriately under"
							+ " the respective columns.");
				} else {
					strFuncResult = "Sub-resource uploaded is listed and the details are"
							+ " NOT displayed appropriately under the respective columns.";
					log4j
							.info("Sub-resource uploaded is listed and the details are"
									+ " NOT displayed appropriately under the respective columns.");
				}
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
			gstrTCID = "BQS-108933";
			gstrTO = "Verify that user can import sub resource providing mandatory data.";
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
//start//testBQS126266//
	/*********************************************************************************
	'Description	:Verify that RegAdmin can upload resource providing mandatory data.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/8/2013
	'Author			:QSG
	'---------------------------------------------------------------------------------
	'Modified Date				                                      Modified By
	'Date					                                          Name
	**********************************************************************************/

	@Test
	public void testSmoke126266() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Upload objUpload = new Upload();
		StatusTypes objST = new StatusTypes();
		General objGen = new General();
		ResourceTypes objRT = new ResourceTypes();
		Resources objResources = new Resources();
		OfficeCommonFunctions OFC = new OfficeCommonFunctions();
		try {
			gstrTCID = "126266"; // Test Case Id
			gstrTO = " Verify that RegAdmin can upload resource providing mandatory data.";//TO
			gstrReason = "";
			gstrResult = "FAIL";
		
			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
            //ST;
			String strNStatType1 = "AutoNSt1_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strNSTvalue[] = new String[1];
			String strStatValue = "";
			//RT
			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strRTValue[] = new String[1];
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "Av";

			String strDate = "";
			String strDateTime = "";
		/*
		* STEP :
		  Action:Precondition: 
			1. Status type 'ST' is created and is associated to resource type 'RT' in region 'X' 
			2. Resource type ID for 'RT' is noted.
		  Expected Result:No Expected Result
		*/
		//662719
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STARTS~~~~~");
			
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

			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNStatType1);
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName1,
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
				strFuncResult = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrctTypName1);
				if (strFuncResult.compareTo("") != 0) {
					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~Precondition" + gstrTCID + " ENDS~~~~~");
			// Logout
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");

		/*
		* STEP :
		  Action:Login as RegAdmin to region 'X'
		  Expected Result:'Region Default' screen is displayed.
		*/
		//662720
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

		/*
		* STEP :
		  Action:Navigate to 'Setup >> Upload'
		  Expected Result:'Upload List' screen is displayed.
				  'Upload Template' link is present next to 'Upload Resource' button at top left.
				  Details of previous upload are listed on 'Upload List' screen
		*/
		//662721
			
			// Downloading Upload Template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Upload Template' link
		  Expected Result:Standard 'Upload Template' in .XLS format is opened
			Following data is provided in the standard template 'X' used for uploading resource:
			ResourceID
			SubResource
			ResourceName
			Abbrev
			ResourceTypeID
			ResourceType
			StandardResourceTypeID
			StandardResourceType
			HAvBED
			Shared
			AhaID
			ExternalID
			StreetAddress
			City
			State
			ZipCode
			County
			ContactFirstName
			ContactLastName
			ContactAddress
			ContactPhone1
			ContactPhone2
			ContactFax
			ContactEMail
			UserID
			RoleID
			RoleName
			Password
			FullName
			FirstName
			MiddleName
			LastName
			Organization
			WorkPhone
			PrimaryEMail
			AlternateEMails
			TextPager
			Associated
			Update
			Reports
		*/
		//662732
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
				
				String strDownloadPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp"
						+ gstrTCID + strTimeText + ".xls";

				String[] strTestData = {"ResourceID", "SubResource",
						"ResourceName", "Abbrev", "ResourceTypeID",
						"ResourceType", "StandardResourceTypeID",
						"StandardResourceType", "HAvBED", "Shared", "AhaID","ExternalID",
						"StreetAddress", "City", "State", "ZipCode", "County",
						"ContactFirstName", "ContactLastName",
						"ContactAddress", "ContactPhone1", "ContactPhone2",
						"ContactFax", "ContactEMail", "UserID", "RoleID",
						"RoleName", "Password", "FullName", "FirstName",
						"MiddleName", "LastName", "Organization", "WorkPhone",
						"PrimaryEMail", "AlternateEMails", "TextPager",
						"Associated", "Update", "Reports" };

				strFuncResult = OFC.readAndVerifyParticularDataExcel(strTestData,
						strDownloadPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide appropriate data in the 'Standard Template' and 'Save' the template.
		  Expected Result:Template with appropriate data is saved.
		*/
		//662733
			// Writing data in Template
			try {
				assertEquals("", strFuncResult);
				
				String strWriteFilePath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp"
						+ gstrTCID + strTimeText + ".xls";
				
				String[] strTestData = { "", "N", strResource, strAbbrv,
						strRTValue[0], strResrctTypName1, "106", "Hospital",
						"N", "N", "", "", "", "", "", "", "", "FN", "LN", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "", "", "", "" };
				
				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 1);

			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Upload Resources' button.
		  Expected Result:'New Upload' screen is displayed. 
		  Field 'Spreadsheet' is displayed as mandatory with a 'Browse' button. An instruction
		  'Only .xls files are allowed. Maximum file size is 5 megabytes (MB).' is displayed. 
	      Option 'Test?' is available with a checkbox which is selected by default.
	       An instruction 'If selected, then the system will not actually create Resources.' is displayed.
		*/
		//662722
			// Uploading template by selecting test(Actual upload )
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
		* STEP :
		  Action:Select 'Browse' and add file 'X'
		  Expected Result:File 'X' attached path is displayed in the 'Spreadsheet' field.
		*/
		//662723
			try {
				assertEquals("", strFuncResult);
				String strDownloadPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp"
						+ gstrTCID + strTimeText + ".xls";

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strDownloadPath, strAutoFileName, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Save'
		  Expected Result:'Upload Details' screen is displayed. 
			Following fields are displayed with appropriate value and are disabled. 
			1. Spreadsheet: File path 
			2. Test?: check box is selected
			3. User: RegAdmin 
			4. Date: Current Date in format MM/DD/YYYY
			   Appropriate values are present under the following column headers: 
			1. Row- 1 
			2. Resource ID- None (Left blank)
			3. Resource Name- As provided in the template 
			4. Failed- No 
			5. Geocode- None 
			6. Resource Comments- None 
			7. User ID- None 
			8. Full Name- None 
			9. Failed- No 
		   10. User Comments- No user data provided
			 'Return' button is available.
		*/
		//662724
			try {
				assertEquals("", strFuncResult);
				String strCurYear = dts.getCurrentDate("yyyy");
				String[] strArFunRes = new String[5];
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strDate=dts.getTimeOfParticularTimeZone("CST", "MM/dd/yyyy");
				strDateTime = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"yyyy-MM-dd");
				strDateTime = strDateTime + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Return' button, click on 'Upload Resources', attach the same stand template, deselect 'Test' check box and click on 'Save'.
		  Expected Result:'Upload Details' screen is displayed. 
		  Following fields are displayed with appropriate value and are disabled. 
			1. Spreadsheet: File path 
			2. Test?: check box is deselected 
			3. User: RegAdmin 
			4. Date: Current Date in format MM/DD/YYYY 
			Appropriate values are present under the following column headers: 
			1. Row- 1 
			2. Resource ID- Auto generated id (valid number) 
			3. Resource Name- As provided in the template 
			4. Failed- No 
			5. Geocode- None 
			6. Resource Comments- None 
			7. User ID- None 
			8. Full Name- None 
			9. Failed- No 
			10. User Comments- No user data provided 
			'Return' button is available.
		*/
		//662725
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload
						.verifyElmentsInUploadDetailsPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strDownloadPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp"
						+ gstrTCID + strTimeText + ".xls";

				strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(
						selenium, strDownloadPath, strLoginUserName, strDate,
						true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "1", "\\d+", strResource, "No", "",
					"", "", "", "No", "no user data provided" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on the auto generated resource id.
		  Expected Result:'Edit Resource' screen is displayed.
		  Details provided in the standard template while uploading the resource is displayed appropriately
		   for the respective fields.
		*/
		//662726
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToEditResourcePageFromResourceID(
						selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				String strElementID=propElementDetails.getProperty("CreateResource.Name");
				strFuncResult = objGen.assertEQUALSValue(selenium, strElementID, strResource);
				if(strFuncResult.equals("")){
					log4j.info("Resource Name provided in the standard template while " +
							"uploading the resource is displayed appropriately for " +
							"the respective fields");
				}else{
					log4j.info("Resource Name provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields");
					strFuncResult="Resource Name provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.Abbreviation");
				strFuncResult = objGen.assertEQUALSValue(selenium,
						strElementID, strAbbrv);
				
				if(strFuncResult.equals("")){
					log4j.info("Abbrevation provided in the standard template while " +
							"uploading the resource is displayed appropriately for " +
							"the respective fields");
				}else{
					log4j.info("Abbrevation provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields");
					strFuncResult="Abbrevation provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.ResourceType");
				strFuncResult = objGen.assertEQUALSgetSelectedLabel(selenium,
						strElementID, strResrctTypName1);
				if(strFuncResult.equals("")){
					log4j.info("Resource type provided in the standard template while " +
							"uploading the resource is displayed appropriately for " +
							"the respective fields");
				}else{
					log4j.info("Resource type provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields");
					strFuncResult="Resource type provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.StandResType");
				strFuncResult = objGen.assertEQUALSgetSelectedLabel(selenium,
						strElementID, "Hospital");
				
				if(strFuncResult.equals("")){
					log4j.info("Standard Resource type provided in the standard template while " +
							"uploading the resource is displayed appropriately for " +
							"the respective fields");
				}else{
					log4j.info("Standard Resource type provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields");
					strFuncResult="Standard Resource type provided in the standard template while " +
							"uploading the resource is NOT displayed appropriately for " +
							"the respective fields";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Cancel'
		  Expected Result:'Resource List' screen is displayed.
		  Uploaded resource is listed, appropriate values are displayed.
	      'Edit', 'Status Types', 'Users','Sub-resources' and 'Demote' link are 
	      available corresponding to the uploaded resource.
		*/
		//662727
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.cancelAndNavToRSListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyResourceInfoInResList(
						selenium, strResource, "No", "", strAbbrv,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyLinksUndrActionInRSListPge(
						selenium, strResource);
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
			gstrTCID = "BQS-126266";
			gstrTO = "Verify that RegAdmin can upload resource providing mandatory data.";
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

	// end//testBQS126266//
	
}
