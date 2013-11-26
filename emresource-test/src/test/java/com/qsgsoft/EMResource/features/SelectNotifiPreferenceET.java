package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;

import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


/**********************************************************************
' Description :This class includes SelectNotifiPreferenceET requirement 
'				testcases
' Precondition:
' Date		  :30-May-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class SelectNotifiPreferenceET  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.SelectNotifiPreferenceET");
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
	
	String gstrTimeOut="";
	
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
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));

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
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
		
		selenium.close();
		
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

		  gstrReason=gstrReason.replaceAll("'", " ");
		  objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
		    gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
		    gslsysDateTime, gstrBrowserName, gstrBuild,strSessionId);
	}
	
	
	/********************************************************************************
	'Description	:Verify that user can set 'Event Notification' preferences.
	'Precondition	:1. Event template 'ET' is created selecting status type 'ST' and 
	'				 resource type 'RT' without selecting Email/Pager/Web notification preferences.
	
	'				 2. User 'A' is provided with 'Email' and 'Pager' addresses.(created within tc)
	'				 3. User 'A' has 'Edit Event Notification Preferences' right. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:5-June-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS118373() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		EventSetup objEventSetup=new EventSetup();
		EventList objEventList=new EventList();
		EventNotification objEventNotification=new EventNotification();
		Preferences objPreferences=new Preferences();
		General objMail = new General();
		General objGeneral = new General();

		try {
			gstrTCID = "118373";
			gstrTO = "Verify that user can set "
					+ "'Event Notification' preferences.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strApplTime="";
			String strAddTime="";
			
			String strLoginUserName=rdExcel.readData("Login", 3, 1);
			String strLoginPassword=rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login", 3, 4);
			
			
			// Users mandatory fields
			String strUserName = "";
			String strInitPwd = "";
			String strConfirmPwd = "";
			String strUsrFulName = "";
			String strOptions = "";
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName = "AutoST_" +strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" +strTimeText;
			String strRsTypeValues[]=new String[1];
			
			// Event template
			String strTempName = "Autotmp_" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			String strStartDate = "";
			String strStartDateEnd = "";
			String strStartDateEnd1 = "";
			String strETValues[]=new String[1];

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
	
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;
			
			int intEMailResEdit = 0;
			int intPagerResEdit = 0;
			
			int intEMailResEnd = 0;
			int intPagerResEnd = 0;
			
			
			String strEveName = "Eve_" + strTimeText;
			String strInfo = strEveName;
			String strAdminFullName = rdExcel.readData("Login", 4, 1);	
			String strEditEveName="";

			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			
			String strMsgBodyEmail = "";
			String strMsgBodyPager = "";
			String strSubjName = "";
			
			String strMsgBodyEmailEdit = "";
			String strMsgBodyPagerEdit = "";
			String strSubjNameEdit="";
			
			String strMsgBodyEmailEnd = "";
			String strMsgBodyPagerEnd = "";
			
			String strMsgBodyEmailEnd1 = "";
			String strMsgBodyPagerEnd1 = "";
			
			String strSubjNameEnd="";
			
				
			
			
			/*	Precondition:
				1. Event template 'ET' is created selecting status type 'ST' and resource type 'RT' without selecting Email/Pager/Web notification preferences.
				2. User 'A' is provided with 'Email' and 'Pager' addresses.
				3. User 'A' has 'Edit Event Notification Preferences' right. 
			 */

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
				     + " EXECUTION STATRTS~~~~~");
					
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);
			
			
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
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);

				if (strStatusTypeValues[0].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);

				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
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
				String[] strResTypeVal = {strRsTypeValues[0]};
				String[] strStatusTypeval = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strETValues[0] =objEventSetup.fetchETInETList(seleniumPrecondition, strTempName);

				if (strETValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventNotification
						.selectAndDeselectEventNotifForAll(seleniumPrecondition,
								strTempName, false, false, false);
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

				strUserName = "User" + System.currentTimeMillis();
				strInitPwd = rdExcel.readData("Login", 4, 2);
				strConfirmPwd = strInitPwd;
				strUsrFulName = "Full" + strUserName;

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
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

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditEventNotiPrefer");
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

			
			
			/* * STEP 2: Login as user 'A' and navigate to 'Preferences >> Event
			 * Notification' 'My Event Notification Preferences' page is
			 * displayed. <->Event template 'ET' is displayed. Check box
			 * corresponding to 'Email', 'Pager' & 'Web' is not selected.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objPreferences
						.navEventNotificationPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifyEventNotifInMyEventNotifPref(selenium,
								strETValues[0], false, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * STEP 3: Select to receive Email, pager and web notifications for
			 * an event template ET and 'Save'<-> Check box corresponding to
			 * 'Email', 'Pager' & 'Web' are now selected.
			 
			*/

			try {
				assertEquals("", strFuncResult);
				String[] strTemplateValue = { strETValues[0] };
				strFuncResult = objPreferences
						.selectEvenNofMethodsOnlyWitoutPageVerification(selenium,
								strTemplateValue, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifyEventNotifInMyEventNotifPref(selenium,
								strETValues[0], true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);				
				strFuncResult=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;				
				strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;				
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 4: Create an event 'Eve' under 'ET' User A receives email,
			 * pager and web notification
			 */
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objEventSetup.navToEventManagementNew(selenium);				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
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


				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEveName + "']"));
					log4j.info("Event '" + strEveName
							+ "' is listed on 'Event Management' screen.");
					
				} catch (AssertionError ae) {
					log4j.info("Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.");
					gstrReason = "Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.";
					strFuncResult=gstrReason;
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

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

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
				strMsgBodyEmail = "Event Notice for " + strUsrFulName + ": \n"
						+ strInfo + "\nFrom: "+strAdminFullName+""+ "\nRegion: "+strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required."+"\n"+propEnvDetails.getProperty("MailURL");
				strMsgBodyPager = strInfo + "\nFrom: "+strAdminFullName+""+ "\nRegion: " + strRegn;

	
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				
				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
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
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult=objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strEditEveName = "EDIT"+strEveName;
				strFuncResult = objEventSetup.editEvent(selenium, strEveName,
						strEditEveName, strInfo, false);
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

	

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEditEveName + "']"));
					log4j.info("Event '" + strEditEveName
							+ "' is listed on 'Event Management' screen.");
					
				} catch (AssertionError ae) {
					log4j.info("Event '" + strEditEveName
							+ "' is NOT listed on 'Event Management' screen.");
					gstrReason = "Event '" + strEditEveName
							+ "' is NOT listed on 'Event Management' screen.";
					strFuncResult = gstrReason;
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

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
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
				strFuncResult = objEventNotification.ackWebNotification(
						selenium, "Update 1: " + strEditEveName, strInfo,
						strStartDate);
				try {
					assertTrue(strFuncResult.equals(""));
					intResCnt++;
					Thread.sleep(70000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				
				strSubjNameEdit = "Update 1: "+strEditEveName;
				strMsgBodyEmailEdit = "Event Notice for " + strUsrFulName + ": \n"
						+ strInfo+ "\nFrom: "+strAdminFullName+""+ "\nRegion: "+strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required."+"\n"+propEnvDetails.getProperty("MailURL");
				
			
				strMsgBodyPagerEdit = strInfo + "\nFrom: "+strAdminFullName+""+ "\nRegion: " + strRegn;
				
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				
				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjNameEdit);
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
						if (strMsg.equals(strMsgBodyEmailEdit)) {
							intEMailResEdit++;
							log4j.info("Email body is displayed correctly");
						} else if (strMsg.equals(strMsgBodyPagerEdit)) {
							intPagerResEdit++;
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjNameEdit);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
						
						if (strMsg.equals(strMsgBodyEmailEdit)) {
							intEMailResEdit++;
							log4j.info("Email body is displayed correctly");
						} else if (strMsg.equals(strMsgBodyPagerEdit)) {
							intPagerResEdit++;
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjNameEdit);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						if (strMsg.equals(strMsgBodyEmailEdit)) {
							intEMailResEdit++;
							log4j.info("Email body is displayed correctly");
						} else if (strMsg.equals(strMsgBodyPagerEdit)) {
							intPagerResEdit++;
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
					if (intEMailResEdit == 2 && intPagerResEdit == 1) {
						intResCnt++;
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
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				selenium.click("//div[@id='mainContainer']/table/tbody/tr/"
						+ "td[6][text()='" + strEditEveName
						+ "']/parent::tr/td[1]/a[text()='End']");
				assertTrue(selenium
						.getConfirmation()
						.matches(
								"^Are you sure you "
										+ "want to end this event[\\s\\S]\n\nPress OK to end the "
										+ "event\\. Press Cancel if you do NOT want to end the event\\.$"));
				
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
				
				strAddTime=dts.addTimetoExisting(strCurentDat[2]+":"+strCurentDat[3], 1, "HH:mm");


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(30000);
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objEventList.navToEventListNew(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				String strElementID = "//div[@id='mainContainer']/table"
									+ "/tbody/tr/td[text()='"
									+ strEditEveName
									+ "']"
									+ "/parent::tr/td/a[contains(text(),'View')]";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objEventSetup.navToEventManagementNew(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try{
					assertEquals(selenium.getText("//div[@id='mainContainer']/table"
									+ "/tbody/tr/td[text()='"
									+ strEditEveName
									+ "']"
									+ "/parent::tr/td/a"),"View�History");
					log4j.info("Event ended");
					
				}catch(AssertionError Ae){
					strFuncResult="Event NOT ended";
					log4j.info("Event NOT ended");
					gstrReason = strFuncResult;
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

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
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
				
				strStartDateEnd1= strStartDateEnd + " " +strAddTime;
				
				strStartDateEnd = strStartDateEnd + " " + strFndStHr + ":"
						+ strFndStMinu;
				
				
				
				strInfo="Event ended.";
				
				strFuncResult = objEventNotification.ackWebNotification(
						selenium, "End of Update 1: "+strEditEveName, strInfo,
						strStartDate);
				try {
					assertTrue(strFuncResult.equals(""));
					intResCnt++;
					Thread.sleep(80000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				
				strSubjNameEnd = "End of Update 1: " + strEditEveName;
				strMsgBodyEmailEnd = "Event Notice for "
						+ strUsrFulName
						+ ": "
						+ "\n"
						+ strEditEveName
						+ " ended at "
						+ strStartDateEnd
						+ "\nFrom: "+strAdminFullName+""
						+ "\n"
						+ "Region: "
						+ strRegn
						+ "\n\nPlease do not reply"
						+ " to this email message. You must log into EMResource to take"
						+ " any action that may be required." + "\n"
						+ propEnvDetails.getProperty("MailURL");

				strMsgBodyEmailEnd1 = "Event Notice for "
						+ strUsrFulName
						+ ": "
						+ "\n"
						+ strEditEveName
						+ " ended at "
						+ strStartDateEnd1
						+ "\nFrom: "+strAdminFullName+""
						+ "\n"
						+ "Region: "
						+ strRegn
						+ "\n\nPlease do not reply"
						+ " to this email message. You must log into EMResource to take"
						+ " any action that may be required." + "\n"
						+ propEnvDetails.getProperty("MailURL");

				Writer output = null;
				String text = strMsgBodyEmailEnd;
				File file = new File(
						"C:\\Documents and Settings\\All Users\\Desktop\\Input.txt");
				output = new BufferedWriter(new FileWriter(file));
				output.write(text);
				output.close();

				strMsgBodyPagerEnd =strEditEveName+" ended at "+strStartDateEnd+ "\nFrom: "+strAdminFullName+""+"\nRegion: "+strRegn;
				strMsgBodyPagerEnd1 =strEditEveName+" ended at "+strStartDateEnd1+ "\nFrom: "+strAdminFullName+""+"\nRegion: "+strRegn;
				
				output = null;
				text = strMsgBodyPagerEnd;
				file = new File("C:\\Documents and Settings\\All Users\\Desktop\\InputPager.txt");
				output = new BufferedWriter(new FileWriter(file));
				output.write(text);
				output.close();

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				
				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjNameEnd);
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
						output = null;
						text = strMsg;
						file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();
						
						
						if (strMsg.equals(strMsgBodyEmailEnd)||strMsg.equals(strMsgBodyEmailEnd1)) {
							intEMailResEnd++;
							log4j.info("Email body is displayed correctly");
						} else if (strMsg.equals(strMsgBodyPagerEnd)||strMsg.equals(strMsgBodyPagerEnd1)) {
							intPagerResEnd++;
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjNameEnd);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						output = null;
						text = strMsg;
						file = new File("C:\\Documents and Settings\\All Users\\Desktop\\MailPager.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();
						
						
						if (strMsg.equals(strMsgBodyEmailEnd)||strMsg.equals(strMsgBodyEmailEnd1)) {
							intEMailResEnd++;
							log4j.info("Email body is displayed correctly");
						} else if (strMsg.equals(strMsgBodyPagerEnd)||strMsg.equals(strMsgBodyPagerEnd1)) {
							intPagerResEnd++;
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjNameEnd);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						if (strMsg.equals(strMsgBodyEmailEnd)||strMsg.equals(strMsgBodyEmailEnd1)) {
							intEMailResEnd++;
							log4j.info("Email body is displayed correctly");
						} else if (strMsg.equals(strMsgBodyPagerEnd)||strMsg.equals(strMsgBodyPagerEnd1)) {
							intPagerResEnd++;
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
					if (intEMailResEnd == 2 && intPagerResEnd == 1) {
						intResCnt++;
					}
					
					selenium.close();
					selenium.selectWindow("");
					
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// verify result
				if (intResCnt == 6)
					gstrResult = "PASS";


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
		.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118373";
			gstrTO = "Verify that user can set "
					+ "'Event Notification' preferences.";
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
	'Description	:Verify that user can edit the event notification preferences.
	'Precondition	:1. Event template 'ET' is created selecting status type 'ST' and 
	'				 resource type 'RT' without selecting Email/Pager/Web notification preferences.
	
	'				 2. User 'A' is provided with 'Email' and 'Pager' addresses.(created within tc)
	'				 3. User 'A' has 'Edit Event Notification Preferences' right. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:5-June-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS118372() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetup=new EventSetup();
		EventNotification objEventNotification=new EventNotification();
		General objMail = new General();
		General objGeneral = new General();
		Preferences objPreferences=new Preferences();

		try {
			gstrTCID = "118372";
			gstrTO = "Verify that user can edit the event notification preferences.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strApplTime="";
			
			String strLoginUserName=rdExcel.readData("Login", 3, 1);
			String strLoginPassword=rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login",3, 4);
			
			
			// Users mandatory fields
			String strUserName = "User" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulName = "Full" + strUserName;

			String strOptions = "";
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
		
			// Event template
			String strTempName = "Autotmp_" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			String strStartDate = "";
			String strStartDateEnd = "";
			String strETValues[]=new String[1];

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
	
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;
			
			
			String strEveName = "Eve_" + strTimeText;
			String strInfo = strEveName;
			String strAdminFullName = rdExcel.readData("Login", 4, 1);	
			String strEditEveName="";

			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			
			String strMsgBodyEmail = "";
			String strSubjName = "";
			
			String strMsgBodyEmailEdit = "";
			String strSubjNameEdit="";
			
			String strMsgBodyEmailEnd = "";
			String strSubjNameEnd="";
			
				
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
				     + " EXECUTION STATRTS~~~~~");
					
			strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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

				
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
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

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditEventNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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

				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = {};
				String[] strStatusTypeval = {};
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						selenium, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strETValues[0] =objEventSetup.fetchETInETList(selenium, strTempName);

				if (strETValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventNotification
						.selectAndDeselectEventNotifForAll(selenium,
								strTempName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			*/
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventNotification.selectEventNofifForUser(
						selenium, strUsrFulName, strTempName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
				     + " EXECUTION ENDS~~~~~");
					
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 2 Login as user 'A' and navigate to 'Preferences >> Event
			 * Notification' 'My Event Notification Preferences' page is
			 * displayed.
			 */
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences
						.navEventNotificationPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifyEventNotifInMyEventNotifPrefAndET(selenium,
								strTempName, strETValues[0], true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.selectEvenNofMethodsOnlyWitoutPageVerification(selenium,
						strETValues, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifyEventNotifInMyEventNotifPref(selenium,
								strETValues[0], true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 4 Logout and login as RegAdmin and navigate to Setup>>Users,
			 * select to edit user A. 'Edit User' screen is displayed.
			 */	
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;

				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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
			
			
			/*
			 * 5 Under 'User Preferences' section, click on the the link 'Event
			 * Notification Preferences'. "Event Notification Preferences for A"
			 * screen is displayed.
			 * 
			 * Check box corresponding to 'Email' & 'Web' are selected for ET.
			 * 
			 * Check box corresponding to 'Pager' is not selected for ET.
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEventNotificationPreferencePage(selenium,
								strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifyEventNotifInMyEventNotifPref(selenium,
								strETValues[0], true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Navigate to Events>>Event Management and Create an event 'Eve'
			 * under 'ET' User A receives email and web notification but not
			 * pager.
			 */
			
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objEventSetup.navToEventManagementNew(selenium);				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
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


				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEveName + "']"));
					log4j.info("Event '" + strEveName
							+ "' is listed on 'Event Management' screen.");
					
				} catch (AssertionError ae) {
					log4j.info("Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.");
					gstrReason = "Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.";
					strFuncResult=gstrReason;
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

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

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
				strMsgBodyEmail = "Event Notice for " + strUsrFulName + ": \n"
						+ strInfo + "\nFrom: "+strAdminFullName+"" +  "\nRegion: "
						+ strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required." + "\n"
						+ propEnvDetails.getProperty("MailURL");
				
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				
				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						if (strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly");
						} else {
							
							log4j.info("Email body is NOT displayed correctly");
							strFuncResult="Email body is NOT displayed correctly";
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						if (strMsg.equals(strMsgBodyEmail)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly");
						} else {
							log4j.info("Email body is NOT displayed correctly");
							strFuncResult="Email body is NOT displayed correctly";
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjName);

					try {
						assertTrue(strFuncResult.equals("The mail with subject " + strSubjName
						+ " is NOT present in the inbox"));
						
						intPagerRes++;
						strFuncResult="";
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
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
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);				
				strFuncResult=objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strEditEveName = "EDIT"+strEveName;
				strFuncResult = objEventSetup.editEvent(selenium, strEveName,
						strEditEveName, strInfo, false);
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

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEditEveName + "']"));
					log4j.info("Event '" + strEditEveName
							+ "' is listed on 'Event Management' screen.");
					
				} catch (AssertionError ae) {
					log4j.info("Event '" + strEditEveName
							+ "' is NOT listed on 'Event Management' screen.");
					gstrReason = "Event '" + strEditEveName
							+ "' is NOT listed on 'Event Management' screen.";
					strFuncResult = gstrReason;
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

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
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
				strFuncResult = objEventNotification.ackWebNotification(
						selenium, "Update 1: " + strEditEveName, strInfo,
						strStartDate);
				try {
					assertTrue(strFuncResult.equals(""));
					intResCnt++;
					Thread.sleep(70000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				
				strSubjNameEdit = "Update 1: " + strEditEveName;
				strMsgBodyEmailEdit = "Event Notice for " + strUsrFulName
						+ ": \n" + strInfo + "\nFrom: "+strAdminFullName+"" 
						+ "\nRegion: " + strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required." + "\n"
						+ propEnvDetails.getProperty("MailURL");
			
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				
				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjNameEdit);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						if (strMsg.equals(strMsgBodyEmailEdit)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly");
						} else {
							log4j.info("Email body is NOT displayed correctly");
							strFuncResult="Email body is NOT displayed correctly";
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjNameEdit);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						if (strMsg.equals(strMsgBodyEmailEdit)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly");
						} else {
							log4j.info("Email body is NOT displayed correctly");
							strFuncResult="Email body is NOT displayed correctly";
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjNameEdit);

					try {
						assertTrue(strFuncResult.equals("The mail with subject " + strSubjNameEdit
						+ " is NOT present in the inbox"));
						
						intPagerRes++;
						strFuncResult="";
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				
					
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();
					
					selenium.selectWindow("");
					selenium.selectWindow("");
					Thread.sleep(30000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
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

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				selenium.click("//div[@id='mainContainer']/table/tbody/tr/"
						+ "td[6][text()='" + strEditEveName
						+ "']/parent::tr/td[1]/a[text()='End']");
				assertTrue(selenium
						.getConfirmation()
						.matches(
								"^Are you sure you "
										+ "want to end this event[\\s\\S]\n\nPress OK to end the "
										+ "event\\. Press Cancel if you do NOT want to end the event\\.$"));
				
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
				assertEquals("", strFuncResult);
				Thread.sleep(30000);
				String strElementID = "//div[@id='mainContainer']/table"
									+ "/tbody/tr/td[text()='"
									+ strEditEveName
									+ "']"
									+ "/parent::tr/td/a[contains(text(),'View')]";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				
				
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
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
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
				
				strInfo="Event ended.";
				strFuncResult = objEventNotification.ackWebNotification(
						selenium, "End of Update 1: "+strEditEveName, strInfo,
						strStartDate);
				try {
					assertTrue(strFuncResult.equals(""));
					intResCnt++;
					Thread.sleep(70000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				
				strSubjNameEnd = "End of Update 1: " + strEditEveName;
				strMsgBodyEmailEnd = "Event Notice for "
						+ strUsrFulName
						+ ": "
						+ "\n"
						+ strEditEveName
						+ " ended at "
						+ strStartDateEnd+ "\nFrom: "+strAdminFullName+""
						+ "\n"
						+ "Region: "
						+ strRegn
						+ "\n\nPlease do not reply"
						+ " to this email message. You must log into EMResource to take"
						+ " any action that may be required."+"\n"+propEnvDetails.getProperty("MailURL");

				Writer output = null;
				String text = strMsgBodyEmailEnd;
				File file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Input.txt");
				output = new BufferedWriter(new FileWriter(file));
				output.write(text);
				output.close();

				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
				
				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjNameEnd);
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
						output = null;
						text = strMsg;
						file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();
						
						
						if (strMsg.equals(strMsgBodyEmailEnd)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly");
						} else {
							log4j.info("Email body is NOT displayed correctly");
							strFuncResult="Email body is NOT displayed correctly";
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjNameEnd);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
						
						if (strMsg.equals(strMsgBodyEmailEnd)) {
							intEMailRes++;
							log4j.info("Email body is displayed correctly");
						} else {
							log4j.info("Email body is NOT displayed correctly");
							strFuncResult="Email body is NOT displayed correctly";
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

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjNameEnd);

					try {
						assertTrue(strFuncResult.equals("The mail with subject " + strSubjNameEnd
						+ " is NOT present in the inbox"));
						intPagerRes++;
						
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes == 6 && intPagerRes == 3&&intResCnt==3) {
						gstrResult="PASS";
					}
					
					selenium.close();
					selenium.selectWindow("");
					selenium.selectWindow("");
					
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
		.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118372";
			gstrTO = "Verify that user can edit the event notification preferences.";
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
	
	
}

	
