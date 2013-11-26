package com.qsgsoft.EMResource.features;

import java.util.Date;
import java.util.Properties;
import static org.junit.Assert.*;

import org.junit.*;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/*************************************************************
' Description       :This class includes requirement testcases
' Requirement Group :Smoke Test Suite 
' Requirement       :Multi Region Features
' Date		        :08-May-2012
' Author	        :QSG
'--------------------------------------------------------------
' Modified Date                                     Modified By
' <Date>                           	                <Name>
'***************************************************************/

public class SmokeMultiRegionFeatures {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.SmokeMultiRegionFeatures");
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
	String gstrTimeOut;

	Selenium selenium, seleniumPrecondition;

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
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

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
	
	
	 // start//testBQS117549//
	/**************************************************************************************
	 * 'Description  :Access to 2 Regions: Verify that a user with appropriate right in one
	 *                region can view resources and status types from another region on 
	 *                different view screens. 
	 * 'Precondition :Regions RG1 and RG2 do not have other region view agreement. 
	 * 'Arguments    :None 
	 * 'Returns      :None
	 * 'Date         :5/10/2013 'Author :QSG
	 * '-----------------------------------------------------------------------------------
	 * 'Modified Date                                                               Date 
	 *  Modified By                                                                 'Name
	 **************************************************************************************/

	@Test
	public void testSmoke117549() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Regions objRegion = new Regions();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "117549"; // Test Case Id
			gstrTO = " Access to 2 Regions: Verify that a user with appropriate right"
					+ "in one region can view resources and status types from another "
					+ "region on different view screens.";//Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTmText = dts.getCurrentDate("HHmm");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);

			String strRegn1 = rdExcel.readData("Login", 3, 4);
			String strRegn2 = rdExcel.readData("Regions", 3, 5);
			String strRegionValue[] = new String[2];

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// User
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName;

			// Resource for region 1
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";

			// Role for region 1
			String strRoleName = "AutoRol_1" + strTimeText;
			String strRoleValue[] = new String[1];

			// Status Types for region 1
			String strStatTypDefn = "Automation";
			String strStatTypeColor = "Black";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			// Shared based status type
			String statsNumTypeName = "sNST" + strTimeText;
			String statsEventNumTypeName = "sENST" + strTimeText;
			String statsMultiTypeName = "sMST" + strTimeText;
			String statsEventMultiTypeName = "sEMST" + strTimeText;
			String statsTextTypeName = "sTST" + strTimeText;
			String statsEventTextTypeName = "sETST" + strTimeText;
			String statsSaturtnTypeName = "sSST" + strTimeText;
			String statsEventSaturtnTypeName = "sESST" + strTimeText;

			String str_sharedStatusName1 = "sSa" + strTimeText;
			String str_sharedStatusName2 = "sSb" + strTimeText;
			String str_sharedStatusName3 = "sSc" + strTimeText;
			String str_sharedStatusName4 = "sSd" + strTimeText;

			String str_sharedStatusValue[] = new String[4];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";
			str_sharedStatusValue[2] = "";
			str_sharedStatusValue[3] = "";

			String str_sharedStatusTypeValues[] = new String[8];

			// Role based status type
			String statrNumTypeName = "rNST" + strTimeText;
			String statrEventNumTypeName = "rENST" + strTimeText;
			String statrMultiTypeName = "rMST" + strTimeText;
			String statrEventMultiTypeName = "rEMST" + strTimeText;
			String statrTextTypeName = "rTST" + strTimeText;
			String statrEventTextTypeName = "rETST" + strTimeText;
			String statrSaturtnTypeName = "rSST" + strTimeText;
			String statrEventSaturtnTypeName = "rESST" + strTimeText;

			String str_roleStatusName1 = "rSa" + strTimeText;
			String str_roleStatusName2 = "rSb" + strTimeText;
			String str_roleStatusName3 = "rSc" + strTimeText;
			String str_roleStatusName4 = "rSd" + strTimeText;

			String str_roleStatusValue[] = new String[4];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";
			str_roleStatusValue[2] = "";
			str_roleStatusValue[3] = "";

			String str_roleStatusTypeValues[] = new String[8];

			// Private based Status type
			String statpNumTypeName = "pNST" + strTimeText;
			String statpEventNumTypeName = "pENST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpEventMultiTypeName = "pEMST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpEventTextTypeName = "pETST" + strTimeText;
			String statpSaturtnTypeName = "pSST" + strTimeText;
			String statpEventSaturtnTypeName = "pESST" + strTimeText;

			String str_privateStatusName1 = "pSa" + strTimeText;
			String str_privateStatusName2 = "pSb" + strTimeText;
			String str_privateStatusName3 = "pSc" + strTimeText;
			String str_privateStatusName4 = "pSd" + strTimeText;

			String str_privateStatusValue[] = new String[4];
			str_privateStatusValue[0] = "";
			str_privateStatusValue[1] = "";
			str_privateStatusValue[2] = "";
			str_privateStatusValue[3] = "";

			String str_privateStatusTypeValues[] = new String[8];

			// Data to create section
			String strSection = "SEC_1" + strTimeText;
			String strSectionValue = "";

			// Resource Type for region 1
			String strResrceTypName1 = "RT1" + strTimeText;
			String strResrceTypVal[] = new String[1];

			String strArStatType1[] = { statsNumTypeName,
					statsEventNumTypeName, statsMultiTypeName,
					statsEventMultiTypeName, statsTextTypeName,
					statsEventTextTypeName, statsSaturtnTypeName,
					statsEventSaturtnTypeName, statrNumTypeName,
					statrEventNumTypeName, statrMultiTypeName,
					statrEventMultiTypeName, statrTextTypeName,
					statrEventTextTypeName, statrSaturtnTypeName,
					statrEventSaturtnTypeName, statpNumTypeName,
					statpEventNumTypeName, statpMultiTypeName,
					statpEventMultiTypeName, statpTextTypeName,
					statpEventTextTypeName, statpSaturtnTypeName,
					statpEventSaturtnTypeName };

			// Resource for Region 1
			String strResourceA = "AutoRs_A" + strTimeText;
			String strResourceB = "AutoRs_B" + strTimeText;
			String strRSValue[] = new String[3];
			String strLongitude = "";
			String strLatitude = "";

			// Event Template
			String strTempName = "Autotmp1" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";

			// Event
			String strEveName = "AutoEve_1" + strTimeText;

			// Serach Resource
			String strCategory = "(Any)";
			String strCityZipCd = "";

	    log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");

		/*
		 * STEP 1:Login as RegAdmin to RG2 and on Setup>>Users screen,
		 * create a new user U1 selecting the right "View Custom View" right
		 * under 'Advanced Options' and provide access to region RG1 (do not
		 * select RG1 for 'Other Region Views' field) .
		 */

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
						seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fetch region values
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegionValue[0] = objRegion.fetchRegionValue(
						seleniumPrecondition, strRegn1);
				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strRegionValue[1] = objRegion.fetchRegionValue(
						seleniumPrecondition, strRegn2);
				if (strRegionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
				
		   log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");
				
		   log4j.info("~~~~~TEST CASE " + gstrTCID+ " EXECUTION STATRTS~~~~~");
				

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
				strFuncResult = objCreateUsers
						.navToCreateUserPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.CustomView"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUser(
						seleniumPrecondition, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRegionValues = { strRegionValue[0] };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName, strRegionValues);
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
			 * STEP 3:Login as RegAdmin to RG1 and on Setup>>Status Types
			 * screen, create the following different types of status types:
			 * Shared: NST1 (Number) ENST1 (Event related Number) MST1 (Multi)
			 * EMST1 (Event related Multi) TST1 (Text) ETST1 (Event related
			 * Text) SST1 (Saturation score) and ESST1 (Event related Saturation
			 * score) Role-based: rNST1 (Number) rENST1 (Event related Number)
			 * rMST1 (Multi) rEMST1 (Event related Multi) rTST1 (Text) rETST1
			 * (Event related Text) rSST1 (Saturation score) rESST1 (Event
			 * related Saturation score) Private: pNST (Number) pENST1 (Event
			 * related Number) pMST1 (Multi) pEMST1 (Event related Multi) pTST1
			 * (Text) pETST1 (Event related Text) pSST1 (Saturation score)
			 * pESST1 (Event related Saturation score) All the above status
			 * types are under a section SEC1.
			 */

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
						seleniumPrecondition, strRegn1);
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

			// Shared based Status Type

			/*
			 * Number
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statsNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statsNumTypeName, strVisibilty,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsNumTypeName);
				if (str_sharedStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event Related Number

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue,
						statsEventNumTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statsEventNumTypeName,
						strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsEventNumTypeName);
				if (str_sharedStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Multi

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statsMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statsMultiTypeName, strVisibilty,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsMultiTypeName);
				if (str_sharedStatusTypeValues[2].compareTo("") != 0) {
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
						seleniumPrecondition, statsMultiTypeName,
						str_sharedStatusName1, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName,
						str_sharedStatusName2, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statsMultiTypeName, str_sharedStatusName1);
				if (str_sharedStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statsMultiTypeName, str_sharedStatusName2);
				if (str_sharedStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Event Related Multi

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strMSTValue,
						statsEventMultiTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statsEventMultiTypeName,
						strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsEventMultiTypeName);
				if (str_sharedStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName,
						str_sharedStatusName3, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName,
						str_sharedStatusName4, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[2] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statsMultiTypeName, str_sharedStatusName3);
				if (str_sharedStatusValue[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[3] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statsMultiTypeName, str_sharedStatusName4);
				if (str_sharedStatusValue[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Text

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statsTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statsTextTypeName, strVisibilty,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsTextTypeName);
				if (str_sharedStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event Related Text

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue,
						statsEventTextTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statsEventTextTypeName,
						strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsEventTextTypeName);
				if (str_sharedStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue,
						statsSaturtnTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statsSaturtnTypeName,
						strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[6] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsSaturtnTypeName);
				if (str_sharedStatusTypeValues[6].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event related Saturation score

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strSSTValue,
						statsEventSaturtnTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statsEventSaturtnTypeName,
						strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[7] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsEventSaturtnTypeName);
				if (str_sharedStatusTypeValues[7].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role Based Status Type

			// Number

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

			// Event Related Number

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue,
						statrEventNumTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrEventNumTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Multi
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
				str_roleStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (str_roleStatusTypeValues[2].compareTo("") != 0) {
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

			// Event Related Multi

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strMSTValue,
						statrEventMultiTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrEventMultiTypeName);
				if (str_roleStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName3, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName4, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[2] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName3);
				if (str_roleStatusValue[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[3] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName4);
				if (str_roleStatusValue[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Text

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
				str_roleStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTextTypeName);
				if (str_roleStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event Related text

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue,
						statrEventTextTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrEventTextTypeName);
				if (str_roleStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue,
						statrSaturtnTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[6] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturtnTypeName);
				if (str_roleStatusTypeValues[6].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event related Saturation Score

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strSSTValue,
						statrEventSaturtnTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[7] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrEventSaturtnTypeName);
				if (str_roleStatusTypeValues[7].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Private based status type

			// Number
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
				str_privateStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpNumTypeName);
				if (str_privateStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event Related Number

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue,
						statpEventNumTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "PRIVATE";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statpEventNumTypeName,
						strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpEventNumTypeName);
				if (str_privateStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Multi

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpMultiTypeName);
				if (str_privateStatusTypeValues[2].compareTo("") != 0) {
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
						seleniumPrecondition, statpMultiTypeName,
						str_privateStatusName1, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName,
						str_privateStatusName2, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statpMultiTypeName, str_privateStatusName1);
				if (str_privateStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statpMultiTypeName, str_privateStatusName2);
				if (str_privateStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Event Related Multi

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strMSTValue,
						statpEventMultiTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "PRIVATE";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statpEventMultiTypeName,
						strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpEventMultiTypeName);
				if (str_privateStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statpEventMultiTypeName,
						str_privateStatusName3, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpEventMultiTypeName,
						str_privateStatusName4, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[2] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statpEventMultiTypeName, str_privateStatusName3);
				if (str_privateStatusValue[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[3] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statpEventMultiTypeName, str_privateStatusName4);
				if (str_privateStatusValue[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Text

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statpTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpTextTypeName);
				if (str_privateStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event Related Text

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue,
						statpEventTextTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "PRIVATE";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statpEventTextTypeName,
						strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpEventTextTypeName);
				if (str_privateStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statpSaturtnTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[6] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpSaturtnTypeName);
				if (str_privateStatusTypeValues[6].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event related Saturation Score

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strSSTValue,
						statpEventSaturtnTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "PRIVATE";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statpEventSaturtnTypeName,
						strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[7] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpEventSaturtnTypeName);
				if (str_privateStatusTypeValues[7].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// All the above status types are under a section SEC1.

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumPrecondition, strArStatType1, strSection, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
						strSection);
				if (strSectionValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
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
			 * STEP 4:From Setup>>Resource Types screen, create a resource type
			 * RT1 selecting all the above status type.
			 */

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						selenium, strResrceTypName1,
						"css=input[name='statusTypeID'][value='"
								+ str_privateStatusTypeValues[0] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[1] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[2] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[3] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[4] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[5] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[6] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[7] + "']");

				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[0] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[1] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[2] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[3] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[4] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[5] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[6] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[7] + "']");

				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[0] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[1] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[2] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[3] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[4] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[5] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[6] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[7] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrceTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResrceTypVal[0] = objResourceTypes.fetchRTValueInRTList(
						selenium, strResrceTypName1);

				if (strResrceTypVal[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP 5: From Setup>>Resources screen, create following resources
		 * under RT1: 1. RS1: selecting the option
		 * "Share with Other Regions" and providing valid address. 2. RS2:
		 * WITHOUT selecting the option "Share with Other Regions" and
		 * providing valid address.
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadresSharWitRgn(
						selenium, strResourceA, strAbbrv, strResrceTypName1,
						true, strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(selenium,
						strResourceA);
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
				strFuncResult = objRs.navToEditResourcePage(selenium,
						strResourceA);
				int intCnt = 0;
				do {
					try {

						assertTrue(selenium.isElementPresent("id=latitude"));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);
				strLatitude = selenium.getValue("id=latitude");
				strLongitude = selenium.getValue("id=longitude");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResourceB, strAbbrv, strResrceTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objRs.fetchResValueInResList(selenium,
						strResourceB);

				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 6: From Event>>Event Setup screen, create an event template
			 * ET1 selecting RT1 and any 20 above status types.
			 */

			// EVENT Template

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
				String[] strResTypeValues = { strResrceTypVal[0] };
				String[] strStatusTypeval = { str_sharedStatusTypeValues[0],
						str_sharedStatusTypeValues[1],
						str_sharedStatusTypeValues[2],
						str_sharedStatusTypeValues[3],
						str_sharedStatusTypeValues[4],
						str_sharedStatusTypeValues[5],
						str_sharedStatusTypeValues[6],
						str_sharedStatusTypeValues[7],

						str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],
						str_roleStatusTypeValues[4],
						str_roleStatusTypeValues[5],
						str_roleStatusTypeValues[6],
						str_roleStatusTypeValues[7],

						str_privateStatusTypeValues[0],
						str_privateStatusTypeValues[1],
						str_privateStatusTypeValues[2],
						str_privateStatusTypeValues[3], };

				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeValues, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 7:From Event>>Event Management screen, create an event EVE
			 * under template ET selecting RS1 and RS2
			 */

			// Event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResourceA, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResourceB, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 8:From Event>>Event Management screen, edit event EVE and
			 * add remaining status types to the running event
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								str_privateStatusTypeValues[4], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								str_privateStatusTypeValues[5], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								str_privateStatusTypeValues[6], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								str_privateStatusTypeValues[7], true, false);
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

			// role to view and update all the above status types

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strSTUpdateValue = {

				{ str_privateStatusTypeValues[0], "true" },
						{ str_privateStatusTypeValues[1], "true" },
						{ str_privateStatusTypeValues[2], "true" },
						{ str_privateStatusTypeValues[3], "true" },
						{ str_privateStatusTypeValues[4], "true" },
						{ str_privateStatusTypeValues[5], "true" },
						{ str_privateStatusTypeValues[6], "true" },
						{ str_privateStatusTypeValues[7], "true" },

						{ str_roleStatusTypeValues[0], "true" },
						{ str_roleStatusTypeValues[1], "true" },
						{ str_roleStatusTypeValues[2], "true" },
						{ str_roleStatusTypeValues[3], "true" },
						{ str_roleStatusTypeValues[4], "true" },
						{ str_roleStatusTypeValues[5], "true" },
						{ str_roleStatusTypeValues[6], "true" },
						{ str_roleStatusTypeValues[7], "true" },

						{ str_sharedStatusTypeValues[0], "true" },
						{ str_sharedStatusTypeValues[1], "true" },
						{ str_sharedStatusTypeValues[2], "true" },
						{ str_sharedStatusTypeValues[3], "true" },
						{ str_sharedStatusTypeValues[4], "true" },
						{ str_sharedStatusTypeValues[5], "true" },
						{ str_sharedStatusTypeValues[6], "true" },
						{ str_sharedStatusTypeValues[7], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						selenium, false, false, strSTUpdateValue,
						strSTUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(selenium,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(selenium,
						strRoleName);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
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
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResourceA, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResourceB, strRSValue[1], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.CustomView"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);
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
		 * STEP 9:Login as user U1 to region RG2 and click on 'Search' link
		 * (at the top right corner of the screen).
		 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Click on search

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 10:Provide search criteria as 'RS' and click on 'Search'
			 */

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResourceA, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResourceB, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 12:Click on 'Options', select all the 12 status types
			 * displayed NST1, rNST1, pNST1,MST1, rMST1, pMST1, TST1, rTST1,
			 * pTST1, SST1, rSST1, pSST1 and select all check boxes under
			 * 'Options' section and click on 'Save'
			 */

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statsNumTypeName,
						statsMultiTypeName, statsTextTypeName,
						statsSaturtnTypeName, statrNumTypeName,
						statrMultiTypeName, statrTextTypeName,
						statrSaturtnTypeName, statpNumTypeName,
						statpMultiTypeName, statpTextTypeName,
						statpSaturtnTypeName };

				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statsNumTypeName,
						statsMultiTypeName, statsTextTypeName,
						statsSaturtnTypeName, statrNumTypeName,
						statrMultiTypeName, statrTextTypeName,
						statrSaturtnTypeName, statpNumTypeName,
						statpMultiTypeName, statpTextTypeName,
						statpSaturtnTypeName };
				String[] strResources = { strResourceA, strResourceB };

				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrceTypName1, strResources,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP 13:Click on the resource RS1.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResourceA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strResouceData = {
						{ "Type:", strResrceTypName1 },
						{ "Address:", "AL " },
						{ "County:", "Autauga County" },
						{ "Lat/Longitude:", strLatitude + " / " + strLongitude },
						{ "EMResource/AHA ID:", strRSValue[0] },
						{ "Contact:", strContFName + " " + strContLName } };

				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strResourceA, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection, strSectionValue, strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 14:Click on Event banner 'EVE'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.checkInEventBannerNew(selenium,
						strEveName, strResrceTypName1, strResourceA,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.chkResourceInEventBanner(selenium,
						strResourceB, true);
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
			gstrTCID = "BQS-117549";
			gstrTO = "Access to 2 Regions: Verify that a user with appropriate right in "
					+ "one region can view resources and status types from another region "
					+ "on different view screens.";
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
}
