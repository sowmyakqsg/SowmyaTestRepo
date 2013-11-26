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

/*******************************************************************
' Description       :This class includes  requirement testcases
' Requirement Group :Creating & managing Events
' Requirement       :Create event
' Date		        :16-Jan-2013
' Author	        :QSG
'-------------------------------------------------------------------
' Modified Date                                        Modified By
' <Date>                           	                   <Name>
'*******************************************************************/
public class FTSCreateEvent_AutoIT {

	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSCreateEvent_AutoIT");
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

	Selenium selenium;

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
	'Description	:Verify that an event can be created by attaching .html files.
	'Arguments		:None
	'Returns		:None
	'Date	 		:23-Jan-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS118093() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		EventNotification objEventNotification = new EventNotification();
		Roles objRoles = new Roles();
		General objMail = new General();
		Date_Time_settings dts = new Date_Time_settings();
		EventList objEventList = new EventList();
		Views objViews = new Views();

		try {
			gstrTCID = "118093";
			gstrTO = "Verify that an event can be created by attaching .html files.";
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

			String statTypeName1 = "ST_1" + strTimeText;
			String statTypeName2 = "ST_2" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";
			String strSTvalue[] = new String[2];
			String[] strStatTypeArr = { statTypeName1, statTypeName2 };

			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTvalue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");

			String strUploadFilePath = pathProps
					.getProperty("CreateEve_UploadHtmlFile_OpenPath");

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
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			String strMsgBodyEmail = "";
			String strMsgBodyPager = "";
			String strSubjName = "";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			String strStartDate = "";
			String strEndDate = "";

			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;

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
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTypeName1);
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
						selenium, strStatusTypeValue, statTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTypeName2);
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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName);
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" } };
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(selenium, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError ae) {
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
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
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
				strRTvalue = objRT.fetchResTypeValueInResTypeList(selenium,
						strResrctTypName);
				if (strRTvalue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Resource RS is created selecting RT.
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
			 * 5. User 'U1' is created providing primary e-mail, e-mail and
			 * pager addresses and providing following rights.
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
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");

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
			 * 4. Event template 'ET' is created selecting status types ST1 ,ST2
			 * and RT and selecting the check boxes E-mail, Pager and Web for
			 * user U1.
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

				String[] strResTypeValue = { strRTvalue };
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventNotification.selectEventNofifForUser(
						selenium, strUsrFulName_1, strTempName, true, true,
						true);
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
			 * 2 Login as user U1, navigate to Event >> Event Management. 'Event
			 * Management' screen is displayed.
			 */

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

			/*
			 * 3 Click on 'Create a New event' button 'Create New Event' screen
			 * is displayed.
			 */

			/*
			 * 4 Create an event EVE providing mandatory data, attaching an html
			 * file. Event is listed in the 'Event Management' screen.
			 * 
			 * User 'U1' receives web,e-mail and pager notifications.
			 * 
			 * In the e-mail and pager notifications received, the message 'See
			 * attached document at https://emresource-test.emsystem.com' is
			 * displayed.
			 */

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(selenium,
						strTempName, strResource, strEveName, strInfo, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.uploadFilesInCreateEventPage(
						selenium, strAutoFilePath, strAutoFileName,
						strUploadFilePath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup
						.saveAndvrfyEventAlongWithPageName(selenium,
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

				strFuncResult = objEventSetup.navEditEventPage(selenium,
						strEveName);
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

				strFuncResult = objEventSetup
						.cancelAndNavToEventManagementScreen(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strEndDate = dts.AddDaytoExistingDate(strStartDate, 1,
						"M/d/yyyy");

				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strEndDate = strEndDate + " " + strFndStHr + ":" + strFndStMinu;

				strFuncResult = objEventNotification.ackWebNotification(
						selenium, strEveName, strInfo, strStartDate);
				try {
					assertTrue(strFuncResult.equals(""));
					intResCnt++;
					Thread.sleep(70000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				strSubjName = strEveName;
				strMsgBodyEmail = "Event Notice for " + strUsrFulName_1
						+ ": \n" + strInfo + "\nSee attached document at "
						+ propEnvDetails.getProperty("MailURL") + "\nFrom: "
						+ strUsrFulName_1 + "\nRegion: " + strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required." + "\n"
						+ propEnvDetails.getProperty("MailURL");
				strMsgBodyPager = strInfo + "\nSee attached document at "
						+ propEnvDetails.getProperty("MailURL") + "\nFrom: "
						+ strUsrFulName_1 + "\nRegion: " + strRegn;

				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);

						if (strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
						} else if (strMsg.equals(strMsgBodyPager)) {
							intPagerRes++;
							log4j.info("Pager body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);
						if (strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
						} else if (strMsg.equals(strMsgBodyPager)) {
							intPagerRes++;
							log4j.info("Pager body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);
						if (strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
						} else if (strMsg.equals(strMsgBodyPager)) {
							intPagerRes++;
							log4j.info("Pager body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
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
						intResCnt++;
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

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * 5 Click on edit link associated with event EVE 'Edit Event '
			 * screen is displayed with appropriate data provided while creating
			 * event EVE.
			 * 
			 * Name of the attached html file is displayed at Attached File
			 * field.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navEditEventPage(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info(strStartDate);
				log4j.info(strEndDate);
				String strUploadedFileName = "EMR.html";
				strFuncResult = objEventSetup
						.verifyMandDataReatinedInEditEvntPage(selenium,
								strEveName, strInfo, true, strStartDate,
								strEndDate, true, strUploadedFileName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Click on event banner of EVE Event eve is displayed along with
			 * the Resource RS under RT and ST1,ST2 status types.
			 * 
			 * Created By:(user full name) @ (Date and time)(information of
			 * event) is displayed.
			 * 
			 * 'Attached file' link is displayed on event banner.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("Created by user");
			String strNewStartDate = dts.converDateFormat(strStartDate,
					"MM/dd/yyyy", "MM/dd/yy");
			log4j.info(strNewStartDate);
			strNewStartDate = strNewStartDate + " " + strFndStHr + ":"
					+ strFndStMinu;
			log4j.info(strNewStartDate);
			String strNewStartDate1 = dts.addTimetoExisting(strNewStartDate, 1,
					"MM/dd/yy HH:mm");
			log4j.info(strNewStartDate1);

			try {
				assertTrue(strFuncResult.equals(""));

				strFuncResult = objEventSetup
						.checkUserInfoInEventBannerCorrect(
								selenium,
								"Created By: "
										+ strUsrFulName_1
										+ " @ "
										+ strNewStartDate
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone") + "\n"
										+ strInfo + " | Attached File", false,
								strEventValue);

				if (strFuncResult.compareTo("") != 0) {
					strFuncResult = objEventSetup
							.checkUserInfoInEventBannerCorrect(
									selenium,
									"Created By: "
											+ strUsrFulName_1
											+ " @ "
											+ strNewStartDate1
											+ " "
											+ propEnvDetails
													.getProperty("TimeZone")
											+ "\n" + strInfo
											+ " | Attached File", false,
									strEventValue);
				}
				assertTrue(strFuncResult.equals(""));
			} catch (AssertionError Ae) {
				strFuncResult = objEventSetup
						.checkUserInfoInEventBannerCorrect(selenium,
								"Created By: " + strUsrFulName_1 + " @ "
										+ strNewStartDate + "\n" + strInfo,
								false, strEventValue);

			}

			/*
			 * 7 Click on 'Attached file' link. The attached file (while event
			 * creation) is opened in new window.
			 */

			try {
				assertEquals("", strFuncResult);

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				String strHtmlText = "Test EMResource Events";
				strFuncResult = objEventSetup
						.checkAttachedFilesForEventsNewWithChangedElmentID(
								selenium, strEveName, strHtmlText,
								strEventValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Click on key icon associated with resource RS and update the
			 * status of status types. Updated status values are displayed in
			 * 'Event Status' screen.
			 * 
			 * The data in the attached file remains the same
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", strSTvalue[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"102", strSTvalue[1], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statTypeName1, "101", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statTypeName2, "102", "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				String strHtmlText = "Test EMResource Events";
				strFuncResult = objEventSetup
						.checkAttachedFilesForEventsNewWithChangedElmentID(
								selenium, strEveName, strHtmlText,
								strEventValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				if (intResCnt == 2) {
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118093";
			gstrTO = "Verify that an event can be created by attaching .html files.";
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
	

	/********************************************************************************
	'Description	:Verify that an event can be created by attaching .txt files.
	'Arguments		:None
	'Returns		:None
	'Date	 		:11-Feb-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS118090() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		EventNotification objEventNotification = new EventNotification();
		Roles objRoles = new Roles();
		General objMail = new General();
		Date_Time_settings dts = new Date_Time_settings();
		EventList objEventList = new EventList();
		Views objViews = new Views();

		try {
			gstrTCID = "118090";
			gstrTO = "Verify that an event can be created by attaching .txt files.";
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

			String statTypeName1 = "ST_1" + strTimeText;
			String statTypeName2 = "ST_2" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";
			String strSTvalue[] = new String[2];
			String[] strStatTypeArr = { statTypeName1, statTypeName2 };

			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTvalue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");

			String strUploadFilePath = pathProps
					.getProperty("CreateEve_UploadTxtFile_OpenPath");

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
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			String strMsgBodyEmail = "";
			String strMsgBodyPager = "";
			String strSubjName = "";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			String strStartDate = "";
			String strEndDate = "";

			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;

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
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTypeName1);
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
						selenium, strStatusTypeValue, statTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTypeName2);
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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName);
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" } };
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(selenium, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError ae) {
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
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
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
				strRTvalue = objRT.fetchResTypeValueInResTypeList(selenium,
						strResrctTypName);
				if (strRTvalue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Resource RS is created selecting RT.
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
			 * 5. User 'U1' is created providing primary e-mail, e-mail and
			 * pager addresses and providing following rights.
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
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");

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
			 * 4. Event template 'ET' is created selecting status types ST1 ,ST2
			 * and RT and selecting the check boxes E-mail, Pager and Web for
			 * user U1.
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

				String[] strResTypeValue = { strRTvalue };
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventNotification.selectEventNofifForUser(
						selenium, strUsrFulName_1, strTempName, true, true,
						true);
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
			 * 2 Login as user U1, navigate to Event >> Event Management. 'Event
			 * Management' screen is displayed.
			 */

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

			/*
			 * 3 Click on 'Create a New event' button 'Create New Event' screen
			 * is displayed.
			 */

			/*
			 * 4 Create an event EVE providing mandatory data, attaching an html
			 * file. Event is listed in the 'Event Management' screen.
			 * 
			 * User 'U1' receives web,e-mail and pager notifications.
			 * 
			 * In the e-mail and pager notifications received, the message 'See
			 * attached document at https://emresource-test.emsystem.com' is
			 * displayed.
			 */

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(selenium,
						strTempName, strResource, strEveName, strInfo, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.uploadFilesInCreateEventPage(
						selenium, strAutoFilePath, strAutoFileName,
						strUploadFilePath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup
						.saveAndvrfyEventAlongWithPageName(selenium,
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

				strFuncResult = objEventSetup.navEditEventPage(selenium,
						strEveName);
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

				strFuncResult = objEventSetup
						.cancelAndNavToEventManagementScreen(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strEndDate = dts.AddDaytoExistingDate(strStartDate, 1,
						"M/d/yyyy");

				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strEndDate = strEndDate + " " + strFndStHr + ":" + strFndStMinu;

				strFuncResult = objEventNotification.ackWebNotification(
						selenium, strEveName, strInfo, strStartDate);
				try {
					assertTrue(strFuncResult.equals(""));
					intResCnt++;
					Thread.sleep(70000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				strSubjName = strEveName;
				strMsgBodyEmail = "Event Notice for " + strUsrFulName_1
						+ ": \n" + strInfo + "\nSee attached document at "
						+ propEnvDetails.getProperty("MailURL") + "\nFrom: "
						+ strUsrFulName_1 + "\nRegion: " + strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required." + "\n"
						+ propEnvDetails.getProperty("MailURL");
				strMsgBodyPager = strInfo + "\nSee attached document at "
						+ propEnvDetails.getProperty("MailURL") + "\nFrom: "
						+ strUsrFulName_1 + "\nRegion: " + strRegn;

				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);

						if (strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
						} else if (strMsg.equals(strMsgBodyPager)) {
							intPagerRes++;
							log4j.info("Pager body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);
						if (strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
						} else if (strMsg.equals(strMsgBodyPager)) {
							intPagerRes++;
							log4j.info("Pager body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);
						if (strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
						} else if (strMsg.equals(strMsgBodyPager)) {
							intPagerRes++;
							log4j.info("Pager body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
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
						intResCnt++;
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

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * 5 Click on edit link associated with event EVE 'Edit Event '
			 * screen is displayed with appropriate data provided while creating
			 * event EVE.
			 * 
			 * Name of the attached html file is displayed at Attached File
			 * field.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navEditEventPage(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info(strStartDate);
				log4j.info(strEndDate);
				String strUploadedFileName = "EMTest.txt";
				strFuncResult = objEventSetup
						.verifyMandDataReatinedInEditEvntPage(selenium,
								strEveName, strInfo, true, strStartDate,
								strEndDate, true, strUploadedFileName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Click on event banner of EVE Event eve is displayed along with
			 * the Resource RS under RT and ST1,ST2 status types.
			 * 
			 * Created By:(user full name) @ (Date and time)(information of
			 * event) is displayed.
			 * 
			 * 'Attached file' link is displayed on event banner.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("Created by user");
			String strNewStartDate = dts.converDateFormat(strStartDate,
					"MM/dd/yyyy", "MM/dd/yy");
			log4j.info(strNewStartDate);
			strNewStartDate = strNewStartDate + " " + strFndStHr + ":"
					+ strFndStMinu;
			log4j.info(strNewStartDate);
			String strNewStartDate1 = dts.addTimetoExisting(strNewStartDate, 1,
					"MM/dd/yy HH:mm");
			log4j.info(strNewStartDate1);

			try {
				assertTrue(strFuncResult.equals(""));

				strFuncResult = objEventSetup
						.checkUserInfoInEventBannerCorrect(selenium,
								"Created By: " + strUsrFulName_1 + " @ "
										+ strNewStartDate + " " + "\n"
										+ strInfo + " | Attached File", false,
								strEventValue);

				if (strFuncResult.compareTo("") != 0) {
					strFuncResult = objEventSetup
							.checkUserInfoInEventBannerCorrect(
									selenium,
									"Created By: "
											+ strUsrFulName_1
											+ " @ "
											+ strNewStartDate1
											+ " "
											+ propEnvDetails
													.getProperty("TimeZone")
											+ "\n" + strInfo
											+ " | Attached File", false,
									strEventValue);
				}
				assertTrue(strFuncResult.equals(""));
			} catch (AssertionError Ae) {
				strFuncResult = objEventSetup
						.checkUserInfoInEventBannerCorrect(selenium,
								"Created By: " + strUsrFulName_1 + " @ "
										+ strNewStartDate + "\n" + strInfo,
								false, strEventValue);

			}

			/*
			 * 7 Click on 'Attached file' link. The attached file (while event
			 * creation) is opened in new window.
			 */

			try {
				assertEquals("", strFuncResult);

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				String strHtmlText = "Automation event";
				strFuncResult = objEventSetup
						.checkAttachedFilesForEventsNewWithChangedElmentID(
								selenium, strEveName, strHtmlText,
								strEventValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Click on key icon associated with resource RS and update the
			 * status of status types. Updated status values are displayed in
			 * 'Event Status' screen.
			 * 
			 * The data in the attached file remains the same
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", strSTvalue[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"102", strSTvalue[1], false, "", "");

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
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statTypeName1, "101", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statTypeName2, "102", "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				String strHtmlText = "Automation event";
				strFuncResult = objEventSetup
						.checkAttachedFilesForEventsNewWithChangedElmentID(
								selenium, strEveName, strHtmlText,
								strEventValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				if (intResCnt == 2) {
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118090";
			gstrTO = "Verify that an event can be created by attaching .txt files.";
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

/********************************************************************************
'Description	:Verify that an event can be created by attaching .pdf files.
'Arguments		:None
'Returns		:None
'Date	 		:11-Feb-2013
'Author			:QSG
'-------------------------------------------------------------------------------
'Modified Date                                                Modified By
'<Date>                                                       <Name>
**********************************************************************************/
	@Test
	public void testFTS118089() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		EventNotification objEventNotification = new EventNotification();
		Roles objRoles = new Roles();
		General objMail = new General();
		Date_Time_settings dts = new Date_Time_settings();
		EventList objEventList = new EventList();
		Views objViews = new Views();

		try {
			gstrTCID = "118089";
			gstrTO = "Verify that an event can be created by attaching .pdf files.";
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

			String statTypeName1 = "ST_1" + strTimeText;
			String statTypeName2 = "ST_2" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";
			String strSTvalue[] = new String[2];
			String[] strStatTypeArr = { statTypeName1, statTypeName2 };

			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTvalue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");

			String strUploadFilePath = pathProps
					.getProperty("CreateEve_UploadPdfFile_OpenPath_New");

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
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			String strMsgBodyEmail = "";
			String strMsgBodyPager = "";
			String strSubjName = "";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			String strStartDate = "";
			String strEndDate = "";

			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;

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
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTypeName1);
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
						selenium, strStatusTypeValue, statTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTypeName2);
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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName);
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" } };
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(selenium, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError ae) {
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
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
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
				strRTvalue = objRT.fetchResTypeValueInResTypeList(selenium,
						strResrctTypName);
				if (strRTvalue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Resource RS is created selecting RT.
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
			 * 5. User 'U1' is created providing primary e-mail, e-mail and
			 * pager addresses and providing following rights.
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
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");

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
			 * 4. Event template 'ET' is created selecting status types ST1 ,ST2
			 * and RT and selecting the check boxes E-mail, Pager and Web for
			 * user U1.
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

				String[] strResTypeValue = { strRTvalue };
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventNotification.selectEventNofifForUser(
						selenium, strUsrFulName_1, strTempName, true, true,
						true);
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
			 * 2 Login as user U1, navigate to Event >> Event Management. 'Event
			 * Management' screen is displayed.
			 */

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

			/*
			 * 3 Click on 'Create a New event' button 'Create New Event' screen
			 * is displayed.
			 */

			/*
			 * 4 Create an event EVE providing mandatory data, attaching an html
			 * file. Event is listed in the 'Event Management' screen.
			 * 
			 * User 'U1' receives web,e-mail and pager notifications.
			 * 
			 * In the e-mail and pager notifications received, the message 'See
			 * attached document at https://emresource-test.emsystem.com' is
			 * displayed.
			 */

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(selenium,
						strTempName, strResource, strEveName, strInfo, false);

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

				strFuncResult = objEventSetup.uploadFilesInCreateEventPage(
						selenium, strAutoFilePath, strAutoFileName,
						strUploadFilePath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup
						.saveAndvrfyEventAlongWithPageName(selenium,
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
				blnLogin = true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strEndDate = dts.AddDaytoExistingDate(strStartDate, 1,
						"M/d/yyyy");

				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				strEndDate = strEndDate + " " + strFndStHr + ":" + strFndStMinu;

				strFuncResult = objEventNotification.ackWebNotification(
						selenium, strEveName, strInfo, strStartDate);
				try {
					assertTrue(strFuncResult.equals(""));
					intResCnt++;
					Thread.sleep(70000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				strSubjName = strEveName;
				strMsgBodyEmail = "Event Notice for " + strUsrFulName_1
						+ ": \n" + strInfo + "\nSee attached document at "
						+ propEnvDetails.getProperty("MailURL") + "\nFrom: "
						+ strUsrFulName_1 + "\nRegion: " + strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required." + "\n"
						+ propEnvDetails.getProperty("MailURL");
				strMsgBodyPager = strInfo + "\nSee attached document at "
						+ propEnvDetails.getProperty("MailURL") + "\nFrom: "
						+ strUsrFulName_1 + "\nRegion: " + strRegn;

				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);

						if (strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
						} else if (strMsg.equals(strMsgBodyPager)) {
							intPagerRes++;
							log4j.info("Pager body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);
						if (strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
						} else if (strMsg.equals(strMsgBodyPager)) {
							intPagerRes++;
							log4j.info("Pager body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);
						if (strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
						} else if (strMsg.equals(strMsgBodyPager)) {
							intPagerRes++;
							log4j.info("Pager body is displayed correctly, the message"
									+ " 'See attached document at "
									+ propEnvDetails.getProperty("MailURL")
									+ "'" + " is displayed. ");
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
						intResCnt++;
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

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * 5 Click on edit link associated with event EVE 'Edit Event '
			 * screen is displayed with appropriate data provided while creating
			 * event EVE.
			 * 
			 * Name of the attached html file is displayed at Attached File
			 * field.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navEditEventPage(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info(strStartDate);
				log4j.info(strEndDate);
				String strUploadedFileName = "AutomationEvent.pdf";
				strFuncResult = objEventSetup
						.verifyMandDataReatinedInEditEvntPage(selenium,
								strEveName, strInfo, true, strStartDate,
								strEndDate, true, strUploadedFileName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Click on event banner of EVE Event eve is displayed along with
			 * the Resource RS under RT and ST1,ST2 status types.
			 * 
			 * Created By:(user full name) @ (Date and time)(information of
			 * event) is displayed.
			 * 
			 * 'Attached file' link is displayed on event banner.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("Created by user");
			String strNewStartDate = dts.converDateFormat(strStartDate,
					"MM/dd/yyyy", "MM/dd/yy");
			log4j.info(strNewStartDate);
			strNewStartDate = strNewStartDate + " " + strFndStHr + ":"
					+ strFndStMinu;
			log4j.info(strNewStartDate);
			String strNewStartDate1 = dts.addTimetoExisting(strNewStartDate, 1,
					"MM/dd/yy HH:mm");
			log4j.info(strNewStartDate1);

			try {
				assertTrue(strFuncResult.equals(""));

				strFuncResult = objEventSetup
						.checkUserInfoInEventBannerCorrect(
								selenium,
								"Created By: "
										+ strUsrFulName_1
										+ " @ "
										+ strNewStartDate
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone") + "\n"
										+ strInfo + " | Attached File", false,
								strEventValue);

				if (strFuncResult.compareTo("") != 0) {
					strFuncResult = objEventSetup
							.checkUserInfoInEventBannerCorrect(
									selenium,
									"Created By: "
											+ strUsrFulName_1
											+ " @ "
											+ strNewStartDate1
											+ " "
											+ propEnvDetails
													.getProperty("TimeZone")
											+ "\n" + strInfo
											+ " | Attached File", false,
									strEventValue);
				}
				assertTrue(strFuncResult.equals(""));
			} catch (AssertionError Ae) {
				strFuncResult = objEventSetup
						.checkUserInfoInEventBannerCorrect(selenium,
								"Created By: " + strUsrFulName_1 + " @ "
										+ strNewStartDate + "\n" + strInfo,
								false, strEventValue);

			}

			/*
			 * 7 Click on 'Attached file' link. The attached file (while event
			 * creation) is opened in new window.
			 */

			try {
				assertEquals("", strFuncResult);

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				String strAutoFilePath_Download = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Path");
				String strAutoFileName_Download = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Name");

				String strPDFDownlPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_"
						+ gstrTCID
						+ "_"
						+ System.currentTimeMillis() + ".pdf";

				String strHtmlText = "Automation event\r\n";
				strFuncResult = objEventSetup.checkAttachedPDFForEvents(
						selenium, strEveName, strHtmlText, strEventValue,
						strAutoFilePath_Download, strPDFDownlPath,
						strAutoFileName_Download);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Click on key icon associated with resource RS and update the
			 * status of status types. Updated status values are displayed in
			 * 'Event Status' screen.
			 * 
			 * The data in the attached file remains the same
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", strSTvalue[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"102", strSTvalue[1], false, "", "");

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
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statTypeName1, "101", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statTypeName2, "102", "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				String strAutoFilePath_Download = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Path");
				String strAutoFileName_Download = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Name");

				String strPDFDownlPath_New = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "StatSummary_"
						+ gstrTCID
						+ "_"
						+ System.currentTimeMillis() + ".pdf";

				String strHtmlText = "Automation event\r\n";
				strFuncResult = objEventSetup.checkAttachedPDFForEvents(
						selenium, strEveName, strHtmlText, strEventValue,
						strAutoFilePath_Download, strPDFDownlPath_New,
						strAutoFileName_Download);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				if (intResCnt == 2) {
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118089";
			gstrTO = "Verify that an event can be created by attaching .pdf files.";
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
