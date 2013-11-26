package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Calendar;
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
' Description :This class includes Event Snapshot Report report 
                requirement test cases
' Date		  :11-Dec-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/
public class FTSEventSnapshotReport {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSEventSnapshotReport");
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
	Properties propElementAutoItDetails,propAutoItDetails;
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

		seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
						.getProperty("urlEU"));
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		
		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try{
			selenium.close();
		}catch(Exception e){
			
		}
		
		selenium.stop();
		
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
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
		  gstrReason=gstrReason.replaceAll("'", " ");
		  objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
		    gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
		    gslsysDateTime, gstrBrowserName, gstrBuild,strSessionId);
	}	
	/***************************************************************
	'Description	: Verify that event snapshot report cannot be generated without
	                  entering data in mandatory fields.
	'Arguments		:None
	'Returns		:None
	'Date			:12/12/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS4139() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Reports objRep = new Reports();
		EventSetup objEventSetup = new EventSetup();
		General objGeneral = new General();
		try {

			gstrTCID = "4139"; // Test Case Id
			gstrTO = " Verify that event snapshot report cannot be generated without entering data "
					+ " in mandatory fields.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyyhhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strTempName = "Autotmp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "Automation";
			// report data
			String strApplTime = "";
			String strETValue = "";
			String strEventValue = "";
			/*
			 * STEP :1. Navigate to Report>>Event Reports>>Event Snapshot.
			 * Expected Result:'Event Snapshot Report (Step 1 of 2)' page is
			 * displayed.
			 */
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
			// Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = {};
				String[] strStatusTypeval = {};
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(selenium,
						strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
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
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEveName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strEventValue = objEventSetup.fetchEventValueInEventList(
						selenium, strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// Event snapshot report
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objRep.navToEventSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * STEP :Do not enter data in any of the fields and click on 'Next'.
			 * Expected Result:The following error messages are displayed: Start
			 * date is required. End date is required. Event template is
			 * required. Please select one or more. User is retained on the same
			 * page.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navEventSnapShotRepVarErrMsg(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * STEP :Now enter data in mandatory fields and click on 'Next'.
			 * Expected Result:'Event Snapshot Report (Step 2 of 2)' page is
			 * displayed.
			 */
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

				strFuncResult = objRep.selectHtmlOrExcelformatInEventSnapShotReport(
						selenium, strApplTime, strApplTime, strETValue,true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * STEP :Do not enter data in any of the fields and click on
			 * 'Generate Report'. Expected Result:The follwing error messages
			 * are displayed: 1.Event is required. Please select an event.
			 * 2.Report date is required. Report is not generated.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.enterEvntSnapshotGenRepVarErrMsg(selenium);
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
			gstrTCID = "4139"; // Test Case Id
			gstrTO = " Verify that event snapshot report cannot be generated without entering data "
					+ " in mandatory fields.";// Test Objective
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
	//start//testFTS118884//
	/***************************************************************
	'Description		:Update statuses of event related status types added at the resource type level for resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role WITHOUT view/update rights for these status types CANNOT view these status types in the Event Snapshot Report.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:10/16/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS118884() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();
		Reports objRep = new Reports();
		General objGeneral = new General();
		OfficeCommonFunctions OFC = new OfficeCommonFunctions();

		try {
			gstrTCID = "118884"; // Test Case Id
			gstrTO = " Update statuses of event related status types added at the resource type level for resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role WITHOUT view/update rights for these status types CANNOT view these status types in the Event Snapshot Report.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// Admin Login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Event related ST
			String stateNumTypeName = "eNST" + strTimeText;
			String stateTextTypeName = "eTST" + strTimeText;
			String stateMultiTypeName = "eMST" + strTimeText;
			String stateSaturatTypeName = "eSST" + strTimeText;

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";

			String strEStatusName1 = "eSta" + strTimeText;
			String strEStatusValue[] = new String[2];

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			String strUserName_2 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";

			String strEveName = "AutoEve_1" + strTimeText;
			String strInfo = "This is an automation event";
			String strEventValue = "";
			String strETValue = "";

			String strUpdate1 = "101";
			String strUpdate2 = "Update2";
			String strApplTime = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xlsx";
			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "EventSnapshot_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strTimeReport = "";
			String strHr = "";
			String strMin = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strLastUpdArr[] = {};
			String strUpdateDataHr = "";
			String strUpdateDataMin = "";
			
			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with event related status types ENST (Number), EMST (Multi), ETST
			 * (Text) and ESST (Saturation Score) 2. Resources RS is created
			 * under RT. 3. Event template ET1 is created selecting resource
			 * type RT and status types ENST, EMST, ETST, ESST 4. Event E1 is
			 * created under ET1 selecting resource RS 5. User U1 has following
			 * rights: a. Report - Event Snapshot b. No role is associated with
			 * User U1 c. 'View Status' and 'Run Report' rights on resources RS.
			 * 6. Status Type ENST, EMST, ETST and ESST are updated on day D1
			 * time hour H1 for resource RS. Expected Result:No Expected Result
			 */
			// 653312

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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

			// Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event Related ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue, stateNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateNumTypeName);
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
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue, stateTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateTextTypeName);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strSSTValue,
						stateSaturatTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateSaturatTypeName);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strMSTValue, stateMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[3] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateMultiTypeName);
				if (strSTvalue[3].compareTo("") != 0) {
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
						seleniumPrecondition, stateMultiTypeName,
						strEStatusName1, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strEStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, stateMultiTypeName,
						strEStatusName1);
				if (strEStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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

				for (int i = 1; i < strSTvalue.length; i++) {
					strFuncResult = objRT.selAndDeselSTInEditRT(
							seleniumPrecondition, strSTvalue[i], true);
				}

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

			// *2. Resources RS is created under RT. */

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

			// ET
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(
						seleniumPrecondition, strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strViewRightValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				String[] updateRightValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
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

			// User1

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EventSnapShotReprt");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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

			// User2

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);
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

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user U1, navigate to Reports>>Event
			 * Reports, click on 'Event Snapshot'. Expected Result:'Event
			 * Snapshot Report (Step 1 of 2)' screen is displayed with: 1. Start
			 * Date field with calender widget 2. End Date field with calender
			 * widget 3. Report Format a. Web Browser (HTML) b. Excel Report
			 * (XLSX) 4. List of Event Templates
			 */
			// 653313

			try {
				assertEquals("", strFuncResult);

				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_2,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = { stateNumTypeName,
						stateTextTypeName, stateMultiTypeName,
						stateSaturatTypeName };
				String[] strRoleStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, true, false);
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
						strUpdate1, strSTvalue[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate2, strSTvalue[1], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strUpdateValue1, strSTvalue[2]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strEStatusValue[0], strSTvalue[3], false, "", "");

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
				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			try {
				assertEquals("", strFuncResult);

				strLastUpdArr = selenium.getText(
						"//span[text()='" + strEStatusName1 + "']"
								+ "/following-sibling::span[@class='time'][1]")
						.split(" ");
				strLastUpdArr = strLastUpdArr[2].split(":");
				strUpdateDataHr = strLastUpdArr[0];
				String str[] = strLastUpdArr[1].split("\\)");
				strLastUpdArr[1] = str[0];
				strUpdateDataMin = strLastUpdArr[1];

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

				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToEventSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//td/input[@id='searchStartDate']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='ui-datepicker-div']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Start Date field with calender widget is displayed");

				String strElementID = "//td/input[@id='searchEndDate']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				log4j
						.info("Start Date field with calender widget is NOT displayed");
				strFuncResult = "Start Date field with calender widget is NOT displayed";
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='ui-datepicker-div']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("End Date field with calender widget is displayed");

				String strElementID = "css=input[value='" + strETValue
						+ "'][name='eventTypeID']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				log4j
						.info("End Date field with calender widget is NOT displayed");
				strFuncResult = "End Date field with calender widget is NOT displayed";
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//input[@class='checkbox'][@type='radio'][@value='HTML'][@name='reportFormat']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				log4j.info("Web Browser (HTML) is displayed");
			} catch (AssertionError Ae) {
				log4j.info("Web Browser (HTML) is NOT displayed");
				strFuncResult = "Web Browser (HTML) is NOT displayed";
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//input[@class='checkbox'][@type='radio'][@value='CSV'][@name='reportFormat']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				log4j.info("Excel Report (XLSX) is displayed");
			} catch (AssertionError Ae) {
				log4j.info("Excel Report (XLSX) is NOT displayed");
				strFuncResult = "Excel Report (XLSX) is NOT displayed";
				gstrReason = strFuncResult;

			}

			/*
			 * STEP : Action:Select date 'D1' as the 'Start and End Date',
			 * Select Web Browser (HTML) radio button, Select event template ET1
			 * under 'Event Templates' section, and then click on 'Next'.
			 * Expected Result:'Event Snapshot Report (Step 2 of 2)' screen is
			 * displayed with : 1.Select an Event 2. Snapshot Date and Time
			 * should fall between (boxes) 3. Snapshot Date (mm/dd/yyyy):** 4.
			 * Snapshot Time - Hour** 5.Snapshot Time - Minutes **
			 */
			// 653314

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

				strFuncResult = objRep
						.selectHtmlOrExcelformatInEventSnapShotReport(selenium,
								strApplTime, strApplTime, strETValue, true);
				strHr = strCurentDat[2];
				strMin = strCurentDat[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "m",
						"mm");
				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "H",
						"HH");

				String strTime = selenium.getText("//div[@id='mainContainer']"
						+ "/form/table/tbody/tr/td/table/tbody/tr/td[2]");
				strHr = strTime.substring(11, 13);
				System.out.println(strHr);

				strMin = strTime.substring(14, 16);
				System.out.println(strMin);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");
				System.out.println(strTimeReport);

				String str = strLastUpdArr[0] + ":" + strLastUpdArr[1];
				str = dts.addTimetoExisting(str, 1, "HH:mm");
				strLastUpdArr = str.split(":");

				String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterEvntSnapshotGenerateReport(
						selenium, strCSTtime, strEventValue, strLastUpdArr[0],
						strLastUpdArr[1]);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");

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

			int intCount = 0;
			int intCount1 = 0;
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

			try {
				assertEquals("", strFuncResult);

				String strTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"dd MMM yyyy");
				String strFutureDate = dts.DaytoExistingDate(strTime, 1,
						"dd MMM yyyy");
				strApplTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"M/d/yyyy");

				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "HH",
						"H");
				/*
				 * strLastUpdArr[1]=dts.converDateFormat(strLastUpdArr[1], "mm",
				 * "m");
				 */

				String str = "Event Snapshot for " + strEveName + " - "
						+ strApplTime + " " + strLastUpdArr[0] + ":"
						+ strLastUpdArr[1]
						+ " Central Standard Time. Event Start:" + " "
						+ strTime + " " + strHr + ":" + strMin
						+ ". Event End: " + strFutureDate + "" + " " + strHr
						+ ":" + strMin + ". ";

				String[][] strTestData = {
						{ str, "", "", "", "", "", "", "", "", "", "", "", "" },
						{ "Resource Type", "Resource", "Comment",
								"Last Update", "By User" },
						{
								strResrctTypName,
								strResource,
								"",
								strTime + " " + strUpdateDataHr + ":"
										+ strUpdateDataMin, strUsrFulName_1 } };

				strFuncResult = OFC.readAndVerifyDataExcel(strTestData,
						strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Status types eNST, eMST, eTST and eSST are NOT displayed for RS in the generated report. ");

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				log4j
						.info("Status types eNST, eMST, eTST and eSST are displayed for RS in the generated report. ");
			}

			/*
			 * STEP : Action:Navigate to Reports>>Event Reports, click on 'Event
			 * Snapshot' , Select day D1 as start and end date, Select Excel
			 * Report (XLSX) radio button, Select 'Event', select day D1, hour
			 * H1 and click on 'Generate Report' Expected Result:Event Snapshot
			 * report is generated in XLSX format with following details:
			 * Header:
			 * "Event Snapshot for < Event Name > - MM/DD/YYYY HH:MM Central Standard Time. Event Start: DD MMM YYYY HH:MM. Event End: DD MMM YYYY HH:MM."
			 * Columns: 1. Resource Type 2. Resource 3. Comments 4. Last Update
			 * 5. By User Status types ENST, EMST, ETST and ESST are not
			 * displayed for RS in the generated report.
			 */
			// 653316

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToEventSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

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

				strFuncResult = objRep
						.selectHtmlOrExcelformatInEventSnapShotReport(selenium,
								strApplTime, strApplTime, strETValue, false);
				strHr = strCurentDat[2];
				strMin = strCurentDat[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strEndTime = selenium
						.getText("//div[@id='mainContainer']"
								+ "/form/table/tbody/tr/td/table/tbody/tr/td[3]");

				String str[] = strEndTime.split(" ");
				str[1] = dts.addTimetoExisting(str[1], 2, "HH:mm");
				String str2[] = str[1].split(":");

				strFuncResult = objRep
						.enterEvntSnapshotGenerateReportAndVrfyValidationMsg(
								selenium, str[0], strEventValue, str2[0],
								str2[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			String strCurentDatReportGen[] = {};
			String strData[] = {};
			String strTimeIn = "";

			try {
				assertEquals("", strFuncResult);
				strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "m",
						"mm");
				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "H",
						"HH");

				String strTime = selenium.getText("//div[@id='mainContainer']"
						+ "/form/table/tbody/tr/td/table/tbody/tr/td[2]");
				strHr = strTime.substring(11, 13);
				System.out.println(strHr);

				strMin = strTime.substring(14, 16);
				System.out.println(strMin);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");
				System.out.println(strTimeReport);

				String str = strLastUpdArr[0] + ":" + strLastUpdArr[1];
				str = dts.addTimetoExisting(str, 1, "HH:mm");
				strLastUpdArr = str.split(":");

				strCurentDatReportGen = objGeneral.getSnapTime(selenium);
				String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterEvntSnapshotGenerateReport(
						selenium, strCSTtime, strEventValue, strLastUpdArr[0],
						strLastUpdArr[1]);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"dd MMM yyyy");
				String strFutureDate = dts.DaytoExistingDate(strTime, 1,
						"dd MMM yyyy");
				strApplTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"M/d/yyyy");

				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "HH",
						"H");

				strData = new String[] {
						strResource,
						"�",
						strTime + " " + strUpdateDataHr + ":"
								+ strUpdateDataMin, strUsrFulName_1 };

				strTimeIn = strTime;

				String strRepGen = "Event Snapshot: " + strApplTime + " "
						+ strLastUpdArr[0] + ":" + strLastUpdArr[1]
						+ " Central Standard Time";
				strFuncResult = objRep.selectHTMLReport(selenium, strEveName,
						"Event Start:" + " " + strTime + " " + strHr + ":"
								+ strMin + ". Event End: " + strFutureDate + ""
								+ " " + strHr + ":" + strMin, strRepGen);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strHeaderName[] = { strResrctTypName, "Comment",
						"Last Update", "By User" };
				strFuncResult = objRep.verifyHeadersInHTMLEventSnapshotReport(
						selenium, strHeaderName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.verifDataInHTMLEventSnapshotReport(
						selenium, strData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {

				assertEquals("", strFuncResult);
				strData = new String[] { "Summary", "�", "�", "�" };
				strFuncResult = objRep
						.verifSummaryDataInHTMLEventSnapshotReport(selenium,
								strData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.verifFoooterInHTMLEventSnapshotReport(
						selenium, strTimeIn, strCurentDatReportGen[2] + ":"
								+ strCurentDatReportGen[3]);
				selenium.close();
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
			gstrTCID = "FTS-118884";
			gstrTO = "Update statuses of event related status types added at the resource type level for resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role WITHOUT view/update rights for these status types CANNOT view these status types in the Event Snapshot Report.";
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

	// end//testFTS118884//
	
	//start//testFTS118883//
	/***************************************************************
	'Description		:Update statuses of event related status types added at the resource level for resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role WITHOUT view/update rights for these status types CANNOT view these status types in the Event Snapshot Report.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:10/17/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS118883() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();
		Reports objRep = new Reports();
		General objGeneral = new General();
		OfficeCommonFunctions OFC = new OfficeCommonFunctions();

		try {
			gstrTCID = "118883"; // Test Case Id
			gstrTO = " Update statuses of event related status types added at the resource level for resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role WITHOUT view/update rights for these status types CANNOT view these status types in the Event Snapshot Report.";// Test
																																																																														// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// Admin Login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatTypeValue = "Number";

			// Event related ST
			String stateNumTypeName = "eNST" + strTimeText;
			String stateTextTypeName = "eTST" + strTimeText;
			String stateMultiTypeName = "eMST" + strTimeText;
			String stateSaturatTypeName = "eSST" + strTimeText;

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";

			String strEStatusName1 = "eSta" + strTimeText;
			String strEStatusValue[] = new String[2];

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			String strUserName_2 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";

			String strEveName = "AutoEve_1" + strTimeText;
			String strInfo = "This is an automation event";
			String strEventValue = "";
			String strETValue = "";

			String strUpdate1 = "101";
			String strUpdate2 = "Update2";
			String strApplTime = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xlsx";
			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "EventSnapshot_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strTimeReport = "";
			String strHr = "";
			String strMin = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strStatType1 = "AutoSt1_" + strTimeText;
			String strTxtStatTypDefn = "Auto";
			String[] strST = new String[1];

			String strState = "Alabama";
			String strCountry = "Barbour County";

			String strLastUpdArr[] = {};
			String strUpdateDataHr = "";
			String strUpdateDataMin = "";

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1 and NOT with event related status types ENST
			 * (Number), EMST (Multi), ETST (Text) and ESST (Saturation Score)
			 * 2. Resources RS is created under RT. 3. Status Type ENST, EMST,
			 * ETST and ESST are added for resource RS at the resource level 4.
			 * Event template ET1 is created selecting resource type RT and
			 * status types ENST, EMST, ETST, ESST 5. Event E1 is created under
			 * ET1 selecting resource RS 6. User U1 has following rights: a.
			 * Report - Event Snapshot b. No role is associated with User U1 c.
			 * 'View Status' and 'Run Report' rights on resources RS. 7. Status
			 * Type ENST, EMST, ETST and ESST are updated on day D1 time hour H1
			 * for resource RS. Expected Result:No Expected Result
			 */
			// 653307

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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

			// Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event Related ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue, stateNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateNumTypeName);
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
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue, stateTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateTextTypeName);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strSSTValue,
						stateSaturatTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateSaturatTypeName);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strMSTValue, stateMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[3] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateMultiTypeName);
				if (strSTvalue[3].compareTo("") != 0) {
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
						seleniumPrecondition, stateMultiTypeName,
						strEStatusName1, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strEStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, stateMultiTypeName,
						strEStatusName1);
				if (strEStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNumStatTypeValue,
						strStatType1, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								strStatType1);
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='" + strST[0]
								+ "']");

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

			// RS

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
				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < strSTvalue.length; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(seleniumPrecondition, strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			// ET
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(
						seleniumPrecondition, strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strViewRightValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				String[] updateRightValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
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

			// User1

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EventSnapShotReprt");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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

			// User2

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);
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

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_2,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = { stateNumTypeName,
						stateTextTypeName, stateMultiTypeName,
						stateSaturatTypeName };
				String[] strRoleStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, true, false);
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
						strUpdate1, strSTvalue[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate2, strSTvalue[1], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strUpdateValue1, strSTvalue[2]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strEStatusValue[0], strSTvalue[3], false, "", "");

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
				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strLastUpdArr = selenium.getText(
						"//span[text()='" + strEStatusName1 + "']"
								+ "/following-sibling::span[@class='time'][1]")
						.split(" ");
				strLastUpdArr = strLastUpdArr[2].split(":");
				strUpdateDataHr = strLastUpdArr[0];
				String str[] = strLastUpdArr[1].split("\\)");
				strLastUpdArr[1] = str[0];
				strUpdateDataMin = strLastUpdArr[1];

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
			 * STEP : Action:Login as user U1, navigate to Reports>>Event
			 * Reports, click on 'Event Snapshot'. Expected Result:'Event
			 * Snapshot Report (Step 1 of 2)' screen is displayed with: 1. Start
			 * Date field with calender widget 2. End Date field with calender
			 * widget 3. Report Format: a. Web Browser (HTML) b. Excel Report
			 * (XLSX) 4. List of Event Templates
			 */
			// 653308

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

				strFuncResult = objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToEventSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//td/input[@id='searchStartDate']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='ui-datepicker-div']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Start Date field with calender widget is displayed");

				String strElementID = "//td/input[@id='searchEndDate']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				log4j
						.info("Start Date field with calender widget is NOT displayed");
				strFuncResult = "Start Date field with calender widget is NOT displayed";
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='ui-datepicker-div']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("End Date field with calender widget is displayed");

				String strElementID = "css=input[value='" + strETValue
						+ "'][name='eventTypeID']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				log4j
						.info("End Date field with calender widget is NOT displayed");
				strFuncResult = "End Date field with calender widget is NOT displayed";
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//input[@class='checkbox'][@type='radio'][@value='HTML'][@name='reportFormat']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				log4j.info("Web Browser (HTML) is displayed");
			} catch (AssertionError Ae) {
				log4j.info("Web Browser (HTML) is NOT displayed");
				strFuncResult = "Web Browser (HTML) is NOT displayed";
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//input[@class='checkbox'][@type='radio'][@value='CSV'][@name='reportFormat']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				log4j.info("Excel Report (XLSX) is displayed");
			} catch (AssertionError Ae) {
				log4j.info("Excel Report (XLSX) is NOT displayed");
				strFuncResult = "Excel Report (XLSX) is NOT displayed";
				gstrReason = strFuncResult;

			}

			/*
			 * STEP : Action:Select date 'D1' as the 'Start and End Date',
			 * Select Web Browser (HTML) radio button, Select event template ET1
			 * under 'Event Templates' section, and then click on 'Next'.
			 * Expected Result:'Event Snapshot Report (Step 2 of 2)' screen is
			 * displayed with : 1.Select an Event 2. Snapshot Date and Time
			 * should fall between (boxes) 3. Snapshot Date (mm/dd/yyyy):**
			 * 2.Snapshot Time - Hour** 3.Snapshot Time - Minutes **
			 */
			// 653309

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

				strFuncResult = objRep
						.selectHtmlOrExcelformatInEventSnapShotReport(selenium,
								strApplTime, strApplTime, strETValue, true);
				strHr = strCurentDat[2];
				strMin = strCurentDat[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "m",
						"mm");
				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "H",
						"HH");

				String strTime = selenium.getText("//div[@id='mainContainer']"
						+ "/form/table/tbody/tr/td/table/tbody/tr/td[2]");
				strHr = strTime.substring(11, 13);
				System.out.println(strHr);

				strMin = strTime.substring(14, 16);
				System.out.println(strMin);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");
				System.out.println(strTimeReport);

				String str = strLastUpdArr[0] + ":" + strLastUpdArr[1];
				str = dts.addTimetoExisting(str, 1, "HH:mm");
				strLastUpdArr = str.split(":");

				String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterEvntSnapshotGenerateReport(
						selenium, strCSTtime, strEventValue, strLastUpdArr[0],
						strLastUpdArr[1]);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * STEP : Action:Select 'Event', select day D1, hour H1 and click on
			 * 'Generate Report' Expected Result:Event Snapshot report is
			 * generated in HTML format with following details: Header:
			 * "Event Snapshot "< Event name > Event snapshot: MM/DD/YYYY HH:MM
			 * Central Standard Time. Event Start: DD MMM YYYY HH:MM. Event End:
			 * DD MMM YYYY HH:MM." Columns: 1. Resource Type Resources in event
			 * 2. Comment 3. Last Update 4. By User Status types ENST, EMST,
			 * ETST and ESST are not displayed in the generated report. Footer:
			 * EMResource Report Created: DD MM YYYY HH:MM < Time Zone >
			 */
			// 653310

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

			int intCount = 0;
			int intCount1 = 0;
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

			try {
				assertEquals("", strFuncResult);

				String strTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"dd MMM yyyy");
				String strFutureDate = dts.DaytoExistingDate(strTime, 1,
						"dd MMM yyyy");
				strApplTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"M/d/yyyy");

				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "HH",
						"H");
				/*
				 * strLastUpdArr[1]=dts.converDateFormat(strLastUpdArr[1], "mm",
				 * "m");
				 */

				String str = "Event Snapshot for " + strEveName + " - "
						+ strApplTime + " " + strLastUpdArr[0] + ":"
						+ strLastUpdArr[1]
						+ " Central Standard Time. Event Start:" + " "
						+ strTime + " " + strHr + ":" + strMin
						+ ". Event End: " + strFutureDate + "" + " " + strHr
						+ ":" + strMin + ". ";

				String[][] strTestData = {
						{ str, "", "", "", "", "", "", "", "", "", "", "", "" },
						{ "Resource Type", "Resource", "Comment",
								"Last Update", "By User" },
						{
								strResrctTypName,
								strResource,
								"",
								strTime + " " + strUpdateDataHr + ":"
										+ strUpdateDataMin, strUsrFulName_1 } };

				strFuncResult = OFC.readAndVerifyDataExcel(strTestData,
						strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Status types eNST, eMST, eTST and eSST are NOT displayed for RS in the generated report. ");

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				log4j
						.info("Status types eNST, eMST, eTST and eSST are displayed for RS in the generated report. ");
			}

			/*
			 * STEP : Action:Navigate to Reports>>Event Reports, click on 'Event
			 * Snapshot' , Select day D1 as start and end date, Select Excel
			 * Report (XLSX) radio button, Select 'Event', select day D1, hour
			 * H1 and click on 'Generate Report' Expected Result:Event Snapshot
			 * report is generated in XLSX format with following details:
			 * Header:
			 * "Event Snapshot for < Event Name > - MM/DD/YYYY HH:MM Central Standard Time. Event Start: DD MMM YYYY HH:MM. Event End: DD MMM YYYY HH:MM."
			 * Columns: 1. Resource Type 2. Resource 3. Comments 4. Last Update
			 * 5. By User Status types ENST, EMST, ETST and ESST are not
			 * displayed for RS in the generated report.
			 */
			// 653311

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToEventSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

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

				strFuncResult = objRep
						.selectHtmlOrExcelformatInEventSnapShotReport(selenium,
								strApplTime, strApplTime, strETValue, false);
				strHr = strCurentDat[2];
				strMin = strCurentDat[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strEndTime = selenium
						.getText("//div[@id='mainContainer']"
								+ "/form/table/tbody/tr/td/table/tbody/tr/td[3]");

				String str[] = strEndTime.split(" ");
				str[1] = dts.addTimetoExisting(str[1], 2, "HH:mm");
				String str2[] = str[1].split(":");

				strFuncResult = objRep
						.enterEvntSnapshotGenerateReportAndVrfyValidationMsg(
								selenium, str[0], strEventValue, str2[0],
								str2[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			String strCurentDatReportGen[] = {};
			String strData[] = {};
			String strTimeIn = "";

			try {
				assertEquals("", strFuncResult);
				strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "m",
						"mm");
				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "H",
						"HH");

				String strTime = selenium.getText("//div[@id='mainContainer']"
						+ "/form/table/tbody/tr/td/table/tbody/tr/td[2]");
				strHr = strTime.substring(11, 13);
				System.out.println(strHr);

				strMin = strTime.substring(14, 16);
				System.out.println(strMin);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");
				System.out.println(strTimeReport);

				String str = strLastUpdArr[0] + ":" + strLastUpdArr[1];
				str = dts.addTimetoExisting(str, 1, "HH:mm");
				strLastUpdArr = str.split(":");

				strCurentDatReportGen = objGeneral.getSnapTime(selenium);
				String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterEvntSnapshotGenerateReport(
						selenium, strCSTtime, strEventValue, strLastUpdArr[0],
						strLastUpdArr[1]);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"dd MMM yyyy");
				String strFutureDate = dts.DaytoExistingDate(strTime, 1,
						"dd MMM yyyy");
				strApplTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"M/d/yyyy");

				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "HH",
						"H");

				strData = new String[] {
						strResource,
						"�",
						strTime + " " + strUpdateDataHr + ":"
								+ strUpdateDataMin, strUsrFulName_1 };

				strTimeIn = strTime;

				String strRepGen = "Event Snapshot: " + strApplTime + " "
						+ strLastUpdArr[0] + ":" + strLastUpdArr[1]
						+ " Central Standard Time";
				strFuncResult = objRep.selectHTMLReport(selenium, strEveName,
						"Event Start:" + " " + strTime + " " + strHr + ":"
								+ strMin + ". Event End: " + strFutureDate + ""
								+ " " + strHr + ":" + strMin, strRepGen);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strHeaderName[] = { strResrctTypName, "Comment",
						"Last Update", "By User" };
				strFuncResult = objRep.verifyHeadersInHTMLEventSnapshotReport(
						selenium, strHeaderName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.verifDataInHTMLEventSnapshotReport(
						selenium, strData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {

				assertEquals("", strFuncResult);
				strData = new String[] { "Summary", "�", "�", "�" };
				strFuncResult = objRep
						.verifSummaryDataInHTMLEventSnapshotReport(selenium,
								strData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.verifFoooterInHTMLEventSnapshotReport(
						selenium, strTimeIn, strCurentDatReportGen[2] + ":"
								+ strCurentDatReportGen[3]);
				selenium.close();
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
			gstrTCID = "FTS-118883";
			gstrTO = "Update statuses of event related status types added at the resource level for resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role WITHOUT view/update rights for these status types CANNOT view these status types in the Event Snapshot Report.";
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

	// end//testFTS118883//

	//start//testFTS118649//
	/***************************************************************
	'Description		:Update a status value of a status type ST that was added for a resource RS at the resource level, verify that the data is displayed appropriately in the  generated 'Event Snapshot Report'
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:10/18/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS118649() throws Exception {
	
		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();
		Reports objRep = new Reports();
		General objGeneral = new General();
		OfficeCommonFunctions OFC = new OfficeCommonFunctions();
		EventList objEventList = new EventList();
		
		try{	
		gstrTCID = "118649";	//Test Case Id	
		gstrTO = " Update a status value of a status type ST that was added for a resource RS at the resource level, verify that the data is displayed appropriately in the  generated 'Event Snapshot Report'";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

		Date_Time_settings dts = new Date_Time_settings();
		String strFILE_PATH = pathProps.getProperty("TestData_path");
		gstrTimetake = dts.timeNow("HH:mm:ss");
		String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
		String strTimeTxt = dts.getCurrentDate("MMddyyyy");
						
		String strLoginUserName = rdExcel.readData("Login", 3, 1);
		String strLoginPassword = rdExcel.readData("Login", 3, 2);
		String strRegn = rdExcel.readData("Login", 3, 4);

		
		// Status types
		String strStatTypDefn = "Automation";
		String strNSTValue = "Number";
		String strMSTValue = "Multi";
		String strTSTValue = "Text";
		String strSSTValue = "Saturation Score";
		
		// role based
		String statrNumTypeName = "AutoNST" + strTimeText;
		String statrTextTypeName = "AutoTST" + strTimeText;
		String statrMultiTypeName = "AutoMST" + strTimeText;
		String statrSaturatTypeName = "AutoSST" + strTimeText;
		String strStatusTypeValues[] = new String[4];
		

		String strStatTypeColor = "Black";
		String strStatusName1 = "Stat" + strTimeTxt;
		String strStatusValue[] = new String[2];
		
		//ST
		String strNumStatTypeValue = "Number";
		String strStatType1 = "AutoSt1_" + strTimeText;
		String strTxtStatTypDefn = "Auto";
		String[] strST = new String[1];
		
		//RS
		
		String strState = "Alabama";
		String strCountry = "Barbour County";
		String strResource1 = "AutoRs1" + strTimeText;
		String strResource2 = "AutoRs2" + strTimeText;
		String strAbbrv = "AB";
		String strResVal = "";

		String strStandResType = "Aeromedical";
		String strContFName = "auto";
		String strContLName = "qsg";
		String strRSValue[] = new String[2];
		
		//RT
		
		String strResrctTypName = "AutoRt_" + strTimeText;
		String strRTValue = "";
		
		//ET
		
		String strTempName = "ET" + System.currentTimeMillis();
		String strTempDef = "Automation";

		String strEveName = "AutoEve_1" + strTimeText;
		String strInfo = "This is an automation event";
		String strEventValue = "";
		String strETValue = "";
		
		String strRoleName = "AutoR_" + strTimeText;
		String strRoleRights[][] = {};
		String strRoleValue = "";
		
		//User
		String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
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
		
		String strLastUpdArr[] = {};
		String strUpdateDataHr = "";
		String strUpdateDataMin = "";
		
		String strUpdate1 = "101";
		String strUpdate2 = "Update2";
		String strApplTime = "";

		String strTimeReport = "";
		String strHr = "";
		String strMin = "";
		
		String strAutoFilePath = propElementAutoItDetails
		.getProperty("Reports_DownloadFile_Path");
		String strAutoFileName = propElementAutoItDetails
		.getProperty("Reports_DownloadFile_Name");

		String strCSVDownlPath = pathProps
		.getProperty("Reports_DownloadCSV_Path")
		+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xlsx";
		String strCSVDownlRenamedPath = pathProps
		.getProperty("Reports_DownloadCSV_Path")
		+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xls";

		String strCSVFileNameRenamed = "EventSnapshot_" + gstrTCID + "_"
		+ strTimeText;

		String strCSVDownlPathRenamed = pathProps
		.getProperty("Reports_DownloadCSV_Path");


		/*
		* STEP :
		  Action:Preconditions:
			1. Resource type RT1 is associated with status type ST1 and NOT with status types NST (Number), 
			MST (Multi), TST (Text) and SST (Saturation Score)
			2. Resources RS1 and RS2 are created under RT1.
			3. Status Type NST, MST, TST and SST are added for resource RS1 at the resource level
			4. Event template ET1 is created selecting resource type RT1 and status types NST, MST, TST, SST
			5. Event E1 is created under ET1 selecting resource RS1
			6. User U1 has following rights:
			'Report - Event Snapshot'
			Role to update status type NST, MST, TST and SST
			'Update Status' and 'Run Report' rights on resources RS1 and RS2
				  Expected Result:No Expected Result
		*/
		//652842

		log4j.info("---------------Precondtion for test case " + gstrTCID
				+ " starts----------");

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
					seleniumPrecondition, strNSTValue, statrNumTypeName,
					strStatTypDefn, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strStatusTypeValues[0] = objStatusTypes
					.fetchSTValueInStatTypeList(seleniumPrecondition,
							statrNumTypeName);
			if (strStatusTypeValues[0].compareTo("") != 0) {
				strFuncResult = "";

			} else {
				strFuncResult = "Failed to fetch status type value";
			}

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		//TST
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
					seleniumPrecondition, strTSTValue, statrTextTypeName,
					strStatTypDefn, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strStatusTypeValues[1] = objStatusTypes
					.fetchSTValueInStatTypeList(seleniumPrecondition,
							statrTextTypeName);
			if (strStatusTypeValues[1].compareTo("") != 0) {
				strFuncResult = "";

			} else {
				strFuncResult = "Failed to fetch status type value";
			}

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		// SST
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
					seleniumPrecondition, strSSTValue,
					statrSaturatTypeName, strStatTypDefn, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strStatusTypeValues[2] = objStatusTypes
					.fetchSTValueInStatTypeList(seleniumPrecondition,
							statrSaturatTypeName);
			if (strStatusTypeValues[2].compareTo("") != 0) {
				strFuncResult = "";

			} else {
				strFuncResult = "Failed to fetch status type  value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		

		// MST
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
					seleniumPrecondition, strMSTValue, statrMultiTypeName,
					strStatTypDefn, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strStatusTypeValues[3] = objStatusTypes
					.fetchSTValueInStatTypeList(seleniumPrecondition,
							statrMultiTypeName);
			if (strStatusTypeValues[3].compareTo("") != 0) {
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
					strStatusName1, strMSTValue, strStatTypeColor, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
					seleniumPrecondition, statrMultiTypeName,
					strStatusName1);
			if (strStatusValue[0].compareTo("") != 0) {
				strFuncResult = "";

			} else {
				strFuncResult = "Failed to fetch status value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		
		// ST1
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
					seleniumPrecondition, strNumStatTypeValue,
					strStatType1, strTxtStatTypDefn, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String strStatValue = objStatusTypes
					.fetchSTValueInStatTypeList(seleniumPrecondition,
							strStatType1);
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
			strFuncResult = objRT.resrcTypeMandatoryFlds(
					seleniumPrecondition, strResrctTypName,
					"css=input[name='statusTypeID'][value='" + strST[0]
							+ "']");

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

		//RS1
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRs.navResourcesList(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRs.createResourceWitLookUPadres(
					seleniumPrecondition, strResource1, strAbbrv,
					strResrctTypName, strContFName, strContLName, strState,
					strCountry, strStandResType);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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

		//RS2
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRs.navResourcesList(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRs.createResourceWitLookUPadres(
					seleniumPrecondition, strResource2, strAbbrv,
					strResrctTypName, strContFName, strContLName, strState,
					strCountry, strStandResType);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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

		//Status types added at resource level
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
					strResource1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		for (int i = 0; i < strStatusTypeValues.length; i++) {
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strStatusTypeValues[i], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRs.saveAndVerifyResourceInRSList(
					seleniumPrecondition, strResource1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		// ET
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objEventSetup
					.navToEventSetupPge(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objEventSetup
					.createEventTemplate(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String[] strResTypeValue = { strRTValue };
			String[] strStatusTypeValue = { strStatusTypeValues[0], strStatusTypeValues[1],
					strStatusTypeValues[2], strStatusTypeValues[3] };
			strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
					seleniumPrecondition, strTempName, strTempDef, true,
					strResTypeValue, strStatusTypeValue);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strETValue = objEventSetup.fetchETInETList(
					seleniumPrecondition, strTempName);
			if (strETValue.compareTo("") != 0) {
				strFuncResult = "";
			} else {
				strFuncResult = "Function failes to fetch ETY Value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		// Event
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objEventSetup
					.navToEventManagementNew(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {

			assertEquals("", strFuncResult);

			strFuncResult = objEventSetup.createEvent(seleniumPrecondition,
					strTempName, strResource1, strEveName, strInfo, true);
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
		
		// Role
		try {
			assertEquals("", strFuncResult);

			strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
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

			String[] strViewRightValue = { strStatusTypeValues[0], strStatusTypeValues[1],
					strStatusTypeValues[2], strStatusTypeValues[3] };
			String[] updateRightValue = { strStatusTypeValues[0], strStatusTypeValues[1],
					strStatusTypeValues[2], strStatusTypeValues[3] };
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
		
		// User1

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
			String strOptions = propElementDetails
					.getProperty("CreateNewUsr.AdvOptn.EventSnapShotReprt");
			strFuncResult = objCreateUsers.advancedOptns(
					seleniumPrecondition, strOptions, true);
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
					seleniumPrecondition, strResource1, strRSValue[0],
					false, true, true, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
					seleniumPrecondition, strResource1, strRSValue[0],
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
	
		log4j.info("~~~~~PRE-CONDITION " + gstrTCID
				+ " EXECUTION ENDS~~~~~");

		try {
			assertEquals("", strFuncResult);

			strFuncResult = objLogin.logout(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		/*
		* STEP :
		  Action:Login as user U1, Click on event banner of 'EVE'
		  Expected Result:'Event Status' screen is displayed.
		  Status types NST, MST, TST and SST  are displayed on 'Event Status' screen along with resource 'RS' under 'RT'.
		*/
		//652843

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
					strInitPwd);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);

			String strArStatType2[] = { statrMultiTypeName, statrNumTypeName,
					statrSaturatTypeName, statrTextTypeName };

			strFuncResult = objEventList.checkInEventBannerNew(selenium,
					strEveName, strResrctTypName, strResource1,
					strArStatType2);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		
		/*
		* STEP :
		  Action:Update the statuses of NST, MST, TST and SST on day D1 time hour H1 for resource RS1.
		  Expected Result:Updated status values are displayed on 'Event Status' screen.
		*/
		//652857

		try {
			assertEquals("", strFuncResult);
			blnLogin = true;

			String[] strEventStatType = { };
			String[] strRoleStatType = { statrMultiTypeName, statrNumTypeName,
					statrSaturatTypeName, statrTextTypeName };
			strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
					selenium, strResource1, strEventStatType,
					strRoleStatType, false, true);
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

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
					strUpdate1, strStatusTypeValues[0], false, "", "");

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
					strUpdate2, strStatusTypeValues[1], false, "", "");

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
					"7", "8" };
			strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
					strUpdateValue1, strStatusTypeValues[2]);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);

			strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
					strStatusValue[0], strStatusTypeValues[3], false, "", "");

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
			strFuncResult = objViewMap.navResPopupWindowNew(selenium,
					strResource1);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);

			strLastUpdArr = selenium.getText(
					"//span[text()='" + strStatusName1 + "']"
							+ "/following-sibling::span[@class='time'][1]")
					.split(" ");
			strLastUpdArr = strLastUpdArr[2].split(":");
			strUpdateDataHr = strLastUpdArr[0];
			String str[] = strLastUpdArr[1].split("\\)");
			strLastUpdArr[1] = str[0];
			strUpdateDataMin = strLastUpdArr[1];

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
				
		/*
		* STEP :
		  Action:Navigate to Reports>>Event Reports, click on 'Event Snapshot'
		  Expected Result:'Event Snapshot Report (Step 1 of 2)' screen is displayed with:
			1. Start Date field with calender widget
			2. End Date field with calender widget
			3. Report Format: 
			a. Web Browser (HTML)
			b. Excel Report (XLSX)
			4. List of Event Templates
			'Next' and 'Cancel' buttons.
		*/
		//652844

		try {
			assertEquals("", strFuncResult);
			blnLogin = true;

			strFuncResult = objRep.navToEventReports(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRep.navToEventSnapshotReport(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

		try {
			assertEquals("", strFuncResult);

			String strElementID = "//td/input[@id='searchStartDate']";
			strFuncResult = objGeneral.checkForAnElements(selenium,
					strElementID);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}
		try {
			assertEquals("", strFuncResult);

			String strElementID = "//div[@id='ui-datepicker-div']";
			strFuncResult = objGeneral.checkForAnElements(selenium,
					strElementID);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

		try {
			assertEquals("", strFuncResult);
			log4j
					.info("Start Date field with calender widget is displayed");

			String strElementID = "//td/input[@id='searchEndDate']";
			strFuncResult = objGeneral.checkForAnElements(selenium,
					strElementID);
		} catch (AssertionError Ae) {
			log4j
					.info("Start Date field with calender widget is NOT displayed");
			strFuncResult = "Start Date field with calender widget is NOT displayed";
			gstrReason = strFuncResult;

		}
		try {
			assertEquals("", strFuncResult);

			String strElementID = "//div[@id='ui-datepicker-div']";
			strFuncResult = objGeneral.checkForAnElements(selenium,
					strElementID);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}
		try {
			assertEquals("", strFuncResult);
			log4j.info("End Date field with calender widget is displayed");

			String strElementID = "css=input[value='" + strETValue
					+ "'][name='eventTypeID']";
			strFuncResult = objGeneral.checkForAnElements(selenium,
					strElementID);
		} catch (AssertionError Ae) {
			log4j
					.info("End Date field with calender widget is NOT displayed");
			strFuncResult = "End Date field with calender widget is NOT displayed";
			gstrReason = strFuncResult;

		}

		try {
			assertEquals("", strFuncResult);

			String strElementID = "//input[@class='checkbox'][@type='radio'][@value='HTML'][@name='reportFormat']";
			strFuncResult = objGeneral.checkForAnElements(selenium,
					strElementID);
			log4j.info("Web Browser (HTML) is displayed");
		} catch (AssertionError Ae) {
			log4j.info("Web Browser (HTML) is NOT displayed");
			strFuncResult = "Web Browser (HTML) is NOT displayed";
			gstrReason = strFuncResult;

		}

		try {
			assertEquals("", strFuncResult);

			String strElementID = "//input[@class='checkbox'][@type='radio'][@value='CSV'][@name='reportFormat']";
			strFuncResult = objGeneral.checkForAnElements(selenium,
					strElementID);
			log4j.info("Excel Report (XLSX) is displayed");
		} catch (AssertionError Ae) {
			log4j.info("Excel Report (XLSX) is NOT displayed");
			strFuncResult = "Excel Report (XLSX) is NOT displayed";
			gstrReason = strFuncResult;

		}
		
			/*
			 * STEP : Action:Select day D1, hour H1, Select Web Browser (HTML)
			 * radio button and click on 'Generate Report' Expected Result:Event
			 * Snapshot report is generated in HTML format with following
			 * details: Header: "Event Snapshot " < Event name > Event snapshot:
			 * MM/DD/YYYY HH:MM Central Standard Time. Columns: 1. Resource Type
			 * Resources in event 2. Status Types 3. Comment 4. Last Update 5.
			 * By User Status types NST, MST, TST and SST are displayed with
			 * correct values for RS1 in the generated report. Status types
			 * 'NST', 'SST' are displayed under 'Status Type summary' section is
			 * displayed. Column 'Total' is displayed. RS2 is NOT displayed the
			 * generated report. Footer: EMResource Report Created: DD MM YYYY
			 * HH:MM < Time Zone >
			 */
			// 652845

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

			strFuncResult = objRep
					.selectHtmlOrExcelformatInEventSnapShotReport(selenium,
							strApplTime, strApplTime, strETValue, true);
			strHr = strCurentDat[2];
			strMin = strCurentDat[3];
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

		try {
			assertEquals("", strFuncResult);
			strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "m",
					"mm");
			strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "H",
					"HH");

			String strTime = selenium.getText("//div[@id='mainContainer']"
					+ "/form/table/tbody/tr/td/table/tbody/tr/td[2]");
			strHr = strTime.substring(11, 13);
			System.out.println(strHr);

			strMin = strTime.substring(14, 16);
			System.out.println(strMin);

			strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
					"HH:mm", "H:mm");
			System.out.println(strTimeReport);

			String str = strLastUpdArr[0] + ":" + strLastUpdArr[1];
			str = dts.addTimetoExisting(str, 1, "HH:mm");
			strLastUpdArr = str.split(":");

			String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
					"M/d/yyyy");
			strFuncResult = objRep.enterEvntSnapshotGenerateReport(
					selenium, strCSTtime, strEventValue, strLastUpdArr[0],
					strLastUpdArr[1]);

			strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
					"HH:mm", "H:mm");

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

		int intCount = 0;
		int intCount1 = 0;
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

		try {
			assertEquals("", strFuncResult);

			String strTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
					"dd MMM yyyy");
			String strFutureDate = dts.DaytoExistingDate(strTime, 1,
					"dd MMM yyyy");
			strApplTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
					"M/d/yyyy");

			strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "HH",
					"H");
			/*
			 * strLastUpdArr[1]=dts.converDateFormat(strLastUpdArr[1], "mm",
			 * "m");
			 */
					
			String str = "Event Snapshot for " + strEveName + " - "
					+ strApplTime + " " + strLastUpdArr[0] + ":"
					+ strLastUpdArr[1]
					+ " Central Standard Time. Event Start:" + " "
					+ strTime + " " + strHr + ":" + strMin
					+ ". Event End: " + strFutureDate + "" + " " + strHr
					+ ":" + strMin + ". ";

			String[][] strTestData = {
					{ str, "", "", "", "", "", "", "", "", "", "", "", "" },
					{ "Resource Type", "Resource",statrMultiTypeName ,statrNumTypeName,statrSaturatTypeName,
						statrTextTypeName,"Comment","Last Update", "By User" },
					{
							strResrctTypName,
							strResource1,strStatusName1,
							strUpdate1,"393","Update2","",
							strTime + " " + strUpdateDataHr + ":"
									+ strUpdateDataMin, strUsrFulName_1 } };

			strFuncResult = OFC.readAndVerifyDataExcel(strTestData,
					strCSVDownlRenamedPath);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			log4j
					.info("Status types NST, MST, TST and SST are displayed for RS1 in the generated report. ");

			gstrResult = "PASS";
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
			log4j
					.info("Status types NST, MST, TST and SST are NOT displayed for RS in the generated report. ");
		}

		
		/*
		* STEP :
		  Action:Navigate to Reports>>Event Reports, click on 'Event Snapshot' , 	Select day D1, hour H1, Select Excel Report (XLSX) radio button and click on 'Generate Report'
		  Expected Result:Event Snapshot report is generated in XLSX format with following details:
		Header:
		"Event Snapshot for E1 - MM/DD/YYYY HH:MM Central Standard Time. Event Start: DD MMM YYYY HH:MM. Event End: DD MMM YYYY HH:MM."
		Columns:
		1. Resource Type
		2. Resource
		3. Status Types
		4. Comments
		5. Last Update
		6. By User
		Status types NST, MST, TST and SST are displayed with updated  values for RS1 in the generated report.
		RS2 is NOT displayed the generated report.
		*/
		//652888

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRep.navToEventReports(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

		try {
			assertEquals("", strFuncResult);

			strFuncResult = objRep.navToEventSnapshotReport(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

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

			strFuncResult = objRep
					.selectHtmlOrExcelformatInEventSnapShotReport(selenium,
							strApplTime, strApplTime, strETValue, false);
			strHr = strCurentDat[2];
			strMin = strCurentDat[3];
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

		try {
			assertEquals("", strFuncResult);

			String strEndTime = selenium
					.getText("//div[@id='mainContainer']"
							+ "/form/table/tbody/tr/td/table/tbody/tr/td[3]");

			String str[] = strEndTime.split(" ");
			str[1] = dts.addTimetoExisting(str[1], 2, "HH:mm");
			String str2[] = str[1].split(":");

			strFuncResult = objRep
					.enterEvntSnapshotGenerateReportAndVrfyValidationMsg(
							selenium, str[0], strEventValue, str2[0],
							str2[1]);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

		String strCurentDatReportGen[] = {};
		String strData[] = {};
		String strTimeIn = "";

		try {
			assertEquals("", strFuncResult);
			strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "m",
					"mm");
			strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "H",
					"HH");

			String strTime = selenium.getText("//div[@id='mainContainer']"
					+ "/form/table/tbody/tr/td/table/tbody/tr/td[2]");
			strHr = strTime.substring(11, 13);
			System.out.println(strHr);

			strMin = strTime.substring(14, 16);
			System.out.println(strMin);

			strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
					"HH:mm", "H:mm");
			System.out.println(strTimeReport);

			String str = strLastUpdArr[0] + ":" + strLastUpdArr[1];
			str = dts.addTimetoExisting(str, 1, "HH:mm");
			strLastUpdArr = str.split(":");

			strCurentDatReportGen = objGeneral.getSnapTime(selenium);
			String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
					"M/d/yyyy");
			strFuncResult = objRep.enterEvntSnapshotGenerateReport(
					selenium, strCSTtime, strEventValue, strLastUpdArr[0],
					strLastUpdArr[1]);

			strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
					"HH:mm", "H:mm");

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

		try {
			assertEquals("", strFuncResult);
			String strTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
					"dd MMM yyyy");
			String strFutureDate = dts.DaytoExistingDate(strTime, 1,
					"dd MMM yyyy");
			strApplTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
					"M/d/yyyy");

			strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "HH",
					"H");

			strData = new String[] {
					strResource1,strStatusName1,
					"101","393","Update2", 
					"�",
					strTime + " " + strUpdateDataHr + ":"
							+ strUpdateDataMin, strUsrFulName_1 };

			strTimeIn = strTime;

			String strRepGen = "Event Snapshot: " + strApplTime + " "
					+ strLastUpdArr[0] + ":" + strLastUpdArr[1]
					+ " Central Standard Time";
			strFuncResult = objRep.selectHTMLReport(selenium, strEveName,
					"Event Start:" + " " + strTime + " " + strHr + ":"
							+ strMin + ". Event End: " + strFutureDate + ""
							+ " " + strHr + ":" + strMin, strRepGen);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

		
		try {
			assertEquals("", strFuncResult);
			String strHeaderName[] = { strResrctTypName,statrMultiTypeName, statrNumTypeName,
					statrSaturatTypeName,statrTextTypeName,"Comment",
					"Last Update", "By User" };
			strFuncResult = objRep.verifyHeadersInHTMLEventSnapshotReport(
					selenium, strHeaderName);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

		
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRep.verifDataInHTMLEventSnapshotReport(
					selenium, strData);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

		try {

			assertEquals("", strFuncResult);
			strData = new String[] { "Summary","101","393","�","�","�","�"};
			strFuncResult = objRep
					.verifSummaryDataInHTMLEventSnapshotReport(selenium,
							strData);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

				
		try {
			assertEquals("", strFuncResult);

			String[] strHeader = { "Status Type Summary", "Total" };
			String[][] strDataDouble = { { statrNumTypeName, strUpdate1 },{statrSaturatTypeName,"393"} };
			strFuncResult = objRep
					.verifStatusSummaryHeaderDataInHTMLEventSnapshotReport(
							selenium, strDataDouble, strHeader);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}

		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRep.verifFoooterInHTMLEventSnapshotReport(
					selenium, strTimeIn, strCurentDatReportGen[2] + ":"
							+ strCurentDatReportGen[3]);
			selenium.close();
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
		gstrTCID = "FTS-118649";
		gstrTO = "Update a status value of a status type ST that was added for a resource RS at the resource level, verify that the data is displayed appropriately in the  generated 'Event Snapshot Report'";
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


	//end//testFTS118649//
	
	//start//testFTS118730//
	/***************************************************************
	'Description		:Update statuses of event related status types added at the resource type level for resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role WITH only view right for these status types CAN view these status types in the Event Snapshot Report.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:10/18/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS118730() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();
		Reports objRep = new Reports();
		General objGeneral = new General();
		OfficeCommonFunctions OFC = new OfficeCommonFunctions();

		try {
			gstrTCID = "118730"; // Test Case Id
			gstrTO = " Update statuses of event related status types added at the resource type level for resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role WITH only view right for these status types CAN view these status types in the Event Snapshot Report.";// Test
																																																																													// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// Admin Login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Event related ST
			String stateNumTypeName = "eNST" + strTimeText;
			String stateTextTypeName = "eTST" + strTimeText;
			String stateMultiTypeName = "eMST" + strTimeText;
			String stateSaturatTypeName = "eSST" + strTimeText;

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";

			String strEStatusName1 = "eSta" + strTimeText;
			String strEStatusValue[] = new String[2];

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
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

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";

			String strEveName = "AutoEve_1" + strTimeText;
			String strInfo = "This is an automation event";
			String strEventValue = "";
			String strETValue = "";

			String strUpdate1 = "101";
			String strUpdate2 = "Update2";
			String strApplTime = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xlsx";
			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "EventSnapshot_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strTimeReport = "";
			String strHr = "";
			String strMin = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strState = "Alabama";
			String strCountry = "Barbour County";

			String strLastUpdArr[] = {};
			String strUpdateDataHr = "";
			String strUpdateDataMin = "";

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with event related status types ENST (Number), EMST (Multi), ETST
			 * (Text) and ESST (Saturation Score) 2. Resources RS is created
			 * under RT. 3. Event template ET1 is created selecting resource
			 * type RT and status types ENST, EMST, ETST, ESST 4. Event E1 is
			 * created under ET1 selecting resource RS 5. User U1 has following
			 * rights: a. Report - Event Snapshot b. Role to View status types
			 * ENST (number), EMST (multi), ETST (text) and ESST (saturation
			 * score) c. 'View Status' and 'Run Report' rights on resources RS.
			 * 6. Status Type ENST, EMST, ETST and ESST are updated on day D1
			 * time hour H1 for resource RS. Expected Result:No Expected Result
			 */
			// 653129

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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

			// Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event Related ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue, stateNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateNumTypeName);
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
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue, stateTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateTextTypeName);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strSSTValue,
						stateSaturatTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateSaturatTypeName);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strMSTValue, stateMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[3] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateMultiTypeName);
				if (strSTvalue[3].compareTo("") != 0) {
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
						seleniumPrecondition, stateMultiTypeName,
						strEStatusName1, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strEStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, stateMultiTypeName,
						strEStatusName1);
				if (strEStatusValue[0].compareTo("") != 0) {
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

			for (int intST = 1; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(
							seleniumPrecondition, strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
			// RS

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

			// ET
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(
						seleniumPrecondition, strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
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

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strViewRightValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				String[] updateRightValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
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

			// User1

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EventSnapShotReprt");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Update the statuses of NST, MST, TST and SST on day
			 * D1 time hour H1 for resource RS1. Expected Result:Updated status
			 * values are displayed on 'Event Status' screen.
			 */
			// 652857

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { stateMultiTypeName,
						stateNumTypeName, stateSaturatTypeName,
						stateTextTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
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
						strUpdate1, strSTvalue[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate2, strSTvalue[1], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strUpdateValue1, strSTvalue[2]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strEStatusValue[0], strSTvalue[3], false, "", "");

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
				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strLastUpdArr = selenium.getText(
						"//span[text()='" + strEStatusName1 + "']"
								+ "/following-sibling::span[@class='time'][1]")
						.split(" ");
				strLastUpdArr = strLastUpdArr[2].split(":");
				strUpdateDataHr = strLastUpdArr[0];
				String str[] = strLastUpdArr[1].split("\\)");
				strLastUpdArr[1] = str[0];
				strUpdateDataMin = strLastUpdArr[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user U1, navigate to Reports>>Event
			 * Reports, click on 'Event Snapshot'. Expected Result:'Event
			 * Snapshot Report (Step 1 of 2)' screen is displayed with: 1. Start
			 * Date field with calender widget 2. End Date field with calender
			 * widget 3. Report Format: a. Web Browser (HTML) b. Excel Report
			 * (XLSX) 4. List of Event Templates
			 */
			// 653130

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToEventSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//td/input[@id='searchStartDate']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='ui-datepicker-div']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Start Date field with calender widget is displayed");

				String strElementID = "//td/input[@id='searchEndDate']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				log4j
						.info("Start Date field with calender widget is NOT displayed");
				strFuncResult = "Start Date field with calender widget is NOT displayed";
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='ui-datepicker-div']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("End Date field with calender widget is displayed");

				String strElementID = "css=input[value='" + strETValue
						+ "'][name='eventTypeID']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				log4j
						.info("End Date field with calender widget is NOT displayed");
				strFuncResult = "End Date field with calender widget is NOT displayed";
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//input[@class='checkbox'][@type='radio'][@value='HTML'][@name='reportFormat']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				log4j.info("Web Browser (HTML) is displayed");
			} catch (AssertionError Ae) {
				log4j.info("Web Browser (HTML) is NOT displayed");
				strFuncResult = "Web Browser (HTML) is NOT displayed";
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//input[@class='checkbox'][@type='radio'][@value='CSV'][@name='reportFormat']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				log4j.info("Excel Report (XLSX) is displayed");
			} catch (AssertionError Ae) {
				log4j.info("Excel Report (XLSX) is NOT displayed");
				strFuncResult = "Excel Report (XLSX) is NOT displayed";
				gstrReason = strFuncResult;

			}

			/*
			 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
			 * Date' fields, select Event Templates ,select Web Browser (HTML)
			 * radio button and click on 'Next' Expected Result:'Event Snapshot
			 * Report (Step 2 of 2)' screen is displayed with : 1.Select an
			 * Event 2. Snapshot Date and Time should fall between (boxes) 3.
			 * Snapshot Date (mm/dd/yyyy):** 4.Snapshot Time - Hour** 5.Snapshot
			 * Time - Minutes **
			 */
			// 653131

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

				strFuncResult = objRep
						.selectHtmlOrExcelformatInEventSnapShotReport(selenium,
								strApplTime, strApplTime, strETValue, true);
				strHr = strCurentDat[2];
				strMin = strCurentDat[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "m",
						"mm");
				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "H",
						"HH");

				String strTime = selenium.getText("//div[@id='mainContainer']"
						+ "/form/table/tbody/tr/td/table/tbody/tr/td[2]");
				strHr = strTime.substring(11, 13);
				System.out.println(strHr);

				strMin = strTime.substring(14, 16);
				System.out.println(strMin);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");
				System.out.println(strTimeReport);

				String str = strLastUpdArr[0] + ":" + strLastUpdArr[1];
				str = dts.addTimetoExisting(str, 1, "HH:mm");
				strLastUpdArr = str.split(":");

				String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterEvntSnapshotGenerateReport(
						selenium, strCSTtime, strEventValue, strLastUpdArr[0],
						strLastUpdArr[1]);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");

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

			int intCount = 0;
			int intCount1 = 0;
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

			try {
				assertEquals("", strFuncResult);

				String strTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"dd MMM yyyy");
				String strFutureDate = dts.DaytoExistingDate(strTime, 1,
						"dd MMM yyyy");
				strApplTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"M/d/yyyy");

				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "HH",
						"H");
				/*
				 * strLastUpdArr[1]=dts.converDateFormat(strLastUpdArr[1], "mm",
				 * "m");
				 */

				String str = "Event Snapshot for " + strEveName + " - "
						+ strApplTime + " " + strLastUpdArr[0] + ":"
						+ strLastUpdArr[1]
						+ " Central Standard Time. Event Start:" + " "
						+ strTime + " " + strHr + ":" + strMin
						+ ". Event End: " + strFutureDate + "" + " " + strHr
						+ ":" + strMin + ". ";

				String[][] strTestData = {
						{ str, "", "", "", "", "", "", "", "", "", "", "", "" },
						{ "Resource Type", "Resource", stateMultiTypeName,
								stateNumTypeName, stateSaturatTypeName,
								stateTextTypeName, "Comment", "Last Update",
								"By User" },
						{
								strResrctTypName,
								strResource,
								strEStatusName1,
								strUpdate1,
								"393",
								"Update2",
								"",
								strTime + " " + strUpdateDataHr + ":"
										+ strUpdateDataMin, strUsrFulName_1 } };

				strFuncResult = OFC.readAndVerifyDataExcel(strTestData,
						strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Status types NST, MST, TST and SST are displayed for RS1 in the generated report. ");

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				log4j
						.info("Status types NST, MST, TST and SST are NOT displayed for RS in the generated report. ");
			}

			/*
			 * STEP : Action:Select 'Event', select day D1, hour H1 and click on
			 * 'Generate Report' Expected Result:Event Snapshot report is
			 * generated in HTML format with following details: Header:
			 * "Event Snapshot Report" Event name > Event snapshot: MM/DD/YYYY
			 * HH:MM (Provided snapshot time) < Region Time zone > . Event
			 * Start: < Date > < Month > < Year > HH:MM. Event End:< Date > <
			 * Month > < Year > HH:MM Columns: 1. Resource Type Resources
			 * participating in event 2. Status Types 3. Comment 4. Last Update
			 * 5. By User Status types ENST, EMST, ETST and ESST are displayed
			 * with appropriate values for RS in the generated report. Total
			 * values for 'ENST' and 'ESST' are displayed under 'Summary'
			 * section. Status types 'ENST', 'ESST' along with their totals are
			 * displayed under 'Status Type summary' section under 'Total'
			 * column header. Footer: EMResource Report Created: DD MM YYYY
			 * HH:MM < Time Zone > .
			 */
			// 653132

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToEventSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

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

				strFuncResult = objRep
						.selectHtmlOrExcelformatInEventSnapShotReport(selenium,
								strApplTime, strApplTime, strETValue, false);
				strHr = strCurentDat[2];
				strMin = strCurentDat[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strEndTime = selenium
						.getText("//div[@id='mainContainer']"
								+ "/form/table/tbody/tr/td/table/tbody/tr/td[3]");

				String str[] = strEndTime.split(" ");
				str[1] = dts.addTimetoExisting(str[1], 2, "HH:mm");
				String str2[] = str[1].split(":");

				strFuncResult = objRep
						.enterEvntSnapshotGenerateReportAndVrfyValidationMsg(
								selenium, str[0], strEventValue, str2[0],
								str2[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			String strCurentDatReportGen[] = {};
			String strData[] = {};
			String strTimeIn = "";

			try {
				assertEquals("", strFuncResult);
				strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "m",
						"mm");
				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "H",
						"HH");

				String strTime = selenium.getText("//div[@id='mainContainer']"
						+ "/form/table/tbody/tr/td/table/tbody/tr/td[2]");
				strHr = strTime.substring(11, 13);
				System.out.println(strHr);

				strMin = strTime.substring(14, 16);
				System.out.println(strMin);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");
				System.out.println(strTimeReport);

				String str = strLastUpdArr[0] + ":" + strLastUpdArr[1];
				str = dts.addTimetoExisting(str, 1, "HH:mm");
				strLastUpdArr = str.split(":");

				strCurentDatReportGen = objGeneral.getSnapTime(selenium);
				String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterEvntSnapshotGenerateReport(
						selenium, strCSTtime, strEventValue, strLastUpdArr[0],
						strLastUpdArr[1]);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"dd MMM yyyy");
				String strFutureDate = dts.DaytoExistingDate(strTime, 1,
						"dd MMM yyyy");
				strApplTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"M/d/yyyy");

				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "HH",
						"H");

				strData = new String[] {
						strResource,
						strEStatusName1,
						"101",
						"393",
						"Update2",
						"�",
						strTime + " " + strUpdateDataHr + ":"
								+ strUpdateDataMin, strUsrFulName_1 };

				strTimeIn = strTime;

				String strRepGen = "Event Snapshot: " + strApplTime + " "
						+ strLastUpdArr[0] + ":" + strLastUpdArr[1]
						+ " Central Standard Time";
				strFuncResult = objRep.selectHTMLReport(selenium, strEveName,
						"Event Start:" + " " + strTime + " " + strHr + ":"
								+ strMin + ". Event End: " + strFutureDate + ""
								+ " " + strHr + ":" + strMin, strRepGen);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * STEP : Action:Navigate to Reports>>Event Reports, click on 'Event
			 * Snapshot' , Select day D1as start and end date, Select Excel
			 * Report (XLSX) radio button, Select 'Event', select day D1, hour
			 * H1 and click on 'Generate Report' Expected Result:Event Snapshot
			 * report is generated in XLSX format with following details:
			 * Header: "Event Snapshot for < Name of Event > - MM/DD/YYYY HH:MM
			 * (Provided snapshot time) < Region Time Zone > Event Start: < Date
			 * > < Month > < Year > HH:MM. Event End:< Date > < Month > < Year >
			 * HH:MM Columns: 1. Resource Type 2. Resource 3. Status Types 4.
			 * Comments 5. Last Update 6. By User Status types ENST, EMST, ETST
			 * and ESST are displayed with updated values for RS in the
			 * generated report.
			 */
			// 653133

			try {
				assertEquals("", strFuncResult);
				String strHeaderName[] = { strResrctTypName,
						stateMultiTypeName, stateNumTypeName,
						stateSaturatTypeName, stateTextTypeName, "Comment",
						"Last Update", "By User" };
				strFuncResult = objRep.verifyHeadersInHTMLEventSnapshotReport(
						selenium, strHeaderName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.verifDataInHTMLEventSnapshotReport(
						selenium, strData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {

				assertEquals("", strFuncResult);
				strData = new String[] { "Summary", "101", "393", "�", "�",
						"�", "�" };
				strFuncResult = objRep
						.verifSummaryDataInHTMLEventSnapshotReport(selenium,
								strData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String[] strHeader = { "Status Type Summary", "Total" };
				String[][] strDataDouble = { { stateNumTypeName, strUpdate1 },
						{ stateSaturatTypeName, "393" } };
				strFuncResult = objRep
						.verifStatusSummaryHeaderDataInHTMLEventSnapshotReport(
								selenium, strDataDouble, strHeader);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.verifFoooterInHTMLEventSnapshotReport(
						selenium, strTimeIn, strCurentDatReportGen[2] + ":"
								+ strCurentDatReportGen[3]);
				selenium.close();
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
			gstrTCID = "FTS-118730";
			gstrTO = "Update statuses of event related status types added at the resource type level for resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role WITH only view right for these status types CAN view these status types in the Event Snapshot Report.";
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

	// end//testFTS118730//
	
	//start//testFTS118728//
	/***************************************************************
	'Description		:Update statuses of event related status types NST (number), MST (multi), TST (text) and SST (saturation score) added at the resource level for a resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role WITH only view right for these status types CAN view these status types in the Event Snapshot report.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:10/21/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS118728() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();
		Reports objRep = new Reports();
		General objGeneral = new General();
		OfficeCommonFunctions OFC = new OfficeCommonFunctions();

		try {
			gstrTCID = "118728"; // Test Case Id
			gstrTO = " Update statuses of event related status types NST (number), MST (multi), TST (text) and SST (saturation score) added at the resource level for a resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role WITH only view right for these status types CAN view these status types in the Event Snapshot report.";// Test
																																																																																												// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// Admin Login credentials
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Event related ST
			String stateNumTypeName = "eNST" + strTimeText;
			String stateTextTypeName = "eTST" + strTimeText;
			String stateMultiTypeName = "eMST" + strTimeText;
			String stateSaturatTypeName = "eSST" + strTimeText;

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";

			String strEStatusName1 = "eSta" + strTimeText;
			String strEStatusValue[] = new String[1];

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
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

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";

			String strEveName = "AutoEve_1" + strTimeText;
			String strInfo = "This is an automation event";
			String strEventValue = "";
			String strETValue = "";

			String strUpdate1 = "101";
			String strUpdate2 = "Update2";
			String strApplTime = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xlsx";
			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xls";
			String strCSVFileNameRenamed = "EventSnapshot_" + gstrTCID + "_"
					+ strTimeText;
			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strTimeReport = "";
			String strHr = "";
			String strMin = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strState = "Alabama";
			String strCountry = "Barbour County";

			String strLastUpdArr[] = {};
			String strUpdateDataHr = "";
			String strUpdateDataMin = "";

			// ST
			String strNumStatTypeValue = "Number";
			String strStatType1 = "AutoSt1_" + strTimeText;
			String strTxtStatTypDefn = "Auto";
			String[] strST = new String[1];

			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1 and NOT with event related status types NST
			 * (Number), MST (Multi), TST (Text) and SST (Saturation Score) 2.
			 * Resources RS is created under RT. 3. Status Type NST, MST, TST
			 * and SST are added for resource RS at the resource level 4. Event
			 * template ET1 is created selecting resource type RT and status
			 * types NST, MST, TST, SST 5. Event E1 is created under ET1
			 * selecting resource RS 6. User U1 has following rights: a. Report
			 * - Event Snapshot b. Role to View event related status types NST
			 * (number), MST (multi), TST (text) and SST (saturation score) c.
			 * 'View Status' and 'Run Report' rights on resources RS. 7. Status
			 * Type NST, MST, TST and SST are updated on day D1 time hour H1 for
			 * resource RS. Expected Result:No Expected Result
			 */
			// 653114

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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

			// Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event Related ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue, stateNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateNumTypeName);
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
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue, stateTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateTextTypeName);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strSSTValue,
						stateSaturatTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateSaturatTypeName);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strMSTValue, stateMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[3] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, stateMultiTypeName);
				if (strSTvalue[3].compareTo("") != 0) {
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
						seleniumPrecondition, stateMultiTypeName,
						strEStatusName1, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strEStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, stateMultiTypeName,
						strEStatusName1);
				if (strEStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNumStatTypeValue,
						strStatType1, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								strStatType1);
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='" + strST[0]
								+ "']");

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

			// RS
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

			// Status types added at resource level

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < strSTvalue.length; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
							seleniumPrecondition, strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ET
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(
						seleniumPrecondition, strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
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

			// Role
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strViewRightValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				String[] updateRightValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
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

			// User1

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
						.getProperty("CreateNewUsr.AdvOptn.EventSnapShotReprt");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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

			// User2

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);
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

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_2,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = { stateNumTypeName,
						stateTextTypeName, stateMultiTypeName,
						stateSaturatTypeName };
				String[] strRoleStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, true, false);
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
						strUpdate1, strSTvalue[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate2, strSTvalue[1], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strUpdateValue1, strSTvalue[2]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strEStatusValue[0], strSTvalue[3], false, "", "");

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
				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strLastUpdArr = selenium.getText(
						"//span[text()='" + strEStatusName1 + "']"
								+ "/following-sibling::span[@class='time'][1]")
						.split(" ");
				strLastUpdArr = strLastUpdArr[2].split(":");
				strUpdateDataHr = strLastUpdArr[0];
				String str[] = strLastUpdArr[1].split("\\)");
				strLastUpdArr[1] = str[0];
				strUpdateDataMin = strLastUpdArr[1];

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
			 * STEP : Action:Login as user U1, navigate to Reports>>Event
			 * Reports, click on 'Event Snapshot'. Expected Result:'Event
			 * Snapshot Report (Step 1 of 2)' screen is displayed with: 1. Start
			 * Date field with calender widget 2. End Date field with calender
			 * widget 3. Report Format: a. Web Browser (HTML) b. Excel Report
			 * (XLSX) 4. List of Event Templates
			 */
			// 653115

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToEventSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//td/input[@id='searchStartDate']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='ui-datepicker-div']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Start Date field with calender widget is displayed");

				String strElementID = "//td/input[@id='searchEndDate']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				log4j
						.info("Start Date field with calender widget is NOT displayed");
				strFuncResult = "Start Date field with calender widget is NOT displayed";
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='ui-datepicker-div']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("End Date field with calender widget is displayed");

				String strElementID = "css=input[value='" + strETValue
						+ "'][name='eventTypeID']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				log4j
						.info("End Date field with calender widget is NOT displayed");
				strFuncResult = "End Date field with calender widget is NOT displayed";
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//input[@class='checkbox'][@type='radio'][@value='HTML'][@name='reportFormat']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				log4j.info("Web Browser (HTML) is displayed");
			} catch (AssertionError Ae) {
				log4j.info("Web Browser (HTML) is NOT displayed");
				strFuncResult = "Web Browser (HTML) is NOT displayed";
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//input[@class='checkbox'][@type='radio'][@value='CSV'][@name='reportFormat']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				log4j.info("Excel Report (XLSX) is displayed");
			} catch (AssertionError Ae) {
				log4j.info("Excel Report (XLSX) is NOT displayed");
				strFuncResult = "Excel Report (XLSX) is NOT displayed";
				gstrReason = strFuncResult;

			}

			/*
			 * STEP : Action:Provide D1 as the date for 'Start Date' and 'End
			 * Date' fields, select Event Templates ,select Web Browser (HTML)
			 * radio button and click on 'Next' Expected Result:'Event Snapshot
			 * Report (Step 2 of 2)' screen is displayed with : 1.Select an
			 * Event 2. Snapshot Date and Time should fall between (boxes) 3.
			 * Snapshot Date (mm/dd/yyyy):** 2.Snapshot Time - Hour** 3.Snapshot
			 * Time - Minutes **
			 */
			// 653116

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

				strFuncResult = objRep
						.selectHtmlOrExcelformatInEventSnapShotReport(selenium,
								strApplTime, strApplTime, strETValue, true);
				strHr = strCurentDat[2];
				strMin = strCurentDat[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "m",
						"mm");
				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "H",
						"HH");

				String strTime = selenium.getText("//div[@id='mainContainer']"
						+ "/form/table/tbody/tr/td/table/tbody/tr/td[2]");
				strHr = strTime.substring(11, 13);
				System.out.println(strHr);

				strMin = strTime.substring(14, 16);
				System.out.println(strMin);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");
				System.out.println(strTimeReport);

				String str = strLastUpdArr[0] + ":" + strLastUpdArr[1];
				str = dts.addTimetoExisting(str, 1, "HH:mm");
				strLastUpdArr = str.split(":");

				String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterEvntSnapshotGenerateReport(
						selenium, strCSTtime, strEventValue, strLastUpdArr[0],
						strLastUpdArr[1]);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");

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

			int intCount = 0;
			int intCount1 = 0;
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

			try {
				assertEquals("", strFuncResult);

				String strTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"dd MMM yyyy");
				String strFutureDate = dts.DaytoExistingDate(strTime, 1,
						"dd MMM yyyy");
				strApplTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"M/d/yyyy");

				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "HH",
						"H");
				/*
				 * strLastUpdArr[1]=dts.converDateFormat(strLastUpdArr[1], "mm",
				 * "m");
				 */

				String str = "Event Snapshot for " + strEveName + " - "
						+ strApplTime + " " + strLastUpdArr[0] + ":"
						+ strLastUpdArr[1]
						+ " Central Standard Time. Event Start:" + " "
						+ strTime + " " + strHr + ":" + strMin
						+ ". Event End: " + strFutureDate + "" + " " + strHr
						+ ":" + strMin + ". ";

				String[][] strTestData = {
						{ str, "", "", "", "", "", "", "", "", "", "", "", "" },
						{ "Resource Type", "Resource", stateMultiTypeName,
								stateNumTypeName, stateSaturatTypeName,
								stateTextTypeName, "Comment", "Last Update",
								"By User" },
						{
								strResrctTypName,
								strResource,
								strEStatusName1,
								strUpdate1,
								"393",
								"Update2",
								"",
								strTime + " " + strUpdateDataHr + ":"
										+ strUpdateDataMin, strUsrFulName_2 } };

				strFuncResult = OFC.readAndVerifyDataExcel(strTestData,
						strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Status types NST, MST, TST and SST are displayed for RS1 in the generated report. ");

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				log4j
						.info("Status types NST, MST, TST and SST are NOT displayed for RS in the generated report. ");
			}

			/*
			 * STEP : Action:Select 'Event', select day D1, hour H1 and click on
			 * 'Generate Report' Expected Result:Event Snapshot report is
			 * generated in HTML format with following details: Header:
			 * "Event Snapshot Report" < Event name > Event snapshot: MM/DD/YYYY
			 * HH:MM (Provided snapshot time) < Region Time zone > . Event
			 * Start: < Date > < Month > < Year > HH:MM. Event End:< Date > <
			 * Month > < Year > HH:MM Columns: 1. Resource Type Resources
			 * participating in event 2. Status Types 3. Comment 4. Last Update
			 * 5. By User Status types NST, MST, TST and SST are displayed with
			 * appropriate values for RS in the generated report. Total values
			 * for 'NST' and 'SST' are displayed under 'Summary' section. Status
			 * types 'NST', 'SST' along with their totals are displayed under
			 * 'Status Type summary' section under 'Total' column header.
			 * Footer: EMResource Report Created: DD MM YYYY HH:MM < Time Zone >
			 * .
			 */
			// 653117

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToEventSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

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

				strFuncResult = objRep
						.selectHtmlOrExcelformatInEventSnapShotReport(selenium,
								strApplTime, strApplTime, strETValue, false);
				strHr = strCurentDat[2];
				strMin = strCurentDat[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strEndTime = selenium
						.getText("//div[@id='mainContainer']"
								+ "/form/table/tbody/tr/td/table/tbody/tr/td[3]");

				String str[] = strEndTime.split(" ");
				str[1] = dts.addTimetoExisting(str[1], 2, "HH:mm");
				String str2[] = str[1].split(":");

				strFuncResult = objRep
						.enterEvntSnapshotGenerateReportAndVrfyValidationMsg(
								selenium, str[0], strEventValue, str2[0],
								str2[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			String strCurentDatReportGen[] = {};
			String strData[] = {};
			String strTimeIn = "";

			try {
				assertEquals("", strFuncResult);
				strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "m",
						"mm");
				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "H",
						"HH");

				String strTime = selenium.getText("//div[@id='mainContainer']"
						+ "/form/table/tbody/tr/td/table/tbody/tr/td[2]");
				strHr = strTime.substring(11, 13);
				System.out.println(strHr);

				strMin = strTime.substring(14, 16);
				System.out.println(strMin);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");
				System.out.println(strTimeReport);

				String str = strLastUpdArr[0] + ":" + strLastUpdArr[1];
				str = dts.addTimetoExisting(str, 1, "HH:mm");
				strLastUpdArr = str.split(":");

				strCurentDatReportGen = objGeneral.getSnapTime(selenium);
				String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterEvntSnapshotGenerateReport(
						selenium, strCSTtime, strEventValue, strLastUpdArr[0],
						strLastUpdArr[1]);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"dd MMM yyyy");
				String strFutureDate = dts.DaytoExistingDate(strTime, 1,
						"dd MMM yyyy");
				strApplTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"M/d/yyyy");

				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "HH",
						"H");

				strData = new String[] {
						strResource,
						strEStatusName1,
						"101",
						"393",
						"Update2",
						"�",
						strTime + " " + strUpdateDataHr + ":"
								+ strUpdateDataMin, strUsrFulName_2 };

				strTimeIn = strTime;

				String strRepGen = "Event Snapshot: " + strApplTime + " "
						+ strLastUpdArr[0] + ":" + strLastUpdArr[1]
						+ " Central Standard Time";
				strFuncResult = objRep.selectHTMLReport(selenium, strEveName,
						"Event Start:" + " " + strTime + " " + strHr + ":"
								+ strMin + ". Event End: " + strFutureDate + ""
								+ " " + strHr + ":" + strMin, strRepGen);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * STEP : Action:Navigate to Reports>>Event Reports, click on 'Event
			 * Snapshot' , Select day D1as start and end date, Select Excel
			 * Report (XLSX) radio button, Select 'Event', select day D1, hour
			 * H1 and click on 'Generate Report' Expected Result:Event Snapshot
			 * report is generated in XLSX format with following details:
			 * Header: "Event Snapshot for < Name of Event > - MM/DD/YYYY HH:MM
			 * (Provided snapshot time) < Region Time Zone > Event Start: < Date
			 * > < Month > < Year > HH:MM. Event End:< Date > < Month > < Year >
			 * HH:MM Columns: 1. Resource Type 2. Resource 3. Status Types 4.
			 * Comments 5. Last Update 6. By User Status types NST, MST, TST and
			 * SST are displayed with updated values for RS in the generated
			 * report.
			 */
			// 653118

			try {
				assertEquals("", strFuncResult);
				String strHeaderName[] = { strResrctTypName,
						stateMultiTypeName, stateNumTypeName,
						stateSaturatTypeName, stateTextTypeName, "Comment",
						"Last Update", "By User" };
				strFuncResult = objRep.verifyHeadersInHTMLEventSnapshotReport(
						selenium, strHeaderName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.verifDataInHTMLEventSnapshotReport(
						selenium, strData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {

				assertEquals("", strFuncResult);
				strData = new String[] { "Summary", "101", "393", "�", "�",
						"�", "�" };
				strFuncResult = objRep
						.verifSummaryDataInHTMLEventSnapshotReport(selenium,
								strData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String[] strHeader = { "Status Type Summary", "Total" };
				String[][] strDataDouble = { { stateNumTypeName, strUpdate1 },
						{ stateSaturatTypeName, "393" } };
				strFuncResult = objRep
						.verifStatusSummaryHeaderDataInHTMLEventSnapshotReport(
								selenium, strDataDouble, strHeader);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.verifFoooterInHTMLEventSnapshotReport(
						selenium, strTimeIn, strCurentDatReportGen[2] + ":"
								+ strCurentDatReportGen[3]);
				selenium.close();
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
			gstrTCID = "FTS-118728";
			gstrTO = "Update statuses of event related status types NST (number), MST (multi), TST (text) and SST (saturation score) added at the resource level for a resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role WITH only view right for these status types CAN view these status types in the Event Snapshot report.";
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

	// end//testFTS118728//
}
