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

import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class CreateStatTypeWithTimer {

	//Log4j object to write log entries to the Log files
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.features.CreateStatTypeWithTimer");
	
	static{
		BasicConfigurator.configure();
	}

	//Objects to access the common functions
	OfficeCommonFunctions objOFC;
		ReadData objrdExcel;

	/*Global variables to store the test case details – TestCaseID, Test Objective,Result,
	Reason for failure */
	String gstrTCID, gstrTO, gstrResult, gstrReason;

	//Selenium Object
	Selenium selenium,seleniumPrecondition;

	public Properties propElementDetails; //Property variable for ElementID file
	public Properties propEnvDetails;//Property variable for Environment data
	public Properties pathProps;
	public static Properties browserProps = new Properties();
	public static String gstrBrowserName;//Variable to store browser name
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;//Result Variables
	private String browser;
	double gdbTimeTaken; //Variable to store the time taken

	public static Date gdtStartDate;// Date variable

	@SuppressWarnings("unused")
	private String json;
	public static long sysDateTime;
	public static long gsysDateTime=0;
	public static String gstrTimeOut="";
	public static String sessionId_FF36,sessionId_FF20,sessionId_FF3,sessionId_FF35,sessionId_IE6,sessionId_IE7,
	    sessionId_IE8,session_SF3,session_SF4,session_op9,session_op10,session_GC,StrSessionId="",StrSessionId1,StrSessionId2;
	/****************************************************************************************************************
		* This function is called the setup() function which is executed before every test.
		*
		* The function will take care of creating a new selenium session for every test
		*
	****************************************************************************************************************/
	@Before
	public void setUp() throws Exception {

		//Create object to read environment properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		
		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails=objelementProp.ElementId_FilePath();
		
		//Retrieve browser information
		browser=propEnvDetails.getProperty("Browser");
		gstrBrowserName=propEnvDetails.getProperty("Browser").substring(1)+" "+propEnvDetails.getProperty("BrowserVersion");
		//Retrieve the value of page load time limit
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		
		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, this.browser, propEnvDetails.getProperty("urlEU"));	
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
		
		//Start Selenium RC
		selenium.start();
		//Maximize the window
		selenium.windowMaximize();
		selenium.setTimeout("");
		//Define object to call support functions to read excel, date etc
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();

	}
	/****************************************************************************************************************	* This function is called the teardown() function which is executed after every test.
		*
		* The function will take care of stopping the selenium session for every test and writing the execution
		* result of the test. 
		*
	****************************************************************************************************************/
	@After
	public void tearDown() throws Exception {
		// kill browser
		
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
		
		selenium.close();
		selenium.stop();
		// determine log message
		if (gstrResult.toUpperCase().equals("PASS"))
		{
			log4j.info("-------------------Test Case Execution " + gstrTCID
			+ " has PASSED------------------");
		} else if (gstrResult.toUpperCase().equals("SKIP"))
		{
			log4j.info("-------------------Test Case Execution " + gstrTCID
			+ " was SKIPPED------------------");
		} else
		{
			log4j.info("-------------------Test Case Execution " + gstrTCID
							+ " has FAILED------------------");
		}

		//Retrieve the path of the Result file
		String FILE_PATH = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		FILE_PATH = pathProps.getProperty("Resultpath");
		// Retrieve the total execution time
		gdbTimeTaken = objOFC.TimeTaken(gdtStartDate);
		Date_Time_settings dts = new Date_Time_settings();
		//Get the current date
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");
		//Get the Build ID of the application
		gstrBuild = propEnvDetails.getProperty("Build");
		//Check if result should be written to Excel or Test Management Tool
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");

		gstrReason=gstrReason.replaceAll("'", " ");
		//Write Result of the test.
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
						gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
						sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);

	}
	
	@Test
	public void testBQS69101() throws Exception {
		try {
			gstrTCID = "BQS-69101"; // Test Case Id
			gstrTO = "Verify that a timer can be associated with a number status type.";
																																// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
						
			String strFuncResult="";
			
			Login objLogin = new Login();// object of class Login
			StatusTypes objST=new StatusTypes();
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			CreateUsers objCreateUsers=new CreateUsers();
			Views objViews=new Views();
			Roles objRole=new Roles();
			
					
			String strHavBed="No";
			// login details
			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
		
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
			String strUpdateValue="0";
			
			String strStatusTypeValue="Number";
			String statTypeName="AutoNSt_"+strTimeText;
			String strStatTypDefn="Auto";
							
			String strResrctTypName="AutoRt_"+strTimeText;
			String strStatValue="";
			String strExpHr="00";
			String strExpMn="05";
			
			String strResource="AutoRs_"+strTimeText;
			String strAbbrv="Rs";
		
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strInitPwd=objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd=objrdExcel.readData("Login", 4, 2);
			String strUsrFulName="autouser";
			
			String strRoleValue="";
			
			String strViewName="AutoV_"+strTimeText;
			
			String strVewDescription="";
			String strViewType="Resource (Resources and status types as rows. Status, comments and timestamps as columns.)";
			
			String strSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
			
			String strResVal="";
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			strSTvalue[0]="";
			String strFILE_PATH1 = pathProps.getProperty("TestData_path");
		
			String strUpdTimeBef=objrdExcel.readInfoExcel("Timer", 2,
					2, strFILE_PATH1);
			String strUpdTimeAft=objrdExcel.readInfoExcel("Timer", 2,
					3, strFILE_PATH1);
						
			String strStatTypeAr[]={statTypeName};
			String strResArr[]={strResource};
			
			String strSnapTime="";
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strStatusTypeValue, statTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				seleniumPrecondition.click(propElementDetails.getProperty("StatusType.CreateStatType.Expire"));
				seleniumPrecondition.select(propElementDetails.getProperty("StatusType.CreateStatType.Expire.Hours"), "label="+strExpHr);
				seleniumPrecondition.select(propElementDetails.getProperty("StatusType.CreateStatType.Expire.Mins"), "label="+strExpMn);
				
				seleniumPrecondition.click(propElementDetails
						.getProperty("Save"));
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
						strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);
						if(strStatValue.compareTo("")!=0){
							strFuncResult="";
							strSTvalue[0]=strStatValue;
						}else{
							strFuncResult="Failed to fetch status type value";
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
						strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResrctTypName, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResrctTypName);
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
						strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition, strResource, strAbbrv, strResrctTypName, strStandResType, strContFName, strContLName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition, strResource);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition, strResource, strHavBed, "", strAbbrv, strResrctTypName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
						
						if(strResVal.compareTo("")!=0){
							strFuncResult="";
							strRSValue[0]=strResVal;
						}else{
							strFuncResult="Failed to fetch Resource value";
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
						strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleName);
						
						if(strRoleValue.compareTo("")!=0){
							strFuncResult="";
							
						}else{
							strFuncResult="Failed to fetch Role value";
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
						strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition, strResource, false, true, false, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"), true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}	
				
					
					try {
						assertEquals("", strFuncResult);
												
						strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition, strUserName,
								strByRole, strByResourceType, strByUserInfo, strNameFormat);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult =objViews.navRegionViewsList(seleniumPrecondition);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult =objViews.createView(seleniumPrecondition, strViewName, strVewDescription, strViewType, true, false, strSTvalue, false, strRSValue);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
							
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
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
						strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium, strResrctTypName, strResArr, strStatTypeAr);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.navStatusTypList(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.editStatusTypesMandFlds(selenium, statTypeName, "", "", false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}	
					
					try {
						assertEquals("", strFuncResult);
						selenium.select(propElementDetails.getProperty("StatusType.CreateStatType.TimerType"), "label=Count down to expiration, then stop counting");
						
						selenium.click(propElementDetails
								.getProperty("Save"));
						selenium.waitForPageToLoad(gstrTimeOut);
						
						try {
							assertEquals("Status Type List", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));
							log4j.info("Status Type List screen is displayed");
							
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews.navToUserView(selenium, strViewName);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews.updateStatusWithoutStatus(selenium, strResource, statTypeName,strSTvalue[0], strUpdateValue, "");
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);
								
								strSnapTime=selenium.getText("css=#statusTime");
								strUpdTimeBef=strSnapTime+strUpdTimeBef;
								strFuncResult = objViews.verifyLastUpdTimeInView(selenium, strUpdTimeBef, strResource,strUsrFulName,false);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);
								Thread.sleep(420000);
																
								strFuncResult = objViews.verifyLastUpdTimeInView(selenium, strUpdTimeAft, strResource,strUsrFulName,true);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);																								
								gstrResult="PASS";
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
						} catch (AssertionError Ae) {
							log4j.info("Status Type List screen is NOT displayed");
							gstrReason = "Status Type List screen is NOT displayed";						}	
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}				
					
				} catch (AssertionError Ae) {

					log4j
							.info("Status type "
									+ statTypeName
									+ " is created and is NOT listed in the "
									+ "'Status Type List' screen. ");

					gstrReason = "Status type "
							+ statTypeName
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
			gstrTCID = "BQS-69101"; // Test Case Id
			gstrTO = "Verify that a timer can be associated with a number status type.";
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
	
	@Test
	public void testBQS69147() throws Exception {
		try {
			gstrTCID = "BQS-69147"; // Test Case Id
			gstrTO = "Verify that a timer can be associated with a multi status type.";
																																// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
						
			String strFuncResult="";
			
			Login objLogin = new Login();// object of class Login
			StatusTypes objST=new StatusTypes();
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			CreateUsers objCreateUsers=new CreateUsers();
			Views objViews=new Views();
			Roles objRole=new Roles();
			
					
			String strHavBed="No";
			// login details
			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
		
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
		//	String strUpdateValue="0";
			
			String strStatusTypeValue="Multi";
			String statTypeName="AutoMSt_"+strTimeText;
			String strStatTypDefn="Auto";
							
			String strResrctTypName="AutoRt_"+strTimeText;
			String strStatValue="";
			String strExpHr="00";
			String strExpMn="05";
			
			String strResource="AutoRs_"+strTimeText;
			String strAbbrv="Rs";
		
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strInitPwd=objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd=objrdExcel.readData("Login", 4, 2);
			String strUsrFulName="autouser";
			
			String strRoleValue="";
			
			String strViewName="AutoV_"+strTimeText;
			
			String strVewDescription="";
			String strViewType="Resource (Resources and status types as rows. Status, comments and timestamps as columns.)";
			
			String strSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
			
			String strResVal="";
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			strSTvalue[0]="";
			String strFILE_PATH1 = pathProps.getProperty("TestData_path");
		
			String strUpdTimeBef=objrdExcel.readInfoExcel("Timer", 5,
					2, strFILE_PATH1);
			String strUpdTimeAft1=objrdExcel.readInfoExcel("Timer", 5,
					3, strFILE_PATH1);
			String strUpdTimeAft2=objrdExcel.readInfoExcel("Timer", 5,
					4, strFILE_PATH1);
			String strUpdTimeAft3=objrdExcel.readInfoExcel("Timer", 5,
					5, strFILE_PATH1);
						
			String strStatTypeAr[]={statTypeName};
			String strResArr[]={strResource};
			
			String strSnapTime="";
			String strStatusName1="Sta"+strTimeText;
			
			String strStatTypeColor="Black";
			String strStatusValue[]=new String[1];
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strStatusTypeValue, statTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
												
				seleniumPrecondition.click(propElementDetails
						.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);
				
								
				seleniumPrecondition
				.click("link=Return to Status Type List");
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
						strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);
						if(strStatValue.compareTo("")!=0){
							strFuncResult="";
							strSTvalue[0]=strStatValue;
						}else{
							strFuncResult="Failed to fetch status type value";
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeSTExpTime(seleniumPrecondition, statTypeName, strStatusName1, strStatusTypeValue, strStatTypeColor,strExpHr,strExpMn,"","", true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
								
															
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,statTypeName, strStatusName1);
						if(strStatValue.compareTo("")!=0){
							strFuncResult="";
							strStatusValue[0]=strStatValue;
						}else{
							strFuncResult="Failed to fetch status value";
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
						strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResrctTypName, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResrctTypName);
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
						strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition, strResource, strAbbrv, strResrctTypName, strStandResType, strContFName, strContLName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition, strResource);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition, strResource, strHavBed, "", strAbbrv, strResrctTypName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
						
						if(strResVal.compareTo("")!=0){
							strFuncResult="";
							strRSValue[0]=strResVal;
						}else{
							strFuncResult="Failed to fetch Resource value";
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
						strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleName);
						
						if(strRoleValue.compareTo("")!=0){
							strFuncResult="";
							
						}else{
							strFuncResult="Failed to fetch Role value";
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
						strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition, strResource, false, true, false, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"), true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}	
				
					
					try {
						assertEquals("", strFuncResult);
												
						strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition, strUserName,
								strByRole, strByResourceType, strByUserInfo, strNameFormat);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult =objViews.navRegionViewsList(seleniumPrecondition);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult =objViews.createView(seleniumPrecondition, strViewName, strVewDescription, strViewType, true, false, strSTvalue, false, strRSValue);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
							
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
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
						strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium, strResrctTypName, strResArr, strStatTypeAr);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.navStatusTypList(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.navToStatusList(selenium, statTypeName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}	
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.editStatusForST(selenium, strStatusName1);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}	
					
					try {
						assertEquals("", strFuncResult);
						selenium.select(propElementDetails.getProperty("StatusType.CreateStatType.TimerType"), "label=Count down to expiration, then count up");
						
						selenium.click(propElementDetails
								.getProperty("Save"));
						selenium.waitForPageToLoad(gstrTimeOut);
						
						try {
							assertEquals(
									"Status List for " + statTypeName + "",
									selenium.getText(propElementDetails
											.getProperty("Header.Text")));

							log4j.info("Status List for " + statTypeName
									+ "page is displayed");
						
					
							
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews.navToUserView(selenium, strViewName);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews.navToUpdateStatus(selenium, "//div[@id='mainContainer']/div/table/tbody/"
											+ "tr/td[2]/a[text()='"+strResource+"']/ancestor::tr/td[1]/a/img");
							} catch (AssertionError Ae) {
								gstrReason = gstrReason+" "+strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews.fillAndSavUpdateStatusMST(
										selenium, strStatusValue[0], strSTvalue[0],
										false, "", "");
							} catch (AssertionError Ae) {
								gstrReason = gstrReason + " " + strFuncResult;
							}
											
							try {
								assertEquals("", strFuncResult);
								selenium.click(propElementDetails
										.getProperty("UpdateStatus.Save"));
								selenium.waitForPageToLoad(gstrTimeOut);
								
								strFuncResult = objViews.verifyUpdateST(selenium, strResrctTypName, statTypeName, strStatusName1);
							} catch (AssertionError Ae) {
								gstrReason = gstrReason+" "+strFuncResult;
							}
							try {
								assertEquals("", strFuncResult);
								
								strSnapTime=selenium.getText("css=#statusTime");
								strUpdTimeBef=strSnapTime+strUpdTimeBef;
								strFuncResult = objViews.verifyLastUpdTimeInView(selenium, strUpdTimeBef, strResource,strUsrFulName,false);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);
								Thread.sleep(420000);
																
								strFuncResult = objViews.verifyLastUpdTimeInView(selenium, strUpdTimeAft1, strResource,strUsrFulName,true);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							try {
								assertEquals("", strFuncResult);
															
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
								strFuncResult = objViews.verifyLastUpdTimeInView(selenium, strUpdTimeAft2, strResource,strUsrFulName,true);
								try {
									assertEquals("", strFuncResult);																								
									
								} catch (AssertionError Ae2) {
									gstrReason = strFuncResult;
									strFuncResult = objViews.verifyLastUpdTimeInView(selenium, strUpdTimeAft3, strResource,strUsrFulName,true);
								}
								
							}
						
							try {
								assertEquals("", strFuncResult);																								
								gstrResult="PASS";
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
						} catch (AssertionError Ae) {

							gstrReason = "Status List for "
									+ statTypeName + " page is NOT displayed"
									+ Ae;
							log4j.info("Status Type List for " + statTypeName
									+ " page is NOT displayed" + Ae);
					
						} 
					}catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				} catch (AssertionError Ae) {

					log4j
							.info("Status type "
									+ statTypeName
									+ " is created and is NOT listed in the "
									+ "'Status Type List' screen. ");

					gstrReason = "Status type "
							+ statTypeName
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
			gstrTCID = "BQS-69147"; // Test Case Id
			gstrTO = "Verify that a timer can be associated with a multi status type.";
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
	@Test
	public void testBQS69148() throws Exception {
		try {
			gstrTCID = "BQS-69148"; // Test Case Id
			gstrTO = "Verify that a timer can be associated with a text status type.";
																																// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
						
			String strFuncResult="";
			
			Login objLogin = new Login();// object of class Login
			StatusTypes objST=new StatusTypes();
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			CreateUsers objCreateUsers=new CreateUsers();
			Views objViews=new Views();
			Roles objRole=new Roles();
			
					
			String strHavBed="No";
			// login details
			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
		
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
			String strUpdateValue="s";
			
			String strStatusTypeValue="Text";
			String statTypeName="AutoTSt_"+strTimeText;
			String strStatTypDefn="Auto";
							
			String strResrctTypName="AutoRt_"+strTimeText;
			String strStatValue="";
			String strExpHr="00";
			String strExpMn="05";
			
			String strResource="AutoRs_"+strTimeText;
			String strAbbrv="Rs";
		
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strInitPwd=objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd=objrdExcel.readData("Login", 4, 2);
			String strUsrFulName="autouser";
			
			String strRoleValue="";
			
			String strViewName="AutoV_"+strTimeText;
			
			String strVewDescription="";
			String strViewType="Resource (Resources and status types as rows. Status, comments and timestamps as columns.)";
			
			String strSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
			
			String strResVal="";
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			strSTvalue[0]="";
			String strFILE_PATH1 = pathProps.getProperty("TestData_path");
		
			String strUpdTimeBef=objrdExcel.readInfoExcel("Timer", 3,
					2, strFILE_PATH1);
			String strUpdTimeAft=objrdExcel.readInfoExcel("Timer", 3,
					3, strFILE_PATH1);
						
			String strStatTypeAr[]={statTypeName};
			String strResArr[]={strResource};
			
			String strSnapTime="";
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strStatusTypeValue, statTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				seleniumPrecondition.click(propElementDetails.getProperty("StatusType.CreateStatType.Expire"));
				seleniumPrecondition.select(propElementDetails.getProperty("StatusType.CreateStatType.Expire.Hours"), "label="+strExpHr);
				seleniumPrecondition.select(propElementDetails.getProperty("StatusType.CreateStatType.Expire.Mins"), "label="+strExpMn);
				
				seleniumPrecondition.click(propElementDetails
						.getProperty("Save"));
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
						strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);
						if(strStatValue.compareTo("")!=0){
							strFuncResult="";
							strSTvalue[0]=strStatValue;
						}else{
							strFuncResult="Failed to fetch status type value";
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
						strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResrctTypName, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResrctTypName);
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
						strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition, strResource, strAbbrv, strResrctTypName, strStandResType, strContFName, strContLName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition, strResource);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition, strResource, strHavBed, "", strAbbrv, strResrctTypName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
						
						if(strResVal.compareTo("")!=0){
							strFuncResult="";
							strRSValue[0]=strResVal;
						}else{
							strFuncResult="Failed to fetch Resource value";
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
						strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleName);
						
						if(strRoleValue.compareTo("")!=0){
							strFuncResult="";
							
						}else{
							strFuncResult="Failed to fetch Role value";
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
						strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition, strResource, false, true, false, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"), true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}	
				
					
					try {
						assertEquals("", strFuncResult);
												
						strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition, strUserName,
								strByRole, strByResourceType, strByUserInfo, strNameFormat);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult =objViews.navRegionViewsList(seleniumPrecondition);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult =objViews.createView(seleniumPrecondition, strViewName, strVewDescription, strViewType, true, false, strSTvalue, false, strRSValue);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
							
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
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
						strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium, strResrctTypName, strResArr, strStatTypeAr);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.navStatusTypList(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.editStatusTypesMandFlds(selenium, statTypeName, "", "", false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}	
					
					try {
						assertEquals("", strFuncResult);
						selenium.select(propElementDetails.getProperty("StatusType.CreateStatType.TimerType"), "label=Count up to expiration, then stop counting");
						
						selenium.click(propElementDetails
								.getProperty("Save"));
						selenium.waitForPageToLoad(gstrTimeOut);
						
						try {
							assertEquals("Status Type List", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));
							log4j.info("Status Type List screen is displayed");
							
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews.navToUserView(selenium, strViewName);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews.updateStatusWithoutStatus(selenium, strResource, statTypeName,strSTvalue[0], strUpdateValue, "");
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);
								
								strSnapTime=selenium.getText("css=#statusTime");
								strUpdTimeBef=strSnapTime+strUpdTimeBef;
								strFuncResult = objViews.verifyLastUpdTimeInView(selenium, strUpdTimeBef, strResource,strUsrFulName,false);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);
								Thread.sleep(420000);
																
								strFuncResult = objViews.verifyLastUpdTimeInView(selenium, strUpdTimeAft, strResource,strUsrFulName,true);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);																								
								gstrResult="PASS";
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
						} catch (AssertionError Ae) {
							log4j.info("Status Type List screen is NOT displayed");
							gstrReason = "Status Type List screen is NOT displayed";						}	
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}				
					
				} catch (AssertionError Ae) {

					log4j
							.info("Status type "
									+ statTypeName
									+ " is created and is NOT listed in the "
									+ "'Status Type List' screen. ");

					gstrReason = "Status type "
							+ statTypeName
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
			gstrTCID = "BQS-69148"; // Test Case Id
			gstrTO = "Verify that a timer can be associated with a text status type.";
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
	
	@Test
	public void testBQS69149() throws Exception {
		try {
			gstrTCID = "BQS-69149"; // Test Case Id
			gstrTO = "Verify that a timer can be associated with a saturation score status type.";
																																// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
						
			String strFuncResult="";
			
			Login objLogin = new Login();// object of class Login
			StatusTypes objST=new StatusTypes();
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			CreateUsers objCreateUsers=new CreateUsers();
			Views objViews=new Views();
			Roles objRole=new Roles();
			
					
			String strHavBed="No";
			// login details
			String strLoginUserName = objrdExcel.readData("Login", 3, 1);
			String strLoginPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
		
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
			String strUpdateValue="393";
			String strScUpdValue[]={"0","1","2","3","4","5","6","7","8"};
			
			String strStatusTypeValue="Saturation Score";
			String statTypeName="AutoSSt_"+strTimeText;
			String strStatTypDefn="Auto";
							
			String strResrctTypName="AutoRt_"+strTimeText;
			String strStatValue="";
			String strExpHr="00";
			String strExpMn="05";
			
			String strResource="AutoRs_"+strTimeText;
			String strAbbrv="Rs";
		
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strInitPwd=objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd=objrdExcel.readData("Login", 4, 2);
			String strUsrFulName="autouser";
			
			String strRoleValue="";
			
			String strViewName="AutoV_"+strTimeText;
			
			String strVewDescription="";
			String strViewType="Resource (Resources and status types as rows. Status, comments and timestamps as columns.)";
			
			String strSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
			
			String strResVal="";
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			strSTvalue[0]="";
			String strFILE_PATH1 = pathProps.getProperty("TestData_path");
		
			String strUpdTimeBef=objrdExcel.readInfoExcel("Timer", 4,
					2, strFILE_PATH1);
			String strUpdTimeAft1=objrdExcel.readInfoExcel("Timer", 4,
					3, strFILE_PATH1);
			String strUpdTimeAft2=objrdExcel.readInfoExcel("Timer", 4,
					4, strFILE_PATH1);
			String strUpdTimeAft3=objrdExcel.readInfoExcel("Timer", 4,
					5, strFILE_PATH1);
						
			String strStatTypeAr[]={statTypeName};
			String strResArr[]={strResource};
			
			String strSnapTime="";
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strStatusTypeValue, statTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				seleniumPrecondition.click(propElementDetails.getProperty("StatusType.CreateStatType.Expire"));
				seleniumPrecondition.select(propElementDetails.getProperty("StatusType.CreateStatType.Expire.Hours"), "label="+strExpHr);
				seleniumPrecondition.select(propElementDetails.getProperty("StatusType.CreateStatType.Expire.Mins"), "label="+strExpMn);
				
				seleniumPrecondition.click(propElementDetails
						.getProperty("Save"));
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
						strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);
						if(strStatValue.compareTo("")!=0){
							strFuncResult="";
							strSTvalue[0]=strStatValue;
						}else{
							strFuncResult="Failed to fetch status type value";
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
						strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResrctTypName, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResrctTypName);
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
						strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition, strResource, strAbbrv, strResrctTypName, strStandResType, strContFName, strContLName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition, strResource);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition, strResource, strHavBed, "", strAbbrv, strResrctTypName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
						
						if(strResVal.compareTo("")!=0){
							strFuncResult="";
							strRSValue[0]=strResVal;
						}else{
							strFuncResult="Failed to fetch Resource value";
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
						strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleName);
						
						if(strRoleValue.compareTo("")!=0){
							strFuncResult="";
							
						}else{
							strFuncResult="Failed to fetch Role value";
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
						strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition, strResource, false, true, false, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"), true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}	
				
					
					try {
						assertEquals("", strFuncResult);
												
						strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition, strUserName,
								strByRole, strByResourceType, strByUserInfo, strNameFormat);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult =objViews.navRegionViewsList(seleniumPrecondition);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult =objViews.createView(seleniumPrecondition, strViewName, strVewDescription, strViewType, true, false, strSTvalue, false, strRSValue);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
							
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
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
						strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium, strResrctTypName, strResArr, strStatTypeAr);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.navStatusTypList(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.editStatusTypesMandFlds(selenium, statTypeName, "", "", false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}	
					
					try {
						assertEquals("", strFuncResult);
						selenium.select(propElementDetails.getProperty("StatusType.CreateStatType.TimerType"), "label=Count up to expiration, then start counting up again from zero");
						
						selenium.click(propElementDetails
								.getProperty("Save"));
						selenium.waitForPageToLoad(gstrTimeOut);
						
						try {
							assertEquals("Status Type List", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));
							log4j.info("Status Type List screen is displayed");
							
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews.navToUserView(selenium, strViewName);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews.navToUpdateStatus(selenium, "//div[@id='mainContainer']/div/table/tbody/"
											+ "tr/td[2]/a[text()='"+strResource+"']/ancestor::tr/td[1]/a/img");
							} catch (AssertionError Ae) {
								gstrReason = gstrReason+" "+strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium, strScUpdValue, strSTvalue[0]);
							} catch (AssertionError Ae) {
								gstrReason = gstrReason+" "+strFuncResult;
							}
							try {
								assertEquals("", strFuncResult);
								selenium.click(propElementDetails
										.getProperty("UpdateStatus.Save"));
								selenium.waitForPageToLoad(gstrTimeOut);
								
								strFuncResult = objViews.verifyUpdateST(selenium, strResrctTypName, statTypeName, strUpdateValue);
							} catch (AssertionError Ae) {
								gstrReason = gstrReason+" "+strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);
								
								strSnapTime=selenium.getText("css=#statusTime");
								strUpdTimeBef=strSnapTime+strUpdTimeBef;
								strFuncResult = objViews.verifyLastUpdTimeInView(selenium, strUpdTimeBef, strResource,strUsrFulName,false);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
							try {
								assertEquals("", strFuncResult);
								Thread.sleep(420000);
																
								strFuncResult = objViews.verifyLastUpdTimeInView(selenium, strUpdTimeAft1, strResource,strUsrFulName,true);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							try {
								assertEquals("", strFuncResult);
															
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
								strFuncResult = objViews.verifyLastUpdTimeInView(selenium, strUpdTimeAft2, strResource,strUsrFulName,true);
								try {
									assertEquals("", strFuncResult);																								
									
								} catch (AssertionError Ae2) {
									gstrReason = strFuncResult;
									strFuncResult = objViews.verifyLastUpdTimeInView(selenium, strUpdTimeAft3, strResource,strUsrFulName,true);
								}
								
							}
						
							
							try {
								assertEquals("", strFuncResult);																								
								gstrResult="PASS";
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
						} catch (AssertionError Ae) {
							log4j.info("Status Type List screen is NOT displayed");
							gstrReason = "Status Type List screen is NOT displayed";						}	
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}				
					
				} catch (AssertionError Ae) {

					log4j
							.info("Status type "
									+ statTypeName
									+ " is created and is NOT listed in the "
									+ "'Status Type List' screen. ");

					gstrReason = "Status type "
							+ statTypeName
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
			gstrTCID = "BQS-69149"; // Test Case Id
			gstrTO = "Verify that a timer can be associated with a saturation score status type.";
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
