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
' Requirement		:Create Event
' Requirement Group	:Creating & managing Events 
� Product		    :EMResource v3.23
' Date			    :26/April/2013
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class HappyDayCreateEvent {

	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.HappyDay_CreateEvent");
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

		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		
		selenium.start();
		selenium.windowMaximize();
		selenium.setTimeout("");
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		seleniumPrecondition.setTimeout("");

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
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
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
	
	/***************************************************************************************
	'Description :Create an event template selecting mandatory address check box and verify 
	             that City, State and County are required when an event is created under it. 
	'Arguments	 :None
	'Returns	 :None
	'Date		 :26/April/2013
	'Author		 :QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                                 Modified By
	'Date					                                                      Name
	*****************************************************************************************/
	@Test
	public void testHapyDay117144() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();//object of class Login
		EventSetup objEventSetup = new EventSetup();//object of class EventSetup
		try {
			gstrTCID = "117144"; // Test Case Id
			gstrTO = "Create an event template selecting mandatory address check box and verify that"
					+ " City, State and County are required when an event is created under it.";// TO
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
			String strCity = "Banglore";
			String strState = "Kansas";
			String strCounty = "Allen County";
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Event template Creation
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
				strFuncResult = objEventSetup.CreateETByMandFields(selenium,
						strTempName1, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selAndDeselMandChkBoxForEventTemp(selenium,
								strTempName1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.saveAndNavToEvenTNotPreferencesPage(selenium,
								strTempName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(
						selenium, strTempName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event Creation
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
				strFuncResult = objEventSetup
						.chkAddressFieldsMandForEvent(selenium);
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
				strFuncResult = objEventSetup
						.chkErrorMsgForMandAddress(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.fillonlyAddressFieldsOfEvent(
						selenium, strCity, strState, strCounty, false,
						strEveName, false);
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
			gstrTCID = "117144"; // Test Case Id
			gstrTO = "Create an event template selecting mandatory address check box and verify that "
					+ "City, State and County are required when an event is created under it. ";// TO
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
	public void testHapyDay118243() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();//object of class Login
		EventSetup objEventSetup = new EventSetup();//object of class EventSetup
		CreateUsers objCreateUsers = new CreateUsers();//object of class CreateUsers
		General objGeneral = new General();//object of class General
		EventNotification objEventNotification = new EventNotification();//object of class EventNotification
		try {
			gstrTCID = "118243"; // Test Case Id
			gstrTO = "Create an event selecting to start immediately"
					+ " and to end after a certain number of hours and "
					+ "verify that user who created the event is displayed "
					+ "in the Event notification.";// TO
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
			String strEndMinute = "";
			String strEndHour = "";
			String strEndYear = "";
			String strEndDay = "";
			String strEndMonth = "";
			String strApplTime = "";
			String strCurentDat[] = new String[5];
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
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

			log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");

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
				blnLogin = true;
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
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);
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

			// Event template Creation
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
				strFuncResult = objEventSetup
						.CreateETByMandFieldsByAdminAndUser(selenium,
								strTempName1, strTempDef, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventNotification.selectEventNofifForUser(
						selenium, strUsrFulName_1, strTempName1, true, true,
						false);
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

				String strSEndTime[] = dts.addTimetoExistingHour(strApplTime,
						10, "MMM/d/yyyy HH:mm").split("/");
				strEndDay = strSEndTime[1];
				strEndMonth = strSEndTime[0];

				strSEndTime = strSEndTime[2].split(" ");
				strEndYear = strSEndTime[0];
				strSEndTime = strSEndTime[1].split(":");

				strEndMinute = strSEndTime[1];
				strEndHour = strSEndTime[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.selectStartAndEndDate(selenium,
						strEveName, "", "", "", "", "", strEndMonth, strEndDay,
						strEndYear, strEndHour, strEndMinute, false, false);
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
					gstrResult = "PASS";
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
		
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118243";
			gstrTO = "Create an event selecting to start immediately and " +
					"to end after a certain number of hours and verify " +
					"that user who created the event is displayed in the" +
					" Event notification.";
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

	/***************************************************************************
	'Description :Verify that address fields are not mandatory if 'Mandatory address'
	              is selected for an event template after the event was created
	'Arguments	 :None
	'Returns	 :None
	'Date		 :30/April/2013
	'Author		 :QSG
	'---------------------------------------------------------------------------
	'Modified Date				                                     Modified By
	'Date					                                         Name
	****************************************************************************/
	@Test
	public void testHapyDay119912() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();//object of class Login
		EventSetup objEventSetup = new EventSetup();//object of class EventSetup
		try {
			gstrTCID = "119912"; // Test Case Id
			gstrTO = "Verify that address fields are not mandatory if 'Mandatory address' is " +
					"selected for an event template after the event was created";// TO
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
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName1, strEveName, strTempDef, true);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
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
				strFuncResult = objEventSetup.chkAddressFieldsNonMandForEvent(selenium);
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
			gstrTCID = "119912"; // Test Case Id
			gstrTO = "Verify that address fields are not mandatory if 'Mandatory address' is " +
					"selected for an event template after the event was created";// TO
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
	
	/***********************************************************************************************
	'Description :Verify that event notification displays the address provided for an event which is
	              created selecting a template for which �Mandatory Address� check box is selected. 
	'Arguments	 :None
	'Returns	 :None
	'Date		 :30/April/2013
	'Author		 :QSG
	'-----------------------------------------------------------------------------------------------
	'Modified Date				                                                      Modified By
	'Date					                                                          Name
	************************************************************************************************/
	@Test
	public void testHapyDay119026() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup = new EventSetup();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();
		try {
			gstrTCID = "119026"; // Test Case Id
			gstrTO = "Verify that event notification displays the address provided for an event which"
					+ " is created selecting a template for which �Mandatory Address� check box is selected.";// TO
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
			// User
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";
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
			String strCity = "Banglore";
			String strState = "Kansas";
			String strCounty = "Allen County";
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strSubjName = "";
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody_EventNot1 = "";
			String strMsgBody_EventNot2 = "";
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;

			log4j.info("~~~~~TEST-CASE" + gstrTCID
					+ " PRECONDITION STATRTS~~~~~");

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
				blnLogin = true;
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName,
						strLastName, strOrganization, strPhoneNo,
						strPrimaryEMail, strEMail, strPagerValue,
						strAdminComments);
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
			// Event template Creation
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
				strFuncResult = objEventSetup.CreateETByMandFields(
						seleniumPrecondition, strTempName1, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selAndDeselMandChkBoxForEventTemp(
								seleniumPrecondition, strTempName1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.saveAndNavToEvenTNotPreferencesPage(
								seleniumPrecondition, strTempName1);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventNotification.selectEventNofifForUser(
						seleniumPrecondition, strUsrFulName_1, strTempName1,
						true, true, true);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Event Creation
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
				strFuncResult = objEventSetup.fillonlyAddressFieldsOfEvent(
						selenium, strCity, strState, strCounty, false,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertTrue(strFuncResult.equals(""));

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
				gstrReason = gstrReason + " " + strFuncResult;
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
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strDesc = strTempDef + "\n" + "Location: " + strCity
						+ " KS " + strCounty;
				strFuncResult = objEventNotification.ackWebNotification(
						selenium, strEveName, strDesc, strStartDate);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody_EventNot1 = strTempDef + "\n" + "Location: " + strCity
					+ ", KS  " + strCounty + "\n" + "From: " + strUsrFulName_1
					+ "\n" + "Region: " + strRegn;

			strMsgBody_EventNot2 = "Event Notice for "
					+ strUserName_1
					+ ": "
					+ "\n"
					+ strTempDef
					+ "\n"
					+ "Location: "
					+ strCity
					+ ", KS  " 
					+ strCounty
					+ "\n"
					+ "From: "
					+ strUserName_1
					+ "\nRegion: "
					+ strRegn
					+ "\n\nPlease do not reply to this email message. You must "
					+ "log into EMResource to take any action that may be required."
					+ "\n" + propEnvDetails.getProperty("MailURL");

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(30000);

				strSubjName = strEveName;

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					for (int i = 0; i < 3; i++) {
						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");

							String[] strMails=objMail.enodeToUnicode(selenium, strMsg, 160);
							strMsg=strMails[1];
							
							if (strMsg.equals(strMsgBody_EventNot1)) {
								intPagerRes++;
								log4j.info("Pager body is displayed.");
							} else if (strMsg.equals(strMsgBody_EventNot2)) {
								intEMailRes++;
								log4j.info("Email body is displayed.");
							} else {
								log4j.info("Email and Pager body not displayed.");
								strFuncResult = "Email and Pager body not displayed.";
								gstrReason = gstrReason + " " + strFuncResult;
							}
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}
						selenium.selectFrame("relative=up");
						selenium.selectFrame("horde_main");
						// click on Back to Inbox
						selenium.click("link=Back to Inbox");
						selenium.waitForPageToLoad("90000");
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad("90000");
				selenium.close();
				selenium.selectWindow("");
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				try {
					assertTrue(strFuncResult.equals(""));
					if (intResCnt == 1 && intEMailRes == 2 && intPagerRes == 1) {
						gstrResult = "PASS";
					}
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "119026"; // Test Case Id
			gstrTO = "VVerify that event notification displays the address provided for an event which"
					+ " is created selecting a template for which �Mandatory Address� check box is selected.";// TO
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

	/***********************************************************************************************
	'Description :Verify that event notification does not display the address provided for an event 
	   			  which is created selecting a template for which �Mandatory Address� checkbox is not selected.
	'Arguments	 :None
	'Returns	 :None
	'Date		 :30/April/2013
	'Author		 :QSG
	'-----------------------------------------------------------------------------------------------
	'Modified Date				                                                      Modified By
	'Date					                                                          Name
	************************************************************************************************/
	@Test
	public void testHapyDay119027() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();//object of class Login
		CreateUsers objCreateUsers=new CreateUsers();//object of class CreateUsers
		EventSetup objEventSetup = new EventSetup();//object of class EventSetup
		General objMail = new General();//object of class General
		EventNotification objEventNotification = new EventNotification();//object of class EventNotificatio
		try {
			gstrTCID = "119027"; // Test Case Id
			gstrTO = "Verify that event notification does not display the address provided for an event which " +
					"is created selecting a template for which �Mandatory Address� checkbox is not selected.";// TO
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
			//User
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";
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
			String strCity = "Banglore";
			String strState = "Kansas";
			String strCounty = "Allen County";
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strSubjName = "";
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody_EventNot1 = "";
			String strMsgBody_EventNot2 = "";
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;
							
		log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION STATRTS~~~~~");

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
				blnLogin = true;
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName,
						strLastName, strOrganization, strPhoneNo,
						strPrimaryEMail, strEMail, strPagerValue,
						strAdminComments);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventNotification.selectEventNofifForUser(
						seleniumPrecondition, strUsrFulName_1, strTempName1,
						true, true, true);
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
		try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Event Creation
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
				strFuncResult = objEventSetup.fillonlyAddressFieldsOfEvent(
						selenium, strCity, strState, strCounty, false,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertTrue(strFuncResult.equals(""));

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
				gstrReason = gstrReason + " " + strFuncResult;
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
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				String strDesc = strTempDef;

				strFuncResult = objEventNotification.ackWebNotification(
						selenium, strEveName, strDesc, strStartDate);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody_EventNot1 = strTempDef + "\n" + "From: " + strUserName_1
					+ "\nRegion: " + strRegn;

			strMsgBody_EventNot2 = "Event Notice for "
					+ strUserName_1
					+ ": "
					+ "\n"
					+ strTempDef
					+ "\n"
					+ "From: "
					+ strUserName_1
					+ "\nRegion: "
					+ strRegn
					+ "\n\nPlease do not reply to this email message. You must "
					+ "log into EMResource to take any action that may be required."
					+ "\n" + propEnvDetails.getProperty("MailURL");

			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(30000);

				strSubjName = strEveName;

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					for (int i = 0; i < 3; i++) {

						strFuncResult = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);
						try {
							assertTrue(strFuncResult.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							
							if (strMsg.equals(strMsgBody_EventNot1)) {
								intPagerRes++;
								log4j.info("Pager body is displayed.");
							} else if (strMsg.equals(strMsgBody_EventNot2)) {
								intEMailRes++;
								log4j.info("Email body is displayed.");
							} else {
								log4j.info("Email and Pager body not displayed.");
								strFuncResult = "Email and Pager body not displayed.";
								gstrReason = gstrReason + " " + strFuncResult;
							}
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}

						selenium.selectFrame("relative=up");
						selenium.selectFrame("horde_main");
						// click on Back to Inbox
						selenium.click("link=Back to Inbox");
						selenium.waitForPageToLoad("90000");
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad("90000");
				selenium.close();
				selenium.selectWindow("");
				selenium.selectWindow("");
				selenium.selectFrame("Data");

				try {
					assertTrue(strFuncResult.equals(""));
					if (intResCnt == 1 && intEMailRes == 2 && intPagerRes == 1) {
						gstrResult = "PASS";
					}
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "119027"; // Test Case Id
			gstrTO = "Verify that event notification does not display the address provided for an event which " +
					"is created selecting a template for which �Mandatory Address� checkbox is not selected.";// TO
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
	
	/****************************************************************************
	'Description :Verify that address is displayed in the �Event Banner� when an  
	              event is created selecting a template with �Mandatory Address�.
	'Arguments	 :None
	'Returns	 :None
	'Date		 :30/April/2013
	'Author		 :QSG
	'----------------------------------------------------------------------------
	'Modified Date				                                      Modified By
	'Date					                                           Name
	*****************************************************************************/
	@Test
	public void testHapyDay118980() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();//object of class Login
		CreateUsers objCreateUsers = new CreateUsers();//object of class CreateUsers
		EventSetup objEventSetup = new EventSetup();//object of class EventSetup
		EventBanner objEventBanner = new EventBanner();//object of class EventBanner
		try {
			gstrTCID = "118980"; // Test Case Id
			gstrTO = "Verify that address is displayed in the �Event Banner� when an event "
					+ "is created selecting a template with �Mandatory Address�.";// TO
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
			// User
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
			String strEventValue = "";
			String strCity = "Banglore";
			String strState = "Kansas";
			String strCounty = "Allen County";
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			String strETValue="";
			log4j.info("~~~~~TEST-CASE" + gstrTCID
					+ " PRECONDITION STARTS~~~~~");

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
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
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Event template Creation
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
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
				strFuncResult = objEventSetup.CreateETByMandFields(selenium,
						strTempName1, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selAndDeselMandChkBoxForEventTemp(selenium,
								strTempName1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(
						selenium, strTempName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(selenium, strTempName1);				
				if(strETValue.compareTo("")==0){
					strFuncResult="Failed to fetch ET value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			
			// Event Creation
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
				strFuncResult = objEventSetup.fillonlyAddressFieldsOfEvent(
						selenium, strCity, strState, strCounty, false,
						strEveName, false);
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
					strFuncResult = "Function failes to fetch Event Value";
				}
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
				assertTrue(strFuncResult.equals(""));

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
				gstrReason = gstrReason + " " + strFuncResult;
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
				strFuncResult = objEventBanner.clickEventWithFocusOnEveBanner(
						selenium, strEveName);
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "MM/dd/yy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				String strlocData = "Created By: " + strUsrFulName_1 + " @ "
						+ strStartDate + " "
						+ propEnvDetails.getProperty("TimeZone") + "\n"
						+ strTempDef + " " + "Location:  " + strCity + " KS "
						+ strCounty;
				strFuncResult = objEventBanner.VarAddressOnEveBanner(selenium,
						strEventValue, strlocData);

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
			gstrTCID = "118980"; // Test Case Id
			gstrTO = "Verify that address is displayed in the �Event Banner� when an event "
					+ "is created selecting a template with �Mandatory Address�.";// TO
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
	'Description :Verify that address is not displayed in the �Event Banner� when an event is
	              created selecting a template for which �Mandatory Address� is not selected.
	'Arguments	 :None
	'Returns	 :None
	'Date		 :30/April/2013
	'Author		 :QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                                  Modified By
	'Date					                                                      Name
	*****************************************************************************************/
	@Test
	public void testHapyDay118981() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();//object of class Login
		CreateUsers objCreateUsers = new CreateUsers();//object of class CreateUsers
		EventSetup objEventSetup = new EventSetup();//object of class EventSetup
		EventBanner objEventBanner = new EventBanner();//object of class EventBanner
		try {
			gstrTCID = "118981"; // Test Case Id
			gstrTO = "Verify that address is not displayed in the �Event Banner� when an event "
					+ "is created selecting a template for which �Mandatory Address� is not selected.";// TO
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
			// User
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
			String strEventValue = "";
			String strCity = "Banglore";
			String strState = "Kansas";
			String strCounty = "Allen County";
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			log4j.info("~~~~~TEST-CASE" + gstrTCID
					+ " PRECONDITION STARTS~~~~~");

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
				blnLogin = true;
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Event template Creation
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
				strFuncResult = objEventSetup.CreateETByMandFields(selenium,
						strTempName1, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(
						selenium, strTempName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event Creation
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
				strFuncResult = objEventSetup.fillonlyAddressFieldsOfEvent(
						selenium, strCity, strState, strCounty, false,
						strEveName, false);
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
					strFuncResult = "Function failes to fetch Event Value";
				}
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
				assertTrue(strFuncResult.equals(""));

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
				gstrReason = gstrReason + " " + strFuncResult;
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
				strFuncResult = objEventBanner.clickEventWithFocusOnEveBanner(
						selenium, strEveName);
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "MM/dd/yy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				String strlocData = "Created By: " + strUsrFulName_1 + " @ "
						+ strStartDate + " "
						+ propEnvDetails.getProperty("TimeZone") + "\n"
						+ strTempDef + " " + "Location:  " + strCity + " KS "
						+ strCounty;
				strFuncResult = objEventBanner.VarAddressOnEveBanner(selenium,
						strEventValue, strlocData);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("Address is NOT displayed in the �Event Banner�.",
						strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118981"; // Test Case Id
			gstrTO = "Verify that address is not displayed in the �Event Banner� when an event "
					+ "is created selecting a template for which �Mandatory Address� is not selected.";// TO
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
