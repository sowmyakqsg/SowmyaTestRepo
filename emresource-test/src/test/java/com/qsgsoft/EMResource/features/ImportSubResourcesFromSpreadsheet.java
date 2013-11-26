package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.General;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.ResourceTypes;
import com.qsgsoft.EMResource.shared.Resources;
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
' Requirement       :Import Sub-Resources from Spreadsheet
' Product		    :EMResource v3.20
' Date			    :12-11-2012
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date				                         Modified By
' Date					                             Name
'*******************************************************************/
public class ImportSubResourcesFromSpreadsheet {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.HappyDay_EditNEDOCScoreStatusTypes");
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
	
	//start//testBQS107892//
	/***********************************************************************************************
	'Description	:Verify that sub-resources can be uploaded under resources providing Resource ID
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/16/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	**********************************************************************************************/

	@Test
	public void testBQS107892() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Upload objUpload = new Upload();
		General objGen = new General();
		StatusTypes objST = new StatusTypes();
		Resources objResources = new Resources();
		ResourceTypes objRT = new ResourceTypes();
		try {
			gstrTCID = "107892"; // Test Case Id
			gstrTO = " Verify that sub-resources can be uploaded under resources providing Resource ID";//TO
			gstrReason = "";
			gstrResult = "FAIL";	
		
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");			
			Paths_Properties objAP = new Paths_Properties();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			pathProps = objAP.Read_FilePath();
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			String strNStatType1 = "AutoST1_" + strTimeText;
			String strNStatType2 = "AutoST2_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strNSTvalue[] = new String[2];
			
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strSubResrctTypName = "SRt1_" + strTimeText;
			String strRTValue[] = new String[2];		
			String strResource = "AutoRs1_" + strTimeText;
			String strSubResource = "SubRs_" + strTimeText;
			String strStandResType = "Aeromedical";
			String strAbbrv = "Abbv";	
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			// Sub Resource Icon
			String strIconImg = rdExcel.readInfoExcel("ResourceIcon", 2, 2,
					strFILE_PATH);
			String strRSValue[]=new String[2];

			//Upload file details
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strUplPath = pathProps
					.getProperty("UploadResFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");
			
		log4j.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION STARTS~~~~~");
		/*
		* STEP :
		  Action:Precondition:
			1. Status type 'ST1' and 'ST2' are created and is associated to resource type 'RT' in region 'RG1'
			2. Sub-Resource type 'SRT1' is created selecting status type 'ST2'
			3. Resource 'RS' is created under 'RT'.
			4. Resource ID for 'RS' is noted.
			5. Following data is provided in the standard template 'RG1':
			1. ResourceID (Resource ID of the parent resource)
			2. Sub-resource (provide as 'Y')
			3. Resource Name (Sub-resource name)
			4. Abbrev
			5. ResourceTypeID (Sub-resource type ID)
			6. Standard Resource Type ID (selected standard sub-resource type ID)
			7. HAvBED (Y\N for the field)
			8. Shared (Y\N for the field)
			9. ContactFirstName 
			10. ContactLastNAme
		 Expected Result:No Expected Result
		*/
		//610244
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
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
				strNSTvalue[0] = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strNStatType1);
				if (strNSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strNStatType2, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strNSTvalue[1] = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strNStatType2);
				if (strNSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
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
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strNSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strNSTvalue[1], true);
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
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// SRT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strNSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue[1] = objRT
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strRTValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
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
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources
						.fetchResValueInResList(seleniumPrecondition, strResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Write data to template
			try {
				assertEquals("", strFuncResult);
				String[] strTestData = { strRSValue[0], "Y",strSubResource, strAbbrv,
						strRTValue[1], "", "101", "", "N", "N", "", "", "", "",
						"", "", "", strContFName, strContLName, "", "", "", "",
						"", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "",
						"" };

				objOFC.writeResultDataToParticularRow(strTestData, strUplPath,
						"Resource", 1);
			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		 log4j.info("~~~~~Precondition" + gstrTCID + " ENDS~~~~~");
		 log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");
		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to region 'RG1'
		  Expected Result:'Region Default' screen is displayed.
		*/
		//610245
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
		*/
		//610246
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Select 'Upload Resources'
		  Expected Result:'New Upload' screen is displayed.
			Field 'Spreadsheet' is displayed as mandatory with a 'Browse' button. An instruction 
			'Only .xls files are allowed. Maximum file size is 5 megabytes (MB).' is displayed.
			Option 'Test?' is available with a check-box which is selected by default.
			 An instruction 'If selected, then the system will not actually create Resources.' is displayed.
		*/
		//610247
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		* STEP :
		  Action:Select 'Browse' and add file 'X'
		  Expected Result:File 'X' is populated in the 'Spreadsheet' field.
		*/
		//610248	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Deselect the option 'Test?' and 'Save'
		  Expected Result:'Upload Details' screen is displayed.
			Following fields are displayed with appropriate value and is disabled.
			1. Spreadsheet: File path
			2. Test?: Is deselected
			3. User: RegAdmin
			4. Date: Current Date
		Appropriate values are present under the following column headers:
			1. Row- 1
			2. Resource ID- An appropriate value
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
		//610249
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
				/*strDate = dts.converDateFormat(strArFunRes[0] + strArFunRes[1]
						+ strCurYear, "ddMMMyyyy", "MM/dd/yyyy");*/
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
				strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(
						selenium, strUplPath, strLoginUserName, strDate,
						true, blnDisabled);
				if (strFuncResult.compareTo("") != 0) {
					String strExcelName = "ImportResource.xls";
					strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(
							selenium, strExcelName, strLoginUserName, strDate,
							true, blnDisabled);
				}

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			int intRSvalue=Integer.parseInt(strRSValue[0]);
			intRSvalue=intRSvalue+1;
			String strRsvalue=""+intRSvalue;
			String[] pStrArrUplData = { "1", strRsvalue, strSubResource, "No", "",
					"",
					"", "", "No", "no user data provided" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Select 'Return'
		  Expected Result:'Upload List' screen is displayed with appropriate values under following column headers.
			1. Action: View Details
			2. Test?: No
			3. Date: Resource upload date and time
			4. User: RegAdmin
		*/
		//610250
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strUpldColHeaders = { "Action", "Test?", "Date",
						"User" };
				String[] pStrArrUplListData = { "View Details", "No",
						strDateTime, strLoginUserName };
				strFuncResult = objUpload.verifyUplDetailsInUpldListPgeWithVaryingMin(
						selenium, strUpldColHeaders, pStrArrUplListData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to 'Setup >> Resources'
		  Expected Result:'Resource List' screen is displayed.
			Resource 'RS' is displayed under it.
			Sub-resource(1) is displayed associated to resource 'RS'
		*/
		//610251

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
				String strSubRSCount = "Sub-Resources (1)";
				strFuncResult = objResources.VerifySubResourceCountInRSList(
						selenium, strSubRSCount, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Sub-Resource(1)' link
		  Expected Result:Uploaded sub-resource is present under parent resource 'RS' 
		   under appropriate column headers with values provided while uploading sub-resource.
		*/
		//610253
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifySubResDataInRSListPge(selenium,
						strSubResource, strAbbrv, strSubResrctTypName, strIconImg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Select to edit the resource.
		  Expected Result:The values provided while uploading the resource is retained.
		*/
		//610254
			
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
				strFuncResult = objResources.verifyResMandValuesWithCityAndStInEditRes(selenium,
						strResource, strAbbrv, "", strResrctTypName,
						strStandResType, strState, strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Status Types' link associated with the parent resource
		  Expected Result:'ST1' and 'ST2' are listed on 'Edit Resource-Level Status Types'
		   screen, check boxes corresponding to the status types are selected and are disabled.
		*/
		//610255
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.cancelAndNavToRSListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage_LinkChanged(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium,
						strNStatType1, strNSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifySTisCheckedOrNot(selenium, strNSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifySTisEditableOrNot(selenium, strNSTvalue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium,
						strNStatType2, strNSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifySTisCheckedOrNot(selenium, strNSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifySTisEditableOrNot(selenium, strNSTvalue[1], false);
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
			gstrTCID = "BQS-107892";
			gstrTO = "Verify that sub-resources can be uploaded under resources providing Resource ID";
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

	//end//testBQS107892//
	//start//testBQS107893//
	/***************************************************************
	'Description	:Verify that sub-resources cannot be uploaded by providing normal resource type id under 'ResourceType ID' column in the standard template
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/17/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS107893() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Upload objUpload=new Upload();
		try {
			gstrTCID = "107893"; // Test Case Id
			gstrTO = " Verify that sub-resources cannot be uploaded by providing normal resource type id under 'ResourceType ID' column in the standard template";// Test
																																									// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative
			// URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;

			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";

			String strStatValue = "";
			String strSTvalue[] = new String[2];

			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;

			String strRTValue[] = new String[2];
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strResVal = "";
			String strRSValue[] = new String[3];

			// Upload file paths
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strUplPath = pathProps
					.getProperty("UploadResFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");


			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Precondition: 1. Status type 'ST1' and 'ST2' are
			 * created and is associated to resource type 'RT' in region 'RG1'
			 * 2. Sub-Resource type 'SRT1' is created selecting status type'ST2'
			 * 3. Resource 'RS' is created under 'RT'.
			 */
			// 610256
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				blnLogin = true;
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
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
						seleniumPrecondition, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
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
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
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
						strResrctTypName, strStandResType, strContFName,
						strContLName);

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
				strResVal = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SRT
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
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {
					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. Resource ID for 'RS' is noted. 5. Following data is provided
			 * in the standard template 'RG1': 1. ResourceID (Resource ID of the
			 * parent resource) 2. Sub-resource (provide as 'Y') 3. Resource
			 * Name (Sub-resource name) 4. Abbrev 5. ResourceTypeID (provide
			 * normal resource type ID) 6. Standard Resource Type ID (selected
			 * standard sub-resource type ID) 7. HAvBED (Y\N for the field) 8.
			 * Shared (Y\N for the field) 9. ContactFirstName 10.
			 * ContactLastNAme Expected Result:No Expected Result
			 */

			String[] strTestData = { strRSValue[0], "Y", strSubResource,
					strAbbrv, strRTValue[0], "", "101", "", "N", "N", "", "",
					"", "", "", "", "", strContFName, strContLName,
					"", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "" };
			try {
				assertEquals("", strFuncResult);
				objOFC.writeResultDataToParticularRow(strTestData, strUplPath,
						"Resource", 1);
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
			/*
			 * STEP : Action:Login as RegAdmin and navigate to region 'RG1'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 610257
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
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
			// 610258
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select 'Upload Resources' Expected Result:'New
			 * Upload' screen is displayed.
			 * 
			 * Field 'Spreadsheet' is displayed as mandatory with a 'Browse'
			 * button. An instruction 'Only .xls files are allowed. Maximum file
			 * size is 5 megabytes (MB).' is displayed.
			 * 
			 * Option 'Test?' is available with a check-box which is selected by
			 * default. An instruction 'If selected, then the system will not
			 * actually create Resources.' is displayed.
			 */
			// 610259
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
			 * STEP : Action:Select 'Browse' and add file 'X' Expected
			 * Result:File 'X' is populated in the 'Spreadsheet' field.
			 */
			// 610260
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Deselect the option 'Test?' and 'Save' Expected
			 * Result:Invalid Resource Type ID message is displayed under
			 * 'Resource Comments' column on 'Upload Details' screen.
			 * 
			 * Sub-Resource is not uploaded.
			 */
			// 610261
			String[] strColHeaders = { "", "", "", "", "", "Resource Comments",
					"", "", "", "" };
			String[] pStrArrUplData = { "", "", "", "", "",
					"invalid ResourceTypeID", "", "", "", "" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsNew(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to 'Setup >> Resources' Expected
			 * Result:'Resource List' screen is displayed.
			 * 
			 * Resource 'RS' is displayed under it.
			 * 
			 * Sub-resource(1) is not displayed associated to resource 'RS'
			 */
			// 610263
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSubRSCount = "Sub-Resources (1)";
				strFuncResult = objResources.VerifySubResourceCountInRSList(
						selenium, strSubRSCount, strResource1);
				if (strFuncResult
						.equals("'"
								+ strSubRSCount
								+ "' is NOT displayed for 'Sub-Resource' corresponding to resource "
								+ strResource1 + ".")) {
					strFuncResult = "";
				} else {
					strFuncResult = "'"
							+ strSubRSCount
							+ "' is NOT displayed for 'Sub-Resource' corresponding to resource "
							+ strResource1 + ".";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Sub-Resource' link Expected
			 * Result:'Sub-Resource List for < Resource Name >' scrren is
			 * displayed.
			 * 
			 * 'Create sub-resource' button is present.
			 * 
			 * Sub-Rersource is not listed under it.
			 */
			// 610264
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateResource.ValuesubResource")));
				log4j.info("'Create sub-resource' button is present.");
			} catch (AssertionError Ae) {
				strFuncResult = "'Create sub-resource' button is Not present.";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifySubResource(selenium,
						strSubResource, false);
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
			gstrTCID = "BQS-107893";
			gstrTO = "Verify that sub-resources cannot be uploaded by providing normal resource type id under 'ResourceType ID' column in the standard template";
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

	//end//testBQS107893//
	
	
	
}
