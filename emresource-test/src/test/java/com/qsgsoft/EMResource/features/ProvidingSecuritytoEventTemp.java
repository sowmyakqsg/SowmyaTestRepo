package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

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
import com.qsgsoft.EMResource.shared.Login;

import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
/**
'*************************************************************
' Description: The class ProvidingSecuritytoEventTemp contains the test case to verify security can be provided for an event template.

' Precondition:
' Functions: testBQS68109
' Date: 19-Apr-2012
' Author:QSG
'---------------------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*************************************************************
*/
public class ProvidingSecuritytoEventTemp {

	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.ProvidingSecuritytoEventTemp");
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
/*	@Test
	
	public void testPreCond_68109() throws Exception{
		try{
			gstrTCID = "Precond-68109";			
			gstrTO = "Create two users";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("hh:mm:ss");
			

			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			ReadData objReadData = new ReadData ();
			CreateUsers objUser=new CreateUsers();
			SearchUserByDiffCrteria objSearch=new SearchUserByDiffCrteria();
			
			
			String strFILE_PATH=pathProps.getProperty("TestData_path");
						
			String strUserName = objReadData.ReadData("Login", 3, 1);
			String strPassword =objReadData.ReadData("Login", 3, 2);
			String strRegion=objReadData.ReadData("Login", 3, 4);
			
			String strNewUsr1=objReadData.readInfoExcel("Precondition", 10, 2, strFILE_PATH);			
			String strUsrPwd=objReadData.readInfoExcel("Precondition", 10, 4, strFILE_PATH);
			
			String strUsrFulName=objReadData.readInfoExcel("Precondition", 10, 3, strFILE_PATH);
			String strUsrRole=objReadData.readInfoExcel("Precondition", 10, 9, strFILE_PATH);
			String strResType=objReadData.readInfoExcel("Precondition", 10, 10, strFILE_PATH);
			String strUsrInfo=objReadData.readInfoExcel("Precondition", 10, 11, strFILE_PATH);
			String strNameFormat=objReadData.readInfoExcel("Precondition", 10, 12, strFILE_PATH);
			
			Login objLogin = new Login();// object of class Login
			//login to application
			selenium.open(propEnvDetails.getProperty("urlRel"));
			String strFailMsg = objLogin.login(selenium, strUserName,
						strPassword);				
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//navigate to user region
				strFailMsg=objLogin.navUserDefaultRgn(selenium, strRegion);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//navigate to user list page
				strFailMsg=objUser.navUserListPge(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
						
			try {
				assertTrue(strFailMsg.equals(""));	
				//search for the user
				strFailMsg=objSearch.searchUserByDifCriteria(selenium, strNewUsr1,strUsrRole , strResType, strUsrInfo, strNameFormat);
				
				try{
					assertTrue(selenium.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2][text()='"+strNewUsr1+"']"));
					log4j.info("User "+strNewUsr1+" is already present");
					gstrResult="PASS";
				} catch (AssertionError Aee) {
					log4j.info("User "+strNewUsr1+" is NOT present");
					//create the user if it is not present
					try {
						assertTrue(strFailMsg.equals(""));	
						//enter the mandatory fields
						strFailMsg=objUser.fillUsrMandatoryFlds(selenium, strNewUsr1, strUsrPwd, strUsrPwd, strUsrFulName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));	
						//select the advanced options
						strFailMsg=objUser.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));						
						strFailMsg=objUser.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));	
						//save the user data and check user is created
						strFailMsg=objUser.savVrfyUser(selenium, strNewUsr1);
						
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
									
					try {
						assertTrue(strFailMsg.equals(""));	
						strFailMsg = objLogin.logout(selenium);	
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));	
						strFailMsg = objLogin.newUsrLogin(selenium, strNewUsr1,
								strUsrPwd);							
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					try {
						assertTrue(strFailMsg.equals(""));	
						gstrResult="PASS";		
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;				
			}
									
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "Precond-68109";
			gstrTO = "Create two users";
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
	}*/
	/**
	'*************************************************************
	' Description: Verify that security can be provided for an event template
	' Precondition: User A and User B are created with 'Maintain Event Templates' and 'Maintain Events' rights 

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
	public void testBQS68109()throws Exception{
		try{
			gstrTCID = "68109";			
			gstrTO = "Verify that security can be provided for an event template";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");
			
			Login objLogin = new Login();// object of class Login
			EventSetup objEve=new EventSetup();
			CreateUsers objUser=new CreateUsers();
			ReadData objReadData = new ReadData (); 	
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			EventNotification objNotif=new EventNotification();
			
			String strFILE_PATH=pathProps.getProperty("TestData_path");
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			//fetch the required test data from excel
			String strEveTemp=objReadData.readInfoExcel("Event Temp Data", 2, 2, strFILE_PATH);
			strEveTemp=strEveTemp+strTimeText;
			
			String strTempDef=objReadData.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objReadData.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objReadData.readInfoExcel("Event Temp Data", 2, 5, strFILE_PATH);
			String strIconSrc=objReadData.readInfoExcel("Event Temp Data", 2, 6, strFILE_PATH);
					
			String []strResTypeArr={};
			String []strStatTypeArr={};
			
			int intResCnt=0;
			String strInfo="This is an automation event";
			String strEveName="AutoEve_"+strTimeText;
			String strEditEveName="AutoEdEv_"+strTimeText;
			String strLoginUserName1 = "AutoUsra"+System.currentTimeMillis();
			String strLoginPassword1 ="abc123";
			String strFullUserName1="Full"+strLoginUserName1;
			
			String strLoginUserName2 = "AutoUsrb"+System.currentTimeMillis();
			String strLoginPassword2 ="abc123";
			String strFullUserName2="Full"+strLoginUserName2;
			
			String[] strUserNameCheck={strLoginUserName1,strLoginUserName2};
			String[] strUserNameSel={strLoginUserName1};
			
					
			// login details
			String strAdmUserName = objReadData.readData("Login", 3, 1);
			String strAdmPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);		
			
			String strFailMsg = objLogin.login(selenium,strAdmUserName,
					strAdmPassword);

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
				strFailMsg = objUser.fillUsrMandatoryFlds(selenium, strLoginUserName2, strLoginPassword2, strLoginPassword2, strFullUserName2);
				
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
								
				strFailMsg = objUser.savVrfyUser(selenium, strLoginUserName2);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * Step 2: Login as user A, navigate to Event >> Event Setup. <-> 'Event Template List' screen is displayed. 
			 */
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.newUsrLogin(selenium, strLoginUserName1, strLoginPassword1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
					
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg="'Event Template List' screen is NOT displayed.";
				//navigate to Event Setup
				assertTrue(objEve.navToEventSetup(selenium));	
				strFailMsg="";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
		
			/*
			 * Step 3: Create an event template ET selecting 'Security' option. <->	The template is listed in the event template list screen
			 */
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
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(selenium, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIconSrc,"","","","",false,strResTypeArr,strStatTypeArr,true,true,false);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			/*
			 * Step 4: Click on 'Security' option associated with 'ET'. <->	Event Security for < Event template name >' screen is displayed
			 * <-> User A and user B are displayed. 
			 * Step 5: Select User A and click on 'Save'. <-> 'Event Template List' screen is displayed. 
			 */
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg=objEve.selectEventSecurityforUser(selenium, strEveTemp, strUserNameCheck, strUserNameSel);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			/*
			 * Step 6: 	Navigate to Event>>Event Management , click on 'Create New Event' button. <->	Event Template 'ET' is displayed in 'Select Event Template' screen
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
			 * Step 7: Click on 'Create' associated with ET <->	'Create New Event' screen is displayed
			 * Step 8: 	Create an event EVE. <-> Event 'EVE' is listed in the 'Event List' screen
			 */
			try {
				assertTrue(strFailMsg.equals(""));
								
				strFailMsg=objEve.createEventMandFlds(selenium, strEveTemp, strEveName, strInfo, true);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));								
				strFailMsg=objNotif.acknNotification(selenium);
				selenium.selectWindow("");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
			
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEveName + "']"));
					log4j
							.info("Event '"
									+ strEveName
									+ "' is listed on 'Event Management' screen.");
					
					try {
						assertTrue(strFailMsg.equals(""));				
						strFailMsg=objLogin.logout(selenium);					
										
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}		
				
					/*
					 * Step 9: Login as a user B and navigate to Event>>Event Management <-> 'Event Management' screen is displayed.
					 * <-> Edit' and 'End' links are not available with event 'EVE
					 */
					try {
						assertTrue(strFailMsg.equals(""));		
						strFailMsg = objLogin.newUsrLogin(selenium, strLoginUserName2,
								strLoginPassword2);
						
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}	
					
					try {
						assertTrue(strFailMsg.equals(""));
						//selenium.selectFrame("Data");
						strFailMsg=objNotif.acknNotification(selenium);
						selenium.selectWindow("");
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
						try{
							  assertFalse( selenium
							     .isElementPresent("//div[@id='mainContainer']/table[@class='displayTable" +
							       " striped border sortable']/tbody/tr/td[6][text()=" +
							       "'"+strEveName+"']/parent::tr/td[1]/a[text()='Edit']"));
		
							  assertFalse(  selenium
							     .isElementPresent("//div[@id='mainContainer']/table[@class='displayTable" +
							       " striped border sortable']/tbody/tr/td[6][text()=" +
							       "'"+strEveName+"']/parent::tr/td[1]/a[text()='End']"));
							  log4j.info("'Edit' and 'End' links are not available with event 'EVE'.");
							  intResCnt++;
						} catch (AssertionError ae) {
							 log4j.info("'Edit' and 'End' links are available with event 'EVE'.");
							 gstrReason="'Edit' and 'End' links are available with event 'EVE'.";
						}
					   
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));	
						/*
						 * Step 10: 	Click on 'Create New Event' <->	Event Template 'ET' is NOT displayed in 'Select Event Template' screen
						 */
						// Click on Create New Event
						selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
						selenium.waitForPageToLoad(gstrTimeOut);
						
						try {
							assertEquals("Select Event Template", selenium
									.getText("css=h1"));
							
							// Check Event Template is displayed
							assertFalse(selenium
									.isElementPresent("//div[@id='mainContainer']/table[@class='displayTable"
											+ " striped border sortable']/tbody/tr/td[text()='"
											+ strEveTemp
											+ "']"));
							log4j.info("Event Template "+strEveTemp+" is NOT displayed in 'Select Event Template' screen");
							intResCnt++;
						} catch (AssertionError ae) {
							log4j.info("Event Template "+strEveTemp+" is still displayed in 'Select Event Template' screen");
							
							gstrReason = "Event Template "+strEveTemp+" is still displayed in 'Select Event Template' screen";
						}
						
										   
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
					 * Step 11: Login as user A and navigate to Event>>Event Management <->	'Event Management' screen is displayed
					 */
					try {
						assertTrue(strFailMsg.equals(""));		
						strFailMsg = objLogin.login(selenium, strLoginUserName1,
								strLoginPassword1);
						
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
					 * Step 12: Click on 'Edit' and edit the event and save <->	The changes are updated in the 'Event List' screen
					 */
					try {
						assertTrue(strFailMsg.equals(""));
										
						strFailMsg=objEve.editEvent(selenium, strEveName, strEditEveName, strInfo, true);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));								
						strFailMsg=objNotif.acknNotification(selenium);
						selenium.selectWindow("");
						selenium.selectFrame("Data");
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					/*
					 * Step 13: Click on 'End' and click on 'Ok' in the Confirmation window <-> The event is ended
					 */
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg=objEve.endEvent(selenium, strEditEveName);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));								
						strFailMsg=objNotif.acknNotification(selenium);
						selenium.selectWindow("");
						selenium.selectFrame("Data");
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));
					
						if(intResCnt==2)
							gstrResult="PASS";
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
				} catch (AssertionError ae) {
					log4j
							.info("Event '"
									+ strEveName
									+ "' is NOT listed on 'Event Management' screen.");
					gstrReason = "Event '"
							+ strEveName
							+ "' is NOT listed on 'Event Management' screen.";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}			
		
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "68109";
			gstrTO = "Verify that security can be provided for an event template";
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
