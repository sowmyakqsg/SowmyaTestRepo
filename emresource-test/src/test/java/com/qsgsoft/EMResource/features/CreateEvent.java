package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
'*****************************************************************************
' Description : The class CreateEvent contains the test cases to create events
' Date        : 17-Apr-2012
' Author      :QSG
'------------------------------------------------------------------------------
' Modified Date                                                 Modified By
' <Date>                           	                            <Name>
'******************************************************************************
*/

public class CreateEvent {
	
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.CreateEvent");
	static{
		BasicConfigurator.configure();
	}
	
	String gstrTCID, gstrTO, gstrResult, gstrReason;	
	Selenium selenium,seleniumPrecondition;	
	double gdbTimeTaken;
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	public static Date gdtStartDate;
	public Properties propElementDetails;
	public static String gstrBrowserName;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;
	public Properties propEnvDetails,propAutoItDetails;
	public static Properties browserProps = new Properties();
	
	private String browser="";
	
	private String json;
	public static long sysDateTime;	
	public static long gsysDateTime;
	public static String sessionId_FF36,sessionId_FF20,sessionId_FF3,sessionId_FF35,sessionId_IE6,sessionId_IE7,
    sessionId_IE8,session_SF3,session_SF4,session_op9,session_op10,session_GC,StrSessionId,StrSessionId1,StrSessionId2;
	public static String gstrTimeOut="";
	
	@Before
	public void setUp() throws Exception {
		
		
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
					
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");		
		
		browser=propEnvDetails.getProperty("Browser");
		gstrBrowserName=propEnvDetails.getProperty("Browser").substring(1)+" "+propEnvDetails.getProperty("BrowserVersion");
		//create an object to refer to properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		if (propEnvDetails.getProperty("Server").equals("saucelabs.com")) {
		         
	        
			selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.json, propEnvDetails.getProperty("urlEU"));
			
	       
		} else {
			selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.browser, propEnvDetails.getProperty("urlEU"));			
		
		}		
			
		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		
		selenium.start();
		selenium.windowMaximize();
		selenium.setTimeout("");
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		seleniumPrecondition.setTimeout("");

		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();		
		
	}

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
		selenium.stop();
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

		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);
	
	}
	
	/**
	'*****************************************************************************
	' Description: Create an event selecting to start immediately and to end after
	               a certain number of hours and verify that the event starts 
	               immediately and ends after the specified number of hours.
	' Arguments:
	' Returns:
	' Date: 17-Apr-2012
	' Author:QSG
	'------------------------------------------------------------------------------
	' Modified Date                                                   Modified By
	' <Date>                                                           <Name>
	'*******************************************************************************
*/
	
	@Test
	public void testBQS118078()throws Exception{
		try{
			gstrTCID = "118078";			
			gstrTO = "Create an event selecting to start immediately and to end after a certain number of hours " +
					"and verify that the event starts immediately and ends after the specified number of hours.";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			ReadData objReadData = new ReadData (); 
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");			
			String strTestData[]=new String[10];
			
			String strAction1="Edit";
			String strAction2="View";
			String strIcon=objReadData.readInfoExcel("Event Temp Data", 8, 6, strFILE_PATH);
			String strStatus="Ongoing";
			String strStartDate="";
			String strEndDate="";
			String strDrill="No";
			String strInfo="This is an automation event";
			
			String strHourDur="25";
			String strFutureTime="";
			
			String strFndStMnth="";
			String strFndStYear="";
			String strFndStDay="";
			String strFndStHr="";
			String strFndStMinu="";
			
			String strFndEdMnth="";
			String strFndEdYear="";
			String strFndEdDay="";
			String strFndEdHr="";
			String strFndEdMinu="";
					
			Login objLogin = new Login();// object of class Login
			EventSetup objEve=new EventSetup();
			General objMail=new General();
			EventList objList=new EventList();
			MobileView objMob=new MobileView();
			CreateUsers objUser=new CreateUsers();
			int intResCnt=0;
											
			EventNotification objNotif=new EventNotification();
			
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
		
			String strEMail=objReadData.readData("WebMailUser", 2, 1);
			
			String strLoginName = objReadData.readData("WebMailUser", 2, 1);
			String strPassword =objReadData.readData("WebMailUser", 2, 2);
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strUsrPassword="abc123";
			String strFullUserName="Full"+strUserName;
			
			String strFrom="notification@emsystems.com";
			String strTo=strLoginName;
			String strMsgBody1="";
			String strMsgBody2="";
			String strSubjName="";
		//	String strUsrFullName="auto user";
			
			int intEMailRes=0;
			int intPagerRes=0;
			
			String strEveTemp="AutoET_"+strTimeText;
			String strEveName="AutoEve_"+strTimeText;
			
					
			// search user data
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String[] strHeader={"Action","Icon","Status","Start","End","Title","Drill","Template","Information"};
			String strFailMsg="";
		
			// login details
			String strAdmUserName = objReadData.readData("Login", 3, 1);
			String strAdmPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);		
			
			String strTempDef=objReadData.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objReadData.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objReadData.readInfoExcel("Event Temp Data", 2, 5, strFILE_PATH);
			String strIconSrc=objReadData.readInfoExcel("Event Temp Data", 2, 6, strFILE_PATH);
			
			String strResTypeArr[]={};
			String strStatTypeArr[]={};
			
		/*STEP:' Precondition: 1. User A is created providing primary e-mail, e-mail and pager addresses.
				2. User A has the following rights;
						a. 'Maintain Events' right
						b. 'Maintain Event Templates' right
						c. 'User - Setup User Accounts' right					
				2. Event template ET is created providing mandatory data and selecting the check boxes E-mail, Pager and Web for user A. 
		*/
			strFailMsg = objLogin.login(seleniumPrecondition,strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
				strFailMsg = objUser.fillUsrMandatoryFlds(seleniumPrecondition, strUserName, strUsrPassword, strUsrPassword, strFullUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser
						.nonMandatoryUsrProfileFlds(seleniumPrecondition, "", "",
								"", "", "", strEMail, strEMail,
								strEMail, "");

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objUser.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			strFailMsg = objLogin.login(selenium,strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn);
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
				strFailMsg=objNotif.selectEventNofifForUser(selenium, strFullUserName, strEveTemp, true, true, true);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			
			/*
			 * Step 2: 	Login as user A, navigate to Event >> Event Management. <->	'Event Management' screen is displayed.
			 */
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.newUsrLogin(selenium, strUserName, strUsrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * Step 3: Create a new event selecting event template ET. 	<->	'Create New Event' screen is displayed. 
			 * Step 4: 	Provide mandatory data, select 'Immediately' for 'Event start', provide N number of hours for 'Event End' and click on 'Save'. 	<->	Event banner is displayed.
			 * <-> User A receives email, pager and web notifications for event start.

				Web Notification displays the following:
				
				< Event Name > MM/DD/YYYY HH:MM
				< Event Description >
				'Acknowledge All notifications' button 
				
				<-> E-mail and pri-mail notifications displays the following:

				Subject: < Event Name >
				
				Mail body has the following:
				Event Notice for < User full name >
				< description >
				Region: < Region >
				Information Message 'Please do not reply to this email message. You must log into EMResource to take any action that may be required.'
				
				<->  Pager notification display the following:

				Subject: < Event Name >
				
				Body of the pager has the following:
				< description >
				Region: < Region > 
				
				<-> Event is listed in the 'Event Management' screen.

				'Title' and 'Description' are displayed appropriately as provided while event is created under 'Title' and 'Description' columns.
				
				Event start Date and time are displayed appropriately under the column 'Start'.
				
				Event end Date and time are displayed appropriately under the column 'End'.
				
				'Ongoing' is displayed under the 'Status' column.
				
				'Edit' and 'End' links are displayed under the 'Action' column for the event created. 
			 */
			try {
				assertTrue(strFailMsg.equals(""));
								
				strFailMsg=objEve.createEventMandFlds(selenium, strEveTemp, strEveName, strInfo, false);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				
				//get Start Date
				 strFndStMnth=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartMnt"));
				 strFndStDay=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartDay"));
				 strFndStYear=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartYear"));
				
				//get Start Time
				 strFndStHr=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartHour"));
				 strFndStMinu=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartMinut"));
												 
				 String strEdDate=strFndStMnth+"/"+strFndStDay+"/"+strFndStYear+" "+strFndStHr+":"+strFndStMinu;
				 //End Date
				 strFutureTime=dts.addTimetoExistingHour(strEdDate,Integer.parseInt(strHourDur) , "MMM/dd/yyyy HH:mm");
				 
				 String strFutDateArr[]=strFutureTime.split(" ");
				 String strEdDateArr[]=strFutDateArr[0].split("/");
				 strFndEdMnth=strEdDateArr[0];
				 strFndEdDay=strEdDateArr[1];
				 strFndEdYear=strEdDateArr[2];
				 
				 String strEdTime[]=strFutDateArr[1].split(":");
				 strFndEdHr=strEdTime[0];
				 strFndEdMinu=strEdTime[1];
				 
				strFailMsg=objEve.createEvent_FillOtherFields(selenium, strEveName, true, false, false, false, true, "Hours",strHourDur, "", "", false, "", "", "", "", "", "", "", "");
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				
				try{
					
					assertTrue(selenium.isElementPresent("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"+strEveName+"']"));
					log4j.info("Event banner is displayed.");
					
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
					strMsgBody1 = "Event Notice for " + strFullUserName
							+ ": \n " + strInfo + "\n From: " + strFullUserName
							+ "\n Region: " + strRegn
							+ "\n \n Please do not reply to this email message."
							+ " You must log into EMResource to take "
							+ "any action that may be required." + "\n "
							+ propEnvDetails.getProperty("MailURL");
				
					
					strMsgBody2 = strInfo + "\n From: " + strFullUserName
							+ "\n Region: " + strRegn;

					selenium.selectWindow("");
					strFailMsg = objMail.loginAndnavToInboxInWebMail(seleniumPrecondition,
							strLoginName, strPassword);
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objMail.verifyEmail(seleniumPrecondition, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg = seleniumPrecondition
									.getText("css=div.fixed.leftAlign");
					
							
							if (strMsg.equals(strMsgBody1)) {
								intEMailRes++;
								
							} else if (strMsg.equals(strMsgBody2)) {
								intPagerRes++;
							}else{
								strFailMsg="Message body is NOT displayed";
								gstrReason = gstrReason+" "+strFailMsg;
								log4j.info("Message body is NOT displayed");
							}

						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}

						seleniumPrecondition.selectFrame("relative=up");
						seleniumPrecondition.selectFrame("horde_main");

						seleniumPrecondition.click("link=Back to Inbox");
						seleniumPrecondition.waitForPageToLoad("90000");
						
						int intCnt=0;
						do{
							try {

								assertTrue(seleniumPrecondition.isElementPresent("//a[text()='"
										+ strSubjName + "']"));
								break;
							}catch(AssertionError Ae){
								Thread.sleep(1000);
								intCnt++;
							
							} catch (Exception Ae) {
								Thread.sleep(1000);
								intCnt++;
							}
						}while(intCnt<20);
						

						strFailMsg = objMail.verifyEmail(seleniumPrecondition, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg = seleniumPrecondition
									.getText("css=div.fixed.leftAlign");
						
							if (strMsg.equals(strMsgBody1)) {
								intEMailRes++;
							} else if (strMsg.equals(strMsgBody2)) {
								intPagerRes++;
							}else{
								strFailMsg="Message body is NOT displayed";
								gstrReason = gstrReason+" "+strFailMsg;
								log4j.info("Message body is NOT displayed");
							}

						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}

						seleniumPrecondition.selectFrame("relative=up");
						seleniumPrecondition.selectFrame("horde_main");
						
						seleniumPrecondition.click("link=Back to Inbox");
						seleniumPrecondition.waitForPageToLoad("90000");
						
						intCnt=0;
						do{
							try {

								assertTrue(seleniumPrecondition.isElementPresent("//a[text()='"
										+ strSubjName + "']"));
								break;
							}catch(AssertionError Ae){
								Thread.sleep(1000);
								intCnt++;
							
							} catch (Exception Ae) {
								Thread.sleep(1000);
								intCnt++;
							}
						}while(intCnt<20);
						
						
						strFailMsg=objMail.verifyEmail(seleniumPrecondition, strFrom, strTo, strSubjName);
						
						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg= seleniumPrecondition.getText("css=div.fixed.leftAlign");
							if(strMsg.equals(strMsgBody1)){
								intEMailRes++;
							}else if(strMsg.equals(strMsgBody2)){
								intPagerRes++;
							}else{
								log4j.info("Message body is NOT displayed");
								strFailMsg="Message body is NOT displayed";
								gstrReason = gstrReason+" "+strFailMsg;
							}
							
						} catch (AssertionError Ae) {
							gstrReason = gstrReason+" "+strFailMsg;
						}
						if(intEMailRes==2&&intPagerRes==1){
							intResCnt++;
						}
						//objMail.deleteWebMailInbox(selenium);
						
						selenium.selectWindow("");	
						selenium.selectFrame("Data");
						strStartDate=dts.converDateFormat(strFndStYear+strFndStMnth+strFndStDay,"yyyyMMMdd","yyyy-MM-dd");
						strStartDate=strStartDate+" "+strFndStHr+":"+strFndStMinu;
						
						strEndDate=dts.converDateFormat(strFndEdYear+strFndEdMnth+strFndEdDay,"yyyyMMMdd","yyyy-MM-dd");
						strEndDate=strEndDate+" "+strFndEdHr+":"+strFndEdMinu;
						
						String[] strData={strAction1,strIcon,strStatus,strStartDate,strEndDate,strEveName,strDrill,strEveTemp,strInfo};
						
						for(int intRec=0;intRec<strHeader.length;intRec++){
							strFailMsg=objList.checkDataInEventListTable(selenium, strHeader[intRec], strEveName, strData[intRec], String.valueOf(intRec+1));
							try{
								assertTrue(strFailMsg.equals(""));						
							}catch(AssertionError ae){
								gstrReason = gstrReason+" "+strFailMsg;
							}
						}
							
						/*
						 * Step 5:Navigate to Event >> Event List <->	Event is listed in the 'Event List' screen.
						

							<-> 'Title' and 'Description' are displayed appropriately as provided while event is created under 'Title' and 'Description' columns.
	
							<-> Event start Date and time are displayed appropriately under the column 'Start'.
	
							<-> Event end Date and time are displayed appropriately under the column 'End'.
	
							<-> 'Ongoing' is displayed under the 'Status' column.
	
							<-> 'View History' link is displayed under the 'Action' column for the event created. 
						*/
						try {
							assertTrue(strFailMsg.equals(""));							
							assertTrue(objList.navToEventList(selenium));	
														
							String[] strEveData={strAction2,strIcon,strStatus,strStartDate,strEndDate,strEveName,strDrill,strEveTemp,strInfo};
							
							for(int intRec=0;intRec<strHeader.length;intRec++){
								strFailMsg=objList.checkDataInEventListTable(selenium, strHeader[intRec], strEveName, strEveData[intRec], String.valueOf(intRec+1));
								try{
									assertTrue(strFailMsg.equals(""));						
								}catch(AssertionError ae){
									gstrReason = gstrReason+" "+strFailMsg;
								}
							}
							
						} catch (AssertionError Ae) {
							gstrReason =gstrReason+ " Event List screen is NOT displayed";
						}	
					
						/*
						 * Step 6: 	Click on 'Mobile View' link on footer of the application. <->	'Main Menu' screen is displayed.
							Step 7: Navigate to Events>>< Event Name >>>Times screen  <->	'Event Times' screen is displayed with appropriate data for 'Event Started' and 'Scheduled End' controls. 
						 */
						strStartDate=dts.converDateFormat(strFndStYear+strFndStMnth+strFndStDay,"yyyyMMMdd","dd-MMM-yyyy");
						strStartDate=strStartDate+" "+strFndStHr+":"+strFndStMinu;
						
						strEndDate=dts.converDateFormat(strFndEdYear+strFndEdMnth+strFndEdDay,"yyyyMMMdd","dd-MMM-yyyy");
						strEndDate=strEndDate+" "+strFndEdHr+":"+strFndEdMinu;
						
						strFailMsg=objMob.checkInEventTimes(selenium, strEveName, strStartDate, strEndDate);
						try {
							assertTrue(strFailMsg.equals(""));	
							intResCnt++;
						}catch(AssertionError ae){
							gstrReason = gstrReason+" "+strFailMsg;
						}
						
						if(intResCnt==3&&gstrReason.compareTo("")==0){
							gstrResult="PASS";
							//Write result data
							strTestData[0]= propEnvDetails.getProperty("Build");
							strTestData[1]=gstrTCID;
							strTestData[2]=strUserName+"/"+strUsrPassword;
							strTestData[3]=strEveTemp;
							strTestData[4]=strEveName;
							strTestData[5]="Wait for end Date and check in Mobile, Start Date: "+strStartDate+", End Date: "+strEndDate+"Event Duration: "+strHourDur;
							
							String strWriteFilePath=pathProps.getProperty("WriteResultPath");
							objOFC.writeResultData(strTestData, strWriteFilePath, "Events");
						}
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}			
					
					
				}catch(AssertionError ae){
					log4j.info("Event banner is NOT displayed.");
					gstrReason="Event banner is NOT displayed.";
				}
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "118078";
			gstrTO = "Create an event selecting to start immediately and to end after a certain number of hours and verify that the event starts immediately and ends after the specified number of hours.";
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
	
	/**************************************************************************************************
	' Description: Create an event selecting to start immediately and to end at a certain date and time 
	               and verify that the event starts immediately and ends at the specified date and time.
	' Arguments  :
	' Returns    :
	' Date       : 17-Apr-2012
	' Author     :QSG
	'--------------------------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'***************************************************************************************************/

	
	@Test
	public void testBQS118079()throws Exception{
		
		CreateUsers objUser=new CreateUsers();
		Login objLogin = new Login();// object of class Login
		EventSetup objEve=new EventSetup();
		General objMail=new General();
		EventList objList=new EventList();
		MobileView objMob=new MobileView();
		try{
			gstrTCID = "118079";			
			gstrTO = "Create an event selecting to start immediately and " +
					"to end at a certain date and time and verify that " +
					"the event starts immediately and ends at the specified date and time.";
			gstrReason = "";
			gstrResult = "FAIL";		
		
			
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			ReadData objReadData = new ReadData (); 
			EventNotification objNotif=new EventNotification();
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strFailMsg="";
			String strTestData[]=new String[10];
			String strAction1="Edit";
			String strAction2="View";
			String strIcon=objReadData.readInfoExcel("Event Temp Data", 8, 6, strFILE_PATH);
			String strStatus="Ongoing";
			String strStartDate="";
			String strEndDate="";
			String strDrill="No";
			String strInfo="This is an automation event";
			
			String strEMail=objReadData.readData("WebMailUser", 2, 1);
			String strFndStMnth="";
			String strFndStYear="";
			String strFndStDay="";
			String strFndStHr="";
			String strFndStMinu="";
			
			String strFndEdMnth="";
			String strFndEdYear="";
			String strFndEdDay="";
			String strFndEdHr="";
			String strFndEdMinu="";
			
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			
			int intResCnt=0;
											
			String strLoginName = objReadData.readData("WebMailUser", 2, 1);
			String strPassword =objReadData.readData("WebMailUser", 2, 2);
			
			String strFrom="notification@emsystems.com";
			String strTo=strLoginName;
			String strMsgBody1="";
			String strMsgBody2="";
			String strSubjName="";
			//String strUsrFullName="auto user";
			
			int intEMailRes=0;
			int intPagerRes=0;
			
			String strEveTemp="AutoET_"+strTimeText;
			String strEveName="AutoEve_"+strTimeText;
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strUsrPassword="abc123";
			String strFullUserName="Full"+strUserName;
					
			String[] strHeader={"Action","Icon","Status","Start","End","Title","Drill","Template","Information"};
			
			// login details
			String strAdmUserName = objReadData.readData("Login", 3, 1);
			String strAdmPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);		
			
			String strTempDef=objReadData.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objReadData.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objReadData.readInfoExcel("FirefoxTestData", 2, 2, strFILE_PATH);
			String strIconSrc=objReadData.readInfoExcel("FirefoxTestData", 2, 3, strFILE_PATH);
			
			String strResTypeArr[]={};
			String strStatTypeArr[]={};
			
			/* 	Preconditions:1. User A is created providing primary e-mail, e-mail and pager addresses.
                              2. User A has the following rights;
								a. 'Maintain Events' right
								b. 'Maintain Event Templates' right
								c. 'User - Setup User Accounts' right
             2. Event template ET is created providing mandatory data and selecting the check boxes E-mail, Pager and Web for user A. 
			 * 
			 */
			
			log4j.info("~~~~~PRE-CONDITION - "+gstrTCID+" EXECUTION STARTS~~~~~");
			
			strFailMsg = objLogin.login(seleniumPrecondition,strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
				strFailMsg = objUser.fillUsrMandatoryFlds(seleniumPrecondition, strUserName, strUsrPassword, strUsrPassword, strFullUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser
						.nonMandatoryUsrProfileFlds(seleniumPrecondition, "", "",
								"", "", "", strEMail, strEMail,
								strEMail, "");

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objUser.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(seleniumPrecondition));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//navigate to Event Template
				strFailMsg=objEve.createEventTemplate(seleniumPrecondition);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// fill the required fields in Create Event Template and save
				strFailMsg = objEve.fillMandfieldsAndSaveEveTemp(seleniumPrecondition,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strResTypeArr,
						strStatTypeArr, true, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				//fill the required fields in Create Event Template and save
				strFailMsg=objNotif.selectEventNofifForUser(seleniumPrecondition, strFullUserName, strEveTemp, true, true, true);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
						
			/*
			 * Step 2: 	Login as user A, navigate to Event >> Event Management. <->	'Event Management' screen is displayed.
			 */
			log4j.info("~~~~~PRE-CONDITION - "+gstrTCID+" EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.newUsrLogin(selenium, strUserName, strUsrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * Step 3: Create a new event selecting event template ET. 	<->	'Create New Event' screen is displayed. 
			 * Step 4: 	Provide mandatory data, select 'Immediately' for 'Event start', provide certain date and time for 'Event end' and click on 'Save'. 	<->	Event banner is displayed.
			 * <-> User A receives email, pager and web notifications for event start.

				Web Notification displays the following:
				
				< Event Name > MM/DD/YYYY HH:MM
				< Event Description >
				'Acknowledge All notifications' button 
				
				<-> E-mail and pri-mail notifications displays the following:

				Subject: < Event Name >
				
				Mail body has the following:
				Event Notice for < User full name >
				< description >
				Region: < Region >
				Information Message 'Please do not reply to this email message. You must log into EMResource to take any action that may be required.'
				
				<->  Pager notification display the following:

				Subject: < Event Name >
				
				Body of the pager has the following:
				< description >
				Region: < Region > 
				
				<-> Event is listed in the 'Event Management' screen.

				'Title' and 'Description' are displayed appropriately as provided while event is created under 'Title' and 'Description' columns.
				
				Event start Date and time are displayed appropriately under the column 'Start'.
				
				Event end Date and time are displayed appropriately under the column 'End'.
				
				'Ongoing' is displayed under the 'Status' column.
				
				'Edit' and 'End' links are displayed under the 'Action' column for the event created. 
			 */
			
			try {
				assertTrue(strFailMsg.equals(""));
								
				strFailMsg=objEve.createEventMandFlds(selenium, strEveTemp, strEveName, strInfo, false);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				
				//get Start Date
				 strFndStMnth=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartMnt"));
				 strFndStDay=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartDay"));
				 strFndStYear=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartYear"));
				
				//get Start Time
				 strFndStHr=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartHour"));
				 strFndStMinu=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartMinut"));
				
				 String strStatTimeArr[]=selenium.getText("css=#statusTime").split(" ");
				 String strCurYear=dts.getCurrentDate("yyyy");
				 String strStatTime=strStatTimeArr[1]+"/"+strStatTimeArr[0]+"/"+strCurYear+" "+strStatTimeArr[2];
				
				 //End Date
				 String strFutureDate=dts.AddDaytoExistingDate(strStatTime, 1, "MMM/d/yyyy HH:mm");
							
				 String strFutDateArr[]=strFutureDate.split(" ");
				 String strEdDate[]=strFutDateArr[0].split("/");
				 strFndEdMnth=strEdDate[0];
				 strFndEdDay=strEdDate[1];
				 strFndEdYear=strEdDate[2];
				 
				 String strEdTime[]=strFutDateArr[1].split(":");
				 strFndEdHr=strEdTime[0];
				 strFndEdMinu=strEdTime[1];
				 
				 strEndDate=strFndEdMnth+strFndEdDay+strFndEdYear+" "+strFndEdHr+":"+strFndEdMinu;
					
				strFailMsg=objEve.createEvent_FillOtherFields(selenium, strEveName, true, false, false, false, true, "Date", "", "", strFutureDate, false, "", "", "", "", "", "", "", "");
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				
				try{
					
					
					strStartDate=dts.converDateFormat(strFndStYear+strFndStMnth+strFndStDay,"yyyyMMMdd","M/d/yyyy");
					strStartDate=strStartDate+" "+strFndStHr+":"+strFndStMinu;
								
					strFailMsg=objNotif.ackWebNotification(selenium, strEveName, strInfo, strStartDate);

					try {
						assertTrue(strFailMsg.equals(""));

						strFailMsg = objEve.verfyEventInEventBanner(selenium,
								strEveName);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));
						intResCnt++;
						Thread.sleep(60000);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					strSubjName = strEveName;
					strMsgBody1 = "Event Notice for "
							+ strFullUserName
							+ ": \n "
							+ strInfo
							+ "\n From: "
							+ strFullUserName
							+ "\n Region: "
							+ strRegn
							+ "\n \n Please do not reply to this email message."
							+ " You must log into EMResource to take "
							+ "any action that may be required." + "\n "
							+ propEnvDetails.getProperty("MailURL");

					Writer output = null;
					String text = strMsgBody1;
					File file = new File(
							"C:\\Documents and Settings\\All Users\\Desktop\\InputMail.txt");
					output = new BufferedWriter(new FileWriter(file));
					output.write(text);
					output.close();

					strMsgBody2 = strInfo + "\n From: " + strFullUserName
							+ "\n Region: " + strRegn;
					
					
					
					output = null;
					text = strMsgBody2;
					file = new File("C:\\Documents and Settings\\All Users\\Desktop\\InputPager.txt");
					output = new BufferedWriter(new FileWriter(file));
					output.write(text);
					output.close();
					
					selenium.selectWindow("");
					strFailMsg=objMail.loginAndnavToInboxInWebMail(seleniumPrecondition, strLoginName, strPassword);
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg=objMail.verifyEmail(seleniumPrecondition, strFrom, strTo, strSubjName);
						
						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg= seleniumPrecondition.getText("css=div.fixed.leftAlign");
							
							output = null;
							text = strMsg;
							file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
							output = new BufferedWriter(new FileWriter(file));
							output.write(text);
							output.close();
							
							
							if(strMsg.equals(strMsgBody1)){
								intEMailRes++;
							}else if(strMsg.equals(strMsgBody2)){
								intPagerRes++;
							}
							
						} catch (AssertionError Ae) {
							gstrReason = gstrReason+" "+strFailMsg;
						}
						
						seleniumPrecondition.selectFrame("relative=up");
						seleniumPrecondition.selectFrame("horde_main");
						
						seleniumPrecondition.click("link=Back to Inbox");
						seleniumPrecondition.waitForPageToLoad("90000");
						
						int intCnt=0;
						do{
							try {

								assertTrue(seleniumPrecondition.isElementPresent("//a[text()='"
										+ strSubjName + "']"));
								break;
							}catch(AssertionError Ae){
								Thread.sleep(1000);
								intCnt++;
							
							} catch (Exception Ae) {
								Thread.sleep(1000);
								intCnt++;
							}
						}while(intCnt<20);
						
						strFailMsg=objMail.verifyEmail(seleniumPrecondition, strFrom, strTo, strSubjName);
						
						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg= seleniumPrecondition.getText("css=div.fixed.leftAlign");
							
							output = null;
							text = strMsg;
							file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Pager.txt");
							output = new BufferedWriter(new FileWriter(file));
							output.write(text);
							output.close();
							
							if(strMsg.equals(strMsgBody1)){
								intEMailRes++;
							}else if(strMsg.equals(strMsgBody2)){
								intPagerRes++;
							}
							
						} catch (AssertionError Ae) {
							gstrReason = gstrReason+" "+strFailMsg;
						}
						
						seleniumPrecondition.selectFrame("relative=up");
						seleniumPrecondition.selectFrame("horde_main");
						
						seleniumPrecondition.click("link=Back to Inbox");
						seleniumPrecondition.waitForPageToLoad("90000");
						
						
						intCnt=0;
						do{
							try {

								assertTrue(seleniumPrecondition.isElementPresent("//a[text()='"
										+ strSubjName + "']"));
								break;
							}catch(AssertionError Ae){
								Thread.sleep(1000);
								intCnt++;
							
							} catch (Exception Ae) {
								Thread.sleep(1000);
								intCnt++;
							}
						}while(intCnt<20);
							
						
						strFailMsg=objMail.verifyEmail(seleniumPrecondition, strFrom, strTo, strSubjName);
						
						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg= seleniumPrecondition.getText("css=div.fixed.leftAlign");
							
							output = null;
							text = strMsg;
							file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Mail2.txt");
							output = new BufferedWriter(new FileWriter(file));
							output.write(text);
							output.close();
							
							
							if(strMsg.equals(strMsgBody1)){
								intEMailRes++;
							}else if(strMsg.equals(strMsgBody2)){
								intPagerRes++;
							}
							seleniumPrecondition.close();
						} catch (AssertionError Ae) {
							gstrReason = gstrReason+" "+strFailMsg;
						}
						if(intEMailRes==2&&intPagerRes==1){
							intResCnt++;
						}
						//objMail.deleteWebMailInbox(selenium);
						
						selenium.selectWindow("");	
						selenium.selectFrame("Data");
						strStartDate=dts.converDateFormat(strFndStYear+strFndStMnth+strFndStDay,"yyyyMMMdd","yyyy-MM-dd");
						strStartDate=strStartDate+" "+strFndStHr+":"+strFndStMinu;
						
						strEndDate=dts.converDateFormat(strFndEdYear+strFndEdMnth+strFndEdDay,"yyyyMMMdd","yyyy-MM-dd");
						strEndDate=strEndDate+" "+strFndEdHr+":"+strFndEdMinu;
						
						String[] strData={strAction1,strIcon,strStatus,strStartDate,strEndDate,strEveName,strDrill,strEveTemp,strInfo};
						
						for(int intRec=0;intRec<strHeader.length;intRec++){
							strFailMsg=objList.checkDataInEventListTable(selenium, strHeader[intRec], strEveName, strData[intRec], String.valueOf(intRec+1));
							try{
								assertTrue(strFailMsg.equals(""));						
							}catch(AssertionError ae){
								gstrReason = gstrReason+" "+strFailMsg;
							}
						}
							
						/*
						 * Step 5:Navigate to Event >> Event List <->	Event is listed in the 'Event List' screen.
						

							<-> 'Title' and 'Description' are displayed appropriately as provided while event is created under 'Title' and 'Description' columns.
	
							<-> Event start Date and time are displayed appropriately under the column 'Start'.
	
							<-> Event end Date and time are displayed appropriately under the column 'End'.
	
							<-> 'Ongoing' is displayed under the 'Status' column.
	
							<-> 'View History' link is displayed under the 'Action' column for the event created. 
						*/
						
						try {
							assertTrue(strFailMsg.equals(""));							
							assertTrue(objList.navToEventList(selenium));	
														
							String[] strEveData={strAction2,strIcon,strStatus,strStartDate,strEndDate,strEveName,strDrill,strEveTemp,strInfo};
							
							for(int intRec=0;intRec<strHeader.length;intRec++){
								strFailMsg=objList.checkDataInEventListTable(selenium, strHeader[intRec], strEveName, strEveData[intRec], String.valueOf(intRec+1));
								try{
									assertTrue(strFailMsg.equals(""));						
								}catch(AssertionError ae){
									gstrReason = gstrReason+" "+strFailMsg;
								}
							}
							
						} catch (AssertionError Ae) {
							gstrReason = gstrReason+" Event List screen is NOT displayed";
						}	
					
						/*
						 * Step 6: 	Click on 'Mobile View' link on footer of the application. <->	'Main Menu' screen is displayed.
							Step 7: Navigate to Events>>< Event Name >>>Times screen  <->	'Event Times' screen is displayed with appropriate data for 'Event Started' and 'Scheduled End' controls. 
						 */
						
						strStartDate=dts.converDateFormat(strFndStYear+strFndStMnth+strFndStDay,"yyyyMMMdd","dd-MMM-yyyy");
						strStartDate=strStartDate+" "+strFndStHr+":"+strFndStMinu;
						
						strEndDate=dts.converDateFormat(strFndEdYear+strFndEdMnth+strFndEdDay,"yyyyMMMdd","dd-MMM-yyyy");
						strEndDate=strEndDate+" "+strFndEdHr+":"+strFndEdMinu;
						
						strFailMsg=objMob.checkInEventTimes(selenium, strEveName, strStartDate, strEndDate);
						try {
							assertTrue(strFailMsg.equals(""));	
							intResCnt++;
						}catch(AssertionError ae){
							gstrReason = gstrReason+" "+strFailMsg;
						}
						
						if(intResCnt==3&&gstrReason.compareTo("")==0){
							gstrResult="PASS";
							//Write result data
							strTestData[0]= propEnvDetails.getProperty("Build");
							strTestData[1]=gstrTCID;
							strTestData[2]=strUserName+"/"+strUsrPassword;
							strTestData[3]=strEveTemp;
							strTestData[4]=strEveName;
							strTestData[5]="Wait for end Date and check in Mobile, Start Date: "+strStartDate+", End Date: "+strEndDate;
							
							String strWriteFilePath=pathProps.getProperty("WriteResultPath");
							objOFC.writeResultData(strTestData, strWriteFilePath, "Events");
						}
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}			
					
					
				}catch(AssertionError ae){
					log4j.info("Event banner is NOT displayed.");
					gstrReason="Event banner is NOT displayed.";
				}
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "118079";
			gstrTO = "Create an event selecting to start immediately and " +
					"to end at a certain date and time and verify that" +
					" the event starts immediately and ends at the specified date and time.";
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

	/**
	'*************************************************************
	' Description: Create an event selecting to start immediately and to end never and verify that the event starts immediately and does not have an end date.
	' Precondition: 1. User A is created providing primary e-mail, e-mail and pager addresses.
					2. User A has the following rights;
					a. 'Maintain Events' right
					b. 'Maintain Event Templates' right
					c. 'User - Setup User Accounts' right					
					2. Event template ET is created providing mandatory data and selecting the check boxes E-mail, Pager and Web for user A. 
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
	public void testBQS118080()throws Exception{
		try{
			gstrTCID = "118080";			
			gstrTO = "Create an event selecting to start immediately and to" +
					" end never and verify that the event starts" +
					" immediately and does not have an end date.";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			ReadData objReadData = new ReadData (); 
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strTestData[]=new String[10];
			String strAction1="Edit";
			String strAction2="View";
			String strIcon=objReadData.readInfoExcel("Event Temp Data", 8, 6, strFILE_PATH);
			String strStatus="Ongoing";
			String strStartDate="";
			String strEndDate="Never";
			String strDrill="No";
			String strInfo="This is an automation event";
			
			String strFndStMnth="";
			String strFndStYear="";
			String strFndStDay="";
			String strFndStHr="";
			String strFndStMinu="";
							
			CreateUsers objUser=new CreateUsers();
			Login objLogin = new Login();// object of class Login
			EventSetup objEve=new EventSetup();
			General objMail=new General();
			EventList objList=new EventList();
			MobileView objMob=new MobileView();
			int intResCnt=0;
							
			String strEMail=objReadData.readData("WebMailUser", 2, 1);
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strUsrPassword="abc123";
			String strFullUserName="Full"+strUserName;
			
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			
			EventNotification objNotif=new EventNotification();
			
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
						
			String strLoginName = objReadData.readData("WebMailUser", 2, 1);
			String strPassword =objReadData.readData("WebMailUser", 2, 2);
			
			String strFrom="notification@emsystems.com";
			String strTo=strLoginName;
			String strMsgBody1="";
			String strMsgBody2="";
			String strSubjName="";
						
			int intEMailRes=0;
			int intPagerRes=0;
			
			String strEveTemp="AutoET_"+strTimeText;
			String strEveName="AutoEve_"+strTimeText;
			
			String strFailMsg="";
			String[] strHeader={"Action","Icon","Status","Start","End","Title","Drill","Template","Information"};
			
		
			// login details
			String strAdmUserName = objReadData.readData("Login", 3, 1);
			String strAdmPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);		
			
			String strTempDef=objReadData.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objReadData.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objReadData.readInfoExcel("FirefoxTestData", 2, 2, strFILE_PATH);
			String strIconSrc=objReadData.readInfoExcel("FirefoxTestData", 2, 3, strFILE_PATH);
			
			String strResTypeArr[]={};
			String strStatTypeArr[]={};
			strFailMsg = objLogin.login(seleniumPrecondition,strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
				strFailMsg = objUser.fillUsrMandatoryFlds(seleniumPrecondition, strUserName, strUsrPassword, strUsrPassword, strFullUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser
						.nonMandatoryUsrProfileFlds(seleniumPrecondition, "", "",
								"", "", "", strEMail, strEMail,
								strEMail, "");

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objUser.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(seleniumPrecondition));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//navigate to Event Template
				strFailMsg=objEve.createEventTemplate(seleniumPrecondition);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				//fill the required fields in Create Event Template and save
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(seleniumPrecondition, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIconSrc,"","","","",true,strResTypeArr,strStatTypeArr,true,false,false);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertTrue(strFailMsg.equals(""));
				//fill the required fields in Create Event Template and save
				strFailMsg=objNotif.selectEventNofifForUser(seleniumPrecondition, strFullUserName, strEveTemp, true, true, true);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			/*
			 * Step 2: 	Login as user A, navigate to Event >> Event Management. <->	'Event Management' screen is displayed.
			 */
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.newUsrLogin(selenium, strUserName, strUsrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
	
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * Step 3: Create a new event selecting event template ET. 	<->	'Create New Event' screen is displayed. 
			 * Step 4: 	Provide mandatory data select 'Immediately' for 'Event start', Never for 'Event end' and click on 'Save'. <->	Event banner is displayed.
			 * <-> User A receives email, pager and web notifications for event start.

				Web Notification displays the following:
				
				< Event Name > MM/DD/YYYY HH:MM
				< Event Description >
				'Acknowledge All notifications' button 
				
				<-> E-mail and pri-mail notifications displays the following:

				Subject: < Event Name >
				
				Mail body has the following:
				Event Notice for < User full name >
				< description >
				Region: < Region >
				Information Message 'Please do not reply to this email message. You must log into EMResource to take any action that may be required.'
				
				<->  Pager notification display the following:

				Subject: < Event Name >
				
				Body of the pager has the following:
				< description >
				Region: < Region > 
				
				<-> Event is listed in the 'Event Management' screen.

				'Title' and 'Description' are displayed appropriately as provided while event is created under 'Title' and 'Description' columns.
				
				Event start Date and time are displayed appropriately under the column 'Start'.
				
				'Never' is displayed under the column 'End'. 
				
				'Ongoing' is displayed under the 'Status' column.
				
				'Edit' and 'End' links are displayed under the 'Action' column for the event created. 
			 */
			
			try {
				assertTrue(strFailMsg.equals(""));
								
				strFailMsg=objEve.createEventMandFlds(selenium, strEveTemp, strEveName, strInfo, false);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				
				//get Start Date
				 strFndStMnth=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartMnt"));
				 strFndStDay=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartDay"));
				 strFndStYear=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartYear"));
				
				//get Start Time
				 strFndStHr=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartHour"));
				 strFndStMinu=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartMinut"));
				
									
				strFailMsg=objEve.createEvent_FillOtherFields(selenium, strEveName, true, false, false, false, true, "Never", "", "", "", false, "", "", "", "", "", "", "", "");
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				
				try{
					strStartDate=dts.converDateFormat(strFndStYear+strFndStMnth+strFndStDay,"yyyyMMMdd","M/d/yyyy");
					strStartDate=strStartDate+" "+strFndStHr+":"+strFndStMinu;
												
					strFailMsg=objNotif.ackWebNotification(selenium, strEveName, strInfo, strStartDate);
					
					
					try {
						assertTrue(strFailMsg.equals(""));

						strFailMsg = objEve.verfyEventInEventBanner(selenium,
								strEveName);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					
					try {
						assertTrue(strFailMsg.equals(""));
						intResCnt++;
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					strSubjName = strEveName;
					strMsgBody1 = "Event Notice for "
							+ strFullUserName
							+ ": \n "
							+ strInfo
							+ "\n From: "
							+ strFullUserName
							+ "\n Region: "
							+ strRegn
							+ "\n \n Please do not reply to this email message."
							+ " You must log into EMResource to take "
							+ "any action that may be required." + "\n "
							+ propEnvDetails.getProperty("MailURL");
					strMsgBody2 = strInfo + "\n From: " + strFullUserName
							+ "\n Region: " + strRegn;
					
					selenium.selectWindow("");
					strFailMsg = objMail.loginAndnavToInboxInWebMail(seleniumPrecondition,
							strLoginName, strPassword);
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objMail.verifyEmail(seleniumPrecondition, strFrom,
								strTo, strSubjName);

						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg= seleniumPrecondition.getText("css=div.fixed.leftAlign");
							if(strMsg.equals(strMsgBody1)){
								intEMailRes++;
							}else if(strMsg.equals(strMsgBody2)){
								intPagerRes++;
							}
							
						} catch (AssertionError Ae) {
							gstrReason = gstrReason+" "+strFailMsg;
						}
						
						seleniumPrecondition.selectFrame("relative=up");
						seleniumPrecondition.selectFrame("horde_main");
						
						seleniumPrecondition.click("link=Back to Inbox");
						seleniumPrecondition.waitForPageToLoad("90000");
						
						int intCnt=0;
						do{
							try {

								assertTrue(seleniumPrecondition.isElementPresent("//a[text()='"
										+ strSubjName + "']"));
								break;
							}catch(AssertionError Ae){
								Thread.sleep(1000);
								intCnt++;
							
							} catch (Exception Ae) {
								Thread.sleep(1000);
								intCnt++;
							}
						}while(intCnt<20);
						
						strFailMsg=objMail.verifyEmail(seleniumPrecondition, strFrom, strTo, strSubjName);
						
						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg= seleniumPrecondition.getText("css=div.fixed.leftAlign");
							if(strMsg.equals(strMsgBody1)){
								intEMailRes++;
							}else if(strMsg.equals(strMsgBody2)){
								intPagerRes++;
							}
							
						} catch (AssertionError Ae) {
							gstrReason = gstrReason+" "+strFailMsg;
						}
						
						seleniumPrecondition.selectFrame("relative=up");
						seleniumPrecondition.selectFrame("horde_main");
						
						seleniumPrecondition.click("link=Back to Inbox");
						seleniumPrecondition.waitForPageToLoad("90000");
						
						
						intCnt=0;
						do{
							try {

								assertTrue(seleniumPrecondition.isElementPresent("//a[text()='"
										+ strSubjName + "']"));
								break;
							}catch(AssertionError Ae){
								Thread.sleep(1000);
								intCnt++;
							
							} catch (Exception Ae) {
								Thread.sleep(1000);
								intCnt++;
							}
						}while(intCnt<20);
						
						
						strFailMsg=objMail.verifyEmail(seleniumPrecondition, strFrom, strTo, strSubjName);
						
						try {
							assertTrue(strFailMsg.equals(""));
							String strMsg= seleniumPrecondition.getText("css=div.fixed.leftAlign");
							if(strMsg.equals(strMsgBody1)){
								intEMailRes++;
							}else if(strMsg.equals(strMsgBody2)){
								intPagerRes++;
							}
							
						} catch (AssertionError Ae) {
							gstrReason = gstrReason+" "+strFailMsg;
						}
						if(intEMailRes==2&&intPagerRes==1){
							intResCnt++;
						}
						seleniumPrecondition.close();
						
						selenium.selectWindow("");	
						selenium.selectFrame("Data");
						strStartDate=dts.converDateFormat(strFndStYear+strFndStMnth+strFndStDay,"yyyyMMMdd","yyyy-MM-dd");
						strStartDate=strStartDate+" "+strFndStHr+":"+strFndStMinu;						
						strEndDate="never";
						String[] strData={strAction1,strIcon,strStatus,strStartDate,strEndDate,strEveName,strDrill,strEveTemp,strInfo};
						
						for(int intRec=0;intRec<strHeader.length;intRec++){
							strFailMsg=objList.checkDataInEventListTable(selenium, strHeader[intRec], strEveName, strData[intRec], String.valueOf(intRec+1));
							try{
								assertTrue(strFailMsg.equals(""));						
							}catch(AssertionError ae){
								gstrReason = gstrReason+" "+strFailMsg;
							}
						}
							
						/*
						 * Step 5:Navigate to Event >> Event List <->	Event is listed in the 'Event List' screen.
						

							<-> 'Title' and 'Description' are displayed appropriately as provided while event is created under 'Title' and 'Description' columns.
	
							<-> Event start Date and time are displayed appropriately under the column 'Start'.
	
							<-> Event end Date and time are displayed appropriately under the column 'End'.
	
							<-> 'Never' is displayed under the column 'End'. 
	
							<-> 'View History' link is displayed under the 'Action' column for the event created. 
						*/
						
						try {
							assertTrue(strFailMsg.equals(""));							
							assertTrue(objList.navToEventList(selenium));	
							
							String[] strEveData={strAction2,strIcon,strStatus,strStartDate,strEndDate,strEveName,strDrill,strEveTemp,strInfo};
							
							for(int intRec=0;intRec<strHeader.length;intRec++){
								strFailMsg=objList.checkDataInEventListTable(selenium, strHeader[intRec], strEveName, strEveData[intRec], String.valueOf(intRec+1));
								try{
									assertTrue(strFailMsg.equals(""));						
								}catch(AssertionError ae){
									gstrReason = gstrReason+" "+strFailMsg;
								}
							}
							
						} catch (AssertionError Ae) {
							gstrReason = gstrReason+" Event List screen is NOT displayed";
						}	
					
						strStartDate=dts.converDateFormat(strFndStYear+strFndStMnth+strFndStDay,"yyyyMMMdd","dd-MMM-yyyy");
						strStartDate=strStartDate+" "+strFndStHr+":"+strFndStMinu;
																		
						strFailMsg=objMob.checkInEventTimes(selenium, strEveName, strStartDate, strEndDate);
						try {
							assertTrue(strFailMsg.equals(""));	
							intResCnt++;
						}catch(AssertionError ae){
							gstrReason = gstrReason+" "+strFailMsg;
						}
						
						if(intResCnt==3&&gstrReason.compareTo("")==0){
							gstrResult="PASS";
							//Write result data
							strTestData[0]= propEnvDetails.getProperty("Build");
							strTestData[1]=gstrTCID;
							strTestData[2]=strUserName+"/"+strUsrPassword;
							strTestData[3]=strEveTemp;
							strTestData[4]=strEveName;
							strTestData[5]="check in Mobile, Start Date: "+strStartDate+", End Date: "+strEndDate;
							
							String strWriteFilePath=pathProps.getProperty("WriteResultPath");
							objOFC.writeResultData(strTestData, strWriteFilePath, "Events");
						}
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}			
					
					
				}catch(AssertionError ae){
					log4j.info("Event banner is NOT displayed.");
					gstrReason="Event banner is NOT displayed.";
				}
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "118080";
			gstrTO = "Create an event selecting to start immediately and " +
					"to end never and verify that the event starts " +
					"immediately and does not have an end date.";
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
	/**
	'*************************************************************
	' Description: Verify that a file can be attached to an event
	' Precondition: 1. User A is created providing primary e-mail, e-mail and pager addresses.
				2. User A has the following rights;
				a. 'Maintain Events' right
				b. 'Maintain Event Templates' right
				c. 'User - Setup User Accounts' right
				
				2. Event template ET is created providing mandatory data and selecting the check boxes E-mail, Pager and Web for user A. 
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
	public void testBQS118085() throws Exception {
		try {
			gstrTCID = "118085";
			gstrTO = "Verify that a file can be attached to an event";
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			CreateUsers objUser = new CreateUsers();
			ReadData objReadData = new ReadData();
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strAutoFilePath = propAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strAutoFileName = propAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");

			String strUplTxtFilePath = pathProps
					.getProperty("CreateEve_UploadTxtFile_OpenPath");
			String strUplHtmlFilePath = pathProps
					.getProperty("CreateEve_UploadHtmlFile_OpenPath");

			String strUploadPath[] = { strUplTxtFilePath, strUplHtmlFilePath };
			String strTestData[] = new String[10];
			String strStartDate = "";

			String strTempDef = objReadData.readInfoExcel("Event Temp Data", 2,
					3, strFILE_PATH);
			String strEveColor = objReadData.readInfoExcel("Event Temp Data",
					2, 4, strFILE_PATH);
			String strAsscIcon=objReadData.readInfoExcel("FirefoxTestData", 2, 2, strFILE_PATH);
			String strIconSrc=objReadData.readInfoExcel("FirefoxTestData", 2, 3, strFILE_PATH);

			String strInfo = "This is an automation event";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			Login objLogin = new Login();// object of class Login
			EventSetup objEve = new EventSetup();
			General objMail = new General();

			int intResCnt = 0;

			EventNotification objNotif = new EventNotification();

			//String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			String strFullUserName = "Full" + strUserName;
			
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strLoginName = objReadData.readData("WebMailUser", 2, 1);
			String strPassword = objReadData.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1 = "";
			String strMsgBody2 = "";
			String strSubjName = "";

			String strEMail = objReadData.readData("WebMailUser", 2, 1);
			String strEveTemp = "AutoET_" + System.currentTimeMillis();

			String strTxtText = "Automation event";
			String strHtmlText = "Test EMResource Events";

			String strFileTextArr[] = { strTxtText, strHtmlText };
			String strEventArr[] = new String[4];
			String strEventValue[] = new String[2];
			String strFailMsg = "";
			// login details
			String strAdmUserName = objReadData.readData("Login", 3, 1);
			String strAdmPassword = objReadData.readData("Login", 3, 2);
			String strRegn = objReadData.readData("Login", 3, 4);

			String strResTypeArr[] = {};
			String strStatTypeArr[] = {};

			strFailMsg = objLogin.login(seleniumPrecondition, strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
						strUserName, strUsrPassword, strUsrPassword,
						strFullUserName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.nonMandatoryUsrProfileFlds(seleniumPrecondition, "",
						"", "", "", "", strEMail, strEMail, strEMail, "");

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(seleniumPrecondition, propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
						true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(seleniumPrecondition, propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);

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

				strFailMsg = objUser.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

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
				strFailMsg = objEve.fillMandfieldsAndSaveEveTemp(seleniumPrecondition,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strResTypeArr,
						strStatTypeArr, true, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// fill the required fields in Create Event Template and save
				strFailMsg = objNotif.selectEventNofifForUser(seleniumPrecondition,
						strFullUserName, strEveTemp, true, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			/*
			 * Step 2: Login as user A, navigate to Event >> Event Management.
			 * <-> 'Event Management' screen is displayed.
			 */
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			/*	 
			 * * Step 2: Login as user U1, navigate to Event >> Event
			 * Management. <-> 'Event Management' screen is displayed.
			 */

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.newUsrLogin(selenium, strUserName,
						strUsrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

		
			try {
				assertTrue(strFailMsg.equals(""));

				for (int intRec = 0; intRec < 2; intRec++) {
					int intEMailRes = 0;
					int intPagerRes = 0;
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					//strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

					strEventArr[intRec] = "Eve"+System.currentTimeMillis();
					try {

						assertTrue(objEve.navToEventManagement(selenium));

						/*
						 * Step 3: Create a new event selecting event template
						 * ET. <-> 'Create New Event' screen is displayed. Step
						 * 4: Create an event EVE providing mandatory data,
						 * attaching an html file. <-> Event is listed in the
						 * 'Event Management' screen. <-> In the e-mail and
						 * pager notifications received, the message
						 * "See attached document at https://emresource-test.emsystem.com"
						 * is displayed.
						 */

						strFailMsg = objEve.createEventMandFlds(selenium,
								strEveTemp, strEventArr[intRec], strInfo, false);

						try {
							assertTrue(strFailMsg.equals(""));

							// get Start Date
							strFndStMnth = selenium
									.getSelectedLabel(propElementDetails
											.getProperty("Event.CreateEve.StartMnt"));
							strFndStDay = selenium
									.getSelectedLabel(propElementDetails
											.getProperty("Event.CreateEve.StartDay"));
							strFndStYear = selenium
									.getSelectedLabel(propElementDetails
											.getProperty("Event.CreateEve.StartYear"));

							// get Start Time
							strFndStHr = selenium
									.getSelectedLabel(propElementDetails
											.getProperty("Event.CreateEve.StartHour"));
							strFndStMinu = selenium
									.getSelectedLabel(propElementDetails
											.getProperty("Event.CreateEve.StartMinut"));

							String strProcess = "";
							String strArgs[] = { strAutoFilePath,
									strUploadPath[intRec] };
							// AutoIt
							Runtime.getRuntime().exec(strArgs);
							selenium.click(propElementDetails
									.getProperty("Event.CreateEVe.Browse"));
							Thread.sleep(5000);
							int intCnt = 0;
							do {
								GetProcessList objGPL = new GetProcessList();
								strProcess = objGPL.GetProcessName();
								intCnt++;
								Thread.sleep(500);
							} while (strProcess.contains(strAutoFileName)
									&& intCnt < 120);

						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}

						try {
							assertTrue(strFailMsg.equals(""));
							// click on save
							selenium.click(propElementDetails
									.getProperty("Event.CreateEve.Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
												+ strEventArr[intRec] + "']"));
								log4j
										.info("Event '"
												+ strEventArr[intRec]
												+ "' is listed on 'Event Management' screen.");
								intResCnt++;
							} catch (AssertionError ae) {
								log4j
										.info("Event '"
												+ strEventArr[intRec]
												+ "' is NOT listed on 'Event Management' screen.");
								gstrReason = gstrReason
										+ " Event '"
										+ strEventArr[intRec]
										+ "' is NOT listed on 'Event Management' screen.";
							}

							log4j.info(strEventArr[intRec]);
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}

						try {
							assertEquals("", strFailMsg);
							strEventValue[intRec] = objEve
									.fetchEventValueInEventList(selenium,
											strEventArr[intRec]);

							if (strEventValue[intRec].compareTo("") != 0) {
								strFailMsg = "";
							} else {
								strFailMsg = "Failed to fetch Event Value";
							}
						} catch (AssertionError Ae) {
							gstrReason = strFailMsg;
						}
						
						
						
						try {
							assertTrue(strFailMsg.equals(""));

							try {
								
								strStartDate = dts.converDateFormat(
										strFndStYear + strFndStMnth
												+ strFndStDay, "yyyyMMMdd",
										"M/d/yyyy");
								strStartDate = strStartDate + " " + strFndStHr
										+ ":" + strFndStMinu;

								strFailMsg = objNotif.ackWebNotification(
										selenium, strEventArr[intRec], strInfo,
										strStartDate);
								try {
									assertEquals("", strFailMsg);
									strFailMsg = objEve
											.verfyEventInEventBanner(selenium,
													strEventArr[intRec]);
								} catch (AssertionError Ae) {
									gstrReason = strFailMsg;
								}
								

								try {
									assertTrue(strFailMsg.equals(""));
									intResCnt++;
								} catch (AssertionError Ae) {
									gstrReason = gstrReason + " " + strFailMsg;
								}

								strSubjName = strEventArr[intRec];
								
								strMsgBody1 = "Event Notice for "
										+ strFullUserName
										+ ": \n "
										+ strInfo
										+ "\n See attached document at"
										+ " "
										+ propEnvDetails.getProperty("MailURL")
										+ "\n From: "
										+ strFullUserName
										+ "\n Region: "
										+ strRegn
										+ "\n \n Please do not reply to this email message."
										+ " You must log into EMResource to take "
										+ "any action that may be required."
										+ "\n "
										+ propEnvDetails.getProperty("MailURL");
								
								/*strMsgBody1 = "Event Notice for "
										+ strFullUserName
										+ ":"
										+ " \n"
										+ strInfo
										+ "\nSee attached document at"
										+ " "
										+ propEnvDetails.getProperty("MailURL")+ "\nFrom: "
										+ strFullUserName
										+ "\nRegion: "
										+ ""
										+ strRegn
										+ "\n\nPlease do not reply to "
										+ "this email message. You must log into EMResource "
										+ "to take any action that may be required."
										+ "\n"
										+ propEnvDetails.getProperty("MailURL");
								*/
								
								
								strMsgBody2 = strInfo
										+ "\n See attached document at "
										+ propEnvDetails.getProperty("MailURL")
										+ "\n From: " + strFullUserName
										+ "\n Region: " + strRegn;
								
								/*strMsgBody2 = strInfo
										+ "\nSee attached document at "
										+ propEnvDetails.getProperty("MailURL")
										+ "\nFrom: " + strFullUserName
										+ "\nRegion: " + strRegn;
*/
								Writer output = null;
								String text = strMsgBody1;
								File file = new File(
										"C:\\Documents and Settings\\All Users\\Desktop\\Input.txt");
								output = new BufferedWriter(
										new FileWriter(file));
								output.write(text);
								output.close();

								output = null;
								text = strMsgBody2;
								file = new File(
										"C:\\Documents and Settings\\All Users\\Desktop\\InputPager.txt");
								output = new BufferedWriter(
										new FileWriter(file));
								output.write(text);
								output.close();

								seleniumPrecondition.selectWindow("");
								strFailMsg = objMail
										.loginAndnavToInboxInWebMail(seleniumPrecondition,
												strLoginName, strPassword);
								try {
									assertTrue(strFailMsg.equals(""));
									strFailMsg = objMail.verifyEmail(seleniumPrecondition,
											strFrom, strTo, strSubjName);

									try {
										assertTrue(strFailMsg.equals(""));
										String strMsg = seleniumPrecondition
												.getText("css=div.fixed.leftAlign");
										if (strMsg.equals(strMsgBody1)) {
											intEMailRes++;
										} else if (strMsg.equals(strMsgBody2)) {
											intPagerRes++;
										}

									} catch (AssertionError Ae) {
										gstrReason = gstrReason + " "
												+ strFailMsg;
									}

									seleniumPrecondition.selectFrame("relative=up");
									seleniumPrecondition.selectFrame("horde_main");

									seleniumPrecondition.click("link=Back to Inbox");
									seleniumPrecondition.waitForPageToLoad("90000");
									
									int intCnt=0;
									do{
										try {

											assertTrue(seleniumPrecondition.isElementPresent("//a[text()='"
													+ strSubjName + "']"));
											break;
										}catch(AssertionError Ae){
											Thread.sleep(1000);
											intCnt++;
										
										} catch (Exception Ae) {
											Thread.sleep(1000);
											intCnt++;
										}
									}while(intCnt<20);
									

									strFailMsg = objMail.verifyEmail(seleniumPrecondition,
											strFrom, strTo, strSubjName);

									try {
										assertTrue(strFailMsg.equals(""));
										String strMsg = seleniumPrecondition
												.getText("css=div.fixed.leftAlign");
										output = null;
										text = strMsg;
										file = new File(
												"C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
										output = new BufferedWriter(
												new FileWriter(file));
										output.write(text);
										output.close();
										if (strMsg.equals(strMsgBody1)) {

											intEMailRes++;
										} else if (strMsg.equals(strMsgBody2)) {
											intPagerRes++;
										}

									} catch (AssertionError Ae) {
										gstrReason = gstrReason + " "
												+ strFailMsg;
									}

									seleniumPrecondition.selectFrame("relative=up");
									seleniumPrecondition.selectFrame("horde_main");

									seleniumPrecondition.click("link=Back to Inbox");
									seleniumPrecondition.waitForPageToLoad("90000");
									
									intCnt=0;
									do{
										try {

											assertTrue(seleniumPrecondition.isElementPresent("//a[text()='"
													+ strSubjName + "']"));
											break;
										}catch(AssertionError Ae){
											Thread.sleep(1000);
											intCnt++;
										
										} catch (Exception Ae) {
											Thread.sleep(1000);
											intCnt++;
										}
									}while(intCnt<20);
									

									strFailMsg = objMail.verifyEmail(seleniumPrecondition,
											strFrom, strTo, strSubjName);

									try {
										assertTrue(strFailMsg.equals(""));
										String strMsg = seleniumPrecondition
												.getText("css=div.fixed.leftAlign");
										output = null;
										text = strMsg;
										file = new File(
												"C:\\Documents and Settings\\All Users\\Desktop\\MailPager.txt");
										output = new BufferedWriter(
												new FileWriter(file));
										output.write(text);
										output.close();

										if (strMsg.equals(strMsgBody1)) {
											intEMailRes++;
										} else if (strMsg.equals(strMsgBody2)) {
											intPagerRes++;
										}

									} catch (AssertionError Ae) {
										gstrReason = gstrReason + " "
												+ strFailMsg;
									}
									if (intEMailRes == 2 && intPagerRes == 1) {
										intResCnt++;
									} else {
										log4j
												.info("Email Message body is NOT displayed correctly");
										gstrReason = "Email Message body is NOT displayed correctly";
									}

									seleniumPrecondition.selectFrame("relative=up");
									seleniumPrecondition.selectFrame("horde_main");

								} catch (AssertionError Ae) {
									gstrReason = gstrReason + " " + strFailMsg;
								}

								// logout from web mail
								seleniumPrecondition.click("link=Log out");
								seleniumPrecondition.waitForPageToLoad("30000");
								seleniumPrecondition.close();
								
								try{
									selenium.selectWindow("");
									selenium.selectFrame("Data");
								}catch(Exception e){
									
								}
								/*
								 * Step 5: Click on the event banner of EVE.
								 * <->'Attached file' link is displayed. Step 6:
								 * Click on 'Attached file' link. <-> The
								 * attached file (while event creation) is
								 * opened.
								 */

								strFailMsg = objEve
										.checkAttachedFilesForEventsWithFocus(selenium,
												strEventArr[intRec],strEventValue[intRec],
												strFileTextArr[intRec]);

								try {
									assertTrue(strFailMsg.equals(""));
									intResCnt++;
								} catch (AssertionError ae) {
									gstrReason = gstrReason + " " + strFailMsg;
								}

								try{
									selenium.selectWindow("");
									selenium.selectFrame("Data");
								}catch(Exception e){
									
								}
								
							} catch (AssertionError ae) {
								log4j.info("Event banner is NOT displayed.");
								gstrReason = gstrReason
										+ " Event banner is NOT displayed.";
							}

						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}
					} catch (AssertionError Ae) {
						gstrReason = gstrReason
								+ "  Event Management screen is NOT displayed";
					}

				}
				if (intResCnt == 8) {
					gstrResult = "PASS";
					// Write result data
					strTestData[0] = propEnvDetails.getProperty("Build");
					strTestData[1] = gstrTCID;
					strTestData[2] = strUserName + "/" + strUsrPassword;
					strTestData[3] = strEveTemp;
					strTestData[4] = "";
					strTestData[5] = "Attach PDF file and Create Event in Event Template and check";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestData, strWriteFilePath,
							"Events");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		} catch (Exception e) {

			Exception excReason;
			gstrTCID = "118085";
			gstrTO = "Verify that a file can be attached to an event";
			gstrResult = "FAIL";
			excReason = null;

			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();

		}
	}
	
	/**
	'*************************************************************
	' Description: Create an event selecting to start immediately and to end never and verify that the event starts immediately and does not have an end date.
	' Precondition: 1. User A is created providing primary e-mail, e-mail and pager addresses.
					2. User A has the following rights;
					a. 'Maintain Events' right
					b. 'Maintain Event Templates' right
					c. 'User - Setup User Accounts' right					
					2. Event template ET is created providing mandatory data and selecting the check boxes E-mail, Pager and Web for user A. 
	' Arguments:
	' Returns:
	' Date: 22-10-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'*************************************************************
*/
	
	@Test
	public void testBQS92364() throws Exception {
		try {
			gstrTCID = "92364";
			gstrTO = "Verify that an event can be created by providing data only in the mandatory fields.";
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			ReadData objReadData = new ReadData();
			EventList objList = new EventList();
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strTestData[] = new String[10];
			String strTmText = dts.getCurrentDate("HHmm");
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			EventSetup objEve = new EventSetup();
			Login objLogin = new Login();// object of class Login
			StatusTypes objST = new StatusTypes();

			CreateUsers objCreateUsers = new CreateUsers();

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strHourDur = "24";
			String strFutureTime = "";

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

			String strResource = "AutoRs_" + strTimeText;
			String strEveTemp = "AutoET_" + strTimeText;
			String strTempDef = objReadData.readInfoExcel("Event Temp Data", 2,
					3, strFILE_PATH);
			String strEveColor = objReadData.readInfoExcel("Event Temp Data",
					2, 4, strFILE_PATH);
			String strAsscIcon = objReadData.readInfoExcel("Event Temp Data",
					2, 5, strFILE_PATH);
			String strIconSrc = objReadData.readInfoExcel("Event Temp Data", 2,
					6, strFILE_PATH);

			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strMSTValue = "Multi";
			String strNSTValue = "Number";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strNumStat = "Aa1_" + strTimeText;
			String strMulStat = "Aa2_" + strTimeText;
			String strTextStat = "Aa3_" + strTimeText;
			String strSatuStat = "Aa4_" + strTimeText;

			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword = objReadData.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
			String strStatTypDefn = "Auto";
			String strFailMsg = "";
			String strRSTvalue[] = new String[4];

			String strStatValue = "";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue[] = new String[1];
			String strRSValue[] = new String[1];
			String strAbbrv = "A" + strTmText;
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "This is an automation event";
			String strEventValue = "";

			String strAction1 = "Edit";
			String strAction2 = "End";
			String strIcon = objReadData.readInfoExcel("Event Temp Data", 8, 6,
					strFILE_PATH);
			String strStatus = "Ongoing";
			String strStartDate = "";
			String strEndDate = "";
			String strDrill = "No";

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMSTValue, strMulStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strMulStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNSTValue, strNumStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strNumStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTSTValue, strTextStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strTextStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[2] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSSTValue, strSatuStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strSatuStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[3] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
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
				strFailMsg = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strRSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			for (int intST = 0; intST < strRSTvalue.length; intST++) {
				try {
					assertEquals("", strFailMsg);

					strFailMsg = objRs.selAndDeselSTInEditResLevelST(selenium,
							strRSTvalue[intST], true);

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(selenium,
						strResrctTypName);
				if (strFailMsg.compareTo("") != 0) {

					strRTValue[0] = strFailMsg;
					strFailMsg = "";
				} else {
					strFailMsg = "Failed to fetch resource type value";
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
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFailMsg = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objEve.navToEventSetupPge(selenium);
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
				strFailMsg = objEve.fillMandfieldsAndSaveEveTemp(selenium,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strRTValue,
						strRSTvalue, true, false, false);

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
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

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

				strFailMsg = objCreateUsers.savVrfyUserWithSearchUser(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			/*
			 * Step 2: Login as user A, navigate to Event >> Event Management.
			 * <-> 'Event Management' screen is displayed.
			 */

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.newUsrLogin(selenium, strUserName,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objEve.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			String strEveHeaders[] = { "Action", "Icon", "Status", "Start",
					"End", "Title", "Drill", "Template", "Information" };
			for (int intCol = 0; intCol < strEveHeaders.length; intCol++) {
				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg = objEve.checkEventHeadersInEventMgmt(selenium,
							strEveHeaders[intCol], String.valueOf(intCol + 1));

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}

			}
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objEve.navSelectEventTemp(selenium, strEveTemp);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objEve.navToCreateNewEventPage(selenium,
						strEveTemp);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objEve.chkFieldsInCreateNewEventPge(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
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

				String strEdDate = strFndStMnth + "/" + strFndStDay + "/"
						+ strFndStYear + " " + strFndStHr + ":" + strFndStMinu;
				// End Date
				strFutureTime = dts.addTimetoExistingHour(strEdDate, Integer
						.parseInt(strHourDur), "MMM/dd/yyyy HH:mm");

				String strFutDateArr[] = strFutureTime.split(" ");
				String strEdDateArr[] = strFutDateArr[0].split("/");
				strFndEdMnth = strEdDateArr[0];
				strFndEdDay = strEdDateArr[1];
				strFndEdYear = strEdDateArr[2];

				String strEdTime[] = strFutDateArr[1].split(":");
				strFndEdHr = strEdTime[0];
				strFndEdMinu = strEdTime[1];

				strFailMsg = objEve.fillMandFieldsAndCreateEvent(selenium,
						strResource, strEveName, strInfo, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				strEventValue = objEve.fetchEventValueInEventList(selenium,
						strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFailMsg = "";
				} else {
					strFailMsg = "Failed to fetch event value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
					+ strFndStDay, "yyyyMMMdd", "yyyy-MM-dd");
			strStartDate = strStartDate + " " + strFndStHr + ":" + strFndStMinu;

			strEndDate = dts.converDateFormat(strFndEdYear + strFndEdMnth
					+ strFndEdDay, "yyyyMMMdd", "yyyy-MM-dd");
			strEndDate = strEndDate + " " + strFndEdHr + ":" + strFndEdMinu;

			String[] strData = { strAction1 + "  |  " + strAction2, strIcon,
					strStatus, strStartDate, strEndDate, strEveName, strDrill,
					strEveTemp, strInfo };

			for (int intRec = 0; intRec < strEveHeaders.length; intRec++) {

				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg = objList.checkDataInEventListTable(selenium,
							strEveHeaders[intRec], strEveName, strData[intRec],
							String.valueOf(intRec + 1));
				} catch (AssertionError ae) {
					gstrReason = gstrReason + " " + strFailMsg;
				}
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objList.navToEventListNew(selenium);
			} catch (AssertionError ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objList.checkDataInEventListTable(selenium,
						"Action", strEveName, "View", "1");
			} catch (AssertionError ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}

			strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
					+ strFndStDay, "yyyyMMMdd", "MM/dd/yy");
			strStartDate = strStartDate + " " + strFndStHr + ":" + strFndStMinu;
			String[] strTime = strStartDate.split(" ");
			String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
					"HH:mm");
			strAddedDtTime = strTime[0] + " " + strAddedDtTime;
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objList.clickEventBanner(selenium, strEveName);
			} catch (AssertionError ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objEve
						.checkUserInfoInEventBannerCorrect(selenium,
								"Created By: " + strUsrFulName + " @ "
										+ strStartDate + " "+propEnvDetails.getProperty("TimeZone") + "\n"
										+ strInfo, false, strEventValue);
				assertTrue(strFailMsg.equals(""));
			} catch (AssertionError Ae) {
				strFailMsg = objEve.checkUserInfoInEventBannerCorrect(selenium,
						"Created By: " + strUsrFulName + " @ " + strAddedDtTime
								+ "\n" + strInfo, false, strEventValue);

			}
			try {
				assertTrue(strFailMsg.equals(""));
				gstrResult = "PASS";

				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strConfirmPwd;
				strTestData[3] = strEveTemp;
				strTestData[4] = strEveName;
				strTestData[5] = "check in Mobile, Start Date: " + strStartDate
						+ ", End Date: " + strEndDate;

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Events");

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		} catch (Exception e) {

			Exception excReason;
			gstrTCID = "92364";
			gstrTO = " Verify that an event can be created by providing data only in the mandatory fields.";
			gstrResult = "FAIL";
			excReason = null;

			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();

		}
	}
	
	//start//testBQS118083//
	/***************************************************************
	'Description	:Create an event selecting to start at a specified date and time and to end never and verify that the event starts at the specified time and does not have an end date.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:5/23/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS118083() throws Exception {
		try {
			gstrTCID = "118083"; // Test Case Id
			gstrTO = " Create an event selecting to start at a specified date and time and" +
					"to end never and verify that the event starts at the specified " +
					"time and does not have an end date.";// Test																																										// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			ReadData objReadData = new ReadData (); 
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strTestData[]=new String[10];
			String strAction1="Edit";
			String strIcon=objReadData.readInfoExcel("Event Temp Data", 8, 6, strFILE_PATH);
			String strStatus="Future";
			String strStartDate="";
			String strEndDate="Never";
			String strDrill="No";
			String strInfo="This is an automation event";
	
			String strFutureDate="";	
			CreateUsers objUser=new CreateUsers();
			Login objLogin = new Login();// object of class Login
			EventSetup objEve=new EventSetup();
			EventList objList=new EventList();
			MobileView objMob=new MobileView();
							
			String strEMail=objReadData.readData("WebMailUser", 2, 1);
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strUsrPassword="abc123";
			String strFullUserName="Full"+strUserName;
			
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			
			EventNotification objNotif=new EventNotification();
			
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
						
			String strEveTemp="AutoET_"+strTimeText;
			String strEveName="AutoEve_"+strTimeText;
			
			String strFuncResult="";
			String[] strHeader={"Action","Icon","Status","Start","End","Title","Drill","Template","Information"};
			
		
			// login details
			String strAdmUserName = objReadData.readData("Login", 3, 1);
			String strAdmPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);		
			
			String strTempDef=objReadData.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objReadData.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objReadData.readInfoExcel("FirefoxTestData", 2, 2, strFILE_PATH);
			String strIconSrc=objReadData.readInfoExcel("FirefoxTestData", 2, 3, strFILE_PATH);
			
			String strResTypeArr[]={};
			String strStatTypeArr[]={};

			/*
			 * STEP : Action:Preconditions:
			 * 
			 * 1. User A is created providing primary e-mail, e-mail and pager
			 * addresses. 2. User A has the following rights; a. 'Maintain
			 * Events' right b. 'Maintain Event Templates' right c. 'User -
			 * Setup User Accounts' right
			 * 
			 * 2. Event template ET is created providing mandatory data and
			 * selecting the check boxes E-mail, Pager and Web for user A.
			 * Expected Result:No Expected Result
			 */
			// 650760
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			strFuncResult = objLogin.login(seleniumPrecondition,strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUser.navUserListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUser.fillUsrMandatoryFlds(seleniumPrecondition, strUserName, strUsrPassword, strUsrPassword, strFullUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUser
						.nonMandatoryUsrProfileFlds(seleniumPrecondition, "", "",
								"", "", "", strEMail, strEMail,
								strEMail, "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUser
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
				strFuncResult = objUser
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUser
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUser.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertTrue(strFuncResult.equals(""));	
				strFuncResult= "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(seleniumPrecondition));
				strFuncResult="";	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));	
				//navigate to Event Template
				strFuncResult=objEve.createEventTemplate(seleniumPrecondition);
								
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));
				//fill the required fields in Create Event Template and save
				strFuncResult=objEve.fillMandfieldsAndSaveEveTemp(seleniumPrecondition, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIconSrc,"","","","",true,strResTypeArr,strStatTypeArr,true,false,false);
								
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+strFuncResult;
			}	
			
			try {
				assertTrue(strFuncResult.equals(""));
				//fill the required fields in Create Event Template and save
				strFuncResult=objNotif.selectEventNofifForUser(seleniumPrecondition, strFullUserName, strEveTemp, true, true, true);
								
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+strFuncResult;
			}
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
			/*
			 * STEP : Action:Login as user A, navigate to Event >> Event
			 * Management. Expected Result:'Event Management' screen is
			 * displayed.
			 */
			// 650761
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strUsrPassword);
			} catch (AssertionError Ae) {
				gstrReason =gstrReason+ strFuncResult;
			}
	
			
			try {
				assertTrue(strFuncResult.equals(""));	
				strFuncResult= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFuncResult="";	
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+strFuncResult;
			}

			/*
			 * STEP : Action:Create a new event selecting event template ET.
			 * Expected Result:'Create New Event' screen is displayed.
			 */
			// 650762

			/*
			 * STEP : Action:Provide mandatory data, select certain date and
			 * time for 'Event start', Never for 'Event end' and click on
			 * 'Save'. Expected Result:Event is listed in the 'Event Management'
			 * screen.
			 * 
			 * 'Title' and 'Description' are displayed appropriately as provided
			 * while event is created under 'Title' and 'Description' columns.
			 * 
			 * Event start Date and time are displayed appropriately under the
			 * column 'Start'.
			 * 
			 * 'Never' is displayed under the column 'End'.
			 * 
			 * 'Future' is displayed under the 'Status' column.
			 * 
			 * 'Edit' and 'Cancel' links are displayed under the 'Action' column
			 * for the event created.
			 */
			// 650763

			try {
				assertTrue(strFuncResult.equals(""));
								
				strFuncResult=objEve.createEventMandFlds(selenium, strEveTemp, strEveName, strInfo, false);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));

				String strStatTimeArr[]=selenium.getText("css=#statusTime").split(" ");
				String strCurYear=dts.getCurrentDate("yyyy");
				String strStatTime=strStatTimeArr[1]+"/"+strStatTimeArr[0]+"/"+strCurYear+" "+strStatTimeArr[2];
				 
				strFutureDate=dts.AddDaytoExistingDate(strStatTime, 1, "MMM/d/yyyy HH:mm");
				strFuncResult = objEve.createEvent_FillOtherFields(selenium,strEveName, true, false, false, false, false, "Never","", strFutureDate, "", false, "", "", "", "", "", "", "", "");
				
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+strFuncResult;
			}
					
			selenium.selectWindow("");	
			selenium.selectFrame("Data");
			strStartDate=dts.converDateFormat(strFutureDate,"MMM/d/yyyy HH:mm","yyyy-MM-dd HH:mm");
									
			strEndDate="never";
			String[] strData={strAction1,strIcon,strStatus,strStartDate,strEndDate,strEveName,strDrill,strEveTemp,strInfo};
			
			for(int intRec=0;intRec<strHeader.length;intRec++){
				strFuncResult=objList.checkDataInEventListTable(selenium, strHeader[intRec], strEveName, strData[intRec], String.valueOf(intRec+1));
				try{
					assertTrue(strFuncResult.equals(""));						
				}catch(AssertionError ae){
					gstrReason = gstrReason+" "+strFuncResult;
				}
			}

			/*
			 * STEP : Action:Navigate to Event >> Event List Expected
			 * Result:Event created (Future event) is not listed on the 'Event
			 * List' screen.
			 */
			// 650764
			try {
				assertTrue(strFuncResult.equals(""));							
				assertTrue(objList.navToEventList(selenium));	
				
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" Event List screen is NOT displayed";
			}	
			
				
			try{
				strFuncResult=objList.checkEvenetEventListTable(selenium, strEveName);
				assertTrue(strFuncResult.equals(""));						
			}catch(AssertionError ae){
				gstrReason = gstrReason+" "+strFuncResult;
			}
			
			/*
			 * STEP : Action:Click on 'Mobile View' link on footer of the
			 * application. Expected Result:'Main Menu' screen is displayed.
			 */
			// 650765
			strStartDate=dts.converDateFormat(strFutureDate,"MMM/d/yyyy HH:mm","dd-MMM-yyyy HH:mm");
			try {
				assertTrue(strFuncResult.equals(""));											
				strFuncResult=objMob.checkInEventTimes(selenium, strEveName, strStartDate, strEndDate);
			
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+strFuncResult;
			}
			
			try{
				assertTrue(gstrReason.compareTo("")==0);
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				//Write result data
				strTestData[0]= propEnvDetails.getProperty("Build");
				strTestData[1]=gstrTCID;
				strTestData[2]=strUserName+"/"+strUsrPassword;
				strTestData[3]=strEveTemp;
				strTestData[4]=strEveName;
				strTestData[5]="check in Mobile, Start Date: "+strStartDate+", End Date: "+strEndDate;
				
				String strWriteFilePath=pathProps.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Events");
			}catch(AssertionError a){
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Events' link. Expected Result:'Event
			 * Times' screen is displayed with appropriate data for 'Event
			 * Started' and 'Scheduled End' controls.
			 */
			// 650766

			/*
			 * STEP : Action:Login to mobile device as user A, click on 'Events'
			 * link. Expected Result:On 'Event Detail' screen appropriate data
			 * for 'Start' and 'End' controls is displayed.
			 */
			// 650767

			/*
			 * STEP : Action:On Web application, wait for event start (Date and
			 * time (D1 and T1)) Expected Result:Event banner is displayed.
			 * 
			 * User A receives email, pager and web notifications for event
			 * start.
			 * 
			 * E-mail, Pager notifications display 'Full name of user' who
			 * created the event for 'From' field.
			 * 
			 * Event is listed in the 'Event Management' screen.
			 * 
			 * 'Title' and 'Description' are displayed appropriately as provided
			 * while event is created under 'Title' and 'Description' columns.
			 * 
			 * Event start Date and time, Event end Date and time are displayed
			 * appropriately under the column 'Start', 'End' controls
			 * respectively.
			 * 
			 * 'Ongoing' is displayed under the 'Status' column.
			 * 
			 * 'Edit' and 'End' links are displayed under the 'Action' column
			 * for the event created.
			 */
			// 650768

			/*
			 * STEP : Action:Navigate to Event >> Event List Expected
			 * Result:Event is listed in the 'Event List' screen.
			 * 
			 * 'Title' and 'Description' are displayed appropriately as provided
			 * while event is created under 'Title' and 'Description' columns.
			 * 
			 * Event start Date and time are displayed appropriately under the
			 * column 'Start'.
			 * 
			 * 'Never' is displayed under the column 'End'.
			 * 
			 * 'Ongoing' is displayed under the 'Status' column.
			 * 
			 * 'View ' link is displayed under the 'Action' column for the event
			 * created.
			 */
			// 650769

			/*
			 * STEP : Action:On 'Mobile View' web, navigate to Events>>< Event
			 * Name >>>Times screen Expected Result:Event 'EVE' is displayed on
			 * the 'Event List' screen.
			 */
			// 650770

			/*
			 * STEP : Action:On mobile device , navigate to Events>> Event Name
			 * Expected Result:Event 'EVE' is listed on 'Event List' screen.
			 */
			// 650771
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-118083";
			gstrTO = "Create an event selecting to start at a specified date and time and to end never and verify that the event starts at the specified time and does not have an end date.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

	//end//testBQS118083//
	
}
