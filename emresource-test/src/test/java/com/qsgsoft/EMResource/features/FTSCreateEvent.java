package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Properties;
import org.junit.*;

import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**********************************************************************
' Description :This class includes FTSCreateEvent requirement 
'				testcases
' Date		  :16-Jan-2013
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class FTSCreateEvent {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSCreateEvent");
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
	
	/********************************************************************************
	'Description	:Verify that event can be created providing current time.
	'Arguments		:None
	'Returns		:None
	'Date	 		:12-Feb-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS92695() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		EventList objEventList = new EventList();

		try {
			gstrTCID = "92695";
			gstrTO = "Verify that event can be created providing current time.";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "auto event";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strAction = "View";
			String strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
					strFILE_PATH);
			String strStatus = "Ongoing";
			String strStartDate = "";
			String strEndDate = "";
			String strDrill = "No";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			String strFndEdMnth = "";
			String strFndEdYear = "";
			String strFndEdDay = "";
			String strFndEdHr = "";
			String strFndEdMinu = "";

			String[] strHeader = { "Action", "Icon", "Status", "Start", "End",
					"Title", "Drill", "Template", "Information" };

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDeSelEventOnly(seleniumPrecondition,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(seleniumPrecondition,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
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

			/*
			 * 2. Resource type RT is created selecting status types NST,MST,SST
			 * and TST.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
							strSTvalue[intST], true);
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

			/*
			 * 2. Resource RS is created under resource type RT.
			 */

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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

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
			 * 3. Event template ET is created selecting RT and all 4 status
			 * types.
			 */

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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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

			/*
			 * 4. User 'U1' is created with maintain events right and view
			 * resource right on Resource RS.
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
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
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
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user U1, navigate to Event >> Event Management. 'Event
			 * Management' screen is displayed.
			 */

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

			/*
			 * 3 Create a event EVE selecting event template ET providing
			 * mandatory data, select 'Immediately' for 'Event start', default
			 * value for 'Event End' and click on 'Save'. Event is listed in the
			 * 'Event Management' screen.
			 * 
			 * 'Title' and 'Description' are displayed appropriately as provided
			 * while event is created under 'Title' and 'Description' columns.
			 * 
			 * Event start Date and time are displayed appropriately under the
			 * column 'Start'.
			 * 
			 * Event end Date and time are displayed appropriately under the
			 * column 'End'.
			 * 
			 * 'Ongoing' is displayed under the 'Status' column.
			 * 
			 * 'Edit' and 'End' links are displayed under the 'Action' column
			 * for the event created.
			 * 
			 * Event banner is displayed.
			 */

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, false);

				// get Start Date
				strFndStMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMnt"));
				strFndStDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartDay"));
				strFndStYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartYear"));

				// get Start Time
				strFndStHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartHour"));
				strFndStMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMinut"));

				// get End Date
				strFndEdMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMnt"));
				strFndEdDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndDay"));
				strFndEdYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndYear"));

				// get End Time
				strFndEdHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndHour"));
				strFndEdMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMinut"));

				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
						+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate = strEndDate + " " + strFndEdHr + ":" + strFndEdMinu;

				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strEndDate, strEveName, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Navigate to Event >> Event List Event is listed in the 'Event
			 * List' screen.
			 * 
			 * 'Title' and 'Description' are displayed appropriately as provided
			 * while event is created under 'Title' and 'Description' columns.
			 * 
			 * Event start Date and time are displayed appropriately under the
			 * column 'Start'.
			 * 
			 * Event end Date and time are displayed appropriately under the
			 * column 'End'.
			 * 
			 * 'Ongoing' is displayed under the 'Status' column.
			 * 
			 * 'View ' link is displayed under the 'Action' column for the event
			 * created.
			 */

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objEventList.navToEventListNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strEndDate, strEveName, strDrill,
						strTempName, strInfo };
				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}

			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "92695";
			gstrTO = "Verify that event can be created providing current time.";
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
	
	/********************************************************************************
	'Description	:Verify that event can be created selecting multiple resources present under multiple resource types.
	'Arguments		:None
	'Returns		:None
	'Date	 		:13-Feb-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS92675() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		EventList objEventList = new EventList();
		Roles objRoles=new Roles();

		try {
			gstrTCID = "92675";
			gstrTO = "Verify that event can be created selecting multiple " +
					"resources present under multiple resource types.";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[2];
			String strRTValue[] = new String[2];
			String strStatusValue[] = new String[1];

			String strResrctTypName1 = "AutoRt1" + strTimeText;
			String strResrctTypName2 = "AutoRt2" + strTimeText;

			String strResVal = "";
			String strResource1 = "AutoRs1" + strTimeText;
			String strResource2 = "AutoRs2" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "auto event";
			String strEventValue="";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strRolesName="Role"+strTimeText;
			String strRoleValue="";
			
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, strMultiStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT is created selecting status types NST,MST,SST
			 * and TST.
			 */
			//RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[1], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
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

			//RT2
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName2,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[2] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[3], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
						strResrctTypName2);
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
			 * 2. Resource RS1 and RS2 are created under resource type RT1 and
			 * RT2 respectively.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//RS1
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource1, strAbbrv, strResrctTypName1, strContFName,
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
			
			//RS2
			
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource2, strAbbrv, strResrctTypName2, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource2);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			/*
			 * 4. Event Template 'ET' is created providing mandatory data and
			 * selecting Resource Type 'RT1', 'RT2' status Types NST,MST,SST and
			 * TST.
			 */
			

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
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strRTValue, strSTvalue);
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
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			// Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" } };
				String strSTvaluesUpdate[][] = { { strSTvalue[0], "false" },
						{ strSTvalue[1], "false" }, { strSTvalue[2], "false" },
						{ strSTvalue[3], "false" } };

				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						selenium, false, false, strSTvalues, strSTvaluesUpdate,
						false);
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

				strRoleValue = objRoles.fetchRoleValueInRoleList(
						selenium, strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			/*
			 * 3. User 'U1' is created with following rights:
			 * 
			 * a. Maintain events Template right. b. Maintain Events right. c.
			 * View resource right on Resource RS1 and RS2. d. Role to view the
			 * status types associated.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource1, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource2, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user U1, navigate to Event >> Event Management. 'Event
			 * Management' screen is displayed.
			 */

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

			/*
			 * 3 Click on 'Create New Event' button. 'Select Event Template'
			 * screen is displayed, event template 'ET' is listed under it.
			 */

			
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventWitManyRS(selenium,
						strTempName, strRSValue, strEveName, strInfo, true);
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
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 5 Click on the event banner of 'EVE'. Resources 'RS1' is
			 * displayed on the 'Event Status' screen under RT1 along with NST,
			 * MST status types.
			 * 
			 * Resources 'RS2' is displayed on the 'Event Status' screen under
			 * RT2 along with SST, TST status types.
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				String[] strStatTypeArr={strMultiStatType,strNumStatType};
				strFuncResult = objEventList.checkInEventBannerNew(selenium,
						strEveName, strResrctTypName1, strResource1,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strStatTypeArr={strSatuStatType,strTxtStatType};
				strFuncResult = objEventList.checkInEventBannerNew(selenium,
						strEveName, strResrctTypName2, strResource2,
						strStatTypeArr);
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
			gstrTCID = "92675";
			gstrTO = "Verify that event can be created selecting multiple resources" +
					" present under multiple resource types.";
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
	
	/********************************************************************************
	'Description	:Verify that user can cancel the process of creating an event.
	'Arguments		:None
	'Returns		:None
	'Date	 		:13-Feb-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS92367() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		General objGeneral=new General();

		try {
			gstrTCID = "92367";
			gstrTO = "Verify that user can cancel the process of creating an event.";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "auto event";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDeSelEventOnly(selenium,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT is created selecting status types NST,MST,SST
			 * and TST.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
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

			/*
			 * 2. Resource RS is created under resource type RT.
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
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
			 * 3. Event template ET is created selecting RT and all 4 status
			 * types.
			 */

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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. User 'U1' is created with maintain events right and view
			 * resource right on Resource RS.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user U1, navigate to Event >> Event Management. 'Event
			 * Management' screen is displayed.
			 */

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

			/*
			 * 3 Create a event EVE selecting event template ET providing
			 * mandatory data, select 'Immediately' for 'Event start', default
			 * value for 'Event End' and click on 'Save'. Event is listed in the
			 * 'Event Management' screen.
			 * 
			 * 'Title' and 'Description' are displayed appropriately as provided
			 * while event is created under 'Title' and 'Description' columns.
			 * 
			 * Event start Date and time are displayed appropriately under the
			 * column 'Start'.
			 * 
			 * Event end Date and time are displayed appropriately under the
			 * column 'End'.
			 * 
			 * 'Ongoing' is displayed under the 'Status' column.
			 * 
			 * 'Edit' and 'End' links are displayed under the 'Action' column
			 * for the event created.
			 * 
			 * Event banner is displayed.
			 */

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup
						.cancelAndNavToEventManagementScreen(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {

				assertEquals("", strFuncResult);

				String strElementID="//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
												+ strEveName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("Element NOT Present", strFuncResult);

				String strElementID="//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals("Element NOT Present"));
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "92367";
			gstrTO = "Verify that user can cancel the process of creating an event.";
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
	
	/********************************************************************************
	'Description	:Verify that event details displayed are appropriate when clicked on the event displayed on the event banner.
	'Arguments		:None
	'Returns		:None
	'Date	 		:14-Feb-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS92669() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		ViewMap objViewMap=new ViewMap();

		try {
			gstrTCID = "92669";
			gstrTO = "Verify that event details displayed are appropriate " +
					"when clicked on the event displayed on the event banner.";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "St_1" + strTimeText;
			String strStatType2 = "St_2" + strTimeText;
	
			String strStatTypDefn = "Auto";
			String strNSTValue = "Number";
			
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			
			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "auto event";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			
			String strStartDate = "";
	
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			String strEventValue="";
			
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Status types ST1 and ST2 are created.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strStatType1, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT is created selecting Status types ST1 and
			 * ST2.
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
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
						strSTvalue[1], true);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
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

			/* 3. Resource RS is created selecting RT. */

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
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
			 * 4. Event template 'ET' is created selecting status types ST1 ,ST2
			 * and RT.
			 */
			
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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. User 'U1' is created selecting 'Maintain Events' right and
			 * View resource right on RS.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 2 Login as a user 'U1' navigate to 'Event >> Event management'
			 * 'Event Management' screen is displayed with list of events.
			 */

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

			/*
			 * 3 Click on 'Create New Event' button 'Select Event Template'
			 * screen is displayed, event template 'ET' is listed under it.
			 */

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, false);

				// get Start Date
				strFndStMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMnt"));
				strFndStDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartDay"));
				strFndStYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartYear"));

				// get Start Time
				strFndStHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartHour"));
				strFndStMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMinut"));

				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
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
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 5 Click on event banner of event 'EVE' Event eve is displayed
			 * along with the Resource RS under RT and NST , MST, SST and TST
			 * status types.
			 * 
			 * Created By: < user full name > @ < Date and time > < information
			 * provided for event > is displayed.
			 * 
			 * Date and time is displayed in the format MM/DD/YY HH:MM
			 */
			try {

				assertEquals("", strFuncResult);
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				/*strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				*/
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String strNewStartDate = dts.converDateFormat(strStartDate,
					"yyyy-MM-dd", "MM/dd/yy");
			log4j.info(strNewStartDate);

			log4j.info(strNewStartDate);
			String strNewStartDate1 = dts.addTimetoExisting(strFndStHr + ":"
					+ strFndStMinu, 1, "HH:mm");
			strNewStartDate1 = strNewStartDate + " " + strNewStartDate1;
			log4j.info(strNewStartDate1);

			strNewStartDate = strNewStartDate + " " + strFndStHr + ":"
					+ strFndStMinu;
			
			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objViewMap.navEventStatusPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));
				
	
				strFuncResult = objEventSetup.checkUserInfoInEventBannerCorrect(selenium,
						"Created By: " + strUsrFulName_1 + " @ " + strNewStartDate+" "+propEnvDetails.getProperty("TimeZone")
								+ "\n" + strInfo, false, strEventValue);
				
				if(strFuncResult.compareTo("")!=0){
					strFuncResult = objEventSetup.checkUserInfoInEventBannerCorrect(selenium,
							"Created By: " + strUsrFulName_1 + " @ " + strNewStartDate1+" "+propEnvDetails.getProperty("TimeZone")
									+ "\n" + strInfo, false, strEventValue);
				}
				assertTrue(strFuncResult.equals(""));
			} catch (AssertionError Ae) {
				strFuncResult = objEventSetup.checkUserInfoInEventBannerCorrect(selenium,
						"Created By: " + strUsrFulName_1 + " @ " + strNewStartDate
								+ "\n" + strInfo, false, strEventValue);

			}
			

			try {
				assertTrue(strFuncResult.equals(""));
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "92669";
			gstrTO = "Verify that event details displayed are appropriate when" +
					" clicked on the event displayed on the event banner.";
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
	
	/********************************************************************************
	'Description	:Verify that 'Event Status' screen is displayed when clicked on the
	'				 event icon associated with a resource from the view screen.
	'Arguments		:None
	'Returns		:None
	'Date	 		:14-Feb-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS92678() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		Roles objRoles=new Roles();
		Views objViews=new Views();
		EventList objEventList =new EventList();
		General objGeneral=new General();
		try {
			gstrTCID = "92678";
			gstrTO = "Verify that 'Event Status' screen is displayed when " +
					"clicked on the event icon associated with a resource from the view screen.";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "auto event";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			
			String strRoleValue="";
			String strRolesName="Role" + strTimeText;
			
			//Data for creating View
			String strViewName_1="autoView_1" + strTimeText;
			String strVewDescription="";
			String strViewType="";

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Status Types NST,MST,SST,TST are created
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, strMultiStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 2. Resource type RT is created selecting status types NST,MST,SST
			 * and TST.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
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

			/*
			 * 3. Resource RS is created under resource type RT.
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
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
			 * 4. Event template ET is created selecting RT and all 4 status
			 * types.
			 */

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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
					strFuncResult = "Function failes to fetch ETY Value";
				}
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

				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" } };
				String strSTvaluesUpdate[][] = { { strSTvalue[0], "false" },
						{ strSTvalue[1], "false" }, { strSTvalue[2], "false" },
						{ strSTvalue[3], "false" } };

				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						selenium, false, false, strSTvalues, strSTvaluesUpdate,
						false);
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

				strRoleValue = objRoles.fetchRoleValueInRoleList(
						selenium, strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			/*
			 * 5. User 'U1' is created with maintain events right, role to view
			 * to associated status types and view resource right on Resource
			 * RS.
			 */
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 6. View 'V1' is created selecting all 4 status types and resource
			 * RS.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				
				strVewDescription = "Automation";
				strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
				
				
				strFuncResult = objViews.createView(selenium, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * 2 Login as user U1, navigate to Event >> Event Management. 'Event
			 * Management' screen is displayed.
			 */
		
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 3 Click on 'Create New Event' button 'Select Event Template'
			 * screen is displayed with event templates in the region.
			 * 
			 * 'Create' link is present adjacent to all event templates.
			 * 
			 * Event template ET is listed under it.
			 */
			

			/*
			 * 4 Click on 'Create' link associated with ET,Create event EVE
			 * providing data in the mandatory fields. Event EVE is created and
			 * listed under event management screen.
			 * 
			 * Event Banner for EVE is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createNewEvent(selenium,
						strTempName, strResource, strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);

				String strElementID="//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
												+ strEveName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			/*
			 * 5 Navigate to view >> V1 Resource RS is displayed under RT along
			 * with NST , MST, SST, TST status types.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName_1, strResrctTypName, strRS,
						strRTValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strArStatType1={strMultiStatType,strNumStatType,strSatuStatType,strTxtStatType};
				strFuncResult = objViews.checkStatusTypeInUserView(selenium, strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strArStatType1={strMultiStatType,strNumStatType,strSatuStatType,strTxtStatType};
				strFuncResult = objEventList.checkInEventBannerNew(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType1);
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
			gstrTCID = "92678";
			gstrTO = "Verify that 'Event Status' screen is displayed when " +
					"clicked on the event icon associated with a resource from the view screen.";
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
	
	
	
	/********************************************************************************
	'Description	:Verify that event can be created by selecting the resources with
	'				 address and without providing address for the event.
	'Arguments		:None
	'Returns		:None
	'Date	 		:20-Feb-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS92677() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		Roles objRoles=new Roles();
		ViewMap objViewMap=new ViewMap();
		General objGeneral=new General();
		try {
			gstrTCID = "92677";
			gstrTO = "Verify that event can be created by selecting the resources " +
					"with address and without providing address for the event.";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "auto event";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			
			String strRoleValue="";
			String strRolesName="Role" + strTimeText;
			
		
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Status types 'MST','NST','SST','TST' are created.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, strMultiStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2. Resource type RT is created selecting all the four status
			 * types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
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

			/*
			 * 3. Resource RS is created under resource type RT.
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
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
			 * 4. Event template ET is created selecting RT and all 4 status
			 * types.
			 */

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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
					strFuncResult = "Function failes to fetch ETY Value";
				}
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

				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" } };
				String strSTvaluesUpdate[][] = { { strSTvalue[0], "false" },
						{ strSTvalue[1], "false" }, { strSTvalue[2], "false" },
						{ strSTvalue[3], "false" } };

				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						selenium, false, false, strSTvalues, strSTvaluesUpdate,
						false);
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

				strRoleValue = objRoles.fetchRoleValueInRoleList(
						selenium, strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			/*4. User 'U1' is created providing 'Maintain Events' right. 
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			
			/*
			 * 2 Login as user U1, navigate to Event >> Event Management. 'Event
			 * Management' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 3 Create a event EVE selecting event template ET and providing
			 * mandatory data. Event Management' screen is displayed.
			 * 
			 * Event 'EVE' is listed under it.
			 * 
			 * Event banner for event 'EVE' is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createNewEventWithPageVerify(selenium,
						strTempName, strResource, strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {

				assertEquals("", strFuncResult);

				String strElementID="//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
				
				if(strFuncResult.equals("")){
					log4j.info("Event banner for event 'EVE' is displayed.");
					
				}else{
					log4j.info("Event banner for event 'EVE' is NOT displayed.");
					strFuncResult="Event banner for event 'EVE' is NOT displayed.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			/*
			 * 4 Navigate to view >> Map,Select drop down list of Resource Types
			 * Event 'EVE' is listed under it.
			 */
			
			
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navEventPopupWindow(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 6 Select resource RS under Find resource drop down. Resource RS
			 * is displayed on map with associated status types.
			 */
			
			try {

				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { strNumStatType, strTxtStatType,
						strMultiStatType, strSatuStatType };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserName_1 + "/" + strInitPwd,
						strTempName,
						strEveName,
						strNumStatType + "," + strTxtStatType + ","
								+ strMultiStatType + "," + strSatuStatType,
						strResource, strResrctTypName, "Verify only 5th step",
						strRegn };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC
						.writeResultData(strTestData, strWriteFilePath,
								"View_ST");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "92677";
			gstrTO = "Verify that event can be created by selecting the resources " +
					"with address and without providing address for the event.";
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
	
	
	/********************************************************************************
	'Description	:Verify that a future event can be created.
	'Arguments		:None
	'Returns		:None
	'Date	 		:12-Feb-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS118088() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		EventList objEventList = new EventList();
		EventNotification objEventNotification=new EventNotification();
		General objGeneral=new General();

		try {
			gstrTCID = "118088";
			gstrTO = "Verify that a future event can be created.";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "auto event";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strAction = "View";
			String strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
					strFILE_PATH);
			String strStatus = "Future";
			String strStartDate = "";
			String strEndDate = "";
			String strDrill = "No";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			String strFndEdMnth = "";
			String strFndEdYear = "";
			String strFndEdDay = "";
			String strFndEdHr = "";
			String strFndEdMinu = "";

			String[] strHeader = { "Action", "Icon", "Status", "Start", "End",
					"Title", "Drill", "Template", "Information" };

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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDeSelEventOnly(selenium,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT is created selecting status types
			 * 'NST','MST','SST' and 'TST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
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

			/*
			 * 3. Resource RS is created under resource type RT
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
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
			 * 4. User 'U1' is created by providing mandatory data primary
			 * e-mail, e-mail and pager addresses and assigning following
			 * rights:
			 * 
			 * a. 'Maintain Events' right
			 * 
			 * b. 'Maintain Event Templates' right
			 */
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 5. Event template ET is created by selecting status Types
			 * 'NST','MST','SST' and 'TST' and Resource Type 'RT' and selecting
			 * the check boxes E-mail, Pager and Web for user'U1'.
			 */

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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventNotification.selectEventNofifForUser(
						selenium, strUserName_1, strTempName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user 'U1', navigate to Event >> Event Management.
			 * 'Event Management' screen is displayed.
			 */

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
			
			/*
			 * 3 Click on 'Create New Event' button 'Select Event Template'
			 * screen is displayed, event template 'ET' is listed under it.
			 */
			
			
			/*
			 * 4 Create event 'EVE' selecting event template 'ET' and providing
			 * mandatory data,for 'Event Start' field select future time(time
			 * T1) on day 'D1',default value for 'Event end', select to
			 * Re-notify every 0.5 hours. Event is listed in the 'Event
			 * Management' screen.
			 * 
			 * 'Title' and 'Information' are displayed appropriately as provided
			 * while event creation under 'Title' and 'Information' columns.
			 * 
			 * Event start Date and time are displayed appropriately under the
			 * column 'Start'.
			 * 
			 * Event end Date and time are displayed appropriately under the
			 * column 'End'.
			 * 
			 * 'Future' is displayed under the 'Status' column.
			 * 
			 * 'Edit' and 'Cancel' links are displayed under the 'Action' column
			 * for the event created.
			 */

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, false);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {

				assertEquals("", strFuncResult);

				// get Start Date
				strFndStMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMnt"));
				strFndStDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartDay"));
				strFndStYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartYear"));

				// get Start Time
				strFndStHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartHour"));
				strFndStMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMinut"));

				strFndStHr = dts.addTimetoExistingHour(strFndStHr, 1, "HH");

				strFuncResult = objEventSetup.selectStartDateInCreatingEvent(
						selenium, strEveName, strFndStMnth, strFndStDay,
						strFndStYear, strFndStHr, strFndStMinu, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.provideRenotifyTime(selenium,
						strEveName, "1.0", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium, strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {

				assertEquals("", strFuncResult);
				
				// get Start Date
				strFndStMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMnt"));
				strFndStDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartDay"));
				strFndStYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartYear"));

				// get Start Time
				strFndStHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartHour"));
				strFndStMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMinut"));

				// get End Date
				strFndEdMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMnt"));
				strFndEdDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndDay"));
				strFndEdYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndYear"));

				// get End Time
				strFndEdHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndHour"));
				strFndEdMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMinut"));

				
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
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
						+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate = strEndDate + " " + strFndEdHr + ":" + strFndEdMinu;

				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strEndDate, strEveName, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Navigate to Event >> Event List Event is listed in the 'Event
			 * List' screen.
			 * 
			 * 'Title' and 'Description' are displayed appropriately as provided
			 * while event is created under 'Title' and 'Description' columns.
			 * 
			 * Event start Date and time are displayed appropriately under the
			 * column 'Start'.
			 * 
			 * Event end Date and time are displayed appropriately under the
			 * column 'End'.
			 * 
			 * 'Ongoing' is displayed under the 'Status' column.
			 * 
			 * 'View ' link is displayed under the 'Action' column for the event
			 * created.
			 */

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objEventList.navToEventListNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				String strElementID="//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
										+ strEveName + "']";
				strFuncResult=objGeneral.checkForAnElements(selenium, strElementID);
				
				if(strFuncResult.equals("Element NOT Present")){
					log4j.info("Event 'EVE' (Future event) is NOT listed on the 'Event List' screen.");
					strFuncResult="";
					
				}else{
					log4j.info("Event 'EVE' (Future event) is listed on the 'Event List' screen.");
					strFuncResult="Event 'EVE' (Future event) is listed on the 'Event List' screen.";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118088";
			gstrTO = "Verify that a future event can be created.";
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
	
	/********************************************************************************
	'Description	:Verify that unending event can be ended manually.
	'Arguments		:None
	'Returns		:None
	'Date	 		:21-Feb-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS118091() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		EventList objEventList = new EventList();
		EventNotification objEventNotification=new EventNotification();

		try {
			gstrTCID = "118091";
			gstrTO = "Verify that unending event can be ended manually.";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "auto event";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strAction = "View";
			String strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
					strFILE_PATH);
			String strStatus = "Ongoing";
			String strStartDate = "";
			String strEndDate = "never";
			String strDrill = "No";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

		

			String[] strHeader = { "Action", "Icon", "Status", "Start", "End",
					"Title", "Drill", "Template", "Information" };

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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDeSelEventOnly(selenium,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT is created selecting status types
			 * 'NST','MST','SST' and 'TST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
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

			/*
			 * 3. Resource RS is created under resource type RT
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
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
			 * 4. User 'U1' is created by providing mandatory data primary
			 * e-mail, e-mail and pager addresses and assigning following
			 * rights:
			 * 
			 * a. 'Maintain Events' right
			 */
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 5. Event template ET is created by selecting status Types
			 * 'NST','MST','SST' and 'TST' and Resource Type 'RT' and selecting
			 * the check boxes E-mail, Pager and Web for user'U1'.
			 */

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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventNotification.selectEventNofifForUser(
						selenium, strUserName_1, strTempName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user 'U1', navigate to Event >> Event Management.
			 * 'Event Management' screen is displayed.
			 */

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
			
			/*
			 * 3 Click on 'Create New Event' button 'Select Event Template'
			 * screen is displayed, event template 'ET' is listed under it.
			 */
			
			/*
			 * 4 Create event 'EVE' selecting event template 'ET' and providing
			 * mandatory data,for 'Event Start' field select 'immediately' and
			 * 'never' for 'Event end' field. Event is listed in the 'Event
			 * Management' screen.
			 * 
			 * Event start Date and time are displayed appropriately under the
			 * column 'Start'.
			 * 
			 * 'Never' is displayed under the column 'End'.
			 * 
			 * 'Ongoing' is displayed under the 'Status' column.
			 * 
			 * 'Edit' and 'End' links are displayed under the 'Action' column
			 * for the event created.
			 */
			
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventEndNever(selenium,
						strTempName, strResource, strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium, strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			
			try {

				assertEquals("", strFuncResult);

				// get Start Date
				strFndStMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMnt"));
				strFndStDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartDay"));
				strFndStYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartYear"));

				// get Start Time
				strFndStHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartHour"));
				strFndStMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMinut"));

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
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				
				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strEndDate, strEveName, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserName_1 + "/" + strInitPwd,
						strTempName,
						strEveName,
						"Verify from 5th step",
						strNumStatType + "," + strTxtStatType + ","
								+ strMultiStatType + "," + strSatuStatType,
						strResource, strResrctTypName, strRegn };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Events");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118091";
			gstrTO = "Verify that unending event can be ended manually.";
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
	
	/********************************************************************************
	'Description	:Verify that canceling the process of ending an event does not end the event.
	'Arguments		:None
	'Returns		:None
	'Date	 		:21-Feb-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS92680() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		EventList objEventList = new EventList();
		EventNotification objEventNotification=new EventNotification();
		General objGeneral=new General();

		try {
			gstrTCID = "92680";
			gstrTO = "Verify that canceling the process of ending an event does not end the event.";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "auto event";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strAction = "View";
			String strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
					strFILE_PATH);
			String strStatus = "Ongoing";
			String strStartDate = "";
			String strEndDate = "";
			String strDrill = "No";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";


			String strFndEdMnth = "";
			String strFndEdYear = "";
			String strFndEdDay = "";
			String strFndEdHr = "";
			String strFndEdMinu = "";
			
		

			String[] strHeader = { "Action", "Icon", "Status", "Start", "End",
					"Title", "Drill", "Template", "Information" };

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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDeSelEventOnly(selenium,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT is created selecting status types
			 * 'NST','MST','SST' and 'TST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
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

			/*
			 * 3. Resource RS is created under resource type RT
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
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*5. User 'U1' is created with following rights:

				a. Maintain events right.
				b. View resource on Resource RS. */
				
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 5. Event template ET is created by selecting status Types
			 * 'NST','MST','SST' and 'TST' and Resource Type 'RT' and selecting
			 * the check boxes E-mail, Pager and Web for user'U1'.
			 */

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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventNotification.selectEventNofifForUser(
						selenium, strUserName_1, strTempName, true, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user 'U1', navigate to Event >> Event Management.
			 * 'Event Management' screen is displayed.
			 */

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
			
			/*
			 * 3 Click on 'Create New Event' button 'Select Event Template'
			 * screen is displayed, event template 'ET' is listed under it.
			 */
			
			/*
			 * 4 Click on 'Create' link associated with ET, provide data in all
			 * the mandatory fields and click on Save. Event is listed in the
			 * 'Event Management' screen.
			 * 
			 * 'Title' and 'Information' are displayed appropriately as provided
			 * while event is created under 'Title' and 'Information' columns.
			 * 
			 * Event start Date and time are displayed appropriately under the
			 * column 'Start'.
			 * 
			 * Event end Date and time are displayed appropriately under the
			 * column 'End'.
			 * 
			 * 'Ongoing' is displayed under the 'Status' column.
			 * 
			 * 'Edit' and 'End' links are displayed under the 'Action' column
			 * for the event created.
			 */
			
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium, strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			
			try {

				assertEquals("", strFuncResult);

				// get Start Date
				strFndStMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMnt"));
				strFndStDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartDay"));
				strFndStYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartYear"));

				// get Start Time
				strFndStHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartHour"));
				strFndStMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMinut"));
				

				// get End Date
				strFndEdMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMnt"));
				strFndEdDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndDay"));
				strFndEdYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndYear"));

				// get End Time
				strFndEdHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndHour"));
				strFndEdMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMinut"));
				

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
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				
				strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
						+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate = strEndDate + " " + strFndEdHr + ":" + strFndEdMinu;
				
				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strEndDate, strEveName, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 5 Click on 'End' link associated with event 'EVE' Pop up window
			 * is displayed with following message:
			 * 
			 * Are you sure you want to end this event?
			 * 
			 * Press ok to End the event.Press Cancel if you do NOT want to end
			 * the event.
			 * 
			 * 'OK' and 'Cancel' buttons.
			 */
				
			
			/*
			 * 6 Click on 'Cancel' button 'Event Management' screen is
			 * displayed.
			 * 
			 * Event 'EVE' is not ended.
			 * 
			 * Event EVE is listed under it.
			 */
			
			try {

				assertEquals("", strFuncResult);
				selenium.chooseCancelOnNextConfirmation();
				
				selenium.click("//div[@id='mainContainer']/table/tbody/tr/"
						+ "td[6][text()='" + strEveName
						+ "']/parent::tr/td[1]/a[text()='End']");
				assertTrue(selenium
						.getConfirmation()
						.matches(
								"^Are you sure you "
										+ "want to end this event[\\s\\S]\n\nPress OK to end the "
										+ "event\\. Press Cancel if you do NOT want to end the event\\.$"));
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objGeneral.checkForAnElements(selenium,
						"//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
								+ strEveName + "']");
				
				if(strFuncResult.equals("")){
					strFuncResult="";
					log4j.info("'Cancel' buttons is Present,Event 'EVE' is NOT ended.Event EVE is NOT listed under it. ");
				}else{
					strFuncResult="'Cancel' buttons is NOT Present,Event 'EVE' is ended.Event EVE is listed under it. ";
					log4j.info("'Cancel' buttons is NOT Present,Event 'EVE' is ended.Event EVE is NOT listed under it. ");
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {

				assertEquals("", strFuncResult);
			
				selenium.click("//div[@id='mainContainer']/table/tbody/tr/"
						+ "td[6][text()='" + strEveName
						+ "']/parent::tr/td[1]/a[text()='End']");
				assertTrue(selenium
						.getConfirmation()
						.matches(
								"^Are you sure you "
										+ "want to end this event[\\s\\S]\n\nPress OK to end the "
										+ "event\\. Press Cancel if you do NOT want to end the event\\.$"));
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table"
						+ "/tbody/tr/td[6][text()='" + strEveName + "']"
						+ "/parent::tr/td[1]/a[text()='View�History']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				

				String strElementID = "//div[@id='mainContainer']/table"
						+ "/tbody/tr/td[6][text()='" + strEveName + "']"
						+ "/parent::tr/td[1]/a[text()='View�History']";

				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
				if(strFuncResult.equals("")){
					log4j.info("'OK' buttons is Present");
				}else{
					strFuncResult="'OK' buttons is NOT Present";
					log4j.info("'OK' buttons is NOT Present");
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
			gstrTCID = "92680";
			gstrTO = "Verify that canceling the process of ending an event does not end the event.";
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
	
	
	
	/********************************************************************************
	'Description	:Verify that event banner is not displayed when an event is created by deselecting the option to display in banner.
	'Arguments		:None
	'Returns		:None
	'Date	 		:22-Feb-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS92666() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		General objGeneral=new General();
		ViewMap objViewMap=new ViewMap();
		try {
			gstrTCID = "92666";
			gstrTO = "Verify that event banner is not displayed when an event" +
					" is created by deselecting the option to display in banner.";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strExistEveName = "ExistEvent" + System.currentTimeMillis();
			String strExistEveValue="";
			
			String strInfo = "auto event";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDeSelEventOnly(selenium,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT is created selecting status types
			 * 'NST','MST','SST' and 'TST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
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

			/*
			 * 3. Resource RS is created under resource type RT
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
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*5. User 'U1' is created with following rights:

				a. Maintain events right.
				b. View resource on Resource RS. */
				
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 5. Event template ET is created by selecting status Types
			 * 'NST','MST','SST' and 'TST' and Resource Type 'RT' and selecting
			 * the check boxes E-mail, Pager and Web for user'U1'.
			 */

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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
					strFuncResult = "Function failes to fetch ETY Value";
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
						strTempName, strExistEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strExistEveValue= objEventSetup.fetchEventValueInEventList(selenium, strExistEveName);

				if (strExistEveValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Event value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
		

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user 'U1', navigate to Event >> Event Management.
			 * 'Event Management' screen is displayed.
			 */

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
		
			
			/*
			 * 3 Navigate to 'Event >> Event management' 'Event Management'
			 * screen is displayed with list of events.
			 */
			/*
			 * 4 Click on 'Create New Event' button 'Select Event Template'
			 * screen is displayed with event templates in the region.
			 * 
			 * Event template ET is listed under it.
			 */
			
			/*
			 * 5 Click on 'Create' link associated with ET,Create event EVE
			 * providing data in the mandatory fields and deselecting the option
			 * to display in banner. Event EVE is created and listed under event
			 * management screen.
			 * 
			 * Event Banner for EVE is not displayed.
			 */
			
			
			try {

				assertEquals("", strFuncResult);

				String[] strResourceValue = { strRSValue[0] };
				strFuncResult = objEventSetup.createEventWitManyRS(selenium,
						strTempName, strResourceValue, strEveName, strInfo,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup
						.slectAndDeselectEventBannerOption(selenium,
								strEveName, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {

				assertEquals("", strFuncResult);

				String strElementID="//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
				
				if(strFuncResult.equals("Element NOT Present")){
					log4j.info("Event banner for event 'EVE' is NOT displayed.");
					strFuncResult="";
					
				}else{
					log4j.info("Event banner for event 'EVE' is  displayed.");
					strFuncResult="Event banner for event 'EVE' is displayed.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			/*
			 * 6 Navigate to other screens in the application. Event Banner for
			 * EVE is not displayed.
			 */
		
			
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);

				String strElementID="//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
				
				if(strFuncResult.equals("Element NOT Present")){
					log4j.info("Event banner for event 'EVE' is NOT displayed.");
					strFuncResult="";
					
				}else{
					log4j.info("Event banner for event 'EVE' is  displayed.");
					strFuncResult="Event banner for event 'EVE' is displayed.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 7 Click on event banner of any other event present in the region.
			 * Event banner for event 'EVE' is dispalyed.
			 */
			
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navEventStatusPage(selenium, strExistEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);

				String strElementID="//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
				
				if(strFuncResult.equals("")){
					log4j.info("Event banner for event 'EVE' is displayed.");
					strFuncResult="";
					
				}else{
					log4j.info("Event banner for event 'EVE' is NOT displayed.");
					strFuncResult="Event banner for event 'EVE' is NOT displayed.";
				}
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

				String strElementID="//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
				
				if(strFuncResult.equals("Element NOT Present")){
					log4j.info("Event banner for event 'EVE' is NOT displayed.");
					strFuncResult="";
					
				}else{
					log4j.info("Event banner for event 'EVE' is  displayed.");
					strFuncResult="Event banner for event 'EVE' is displayed.";
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
			gstrTCID = "92666";
			gstrTO = "Verify that event banner is not displayed when an event is" +
					" created by deselecting the option to display in banner.";
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
	
	/********************************************************************************
	'Description	:Verify that event can be ended if more than one active events are present.
	'Arguments		:None
	'Returns		:None
	'Date	 		:22-Feb-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS93300() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		EventList objEventList = new EventList();
		EventNotification objEventNotification=new EventNotification();
		General objGeneral=new General();

		try {
			gstrTCID = "93300";
			gstrTO = "Verify that event can be ended if more than one active events are present.";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName1 = "Event1" + System.currentTimeMillis();
			String strEveName2 = "Event2" + System.currentTimeMillis();
			String strInfo = "auto event";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strAction = "Edit��|��End";
			String strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
					strFILE_PATH);
			String strStatus = "Ongoing";
			String strStartDate = "";
			String strEndDate = "";
			String strDrill = "No";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";


			String strFndEdMnth = "";
			String strFndEdYear = "";
			String strFndEdDay = "";
			String strFndEdHr = "";
			String strFndEdMinu = "";
			
		

			String[] strHeader = { "Action", "Icon", "Status", "Start", "End",
					"Title", "Drill", "Template", "Information" };

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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDeSelEventOnly(selenium,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT is created selecting status types
			 * 'NST','MST','SST' and 'TST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
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

			/*
			 * 3. Resource RS is created under resource type RT
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
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*5. User 'U1' is created with following rights:

				a. Maintain events right.
				b. View resource on Resource RS. */
				
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 5. Event template ET is created by selecting status Types
			 * 'NST','MST','SST' and 'TST' and Resource Type 'RT' and selecting
			 * the check boxes E-mail, Pager and Web for user'U1'.
			 */

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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventNotification.selectEventNofifForUser(
						selenium, strUserName_1, strTempName, true, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user 'U1', navigate to Event >> Event Management.
			 * 'Event Management' screen is displayed.
			 */

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
			
			/*
			 * 3 Click on 'Create New Event' button 'Select Event Template'
			 * screen is displayed, event template 'ET' is listed under it.
			 */
			
			/*
			 * 4 Click on 'Create' link associated with ET, provide data in all
			 * the mandatory fields and click on Save. Event is listed in the
			 * 'Event Management' screen.
			 * 
			 * 'Title' and 'Information' are displayed appropriately as provided
			 * while event is created under 'Title' and 'Information' columns.
			 * 
			 * Event start Date and time are displayed appropriately under the
			 * column 'Start'.
			 * 
			 * Event end Date and time are displayed appropriately under the
			 * column 'End'.
			 * 
			 * 'Ongoing' is displayed under the 'Status' column.
			 * 
			 * 'Edit' and 'End' links are displayed under the 'Action' column
			 * for the event created.
			 */
			
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName2, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName1, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium, strEveName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			
			try {

				assertEquals("", strFuncResult);

				// get Start Date
				strFndStMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMnt"));
				strFndStDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartDay"));
				strFndStYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartYear"));

				// get Start Time
				strFndStHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartHour"));
				strFndStMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMinut"));
				

				// get End Date
				strFndEdMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMnt"));
				strFndEdDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndDay"));
				strFndEdYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndYear"));

				// get End Time
				strFndEdHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndHour"));
				strFndEdMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMinut"));
				

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEveName1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				
				strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
						+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate = strEndDate + " " + strFndEdHr + ":" + strFndEdMinu;
				
				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strEndDate, strEveName1, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName1,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Click on 'End' link corresponding to event EVE1 Confirmation
			 * window is displayed.
			 */
			try {

				assertEquals("", strFuncResult);
			
				selenium.click("//div[@id='mainContainer']/table/tbody/tr/"
						+ "td[6][text()='" + strEveName1
						+ "']/parent::tr/td[1]/a[text()='End']");
				assertTrue(selenium
						.getConfirmation()
						.matches(
								"^Are you sure you "
										+ "want to end this event[\\s\\S]\n\nPress OK to end the "
										+ "event\\. Press Cancel if you do NOT want to end the event\\.$"));
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*	4 	Click on 'OK' 		Event EVE2 is ended and event banner is not displayed.

			'View History' link is displayed under 'Action' column for event EVE1

			'Ended' is displayed under 'Status' column for event EVE1.

			Event EVE2 is displayed in banner and is listed on the screen.

			'View' link is displayed under 'Action' column for event EVE2

			'Ongoing' is displayed under 'Status' column for event EVE2.
*/
			
			try {

				assertEquals("", strFuncResult);
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table"
						+ "/tbody/tr/td[6][text()='" + strEveName1 + "']"
						+ "/parent::tr/td[1]/a[text()='View�History']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				

				String strElementID = "//div[@id='mainContainer']/table"
						+ "/tbody/tr/td[6][text()='" + strEveName1 + "']"
						+ "/parent::tr/td[1]/a[text()='View�History']";

				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {

				assertEquals("", strFuncResult);
				strAction = "View�History";
				strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
						strFILE_PATH);
				strStatus = "Ended";
				strStartDate = "";
				strEndDate = "";
				strDrill = "No";
				
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				
				strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
						+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate = strEndDate + " " + strFndEdHr + ":" + strFndEdMinu;
				
				

				
				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strEndDate, strEveName1, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName1,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
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
			gstrTCID = "93300";
			gstrTO = "Verify that event can be ended if more than one active events are present.";
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
	/****************************************************************************************
	'Description	:Verify that 'Event status' of particular event is displayed when clicked
	                 on 'View' link associated with the ongoing event on 'Event List' screen.
	'Arguments		:None
	'Returns		:None
	'Date			:2/25/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                                 Modified By
	'Date					                                                     Name
	*****************************************************************************************/
	@Test
	public void testFTS93254() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews=new Views();
		Roles objRole = new Roles();
		CreateUsers objCreateUsers=new CreateUsers();
		EventList objEventList = new EventList();
		EventSetup objEventSetup=new EventSetup();
		General objGeneral=new General();
		try {
			gstrTCID = "93254"; // Test Case Id
			gstrTO = "Verify that 'Event status' of particular event is displayed when clicked on"
					+ "'View' link associated with the ongoing event on 'Event List' screen.";// TO
			gstrReason = "";
			gstrResult = "FAIL";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];
			// Rol
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			//RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
            //ET
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";
			String strEveName1 = "Event1" + System.currentTimeMillis();
			String strInfo = "auto event";

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
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
		/*
		* STEP :
		  Action:Preconditions:
				1. Status Types NST,MST,SST,TST are created
				2. Resource type RT is created selecting status types NST,MST,SST and TST.
				3. Resource RS is created under resource type RT.
				4. Event template ET is created selecting RT and all 4 status types.
				5. User 'U1' is created with maintain events right and view resource right on Resource RS and role to view associated status types .
				6. View 'V1' is created selecting all 4 status types and resource RS.
		  Expected Result:No Expected Result
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTsvalue = {};
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTsvalue, false,
						strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						selenium, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							selenium, strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
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

			// RS
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
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ET

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
				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strStatusTypeVal, false, strRSValue);
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
		* STEP :
		  Action:Login as user 'U1',Navigate to 'Event >> Event management'
		  Expected Result:'Event Management' screen is displayed with list of events.
		*/
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
		
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
		/*
		* STEP :
		  Action:Click on 'Create New Event' button
		  Expected Result:'Select Event Template' screen is displayed with event templates in the region.
		  'Create' link is present adjacent to all event templates.
		  Event template ET is listed under it.
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createNewEventWithPageVerify(selenium, strTempName, 
						strResource, strEveName1, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Create' link associated with ET, create event EVE providing data in the mandatory fields and selecting resource RS.
		  Expected Result:Event EVE is created and listed under event management screen.
          Event Banner for EVE is displayed.
		*/	
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
						+ strEveName1 + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				log4j.info(" Event Banner for "+strEveName1+" is displayed.");
			} catch (AssertionError Ae) {
				log4j.info(" Event Banner for "+strEveName1+" is NOT displayed.");
				strFuncResult=" Event Banner for "+strEveName1+" is NOT displayed.";
				gstrReason = strFuncResult;
			}		
		/*
		* STEP :
		  Action:Navigate to view >> V1
		  Expected Result:Resource RS is displayed under RT along with NST , MST, SST, TST status types.
          Event icon selected for event EVE is displayed next to resource RS.
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource};
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrctTypName, strRS,
						strRTValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={strMultiStatType,strNumStatType,strSatuStatType,strTxtStatType};
				strFuncResult = objViews.checkStatusTypeInUserView(selenium, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on event icon associated with resource RS.
		  Expected Result:'Event Status' screen for event EVE is displayed along with the Resource RS under
		   RT and NST , MST, SST, TST status types.
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.navToEvntStatScreenByEventIcon(
						selenium, strEveName1, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strMultiStatType, strNumStatType,
						strSatuStatType, strTxtStatType };
				strFuncResult = objEventList.checkOnlyDetailsInEventBanner(selenium, 
						strEveName1, strResrctTypName, strResource, strStatTypeArr);
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
			gstrTCID = "93254";
			gstrTO = "Verify that 'Event status' of particular event is displayed when clicked on 'View' link associated with the ongoing event on 'Event List' screen.";
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
	/****************************************************************************************
	'Description	:Verify that 'Ended' is displayed under the 'Status' column on 
	                 'Event List' screen when event is ended.
	'Arguments		:None
	'Returns		:None
	'Date			:2/25/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                                 Modified By
	'Date					                                                     Name
	*****************************************************************************************/
	@Test
	public void testFTS93257() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		CreateUsers objCreateUsers=new CreateUsers();
		EventList objEventList = new EventList();
		EventSetup objEventSetup=new EventSetup();
		General objGeneral=new General();
		try {
			gstrTCID = "93257"; // Test Case Id
			gstrTO = "Verify that 'Ended' is displayed under the 'Status' column on 'Event List' screen when event is ended.";// TO
			gstrReason = "";
			gstrResult = "FAIL";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];
			// Rol
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			//RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
            //ET
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";
			String strEveName1 = "Event1" + System.currentTimeMillis();
			String strInfo = "auto event";

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
			String strAction = "Edit��|��End";
			String strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
					strFILE_PATH);
			
			String strStatus = "Ongoing";
			String strStartDate = "";
			String strEndDate = "";
			String strDrill = "No";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			String strFndEdMnth = "";
			String strFndEdYear = "";
			String strFndEdDay = "";
			String strFndEdHr = "";
			String strFndEdMinu = "";
			
			String[] strHeader = { "Action", "Icon", "Status", "Start", "End",
					"Title", "Drill", "Template", "Information" };
		/*
		* STEP :
		  Action:Preconditions:
				1. Status Types NST,MST,SST,TST are created
				2. Resource type RT is created selecting status types NST,MST,SST and TST.
				3. Resource RS is created under resource type RT.
				4. Event template ET is created selecting RT and all status types.
				5. User 'U1' is created with following rights:
				a. Maintain events right.
				b. View resource on Resource RS. 
		  Expected Result:No Expected Result
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTsvalue = {};
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTsvalue, false,
						strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						selenium, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							selenium, strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
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

			// RS
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
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ET

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
				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
					strFuncResult = "Function failes to fetch ETY Value";
				}
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
		* STEP :
		  Action:Login as user 'U1',Navigate to 'Event >> Event management'
		  Expected Result:'Event Management' screen is displayed with list of events.
		*/
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
		
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
		/*
		* STEP :
		  Action:Click on 'Create New Event' button
		  Expected Result:'Select Event Template' screen is displayed with event templates in the region.
                           Event template ET is listed under it. 
		  Event template ET is listed under it.
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createNewEventWithPageVerify(selenium, strTempName, 
						strResource, strEveName1, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Create' link associated with ET, create event EVE providing data in the mandatory fields and selecting resource RS.
		  Expected Result: Event is listed in the 'Event Management' screen.
					'Title' and 'Information' are displayed appropriately as provided while event is created under
					 'Title' and 'Information' columns.
					Event start Date and time are displayed appropriately under the column 'Start'.
					Event end Date and time are displayed appropriately under the column 'End'.
					'Ongoing' is displayed under the 'Status' column.
					'Edit' and 'End' links are displayed under the 'Action' column for the event created. 
		*/	

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				// get Start Date
				strFndStMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMnt"));
				strFndStDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartDay"));
				strFndStYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartYear"));

				// get Start Time
				strFndStHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartHour"));
				strFndStMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMinut"));

				// get End Date
				strFndEdMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMnt"));
				strFndEdDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndDay"));
				strFndEdYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndYear"));

				// get End Time
				strFndEdHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndHour"));
				strFndEdMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMinut"));
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEveName1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
						+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate = strEndDate + " " + strFndEdHr + ":" + strFndEdMinu;

				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strEndDate, strEveName1, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName1,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'End' link associated with event 'EVE' 	
		  Expected Result:Pop up window is displayed with following message:
					Are you sure you want to end this event?
					Press ok to End the event.Press Cancel if you do NOT want to end the event.
					'OK' and 'Cancel' buttons. 
		*/
			try {

				assertEquals("", strFuncResult);			
				selenium.click("//div[@id='mainContainer']/table/tbody/tr/"
						+ "td[6][text()='" + strEveName1
						+ "']/parent::tr/td[1]/a[text()='End']");
				assertTrue(selenium
						.getConfirmation()
						.matches(
								"^Are you sure you "
										+ "want to end this event[\\s\\S]\n\nPress OK to end the "
										+ "event\\. Press Cancel if you do NOT want to end the event\\.$"));				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		* STEP :
		  Action:Click on 'Ok' button 
		  Expected Result:'Event Management' screen is displayed.
					Event 'EVE' is ended.
					'Ended' is displayed under the 'Status' column.
					Event EVE is listed under 'Event Management' screen with 'View History' link under action column. 
		*/
			
			try {
				assertEquals("", strFuncResult);			
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table"
						+ "/tbody/tr/td[6][text()='" + strEveName1 + "']"
						+ "/parent::tr/td[1]/a[text()='View�History']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				

				String strElementID = "//div[@id='mainContainer']/table"
						+ "/tbody/tr/td[6][text()='" + strEveName1 + "']"
						+ "/parent::tr/td[1]/a[text()='View�History']";

				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {

				assertEquals("", strFuncResult);
				strAction = "View�History";
				strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
						strFILE_PATH);
				strStatus = "Ended";
				strStartDate = "";
				strEndDate = "";
				strDrill = "No";
				
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				
				strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
						+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate = strEndDate + " " + strFndEdHr + ":" + strFndEdMinu;
				
				

				
				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strEndDate, strEveName1, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName1,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
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
			gstrTCID = "93257"; // Test Case Id
			gstrTO = "Verify that 'Ended' is displayed under the 'Status' column on 'Event List' screen when event is ended.";// TO
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
	/****************************************************************************************
	'Description	:Verify that event can be created by selecting multiple resources present
	                 under the same resource type.
	'Arguments		:None
	'Returns		:None
	'Date			:2/25/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                                 Modified By
	'Date					                                                     Name
	*****************************************************************************************/
	@Test
	public void testFTS92674() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		CreateUsers objCreateUsers=new CreateUsers();
		EventList objEventList = new EventList();
		EventSetup objEventSetup=new EventSetup();
		try {
			gstrTCID = "92674"; // Test Case Id
			gstrTO = "Verify that event can be created by selecting multiple resources present under the same resource type.";// TO
			gstrReason = "";
			gstrResult = "FAIL";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[2];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];
			// Rol
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			//RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			//RS
			String strResource1= "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strResVal = "";
			String strResValue = "";
			String strContFName = "auto";
			String strContLName = "qsg";
            //ET
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";
			String strEveName1 = "Event1" + System.currentTimeMillis();
			String strInfo = "auto event";

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
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);;
		/*
		* STEP :
		  Action:Preconditions:
				1. Status Types NST,MST,SST,TST are created
				2. Resource type RT is created selecting status types NST,MST,SST and TST.
				2. Resource RS1 and RS2 are created under resource type RT.
				3. User 'U1' is created with following rights:
				a. Maintain events Template right.
				b. Maintain Events right.
				c. View resource right on Resource RS1 and RS2.
				d. Role to view the associated status types.
				4. Event Template 'ET' is created providing mandatory data and selecting Resource Type 'RT' status Types NST,MST,SST and TST.
		  Expected Result:No Expected Result
		*/
		
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
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(seleniumPrecondition,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
				String[] strSTsvalue = {};
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTsvalue, false,
						strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
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

			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition, strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
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

			// RS
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource1, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource1);
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
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource2, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResValue= objRs.fetchResValueInResList(seleniumPrecondition, strResource1);
				if (strResValue.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResValue;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user
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
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource2, strRSValue[1], false, false,
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
			// ET

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
				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Login as user 'U1',Navigate to 'Event >> Event management'
		  Expected Result:'Event Management' screen is displayed with list of events.
		*/
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
		
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
		/*
		* STEP :
		  Action:Click on 'Create New Event' button
		  Expected Result: 'Select Event Template' screen is displayed. 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName1, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Create an event 'EVE' providing mandatory data and selecting resources RS1 and RS2.
		  Expected Result:Event 'EVE' is listed on 'Event List' screen. 
		*/	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource2, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEvent(selenium,
						strEveName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on the event banner of 'EVE'. 
		  Expected Result:Resources 'RS1' and 'RS2' are displayed on the 'Event Status' screen under
		                  RT along with NST, MST, TST and SST status types. 
		*/
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strMultiStatType, strNumStatType,
						strSatuStatType, strTxtStatType };
				strFuncResult = objEventList.checkInEventBannerNew(selenium,
						strEveName1, strResrctTypName, strResource1,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strMultiStatType, strNumStatType,
						strSatuStatType, strTxtStatType };
				strFuncResult = objEventList.checkOnlyDetailsInEventBanner(
						selenium, strEveName1, strResrctTypName, strResource2,
						strStatTypeArr);
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
			gstrTCID = "92674"; // Test Case Id
			gstrTO = "Verify that event can be created by selecting multiple resources present under the same resource type.";// TO
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

	/****************************************************************************************
	'Description	:Verify that all the events ended within the last 24 hours are displayed 
	                 with a 'View History' button in the event list screen under:
                     1. Event>>Event Management                
                     2. Event>>Event List
	'Arguments		:None
	'Returns		:None
	'Date			:2/25/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                                 Modified By
	'Date					                                                     Name
	*****************************************************************************************/
	@Test
	public void testFTS93259() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		CreateUsers objCreateUsers=new CreateUsers();
		EventList objEventList = new EventList();
		EventSetup objEventSetup=new EventSetup();
		try {
			gstrTCID = "93259"; // Test Case Id
			gstrTO = "Verify that all the events ended within the last 24 hours are displayed "+
	                 "with a 'View History' button in the event list screen under:"+
                     "1. Event>>Event Management  "+              
                     "2. Event>>Event List";// TO
			gstrReason = "";
			gstrResult = "FAIL";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];
			// Rol
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			//RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
            //ET
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";
			String strEveName1 = "Event1" + System.currentTimeMillis();
			String strEveName2 = "Event2" + System.currentTimeMillis();
			String strInfo = "auto event";
			String strAdminFullName = rdExcel.readData("Login", 4, 1);	
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
			
			String strAction = "Edit��|��End";
			String strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
					strFILE_PATH);
			
			String strStatus = "Ongoing";
			String strStartDate = "";
			String strEndDate = "";
			String strDrill = "No";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			String strFndEdMnth = "";
			String strFndEdYear = "";
			String strFndEdDay = "";
			String strFndEdHr = "";
			String strFndEdMinu = "";

			String strFndStHr1 = "";
			String strFndStMinu1 = "";
			String strFndEdHr1 = "";
			String strFndEdMinu1 = "";
			
			String[] strHeader = { "Action", "Icon", "Status", "Start", "End",
					"Title", "Drill", "Template", "Information" };
		/*
		* STEP :
		  Action:Preconditions:
			1. User 'U1' is created with default rights.
			2. Status Types NST,MST,SST,TST are created
			3. Resource type RT is created selecting status types NST,MST,SST and TST.
			4. Resource RS is created under resource type RT.
			5. Event template ET is created selecting RT and all status types.
			6. Active event EVE is created selecting ET.
			7. Inactive event EVE1 is present in the region. 
		  Expected Result:No Expected Result
		*/
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
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
						seleniumPrecondition, strNSTValue, strNumStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, strTxtStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
				String[] strSTsvalue = {};
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTsvalue, false, strSTvalue, false, true);
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

			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition, strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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

			// RS
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
			// user
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
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
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
						false, false, false, true);
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
				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
			// event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createNewEventWithPageVerify(
						seleniumPrecondition, strTempName, strResource,
						strEveName1, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(
						seleniumPrecondition, strEveName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				// get Start Date
				strFndStMnth = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.StartMnt"));
				strFndStDay = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.StartDay"));
				strFndStYear = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.StartYear"));

				// get Start Time
				strFndStHr = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.StartHour"));
				strFndStMinu = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.StartMinut"));

				// get End Date
				strFndEdMnth = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.EndMnt"));
				strFndEdDay = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.EndDay"));
				strFndEdYear = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.EndYear"));

				// get End Time
				strFndEdHr = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.EndHour"));
				strFndEdMinu = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.EndMinut"));
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(
						seleniumPrecondition, strEveName1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createNewEventWithPageVerify(
						seleniumPrecondition, strTempName, strResource,
						strEveName2, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(
						seleniumPrecondition, strEveName2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				// get Start Date
				strFndStMnth = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.StartMnt"));
				strFndStDay = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.StartDay"));
				strFndStYear = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.StartYear"));

				// get Start Time
				strFndStHr1 = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.StartHour"));
				strFndStMinu1 = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.StartMinut"));

				// get End Date
				strFndEdMnth = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.EndMnt"));
				strFndEdDay = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.EndDay"));
				strFndEdYear = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.EndYear"));

				// get End Time
				strFndEdHr1 = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.EndHour"));
				strFndEdMinu1 = seleniumPrecondition
						.getSelectedLabel(propElementDetails
								.getProperty("Event.CreateEve.EndMinut"));
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(
						seleniumPrecondition, strEveName2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.endEvent(seleniumPrecondition,
						strEveName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
		/*
		* STEP :
		  Action:Login as user 'U1' 
		  Expected Result:Region default screen is displayed. 
		*/
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		* STEP :
		  Action:Navigate to Event >> Event List. 
		  Expected Result:Event EVE is listed on the 'Event List' screen along with the other active events and
		   inactive events(ended in last 24 hours)created in the region under the columns:
						a. Action
						b. Icon
						c. Status
						d. Start
						e. End
						f. Title
						g. Drill
						h. Template
						i. Information
						'Create New Event' button is not available for user 'U1'.
						Under Action column 'View' is displayed for active events,' View History' is displayed for inactive events. 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.navToEventListNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
								+ strEveName1 + "']"));
				log4j.info("Active Event '" + strEveName1
						+ "' is listed on 'Event List' screen.");

			} catch (AssertionError ae) {
				log4j.info("Active Event '" + strEveName1
						+ "' is NOT listed on 'Event List' screen.");
				strFuncResult = "Active Event '" + strEveName1
						+ "' is NOT listed on 'Event List' screen.";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
								+ strEveName2 + "']"));
				log4j.info("InActive Event '" + strEveName2
						+ "' is listed on 'Event List' screen.");

			} catch (AssertionError ae) {
				log4j.info("InActive Event '" + strEveName2
						+ "' is NOT listed on 'Event List' screen.");
				strFuncResult = "InActive Event '" + strEveName2
						+ "' is NOT listed on 'Event List' screen.";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEvent")));
				log4j.info("'Create New Event' button is NOT available for user '"
						+ strUserName_1 + "'. ");

			} catch (AssertionError ae) {
				log4j.info("'Create New Event' button is available for user '"
						+ strUserName_1 + "'.");
				strFuncResult = "'Create New Event' button is available for user '"
						+ strUserName_1 + "'.";
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				strAction = "View�History";
				strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
						strFILE_PATH);
				strStatus = "Ended";
				strStartDate = "";
				strEndDate = "";
				strDrill = "No";

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr1 + ":"
						+ strFndStMinu1;

				strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
						+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate = strEndDate + " " + strFndEdHr1 + ":"
						+ strFndEdMinu1;

				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strStartDate, strEveName2, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName2,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				strAction = "View";
				strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
						strFILE_PATH);
				strStatus = "Ongoing";
				strStartDate = "";
				strEndDate = "";
				strDrill = "No";

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
						+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate = strEndDate + " " + strFndEdHr + ":" + strFndEdMinu;

				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strEndDate, strEveName1, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName1,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on View link associated with event EVE 	
		  Expected Result:'Event Status' screen is displayed along with the Resource RS under RT and NST , MST, SST, TST status types. 
		*/	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.navToEventStatusScreenByViewLink(
						selenium, strEveName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strMultiStatType, strNumStatType,
						strSatuStatType, strTxtStatType };
				strFuncResult = objEventList.checkOnlyDetailsInEventBanner(
						selenium, strEveName1, strResrctTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
		/*
		* STEP :
		  Action:Click on View History link associated with inactive event EVE1. 
		  Expected Result:Event History Pop up window is displayed with details of event EVE1
						Event Name
						(< Created user full name>)
						Event Name
						(< Updated user full name>) 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.navToEventListNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strUser = ""+strAdminFullName+"";
				strFuncResult = objEventList.navToEventHistoryVerifyNew(selenium,
						strEveName2, strInfo, strUser);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Event >> Event Management. 
		  Expected Result:'Event Management' screen is displayed with appropriate values for events under following columns:
					a. Action (Edit/End links available for active events , View History link for inactive events)
					b. Icon (Icon selected for particular events is displayed)
					c. Status ('Ongoing' for active events, 'Future' for future events and 'Ended' for Ended/inactive events in last 24 hours)
					d. Start (event start date and time for events)
					e. End (event End date and time)
					f. Title (Title of the event)
					g. Drill (Yes for drill event and No for non drill event)
					h. Template (Event template name for corresponding event)
					i. Information (information of the event)
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				strAction = "View�History";
				strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
						strFILE_PATH);
				strStatus = "Ended";
				strStartDate = "";
				strEndDate = "";
				strDrill = "No";

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr1 + ":"
						+ strFndStMinu1;

				strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
						+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate = strEndDate + " " + strFndEdHr1 + ":"
						+ strFndEdMinu1;

				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strStartDate, strEveName2, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName2,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				strAction = "Edit��|��End";
				strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
						strFILE_PATH);
				strStatus = "Ongoing";
				strStartDate = "";
				strEndDate = "";
				strDrill = "No";

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
						+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate = strEndDate + " " + strFndEdHr + ":" + strFndEdMinu;

				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strEndDate, strEveName1, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName1,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
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
			gstrTCID = "93259"; // Test Case Id
			gstrTO = "Verify that all the events ended within the last 24 hours are displayed "
					+ "with a 'View History' button in the event list screen under:"
					+ "1. Event>>Event Management  " + "2. Event>>Event List";// TO
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
	/****************************************************************************************
	'Description	:Verify that user with right to maintain events can view 'Event List' screen.
	'Arguments		:None
	'Returns		:None
	'Date			:2/25/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                                 Modified By
	'Date					                                                     Name
	*****************************************************************************************/
	@Test
	public void testFTS92694() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		CreateUsers objCreateUsers=new CreateUsers();
		EventList objEventList = new EventList();
		EventSetup objEventSetup=new EventSetup();
		General objGeneral=new General();
		try {
			gstrTCID = "92694"; // Test Case Id
			gstrTO = "Verify that user with right to maintain events can view 'Event List' screen.";// TO
			gstrReason = "";
			gstrResult = "FAIL";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];
			// Rol
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			//RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
            //ET
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";
			String strEveName1 = "Event1" + System.currentTimeMillis();
			String strEveName2 = "Event2" + System.currentTimeMillis();
			String strInfo = "auto event";
			String strAdminFullName = rdExcel.readData("Login", 4, 1);	
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
			
			String strAction = "Edit��|��End";
			String strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
					strFILE_PATH);
			
			String strStatus = "Ongoing";
			String strStartDate = "";
			String strEndDate = "";
			String strDrill = "No";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			String strFndEdMnth = "";
			String strFndEdYear = "";
			String strFndEdDay = "";
			String strFndEdHr = "";
			String strFndEdMinu = "";

			String strStartDate1 = "";
			String strEndDate1 = "";		
			String strFndStMnth1 = "";
			String strFndStYear1 = "";
			String strFndStDay1 = "";
			String strFndStHr1 = "";
			String strFndStMinu1 = "";

			String strFndEdMnth1 = "";
			String strFndEdYear1 = "";
			String strFndEdDay1 = "";
			String strFndEdHr1 = "";
			String strFndEdMinu1 = "";

			
			String[] strHeader = { "Action", "Icon", "Status", "Start", "End",
					"Title", "Drill", "Template", "Information" };
		/*
		* STEP :
		  Action:Preconditions:
			1. User 'U1' is created with default rights.
			2. Status Types NST,MST,SST,TST are created
			3. Resource type RT is created selecting status types NST,MST,SST and TST.
			4. Resource RS is created under resource type RT.
			5. Event template ET is created selecting RT and all status types.
			6. Active event EVE is created selecting ET.
			7. Inactive event EVE1 is present in the region. 
		  Expected Result:No Expected Result
		*/
		
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
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(seleniumPrecondition,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
				String[] strSTsvalue = {};
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTsvalue, false,
						strSTvalue, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
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

			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition, strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
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

			// RS
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user
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
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
			// ET

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
				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
			//event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createNewEventWithPageVerify(seleniumPrecondition, strTempName, 
						strResource, strEveName1, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(seleniumPrecondition,
						strEveName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				// get Start Date
				strFndStMnth = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMnt"));
				strFndStDay = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartDay"));
				strFndStYear = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartYear"));

				// get Start Time
				strFndStHr = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartHour"));
				strFndStMinu = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMinut"));

				// get End Date
				strFndEdMnth = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMnt"));
				strFndEdDay = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndDay"));
				strFndEdYear = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndYear"));

				// get End Time
				strFndEdHr = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndHour"));
				strFndEdMinu = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMinut"));
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(seleniumPrecondition,
						strEveName1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createNewEventWithPageVerify(seleniumPrecondition, strTempName, 
						strResource, strEveName2, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(seleniumPrecondition,
						strEveName2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				// get Start Date
				strFndStMnth1= seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMnt"));
				strFndStDay1= seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartDay"));
				strFndStYear1 = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartYear"));

				// get Start Time
				strFndStHr1 = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartHour"));
				strFndStMinu1 = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMinut"));

				// get End Date
				strFndEdMnth1 = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMnt"));
				strFndEdDay1= seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndDay"));
				strFndEdYear1 = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndYear"));

				// get End Time
				strFndEdHr1 = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndHour"));
				strFndEdMinu1 = seleniumPrecondition.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.EndMinut"));
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(seleniumPrecondition,
						strEveName2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.endEvent(seleniumPrecondition, strEveName2);
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
		  Action:Login as user 'U1' 
		  Expected Result:Region default screen is displayed. 
		*/
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		* STEP :
		  Action:Navigate to Event >> Event List. 
		  Expected Result:Event EVE is listed on the 'Event List' screen along with the other active events and
		   inactive events(ended in last 24 hours)created in the region under the columns:
						a. Action
						b. Icon
						c. Status
						d. Start
						e. End
						f. Title
						g. Drill
						h. Template
						i. Information
						'Create New Event' button is not available for user 'U1'.
						Under Action column 'View' is displayed for active events,' View History' is displayed for inactive events. 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.navToEventListNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID="//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
								+ strEveName1 + "']";
				strFuncResult=objGeneral.CheckForElements(selenium, strElementID);
				log4j.info("Active Event '" + strEveName1+ "' is listed on 'Event List' screen.");			
			} catch (AssertionError ae) {
				log4j.info("Active Event '" + strEveName1+ "' is NOT listed on 'Event List' screen.");
				strFuncResult = "Active Event '" + strEveName1+ "' is NOT listed on 'Event List' screen.";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID="//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
								+ strEveName2 + "']";
		        strFuncResult=objGeneral.CheckForElements(selenium, strElementID);
				log4j.info("InActive Event '" + strEveName2+ "' is listed on 'Event List' screen.");	
			} catch (AssertionError ae) {
				log4j.info("InActive Event '" + strEveName2+ "' is NOT listed on 'Event List' screen.");
				strFuncResult = "InActive Event '" + strEveName2 + "' is NOT listed on 'Event List' screen.";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent(propElementDetails.getProperty("Event.CreateEvent")));
				log4j.info("'Create New Event' button is NOT available for user '"+strUserName_1+"'. ");			
			} catch (AssertionError ae) {
				log4j.info("'Create New Event' button is available for user '"+strUserName_1+"'.");
				strFuncResult = "'Create New Event' button is available for user '"+strUserName_1+"'.";
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				strAction = "View�History";
				strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,strFILE_PATH);
				strStatus = "Ended";
				strStartDate1 = "";
				strEndDate1 = "";
				strDrill = "No";
				
				strStartDate1 = dts.converDateFormat(strFndStYear1 + strFndStMnth1
						+ strFndStDay1, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate1 = strStartDate1 + " " + strFndStHr1 + ":"
						+ strFndStMinu1;
				
				strEndDate1 = dts.converDateFormat(strFndEdYear1 + strFndEdMnth1
						+ strFndEdDay1, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate1 = strEndDate1 + " " + strFndEdHr1 + ":" + strFndEdMinu1;
						
				String[] strData = { strAction, strIcon, strStatus,
						strStartDate1, strStartDate1, strEveName2, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName2,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				strAction = "View";
				strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,strFILE_PATH);
				strStatus = "Ongoing";
				strStartDate = "";
				strEndDate = "";
				strDrill = "No";
				
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				
				strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
						+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate = strEndDate + " " + strFndEdHr + ":" + strFndEdMinu;
						
				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strEndDate, strEveName1, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName1,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on View link associated with event EVE 	
		  Expected Result:'Event Status' screen is displayed along with the Resource RS under RT and NST , MST, SST, TST status types. 
		*/	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.navToEventStatusScreenByViewLink(selenium, strEveName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strMultiStatType, strNumStatType,
						strSatuStatType, strTxtStatType };
				strFuncResult = objEventList.checkOnlyDetailsInEventBanner(
						selenium, strEveName1, strResrctTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		* STEP :
		  Action:Click on View History link associated with inactive event EVE1. 
		  Expected Result:Event History Pop up window is displayed with details of event EVE1
						Event Name
						(< Created user full name>)
						Event Name
						(< Updated user full name>) 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.navToEventListNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strUser=""+strAdminFullName+"";
				strFuncResult = objEventList.navToEventHistoryVerifyNew(selenium,
						strEveName2, strInfo, strUser);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
		/*
		* STEP :
		  Action:Navigate to Event >> Event Management. 
		  Expected Result:Event EVE is listed on the 'Event List' screen along with the active events 
		  and ended events(ended in last 24 hours)created in the region under the columns:
				a. Action
				b. Icon
				c. Status
				d. Start
				e. End
				f. Title
				g. Drill
				h. Template
				i. Information
				'Create New Event' button is available for user 'U1'.
				Under Action column 'Edit and End' links are displayed for active events.
				' View History' is displayed for ended events.
				Under Status column 'Ongoing' is displayed for active events.
				'Ended' is displayed for ended events. 
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID=propElementDetails.getProperty("Event.CreateEvent");
                strFuncResult=objGeneral.CheckForElements(selenium, strElementID);
				log4j.info("'Create New Event' button is available for user '"+strUserName_1+"'. ");			
			} catch (AssertionError ae) {
				log4j.info("'Create New Event' button is NOT available for user '"+strUserName_1+"'.");
				strFuncResult = "'Create New Event' button is NOT available for user '"+strUserName_1+"'.";
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				strAction = "View�History";
				strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
						strFILE_PATH);
				strStatus = "Ended";
				strStartDate1 = "";
				strEndDate1 = "";
				strDrill = "No";
				
				strStartDate1 = dts.converDateFormat(strFndStYear1 + strFndStMnth1
						+ strFndStDay1, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate1 = strStartDate1 + " " + strFndStHr1 + ":"
						+ strFndStMinu1;
				
				strEndDate1 = dts.converDateFormat(strFndEdYear1 + strFndEdMnth1
						+ strFndEdDay1, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate1 = strEndDate1 + " " + strFndEdHr1 + ":" + strFndEdMinu1;
				
				String[] strData = { strAction, strIcon, strStatus,
						strStartDate1, strStartDate1, strEveName2, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName2,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				strAction = "Edit��|��End";
				strIcon = rdExcel.readInfoExcel("Event Temp Data", 4, 6,
						strFILE_PATH);
				strStatus = "Ongoing";
				strStartDate = "";
				strEndDate = "";
				strDrill = "No";
				
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				
				strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
						+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
				strEndDate = strEndDate + " " + strFndEdHr + ":" + strFndEdMinu;
				
				String[] strData = { strAction, strIcon, strStatus,
						strStartDate, strEndDate, strEveName1, strDrill,
						strTempName, strInfo };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {
					strFuncResult = objEventList.checkDataInEventListTable(
							selenium, strHeader[intRec], strEveName1,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
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
			gstrTCID = "92694"; // Test Case Id
			gstrTO = "Verify that user with right to maintain events can view 'Event List' screen.";// TO
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
	// start//testFTS118238//
	/***************************************************************************
	'Description :Verify that address fields are mandatory if 'Mandatory address' 
			is selected for an event template after the future event was created
	'Arguments	 :None
	'Returns	 :None
	'Date		 :7/30/2013
	'Author		 :QSG
	'---------------------------------------------------------------------------
	'Modified Date				                                     Modified By
	'Date					                                         Name
	****************************************************************************/

	@Test
	public void testFTS118238() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		EventSetup objEventSetup = new EventSetup();
		General objGeneral=new General();
		try {
			gstrTCID = "118238"; // Test Case Id
			gstrTO = "	Verify that address fields are mandatory if 'Mandatory address' is " +
					"selected for an event template after the future event was created";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ET
			String strTempName1 = "ET1_" + strTimeText;
			String strTempDef = "Automation";

			String strStartMinute = "";
			String strStartHour = "";
			String strStartYear = "";
			String strStartDay = "";
			String strStartMonth = "";

			String strApplTime = "";
			String strCurentDat[] = new String[5];

			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			String strInfo="Information";
			String strCity = "Banglore";
			String strState = "Kansas";
			String strCounty = "Allen County";
	
			
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Event template Creation
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
				strFuncResult = objEventSetup.CreateETByMandFields(seleniumPrecondition,
						strTempName1, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.saveAndNavToEvenTNotPreferencesPage(seleniumPrecondition,
								strTempName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(
						seleniumPrecondition, strTempName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event Creation
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName1, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				// Fetch Application time
				strCurentDat = objGeneral.getSnapTime(seleniumPrecondition);
				java.util.Calendar cal = java.util.Calendar.getInstance();
				int year = cal.get(java.util.Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year + " " + strCurentDat[2]
						+ ":" + strCurentDat[3], "dd-MMM-yyyy HH:mm",
						"MMM/d/yyyy HH:mm");
				System.out.println(strApplTime);

				String strSStartTime[] = dts.addTimetoExisting(strApplTime,
						5, "MMM/d/yyyy HH:mm").split("/");
				strStartDay = strSStartTime[1];
				strStartMonth = strSStartTime[0];

				strSStartTime = strSStartTime[2].split(" ");
				strStartYear = strSStartTime[0];
				strSStartTime = strSStartTime[1].split(":");

				strStartMinute = strSStartTime[1];
				strStartHour = strSStartTime[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.selectStartAndEndDate(seleniumPrecondition,
						strEveName, strStartMonth, strStartDay, strStartYear,
						strStartHour, strStartMinute, "", "", "", "", "", true,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savEvent(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~Test Case  " + gstrTCID + " EXECUTION Starts~~~~~");
			
			try {
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
			//Edit ET
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium, strTempName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.selAndDeselMandChkBoxForEventTemp(selenium, strTempName1, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			int intCnt=0;
			while((selenium.getText("//div[@id='mainContainer']/table/tbody/tr/td[text()" +
					"='"+strEveName+"']/parent::tr/td[3]").equals("Ongoing"))==true && intCnt<120){
				Thread.sleep(3000);
				intCnt++;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navEditEventPage(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkAddressFieldsMandForEvent(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.fillonlyAddressFieldsOfEvent(selenium, strCity, strState, 
						strCounty, false, strEveName, false);
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
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118238"; // Test Case Id
			gstrTO = "	Verify that address fields are mandatory if 'Mandatory address' " +
					"is selected for an event template after the future event was created";// TO
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
	// end//testFTS118238//
	// starts//testFTS117339//
	/***************************************************************************************
	'Description :Create an event selecting to start immediately and to end after a certain
	'			 number of hours and verify that user who created the event is displayed in 
	'				the Event notification.
	'Arguments	 :None
	'Returns	 :None
	'Date		 :29/April/2013
	'Author		 :QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                                 Modified By
	'Date					                                                      Name
	*****************************************************************************************/

	@Test
	public void testFTS117339() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral = new General();
		EventNotification objEventNotification = new EventNotification();
		try {
			gstrTCID = "117339"; // Test Case Id
			gstrTO = "Create an event selecting to start immediate and " +
					"to end never and verify that user who created the " +
					"event is displayed in the Event notification. ";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strFulUserName = rdExcel.readData("Login", 3, 3);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// ET
			String strTempName1 = "ET1_" + strTimeText;
			String strTempDef = "Automation";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			String strEveName2 = "AutoEve_2" + strTimeText;

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			String strMsgBodyEmail = "";
			String strMsgBodyPager = "";
			String strSubjName = "";

			int intEMailRes = 0;
			int intPagerRes = 0;
			int intAllTrue=0;

			log4j.info("~~~~~PRECONDITION " + gstrTCID + " EXECUTION STATRTS~~~~~");

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
				blnLogin = true;
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);
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

			// Event template Creation
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
				strFuncResult = objEventSetup
						.CreateETByMandFieldsByAdminAndUser(seleniumPrecondition,
								strTempName1, strTempDef, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventNotification.selectEventNofifForUser(
						seleniumPrecondition, strUsrFulName_1, strTempName1, true, true,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			//Login as U1
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
				log4j.info("==========Loged as New User,Create Event and verifying notification=======");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName1, strEveName, strTempDef, false);
				if (selenium
						.isChecked("css=input[name='eventEndType'][value='NEVER']") == false) {
					selenium
					.click("css=input[name='eventEndType'][value='NEVER']");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savEvent(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(60000);
				strSubjName = strEveName;
				strMsgBodyEmail = "Event Notice for " + strUsrFulName_1 + ": \n"
						+ strTempDef + "\nFrom: " + strUsrFulName_1 + "\nRegion: "
						+ strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required." + "\n"
						+ propEnvDetails.getProperty("MailURL");
				strMsgBodyPager = strTempDef + "\nFrom: " + strUsrFulName_1
						+ "\nRegion: " + strRegn;

				strFuncResult = objGeneral.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			


			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objGeneral.verifyEmail(selenium, strFrom,
						strTo, strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");
					if (strMsg.equals(strMsgBodyEmail)) {
						intEMailRes++;
						log4j.info("Email body is displayed correctly");
					} else if (strMsg.equals(strMsgBodyPager)) {
						intPagerRes++;
						log4j.info("Pager body is displayed correctly");
					} else {
						log4j.info("Mail body is NOT displayed correctly");
						strFuncResult = "Mail body is NOT displayed correctly";
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				selenium.selectFrame("relative=up");
				selenium.selectFrame("horde_main");
				// click on Back to Inbox
				selenium.click("link=Back to Inbox");
				selenium.waitForPageToLoad("90000");

				strFuncResult = objGeneral.verifyEmail(selenium, strFrom,
						strTo, strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");
					if (strMsg.equals(strMsgBodyEmail)) {
						intEMailRes++;
						log4j.info("Email body is displayed correctly");
					} else if (strMsg.equals(strMsgBodyPager)) {
						intPagerRes++;
						log4j.info("Pager body is displayed correctly");
					} else {
						log4j.info("Mail body is NOT displayed correctly");
						strFuncResult = "Mail body is NOT displayed correctly";
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				selenium.selectFrame("relative=up");
				selenium.selectFrame("horde_main");
				// click on Back to Inbox
				selenium.click("link=Back to Inbox");
				selenium.waitForPageToLoad("90000");

				strFuncResult = objGeneral.verifyEmail(selenium, strFrom,
						strTo, strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");
					if (strMsg.equals(strMsgBodyEmail)) {
						intEMailRes++;
						log4j.info("Email body is displayed correctly");
					} else if (strMsg.equals(strMsgBodyPager)) {
						intPagerRes++;
						log4j.info("Pager body is displayed correctly");
					} else {
						log4j.info("Mail body is NOT displayed correctly");
						strFuncResult = "Mail body is NOT displayed correctly";
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// check Email, pager notification
				if (intEMailRes == 2 && intPagerRes == 1) {
					intAllTrue++;
				}

				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad("90000");
				selenium.close();

				selenium.selectWindow("");
				Thread.sleep(30000);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Login as RegAdmin Create Event and verify notification
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
				log4j.info("==========Loged as RegAdmin,Create Event and verifying notification=======");
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName1, strEveName2, strTempDef, false);
				if (selenium
						.isChecked("css=input[name='eventEndType'][value='NEVER']") == false) {
					selenium
					.click("css=input[name='eventEndType'][value='NEVER']");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savEvent(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(60000);
				strSubjName = strEveName2;
				strMsgBodyEmail = "Event Notice for " + strUsrFulName_1 + ": \n"
						+ strTempDef + "\nFrom: " + strFulUserName + "\nRegion: "
						+ strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required." + "\n"
						+ propEnvDetails.getProperty("MailURL");
				strMsgBodyPager = strTempDef + "\nFrom: " + strFulUserName
						+ "\nRegion: " + strRegn;

				strFuncResult = objGeneral.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objGeneral.verifyEmail(selenium, strFrom,
						strTo, strSubjName);
				intEMailRes=0;
				intPagerRes=0;
				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");
					if (strMsg.equals(strMsgBodyEmail)) {
						intEMailRes++;
						log4j.info("Email body is displayed correctly");
					} else if (strMsg.equals(strMsgBodyPager)) {
						intPagerRes++;
						log4j.info("Pager body is displayed correctly");
					} else {
						log4j.info("Mail body is NOT displayed correctly");
						strFuncResult = "Mail body is NOT displayed correctly";
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				selenium.selectFrame("relative=up");
				selenium.selectFrame("horde_main");
				// click on Back to Inbox
				selenium.click("link=Back to Inbox");
				selenium.waitForPageToLoad("90000");

				strFuncResult = objGeneral.verifyEmail(selenium, strFrom,
						strTo, strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");
					if (strMsg.equals(strMsgBodyEmail)) {
						intEMailRes++;
						log4j.info("Email body is displayed correctly");
					} else if (strMsg.equals(strMsgBodyPager)) {
						intPagerRes++;
						log4j.info("Pager body is displayed correctly");
					} else {
						log4j.info("Mail body is NOT displayed correctly");
						strFuncResult = "Mail body is NOT displayed correctly";
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				selenium.selectFrame("relative=up");
				selenium.selectFrame("horde_main");
				// click on Back to Inbox
				selenium.click("link=Back to Inbox");
				selenium.waitForPageToLoad("90000");

				strFuncResult = objGeneral.verifyEmail(selenium, strFrom,
						strTo, strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");
					if (strMsg.equals(strMsgBodyEmail)) {
						intEMailRes++;
						log4j.info("Email body is displayed correctly");
					} else if (strMsg.equals(strMsgBodyPager)) {
						intPagerRes++;
						log4j.info("Pager body is displayed correctly");
					} else {
						log4j.info("Mail body is NOT displayed correctly");
						strFuncResult = "Mail body is NOT displayed correctly";
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// check Email, pager notification
				if (intEMailRes == 2 && intPagerRes == 1) {
					intAllTrue++;
				}

				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad("90000");
				selenium.close();

				selenium.selectWindow("");
				Thread.sleep(30000);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info(intAllTrue);
				if(intAllTrue == 2){
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}
		
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "117339";
			gstrTO = "Create an event selecting to start immediate and to " +
					"end never and verify that user who created the event " +
					"is displayed in the Event notification. ";
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

	//start//testFTS92693//
	/***************************************************************
	'Description		:Verify that user without any rights can view 'Event List' screen.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/25/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/

	@Test
	public void testFTS92693() throws Exception {
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		EventList objEventList = new EventList();
		General objGeneral = new General();
		Roles objRoles = new Roles();
		boolean blnLogin = false;
		try {
			gstrTCID = "92693"; // Test Case Id
			gstrTO = " Verify that user without any rights can view 'Event List' screen.";// Test// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strAdminFullName = rdExcel.readData("Login", 4, 1);	
			// ST
			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[1];
			// RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strResVal = "";
			//Role
			String strRoleName = "Rol_1" + strTimeText;
			String strRoleValue[] = new String[1];
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			// ET
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";
			// EVE
			String strEveName = "Event" + System.currentTimeMillis();
			String strEveName1 = "Event2" + System.currentTimeMillis();
			String strInfo = "auto event";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			/*
			 * STEP : Action:Preconditions:
			 * 
			 * 1. User 'U1' is created with default rights and has a role to
			 * view associated status types.
			 * 
			 * 2. Status Types NST,MST,SST,TST are created
			 * 
			 * 3. Resource type RT is created selecting status types NST,MST,SST
			 * and TST.
			 * 
			 * 4. Resource RS is created under resource type RT.
			 * 
			 * 5. Event template ET is created selecting RT and all status
			 * types.
			 * 
			 * 6. Active event EVE is created selecting ET.
			 * 
			 * 7. Ended event EVE1 is present in the region. Expected Result:No
			 * Expected Result
			 */
			// 558302

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Status types 'MST','NST','SST','TST' are created.
			 */
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDeSelEventOnly(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, strTxtStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strSatuStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
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
			/*
			 * 2. Resource type RT is created selecting all the four status
			 * types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 0; intST < strSTvalue.length; intST++) {
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
			/*
			 * 3. Resource RS is created under RT
			 */
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
			
			// Creating Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				String[][] strRoleRightss = {};
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRightss,
						updateRightValue, true, updateRightValue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4. Event template ET is created providing mandatory data and
			 * selecting the check boxes E-mail, Pager and Web for user 'U1' and
			 * selecting status types MST,NST,TST,SST,resource type RT.
			 */

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

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
			/*
			 * 3. User 'U1' is created providing following rights.:
			 * 
			 * a. 'Maintain Events' right b. View and Update right on resource
			 * 'RS'
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
						seleniumPrecondition, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, false, false, true);
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
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventWitManyRS(
						seleniumPrecondition, strTempName, strRSValue,
						strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventWitManyRS(
						seleniumPrecondition, strTempName, strRSValue,
						strEveName1, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.endEvent(seleniumPrecondition,
						strEveName1);

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

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as user 'U1' Expected Result:Region default
			 * screen is displayed.
			 */
			// 558304

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event List. Expected
			 * Result:Event EVE is listed on the 'Event List' screen along with
			 * the other active events and ended events(ended in last 24
			 * hours)created in the region under the columns:
			 * 
			 * a. Action b. Icon c. Status d. Start e. End f. Title g. Drill h.
			 * Template i. Information
			 * 
			 * 'Create New Event' button is not available for user 'U1'.
			 * 
			 * Under Action column 'View' link is available for active events.
			 * 
			 * ' View History' link is available for ended events.
			 */
			// 558312

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventList.navToEventListNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.checkEventHeadersInEventMgmt(
						selenium, "Action", "1");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.checkEventHeadersInEventMgmt(
						selenium, "Icon", "2");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.checkEventHeadersInEventMgmt(
						selenium, "Status", "3");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.checkEventHeadersInEventMgmt(
						selenium, "Start", "4");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.checkEventHeadersInEventMgmt(
						selenium, "End", "5");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.checkEventHeadersInEventMgmt(
						selenium, "Title", "6");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.checkEventHeadersInEventMgmt(
						selenium, "Drill", "7");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.checkEventHeadersInEventMgmt(
						selenium, "Template", "8");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.checkEventHeadersInEventMgmt(
						selenium, "Information", "9");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table/thead/tr/th[text()='Action']/following::tbody/tr/"
						+ "td[6][text()='"
						+ strEveName
						+ "']/parent::tr/td/a[text()='View']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("'View' link is available for active events. ");
				} else {
					strFuncResult = "'View' link is NOT available for active events. ";
					log4j
							.info(" 'View' link is NOT available for active events. ");
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table/thead/tr/th[text()='Action']/following::tbody/tr"
						+ "/td[6][text()='"
						+ strEveName1
						+ "']/parent::tr/td/a[contains(text(),'History')]";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j
							.info("' View History' link is available for ended events.  ");
				} else {
					strFuncResult = "' View History' link is NOT available for ended events.  ";
					log4j
							.info(" ' View History' link is NOT available for ended events.  ");
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on View link associated with event EVE
			 * Expected Result:'Event Status' screen is displayed along with the
			 * Resource RS under RT and NST , MST, SST, TST status types.
			 */
			// 558336
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.navToEventStatusScreenByViewLink(
						selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strMultiStatType, strNumStatType,
						strSatuStatType, strTxtStatType };
				strFuncResult = objEventList.checkOnlyDetailsInEventBanner(
						selenium, strEveName, strResrctTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	

			/*
			 * STEP : Action:Navigate to Event >> Event List Expected
			 * Result:'Event List' screen is displayed.
			 */
			// 613214
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.navToEventListNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP : Action:Click on 'View History' link associated with ended
			 * event EVE1. Expected Result:Event History thick box is displayed
			 * with details of event EVE1.
			 * 
			 * Event Name <Event Information> (< Created user full name>)
			 * 
			 * Event Name <Event Information> (< Updated user full name>)
			 * 
			 * 'Close' and 'Print' links are available.
			 */
			// 558361
			
			try {
				assertEquals("", strFuncResult);
				String strUser = ""+strAdminFullName+"";
				strFuncResult = objEventList.navToEventHistoryVerifyNew(selenium,
						strEveName1, strInfo, strUser);
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
			gstrTCID = "FTS-92693";
			gstrTO = "Verify that user without any rights can view 'Event List' screen.";
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

	// end//testFTS92693//
}
