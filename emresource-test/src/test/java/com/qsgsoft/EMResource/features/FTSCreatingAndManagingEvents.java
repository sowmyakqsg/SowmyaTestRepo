package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**********************************************************************
 * ' Description       :This class contains test cases from requirement 
 * ' Requirement       :Creating and Managing Events 
 * ' Requirement Group :Resource Hierarchies 
 * ' Product           :EMResource v3.21 
 * ' Date              :6/27/2013 
 * ' Author            :QSG '  
 * --------------------------------------------------------------------               
 *  Modified Date                                       Modified By 
 *  'Date                                               Name '
 **********************************************************************/

public class FTSCreatingAndManagingEvents {

	// Log4j object to write log entries to the Log files
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSCreatingAndManagingEvents");
	static {
		BasicConfigurator.configure();
	}
	// Objects to access the common functions
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	ReadData rdExcel;
	/*
	 * Global variables to store the test case details – TestCaseID, Test
	 * Objective,Result, Reason for failure
	 */
	String gstrTCID, gstrTO, gstrResult, gstrReason;

	// Selenium Object
	Selenium selenium, seleniumPrecondition;

	// Object for date time settings
	Date_Time_settings dts = new Date_Time_settings();

	public Properties propElementDetails; // Property variable for ElementID
											// file
	public Properties propEnvDetails;// Property variable for Environment data
	public Properties propPathDetails; // Property variable for Path information
	public Properties propAutoITDetails;// Property variable for AutoIT file
	public Properties pathProps;
	// details
	public static Properties browserProps = new Properties();
	public static String gstrBrowserName;// Variable to store browser name
	public static String gstrTimetaken, gstrdate, gstrtime, gstrBuild;// Result
																		// Variables
	double gdbTimeTaken; // Variable to store the time taken

	public static Date gdtStartDate;// Date variable

	public static long sysDateTime;
	public static long gsysDateTime = 0;
	public static String gstrTimeOut = "";
	public static String sessionId_FF36, sessionId_FF20, sessionId_FF3,
			sessionId_FF35, sessionId_IE6, sessionId_IE7, sessionId_IE8,
			session_SF3, session_SF4, session_op9, session_op10, session_GC,
			StrSessionId = "", StrSessionId1, StrSessionId2;

	/*************************************************************************************
	 * This function is called the setup() function which is executed before every test. 
	 * The function will take care of creating a new selenium session for every test 
	 *************************************************************************************/

	@Before
	public void setUp() throws Exception {

		Paths_Properties objPathsProps = new Paths_Properties();
		propAutoITDetails = objPathsProps.ReadAutoit_FilePath();

		propPathDetails = objPathsProps.Read_FilePath();
		pathProps = objPathsProps.Read_FilePath();

		// Create object to read environment properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		// Retrieve browser information
		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		// Retrieve the value of page load time limit
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		// create an object to refer to Element ID properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		// Create a new selenium session
		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));
		// Start Selenium RC
		selenium.start();
		// Maximize the window
		selenium.windowMaximize();
		selenium.setTimeout(propEnvDetails.getProperty("TimeOut"));

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		// Define object to call support functions to read excel, date etc
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();
		rdExcel = new ReadData();

	}

	/****************************************************************************************
	 * * This function is called the teardown() function which is executed after every test. 
	 * The function will take care of stopping the selenium session for every
	 * test and writing the execution result of the test.
	 ***************************************************************************************/
	@After
	public void tearDown() throws Exception {
		// kill browser
		try{
			selenium.close();
		}catch(Exception e){
			
		}
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
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

		// Retrieve the path of the Result file
		String FILE_PATH = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		FILE_PATH = pathProps.getProperty("Resultpath");
		// Retrieve the total execution time
		gdbTimeTaken = objOFC.TimeTaken(gdtStartDate);
		Date_Time_settings dts = new Date_Time_settings();
		// Get the current date
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");
		// Get the Build ID of the application
		gstrBuild = propEnvDetails.getProperty("Build");
		// Check if result should be written to Excel or Test Management Tool
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");
		// Write Result of the test.
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetaken, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild, StrSessionId);

	}

	// start//testFTS107915//
	/*********************************************************************************
	 * 'Description  :Verify that sub-resources are not listed while editing an event.
	 * 'Precondition : 
	 * 'Arguments    :None 
	 * 'Returns      :None 
	 * 'Date         :8/01/2013 
	 * 'Author       :QSG
	 * '-----------------------------------------------------------------------------
	 * 'Modified Date                                                  Modified By 
	 * 'Date                                                            Name
	 ********************************************************************************/

	@Test
	public void testFTS107915() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		ResourceTypes objRT = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup=new EventSetup();
		General objGeneral=new General();
		Roles objRole = new Roles();
		EventList objEventList=new EventList();
		try {
			gstrTCID = "107915"; // Test Case Id
			gstrTO = "Verify that sub-resources are not listed while editing an event.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			gstrTimetaken = dts.timeNow("hh:mm:ss");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetaken = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = propPathDetails.getProperty("TestData_path");

			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypeName1 = "St1_" + strTimeText;
			String strStatTypeName2 = "St2_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strNSTValue = "Number";
			String strTSTValue = "Number";
			String strMSTValue = "Multi";
			String strSSTValue = "Saturation Score";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strStatusValue[] = new String[2];
			String strSTvalue[] = new String[6];
            //RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTvalue[] = new String[2];
			 //RS
			String strResource = "AutoRs_" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strAbbrv = "AB";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[2];
			//Role
			String strRoleName1 = "AutoR1_" + strTimeText;
			String strRoleValue[] = new String[1];
			String strRoleRights[][] = {};
			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			//ET
			String strEveTempName="ET_"+ strTimeText;
			String strTempDef=objrdExcel.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor="Red";
			//Event
			String strEveName="Eve_"+ strTimeText;
			String strInfo=objrdExcel.readInfoExcel("Event", 3, 2, strFILE_PATH);
			
		/*
		 * STEP : Action:Precondition:
		1. Status type NST,MST,SST,TST,ST1 and ST2 are created in region RG1.
		2. Resource Type (Normal) RT1 is created selecting status type NST,MST,SST and TST.
		3. Resource RS1 is created selecting RT1
		4. Sub-Resource SRT1 is created selecting status type ST1 and ST2.
		5. Sub-Resource SRS1 is created under parent resource 'RS1' selecting Sub-Resource 'SRT1'.
		6. Event Template 'ET' is created selecting status types NST,MST,SST,TST and resource 'RS1'
		7. User 'U1' is created selecting following rights:
				a. 'Maintain Events' right.
				b. View and update right on resource 'RS1' 
		 * 'RS1' Expected Result:No Expected Result
		 */
		// 609169

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);		
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST
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

			// TST

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
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// MST
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
					strSTvalue[2] = strStatValue;
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
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType, strStatusName2,
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
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SST
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

			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strStatTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[4] = strStatValue;
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
						seleniumPrecondition, strNSTValue, strStatTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[5] = strStatValue;
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
				strFuncResult = objRT
						.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[0] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrctTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SRT
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[4] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[5], true);
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
				strFuncResult = objRT
						.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[1] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strSubResrctTypName);
				if (strRTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
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
				strRSValue[0] = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			//SRS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource, strAbbrv,
						strSubResrctTypName, strStandResType, false,
						strContFName, strContLName, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objRs.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//R1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalues={strSTvalue[0],strSTvalue[1],strSTvalue[2],strSTvalue[3]};
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(seleniumPrecondition,
						strRoleName1, strRoleRights, strSTvalues, true,
						strSTvalues, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName1);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER
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
						false, true, false, true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//ET
			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objEventSetup
						.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				String strRTvalues[] = { strRTvalue[0] };
				String strSTvalues[] = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strEveTempName, strTempDef,
						strEveColor, true, strRTvalues, strSTvalues, false,
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
			
		log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
		
		/*
		 * STEP : Action:Login as user 'U1' Expected Result:'Region Default'
		 * screen is displayed.
		 */
		// 609170

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
				blnLogin = true;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate Event >> Event Management  
		 *  Expected Result:'Event Management' screen is displayed.
          'Create New Event' button is displayed at top left of the screen. 
		 */
		// 609171
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("Event.CreateEvent");
				strFuncResult = objGeneral
						.CheckForElements(selenium, strElementID);
				log4j.info("'Create New Event' button is displayed at top left of the screen."
						+ strUserName_1 + "'. ");
			} catch (AssertionError ae) {
				log4j.info("'Create New Event' button is Not displayed at top left of the screen."
						+ strUserName_1 + "'.");
				strFuncResult = "'Create New Event' button is Not displayed at top left of the screen."
						+ strUserName_1 + "'.";
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on 'Create New Event' button,select event template 'ET' under
		 *  'Select Event Template' screen 
		 * Expected Result:	'Create New Event' screen is displayed. 
		 */
		// 609172
			//EVENT
			try {
				assertEquals("",strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strEveTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + strFuncResult;
			}
		/*
		 * STEP : Action:Create event 'EVE' filling data in all the mandatory field and selecting resource 'RS1' 	
		 *  Expected Result:Event 'EVE' is listed under 'Event Management' screen. 
		 */
		// 609173
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource, true, false);
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
			
		/*
		 * STEP : Action:Click on 'Edit' link associated with event 'EVE' 
		 *  Expected Result:'Edit Event' screen is displayed.
			Sub-resource 'SRS1' is not listed under 'Resources to Participate in This Event:' section.
			Resource 'RS1' is listed under 'Resources to Participate in This Event:' section. 
		 */
		// 609173
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navEditEventPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[0], true, strResource, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkResInEditEventPage(selenium,
						strRSValue[1], false, strSubResource, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * STEP : Action:Click on event banner 
		 *  Expected Result:'Event Status' screen is displayed.
            Resources 'RS1' is displayed on the 'Event Status' screen under RT1 along 
            with NST, MST, TST and SST status types.
  	  		Sub-Resource 'SRS1' is not displayed. 
		 */
		// 609173
			try {
				assertEquals("", strFuncResult);
				String strArStatType[] = { strMultiStatType, strNumStatType,
						strSatuStatType, strTxtStatType };
				strFuncResult = objEventList.checkInEventBannerNew(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.chkResourceInEventBanner(selenium,
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
			gstrTCID = "BQS-107915";
			gstrTO = "Verify that sub-resources are not listed while editing an event.";// TO
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

	// end//testFTS107915//
	
	/***************************************************************
	'Description	:Create an event selecting to start at a certain date and time and to end after a certain date and 
					 time and verify that user who created the event is displayed in the Event 
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/2/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@SuppressWarnings("unused")
	@Test
	public void testFTS117340() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral = new General();
		EventNotification objEventNotification = new EventNotification();
		try {
			gstrTCID = "117340"; // Test Case Id
			gstrTO = "Create an event selecting to start at a certain date and time and to end after " +
					 "a certain date and time and verify that user who created the event is displayed in the Event ";// Test
																																								// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetaken = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			

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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
				log4j.info("==========Loged as New User,Create Event and verifying notification=======");
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
						strTempName1, strEveName, strTempDef, false);
				
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
				strSubjName =strEveName;
				strMsgBodyEmail = "Event Notice for " + strUsrFulName_1 + ": \n"
						+ strTempDef +  "\nFrom: " + strUsrFulName_1 + "\nRegion: "+strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required."+ "\n"
						+ propEnvDetails.getProperty("MailURL");
				
			
				strMsgBodyPager = strTempDef +  "\nFrom: "+strUsrFulName_1 +"\nRegion: " + strRegn;
				
				
				strFuncResult = objGeneral.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objGeneral.verifyEmail(selenium, strFrom, strTo,
						strSubjName);
				

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium
							.getText("css=div.fixed.leftAlign");
					
					if (strMsg.equals(strMsgBodyEmail)) {
						intEMailRes++;
						log4j.info("Email body is displayed correctly");
					} else if (strMsg.equals(strMsgBodyPager)) {
						intPagerRes++;
						log4j.info("Pager body is displayed correctly");
					}else{
						log4j.info("Mail body is NOT displayed correctly");
						strFuncResult="Mail body is NOT displayed correctly";
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

				strFuncResult = objGeneral.verifyEmail(selenium, strFrom, strTo,
						strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium
							.getText("css=div.fixed.leftAlign");
					
					
					if (strMsg.equals(strMsgBodyEmail)) {
						intEMailRes++;
						log4j.info("Email body is displayed correctly");
					} else if (strMsg.equals(strMsgBodyPager)) {
						intPagerRes++;
						log4j.info("Pager body is displayed correctly");
					}else{
						log4j.info("Mail body is NOT displayed correctly");
						strFuncResult="Mail body is NOT displayed correctly";
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason =strFuncResult;
				}

				selenium.selectFrame("relative=up");
				selenium.selectFrame("horde_main");
				// click on Back to Inbox
				selenium.click("link=Back to Inbox");
				selenium.waitForPageToLoad("90000");

				strFuncResult = objGeneral.verifyEmail(selenium, strFrom, strTo,
						strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium
							.getText("css=div.fixed.leftAlign");
					if (strMsg.equals(strMsgBodyEmail)) {
						intEMailRes++;
						log4j.info("Email body is displayed correctly");
					} else if (strMsg.equals(strMsgBodyPager)) {
						intPagerRes++;
						log4j.info("Pager body is displayed correctly");
					}else{
						log4j.info("Mail body is NOT displayed correctly");
						strFuncResult="Mail body is NOT displayed correctly";
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason =strFuncResult;
				}
				// check Email, pager notification
				if (intEMailRes == 2 && intPagerRes == 1) {
					gstrResult = "PASS";
				}
				
				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad("90000");
				selenium.close();
				
				selenium.selectWindow("");
				Thread.sleep(10000);
				
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
			gstrTCID = "FTS-117340";
			gstrTO = " Create an event selecting to start at a certain date and time and to end after a " +
					 "certain date and time and verify that user who created the event is displayed in the Event ";
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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}

	/***************************************************************
	'Description	:Create an event selecting to start at a certain date and time and to end never and verify that user 
					who created the event is displayed in the Event notification.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/2/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@SuppressWarnings("unused")
	@Test
	public void testFTS117341() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral = new General();
		EventNotification objEventNotification = new EventNotification();
		try {
			gstrTCID = "117341"; // Test Case Id
			gstrTO = "Create an event selecting to start at a certain date and time and to end never and verify that " +
					"user who created the event is displayed in the Event notification.";// Test
																																								// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetaken = dts.timeNow("hh:mm:ss");
			gstrTimetaken = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
				log4j.info("==========Loged as New User,Create Event and verifying notification=======");
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
				strSubjName =strEveName;
				strMsgBodyEmail = "Event Notice for " + strUsrFulName_1 + ": \n"
						+ strTempDef +  "\nFrom: " + strUsrFulName_1 + "\nRegion: "+strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required."+ "\n"
						+ propEnvDetails.getProperty("MailURL");
				
			
				strMsgBodyPager = strTempDef +  "\nFrom: "+strUsrFulName_1 +"\nRegion: " + strRegn;
				
				
				strFuncResult = objGeneral.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objGeneral.verifyEmail(selenium, strFrom, strTo,
						strSubjName);
				

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium
							.getText("css=div.fixed.leftAlign");
					
					if (strMsg.equals(strMsgBodyEmail)) {
						intEMailRes++;
						log4j.info("Email body is displayed correctly");
					} else if (strMsg.equals(strMsgBodyPager)) {
						intPagerRes++;
						log4j.info("Pager body is displayed correctly");
					}else{
						log4j.info("Mail body is NOT displayed correctly");
						strFuncResult="Mail body is NOT displayed correctly";
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

				strFuncResult = objGeneral.verifyEmail(selenium, strFrom, strTo,
						strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium
							.getText("css=div.fixed.leftAlign");
					
					
					if (strMsg.equals(strMsgBodyEmail)) {
						intEMailRes++;
						log4j.info("Email body is displayed correctly");
					} else if (strMsg.equals(strMsgBodyPager)) {
						intPagerRes++;
						log4j.info("Pager body is displayed correctly");
					}else{
						log4j.info("Mail body is NOT displayed correctly");
						strFuncResult="Mail body is NOT displayed correctly";
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason =strFuncResult;
				}

				selenium.selectFrame("relative=up");
				selenium.selectFrame("horde_main");
				// click on Back to Inbox
				selenium.click("link=Back to Inbox");
				selenium.waitForPageToLoad("90000");

				strFuncResult = objGeneral.verifyEmail(selenium, strFrom, strTo,
						strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium
							.getText("css=div.fixed.leftAlign");
					if (strMsg.equals(strMsgBodyEmail)) {
						intEMailRes++;
						log4j.info("Email body is displayed correctly");
					} else if (strMsg.equals(strMsgBodyPager)) {
						intPagerRes++;
						log4j.info("Pager body is displayed correctly");
					}else{
						log4j.info("Mail body is NOT displayed correctly");
						strFuncResult="Mail body is NOT displayed correctly";
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason =strFuncResult;
				}
				// check Email, pager notification
				if (intEMailRes == 2 && intPagerRes == 1) {
					gstrResult = "PASS";
				}
				
				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad("90000");
				selenium.close();
				
				selenium.selectWindow("");
				Thread.sleep(10000);
				
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
			gstrTCID = "FTS-117341";
			gstrTO = "Create an event selecting to start at a certain date and time and to end never and verify that " +
					"user who created the event is displayed in the Event notification.";
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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}
}

