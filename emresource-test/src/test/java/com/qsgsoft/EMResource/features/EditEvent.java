package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.EventList;
import com.qsgsoft.EMResource.shared.EventNotification;
import com.qsgsoft.EMResource.shared.EventSetup;
import com.qsgsoft.EMResource.shared.General;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.MobileView;
import com.qsgsoft.EMResource.shared.ResourceTypes;
import com.qsgsoft.EMResource.shared.Resources;
import com.qsgsoft.EMResource.shared.Roles;
import com.qsgsoft.EMResource.shared.StatusTypes;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/****************************************************************
' Description        : The class  contains the test cases from
' Requirement Group  : Creating & managing Events
' Requirement        : Edit event		
' Date               : 17-Apr-2012
' Author             :QSG
'---------------------------------------------------------------
' Modified Date                                     Modified By
' <Date>                           	                <Name>
'****************************************************************/

public class EditEvent {

	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.EditEvent");
	static {
		BasicConfigurator.configure();
	}
	String gstrTCID, gstrTO, gstrResult, gstrReason;
	Selenium selenium, seleniumPrecondition;
	double gdbTimeTaken;
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	public static Date gdtStartDate;
	public Properties propElementDetails;
	public static String gstrBrowserName;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;
	public Properties propEnvDetails;
	public static Properties browserProps = new Properties();

	private String browser = "";

	private String json;
	public static long sysDateTime;
	public static long gsysDateTime;
	public static String sessionId_FF36, sessionId_FF20, sessionId_FF3,
			sessionId_FF35, sessionId_IE6, sessionId_IE7, sessionId_IE8,
			session_SF3, session_SF4, session_op9, session_op10, session_GC,
			StrSessionId, StrSessionId1, StrSessionId2;
	public static String gstrTimeOut = "";

	@Before
	public void setUp() throws Exception {

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		browser = propEnvDetails.getProperty("Browser");
		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		// create an object to refer to properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		if (propEnvDetails.getProperty("Server").equals("saucelabs.com")) {

			selenium = new DefaultSelenium(
					propEnvDetails.getProperty("Server"), 4444, this.json,
					propEnvDetails.getProperty("urlEU"));

		} else {
			selenium = new DefaultSelenium(
					propEnvDetails.getProperty("Server"), 4444, this.browser,
					propEnvDetails.getProperty("urlEU"));

		}

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		selenium.start();
		selenium.windowMaximize();
		selenium.setTimeout("");

		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		// kill browser
		selenium.close();
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
				sysDateTime, gstrBrowserName, gstrBuild, StrSessionId);

	}
	/**
	'*************************************************************
	' Description: Edit event and change the start time and verify that the event starts at the newly provided time.
	' Precondition: 

				1. User A is created providing primary e-mail, e-mail and pager addresses.
				2. User A has the following rights;
				a. 'Maintain Events' right
				b. 'Maintain Event Templates' right
				c. 'User - Setup User Accounts' right
				
				2. Event template ET is created providing mandatory data and selecting the check boxes E-mail, Pager and Web for user A.
				3. Future event EVE(providing future time T1 ) is created selecting ET. 
	' Arguments:
	' Returns:
	' Date: 17-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'*************************************************************
*/
	@Test
	public void testBQS118120() throws Exception {
		try {
			gstrTCID = "118120";
			gstrTO = "Edit event and change the start time and verify that"
					+ " the event starts at the newly provided time.";
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strTestData[] = new String[10];
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			ReadData objReadData = new ReadData();
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strAction1 = "Edit";
			String strEMail = objReadData.readData("WebMailUser", 2, 1);
			String strIcon = objReadData.readInfoExcel("Event Temp Data", 8, 6,
					strFILE_PATH);
			String strStatus = "Ongoing";
			String strStatDateBef = "";

			String strStartDateAft = "";
			String strEndDate = "";
			String strDrill = "No";
			String strInfo = "This is an automation event";
			String strFutureTime = "";
			String strStatTime = "";
			String strAdminFullName = objrdExcel.readData("Login", 4, 1);
			String strHourDur = "25";

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

			Login objLogin = new Login();// object of class Login
			EventSetup objEve = new EventSetup();
			General objMail = new General();
			EventList objList = new EventList();
			MobileView objMob = new MobileView();
			CreateUsers objUser = new CreateUsers();
			int intResCnt = 0;

			EventNotification objNotif = new EventNotification();

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = "AutoUsr" + System.currentTimeMillis();
			String strLoginPassword = "abc123";
			String strRegion = objReadData.readData("Login", 3, 4);

			String strLoginName = objReadData.readData("WebMailUser", 2, 1);
			String strPassword = objReadData.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1 = "";
			String strMsgBody2 = "";
			String strSubjName = "";
			String strUsrFullName = "Full" + strLoginUserName;

			int intEMailRes = 0;
			int intPagerRes = 0;

			String strEveTemp = "AutoET_" + strTimeText;
			String strEveName = "AutoEve_" + strTimeText;

			String strTempDef = objReadData.readInfoExcel("Event Temp Data", 2,
					3, strFILE_PATH);
			String strEveColor = objReadData.readInfoExcel("Event Temp Data",
					2, 4, strFILE_PATH);
			String strAsscIcon = objReadData.readInfoExcel("Event Temp Data",
					2, 5, strFILE_PATH);
			String strIconSrc = objReadData.readInfoExcel("FirefoxTestData", 2,
					3, strFILE_PATH);

			String strUserName = objReadData.readData("Login", 3, 1);
			String strUsrPassword = objReadData.readData("Login", 3, 2);
			String strRegn = objReadData.readData("Login", 3, 4);

			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String[] strHeader = { "Action", "Icon", "Status", "Start", "End",
					"Title", "Drill", "Template", "Information" };
			String strResTypeArr[] = {};
			String strStatTypeArr[] = {};
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			String strFailMsg = objLogin.login(seleniumPrecondition,
					strUserName, strUsrPassword);

			try {
				assertTrue(strFailMsg.equals(""));
				// nav to default region
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.fillUsrMandatoryFlds(seleniumPrecondition,
						strLoginUserName, strLoginPassword, strLoginPassword,
						strUsrFullName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail,
						strEMail, strEMail, "");

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objUser.savVrfyUserWithSearchUser(
						seleniumPrecondition, strLoginUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(seleniumPrecondition));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// navigate to Event Template
				strFailMsg = objEve.createEventTemplate(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// fill the required fields in Create Event Template and save
				strFailMsg = objEve
						.fillMandfieldsAndSaveEveTemp(seleniumPrecondition,
								strEveTemp, strTempDef, strEveColor,
								strAsscIcon, strIconSrc, "", "", "", "", true,
								strResTypeArr, strStatTypeArr, true, false,
								false);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// fill the required fields in Create Event Template and save
				strFailMsg = objNotif.selectEventNofifForUser(
						seleniumPrecondition, strUsrFullName, strEveTemp, true,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(seleniumPrecondition));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = objEve.createEventMandFlds(seleniumPrecondition,
						strEveTemp, strEveName, strInfo, false);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				String strStatTimeArr[] = seleniumPrecondition.getText(
						"css=#statusTime").split(" ");
				String strCurYear = dts.getCurrentDate("yyyy");

				// Start Date
				strStatDateBef = strStatTimeArr[1] + "/" + strStatTimeArr[0]
						+ "/" + strCurYear + " " + strStatTimeArr[2];

				strStatDateBef = dts.addTimetoExisting(strStatDateBef, 10,
						"MMM/d/yyyy HH:mm");

				strFailMsg = objEve.createEvent_FillOtherFields(
						seleniumPrecondition, strEveName, true, false, false,
						false, false, "Hours", strHourDur, strStatDateBef, "",
						false, "", "", "", "", "", "", "", "");
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * Step 2: Login as user A, navigate to Event >> Event Management.
			 * <-> 'Event Management' screen is displayed.
			 */
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objLogin.newUsrLogin(selenium, strLoginUserName,
						strLoginPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			/*
			 * Step 3: Click on 'Edit' link next to EVE, change 'Event Start'
			 * time from T1 to T2 and click on 'Save'. <-> Event EVE starts are
			 * time T2 and not at T1 (Event banner is displayed at time T2 and
			 * not at time T1). <-> User receives email, pager and web
			 * notifications for event start at time T2 and the event start time
			 * is displayed in the notifications as T2. <-> Event is listed in
			 * the 'Event Management' screen <-> Event start Date and time are
			 * displayed appropriately under the column 'Start'. <-> Event end
			 * Date and time are displayed appropriately under the column 'End'
			 * <-> 'Ongoing' is displayed under the 'Status' column <-> 'Edit'
			 * and 'End' links are displayed under the 'Action' column for the
			 * event created.
			 */
			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = objEve.editEvent(selenium, strEveName, strEveName,
						strInfo, false);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				// get End Date

				String strStatTimeArr[] = selenium.getText("css=#statusTime")
						.split(" ");
				String strCurYear = dts.getCurrentDate("yyyy");

				strFutureTime = strStatTimeArr[1] + "/" + strStatTimeArr[0]
						+ "/" + strCurYear + " " + strStatTimeArr[2];
				// End Date
				strStartDateAft = dts.addTimetoExisting(strFutureTime, 2,
						"MMM/d/yyyy HH:mm");

				String strFutDateArr[] = strStartDateAft.split(" ");
				String strStDateArr[] = strFutDateArr[0].split("/");
				strFndStMnth = strStDateArr[0];
				strFndStDay = strStDateArr[1];
				strFndStYear = strStDateArr[2];

				String strStTime[] = strFutDateArr[1].split(":");
				strFndStHr = strStTime[0];
				strFndStMinu = strStTime[1];
				strStatTime = strFutDateArr[1];

				// End Date
				strFutureTime = dts.addTimetoExistingHour(strStartDateAft,
						Integer.parseInt(strHourDur), "MMM/d/yyyy HH:mm");

				strFutDateArr = strFutureTime.split(" ");
				String strEdDateArr[] = strFutDateArr[0].split("/");
				strFndEdMnth = strEdDateArr[0];
				strFndEdDay = strEdDateArr[1];
				strFndEdYear = strEdDateArr[2];

				String strEdTime[] = strFutDateArr[1].split(":");
				strFndEdHr = strEdTime[0];
				strFndEdMinu = strEdTime[1];

				strFailMsg = objEve.createEvent_FillOtherFields(selenium,
						strEveName, true, false, false, false, false, "Hours",
						strHourDur, strStartDateAft, "", false, "", "", "", "",
						"", "", "", "");
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				String strStatTimeArr[] = selenium.getText("css=#statusTime")
						.split(" ");
				String strCurTime = strStatTimeArr[2];

				int TimeDiff = dts.getTimeDiff(strStatTime, strCurTime);
				TimeDiff = TimeDiff * 1000;
				Thread.sleep(TimeDiff + 10);
				Thread.sleep(60000);

				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg = "Event Management screen is NOT displayed";
					assertTrue(objEve.navToEventManagement(selenium));
					strFailMsg = "";
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
									+ strEveName + "']"));
					log4j.info("Event banner is displayed.");

					strStartDateAft = dts.converDateFormat(strFndStYear
							+ strFndStMnth + strFndStDay, "yyyyMMMdd",
							"M/d/yyyy");
					strStartDateAft = strStartDateAft + " " + strFndStHr + ":"
							+ strFndStMinu;

					strFailMsg = objNotif.ackWebNotification(selenium,
							strEveName, strInfo, strStartDateAft);

					try {
						assertTrue(strFailMsg.equals(""));
						intResCnt++;
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					strSubjName = strEveName;
					strMsgBody1 = "Event Notice for " + strUsrFullName + ": \n"
							+ strInfo + "\nFrom: " + strAdminFullName + ""
							+ "\nRegion: " + strRegion
							+ "\n\nPlease do not reply to this email message. "
							+ "You must log into EMResource"
							+ " to take any action that may be required."
							+ "\n" + propEnvDetails.getProperty("MailURL");
					strMsgBody2 = strInfo + "\nFrom: " + strAdminFullName + ""
							+ "\nRegion: " + strRegion;

					selenium.selectWindow("");
					strFailMsg = objMail.loginAndnavToInboxInWebMail(selenium,
							strLoginName, strPassword);
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody1)) {
								intEMailRes++;
							} else if (strMsg.equals(strMsgBody2)) {
								intPagerRes++;
							}

						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}

						selenium.selectFrame("relative=up");
						selenium.selectFrame("horde_main");

						selenium.click("link=Back to Inbox");
						selenium.waitForPageToLoad("90000");

						strFailMsg = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody1)) {
								intEMailRes++;
							} else if (strMsg.equals(strMsgBody2)) {
								intPagerRes++;
							}

						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}

						selenium.selectFrame("relative=up");
						selenium.selectFrame("horde_main");

						selenium.click("link=Back to Inbox");
						selenium.waitForPageToLoad("90000");

						strFailMsg = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody1)) {
								intEMailRes++;
							} else if (strMsg.equals(strMsgBody2)) {
								intPagerRes++;
							}

						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}
						if (intEMailRes == 2 && intPagerRes == 1) {
							intResCnt++;
						}
						selenium.selectWindow("Mail");
						selenium.selectFrame("horde_main");
						selenium.click("link=Log out");
						selenium.waitForPageToLoad("90000");
						selenium.close();

						selenium.selectWindow("");
						selenium.selectFrame("Data");
						strStartDateAft = dts.converDateFormat(strFndStYear
								+ strFndStMnth + strFndStDay, "yyyyMMMdd",
								"yyyy-MM-dd");
						strStartDateAft = strStartDateAft + " " + strFndStHr
								+ ":" + strFndStMinu;

						strEndDate = dts.converDateFormat(strFndEdYear
								+ strFndEdMnth + strFndEdDay, "yyyyMMMdd",
								"yyyy-MM-dd");
						strEndDate = strEndDate + " " + strFndEdHr + ":"
								+ strFndEdMinu;

						String[] strData = { strAction1, strIcon, strStatus,
								strStartDateAft, strEndDate, strEveName,
								strDrill, strEveTemp, strInfo };

						for (int intRec = 0; intRec < strHeader.length; intRec++) {
							strFailMsg = objList
									.checkDataInEventListTable(selenium,
											strHeader[intRec], strEveName,
											strData[intRec],
											String.valueOf(intRec + 1));
							try {
								assertTrue(strFailMsg.equals(""));
							} catch (AssertionError ae) {
								gstrReason = gstrReason + " " + strFailMsg;
							}
						}

						/*
						 * Step 4: Click on 'Mobile View' link on footer of the
						 * application. <-> 'Main Menu' screen is displayed.
						 * Step 5: Navigate to Events>>< Event Name >>>Times
						 * screen <-> 'Event Times' screen is displayed with
						 * appropriate data for 'Event Started' and 'Scheduled
						 * End' controls
						 */

						strStartDateAft = dts.converDateFormat(strFndStYear
								+ strFndStMnth + strFndStDay, "yyyyMMMdd",
								"dd-MMM-yyyy");
						strStartDateAft = strStartDateAft + " " + strFndStHr
								+ ":" + strFndStMinu;

						strEndDate = dts.converDateFormat(strFndEdYear
								+ strFndEdMnth + strFndEdDay, "yyyyMMMdd",
								"dd-MMM-yyyy");
						strEndDate = strEndDate + " " + strFndEdHr + ":"
								+ strFndEdMinu;

						strFailMsg = objMob.checkInEventTimes(selenium,
								strEveName, strStartDateAft, strEndDate);
						try {
							assertTrue(strFailMsg.equals(""));
							intResCnt++;
						} catch (AssertionError ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}

						if (intResCnt == 3 && gstrReason.compareTo("") == 0) {
							gstrResult = "PASS";
							// Write result data
							strTestData[0] = propEnvDetails
									.getProperty("Build");
							strTestData[1] = gstrTCID;
							strTestData[2] = strLoginUserName + "/"
									+ strLoginPassword;
							strTestData[3] = strEveTemp;
							strTestData[4] = strEveName;
							strTestData[5] = "Check in Mobile, Start Date Bef: "
									+ strStatDateBef
									+ "Start Date Aft: "
									+ strStartDateAft
									+ ", End Date: "
									+ strEndDate;

							String strWriteFilePath = pathProps
									.getProperty("WriteResultPath");
							objOFC.writeResultData(strTestData,
									strWriteFilePath, "Events");
						}
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}

				} catch (AssertionError ae) {
					log4j.info("Event banner is NOT displayed.");
					gstrReason = "Event banner is NOT displayed.";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
		} catch (Exception e) {

			Exception excReason;
			gstrTCID = "118120";
			gstrTO = "Edit event and change the start time and "
					+ "verify that the event starts at the newly provided time.";
			gstrResult = "FAIL";
			excReason = null;

			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();

		}
	}
	/**
	'*************************************************************
	' Description: Edit event and change the end time and verify that the event ends at the newly provided time.
	' Precondition: 
			1. User A is created providing primary e-mail, e-mail and pager addresses.
			2. User A has the following rights;
			a. 'Maintain Events' right
			b. 'Maintain Event Templates' right
			c. 'User - Setup User Accounts' right 
	' Arguments:
	' Returns:
	' Date: 17-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'*************************************************************
*/
	@Test
	public void testBQS118121() throws Exception {
		try {
			gstrTCID = "118121";
			gstrTO = "Edit event and change the end time and verify that the event ends at the newly provided time.";
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strTestData[] = new String[10];
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			ReadData objReadData = new ReadData();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			CreateUsers objUser = new CreateUsers();
			String strHourDur = "24";

			String strStatTime = "";
			String strAction1 = "Edit";

			String strIcon = objReadData.readInfoExcel("Event Temp Data", 8, 6,
					strFILE_PATH);
			String strStatus = "Ongoing";
			String strStatDate = "";

			String strEndDateBef = "";
			String strEndDateAft = "";
			String strDrill = "No";
			String strInfo = "This is an automation event";
			String strAdminFullName = objrdExcel.readData("Login", 4, 1);

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

			Login objLogin = new Login();// object of class Login
			EventSetup objEve = new EventSetup();
			General objMail = new General();
			EventList objList = new EventList();
			MobileView objMob = new MobileView();
			int intResCnt = 0;

			EventNotification objNotif = new EventNotification();

			String strByRole = objrdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("Precondition",
					3, 10, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("Precondition", 3,
					11, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("Precondition", 4,
					12, strFILE_PATH);

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = "AutoUsr" + System.currentTimeMillis();
			String strLoginPassword = "abc123";
			String strRegion = objReadData.readData("Login", 3, 4);

			String strLoginName = objReadData.readData("WebMailUser", 2, 1);
			String strPassword = objReadData.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1 = "";
			String strMsgBody2 = "";
			String strSubjName = "";
			String strUsrFullName = "Full" + strLoginUserName;

			int intEMailRes = 0;
			int intPagerRes = 0;

			String strEveTemp = "AutoET_" + strTimeText;
			String strEveName = "AutoEve_" + strTimeText;
			String strEMail = objReadData.readData("WebMailUser", 2, 1);
			String strTempDef = objReadData.readInfoExcel("Event Temp Data", 2,
					3, strFILE_PATH);
			String strEveColor = objReadData.readInfoExcel("Event Temp Data",
					2, 4, strFILE_PATH);
			String strAsscIcon = objReadData.readInfoExcel("Event Temp Data",
					2, 5, strFILE_PATH);
			String strIconSrc = objReadData.readInfoExcel("FirefoxTestData", 2,
					3, strFILE_PATH);

			String strUserName = objReadData.readData("Login", 3, 1);
			String strUsrPassword = objReadData.readData("Login", 3, 2);
			String strRegn = objReadData.readData("Login", 3, 4);

			String[] strHeader = { "Action", "Icon", "Status", "Start", "End",
					"Title", "Drill", "Template", "Information" };
			String strResTypeArr[] = {};
			String strStatTypeArr[] = {};
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			String strFailMsg = objLogin.login(seleniumPrecondition,
					strUserName, strUsrPassword);

			try {
				assertTrue(strFailMsg.equals(""));
				// nav to default region
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.fillUsrMandatoryFlds(seleniumPrecondition,
						strLoginUserName, strLoginPassword, strLoginPassword,
						strUsrFullName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail,
						strEMail, strEMail, "");

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objUser.savVrfyUserWithSearchUser(
						seleniumPrecondition, strLoginUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "seleniumPrecondition Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(seleniumPrecondition));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// navigate to Event Template
				strFailMsg = objEve.createEventTemplate(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// fill the required fields in Create Event Template and save
				strFailMsg = objEve
						.fillMandfieldsAndSaveEveTemp(seleniumPrecondition,
								strEveTemp, strTempDef, strEveColor,
								strAsscIcon, strIconSrc, "", "", "", "", true,
								strResTypeArr, strStatTypeArr, true, false,
								false);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// fill the required fields in Create Event Template and save
				strFailMsg = objNotif.selectEventNofifForUser(
						seleniumPrecondition, strUsrFullName, strEveTemp, true,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(seleniumPrecondition));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = objEve.createEventMandFlds(seleniumPrecondition,
						strEveTemp, strEveName, strInfo, false);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				String strStatTimeArr[] = seleniumPrecondition.getText(
						"css=#statusTime").split(" ");
				String strCurYear = dts.getCurrentDate("yyyy");

				// Start Date
				strStatDate = strStatTimeArr[1] + "/" + strStatTimeArr[0] + "/"
						+ strCurYear + " " + strStatTimeArr[2];

				strStatDate = dts.addTimetoExisting(strStatDate, 3,
						"MMM/d/yyyy HH:mm");

				String strFutDateArr[] = strStatDate.split(" ");
				String strStDateArr[] = strFutDateArr[0].split("/");
				strFndStMnth = strStDateArr[0];
				strFndStDay = strStDateArr[1];
				strFndStYear = strStDateArr[2];

				String strStTime[] = strFutDateArr[1].split(":");
				strFndStHr = strStTime[0];
				strFndStMinu = strStTime[1];

				strStatTime = strFutDateArr[1];

				// End Date
				strEndDateBef = dts.addTimetoExistingHour(strStatDate,
						Integer.parseInt(strHourDur), "MMM/d/yyyy HH:mm");

				strFailMsg = objEve.createEvent_FillOtherFields(
						seleniumPrecondition, strEveName, true, false, false,
						false, false, "Date", strHourDur, strStatDate,
						strEndDateBef, false, "", "", "", "", "", "", "", "");
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			/*
			 * Step 2: Login as user A, navigate to Event >> Event Management.
			 * <-> 'Event Management' screen is displayed.
			 */

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objLogin.newUsrLogin(selenium, strLoginUserName,
						strLoginPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			/*
			 * Step 3: Click on 'Edit' link next to EVE, change 'Event End' time
			 * from T2 to T3 and click on 'Save'. <-> 'Event Management' screen
			 * is displayed. <-> Event start Date and time are displayed
			 * appropriately under the column 'Start' <-> Event end Date and
			 * time are displayed appropriately under the column 'End'. <->
			 * Event EVE starts at time T1. <-> User receives email, pager and
			 * web notifications for event start at time T1
			 */
			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = objEve.editEvent(selenium, strEveName, strEveName,
						strInfo, false);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				// End Date
				strEndDateAft = dts.addTimetoExistingHour(strEndDateBef, 1,
						"MMM/d/yyyy HH:mm");

				String strFutDateArr[] = strEndDateAft.split(" ");
				String strEdDate[] = strFutDateArr[0].split("/");
				strFndEdMnth = strEdDate[0];
				strFndEdDay = strEdDate[1];
				strFndEdYear = strEdDate[2];

				String strEdTime[] = strFutDateArr[1].split(":");
				strFndEdHr = strEdTime[0];
				strFndEdMinu = strEdTime[1];

				strFailMsg = objEve.createEvent_FillOtherFields(selenium,
						strEveName, true, false, false, false, false, "Date",
						"", strStatDate, strEndDateAft, false, "", "", "", "",
						"", "", "", "");
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				String strStatTimeArr[] = selenium.getText("css=#statusTime")
						.split(" ");
				String strCurTime = strStatTimeArr[2];

				int TimeDiff = dts.getTimeDiff(strStatTime, strCurTime);
				TimeDiff = TimeDiff * 1000;
				Thread.sleep(TimeDiff + 10);
				Thread.sleep(60000);

				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg = "Event Management screen is NOT displayed";
					assertTrue(objEve.navToEventManagement(selenium));
					strFailMsg = "";
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
									+ strEveName + "']"));
					log4j.info("Event banner is displayed.");

					strStatDate = dts.converDateFormat(strFndStYear
							+ strFndStMnth + strFndStDay, "yyyyMMMdd",
							"M/d/yyyy");
					strStatDate = strStatDate + " " + strFndStHr + ":"
							+ strFndStMinu;

					strFailMsg = objNotif.ackWebNotification(selenium,
							strEveName, strInfo, strStatDate);

					try {
						assertTrue(strFailMsg.equals(""));
						intResCnt++;
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					strSubjName = strEveName;
					strMsgBody1 = "Event Notice for " + strUsrFullName + ": \n"
							+ strInfo + "\nFrom: " + strAdminFullName + ""
							+ "\nRegion: " + strRegion
							+ "\n\nPlease do not reply to this email message."
							+ " You must log into EMResource to"
							+ " take any action that may be required." + "\n"
							+ propEnvDetails.getProperty("MailURL");

					strMsgBody2 = strInfo + "\nFrom: " + strAdminFullName + ""
							+ "\nRegion: " + strRegion;

					selenium.selectWindow("");
					strFailMsg = objMail.loginAndnavToInboxInWebMail(selenium,
							strLoginName, strPassword);
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody1)) {
								intEMailRes++;
							} else if (strMsg.equals(strMsgBody2)) {
								intPagerRes++;
							}

						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}

						selenium.selectFrame("relative=up");
						selenium.selectFrame("horde_main");

						selenium.click("link=Back to Inbox");
						selenium.waitForPageToLoad("90000");

						strFailMsg = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody1)) {
								intEMailRes++;
							} else if (strMsg.equals(strMsgBody2)) {
								intPagerRes++;
							}

						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}

						selenium.selectFrame("relative=up");
						selenium.selectFrame("horde_main");

						selenium.click("link=Back to Inbox");
						selenium.waitForPageToLoad("90000");

						strFailMsg = objMail.verifyEmail(selenium, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg = selenium
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody1)) {
								intEMailRes++;
							} else if (strMsg.equals(strMsgBody2)) {
								intPagerRes++;
							}

						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}
						if (intPagerRes == 1) {
							log4j.info(strMsgBody2
									+ " is displayed for Pager Notification");
						} else {
							log4j.info(strMsgBody2
									+ " is NOT displayed for Pager Notification");
							gstrReason = strMsgBody2
									+ " is NOT displayed for Pager Notification";
						}

						if (intEMailRes == 2) {
							log4j.info(strMsgBody1
									+ " is displayed for Email and primary Email Notification");
						} else {
							log4j.info(strMsgBody1
									+ " is NOT displayed for  Email and primary Email  Notification");
							gstrReason = strMsgBody1
									+ " is NOT displayed for  Email and primary Email  Notification";
						}

						if (intEMailRes == 2 && intPagerRes == 1) {
							intResCnt++;
						}

						selenium.selectWindow("");
						selenium.selectFrame("Data");
						strStatDate = dts.converDateFormat(strFndStYear
								+ strFndStMnth + strFndStDay, "yyyyMMMdd",
								"yyyy-MM-dd");
						strStatDate = strStatDate + " " + strFndStHr + ":"
								+ strFndStMinu;

						strEndDateAft = dts.converDateFormat(strFndEdYear
								+ strFndEdMnth + strFndEdDay, "yyyyMMMdd",
								"yyyy-MM-dd");
						strEndDateAft = strEndDateAft + " " + strFndEdHr + ":"
								+ strFndEdMinu;

						String[] strData = { strAction1, strIcon, strStatus,
								strStatDate, strEndDateAft, strEveName,
								strDrill, strEveTemp, strInfo };

						for (int intRec = 0; intRec < strHeader.length; intRec++) {
							strFailMsg = objList
									.checkDataInEventListTable(selenium,
											strHeader[intRec], strEveName,
											strData[intRec],
											String.valueOf(intRec + 1));
							try {
								assertTrue(strFailMsg.equals(""));
							} catch (AssertionError ae) {
								gstrReason = gstrReason + " " + strFailMsg;
							}
						}

						/*
						 * Step 4: Click on 'Mobile View' link on footer of the
						 * application. <-> 'Main Menu' screen is displayed.
						 * Step 5: Navigate to Events>>< Event Name >>>Times
						 * screen <-> 'Event Times' screen is displayed with
						 * appropriate data for 'Event Started' and 'Scheduled
						 * End' controls.
						 */
						strStatDate = dts.converDateFormat(strFndStYear
								+ strFndStMnth + strFndStDay, "yyyyMMMdd",
								"dd-MMM-yyyy");
						strStatDate = strStatDate + " " + strFndStHr + ":"
								+ strFndStMinu;

						strEndDateAft = dts.converDateFormat(strFndEdYear
								+ strFndEdMnth + strFndEdDay, "yyyyMMMdd",
								"dd-MMM-yyyy");
						strEndDateAft = strEndDateAft + " " + strFndEdHr + ":"
								+ strFndEdMinu;

						strFailMsg = objMob.checkInEventTimes(selenium,
								strEveName, strStatDate, strEndDateAft);
						try {
							assertTrue(strFailMsg.equals(""));
							intResCnt++;
						} catch (AssertionError ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}

						if (intResCnt == 3 && gstrReason.compareTo("") == 0) {
							gstrResult = "PASS";
							// Write result data
							strTestData[0] = propEnvDetails
									.getProperty("Build");
							strTestData[1] = gstrTCID;
							strTestData[2] = strLoginUserName + "/"
									+ strLoginPassword;
							strTestData[3] = strEveTemp;
							strTestData[4] = strEveName;
							strTestData[5] = "Check in Mobile, Start Date: "
									+ strStatDate + "End Date Bef: "
									+ strEndDateBef + ", End Date Aft: "
									+ strEndDateAft
									+ " Wait for End date and check Email";

							String strWriteFilePath = pathProps
									.getProperty("WriteResultPath");
							objOFC.writeResultData(strTestData,
									strWriteFilePath, "Events");
						}
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}

				} catch (AssertionError ae) {
					log4j.info("Event banner is NOT displayed.");
					gstrReason = "Event banner is NOT displayed.";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
		} catch (Exception e) {

			Exception excReason;
			gstrTCID = "118121";
			gstrTO = "Edit event and change the end time and verify that the event ends at the newly provided time.";
			gstrResult = "FAIL";
			excReason = null;

			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();

		}
	}
	/**
	'*************************************************************
	' Description: Edit event and add new resources and verify that the newly added resources are displayed in the 'Event Status' screen
	' Precondition: 
			1. Role based status types MST, NST, TST and SST are created.
			2. Resource type RT is created selecting MST, NST, TST and SST status types.
			3. Resources RS1 and TS2 are created under RT
			4. User A is created selecting;
			
			a. View Resource right on RS1 and RS2.
			b. Role R1 to view and update status types NST, MST, TST and SST.
			c. 'Maintain Event Templates' and 'Maintain Events' right.
			
			5. Event Template ET is created selecting RT,NST, MST, TST and SST. 
	' Arguments:
	' Returns:
	' Date: 17-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'************************************************************* 
*/
	@Test
	public void testBQS68436() throws Exception {
		try {
			gstrTCID = "68436";
			gstrTO = "Edit event and add new resources and verify that the newly added resources are displayed in the 'Event Status' screen";
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTestData[] = new String[10];

			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			ReadData objReadData = new ReadData();
			ResourceTypes objRT = new ResourceTypes();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			Resources objRs = new Resources();
			Login objLogin = new Login();// object of class Login
			EventSetup objEve = new EventSetup();
			Roles objRole = new Roles();
			EventList objList = new EventList();
			MobileView objMob = new MobileView();
			CreateUsers objCreateUsers = new CreateUsers();

			StatusTypes objST = new StatusTypes();
			int intResCnt = 0;
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			// Read test data from excel
			String strEveTemp = "AutoEveTem_" + strTimeText;
			String strEveName = "AutoEve_" + strTimeText;

			String strResType = "AutoRT_" + strTimeText;

			// String
			// strStatTypeArr[]={strMulStat,strNumStat,strSatuStat,strTextStat};
			String strInfo = "This is an automation event";

			String strLoginUserName = "AutoUsr" + System.currentTimeMillis();
			String strLoginPassword = "abc123";
			String strFullUserName = "Full" + strLoginUserName;
			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";

			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";

			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strSTvalue[] = new String[4];
			String strStatusValue[] = new String[2];

			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strResource1 = "AutoRsa_" + strTimeText;
			String strResource2 = "AutoRsb_" + strTimeText;
			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strTempDef = objReadData.readInfoExcel("Event Temp Data", 2,
					3, strFILE_PATH);
			String strEveColor = objReadData.readInfoExcel("Event Temp Data",
					2, 4, strFILE_PATH);
			String strAsscIcon = objReadData.readInfoExcel("Event Temp Data",
					2, 5, strFILE_PATH);
			String strIconSrc = objReadData.readInfoExcel("Event Temp Data", 2,
					6, strFILE_PATH);

			String strResTypeArr[] = new String[1];
			String strStatTypeArr[] = { strMulTypeName, strNumTypeName,
					strSatuTypeName, strTxtTypeName };

			// login details
			String strAdmUserName = objReadData.readData("Login", 3, 1);
			String strAdmPassword = objReadData.readData("Login", 3, 2);
			String strRegn = objReadData.readData("Login", 3, 4);
			/*
			 * Step 2: Login as user A, navigate to Event >> Event Management
			 * <-> 'Event Management' screen is displayed.
			 */
			selenium.open(propEnvDetails.getProperty("urlRel"));
			String strFailMsg = objLogin.login(selenium, strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			// Multi ST
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMulStatTypeValue, strMulTypeName, strMulStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strMulTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName1, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName2, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			// Number ST

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNumStatTypeValue, strNumTypeName, strNumStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			// Saturation Score ST

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSatuStatTypeValue, strSatuTypeName,
						strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strSatuTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			// Text ST

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strTxtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch Text status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(selenium, strResType,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFailMsg);
					strFailMsg = objRs.selDeselctOnlySTInEditRSLevelPage(
							selenium, strSTvalue[intST], true);

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}

			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(selenium, strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strRestypVal = objRT.fetchResTypeValueInResTypeList(
						selenium, strResType);

				if (strRestypVal.compareTo("") != 0) {
					strFailMsg = "";
					strResTypeArr[0] = strRestypVal;
				} else {
					strFailMsg = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.createResourceWitLookUPadres(selenium,
						strResource1, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strResVal = objRs.fetchResValueInResList(selenium,
						strResource1);

				if (strResVal.compareTo("") != 0) {
					strFailMsg = "";
					strRSValue[0] = strResVal;
				} else {
					strFailMsg = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.createResourceWitLookUPadres(selenium,
						strResource2, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strResVal = objRs.fetchResValueInResList(selenium,
						strResource2);

				if (strResVal.compareTo("") != 0) {
					strFailMsg = "";
					strRSValue[0] = strResVal;
				} else {
					strFailMsg = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName);

				if (strRoleValue.compareTo("") != 0) {
					strFailMsg = "";

				} else {
					strFailMsg = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strLoginUserName, strLoginPassword, strLoginPassword,
						strFullUserName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(selenium,
						strResource1, false, false, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(selenium,
						strResource2, false, false, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objCreateUsers.savVrfyUser(selenium,
						strLoginUserName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(selenium));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// navigate to Event Template
				strFailMsg = objEve.createEventTemplate(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// fill the required fields in Create Event Template and save
				strFailMsg = objEve.fillMandfieldsAndSaveEveTemp(selenium,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strResTypeArr,
						strSTvalue, true, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.newUsrLogin(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "Event Management screen is NOT displayed";
				/*
				 * Step 3: Click on 'Create New Event' button. <-> 'Select Event
				 * Template' screen is displayed.
				 */
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			/*
			 * Step 4: Click on 'Create' link associated with 'ET'. <-> 'Create
			 * New Event' screen is displayed
			 */
			/*
			 * Step 5: Create an event 'EVE' providing mandatory data and
			 * selecting resource RS1. <-> Event 'EVE' is listed on 'Event List'
			 * screen.
			 */
			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = objEve.createEvent(selenium, strEveTemp,
						strResource1, strEveName, strInfo, true);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			/*
			 * Step 6: Click on the event banner of 'EVE'. <-> Resource 'RS1' is
			 * displayed on the 'Event Status' screen under RT along with NST,
			 * MST, TST and SST status types.
			 */
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objList.checkInEventBanner(selenium, strEveName,
						strResType, strResource1, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			/*
			 * Step 7: Navigate to Event >> Event Management, click on 'Edit'
			 * link next to EVE, select resource RS2 and click on 'Save'. <->
			 * 'Event List' screen is displayed
			 */
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = objEve.editEvent(selenium, strEveName, strEveName,
						strInfo, false);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));
				// select the resource
				selenium.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
						+ strResource2
						+ "']/parent::tr/td[1]/input[@name='resourceID']");

				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				/*
				 * Step 8: Click on the event banner of 'EVE'. <-> Resources
				 * 'RS1' and 'RS2' are displayed on the 'Event Status' screen
				 * under RT along with NST, MST, TST and SST status types.
				 */
				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg = objList.checkInEventBanner(selenium,
							strEveName, strResType, strResource1,
							strStatTypeArr);
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}

				try {
					assertTrue(strFailMsg.equals(""));
					try {
						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"
										+ strResource2 + "']"));
						log4j.info("Resources '"
								+ strResource1
								+ "' and '"
								+ strResource2
								+ "' are displayed on the 'Event Status' screen");
						intResCnt++;
					} catch (AssertionError ae) {
						log4j.info("Resources '"
								+ strResource1
								+ "' and '"
								+ strResource2
								+ "' are NOT displayed on the 'Event Status' screen");
						gstrReason = "Resources '"
								+ strResource1
								+ "' and '"
								+ strResource2
								+ "' are NOT displayed on the 'Event Status' screen";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}

				/*
				 * Step 9: Click on 'Mobile View' link on the footer of
				 * application. <-> 'Main Menu' screen is displayed. Step 10:
				 * Click on 'Events' link. <-> 'Event List' screen is displayed.
				 * Step 11: Click on Event 'EVE'. <-> 'Event Detail' screen is
				 * displayed Step 12: Click on 'Resources' link. <-> Resources
				 * 'RS1' and 'RS2' are displayed in the 'Event Resources'
				 * screen.
				 */
				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg = objMob.navToEventDetailInMob(selenium,
							strEveName, strIconSrc, strResource1);
				} catch (AssertionError ae) {
					gstrReason = gstrReason + " " + strFailMsg;
				}

				try {
					assertTrue(strFailMsg.equals(""));

					try {
						assertTrue(selenium.isElementPresent("link="
								+ strResource2));
						log4j.info(strResource2
								+ " is listed in the 'Event Resources' screen.");
						intResCnt++;
					} catch (AssertionError ae) {
						log4j.info(strResource2
								+ " is NOT listed in the 'Event Resources' screen.");
						gstrReason = gstrReason
								+ " "
								+ strResource2
								+ " is NOT listed in the 'Event Resources' screen.";
					}

				} catch (AssertionError ae) {
					gstrReason = gstrReason + " " + strFailMsg;
				}

				if (intResCnt == 2) {
					gstrResult = "PASS";

					// Write result data
					strTestData[0] = propEnvDetails.getProperty("Build");
					strTestData[1] = gstrTCID;
					strTestData[2] = strLoginUserName + "/" + strLoginPassword;
					strTestData[3] = strEveTemp;
					strTestData[4] = strEveName;
					strTestData[5] = "Resources: " + strResource1 + " "
							+ strResource2 + " check in mobile";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestData, strWriteFilePath,
							"Events");

				}
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
		} catch (Exception e) {

			Exception excReason;
			gstrTCID = "68436";
			gstrTO = "Edit event and add new resources and verify that the newly added resources are displayed in the 'Event Status' screen";
			gstrResult = "FAIL";
			excReason = null;

			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();

		}
	}
	/*'***********************************************************************************
	' Description: Edit event and remove resources and verify that the removed resources
	                are no longer displayed in the event detail screen 
	' Arguments:
	' Returns:
	' Date: 17-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                                                       Modified By
	' <Date>                                                               <Name>
	'***********************************************************************************/
	
	@Test
	public void testBQS68437() throws Exception {
		try {
			gstrTCID = "68437";
			gstrTO = "Edit event and remove resources and verify that the removed resources are no " +
					"longer displayed in the event detail screen";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTestData[] = new String[10];
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			ReadData objReadData = new ReadData();
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			Login objLogin = new Login();// object of class Login
			EventSetup objEve = new EventSetup();
			Roles objRole = new Roles();
			EventList objList = new EventList();
			MobileView objMob = new MobileView();
			CreateUsers objCreateUsers = new CreateUsers();

			StatusTypes objST = new StatusTypes();
			int intResCnt = 0;
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			// Read test data from excel
			String strEveTemp = "AutoEvTemp_" + strTimeText;
			String strEveName = "AutoEve_" + strTimeText;
			// String
			// strResourceArr[]=objReadData.readInfoExcel("Event Temp Data", 11,
			// 7, strFILE_PATH).split(",");
			String strResType = "AutoRt_" + strTimeText;

			String strInfo = "This is an automation event";

			String strRegn = objReadData.readData("Login", 3, 4);

			String strLoginUserName = "AutoUsr" + System.currentTimeMillis();
			String strLoginPassword = "abc123";
			String strFullUserName = "Full" + strLoginUserName;

			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";

			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";

			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strSTvalue[] = new String[4];
			String strStatusValue[] = new String[2];

			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strResource1 = "AutoRsa_" + strTimeText;
			String strResource2 = "AutoRsb_" + strTimeText;
			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strTempDef = objReadData.readInfoExcel("Event Temp Data", 2,
					3, strFILE_PATH);
			String strEveColor = objReadData.readInfoExcel("Event Temp Data",
					2, 4, strFILE_PATH);
			String strAsscIcon = objReadData.readInfoExcel("Event Temp Data",
					2, 5, strFILE_PATH);
			String strIconSrc = objReadData.readInfoExcel("Event Temp Data", 2,
					6, strFILE_PATH);

			String strResTypeArr[] = new String[1];
			String strStatTypeArr[] = { strMulTypeName, strNumTypeName,
					strSatuTypeName, strTxtTypeName };

			// login details
			String strAdmUserName = objReadData.readData("Login", 3, 1);
			String strAdmPassword = objReadData.readData("Login", 3, 2);
		
		/*
		 * 	' Precondition: 
		1. Role based status types MST, NST, TST and SST are created.
		2. Resource type RT is created selecting MST, NST, TST and SST status types.
		3. Resources RS1 and TS2 are created under RT
		4. User A is created selecting;
		
		a. View Resource right on RS1 and RS2.
		b. Role R1 to view and update status types NST, MST, TST and SST.
		c. 'Maintain Event Templates' and 'Maintain Events' right.
		
		5. Event Template ET is created selecting RT,NST, MST, TST and SST.
		6. Event EVE is created selecting ET, resources RS1 and RS2.
		 */
		/*
		 * Step 2: Login as user A, navigate to Event >> Event Management <->
		 * 	'Event Management' screen is displayed.
		 */
			selenium.open(propEnvDetails.getProperty("urlRel"));
			String strFailMsg = objLogin.login(selenium, strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			// Multi ST
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMulStatTypeValue, strMulTypeName, strMulStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strMulTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName1, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName2, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			// Number ST

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNumStatTypeValue, strNumTypeName, strNumStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			// Saturation Score ST

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSatuStatTypeValue, strSatuTypeName,
						strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strSatuTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			// Text ST

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strTxtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch Text status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(selenium, strResType,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFailMsg);
					strFailMsg = objRs.selDeselctOnlySTInEditRSLevelPage(
							selenium, strSTvalue[intST], true);

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}

			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(selenium, strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strRestypVal = objRT.fetchResTypeValueInResTypeList(
						selenium, strResType);

				if (strRestypVal.compareTo("") != 0) {
					strFailMsg = "";
					strResTypeArr[0] = strRestypVal;
				} else {
					strFailMsg = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.createResourceWitLookUPadres(selenium,
						strResource1, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strResVal = objRs.fetchResValueInResList(selenium,
						strResource1);

				if (strResVal.compareTo("") != 0) {
					strFailMsg = "";
					strRSValue[0] = strResVal;
				} else {
					strFailMsg = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.createResourceWitLookUPadres(selenium,
						strResource2, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strResVal = objRs.fetchResValueInResList(selenium,
						strResource2);

				if (strResVal.compareTo("") != 0) {
					strFailMsg = "";
					strRSValue[0] = strResVal;
				} else {
					strFailMsg = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName);

				if (strRoleValue.compareTo("") != 0) {
					strFailMsg = "";

				} else {
					strFailMsg = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strLoginUserName, strLoginPassword, strLoginPassword,
						strFullUserName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(selenium,
						strResource1, false, false, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(selenium,
						strResource2, false, false, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objCreateUsers.savVrfyUser(selenium,
						strLoginUserName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(selenium));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// navigate to Event Template
				strFailMsg = objEve.createEventTemplate(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// fill the required fields in Create Event Template and save
				strFailMsg = objEve.fillMandfieldsAndSaveEveTemp(selenium,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strResTypeArr,
						strSTvalue, true, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "Event Management screen is NOT displayed";

				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = objEve.createEvent(selenium, strEveTemp,
						strResource1, strEveName, strInfo, false);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));
				// select Resource RS2
				selenium.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
						+ strResource2
						+ "']/parent::tr/td[1]/input[@name='resourceID']");

				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
		/*
		 * Step 2: Login as user A, click on event banner of event EVE. <->	Resources 'RS1' and 'RS2' are displayed on the 'Event Status' screen under RT along with NST, MST, TST and SST status types. 
		 */
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objLogin.newUsrLogin(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objList.checkInEventBanner(selenium, strEveName,
						strResType, strResource1, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				try {
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"
									+ strResource2 + "']"));
					log4j.info("Resources '" + strResource1 + "' and '"
							+ strResource2
							+ "' are displayed on the 'Event Status' screen");
					intResCnt++;
				} catch (AssertionError ae) {
					log4j.info("Resources '"
							+ strResource1
							+ "' and '"
							+ strResource2
							+ "' are NOT displayed on the 'Event Status' screen");
					gstrReason = "Resources '"
							+ strResource1
							+ "' and '"
							+ strResource2
							+ "' are NOT displayed on the 'Event Status' screen";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		/*
		 * Step 3: Navigate to Event >> Event Management, click on 'Edit' link next to EVE, deselect resource RS2 and click on 'Save'. 	<->	'Event List' screen is displayed. 
		 */
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = objEve.editEvent(selenium, strEveName, strEveName,
						strInfo, false);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));
				// deselect the resource
				selenium.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
						+ strResource2
						+ "']/parent::tr/td[1]/input[@name='resourceID']");

				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
			/*
			 * Step 4: Click on the event banner of 'EVE'. <->	Resource 'RS1' is displayed on the 'Event Status' screen under RT along with NST, MST, TST and SST status types.
			 * <-> Resource 'RS2' is not displayed. 
			 */
				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg = objList.checkInEventBanner(selenium,
							strEveName, strResType, strResource1,
							strStatTypeArr);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFailMsg;
				}

				try {
					assertTrue(strFailMsg.equals(""));
					try {
						assertFalse(selenium
								.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"
										+ strResource2 + "']"));
						log4j.info("Resource '" + strResource2
								+ "' is NOT displayed");
						intResCnt++;
					} catch (AssertionError ae) {
						log4j.info("Resource '" + strResource2
								+ "' is displayed");
						gstrReason = gstrReason + " Resource '" + strResource2
								+ "' is displayed";
					}
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFailMsg;
				}
			
			/*
			 * Step 5: 	Click on 'Mobile View' link on the footer of application. <->	'Main Menu' screen is displayed.
			 * Step 6: 	Click on 'Events' link and click on Event 'EVE'. <-> 'Event Detail' screen is displayed
			 * Step 7: Click on 'Resources' link. <->	Only resource 'RS1' is displayed in the 'Event Resources' screen. 
			 * 
			 */
				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg = objMob.navToEventDetailInMob(selenium,
							strEveName, strIconSrc, strResource1);
				} catch (AssertionError ae) {
					gstrReason = gstrReason + " " + strFailMsg;
				}

				try {
					assertTrue(strFailMsg.equals(""));

					try {
						assertFalse(selenium.isElementPresent("link="
								+ strResource2));
						log4j.info(strResource2
								+ " is not listed in the 'Event Resources' screen.");
						intResCnt++;
					} catch (AssertionError ae) {
						log4j.info(strResource2
								+ " is listed in the 'Event Resources' screen.");
						gstrReason = gstrReason + " " + strResource2
								+ " is listed in the 'Event Resources' screen.";
					}

				} catch (AssertionError ae) {
					gstrReason = gstrReason + " " + strFailMsg;
				}

				if (intResCnt == 3) {
					gstrResult = "PASS";

					// Write result data
					strTestData[0] = propEnvDetails.getProperty("Build");
					strTestData[1] = gstrTCID;
					strTestData[2] = strLoginUserName + "/" + strLoginPassword;
					strTestData[3] = strEveTemp;
					strTestData[4] = strEveName;
					strTestData[5] = "Verify from 8th step : Resource: "
							+ strResource1 + " check in mobile";
					strTestData[6] = "Status Types :" + strMulTypeName + ","
							+ strNumTypeName + "," + strSatuTypeName + ","
							+ strTxtTypeName;

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestData, strWriteFilePath,
							"Events");

				}
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
		} catch (Exception e) {

			Exception excReason;
			gstrTCID = "68437";
			gstrTO = "Edit event and remove resources and verify that the removed resources are no longer displayed in the event detail screen";
			gstrResult = "FAIL";
			excReason = null;

			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();

		}
	}
}
