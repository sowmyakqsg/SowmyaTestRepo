package com.qsgsoft.EMResource.features;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.EventList;
import com.qsgsoft.EMResource.shared.EventSetup;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/**********************************************************************
' Description :This class includes test case to check in Event List
' Precondition:
' Functions: testBQS68339()
' Date		  :17-April-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class EventLists {

	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.EventLists");
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
	
	private String browser;
		
	private String json;
	public static long sysDateTime;	
	public static long gsysDateTime=0;
	public static String sessionId_FF36,sessionId_FF20,sessionId_FF3,sessionId_FF35,sessionId_IE6,sessionId_IE7,
    sessionId_IE8,session_SF3,session_SF4,session_op9,session_op10,session_GC,StrSessionId="",StrSessionId1,StrSessionId2;
	public static String gstrTimeOut="";
	
	@Before
	public void setUp() throws Exception {
		
		
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
					
		browser=propEnvDetails.getProperty("Browser");
		gstrBrowserName=propEnvDetails.getProperty("Browser").substring(1)+" "+propEnvDetails.getProperty("BrowserVersion");
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		
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

		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);
	
	}
	
	/***********************************************************************
	'Description	:Verify that events are listed in the 'Events List' screen for all users.
	'Precondition	:1. User A is created providing the following rights;
					a. 'Maintain Events' right
					b. 'Maintain Event Templates' right
					
					2. User B is created WITHOUT providing the following rights;
					a. 'Maintain Events' right
					b. 'Maintain Event Templates' right
					
					3. Event template ET is created providing mandatory data.
					4. Event EVE is created selecting ET. 
	'Arguments		:void
	'Returns		:void
	'Date	 		:17-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Name>                              		<Name>
	************************************************************************/
	
	@Test
	public void testBQS68339() throws Exception{
		try{
			gstrTCID = "68339";			
			gstrTO = "Verify that events are listed in the 'Events List' screen for all users.";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");
			CreateUsers objUser=new CreateUsers();
			ReadData objReadData = new ReadData (); 
			
			String strLoginUserName1 = "AutoUsra"+System.currentTimeMillis();
			String strLoginPassword1 = "abc123";
			
			String strLoginUserName2 ="AutoUsrb"+System.currentTimeMillis();
			String strLoginPassword2 = "abc123";
			
			String strFullUserName1="Full"+strLoginUserName1;
			String strFullUserName2="Full"+strLoginUserName2;
			
			Login objLogin = new Login();// object of class Login
			EventSetup objEve=new EventSetup();
			EventList objList=new EventList();
			
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			
			String strFILE_PATH=pathProps.getProperty("TestData_path");
			//fetch the required test data from excel
			String strEventName="AutoEv"+System.currentTimeMillis();
			String strEveTempName="AutoET_"+System.currentTimeMillis();
						
			String strAction="View";
			String strIcon=objReadData.readInfoExcel("Event Temp Data", 4, 6, strFILE_PATH);
			String strStatus="Ongoing";
			String strStartDate="";
			String strEndDate="";
			String strDrill="No";
			String strInfo="auto event";			
			
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
			
			String[] strHeader={"Action","Icon","Status","Start","End","Title","Drill","Template","Information"};
			
			String strResTypeArr[]={};
			String strStatTypeArr[]={};
			String strTempDef=objReadData.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objReadData.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objReadData.readInfoExcel("Event Temp Data", 2, 5, strFILE_PATH);
			String strIconSrc=objReadData.readInfoExcel("Event Temp Data", 2, 6, strFILE_PATH);
			
			// login details
			String strUserName = objReadData.readData("Login", 3, 1);
			String strPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);		
			
			String strFailMsg = objLogin.login(selenium,strUserName,
					strPassword);

			try {
				assertEquals("", strFailMsg);
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
				strFailMsg = objUser.fillUsrMandatoryFlds(selenium, strLoginUserName1, strLoginPassword1, strLoginPassword1, strFullUserName1);
				
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
								
				strFailMsg = objUser.savVrfyUser(selenium, strLoginUserName1);
				
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
				strFailMsg = objUser.fillUsrMandatoryFlds(selenium, strLoginUserName2, strLoginPassword2, strLoginPassword2, strFullUserName2);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
								
				strFailMsg = objUser.savVrfyUser(selenium, strLoginUserName2);
				
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
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(selenium, strEveTempName, strTempDef, strEveColor, strAsscIcon, strIconSrc,"","","","",true,strResTypeArr,strStatTypeArr,true,false,false);
								
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
			
			try {
				assertTrue(strFailMsg.equals(""));	
											
				strFailMsg=objEve.createEventMandFlds(selenium, strEveTempName, strEventName, strInfo,false);
				
				//get Start Date
				 strFndStMnth=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartMnt"));
				 strFndStDay=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartDay"));
				 strFndStYear=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartYear"));
				
				//get Start Time
				 strFndStHr=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartHour"));
				 strFndStMinu=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.StartMinut"));
				
				//get End Date
				 strFndEdMnth=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.EndMnt"));
				 strFndEdDay=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.EndDay"));
				 strFndEdYear=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.EndYear"));
				
				//get End Time
				 strFndEdHr=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.EndHour"));
				 strFndEdMinu=selenium.getSelectedLabel(propElementDetails.getProperty("Event.CreateEve.EndMinut"));
				 
				//click on save
				selenium.click(propElementDetails.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				log4j.info(strEventName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			/*
			 * Step 2: 	Login as user A and navigate to Event >> Event List. <-> Event EVE is listed on the 'Event List' screen along with the other active events created in the region.
	  			Event EVE is listed with all the correct data under the following columns:

			1. Action
			2. Icon
			3. Status
			4. Start
			5. End
			6. Title
			7. Drill
			8. Template
			9. Information 
			 */
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objLogin.newUsrLogin(selenium, strLoginUserName1,
						strLoginPassword1);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}			
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg="Event List screen is NOT displayed";
				assertTrue(objList.navToEventList(selenium));	
				strFailMsg="";
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			
			try {
				assertTrue(strFailMsg.equals(""));
								
				strStartDate=dts.converDateFormat(strFndStYear+strFndStMnth+strFndStDay,"yyyyMMMdd","yyyy-MM-dd");
				strStartDate=strStartDate+" "+strFndStHr+":"+strFndStMinu;
				strEndDate=dts.converDateFormat(strFndEdYear+strFndEdMnth+strFndEdDay,"yyyyMMMdd","yyyy-MM-dd");
				strEndDate=strEndDate+" "+strFndEdHr+":"+strFndEdMinu;
				
				String[] strData={strAction,strIcon,strStatus,strStartDate,strEndDate,strEventName,strDrill,strEveTempName,strInfo};
				
				for(int intRec=0;intRec<strHeader.length;intRec++){
					strFailMsg=objList.checkDataInEventListTable(selenium, strHeader[intRec], strEventName, strData[intRec], String.valueOf(intRec+1));
					try{
						assertTrue(strFailMsg.equals(""));						
					}catch(AssertionError ae){
						gstrReason = gstrReason+" "+strFailMsg;
					}
				}
				
				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg=objLogin.logout(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
				try {
					assertTrue(strFailMsg.equals(""));
					 strFailMsg = objLogin.newUsrLogin(selenium, strLoginUserName2,
								strLoginPassword2);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				/*
				 * Step 3: 	Login as user B and navigate to Event >> Event List. <-> Event EVE is listed on the 'Event List' screen along with the other active events created in the region.
					Event EVE is listed with all the correct data under the following columns:
					
					1. Action
					2. Icon
					3. Status
					4. Start
					5. End
					6. Title
					7. Drill
					8. Template
					9. Information
				 */
				
				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg="Event List screen is NOT displayed";
					assertTrue(objList.navToEventList(selenium));	
					strFailMsg="";
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				
					
				try {
					assertTrue(strFailMsg.equals(""));
					
					for(int intRec=0;intRec<strHeader.length;intRec++){
						strFailMsg=objList.checkDataInEventListTable(selenium, strHeader[intRec], strEventName, strData[intRec], String.valueOf(intRec+1));
						try{
							assertTrue(strFailMsg.equals(""));						
						}catch(AssertionError ae){
							gstrReason = gstrReason+" "+strFailMsg;
						}
					}
					
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				
				if(gstrReason.equals("")){
					gstrResult="PASS";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "68339";
			gstrTO = "Verify that events are listed in the 'Events List' screen for all users.";
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
