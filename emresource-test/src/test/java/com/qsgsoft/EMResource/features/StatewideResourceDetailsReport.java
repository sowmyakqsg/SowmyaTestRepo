package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.io.File;
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
' Description         :This class includes  test cases related to
' Requirement Group   :Reports
' Requirement         :Statewide Resource Details Report
' Date		          :30-oct-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/
public class StatewideResourceDetailsReport {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSAddADocument");
	static {
		BasicConfigurator.configure();
	}
	String gstrTCID, gstrTO, gstrResult, gstrReason;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild,
			gstrBrowserName, strSessionId;
	double gdbTimeTaken;
	public static long gslsysDateTime;
	OfficeCommonFunctions objOFC;
	public Properties propEnvDetails, propAutoItDetails;
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

		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		selenium.start();
		selenium.windowMaximize();
		selenium.setTimeout("");

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		// kill browser
		try {
			selenium.close();
		} catch (Exception e) {

		}

		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
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
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}

/******************************************************************************
'Description	:Verify that RegAdmin cannot generate statewide resource detail 
                 report in any other region apart from 'Statewide Florida'.
'Arguments		:None
'Returns		:None
'Date			:10/30/2012
'Author			:QSG
'------------------------------------------------------------------------------
'Modified Date				                                      Modified By
'Date					                                          Name
*******************************************************************************/

	@Test
	public void testBQS99104() throws Exception {
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Reports objReports = new Reports();
		General objGeneral = new General();
		try {
			gstrTCID = "99104"; // Test Case Id
			gstrTO = " Verify that RegAdmin cannot generate statewide resource "
					+ "detail report in any other region apart from 'Statewide Florida'.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
		/*
		 * STEP : Action:Login as RegAdmin to region RG1. Expected
		 * Result:'Region Default' screen is displayed.
		 */
		// 584294
			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate to Reports >> Resource reports Expected
		 * Result:'Resource Reports Menu' screen is displayed 'Resource
		 * Details' link is displayed. 'Statewide Resource details' link is
		 * NOT available.
		 */
		// 584295
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "link=Resource Details";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				log4j.info("'Resource Details' link is displayed. ");
			} catch (AssertionError Ae) {
				log4j.info("'Resource Details' link is NOT displayed. ");
				strFuncResult = "'Resource Details' link is NOT displayed. ";
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertFalse(selenium
							.isElementPresent("link=Statewide Resource details"));
					log4j.info("'Statewide Resource details' link is NOT available. ");
					gstrResult = "PASS";
				} catch (AssertionError Ae) {
					log4j.info("'Statewide Resource details' link is available. ");
					strFuncResult = "'Statewide Resource details' link is displayed. ";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-99104";
			gstrTO = "Verify that RegAdmin cannot generate statewide resource detail report in any other region apart from 'Statewide Florida'.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
/********************************************************************************
'Description	:Verify that 'Statewide Resource Details' report can be generated
                 by RegAdmin in 'Statewide Florida' for all the resources for the 
                 selected criteria while generating report.
'Arguments		:None
'Returns		:None
'Date			:10/31/2012
'Author			:QSG
'--------------------------------------------------------------------------------
'Modified Date				                                          Modified By
'Date					                                              Name
**********************************************************************************/

	@Test
	public void testBQS99028() throws Exception {
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Reports objRep = new Reports();

		try {
			gstrTCID = "99028"; // Test Case Id
			gstrTO = " Verify that 'Statewide Resource Details' report can "
					+ "be generated by RegAdmin in 'Statewide Florida' for all "
					+ "the resources for the selected criteria while generating report.";

			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MMddyyyyhhmmss");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);

			String strRegn1 = rdExcel.readData("Login", 22, 4);
			String strRegn2 = rdExcel.readData("Login", 26, 4);

			String strStatType1 = "AutoSt1_" + strTimeText;
			String strStatType2 = "AutoSt2_" + strTimeText;
			String strNumStatTypeValue = "Number";
			String strTxtStatTypDefn = "Auto";
			String[] strST = new String[2];

			String strResrcTypName1 = "AutoRt1_" + strTimeText;
			String strResrcTypName2 = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[2];

			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strRSValue[] = new String[2];
			String strStandResType = "Hospital";
			String strContFName = "FN";
			String strContLName = "LN";
			// Report
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1" + gstrTCID + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1" + gstrTCID + strTimeText + ".xls";

			String strCSVFileNameRenamed = "StatewideResDetRep1" + gstrTCID
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			/*
			 * STEP : Action:Precondition: All the below mentioned test data is
			 * created in 'Statewide Florida' region. 1. Number status type ST1
			 * is created with a standard status type 'Average wait time'. 2.
			 * Resource type RT1 is created selecting status type ST1. 3.
			 * Resource RS1 is created under resource type RT1 selecting
			 * standard resource type 'Hospital'. All the below mentioned test
			 * data is created in 'Central Florida' region. 1. Number status
			 * type ST2 is created with a standard status type 'Average wait
			 * time'. 2. Resource type RT2 is created selecting status type ST2.
			 * 3. Resource RS2 is created under resource type RT2 selecting
			 * standard resource type 'Hospital'. Expected Result:No Expected
			 * Result
			 */
			// 584154
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
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
						strNumStatTypeValue, strStatType1, strTxtStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStandardST = "Average Wait Time";
				strFuncResult = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition, strStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[0] = strStatValue;
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
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(seleniumPrecondition,
						strResrcTypName1, strST[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName1);

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
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition,
						strResource1, "ab", strResrcTypName1, strStandResType,
						strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
						strResource1, "No", "", "ab", strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Region 2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNumStatTypeValue, strStatType2, strTxtStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStandardST = "Average Wait Time";
				strFuncResult = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition, strStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(seleniumPrecondition,
						strResrcTypName2, strST[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[1] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName2);

				if (strRsTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition,
						strResource2, "ab", strResrcTypName2, strStandResType,
						strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
						strResource2, "No", "", "ab", strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");

			/*
			 * STEP : Action:Login as RegAdmin to 'Statewide Florida' region
			 * Expected Result:'Region Default' screen is displayed
			 */
			// 584084
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Reports >> Resource reports Expected
			 * Result:'Resource Reports Menu' screen is displayed*/
			 
			// 584086
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Statewide resource details report' link
			 * Expected Result:Resources created across all the Florida regions
			 * under the resource type with standard resource types that are
			 * active in a region are listed under 'Standard Resources' on
			 * 'Statewide Resource Detail Report (Step 1 of 2) 'Hospital' is
			 * listed on the page.*/
			 
			// 584124
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.navToStatewideResourceDetailReportPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			 /* STEP : Action:Select the check box corresponding to 'Hospital'
			 * under 'Standard Resources' and click on 'Next' button. Expected
			 * Result:'Statewide Resource Detail Report (Step 2 of 2)' page is
			 * displayed with 'Standard statuses' listed. 'Average Wait time' is
			 * listed on the page.*/
			 
			// 584155
			try {
				assertEquals("", strFuncResult);
				String strStdResTypeVal = "106";
				String strStdRt = "Hospital";
				strFuncResult = objRep.selectStdResTypeForReport(selenium,
						strStdResTypeVal, strStdRt);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/* * STEP : Action:Select the check box corresponding to 'Average wait
			 * time' under 'Standard statuses' and click on 'Generate Report'
			 * button. Expected Result:'File Download' pop up window is
			 * displayed with 'Name', 'Type' and 'From' details. 'Open', 'Save'
			 * and 'Cancel' buttons are available.
			 */
			// 584160
			try {
				assertEquals("", strFuncResult);
				String strStdStatusVal = "4004";
				String strStdStatusName = "Average Wait Time";
				strFuncResult = objRep.selectStdStatusesForReport(selenium,
						strStdStatusVal, strStdStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt < 300);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1=0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath)
									.exists());
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCount1++;
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);

			
			
			/*
			 * STEP : Action:Click on 'Open' button Expected Result:'Statewide
			 * Resource Detail Report' is generated in 'Excel' (xls format)
			 * displaying appropriate data in the following columns: 1. Region
			 * 2. Resource name 3. Type (Resource Type) 4. Address 5. County 6.
			 * Latitude 7. Longitude 8. EMResource ID 9. AHA ID 10. External ID
			 * 11. Website 12. Contact 13. Phone 1 14. Phone 2 15. Fax 16. Email
			 * 17. Notes 18. Standard status type name (Average Wait time)
			 * Resources created under the standard resource type (Hospital) and
			 * status types associated with standard status type (Average wait
			 * time) which are active across all the Florida regions are listed
			 * in the generated report. Resource 'RS1' details are displayed
			 * appropriately. 'Region' is displayed as 'Statewide Florida'
			 * Resource 'RS2' details are displayed appropriately. 'Region' is
			 * displayed as 'Central Florida'
			 */

			try {
				assertEquals("", strFuncResult);

				String[] strReportData = { "Statewide Resource Detail Report",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String[] strReportData = { "Region", "Resource Name", "Type",
						"Address", "County", "Latitude", "Longitude",
						"EMResource ID", "AHA ID", "External ID", "Website",
						"Contact", "Phone 1", "Phone 2", "Fax", "Email",
						"Notes", "Average Wait Time" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResData[] = { strRegn1, strResource1,
						strResrcTypName1, "", "", "", "", "\\d+", "", "", "",
						strContFName + " " + strContLName, "", "", "", "", "",
						"0" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strResData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResData[] = { strRegn2, strResource2,
						strResrcTypName2, "", "", "", "", "\\d+", "", "", "",
						strContFName + " " + strContLName, "", "", "", "", "",
						"0" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strResData, strCSVDownlRenamedPath);
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
			gstrTCID = "BQS-99028";
			gstrTO = "Verify that 'Statewide Resource Details' report can be generated"
					+ " by RegAdmin in 'Statewide Florida' for all the resources for the "
					+ "selected criteria while generating report.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

/********************************************************************************
'Description	:Verify that the resource information edited is displayed appropriately in the generated report

'Precondition	:All the below mentioned test data is created in 'Statewide Florida' region. 
		1. Number status type ST1 is created with a standard status type 'Average wait time'. 
		2. Resource type RT1 is created selecting status type ST1. 
		3. Resource RS1 is created under resource type RT1 selecting standard resource type 'Hospital'. 
		
		All the below mentioned test data is created in 'Central Florida' region. 
		1. Number status type ST2 is created with a standard status type 'Average wait time'. 
		2. Resource type RT2 is created selecting status type ST2. 
		3. Resource RS2 is created under resource type RT2 selecting standard resource type 'Hospital'. 
'Arguments		:None
'Returns		:None
'Date	 		:31-10-2012
'Author			:QSG
'-------------------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
**********************************************************************************/

	@Test
	public void testBQS99092() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login

		Reports objRep = new Reports();
		try {
			gstrTCID = "BQS-99092";
			gstrTO = "Verify that the resource information edited is displayed"
					+ " appropriately in the generated report";

			gstrResult = "FAIL";
			gstrReason = "";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTmText = dts.getCurrentDate("HHmm");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			ReadData objReadData = new ReadData();
			StatusTypes objST = new StatusTypes();

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".xls";

			String strCSVFileNameRenamed = "StatewideResDetRep1_" + gstrTCID
					+ "_" + strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;

			String strNSTValue = "Number";

			String strStatType1 = "Aa1_" + strTimeText;
			String strStatType2 = "Aa2_" + strTimeText;

			String strRegn1 = rdExcel.readData("Login", 22, 4);
			String strRegn2 = rdExcel.readData("Login", 26, 4);

			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword = objReadData.readData("Login", 3, 2);

			String strStatTypDefn = "Auto";
			String strFailMsg = "";
			String strRSTvalue[] = new String[2];

			String strStatValue = "";

			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strResrctTypName2 = "AutoRt2_" + strTimeText;

			String strRTValue[] = new String[2];
			String strRSValue[] = new String[2];
			String strAbbrv = "A" + strTmText;

			String strResVal = "";

			String strStandResType = "Hospital";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strStandardST = "Average Wait Time";
			String strEdAbbrv = "C" + strTmText;
			String strEdContFName = "autoF";
			String strEdContLName = "qsgc";

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strStatType1, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(seleniumPrecondition, strStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ strRSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName1);
				if (strFailMsg.compareTo("") != 0) {

					strRTValue[0] = strFailMsg;
					strFailMsg = "";
				} else {
					strFailMsg = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objRs.createResourceWithMandFields(seleniumPrecondition,
						strResource1, strAbbrv, strResrctTypName1,
						strStandResType, strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs
						.saveAndNavToAssignUsr(seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.saveAndVerifyResource(seleniumPrecondition,
						strResource1, "No", "", strAbbrv, strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource1);

				if (strResVal.compareTo("") != 0) {
					strFailMsg = "";
					strRSValue[0] = strResVal;
				} else {
					strFailMsg = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strStatType2, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(seleniumPrecondition, strStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName2,
						"css=input[name='statusTypeID'][value='"
								+ strRSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName2);
				if (strFailMsg.compareTo("") != 0) {

					strRTValue[1] = strFailMsg;
					strFailMsg = "";
				} else {
					strFailMsg = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objRs.createResourceWithMandFields(seleniumPrecondition,
						strResource2, strAbbrv, strResrctTypName2,
						strStandResType, strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs
						.saveAndNavToAssignUsr(seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.saveAndVerifyResource(seleniumPrecondition,
						strResource2, "No", "", strAbbrv, strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource2);

				if (strResVal.compareTo("") != 0) {
					strFailMsg = "";
					strRSValue[1] = strResVal;
				} else {
					strFailMsg = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs
						.navToEditResourcePage(selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objRs.createResourceWithMandFields(selenium,
						strResource2, strEdAbbrv, strResrctTypName2,
						strStandResType, strEdContFName, strEdContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.saveAndVerifyResource(selenium,
						strResource2, "No", "", strEdAbbrv, strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRep.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRep
						.navToStatewideResourceDetailReportPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRep.selectStdResTypeForReport(selenium, "106",
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRep.selectStdStatusesForReport(selenium,
						"4004", strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt < 300);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			int intCount = 0;
			int intCount1=0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath)
									.exists());
							break;
						} catch (AssertionError Ae) {
							intCount1++;
							Thread.sleep(1000);
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);

			
			try {
				assertEquals("", strFuncResult);

				String[] strReportData = { "Statewide Resource Detail Report",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String[] strReportData = { "Region", "Resource Name", "Type",
						"Address", "County", "Latitude", "Longitude",
						"EMResource ID", "AHA ID", "External ID", "Website",
						"Contact", "Phone 1", "Phone 2", "Fax", "Email",
						"Notes", "Average Wait Time" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResData[] = { strRegn1, strResource1,
						strResrctTypName1, "", "", "", "", "\\d+", "", "", "",
						strContFName + " " + strContLName, "", "", "", "", "",
						"0" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strResData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResData[] = { strRegn2, strResource2,
						strResrctTypName2, "", "", "", "", "\\d+", "", "", "",
						strEdContFName + " " + strEdContLName, "", "", "", "",
						"", "0" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strResData, strCSVDownlRenamedPath);
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
			gstrTCID = "BQS-99092";
			gstrTO = "Verify that the resource information edited is displayed"
					+ " appropriately in the generated report";

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
'Description	:Verify that for a number status type created with same standard 
'				statuses gets added up in the report generated.
'Precondition	:All the below mentioned test data is created in 'Statewide Florida' region.
				1. Number status types NST1, NST2 are created with a standard status type 'Average Wait time'.
				2. Resource type RT1 is created selecting status types NST1 and NST2.
				3. Resource RS1 is created under resource type RT1 selecting standard resource type as 'Ambulance'.
				4. User U1 is created in 'Statewide Florida' region, providing the following rights:
				a) Update right on resource 'RS1.
				b) Role to update the status types NST1, NST2 and selecting 'Report-Statewide resource details' right for a role.
				c) User is provided access to 'Central FL' region.
				
				All the below mentioned test data is created in 'Central Florida' region.
				1. Number status types NST3 is created with a standard status type 'Average Wait time'.
				2. Resource type RT2 is created selecting status types NST3.
				3. Resource RS2 is created under resource type RT2 selecting standard status type as 'Hospital'. . 
'Arguments		:None
'Returns		:None
'Date	 		:1-Nov-2012
'Author			:QSG
'-------------------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
**********************************************************************************/

	@Test
	public void testBQS100381() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		ReadData objReadData = new ReadData();
		StatusTypes objST = new StatusTypes();
		Reports objRep = new Reports();
		Roles objRoles=new Roles();
		CreateUsers objCreateUsers=new CreateUsers();
		Regions objRegions=new Regions();
		ViewMap objViewMap=new ViewMap();
		General objGeneral=new General();
		
		try {
			gstrTCID = "BQS-100381";
			gstrTO = "Verify that for a number status type created with "
					+ "same standard statuses gets added up in the report generated.";

			gstrResult = "FAIL";
			gstrReason = "";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTmText = dts.getCurrentDate("HHmm");
			String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".xls";

			String strCSVFileNameRenamed = "StatewideResDetRep1_" + gstrTCID
					+ "_" + strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword = objReadData.readData("Login", 3, 2);
			
			String strRegnStateWideFlorida = rdExcel.readData("Login", 22, 4);
			String strRegnCentralFloria = rdExcel.readData("Login", 26, 4);
		
			String strStateWideStatType1 = "Aa1_" + strTimeText;
			String strStateWideStatType2 = "Aa2_" + strTimeText;
			String strStateWideSTvalue[] = new String[2];
		
			
			String strCentarlStatType1 = "Aa3_" + strTimeText;
			String strCentralSTvalue[] = new String[1];
			
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strStatValue = "";
			
			String strRolesNameStateWide = "RoleStatWid" + strTimeText;
			String strRoleValueStateWide = "";
			

			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strResrctTypName2 = "AutoRt2_" + strTimeText;
			String strRTValue[] = new String[2];
			
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strRSValue[] = new String[2];
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Ambulance";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strStandardST = "Average Wait Time";
			String strLongitude="";
			String strLatitude= "";

			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * All the below mentioned test data is created in 'Statewide
			 * Florida' region.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegnStateWideFlorida);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Number status types NST1, NST2 are created with a standard
			 * status type 'Average Wait time'.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//ST1
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strStateWideStatType1, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition, strStateWideStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strStateWideStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStateWideSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//ST2
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strStateWideStatType2, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition, strStateWideStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strStateWideStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStateWideSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Role
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
						strRolesNameStateWide);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strStateWideSTvalue[0], "true" },
						{ strStateWideSTvalue[1], "true" } };
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(seleniumPrecondition, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StateWideResourceDetailReport");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesNameStateWide);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValueStateWide = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesNameStateWide);

				if (strRoleValueStateWide.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT1 is created selecting status types NST1 and
			 * NST2.
			 */
			
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
								+ strStateWideSTvalue[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
								+ strStateWideSTvalue[1] + "']");
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
				strRTValue[0] = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName1);
				if (strRTValue[0].compareTo("") != 0) {

					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3. Resource RS1 is created under resource type RT1 selecting
			 * standard resource type as 'Ambulance'.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition,
						strResource1, strAbbrv, strResrctTypName1,
						strStandResType, strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strState = "Alabama";
				String strCountry = "Barbour County";

				strFuncResult = objRs.provideLookUpAddressToResource(seleniumPrecondition,
						strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.saveAndNavToAssignUsr(seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
						strResource1, "No", "", strAbbrv, strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource1);

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
				
				strFuncResult = objRs.navToEditResourcePage(seleniumPrecondition, strResource1);
				strLongitude=seleniumPrecondition.getValue("id=longitude");
				log4j.info(strLongitude);
				
				char ch='.';
				
				int intpos=strLongitude.indexOf(ch);
				
				String strLongitudeBefore=strLongitude.substring(0,intpos );
				strLongitude=strLongitude.substring(intpos,intpos+4 );
				
				strLongitude=strLongitudeBefore+strLongitude;
				log4j.info(strLongitude);
				
				strLatitude= seleniumPrecondition.getValue("id=latitude");
				
				intpos=strLatitude.indexOf(ch);
				String strLatitudeBefore=strLatitude.substring(0,intpos );
				strLatitude=strLatitude.substring(intpos,intpos+4 );
			
				strLatitude=strLatitudeBefore+strLatitude;
				log4j.info(strLatitude);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 4. User U1 is created in 'Statewide Florida' region, providing
			 * the following rights: a) Update right on resource 'RS1. b) Role
			 * to update the status types NST1, NST2 and selecting
			 * 'Report-Statewide resource details' right for a role. c) User is
			 * provided access to 'Central FL' region.
			 */
							
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

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
						strRoleValueStateWide, true);

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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * c) User is provided access to 'Central FL' region.
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strRegionValue = { rdExcel.readData("Login", 5, 10) };
				strFuncResult = objRegions.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName_1, strRegionValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*All the below mentioned test data is created in 'Central Florida' region. 
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
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegnCentralFloria);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 1. Number status types NST3 is created with a standard status
			 * type 'Average Wait time'.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strCentarlStatType1, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition, strCentarlStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strCentarlStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strCentralSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2. Resource type RT2 is created selecting status types NST3.
			 */
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName2,
						"css=input[name='statusTypeID'][value='"
								+ strCentralSTvalue[0] + "']");
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
				strRTValue[1] = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName2);
				if (strRTValue[1].compareTo("") != 0) {

					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3. Resource RS2 is created under resource type RT2 selecting
			 * standard status type as 'Hospital'.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition,
						strResource2, strAbbrv, strResrctTypName2,
						"Hospital", strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.saveAndNavToAssignUsr(seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
						strResource2, "No", "", strAbbrv, strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource2);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * 2 Login as user U1 to 'Statewide Florida' region and navigate to
			 * View >> Map screen 'Regional Map View' screen is displayed.
			 */

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
				strFuncResult = objLogin.navUserDefaultRgn(selenium,
						strRegnStateWideFlorida);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatTypeValue = { strStateWideSTvalue[0] };
				strFuncResult = objViewMap.updateStatusType(selenium,
						strResource1, strRoleStatTypeValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatTypeValue = { strStateWideSTvalue[1] };
				strFuncResult = objViewMap.updateStatusType(selenium,
						strResource1, strRoleStatTypeValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 4 Navigate to Reports >> Resource reports 'Resource Reports Menu'
		 * screen is displayed
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
		/*
		 * 5 Click on 'Statewide resource details report' link Resources
		 * created across all the Florida regions under the resource type
		 * with standard resource types that are active in a region are
		 * listed under 'Standard Resources' on 'Statewide Resource Detail
		 * Report (Step 1 of 2) page.
		 * 
		 * 'Ambulance' and 'Hospital' are listed on the page.
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.navToStatewideResourceDetailReportPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStdResTypeVal = { "102", "106" };
				String[] strStdRt = { "Ambulance", "Hospital" };
				strFuncResult = objRep.selectStdResTypeManyForReport(selenium,
						strStdResTypeVal, strStdRt, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 6 Select the check boxes corresponding to 'Ambulance' and
		 * 'Hospital' under 'Standard Resources' and click on 'Next' button.
		 * 'Statewide Resource Detail Report (step 2 of 2)' page is
		 * displayed with 'Standard statuses' listed.
		 * 
		 * 'Average Wait time' is listed on the page.
		 */
			
			try {
				assertEquals("", strFuncResult);
				String strStdStatusVal = "4004";
				String strStdStatusName = "Average Wait Time";
				strFuncResult = objRep.selectStdStatusesForReport(selenium,
						strStdStatusVal, strStdStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				//Thread.sleep(20000);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strCSVDownlPath};
				
				//AutoIt
				Runtime.getRuntime().exec(strArgs);
				
				int intCnt=0; 
				do{
				   GetProcessList objGPL = new GetProcessList();
				   strProcess = objGPL.GetProcessName();
				   intCnt++;
				   Thread.sleep(1000);
				 } while (strProcess.contains(strAutoFileName)&&intCnt<300);
						 	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			int intCount = 0;
			int intCount1=0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath)
									.exists());
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCount1++;
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);

			
			
		/*
		 * 7 Select the check box corresponding to 'Average wait time' under
		 * 'Standard statuses' and click on 'Generate Report' button.
		 * 'Statewide Resource Detail Report' is generated in 'Excel' (xls
		 * format) displaying appropriate data in the following columns:
		 * 
		 * 1. Region 2. Resource name 3. Type (Resource Type) 4. Address 5.
		 * County 6. Latitude 7. Longitude 8. EMResource ID 9. AHA ID 10.
		 * External ID 11. Website 12. Contact 13. Phone 1 14. Phone 2 15.
		 * Fax 16. Email 17. Notes 18. Standard status type name (Average
		 * Wait time)
		 * 
		 * Resources created under the standard resource type (Hospital and
		 * Ambulance) and status types associated with standard status type
		 * (Average wait time) which are active across all the Florida
		 * regions are listed in the generated report.
		 * 
		 * Resource 'RS1' details are displayed appropriately.
		 * 
		 * 'Region' is displayed as 'Statewide Florida'
		 * 
		 * Total status update for NST1 and NST2 is displayed under the
		 * 'Average wait time' column. Resource 'RS2' details are displayed
		 * appropriately.
		 * 
		 * 'Region' is displayed as 'Central FL'
		 * 
		 * Status update for NST3 is displayed under the 'Average wait time'
		 * column.
		 */

			try {
				assertEquals("", strFuncResult);

				String[][] strReportData = {
						{ "Statewide Resource Detail Report", "", "", "", "",
								"", "", "", "", "", "", "", "", "", "", "", "",
								"" },
						{ "Region", "Resource Name", "Type", "Address",
								"County", "Latitude", "Longitude",
								"EMResource ID", "AHA ID", "External ID",
								"Website", "Contact", "Phone 1", "Phone 2",
								"Fax", "Email", "Notes", "Average Wait Time" } };

				String[] strReportData1 = { "Statewide Florida", strResource1,
						strResrctTypName1, "AL ", "Barbour County",
						strLongitude, strLatitude, strRSValue[0], "", "", "",
						"auto qsg", "", "", "", "", "", "4" };

				strFuncResult = objGeneral.readAndVerifySpecificDataInExcel(
						strReportData, strCSVDownlRenamedPath, strResource1,
						true, true, 2, strReportData1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strReportData = {
						{ "Statewide Resource Detail Report", "", "", "", "",
								"", "", "", "", "", "", "", "", "", "", "", "",
								"" },
						{ "Region", "Resource Name", "Type", "Address",
								"County", "Latitude", "Longitude",
								"EMResource ID", "AHA ID", "External ID",
								"Website", "Contact", "Phone 1", "Phone 2",
								"Fax", "Email", "Notes", "Average Wait Time" } };

				String[] strReportData1 = { strRegnCentralFloria, strResource2,
						strResrctTypName2, "", "", "", "", strRSValue[1], "",
						"", "", "auto qsg", "", "", "", "", "", "0" };

				strFuncResult = objGeneral.readAndVerifySpecificDataInExcel(
						strReportData, strCSVDownlRenamedPath, strResource2,
						false, true, 2, strReportData1);
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
			gstrTCID = "BQS-100381";
			gstrTO = "Verify that for a number status type created with "
					+ "same standard statuses gets added up in the report generated.";

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
	'Description		:Verify that for a text status types created with same standard statuses the updates are displayed appropriately in the report generated.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:11/1/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS99555() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		ReadData objReadData = new ReadData();
		StatusTypes objST = new StatusTypes();
		Reports objRep = new Reports();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		Regions objRegions = new Regions();
		ViewMap objViewMap = new ViewMap();
		General objGeneral = new General();
		Views objViews = new Views();
		try {
			gstrTCID = "99555"; // Test Case Id
			gstrTO = " Verify that for a text status types created with same"
					+ " standard statuses the updates are displayed appropriately"
					+ " in the report generated.";
			gstrReason = "";
			gstrResult = "FAIL";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTmText = dts.getCurrentDate("HHmm");
			String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".xls";

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVFileNameRenamed = "StatewideResDetRep1_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword = objReadData.readData("Login", 3, 2);

			String strRegnStateWideFlorida = rdExcel.readData("Login", 22, 4);
			String strRegnCentralFloria = rdExcel.readData("Login", 26, 4);
			String strRegionValue[] = new String[1];

			String strStateWideStatType1 = "Aa1_" + strTimeText;
			String strStateWideStatType2 = "Aa2_" + strTimeText;
			String strStateWideSTvalue[] = new String[2];

			String strCentarlStatType1 = "Aa3_" + strTimeText;
			String strCentralSTvalue[] = new String[1];

			String strTSTValue = "Text";
			String strStatTypDefn = "Auto";
			String strStatValue = "";

			String strRolesNameStateWide = "RoleStatWid" + strTimeText;
			String strRoleValueStateWide = "";

			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strResrctTypName2 = "AutoRt2_" + strTimeText;
			String strRTValue[] = new String[2];

			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strRSValue[] = new String[2];
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Trailer";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strStandardST = "CEO Name";
			String strLongitude = "";
			String strLatitude = "";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * All the below mentioned test data is created in 'Statewide
			 * Florida' region.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegnStateWideFlorida);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Text status types TST1, TST2 are created with a standard
			 * status type 'CEO Name'.
			 */

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
						strTSTValue, strStateWideStatType1, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
						strStateWideStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strStateWideStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStateWideSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTSTValue, strStateWideStatType2, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
						strStateWideStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strStateWideStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStateWideSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
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

				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
						strRolesNameStateWide);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strStateWideSTvalue[0], "true" },
						{ strStateWideSTvalue[1], "true" } };
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(seleniumPrecondition, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StateWideResourceDetailReport");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesNameStateWide);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValueStateWide = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesNameStateWide);

				if (strRoleValueStateWide.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT1 is created selecting status types TST1 .
			 */

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
								+ strStateWideSTvalue[0] + "']");
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
				strRTValue[0] = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName1);
				if (strRTValue[0].compareTo("") != 0) {

					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Resource RS1 is created under resource type RT1 selecting
			 * standard resource type as 'Trailer'. 4. TST2 is added to resource
			 * RS1 at the resource level.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition,
						strResource1, strAbbrv, strResrctTypName1,
						strStandResType, strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.provideLookUpAddressToResource(seleniumPrecondition,
						strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
						strResource1, "No", "", strAbbrv, strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource1);

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
				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strStateWideSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.navToEditResourcePage(seleniumPrecondition,
						strResource1);
				strLongitude = seleniumPrecondition.getValue("id=longitude");
				log4j.info(strLongitude);

				char ch = '.';

				int intpos = strLongitude.indexOf(ch);

				String strLongitudeBefore = strLongitude.substring(0, intpos);
				strLongitude = strLongitude.substring(intpos, intpos + 4);

				strLongitude = strLongitudeBefore + strLongitude;
				log4j.info(strLongitude);

				strLatitude = seleniumPrecondition.getValue("id=latitude");

				intpos = strLatitude.indexOf(ch);
				String strLatitudeBefore = strLatitude.substring(0, intpos);
				strLatitude = strLatitude.substring(intpos, intpos + 4);

				strLatitude = strLatitudeBefore + strLatitude;
				log4j.info(strLatitude);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. User U1 is created in 'Statewide Florida' region, providing
			 * the following rights: a) Update right on resource 'RS1. b) Role
			 * to update the status types TST1, TST2 and selecting
			 * 'Report-Statewide resource details' right for a role. c) User is
			 * provided access to 'Florida Assisted Living 1' region.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegionValue[0] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegnCentralFloria);
				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

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
						strRoleValueStateWide, true);

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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * c) User is provided access to 'Florida Assisted Living 1' region.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName_1, strRegionValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * All the below mentioned test data is created in 'Florida Assisted
			 * Living 1' region.
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
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegnCentralFloria);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Text status types TST3 is created with a standard status type
			 * 'CEO Name'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST
						.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strTSTValue,
								strCentarlStatType1, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
						strCentarlStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strCentarlStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strCentralSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT2 is created selecting status types TST3.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName2,
						"css=input[name='statusTypeID'][value='"
								+ strCentralSTvalue[0] + "']");
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
				strRTValue[1] = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName2);
				if (strRTValue[1].compareTo("") != 0) {

					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Resource RS2 is created under resource type RT2 selecting
			 * standard status type as 'Trailer'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition,
						strResource2, strAbbrv, strResrctTypName2,
						strStandResType, strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
						strResource2, "No", "", strAbbrv, strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource2);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * 2 Login as user U1 to 'Statewide Florida' region and navigate to
			 * View >> Map screen 'Regional Map View' screen is displayed.
			 */

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
				strFuncResult = objLogin.navUserDefaultRgn(selenium,
						strRegnStateWideFlorida);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * Action:Select the resource 'RS1' from 'Find Resource' drop down
			 * and update the status of TST1 and TST2 by clicking on 'Update
			 * Status' link on the resource RS1 pop up window Result: Updated
			 * status are displayed appropriately on the resource pop up window
			 * of resource RS1.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatus(selenium,
						"link=Update Status");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"2", strStateWideSTvalue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"2", strStateWideSTvalue[1], false, "", "");
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
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "2");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "2");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Navigate to Reports >> Resource reports 'Resource Reports Menu'
			 * screen is displayed
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Click on 'Statewide resource details report' link Resources
			 * created across all the Florida regions under the resource type
			 * with standard resource types that are active in a region are
			 * listed under 'Standard Resources' on 'Statewide Resource Detail
			 * Report (Step 1 of 2) page.
			 * 
			 * 'Trailer' is listed on the page.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.navToStatewideResourceDetailReportPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStdResTypeVal = { "121" };
				String[] strStdRt = { "Trailer" };
				strFuncResult = objRep.selectStdResTypeManyForReport(selenium,
						strStdResTypeVal, strStdRt, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Select the check boxes corresponding to 'Ambulance' and
			 * 'Hospital' under 'Standard Resources' and click on 'Next' button.
			 * 'Statewide Resource Detail Report (step 2 of 2)' page is
			 * displayed with 'Standard statuses' listed. 'CEO Name' is listed
			 * on the page.
			 */

			try {
				assertEquals("", strFuncResult);
				String strStdStatusVal = "7041";
				String strStdStatusName = "CEO Name";
				strFuncResult = objRep.selectStdStatusesForReport(selenium,
						strStdStatusVal, strStdStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			try {
				assertEquals("", strFuncResult);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strCSVDownlPath};
				
				//AutoIt
				Runtime.getRuntime().exec(strArgs);
				
				int intCnt=0; 
				do{
				   GetProcessList objGPL = new GetProcessList();
				   strProcess = objGPL.GetProcessName();
				   intCnt++;
				   Thread.sleep(1000);
				 } while (strProcess.contains(strAutoFileName)&&intCnt<300);
						 	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			int intCount = 0;
			int intCount1=0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath).exists());
					Thread.sleep(10000);
					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath)
									.exists());
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCount1++;
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);

			/*
			 * 7 Select the check box corresponding to 'Average wait time' under
			 * 'Standard statuses' and click on 'Generate Report' button.
			 * 'Statewide Resource Detail Report' is generated in 'Excel' (xls
			 * format) displaying appropriate data in the following columns:
			 * 
			 * 1. Region 2. Resource name 3. Type (Resource Type) 4. Address 5.
			 * County 6. Latitude 7. Longitude 8. EMResource ID 9. AHA ID 10.
			 * External ID 11. Website 12. Contact 13. Phone 1 14. Phone 2 15.
			 * Fax 16. Email 17. Notes 18. Standard status type name (Average
			 * Wait time)
			 * 
			 * Resources created under the standard resource type (Hospital and
			 * Ambulance) and status types associated with standard status type
			 * (Average wait time) which are active across all the Florida
			 * regions are listed in the generated report.
			 * 
			 * Resource 'RS1' details are displayed appropriately.
			 * 
			 * 'Region' is displayed as 'Statewide Florida'
			 * 
			 * Total status update for TST1 and TST2 is displayed under the
			 * 'Average wait time' column. Resource 'RS2' details are displayed
			 * appropriately.
			 * 
			 * 'Region' is displayed as 'Florida Assisted Living 1'
			 * 
			 * Status update for TST3 is displayed under the 'Average wait time'
			 * column.
			 */

			try {
				assertEquals("", strFuncResult);

				String[][] strReportData = {
						{ "Statewide Resource Detail Report", "", "", "", "",
								"", "", "", "", "", "", "", "", "", "", "", "",
								"" },
						{ "Region", "Resource Name", "Type", "Address",
								"County", "Latitude", "Longitude",
								"EMResource ID", "AHA ID", "External ID",
								"Website", "Contact", "Phone 1", "Phone 2",
								"Fax", "Email", "Notes", "CEO Name" } };

				String[] strReportData1 = { "Statewide Florida", strResource1,
						strResrctTypName1, "AL ", "Barbour County",
						strLongitude, strLatitude, strRSValue[0], "", "", "",
						"auto qsg", "", "", "", "", "", "2; 2" };

				strFuncResult = objGeneral.readAndVerifySpecificDataInExcel(
						strReportData, strCSVDownlRenamedPath, strResource1,
						true, true, 2, strReportData1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strReportData = {
						{ "Statewide Resource Detail Report", "", "", "", "",
								"", "", "", "", "", "", "", "", "", "", "", "",
								"" },
						{ "Region", "Resource Name", "Type", "Address",
								"County", "Latitude", "Longitude",
								"EMResource ID", "AHA ID", "External ID",
								"Website", "Contact", "Phone 1", "Phone 2",
								"Fax", "Email", "Notes", "CEO Name" } };

				String[] strReportData1 = { strRegnCentralFloria, strResource2,
						strResrctTypName2, "", "", "", "", strRSValue[1], "",
						"", "", "auto qsg", "", "", "", "", "", "" };

				strFuncResult = objGeneral.readAndVerifySpecificDataInExcel(
						strReportData, strCSVDownlRenamedPath, strResource2,
						false, true, 2, strReportData1);
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
			gstrTCID = "BQS-99555";
			gstrTO = "Verify that for a text status types created with same standard statuses" +
					" the updates are displayed appropriately in the report generated.";
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
	
	/***************************************************************************************
	'Description	:Verify that for a text status types created with same standard statuses
	                 the updates are displayed appropriately in the report generated.
	'Arguments		:None
	'Returns		:None
	'Date			:11/1/2012
	'Author			:QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	*****************************************************************************************/

	@Test
	public void testBQS99556() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		ReadData objReadData = new ReadData();
		StatusTypes objST = new StatusTypes();
		Reports objRep = new Reports();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		Regions objRegions = new Regions();
		ViewMap objViewMap = new ViewMap();
		General objGeneral = new General();
		Views objViews = new Views();
		try {
			gstrTCID = "99556"; // Test Case Id
			gstrTO = " Verify that for a multi status types created with"
					+ " same standard statuses the updates are displayed"
					+ " appropriately in the report generated.";
			gstrReason = "";
			gstrResult = "FAIL";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTmText = dts.getCurrentDate("HHmm");
			String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".xlsx";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".xls";

			String strCSVFileNameRenamed = "StatewideResDetRep1_" + gstrTCID
					+ "_" + strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword = objReadData.readData("Login", 3, 2);

			String strRegnStateWideFlorida = rdExcel.readData("Login", 22, 4);
			String strRegnCentralFloria = rdExcel.readData("Login", 26, 4);
			String strRegionValue[] = new String[1];

			String strStateWideStatType1 = "Aa1_" + strTimeText;
			String strStateWideStatType2 = "Aa2_" + strTimeText;
			String strStateWideSTvalue[] = new String[2];

			String strCentarlStatType1 = "Aa3_" + strTimeText;
			String strCentralSTvalue[] = new String[1];

			String strMSTValue = "Multi";
			String strStatTypDefn = "Auto";
			String strStatValue = "";

			String strStatusValue[] = new String[3];
			String strStatusMST1 = "St1" + strTimeText;
			String strStatusMST2 = "St2" + strTimeText;
			String strStatusMST3 = "St3" + strTimeText;

			String strRolesNameStateWide = "RoleStatWid" + strTimeText;
			String strRoleValueStateWide = "";

			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strResrctTypName2 = "AutoRt2_" + strTimeText;
			String strRTValue[] = new String[2];

			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strRSValue[] = new String[2];
			String strAbbrv = "A" + strTmText;
			String strResVal = "";

			String strContFName = "auto";
			String strContLName = "qsg";
			String strStandardST = "FL: Active";
			String strLongitude = "";
			String strLatitude = "";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * All the below mentioned test data is created in 'Statewide
		 * Florida' region.
		 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegnStateWideFlorida);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 1. Multi status types MST1, MTST2 are created with a standard
		 * status type 'CEO Name'.
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strMSTValue, strStateWideStatType1, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
				seleniumPrecondition.click(propElementDetails
						.getProperty("CreateStatusType.Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);
				seleniumPrecondition.click(propElementDetails
						.getProperty("CreateStatusType.ReturnToSTList"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strStateWideStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStateWideSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status 1
			try {
				assertEquals("", strFuncResult);
				String strStatTypeColor = "Black";
				String strStatusTypeValue = "Multi";
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						strStateWideStatType1, strStatusMST1,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						strStateWideStatType1, strStatusMST1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strMSTValue, strStateWideStatType2, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
				seleniumPrecondition.click(propElementDetails
						.getProperty("CreateStatusType.Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);
				seleniumPrecondition.click(propElementDetails
						.getProperty("CreateStatusType.ReturnToSTList"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strStateWideStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStateWideSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Status 2
			try {
				assertEquals("", strFuncResult);
				String strStatTypeColor = "Black";
				String strStatusTypeValue = "Multi";
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						strStateWideStatType2, strStatusMST2,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						strStateWideStatType2, strStatusMST2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

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

				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
						strRolesNameStateWide);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strStateWideSTvalue[0], "true" },
						{ strStateWideSTvalue[1], "true" } };
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(seleniumPrecondition, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StateWideResourceDetailReport");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesNameStateWide);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValueStateWide = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesNameStateWide);

				if (strRoleValueStateWide.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT1 is created selecting status types TST1 .
			 */

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
								+ strStateWideSTvalue[0] + "']");
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
				strRTValue[0] = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName1);
				if (strRTValue[0].compareTo("") != 0) {

					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Resource RS1 is created under resource type RT1 selecting
			 * standard resource type as 'Trailer'. 4. TST2 is added to resource
			 * RS1 at the resource level.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStandResType = "Home Health";
				strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition,
						strResource1, strAbbrv, strResrctTypName1,
						strStandResType, strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.provideLookUpAddressToResource(seleniumPrecondition,
						strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
						strResource1, "No", "", strAbbrv, strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource1);

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
				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strStateWideSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.navToEditResourcePage(seleniumPrecondition,
						strResource1);
				strLongitude = seleniumPrecondition.getValue("id=longitude");
				log4j.info(strLongitude);

				char ch = '.';

				int intpos = strLongitude.indexOf(ch);

				String strLongitudeBefore = strLongitude.substring(0, intpos);
				strLongitude = strLongitude.substring(intpos, intpos + 4);

				strLongitude = strLongitudeBefore + strLongitude;
				log4j.info(strLongitude);

				strLatitude = seleniumPrecondition.getValue("id=latitude");

				intpos = strLatitude.indexOf(ch);
				String strLatitudeBefore = strLatitude.substring(0, intpos);
				strLatitude = strLatitude.substring(intpos, intpos + 4);

				strLatitude = strLatitudeBefore + strLatitude;
				log4j.info(strLatitude);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. User U1 is created in 'Statewide Florida' region, providing
			 * the following rights: a) Update right on resource 'RS1. b) Role
			 * to update the status types TST1, TST2 and selecting
			 * 'Report-Statewide resource details' right for a role. c) User is
			 * provided access to 'Florida Assisted Living 1' region.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegionValue[0] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegnCentralFloria);
				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

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
						strRoleValueStateWide, true);

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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * c) User is provided access to 'Florida Assisted Living 1' region.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName_1, strRegionValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * All the below mentioned test data is created in 'Florida Assisted
			 * Living 1' region.
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
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegnCentralFloria);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Text status types TST3 is created with a standard status type
			 * 'CEO Name'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST
						.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strMSTValue,
								strCentarlStatType1, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
				seleniumPrecondition.click(propElementDetails
						.getProperty("CreateStatusType.Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);
				seleniumPrecondition.click(propElementDetails
						.getProperty("CreateStatusType.ReturnToSTList"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strCentarlStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strCentralSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status 3
			try {
				assertEquals("", strFuncResult);
				String strStatTypeColor = "Black";
				String strStatusTypeValue = "Multi";
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						strCentarlStatType1, strStatusMST3, strStatusTypeValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						strCentarlStatType1, strStatusMST3);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT2 is created selecting status types TST3.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName2,
						"css=input[name='statusTypeID'][value='"
								+ strCentralSTvalue[0] + "']");
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
				strRTValue[1] = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName2);
				if (strRTValue[1].compareTo("") != 0) {

					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Resource RS2 is created under resource type RT2 selecting
			 * standard status type as 'Trailer'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition,
						strResource2, strAbbrv, strResrctTypName2, "Trailer",
						strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
						strResource2, "No", "", strAbbrv, strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource2);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * 2 Login as user U1 to 'Statewide Florida' region and navigate to
			 * View >> Map screen 'Regional Map View' screen is displayed.
			 */

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
				strFuncResult = objLogin.navUserDefaultRgn(selenium,
						strRegnStateWideFlorida);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * Action:Select the resource 'RS1' from 'Find Resource' drop down
			 * and update the status of MST1 and MST2 by clicking on 'Update
			 * Status' link on the resource RS1 pop up window Result: Updated
			 * status are displayed appropriately on the resource pop up window
			 * of resource RS1.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatus(selenium,
						"link=Update Status");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strStateWideSTvalue[0], false, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strStateWideSTvalue[1], false, "",
						"");
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
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strStatusMST1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strStatusMST2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Navigate to Reports >> Resource reports 'Resource Reports Menu'
			 * screen is displayed
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Click on 'Statewide resource details report' link Resources
			 * created across all the Florida regions under the resource type
			 * with standard resource types that are active in a region are
			 * listed under 'Standard Resources' on 'Statewide Resource Detail
			 * Report (Step 1 of 2) page.
			 * 
			 * 'Trailer' and 'Home Health' are listed on the page.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.navToStatewideResourceDetailReportPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strStdResTypeVal = { "116" };
				String[] strStdRt = { "Home Health" };
				strFuncResult = objRep.selectStdResTypeManyForReport(selenium,
						strStdResTypeVal, strStdRt, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStdResTypeVal = { "121" };
				String[] strStdRt = { "Trailer" };
				strFuncResult = objRep.selectStdResTypeManyForReport(selenium,
						strStdResTypeVal, strStdRt, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Select the check boxes corresponding to 'Ambulance' and 'Home
			 * Health' under 'Standard Resources' and click on 'Next' button.
			 * 'Statewide Resource Detail Report (step 2 of 2)' page is
			 * displayed with 'Standard statuses' listed. 'FL: Active' is listed
			 * on the page.
			 */

			try {
				assertEquals("", strFuncResult);
				String strStdStatusVal = "7000";
				String strStdStatusName = "FL: Active";
				strFuncResult = objRep.selectStdStatusesForReport(selenium,
						strStdStatusVal, strStdStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt < 300);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath).exists());
					Thread.sleep(10000);
					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath)
									.exists());
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCount1++;
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);

			/*
			 * 7 Select the check box corresponding to 'FL:Active' under
			 * 'Standard statuses' and click on 'Generate Report' button.
			 * 'Statewide Resource Detail Report' is generated in 'Excel' (xls
			 * format) displaying appropriate data in the following columns:
			 * 
			 * 1. Region 2. Resource name 3. Type (Resource Type) 4. Address 5.
			 * County 6. Latitude 7. Longitude 8. EMResource ID 9. AHA ID 10.
			 * External ID 11. Website 12. Contact 13. Phone 1 14. Phone 2 15.
			 * Fax 16. Email 17. Notes 18. Standard status type name (Average
			 * Wait time)
			 * 
			 * Resources created under the standard resource type (Hospital and
			 * Ambulance) and status types associated with standard status type
			 * (Average wait time) which are active across all the Florida
			 * regions are listed in the generated report.
			 * 
			 * Resource 'RS1' details are displayed appropriately.
			 * 
			 * 'Region' is displayed as 'Statewide Florida'
			 * 
			 * Total status update for MST1 and MST2 is displayed under the
			 * 'Average wait time' column. Resource 'RS2' details are displayed
			 * appropriately.
			 * 
			 * 'Region' is displayed as 'Florida Assisted Living 1'
			 * 
			 * Status update for MST3 is displayed under the 'Average wait time'
			 * column.
			 */

			try {
				assertEquals("", strFuncResult);

				String[][] strReportData = {
						{ "Statewide Resource Detail Report", "", "", "", "",
								"", "", "", "", "", "", "", "", "", "", "", "",
								"" },
						{ "Region", "Resource Name", "Type", "Address",
								"County", "Latitude", "Longitude",
								"EMResource ID", "AHA ID", "External ID",
								"Website", "Contact", "Phone 1", "Phone 2",
								"Fax", "Email", "Notes", "FL: Active" } };

				String[] strReportData1 = { "Statewide Florida", strResource1,
						strResrctTypName1, "AL ", "Barbour County",
						strLongitude, strLatitude, strRSValue[0], "", "", "",
						"auto qsg", "", "", "", "", "", strStatusMST1+"; "+strStatusMST2 };
				
				

				strFuncResult = objGeneral.readAndVerifySpecificDataInExcel(
						strReportData, strCSVDownlRenamedPath, strResource1,
						true, true, 2, strReportData1);
				
				if(strFuncResult.equals("Specified Data "
						+ strStatusMST1+"; "+strStatusMST2+ " is NOT displayed in the report")){
					String[][] strReportData2 = {
							{ "Statewide Resource Detail Report", "", "", "", "",
									"", "", "", "", "", "", "", "", "", "", "", "",
									"" },
							{ "Region", "Resource Name", "Type", "Address",
									"County", "Latitude", "Longitude",
									"EMResource ID", "AHA ID", "External ID",
									"Website", "Contact", "Phone 1", "Phone 2",
									"Fax", "Email", "Notes", "FL: Active" } };

					String[] strReportData3 = { "Statewide Florida", strResource1,
							strResrctTypName1, "AL ", "Barbour County",
							strLongitude, strLatitude, strRSValue[0], "", "", "",
							"auto qsg", "", "", "", "", "", strStatusMST2+"; "+strStatusMST1 };
					
					

					strFuncResult = objGeneral.readAndVerifySpecificDataInExcel(
							strReportData2, strCSVDownlRenamedPath, strResource1,
							true, true, 2, strReportData3);
				}
														
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strReportData = {
						{ "Statewide Resource Detail Report", "", "", "", "",
								"", "", "", "", "", "", "", "", "", "", "", "",
								"" },
						{ "Region", "Resource Name", "Type", "Address",
								"County", "Latitude", "Longitude",
								"EMResource ID", "AHA ID", "External ID",
								"Website", "Contact", "Phone 1", "Phone 2",
								"Fax", "Email", "Notes", "FL: Active" } };

				
				String[] strReportData1 = {strRegnCentralFloria,
						strResource2, strResrctTypName2, "",
						"", "", "", strRSValue[1], "", "", "",
						"auto qsg", "", "", "", "", "", "" };


				strFuncResult = objGeneral.readAndVerifySpecificDataInExcel(
						strReportData, strCSVDownlRenamedPath, strResource2,
						false, true, 2, strReportData1);
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
			gstrTCID = "BQS-99556";
			gstrTO = "Verify that for a multi status types created with same"
					+ " standard statuses the updates are displayed appropriately"
					+ " in the report generated.";
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
	
    //start//testFTS99089//
	/**************************************************************************************
	'Description		:Verify that user without run report right on any resource and with 
	                     'Report-Statewide resource detail' right CAN generate the report.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/7/2013
	'Author				:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date				           Modified By
	'Date					               Name
	****************************************************************************************/

	@Test
	public void testFTS99089() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		Reports objReports = new Reports();
		try {
			gstrTCID = "99089"; // Test Case Id
			gstrTO = "Verify that user without run report right on any resource and with 'Report-Statewide"
					+ " resource detail' right CAN generate the report.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 22, 4);

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

		/*
		* STEP :
		  Action:Precondition:User U1 is created in 'Statewide Florida' region and has
		   'Report-Statewide resource detail' right.
		   User does not have run report rights on any of the resource present in the region. 
		  Expected Result:No Expected Result
		*/
		//584304

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
				strFuncResult = objCreateUsers
						.selAndDeselAllResRights(seleniumPrecondition,
								false, false, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepStatewideResDetail"),
								true);
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
		log4j.info("~~~~~PRECONDITION  - "+gstrTCID+" EXECUTION ENDS~~~~~");
		/*
		* STEP :
		  Action:Login as user U1, navigate to Reports >> Resource reports and 
		  click on 'Statewide Resource Details' link 
		  Expected Result:'Region Default' screen is displayed
		  'Statewide Resource Detail Report' screen is displayed. 
		*/
		//584305
		log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports
						.navToStatewideResourceDetailReportPage(selenium);
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
			gstrTCID = "FTS-99089";
			gstrTO = "Verify that user without run report right on any resource and with 'Report-Statewide "
					+ "resource detail' right CAN generate the report.";
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

	// end//testFTS99089//
}




