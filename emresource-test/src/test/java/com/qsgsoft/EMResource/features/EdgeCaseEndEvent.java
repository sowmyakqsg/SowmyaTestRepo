package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

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

/*******************************************************************
' Description		:This class contains test cases from requirement
' Requirement		:End Event
' Requirement Group	:Creating & managing Events 
� Product		    :EMResource v3.23
' Date			    :29/April/2013
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class EdgeCaseEndEvent {

	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EdgeCase_CreateEvent");
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

		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		selenium.start();
		selenium.windowMaximize();

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
	/************************************************************************************
	'Description :Verify that EMResource link is displayed in the Event End notification. 
	'Arguments	 :None
	'Returns	 :None
	'Date		 :29/April/2013
	'Author		 :QSG
	'------------------------------------------------------------------------------------
	'Modified Date				                                             Modified By
	'Date					                                                 Name
	**************************************************************************************/

	@Test
	public void testBugEdgeCase118147() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup = new EventSetup();
		General objGeneral = new General();
		EventNotification objNotif = new EventNotification();
		try {
			gstrTCID = "118147"; // Test Case Id
			gstrTO = "Verify that EMResource link is displayed in the Event End notification.";// TO
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

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			// Search user criteria
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
			String[] strResTypeVal = {};
			String[] strStatusTypeval = {};
			String strEveColor = "Red";
			// Event
			String strEveName = "A" + strTimeText;
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			
			// Mail verification
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1 = "";
			String strSubjName = "";
			String strApplTime="";
					
		log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, "", "", "",
						"", "", strEMail, strEMail,
						strEMail, "");
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
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);
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
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName1, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objNotif.selectEventNofifForUser(selenium,
						strUsrFulName_1, strTempName1, true, false, false);
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
						strTempName1, strEveName, strTempDef, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//New user Login
			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_1, strInitPwd);
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
				strFuncResult = objEventSetup.endEvent(selenium, strEveName);
				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);		
				strFndStHr = strCurentDat[2];
				strFndStMinu =  strCurentDat[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strSubjName = strEveName;
				strMsgBody1 = "Event Notice for "
						+ strUsrFulName_1
						+ "\n"
						+ strEveName
						+ " ended at "
						+ strApplTime
						+ "\nFrom: "
						+ strUsrFulName_1
						+ "\n"
						+ "Region: "
						+ strRegn
						+ "\n\nPlease"
						+ " do not reply to this email message. You must log into EMResource to take any "
						+ "action that may be required." + "\n"
						+ propEnvDetails.getProperty("MailURL");

				selenium.selectWindow("");
				strFuncResult = objGeneral.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objGeneral.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");
					if (strMsg.equals(strMsgBody1)) {
						gstrResult = "PASS";
					} else {

						selenium.selectFrame("relative=up");
						selenium.selectFrame("horde_main");
						// click on Back to Inbox
						selenium.click("link=Back to Inbox");
						selenium.waitForPageToLoad("90000");

						try {
							assertTrue(strFuncResult.equals(""));
							strFuncResult = objGeneral.verifyEmail(selenium,
									strFrom, strTo, strSubjName);
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}

						try {
							assertTrue(strFuncResult.equals(""));
							selenium.selectWindow("Mail");
							selenium.selectFrame("horde_main");
							selenium.click("link=Log out");
							selenium.waitForPageToLoad("90000");
							selenium.close();

							selenium.selectWindow("");
							selenium.selectWindow("");
							selenium.selectFrame("Data");
							gstrResult = "PASS";
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}
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
			gstrTCID = "118147"; // Test Case Id
			gstrTO = "Verify that EMResource link is displayed in the Event End notification.";// TO
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
	'Description :Verify that user who created/edited the event details are displayed in
	              the event notification received for a user after the event ends
	'Arguments	 :None
	'Returns	 :None
	'Date		 :29/April/2013
	'Author		 :QSG
	'------------------------------------------------------------------------------------
	'Modified Date				                                             Modified By
	'Date					                                                 Name
	**************************************************************************************/

	@Test
	public void testBugEdgeCase117972() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup = new EventSetup();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		General objMail=new General();
		EventNotification objNotif = new EventNotification();
		try {
			gstrTCID = "117972"; // Test Case Id
			gstrTO = "Verify that user who created/edited the event details are displayed in the event " +
					"notification received for a user after the event ends";// TO
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
			// RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			// Search user criteria
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
			String strEveColor = "Red";
			// Event
			String strEveName = "A" + strTimeText;
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strStartDate="";
			
			String strFndStMnth="";
			String strFndStYear="";
			String strFndStDay="";
			String strFndStHr="";
			String strFndStMinu="";
			
			String strFrom="notification@emsystems.com";
			String strTo=strLoginName;
			String strMsgBody1="";
			String strSubjName="";
			String strApplTime="";
			
			
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
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, "", "", "",
						"", "", strEMail, strEMail,
						strEMail, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);
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
				String[] strResTypeVal = {strRTValue[0]};
				String[] strStatusTypeval = {strSTvalue[0],strSTvalue[1],strSTvalue[2],strSTvalue[3]};
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName1, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objNotif.selectEventNofifForUser(selenium,
						strUsrFulName_1, strTempName1, true, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//New user Login
			try {
				assertTrue(strFuncResult.equals(""));
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
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEvent(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.endEvent(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				// Fetch Application time
				String strCurentDat[] = objMail.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);		
				strFndStHr = strCurentDat[2];
				strFndStMinu =  strCurentDat[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strSubjName = strEveName;
				strMsgBody1 = "Event Notice for "
						+ strUsrFulName_1
						+ "\n"
						+ strEveName
						+ " ended at "
						+ strApplTime
						+ "\nFrom: "
						+ strUsrFulName_1
						+ "\n"
						+ "Region: "
						+ strRegn
						+ "\n\nPlease"
						+ " do not reply to this email message. You must log into EMResource to take any "
						+ "action that may be required." + "\n"
						+ propEnvDetails.getProperty("MailURL");

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = selenium.getText("css=div.fixed.leftAlign");
					if (strMsg.equals(strMsgBody1)) {
						gstrResult = "PASS";
					} else {

						selenium.selectFrame("relative=up");
						selenium.selectFrame("horde_main");
						// click on Back to Inbox
						selenium.click("link=Back to Inbox");
						selenium.waitForPageToLoad("90000");

						try {
							assertTrue(strFuncResult.equals(""));
							strFuncResult = objMail.verifyEmail(selenium,
									strFrom, strTo, strSubjName);
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}

						try {
							assertTrue(strFuncResult.equals(""));
							selenium.selectWindow("Mail");
							selenium.selectFrame("horde_main");
							selenium.click("link=Log out");
							selenium.waitForPageToLoad("90000");
							selenium.close();

							selenium.selectWindow("");
							selenium.selectWindow("");
							selenium.selectFrame("Data");
							
							gstrResult = "PASS";
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}
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
			gstrTCID = "117972"; // Test Case Id
			gstrTO = "Verify that user who created/edited the event details are displayed in the event " +
					"notification received for a user after the event ends";// TO
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
	'Description :Verify that address is displayed in the event notification received while an event
	              which is created selecting a template with 'Mandatory address' is ended.
	'Arguments	 :None
	'Returns	 :None
	'Date		 :30/April/2013
	'Author		 :QSG
	'-----------------------------------------------------------------------------------------------
	'Modified Date				                                                      Modified By
	'Date					                                                          Name
	************************************************************************************************/

	@Test
	public void testEdgeCase119034() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		General objMail = new General();
		CreateUsers objCreateUsers=new CreateUsers();
		EventSetup objEventSetup = new EventSetup();
		EventNotification objEventNotification = new EventNotification();
		try {
			gstrTCID = "119034"; // Test Case Id
			gstrTO = "Verify that address is displayed in the event notification received while an event" +
					" which is created selecting a template with 'Mandatory address' is ended.";// TO
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
			
			// time data
			String strApplTime="";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			String strStartDate = "";
			String strStartDateEnd = "";

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
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(
						seleniumPrecondition, strTempName1, strEveName,
						strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.fillonlyAddressFieldsOfEvent(
						seleniumPrecondition, strCity, strState, strCounty,
						false, strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertTrue(strFuncResult.equals(""));

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
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(
						seleniumPrecondition, strEveName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Notification for user
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
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
			
			
			// Edit Event template
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventSetupPge(selenium);
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
						.selAndDeselMandChkBoxForEventTemp(
								selenium, strTempName1, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//end Event  
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.endEvent(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				// Fetch Application time
				String strCurentDat[] = objMail.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);
				
				
				strFndStHr = strCurentDat[2];
				strFndStMinu =  strCurentDat[3];
				
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
				
				strStartDateEnd = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "MM/dd/yyyy");
				strStartDateEnd = strStartDateEnd + " " + strFndStHr + ":"
						+ strFndStMinu;
				
				String strDesc = "Event ended." + "\n" + "Location: " + strCity
						+ " KS " + strCounty;
				String strEveNames = "End of "+strEveName;
				
				strFuncResult = objEventNotification.ackWebNotification(
						selenium, strEveNames, strDesc, strStartDate);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strMsgBody_EventNot1 = strEveName + " ended at " + strStartDateEnd
					+ "\n" + "Location: " + strCity + ", KS  " + strCounty
					+ "\n" + "From: " + strUsrFulName_1 + "\n" + "Region: "
					+ strRegn;

			strMsgBody_EventNot2 = "Event Notice for "
					+ strUserName_1
					+ ": "
					+ "\n"
					+ strEveName
					+ " ended at "
					+ strStartDateEnd
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

				strSubjName = "End of "+strEveName;

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
			gstrTCID = "119034"; // Test Case Id
			gstrTO = "Verify that address is displayed in the event notification received while an event" +
					" which is created selecting a template with 'Mandatory address' is ended.";// TO
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
	'Description :Verify that the event end notification does not include the Location when 'Mandatory
	              Address' is deselected for the template before the event is ended. 
	'Arguments	 :None
	'Returns	 :None
	'Date		 :20/05/2013
	'Author		 :QSG
	'-----------------------------------------------------------------------------------------------
	'Modified Date				                                                      Modified By
	'Date					                                                          Name
	************************************************************************************************/

	@Test
	public void testEdgecase119037() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup = new EventSetup();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();
		try {
			gstrTCID = "119037"; // Test Case Id
			gstrTO = "Verify that the event end notification does not include the Location when 'Mandatory"
					+ " Address' is deselected for the template before the event is ended. .";// TO
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
			String strEveName1 = "AutoEve_1" + strTimeText;
			String strCity = "Banglore";
			String strState = "Kansas";
			String strCounty = "Allen County";

			String strApplTime = "";
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			String strStartDateEnd = "";

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

			// Event_1 Creation
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(
						seleniumPrecondition, strTempName1, strEveName1,
						strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.fillonlyAddressFieldsOfEvent(
						seleniumPrecondition, strCity, strState, strCounty,
						false, strEveName1, false);
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

			// Notification for user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
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
			// Edit Event Template
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
			// End Event1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.endEvent(selenium, strEveName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				// Fetch Application time
				String strCurentDat[] = objMail.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"MM/dd/yyyy");
				System.out.println(strApplTime);

				strFndStHr = strCurentDat[2];
				strFndStMinu = strCurentDat[3];

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

				strStartDateEnd = dts
						.converDateFormat(strFndStYear + strFndStMnth
								+ strFndStDay, "yyyyMMMdd", "MM/dd/yyyy");
				strStartDateEnd = strStartDateEnd + " " + strFndStHr + ":"
						+ strFndStMinu;

				String strDesc = "Event ended." + "\n" + "Location: " + strCity
						+ " KS " + strCounty;
				String strEveNames = "End of " + strEveName1;

				strFuncResult = objEventNotification.ackWebNotification(
						selenium, strEveNames, strDesc, strStartDateEnd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			strMsgBody_EventNot1 = strEveName1 + " ended at " + strApplTime
					+ strStartDateEnd + "\n" + "Location: " + strCity
					+ ", KS  " + strCounty + "\n" + "From: " + strUsrFulName_1
					+ "\n" + "Region: " + strRegn;

			strMsgBody_EventNot2 = "Event Notice for "
					+ strUserName_1
					+ ": "
					+ "\n"
					+ strEveName1
					+ " ended at "
					+ strApplTime
					+ strStartDateEnd
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

				strSubjName = "End of " + strEveName1;

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

							String[] strMails = objMail.enodeToUnicode(
									selenium, strMsg, 160);
							strMsg = strMails[1];

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
			gstrTCID = "119037"; // Test Case Id
			gstrTO = "Verify that the event end notification does not include the Location when 'Mandatory"
					+ " Address' is deselected for the template before the event is ended. .";// TO
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
