package com.qsgsoft.EMResource.features;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.General;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.Reports;
import com.qsgsoft.EMResource.shared.ResourceTypes;
import com.qsgsoft.EMResource.shared.Resources;
import com.qsgsoft.EMResource.shared.Roles;
import com.qsgsoft.EMResource.shared.StatusTypes;
import com.qsgsoft.EMResource.shared.ViewMap;
import com.qsgsoft.EMResource.shared.Views;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.GetProcessList;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

public class FTSStatusDetailReport {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSStatusSnapShotReport_AutoIT");
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
	Properties propElementAutoItDetails, propAutoItDetails;
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

		selenium.start();
		selenium.windowMaximize();
		
		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
			    4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
			    .getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}
	
	@After
	public void tearDown() throws Exception {

		try {
			selenium.close();
		} catch (Exception e) {

		}

		// kill browser
		selenium.stop();
		
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
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
	

	//start//testFTS126295//
		/***************************************************************
		'Description		:When a status type ST is added for resource RS1 of type RT1 and ST is 
		                     also associated with resource type RT2 (where resource RS2 is created under RT2),
		                     the 'Status Detail report (step 2 of 2)' page displays resources of both the resource types.
		'Precondition		:
		'Arguments			:None
		'Returns			:None
		'Date				:10/8/2013
		'Author				:QSG
		'---------------------------------------------------------------
		'Modified Date				                   Modified By
		'Date					                       Name
		***************************************************************/

		@Test
		public void testFTS126295() throws Exception {
			boolean blnLogin = false;
			String strFuncResult = "";
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();
			Login objLogin = new Login();// object of class Login
			Roles objRole = new Roles();
			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();
			General objGeneral = new General();
			try {
				gstrTCID = "126295"; // Test Case Id
				gstrTO = " When a status type ST is added for resource RS1 of type RT1 and ST is also associated with resource type RT2" +
						" (where resource RS2 is created under RT2), the 'Status Detail report (step 2 of 2)' page displays resources of both the resource types.";// Test// Objective
				gstrReason = "";
				gstrResult = "FAIL";			
				Date_Time_settings dts = new Date_Time_settings();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				gstrTimetake = dts.timeNow("HH:mm:ss");
				String strFILE_PATH = pathProps.getProperty("TestData_path");
				propElementAutoItDetails = objAP.ReadAutoit_FilePath();
				String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
				String strApplTime = "";
				// login details
				String strLoginUserName = rdExcel.readData("Login", 3, 1);
				String strLoginPassword = rdExcel.readData("Login", 3, 2);
				String strRegn = rdExcel.readData("Login", 3, 4);
				String strNumTypeName = "AutoNSt_" + strTimeText;
				String strNumStatTypDefn = "Auto";
				String strSTvalue[] = new String[1];

				// RT
				String strResType = "AutoRt_" + strTimeText;
				String strResType1 = "AutoRt_1" + strTimeText;
				String strRsTypeValues[] = new String[2];
				//RS
				String strResource = "AutoRs_" + strTimeText;
				String strResource1 = "AutoRs_1" + strTimeText;			
				String strRSValue[] = new String[2];
				String strStandResType = "Aeromedical";
				String strContFName = "auto";
				String strContLName = "qsg";
				String strAbbrv = "Rs";
				String strState = "Alabama";
				String strCountry = "Autauga County";
				// Role
				String strRoleName = "AutoR_" + strTimeText;
				String strRoleRights[][] = {};
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

				String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
				String strUsrFulName_1 = strUserName_1;
				String strInitPwd = rdExcel.readData("Login", 4, 2);
				String strConfirmPwd = rdExcel.readData("Login", 4, 2);

				// Application time
				String strUpdatdVale = "";
				String strUpdatedDate = "";
				String strReportGenrtTime = "";
				String strReportGenrtTimePDF = "";

				// System Time
				String strTimeGenerateSystem = "";
				String strTimeUpdateSystem = "";
				String strTimeUpdateSystem1="";

				String strTimePDFGenerateSystem = "";
				String strDurationDiffPDF = "";

				String strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Path");
				String strCSVDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_"
						+ gstrTCID + "_" + strTimeText + ".csv";
				String strPDFDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_"
						+ gstrTCID + "_" + strTimeText + ".pdf";

				String strAutoFileName = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Name");

				String[] strTestDataCSV1 = null;
				String[] strTestDataPDF1 = null;

				/*
				 * STEP : Action:Preconditions:
				 * 
				 * 1. Resource types RT1 and RT2 are associated with status type ST
				 * 
				 * 2. Resources RS1 is created under RT1.
				 * 
				 * 3. Resources RS2 is created under RT2.
				 * 
				 * 4. User U1 has following rights:
				 * 
				 * a. 'Report - Status Detail' b. Role to update status type ST c.
				 * 'Update Status' and 'Run Report' rights on resources RS1 and RS2
				 * Expected Result:No Expected Result
				 */
				// 662861

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

				//Creating ST
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.navStatusTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strStatusTypeValue = "Number";
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusTypeValue,
							strNumTypeName, strNumStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[0] = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, strNumTypeName);

					if (strSTvalue[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				//Creating RT1
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.resrcTypeMandatoryFldsNew(
							seleniumPrecondition, strResType, strSTvalue[0]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
							strResType);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRsTypeValues[0] = objRT.fetchResTypeValueInResTypeList(
							seleniumPrecondition, strResType);

					if (strRsTypeValues[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				//Creating RT2
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.resrcTypeMandatoryFldsNew(
							seleniumPrecondition, strResType1, strSTvalue[0]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
							strResType1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRsTypeValues[1] = objRT.fetchResTypeValueInResTypeList(
							seleniumPrecondition, strResType1);

					if (strRsTypeValues[1].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				//Creating RS1
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
							strResType, strContFName, strContLName, strState,
							strCountry, strStandResType);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strResVal = objRs.fetchResValueInResList(
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
				//Creating RS2
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.createResourceWitLookUPadres(
							seleniumPrecondition, strResource1, strAbbrv,
							strResType1, strContFName, strContLName, strState,
							strCountry, strStandResType);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strResVal = objRs.fetchResValueInResList(
							seleniumPrecondition, strResource1);

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
					strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// create role to update

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.CreateRoleWithAllFields(
							seleniumPrecondition, strRoleName, strRoleRights,
							strSTvalue, true, strSTvalue, true, true);

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

				// Update user
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navUserListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navToCreateUserPage(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
							seleniumPrecondition, strUserName_1, strInitPwd,
							strConfirmPwd, strUsrFulName_1);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.selectResourceRights(
							seleniumPrecondition, strResource, false, true, true,
							true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.selectResourceRights(
							seleniumPrecondition, strResource1, false, true, true,
							true);

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
					String strOptions = propElementDetails
							.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
					strFuncResult = objCreateUsers.advancedOptns(
							seleniumPrecondition, strOptions, true);
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
				/*
				 * STEP : Action:Login as user U1, update the status of ST on day D1
				 * for resource RS1 and RS2. Expected Result:Updated values are
				 * displayed.
				 */
				// 662862
				log4j.info("~~~~~PRECONDITION - " + gstrTCID
						+ " EXECUTION ENDS~~~~~");
				log4j.info("~~~~~TEST CASE - " + gstrTCID
						+ " EXECUTION STARTS~~~~~");

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

					String[] strEventStatType = {};
					String[] strRoleStatType = { strNumTypeName };
					strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
							selenium, strResource, strEventStatType,
							strRoleStatType, false, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}
				try {
					assertEquals("", strFuncResult);

					String strReportResult[] = objViewMap.updateStatusTypeWithTime(
							selenium, strResource, strSTvalue, "HH:mm:ss");
					strTimeUpdateSystem = strReportResult[1];
					strFuncResult = strReportResult[0];
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}
				try {
					assertEquals("", strFuncResult);
					blnLogin = true;

					String[] strEventStatType = {};
					String[] strRoleStatType = { strNumTypeName };
					strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
							selenium, strResource1, strEventStatType,
							strRoleStatType, false, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);
					String strReportResult[] = objViewMap.updateStatusTypeWithTime(
							selenium, strResource1, strSTvalue, "HH:mm:ss");
					strTimeUpdateSystem1 = strReportResult[1];
					strFuncResult = strReportResult[0];
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}
				try {
					assertEquals("", strFuncResult);

					strUpdatdVale = selenium
							.getText("css=div.emsText.maxheight > span");

					strUpdatedDate = selenium.getText("//div/span" + "[text()='"
							+ strUpdatdVale + "']/parent::"
							+ "div/span[@class='time']");
					strUpdatedDate = strUpdatedDate.substring(1, 13);

					String strLastUpdArr[] = strUpdatedDate.split(" ");
					strUpdatedDate = strLastUpdArr[2];

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				/*
				 * STEP : Action:Navigate to Reports>>Status Reports, click on
				 * 'Status Detail' Expected Result:'Status Detail Report (Step 1 of
				 * 2)' screen is displayed with:
				 * 
				 * 1. 'Start Date' field (with Date picker) 2. 'End Date' field
				 * (with Date picker) 3. 'Report Format' (with PDF and CSV options,
				 * PDF is selected by default) 4. Status Types dropdown with status
				 * types. (All status types in region)
				 */
				// 662863

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					String[] statTypeName = { strNumTypeName };
					strFuncResult = objRep.verifyCalndrWidInStatusDetailReportNew(
							selenium, statTypeName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Generate PDF File and fetch time

				/*
				 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
				 * Date' fields, select PDF, select ST and click on Next Expected
				 * Result:'Status Detail Report (Step 1 of 2)' screen is displayed
				 * with:
				 * 
				 * 1. Status type ST 2. Resources RS1 and RS2
				 */
				// 662864

				try {
					assertEquals("", strFuncResult);
					String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
							"M/d/yyyy");

					// Fetch Application time
					String strCurentDat[] = objGeneral.getSnapTime(selenium);
					Calendar cal = Calendar.getInstance();
					int year = cal.get(Calendar.YEAR);
					System.out.println("Current Year: " + year);

					strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
							+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
							"M/d/yyyy");
					System.out.println(strApplTime);

					strFuncResult = objRep.enterReportStatusDetailAndNavigate(
							selenium, strCSTApplTime, strCSTApplTime, true,
							strNumTypeName);

					try {
						assertEquals("", strFuncResult);
						String strElementID = "//div[@id='mainContainer']/form/table/tbody/tr/td[2][text()='"
								+ strNumTypeName + "']";
						strFuncResult = objGeneral.checkForAnElements(selenium,
								strElementID);
						if (strFuncResult.equals("")) {
							log4j.info("status type "
									+ strNumTypeName
									+ "is displayed in 'Status Detail Report (Step 2 of 2)' screen ");
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}				
					try {
						assertEquals("", strFuncResult);
						String strElementID = "css=input[name='resourceID'][value='" + strRSValue[0]
								+ "']";
						strFuncResult = objGeneral.checkForAnElements(selenium,
								strElementID);
						if (strFuncResult.equals("")) {
							log4j.info("Resource  "
									+ strResource
									+ "is displayed in 'Status Detail Report (Step 2 of 2)' screen ");
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String strElementID = "css=input[name='resourceID'][value='" + strRSValue[1]
							+ "']";
						strFuncResult = objGeneral.checkForAnElements(selenium,
								strElementID);
						if (strFuncResult.equals("")) {
							log4j.info("Resource  "
									+ strResource1
									+ "is displayed in 'Status Detail Report (Step 2 of 2)' screen ");
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						selenium.click("css=input[name='resourceID'][value='"
								+ strRSValue[0] + "']");
						String[] strStatusValue = {};
						strFuncResult = objRep
								.enterReportStatusDetailGenerateReport(selenium,
										strStatusValue, false, strRSValue[1]);
						Thread.sleep(3000);
						strTimePDFGenerateSystem = dts.timeNow("HH:mm:ss");

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					int intDurationDiff = dts.getTimeDiff1(
							strTimePDFGenerateSystem, strTimeUpdateSystem);

					double fltDurationDiff = (double) intDurationDiff / 3600;

					double dRounded = dts.roundTwoDecimals(fltDurationDiff);
					strDurationDiffPDF = Double.toString(dRounded);
					System.out.println("PDF generation duration "
							+ strDurationDiffPDF);

					// Application Time
					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTimePDF = strStatusTime[2];
					System.out.println("PDF generation time "
							+ strReportGenrtTimePDF);

					strTestDataPDF1 = new String[] {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strResource, strUpdatdVale,
							strCurrDate + " " + strUpdatedDate,
							strCurrDate + " " + strReportGenrtTimePDF,
							strDurationDiffPDF, strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strPDFDownlPath,
							"Status Detail Report need to be checked in PDF file"

					};

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					// Thread.sleep(20000);
					String strProcess = "";
					String strArgs[] = { strAutoFilePath, strPDFDownlPath };

					// AutoIt
					Runtime.getRuntime().exec(strArgs);

					int intCnt = 0;
					do {
						GetProcessList objGPL = new GetProcessList();
						strProcess = objGPL.GetProcessName();
						intCnt++;
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Select RS1 and RS2, click on 'Generate Report'
				 * Expected Result:Status Detail Report is generated in the PDF
				 * format.
				 * 
				 * Header and Footer is with following details.
				 * 
				 * Header: 1. Start Date: dd-mm-yyyy 2. End Date: dd-mm-yyyy 3.
				 * Status Type
				 * 
				 * Footer: 1. Report Run By: (name of the user) 2. From: (Name of
				 * the Region) 3. On: MM/DD/YYYY HH:MM:SS (Time Zone) 4. Intermedix
				 * Emsystems logo 5. Page number
				 * 
				 * Details of resource RS1 are displayed appropriately with
				 * following columns:
				 * 
				 * 1. Status Value 2. Status Start Date 3. Status End Date 4.
				 * Duration (Hrs) 5. User 6. IP 7. Trace 8. Comments
				 */
				// 662865

				/*
				 * STEP : Action:Navigate to Reports>>Status Reports, click on
				 * 'Status Detail' Expected Result:'Status Detail Report (Step 1 of
				 * 2)' screen is displayed
				 */
				// 662866

				try {
					assertEquals("", strFuncResult);
					selenium.selectWindow("");
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				/*
				 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
				 * Date' fields, select CSV for report format, select ST and click
				 * on Next Expected Result:'Status Detail Report (Step 2 of 2)'
				 * screen is displayed
				 */
				// 662867

				// Generate report along with time
				try {
					assertEquals("", strFuncResult);
					String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
							"M/d/yyyy");
					strFuncResult = objRep.enterReportStatusDetailAndNavigate(
							selenium, strCSTApplTime, strCSTApplTime, false,
							strNumTypeName);

					try {
						assertEquals("", strFuncResult);
						selenium.click("css=input[name='resourceID'][value='"
								+ strRSValue[0] + "']");
						String[] strStatusValue = {};
						strFuncResult = objRep
								.enterReportStatusDetailGenerateReport(selenium,
										strStatusValue, false, strRSValue[1]);
						Thread.sleep(3000);
						strTimeGenerateSystem = dts.timeNow("HH:mm:ss");

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTime = strStatusTime[2];
					System.out.println("CSV generation time " + strReportGenrtTime);

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
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
					String strDurationUpdat = strUpdatedDate;
					String strDurationGenerat = strReportGenrtTime;

					int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
							strTimeUpdateSystem1);

					double fltDurationDiff = (double) intDurationDiff / 3600;

					double dRounded = dts.roundTwoDecimals(fltDurationDiff);
					String strDurationDiff = Double.toString(dRounded);
					System.out
							.println("PDF generation duration " + strDurationDiff);

					strTestDataCSV1 = new String[] {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strResource + "/" + strResource1, strUpdatdVale,
							strCurrDate + " " + strDurationUpdat,
							strCurrDate + " " + strDurationGenerat,
							strDurationDiff, strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strCSVDownlPath,
							"Status Detail Report need to be checked in CSV file"

					};

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				File Pf1 = new File(strCSVDownlPath);
				File Cf1 = new File(strPDFDownlPath);

				if (Pf1.exists() && Cf1.exists()) {
					try {
						assertEquals("", strFuncResult);
						gstrResult = "PASS";

						String strWriteFilePath = pathProps
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
								"Status_Detail_Report");
						objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
								"Status_Detail_Report");
					} catch (AssertionError Ae) {
						gstrResult = "FAIL";
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
				/*
				 * STEP : Action:Select RS1 and RS2, click on 'Generate Report'
				 * Expected Result:Status Detail Report is generated in the CSV
				 * (Comma Separated Values) format with sections 'Status Detail' and
				 * 'Status Summary'.
				 * 
				 * 'Status Detail' section displays following columns with
				 * appropriate data:
				 * 
				 * 1. Resource 2. Status 3. Start Date 4. End Date 5. Duration 6.
				 * User 7. IP 8. Trace 9. Comments
				 */
				// 662868

				

				log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
				log4j.info("----------------------------------------------------------");

			} catch (Exception e) {
				gstrTCID = "FTS-126295";
				gstrTO = "When a status type ST is added for resource RS1 of type RT1 and ST is also associated with resource type RT2 (where resource RS2 is created under RT2), the 'Status Detail report (step 2 of 2)' page displays resources of both the resource types.";
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

		// end//testFTS126295//

		//start//testFTS126297//
		/***************************************************************
		'Description		:Update status of a multi status type MST added at the resource level for a resource RS.
		                     Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role 
		                     with view right for MST can view the status of MST in the generated status detail report.
		'Precondition		:
		'Arguments			:None
		'Returns			:None
		'Date				:10/9/2013
		'Author				:QSG
		'---------------------------------------------------------------
		'Modified Date				          Modified By
		'Date					              Name
		***************************************************************/

		@Test
		public void testFTS126297() throws Exception {
			boolean blnLogin = false;
			String strFuncResult = "";
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();
			Login objLogin = new Login();// object of class Login
			Roles objRole = new Roles();
			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();
			try {
				gstrTCID = "126297"; // Test Case Id
				gstrTO = " Update status of a multi status type MST added at the resource level for a resource RS. " +
						"Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role with view right " +
						"for MST can view the status of MST in the generated status detail report.";// Test// Objective
				gstrReason = "";
				gstrResult = "FAIL";		

				Date_Time_settings dts = new Date_Time_settings();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				gstrTimetake = dts.timeNow("HH:mm:ss");
				String strFILE_PATH = pathProps.getProperty("TestData_path");
				propElementAutoItDetails = objAP.ReadAutoit_FilePath();
				String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

				// login details
				String strLoginUserName = rdExcel.readData("Login", 3, 1);
				String strLoginPassword = rdExcel.readData("Login", 3, 2);
				String strRegn = rdExcel.readData("Login", 3, 4);

				//ST
				String strNumTypeName = "AutoNSt_" + strTimeText;
				String strNumStatTypDefn = "Auto";
				String strSTvalue[] = new String[2];

				String strStatTypeColor = "Black";
				String strStatusName1 = "Sta" + strTimeText;
				String strStatusName2 = "Stb" + strTimeText;

				String strStatusValue[] = new String[2];
				strStatusValue[0] = "";
				strStatusValue[1] = "";
				String statMultiTypeName1 = "MST_1" + strTimeText;
				String strStatusTypeValue = "Multi";
				String strStatTypDefn = "Automation";
				String strStatValue = "";
				// RT
				String strResType = "AutoRt_" + strTimeText;
				String strRsTypeValues[] = new String[1];
				// RS
				String strResource = "AutoRs_" + strTimeText;
				String strRSValue[] = new String[1];
				String strStandResType = "Aeromedical";
				String strContFName = "auto";
				String strContLName = "qsg";
				String strAbbrv = "Rs";
				String strState = "Alabama";
				String strCountry = "Autauga County";
				// Role
				String strRoleName = "AutoR_" + strTimeText;
				String strRoleRights[][] = {};
				String strRoleValue = "";

				String strApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				// Search user criteria
				String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel("User_Template",
						7, 12, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
						13, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
						14, strFILE_PATH);

				String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
				String strUsrFulName_1 = strUserName_1;
				String strInitPwd = rdExcel.readData("Login", 4, 2);
				String strConfirmPwd = rdExcel.readData("Login", 4, 2);

				String strUserName_2 = "AutoUsr_1" + System.currentTimeMillis();
				String strUsrFulName_2 = strUserName_2;
				String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");			
				// Application time
				String strUpdatdVale = "";
				String strUpdatedDate = "";
				String strTimeUpdateSystem = "";
				String strReportGenrtTime = "";
				String strUpdatdGenrtdValeSystem_2 = "";
				// Auto IT
				String strPDFDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_"
						+ gstrTCID + "_" + strTimeText + ".pdf";
				String[] strTestDataPDF1 = null;
				String[] strTestDataCSV1 = null;

				String strAutoFileName = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Name");
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Path");
				String strCSVDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_"
						+ gstrTCID + "_" + strTimeText + ".csv";
				/*
				 * 
				 * STEP : Action:Preconditions: <br> <br>1. Status Type MST(multi
				 * status type) is created with statuses S1 and S2. <br> <br>2.
				 * Resource type RT is associated with status type ST1. <br> <br>3.
				 * Resources RS is created under RT. <br> <br>4. Status Type
				 * MST(multi status type) is added for resource RS at the resource
				 * level <br> <br>5. User U1 has following rights: <br> <br>a.
				 * Report - Status Detail. <br>b. Role to View status type MST.
				 * <br>c.'View Resource' and 'Run Report' rights on resources RS.
				 * <br> <br>6.Status Type MST is updated with some statuses S1 or S2
				 * on day D1. Expected Result:No Expected Result
				 */
				// 662869
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

				// Creating ST
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.navStatusTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strStatusTypeValue1 = "Number";
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusTypeValue1,
							strNumTypeName, strNumStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[0] = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, strNumTypeName);

					if (strSTvalue[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);

					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusTypeValue,
							statMultiTypeName1, strStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strStatValue = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, statMultiTypeName1);
					if (strStatValue.compareTo("") != 0) {
						strFuncResult = "";
						strSTvalue[1] = strStatValue;
					} else {
						strFuncResult = "Failed to fetch status value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.createSTWithinMultiTypeST(
							seleniumPrecondition, statMultiTypeName1,
							strStatusName1, strStatusTypeValue, strStatTypeColor,
							true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.createSTWithinMultiTypeST(
							seleniumPrecondition, statMultiTypeName1,
							strStatusName2, strStatusTypeValue, strStatTypeColor,
							true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strStatValue = objST.fetchStatValInStatusList(
							seleniumPrecondition, statMultiTypeName1,
							strStatusName1);
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
					strStatValue = objST.fetchStatValInStatusList(
							seleniumPrecondition, statMultiTypeName1,
							strStatusName2);
					if (strStatValue.compareTo("") != 0) {
						strFuncResult = "";
						strStatusValue[1] = strStatValue;
					} else {
						strFuncResult = "Failed to fetch status value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating RT
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.resrcTypeMandatoryFldsNew(
							seleniumPrecondition, strResType, strSTvalue[0]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
							strResType);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRsTypeValues[0] = objRT.fetchResTypeValueInResTypeList(
							seleniumPrecondition, strResType);

					if (strRsTypeValues[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating RS
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
							strResType, strContFName, strContLName, strState,
							strCountry, strStandResType);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strResVal = objRs.fetchResValueInResList(
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
					blnLogin = true;

					strFuncResult = objRs.navToEditResLevelSTPage(
							seleniumPrecondition, strResource);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					blnLogin = true;

					strFuncResult = objRs.selDeselctSTInEditRSLevelPage(
							seleniumPrecondition, strSTvalue[1], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// create role to update
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.CreateRoleWithAllFields(
							seleniumPrecondition, strRoleName, strRoleRights,
							strSTvalue, false, strSTvalue, false, false);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[][] strSTViewValue = { { strSTvalue[1], "true" } };
					String[][] strSTUpdateValue = { { strSTvalue[1], "true" } };
					strFuncResult = objRole.slectAndDeselectSTInCreateRole(
							seleniumPrecondition, false, false, strSTViewValue,
							strSTUpdateValue, true);

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

				// Update user
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navUserListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navToCreateUserPage(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
							seleniumPrecondition, strUserName_1, strInitPwd,
							strConfirmPwd, strUsrFulName_1);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.selectResourceRights(
							seleniumPrecondition, strResource, false, false, true,
							true);

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
					String strOptions = propElementDetails
							.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
					strFuncResult = objCreateUsers.advancedOptns(
							seleniumPrecondition, strOptions, true);
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

				//Run Report user
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navToCreateUserPage(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
							seleniumPrecondition, strUserName_2, strInitPwd,
							strConfirmPwd, strUsrFulName_2);

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
					strFuncResult = objCreateUsers.slectAndDeselectRole(
							seleniumPrecondition, strRoleValue, true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
							seleniumPrecondition, strUserName_2, strByRole,
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

				try {
					assertEquals("", strFuncResult);
					blnLogin = false;
					strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
							strUserName_2, strInitPwd);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);
					blnLogin = true;

					String[] strEventStatType = {};
					String[] strRoleStatType = { statMultiTypeName1 };
					strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
							seleniumPrecondition, strResource, strEventStatType,
							strRoleStatType, false, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);

					String strUpdateST[] = objViewMap
							.updateMultiStatusTypeWithTime(seleniumPrecondition,
									strResource, statMultiTypeName1, strSTvalue[1],
									strStatusName1, strStatusValue[0],
									strStatusName2, strStatusValue[1], "HH:mm:ss");
					strTimeUpdateSystem = strUpdateST[1];				
					strFuncResult = strUpdateST[0];
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);

					strUpdatdVale = seleniumPrecondition
							.getText("css=div.emsText.maxheight > span");

					strUpdatedDate = seleniumPrecondition.getText("//div/span" + "[text()='"
							+ strUpdatdVale + "']/parent::"
							+ "div/span[@class='time']");
					strUpdatedDate = strUpdatedDate.substring(1, 13);

					String strLastUpdArr[] = strUpdatedDate.split(" ");
					strUpdatedDate = strLastUpdArr[2];

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.logout(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
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
					strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.navEditRolesPge(seleniumPrecondition,
							strRoleName);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[][] strSTViewValue = { { strSTvalue[1], "true" } };
					String[][] strSTUpdateValue = { { strSTvalue[1], "false" } };
					strFuncResult = objRole.slectAndDeselectSTInCreateRole(
							seleniumPrecondition, false, false, strSTViewValue,
							strSTUpdateValue, true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.logout(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				log4j.info("~~~~~PRECONDITION - " + gstrTCID
						+ " EXECUTION ENDS~~~~~");
				

				
				/*
				 * STEP : Action:Login as User U1, navigate to Reports>>Status
				 * Reports, click on 'Status Detail' Expected Result:Status Detail
				 * Report (Step 1 of 2) screen is displayed with following options:
				 * Start Date (Date picker) End Date (Date Picker)
				 *Report Format (radio button with PDF and CSV format options)
				 * Status Type (Drop down)
				 */
				// 662870
				
				log4j.info("~~~~~TEST CASE - " + gstrTCID
						+ " EXECUTION STARTS~~~~~");
				try {
					assertEquals("", strFuncResult);
					blnLogin = false;
					strFuncResult = objLogin.newUsrLogin(selenium,
							strUserName_1, strInitPwd);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[] statTypeName = { statMultiTypeName1 };
					strFuncResult = objRep.verifyCalndrWidInStatusDetailReportNew(
							selenium, statTypeName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}


				/*
				 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
				 * Date' fields, select MST from 'Status Type' dropdown and click on
				 * 'Next'. Expected Result:'Status Detail Report (Step 2 of 2)'
				 * screen is displayed.
				 */
				// 662871
				

				try {
					assertEquals("", strFuncResult);

					String strReport[] = objRep
							.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
									strRSValue[0], strSTvalue[1], strApplTime,
									strApplTime, true, statMultiTypeName1,
									strStatusValue[0], "HH:mm:ss");

					String strUpdatdGenrtdValeSystem_1 = strReport[1];			

					strFuncResult = strReport[0];

					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTime = strStatusTime[2];

					strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

					String strDurationUpdat = strUpdatedDate;
					String strDurationGenerat = strReportGenrtTime;

					// Updating

					int intDurationDiff_2 = dts.getTimeDiff1(
							strUpdatdGenrtdValeSystem_1, strTimeUpdateSystem);

					double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

					double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
					String strDurationDiff_2 = Double.toString(dRounded_2);

					strTestDataPDF1 = new String[] {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strResource, strUpdatdVale,
							strCurrDate + " " + strDurationUpdat,
							strCurrDate + " " + strDurationGenerat,
							strDurationDiff_2, "(Update user)"+strUserName_2+"/"+"(Report user)"+strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strPDFDownlPath,
							"Status Detail Report need to be checked in PDF file"

					};

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					// Thread.sleep(20000);
					String strProcess = "";
					String strArgs[] = { strAutoFilePath, strPDFDownlPath };

					// AutoIt
					Runtime.getRuntime().exec(strArgs);

					int intCnt = 0;
					do {
						GetProcessList objGPL = new GetProcessList();
						strProcess = objGPL.GetProcessName();
						intCnt++;
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Select the statuses S1 or S2 under 'Statuses'
				 * section and resource 'RS' under 'Resources' section, and click on
				 * 'Generate Report'. Expected Result:Status detail Report is
				 * generated in the PDF format. Header and Footer are
				 * displayed in all the pages of the report with following details.
				 * Header:1. Start Date 2. End Date 3.Status
				 * type Footer: 1. Report Run By: (name of the user)
				 * 2. From: (Name of the Region) 3. On: MM/DD/YYYY HH:MM:SS
				 * (Time Zone)4. Intermedix Emsystems logo 5. Page number
				 * Details of resource RS are displayed appropriately with
				 * following columns:1. Status Value2. Status Start
				 * Date 3. Status End Date 4. Duration (Hrs) 5. User
				 *6. IP 7. Trace8. Comments 'Status Summary' section
				 * for the resource displays following details: 1. Status
				 * 2. Total Hours 3. % of Total Hours
				 */
				// 662872
				
				

				/*
				 * STEP : Action:Navigate to Reports>>Status Reports, click on
				 * 'Status Detail' Expected Result:'Status Detail Report (Step 1 of
				 * 2)' screen is displayed.
				 */
				// 662873
				
				try {
					assertEquals("", strFuncResult);
					selenium.selectWindow("");
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
				 * Date' fields, select Data File, Comma-separated (CSV) format,
				 * select NST and click on Next. Expected Result:Status Detail
				 * Report (Step 2 of 2)' screen is displayed.
				 */
				// 662874

				try {
					assertEquals("", strFuncResult);

					String strReport[] = objRep
							.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
									strRSValue[0], strSTvalue[1], strApplTime,
									strApplTime, false, statMultiTypeName1,
									strStatusValue[0], "HH:mm:ss");

					strUpdatdGenrtdValeSystem_2 = strReport[1];

					strFuncResult = strReport[0];

					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTime = strStatusTime[2];

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
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

					String strDurationUpdat = strUpdatedDate;
					String strDurationGenerat = strReportGenrtTime;

					// Updating

					int intDurationDiff_2 = dts.getTimeDiff1(
							strUpdatdGenrtdValeSystem_2, strTimeUpdateSystem);

					double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

					double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
					String strDurationDiff_2 = Double.toString(dRounded_2);

					strTestDataCSV1 = new String[] {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strResource, strUpdatdVale,
							strCurrDate + " " + strDurationUpdat,
							strCurrDate + " " + strDurationGenerat,
							strDurationDiff_2,"(Update user)"+strUserName_2+"/"+"(Report user)"+strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strCSVDownlPath,
							"Status Detail Report need to be checked in CSV file"

					};

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				File Pf1 = new File(strCSVDownlPath);
				File Cf1 = new File(strPDFDownlPath);
				if (Pf1.exists() && Cf1.exists()) {
					try {
						assertEquals("", strFuncResult);
						gstrResult = "PASS";

						String strWriteFilePath = pathProps
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
								"Status_Detail_Report");
						objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
								"Status_Detail_Report");
					} catch (AssertionError Ae) {
						gstrResult = "FAIL";
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}

				/*
				 * STEP : Action:Select the statuses S1 or S2 under 'Statuses'
				 * section and resource 'RS' under 'Resources' section, and click on
				 * 'Generate Report'. Expected Result:Status Detail Report is
				 * generated in the CSV (Comma Separated Values) format with
				 * sections 'Status Detail' and 'Status Summary'.'Status
				 * Detail' section displays following columns with appropriate data:
				 * 1. Resource2. Status3. Start Date 4. End
				 * Date 5. Duration 6. User 7. IP 8. Trace 9.
				 * Comments 'Status Summary' section displays following
				 * columns with appropriate data:1. Resource 2. Status
				 * 3. Total Hours4. % of Total Hours
				 */
				// 662875
				

				log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
				log4j.info("----------------------------------------------------------");

			} catch (Exception e) {
				gstrTCID = "FTS-126297";
				gstrTO = "Update status of a multi status type MST added at the resource level for a resource RS. Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role with view right for MST can view the status of MST in the generated status detail report.";
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

		// end//testFTS126297//

	//start//testFTS53546//
	/***************************************************************
	'Description		:	Verify that 'Status Detail Report' cannot be generated for refined saturation score status type on a resource. 
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/8/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS53546() throws Exception {

		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRole = new Roles();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Reports objRep = new Reports();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();

		try {
			gstrTCID = "53546"; // Test Case Id
			gstrTO = " 	Verify that 'Status Detail Report' cannot be generated for refined saturation score status type on a resource. ";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String statTypeName1 = "SST1" + strTimeText;
			String statTypeName2 = "SST2" + strTimeText;
			String strStatusTypeValue = "Saturation Score";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;

			/*
			 * STEP : Action:Preconditions: 1. Status Types MST1(multi status
			 * type) is created with statuses S1 and a role R1 to view the and
			 * update these status types. 2. Status Types NST2(number status
			 * type) is created with statuses S2 and a role R1 to view the and
			 * update these status types. 3. Resource type RT is associated with
			 * status types NST1 and NST2. 4. Resources RS is created under
			 * resource type RT. 5. User U1 has following rights: a. Report -
			 * Status Detail. b. Role R1. c.'View Resource' and 'Run Report'
			 * rights on resources RS. 6. Status type MST2 is refined form
			 * resource RS for User U1. 7. Status types MST1 and MST2 are
			 * updated with statuses S1 or S2 on day D1. Expected Result:No
			 * Expected Result
			 */
			// 317650

			log4j.info("~~~~~PRE - CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type SST1
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName2, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String[] strViewRightValue = { strSTvalue[0], strSTvalue[1] };
				String[] updateRightValue = { strSTvalue[0], strSTvalue[1] };
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
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
								+ strSTvalue[0] + "']");

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
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 3. Resources RS is created under RT.

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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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

			// User 1
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselAllResRights(
						seleniumPrecondition, false, false, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);

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

			// User 2

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
						seleniumPrecondition, strUserName_2, strInitPwd,
						strConfirmPwd, strUsrFulName_2);

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
				strFuncResult = objCreateUsers.selAndDeselAllResRights(
						seleniumPrecondition, false, false, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_2, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Refine
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navToRefineVisibleST(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						seleniumPrecondition, strSTvalue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(seleniumPrecondition);
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

			log4j.info("~~~~~PRE - CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			/*
			 * STEP : Action:Login as User U1, navigate to Reports >> Status
			 * Reports, click on 'Status Detail'. Expected Result:Status Detail
			 * Report (Step 1 of 2) screen is displayed. Adobe Acrobat (PDF) is
			 * selected by default. Status type MST2 is not listed in the
			 * 'Status Type' drop-down list.
			 */
			// 317651

			// User2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_2,
						strInitPwd);
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
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue2[] = { "11", "12", "13", "14", "15", "16",
						"17", "18", "19" };

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue2, strSTvalue[1]);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.checkPDFIsSelectedByDefault(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.chkSTPresentOrNotForReport(selenium,
						false, strSTvalue[1], statTypeName2);
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
			gstrTCID = "FTS-53546";
			gstrTO = "	Verify that 'Status Detail Report' cannot be generated for refined saturation score status type on a resource. ";
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

	// end//testFTS53546//
	//start//testFTS37146//
	/***********************************************************************************
	'Description	:Remove a number status type NST from a resource RS and generate the 
	                 'Status Detail Report' and verify that the data is displayed 
	                 appropriately in the  report
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/9/2013
	'Author			:QSG
	'-----------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***********************************************************************************/
	
	@Test
	public void testFTS37146() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Reports objRep=new Reports();
		Resources objResources = new Resources();
		Views objViews = new Views();
		ViewMap objViewMap=new ViewMap();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "37146"; // Test Case Id
			gstrTO = " Remove a number status type NST from a resource RS and"
					+ " generate the 'Status Detail Report' and verify that the data is "
					+ "displayed appropriately in the  report";// TO
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
			String strStatusTypeValues[] = new String[2];
			String statTypeName = "AutoST" + strTimeText;
			String strStatusTypeValue = "Number";
			String statTypeNameNST = "NST" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strRSValue[]=new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			// USER
			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
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
			1. Resource type RT1 is associated with status type ST1 and NOT with status type NST (number)
			2. Resources RS1 is created under RT1.
			3. Status Type NST is added for resource RS1 at the resource level
			4. User U1 has following rights:
			'Report - Status Detail'
			Role to update status type NST
			'Update Status' and 'Run Report' rights on resources RS1
		  Expected Result:No Expected Result
		*/
		//222408
			
		 log4j.info("~~~~~PRE-CONDITION- " + gstrTCID+ " EXECUTION ENDS~~~~~");
			
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeNameNST, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeNameNST);
				if (strStatusTypeValues[1].compareTo("") != 0) {
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
	
			// Creating RS
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
						strResrcTypName, strContFName, strContLName, strState,
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// User
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
						seleniumPrecondition, strUserNameA, strInitPwd,
						strConfirmPwd, strUsrFulNameA);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		
			log4j.info("~~~~~PRE-CONDITION- " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			/* 6.Status Type NST is updated with some value on day D1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}					
		/*
		* STEP :
		  Action:Login as user U1, update the status of NST on day D1 for resource RS1.
		  Expected Result:No Expected Result
		*/
		//222409
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
	
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
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
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"10", strStatusTypeValues[1], false, "", "");				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Resources
		  Expected Result:Resource list screen is displayed with the resource RS listed in the region.
		*/
		//222415
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Edit Status Types' link corresponding to resource RS
		  Expected Result:'Edit Resource-Level Status Types' screen is displayed
				  Status type NST is selected and enabled.
		*/
		//222416
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToEditResLevelSTPage_LinkChanged(selenium,
								strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.chkSTSelectedOrNotEditRsLevelPage(selenium,
								strStatusTypeValues[1], statTypeNameNST, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Deselect the check box NST and click on save
		  Expected Result:User is taken to Resource List screen.
		*/
		//222417
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222410
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1 as the date for 'Start Date' and 'End Date' fields, select PDF,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
		  The selected status type has no resources that you are authorized to view.
		   Please select another status type and try again.' is displayed
		*/
		//222411
			String strApplTime = dts.getTimeOfParticularTimeZone("CST",
					"M/d/yyyy");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTime,
								strApplTime, true, statTypeNameNST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222418
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1 as the date for 'Start Date' and 'End Date' fields, select CSV for report format,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
	      The selected status type has no resources that you are authorized to view. 
	      Please select another status type and try again.' is displayed
		*/
		//222421
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTime,
								strApplTime, false, statTypeNameNST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222488
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1-1 as the date for 'Start Date' and 'End Date' fields, select PDF,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
	      The selected status type has no resources that you are authorized to view.
	       Please select another status type and try again.' is displayed
		*/
		//222419
			String strApplTimeD1 = dts.getPastDayNew(1,"M/d/yyyy");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTimeD1,
								strApplTimeD1, true, statTypeNameNST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222441
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1-1 as the date for 'Start Date' and 'End Date' fields, select CSV for report format,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
	      The selected status type has no resources that you are authorized to view.
	       Please select another status type and try again.' is displayed
		*/
		//222442
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTimeD1,
								strApplTimeD1, false, statTypeNameNST);
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
			gstrTCID = "FTS-37146";
			gstrTO = "Remove a number status type NST from a resource RS and generate the 'Status Detail" +
					" Report' and verify that the data is displayed appropriately in the  report";
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
	
	// end//testFTS37146//
	
	//start//testFTS37183//
	/***********************************************************************************
	'Description	:Remove a multi status type MST from a resource RS and generate the 
	                 �Status Detail Report� and verify that the data is displayed 
	                 appropriately in the report 
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/9/2013
	'Author			:QSG
	'-----------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***********************************************************************************/
	
	@Test
	public void testFTS37183() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Reports objRep=new Reports();
		Resources objResources = new Resources();
		Views objViews = new Views();
		ViewMap objViewMap=new ViewMap();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "37183"; // Test Case Id
			gstrTO = " Remove a multi status type MST from a resource RS and generate the �Status Detail Report� " +
					"and verify that the data is displayed appropriately in the report ";// TO
			gstrReason = "";
			gstrResult = "FAIL";
	
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
	
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
	
			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName = "AutoST" + strTimeText;
			String strStatusTypeValue ="Multi";
			String statTypeNameMST = "MST" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String str_roleStatusName2 = "rSb" + strTimeTxt;
	
			String str_roleStatusValue[] = new String[2];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strRSValue[]=new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			// USER
			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
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
			1. Resource type RT1 is associated with status type ST1 and NOT with status type NST (number)
			2. Resources RS1 is created under RT1.
			3. Status Type NST is added for resource RS1 at the resource level
			4. User U1 has following rights:
			'Report - Status Detail'
			Role to update status type NST
			'Update Status' and 'Run Report' rights on resources RS1
		  Expected Result:No Expected Result
		*/
		//222408
			
		 log4j.info("~~~~~PRE-CONDITION- " + gstrTCID+ " EXECUTION ENDS~~~~~");
			
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeNameMST,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeNameMST);
				if (strStatusTypeValues[1].compareTo("") != 0) {
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
						seleniumPrecondition, statTypeNameMST, str_roleStatusName1,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeNameMST, str_roleStatusName2,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statTypeNameMST,
								str_roleStatusName1);
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
						.fetchStatValInStatusList(seleniumPrecondition, statTypeNameMST,
								str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
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
	
			// Creating RS
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
						strResrcTypName, strContFName, strContLName, strState,
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// User
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
						seleniumPrecondition, strUserNameA, strInitPwd,
						strConfirmPwd, strUsrFulNameA);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		
			log4j.info("~~~~~PRE-CONDITION- " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			/* 6.Status Type NST is updated with some value on day D1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}					
		/*
		* STEP :
		  Action:Login as user U1, update the status of NST on day D1 for resource RS1.
		  Expected Result:No Expected Result
		*/
		//222409
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
	
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
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
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						str_roleStatusValue[0], strStatusTypeValues[1], false,
						"", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Resources
		  Expected Result:Resource list screen is displayed with the resource RS listed in the region.
		*/
		//222415
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Edit Status Types' link corresponding to resource RS
		  Expected Result:'Edit Resource-Level Status Types' screen is displayed
				  Status type NST is selected and enabled.
		*/
		//222416
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToEditResLevelSTPage_LinkChanged(selenium,
								strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.chkSTSelectedOrNotEditRsLevelPage(selenium,
								strStatusTypeValues[1], statTypeNameMST, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Deselect the check box NST and click on save
		  Expected Result:User is taken to Resource List screen.
		*/
		//222417
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222410
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1 as the date for 'Start Date' and 'End Date' fields, select PDF,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
		  The selected status type has no resources that you are authorized to view.
		   Please select another status type and try again.' is displayed
		*/
		//222411
			String strApplTime = dts.getTimeOfParticularTimeZone("CST",
					"M/d/yyyy");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTime,
								strApplTime, true, statTypeNameMST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222418
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1 as the date for 'Start Date' and 'End Date' fields, select CSV for report format,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
	      The selected status type has no resources that you are authorized to view. 
	      Please select another status type and try again.' is displayed
		*/
		//222421
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTime,
								strApplTime, false, statTypeNameMST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222488
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1-1 as the date for 'Start Date' and 'End Date' fields, select PDF,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
	      The selected status type has no resources that you are authorized to view.
	       Please select another status type and try again.' is displayed
		*/
		//222419
			String strApplTimeD1 = dts.getPastDayNew(1,"M/d/yyyy");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTimeD1,
								strApplTimeD1, true, statTypeNameMST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222441
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1-1 as the date for 'Start Date' and 'End Date' fields, select CSV for report format,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
	      The selected status type has no resources that you are authorized to view.
	       Please select another status type and try again.' is displayed
		*/
		//222442
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTimeD1,
								strApplTimeD1, false, statTypeNameMST);
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
			gstrTCID = "FTS-37183";
			gstrTO = "Remove a multi status type MST from a resource RS and generate the �Status Detail Report�" +
					" and verify that the data is displayed appropriately in the report ";
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
	
	// end//testFTS37183//
	
	//start//testFTS37146//
	/***********************************************************************************
	'Description	:Remove a text status type TST from a resource RS and generate the 
	                 �Status Detail Report� and verify that the data is displayed 
	                 appropriately in the report
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/9/2013
	'Author			:QSG
	'-----------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***********************************************************************************/
	
	@Test
	public void testFTS37188() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Reports objRep=new Reports();
		Resources objResources = new Resources();
		Views objViews = new Views();
		ViewMap objViewMap=new ViewMap();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "37188"; // Test Case Id
			gstrTO = "Remove a text status type TST from a resource RS and generate the �Status Detail Report�" +
					" and verify that the data is displayed appropriately in the report";// TO
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
			String strStatusTypeValues[] = new String[2];
			String statTypeName = "AutoST" + strTimeText;
			String strStatusTypeValue = "Text";
			String statTypeNameTST = "TST" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strRSValue[]=new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			// USER
			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
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
			1. Resource type RT1 is associated with status type ST1 and NOT with status type NST (number)
			2. Resources RS1 is created under RT1.
			3. Status Type NST is added for resource RS1 at the resource level
			4. User U1 has following rights:
			'Report - Status Detail'
			Role to update status type NST
			'Update Status' and 'Run Report' rights on resources RS1
		  Expected Result:No Expected Result
		*/
		//222408
			
		 log4j.info("~~~~~PRE-CONDITION- " + gstrTCID+ " EXECUTION ENDS~~~~~");
			
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeNameTST, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeNameTST);
				if (strStatusTypeValues[1].compareTo("") != 0) {
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
	
			// Creating RS
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
						strResrcTypName, strContFName, strContLName, strState,
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// User
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
						seleniumPrecondition, strUserNameA, strInitPwd,
						strConfirmPwd, strUsrFulNameA);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		
			log4j.info("~~~~~PRE-CONDITION- " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			/* 6.Status Type NST is updated with some value on day D1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}					
		/*
		* STEP :
		  Action:Login as user U1, update the status of NST on day D1 for resource RS1.
		  Expected Result:No Expected Result
		*/
		//222409
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
	
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
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
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"10", strStatusTypeValues[1], false, "", "");				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Resources
		  Expected Result:Resource list screen is displayed with the resource RS listed in the region.
		*/
		//222415
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Edit Status Types' link corresponding to resource RS
		  Expected Result:'Edit Resource-Level Status Types' screen is displayed
				  Status type NST is selected and enabled.
		*/
		//222416
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToEditResLevelSTPage_LinkChanged(selenium,
								strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.chkSTSelectedOrNotEditRsLevelPage(selenium,
								strStatusTypeValues[1], statTypeNameTST, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Deselect the check box NST and click on save
		  Expected Result:User is taken to Resource List screen.
		*/
		//222417
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222410
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1 as the date for 'Start Date' and 'End Date' fields, select PDF,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
		  The selected status type has no resources that you are authorized to view.
		   Please select another status type and try again.' is displayed
		*/
		//222411
			String strApplTime = dts.getTimeOfParticularTimeZone("CST",
					"M/d/yyyy");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTime,
								strApplTime, true, statTypeNameTST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222418
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1 as the date for 'Start Date' and 'End Date' fields, select CSV for report format,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
	      The selected status type has no resources that you are authorized to view. 
	      Please select another status type and try again.' is displayed
		*/
		//222421
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTime,
								strApplTime, false, statTypeNameTST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222488
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1-1 as the date for 'Start Date' and 'End Date' fields, select PDF,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
	      The selected status type has no resources that you are authorized to view.
	       Please select another status type and try again.' is displayed
		*/
		//222419
			String strApplTimeD1 = dts.getPastDayNew(1,"M/d/yyyy");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTimeD1,
								strApplTimeD1, true, statTypeNameTST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222441
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1-1 as the date for 'Start Date' and 'End Date' fields, select CSV for report format,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
	      The selected status type has no resources that you are authorized to view.
	       Please select another status type and try again.' is displayed
		*/
		//222442
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTimeD1,
								strApplTimeD1, false, statTypeNameTST);
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
			gstrTCID = "FTS-37188";
			gstrTO = "Remove a text status type TST from a resource RS and generate the �Status Detail Report�" +
					" and verify that the data is displayed appropriately in the report";
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
	
	// end//testFTS37188//
	//start//testFTS53540//
	/***************************************************************
	'Description		:Verify that 'Status Detail Report' cannot be generated for refined text status type on a resource.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:10/8/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS53540() throws Exception {

		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRole = new Roles();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Reports objRep = new Reports();

		try {
			gstrTCID = "53540"; // Test Case Id
			gstrTO = " Verify that 'Status Detail Report' cannot be generated for refined text status type on a resource.";// Test
																															// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String statTypeName1 = "SST1" + strTimeText;
			String statTypeName2 = "SST2" + strTimeText;
			String strStatusTypeValue = "Text";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			/*
			 * STEP : Action:Preconditions: 1. Status Types TST1(text status
			 * type) is created and a role R1 to view the and update these
			 * status types. 2. Status Types TST2(text status type) is created
			 * and a role R1 to view the and update these status types. 3.
			 * Resource type RT is associated with status types TST1 and TST2.
			 * 4. Resources RS is created under resource type RT. 5. User U1 has
			 * following rights: a. Report - Status Detail. b. Role R1. c.'View
			 * Resource' and 'Run Report' rights on resources RS. 6. Status type
			 * TST2 is refined form resource RS for User U1. 7. Status types
			 * TST1 and TST2 are updated on day D1. Expected Result:No Expected
			 * Result
			 */
			// 317733

			log4j.info("~~~~~PRE - CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type SST1
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName2, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Role-based Status Types NST1(number), MST1(multi), TST1(text)
			 * and SST1(saturation score) are created with a role R1 to view and
			 * update these status types.
			 */
			try {
				assertEquals("", strFuncResult);

				String[] strViewRightValue = { strSTvalue[0], strSTvalue[1] };
				String[] updateRightValue = { strSTvalue[0], strSTvalue[1] };
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
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
								+ strSTvalue[0] + "']");

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
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 3. Resources RS is created under RT.

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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, false, true, true);

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

				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navToRefineVisibleST(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						seleniumPrecondition, strSTvalue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(seleniumPrecondition);
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
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE - CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as User U1, navigate to Reports >> Status
			 * Reports, click on 'Status Detail'. Expected Result:Status Detail
			 * Report (Step 1 of 2) screen is displayed. Adobe Acrobat (PDF) is
			 * selected by default. Status type TST2 is not listed in the
			 * 'Status Type' drop-down list.
			 */
			// 317734

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.checkPDFIsSelectedByDefault(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.chkSTPresentOrNotForReport(selenium,
						false, strSTvalue[1], statTypeName2);
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
			gstrTCID = "FTS-53540";
			gstrTO = "Verify that 'Status Detail Report' cannot be generated for refined text status type on a resource.";
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

	// end//testFTS53540//
	//start//testFTS37146//
	/***********************************************************************************
	'Description	:Remove a saturation score status type SST from a resource RS and generate 
					the �Status Detail Report� and verify that the data is 
					displayed appropriately in the report
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/9/2013
	'Author			:QSG
	'-----------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***********************************************************************************/
	
	@Test
	public void testFTS37194() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Reports objRep=new Reports();
		Resources objResources = new Resources();
		Views objViews = new Views();
		ViewMap objViewMap=new ViewMap();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "37194"; // Test Case Id
			gstrTO = " Remove a saturation score status type SST from a resource RS and"
					+ " generate the 'Status Detail Report' and verify that the data is "
					+ "displayed appropriately in the  report";// TO
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
			String strStatusTypeValues[] = new String[2];
			String statTypeName = "AutoST" + strTimeText;
			String strStatusTypeValue = "Saturation Score";
			String statTypeNameNST = "SST" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strRSValue[]=new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			// USER
			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
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
			1. Resource type RT1 is associated with status type ST1 and NOT with status type NST (number)
			2. Resources RS1 is created under RT1.
			3. Status Type NST is added for resource RS1 at the resource level
			4. User U1 has following rights:
			'Report - Status Detail'
			Role to update status type NST
			'Update Status' and 'Run Report' rights on resources RS1
		  Expected Result:No Expected Result
		*/
		//222408
			
		 log4j.info("~~~~~PRE-CONDITION- " + gstrTCID+ " EXECUTION ENDS~~~~~");
			
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeNameNST, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeNameNST);
				if (strStatusTypeValues[1].compareTo("") != 0) {
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
	
			// Creating RS
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
						strResrcTypName, strContFName, strContLName, strState,
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// User
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
						seleniumPrecondition, strUserNameA, strInitPwd,
						strConfirmPwd, strUsrFulNameA);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		
			log4j.info("~~~~~PRE-CONDITION- " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			/* 6.Status Type NST is updated with some value on day D1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}					
		/*
		* STEP :
		  Action:Login as user U1, update the status of NST on day D1 for resource RS1.
		  Expected Result:No Expected Result
		*/
		//222409
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
	
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
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
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = {  "1", "2", "3", "4", "5", "6",
						"7", "8","9"};

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strStatusTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Resources
		  Expected Result:Resource list screen is displayed with the resource RS listed in the region.
		*/
		//222415
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Edit Status Types' link corresponding to resource RS
		  Expected Result:'Edit Resource-Level Status Types' screen is displayed
				  Status type NST is selected and enabled.
		*/
		//222416
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToEditResLevelSTPage_LinkChanged(selenium,
								strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.chkSTSelectedOrNotEditRsLevelPage(selenium,
								strStatusTypeValues[1], statTypeNameNST, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Deselect the check box NST and click on save
		  Expected Result:User is taken to Resource List screen.
		*/
		//222417
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222410
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1 as the date for 'Start Date' and 'End Date' fields, select PDF,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
		  The selected status type has no resources that you are authorized to view.
		   Please select another status type and try again.' is displayed
		*/
		//222411
			String strApplTime = dts.getTimeOfParticularTimeZone("CST",
					"M/d/yyyy");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTime,
								strApplTime, true, statTypeNameNST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222418
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1 as the date for 'Start Date' and 'End Date' fields, select CSV for report format,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
	      The selected status type has no resources that you are authorized to view. 
	      Please select another status type and try again.' is displayed
		*/
		//222421
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTime,
								strApplTime, false, statTypeNameNST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222488
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1-1 as the date for 'Start Date' and 'End Date' fields, select PDF,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
	      The selected status type has no resources that you are authorized to view.
	       Please select another status type and try again.' is displayed
		*/
		//222419
			String strApplTimeD1 = dts.getPastDayNew(1,"M/d/yyyy");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTimeD1,
								strApplTimeD1, true, statTypeNameNST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Reports>>Status Reports, click on 'Status Detail'
		  Expected Result:Status Detail Report (Step 1 of 2)' screen is displayed
		*/
		//222441
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1-1 as the date for 'Start Date' and 'End Date' fields, select CSV for report format,
		   select NST and click on Next
		  Expected Result:Error message 'The following error occurred on this page:
	      The selected status type has no resources that you are authorized to view.
	       Please select another status type and try again.' is displayed
		*/
		//222442
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterReportStatDetailAndVerErrorMsg(selenium,
								strRSValue[0], strStatusTypeValues[1], strApplTimeD1,
								strApplTimeD1, false, statTypeNameNST);
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
			gstrTCID = "FTS-37194";
			gstrTO =  " Remove a saturation score status type SST from a resource RS and"
				+ " generate the 'Status Detail Report' and verify that the data is "
				+ "displayed appropriately in the  report";
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
	
	// end//testFTS37194//
	


	//start//testFTS126303//
		/***************************************************************
		'Description		:Update status of a multi status type MST added at the resource type level for a resource RS.
		                     Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role with
		                     view right for MST can view the status of MST in the generated status detail report.
		'Precondition		:
		'Arguments			:None
		'Returns			:None
		'Date				:10/9/2013
		'Author				:QSG
		'---------------------------------------------------------------
		'Modified Date				              Modified By
		'Date					                  Name
		***************************************************************/

	@Test
	public void testFTS126303() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Login objLogin = new Login();// object of class Login
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		try {
			gstrTCID = "126303"; // Test Case Id
			gstrTO = " Update status of a multi status type MST added at the resource type level for a resource RS. "
					+ "Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role with view "
					+ "right for MST can view the status of MST in the generated status detail report.";// Test//
																										// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String strSTvalue[] = new String[1];
			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			String statMultiTypeName1 = "MST_1" + strTimeText;
			String strStatusTypeValue = "Multi";
			String strStatTypDefn = "Automation";
			String strStatValue = "";
			// RT
			String strResType = "AutoRt_" + strTimeText;
			String strRsTypeValues[] = new String[1];
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strRSValue[] = new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strAbbrv = "Rs";
			String strState = "Alabama";
			String strCountry = "Autauga County";

			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strApplTime = dts.getTimeOfParticularTimeZone("CST",
					"M/d/yyyy");
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_2 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strTimeUpdateSystem = "";
			// Application time
			String strUpdatdVale = "";
			String strUpdatedDate = "";
			String strReportGenrtTime = "";

			String strUpdatdGenrtdValeSystem_2 = "";

			// Auto IT
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_"
					+ gstrTCID + "_" + strTimeText + ".pdf";
			String[] strTestDataPDF1 = null;
			String[] strTestDataCSV1 = null;

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_"
					+ gstrTCID + "_" + strTimeText + ".csv";

			/*
			 * STEP : Action:Preconditions:
			 * 
			 * 1. Status Type MST(multi status type) is created with statuses S1
			 * and S2.
			 * 
			 * 2. Resource type RT is associated with Status Type MST.
			 * 
			 * 3. Resources RS is created under RT.
			 * 
			 * 4. User U1 has following rights:
			 * 
			 * a. Report - Status Detail. b. Role to View status type MST.
			 * c.'View Resource' and 'Run Report' rights on resources RS.
			 * 
			 * 5.Status Type MST is updated with some statuses S1 or S2 on day
			 * D1. Expected Result:No Expected Result
			 */
			// 662891
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

			// Creating MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statMultiTypeName1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statMultiTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName1,
						strStatusName1, strStatusTypeValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName1,
						strStatusName2, strStatusTypeValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName1,
						strStatusName1);
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
				strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName1,
						strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResType, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResType);

				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RS1
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
						strResType, strContFName, strContLName, strState,
						strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(
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
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to view

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTvalue, false, strSTvalue, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strSTViewValue = { { strSTvalue[0], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[0], "true" } };
				strFuncResult = objRole.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTViewValue,
						strSTUpdateValue, true);

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

			// Update user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navToCreateUserPage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						seleniumPrecondition, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource, false, false, true,
						true);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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
				strFuncResult = objCreateUsers
						.navToCreateUserPage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						seleniumPrecondition, strUserName_2, strInitPwd,
						strConfirmPwd, strUsrFulName_2);

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_2, strByRole,
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

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName_2, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statMultiTypeName1 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						seleniumPrecondition, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strUpdateST[] = objViewMap
						.updateMultiStatusTypeWithTime(seleniumPrecondition,
								strResource, statMultiTypeName1, strSTvalue[0],
								strStatusName1, strStatusValue[0],
								strStatusName2, strStatusValue[1], "HH:mm:ss");
				strTimeUpdateSystem = strUpdateST[1];
				System.out.println(strTimeUpdateSystem);
				strFuncResult = strUpdateST[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = seleniumPrecondition
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = seleniumPrecondition.getText("//div/span"
						+ "[text()='" + strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
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
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navEditRolesPge(seleniumPrecondition,
						strRoleName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strSTViewValue = { { strSTvalue[0], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[0], "false" } };
				strFuncResult = objRole.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTViewValue,
						strSTUpdateValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			/*
			 * STEP : Action:Login as User U1, navigate to Reports>>Status
			 * Reports, click on 'Status Detail' Expected Result:'Status Detail
			 * Report (Step 1 of 2)' screen is displayed. <br> <br>(by default
			 * Adobe Acrobat (PDF) is selected)
			 */
			// 662892
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
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
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
			 * Date' fields, select MST from 'Status Type' dropdown and click on
			 * 'Next'. Expected Result:'Status Detail Report (Step 2 of 2)'
			 * screen is displayed.
			 */
			// 662893

			try {
				assertEquals("", strFuncResult);

				String strReport[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statMultiTypeName1,
								strStatusValue[0], "HH:mm:ss");

				String strUpdatdGenrtdValeSystem_1 = strReport[1];

				strFuncResult = strReport[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];

				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				// Updating

				int intDurationDiff_2 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_1, strTimeUpdateSystem);

				double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

				double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
				String strDurationDiff_2 = Double.toString(dRounded_2);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff_2, "Update "+strUserName_2+"/"+"Report"+strUserName_1,
						propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select the statuses S1 or S2 under 'Statuses'
			 * section and resource 'RS' under 'Resources' section, and click on
			 * 'Generate Report'. Expected Result:Status detail Report is
			 * generated in the PDF format.
			 * 
			 * Header and Footer are displayed in all the pages of the report
			 * with following details.
			 * 
			 * Header: 1. Start Date 2. End Date 3.Status type
			 * 
			 * Footer: 1. Report Run By: (name of the user) 2. From: (Name of
			 * the Region) 3. On: MM/DD/YYYY HH:MM:SS (Time Zone) 4. Intermedix
			 * Emsystems logo 5. Page number
			 * 
			 * Details of resource RS are displayed appropriately with following
			 * columns:
			 * 
			 * 1. Status Value 2. Status Start Date 3. Status End Date 4.
			 * Duration (Hrs) 5. User 6. IP 7. Trace 8. Comments 'Status
			 * Summary' section for the resource displays following details:
			 * 
			 * 1. Status 2. Total Hours 3. % of Total Hours
			 */
			// 662894

			/*
			 * STEP : Action:Navigate to Reports>>Status Reports, click on
			 * 'Status Detail' Expected Result:'Status Detail Report (Step 1 of
			 * 2)' screen is displayed.
			 */
			// 662895

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
			 * Date' fields, select Data File, Comma-separated (CSV) format,
			 * select NST and click on Next. Expected Result:Status Detail
			 * Report (Step 2 of 2)' screen is displayed.
			 */
			// 662896
			try {
				assertEquals("", strFuncResult);

				String strReport[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, false, statMultiTypeName1,
								strStatusValue[0], "HH:mm:ss");

				strUpdatdGenrtdValeSystem_2 = strReport[1];

				strFuncResult = strReport[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];

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
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				// Updating

				int intDurationDiff_2 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_2, strTimeUpdateSystem);

				double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

				double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
				String strDurationDiff_2 = Double.toString(dRounded_2);

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff_2, "Update "+strUserName_2+"/"+"Report"+strUserName_1,
						propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			File Pf1 = new File(strCSVDownlPath);
			File Cf1 = new File(strPDFDownlPath);
			if (Pf1.exists() && Cf1.exists()) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
							"Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrResult = "FAIL";
					gstrReason = gstrReason + " " + strFuncResult;
				}
			}

			/*
			 * STEP : Action:Select the statuses S1 or S2 under 'Statuses'
			 * section and resource 'RS' under 'Resources' section, and click on
			 * 'Generate Report'. Expected Result:Status Detail Report is
			 * generated in the CSV (Comma Separated Values) format with
			 * sections 'Status Detail' and 'Status Summary'.
			 * 
			 * 'Status Detail' section displays following columns with
			 * appropriate data:
			 * 
			 * 1. Resource 2. Status 3. Start Date 4. End Date 5. Duration 6.
			 * User 7. IP 8. Trace 9. Comments
			 * 
			 * 'Status Summary' section displays following columns with
			 * appropriate data:
			 * 
			 * 1. Resource 2. Status 3. Total Hours 4. % of Total Hours
			 */
			// 662897

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-126303";
			gstrTO = "Update status of a multi status type MST added at the resource type level for a resource RS. "
					+ "Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role with view"
					+ " right for MST can view the status of MST in the generated status detail report.";
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

	// end//testFTS126303//
		
	//start//testFTS126301//
		/***************************************************************
		'Description		:Update status of a text status type TST added at the resource level for a resource RS.
		                     Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role 
		                     with view right for TST can view the status of TST in the generated status detail report.
		'Precondition		:
		'Arguments			:None
		'Returns			:None
		'Date				:10/9/2013
		'Author				:QSG
		'---------------------------------------------------------------
		'Modified Date				             Modified By
		'Date					                 Name
		***************************************************************/

		@Test
		public void testFTS126301() throws Exception {
			boolean blnLogin = false;
			String strFuncResult = "";
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();
			Login objLogin = new Login();// object of class Login
			Roles objRole = new Roles();
			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();
			try {
				gstrTCID = "126301"; // Test Case Id
				gstrTO = " Update status of a text status type TST added at the resource level for a resource RS."
						+ " Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role with view"
						+ " right for TST can view the status of TST in the generated status detail report.";// Test//// Objective
				gstrReason = "";
				gstrResult = "FAIL";
				Date_Time_settings dts = new Date_Time_settings();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				gstrTimetake = dts.timeNow("HH:mm:ss");
				String strFILE_PATH = pathProps.getProperty("TestData_path");
				propElementAutoItDetails = objAP.ReadAutoit_FilePath();
				String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

				// login details
				String strLoginUserName = rdExcel.readData("Login", 3, 1);
				String strLoginPassword = rdExcel.readData("Login", 3, 2);
				String strRegn = rdExcel.readData("Login", 3, 4);

				// ST
				String strNumTypeName = "AutoNSt_" + strTimeText;
				String strNumStatTypDefn = "Auto";
				String strSTvalue[] = new String[2];
				String strTxtTypeName = "AutoTxt" + strTimeText;
				// RT
				String strResType = "AutoRt_" + strTimeText;
				String strRsTypeValues[] = new String[1];
				// RS
				String strResource = "AutoRs_" + strTimeText;
				String strRSValue[] = new String[1];
				String strStandResType = "Aeromedical";
				String strContFName = "auto";
				String strContLName = "qsg";
				String strAbbrv = "Rs";
				String strState = "Alabama";
				String strCountry = "Autauga County";
				// Role
				String strRoleName = "AutoR_" + strTimeText;
				String strRoleRights[][] = {};
				String strRoleValue = "";

				String strApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				// Search user criteria
				String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel("User_Template",
						7, 12, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
						13, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
						14, strFILE_PATH);

				String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
				String strUsrFulName_1 = strUserName_1;
				String strInitPwd = rdExcel.readData("Login", 4, 2);
				String strConfirmPwd = rdExcel.readData("Login", 4, 2);

				String strUserName_2 = "AutoUsr_1" + System.currentTimeMillis();
				String strUsrFulName_2 = strUserName_2;
				String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
				String strTimeUpdateSystem = "";
				// Application time
				String strUpdatdVale = "";
				String strUpdatedDate = "";
				String strReportGenrtTime = "";
				String strUpdatdGenrtdValeSystem_2 = "";
				// Auto IT
				String strPDFDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_"
						+ gstrTCID + "_" + strTimeText + ".pdf";
				String[] strTestDataPDF1 = null;
				String[] strTestDataCSV1 = null;

				String strAutoFileName = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Name");
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Path");
				String strCSVDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_"
						+ gstrTCID + "_" + strTimeText + ".csv";

				/*
				 * STEP : Action:Preconditions:
				 * 
				 * 1. Status Type TST(text status type) is created.
				 * 
				 * 2. Resource type RT is associated with status type ST1.
				 * 
				 * 3. Resources RS is created under RT.
				 * 
				 * 4. Status Type TST(text status type) is added for resource RS at
				 * the resource level.
				 * 
				 * 5. User U1 has following rights:
				 * 
				 * a. Report - Status Detail. b. Role to View status type TST. c.
				 * 'View Resource' and 'Run Report' rights on resources RS.
				 * 
				 * 6.Status Type TST is updated with some value on day D1. Expected
				 * Result:No Expected Result
				 */
				// 662877
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

				// Creating ST
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.navStatusTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strStatusTypeValue1 = "Number";
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusTypeValue1,
							strNumTypeName, strNumStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[0] = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, strNumTypeName);

					if (strSTvalue[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String strStatusTypeValue1 = "Text";
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusTypeValue1,
							strTxtTypeName, strNumStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[1] = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, strTxtTypeName);

					if (strSTvalue[1].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating RT
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.resrcTypeMandatoryFldsNew(
							seleniumPrecondition, strResType, strSTvalue[0]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
							strResType);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRsTypeValues[0] = objRT.fetchResTypeValueInResTypeList(
							seleniumPrecondition, strResType);

					if (strRsTypeValues[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating RS
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
							strResType, strContFName, strContLName, strState,
							strCountry, strStandResType);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strResVal = objRs.fetchResValueInResList(
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
					blnLogin = true;

					strFuncResult = objRs.navToEditResLevelSTPage(
							seleniumPrecondition, strResource);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					blnLogin = true;

					strFuncResult = objRs.selDeselctSTInEditRSLevelPage(
							seleniumPrecondition, strSTvalue[1], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// create role to update

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.CreateRoleWithAllFields(
							seleniumPrecondition, strRoleName, strRoleRights,
							strSTvalue, false, strSTvalue, false, false);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[][] strSTViewValue = { { strSTvalue[1], "true" } };
					String[][] strSTUpdateValue = { { strSTvalue[1], "true" } };
					strFuncResult = objRole.slectAndDeselectSTInCreateRole(
							seleniumPrecondition, false, false, strSTViewValue,
							strSTUpdateValue, true);

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
				// Update user
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navUserListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navToCreateUserPage(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
							seleniumPrecondition, strUserName_1, strInitPwd,
							strConfirmPwd, strUsrFulName_1);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.selectResourceRights(
							seleniumPrecondition, strResource, false, false, true,
							true);

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
					String strOptions = propElementDetails
							.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
					strFuncResult = objCreateUsers.advancedOptns(
							seleniumPrecondition, strOptions, true);
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
					strFuncResult = objCreateUsers
							.navToCreateUserPage(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
							seleniumPrecondition, strUserName_2, strInitPwd,
							strConfirmPwd, strUsrFulName_2);

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
					strFuncResult = objCreateUsers.slectAndDeselectRole(
							seleniumPrecondition, strRoleValue, true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
							seleniumPrecondition, strUserName_2, strByRole,
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
				try {
					assertEquals("", strFuncResult);
					blnLogin = false;
					strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
							strUserName_2, strInitPwd);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);
					blnLogin = true;

					String[] strEventStatType = {};
					String[] strRoleStatType = { strTxtTypeName };
					strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
							seleniumPrecondition, strResource, strEventStatType,
							strRoleStatType, false, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);

					String strUpdate[] = { strSTvalue[1] };
					String strUpdateST[] = objViewMap.updateStatusTypeWithTime(
							seleniumPrecondition, strResource, strUpdate,
							"HH:mm:ss");
					strFuncResult = strUpdateST[0];

					strTimeUpdateSystem = strUpdateST[1];

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);

					strUpdatdVale = seleniumPrecondition
							.getText("css=div.emsText.maxheight > span");

					strUpdatedDate = seleniumPrecondition.getText("//div/span"
							+ "[text()='" + strUpdatdVale + "']/parent::"
							+ "div/span[@class='time']");
					strUpdatedDate = strUpdatedDate.substring(1, 13);

					String strLastUpdArr[] = strUpdatedDate.split(" ");
					strUpdatedDate = strLastUpdArr[2];

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.logout(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
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
					strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.navEditRolesPge(seleniumPrecondition,
							strRoleName);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[][] strSTViewValue = { { strSTvalue[1], "true" } };
					String[][] strSTUpdateValue = { { strSTvalue[1], "false" } };
					strFuncResult = objRole.slectAndDeselectSTInCreateRole(
							seleniumPrecondition, false, false, strSTViewValue,
							strSTUpdateValue, true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.logout(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				log4j.info("~~~~~PRECONDITION - " + gstrTCID
						+ " EXECUTION ENDS~~~~~");

				/*
				 * STEP : Action:Login as User U1, navigate to Reports>>Status
				 * Reports, click on 'Status Detail'. Expected Result:'Status Detail
				 * Report (Step 1 of 2)' screen is displayed.
				 */
				// 662878
				log4j.info("~~~~~TEST CASE - " + gstrTCID
						+ " EXECUTION STARTS~~~~~");
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
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
				 * Date' fields, select TST from 'Status Type' dropdown and click on
				 * 'Next'. Expected Result:'Status Detail Report (Step 2 of 2)'
				 * screen is displayed.
				 */
				// 662879

				try {
					assertEquals("", strFuncResult);

					String strReport[] = objRep
							.enterReportSumDetailAndGenReportWitTime(selenium,
									strRSValue[0], strSTvalue[1], strApplTime,
									strApplTime, true, strTxtTypeName, "HH:mm:ss");

					String strUpdatdGenrtdValeSystem_1 = strReport[1];
					strFuncResult = strReport[0];

					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTime = strStatusTime[2];

					strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

					String strDurationUpdat = strUpdatedDate;
					String strDurationGenerat = strReportGenrtTime;

					// Updating

					int intDurationDiff_2 = dts.getTimeDiff1(
							strUpdatdGenrtdValeSystem_1, strTimeUpdateSystem);

					double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

					double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
					String strDurationDiff_2 = Double.toString(dRounded_2);

					strTestDataPDF1 = new String[] {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strResource, strUpdatdVale,
							strCurrDate + " " + strDurationUpdat,
							strCurrDate + " " + strDurationGenerat,
							strDurationDiff_2,"(Update user)"+strUserName_2+"/"+"(Report user)"+strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strPDFDownlPath,
							"Status Detail Report need to be checked in PDF file"

					};
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					// Thread.sleep(20000);
					String strProcess = "";
					String strArgs[] = { strAutoFilePath, strPDFDownlPath };

					// AutoIt
					Runtime.getRuntime().exec(strArgs);

					int intCnt = 0;
					do {
						GetProcessList objGPL = new GetProcessList();
						strProcess = objGPL.GetProcessName();
						intCnt++;
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				/*
				 * STEP : Action:Select the resource 'RS' under 'Resources' section,
				 * and click on 'Generate Report'. Expected Result:Status detail
				 * Report is generated in the PDF format.
				 * 
				 * Header and Footer are displayed in all the pages of the report
				 * with following details.
				 * 
				 * Header: 1. Start Date 2. End Date 3.Status type
				 * 
				 * Footer: 1. Report Run By: (name of the user) 2. From: (Name of
				 * the Region) 3. On: MM/DD/YYYY HH:MM:SS (Time Zone) 4. Intermedix
				 * Emsystems logo 5. Page number
				 * 
				 * Details of resource RS are displayed appropriately with following
				 * columns:
				 * 
				 * 1. Status Value 2. Status Start Date 3. Status End Date 4.
				 * Duration (Hrs) 5. User 6. IP 7. Trace 8. Comments
				 * 
				 * 'Status Summary' section for the resource displays following
				 * details:
				 * 
				 * 1. Status 2. Total Hours 3. % of Total Hours
				 */
				// 662880

				/*
				 * STEP : Action:Navigate to Reports>>Status Reports, click on
				 * 'Status Detail'. Expected Result:'Status Detail Report (Step 1 of
				 * 2)' screen is displayed.
				 */
				// 662881
				try {
					assertEquals("", strFuncResult);
					selenium.selectWindow("");
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
				 * Date' fields, select Data File, Comma-separated (CSV) format,
				 * select TST and click on Next. Expected Result:Status Detail
				 * Report (Step 2 of 2)' screen is displayed.
				 */
				// 662882

				try {
					assertEquals("", strFuncResult);

					String strReport[] = objRep
							.enterReportSumDetailAndGenReportWitTime(selenium,
									strRSValue[0], strSTvalue[1], strApplTime,
									strApplTime, false, strTxtTypeName, "HH:mm:ss");

					strUpdatdGenrtdValeSystem_2 = strReport[1];

					strFuncResult = strReport[0];

					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTime = strStatusTime[2];

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
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

					String strDurationUpdat = strUpdatedDate;
					String strDurationGenerat = strReportGenrtTime;

					// Updating

					int intDurationDiff_2 = dts.getTimeDiff1(
							strUpdatdGenrtdValeSystem_2, strTimeUpdateSystem);

					double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

					double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
					String strDurationDiff_2 = Double.toString(dRounded_2);

					strTestDataCSV1 = new String[] {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strResource, strUpdatdVale,
							strCurrDate + " " + strDurationUpdat,
							strCurrDate + " " + strDurationGenerat,
							strDurationDiff_2,"(Update user)"+strUserName_2+"/"+"(Report user)"+strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strCSVDownlPath,
							"Status Detail Report need to be checked in CSV file"

					};

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				File Pf1 = new File(strCSVDownlPath);
				File Cf1 = new File(strPDFDownlPath);
				if (Pf1.exists() && Cf1.exists()) {
					try {
						assertEquals("", strFuncResult);
						gstrResult = "PASS";

						String strWriteFilePath = pathProps
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
								"Status_Detail_Report");
						objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
								"Status_Detail_Report");
					} catch (AssertionError Ae) {
						gstrResult = "FAIL";
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
				/*
				 * STEP : Action:Select the resource 'RS' under 'Resources' section,
				 * and click on 'Generate Report'. Expected Result:Status Detail
				 * Report is generated in the CSV (Comma Separated Values) format
				 * with sections 'Status Detail' and 'Status Summary'.
				 * 
				 * 'Status Detail' section displays following columns with
				 * appropriate data:
				 * 
				 * 1. Resource 2. Status 3. Start Date 4. End Date 5. Duration 6.
				 * User 7. IP 8. Trace 9. Comments 'Status Summary' section displays
				 * following columns with appropriate data:
				 * 
				 * 1. Resource 2. Status 3. Total Hours 4. % of Total Hours
				 */
				// 662883

				log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
				log4j.info("----------------------------------------------------------");

			} catch (Exception e) {
				gstrTCID = "FTS-126301";
				gstrTO = "Update status of a text status type TST added at the resource level for a resource RS. Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role with view right for TST can view the status of TST in the generated status detail report.";
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

		// end//testFTS126301//
	//start//testFTS53451//
	/*******************************************************************************************
	'Description	:Update the status of a numeric private status type pNST associated with a 
	                 resource RS at the resource type level. Verify that a user with 'Run Report'
	                  and 'View Resource' rights on RS and without any role can view the status
	                   of pNST for RS in the generated status detail report.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/9/2013
	'Author			:QSG
	'-------------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	********************************************************************************************/

	@Test
	public void testFTS53451() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		General objGeneral = new General();

		try {
			gstrTCID = "53451"; // Test Case Id
			gstrTO = " Update the status of a numeric private status type pNST associated with a resource RS at the resource type level. Verify that a user with 'Run Report' and 'View Resource' rights on RS and without any role can view the status of pNST for RS in the generated status detail report.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyhhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statpNumTypeName = "pNST" + strTimeText;
			String strNSTValue = "Number";
			String strStatusTypeValues[] = new String[2];
			String strStatTypDefn = "Automation";

			// RT data
			String strResrctTypName = "RT" + System.currentTimeMillis();
			String strRTVal = "";

			String strRolesName = "Rol" + System.currentTimeMillis();
			String strRoleValue = "";

			// Resource
			String strResource = "RS" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// USER
			String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strPDFDownlPath_1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + "1.pdf";

			String strPDFDownlPath_2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + ".csv";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strApplTime = "";

			String strStatusGenerateDateEST1 = "";
			String strStatusGenerateDate = "";

			String strStatusUpdateDate1 = "";
			String strStatusUpdateDateEST1 = "";

			/*
			 * STEP : Action:Preconditions: 1. Private status Type pNST(numeric
			 * status type) is created. 2. Resource type RT is associated with
			 * private status type pNST. 3. Resources RS is created under RT. 4.
			 * User U1 has following rights: a. Report - Status Detail. b. Role
			 * to View private status type pNST. c.'View Resource' and 'Run
			 * Report' rights on resources RS. 5.Private status Type pNST is
			 * updated with some value on day D1. Expected Result:No Expected
			 * Result
			 */
			// 317228

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
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

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpNumTypeName);
				if (strStatusTypeValues[0].compareTo("") != 0) {
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
								+ strStatusTypeValues[0] + "']");

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
				strRTVal = objResourceTypes.fetchRTValueInRTList(
						seleniumPrecondition, strResrctTypName);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Resource
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
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

			// Role

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
				String[][] updateRightValue = { { strStatusTypeValues[0],
						"true" } };
				String[][] strViewRightValue = { { strStatusTypeValues[0],
						"true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strViewRightValue,
						updateRightValue, true);
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

			// User

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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

			log4j.info("~~~~~PRE - CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

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
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
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
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strStatusUpdateValue1 = "101";
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strStatusUpdateValue1, strStatusTypeValues[0], false,
						"", "");
				strStatusUpdateDateEST1 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss.SSS");
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
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strStatusUpdateValue1);

				strStatusUpdateDate1 = selenium.getText("//span[text()='"
						+ strStatusUpdateValue1 + ""
						+ "']/following-sibling::span[1]");
				strStatusUpdateDate1 = strStatusUpdateDate1.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate1.split(" ");
				String strSplitTime[] = strLastUpdArr[2].split(":");
				strStatusUpdateDate1 = strSplitTime[0] + ":" + strSplitTime[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as User U1, navigate to Reports >> Status
			 * Reports, click on 'Status Detail'. Expected Result:'Status Detail
			 * Report (Step 1 of 2)' screen is displayed. Adobe Acrobat (PDF) is
			 * selected by default.
			 */
			// 317235

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.checkPDFIsSelectedByDefault(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
			 * Date' fields, select pNST from 'Status Type' dropdown and click
			 * on 'Next'. Expected Result:'Status Detail Report (Step 2 of 2)'
			 * screen is displayed.
			 */
			// 317236

			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, true,
						statpNumTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select resource 'RS' under 'Resources' section, and
			 * click on 'Generate Report'. Expected Result:Status detail Report
			 * is generated in the PDF format. Header and Footer are displayed
			 * in all the pages of the report with following details. Header: 1.
			 * Start Date 2. End Date 3.Status type Footer: 1. Report Run By:
			 * (name of the user) 2. From: (Name of the Region) 3. On:
			 * MM/DD/YYYY HH:MM:SS (Time Zone) 4. Intermedix Emsystems logo 5.
			 * Page number Details of resource RS are displayed appropriately
			 * with following columns: 1. Status Value 2. Status Start Date 3.
			 * Status End Date 4. Duration (Hrs) 5. User 6. IP 7. Trace 8.
			 * Comments
			 */
			// 317237

			try {
				assertEquals("", strFuncResult);

				String[] strStatusName = {};

				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statpNumTypeName, strResource, false,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusTypeValues, false, strResVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");

				strStatusGenerateDateEST1 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss.SSS");

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");

				// strStatusGenerateDate = strCurentDat[2] +":"+
				// strCurentDat[3];
				strStatusGenerateDate = strStatusGenerateDateEST1.substring(0,
						5);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Reports >> Status Reports, click on
			 * 'Status Detail'. Expected Result:'Status Detail Report (Step 1 of
			 * 2)' screen is displayed.
			 */
			// 317244

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
			 * Date' fields, select Data File, Comma-separated (CSV) format,
			 * select pNST and click on Next. Expected Result:Status Detail
			 * Report (Step 2 of 2)' screen is displayed.
			 */
			// 317246

			/*
			 * STEP : Action:Select resource 'RS' under 'Resources' section, and
			 * click on 'Generate Report'. Expected Result:Status Detail Report
			 * is generated in the CSV (Comma Separated Values) format with
			 * section 'Status Detail'. 'Status Detail' section displays
			 * following columns with appropriate data: 1. Resource 2. Status 3.
			 * Start Date 4. End Date 5. Duration 6. User 7. IP 8. Trace 9.
			 * Comments
			 */
			// 317247

			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, false,
						statpNumTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strStatusName = {};

				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statpNumTypeName, strResource, false,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusTypeValues, false, strResVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");

				strStatusGenerateDateEST1 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss.SSS");

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");

				strStatusGenerateDate = strStatusGenerateDateEST1.substring(0,
						5);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCurrDate = dts.converDateFormat(strApplTime,
						"M/d/yyyy", "dd-MMM-yyyy");
				String strStartDate = strCurrDate + " " + strStatusUpdateDate1;
				String strEndDate = strCurrDate + " " + strStatusGenerateDate;
				int TimeDiff = dts.getTimeDiffWithTimeFormat(
						strStatusGenerateDateEST1, strStatusUpdateDateEST1,
						"HH:mm:ss.SSS");

				double fltDurationDiff = (double) TimeDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);

				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, strResource, strStatusUpdateValue1,
						strStartDate, strEndDate, strDurationDiff,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath_2,
						"Status Detail Report need to be checked in PDF file"

				};

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath,
						"Status_Detail_Report");
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
			gstrTCID = "FTS-53451";
			gstrTO = "Update the status of a numeric private status type pNST associated with a resource RS at the resource type level. Verify that a user with 'Run Report' and 'View Resource' rights on RS and without any role can view the status of pNST for RS in the generated status detail report.";
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

	// end//testFTS53451//
	//start//testFTS126282//
	/*********************************************************************************************
	'Description	:Update a status of a number status type NST that was added for a resource RS 
	                 at the resource level,  verify that the data is displayed appropriately in the
	                   generated 'Status Detail Report'
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/10/2013
	'Author			:QSG
	'---------------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***********************************************************************************************/

	@Test
	public void testFTS126282() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		General objGeneral = new General();

		try {
			gstrTCID = "126282"; // Test Case Id
			gstrTO = " Update a status of a number status type NST that was added for a resource RS at the resource level,  verify that the data is displayed appropriately in the  generated 'Status Detail Report'";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyhhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statNumTypeName1 = "NST1" + strTimeText;
			String statNumTypeName2 = "NST2" + strTimeText;

			String strNSTValue = "Number";
			String strStatusTypeValues[] = new String[2];
			String strStatTypDefn = "Automation";

			// RT data
			String strResrctTypName = "RT" + System.currentTimeMillis();
			String strRTVal = "";

			String strRolesName = "Rol" + System.currentTimeMillis();
			String strRoleValue = "";

			// Resource
			String strResource1 = "RS_1" + System.currentTimeMillis();
			String strResource2 = "RS_2" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal[] = new String[2];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// USER
			String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strPDFDownlPath_1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + "1.pdf";

			String strPDFDownlPath_2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + ".csv";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strApplTime = "";

			String strStatusGenerateDateEST1 = "";
			String strStatusGenerateDate = "";

			String strStatusUpdateDate1 = "";
			String strStatusUpdateDateEST1 = "";

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT1 is associated
			 * with status type ST1 and NOT with status type NST (number) 2.
			 * Resources RS1 and RS2 are created under RT1. 3. Status Type NST
			 * is added for resource RS1 at the resource level 4. User U1 has
			 * following rights: 'Report - Status Detail' Role to update status
			 * type NST 'Update Status' and 'Run Report' rights on resources RS1
			 * and RS2 Expected Result:No Expected Result
			 */
			// 662812

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
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

			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statNumTypeName1);
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statNumTypeName2);
				if (strStatusTypeValues[1].compareTo("") != 0) {
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
								+ strStatusTypeValues[1] + "']");
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
				strRTVal = objResourceTypes.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrctTypName);
				if (strRTVal.compareTo("") != 0) {
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
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strResVal[0].compareTo("") != 0) {
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
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal[1] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strResVal[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status type NST is added at the resource level
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.savAndVerifyEditRSLevelPage(seleniumPrecondition);
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
				String[][] updateRightValue = { { strStatusTypeValues[0],
						"true" } };
				String[][] strViewRightValue = { { strStatusTypeValues[0],
						"true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strViewRightValue,
						updateRightValue, true);
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

			// User

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strResVal[0],
						false, true, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource2, strResVal[1],
						false, true, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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

			/*
			 * STEP : Action:Login as user U1, update the status of NST on day
			 * D1 for resource RS1. Expected Result:No Expected Result
			 */
			// 662813

			log4j.info("~~~~~PRE - CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

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
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
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
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strStatusUpdateValue1 = "101";
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strStatusUpdateValue1, strStatusTypeValues[0], false,
						"", "");
				strStatusUpdateDateEST1 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss.SSS");
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
						strStatusUpdateValue1);

				strStatusUpdateDate1 = selenium.getText("//span[text()='"
						+ strStatusUpdateValue1 + ""
						+ "']/following-sibling::span[1]");
				strStatusUpdateDate1 = strStatusUpdateDate1.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate1.split(" ");
				String strSplitTime[] = strLastUpdArr[2].split(":");
				strStatusUpdateDate1 = strSplitTime[0] + ":" + strSplitTime[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Reports>>Status Reports, click on
			 * 'Status Detail' Expected Result:'Status Detail Report (Step 1 of
			 * 2)' screen is displayed with: <br> <br>1. 'Start Date' field
			 * (with calender widget) <br>2. 'End Date' field (with calender
			 * widget) <br>3. 'Report Format' (with PDF and CSV options, PDF is
			 * selected by default) <br>4. Status Types dropdown with status
			 * types (ST1, NST)
			 */
			// 662814

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.checkPDFIsSelectedByDefault(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
			 * Date' fields, select PDF, select NST and click on Next Expected
			 * Result:'Status Detail Report (Step 1 of 2)' screen is displayed
			 * with: 1. Status type NST 2. Resources RS1
			 */
			// 662815

			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, true,
						statNumTypeName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select RS1 and click on 'Generate Report' Expected
			 * Result:Status Detail Report is generated in the PDF format.
			 * Header and Footer is with following details. Header: 1. Start
			 * Date: dd-mm-yyyy 2. End Date: dd-mm-yyyy 3. Status Type Footer:
			 * 1. Report Run By: (name of the user) 2. From: (Name of the
			 * Region) 3. On: MM/DD/YYYY HH:MM:SS (Time Zone) 4. Intermedix
			 * Emsystems logo 5. Page number Details of resource RS1 are
			 * displayed appropriately with following columns: 1. Status Value
			 * 2. Status Start Date 3. Status End Date 4. Duration (Hrs) 5. User
			 * 6. IP 7. Trace 8. Comments
			 */
			// 662816

			try {
				assertEquals("", strFuncResult);

				String[] strStatusName = {};

				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statNumTypeName1, strResource1, false,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				String strStatusTypeValue[] = { strStatusTypeValues[0] };
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusTypeValue, false, strResVal[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");

				strStatusGenerateDateEST1 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss.SSS");

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");

				// strStatusGenerateDate = strCurentDat[2] +":"+
				// strCurentDat[3];
				strStatusGenerateDate = strStatusGenerateDateEST1.substring(0,
						5);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Reports>>Status Reports, click on
			 * 'Status Detail' Expected Result:No Expected Result
			 */
			// 662817

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
			 * Date' fields, select CSV for report format, select NST and click
			 * on Next Expected Result:No Expected Result
			 */
			// 662818

			/*
			 * STEP : Action:Select RS1 and click on 'Generate Report' Expected
			 * Result:Status Detail Report is generated in the CSV (Comma
			 * Separated Values) format with sections 'Status Detail' and
			 * 'Status Summary'. 'Status Detail' section displays following
			 * columns with appropriate data: 1. Resource 2. Status 3. Start
			 * Date 4. End Date 5. Duration 6. User 7. IP 8. Trace 9. Comments
			 */
			// 662819

			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, false,
						statNumTypeName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strStatusName = {};

				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statNumTypeName1, strResource1, false,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue[] = { strStatusTypeValues[0] };
				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusTypeValue, false, strResVal[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");

				strStatusGenerateDateEST1 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss.SSS");

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");

				strStatusGenerateDate = strStatusGenerateDateEST1.substring(0,
						5);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCurrDate = dts.converDateFormat(strApplTime,
						"M/d/yyyy", "dd-MMM-yyyy");
				String strStartDate = strCurrDate + " " + strStatusUpdateDate1;
				String strEndDate = strCurrDate + " " + strStatusGenerateDate;
				int TimeDiff = dts.getTimeDiffWithTimeFormat(
						strStatusGenerateDateEST1, strStatusUpdateDateEST1,
						"HH:mm:ss.SSS");

				double fltDurationDiff = (double) TimeDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);

				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, strResource1, strStatusUpdateValue1,
						strStartDate, strEndDate, strDurationDiff,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath_2,
						"Status Detail Report need to be checked in PDF file"

				};

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath,
						"Status_Detail_Report");
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
			gstrTCID = "FTS-126282";
			gstrTO = "Update a status of a number status type NST that was added for a resource RS at the resource level,  verify that the data is displayed appropriately in the  generated 'Status Detail Report'";
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

	// end//testFTS126282//

	//start//testFTS126334//
	/***************************************************************
	'Description	:Verify that 'Status Detail Report' cannot be generated for refined number
	  				 status type on a resource.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/10/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS126334() throws Exception {

		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRole = new Roles();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Reports objRep = new Reports();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();

		try {
			gstrTCID = "126334"; // Test Case Id
			gstrTO = " Verify that 'Status Detail Report' cannot be generated for refined number" +
					" status type on a resource.";//TO																															// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String statTypeName1 = "NST1" + strTimeText;
			String statTypeName2 = "NST2" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;

			/*
			 * STEP : Action:Preconditions: 1. Status Types NST1(number status
			 * type) is created and a role R1 to view the and update these
			 * status types. 2. Status Types NST2(number status type) is created
			 * and a role R1 to view the and update these status types. 3.
			 * Resource type RT is associated with status types NST1 and NST2.
			 * 4. Resources RS is created under resource type RT. 5. User U1 has
			 * following rights: a. Report - Status Detail. b. Role R1. c.'View
			 * Resource' and 'Run Report' rights on resources RS. 6. Status
			 * types NST1 and NST2 are updated 7. Status type NST2 is refined
			 * form resource RS for User U1. Expected Result:No Expected Result
			 */
			// 662931

			log4j.info("~~~~~PRE - CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type SST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName2, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strViewRightValue = { strSTvalue[0], strSTvalue[1] };
				String[] updateRightValue = { strSTvalue[0], strSTvalue[1] };
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
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
								+ strSTvalue[0] + "']");

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
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 3. Resources RS is created under RT.

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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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

			// User 1
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselAllResRights(
						seleniumPrecondition, false, false, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);
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

			// User 2

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
						seleniumPrecondition, strUserName_2, strInitPwd,
						strConfirmPwd, strUsrFulName_2);
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
				strFuncResult = objCreateUsers.selAndDeselAllResRights(
						seleniumPrecondition, false, false, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_2, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Refine
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						seleniumPrecondition, strSTvalue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(seleniumPrecondition);
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

			log4j.info("~~~~~PRE - CONDITION " + gstrTCID+ " EXECUTION ENDS~~~~~");
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as User U1, navigate to Reports >> Status
			 * Reports, click on 'Status Detail'. Expected Result:Status Detail
			 * Report (Step 1 of 2) screen is displayed. Adobe Acrobat (PDF) is
			 * selected by default. Status type NST2 is not listed in the
			 * 'Status Type' drop-down list.
			 */
			// 662932

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_2,
						strInitPwd);
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
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"10", strSTvalue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"20", strSTvalue[1], false, "", "");
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.checkPDFIsSelectedByDefault(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.chkSTPresentOrNotForReport(selenium,
						false, strSTvalue[1], statTypeName2);
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
			gstrTCID = "FTS-126334";
			gstrTO = "Verify that 'Status Detail Report' cannot be generated for refined number status type on a resource.";
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

	// end//testFTS126334//
	
   //start//testFTS53503//
	/*************************************************************************************
	'Description	:Update the status of a text private status type pTST associated with 
	                 a resource RS at the resource type level. Verify that a user with 'Run 
	                 Report' and 'View Resource' rights on RS and without any role can view
	                  the status of pTST for RS in the generated status detail report.
	'Arguments		:None
	'Returns		:None
	'Date			:10/10/2013
	'Author			:QSG
	'------------------------------------------------------------------------------------
	'Modified Date				                                             Modified By
	'Date					                                                 Name
	**************************************************************************************/

	@Test
	public void testFTS53503() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Reports objRep=new Reports();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral = new General();
		Resources objResources = new Resources();
		ViewMap objViewMap=new ViewMap();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "53503"; // Test Case Id
			gstrTO = " Update the status of a text private status type pTST associated with a resource"
					+ " RS at the resource type level. Verify that a user with 'Run Report' and "
					+ "'View Resource' rights on RS and without any role can view the status of "
					+ "pTST for RS in the generated status detail report.";// TO
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
			String strStatusTypeValue = "Text";
			String statTypeNameTST = "TST" + strTimeText;
			String strStatusTypeValues[] = new String[1];
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strRSValue[]=new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			// USER
			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			// System Time
			String strTimeGenerateSystem = "";
			String strTimeUpdateSystem = "";
			String strApplTime = "";
			String strTimePDFGenerateSystem = "";
			String strDurationDiffPDF = "";
			
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
			// Application time
			String strUpdatdVale = "";
			String strUpdatedDate = "";
			String strReportGenrtTime = "";
			String strReportGenrtTimePDF = "";
			
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String[] strTestDataCSV1 = null;
			String[] strTestDataPDF1 = null;
		/*
		* STEP :
		  Action:Preconditions: 
		  1. Private status Type pTST(Text status type) is created.
		  2. Resource type RT is associated with private status Type pTST.
		  3. Resources RS is created under RT.
		  4. User U1 has following rights:
		  a. Report - Status Detail. 
		  b. Role to View private status type pTST. 
		  c.'View Resource' and 'Run Report' rights on resources RS. 
		  5. Private status Type pTST is updated with some value on day D1.
		  Expected Result:No Expected Result
		*/
		//317442
         log4j.info("~~~~~PRE-CONDITION- " + gstrTCID+ " EXECUTION ENDS~~~~~");
			
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
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strStatusTypeValue, statTypeNameTST,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeNameTST);
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
	
			// Creating RS
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
						strResrcTypName, strContFName, strContLName, strState,
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
			
			// User
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
						seleniumPrecondition, strUserNameA, strInitPwd,
						strConfirmPwd, strUsrFulNameA);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		
			log4j.info("~~~~~PRE-CONDITION- " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			/* 6.Status Type NST is updated with some value on day D1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameTST };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						seleniumPrecondition, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Update Status
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue={strStatusTypeValues[0]};
				String strReportResult[] = objViewMap.updateStatusTypeWithTime(
						seleniumPrecondition, strResource, strSTvalue, "HH:mm:ss");
				strTimeUpdateSystem = strReportResult[1];
				strFuncResult = strReportResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = seleniumPrecondition
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = seleniumPrecondition.getText("//div/span"
						+ "[text()='" + strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Login as User U1, navigate to Reports >> Status Reports, click on 'Status Detail' .
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed
				  Adobe Acrobat (PDF) is selected by default
		*/
		//317443
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
	
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1 as the date for 'Start Date' and 'End Date' fields, select pTST from 'Status Type' dropdown and click on 'Next'.
		  Expected Result:'Status Detail Report (Step 2 of 2)' screen is displayed.
		*/
		//317444
			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strStatusTypeValues[0], strApplTime,
								strApplTime, true, statTypeNameTST, "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration "
						+ strDurationDiffPDF);

				// Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("PDF generation time "
						+ strReportGenrtTimePDF);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF, strUserNameA,
						propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strStatusTypeValues[0], strApplTime,
								strApplTime, false, statTypeNameTST, "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];

				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time " + strReportGenrtTime);

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
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("CSV generation duration " + strDurationDiff);

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff, strUserNameA,
						propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			File Pf1 = new File(strCSVDownlPath);
			File Cf1 = new File(strPDFDownlPath);

			if (Pf1.exists() && Cf1.exists()) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
							"Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-53503";
			gstrTO = "Update the status of a text private status type pTST associated with a " +
					"resource RS at the resource type level. Verify that a user with 'Run Report'" +
					" and 'View Resource' rights on RS and without any role can view the status" +
					" of pTST for RS in the generated status detail report.";
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

	// end//testFTS53503//
	
	//start//testFTS53506//
	/*******************************************************************************************
	'Description	:Update the status of a saturation score private status type pSST associated
	                 with a resource RS at the resource type level. Verify that a user with 'Run 
	                 Report' and 'View Resource' rights on RS and without any role can view the 
	                 status of pSST for RS in the generated status detail report.
	'Arguments		:None
	'Returns		:None
	'Date			:10/10/2013
	'Author			:QSG
	'-------------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	********************************************************************************************/

	@Test
	public void testFTS53506() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Reports objRep = new Reports();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral = new General();
		Resources objResources = new Resources();
		ViewMap objViewMap = new ViewMap();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "53506"; // Test Case Id
			gstrTO = " Update the status of a saturation score private status type pSST associated with a"
					+ " resource RS at the resource type level. Verify that a user with 'Run Report' and "
					+ "'View Resource' rights on RS and without any role can view the status of pSST for"
					+ " RS in the generated status detail report.";// TO
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
			String strStatusTypeValue = "Saturation Score";
			String statTypeNameSST = "SST" + strTimeText;
			String strStatusTypeValues[] = new String[1];
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strRSValue[] = new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			// USER
			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;

			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// System Time
			String strTimeGenerateSystem = "";
			String strTimeUpdateSystem = "";
			String strApplTime = "";
			String strTimePDFGenerateSystem = "";
			String strDurationDiffPDF = "";

			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
			// Application time
			String strUpdatdVale = "";
			String strUpdatedDate = "";
			String strReportGenrtTime = "";
			String strReportGenrtTimePDF = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_"
					+ gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_"
					+ gstrTCID + "_" + strTimeText + ".pdf";

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String[] strTestDataCSV1 = null;
			String[] strTestDataPDF1 = null;

		/*
		* STEP :
		  Action:Preconditions: 
		  1. Private status Type pSST(saturation score status type) is created. 
		  2. Resource type RT is associated with Private status Type pSST. 
		  3. Resources RS is created under RT. 
		  4. User U1 has following rights:
		    a. Report - Status Detail. 
		    b. Role to View private status type pNST. 
			c.'View Resource' and 'Run Report' rights on resources RS. 
		  5. Private status Type pSST is updated with some value on day D1.
		  Expected Result:No Expected Result
		*/
		//317485
           log4j.info("~~~~~PRE-CONDITION- " + gstrTCID+ " EXECUTION ENDS~~~~~");
			
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
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strStatusTypeValue, statTypeNameSST,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeNameSST);
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
	
			// Creating RS
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
						strResrcTypName, strContFName, strContLName, strState,
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
			
			// User
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
						seleniumPrecondition, strUserNameA, strInitPwd,
						strConfirmPwd, strUsrFulNameA);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		
			log4j.info("~~~~~PRE-CONDITION" +gstrTCID+ " EXECUTION ENDS~~~~~");
			
			/* 6.Status Type NST is updated with some value on day D1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameSST };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						seleniumPrecondition, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Update Status
			try {
				assertEquals("", strFuncResult);

				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				String[] strUpdateValue2 = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				String strUpdateResult[] = objStatusTypes
						.updateSatuScoreStatusTypeWitTime(seleniumPrecondition,
								strResource, statTypeNameSST, strStatusTypeValues[0],
								strUpdateValue1, strUpdateValue2, "393", "429",
								"HH:mm:ss");
				strTimeUpdateSystem = strUpdateResult[1];
				strFuncResult = strUpdateResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strUpdatdVale = seleniumPrecondition
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = seleniumPrecondition.getText("//div/span"
						+ "[text()='" + strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Login as User U1, navigate to Reports >> Status Reports, click on 'Status Detail'
		  Expected Result:'Status Detail Report (Step 1 of 2)' screen is displayed.
				  Adobe Acrobat (PDF) is selected by default
		*/
		//317486
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide D1 as the date for 'Start Date' and 'End Date' fields, select pSST from 'Status Type' dropdown and click on 'Next'.
		  Expected Result:'Status Detail Report (Step 2 of 2)' screen is displayed.
		*/
		//317487

			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strStatusTypeValues[0],
								strApplTime, strApplTime, true,
								statTypeNameSST, "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration "
						+ strDurationDiffPDF);

				// Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("PDF generation time "
						+ strReportGenrtTimePDF);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF, strUserNameA,
						propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"
				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strStatusTypeValues[0],
								strApplTime, strApplTime, false,
								statTypeNameSST, "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];

				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time " + strReportGenrtTime);

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
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("CSV generation duration " + strDurationDiff);

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff, strUserNameA,
						propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"
				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			File Pf1 = new File(strCSVDownlPath);
			File Cf1 = new File(strPDFDownlPath);

			if (Pf1.exists() && Cf1.exists()) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
							"Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-53506";
			gstrTO = "Update the status of a saturation score private status type pSST associated with a resource RS at the resource type level. Verify that a user with 'Run Report' and 'View Resource' rights on RS and without any role can view the status of pSST for RS in the generated status detail report.";
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

	//end//testFTS53506//

	//start//testFTS53523//
	/**************************************************************************
	'Description	:Verify that 'Status Detail Report' cannot be generated for
	                 refined multi status type on a resource.
	'Arguments		:None
	'Returns		:None
	'Date			:10/10/2013
	'Author			:QSG
	'--------------------------------------------------------------------------
	'Modified Date				                                    Modified By
	'Date					                                        Name
	***************************************************************************/

	@Test
	public void testFTS53523() throws Exception {
		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRole = new Roles();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Reports objRep = new Reports();
		ViewMap objViewMap=new ViewMap();
		try {
			gstrTCID = "53523"; // Test Case Id
			gstrTO = " Verify that 'Status Detail Report' cannot be generated for refined"
					+ " multi status type on a resource.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String statTypeName1 = "MST1" + strTimeText;
			String statTypeName2 = "MST2" + strTimeText;
			String strStatusTypeValue = "Multi";
			String strStatTypDefn = "Automation";
			String strStatValue = "";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[3];
			String strMulStatTypeColor = "Black";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

		/*
		* STEP :
		  Action:Preconditions:
		  1. Status Types MST1(multi status type) is created with statuses S1 and a role R1 to view the and update these status types.
		  2. Status Types NST2(number status type) is created with statuses S2 and a role R1 to view the and update these status types.
		  3. Resource type RT is associated with status types NST1 and NST2.
		  4. Resources RS is created under resource type RT.
		  5. User U1 has following rights:
		  a. Report - Status Detail.
		  b. Role R1.
		  c.'View Resource' and 'Run Report' rights on resources RS. 
		  6. Status type MST2 is refined form resource RS for User U1.
		  7. Status types MST1 and MST2 are updated with statuses S1 or S2 on day D1.
		  Expected Result:No Expected Result
		*/
		//317650
			log4j.info("~~~~~PRE - CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type MST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName1, strStatusName1,
						strStatusTypeValue, strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatsValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statTypeName1, strStatusName1);
				if (strStatsValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatsValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName2, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName2, strStatusName2,
						strStatusTypeValue, strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatsValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statTypeName2, strStatusName2);
				if (strStatsValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatsValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strViewRightValue = { strSTvalue[0], strSTvalue[1] };
				String[] updateRightValue = { strSTvalue[0], strSTvalue[1] };
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
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
								+ strSTvalue[0] + "']");
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
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 3. Resources RS is created under RT.

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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);
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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName_2, strInitPwd,
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
				strFuncResult = objCreateUsers
						.selAndDeselAllResRights(seleniumPrecondition,
								false, false, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, false, true);
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
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						seleniumPrecondition, strSTvalue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(seleniumPrecondition);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName_2, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap
						.navToRegionalMapView(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindowNew1(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(
						seleniumPrecondition, strResource, statTypeName1,
						strSTvalue[0], strStatusName1, strStatusValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindowNew1(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(
						seleniumPrecondition, strResource, statTypeName2,
						strSTvalue[1], strStatusName2, strStatusValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Login as User U1, navigate to Reports >> Status Reports, click on 'Status Detail'.
		  Expected Result:Status Detail Report (Step 1 of 2) screen is displayed.
				  Adobe Acrobat (PDF) is selected by default.
				  Status type MST2 is not listed in the 'Status Type' drop-down list.
		*/
		//317651
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.checkPDFIsSelectedByDefault(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.chkSTPresentOrNotForReport(selenium,
						false, strSTvalue[1], statTypeName2);
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
			gstrTCID = "FTS-53523";
			gstrTO = "Verify that 'Status Detail Report' cannot be generated for refined multi status type on a resource.";
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

	// end//testFTS53523//
	//start//testFTS126333//
	/***************************************************************
	'Description		:Verify that for a user with only 'View Resource' right on resource RS but without 'Run Report' right on that resource, RS is not listed in the 'Status Detail Report (Step 2 of 2)' screen.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:10/10/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS126333() throws Exception {

		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objST = new StatusTypes();
		Roles objRoles = new Roles();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();

		try {
			gstrTCID = "126333"; // Test Case Id
			gstrTO = " Verify that for a user with only 'View Resource' right on resource RS but without 'Run Report' right on that resource, RS is not listed in the 'Status Detail Report (Step 2 of 2)' screen.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyhhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statNumTypeName = "NST" + strTimeText;
			String strNSTValue = "Number";
			String strSTvalue[] = new String[2];
			String strStatTypDefn = "Automation";

			String strState = "Alabama";
			String strCountry = "Barbour County";

			// RT data
			String strResType = "RT" + System.currentTimeMillis();
			String strRTVal = "";

			String strRolesName = "Rol" + System.currentTimeMillis();
			String strRoleValue = "";

			// Resource
			String strResource = "RS" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// USER
			String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT1 is associated
			 * with status type ST 2. Resources RS1 is created under RT1. 4.
			 * User U1 has following rights: a. 'Report - Status Detail' b. Role
			 * to update status type NST c. 'View Resource' right on resource
			 * RS1 Expected Result:No Expected Result
			 */
			// 662925

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

			// Creating ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statNumTypeName);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResType, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTVal = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResType);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Resource

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
						strResType, strContFName, strContLName, strState,
						strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
				String[][] updateRightValue = { { strSTvalue[0], "true" } };
				String[][] strViewRightValue = { { strSTvalue[0], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strViewRightValue,
						updateRightValue, true);
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

			// User

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strResVal, false,
						true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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

			/*
			 * STEP : Action:Login as user U1,Navigate to Reports>>Status
			 * Reports, click on 'Status Detail' Expected Result:'Status Detail
			 * Report (Step 1 of 2)' screen is displayed with: 1. 'Start Date'
			 * field (Date picker) 2. 'End Date' field (Date picker) 3. 'Report
			 * Format' (with PDF and CSV options, PDF is selected by default) 4.
			 * Status Types dropdown with status types (ST)
			 */
			// 662927

			log4j.info("~~~~~PRE - CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

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
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
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
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strStatusUpdateValue1 = "101";
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strStatusUpdateValue1, strSTvalue[0], false, "", "");

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
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				assertFalse(selenium.isElementPresent(propElementDetails
						.getProperty("Prop676")));
				log4j.info("Reports tab is NOT  available for the User.");
			} catch (AssertionError Ae) {
				log4j.info("Reports tab is available for the User.");
				strFuncResult = "Reports tab is available for the User.";
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
			gstrTCID = "FTS-126333";
			gstrTO = "Verify that for a user with only 'View Resource' right on resource RS but without 'Run Report' right on that resource, RS is not listed in the 'Status Detail Report (Step 2 of 2)' screen.";
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

	// end//testFTS126333//
	
	//start//testFTS126293//
		/***************************************************************
		'Description		:Update a status of a multi status type MST that was added for a
		 					 resource RS at the resource level, verify that the data is displayed appropriately 
		 					 in the generated �Status Detail Report�
		'Precondition		:
		'Arguments			:None
		'Returns			:None
		'Date				:10/11/2013
		'Author				:QSG
		'---------------------------------------------------------------
		'Modified Date				          Modified By
		'Date					              Name
		***************************************************************/

		@Test
		public void testFTS126293() throws Exception {
			boolean blnLogin = false;
			String strFuncResult = "";
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();
			Login objLogin = new Login();// object of class Login
			Roles objRole = new Roles();
			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();
			try {
				gstrTCID = "126293"; // Test Case Id
				gstrTO = "Update a status of a multi status type MST that was added for"
						+ " a resource RS at the resource level, verify that the data is displayed appropriately in the"
						+ " generated �Status Detail Report�";// Test // Objective
				gstrReason = "";
				gstrResult = "FAIL";

				Date_Time_settings dts = new Date_Time_settings();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				gstrTimetake = dts.timeNow("HH:mm:ss");
				String strFILE_PATH = pathProps.getProperty("TestData_path");
				propElementAutoItDetails = objAP.ReadAutoit_FilePath();
				String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

				// login details
				String strLoginUserName = rdExcel.readData("Login", 3, 1);
				String strLoginPassword = rdExcel.readData("Login", 3, 2);
				String strRegn = rdExcel.readData("Login", 3, 4);

				// ST
				String strNumTypeName = "AutoNSt" + strTimeText;
				String strNumStatTypDefn = "Auto";
				String strSTvalue[] = new String[2];

				String strStatTypeColor = "Black";
				String strStatusName1 = "Sta" + strTimeText;
				String strStatusName2 = "Stb" + strTimeText;

				String strStatusValue[] = new String[2];
				strStatusValue[0] = "";
				strStatusValue[1] = "";
				String statMultiTypeName1 = "MST_1" + strTimeText;
				String strStatusTypeValue = "Multi";
				String strStatTypDefn = "Automation";
				String strStatValue = "";
				// RT
				String strResType = "AutoRt_" + strTimeText;
				String strRsTypeValues[] = new String[1];
				// RS
				String strResource = "AutoRs" + strTimeText;
				String strResource1 = "AutoRs_2" + strTimeText;
				String strRSValue[] = new String[2];
				String strStandResType = "Aeromedical";
				String strContFName = "auto";
				String strContLName = "qsg";
				String strAbbrv = "Rs";
				String strState = "Alabama";
				String strCountry = "Autauga County";
				// Role
				String strRoleName = "AutoR_" + strTimeText;
				String strRoleRights[][] = {};
				String strRoleValue = "";

				String strApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				// Search user criteria
				String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel("User_Template",
						7, 12, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
						13, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
						14, strFILE_PATH);

				String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
				String strUsrFulName_1 = strUserName_1;
				String strInitPwd = rdExcel.readData("Login", 4, 2);
				String strConfirmPwd = rdExcel.readData("Login", 4, 2);
				String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

				String strTimeUpdateSystem = "";
				// Application time
				String strUpdatdVale = "";
				String strUpdatedDate = "";
				String strReportGenrtTime = "";
				String strUpdatdGenrtdValeSystem_2 = "";

				// Auto IT
				String strPDFDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
				String[] strTestDataPDF1 = null;
				String[] strTestDataCSV1 = null;

				String strAutoFileName = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Name");
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Path");
				String strCSVDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";
				/*
				 * Preconditions: 1. Resource type RT1 is associated with status
				 * type ST1 and NOT with status type MST (Multi) 2. Resources RS1
				 * and RS2 are created under RT1. 3. Status Type MST is added for
				 * resource RS1 at the resource level 4. User U1 has following
				 * rights:'Report - Status Detail' Role to update status type
				 * MST'Update Status' and 'Run Report' rights on resources RS1 and
				 * RS2
				 */
				// 662869
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

				// Creating ST
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.navStatusTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strStatusTypeValue1 = "Number";
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusTypeValue1,
							strNumTypeName, strNumStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[0] = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, strNumTypeName);

					if (strSTvalue[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);

					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusTypeValue,
							statMultiTypeName1, strStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strStatValue = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, statMultiTypeName1);
					if (strStatValue.compareTo("") != 0) {
						strFuncResult = "";
						strSTvalue[1] = strStatValue;
					} else {
						strFuncResult = "Failed to fetch status value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				//Creating MST
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.createSTWithinMultiTypeST(
							seleniumPrecondition, statMultiTypeName1,
							strStatusName1, strStatusTypeValue, strStatTypeColor,
							true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.createSTWithinMultiTypeST(
							seleniumPrecondition, statMultiTypeName1,
							strStatusName2, strStatusTypeValue, strStatTypeColor,
							true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strStatValue = objST.fetchStatValInStatusList(
							seleniumPrecondition, statMultiTypeName1,
							strStatusName1);
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
					strStatValue = objST.fetchStatValInStatusList(
							seleniumPrecondition, statMultiTypeName1,
							strStatusName2);
					if (strStatValue.compareTo("") != 0) {
						strFuncResult = "";
						strStatusValue[1] = strStatValue;
					} else {
						strFuncResult = "Failed to fetch status value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating RT
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.resrcTypeMandatoryFldsNew(
							seleniumPrecondition, strResType, strSTvalue[0]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
							strResType);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRsTypeValues[0] = objRT.fetchResTypeValueInResTypeList(
							seleniumPrecondition, strResType);

					if (strRsTypeValues[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating RS
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
							strResType, strContFName, strContLName, strState,
							strCountry, strStandResType);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strResVal = objRs.fetchResValueInResList(
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

				//Creating RS2
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.createResourceWitLookUPadres(
							seleniumPrecondition, strResource1, strAbbrv,
							strResType, strContFName, strContLName, strState,
							strCountry, strStandResType);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strResVal = objRs.fetchResValueInResList(
							seleniumPrecondition, strResource1);

					if (strResVal.compareTo("") != 0) {
						strFuncResult = "";
						strRSValue[1] = strResVal;
					} else {
						strFuncResult = "Failed to fetch Resource value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				//Adding MST to RS1
				try {
					assertEquals("", strFuncResult);
					blnLogin = true;
					strFuncResult = objRs.navToEditResLevelSTPage(
							seleniumPrecondition, strResource);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					blnLogin = true;

					strFuncResult = objRs.selDeselctSTInEditRSLevelPage(
							seleniumPrecondition, strSTvalue[1], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// create role to update

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.CreateRoleWithAllFields(
							seleniumPrecondition, strRoleName, strRoleRights,
							strSTvalue, false, strSTvalue, false, false);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[][] strSTViewValue = { { strSTvalue[1], "true" } };
					String[][] strSTUpdateValue = { { strSTvalue[1], "true" } };
					strFuncResult = objRole.slectAndDeselectSTInCreateRole(
							seleniumPrecondition, false, false, strSTViewValue,
							strSTUpdateValue, true);

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

				//Creating  user
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navUserListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navToCreateUserPage(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
							seleniumPrecondition, strUserName_1, strInitPwd,
							strConfirmPwd, strUsrFulName_1);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.selectResourceRights(
							seleniumPrecondition, strResource, false, true, true,
							true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.selectResourceRights(
							seleniumPrecondition, strResource1, false, true, true,
							true);

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
					String strOptions = propElementDetails
							.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
					strFuncResult = objCreateUsers.advancedOptns(
							seleniumPrecondition, strOptions, true);
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
				log4j.info("~~~~~PRECONDITION - " + gstrTCID
						+ " EXECUTION ENDS~~~~~");

				/*
				 * Login as user U1, update the status of MST with status S1 on day
				 * D1 for resource RS1. Updated status value is displayed.
				 */
				log4j.info("~~~~~TEST CASE - " + gstrTCID
						+ " EXECUTION STARTS~~~~~");
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

					String[] strEventStatType = {};
					String[] strRoleStatType = { statMultiTypeName1 };
					strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
							selenium, strResource, strEventStatType,
							strRoleStatType, false, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);

					String strUpdateST[] = objViewMap
							.updateMultiStatusTypeWithTime(selenium, strResource,
									statMultiTypeName1, strSTvalue[1],
									strStatusName1, strStatusValue[0],
									strStatusName2, strStatusValue[1], "HH:mm:ss");
					strTimeUpdateSystem = strUpdateST[1];
					strFuncResult = strUpdateST[0];
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}
				try {
					assertEquals("", strFuncResult);

					strUpdatdVale = selenium
							.getText("css=div.emsText.maxheight > span");

					strUpdatedDate = selenium.getText("//div/span" + "[text()='"
							+ strUpdatdVale + "']/parent::"
							+ "div/span[@class='time']");
					strUpdatedDate = strUpdatedDate.substring(1, 13);

					String strLastUpdArr[] = strUpdatedDate.split(" ");
					strUpdatedDate = strLastUpdArr[2];

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Login as User U1, navigate to Reports>>Status
				 * Reports, click on 'Status Detail' Expected Result:Status Detail
				 * Report (Step 1 of 2) screen is displayed with following options:
				 * Start Date (Date picker) End Date (Date Picker) Report Format
				 * (radio button with PDF and CSV format options) Status Type (Drop
				 * down)
				 */
				// 662870

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[] statTypeName = { statMultiTypeName1 };
					strFuncResult = objRep.verifyCalndrWidInStatusDetailReportNew(
							selenium, statTypeName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
				 * Date' fields, select MST from 'Status Type' dropdown and click on
				 * 'Next'. Expected Result:'Status Detail Report (Step 2 of 2)'
				 * screen is displayed.
				 */
				// 662871

				try {
					assertEquals("", strFuncResult);

					String strReport[] = objRep
							.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
									strRSValue[0], strSTvalue[1], strApplTime,
									strApplTime, true, statMultiTypeName1,
									strStatusValue[0], "HH:mm:ss");

					String strUpdatdGenrtdValeSystem_1 = strReport[1];

					strFuncResult = strReport[0];

					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTime = strStatusTime[2];

					strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

					String strDurationUpdat = strUpdatedDate;
					String strDurationGenerat = strReportGenrtTime;

					// Updating

					int intDurationDiff_2 = dts.getTimeDiff1(
							strUpdatdGenrtdValeSystem_1, strTimeUpdateSystem);

					double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

					double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
					String strDurationDiff_2 = Double.toString(dRounded_2);

					strTestDataPDF1 = new String[] {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strResource, strUpdatdVale,
							strCurrDate + " " + strDurationUpdat,
							strCurrDate + " " + strDurationGenerat,
							strDurationDiff_2, strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strPDFDownlPath,
							"Status Detail Report need to be checked in PDF file"

					};

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					// Thread.sleep(20000);
					String strProcess = "";
					String strArgs[] = { strAutoFilePath, strPDFDownlPath };

					// AutoIt
					Runtime.getRuntime().exec(strArgs);

					int intCnt = 0;
					do {
						GetProcessList objGPL = new GetProcessList();
						strProcess = objGPL.GetProcessName();
						intCnt++;
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Select the statuses S1 or S2 under 'Statuses'
				 * section and resource 'RS1' under 'Resources' section, and click
				 * on 'Generate Report'. Expected Result:Status detail Report is
				 * generated in the PDF format. Header and Footer are displayed in
				 * all the pages of the report with following details. Header:1.
				 * Start Date 2. End Date 3.Status type Footer: 1. Report Run By:
				 * (name of the user) 2. From: (Name of the Region) 3. On:
				 * MM/DD/YYYY HH:MM:SS (Time Zone)4. Intermedix Emsystems logo 5.
				 * Page number Details of resource RS are displayed appropriately
				 * with following columns:1. Status Value2. Status Start Date 3.
				 * Status End Date 4. Duration (Hrs) 5. User6. IP 7. Trace8.
				 * Comments 'Status Summary' section for the resource displays
				 * following details: 1. Status 2. Total Hours 3. % of Total Hours
				 */
				// 662872

				/*
				 * STEP : Action:Navigate to Reports>>Status Reports, click on
				 * 'Status Detail' Expected Result:'Status Detail Report (Step 1 of
				 * 2)' screen is displayed.
				 */
				// 662873

				try {
					assertEquals("", strFuncResult);
					selenium.selectWindow("");
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
				 * Date' fields, select Data File, Comma-separated (CSV) format,
				 * select NST and click on Next. Expected Result:Status Detail
				 * Report (Step 2 of 2)' screen is displayed.
				 */
				// 662874

				try {
					assertEquals("", strFuncResult);

					String strReport[] = objRep
							.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
									strRSValue[0], strSTvalue[1], strApplTime,
									strApplTime, false, statMultiTypeName1,
									strStatusValue[0], "HH:mm:ss");

					strUpdatdGenrtdValeSystem_2 = strReport[1];

					strFuncResult = strReport[0];

					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTime = strStatusTime[2];

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
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

					String strDurationUpdat = strUpdatedDate;
					String strDurationGenerat = strReportGenrtTime;

					// Updating

					int intDurationDiff_2 = dts.getTimeDiff1(
							strUpdatdGenrtdValeSystem_2, strTimeUpdateSystem);

					double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

					double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
					String strDurationDiff_2 = Double.toString(dRounded_2);

					strTestDataCSV1 = new String[] {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strResource, strUpdatdVale,
							strCurrDate + " " + strDurationUpdat,
							strCurrDate + " " + strDurationGenerat,
							strDurationDiff_2, strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strCSVDownlPath,
							"Status Detail Report need to be checked in CSV file"

					};

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				File Pf1 = new File(strCSVDownlPath);
				File Cf1 = new File(strPDFDownlPath);
				if (Pf1.exists() && Cf1.exists()) {
					try {
						assertEquals("", strFuncResult);
						gstrResult = "PASS";

						String strWriteFilePath = pathProps
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
								"Status_Detail_Report");
						objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
								"Status_Detail_Report");
					} catch (AssertionError Ae) {
						gstrResult = "FAIL";
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}

				/*
				 * STEP : Action:Select the statuses S1 or S2 under 'Statuses'
				 * section and resource 'RS1' under 'Resources' section, and click
				 * on 'Generate Report'. Expected Result:Status Detail Report is
				 * generated in the CSV (Comma Separated Values) format with
				 * sections 'Status Detail' and 'Status Summary'.'Status Detail'
				 * section displays following columns with appropriate data: 1.
				 * Resource2. Status3. Start Date 4. End Date 5. Duration 6. User 7.
				 * IP 8. Trace 9. Comments 'Status Summary' section displays
				 * following columns with appropriate data:1. Resource 2. Status 3.
				 * Total Hours4. % of Total Hours
				 */
				// 662875

				log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
				log4j
						.info("----------------------------------------------------------");

			} catch (Exception e) {
				gstrTCID = "FTS-126293";
				gstrTO = "Update a status of a multi status type MST that was added for"
						+ " a resource RS at the resource level, verify that the data is displayed appropriately in the"
						+ " generated �Status Detail Report�";
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

		// end//testFTS126293//
		
		//start//testFTS126294//
		/***************************************************************
		'Description		:Update a status of a saturation score status type SST that was added for a resource 
							 RS at the resource level, verify that the data is displayed appropriately in the generated 
							 �Status Detail Report�
		'Precondition		:
		'Arguments			:None
		'Returns			:None
		'Date				:10/11/2013
		'Author				:QSG
		'---------------------------------------------------------------
		'Modified Date				             Modified By
		'Date					                 Name
		***************************************************************/

		@Test
		public void testFTS126294() throws Exception {
			boolean blnLogin = false;
			String strFuncResult = "";
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();
			Login objLogin = new Login();// object of class Login
			Roles objRole = new Roles();
			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();
			try {
				gstrTCID = "126294"; // Test Case Id
				gstrTO = "Update a status of a saturation score status type SST that was added for a resource "
						+ " RS at the resource level, verify that the data is displayed appropriately in the generated"
						+ " �Status Detail Report�";// Test// Objective
				gstrReason = "";
				gstrResult = "FAIL";
				Date_Time_settings dts = new Date_Time_settings();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				gstrTimetake = dts.timeNow("HH:mm:ss");
				String strFILE_PATH = pathProps.getProperty("TestData_path");
				propElementAutoItDetails = objAP.ReadAutoit_FilePath();
				String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

				// login details
				String strLoginUserName = rdExcel.readData("Login", 3, 1);
				String strLoginPassword = rdExcel.readData("Login", 3, 2);
				String strRegn = rdExcel.readData("Login", 3, 4);

				// ST
				String strNumTypeName = "AutoNSt_" + strTimeText;
				String strNumStatTypDefn = "Auto";
				String strSTvalue[] = new String[2];
				String strSSTypeName = "AutoSST" + strTimeText;

				// RT
				String strResType = "AutoRt_" + strTimeText;
				String strRsTypeValues[] = new String[1];
				// RS
				String strResource = "AutoRs_" + strTimeText;
				String strResource1 = "AutoRs_2" + strTimeText;
				String strRSValue[] = new String[2];
				String strStandResType = "Aeromedical";
				String strContFName = "auto";
				String strContLName = "qsg";
				String strAbbrv = "Rs";
				String strState = "Alabama";
				String strCountry = "Autauga County";
				// Role
				String strRoleName = "AutoR_" + strTimeText;
				String strRoleRights[][] = {};
				String strRoleValue = "";

				String strApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				// Search user criteria
				String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel("User_Template",
						7, 12, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
						13, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
						14, strFILE_PATH);

				String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
				String strUsrFulName_1 = strUserName_1;
				String strInitPwd = rdExcel.readData("Login", 4, 2);
				String strConfirmPwd = rdExcel.readData("Login", 4, 2);

				String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
				String strTimeUpdateSystem = "";
				// Application time
				String strUpdatdVale = "";
				String strUpdatedDate = "";
				String strReportGenrtTime = "";
				String strUpdatdGenrtdValeSystem_2 = "";

				// Auto IT
				String strPDFDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
				String[] strTestDataPDF1 = null;
				String[] strTestDataCSV1 = null;

				String strAutoFileName = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Name");
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Path");
				String strCSVDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";

				/*
				 * STEP : Action:Preconditions:
				 * 
				 * 1.Resource type RT1 is associated with status type ST1 and NOT
				 * with status type SST (Saturation Score)
				 * 
				 * 2. Resources RS1 and RS2 are created under RT1.
				 * 
				 * 3. Resources RS is created under RT.
				 * 
				 * 4. Status Type SST(saturation score status type) is added for
				 * resource RS at the resource level.
				 * 
				 * 5. User U1 has following rights:
				 * 
				 * a. Report - Status Detail. b. Role to View status type TST.
				 * c.'Update status' 'View Resource' and 'Run Report' rights on
				 * resources RS1 and RS2
				 * 
				 * 6.Status Type SST is updated with some value on day D1. Expected
				 * Result:No Expected Result
				 */
				// 662877
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

				// Creating ST
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.navStatusTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strStatusTypeValue1 = "Number";
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusTypeValue1,
							strNumTypeName, strNumStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[0] = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, strNumTypeName);

					if (strSTvalue[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				//Creating SST
				try {
					assertEquals("", strFuncResult);
					String strStatusTypeValue1 = "Saturation Score";
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusTypeValue1,
							strSSTypeName, strNumStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[1] = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, strSSTypeName);

					if (strSTvalue[1].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating RT
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.resrcTypeMandatoryFldsNew(
							seleniumPrecondition, strResType, strSTvalue[0]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
							strResType);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRsTypeValues[0] = objRT.fetchResTypeValueInResTypeList(
							seleniumPrecondition, strResType);

					if (strRsTypeValues[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating RS1
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
							strResType, strContFName, strContLName, strState,
							strCountry, strStandResType);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strResVal = objRs.fetchResValueInResList(
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
				// Creating RS2
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.createResourceWitLookUPadres(
							seleniumPrecondition, strResource1, strAbbrv,
							strResType, strContFName, strContLName, strState,
							strCountry, strStandResType);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strResVal = objRs.fetchResValueInResList(
							seleniumPrecondition, strResource);

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
					blnLogin = true;

					strFuncResult = objRs.navToEditResLevelSTPage(
							seleniumPrecondition, strResource);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					blnLogin = true;

					strFuncResult = objRs.selDeselctSTInEditRSLevelPage(
							seleniumPrecondition, strSTvalue[1], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// create role to update

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.CreateRoleWithAllFields(
							seleniumPrecondition, strRoleName, strRoleRights,
							strSTvalue, false, strSTvalue, false, false);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[][] strSTViewValue = { { strSTvalue[1], "true" } };
					String[][] strSTUpdateValue = { { strSTvalue[1], "true" } };
					strFuncResult = objRole.slectAndDeselectSTInCreateRole(
							seleniumPrecondition, false, false, strSTViewValue,
							strSTUpdateValue, true);

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
				//Creating  user
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navUserListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navToCreateUserPage(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
							seleniumPrecondition, strUserName_1, strInitPwd,
							strConfirmPwd, strUsrFulName_1);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.selectResourceRights(
							seleniumPrecondition, strResource, false, true, true,
							true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.selectResourceRights(
							seleniumPrecondition, strResource1, false, true, true,
							true);

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
					String strOptions = propElementDetails
							.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
					strFuncResult = objCreateUsers.advancedOptns(
							seleniumPrecondition, strOptions, true);
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

				log4j.info("~~~~~PRECONDITION - " + gstrTCID
						+ " EXECUTION ENDS~~~~~");
				/*
				 * STEP : Action:Login as user U1, update the status of SST on day
				 * D1 for resource RS1. Updated status value is displayed. navigate
				 * to Reports>>Status Reports, click on 'Status Detail'. Expected
				 * Result:'Status Detail Report (Step 1 of 2)' screen is displayed.
				 */
				// 662878
				log4j.info("~~~~~TEST CASE - " + gstrTCID
						+ " EXECUTION STARTS~~~~~");

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

					String[] strEventStatType = {};
					String[] strRoleStatType = { strSSTypeName };
					strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
							selenium, strResource, strEventStatType,
							strRoleStatType, false, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);
					String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
							"7", "8" };
					String[] strUpdateValue2 = { "1", "2", "3", "4", "5", "6", "7",
							"8", "9" };

					String strUpdateST[] = objST.updateSatuScoreStatusTypeWitTime(
							selenium, strResource, strSSTypeName, strSTvalue[1],
							strUpdateValue1, strUpdateValue2, "393", "429",
							"HH:mm:ss");
					strFuncResult = strUpdateST[0];

					strTimeUpdateSystem = strUpdateST[1];

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);

					strUpdatdVale = selenium
							.getText("css=div.emsText.maxheight > span");

					strUpdatedDate = selenium.getText("//div/span" + "[text()='"
							+ strUpdatdVale + "']/parent::"
							+ "div/span[@class='time']");
					strUpdatedDate = strUpdatedDate.substring(1, 13);

					String strLastUpdArr[] = strUpdatedDate.split(" ");
					strUpdatedDate = strLastUpdArr[2];

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[] statTypeName = { strSSTypeName };
					strFuncResult = objRep.verifyCalndrWidInStatusDetailReportNew(
							selenium, statTypeName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				/*
				 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
				 * Date' fields, select SST from 'Status Type' dropdown and click on
				 * 'Next'. Expected Result:'Status Detail Report (Step 2 of 2)'
				 * screen is displayed.
				 */
				// 662879

				try {
					assertEquals("", strFuncResult);

					String strReport[] = objRep
							.enterReportSumDetailAndGenReportWitTime(selenium,
									strRSValue[0], strSTvalue[1], strApplTime,
									strApplTime, true, strSSTypeName, "HH:mm:ss");
					String strUpdatdGenrtdValeSystem_1 = strReport[1];
					strFuncResult = strReport[0];

					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTime = strStatusTime[2];

					strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

					String strDurationUpdat = strUpdatedDate;
					String strDurationGenerat = strReportGenrtTime;

					// Updating

					int intDurationDiff_2 = dts.getTimeDiff1(
							strUpdatdGenrtdValeSystem_1, strTimeUpdateSystem);

					double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

					double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
					String strDurationDiff_2 = Double.toString(dRounded_2);

					strTestDataPDF1 = new String[] {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strResource, "393",
							strCurrDate + " " + strDurationUpdat,
							strCurrDate + " " + strDurationGenerat,
							strDurationDiff_2, strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strPDFDownlPath,
							"Status Detail Report need to be checked in PDF file"

					};
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					// Thread.sleep(20000);
					String strProcess = "";
					String strArgs[] = { strAutoFilePath, strPDFDownlPath };

					// AutoIt
					Runtime.getRuntime().exec(strArgs);

					int intCnt = 0;
					do {
						GetProcessList objGPL = new GetProcessList();
						strProcess = objGPL.GetProcessName();
						intCnt++;
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				/*
				 * STEP : Action:Select the resource 'RS' under 'Resources' section,
				 * and click on 'Generate Report'. Expected Result:Status detail
				 * Report is generated in the PDF format.
				 * 
				 * Header and Footer are displayed in all the pages of the report
				 * with following details.
				 * 
				 * Header: 1. Start Date 2. End Date 3.Status type
				 * 
				 * Footer: 1. Report Run By: (name of the user) 2. From: (Name of
				 * the Region) 3. On: MM/DD/YYYY HH:MM:SS (Time Zone) 4. Intermedix
				 * Emsystems logo 5. Page number
				 * 
				 * Details of resource RS are displayed appropriately with following
				 * columns:
				 * 
				 * 1. Status Value 2. Status Start Date 3. Status End Date 4.
				 * Duration (Hrs) 5. User 6. IP 7. Trace 8. Comments
				 */
				// 662880

				/*
				 * STEP : Action:Navigate to Reports>>Status Reports, click on
				 * 'Status Detail'. Expected Result:'Status Detail Report (Step 1 of
				 * 2)' screen is displayed.
				 */
				// 662881
				try {
					assertEquals("", strFuncResult);
					selenium.selectWindow("");
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
				 * Date' fields, select Data File, Comma-separated (CSV) format,
				 * select TST and click on Next. Expected Result:Status Detail
				 * Report (Step 2 of 2)' screen is displayed.
				 */
				// 662882

				try {
					assertEquals("", strFuncResult);

					String strReport[] = objRep
							.enterReportSumDetailAndGenReportWitTime(selenium,
									strRSValue[0], strSTvalue[1], strApplTime,
									strApplTime, false, strSSTypeName, "HH:mm:ss");

					strUpdatdGenrtdValeSystem_2 = strReport[1];

					strFuncResult = strReport[0];

					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTime = strStatusTime[2];

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
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

					String strDurationUpdat = strUpdatedDate;
					String strDurationGenerat = strReportGenrtTime;

					// Updating

					int intDurationDiff_2 = dts.getTimeDiff1(
							strUpdatdGenrtdValeSystem_2, strTimeUpdateSystem);

					double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

					double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
					String strDurationDiff_2 = Double.toString(dRounded_2);

					strTestDataCSV1 = new String[] {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strResource, "393",
							strCurrDate + " " + strDurationUpdat,
							strCurrDate + " " + strDurationGenerat,
							strDurationDiff_2, strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strCSVDownlPath,
							"Status Detail Report need to be checked in CSV file"

					};

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				File Pf1 = new File(strCSVDownlPath);
				File Cf1 = new File(strPDFDownlPath);
				if (Pf1.exists() && Cf1.exists()) {
					try {
						assertEquals("", strFuncResult);
						gstrResult = "PASS";

						String strWriteFilePath = pathProps
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
								"Status_Detail_Report");
						objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
								"Status_Detail_Report");
					} catch (AssertionError Ae) {
						gstrResult = "FAIL";
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
				/*
				 * STEP : Action:Select the resource 'RS' under 'Resources' section,
				 * and click on 'Generate Report'. Expected Result:Status Detail
				 * Report is generated in the CSV (Comma Separated Values) format
				 * with sections 'Status Detail' and 'Status Summary'.
				 * 
				 * 'Status Detail' section displays following columns with
				 * appropriate data:
				 * 
				 * ED Saturation Report as Heading Report Period: (as provided while
				 * generating report)
				 * 
				 * 'Status Detail' section displays following columns with
				 * appropriate data:
				 * 
				 * 1. Date Time 2. Resource 3. ED Beds Occupied 4. Pts in Lobby 5.
				 * Amb wait 6. General Admits 7. ICU Admits 8. 1 on 1 Pts 9. Excess
				 * Lobby Wait 10. RNs short-staffed 11. Assigned ED Beds 12. Lobby
				 * Capacity 13. Sat Score 14. Charge RN/Mgr 15. Physician 16.
				 * Comments
				 */
				// 662883

				log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
				log4j
						.info("----------------------------------------------------------");

			} catch (Exception e) {
				gstrTCID = "FTS-126294";
				gstrTO = "Update a status of a saturation score status type SST that was added for a resource "
						+ " RS at the resource level, verify that the data is displayed appropriately in the generated"
						+ " �Status Detail Report�";
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
		// end//FTS126294
		

		//start//testFTS126302//
		/***************************************************************
		'Description		:Update status of a text status type SST added at the resource level for a resource RS.
		                     Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role 
		                     with view right for TST can view the status of SST in the generated status detail report.
		'Precondition		:
		'Arguments			:None
		'Returns			:None
		'Date				:10/10/2013
		'Author				:QSG
		'---------------------------------------------------------------
		'Modified Date				             Modified By
		'Date					                 Name
		***************************************************************/

		@Test
		public void testFTS126302() throws Exception {
			boolean blnLogin = false;
			String strFuncResult = "";
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();
			Login objLogin = new Login();// object of class Login
			Roles objRole = new Roles();
			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();
			try {
				gstrTCID = "126302"; // Test Case Id
				gstrTO = "Update status of a saturation score status type SST added at the resource level for a resource RS. "
						+ "Verify that a user with �Run Report� and 'View Resource' rights on RS and with a role with view right"
						+ "for SST can view the status of SST in the generated status detail report.";// Test// Objective
				gstrReason = "";
				gstrResult = "FAIL";
				Date_Time_settings dts = new Date_Time_settings();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				gstrTimetake = dts.timeNow("HH:mm:ss");
				String strFILE_PATH = pathProps.getProperty("TestData_path");
				propElementAutoItDetails = objAP.ReadAutoit_FilePath();
				String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

				// login details
				String strLoginUserName = rdExcel.readData("Login", 3, 1);
				String strLoginPassword = rdExcel.readData("Login", 3, 2);
				String strRegn = rdExcel.readData("Login", 3, 4);

				// ST
				String strNumTypeName = "AutoNSt_" + strTimeText;
				String strNumStatTypDefn = "Auto";
				String strSTvalue[] = new String[2];
				String strSSTypeName = "AutoSST" + strTimeText;

				// RT
				String strResType = "AutoRt_" + strTimeText;
				String strRsTypeValues[] = new String[1];
				// RS
				String strResource = "AutoRs_" + strTimeText;
				String strRSValue[] = new String[1];
				String strStandResType = "Aeromedical";
				String strContFName = "auto";
				String strContLName = "qsg";
				String strAbbrv = "Rs";
				String strState = "Alabama";
				String strCountry = "Autauga County";
				// Role
				String strRoleName = "AutoR_" + strTimeText;
				String strRoleRights[][] = {};
				String strRoleValue = "";

				String strApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				// Search user criteria
				String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel("User_Template",
						7, 12, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
						13, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
						14, strFILE_PATH);

				String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
				String strUsrFulName_1 = strUserName_1;
				String strInitPwd = rdExcel.readData("Login", 4, 2);
				String strConfirmPwd = rdExcel.readData("Login", 4, 2);

				String strUserName_2 = "AutoUsr_1" + System.currentTimeMillis();
				String strUsrFulName_2 = strUserName_2;
				String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
				String strTimeUpdateSystem = "";
				// Application time
				String strUpdatdVale = "";
				String strUpdatedDate = "";
				String strReportGenrtTime = "";
				String strUpdatdGenrtdValeSystem_2 = "";

				// Auto IT
				String strPDFDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
				String[] strTestDataPDF1 = null;
				String[] strTestDataCSV1 = null;

				String strAutoFileName = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Name");
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Path");
				String strCSVDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";

				/*
				 * STEP : Action:Preconditions:
				 * 
				 * 1. Status Type SST(saturation score status type) is created.
				 * 
				 * 2. Resource type RT is associated with status type ST1.
				 * 
				 * 3. Resources RS is created under RT.
				 * 
				 * 4. Status Type SST(saturation score status type) is added for
				 * resource RS at the resource level.
				 * 
				 * 5. User U1 has following rights:
				 * 
				 * a. Report - Status Detail. b. Role to View status type TST. c.
				 * 'View Resource' and 'Run Report' rights on resources RS.
				 * 
				 * 6.Status Type SST is updated with some value on day D1. Expected
				 * Result:No Expected Result
				 */
				// 662877
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

				// Creating ST
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.navStatusTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strStatusTypeValue1 = "Number";
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusTypeValue1,
							strNumTypeName, strNumStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[0] = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, strNumTypeName);

					if (strSTvalue[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String strStatusTypeValue1 = "Saturation Score";
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusTypeValue1,
							strSSTypeName, strNumStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[1] = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, strSSTypeName);

					if (strSTvalue[1].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating RT
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.resrcTypeMandatoryFldsNew(
							seleniumPrecondition, strResType, strSTvalue[0]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
							strResType);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRsTypeValues[0] = objRT.fetchResTypeValueInResTypeList(
							seleniumPrecondition, strResType);

					if (strRsTypeValues[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating RS
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
							strResType, strContFName, strContLName, strState,
							strCountry, strStandResType);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strResVal = objRs.fetchResValueInResList(
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
					blnLogin = true;

					strFuncResult = objRs.navToEditResLevelSTPage(
							seleniumPrecondition, strResource);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					blnLogin = true;

					strFuncResult = objRs.selDeselctSTInEditRSLevelPage(
							seleniumPrecondition, strSTvalue[1], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// create role to update

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.CreateRoleWithAllFields(
							seleniumPrecondition, strRoleName, strRoleRights,
							strSTvalue, false, strSTvalue, false, false);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[][] strSTViewValue = { { strSTvalue[1], "true" } };
					String[][] strSTUpdateValue = { { strSTvalue[1], "true" } };
					strFuncResult = objRole.slectAndDeselectSTInCreateRole(
							seleniumPrecondition, false, false, strSTViewValue,
							strSTUpdateValue, true);

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
				// Update user
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navUserListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navToCreateUserPage(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
							seleniumPrecondition, strUserName_1, strInitPwd,
							strConfirmPwd, strUsrFulName_1);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.selectResourceRights(
							seleniumPrecondition, strResource, false, false, true,
							true);

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
					String strOptions = propElementDetails
							.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
					strFuncResult = objCreateUsers.advancedOptns(
							seleniumPrecondition, strOptions, true);
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
					strFuncResult = objCreateUsers
							.navToCreateUserPage(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
							seleniumPrecondition, strUserName_2, strInitPwd,
							strConfirmPwd, strUsrFulName_2);

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
					strFuncResult = objCreateUsers.slectAndDeselectRole(
							seleniumPrecondition, strRoleValue, true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
							seleniumPrecondition, strUserName_2, strByRole,
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
				try {
					assertEquals("", strFuncResult);
					blnLogin = false;
					strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
							strUserName_2, strInitPwd);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);
					blnLogin = true;

					String[] strEventStatType = {};
					String[] strRoleStatType = { strSSTypeName };
					strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
							seleniumPrecondition, strResource, strEventStatType,
							strRoleStatType, false, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);
					String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
							"7", "8" };
					String[] strUpdateValue2 = { "1", "2", "3", "4", "5", "6", "7",
							"8", "9" };

					String strUpdateST[] = objST.updateSatuScoreStatusTypeWitTime(
							seleniumPrecondition, strResource, strSSTypeName,
							strSTvalue[1], strUpdateValue1, strUpdateValue2, "393",
							"429", "HH:mm:ss");
					strFuncResult = strUpdateST[0];

					strTimeUpdateSystem = strUpdateST[1];

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);

					strUpdatdVale = seleniumPrecondition
							.getText("css=div.emsText.maxheight > span");

					strUpdatedDate = seleniumPrecondition.getText("//div/span"
							+ "[text()='" + strUpdatdVale + "']/parent::"
							+ "div/span[@class='time']");
					strUpdatedDate = strUpdatedDate.substring(1, 13);

					String strLastUpdArr[] = strUpdatedDate.split(" ");
					strUpdatedDate = strLastUpdArr[2];

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.logout(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
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
					strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.navEditRolesPge(seleniumPrecondition,
							strRoleName);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[][] strSTViewValue = { { strSTvalue[1], "true" } };
					String[][] strSTUpdateValue = { { strSTvalue[1], "false" } };
					strFuncResult = objRole.slectAndDeselectSTInCreateRole(
							seleniumPrecondition, false, false, strSTViewValue,
							strSTUpdateValue, true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.logout(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				log4j.info("~~~~~PRECONDITION - " + gstrTCID
						+ " EXECUTION ENDS~~~~~");

				/*
				 * STEP : Action:Login as User U1, navigate to Reports>>Status
				 * Reports, click on 'Status Detail'. Expected Result:'Status Detail
				 * Report (Step 1 of 2)' screen is displayed.
				 */
				// 662878
				log4j.info("~~~~~TEST CASE - " + gstrTCID
						+ " EXECUTION STARTS~~~~~");
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
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
				 * Date' fields, select SST from 'Status Type' dropdown and click on
				 * 'Next'. Expected Result:'Status Detail Report (Step 2 of 2)'
				 * screen is displayed.
				 */
				// 662879

				try {
					assertEquals("", strFuncResult);

					String strReport[] = objRep
							.enterReportSumDetailAndGenReportWitTime(selenium,
									strRSValue[0], strSTvalue[1], strApplTime,
									strApplTime, true, strSSTypeName, "HH:mm:ss");
					String strUpdatdGenrtdValeSystem_1 = strReport[1];
					strFuncResult = strReport[0];

					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTime = strStatusTime[2];

					strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

					String strDurationUpdat = strUpdatedDate;
					String strDurationGenerat = strReportGenrtTime;

					// Updating

					int intDurationDiff_2 = dts.getTimeDiff1(
							strUpdatdGenrtdValeSystem_1, strTimeUpdateSystem);

					double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

					double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
					String strDurationDiff_2 = Double.toString(dRounded_2);

					strTestDataPDF1 = new String[] {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strResource, "393",
							strCurrDate + " " + strDurationUpdat,
							strCurrDate + " " + strDurationGenerat,
							strDurationDiff_2, "(Update user)"+strUserName_2+"/"+"(Report user)"+strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strPDFDownlPath,
							"Status Detail Report need to be checked in PDF file"

					};
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					// Thread.sleep(20000);
					String strProcess = "";
					String strArgs[] = { strAutoFilePath, strPDFDownlPath };

					// AutoIt
					Runtime.getRuntime().exec(strArgs);

					int intCnt = 0;
					do {
						GetProcessList objGPL = new GetProcessList();
						strProcess = objGPL.GetProcessName();
						intCnt++;
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				/*
				 * STEP : Action:Select the resource 'RS' under 'Resources' section,
				 * and click on 'Generate Report'. Expected Result:Status detail
				 * Report is generated in the PDF format.
				 * 
				 * Header and Footer are displayed in all the pages of the report
				 * with following details.
				 * 
				 * Header: 1. Start Date 2. End Date 3.Status type
				 * 
				 * Footer: 1. Report Run By: (name of the user) 2. From: (Name of
				 * the Region) 3. On: MM/DD/YYYY HH:MM:SS (Time Zone) 4. Intermedix
				 * Emsystems logo 5. Page number
				 * 
				 * Details of resource RS are displayed appropriately with following
				 * columns:
				 * 
				 * 1. Status Value 2. Status Start Date 3. Status End Date 4.
				 * Duration (Hrs) 5. User 6. IP 7. Trace 8. Comments
				 */
				// 662880

				/*
				 * STEP : Action:Navigate to Reports>>Status Reports, click on
				 * 'Status Detail'. Expected Result:'Status Detail Report (Step 1 of
				 * 2)' screen is displayed.
				 */
				// 662881
				try {
					assertEquals("", strFuncResult);
					selenium.selectWindow("");
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
				 * Date' fields, select Data File, Comma-separated (CSV) format,
				 * select TST and click on Next. Expected Result:Status Detail
				 * Report (Step 2 of 2)' screen is displayed.
				 */
				// 662882

				try {
					assertEquals("", strFuncResult);

					String strReport[] = objRep
							.enterReportSumDetailAndGenReportWitTime(selenium,
									strRSValue[0], strSTvalue[1], strApplTime,
									strApplTime, false, strSSTypeName, "HH:mm:ss");

					strUpdatdGenrtdValeSystem_2 = strReport[1];

					strFuncResult = strReport[0];

					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTime = strStatusTime[2];

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
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

					String strDurationUpdat = strUpdatedDate;
					String strDurationGenerat = strReportGenrtTime;

					// Updating

					int intDurationDiff_2 = dts.getTimeDiff1(
							strUpdatdGenrtdValeSystem_2, strTimeUpdateSystem);

					double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

					double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
					String strDurationDiff_2 = Double.toString(dRounded_2);

					strTestDataCSV1 = new String[] {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strResource, "393",
							strCurrDate + " " + strDurationUpdat,
							strCurrDate + " " + strDurationGenerat,
							strDurationDiff_2,"(Update user)"+strUserName_2+"/"+"(Report user)"+strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strCSVDownlPath,
							"Status Detail Report need to be checked in CSV file"

					};

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				File Pf1 = new File(strCSVDownlPath);
				File Cf1 = new File(strPDFDownlPath);
				if (Pf1.exists() && Cf1.exists()) {
					try {
						assertEquals("", strFuncResult);
						gstrResult = "PASS";

						String strWriteFilePath = pathProps
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
								"Status_Detail_Report");
						objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
								"Status_Detail_Report");
					} catch (AssertionError Ae) {
						gstrResult = "FAIL";
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
				/*
				 * STEP : Action:Select the resource 'RS' under 'Resources' section,
				 * and click on 'Generate Report'. Expected Result:Status Detail
				 * Report is generated in the CSV (Comma Separated Values) format
				 * with sections 'Status Detail' and 'Status Summary'.
				 * 
				 * 'Status Detail' section displays following columns with
				 * appropriate data:
				 * 
				 * ED Saturation Report as Heading Report Period: (as provided while
				 * generating report)
				 * 
				 * 'Status Detail' section displays following columns with
				 * appropriate data:
				 * 
				 * 1. Date Time 2. Resource 3. ED Beds Occupied 4. Pts in Lobby 5.
				 * Amb wait 6. General Admits 7. ICU Admits 8. 1 on 1 Pts 9. Excess
				 * Lobby Wait 10. RNs short-staffed 11. Assigned ED Beds 12. Lobby
				 * Capacity 13. Sat Score 14. Charge RN/Mgr 15. Physician 16.
				 * Comments
				 */
				// 662883

				log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
				log4j
						.info("----------------------------------------------------------");

			} catch (Exception e) {
				gstrTCID = "FTS-126302";
				gstrTO = "Update status of a saturation score status type SST added at the resource level for a resource RS. "
						+ "Verify that a user with �Run Report� and 'View Resource' rights on RS and with a role with view right"
						+ "for SST can view the status of SST in the generated status detail report.";
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
		
		//end//testFTS126302//
		//start//testFTS126304//
				/***************************************************************
				'Description		:Update status of a text status type TST added at the resource type level for a resource RS.
				                     Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role 
				                     with view right for TST can view the status of TST in the generated status detail report.
				'Precondition		:
				'Arguments			:None
				'Returns			:None
				'Date				:10/10/2013
				'Author				:QSG
				'---------------------------------------------------------------
				'Modified Date				             Modified By
				'Date					                 Name
				***************************************************************/

			@Test
			public void testFTS126304() throws Exception {
				boolean blnLogin = false;
				String strFuncResult = "";
				Paths_Properties objAP = new Paths_Properties();
				Properties pathProps = objAP.Read_FilePath();
				StatusTypes objST = new StatusTypes();
				ResourceTypes objRT = new ResourceTypes();
				Resources objRs = new Resources();
				CreateUsers objCreateUsers = new CreateUsers();
				Login objLogin = new Login();// object of class Login
				Roles objRole = new Roles();
				ViewMap objViewMap = new ViewMap();
				Reports objRep = new Reports();
				try {
					gstrTCID = "126304"; // Test Case Id
					gstrTO = " Update status of a text status type TST added at the resource type level for a resource RS."
							+ " Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role with view"
							+ " right for TST can view the status of TST in the generated status detail report.";// Test//Objective
					gstrReason = "";
					gstrResult = "FAIL";
					Date_Time_settings dts = new Date_Time_settings();
					gstrTimeOut = propEnvDetails.getProperty("TimeOut");
					gstrTimetake = dts.timeNow("HH:mm:ss");
					String strFILE_PATH = pathProps.getProperty("TestData_path");
					propElementAutoItDetails = objAP.ReadAutoit_FilePath();
					String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
					// login details
					String strLoginUserName = rdExcel.readData("Login", 3, 1);
					String strLoginPassword = rdExcel.readData("Login", 3, 2);
					String strRegn = rdExcel.readData("Login", 3, 4);
					// ST
					String strNumStatTypDefn = "Auto";
					String strSTvalue[] = new String[1];
					String strTxtTypeName = "AutoTxt" + strTimeText;
					// RT
					String strResType = "AutoRt_" + strTimeText;
					String strRsTypeValues[] = new String[1];
					// RS
					String strResource = "AutoRs_" + strTimeText;
					String strRSValue[] = new String[1];
					String strStandResType = "Aeromedical";
					String strContFName = "auto";
					String strContLName = "qsg";
					String strAbbrv = "Rs";
					String strState = "Alabama";
					String strCountry = "Autauga County";
					// Role
					String strRoleName = "AutoR_" + strTimeText;
					String strRoleRights[][] = {};
					String strRoleValue = "";

					String strApplTime = dts.getTimeOfParticularTimeZone("CST",
							"M/d/yyyy");
					// Search user criteria
					String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
							strFILE_PATH);
					String strByResourceType = rdExcel.readInfoExcel("User_Template",
							7, 12, strFILE_PATH);
					String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
							13, strFILE_PATH);
					String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
							14, strFILE_PATH);

					String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
					String strUsrFulName_1 = strUserName_1;
					String strInitPwd = rdExcel.readData("Login", 4, 2);
					String strConfirmPwd = rdExcel.readData("Login", 4, 2);

					String strUserName_2 = "AutoUsr_1" + System.currentTimeMillis();
					String strUsrFulName_2 = strUserName_2;
					String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
					String strTimeUpdateSystem = "";
					// Application time
					String strUpdatdVale = "";
					String strUpdatedDate = "";
					String strReportGenrtTime = "";
					String strUpdatdGenrtdValeSystem_2 = "";

					// Auto IT
					String strPDFDownlPath = pathProps
							.getProperty("Reports_DownloadCSV_Path")
							+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
					String[] strTestDataPDF1 = null;
					String[] strTestDataCSV1 = null;

					String strAutoFileName = propElementAutoItDetails
							.getProperty("Reports_DownloadFile_Name");
					String strAutoFilePath = propElementAutoItDetails
							.getProperty("Reports_DownloadFile_Path");
					String strCSVDownlPath = pathProps
							.getProperty("Reports_DownloadCSV_Path")
							+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";

					/*
					 * STEP : Action:Preconditions:
					 * 
					 * 1. Status Type TST(text status type) is created.
					 * 
					 * 2. Resource type RT is associated with status type TST.
					 * 
					 * 3. Resources RS is created under RT.
					 * 
					 * 4. User U1 has following rights:
					 * 
					 * a. Report - Status Detail. b. Role to View status type TST.
					 * c.'View Resource' and 'Run Report' rights on resources RS.
					 * 
					 * 5.Status Type TST is updated with some value on day D1. Expected
					 * Result:No Expected Result
					 */
					// 662877
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

					// Creating ST
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.navStatusTypList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strStatusTypeValue1 = "Text";
						strFuncResult = objST.selectStatusTypesAndFilMandFlds(
								seleniumPrecondition, strStatusTypeValue1,
								strTxtTypeName, strNumStatTypDefn, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strSTvalue[0] = objST.fetchSTValueInStatTypeList(
								seleniumPrecondition, strTxtTypeName);

						if (strSTvalue[0].compareTo("") != 0) {
							strFuncResult = "";
						} else {
							strFuncResult = "Failed to fetch status value";
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// Creating RT
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.resrcTypeMandatoryFldsNew(
								seleniumPrecondition, strResType, strSTvalue[0]);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
								strResType);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strRsTypeValues[0] = objRT.fetchResTypeValueInResTypeList(
								seleniumPrecondition, strResType);

						if (strRsTypeValues[0].compareTo("") != 0) {
							strFuncResult = "";
						} else {
							strFuncResult = "Failed to fetch status value";
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// Creating RS
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
								strResType, strContFName, strContLName, strState,
								strCountry, strStandResType);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strResVal = objRs.fetchResValueInResList(
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
						strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					// create role to update

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.CreateRoleWithAllFields(
								seleniumPrecondition, strRoleName, strRoleRights,
								strSTvalue, false, strSTvalue, false, false);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String[][] strSTViewValue = { { strSTvalue[0], "true" } };
						String[][] strSTUpdateValue = { { strSTvalue[0], "true" } };
						strFuncResult = objRole.slectAndDeselectSTInCreateRole(
								seleniumPrecondition, false, false, strSTViewValue,
								strSTUpdateValue, true);

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
					// Run Report user
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.navUserListPge(seleniumPrecondition);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.navToCreateUserPage(seleniumPrecondition);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
								seleniumPrecondition, strUserName_1, strInitPwd,
								strConfirmPwd, strUsrFulName_1);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(
								seleniumPrecondition, strResource, false, false, true,
								true);

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
						String strOptions = propElementDetails
								.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
						strFuncResult = objCreateUsers.advancedOptns(
								seleniumPrecondition, strOptions, true);
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

					// Update user
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.navToCreateUserPage(seleniumPrecondition);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
								seleniumPrecondition, strUserName_2, strInitPwd,
								strConfirmPwd, strUsrFulName_2);

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
						strFuncResult = objCreateUsers.slectAndDeselectRole(
								seleniumPrecondition, strRoleValue, true);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
								seleniumPrecondition, strUserName_2, strByRole,
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
					try {
						assertEquals("", strFuncResult);
						blnLogin = false;
						strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
								strUserName_2, strInitPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						blnLogin = true;

						String[] strEventStatType = {};
						String[] strRoleStatType = { strTxtTypeName };
						strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
								seleniumPrecondition, strResource, strEventStatType,
								strRoleStatType, false, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);

						String strUpdate[] = { strSTvalue[0] };
						String strUpdateST[] = objViewMap.updateStatusTypeWithTime(
								seleniumPrecondition, strResource, strUpdate,
								"HH:mm:ss");
						strFuncResult = strUpdateST[0];

						strTimeUpdateSystem = strUpdateST[1];

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);

						strUpdatdVale = seleniumPrecondition
								.getText("css=div.emsText.maxheight > span");

						strUpdatedDate = seleniumPrecondition.getText("//div/span"
								+ "[text()='" + strUpdatdVale + "']/parent::"
								+ "div/span[@class='time']");
						strUpdatedDate = strUpdatedDate.substring(1, 13);

						String strLastUpdArr[] = strUpdatedDate.split(" ");
						strUpdatedDate = strLastUpdArr[2];

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
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
						strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.navEditRolesPge(seleniumPrecondition,
								strRoleName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String[][] strSTViewValue = { { strSTvalue[0], "true" } };
						String[][] strSTUpdateValue = { { strSTvalue[0], "false" } };
						strFuncResult = objRole.slectAndDeselectSTInCreateRole(
								seleniumPrecondition, false, false, strSTViewValue,
								strSTUpdateValue, true);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					log4j.info("~~~~~PRECONDITION - " + gstrTCID
							+ " EXECUTION ENDS~~~~~");

					/*
					 * STEP : Action:Login as User U1, navigate to Reports>>Status
					 * Reports, click on 'Status Detail'. Expected Result:'Status Detail
					 * Report (Step 1 of 2)' screen is displayed.
					 */
					// 662878
					log4j.info("~~~~~TEST CASE - " + gstrTCID
							+ " EXECUTION STARTS~~~~~");
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
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep.navToStatusDetailReport(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String[] statTypeName = { strTxtTypeName };
						strFuncResult = objRep.verifyCalndrWidInStatusDetailReportNew(
								selenium, statTypeName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					/*
					 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
					 * Date' fields, select TST from 'Status Type' dropdown and click on
					 * 'Next'. Expected Result:'Status Detail Report (Step 2 of 2)'
					 * screen is displayed.
					 */
					// 662879

					try {
						assertEquals("", strFuncResult);

						String strReport[] = objRep
								.enterReportSumDetailAndGenReportWitTime(selenium,
										strRSValue[0], strSTvalue[0], strApplTime,
										strApplTime, true, strTxtTypeName, "HH:mm:ss");

						String strUpdatdGenrtdValeSystem_1 = strReport[1];
						strFuncResult = strReport[0];

						String StatusTime = selenium.getText("css=#statusTime");

						String strStatusTime[] = StatusTime.split(" ");
						strReportGenrtTime = strStatusTime[2];

						strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

						String strDurationUpdat = strUpdatedDate;
						String strDurationGenerat = strReportGenrtTime;

						// Updating

						int intDurationDiff_2 = dts.getTimeDiff1(
								strUpdatdGenrtdValeSystem_1, strTimeUpdateSystem);

						double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

						double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
						String strDurationDiff_2 = Double.toString(dRounded_2);

						strTestDataPDF1 = new String[] {
								propEnvDetails.getProperty("Build"),
								gstrTCID,
								strResource,
								strUpdatdVale,
								strCurrDate + " " + strDurationUpdat,
								strCurrDate + " " + strDurationGenerat,
								strDurationDiff_2,
								"(Update user)"+strUserName_2+"/"+"(Report user)"+strUserName_1,
								propEnvDetails.getProperty("ExternalIP"), "",
								strPDFDownlPath,
								"Status Detail Report need to be checked in PDF file"

						};
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strPDFDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName) && intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					/*
					 * STEP : Action:Select the resource 'RS' under 'Resources' section,
					 * and click on 'Generate Report'. Expected Result:Status detail
					 * Report is generated in the PDF format.
					 * 
					 * Header and Footer are displayed in all the pages of the report
					 * with following details.
					 * 
					 * Header: 1. Start Date 2. End Date 3.Status type
					 * 
					 * Footer: 1. Report Run By: (name of the user) 2. From: (Name of
					 * the Region) 3. On: MM/DD/YYYY HH:MM:SS (Time Zone) 4. Intermedix
					 * Emsystems logo 5. Page number
					 * 
					 * Details of resource RS are displayed appropriately with following
					 * columns:
					 * 
					 * 1. Status Value 2. Status Start Date 3. Status End Date 4.
					 * Duration (Hrs) 5. User 6. IP 7. Trace 8. Comments
					 * 
					 * 'Status Summary' section for the resource displays following
					 * details:
					 * 
					 * 1. Status 2. Total Hours 3. % of Total Hours
					 */
					// 662880

					/*
					 * STEP : Action:Navigate to Reports>>Status Reports, click on
					 * 'Status Detail'. Expected Result:'Status Detail Report (Step 1 of
					 * 2)' screen is displayed.
					 */
					// 662881
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep.navToStatusDetailReport(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					/*
					 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
					 * Date' fields, select Data File, Comma-separated (CSV) format,
					 * select TST and click on Next. Expected Result:Status Detail
					 * Report (Step 2 of 2)' screen is displayed.
					 */
					// 662882

					try {
						assertEquals("", strFuncResult);

						String strReport[] = objRep
								.enterReportSumDetailAndGenReportWitTime(selenium,
										strRSValue[0], strSTvalue[0], strApplTime,
										strApplTime, false, strTxtTypeName, "HH:mm:ss");

						strUpdatdGenrtdValeSystem_2 = strReport[1];

						strFuncResult = strReport[0];

						String StatusTime = selenium.getText("css=#statusTime");

						String strStatusTime[] = StatusTime.split(" ");
						strReportGenrtTime = strStatusTime[2];

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
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName) && intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

						String strDurationUpdat = strUpdatedDate;
						String strDurationGenerat = strReportGenrtTime;

						// Updating

						int intDurationDiff_2 = dts.getTimeDiff1(
								strUpdatdGenrtdValeSystem_2, strTimeUpdateSystem);

						double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

						double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
						String strDurationDiff_2 = Double.toString(dRounded_2);

						strTestDataCSV1 = new String[] {
								propEnvDetails.getProperty("Build"),
								gstrTCID,
								strResource,
								strUpdatdVale,
								strCurrDate + " " + strDurationUpdat,
								strCurrDate + " " + strDurationGenerat,
								strDurationDiff_2,
								"(Update user)"+strUserName_2+"/"+"(Report user)"+strUserName_1,
								propEnvDetails.getProperty("ExternalIP"), "",
								strCSVDownlPath,
								"Status Detail Report need to be checked in CSV file"

						};

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					File Pf1 = new File(strCSVDownlPath);
					File Cf1 = new File(strPDFDownlPath);
					if (Pf1.exists() && Cf1.exists()) {
						try {
							assertEquals("", strFuncResult);
							gstrResult = "PASS";

							String strWriteFilePath = pathProps
									.getProperty("WriteResultPath");
							objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
									"Status_Detail_Report");
							objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
									"Status_Detail_Report");
						} catch (AssertionError Ae) {
							gstrResult = "FAIL";
							gstrReason = gstrReason + " " + strFuncResult;
						}
					}
					/*
					 * STEP : Action:Select the resource 'RS' under 'Resources' section,
					 * and click on 'Generate Report'. Expected Result:Status Detail
					 * Report is generated in the CSV (Comma Separated Values) format
					 * with sections 'Status Detail' and 'Status Summary'.
					 * 
					 * 'Status Detail' section displays following columns with
					 * appropriate data:
					 * 
					 * 1. Resource 2. Status 3. Start Date 4. End Date 5. Duration 6.
					 * User 7. IP 8. Trace 9. Comments 'Status Summary' section displays
					 * following columns with appropriate data:
					 * 
					 * 1. Resource 2. Status 3. Total Hours 4. % of Total Hours
					 */
					// 662883

					log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
					log4j
							.info("----------------------------------------------------------");

				} catch (Exception e) {
					gstrTCID = "FTS-126304";
					gstrTO = "Update status of a text status type TST added at the resource type level for a resource RS."
							+ " Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role with view right for TST "
							+ "can view the status of TST in the generated status detail report.";
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

			// end//testFTS126304//
			
		//start//testFTS126308//
		/***************************************************************
		'Description		:Update status of a text status type SST added at the resource type level for a resource RS.
		                     Verify that a user with 'Run Report' and 'View Resource' rights on RS and with a role 
		                     with view right for TST can view the status of SST in the generated status detail report.
		'Precondition		:
		'Arguments			:None
		'Returns			:None
		'Date				:10/10/2013
		'Author				:QSG
		'---------------------------------------------------------------
		'Modified Date				             Modified By
		'Date					                 Name
		***************************************************************/

		@Test
		public void testFTS126308() throws Exception {
			boolean blnLogin = false;
			String strFuncResult = "";
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();
			Login objLogin = new Login();// object of class Login
			Roles objRole = new Roles();
			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();
			try {
				gstrTCID = "126308"; // Test Case Id
				gstrTO = "Update status of a saturation score status type SST added at the resource level for a resource RS. "
						+ "Verify that a user with �Run Report� and 'View Resource' rights on RS and with a role with view right"
						+ "for SST can view the status of SST in the generated status detail report.";// Test////
				// Objective
				gstrReason = "";
				gstrResult = "FAIL";
				Date_Time_settings dts = new Date_Time_settings();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				gstrTimetake = dts.timeNow("HH:mm:ss");
				String strFILE_PATH = pathProps.getProperty("TestData_path");
				propElementAutoItDetails = objAP.ReadAutoit_FilePath();
				String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
				// login details
				String strLoginUserName = rdExcel.readData("Login", 3, 1);
				String strLoginPassword = rdExcel.readData("Login", 3, 2);
				String strRegn = rdExcel.readData("Login", 3, 4);
				// ST
				String strNumStatTypDefn = "Auto";
				String strSTvalue[] = new String[1];
				String strSSTypeName = "AutoSST" + strTimeText;
				// RT
				String strResType = "AutoRt_" + strTimeText;
				String strRsTypeValues[] = new String[1];
				// RS
				String strResource = "AutoRs_" + strTimeText;
				String strRSValue[] = new String[1];
				String strStandResType = "Aeromedical";
				String strContFName = "auto";
				String strContLName = "qsg";
				String strAbbrv = "Rs";
				String strState = "Alabama";
				String strCountry = "Autauga County";
				// Role
				String strRoleName = "AutoR_" + strTimeText;
				String strRoleRights[][] = {};
				String strRoleValue = "";

				String strApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				// Search user criteria
				String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel("User_Template",
						7, 12, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
						13, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
						14, strFILE_PATH);

				String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
				String strUsrFulName_1 = strUserName_1;
				String strInitPwd = rdExcel.readData("Login", 4, 2);
				String strConfirmPwd = rdExcel.readData("Login", 4, 2);

				String strUserName_2 = "AutoUsr_1" + System.currentTimeMillis();
				String strUsrFulName_2 = strUserName_2;
				String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
				String strTimeUpdateSystem = "";
				// Application time
				String strUpdatdVale = "";
				String strUpdatedDate = "";
				String strReportGenrtTime = "";
				String strUpdatdGenrtdValeSystem_2 = "";
				// Auto IT
				String strPDFDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
				String[] strTestDataPDF1 = null;
				String[] strTestDataCSV1 = null;

				String strAutoFileName = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Name");
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Path");
				String strCSVDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";

				/*
				 * STEP : Action:Preconditions:
				 * 
				 * 1. Status Type SST(saturation score status type) is created.
				 * 
				 * 2. Resource type RT is associated with status type ST1.
				 * 
				 * 3. Resources RS is created under RT.
				 * 
				 * 4. Status Type SST(saturation score status type) is added for
				 * resource RS at the resource level.
				 * 
				 * 5. User U1 has following rights:
				 * 
				 * a. Report - Status Detail. b. Role to View status type TST. c.
				 * 'View Resource' and 'Run Report' rights on resources RS.
				 * 
				 * 6.Status Type SST is updated with some value on day D1. Expected
				 * Result:No Expected Result
				 */
				// 662877
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

				// Creating ST
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.navStatusTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String strStatusTypeValue1 = "Saturation Score";
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusTypeValue1,
							strSSTypeName, strNumStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[0] = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, strSSTypeName);

					if (strSTvalue[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating RT
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.resrcTypeMandatoryFldsNew(
							seleniumPrecondition, strResType, strSTvalue[0]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
							strResType);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRsTypeValues[0] = objRT.fetchResTypeValueInResTypeList(
							seleniumPrecondition, strResType);

					if (strRsTypeValues[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating RS
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
							strResType, strContFName, strContLName, strState,
							strCountry, strStandResType);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strResVal = objRs.fetchResValueInResList(
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
					strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// create role to update

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.CreateRoleWithAllFields(
							seleniumPrecondition, strRoleName, strRoleRights,
							strSTvalue, false, strSTvalue, false, false);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[][] strSTViewValue = { { strSTvalue[0], "true" } };
					String[][] strSTUpdateValue = { { strSTvalue[0], "true" } };
					strFuncResult = objRole.slectAndDeselectSTInCreateRole(
							seleniumPrecondition, false, false, strSTViewValue,
							strSTUpdateValue, true);

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
				// Run Report user user
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navUserListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navToCreateUserPage(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
							seleniumPrecondition, strUserName_1, strInitPwd,
							strConfirmPwd, strUsrFulName_1);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.selectResourceRights(
							seleniumPrecondition, strResource, false, false, true,
							true);

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
					String strOptions = propElementDetails
							.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
					strFuncResult = objCreateUsers.advancedOptns(
							seleniumPrecondition, strOptions, true);
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

				// Update user
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.navToCreateUserPage(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
							seleniumPrecondition, strUserName_2, strInitPwd,
							strConfirmPwd, strUsrFulName_2);

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
					strFuncResult = objCreateUsers.slectAndDeselectRole(
							seleniumPrecondition, strRoleValue, true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
							seleniumPrecondition, strUserName_2, strByRole,
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
				try {
					assertEquals("", strFuncResult);
					blnLogin = false;
					strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
							strUserName_2, strInitPwd);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);
					blnLogin = true;

					String[] strEventStatType = {};
					String[] strRoleStatType = { strSSTypeName };
					strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
							seleniumPrecondition, strResource, strEventStatType,
							strRoleStatType, false, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);
					String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
							"7", "8" };
					String[] strUpdateValue2 = { "1", "2", "3", "4", "5", "6", "7",
							"8", "9" };

					String strUpdateST[] = objST.updateSatuScoreStatusTypeWitTime(
							seleniumPrecondition, strResource, strSSTypeName,
							strSTvalue[0], strUpdateValue1, strUpdateValue2, "393",
							"429", "HH:mm:ss");
					strFuncResult = strUpdateST[0];

					strTimeUpdateSystem = strUpdateST[1];

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

				try {
					assertEquals("", strFuncResult);

					strUpdatdVale = seleniumPrecondition
							.getText("css=div.emsText.maxheight > span");

					strUpdatedDate = seleniumPrecondition.getText("//div/span"
							+ "[text()='" + strUpdatdVale + "']/parent::"
							+ "div/span[@class='time']");
					strUpdatedDate = strUpdatedDate.substring(1, 13);

					String strLastUpdArr[] = strUpdatedDate.split(" ");
					strUpdatedDate = strLastUpdArr[2];

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.logout(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
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
					strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.navEditRolesPge(seleniumPrecondition,
							strRoleName);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[][] strSTViewValue = { { strSTvalue[0], "true" } };
					String[][] strSTUpdateValue = { { strSTvalue[0], "false" } };
					strFuncResult = objRole.slectAndDeselectSTInCreateRole(
							seleniumPrecondition, false, false, strSTViewValue,
							strSTUpdateValue, true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.logout(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				log4j.info("~~~~~PRECONDITION - " + gstrTCID
						+ " EXECUTION ENDS~~~~~");

				/*
				 * STEP : Action:Login as User U1, navigate to Reports>>Status
				 * Reports, click on 'Status Detail'. Expected Result:'Status Detail
				 * Report (Step 1 of 2)' screen is displayed.
				 */
				// 662878
				log4j.info("~~~~~TEST CASE - " + gstrTCID
						+ " EXECUTION STARTS~~~~~");
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
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[] statTypeName = { strSSTypeName };
					strFuncResult = objRep.verifyCalndrWidInStatusDetailReportNew(
							selenium, statTypeName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
					/*
					 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
					 * Date' fields, select SST from 'Status Type' dropdown and click on
					 * 'Next'. Expected Result:'Status Detail Report (Step 2 of 2)'
					 * screen is displayed.
					 */
					// 662879

				try {
					assertEquals("", strFuncResult);

					String strReport[] = objRep
							.enterReportSumDetailAndGenReportWitTime(selenium,
									strRSValue[0], strSTvalue[0], strApplTime,
									strApplTime, true, strSSTypeName, "HH:mm:ss");
					String strUpdatdGenrtdValeSystem_1 = strReport[1];
					strFuncResult = strReport[0];

					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTime = strStatusTime[2];

					strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

					String strDurationUpdat = strUpdatedDate;
					String strDurationGenerat = strReportGenrtTime;

					// Updating

					int intDurationDiff_2 = dts.getTimeDiff1(
							strUpdatdGenrtdValeSystem_1, strTimeUpdateSystem);

					double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

					double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
					String strDurationDiff_2 = Double.toString(dRounded_2);

					strTestDataPDF1 = new String[] {
							propEnvDetails.getProperty("Build"),
							gstrTCID,
							strResource,
							"393",
							strCurrDate + " " + strDurationUpdat,
							strCurrDate + " " + strDurationGenerat,
							strDurationDiff_2,
							"(Update user)"+strUserName_2+"/"+"(Report user)"+strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strPDFDownlPath,
							"Status Detail Report need to be checked in PDF file"

					};
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					// Thread.sleep(20000);
					String strProcess = "";
					String strArgs[] = { strAutoFilePath, strPDFDownlPath };

					// AutoIt
					Runtime.getRuntime().exec(strArgs);

					int intCnt = 0;
					do {
						GetProcessList objGPL = new GetProcessList();
						strProcess = objGPL.GetProcessName();
						intCnt++;
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				/*
				 * STEP : Action:Select the resource 'RS' under 'Resources' section,
				 * and click on 'Generate Report'. Expected Result:Status detail
				 * Report is generated in the PDF format.
				 * 
				 * Header and Footer are displayed in all the pages of the report
				 * with following details.
				 * 
				 * Header: 1. Start Date 2. End Date 3.Status type
				 * 
				 * Footer: 1. Report Run By: (name of the user) 2. From: (Name of
				 * the Region) 3. On: MM/DD/YYYY HH:MM:SS (Time Zone) 4. Intermedix
				 * Emsystems logo 5. Page number
				 * 
				 * Details of resource RS are displayed appropriately with following
				 * columns:
				 * 
				 * 1. Status Value 2. Status Start Date 3. Status End Date 4.
				 * Duration (Hrs) 5. User 6. IP 7. Trace 8. Comments Status Summary'
				 * section for the resource displays following details:
				 * 
				 * 1. Status 2. Total Hours 3. % of Total Hours
				 */
				// 662880

				/*
				 * STEP : Action:Navigate to Reports>>Status Reports, click on
				 * 'Status Detail'. Expected Result:'Status Detail Report (Step 1 of
				 * 2)' screen is displayed.
				 */
				// 662881
				try {
					assertEquals("", strFuncResult);
					selenium.selectWindow("");
					strFuncResult = objRep.navToStatusReports(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRep.navToStatusDetailReport(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
				 * Date' fields, select Data File, Comma-separated (CSV) format,
				 * select TST and click on Next. Expected Result:Status Detail
				 * Report (Step 2 of 2)' screen is displayed.
				 */
				// 662882

				try {
					assertEquals("", strFuncResult);

					String strReport[] = objRep
							.enterReportSumDetailAndGenReportWitTime(selenium,
									strRSValue[0], strSTvalue[0], strApplTime,
									strApplTime, false, strSSTypeName, "HH:mm:ss");

					strUpdatdGenrtdValeSystem_2 = strReport[1];

					strFuncResult = strReport[0];

					String StatusTime = selenium.getText("css=#statusTime");

					String strStatusTime[] = StatusTime.split(" ");
					strReportGenrtTime = strStatusTime[2];

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
						Thread.sleep(500);
					} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

					String strDurationUpdat = strUpdatedDate;
					String strDurationGenerat = strReportGenrtTime;

					// Updating

					int intDurationDiff_2 = dts.getTimeDiff1(
							strUpdatdGenrtdValeSystem_2, strTimeUpdateSystem);

					double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

					double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
					String strDurationDiff_2 = Double.toString(dRounded_2);

					strTestDataCSV1 = new String[] {
							propEnvDetails.getProperty("Build"),
							gstrTCID,
							strResource,
							"393",
							strCurrDate + " " + strDurationUpdat,
							strCurrDate + " " + strDurationGenerat,
							strDurationDiff_2,
							"(Update user)"+strUserName_2+"/"+"(Report user)"+strUserName_1,
							propEnvDetails.getProperty("ExternalIP"), "",
							strCSVDownlPath,
							"Status Detail Report need to be checked in CSV file"

					};

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				File Pf1 = new File(strCSVDownlPath);
				File Cf1 = new File(strPDFDownlPath);
				if (Pf1.exists() && Cf1.exists()) {
					try {
						assertEquals("", strFuncResult);
						gstrResult = "PASS";

						String strWriteFilePath = pathProps
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
								"Status_Detail_Report");
						objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
								"Status_Detail_Report");
					} catch (AssertionError Ae) {
						gstrResult = "FAIL";
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
				/*
				 * STEP : Action:Select the resource 'RS' under 'Resources' section,
				 * and click on 'Generate Report'. Expected Result:Status Detail
				 * Report is generated in the CSV (Comma Separated Values) format
				 * with sections 'Status Detail' and 'Status Summary'.
				 * 
				 * 'Status Detail' section displays following columns with
				 * appropriate data:
				 * 
				 * ED Saturation Report as Heading Report Period: (as provided while
				 * generating report)
				 * 
				 * 'Status Detail' section displays following columns with
				 * appropriate data:
				 * 
				 * 1. Date Time 2. Resource 3. ED Beds Occupied 4. Pts in Lobby 5.
				 * Amb wait 6. General Admits 7. ICU Admits 8. 1 on 1 Pts 9. Excess
				 * Lobby Wait 10. RNs short-staffed 11. Assigned ED Beds 12. Lobby
				 * Capacity 13. Sat Score 14. Charge RN/Mgr 15. Physician 16.
				 * Comments
				 */
				// 662883

				log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
				log4j
						.info("----------------------------------------------------------");

			} catch (Exception e) {
				gstrTCID = "FTS-126302";
				gstrTO = "Update status of a saturation score status type SST added at the resource level for a resource RS. "
						+ "Verify that a user with �Run Report� and 'View Resource' rights on RS and with a role with view right"
						+ "for SST can view the status of SST in the generated status detail report.";
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
		// start//testFTS126308//
	//start//testFTS53483//
	/***************************************************************
	'Description		:Update the status of a multi private status type pMST associated with a resource 
						 RS at the resource type level. Verify that a user with 'Run Report' and 'View Resource'
						 rights on RS and without any role can view the status of pMST for RS in the generated status detail report.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/16/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date				          Modified By
	'Date								  Name
	***************************************************************/

	@Test
	public void testFTS53483() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Login objLogin = new Login();// object of class Login
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		try {
			gstrTCID = "53483"; // Test Case Id
			gstrTO = " Update the status of a multi private status type pMST associated with a resource RS at"
					+ " the resource type level. Verify that a user with 'Run Report' and 'View Resource' rights"
					+ " on RS and without any role can view the status of pMST for RS in the generated status detail report.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String strSTvalue[] = new String[1];
			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			String statMultiTypeName1 = "PMST_1" + strTimeText;
			String strStatusTypeValue = "Multi";
			String strStatTypDefn = "Automation";
			String strStatValue = "";
			// RT
			String strResType = "AutoRt_" + strTimeText;
			String strRsTypeValues[] = new String[1];
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strRSValue[] = new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strAbbrv = "Rs";
			String strState = "Alabama";
			String strCountry = "Autauga County";

			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strApplTime = dts.getTimeOfParticularTimeZone("CST",
					"M/d/yyyy");
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_2 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strTimeUpdateSystem = "";
			// Application time
			String strUpdatdVale = "";
			String strUpdatedDate = "";
			String strReportGenrtTime = "";

			String strUpdatdGenrtdValeSystem_2 = "";

			// Auto IT
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
			String[] strTestDataPDF1 = null;
			String[] strTestDataCSV1 = null;

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";

			/*
			 * STEP : Action:Preconditions:
			 * 
			 * 1. Private status Type pMST(multi status type) is created.
			 * 
			 * 2. Resource type RT is associated with Private status Type pMST.
			 * 
			 * 3. Resources RS is created under RT.
			 * 
			 * 4. User U1 has following rights:
			 * 
			 * a. Report - Status Detail. b. Role to View private status type
			 * pMST. c.'View Resource' and 'Run Report' rights on resources RS.
			 * 
			 * 5.Private status Type pMST is updated with some value on day D1.
			 * Expected Result:No Expected Result
			 */
			// 317332

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

			// Creating MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.selStatusTypesAndCreatePrivateST(
						seleniumPrecondition, strStatusTypeValue,
						statMultiTypeName1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statMultiTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName1,
						strStatusName1, strStatusTypeValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName1,
						strStatusName2, strStatusTypeValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName1,
						strStatusName1);
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
				strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName1,
						strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResType, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResType);

				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RS1
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
						strResType, strContFName, strContLName, strState,
						strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(
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
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to view

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTvalue, false, strSTvalue, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strSTViewValue = { { strSTvalue[0], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[0], "true" } };
				strFuncResult = objRole.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTViewValue,
						strSTUpdateValue, true);

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

			// Run Report user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navToCreateUserPage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						seleniumPrecondition, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource, false, false, true,
						true);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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

			// Update User
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navToCreateUserPage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						seleniumPrecondition, strUserName_2, strInitPwd,
						strConfirmPwd, strUsrFulName_2);

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_2, strByRole,
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

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName_2, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statMultiTypeName1 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						seleniumPrecondition, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strUpdateST[] = objViewMap
						.updateMultiStatusTypeWithTime(seleniumPrecondition,
								strResource, statMultiTypeName1, strSTvalue[0],
								strStatusName1, strStatusValue[0],
								strStatusName2, strStatusValue[1], "HH:mm:ss");
				strTimeUpdateSystem = strUpdateST[1];
				System.out.println(strTimeUpdateSystem);
				strFuncResult = strUpdateST[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = seleniumPrecondition
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = seleniumPrecondition.getText("//div/span"
						+ "[text()='" + strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
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
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navEditRolesPge(seleniumPrecondition,
						strRoleName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strSTViewValue = { { strSTvalue[0], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[0], "false" } };
				strFuncResult = objRole.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTViewValue,
						strSTUpdateValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as User U1, navigate to Reports >> Status
			 * Reports, click on 'Status Detail' . Expected Result:'Status
			 * Detail Report (Step 1 of 2)' screen is displayed Adobe Acrobat
			 * (PDF) is selected by default
			 */
			// 317333
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
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
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
			 * Date' fields, select pMST from 'Status Type' dropdown and click
			 * on 'Next'. Expected Result:'Status Detail Report (Step 2 of 2)'
			 * screen is displayed.
			 */
			// 317334

			try {
				assertEquals("", strFuncResult);

				String strReport[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statMultiTypeName1,
								strStatusValue[0], "HH:mm:ss");

				String strUpdatdGenrtdValeSystem_1 = strReport[1];

				strFuncResult = strReport[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];

				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				// Updating

				int intDurationDiff_2 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_1, strTimeUpdateSystem);

				double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

				double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
				String strDurationDiff_2 = Double.toString(dRounded_2);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff_2,
						"Update " + strUserName_2 + "/" + "Report"
								+ strUserName_1,
						propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select statuses under 'Statuses' section, resource
			 * 'RS' under 'Resources' section, and click on 'Generate Report'.
			 * Expected Result:Status detail Report is generated in the PDF
			 * format. Header and Footer are displayed in all the pages of the
			 * report with following details.
			 * 
			 * Header: 1. Start Date 2. End Date 3.Status type
			 * 
			 * Footer: 1. Report Run By: (name of the user) 2. From: (Name of
			 * the Region) 3. On: MM/DD/YYYY HH:MM:SS (Time Zone) 4. Intermedix
			 * Emsystems logo 5. Page number
			 * 
			 * Details of resource RS are displayed appropriately with following
			 * columns:
			 * 
			 * 1. Status Value 2. Status Start Date 3. Status End Date 4.
			 * Duration (Hrs) 5. User 6. IP 7. Trace 8. Comments
			 * 
			 * Details of resource RS are displayed appropriately with following
			 * columns under 'Status Summary' section:
			 * 
			 * 1. Status 2. Total Hours 3. % of Total Hours.
			 */
			// 317335
			/*
			 * STEP : Action:Navigate to Reports >> Status Reports, click on
			 * 'Status Detail' Expected Result:'Status Detail Report (Step 1 of
			 * 2)' screen is displayed.
			 */
			// 317336
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
			 * Date' fields, select Data File, Comma-separated (CSV) format,
			 * select pMST and click on Next. Expected Result:Status Detail
			 * Report (Step 2 of 2)' screen is displayed. 'Status Detail'
			 * section displays following columns with appropriate data:
			 * 
			 * 1. Resource 2. Status 3. Start Date 4. End Date 5. Duration 6.
			 * User 7. IP 8. Trace 9. Comments
			 * 
			 * 'Status Summary' section displays following columns with
			 * appropriate data:
			 * 
			 * 1. Resource 2. Status 3. Total Hours 4. % of Total
			 */
			// 317337

			try {
				assertEquals("", strFuncResult);

				String strReport[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, false, statMultiTypeName1,
								strStatusValue[0], "HH:mm:ss");

				strUpdatdGenrtdValeSystem_2 = strReport[1];

				strFuncResult = strReport[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];

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
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				// Updating

				int intDurationDiff_2 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_2, strTimeUpdateSystem);

				double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

				double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
				String strDurationDiff_2 = Double.toString(dRounded_2);

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff_2,
						"Update " + strUserName_2 + "/" + "Report"
								+ strUserName_1,
						propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			File Pf1 = new File(strCSVDownlPath);
			File Cf1 = new File(strPDFDownlPath);
			if (Pf1.exists() && Cf1.exists()) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
							"Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrResult = "FAIL";
					gstrReason = gstrReason + " " + strFuncResult;
				}
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-53483";
			gstrTO = "Update the status of a multi private status type pMST associated with a resource RS "
					+ "at the resource type level. Verify that a user with 'Run Report' and 'View Resource'"
					+ " rights on RS and without any role can view the status of pMST for RS in the generated status detail report.";
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

	// end//testFTS53483//
}