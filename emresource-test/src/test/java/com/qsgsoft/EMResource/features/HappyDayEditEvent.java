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

/*******************************************************************
' Description		:This class contains test cases from requirement
' Requirement		:Edit Event
' Requirement Group	:Creating & managing Events 
� Product		    :EMResource v3.23
' Date			    :26/April/2013
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/
public class HappyDayEditEvent {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.HappyDay_EditEvent");
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
	Selenium selenium,seleniumPrecondition;
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
		selenium.start();
		selenium.windowMaximize();
				seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
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
		selenium.stop();
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
		}
		seleniumPrecondition.stop();

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
	
	/***************************************************************************************
	'Description :Edit an event template and select 'Mandatory Address' and verify that City,
	              State and County are required when a new event is created under it and is
	              non mandatory when an already created event is edited.
	'Arguments	 :None
	'Returns	 :None
	'Date		 :26/April/2013
	'Author		 :QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                                 Modified By
	'Date					                                                      Name
	*****************************************************************************************/
	@Test
	public void testHapyDay119851() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();//object of class Login
		EventSetup objEventSetup = new EventSetup();//object of class EventSetup
		try {
			gstrTCID = "119851"; // Test Case Id
			gstrTO = "Edit an event template and select 'Mandatory Address' and verify that City," +
					" State and County are required when a new event is created under it and is " +
					"non mandatory when an already created event is edited.";// TO
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
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			String strEventNameNew = "Eve_New" + strTimeText;
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
				strFuncResult = objEventSetup
						.CreateETByMandFieldsByAdminAndUser(seleniumPrecondition,
								strTempName1, strTempDef, false, true);
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
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName1, strEveName, strTempDef, false);
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~Test Case  " + gstrTCID+ " EXECUTION Starts~~~~~");
			
			try {
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selAndDeselMandChkBoxForEventTemp(selenium,
								strTempName1, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// 1. Already created event is edited
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
						.chkAddressFieldsNonMandForEvent(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savEvent(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// New event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName1, strEventNameNew, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.chkAddressFieldsMandForEvent(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.fillonlyAddressFieldsOfEvent(
						selenium, strCity, strState, strCounty, false,
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
			gstrTCID = "119851"; // Test Case Id
			gstrTO = "Edit an event template and select 'Mandatory Address' and verify that City," +
					" State and County are required when a new event is created under it and is " +
					"non mandatory when an already created event is edited.";// TO
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
		
	/************************************************************************************
	'Description :Edit an event template and deselect 'Mandatory Address' and verify that
	              City, State and County are non mandatory when a new event is created under
	               it and is mandatory when an already created event is opened for editing
	'Arguments	 :None
	'Returns	 :None
	'Date		 :26/April/2013
	'Author		 :QSG
	'-------------------------------------------------------------------------------------
	'Modified Date				                                              Modified By
	'Date					                                                  Name
	**************************************************************************************/
	@Test
	public void testHapyDay119911() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();//object of class Login
		EventSetup objEventSetup = new EventSetup();//object of class EventSetup
		try {
			gstrTCID = "119911"; // Test Case Id
			gstrTO = "Edit an event template and deselect 'Mandatory Address' and verify that City, " +
					"State and County are non mandatory when a new event is created under it and is " +
					"mandatory when an already created event is opened for editing";// TO
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
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			String strEventNameNew = "Eve_New" + strTimeText;
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
						.selAndDeselMandChkBoxForEventTemp(seleniumPrecondition,
								strTempName1, true, false);
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
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName1, strEveName, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.fillonlyAddressFieldsOfEvent(
						seleniumPrecondition, strCity, strState, strCounty, false,
						strEveName, true);
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
			// deselect 'Mandatory Address'
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selAndDeselMandChkBoxForEventTemp(selenium,
								strTempName1, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// 1. Already created event is opened for editing
			
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
				strFuncResult = objEventSetup.chkAddressFieldsMandForEvent(selenium);
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
			
			// New event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName1, strEventNameNew, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.chkAddressFieldsNonMandForEvent(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEventNameNew, true);
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
			gstrTCID = "119911"; // Test Case Id
			gstrTO = "Edit an event template and deselect 'Mandatory Address' and verify that City, " +
					"State and County are non mandatory when a new event is created under it and is " +
					"mandatory when an already created event is opened for editing";// TO
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
	'Description :Edit a future event and provide to start at a new time and verify that 
	'			 user's full name is displayed in the Event notification received after the event starts.
	'Arguments	 :None
	'Returns	 :None
	'Date		 :2/May/2013
	'Author		 :QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                                 Modified By
	'Date					                                                      Name
	*****************************************************************************************/
	@Test
	public void testHapyDay117570() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();//object of class Login
		EventSetup objEventSetup = new EventSetup();//object of class EventSetup
		General objGeneral = new General();//object of class General
		CreateUsers objCreateUsers=new CreateUsers();//object of class CreateUsers
		EventNotification objEventNotification =new EventNotification();//object of class EventNotification
		try {
			gstrTCID = "117570"; // Test Case Id
			gstrTO = "Edit a future event and provide to start at a new time and " +
					"verify that user's full name is displayed in the Event notification" +
					" received after the event starts.";// TO
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
			String strEditEveName="";			
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
			String strStartDate="";
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");

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
						true);
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
						10, "MMM/d/yyyy HH:mm").split("/");
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
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strEditEveName = "EDIT"+strEveName;
				strFuncResult = objEventSetup.editEvent(selenium, strEveName,
						strEditEveName, strInfo, false);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				// Fetch Application time
				strCurentDat = objGeneral.getSnapTime(selenium);
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

				strFuncResult = objEventSetup.selectStartAndEndDate(selenium,
						strEveName, strStartMonth, strStartDay, strStartYear,
						strStartHour, strStartMinute, "", "", "", "", "", true,
						false);
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
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEditEveName, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			// get Start Date
			String strFndStMnth = selenium.getSelectedLabel(propElementDetails
					.getProperty("Event.CreateEve.StartMnt"));
			String strFndStDay = selenium.getSelectedLabel(propElementDetails
					.getProperty("Event.CreateEve.StartDay"));
			String strFndStYear = selenium.getSelectedLabel(propElementDetails
					.getProperty("Event.CreateEve.StartYear"));

			// get Start Time
			String strFndStHr = selenium.getSelectedLabel(propElementDetails
					.getProperty("Event.CreateEve.StartHour"));
			String strFndStMinu = selenium.getSelectedLabel(propElementDetails
					.getProperty("Event.CreateEve.StartMinut"));
			
			selenium.click("css=input[value='Cancel']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			
			// Fetch Application time
			String strApplnWaitTime[] = objGeneral.getSnapTime(selenium);
			int intTimeDiference = dts.getTimeDiffWithTimeFormatInOurTime(strStartHour + ":"
					+ strStartMinute, 
					strApplnWaitTime[2] + ":" + strApplnWaitTime[3], "HH:mm", 60000);
			
			if(intTimeDiference>0){
				Thread.sleep(intTimeDiference*60000);
				Thread.sleep(20000);
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
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strFuncResult = objEventNotification.ackWebNotification(
						selenium, "Update 1: " + strEditEveName, strInfo, strStartDate);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(30000);
				strSubjName = "Update 1: "+strEditEveName;
				strMsgBodyEmail = "Event Notice for " + strUsrFulName_1 + ": \n"
						+ strInfo +  "\nFrom: " + strUsrFulName_1 + "\nRegion: "+strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required."+ "\n"
						+ propEnvDetails.getProperty("MailURL");			
				strMsgBodyPager = strInfo +  "\nFrom: "+strUsrFulName_1 +"\nRegion: " + strRegn;				
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
		
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "117570"; // Test Case Id
			gstrTO = "Edit a future event and provide to start at a new time and " +
					"verify that user's full name is displayed in the Event " +
					"notification received after the event starts.";// TO
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
