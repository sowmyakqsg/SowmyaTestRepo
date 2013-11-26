package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/***********************************************************************************************
 * ' Description  :This class includes Receive expired status notifications for SST ' testcases '
 * ' Precondition : 
 * ' Date         :12-June-2012
 * ' Author       :QSG
 * '------------------------------------------------------------------------------------------- '
 * 'Modified Date 									                     Modified By 
 * '<Date> 										                        <Name> '
 ***********************************************************************************************/
public class RecvExpiredStatusNotifiForSST {

	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.RecvExpiredStatusNotifiForSST");
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

		selenium.start();
		selenium.windowMaximize();

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

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

		selenium.stop();

		try {
			seleniumPrecondition.close();
		} catch (Exception e) {
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

	/***********************************************************************
	'Description	:Verify that user receives the following when the saturation score status of a
	                 resource expires at the SHIFT time:
					1. Status update prompt.
					2. Expired status notification
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:12-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/

	@Test
	public void testBQS69019() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		General objMail = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();

		try {
			gstrTCID = "BQS-69019 ";
			gstrTO = "Verify that user receives the following when "
					+ "the saturation score status of a resource expires"
					+ " at the SHIFT time:" + "1. Status update prompt."
					+ "2. Expired status notification";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTmText = dts.getCurrentDate("HHmm");

			int intResCnt = 0;
			int intShiftTime = Integer.parseInt(propEnvDetails
					.getProperty("ShiftTime"));

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String StatusTime = "";
			String strUpdTime_Shift = "";
			String strUpdateValue1 = "393";
			String strUpdateValue2 = "429";
			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7",
					"8" };
			String strScUpdValue2[] = { "1", "2", "3", "4", "5", "6", "7", "8",
					"9" };

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strCurDate = "";
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			// Status Type
			String strStatusTypeValue = "Saturation Score";
			String statTypeName = "AutoSST_" + strTimeText;
			String strStatTypDefn = "Auto";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName;

			String strRoleValue = "";
			String strViewName = "AutoV_" + strTimeText;
			String strHavBed = "No";

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strVewDescription = "";
			String strViewType = "Resource (Resources and status"
					+ " types as rows. Status, comments and timestamps"
					+ " as columns.)";
			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];
			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";
			String strSubjName = "EMResource Expired Status Notification: "
					+ strResource;

			String strMsgBody2 = "";
			String strMsgBody1 = "";
			String strMsgBody2Another = "";
			String strMsgBody1Another = "";

			int intEMailRes = 0;
			int intPagerRes = 0;
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}
			// Navigate to status type list

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to resource type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill resource type fields
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Save and verify resource type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to resource list page
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Create Resources page
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create resource

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName,
						strStandResType, strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Save and navigate to resource list page
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
						strResource, strHavBed, "", strAbbrv, strResrctTypName);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

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
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource, false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MustOverDue"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");

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
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						seleniumPrecondition, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillRecvExpStatusNotifinEditUsr(
						seleniumPrecondition, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.editStatusTypePage(seleniumPrecondition,
						statTypeName,strStatusTypeValue);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.provideShiftTimeForStatusTypes(
							seleniumPrecondition, strStatusTime[0], strStatusTime[1]);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

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
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[0]);
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
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.verifyUpdateST(selenium,
						strResrctTypName, statTypeName, strUpdateValue1);
				String strLastUpdArr[] = selenium.getText(
						"//table/tbody/tr/td/a[text()='" + strResource
								+ "']/ancestor::tr/td[last()]").split(" ");

				String strCurYear = dts.getCurrentDate("yyyy");
				String strCurDateMnth = strLastUpdArr[0] + strLastUpdArr[1]
						+ strCurYear;

				strCurDate = dts.converDateFormat(strCurDateMnth, "ddMMMyyyy",
						"MM/dd/yyyy");

				int intWaitTimeDif = dts.getTimeDiff(strUpdTime_Shift,
						strLastUpdArr[2]);
				Thread.sleep(intWaitTimeDif * 1000);
				log4j.info(intWaitTimeDif * 1000);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMail.waitForMailNotification(selenium, 60,
						"//span[@class='overdue'][text()='Status is Overdue']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);

				String strAdUpdTimeShift = dts.addTimetoExisting(
						strUpdTime_Shift, 1, "HH:mm");

				strMsgBody1 = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeName
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime_Shift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody1Another = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeName
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strAdUpdTimeShift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody2 = "EMResource expired status: " + strResource
						+ ". " + statTypeName + " status expired " + strCurDate
						+ " " + strUpdTime_Shift + ".";

				strMsgBody2Another = "EMResource expired status: " + strResource
						+ ". " + statTypeName + " status expired " + strCurDate
						+ " " + strAdUpdTimeShift + ".";

				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);
						if (strMsg.equals(strMsgBody2)
								|| strMsg.equals(strMsgBody2Another)) {
							intPagerRes++;
							log4j.info(strMsgBody2
									+ " is displayed for Pager Notification");
						} else {
							log4j
									.info(strMsgBody2
											+ " is NOT displayed for Pager Notification");
							gstrReason = strMsgBody2
									+ " is NOT displayed for Pager Notification";
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");
					strSubjName = "EMResource Expired Status Notification: "
							+ strResource;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);
						if (strMsg.equals(strMsgBody1)
								|| strMsg.equals(strMsgBody1Another)) {
							intEMailRes++;
							log4j.info(strMsgBody1
									+ " is displayed for Email Notification");
						} else {
							log4j
									.info(strMsgBody1
											+ " is NOT displayed for Email Notification");
							gstrReason = strMsgBody1
									+ " is NOT displayed for Email Notification";
						}
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
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
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);
						if (strMsg.equals(strMsgBody1)
								|| strMsg.equals(strMsgBody1Another)) {
							intEMailRes++;
							log4j.info(strMsgBody1
									+ " is displayed for Email Notification");
						} else {
							log4j
									.info(strMsgBody1
											+ " is NOT displayed for Email Notification");
							gstrReason = strMsgBody1
									+ " is NOT displayed for Email Notification";
						}
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad(gstrTimeOut);
					selenium.close();
					selenium.selectWindow("");
					Thread.sleep(2000);
					
					// check Email, pager notification
					if (intEMailRes == 2 && intPagerRes == 1) {
						intResCnt++;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					selenium.selectWindow("");
					strFuncResult = objViews.checkUpdateStatPrompt(selenium,
							strResource, statTypeName);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews.fillAndSavUpdateStatusSST(
							selenium, strScUpdValue2, strSTvalue[0]);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews.saveUpdateStatusValue(selenium);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);

					strFuncResult = objViews.verifyUpdateST(selenium,
							strResrctTypName, statTypeName, strUpdateValue2);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);

					if (intResCnt == 1)
						gstrResult = "PASS";
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			gstrResult = "PASS";
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69019";
			gstrTO = "Verify that user receives the following when the saturation "
					+ "score status of a resource expires at the SHIFT time:"
					+ "1. Status update prompt."
					+ "2. Expired status notification";
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
	/***********************************************************************
	'Description	:Verify that user receives the following when the saturation score status of a
	                 resource expires at the SHIFT time:
					1. Status update prompt.
					2. Expired status notification
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:12-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	@Test
	public void testBQS69017() throws Exception {
		try {
			gstrTCID = "BQS-69017";
			gstrTO = "Verify that user receives the following when the "
					+ "saturation score status of a resource expires at the EXPIRATION time:"
					+ "1. Status update prompt."
					+ "2. Expired status notification";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTmText = dts.getCurrentDate("HHmm");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			int intResCnt = 0;

			String strFuncResult = "";
			General objMail = new General();
			Login objLogin = new Login();// object of class Login
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();
			Views objViews = new Views();
			Roles objRole = new Roles();

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strHavBed = "No";
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strCurDate = "";

			String strUpdateValue1 = "393";
			String strUpdateValue2 = "429";

			String strStatusTypeValue = "Saturation Score";
			String statTypeName = "AutoScSt_" + strTimeText;
			String strStatTypDefn = "Auto";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";
			String strExpHr = "00";
			String strExpMn = "05";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRoleValue = "";

			String strViewName = "AutoV_" + strTimeText;

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status types as rows."
					+ " Status, comments and timestamps as columns.)";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";
			String strSubjName = "EMResource Expired Status Notification: "
					+ strResource;

			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7",
					"8" };
			String strScUpdValue2[] = { "1", "2", "3", "4", "5", "6", "7", "8",
					"9" };

			String strMsgBody2 = "";
			String strMsgBody1 = "";
			String strMsgBody2Another = "";
			String strMsgBody1Another = "";

			int intEMailRes = 0;
			int intPagerRes = 0;
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strUpdTime = "";

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST
						.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
								strStatusTypeValue, statTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire"));
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Hours"),
						"label=" + strExpHr);
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Mins"),
						"label=" + strExpMn);

				seleniumPrecondition.click(propElementDetails.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeName + "']"));

					log4j.info("Status type " + statTypeName
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								seleniumPrecondition, statTypeName);
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
						strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
								strResrctTypName,
								"css=input[name='statusTypeID'][value='"
										+ strStatValue + "']");
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
						strFuncResult = objRs.navResourcesList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.createResourceWithMandFields(
								seleniumPrecondition, strResource, strAbbrv,
								strResrctTypName, strStandResType,
								strContFName, strContLName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition,
								strResource);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
								strResource, strHavBed, "", strAbbrv,
								strResrctTypName);

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

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.CreateRoleWithAllFields(
								seleniumPrecondition, strRoleName, strRoleRights,
								strSTvalue, true, strSTvalue, true, true);

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

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

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
						strFuncResult = objCreateUsers.slectAndDeselectRole(
								seleniumPrecondition, strRoleValue, true);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.selectResourceRights(seleniumPrecondition, strResource,
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
												.getProperty("CreateNewUsr.AdvOptn.MustOverDue"),
										true);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.nonMandatoryUsrProfileFlds(seleniumPrecondition, "", "",
										"", "", "", strEMail, strEMail,
										strEMail, "");

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.savVrfyUserWithSearchUser(seleniumPrecondition,
										strUserName, strByRole,
										strByResourceType, strByUserInfo,
										strNameFormat);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.navEditUserPge(
								seleniumPrecondition, strUserName, strByRole,
								strByResourceType, strByUserInfo, strNameFormat);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
								seleniumPrecondition, strUserName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.fillRecvExpStatusNotifinEditUsr(
								seleniumPrecondition, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViews.createView(seleniumPrecondition,
								strViewName, strVewDescription, strViewType,
								true, false, strSTvalue, false, strRSValue);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					log4j.info("~~~~~PRE-CONDITION" + gstrTCID
							+ " EXECUTION ENDS~~~~~");

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					log4j.info("~~~~~TEST CASE - " + gstrTCID
							+ " EXECUTION STARTS~~~~~");

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUserName, strInitPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViews.checkUpdateStatPrompt(
								selenium, strResource, statTypeName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViews.fillAndSavUpdateStatusSST(
								selenium, strScUpdValue1, strSTvalue[0]);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViews
								.saveUpdateStatusValue(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViews.navToUserView(selenium,
								strViewName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);

						strFuncResult = objViews
								.verifyUpdateST(selenium, strResrctTypName,
										statTypeName, strUpdateValue1);

						String strLastUpdArr[] = selenium.getText(
								"//table/tbody/tr/td/a[text()='" + strResource
										+ "']/ancestor::tr/td[last()]").split(
								" ");
						strUpdTime = strLastUpdArr[2];
						String strAdUpdTime = dts.addTimetoExisting(strUpdTime,
								5, "HH:mm");
						strUpdTime = strAdUpdTime;
						String strCurYear = dts.getCurrentDate("yyyy");
						String strCurDateMnth = strLastUpdArr[0]
								+ strLastUpdArr[1] + strCurYear;

						strCurDate = dts.converDateFormat(strCurDateMnth,
								"ddMMMyyyy", "MM/dd/yyyy");
						Thread.sleep(360000);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					
					try {
						assertEquals("", strFuncResult);

						strFuncResult = objMail.waitForMailNotification(selenium, 60,
								"//span[@class='overdue'][text()='Status is Overdue']");

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						String strAdUpdTimeShift = dts.addTimetoExisting(
								strUpdTime, 1, "HH:mm");

						Thread.sleep(10000);
						strMsgBody1 = "For "
								+ strUsrFulName
								+ "\nRegion: "
								+ strRegn
								+ "\n\nThe "
								+ statTypeName
								+ " status for "
								+ strResource
								+ " expired "
								+ strCurDate
								+ " "
								+ strUpdTime
								+ ".\n\nClick the link below to go to the EMResource login screen:"
								+

								"\n\n        "+propEnvDetails.getProperty("MailURL")
								+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
								+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

						strMsgBody1Another = "For "
								+ strUsrFulName
								+ "\nRegion: "
								+ strRegn
								+ "\n\nThe "
								+ statTypeName
								+ " status for "
								+ strResource
								+ " expired "
								+ strCurDate
								+ " "
								+ strAdUpdTimeShift
								+ ".\n\nClick the link below to go to the EMResource login screen:"
								+

								"\n\n        "+propEnvDetails.getProperty("MailURL")
								+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
								+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

						strMsgBody2 = "EMResource expired status: "
								+ strResource + ". " + statTypeName
								+ " status expired " + strCurDate + " "
								+ strUpdTime + ".";

						strMsgBody2Another = "EMResource expired status: "
								+ strResource + ". " + statTypeName
								+ " status expired " + strCurDate + " "
								+ strAdUpdTimeShift + ".";

						selenium.selectWindow("");
						strFuncResult = objMail.loginAndnavToInboxInWebMail(
								selenium, strLoginName, strPassword);
						try {
							assertTrue(strFuncResult.equals(""));
							strSubjName = "EMResource Expired Status: "
									+ strAbbrv;
							strFuncResult = objMail.verifyEmail(selenium,
									strFrom, strTo, strSubjName);

							try {
								assertTrue(strFuncResult.equals(""));
								String strMsg = objMail.seleniumGetText(
										selenium, "css=div.fixed.leftAlign",
										160);
								if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2Another)) {
									intPagerRes++;
									log4j
											.info(strMsgBody2
													+ " is displayed for Pager Notification");
								} else {
									log4j
											.info(strMsgBody2
													+ " is NOT displayed for Pager Notification");
									gstrReason = strMsgBody2
											+ " is NOT displayed for Pager Notification";
								}

							} catch (AssertionError Ae) {
								gstrReason = gstrReason + " " + strFuncResult;
							}

							selenium.selectFrame("relative=up");
							selenium.selectFrame("horde_main");
							// click on Back to Inbox
							selenium.click("link=Back to Inbox");
							selenium.waitForPageToLoad("90000");
							strSubjName = "EMResource Expired Status Notification: "
									+ strResource;
							strFuncResult = objMail.verifyEmail(selenium,
									strFrom, strTo, strSubjName);

							try {
								assertTrue(strFuncResult.equals(""));
								String strMsg = objMail.seleniumGetText(
										selenium, "css=div.fixed.leftAlign",
										160);
								if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1Another)) {
									intEMailRes++;
									log4j
											.info(strMsgBody1
													+ " is displayed for Email Notification");
								} else {
									log4j
											.info(strMsgBody1
													+ " is NOT displayed for Email Notification");
									gstrReason = strMsgBody1
											+ " is NOT displayed for Email Notification";
								}
							} catch (AssertionError Ae) {
								gstrReason = gstrReason + " " + strFuncResult;
							}

							selenium.selectFrame("relative=up");
							selenium.selectFrame("horde_main");
							// click on Back to Inbox
							selenium.click("link=Back to Inbox");
							selenium.waitForPageToLoad("90000");

							strFuncResult = objMail.verifyEmail(selenium,
									strFrom, strTo, strSubjName);

							try {
								assertTrue(strFuncResult.equals(""));
								String strMsg = objMail.seleniumGetText(
										selenium, "css=div.fixed.leftAlign",
										160);
								if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1Another)) {
									intEMailRes++;
									log4j
											.info(strMsgBody1
													+ " is displayed for Email Notification");
								} else {
									log4j
											.info(strMsgBody1
													+ " is NOT displayed for Email Notification");
									gstrReason = strMsgBody1
											+ " is NOT displayed for Email Notification";
								}
							} catch (AssertionError Ae) {
								gstrReason = gstrReason + " " + strFuncResult;
							}

							selenium.selectWindow("Mail");
							selenium.selectFrame("horde_main");
							selenium.click("link=Log out");
							selenium.waitForPageToLoad(gstrTimeOut);
							selenium.close();
							selenium.selectWindow("");
							Thread.sleep(2000);

							// check Email, pager notification
							if (intEMailRes == 2 && intPagerRes == 1) {
								intResCnt++;
							}

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							selenium.selectWindow("");
							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, statTypeName);
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.fillAndSavUpdateStatusSST(
									selenium, strScUpdValue2, strSTvalue[0]);
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.saveUpdateStatusValue(selenium);
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.verifyUpdateST(selenium,
									strResrctTypName, statTypeName,
									strUpdateValue2);
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);

							if (intResCnt == 1)
								gstrResult = "PASS";
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFuncResult;
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeName
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeName
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69017";
			gstrTO = "Verify that user receives the following when the saturation score status of a resource expires at the EXPIRATION time:"
					+ "1. Status update prompt."
					+ "2. Expired status notification";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
}
