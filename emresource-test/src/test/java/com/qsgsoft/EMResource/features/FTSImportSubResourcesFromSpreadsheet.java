package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.CreateUsers;
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
' Requirement       :Import Sub-Resources from Spreadsheet
' Product		    :EMResource v3.20
' Date			    :12-11-2012
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date				                         Modified By
' Date					                             Name
'*******************************************************************/
public class FTSImportSubResourcesFromSpreadsheet {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features." +
					"FTSImportSubResourcesFromSpreadsheet");
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

	//start//testFTS108407//
	/***************************************************************
		'Description	:Verify that sub-resources with same name can be uploaded under different resources.
		'Precondition	:
		'Arguments		:None
		'Returns		:None
		'Date			:7/24/2013
		'Author			:QSG
		'---------------------------------------------------------------
		'Modified Date				Modified By
		'Date					Name
	 ***************************************************************/

	@Test
	public void testFTS108407() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Upload objUpload = new Upload();
		StatusTypes objST = new StatusTypes();
		Resources objResources = new Resources();
		ResourceTypes objRT = new ResourceTypes();
		try {
			gstrTCID = "108407"; // Test Case Id
			gstrTO = " Verify that sub-resources with same name can be uploaded under different resources.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();

			String strDate=dts.getCurrentDate("MM/dd/yyyy");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNStatType1 = "AutoST1_" + strTimeText;
			String strNStatType2 = "AutoST2_" + strTimeText;
			String strNStatType3 = "AutoST3_" + strTimeText;
			String strNStatType4 = "AutoST4_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strNSTvalue[] = new String[4];

			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strResrctTypName2 = "AutoRt2_" + strTimeText;
			String strSubResrctTypName = "SRt1_" + strTimeText;
			String strSubResrctTypName2 = "SRt2_" + strTimeText;
			String strRTValue[] = new String[4];
			String strResource = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strSubResource = "SubRs_" + strTimeText;
			String strStandResType = "Aeromedical";
			String strAbbrv = "Abbv";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strRSValue[] = new String[2];

			// Upload file details
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strUplPath = pathProps.getProperty("UploadSubResFile");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Precondition: 1. Status types ST1, ST2, ST3 and ST4
			 * are created in region 'RG1' 2.Resource types RT1 and RT2 are
			 * created selecting status types ST1 and ST2 respectively. 3.
			 * Sub-Resource types SRT1 and SRT2 are created selecting status
			 * types ST3 and ST4 respectively. 4. Resource RS1 is created under
			 * RT1. 5. Resource RS2 is created under RT2. 6. Resource ID for RS1
			 * and RS2 are noted. 7. Sub-resource types SRT1 and SRT2 are noted.
			 * 8. Sub-resource template contains the same name for both the
			 * sub-resources that are uploaded under two different parent
			 * resources RS1 and RS2 along with the other details in the
			 * template. Expected Result:No Expected Result
			 */
			// 611931
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
				strNSTvalue[0] = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNStatType1);
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strNSTvalue[1] = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNStatType2);
				if (strNSTvalue[1].compareTo("") != 0) {
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNStatType3,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strNSTvalue[2] = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNStatType3);
				if (strNSTvalue[2].compareTo("") != 0) {
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNStatType4,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strNSTvalue[3] = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNStatType4);
				if (strNSTvalue[3].compareTo("") != 0) {
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
			// RT2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName2,
						"css=input[name='statusTypeID'][value='"
								+ strNSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrctTypName2);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strNSTvalue[2] + "']");
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
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRTValue[2] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strSubResrctTypName);
				if (strRTValue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SRT2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName2,
						"css=input[name='statusTypeID'][value='"
								+ strNSTvalue[3] + "']");
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
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strSubResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue[3] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strSubResrctTypName2);
				if (strRTValue[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
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
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
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
			// RS2
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
						seleniumPrecondition, strResource2, strAbbrv,
						strResrctTypName2, strContFName, strContLName,
						strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Write data to template
			try {
				assertEquals("", strFuncResult);
				String[] strTestData = { strRSValue[0], "Y", strSubResource,
						strAbbrv, strRTValue[2], "", "101", "", "N", "N", "",
						"", "", "", "", "", "", strContFName, strContLName, "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "", "", "", "" };

				objOFC.writeResultDataToParticularRow(strTestData, strUplPath,
						"Resource", 1);
			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strTestData2 = { strRSValue[1], "Y", strSubResource,
						strAbbrv, strRTValue[2], "", "101", "", "N", "N", "",
						"", "", "", "", "", "", strContFName, strContLName, "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "", "", "", "" };

				objOFC.writeResultDataToParticularRow(strTestData2, strUplPath,
						"Resource", 2);
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
			 * STEP : Action:Login as RegAdmin and navigate to region 'RG1'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 611933
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
			// 611934
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
			// 611935
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
			 * STEP : Action:Select 'Browse' and add the sub-resource template
			 * 'X', deselect 'Test?' checkbox and click on 'Save' Expected
			 * Result:'Upload Details' screen is displayed.
			 * 
			 * Following fields are displayed with appropriate values and are
			 * disabled. 1. Spreadsheet: Attached sub-resource template path 2.
			 * Test?: checkbox unchecked 3. User: RegAdmin 4. Date: Current Date
			 * in the format MM/DD/YYYY
			 */
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(selenium, strUplPath, strLoginUserName, strDate, blnTest, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * Appropriate values are present under the following column
			 * headers: a. Row- 1 b. Resource ID- sub resource id uploaded under
			 * parent resource RS1 c. Resource Name- First sub-resource name as
			 * provided in the template d. Failed- No e. Geocode- None f.
			 * Resource Comments- None g. User ID- None h. Full Name- None i.
			 * Failed- No j. User Comments- No user data provided
			 */

			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "", "", "", "", "",
					"", "", "", "", "" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsNew(selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * The following details are displayed for the second sub-resource:
			 * 
			 * a. Row- 2 b. Resource ID- sub resource id uploaded under parent
			 * resource RS2 c. Resource Name- Same sub-resource name as provided
			 * in the template for first sub-resource name. d. Failed- No e.
			 * Geocode- None f. Resource Comments- None g. User ID- None h. Full
			 * Name- None i. Failed- No j. User Comments- No user data provided
			 * 'Return' button is available.
			 */
			// 611936

			String[][] pStrArrUplData1 = { {"1", "\\d+", strSubResource, "No", "",
				"", "", "", "No", "no user data provided" },{ "2", "\\d+", strSubResource, "No",
					"", "", "", "", "No", "no user data provided" } };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload
						.verifyUplDetailsInUpldDetailsPge_MultipleData(
								selenium, pStrArrUplData1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the generated sub-resource id uploaded for
			 * both the sub-resources. Expected Result:'Edit Sub-Resource'
			 * screen is displayed with all the details intact as provided in
			 * the standard template while uploading.
			 */
			// 611937
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToEditResourcePageFromSpecifiedResourceID(selenium, strSubResource,"1");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifySubResValuesRetInEditSubRes(selenium, strSubResource, strAbbrv, "", 
						strSubResrctTypName, strStandResType, "", strContFName, strContLName);
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
				strFuncResult = objUpload.navToUploadDetailsPageFromUploadListPageOfRecentUpload(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToEditResourcePageFromSpecifiedResourceID(selenium, strSubResource,"2");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifySubResValuesRetInEditSubRes(selenium, strSubResource, strAbbrv, "", 
						strSubResrctTypName, strStandResType, "", strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed. Count for the
			 * sub-resource corresponding to the parent resource RS1 is
			 * incremented. Count for the sub-resource corresponding to the
			 * parent resource RS2 is incremented.
			 */
			// 611940
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSubRSCount = "Sub-Resources (1)";
				strFuncResult = objResources.VerifySubResourceCountInRSList(
						selenium, strSubRSCount, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Sub-resource' link corresponding to the
			 * parent resource RS1. Expected Result:'Sub-Resource List for <
			 * parent resource name >' is displayed.
			 * 
			 * Sub-resource uploaded is listed on the screen.
			 */
			// 611941
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifySubResource(selenium,
						strSubResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources, click on
			 * sub-resources corresponding to the parent resource RS2. Expected
			 * Result:'Sub-Resource List for < parent resource name >' is
			 * displayed. Sub-resource uploaded is listed on the screen.
			 */
			// 611942
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifySubResource(selenium,
						strSubResource, true);
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
			gstrTCID = "FTS-108407";
			gstrTO = "Verify that sub-resources with same name can be uploaded under different resources.";
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

	//end//testFTS108407//

	//start//testFTS108387//
	/***************************************************************
		'Description	:Verify that duplicate sub-resources cannot be created under the parent resource.
		'Precondition	:
		'Arguments		:None
		'Returns		:None
		'Date			:7/24/2013
		'Author			:QSG
		'---------------------------------------------------------------
		'Modified Date				Modified By
		'Date					Name
	 ***************************************************************/

	@Test
	public void testFTS108387() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Upload objUpload=new Upload();
		try {
			gstrTCID = "108387"; // Test Case Id
			gstrTO = " Verify that duplicate sub-resources cannot be created under the parent resource.";// Test
																											// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative
			// URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strDate=dts.getCurrentDate("MM/dd/yyyy");
			
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
			String strUplPath = pathProps.getProperty("UploadSubResFile");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Precondition: 1. Status type 'ST1' and 'ST2' are
			 * created and is associated to resource type 'RT' in region 'RG1'
			 * 2. Sub-Resource type 'SRT1' is created selecting status type
			 * 'ST2' 3. Resource 'RS' is created under 'RT'. 4. Resource ID for
			 * 'RS' is noted. 5. Sub-resource type 'SRT1' is noted. 6. Following
			 * data is provided in the standard template 'RG1': a. ResourceID
			 * (Resource ID of the parent resource RS noted) b. Sub-resource
			 * (provide as 'Y') c. Resource Name (provide 2 entries with same
			 * Sub-resource names) d. Abbrev (provide Abbrev) e. ResourceTypeID
			 * (provide Sub-resource type ID noted) f. Standard Resource Type ID
			 * (selected standard sub-resource type ID) g. HAvBED (N for the
			 * field) h. Shared (N for the field) i. ContactFirstName j.
			 * ContactLastNAme Expected Result:No Expected Result
			 */
			// 611846
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
			// Write data to template
			try {
				assertEquals("", strFuncResult);
				String[] strTestData = { strRSValue[0], "Y", strSubResource,
						strAbbrv, strRTValue[1], "", "101", "", "N", "N", "",
						"", "", "", "", "", "", strContFName, strContLName, "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "", "", "", "" };

				objOFC.writeResultDataToParticularRow(strTestData, strUplPath,
						"Resource", 1);
			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strTestData2 = { strRSValue[0], "Y", strSubResource,
						strAbbrv, strRTValue[1], "", "101", "", "N", "N", "",
						"", "", "", "", "", "", strContFName, strContLName, "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "", "", "", "" };

				objOFC.writeResultDataToParticularRow(strTestData2, strUplPath,
						"Resource", 2);
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
			 * STEP : Action:Login as RegAdmin and navigate to region 'RG1'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 611847
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
				blnLogin = true;
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
			// 611848
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
			// 611849
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
			 * STEP : Action:Select 'Browse' and add the sub-resource template
			 * 'X', deselect 'Test?' checkbox and click on 'Save' Expected
			 * Result:'Upload Details' screen is displayed.
			 * 
			 * Following fields are displayed with appropriate values and are
			 * disabled. 1. Spreadsheet: Attached sub-resource template path 2.
			 * Test?: checkbox unchecked 3. User: RegAdmin 4. Date: Current Date
			 * in the format MM/DD/YYYY
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(selenium, strUplPath, strLoginUserName, strDate, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "", "", "", "", "", "", "", "", "", "" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsNew(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * Appropriate values are present under the following column
			 * headers: 1. Row- 1 2. Resource ID- newly generated sub-resource
			 * id 3. Resource Name- sub-resource name as provided in the
			 * template 4. Failed- No 5. Geocode- None 6. Resource Comments-
			 * None 7. User ID- None 8. Full Name- None 9. Failed- No 10. User
			 * Comments- No user data provided
			 */
			/*
			 * The following details are displayed in red color. 1. Row- 2 2.
			 * Resource ID- parent resource id provided in the template 3.
			 * Resource Name- sub-resource name as provided in the template 4.
			 * Failed- Yes 5. Geocode- None 6. Resource Comments- duplicates
			 * sub-resource in row 1 7. User ID- None 8. Full Name- None 9.
			 * Failed- No 10. User Comments- No user data provided
			 * 
			 * 'Return' button is available.
			 */
			// 611850

			String[][] pStrArrUplData1 = {
					{ "1", "\\d+", strSubResource, "No", "", "", "", "", "No",
							"no user data provided" },
					{ "2", strRSValue[0], strSubResource, "Yes", "", "duplicates sub-resource in row 1", "", "", "No",
							"no user data provided" } };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload
						.verifyUplDetailsInUpldDetailsPge_MultipleData(
								selenium, pStrArrUplData1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the generated sub-resource id uploaded.
			 * Expected Result:'Edit Sub-Resource' screen is displayed with all
			 * the details intact as provided in the standard template while
			 * uploading.
			 */
			// 611851
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload
						.navToEditResourcePageFromSpecifiedResourceID(selenium,
								strSubResource, "1");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifySubResValuesRetInEditSubRes(
						selenium, strSubResource, strAbbrv, "",
						strSubResrctTypName, strStandResType, "", strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 * 
			 * Count for the sub-resource corresponding to the parent resource
			 * is incremented by 1.
			 */
			// 611852
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
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Sub-resource' link corresponding to the
			 * parent resource RS. Expected Result:'Sub-Resource List for <
			 * parent resource name >' is displayed.
			 * 
			 * Sub-resource uploaded is listed on the screen.
			 */
			// 611853
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifySubResource(selenium,
						strSubResource, true);
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
			gstrTCID = "FTS-108387";
			gstrTO = "Verify that duplicate sub-resources cannot be created under the parent resource.";
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

	//end//testFTS108387//

	//start//testFTS108510//
	/***************************************************************
	'Description	:Verify that users cannot be uploaded providing sub-resources id under 'Resource ID' column in the standard template
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/24/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS108510() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Upload objUpload=new Upload();
		CreateUsers objCreateUsers=new CreateUsers();
		Roles objRole=new Roles();
		try {
			gstrTCID = "108510"; // Test Case Id
			gstrTO = " Verify that users cannot be uploaded providing sub-resources id under 'Resource ID' column in the standard template";// Test
																																			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative
			// URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strDate=dts.getCurrentDate("MM/dd/yyyy");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;

			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";

			String strStatValue = "";
			String strSTvalue[] = new String[2];

			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
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
			String strUsrFulName = "Full" + System.currentTimeMillis();
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			String strState = "Alabama";
			String strCountry = "Barbour County";
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
			 * STEP : Action:Precondition:
			 * 1. Status type 'ST1' and 'ST2' are created and is associated to
			 * resource type 'RT' in region 'RG1'
			 * 2. Sub-Resource type 'SRT1' is created selecting status type 'ST2'
			 * 3. Resource 'RS' is created under 'RT'.
			 * 4. Sub-resource type 'SRT1' is noted.
			 * 5. Sub-resource id is provided in the template along with all the
			 * user upload details. Expected Result:No Expected Result
			 */
			// 612018

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
			
			// SRS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

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
				String[] strSTsvalue = { strSTvalue[0], strSTvalue[1] };
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTsvalue, true, strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Write data to template
			try {
				assertEquals("", strFuncResult);
				String[] strTestData = { strRSValue[1], "Y",strSubResource, strAbbrv,
						strRTValue[1], "", "101", "", "N", "N", "", "", "", "",
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
			 * STEP : Action:Login as RegAdmin and navigate to region 'RG1'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 612019
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
			// 612020
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
			// 612021
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
			 * STEP : Action:Select 'Browse' and add the sub-resource template
			 * 'X', deselect 'Test?' checkbox and click on 'Save' Expected
			 * Result:'Upload Details' screen is displayed.
			 * 
			 * Following fields are displayed with appropriate values and are
			 * disabled. 1. Spreadsheet: Attached sub-resource template path 2.
			 * Test?: checkbox unchecked 3. User: RegAdmin 4. Date: Current Date
			 * in the format MM/DD/YYYY
			 * 
			 * Details are displayed below the following column headers in red
			 * color:
			 * 
			 * 1. Row- 1 2. Resource ID- Sub-resource id provided in the
			 * template (not hyperlinked) 3. Resource Name- None (blank) 4.
			 * Failed- Yes 5. Geocode- None (Blank) 6. Resource Comments-
			 * Existing sub-resources cannot be used when uploading 7. User ID-
			 * as provided in the template 8. Full Name- as provided in the
			 * template 9. Failed- Yes 10. User Comments-invalid resource
			 * 
			 * 'Return' button is available.
			 */
			// 612029
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(selenium, strUplPath, strLoginUserName, strDate, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "1", strRSValue[1], "", "Yes", "", "Existing sub-resources cannot be used when uploading; user not allowed for sub-resources", strUserName, strUsrFulName, "Yes", "invalid resource" };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsInRedColor(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Users Expected Result:'User
			 * Lists' screen is displayed.
			 * 
			 * User is not upload.
			 */
			// 612032
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUsrInUserListPage(selenium, strUserName, false, false);
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
			gstrTCID = "FTS-108510";
			gstrTO = "Verify that users cannot be uploaded providing sub-resources id under 'Resource ID' column in the standard template";
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

	//end//testFTS108510//
	//start//testFTS108398//
	/***************************************************************
	'Description	:Verify that sub-resources can be uploaded providing all the data related to sub-resources in the standard template.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/25/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS108398() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Upload objUpload=new Upload();
		try {
			gstrTCID = "108398"; // Test Case Id
			gstrTO = " Verify that sub-resources can be uploaded providing all the data related to sub-resources in the standard template.";// Test
																																			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative
			// URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strDate=dts.getCurrentDate("MM/dd/yyyy");
			
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
			 * STEP : Action:Precondition:
			 * 1. Status type 'ST1' and 'ST2' are created and is associated to
			 * resource type 'RT' in region 'RG1'
			 * 2. Sub-Resource type 'SRT1' is created selecting status type 'ST2'
			 * 3. Resource 'RS' is created under 'RT'.
			 * 4. Resource ID for 'RS' is noted. 
			 * 5. Sub-resource type 'SRT1' is noted.
			 * 6. Data is provided in all the columns for the sub-resource
			 * upload in the standard sub-resource template. Expected Result:No
			 * Expected Result
			 */
			// 611884
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
			
			// Write data to template
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
			 * STEP : Action:Login as RegAdmin and navigate to region 'RG1'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 611885
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
			// 611886
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
			// 611887
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
			 * STEP : Action:Select 'Browse' and add the sub-resource template
			 * 'X', deselect 'Test?' checkbox and click on 'Save' Expected
			 * Result:'Upload Details' screen is displayed.
			 * 
			 * Following fields are displayed with appropriate values and are
			 * disabled. 1. Spreadsheet: Attached sub-resource template path 2.
			 * Test?: checkbox unchecked 3. User: RegAdmin 4. Date: Current Date
			 * in the format MM/DD/YYYY
			 * 
			 * Appropriate values are present under the following column
			 * headers: 1. Row- 1 2. Resource ID- sub resource id uploaded under
			 * parent resource 3. Resource Name- sub-resource name as provided
			 * in the template 4. Failed- No 5. Geocode- (Numeric value) 6.
			 * Resource Comments- None 7. User ID- None 8. Full Name- None 9.
			 * Failed- No 10. User Comments- No user data provided
			 * 
			 * 'Return' button is available.
			 */
			// 611888
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(selenium, strUplPath, strLoginUserName, strDate, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "1", "\\d+", strSubResource, "No", "", "", "", "", "No",
					"no user data provided"  };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the generated sub-resource id uploaded.
			 * Expected Result:'Edit Sub-Resource' screen is displayed with all
			 * the details intact as provided in the standard template while
			 * uploading.
			 */
			// 611908
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToEditResourcePageFromResourceID(selenium, strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifySubResValuesRetInEditSubRes(
						selenium, strSubResource, strAbbrv, "",
						strSubResrctTypName, strStandResType, "", strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 * 
			 * Count for the sub-resource corresponding to the parent resource
			 * is incremented by 1.
			 */
			// 611889
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
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Sub-resource' link corresponding to the
			 * parent resource RS. Expected Result:'Sub-Resource List for <
			 * parent resource name >' is displayed.
			 * 
			 * Sub-resource uploaded is listed on the screen.
			 */
			// 611906
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifySubResource(selenium,
						strSubResource, true);
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
			gstrTCID = "FTS-108398";
			gstrTO = "Verify that sub-resources can be uploaded providing all the data related to sub-resources in the standard template.";
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

	//end//testFTS108398//

	//start//testFTS108385//
	/***************************************************************
	'Description	:Verify that sub-resources are not uploaded under the provided Resource ID for a resource when 'N' is provided under 'Sub-resource' column of the standard template.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/25/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS108385() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Upload objUpload=new Upload();
		try {
			gstrTCID = "108385"; // Test Case Id
			gstrTO = " Verify that sub-resources are not uploaded under the provided Resource ID for a resource when 'N' is provided under 'Sub-resource' column of the standard template.";// Test
																																															// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative
			// URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strDate=dts.getCurrentDate("MM/dd/yyyy");
			
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
			 * STEP : Action:Precondition:
			 * 1. Status type 'ST1' and 'ST2' are created and is associated to
			 * resource type 'RT' in region 'RG1'
			 * 2. Sub-Resource type 'SRT1' is created selecting status type'ST2'
			 * 3. Resource 'RS' is created under 'RT'.
			 * 4. Resource ID for 'RS' is noted.
			 * 5. Sub-resource type 'SRT1' is noted.
			 * 6. Following data is provided in the standard template 'RG1':
			 * a. ResourceID (Resource ID of the parent resource noted) b.
			 * Sub-resource (provide as 'N') c. Resource Name (provide
			 * Sub-resource name) d. Abbrev (provide Abbrev) e. ResourceTypeID
			 * (provide Sub-resource type ID noted) f. Standard Resource Type ID
			 * (selected standard sub-resource type ID) g. HAvBED (N for the
			 * field) h. Shared (N for the field) i. ContactFirstName j.
			 * ContactLastNAme Expected Result:No Expected Result
			 */
			// 611817
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
			
			// Write data to template
			try {
				assertEquals("", strFuncResult);
				String[] strTestData = { strRSValue[0], "N",strSubResource, strAbbrv,
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
			 * STEP : Action:Login as RegAdmin and navigate to region 'RG1'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 611818
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
			// 611819
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
			// 611823
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
			 * STEP : Action:Select 'Browse' and add the sub-resource template
			 * 'X', deselect 'Test?' checkbox and click on 'Save' Expected
			 * Result:'Upload Details' screen is displayed.
			 * 
			 * Following fields are displayed with appropriate values and are
			 * disabled. 1. Spreadsheet: Attached sub-resource template path 2.
			 * Test?: checkbox unchecked 3. User: RegAdmin 4. Date: Current Date
			 * in the format MM/DD/YYYY
			 * 
			 * Appropriate values are present under the following column
			 * headers: 1. Row- 1 2. Resource ID- Parent resource id 3. Resource
			 * Name- sub-resource name as provided in the template 4. Failed- No
			 * 5. Geocode- None 6. Resource Comments- existing resource 7. User
			 * ID- None 8. Full Name- None 9. Failed- No 10. User Comments- No
			 * user data provided
			 * 
			 * 'Return' button is available.
			 */
			// 611825
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(selenium, strUplPath, strLoginUserName, strDate, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
					"Failed", "Geocode", "Resource Comments", "User ID",
					"Full Name", "Failed", "User Comments" };
			String[] pStrArrUplData = { "1", strRSValue[0], strSubResource, "No", "", "existing resource", "", "", "No",
					"no user data provided"  };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 * 
			 * Count for the sub-resource corresponding to the parent resource
			 * is not incremented.
			 */
			// 611827
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSubRSCount = "Sub-Resources (1)";
				strFuncResult = objResources.VerifySubResourceCountInRSListFalse(
						selenium, strSubRSCount, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP : Action:Click on 'Sub-resource' link corresponding to the
			 * parent resource RS. Expected Result:'Sub-Resource List for <
			 * parent resource name >' is displayed.
			 * 
			 * Sub-resource uploaded is not listed on the screen.
			 */
			// 611828
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
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
			gstrTCID = "FTS-108385";
			gstrTO = "Verify that sub-resources are not uploaded under the provided Resource ID for a resource when 'N' is provided under 'Sub-resource' column of the standard template.";
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

	//end//testFTS108385//
	
	//start//testFTS108391//
	/***************************************************************
	'Description		:Verify that user can upload a maximum of 1000 sub-resources.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/26/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS108391() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Upload objUpload=new Upload();
		try {
			gstrTCID = "108391"; // Test Case Id
			gstrTO = " Verify that user can upload a maximum of 1000 sub-resources.";// Test
																						// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative
			// URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strDate = dts.getCurrentDate("MM/dd/yyyy");

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
			String strUplPath = pathProps.getProperty("Upload1000ResFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Precondition:
			 * 1. Status type 'ST1' and 'ST2' are created and is associated to
			 * resource type 'RT' in region 'RG1'
			 * 2. Sub-Resource type 'SRT1' is created selecting status type'ST2'
			 * 3. Resource 'RS' is created under 'RT'.
			 * 4. Resource ID for 'RS' is noted.
			 * 5. Sub-resource type 'SRT1' is noted.
			 * 6. Following data is provided in the standard template in region
			 * 'RG1' for 1000 sub-resources:
			 * a. ResourceID (Resource ID of the parent resource noted) b.
			 * Sub-resource (provide as 'Y') c. Resource Name (provide
			 * Sub-resource name) d. Abbrev (provide Abbrev) e. ResourceTypeID
			 * (provide Sub-resource type ID noted) f. Standard Resource Type ID
			 * (selected standard sub-resource type ID) g. HAvBED (N for the
			 * field) h. Shared (N for the field) i. ContactFirstName j.
			 * ContactLastNAme Expected Result:No Expected Result
			 */
			// 611854
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
			
			// Write data to template
			log4j.info("Writing Subresource names to standard template");
			for(int intCnt=1 ; intCnt<=1000;intCnt++){
				try {
					assertEquals("", strFuncResult);
					String[] strTestData = { strRSValue[0], "Y",intCnt+strSubResource, strAbbrv,
							strRTValue[1], "", "101", "", "N", "N", "", "", "", "",
							"", "", "", strContFName, strContLName, "", "", "", "",
							"", "", "", "", "",
							"", "", "", "", "", "", "", "", "", "", "",
							"" };
					
					objOFC.writeResultDataToParticularRow(strTestData, strUplPath,
							"Resource", intCnt);
				} catch (Exception Ae) {
					strFuncResult = Ae.toString();
					gstrReason = strFuncResult;
				}
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
			 * STEP : Action:Login as RegAdmin and navigate to region 'RG1'
			 * Expected Result:'Region Default' screen is displayed.
			 */
			// 611872
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
			// 611873
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
			// 611874
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
			 * STEP : Action:Select 'Browse' and add the sub-resource template
			 * 'X', deselect 'Test?' checkbox and click on 'Save' Expected
			 * Result:'Upload Details' screen is displayed.
			 * 
			 * Following fields are displayed with appropriate values and are
			 * disabled. 1. Spreadsheet: Attached sub-resource template path 2.
			 * Test?: checkbox unchecked 3. User: RegAdmin 4. Date: Current Date
			 * in the format MM/DD/YYYY
			 * 
			 * Appropriate values are present under the following column
			 * headers: 1. Row- 1 to 1000 2. Resource ID- sub resource id
			 * uploaded under parent resource 3. Resource Name- sub-resource
			 * name as provided in the template 4. Failed- No 5. Geocode- None
			 * 6. Resource Comments- None 7. User ID- None 8. Full Name- None 9.
			 * Failed- No 10. User Comments- No user data provided
			 * 
			 * 'Return' button is available.
			 */
			// 611876
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUplPath, strAutoFileName, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyFieldsPresentInUpldDetails(selenium, strUplPath, strLoginUserName, strDate, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				int intRowCount=selenium.getXpathCount("//div[@id='mainContainer']/form/table[2]/tbody/tr").intValue();
				assertEquals(1000,intRowCount);
				log4j.info("1 to 1000 rows of 1000 subresources are displayed");
			} catch (AssertionError Ae) {
				strFuncResult="1 to 1000 rows of 1000 subresources are NOT displayed";
				gstrReason = strFuncResult;
			}
			try{
				String[] strColHeaders = { "Row", "Resource ID", "Resource Name",
						"Failed", "Geocode", "Resource Comments", "User ID",
						"Full Name", "Failed", "User Comments" };
				String[] pStrArrUplData = { "1", "\\d+", "1"+strSubResource, "No", "", "", "", "", "No",
				"no user data provided"  };
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP : Action:Navigate to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed.
			 * 
			 * Count for the sub-resource corresponding to the parent resource
			 * is incremented by 1000.
			 */
			// 611877
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSubRSCount = "Sub-Resources (1000)";
				strFuncResult = objResources.VerifySubResourceCountInRSList(
						selenium, strSubRSCount, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Sub-resource' link corresponding to the
			 * parent resource RS. Expected Result:'Sub-Resource List for <
			 * parent resource name >' is displayed.
			 * 
			 * 1000 Sub-resources uploaded are listed on the screen.
			 */
			// 611878
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifySubResource(selenium,
						"1"+strSubResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				int intRowCount=selenium.getXpathCount("//div[@id='mainContainer']/table[2]/tbody/tr").intValue();
				assertEquals(1000,intRowCount);
				log4j.info("1000 Sub-resources uploaded are listed on the screen");
			} catch (AssertionError Ae) {
				strFuncResult="1000 Sub-resources uploaded are NOT listed on the screen";
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
			gstrTCID = "FTS-108391";
			gstrTO = "Verify that user can upload a maximum of 1000 sub-resources.";
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
	//end//testFTS108391//
	
}
