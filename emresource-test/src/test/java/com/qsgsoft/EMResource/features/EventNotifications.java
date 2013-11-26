package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.EventNotification;
import com.qsgsoft.EMResource.shared.EventSetup;
import com.qsgsoft.EMResource.shared.General;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/********************************************************************************
' Description: The class Event Notification contains the test case to create an 
         event and check email, web and pager notifications are received by user.
' Functions: testBQS68438
' Date: 19-Apr-2012
' Author:QSG
'-------------------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************************/

public class EventNotifications {

	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.EventNotifications");
	static {
		BasicConfigurator.configure();
	}
	String gstrTCID, gstrTO, gstrResult, gstrReason;
	Selenium selenium;
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
	' Description: Verify that users can be selected to receive E-mail, Pager and Web notifications for an event template in 'Event Notification Preferences for "event template name"' screen
	' Precondition: 1. User A is created providing primary e-mail, e-mail and pager addresses.
				2. User A has the following rights;
				a. 'Maintain Events' right
				b. 'Maintain Event Templates' right
				c. 'User - Setup User Accounts'

				2. Event template ET is created providing mandatory data and the check boxes E-mail, Pager and Web for user A are deselected. 
	' Arguments:
	' Returns:
	' Date: 19-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'*************************************************************
*/
	
	@Test
	public void testBQS118229()throws Exception{
		try{
			gstrTCID = "118229";			
			gstrTO = "Verify that users can be selected to receive E-mail, " +
					"Pager and Web notifications for an event template in " +
					"'Event Notification Preferences for 'event template name' screen.";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");
			
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			ReadData objReadData = new ReadData (); 
			EventSetup objEve=new EventSetup();
			General objMail=new General();
			EventNotification objNotif=new EventNotification();
			CreateUsers objUser=new CreateUsers();
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
		
			String strFILE_PATH=pathProps.getProperty("TestData_path");
			
			Login objLogin = new Login();// object of class Login
			int intResCnt=0;
			//read user details
			String strLoginUserName = "AutoUsr"+System.currentTimeMillis();
			String strLoginPassword ="abc123";
			String strRegion=objReadData.readData("Login", 3, 4);
			
			String strUsrFullName="Full"+strLoginUserName;
			String strEveTemp="AutoET_"+strTimeText;
			String strEveName="AutoEve_"+strTimeText;
			String strInfo="This is an automation event";
			
			String strStartDate="";
								
			String strFndStMnth="";
			String strFndStYear="";
			String strFndStDay="";
			String strFndStHr="";
			String strFndStMinu="";
					
			String strTempDef=objReadData.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objReadData.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objReadData.readInfoExcel("Event Temp Data", 2, 5, strFILE_PATH);
			String strIconSrc=objReadData.readInfoExcel("Event Temp Data", 2, 6, strFILE_PATH);
			
			//Web mail user details
			String strLoginName = objReadData.readData("WebMailUser", 2, 1);
			String strPassword =objReadData.readData("WebMailUser", 2, 2);
			
			String strFrom="notification@emsystems.com";
			String strTo=strLoginName;
			String strMsgBody1="";
			String strMsgBody2="";
			String strSubjName="";
			
			int intEMailRes=0;
			int intPagerRes=0;
			
			String strUserName = objReadData.readData("Login", 3, 1);
			String strUsrPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);
			String strEMail=objReadData.readData("WebMailUser", 2, 1);
			
			String strResTypeArr[]={};
			String strStatTypeArr[]={};
			
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			String strFailMsg = objLogin.login(selenium, strUserName,
					strUsrPassword);				
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//nav to default region
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.navUserListPge(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
				
				
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.fillUsrMandatoryFlds(selenium, strLoginUserName, strLoginPassword, strLoginPassword, strUsrFullName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser
						.nonMandatoryUsrProfileFlds(selenium, "", "",
								"", "", "", strEMail, strEMail,
								strEMail, "");

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
								
				strFailMsg = objUser.savVrfyUser(selenium, strLoginUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//navigate to Event Template
				strFailMsg=objEve.createEventTemplate(selenium);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				//fill the required fields in Create Event Template and save
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(selenium, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIconSrc,"","","","",true,strResTypeArr,strStatTypeArr,true,false,false);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertTrue(strFailMsg.equals(""));
				//fill the required fields in Create Event Template and save
				strFailMsg=objNotif.selectEventNofifForUser(selenium, strUsrFullName, strEveTemp, false, false, false);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
	
			try {
				assertTrue(strFailMsg.equals(""));				
				strFailMsg=objLogin.logout(selenium);					
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
			/*
			 * Step 2: Login as user A, navigate to Event >> Event Setup, click on 'Notifications' link. 
			 * <-> 'Event Notification Preferences for < event template name >' screen is displayed. 
			 * <-> E-mail, Pager and Web check boxes are displayed for user A.
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
				strFailMsg= "'Event Template List' screen is NOT displayed.";
				assertTrue(objEve.navToEventSetup(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			
			/*
			 * Step 3: 	Select E-mail, Pager check boxes and click on 'Save'. <-> 'Event Template List' screen is displayed
			 */
			
			try {
				assertTrue(strFailMsg.equals(""));			
				strFailMsg=objNotif.selectEventNofifForUser(selenium, strUsrFullName, strEveTemp, true, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}			
			
			/*
			 * Step 4: 	Navigate to Event >> Event Management, click on 'Create New Event' button. <->	'Select Event Template' screen is displayed. 
			 */
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * Step 5: 	Create event EVE selecting ET and providing mandatory data. <-> Event EVE created is listed on 'Event List' screen. 
			 * <-> User A receives e-mail, pager and web notifications. 
			 */
			
			try {
				assertTrue(strFailMsg.equals(""));
								
				strFailMsg=objEve.createEventMandFlds(selenium, strEveTemp, strEveName, strInfo, false);
				
				//get Start Date
				 strFndStMnth=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartMnt"));
				 strFndStDay=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartDay"));
				 strFndStYear=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartYear"));
				
				//get Start Time
				 strFndStHr=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartHour"));
				 strFndStMinu=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartMinut"));
									 
				// click on save
					selenium.click(propElementDetails
							.getProperty("Event.CreateEve.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
										+ strEveName + "']"));
						log4j
								.info("Event '"
										+ strEveName
										+ "' is listed on 'Event Management' screen.");
						intResCnt++;
					} catch (AssertionError ae) {
						log4j
								.info("Event '"
										+ strEveName
										+ "' is NOT listed on 'Event Management' screen.");
						gstrReason = "Event '"
								+ strEveName
								+ "' is NOT listed on 'Event Management' screen.";
					}
				
			
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strStartDate=dts.converDateFormat(strFndStYear+strFndStMnth+strFndStDay,"yyyyMMMdd","M/d/yyyy");
				strStartDate=strStartDate+" "+strFndStHr+":"+strFndStMinu;
				
				strFailMsg=objNotif.ackWebNotification(selenium, strEveName, strInfo, strStartDate);
				
				try {
					assertTrue(strFailMsg.equals(""));
					intResCnt++;
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
				strSubjName = strEveName;
				strMsgBody1 = "Event Notice for " + strUsrFullName + ": \n"
						+ strInfo + "\nFrom: " + strUsrFullName + "\nRegion: "
						+ strRegion
						+ "\n\nPlease do not reply to this email message"
						+ ". You must log into EMResource to take "
						+ "any action that may be required." + "\n"
						+ propEnvDetails.getProperty("MailURL");
				strMsgBody2 = strInfo + "\nFrom: " + strUsrFullName
						+ "\nRegion: " + strRegion;

				selenium.selectWindow("");
				strFailMsg=objMail.loginAndnavToInboxInWebMail(selenium, strLoginName, strPassword);
				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg=objMail.verifyEmail(selenium, strFrom, strTo, strSubjName);
					
					try {
						assertTrue(strFailMsg.equals(""));
						String strMsg= selenium.getText("css=div.fixed.leftAlign");
						if(strMsg.equals(strMsgBody1)){
							intEMailRes++;
						}else if(strMsg.equals(strMsgBody2)){
							intPagerRes++;
						}
						
					} catch (AssertionError Ae) {
						gstrReason = gstrReason+" "+strFailMsg;
					}
					
					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					//click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");
					
					strFailMsg=objMail.verifyEmail(selenium, strFrom, strTo, strSubjName);
					
					try {
						assertTrue(strFailMsg.equals(""));
						String strMsg= selenium.getText("css=div.fixed.leftAlign");
						if(strMsg.equals(strMsgBody1)){
							intEMailRes++;
						}else if(strMsg.equals(strMsgBody2)){
							intPagerRes++;
						}
						
					} catch (AssertionError Ae) {
						gstrReason = gstrReason+" "+strFailMsg;
					}
					
					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					//click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");
					
					strFailMsg=objMail.verifyEmail(selenium, strFrom, strTo, strSubjName);
					
					try {
						assertTrue(strFailMsg.equals(""));
						String strMsg= selenium.getText("css=div.fixed.leftAlign");
						if(strMsg.equals(strMsgBody1)){
							intEMailRes++;
						}else if(strMsg.equals(strMsgBody2)){
							intPagerRes++;
						}
						
					} catch (AssertionError Ae) {
						gstrReason = gstrReason+" "+strFailMsg;
					}
					//check Email, pager notification
					if(intEMailRes==2&&intPagerRes==1){
						intResCnt++;
					}
					selenium.selectWindow("");	
					selenium.selectFrame("Data");
				
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
				//verify result
				if(intResCnt==3)
					gstrResult="PASS";
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "118229";
			gstrTO = "Verify that users can be selected to receive E-mail," +
					" Pager and Web notifications for an event template in " +
					"'Event Notification Preferences for 'event template name' screen.";
			gstrResult = "FAIL";
			excReason = null;
	
			log4j.info(e);
			log4j.info("========== Test Case '"+gstrTCID+"' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();	
		
		}			
	}

}
