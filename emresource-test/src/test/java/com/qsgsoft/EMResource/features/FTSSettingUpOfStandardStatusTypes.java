package com.qsgsoft.EMResource.features;
import static org.junit.Assert.*;

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
' Description		:This class contains test cases from 'Req8613'  requirement
' Requirement		:Setting up of Standard Status types
' Requirement Group	:Setting up of Status Types
' Product		:EMResource v3.21
' Date			:7/29/2013
' Author		:QSG
' Modified Date				Modified By
' Date					Name
'*******************************************************************/

public class FTSSettingUpOfStandardStatusTypes {

	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSStatusSnapShotReport");
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

		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
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

	//start//testFTS108225//
	/***********************************************************************************************************
	'Description	:Create a status type ST1 (role-based), ST2 (shared) and ST3 (private) without selecting any
	                 roles (view/update) associating with SST and verify that ST1, ST2 and ST3 along with SST can
	                  be viewed by System Admin in the following setup pages: 
						a. Status Type List  
						b. Create New /Edit Resource Type  
						c. Create/Edit Event Template  
						d. Edit event
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/29/2013
	'Author			:QSG
	'-----------------------------------------------------------------------------------------------------------
	'Modified Date				                                                               Modified By
	'Date					                                                                   Name
	************************************************************************************************************/

	@Test
	public void testFTS108225() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		General objGeneral = new General();
		try {
			gstrTCID = "108225"; // Test Case Id
			gstrTO = " Create a status type ST1 (role-based), ST2 (shared) and ST3 (private)"
					+ " without selecting any roles (view/update) associating with"
					+ "SST and verify that ST1, ST2 and ST3 along with SST can be viewed by "
					+ "System Admin in the following setup pages: a. Status Type List  "
					+ "b. Create New /Edit Resource Type  c. Create/Edit Event Template  "
					+ "d. Edit event";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";
			
			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");

			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";

			String strStandardST1="ALS";
			String strStandardST2="BLS";
			String strStandardST3="Contact: Title";
			
			// role based
			String statrNumTypeName = "rNST" + strTimeText;
			String strSTValues[] = new String[3];
			String strStatTypeColor = "Black";
			// private
			String statpNumTypeName = "pNST" + strTimeText;
			// Shared
			String statsMultiTypeName = "sMST" + strTimeText;
			String str_sharedStatusName1 = "sSa" + strTimeTxt;
			String str_sharedStatusName2 = "sSb" + strTimeTxt;

			String str_sharedStatusValue[] = new String[2];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";

			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRTvalue = new String[1];
			// RS
			String strResource = "AutoRs1_A" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			//ET
			String strTempName = "Autotmp1" + strTimeText;
			String strTempDef = "Automation";
			String strETValue="";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			String strInfo = "auto event";
			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";
			/*
			 * STEP : Action:Precondition:
			 * 
			 * 1. Role based status type 'ST1' (Number status type) is created
			 * selecting standard status type 'SSTA'
			 * 2. Shared status type 'ST2' (Multi status type) is created
			 * selecting standard status type 'SSTB'
			 * 3. Private status type 'ST3' is created selecting standard status
			 * type 'SSTC'
			 * 4.Resource 'RS1' is created under resource Type 'RT1' selecting
			 * 'ST1', 'ST2' ,'ST3' at 'RT1' level.
			 * 5.View 'V1' is created selecting 'ST1', 'ST2' ,'ST3' and 'RT1'.
			 * 6.Event template 'ET1' is created selecting 'RT1' and 'ST1',
			 * 'ST2' ,'ST3'.
			 * 7.Event 'EV' is created under the template 'ET1' selecting 'RT1'.
			 * Expected Result:No Expected Result
			 */
			// 611306
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

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

			// Role based status type -rNST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(seleniumPrecondition, strStandardST1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition, statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrNumTypeName);
				if (strSTValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Private NST 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(seleniumPrecondition, strStandardST2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition, statpNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpNumTypeName);
				if (strSTValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Shared MST
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
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsMultiTypeName, strVisibilty, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(seleniumPrecondition, strStandardST3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(seleniumPrecondition, statsMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsMultiTypeName);
				if (strSTValues[2].compareTo("") != 0) {
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
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName1);
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
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName2);
				if (str_sharedStatusValue[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName,
						strSTValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 1; intST < strSTValues.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition,
							strSTValues[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strRTvalue[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objResources
						.createResourceWitLookUPadresSharWitRgn(
								seleniumPrecondition, strResource, strAbbrv,
								strResrcTypName, true, strContFName,
								strContLName, strState, strCountry,
								strStandResType);
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
			//View 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRSValues[] = { strRSValue[0] };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName_1, strVewDescription, strViewType, true,
						false, strSTValues, false, strRSValues);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(seleniumPrecondition, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(seleniumPrecondition, strRTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//ET1
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
				String[] strResTypeValue = { strRTvalue[0] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strSTValues);
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

			//EVE1
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as RegAdmin (System Admin) and Navigate to
			 * Setup >> Status Type. Expected Result:'Select Status Type' screen
			 * is displayed.
			 * Status types ST1 is displayed along with standard status type
			 * SSTA.
			 * Status types ST2 is displayed along with standard status type
			 * SSTB.
			 * Status types ST3 is displayed along with standard status type
			 * SSTC.
			 */
			// 611307
			try {
				assertEquals("", strFuncResult);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				log4j.info("========Status Type list page===========");
				assertEquals("", strFuncResult);
				String strElementID="//table[@summary='Status Types']"
								+ "/thead/tr/th[5]/a[text()='Standard�Type']"
								+ "/ancestor::table/tbody/tr/td[2][text()='"
								+ statrNumTypeName + "']"
								+ "/following-sibling::td[3][contains(text(),'"+strStandardST1+"')]";
				assertTrue(selenium.isElementPresent(strElementID));
				log4j.info(strStandardST1
						+ " is displayed under 'Standard status type' column for "+statrNumTypeName);
				
			} catch (Exception Ae) {
				strFuncResult=strStandardST1
						+ " is Not displayed under 'Standard status type' column for "+statrNumTypeName;
				gstrReason = strFuncResult;
				
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID="//table[@summary='Status Types']"
								+ "/thead/tr/th[5]/a[text()='Standard�Type']"
								+ "/ancestor::table/tbody/tr/td[2][text()='"
								+ statpNumTypeName + "']"
								+ "/following-sibling::td[3][contains(text(),'"+strStandardST2+"')]";
				assertTrue(selenium.isElementPresent(strElementID));
				log4j.info(strStandardST2
						+ " is displayed under 'Standard status type' column for "+statpNumTypeName);
				
			} catch (AssertionError Ae) {
				strFuncResult=strStandardST2
						+ " is Not displayed under 'Standard status type' column for "+statpNumTypeName;
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID="//table[@summary='Status Types']"
								+ "/thead/tr/th[5]/a[text()='Standard�Type']"
								+ "/ancestor::table/tbody/tr/td[2][text()='"
								+ statsMultiTypeName + "']"
								+ "/following-sibling::td[3][contains(text(),'"+strStandardST3+"')]";
				assertTrue(selenium.isElementPresent(strElementID));
				log4j.info(strStandardST3
						+ " is displayed under 'Standard status type' column for "+statsMultiTypeName);
				
			} catch (AssertionError Ae) {
				strFuncResult=strStandardST3
						+ " is Not displayed under 'Standard status type' column for "+statsMultiTypeName;
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource Type screen Expected
			 * Result:'Resource Type List' screen is displayed.
			 */
			// 611308
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Create New Resource Type. Expected
			 * Result:'Create New Resource Type' screen is displayed.
			 * Status types ST1 is displayed along with standard status type
			 * SSTA.
			 * Status types ST2 is displayed along with standard status type
			 * SSTB.
			 * Status types ST3 is displayed along with standard status type
			 * SSTC.
			 */
			// 611309
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToCreateNewResrcTypePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				log4j.info("========Resource Type list page===========");
				assertEquals("", strFuncResult);
				String statrSTType1=statrNumTypeName+" ("+strStandardST1+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statrNumTypeName+" is displayed along with standard status type "+strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statrNumTypeName+" is Not displayed along with standard status type "+strStandardST1+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2=statpNumTypeName+" ("+strStandardST2+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statpNumTypeName+" is displayed along with standard status type "+strStandardST2);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statpNumTypeName+" is Not displayed along with standard status type "+strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3=statsMultiTypeName+" ("+strStandardST3+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statsMultiTypeName+" is displayed along with standard status type "+strStandardST3);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statsMultiTypeName+" is Not displayed along with standard status type "+strStandardST3+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Cancel' Expected Result:'Resource Type
			 * List' screen is displayed.
			 */
			// 611310
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.cancelAndNavToResourceTypeList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit' link corresponding to resource type
			 * 'RT1' Expected Result:'Edit Resource Type' screen is displayed.
			 * 'Create New Resource Type' screen is displayed.
			 * Status types ST1 is displayed along with standard status type
			 * SSTA.
			 * Status types ST2 is displayed along with standard status type
			 * SSTB.
			 * Status types ST3 is displayed along with standard status type
			 * SSTC.
			 */
			// 611311
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				log4j.info("========Edit Resource Type list page===========");
				assertEquals("", strFuncResult);
				String statrSTType1=statrNumTypeName+" ("+strStandardST1+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statrNumTypeName+" is displayed along with standard status type "+strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statrNumTypeName+" is Not displayed along with standard status type "+strStandardST1+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2=statpNumTypeName+" ("+strStandardST2+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statpNumTypeName+" is displayed along with standard status type "+strStandardST2);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statpNumTypeName+" is Not displayed along with standard status type "+strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3=statsMultiTypeName+" ("+strStandardST3+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statsMultiTypeName+" is displayed along with standard status type "+strStandardST3);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statsMultiTypeName+" is Not displayed along with standard status type "+strStandardST3+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Setup and Click on
			 * 'Create New Event Template' button. Expected Result:'Create New
			 * Event Template' screen is displayed.
			 * 
			 * Status types ST1 is displayed along with standard status type
			 * SSTA.
			 * 
			 * Status types ST2 is displayed along with standard status type
			 * SSTB.
			 * 
			 * Status types ST3 is displayed along with standard status type
			 * SSTC.
			 */
			// 611312
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToCreateNewEventTemplatePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				log4j.info("========Create Event Template page===========");
				assertEquals("", strFuncResult);
				String statrSTType1=statrNumTypeName+" ("+strStandardST1+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statrNumTypeName+" is displayed along with standard status type "+strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statrNumTypeName+" is Not displayed along with standard status type "+strStandardST1+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2=statpNumTypeName+" ("+strStandardST2+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statpNumTypeName+" is displayed along with standard status type "+strStandardST2);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statpNumTypeName+" is Not displayed along with standard status type "+strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3=statsMultiTypeName+" ("+strStandardST3+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statsMultiTypeName+" is displayed along with standard status type "+strStandardST3);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statsMultiTypeName+" is Not displayed along with standard status type "+strStandardST3+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Setup and Click on
			 * 'Edit' link associated with event template 'ET1' Expected
			 * Result:'Edit Event Template' screen is displayed.
			 * 
			 * Status types ST1 is displayed along with standard status type
			 * SSTA.
			 * 
			 * Status types ST2 is displayed along with standard status type
			 * SSTB.
			 * 
			 * Status types ST3 is displayed along with standard status type
			 * SSTC.
			 */
			// 611313
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium, strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				log4j.info("========Edit Event Template page===========");
				assertEquals("", strFuncResult);
				String statrSTType1=statrNumTypeName+" ("+strStandardST1+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statrNumTypeName+" is displayed along with standard status type "+strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statrNumTypeName+" is Not displayed along with standard status type "+strStandardST1+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2=statpNumTypeName+" ("+strStandardST2+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statpNumTypeName+" is displayed along with standard status type "+strStandardST2);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statpNumTypeName+" is Not displayed along with standard status type "+strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3=statsMultiTypeName+" ("+strStandardST3+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statsMultiTypeName+" is displayed along with standard status type "+strStandardST3);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statsMultiTypeName+" is Not displayed along with standard status type "+strStandardST3+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Management,click on
			 * 'Edit' link associated with event 'EV'. Expected Result:'Edit
			 * Event' screen is displayed.
			 * 
			 * Status types ST1 is displayed along with standard status type
			 * SSTA.
			 * 
			 * Status types ST2 is displayed along with standard status type
			 * SSTB.
			 * 
			 * Status types ST3 is displayed along with standard status type
			 * SSTC.
			 */
			// 611314
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navEditEventPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				log4j.info("========Edit Event page===========");
				assertEquals("", strFuncResult);
				String statrSTType1=statrNumTypeName+" ("+strStandardST1+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statrNumTypeName+" is displayed along with standard status type "+strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statrNumTypeName+" is Not displayed along with standard status type "+strStandardST1+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2=statpNumTypeName+" ("+strStandardST2+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statpNumTypeName+" is displayed along with standard status type "+strStandardST2);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statpNumTypeName+" is Not displayed along with standard status type "+strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3=statsMultiTypeName+" ("+strStandardST3+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statsMultiTypeName+" is displayed along with standard status type "+strStandardST3);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statsMultiTypeName+" is Not displayed along with standard status type "+strStandardST3+"";
				log4j.info(strFuncResult);
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
			gstrTCID = "FTS-108225";
			gstrTO = " Create a status type ST1 (role-based), ST2 (shared) and ST3 (private)"
					+ " without selecting any roles (view/update) associating with"
					+ "SST and verify that ST1, ST2 and ST3 along with SST can be viewed by "
					+ "System Admin in the following setup pages: a. Status Type List  "
					+ "b. Create New /Edit Resource Type  c. Create/Edit Event Template  "
					+ "d. Edit event";
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

	//end//testFTS108225//

	//start//testFTS107951//
	/*********************************************************************************************************
	'Description	:Verify that status types created selecting standard status type and without selecting any 
	                role can be viewed by any user (non system admin) on the following setup pages: 
						a. Status Type List 
						b. Create New /Edit Resource Type 
						c. Create/Edit Event Template 
						d. Edit event
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/29/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------------------------
	'Modified Date				                                                                 Modified By
	'Date					                                                                     Name
	***********************************************************************************************************/

	@Test
	public void testFTS107951() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		General objGeneral = new General();
		CreateUsers objCreateUsers =new CreateUsers();
		try {
			gstrTCID = "107951"; // Test Case Id
			gstrTO = " Verify that status types created "
					+ "selecting standard status type and without selecting any "
					+ "role can be viewed by any user (non system admin) on the following setup pages: "
					+ "a. Status Type List b. Create New /Edit Resource Type c. Create/Edit Event Template "
					+ "d. Edit event";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";
			
			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");

			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";

			String strStandardST1="ALS";
			String strStandardST2="Account Type";
			String strStandardST3="Contact: Title";
			
			// role based
			String statrNumTypeName = "rNST" + strTimeText;
			String strSTValues[] = new String[3];
			String strStatTypeColor = "Black";
			// private
			String statpTxtTypeName = "pTST" + strTimeText;
			// Shared
			String statsMultiTypeName = "sMST" + strTimeText;
			String str_sharedStatusName1 = "sSa" + strTimeTxt;
			String str_sharedStatusName2 = "sSb" + strTimeTxt;

			String str_sharedStatusValue[] = new String[2];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";

			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRTvalue = new String[1];
			// RS
			String strResource = "AutoRs1_A" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			//ET
			String strTempName = "Autotmp1" + strTimeText;
			String strTempDef = "Automation";
			String strETValue="";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			String strInfo = "auto event";
			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";
			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserName;
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
			 * STEP : Action:Precondition:
			 * 1. Role based status type 'NST' is created selecting standard
			 * status type 'SSTA'
			 * 2. Shared status type 'MST' is created selecting standard statustype 'SSTB'
			 * 3. Private status type 'TST' is created selecting standard statustype 'SSTC'
			 * 5. User 'A' is created without selecting any role.
			 * 6.Provide user A with the following rights: a.Setup Status Types.
			 * b.Setup Resource Types. f.Maintain Events. g.Maintain Event
			 * Templates.
			 * 7.Resource 'RS1' is created under resource Type 'RT1' selecting
			 * 'NST', 'MST' ,'TST' at 'RT1' level.
			 * 8.View 'V1' is created selecting 'NST', 'MST' ,'TST' and 'RT1'.
			 * 9.Event template 'ET1' is created selecting 'RT1' and 'NST',
			 * 'MST' ,'TST'.
			 * 10.Event 'EV' is created under the template 'ET1' selecting
			 * 'RT1'. Expected Result:No Expected Result
			 */
			// 610581
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

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

			// Role based status type -rNST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(seleniumPrecondition, strStandardST1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition, statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrNumTypeName);
				if (strSTValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Private NST 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statpTxtTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(seleniumPrecondition, strStandardST2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition, statpTxtTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpTxtTypeName);
				if (strSTValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Shared MST
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
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsMultiTypeName, strVisibilty, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(seleniumPrecondition, strStandardST3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(seleniumPrecondition, statsMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsMultiTypeName);
				if (strSTValues[2].compareTo("") != 0) {
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
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName1);
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
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName2);
				if (str_sharedStatusValue[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName,
						strSTValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 1; intST < strSTValues.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition,
							strSTValues[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strRTvalue[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objResources
						.createResourceWitLookUPadresSharWitRgn(
								seleniumPrecondition, strResource, strAbbrv,
								strResrcTypName, true, strContFName,
								strContLName, strState, strCountry,
								strStandResType);
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
			//View 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRSValues[] = { strRSValue[0] };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName_1, strVewDescription, strViewType, true,
						false, strSTValues, false, strRSValues);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(seleniumPrecondition, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(seleniumPrecondition, strRTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//ET1
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
				String[] strResTypeValue = { strRTvalue[0] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strSTValues);
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

			//EVE1
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
			//User
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strInitPwd, strConfirmPwd,
						strUsrFulName);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
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
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as User A and Navigate to Setup >> Status
			 * Type. Expected Result:'Select Status Type' screen is displayed.
			 * Status types NST is displayed along with standard status type
			 * SSTA.
			 * Status types MST is displayed along with standard status type
			 * SSTB.
			 * Status types TST is displayed along with standard status type
			 * SSTC.
			 */
			// 611105
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
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
				log4j.info("========Status Type list page===========");
				assertEquals("", strFuncResult);
				String strElementID="//table[@summary='Status Types']"
								+ "/thead/tr/th[5]/a[text()='Standard�Type']"
								+ "/ancestor::table/tbody/tr/td[2][text()='"
								+ statrNumTypeName + "']"
								+ "/following-sibling::td[3][contains(text(),'"+strStandardST1+"')]";
				assertTrue(selenium.isElementPresent(strElementID));
				log4j.info(strStandardST1
						+ " is displayed under 'Standard status type' column for "+statrNumTypeName);
				
			} catch (Exception Ae) {
				strFuncResult=strStandardST1
						+ " is Not displayed under 'Standard status type' column for "+statrNumTypeName;
				gstrReason = strFuncResult;
				
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID="//table[@summary='Status Types']"
								+ "/thead/tr/th[5]/a[text()='Standard�Type']"
								+ "/ancestor::table/tbody/tr/td[2][text()='"
								+ statpTxtTypeName + "']"
								+ "/following-sibling::td[3][contains(text(),'"+strStandardST2+"')]";
				assertTrue(selenium.isElementPresent(strElementID));
				log4j.info(strStandardST2
						+ " is displayed under 'Standard status type' column for "+statpTxtTypeName);
				
			} catch (AssertionError Ae) {
				strFuncResult=strStandardST2
						+ " is Not displayed under 'Standard status type' column for "+statpTxtTypeName;
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID="//table[@summary='Status Types']"
								+ "/thead/tr/th[5]/a[text()='Standard�Type']"
								+ "/ancestor::table/tbody/tr/td[2][text()='"
								+ statsMultiTypeName + "']"
								+ "/following-sibling::td[3][contains(text(),'"+strStandardST3+"')]";
				assertTrue(selenium.isElementPresent(strElementID));
				log4j.info(strStandardST3
						+ " is displayed under 'Standard status type' column for "+statsMultiTypeName);
				
			} catch (AssertionError Ae) {
				strFuncResult=strStandardST3
						+ " is Not displayed under 'Standard status type' column for "+statsMultiTypeName;
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource Type screen Expected
			 * Result:'Resource Type List' screen is displayed.
			 */
			// 611111
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Create New Resource Type. Expected
			 * Result:'Create New Resource Type' screen is displayed.
			 * 
			 * Status types NST is displayed along with standard status type
			 * SSTA under 'Status Types' section.
			 * 
			 * Status types MST is displayed along with standard status type
			 * SSTB under 'Status Types' section.
			 * 
			 * Status types TST is displayed along with standard status type
			 * SSTC under 'Status Types' section.
			 */
			// 611115
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToCreateNewResrcTypePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				log4j.info("========Create Resource Type page===========");
				assertEquals("", strFuncResult);
				String statrSTType1=statrNumTypeName+" ("+strStandardST1+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statrNumTypeName+" is displayed along with standard status type "+strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statrNumTypeName+" is Not displayed along with standard status type "+strStandardST1+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2=statpTxtTypeName+" ("+strStandardST2+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statpTxtTypeName+" is displayed along with standard status type "+strStandardST2);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statpTxtTypeName+" is Not displayed along with standard status type "+strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3=statsMultiTypeName+" ("+strStandardST3+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statsMultiTypeName+" is displayed along with standard status type "+strStandardST3);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statsMultiTypeName+" is Not displayed along with standard status type "+strStandardST3+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Cancel' Expected Result:'Resource Type
			 * List' screen is displayed.
			 */
			// 611116
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.cancelAndNavToResourceTypeList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit' link corresponding to resource type
			 * 'RT1' Expected Result:'Edit Resource Type' screen is displayed.
			 * 
			 * Status types NST is displayed along with standard status type
			 * SSTA under 'Status Types' section.
			 * 
			 * Status types MST is displayed along with standard status type
			 * SSTB under 'Status Types' section.
			 * 
			 * Status types TST is displayed along with standard status type
			 * SSTC under 'Status Types' section.
			 */
			// 611129
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				log4j.info("========Edit Resource Type list page===========");
				assertEquals("", strFuncResult);
				String statrSTType1=statrNumTypeName+" ("+strStandardST1+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statrNumTypeName+" is displayed along with standard status type "+strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statrNumTypeName+" is Not displayed along with standard status type "+strStandardST1+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2=statpTxtTypeName+" ("+strStandardST2+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statpTxtTypeName+" is displayed along with standard status type "+strStandardST2);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statpTxtTypeName+" is Not displayed along with standard status type "+strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3=statsMultiTypeName+" ("+strStandardST3+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statsMultiTypeName+" is displayed along with standard status type "+strStandardST3);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statsMultiTypeName+" is Not displayed along with standard status type "+strStandardST3+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Setup and Click on
			 * 'Create New Event Template' button. Expected Result:'Create New
			 * Event Template' screen is displayed.
			 * 
			 * Status types NST is displayed along with standard status type
			 * SSTA under 'Status Types' section.
			 * 
			 * Status types MST is displayed along with standard status type
			 * SSTB under 'Status Types' section.
			 * 
			 * Status types TST is displayed along with standard status type
			 * SSTC under 'Status Types' section.
			 */
			// 611130
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToCreateNewEventTemplatePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				log4j.info("========Create Event Template page===========");
				assertEquals("", strFuncResult);
				String statrSTType1=statrNumTypeName+" ("+strStandardST1+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statrNumTypeName+" is displayed along with standard status type "+strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statrNumTypeName+" is Not displayed along with standard status type "+strStandardST1+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2=statpTxtTypeName+" ("+strStandardST2+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statpTxtTypeName+" is displayed along with standard status type "+strStandardST2);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statpTxtTypeName+" is Not displayed along with standard status type "+strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3=statsMultiTypeName+" ("+strStandardST3+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statsMultiTypeName+" is displayed along with standard status type "+strStandardST3);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statsMultiTypeName+" is Not displayed along with standard status type "+strStandardST3+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Setup and Click on
			 * 'Edit' link associated with event template 'ET1' Expected
			 * Result:'Edit Event Template' screen is displayed.
			 * 
			 * Status types NST is displayed along with standard status type
			 * SSTA under 'Status Types' section.
			 * 
			 * Status types MST is displayed along with standard status type
			 * SSTB under 'Status Types' section.
			 * 
			 * Status types TST is displayed along with standard status type
			 * SSTC under 'Status Types' section.
			 */
			// 611132
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium, strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				log4j.info("========Edit Event Template page===========");
				assertEquals("", strFuncResult);
				String statrSTType1=statrNumTypeName+" ("+strStandardST1+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statrNumTypeName+" is displayed along with standard status type "+strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statrNumTypeName+" is Not displayed along with standard status type "+strStandardST1+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2=statpTxtTypeName+" ("+strStandardST2+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statpTxtTypeName+" is displayed along with standard status type "+strStandardST2);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statpTxtTypeName+" is Not displayed along with standard status type "+strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3=statsMultiTypeName+" ("+strStandardST3+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statsMultiTypeName+" is displayed along with standard status type "+strStandardST3);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statsMultiTypeName+" is Not displayed along with standard status type "+strStandardST3+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Management,click on
			 * 'Edit' link associated with event 'EV'. Expected Result:'Edit
			 * Event' screen is displayed. Status types NST is displayed along
			 * with standard status type SSTA under 'Status Types' section.
			 * 
			 * Status types MST is displayed along with standard status type
			 * SSTB under 'Status Types' section.
			 * 
			 * Status types TST is displayed along with standard status type
			 * SSTC under 'Status Types' section.
			 */
			// 611133

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navEditEventPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				log4j.info("========Edit Event page===========");
				assertEquals("", strFuncResult);
				String statrSTType1=statrNumTypeName+" ("+strStandardST1+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statrNumTypeName+" is displayed along with standard status type "+strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statrNumTypeName+" is Not displayed along with standard status type "+strStandardST1+"";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2=statpTxtTypeName+" ("+strStandardST2+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statpTxtTypeName+" is displayed along with standard status type "+strStandardST2);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statpTxtTypeName+" is Not displayed along with standard status type "+strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			//sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3=statsMultiTypeName+" ("+strStandardST3+")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium, statrSTType3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type "+statsMultiTypeName+" is displayed along with standard status type "+strStandardST3);
				
			} catch (AssertionError Ae) {
				strFuncResult="Status types "+statsMultiTypeName+" is Not displayed along with standard status type "+strStandardST3+"";
				log4j.info(strFuncResult);
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
			gstrTCID = "FTS-107951";
			gstrTO = " Verify that status types created "
					+ "selecting standard status type and without selecting any "
					+ "role can be viewed by any user (non system admin) on the following setup pages: "
					+ "a. Status Type List b. Create New /Edit Resource Type c. Create/Edit Event Template "
					+ "d. Edit event";
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

	// end//testFTS107951//
	
	//start//testFTS108151//
	/*******************************************************************************************************
	'Description	:Create a status type ST1 (role-based), ST2 (shared) and ST3 (private) without selecting 
	            any roles (view/update) associating with SST and verify that SST associated with statustypes
	    ST1, ST2 and ST3 CANNOT be viewed by any user (non system admin) in the following setup pages:
						a. Create /Edit Role <br>
						b. Edit Resource Level Status Types <br>
						c. Create New/Edit/Copy View <br>
						d. Edit Resource Detail View Sections <br>
						e. While configuring the form
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:8/1/2013
	'Author			:QSG
	'-------------------------------------------------------------------------------------------------------
	'Modified Date				                                                                Modified By
	'Date					                                                                    Name
	********************************************************************************************************/

	@Test
	public void testFTS108151() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		General objGeneral = new General();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole=new Roles();
		Forms objForms =new Forms();
		try {
			gstrTCID = "108151"; // Test Case Id
			gstrTO = " Create a status type ST1 (role-based), ST2 (shared) and ST3 (private) without selecting any "
					+ "roles (view/update) associating with SST and verify that SST associated with status types ST1, "
					+ "ST2 and ST3 CANNOT be viewed by any user (non system admin) in the following setup pages: "
					+ "a. Create /Edit Role "
					+ "b. Edit Resource Level Status Types "
					+ "c. Create New/Edit/Copy View "
					+ "d. Edit Resource Detail View Sections "
					+ "e. While configuring the form";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");

			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";

			String strStandardST1 = "ALS";
			String strStandardST2 = "Account Type";
			String strStandardST3 = "Contact: Title";

			// role based
			String statrNumTypeName = "rNST" + strTimeText;
			String strSTValues[] = new String[3];
			String strStatTypeColor = "Black";
			// private
			String statpTxtTypeName = "pTST" + strTimeText;
			// Shared
			String statsMultiTypeName = "sMST" + strTimeText;
			String str_sharedStatusName1 = "sSa" + strTimeTxt;
			String str_sharedStatusName2 = "sSb" + strTimeTxt;

			String str_sharedStatusValue[] = new String[2];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";

			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRTvalue = new String[1];
			// RS
			String strResource = "AutoRs1_A" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			// Role
			String strRolesName = "AutoRol_" + strTimeText;
			String strRoleValue = "";
			// ET
			String strTempName = "Autotmp1" + strTimeText;
			String strTempDef = "Automation";
			String strETValue = "";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			String strInfo = "auto event";
			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";
			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Auto" + strUserName;
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// form
			String strFormTempTitle = "OF" + strTimeText;
			String strFormActiv = "As Certain Status Changes";
			String strFormDesc = "Automation";
		/*
		* STEP :
		  Action:Precondition:1. Role based status type 'ST1' (number status type) is created selecting standard status type 'SSTA'
			2. Shared status type 'ST2' (multi status type) is created selecting standard status type 'SSTB'
			3. Private status type 'ST3' (Text status type) is created selecting standard status type 'SSTC'
			5. User 'A' is created without selecting any role.
			6.Provide user A with the following rights:
				a.Setup Resource Types. 
				b.Setup Resources. 
				c.Setup Roles. 
				d.Configure region views. 
				e.Form - User may create and modify forms 
			7.Resource 'RS1' is created under resource Type 'RT1' selecting 'ST1', 'ST2' ,'ST3' at 'RT1' level.
			8.View 'V1' is created selecting 'ST1', 'ST2' ,'ST3' and 'RT1'.
		  Expected Result:No Expected Result
		*/
		//611149
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

			// Role based status type -rNST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(
						seleniumPrecondition, strStandardST1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTValues[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrNumTypeName);
				if (strSTValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Private NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statpTxtTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(
						seleniumPrecondition, strStandardST2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statpTxtTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTValues[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statpTxtTypeName);
				if (strSTValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Shared MST
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
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(
						seleniumPrecondition, strStandardST3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, statsMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTValues[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statsMultiTypeName);
				if (strSTValues[2].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName, strSTValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 1; intST < strSTValues.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition, strSTValues[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strRTvalue[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objResources
						.createResourceWitLookUPadresSharWitRgn(
								seleniumPrecondition, strResource, strAbbrv,
								strResrcTypName, true, strContFName,
								strContLName, strState, strCountry,
								strStandResType);
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
			// View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRSValues[] = { strRSValue[0] };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName_1, strVewDescription, strViewType, true,
						false, strSTValues, false, strRSValues);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(seleniumPrecondition,
						strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ET1
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
				String[] strResTypeValue = { strRTvalue[0] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strSTValues);
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

			// EVE1
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
			//Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName, strRoleRights, strViewRightValue, true,
						updateRightValue, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.savAndVerifyRoles(seleniumPrecondition,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName);

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
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpRoles");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
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
			
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

		/*
		* STEP :
		  Action:Login as user A, navigate to Setup >> Roles
		  Expected Result:'Roles List' screen is displayed.
		*/
		//611151
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Create New Role'
		  Expected Result:'Create Role' screen is displayed.
          Status types ST1, ST2, ST3 are displayed. (Standard status types associated with these status types
           are not displayed).
		*/
		//611152
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navCreateRolePge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("========Create Role page===========");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.varSTPresentOrNotInEditRolePage(
						selenium, strSTValues[1], true, strSTValues[0], true,
						statrNumTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.varSTPresentOrNotInEditRolePage(
						selenium, strSTValues[1], true, strSTValues[1], true,
						statpTxtTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.varSTPresentOrNotInEditRolePage(
						selenium, strSTValues[1], true, strSTValues[2], true,
						statsMultiTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Cancel'
		  Expected Result:'Roles List' screen is displayed.
		*/
		//611153
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.cancelAndNavToRoleListPageNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Edit' link corresponding to role R1.
		  Expected Result:'Edit Role' screen is displayed.
		  Status types ST1, ST2, ST3 are displayed under 'Select the Status Types this Role may view' and 
		  'Select the Status Types this Role may update' sections. (Standard status types associated 
		  with these status types are not displayed).
		*/
		//611154

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navEditRolesPge(selenium, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("========Edit Role page===========");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.varSTPresentOrNotInEditRolePage(selenium,
						strSTValues[1], true, strSTValues[0],
						true, statrNumTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.varSTPresentOrNotInEditRolePage(selenium,
						strSTValues[1], true, strSTValues[1],
						true, statpTxtTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.varSTPresentOrNotInEditRolePage(selenium,
						strSTValues[1], true, strSTValues[2],
						true, statsMultiTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Resource and Click on 'Status Types' link associated with resource 'RS1'.
		  Expected Result:'ST1', 'ST2' and 'ST3' status types are displayed under 'Additional Status Types' section. 
		   (Standard status types associated with these status types are not displayed).
		*/
		//611166
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage_LinkChanged(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("========Edit Resource level page===========");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium,
						statrNumTypeName, strSTValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium,
						statpTxtTypeName, strSTValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium,
						statsMultiTypeName, strSTValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Views
		  Expected Result:'Region Views List' screen is displayed
		*/
		//611167
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Create New View' button.
		  Expected Result:'Create New View' screen is displayed and status types 'ST1', 'ST2' and 'ST3' 
		  status types are displayed under 'Select Status Types' section. 
		  (Standard status types associated with these status types are not displayed).
		*/
		//611168
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToCreateViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("========Create New View page===========");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[0], statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[1], statpTxtTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[2], statsMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Cancel'
		  Expected Result:'Region Views List' screen is displayed
		*/
		//611169
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.cancelAndNavToViewListpage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Edit' link associated with view 'V1'
		  Expected Result:'Edit View' screen is displayed and status types 'ST1', 'ST2' and 'ST3' 
		  status types are displayed under 'Select Status Types' section. 
		  (Standard status types associated with these status types are not displayed).
		*/
		//611170
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("========Edit View page===========");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[0], statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[1], statpTxtTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[2], statsMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Cancel'
		  Expected Result:'Region Views List' screen is displayed
		*/
		//611171
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.cancelAndNavToViewListpage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Copy' link associated with view 'V1'
		  Expected Result:'Edit View' screen is displayed and status types 'ST1', 'ST2' and 'ST3' 
		  status types are displayed under 'Select Status Types' section.
		   (Standard status types associated with these status types are not displayed).
		*/
		//611172
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditViewByCopyLink(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		log4j.info("========Edit View page by copy link===========");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[0], statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[1], statpTxtTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[2], statsMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Views and Click on'Customize Resource Detail View'.
		  Expected Result:'Edit Resource Detail View Sections' screen is displayed.
		*/
		//611173
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Uncategorized' section
		  Expected Result:Status types 'ST1', 'ST2' and 'ST3' are displayed with other status types
		   (Standard status types associated with these status types are not displayed). 
		*/
		//611174
			log4j.info("========Edit Resource Detail View Sections page ===========");
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrNumTypeName, statpTxtTypeName,
						statsMultiTypeName };
				strFuncResult = objViews.verfySTInUncagorizedSection(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Forms >> Configure Forms.
		  Expected Result:'Form Configuration' screen is displayed.
		*/
		//611175
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Create New Form Template'
		  Expected Result:'Create New Form Template' screen is displayed.
		*/
		//611176
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide form title and description and select 'As Status changes' from 'Form Activation'
		   dropdown list and click on 'Next'.
		  Expected Result:Status types 'ST1', 'ST2' and 'ST3' are displayed under 'Form Activation Status Type'
		   section (Standard status types associated with these status types are not displayed).
		*/
		//611177
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.filAllFldsInCreatNewFormAsStatusChanges(selenium,
								strFormTempTitle, strFormDesc, strFormActiv,
								false, false, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("========Form Activation Status Type page ===========");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.checkSTInFormActivationStPage(
						selenium, statrNumTypeName, strSTValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.checkSTInFormActivationStPage(
						selenium, statpTxtTypeName, strSTValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.checkSTInFormActivationStPage(
						selenium, statsMultiTypeName, strSTValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
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
			gstrTCID = "108151"; // Test Case Id
			gstrTO = " Create a status type ST1 (role-based), ST2 (shared) and ST3 (private) without selecting any "
					+ "roles (view/update) associating with SST and verify that SST associated with status types ST1, "
					+ "ST2 and ST3 CANNOT be viewed by any user (non system admin) in the following setup pages: "
					+ "a. Create /Edit Role "
					+ "b. Edit Resource Level Status Types "
					+ "c. Create New/Edit/Copy View "
					+ "d. Edit Resource Detail View Sections "
					+ "e. While configuring the form";// TO
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

	//end//testFTS108151//

	//start//testFTS108226//
	/***********************************************************************************************************
	'Description	:Create a status type ST1 (role-based), ST2 (shared) and ST3 (Private) without selecting any
	               roles (view/update) associating with SST and verify that SST associated with status types ST1,
	                ST2 and ST3 CANNOT be viewed by System Admin in the following setup pages: 
						a. Create /Edit Role <br>
						b. Edit Resource Level Status Types <br>
						c. Create New/Edit/Copy View <br>
						d. Edit Resource Detail View Sections <br>
						e. While configuring the form
						f. Default status type drop down on Create/edit resou
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:8/1/2013
	'Author			:QSG
	'-----------------------------------------------------------------------------------------------------------
	'Modified Date				                                                                  Modified By
	'Date					                                                                      Name
	************************************************************************************************************/

	@Test
	public void testFTS108226() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		Forms objForms =new Forms();
		Roles objRole=new Roles();
		EventSetup objEventSetup = new EventSetup();
		General objGeneral = new General();
		try {
			gstrTCID = "108226"; // Test Case Id
			gstrTO = " Create a status type ST1 (role-based), ST2 (shared) and ST3 (Private) without selecting any roles (view/update) associating with SST and verify that SST associated with status types ST1, ST2 and ST3 CANNOT be viewed by System Admin in the following setup pages: "
					+ "a. Create /Edit Role "
					+ "b. Edit Resource Level Status Types "
					+ "c. Create New/Edit/Copy View "
					+ "d. Edit Resource Detail View Sections "
					+ "e. While configuring the form"
					+ "f. Default status type drop down on Create/edit resou";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");

			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";

			String strStandardST1 = "ALS";
			String strStandardST2 = "Account Type";
			String strStandardST3 = "Contact: Title";

			// role based
			String statrNumTypeName = "rNST" + strTimeText;
			String strSTValues[] = new String[3];
			String strStatTypeColor = "Black";
			// private
			String statpTxtTypeName = "pTST" + strTimeText;
			// Shared
			String statsMultiTypeName = "sMST" + strTimeText;
			String str_sharedStatusName1 = "sSa" + strTimeTxt;
			String str_sharedStatusName2 = "sSb" + strTimeTxt;

			String str_sharedStatusValue[] = new String[2];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";

			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRTvalue = new String[1];
			// RS
			String strResource = "AutoRs1_A" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			// Role
			String strRolesName = "AutoRol_" + strTimeText;
			String strRoleValue = "";
			// ET
			String strTempName = "Autotmp1" + strTimeText;
			String strTempDef = "Automation";
			String strETValue = "";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			String strInfo = "auto event";
			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";
			// form
			String strFormTempTitle = "OF" + strTimeText;
			String strFormActiv = "As Certain Status Changes";
			String strFormDesc = "Automation";

		/*
		* STEP :
		  Action:Precondition:
			1. Role based status type 'ST1' (number status type) is created selecting standard status type 'SSTA'
			2. Shared status type 'ST2' (multi status type) is created selecting standard status type 'SSTB'
			3. Private status type 'ST3' (Text status type) is created selecting standard status type 'SSTC'
			4.Resource 'RS1' is created under resource Type 'RT1' selecting 'ST1', 'ST2' ,'ST3' at 'RT1' level.
			5.View 'V1' is created selecting 'ST1', 'ST2' ,'ST3' and 'RT1'.
		  Expected Result:No Expected Result
		*/
		//611315
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

			// Role based status type -rNST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(
						seleniumPrecondition, strStandardST1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTValues[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrNumTypeName);
				if (strSTValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Private NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statpTxtTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(
						seleniumPrecondition, strStandardST2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statpTxtTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTValues[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statpTxtTypeName);
				if (strSTValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Shared MST
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
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(
						seleniumPrecondition, strStandardST3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, statsMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSTValues[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statsMultiTypeName);
				if (strSTValues[2].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName, strSTValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 1; intST < strSTValues.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition, strSTValues[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strRTvalue[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objResources
						.createResourceWitLookUPadresSharWitRgn(
								seleniumPrecondition, strResource, strAbbrv,
								strResrcTypName, true, strContFName,
								strContLName, strState, strCountry,
								strStandResType);
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
			// View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRSValues[] = { strRSValue[0] };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName_1, strVewDescription, strViewType, true,
						false, strSTValues, false, strRSValues);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(seleniumPrecondition,
						strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ET1
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
				String[] strResTypeValue = { strRTvalue[0] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strSTValues);
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

			// EVE1
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
			//Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName, strRoleRights, strViewRightValue, true,
						updateRightValue, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.savAndVerifyRoles(seleniumPrecondition,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName);
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

		/*
		* STEP :
		  Action:Login as RegAdmin, navigate to Setup >> Roles
		  Expected Result:'Roles List' screen is displayed.
		*/
		//611316
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Create New Role'
		  Expected Result:'Create Role' screen is displayed.
		  Status types ST1, ST2, ST3 are displayed. (Standard status types associated with these status
		   types are not displayed).
		*/
		//611317
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navCreateRolePge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("========Create Role page===========");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.varSTPresentOrNotInEditRolePage(
						selenium, strSTValues[1], true, strSTValues[0], true,
						statrNumTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.varSTPresentOrNotInEditRolePage(
						selenium, strSTValues[1], true, strSTValues[1], true,
						statpTxtTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.varSTPresentOrNotInEditRolePage(
						selenium, strSTValues[1], true, strSTValues[2], true,
						statsMultiTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Cancel'
		  Expected Result:'Roles List' screen is displayed.
		*/
		//611318
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.cancelAndNavToRoleListPageNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Edit' link corresponding to role R1.
		  Expected Result:'Edit Role' screen is displayed.
        Status types ST1, ST2, ST3 are displayed under 'Select the Status Types this Role may view'
         and 'Select the Status Types this Role may update' sections. (Standard status types associated 
         with these status types are not displayed).
		*/
		//611319
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navEditRolesPge(selenium, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("========Edit Role page===========");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.varSTPresentOrNotInEditRolePage(selenium,
						strSTValues[1], true, strSTValues[0],
						true, statrNumTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.varSTPresentOrNotInEditRolePage(selenium,
						strSTValues[1], true, strSTValues[1],
						true, statpTxtTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.varSTPresentOrNotInEditRolePage(selenium,
						strSTValues[1], true, strSTValues[2],
						true, statsMultiTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Resource and Click on 'Status Types' link associated with resource 'RS1'.
		  Expected Result:'ST1', 'ST2' and 'ST3' status types are displayed under 'Additional Status Types' section. (Standard status types associated with these status types are not displayed).
		*/
		//611320
			//611166
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage_LinkChanged(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("========Edit Resource level page===========");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium,
						statrNumTypeName, strSTValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium,
						statpTxtTypeName, strSTValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium,
						statsMultiTypeName, strSTValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Views
		  Expected Result:'Region Views List' screen is displayed
		*/
		//611321
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Create New View' button.
		  Expected Result:'Create New View' screen is displayed and status types 'ST1', 'ST2' and 'ST3' status types are displayed under 'Select Status Types' section. (Standard status types associated with these status types are not displayed).
		*/
		//611322
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToCreateViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("========Create New View page===========");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[0], statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[1], statpTxtTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[2], statsMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Cancel'
		  Expected Result:'Region Views List' screen is displayed
		*/
		//611323
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.cancelAndNavToViewListpage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Edit' link associated with view 'V1'
		  Expected Result:'Edit View' screen is displayed and status types 'ST1', 'ST2' and 'ST3' status types are displayed under 'Select Status Types' section. (Standard status types associated with these status types are not displayed).
		*/
		//611324
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("========Edit View page===========");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[0], statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[1], statpTxtTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[2], statsMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Cancel'
		  Expected Result:'Region Views List' screen is displayed
		*/
		//611325
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.cancelAndNavToViewListpage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Copy' link associated with view 'V1'
		  Expected Result:'Edit View' screen is displayed and status types 'ST1', 'ST2' and 'ST3' status types are displayed under 'Select Status Types' section. (Standard status types associated with these status types are not displayed).
		*/
		//611326
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditViewByCopyLink(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		log4j.info("========Edit View page by copy link===========");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[0], statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[1], statpTxtTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strSTValues[2], statsMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Setup >> Views and Click on'Customize Resource Detail View'.
		  Expected Result:'Edit Resource Detail View Sections' screen is displayed.
		*/
		//611327
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Uncategorized' section
		  Expected Result:Status types 'ST1', 'ST2' and 'ST3' are displayed with other status types (Standard status types associated with these status types are not displayed).
		*/
		//611328
			log4j.info("========Edit Resource Detail View Sections page ===========");
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrNumTypeName, statpTxtTypeName,
						statsMultiTypeName };
				strFuncResult = objViews.verfySTInUncagorizedSection(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			
			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Forms >> Configure Forms.
		  Expected Result:'Form Configuration' screen is displayed.
		*/
		//611329
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Create New Form Template'
		  Expected Result:'Create New Form Template' screen is displayed.
		*/
		//611330
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide form title and description and select 'As
			 * Status changes' from 'Form Activation' dropdown list and click on
			 * 'Next'. Expected Result:Status types 'ST1', 'ST2' and 'ST3' are
			 * displayed under 'Form Activation Status Type' section (Standard
			 * status types associated with these status types are not
			 * displayed).
			 */
			// 611331
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.filAllFldsInCreatNewFormAsStatusChanges(selenium,
								strFormTempTitle, strFormDesc, strFormActiv,
								false, false, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("========Form Activation Status Type page ===========");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.checkSTInFormActivationStPage(
						selenium, statrNumTypeName, strSTValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.checkSTInFormActivationStPage(
						selenium, statpTxtTypeName, strSTValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.checkSTInFormActivationStPage(
						selenium, statsMultiTypeName, strSTValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// rNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType1 = statrNumTypeName + " (" + strStandardST1
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statrNumTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST1);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statrNumTypeName
						+ " is displayed along with standard status type "
						+ strStandardST1 + "";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}

			// pNST
			try {
				assertEquals("", strFuncResult);
				String statrSTType2 = statpTxtTypeName + " (" + strStandardST2
						+ ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statpTxtTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST2);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statpTxtTypeName
						+ " is displayed along with standard status type "
						+ strStandardST2;
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			// sMST
			try {
				assertEquals("", strFuncResult);
				String statrSTType3 = statsMultiTypeName + " ("
						+ strStandardST3 + ")";
				strFuncResult = objGeneral.VerifyIsTextPresent(selenium,
						statrSTType3, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status type " + statsMultiTypeName
						+ " is NOT displayed along with standard status type "
						+ strStandardST3);

			} catch (AssertionError Ae) {
				strFuncResult = "Status types " + statsMultiTypeName
						+ " is displayed along with standard status type "
						+ strStandardST3 + "";
				log4j.info(strFuncResult);
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
			gstrTCID = "108226"; // Test Case Id
			gstrTO = " Create a status type ST1 (role-based), ST2 (shared) and ST3 (Private) without selecting any roles (view/update) associating with SST and verify that SST associated with status types ST1, ST2 and ST3 CANNOT be viewed by System Admin in the following setup pages: "
					+ "a. Create /Edit Role "
					+ "b. Edit Resource Level Status Types "
					+ "c. Create New/Edit/Copy View "
					+ "d. Edit Resource Detail View Sections "
					+ "e. While configuring the form"
					+ "f. Default status type drop down on Create/edit resou";// TO
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

	//end//testFTS108226//
}
