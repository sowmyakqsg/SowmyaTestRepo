package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.junit.*;
import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.EventBanner;
import com.qsgsoft.EMResource.shared.EventList;
import com.qsgsoft.EMResource.shared.EventSetup;
import com.qsgsoft.EMResource.shared.General;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.Preferences;
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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/*******************************************************************
' Description	    :This class contains test cases from requirement
' Requirement Group	:Preferences>> Status Change Preferences 
' Requirement 	    :Delete status change preferences
� Product		    :EMResource v3.23
' Date			    :2/May/2013
' Author		    :QSG
-------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class EdgeCaseRefineStatusType {
	
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.features."
			+ "EdgeCase_RefineStatusType");
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
	String gstrTimeOut = "";
	Selenium selenium, seleniumPrecondition;

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

	@After
	public void tearDown() throws Exception {

		// kill browser
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

	/****************************************************************************************
	'Description	:Verify that for a resource NEDOCS status type can be refined for a user.
	'Arguments		:None
	'Returns		:None
	'Date	 		:30-May-2013
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                                                              Modified By
	'<Date>                                                                     <Name>
	****************************************************************************************/

	@Test
	public void testEdgecase119717() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		General objGeneral = new General();
		EventBanner objEventBanner = new EventBanner();
		EventList objEventList = new EventList();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		Preferences objPreferences = new Preferences();
		Reports objRep = new Reports();

		try {
			gstrTCID = "119717";
			gstrTO = "Verify that for a resource NEDOCS status type can be refined for a user.";
			gstrResult = "FAIL";
			gstrReason = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strTmText = dts.getCurrentDate("HHmm");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String statrNedHocTypeName = "NDST_R" + strTimeText;
			String statrNedHocTypeNamePrivate = "NDST_P" + strTimeText;
			String statrNedHocTypeNameShared = "NDST_S" + strTimeText;
			String strStatTypDefn = "Automation";
			String strSTvalue[] = new String[3];
			String strNDSTValue = "NEDOCS Calculation";

			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";
			String strResource1 = "AutoRs_1" + strTimeText;
			String strResource2 = "AutoRs_2" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[2];
			String strCategory = rdExcel.readInfoExcel("Find Res", 3, 3,
					strFILE_PATH);
			String strRegion = rdExcel.readInfoExcel("Find Res", 3, 4,
					strFILE_PATH);
			String strCityZipCd = rdExcel.readInfoExcel("Find Res", 3, 5,
					strFILE_PATH);

			// Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			// View Details
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			// ET
			String strTempName = "ET1_" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			String strETValue = "";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			String strEventValue = "";
			String strEventStartDate = "";
			String strEventEndDate = "";
			String Drill = "";
			// Update
			String strAbove = "100";
			String strBelow = "50";
			String strNedocUpdValue[] = { "0", "1", "2", "3", "4", "5", "6",
					"7" };
			String strUpdatedValue = "135";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strCurYear = dts.getCurrentDate("yyyy");
			String strLastUpdST1 = "";
			String strLastUpdST2 = "";
			String strGenDate = "";
			String strRegGenTime = "";
			String strApplTime = "";

			// Report data
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapshotRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapshotRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".xls";

			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventDetail_"
					+ gstrTCID + "_" + strTimeText + ".pdf";

			String strCSVFileNameRenamed = "StatSnapshotRep1_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");
			
	 log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");

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
		
		/*
		 * 1.To be tested in:a.Region View
							 b. Map View
							 c. Custom View
							 d. Reports
							 e. Status Change Preferences 
		    No Expected Result 
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NDST Normal
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue,
						statrNedHocTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrNedHocTypeName);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// NDST Private
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNDSTValue, statrNedHocTypeNamePrivate,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNedHocTypeNamePrivate);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// NDST Shared
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue, statrNedHocTypeNameShared,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statrNedHocTypeNameShared, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNedHocTypeNameShared);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			
		/*
		 * 3. Resource type 'RT' is created and is associated with the all NEDHOC status types.
		 */

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
				strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
						 strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
						 strSTvalue[2], true);
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

			/* 4. Resource 'RS1' and 'RS2' is created under 'RT' */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
            //RS1
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
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//RS2
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
				strRSValue[1] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 5. Role R1 is created selecting status type all statustypes under view and update.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_1);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 5. User 'A' has update right on 'RS1' and 'RS2' is assigned a role 'R'
		 * which has update right on all NEDOC status types.
		 */
			
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
						seleniumPrecondition, strUserName_A, strInitPwd,
						strConfirmPwd, strUsrFulName_A);
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
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EventDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
						seleniumPrecondition, strResource2, strRSValue[1],
						false, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSTvalueBef[] = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2] };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalueBef, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//ET
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRTValue};
				String[] strStatusTypeval = { strSTvalue[0],strSTvalue[1],strSTvalue[2]};
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//EVE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}				
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName, strEveName, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResource1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResource2, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(seleniumPrecondition,
						strEveName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strEventStartDate = seleniumPrecondition
						.getText("//div[@id='mainContainer']"
								+ "/table/tbody/tr/td[6][text()='" + strEveName
								+ "']/preceding-sibling::td[2]");

				strEventStartDate = dts.converDateFormat(strEventStartDate,
						"yyyy-MM-dd HH:mm", "MM/dd/yyyy HH:mm");

				strEventEndDate = seleniumPrecondition
						.getText("//div[@id='mainContainer']/table/tbody/tr/td[6]"
								+ "[text()='"
								+ strEveName
								+ "']/preceding-sibling::td[1]");

				strEventEndDate = dts.converDateFormat(strEventEndDate,
						"yyyy-MM-dd HH:mm", "MM/dd/yyyy HH:mm");

				Drill = seleniumPrecondition
						.getText("//div[@id='mainContainer']/table/tbody/tr/td[6]"
								+ "[text()='"
								+ strEveName
								+ "']/following-sibling::td[1]");

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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//New user login
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName_A, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Add Custom View for RS1
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences
						.navEditCustomViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						seleniumPrecondition, strResource1, strCategory,
						strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statrNedHocTypeName,
						statrNedHocTypeNamePrivate, statrNedHocTypeNameShared };
				strFuncResult = objPreferences.editCustomViewOptionsNew(
						seleniumPrecondition, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Add Custom View for RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						seleniumPrecondition, strResource2, strCategory,
						strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statrNedHocTypeName,
						statrNedHocTypeNamePrivate, statrNedHocTypeNameShared };
				strFuncResult = objPreferences.editCustomViewOptionsNew(
						seleniumPrecondition, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Set status change preferences for RS1

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
            //R_NDST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(
								seleniumPrecondition, strResource1,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageNew(seleniumPrecondition,
								strResource1, strRSValue[0], strSTvalue[0],
								statrNedHocTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(seleniumPrecondition,
								strResource1, strRSValue[0], strSTvalue[0],
								statrNedHocTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(seleniumPrecondition,
								strAbove, strBelow, strRSValue[0],
								strSTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//P_NDST
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageNew(seleniumPrecondition,
								strResource1, strRSValue[0], strSTvalue[1],
								statrNedHocTypeNamePrivate, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(seleniumPrecondition,
								strResource1, strRSValue[0], strSTvalue[1],
								statrNedHocTypeNamePrivate, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(seleniumPrecondition,
								strAbove, strBelow, strRSValue[0],
								strSTvalue[1], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//S_NDST
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageNew(seleniumPrecondition,
								strResource1, strRSValue[0], strSTvalue[2],
								statrNedHocTypeNameShared, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(seleniumPrecondition,
								strResource1, strRSValue[0], strSTvalue[2],
								statrNedHocTypeNameShared, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(seleniumPrecondition,
								strAbove, strBelow, strRSValue[0],
								strSTvalue[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Set status change preferences for RS2
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
            //R_NDST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(
								seleniumPrecondition, strResource2,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageNew(seleniumPrecondition,
								strResource2, strRSValue[1], strSTvalue[0],
								statrNedHocTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(seleniumPrecondition,
								strResource2, strRSValue[1], strSTvalue[0],
								statrNedHocTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(seleniumPrecondition,
								strAbove, strBelow, strRSValue[1],
								strSTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//P_NDST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageNew(seleniumPrecondition,
								strResource2, strRSValue[1], strSTvalue[1],
								statrNedHocTypeNamePrivate, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(seleniumPrecondition,
								strResource2, strRSValue[1], strSTvalue[1],
								statrNedHocTypeNamePrivate, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(seleniumPrecondition,
								strAbove, strBelow, strRSValue[1],
								strSTvalue[1], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//S_NDST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageNew(seleniumPrecondition,
								strResource2, strRSValue[1], strSTvalue[2],
								statrNedHocTypeNameShared, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(seleniumPrecondition,
								strResource2, strRSValue[1], strSTvalue[2],
								statrNedHocTypeNameShared, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(seleniumPrecondition,
								strAbove, strBelow, strRSValue[1],
								strSTvalue[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Update all staus types
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViewMap
						.navToRegionalMapView(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(
						seleniumPrecondition, strNedocUpdValue, strSTvalue[0],
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(
						seleniumPrecondition, strNedocUpdValue, strSTvalue[1],
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(
						seleniumPrecondition, strNedocUpdValue, strSTvalue[2],
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdTimeValue(seleniumPrecondition, "1");
				strLastUpdST1 = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":" + strArFunRes[3];
				strFuncResult = strArFunRes[4];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			// RS2
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViewMap
						.navToRegionalMapView(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(
						seleniumPrecondition, strNedocUpdValue, strSTvalue[0],
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(
						seleniumPrecondition, strNedocUpdValue, strSTvalue[1],
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(
						seleniumPrecondition, strNedocUpdValue, strSTvalue[2],
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdTimeValue(seleniumPrecondition, "1");
				strLastUpdST2 = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":" + strArFunRes[3];
				strFuncResult = strArFunRes[4];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
       log4j.info("---Precondtion for test case " + gstrTCID+ " ends----------");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		log4j.info("---- test case execution" + gstrTCID+ " starts----------");
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_A, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
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
				strFuncResult = objLogin.login(selenium, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Region View 
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTAssoOrNotInViewScreenNew(selenium,
						statrNedHocTypeName, false, strResource1, 2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//2 Check Status types not displayed in  Custom view map
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTAssoOrNotInCustomViewScreen(
						selenium, statrNedHocTypeName, false, strResource1, 2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Map View 	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statrNedHocTypeNamePrivate,
						statrNedHocTypeNameShared };
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statrNedHocTypeName };
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statrNedHocTypeName,
						statrNedHocTypeNamePrivate, statrNedHocTypeNameShared };
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource2, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Custom View Map		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statrNedHocTypeName };
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource1, strEventStatType,
								strRoleStatType, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statrNedHocTypeNamePrivate,
						statrNedHocTypeNameShared };
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource1, strEventStatType,
								strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statrNedHocTypeName,
						statrNedHocTypeNamePrivate, statrNedHocTypeNameShared };
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource2, strEventStatType,
								strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// event detail screen

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventBanner.clickEventWithFocusOnEveBanner(
						selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrNedHocTypeName };
				strFuncResult = objEventList.chkSTInEventBanners(selenium,
						strStatTypeArr, false, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status Change Preferences
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.checkStatTypeInMyStatusChangePrefsNew(selenium,
						statrNedHocTypeNamePrivate,strResource1, "1");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.checkStatTypeInMyStatusChangePrefsNew(selenium,
						statrNedHocTypeName,strResource1, "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals(gstrReason
								+ "Status Type "
								+ statrNedHocTypeName
								+ " is NOT displayed in My Status Change Preferences for "
								+ strResource1, strFuncResult);
				strFuncResult = objPreferences.checkStatTypeInMyStatusChangePrefsNew(selenium,
						statrNedHocTypeNameShared,strResource1, "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.checkStatTypeInMyStatusChangePrefsNew(selenium,
						statrNedHocTypeNamePrivate,strResource2, "5");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefsNew(selenium,
								statrNedHocTypeName, strResource2, "7");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefsNew(selenium,
								statrNedHocTypeNameShared, strResource2, "9");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
							
			// Status snapshot Report
			
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Wait for 2 minutes
				Thread.sleep(130000);
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strArFunRes = new String[5];
				strArFunRes = objGeneral.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
						"MM/dd/yyyy");
				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strCSTtime, strGenTimeHrs, strGenTimeMin);
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

				String[][] strReportData = {
						{
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strRegGenTime
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResrctTypName, strResource1,
								statrNedHocTypeNamePrivate, strUpdatedValue,
								"", strLastUpdST1 },
						{ strResrctTypName, strResource1,
								statrNedHocTypeNameShared, strUpdatedValue, "",
								strLastUpdST1 },

						{ strResrctTypName, strResource2,
								statrNedHocTypeNamePrivate, strUpdatedValue,
								"", strLastUpdST2 },
						{ strResrctTypName, strResource2, statrNedHocTypeName,
								strUpdatedValue, "", strLastUpdST2 },
						{ strResrctTypName, strResource2,
								statrNedHocTypeNameShared, strUpdatedValue, "",
								strLastUpdST2 },
				};
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			// Event detail Report
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;			
				strFuncResult=objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;		
				strFuncResult=objRep.navToEventDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				log4j.info("List of Event Templates is displayed");
				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);
	
				strFuncResult = objRep.enterEvntReportStartDateNEndDate(
						selenium, strApplTime, strApplTime, strETValue);
				
			} catch (AssertionError Ae) {
				log4j.info("List of Event Templates is NOT displayed");
				strFuncResult="List of Event Templates is NOT displayed";
				gstrReason = strFuncResult;

			}
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterEvntDetalRepGenerateNew(selenium,
						strEventValue, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
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
				File f = new File(strPDFDownlPath);
				if (f.isFile()) {
					String[] strTestData = {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strUserName_A + "/" + strInitPwd, strTempName,
							strEveName, strPDFDownlPath,
							"verify data in PDF file", strEventStartDate,
							strEventEndDate, propEnvDetails.getProperty("ExternalIP"),Drill };
					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestData, strWriteFilePath,
							"Eventdetailreport");
					System.out
							.println("Generated PDF  is saved in the specified folder ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			// Status detail Report
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportSTSumDetailDate(selenium,
						strApplTime, strApplTime);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterReportSTSumDetailSTAndNavigate(
						selenium, statrNedHocTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[value='" + strRSValue[0]
						+ "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("Element NOT Present")){
					log4j.info("Resource " + strResource1
							+ " is NOT listed for 'Resources' field in the"
							+ " 'Step Detail Report (Step 2 of 2) screen for "
							+ statrNedHocTypeName);
					strFuncResult = "";
				} else {
					gstrReason = strFuncResult + "Resource " + strResource1
							+ " is listed " + "for 'Resources' field in the"
							+ " 'Step Detail Report (Step 2 of 2) screen for "
							+ statrNedHocTypeName;
					log4j.info("Resource " + strResource1
							+ " is listed for 'Resources' field in the"
							+ " 'Step Detail Report (Step 2 of 2) screen for "
							+ statrNedHocTypeName);
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[value='" + strRSValue[1]
						+ "']";

				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Resource " + strResource2
							+ " is listed for 'Resources' field in the"
							+ " 'Step Detail Report (Step 2 of 2) screen for "
							+ statrNedHocTypeName);
					strFuncResult = "";
				} else {
					gstrReason = strFuncResult + "Resource " + strResource2
							+ " is NOT listed " + "for 'Resources' field in the"
							+ " 'Step Detail Report (Step 2 of 2) screen for "
							+ statrNedHocTypeName;
					log4j.info("Resource " + strResource2
							+ " is NOT listed for 'Resources' field in the"
							+ " 'Step Detail Report (Step 2 of 2) screen for "
							+ statrNedHocTypeName);
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
			gstrTCID = "119717";
			gstrTO = "Verify that for a resource NEDOCS status type can be refined for a user.";
			gstrResult = "FAIL";
			gstrReason = "";

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
