package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.Logger;

public class Smoke_ImportResources {
	Date gdtStartDate;
	ReadData rdExcel;
	public static final File PHANTOMJS_EXE = new File(
			System.getProperty("user.dir"),
			"phantomjs-1.9.2-windows/phantomjs.exe");
	
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.Smoke_ImportResources");
	static {
		System.setProperty("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.SimpleLog");
		System.setProperty(
				"org.apache.commons.logging.simplelog.log.org.apache.http",
				"warn");
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

	Selenium selenium;
	WebDriver driver;

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

	/*	DesiredCapabilities ieCapabilities = DesiredCapabilities
				.internetExplorer();
		ieCapabilities
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
		driver = new InternetExplorerDriver(ieCapabilities);
		selenium = new WebDriverBackedSelenium(driver,
				propEnvDetails.getProperty("urlEU"));*/
		
		DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true); 
        caps.setCapability( PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,PHANTOMJS_EXE.getAbsolutePath());
        driver = new PhantomJSDriver(caps);
        selenium = new WebDriverBackedSelenium(driver,
				propEnvDetails.getProperty("urlEU"));
    	objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try {
			selenium.close();
		} catch (Exception e) {

		}
		selenium.stop();

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
		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}

	/***************************************************************
	 * 'Description :Verify that user can import sub resource providing
	 * mandatory data. 'Precondition : 'Arguments :None 'Returns :None 'Date
	 * :13-March-2013 'Author :QSG
	 * '---------------------------------------------------------------
	 * 'Modified Date Modified By 'Date Name
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
			String strUplPath = pathProps.getProperty("ImportDataExcelPath");
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
			String strResVal = "";
			String strRSValue[] = new String[1];
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
				strFuncResult = objRT.navResourceTypList(selenium);

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
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strSubResrctTypName1);
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

			// RT
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
						strAbbrv, strRTValue[0], "", "101", "", "N", "N", "",
						"", "", "", "", "", "", strContFName, strContLName, "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "", "", "", "" };

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
				strDate = dts.getTimeOfParticularTimeZone("CST", "MM/dd/yyyy");

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

			String strExcelName = "";
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
			/*
			 * 7 Click on 'Return', click on 'Upload Resource', select the
			 * standard template, deselect 'Test' check box and click on 'Save'.
			 * 'Upload Details' screen is displayed.
			 * 
			 * Following fields are displayed with appropriate value and are
			 * disabled. 1. Spreadsheet: File path 2. Test?: Check box is
			 * deselected 3. User: RegAdmin 4. Date: Current Date
			 * 
			 * Appropriate values are present under the following column
			 * headers: 1. Row- 1 2. Resource ID- auto generate id for sub
			 * resource uploaded and is hyperlinked 3. Resource Name- As
			 * provided in the template 4. Failed- No 5. Geocode- None 6.
			 * Resource Comments- None 7. User ID- None 8. Full Name- None 9.
			 * Failed- No 10. User Comments- No user data provided
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
				strDate = dts.getTimeOfParticularTimeZone("CST", "MM/dd/yyyy");

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

			strExcelName = "";
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

			strColHeaders = new String[] { "Row", "Resource ID",
					"Resource Name", "Failed", "Geocode", "Resource Comments",
					"User ID", "Full Name", "Failed", "User Comments" };
			pStrArrUplData = new String[] { "1", "\\d+", strSubResource, "No",
					"", "", "", "", "No", "no user data provided" };
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
				strFuncResult = objRs.navToEditSubResourcePageFrmUplDetails(
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
				strFuncResult = objRs.navToEditSubResourcePage(selenium,
						strResource1);
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
					log4j.info("Sub-resource uploaded is listed and the details are"
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-108933";
			gstrTO = "Verify that user can import sub resource providing mandatory data.";
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
