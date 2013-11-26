package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/********************************************************************
' Description		:This class contains test cases from requirement
' Requirement		:Status Reason Detail Report
' Requirement Group	:Reports
' Product		    :EMResource v3.17
' Date			    :06-07-2012
' Author		    :QSG
---------------------------------------------------------------------
' Modified Date				                            Modified By
' Date					                                Name
'*******************************************************************/

@SuppressWarnings("unused")
public class StatusReasonDetailRep {

	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.StatusReasonDetailRep");
	static {
		BasicConfigurator.configure();
	}
	String gstrTCID, gstrTO, gstrResult, gstrReason;
	Selenium selenium,seleniumPrecondition;
	double gdbTimeTaken;
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	public static Date gdtStartDate;
	public Properties propElementDetails, propPathDetails;
	public static String gstrBrowserName;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;
	public Properties propEnvDetails, propAutoItDetails, pathProps;
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

		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));

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
	
	/***************************************************************
	'Description	:Add a multi status type MST back to resource RS with the status reason and update the status value of RS, generate the �Status Reason Detail Report� and verify that the data is displayed appropriately in the reportAdd a multi status type MST back to resource RS with the status reason and update the status value of RS, generate the �Status Reason Detail Report� and verify that the data is displayed appropriately in the report
	'Arguments		:None
	'Returns		:None
	'Date			:09-07-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	/*@Test
	public void testBQS88954() throws Exception {
		try {
			gstrTCID = "BQS-88954"; // Test Case Id
			gstrTO = "Add a multi status type MST back to resource RS with the status reason and update the status value of RS, generate the �Status Reason Detail Report� and verify that the data is displayed appropriately in the reportAdd a multi status type MST back to resource RS with the status reason and update the status value of RS, generate the �Status Reason Detail Report� and verify that the data is displayed appropriately in the report";
																																// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			Paths_Properties objAP = new Paths_Properties();
			propPathDetails = objAP.Read_FilePath();
					
			propAutoItDetails=objAP.ReadAutoit_FilePath();
			String strTestData[]=new String[10];
			CSVFunctions objCSV=new CSVFunctions();
	
			General objGen=new General();
			StatusReason objStatRes=new StatusReason();
			StatusTypes objST=new StatusTypes();
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			CreateUsers objCreateUsers=new CreateUsers();
			
			Roles objRole=new Roles();
			
			String strFuncResult="";
		
			String strSysExtrIp="";
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login
		
			ViewMap objViewMap=new ViewMap();
			Reports objRep=new Reports();
				
			String strUpdMulValueBef="";
			String strUpdValCheckBef="";
			
			String strUpdMulValueAft="";
			String strUpdValCheckAft="";
			// login details
			String strAdmUserName = objrdExcel.ReadData("Login", 3, 1);
			String strAdmPassword = objrdExcel.ReadData("Login", 3, 2);
			String strRegn=objrdExcel.ReadData("Login", 3, 4);
			
			String strStatusTypeValue="Multi";
			String strStatTypeAsgnVal="Number";
			String strStatTypeAsgn="AutoNst_"+strTimeText;
			
			String statTypeName="AutoMSt_"+strTimeText;
			String strStatTypDefn="Auto";
			String strStatTypeColor="Black";
			
			String strAbbrv="Rs";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String strRoleValue="";
						
			String strUsrFulName="autouser";
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strUsrPassword="abc123";
						
				
			String strState="Alabama";
			String strCountry="Autauga County";
					
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strResource="AutoRs_"+strTimeText;
			String strResType="AutoRt_"+strTimeText;	
							
			String strSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
		
			String strSTAsnVal[]=new String[1];
			String strStatusValue[]=new String[2];
			strStatusValue[0]="";
			strStatusValue[1]="";
			
			strSTvalue[0]="";	
			strRSValue[0]="";
			
			String strGenDate="";
			String strAutoFilePath=propAutoItDetails.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath=propPathDetails.getProperty("Reports_DownloadCSV_Path")+"StatReasDetRep_"+gstrTCID+"_"+strTimeText+".csv";
			String strPDFDownlPath=propPathDetails.getProperty("Reports_DownloadCSV_Path")+"StatReasDetRep_"+gstrTCID+"_"+strTimeText+".pdf";
			String strAutoFileName=propAutoItDetails.getProperty("Reports_DownloadFile_Name");
			String strCurrDate=dts.getCurrentDate("MM/dd/yyyy");
					
			String strReasonName="AutoReas_"+System.currentTimeMillis();
			String strReasonVal="";
			String strRegGenTime="";
			String strCurYear=dts.getCurrentDate("yyyy");
			String strLastUpdDateBef="";
			String strLastUpdTimeBef="";
			
			String strLastUpdDateAft="";
			String strLastUpdTimeAft="";
			
			String strEndDateBef="";
			String strEndTimeBef="";
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,strAdmUserName,
						strAdmPassword);
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
				strFuncResult = objStatRes.navStatusReasonList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(selenium, strReasonName, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strReasonVal=objStatRes.fetchStatReasonValue(selenium, strReasonName);
				if(strReasonVal.compareTo("")!=0){
					strFuncResult="";
					
				}else{
					strFuncResult="Failed to fetch status value";
				}
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium, strStatTypeAsgnVal, strStatTypeAsgn, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(selenium, strStatTypeAsgn);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strSTAsnVal[0]=strStatValue;
				}else{
					strFuncResult="Failed to fetch status type value";
				}
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium, strStatusTypeValue, statTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(selenium, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
								
				selenium
				.click("link=Return to Status Type List");
				selenium.waitForPageToLoad(gstrTimeOut);
								
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeName + "']"));
	
					log4j.info("Status type " + statTypeName
							+ " is created and is listed in the "
								+ "'Status Type List' screen. ");		
					
					try {
						assertEquals("", strFuncResult);
						String strStatValue = objST.fetchSTValueInStatTypeList(selenium, statTypeName);
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
						strFuncResult = objST.createSTWithinMultiTypeST(selenium, statTypeName, strStatusName1, strStatusTypeValue, strStatTypeColor,false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
								
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(selenium, strReasonVal, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(selenium, statTypeName, strStatusName1);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(selenium, statTypeName, strStatusName2, strStatusTypeValue, strStatTypeColor,false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(selenium, strReasonVal, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(selenium, statTypeName, strStatusName2);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						String strStatValue = objST.fetchStatValInStatusList(selenium,statTypeName, strStatusName1);
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
						String strStatValue = objST.fetchStatValInStatusList(selenium,statTypeName, strStatusName2);
						if(strStatValue.compareTo("")!=0){
							strFuncResult="";
							strStatusValue[1]=strStatValue;
						}else{
							strFuncResult="Failed to fetch status value";
						}
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.navResourceTypList(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.resrcTypeMandatoryFlds(selenium, strResType, "css=input[name='statusTypeID'][value='"+strSTAsnVal[0]+"']");
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
		
				
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.saveAndvrfyResType(selenium, strResType);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.navResourcesList(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
							
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.createResourceWitLookUPadres(selenium, strResource, strAbbrv, strResType, strContFName, strContLName,strState,strCountry,strStandResType);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
							
					try {
						assertEquals("", strFuncResult);
						String strResVal = objRs.fetchResValueInResList(selenium, strResource);
						
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
						strFuncResult = objRs.navToEditResLevelSTPage(selenium, strResource);
																		
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,strSTvalue[0], true);
																		
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.navRolesListPge(selenium);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					//create role to update
											
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.CreateRoleWithAllFields(selenium, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strRoleValue = objRole.fetchRoleValueInRoleList(selenium, strRoleName);
						
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
						strFuncResult = objCreateUsers.navUserListPge(selenium);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
						
						
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium, strUserName, strUsrPassword, strUsrPassword, strUsrFulName);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
								
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(selenium, strResource, false, true, true, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.RepStatReasonDetail"), true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
							
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.Advoptn.SetUPResources"), true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly"), true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"), true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.slectAndDeselectRole(selenium, strRoleValue, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
										
						strFuncResult = objCreateUsers.savVrfyUser(selenium, strUserName);
						
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
						strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
								strUsrPassword);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
									
					try {
						assertEquals("", strFuncResult);
					
						String[] strEventStatType = {};
						String[] strRoleStatType = {statTypeName};
						strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
								selenium, strResource, strEventStatType,
								strRoleStatType, false, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
		
					}
					
					try {
						assertEquals("", strFuncResult);
					
						strFuncResult = objViewMap.updateMultiStatusTypeWithReason(selenium,
									strResource, statTypeName, strSTvalue[0],
									strStatusName1,strStatusValue[0],strStatusName2,strStatusValue[1],strReasonVal,strReasonVal);
						
					
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
		
					}
					
					try{
						assertEquals("", strFuncResult);
						String[] strArFunRes=new String[5];
						strArFunRes=objViewMap.getUpdSTValueInMap(selenium, "1");			
						strFuncResult=strArFunRes[1];			
						strUpdMulValueBef=strArFunRes[0];						
									
					}
					catch (AssertionError Ae)
					{
						gstrReason = strFuncResult;
					}
					
					try{
						assertEquals("", strFuncResult);
						String[] strArFunRes=new String[5];
						strArFunRes=objViewMap.getUpdTimeValue(selenium, "1");
						
						strLastUpdTimeBef=strArFunRes[2]+":"+strArFunRes[3];
						
						strLastUpdDateBef=dts.converDateFormat(strArFunRes[0]+strArFunRes[1]+strCurYear, "ddMMMyyyy", "dd-MMM-yyyy");
						strFuncResult=strArFunRes[4];									
						
					}
					catch (AssertionError Ae)
					{
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						if(strUpdMulValueBef.equals(strStatusName1)){
							strUpdValCheckBef=strStatusValue[0];
						}else{
							strUpdValCheckBef=strStatusValue[1];
						}
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.navResourcesList(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					//deselect
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.navToEditResLevelSTPage(selenium, strResource);
																		
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,strSTvalue[0], false);
																		
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						String[] strArFunRes=new String[5];
						strArFunRes= objGen.getSnapTime(selenium);
						strFuncResult=strArFunRes[4];		
						strEndDateBef=dts.converDateFormat(strArFunRes[0]+strArFunRes[1]+strCurYear, "ddMMMyyyy", "dd-MMM-yyyy");
						strEndTimeBef=strArFunRes[2]+":"+strArFunRes[3];
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					//select
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.navToEditResLevelSTPage(selenium, strResource);
																		
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,strSTvalue[0], true);
																		
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
						strFuncResult = objLogin.login(selenium,strUserName,
								strUsrPassword);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
					
						String[] strEventStatType = {};
						String[] strRoleStatType = {statTypeName};
						strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
								selenium, strResource, strEventStatType,
								strRoleStatType, false, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
		
					}
					
					try {
						assertEquals("", strFuncResult);
					
						strFuncResult = objViewMap.updateMultiStatusTypeWithReason(selenium,
									strResource, statTypeName, strSTvalue[0],
									strStatusName1,strStatusValue[0],strStatusName2,strStatusValue[1],strReasonVal,strReasonVal);
						
					
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
		
					}
					
					try{
						assertEquals("", strFuncResult);
						String[] strArFunRes=new String[5];
						strArFunRes=objViewMap.getUpdSTValueInMap(selenium, "1");			
						strFuncResult=strArFunRes[1];			
						strUpdMulValueAft=strArFunRes[0];
						
									
					}
					catch (AssertionError Ae)
					{
						gstrReason = strFuncResult;
					}
					
					try{
						assertEquals("", strFuncResult);
						String[] strArFunRes=new String[5];
						strArFunRes=objViewMap.getUpdTimeValue(selenium, "1");
						
						strLastUpdTimeAft=strArFunRes[2]+":"+strArFunRes[3];
						
						strLastUpdDateAft=dts.converDateFormat(strArFunRes[0]+strArFunRes[1]+strCurYear, "ddMMMyyyy", "dd-MMM-yyyy");
						strFuncResult=strArFunRes[4];									
						
					}
					catch (AssertionError Ae)
					{
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						if(strUpdMulValueAft.equals(strStatusName1)){
							strUpdValCheckAft=strStatusValue[0];
						}else{
							strUpdValCheckAft=strStatusValue[1];
						}
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
									
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep.navToStatusReasonDetail(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep.enterRepDetailsAndGenStatusReasonDetRep(selenium, strRSValue[0], statTypeName,strCurrDate, strCurrDate, true, strUpdValCheckAft, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						//Thread.sleep(20000);
						String strProcess="";				
						String strArgs[]={strAutoFilePath,strPDFDownlPath};
						
						//AutoIt
						Runtime.getRuntime().exec(strArgs);
						
						int intCnt=0; 
						do{
						   GetProcessList objGPL = new GetProcessList();
						   strProcess = objGPL.GetProcessName();
						   intCnt++;
						   Thread.sleep(500);
						 } while (strProcess.contains(strAutoFileName)&&intCnt<180);
								 	
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep.navToStatusReasonDetail(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
					
						strFuncResult = objRep.enterRepDetailsAndGenStatusReasonDetRep(selenium, strRSValue[0], statTypeName,strCurrDate, strCurrDate, false, strUpdValCheckAft, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						//Thread.sleep(20000);
						String strProcess="";				
						String strArgs[]={strAutoFilePath,strCSVDownlPath};
						
						//AutoIt
						Runtime.getRuntime().exec(strArgs);
						
						int intCnt=0; 
						do{
						   GetProcessList objGPL = new GetProcessList();
						   strProcess = objGPL.GetProcessName();
						   intCnt++;
						   Thread.sleep(500);
						 } while (strProcess.contains(strAutoFileName)&&intCnt<180);
								 	
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						String[] strArFunRes=new String[5];
						strArFunRes= objGen.getSnapTime(selenium);
						strFuncResult=strArFunRes[4];		
						strGenDate=dts.converDateFormat(strArFunRes[0]+strArFunRes[1]+strCurYear, "ddMMMyyyy", "dd-MMM-yyyy");
						strRegGenTime=strArFunRes[2]+":"+strArFunRes[3];
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strSysExtrIp=objOFC.externalIP();
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						String[][] strReportData={{"Resource","Status","Status Reason","Status Start Date","Status End Date","Duration ","User","IP","Trace","Comments"},
																  
								  {strResource,strUpdMulValueBef,strReasonName,strLastUpdDateBef+" "+strLastUpdTimeBef,strEndDateBef+" "+strEndTimeBef,"\\d+\\.\\d+",strUserName,strSysExtrIp,"**",strReasonName+" "},
								  {strResource,strUpdMulValueAft,strReasonName,strLastUpdDateAft+" "+strLastUpdTimeAft,strGenDate+" "+strRegGenTime,"\\d+\\.\\d+",strUserName,strSysExtrIp,"**",strReasonName+" "}
								  };
				
						
						strFuncResult=objCSV.ReadFromCSV(strCSVDownlPath, strReportData);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						gstrResult="PASS";
						//Write result data
						strTestData[0]= propEnvDetails.getProperty("Build");
						strTestData[1]=gstrTCID;
						strTestData[2]=strUserName+"/"+strUsrPassword;
						strTestData[3]=strResType;
						strTestData[4]=strResource;
						strTestData[5]=statTypeName;
						strTestData[6]="Status Before: "+strUpdMulValueBef+"Status After"+strUpdMulValueAft;
						strTestData[5]="Check the Status Reason details in PDF file: "+strPDFDownlPath+
										"Start Date: "+strLastUpdDateBef+" "+strLastUpdTimeBef+"End Date: "+strGenDate+" "+strRegGenTime
										+"Status Reason: "+strReasonName+"IP: "+strSysExtrIp;
						
						String strWriteFilePath=propPathDetails.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestData, strWriteFilePath, "Reports");
						
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
			gstrTCID = "BQS-88954";
			gstrTO = "Add a multi status type MST back to resource RS with the status reason and update the status value of RS, generate the �Status Reason Detail Report� and verify that the data is displayed appropriately in the report";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}*/
	
	/***************************************************************************************
	'Description	:Update status of a multi status type MST with a status reason where MST 
	                 is added at the resource level for resource RS. Verify that a user with 
	                 �Run Report� & 'View Resource' rights on RS and with a role with only 
	                 VIEW right for MST can view the details of selected reason in the 
	                 generated �Status Reason Detail Report�.
	'Arguments		:None
	'Returns		:None
	'Date			:06-07-2012
	'Author			:QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                                Modified By
	'Date					                                                    Name
	*****************************************************************************************/
	@Test
	public void testBQS42745() throws Exception {

		CSVFunctions objCSV = new CSVFunctions();
		General objGen = new General();
		StatusReason objStatRes = new StatusReason();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		String strFuncResult = "";
		try {
			gstrTCID = "BQS-42745"; // Test Case Id
			gstrTO = "Update status of a multi status type MST with a status reason "
					+ "where MST is added at the resource level for resource RS. "
					+ "Verify that a user with �Run Report� & 'View Resource' "
					+ "rights on RS and with a role with only VIEW right for"
					+ " MST can view the details of selected reason in the "
					+ "generated �Status Reason Detail Report�.";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			propPathDetails = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTestData[] = new String[10];
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			
			String strUpdMulValue = "";
			String strUpdValCheck = "";

			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String strStatTypeAsgnVal = "Number";
			String strStatTypeAsgn = "AutoNst_" + strTimeText;

			String statTypeName = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";
			String strSTvalue[] = new String[1];
			strSTvalue[0] = "";
			String strSTAsnVal[] = new String[1];

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			strRSValue[0] = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strRoleForUser = "AutoRL" + strTimeText;
			String strRoleRitsForUsr[][] = {};
			String[] strRlUpdRtForUsr = {};
			String strRoleUsrVal = "";

			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			String strUpdPwd = "abc123";

			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strReasonName = "AutoReas_" + System.currentTimeMillis();
			String strReasonVal = "";
			String strRegGenTime = "";
			String strCurYear = dts.getCurrentDate("yyyy");
			String strLastUpdDate = "";
			String strLastUpdTime = "";
			String strGenDate = "";

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
			
	 log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");
	 
			strFuncResult = objLogin.login(seleniumPrecondition, strAdmUserName,
					strAdmPassword);
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition,
						strReasonName, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal = objStatRes.fetchStatReasonValue(seleniumPrecondition,
						strReasonName);
				if (strReasonVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatTypeAsgnVal, strStatTypeAsgn, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatTypeAsgn);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTAsnVal[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
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
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				seleniumPrecondition.click(propElementDetails.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				seleniumPrecondition.click("link=Return to Status Type List");
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
						String strStatValue = objST.fetchSTValueInStatTypeList(
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
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName, strStatusName1,
								strStatusTypeValue, strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
										strReasonVal, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
								statTypeName, strStatusName1);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName, strStatusName2,
								strStatusTypeValue, strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
										strReasonVal, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
								statTypeName, strStatusName2);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName, strStatusName1);
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
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName, strStatusName2);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValue[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
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
								strResType,
								"css=input[name='statusTypeID'][value='"
										+ strSTAsnVal[0] + "']");
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
								strResType);
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
						strFuncResult = objRs.createResourceWitLookUPadres(
								seleniumPrecondition, strResource, strAbbrv, strResType,
								strContFName, strContLName, strState,
								strCountry, strStandResType);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strResVal = objRs.fetchResValueInResList(
								seleniumPrecondition, strResource);
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
						strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
								strResource);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.selDeselctSTInEditRSLevelPage(
								seleniumPrecondition, strSTvalue[0], true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					// create role to update

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

					// create role for user
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.CreateRoleWithAllFields(
								seleniumPrecondition, strRoleForUser, strRoleRitsForUsr,
								strSTvalue, true, strRlUpdRtForUsr, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strRoleUsrVal = objRole.fetchRoleValueInRoleList(
								seleniumPrecondition, strRoleForUser);
						if (strRoleUsrVal.compareTo("") != 0) {
							strFuncResult = "";

						} else {
							strFuncResult = "Failed to fetch Role value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// Update user
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
								seleniumPrecondition, strUpdUsrName, strUpdPwd, strUpdPwd,
								strUsrFulName);
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
						strFuncResult = objCreateUsers.slectAndDeselectRole(
								seleniumPrecondition, strRoleValue, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
								strUpdUsrName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
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
								seleniumPrecondition, strUserName, strUsrPassword,
								strUsrPassword, strUsrFulName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.selectResourceRights(seleniumPrecondition, strResource,
										false, false, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.advancedOptns(
										seleniumPrecondition,
										propElementDetails
												.getProperty("CreateNewUsr.AdvOptn.RepStatReasonDetail"),
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.slectAndDeselectRole(
								seleniumPrecondition, strRoleUsrVal, true);
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
						strFuncResult = objLogin.logout(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUpdUsrName, strUpdPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String[] strEventStatType = {};
						String[] strRoleStatType = { statTypeName };
						strFuncResult = objViewMap
								.verifyStatTypesInResourcePopup(selenium,
										strResource, strEventStatType,
										strRoleStatType, false, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViewMap
								.updateMultiStatusTypeWithReason(selenium,
										strResource, statTypeName,
										strSTvalue[0], strStatusName1,
										strStatusValue[0], strStatusName2,
										strStatusValue[1], strReasonVal,
										strReasonVal);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						String[] strArFunRes = new String[5];
						strArFunRes = objViewMap.getUpdSTValueInMap(selenium,
								"1");
						strFuncResult = strArFunRes[1];
						strUpdMulValue = strArFunRes[0];

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String[] strArFunRes = new String[5];
						strArFunRes = objViewMap.getUpdTimeValue(selenium, "1");

						strLastUpdTime = strArFunRes[2] + ":" + strArFunRes[3];

						strLastUpdDate = dts.converDateFormat(strArFunRes[0]
								+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
								"dd-MMM-yyyy");
						strFuncResult = strArFunRes[4];

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						if (strUpdMulValue.equals(strStatusName1)) {
							strUpdValCheck = strStatusValue[0];
						} else {
							strUpdValCheck = strStatusValue[1];
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
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUserName, strUsrPassword);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonDetail(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);

						strFuncResult = objRep
								.enterRepDetailsAndGenStatusReasonDetRep(
										selenium, strRSValue[0], statTypeName,
										strCurrDate, strCurrDate, true,
										strUpdValCheck, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strPDFDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonDetail(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String strCSTApplTime = dts
								.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

						strFuncResult = objRep
								.enterRepDetailsAndGenStatusReasonDetRep(
										selenium, strRSValue[0], statTypeName,
										strCSTApplTime, strCSTApplTime, false,
										strUpdValCheck, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strCSVDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						String[] strArFunRes = new String[5];
						strArFunRes = objGen.getSnapTime(selenium);
						strFuncResult = strArFunRes[4];
						strGenDate = dts.converDateFormat(strArFunRes[0]
								+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
								"dd-MMM-yyyy");
						strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

/*
					try {
						assertEquals("", strFuncResult);
						String[][] strReportData = {
								{ "Resource", "Status", "Status Reason",
										"Status Start Date", "Status End Date",
										"Duration ", "User", "IP", "Trace",
										"Comments" },

								{ strResource, strUpdMulValue, strReasonName,
										strLastUpdDate + " " + strLastUpdTime,
										strGenDate + " " + strRegGenTime,
										"\\d+\\.\\d+", strUpdUsrName,
										propEnvDetails.getProperty("ExternalIP"), "**", strReasonName + " " }, };

						strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath,
								strReportData);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}*/
					
					File Pf1 = new File(strCSVDownlPath);
					File Cf1 = new File(strPDFDownlPath);

					if (Pf1.exists() && Cf1.exists()) {
						try {
							assertEquals("", strFuncResult);
						
							strTestData[0] = propEnvDetails.getProperty("Build");
							strTestData[1] = gstrTCID;
							strTestData[2] = strUserName +","+strUpdUsrName+ "/" + strUsrPassword;
							strTestData[3] = strResType;
							strTestData[4] = strResource;
							strTestData[5] = statTypeName;
							strTestData[6] = strUpdMulValue;
							strTestData[5] = "Check the Status Reason details in CSV file: "
									+ strCSVDownlPath
									+ "Start Date: "
									+ strLastUpdDate
									+ " "
									+ strLastUpdTime
									+ "End Date: "
									+ strGenDate
									+ " "
									+ strRegGenTime
									+ "Status Reason: "
									+ strReasonName + "IP: " + propEnvDetails.getProperty("ExternalIP");

							String strWriteFilePath = propPathDetails
									.getProperty("WriteResultPath");
							objOFC.writeResultData(strTestData, strWriteFilePath,
									"Reports");

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							gstrResult = "PASS";
							// Write result data
							strTestData[0] = propEnvDetails.getProperty("Build");
							strTestData[1] = gstrTCID;
							strTestData[2] =  strUserName +","+strUpdUsrName+ "/" + strUsrPassword;
							strTestData[3] = strResType;
							strTestData[4] = strResource;
							strTestData[5] = statTypeName;
							strTestData[6] = strUpdMulValue;
							strTestData[5] = "Check the Status Reason details in PDF file: "
									+ strPDFDownlPath
									+ "Start Date: "
									+ strLastUpdDate
									+ " "
									+ strLastUpdTime
									+ "End Date: "
									+ strGenDate
									+ " "
									+ strRegGenTime
									+ "Status Reason: "
									+ strReasonName + "IP: " + propEnvDetails.getProperty("ExternalIP");

							String strWriteFilePath = propPathDetails
									.getProperty("WriteResultPath");
							objOFC.writeResultData(strTestData, strWriteFilePath,
									"Reports");

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-42745";
			gstrTO = "Update status of a multi status type MST with a"
					+ " status reason where MST is added at the resource"
					+ " level for resource RS. Verify that a user with "
					+ "�Run Report� & 'View Resource' rights on RS and "
					+ "with a role with only VIEW right for MST can view "
					+ "the details of selected reason in the generated "
					+ "�Status Reason Detail Report�.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	
	/***************************************************************************************
	'Description	:Update status of a multi status type MST with a status reason where MST 
	                 is added at the resource type level for resource RS. Verify that a user 
	                 with �Run Report� & 'View Resource' rights on RS and with a role with 
	                 only VIEW right for MST can view the details of selected reason in the
	                  generated �Status Reason Detail Report�.
	'Arguments		:None
	'Returns		:None
	'Date			:06-07-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date				                                                Modified By
	'Date					                                                    Name
	****************************************************************************************/

	@Test
	public void testBQS42748() throws Exception {

		CSVFunctions objCSV = new CSVFunctions();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		General objGen = new General();
		StatusReason objStatRes = new StatusReason();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		String strFuncResult = "";
		try {
			gstrTCID = "BQS-42748"; // Test Case Id
			gstrTO = "Update status of a multi status type MST with a "
					+ "status reason where MST is added at the resource"
					+ " type level for resource RS. Verify that a user "
					+ "with �Run Report� & 'View Resource' rights on RS"
					+ " and with a role with only VIEW right for MST "
					+ "can view the details of selected reason in the "
					+ "generated �Status Reason Detail Report�.";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			propPathDetails = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTestData[] = new String[10];
			

			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String statTypeName = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";
			String strSTvalue[] = new String[1];
			strSTvalue[0] = "";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			strRSValue[0] = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strRoleForUser = "AutoRL" + strTimeText;
			String strRoleRitsForUsr[][] = {};
			String[] strRlUpdRtForUsr = {};
			String strRoleUsrVal = "";

			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			String strUpdPwd = "abc123";

			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strReasonName = "AutoReas_" + System.currentTimeMillis();
			String strReasonVal = "";
			String strRegGenTime = "";
			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strLastUpdDate = "";
			String strLastUpdTime = "";
			String strUpdMulValue = "";
			String strUpdValCheck = "";

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

		log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strAdmUserName, strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes
						.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes
						.createStatusReasn(seleniumPrecondition, strReasonName,
								"", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal = objStatRes.fetchStatReasonValue(
						seleniumPrecondition, strReasonName);
				if (strReasonVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(
						seleniumPrecondition, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				seleniumPrecondition.click(propElementDetails
						.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				seleniumPrecondition.click("link=Return to Status Type List");
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
						String strStatValue = objST.fetchSTValueInStatTypeList(
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
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName,
								strStatusName1, strStatusTypeValue,
								strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(
										seleniumPrecondition, strReasonVal,
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(
								seleniumPrecondition, statTypeName,
								strStatusName1);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName,
								strStatusName2, strStatusTypeValue,
								strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(
										seleniumPrecondition, strReasonVal,
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(
								seleniumPrecondition, statTypeName,
								strStatusName2);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName,
								strStatusName1);
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
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName,
								strStatusName2);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValue[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT
								.navResourceTypList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.resrcTypeMandatoryFlds(
								seleniumPrecondition, strResType,
								"css=input[name='statusTypeID'][value='"
										+ strSTvalue[0] + "']");
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.saveAndvrfyResType(
								seleniumPrecondition, strResType);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs
								.navResourcesList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.createResourceWitLookUPadres(
								seleniumPrecondition, strResource, strAbbrv,
								strResType, strContFName, strContLName,
								strState, strCountry, strStandResType);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strResVal = objRs.fetchResValueInResList(
								seleniumPrecondition, strResource);

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
						strFuncResult = objRole
								.navRolesListPge(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					// create role to update

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.CreateRoleWithAllFields(
								seleniumPrecondition, strRoleName,
								strRoleRights, strSTvalue, true, strSTvalue,
								true, true);
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

					// create role for user
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.CreateRoleWithAllFields(
								seleniumPrecondition, strRoleForUser,
								strRoleRitsForUsr, strSTvalue, true,
								strRlUpdRtForUsr, true, true);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strRoleUsrVal = objRole.fetchRoleValueInRoleList(
								seleniumPrecondition, strRoleForUser);
						if (strRoleUsrVal.compareTo("") != 0) {
							strFuncResult = "";

						} else {
							strFuncResult = "Failed to fetch Role value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// Update user
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.navUserListPge(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
								seleniumPrecondition, strUpdUsrName, strUpdPwd,
								strUpdPwd, strUsrFulName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(
								seleniumPrecondition, strResource, false, true,
								false, true);
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
								.savVrfyUserWithSearchUser(
										seleniumPrecondition, strUpdUsrName,
										strByRole, strByResourceType,
										strByUserInfo, strNameFormat);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.navUserListPge(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
								seleniumPrecondition, strUserName,
								strUsrPassword, strUsrPassword, strUsrFulName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(
								seleniumPrecondition, strResource, false,
								false, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.advancedOptns(
										seleniumPrecondition,
										propElementDetails
												.getProperty("CreateNewUsr.AdvOptn.RepStatReasonDetail"),
										true);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.slectAndDeselectRole(
								seleniumPrecondition, strRoleUsrVal, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.savVrfyUserWithSearchUser(
										seleniumPrecondition, strUserName,
										strByRole, strByResourceType,
										strByUserInfo, strNameFormat);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUpdUsrName, strUpdPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);

						String[] strEventStatType = {};
						String[] strRoleStatType = { statTypeName };
						strFuncResult = objViewMap
								.verifyStatTypesInResourcePopup(selenium,
										strResource, strEventStatType,
										strRoleStatType, false, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViewMap
								.updateMultiStatusTypeWithReason(selenium,
										strResource, statTypeName,
										strSTvalue[0], strStatusName1,
										strStatusValue[0], strStatusName2,
										strStatusValue[1], strReasonVal,
										strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						String[] strArFunRes = new String[5];
						strArFunRes = objViewMap.getUpdSTValueInMap(selenium,
								"1");
						strFuncResult = strArFunRes[1];
						strUpdMulValue = strArFunRes[0];

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String[] strArFunRes = new String[5];
						strArFunRes = objViewMap.getUpdTimeValue(selenium, "1");

						strLastUpdTime = strArFunRes[2] + ":" + strArFunRes[3];

						strLastUpdDate = dts.converDateFormat(strArFunRes[0]
								+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
								"dd-MMM-yyyy");
						strFuncResult = strArFunRes[4];
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						if (strUpdMulValue.equals(strStatusName1)) {
							strUpdValCheck = strStatusValue[0];
						} else {
							strUpdValCheck = strStatusValue[1];
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
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUserName, strUsrPassword);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonDetail(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);

						String strCSTApplTime = dts
								.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

						strFuncResult = objRep
								.enterRepDetailsAndGenStatusReasonDetRep(
										selenium, strRSValue[0], statTypeName,
										strCSTApplTime, strCSTApplTime, true,
										strUpdValCheck, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strPDFDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonDetail(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String strCSTApplTime = dts
								.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

						strFuncResult = objRep
								.enterRepDetailsAndGenStatusReasonDetRep(
										selenium, strRSValue[0], statTypeName,
										strCSTApplTime, strCSTApplTime, false,
										strUpdValCheck, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strCSVDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						String[] strArFunRes = new String[5];
						strArFunRes = objGen.getSnapTime(selenium);
						strFuncResult = strArFunRes[4];
						strGenDate = dts.converDateFormat(strArFunRes[0]
								+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
								"dd-MMM-yyyy");
						strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}


					try {
						assertEquals("", strFuncResult);
						String[][] strReportData = {
								{ "Resource", "Status", "Status Reason",
										"Status Start Date", "Status End Date",
										"Duration ", "User", "IP", "Trace",
										"Comments" },

								{ strResource, strUpdMulValue, strReasonName,
										strLastUpdDate + " " + strLastUpdTime,
										strGenDate + " " + strRegGenTime,
										"\\d+\\.\\d+", strUpdUsrName,
										propEnvDetails.getProperty("ExternalIP"), "**", strReasonName + " " }, };

						strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath,
								strReportData);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						gstrResult = "PASS";
						// Write result data

						strTestData[0] = propEnvDetails.getProperty("Build");
						strTestData[1] = gstrTCID;
						strTestData[2] = strUserName + "/" + strUsrPassword;
						strTestData[3] = strResType;
						strTestData[4] = strResource;
						strTestData[5] = statTypeName;
						strTestData[6] = strUpdMulValue;
						strTestData[5] = "Check the Status Reason details in PDF file: "
								+ strPDFDownlPath
								+ "Start Date: "
								+ strLastUpdDate
								+ " "
								+ strLastUpdTime
								+ "End Date: "
								+ strGenDate
								+ " "
								+ strRegGenTime
								+ "Status Reason: "
								+ strReasonName + "IP: " + propEnvDetails.getProperty("ExternalIP");

						String strWriteFilePath = propPathDetails
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestData, strWriteFilePath,
								"Reports");

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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-42748";
			gstrTO = "Update status of a multi status type MST with "
					+ "a status reason where MST is added at the resource"
					+ " type level for resource RS. Verify that a user with"
					+ " �Run Report� & 'View Resource' rights on RS and with "
					+ "a role with only VIEW right for MST can view the details"
					+ " of selected reason in the generated �Status Reason Detail Report�.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	/***************************************************************
	'Description		:Update status of a multi private status type pMST with a status reason where pMST is added at the resource level for resource RS. Verify that a user with �Run Report� & 'View Resource' rights on RS and without any role can view the details of selected reason in the generated �Status Reason Detail Report�.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:06-07-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	@Test
	public void testBQS48860() throws Exception {

		CSVFunctions objCSV = new CSVFunctions();
		General objGen = new General();
		StatusReason objStatRes = new StatusReason();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		String strFuncResult = "";

		try {
			gstrTCID = "BQS-48860"; // Test Case Id
			gstrTO = "Update status of a multi private status type pMST with "
					+ "a status reason where pMST is added at the resource level"
					+ " for resource RS. Verify that a user with �Run Report� & "
					+ "'View Resource' rights on RS and without any role can view"
					+ " the details of selected reason in the generated �Status "
					+ "Reason Detail Report�.";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			propPathDetails = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTestData[] = new String[10];

			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String strStatTypeAsgnVal = "Number";
			String strStatTypeAsgn = "AutoNst_" + strTimeText;

			String statTypeName = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strSTvalue[] = new String[1];
			String strSTAsnVal[] = new String[1];
			strSTvalue[0] = "";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			strRSValue[0] = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			String strUpdPwd = "abc123";

			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strReasonName = "AutoReas_" + System.currentTimeMillis();
			String strReasonVal = "";

			String strRegGenTime = "";
			String strGenDate = "";
			String strCurYear = dts.getCurrentDate("yyyy");
			String strLastUpdDate = "";
			String strLastUpdTime = "";
			String strUpdMulValue = "";
			String strUpdValCheck = "";

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strAdmUserName, strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes
						.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes
						.createStatusReasn(seleniumPrecondition, strReasonName,
								"", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal = objStatRes.fetchStatReasonValue(
						seleniumPrecondition, strReasonName);
				if (strReasonVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatTypeAsgnVal,
						strStatTypeAsgn, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatTypeAsgn);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTAsnVal[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// private ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypeVisibility(
						seleniumPrecondition, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(
						seleniumPrecondition, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				seleniumPrecondition.click(propElementDetails
						.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				seleniumPrecondition.click("link=Return to Status Type List");
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
						String strStatValue = objST.fetchSTValueInStatTypeList(
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
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName,
								strStatusName1, strStatusTypeValue,
								strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(
										seleniumPrecondition, strReasonVal,
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(
								seleniumPrecondition, statTypeName,
								strStatusName1);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName,
								strStatusName2, strStatusTypeValue,
								strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(
										seleniumPrecondition, strReasonVal,
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(
								seleniumPrecondition, statTypeName,
								strStatusName2);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName,
								strStatusName1);
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
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName,
								strStatusName2);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValue[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT
								.navResourceTypList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.resrcTypeMandatoryFlds(
								seleniumPrecondition, strResType,
								"css=input[name='statusTypeID'][value='"
										+ strSTAsnVal[0] + "']");
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.saveAndvrfyResType(
								seleniumPrecondition, strResType);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs
								.navResourcesList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.createResourceWitLookUPadres(
								seleniumPrecondition, strResource, strAbbrv,
								strResType, strContFName, strContLName,
								strState, strCountry, strStandResType);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strResVal = objRs.fetchResValueInResList(
								seleniumPrecondition, strResource);
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
						strFuncResult = objRs.navToEditResLevelSTPage(
								seleniumPrecondition, strResource);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.selDeselctSTInEditRSLevelPage(
								seleniumPrecondition, strSTvalue[0], true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole
								.navRolesListPge(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					// create role to update

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.CreateRoleWithAllFields(
								seleniumPrecondition, strRoleName,
								strRoleRights, strSTvalue, true, strSTvalue,
								true, true);
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

					// Update user
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.navUserListPge(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
								seleniumPrecondition, strUpdUsrName, strUpdPwd,
								strUpdPwd, strUsrFulName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(
								seleniumPrecondition, strResource, false, true,
								false, true);
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
								.savVrfyUserWithSearchUser(
										seleniumPrecondition, strUpdUsrName,
										strByRole, strByResourceType,
										strByUserInfo, strNameFormat);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.navUserListPge(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
								seleniumPrecondition, strUserName,
								strUsrPassword, strUsrPassword, strUsrFulName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(
								seleniumPrecondition, strResource, false,
								false, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.advancedOptns(
										seleniumPrecondition,
										propElementDetails
												.getProperty("CreateNewUsr.AdvOptn.RepStatReasonDetail"),
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.savVrfyUserWithSearchUser(
										seleniumPrecondition, strUserName,
										strByRole, strByResourceType,
										strByUserInfo, strNameFormat);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUpdUsrName, strUpdPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String[] strEventStatType = {};
						String[] strRoleStatType = { statTypeName };
						strFuncResult = objViewMap
								.verifyStatTypesInResourcePopup(selenium,
										strResource, strEventStatType,
										strRoleStatType, false, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViewMap
								.updateMultiStatusTypeWithReason(selenium,
										strResource, statTypeName,
										strSTvalue[0], strStatusName1,
										strStatusValue[0], strStatusName2,
										strStatusValue[1], strReasonVal,
										strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						String[] strArFunRes = new String[5];
						strArFunRes = objViewMap.getUpdSTValueInMap(selenium,
								"1");
						strFuncResult = strArFunRes[1];
						strUpdMulValue = strArFunRes[0];
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String[] strArFunRes = new String[5];
						strArFunRes = objViewMap.getUpdTimeValue(selenium, "1");

						strLastUpdTime = strArFunRes[2] + ":" + strArFunRes[3];

						strLastUpdDate = dts.converDateFormat(strArFunRes[0]
								+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
								"dd-MMM-yyyy");
						strFuncResult = strArFunRes[4];

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						if (strUpdMulValue.equals(strStatusName1)) {
							strUpdValCheck = strStatusValue[0];
						} else {
							strUpdValCheck = strStatusValue[1];
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
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUserName, strUsrPassword);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonDetail(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strCSTApplTime = dts
								.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

						strFuncResult = objRep
								.enterRepDetailsAndGenStatusReasonDetRep(
										selenium, strRSValue[0], statTypeName,
										strCSTApplTime, strCSTApplTime, true,
										strUpdValCheck, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strPDFDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonDetail(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strCSTApplTime = dts
								.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

						strFuncResult = objRep
								.enterRepDetailsAndGenStatusReasonDetRep(
										selenium, strRSValue[0], statTypeName,
										strCSTApplTime, strCSTApplTime, false,
										strUpdValCheck, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strCSVDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						String[] strArFunRes = new String[5];
						strArFunRes = objGen.getSnapTime(selenium);
						strFuncResult = strArFunRes[4];
						strGenDate = dts.converDateFormat(strArFunRes[0]
								+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
								"dd-MMM-yyyy");
						strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					/*
					 * try { assertEquals("", strFuncResult); String[][]
					 * strReportData = { { "Resource", "Status",
					 * "Status Reason", "Status Start Date", "Status End Date",
					 * "Duration ", "User", "IP", "Trace", "Comments" },
					 * 
					 * { strResource, strUpdMulValue, strReasonName,
					 * strLastUpdDate + " " + strLastUpdTime, strGenDate + " " +
					 * strRegGenTime, "\\d+\\.\\d+", strUpdUsrName,
					 * propEnvDetails.getProperty("ExternalIP"), "**",
					 * strReasonName + " " }, };
					 * 
					 * strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath,
					 * strReportData); } catch (AssertionError Ae) { gstrReason
					 * = strFuncResult; }
					 */

					File Pf1 = new File(strCSVDownlPath);
					File Cf1 = new File(strPDFDownlPath);

					if (Pf1.exists() && Cf1.exists()) {
						try {
							assertEquals("", strFuncResult);

							strTestData[0] = propEnvDetails
									.getProperty("Build");
							strTestData[1] = gstrTCID;
							strTestData[2] = strUserName + "," + strUpdUsrName
									+ "/" + strUsrPassword;
							strTestData[3] = strResType;
							strTestData[4] = strResource;
							strTestData[5] = statTypeName;
							strTestData[6] = strUpdMulValue;
							strTestData[5] = "Check the Status Reason details in CSV file: "
									+ strCSVDownlPath
									+ "Start Date: "
									+ strLastUpdDate
									+ " "
									+ strLastUpdTime
									+ "End Date: "
									+ strGenDate
									+ " "
									+ strRegGenTime
									+ "Status Reason: "
									+ strReasonName
									+ "IP: "
									+ propEnvDetails.getProperty("ExternalIP");

							String strWriteFilePath = propPathDetails
									.getProperty("WriteResultPath");
							objOFC.writeResultData(strTestData,
									strWriteFilePath, "Reports");

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							gstrResult = "PASS";
							// Write result data
							// Write result data
							strTestData[0] = propEnvDetails
									.getProperty("Build");
							strTestData[1] = gstrTCID;
							strTestData[2] = strUserName + "," + strUpdUsrName
									+ "/" + strUsrPassword;
							strTestData[3] = strResType;
							strTestData[4] = strResource;
							strTestData[5] = statTypeName;
							strTestData[6] = strUpdMulValue;
							strTestData[5] = "Check the Status Reason details in PDF file: "
									+ strPDFDownlPath
									+ "Start Date: "
									+ strLastUpdDate
									+ " "
									+ strLastUpdTime
									+ "End Date: "
									+ strGenDate
									+ " "
									+ strRegGenTime
									+ "Status Reason: "
									+ strReasonName
									+ "IP: "
									+ propEnvDetails.getProperty("ExternalIP");

							String strWriteFilePath = propPathDetails
									.getProperty("WriteResultPath");
							objOFC.writeResultData(strTestData,
									strWriteFilePath, "Reports");

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

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
			gstrTCID = "BQS-48860";
			gstrTO = "Update status of a multi private status type"
					+ " pMST with a status reason where pMST is added"
					+ " at the resource level for resource RS. Verify"
					+ " that a user with �Run Report� & 'View Resource'"
					+ " rights on RS and without any role can view the "
					+ "details of selected reason in the generated �"
					+ "Status Reason Detail Report�.";
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
	
	/***********************************************************************************************
	'Description	:Verify that, status reason details of a status type is not displayed in the 
	                 'Status Reason Detail' report for a particular resource for which it is refined.
	'Arguments		:None
	'Returns		:None
	'Date			:23-10-2012
	'Author			:QSG
	'------------------------------------------------------------------------------------------------
	'Modified Date				                                                        Modified By
	'Date					                                                            Name
	**************************************************************************************************/
	@Test
	public void testBQS63435() throws Exception {

		String strFuncResult = "";
		CSVFunctions objCSV = new CSVFunctions();
		General objGen = new General();
		StatusReason objStatRes = new StatusReason();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
	
		Roles objRole = new Roles();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		try {
			gstrTCID = "BQS-63435"; // Test Case Id
			gstrTO = "Verify that, status reason details of a status type is not"
					+ " displayed in the 'Status Reason Detail' report for a particular"
					+ " resource for which it is refined.";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			propPathDetails= objAP.Read_FilePath();
			String[] strTestData=new String[10];
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String statTypeName1 = "AutoMSt1_" + strTimeText;
			String statTypeName2 = "AutoMSt2_" + strTimeText;
			String statTypeName3 = "AutoMSt3_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";
			String strArStatType[] = { statTypeName1, statTypeName2,
					statTypeName3 };
			String strSTvalue[] = new String[3];
			strSTvalue[0] = "";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusName3 = "Stc" + strTimeText;
			String strArStatus[] = { strStatusName1, strStatusName2,
					strStatusName3 };
			String strStatusValue[] = new String[3];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			strStatusValue[2] = "";

			String strResType = "AutoRt_" + strTimeText;
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[2];
			strRSValue[0] = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			String strUpdPwd = "abc123";
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRoleForUser = "AutoRL" + strTimeText;
			String strRoleRitsForUsr[][] = {};
			String[] strRlUpdRtForUsr = {};
			String strRoleUsrVal = "";

			String strReasonName1 = "AutoReas1_" + System.currentTimeMillis();
			String strReasonName2 = "AutoReas2_" + System.currentTimeMillis();
			String[] strReasonVal = new String[2];

			String strLastUpdDate = "";
			String strLastUpdTime = "";
			String strGenDate = "";
			String strRegGenTime = "";

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath1 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".csv";
			String strPDFDownlPath1 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".pdf";

			String strCSVDownlPath2 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep2_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".csv";
			String strPDFDownlPath2 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep2_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".pdf";

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = "";
			
	   log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");
	   
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strAdmUserName,
						strAdmPassword);
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
				strFuncResult = objStatRes.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SR1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition,
						strReasonName1, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[0] = objStatRes.fetchStatReasonValue(seleniumPrecondition,
						strReasonName1);
				if (strReasonVal[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SR2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition,
						strReasonName2, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[1] = objStatRes.fetchStatReasonValue(seleniumPrecondition,
						strReasonName2);
				if (strReasonVal[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statTypeName1, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName1, strStatusName1, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intVal = 0; intVal < strReasonVal.length; intVal++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intVal], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName1, strStatusName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName1, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statTypeName2, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName2, strStatusName2, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intVal = 0; intVal < strReasonVal.length; intVal++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intVal], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName2, strStatusName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName2, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statTypeName3, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName3);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName3, strStatusName3, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intVal = 0; intVal < strReasonVal.length; intVal++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intVal], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName3, strStatusName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName3, strStatusName3);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
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
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 0; intST < strSTvalue.length - 1; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.selAndDeselSTInEditResLevelST(
							seleniumPrecondition, strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResType);
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource1, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResource1);
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource2, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResource2);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

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

			// create role for user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleForUser, strRoleRitsForUsr, strSTvalue, true,
						strRlUpdRtForUsr, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleUsrVal = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleForUser);
				if (strRoleUsrVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Update user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUpdUsrName, strUpdPwd, strUpdPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource1, false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource2, false, true, false, true);

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
				strFuncResult = objCreateUsers
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUpdUsrName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
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
						strUserName, strUsrPassword, strUsrPassword,
						strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource1, false, false, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource2, false, false, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepStatReasonDetail"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleUsrVal, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
	   log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");
	   log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
	   
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUpdUsrName,
						strUpdPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1, statTypeName2,
						statTypeName3 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			for (int intST = 0; intST < strArStatType.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViewMap.updateMultiSTWithReason(
							selenium, strResource1, strArStatType[intST],
							strSTvalue[intST], strArStatus[intST],
							strStatusValue[intST], strReasonVal);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource1, strArStatus);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1, statTypeName2,
						statTypeName3 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource2, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			for (int intST = 0; intST < strArStatType.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViewMap.updateMultiSTWithReason(
							selenium, strResource2, strArStatType[intST],
							strSTvalue[intST], strArStatus[intST],
							strStatusValue[intST], strReasonVal);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource2, strArStatus);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strYear = dts.getCurrentDate("yyyy");
			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdTimeValue(selenium, "2");

				strLastUpdTime = strArFunRes[2] + ":" + strArFunRes[3];

				strLastUpdDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strYear, "ddMMMyyyy", "dd-MMM-yyyy");
				strFuncResult = strArFunRes[4];

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
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			for (int intST = 1; intST < 3; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers
							.selAndDeselSTInRefineVisibleST(selenium,
									strSTvalue[intST], false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strUsrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonDetail(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strDateAr[] = objGen.getSnapTime(selenium);

			strCurrDate = dts.converDateFormat(strDateAr[1] + strDateAr[0]
					+ strYear, "MMMddyyyy", "MM/dd/yyyy");
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep
						.enterRepDetailsAndGenStatusReasonDetRep_SelAll(
								selenium, strRSValue, statTypeName2,
								strCSTApplTime, strCSTApplTime, true,
								strStatusValue[1], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonDetail(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep
						.enterRepDetailsAndGenStatusReasonDetRep_SelAll(
								selenium, strRSValue, statTypeName2,
								strCSTApplTime, strCSTApplTime, false,
								strStatusValue[1], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				String[] strArFunRes = new String[5];
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strYear, "ddMMMyyyy", "dd-MMM-yyyy");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strReportData = {
						{ "Resource", "Status", "Status Reason",
								"Status Start Date", "Status End Date",
								"Duration ", "User", "IP", "Trace", "Comments" },

						{ strResource2, strStatusName2, strReasonName2,
								strLastUpdDate + " " + strLastUpdTime,
								strGenDate + " " + strRegGenTime,
								"\\d+\\.\\d+", strUpdUsrName, propEnvDetails.getProperty("ExternalIP"),
								"**", "\"" + strReasonName1,
								strReasonName2 + " " + "\"" },
						{ strResource2, strStatusName2, strReasonName1,
								strLastUpdDate + " " + strLastUpdTime,
								strGenDate + " " + strRegGenTime,
								"\\d+\\.\\d+", strUpdUsrName, propEnvDetails.getProperty("ExternalIP"),
								"**", "\"" + strReasonName1,
								strReasonName2 + " " + "\"" } };

				strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath1,
						strReportData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonDetail(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep
						.enterRepDetailsAndGenStatusReasonDetRep_SelAll(
								selenium, strRSValue, statTypeName3,
								strCSTApplTime, strCSTApplTime, true,
								strStatusValue[2], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonDetail(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");

				strFuncResult = objRep
						.enterRepDetailsAndGenStatusReasonDetRep_SelAll(
								selenium, strRSValue, statTypeName3,
								strCSTApplTime, strCSTApplTime, false,
								strStatusValue[2], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				String[] strArFunRes = new String[5];
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strYear, "ddMMMyyyy", "dd-MMM-yyyy");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				String[][] strReportData = {
						{ "Resource", "Status", "Status Reason",
								"Status Start Date", "Status End Date",
								"Duration ", "User", "IP", "Trace", "Comments" },

						{ strResource2, strStatusName3, strReasonName2,
								strLastUpdDate + " " + strLastUpdTime,
								strGenDate + " " + strRegGenTime,
								"\\d+\\.\\d+", strUpdUsrName, propEnvDetails.getProperty("ExternalIP"),
								"**", "\"" + strReasonName1,
								strReasonName2 + " " + "\"" },
						{ strResource2, strStatusName3, strReasonName1,
								strLastUpdDate + " " + strLastUpdTime,
								strGenDate + " " + strRegGenTime,
								"\\d+\\.\\d+", strUpdUsrName, propEnvDetails.getProperty("ExternalIP"),
								"**", "\"" + strReasonName1,
								strReasonName2 + " " + "\"" } };

				strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath2,
						strReportData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strUsrPassword;
				strTestData[3] = strResType;
				strTestData[4] = strResource2;
				strTestData[5] = statTypeName2 + "," + statTypeName3;
				strTestData[6] = strStatusName2 + "," + strStatusName3;
				strTestData[7] = "Check the Status Reason details in PDF file: "
						+ strPDFDownlPath1
						+ " for MST2 and "
						+ strPDFDownlPath2
						+ " for MST3 status reasons: "
						+ strReasonName1 + "," + strReasonName2;

				String strWriteFilePath = propPathDetails
						.getProperty("WriteResultPath");
				objOFC
						.writeResultData(strTestData, strWriteFilePath,
								"Reports");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-63435";
			gstrTO = "Verify that, status reason details of a status "
					+ "type is not displayed in the 'Status Reason Detail'"
					+ " report for a particular resource for which it is refined.";
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
	
	/************************************************************************
	'Description	:Generate 'Status Reason Detail report' in PDF format and
	                 verify that the report displays correct data
	'Arguments		:None
	'Returns		:None
	'Date			:03-12-2012
	'Author			:QSG
	'-------------------------------------------------------------------------
	'Modified Date				                               Modified By
	'Date					                                    Name
	**************************************************************************/
	@Test
	public void testBQS103967() throws Exception {

		CSVFunctions objCSV = new CSVFunctions();
		General objGen = new General();
		StatusReason objStatRes = new StatusReason();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();

		try {
			gstrTCID = "BQS-103967"; // Test Case Id
			gstrTO = "Generate 'Status Reason Detail report' in PDF format"
					+ " and verify that the report displays correct data";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			propPathDetails = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTestData[] = new String[10];
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String statTypeName = "AutoMSt_" + strTimeText;
			String statPtTypeName = "AutopMSt_" + strTimeText;
			String statShTypeName = "AutosMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";
			String strSTvalue[] = new String[3];
			strSTvalue[0] = "";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusName3 = "Stc" + strTimeText;
			String strStatusName4 = "Std" + strTimeText;
			String strStatusName5 = "Ste" + strTimeText;
			String strStatusName6 = "Stf" + strTimeText;
			String strStatusValue[] = new String[2];
			String strPtStatusValue[] = new String[2];
			String strShStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
			String strAbbrv = "Rs";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			strRSValue[0] = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			Properties pathProps = objAP.Read_FilePath();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			String strUpdPwd = "abc123";
			String strUpdMulValue = "";
			String strUpdValCheck = "";

			String strRoleForUser = "AutoRL" + strTimeText;
			String strRoleRitsForUsr[][] = {};
			String[] strRlUpdRtForUsr = {};
			String strRoleUsrVal = "";

			String strReasonName1 = "AutoReas1_" + System.currentTimeMillis();
			String strReasonName2 = "AutoReas2_" + System.currentTimeMillis();
			String[] strReasonVal = new String[2];

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strPDFDownlPath1 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".pdf";
			String strPDFDownlPath2 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep2_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".pdf";

			String strPDFDownlPath3 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep3_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".pdf";		

	    log4j.info("---------------Precondtion for test case " + gstrTCID+ "Starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strAdmUserName, strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition,
						strReasonName1, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[0] = objStatRes.fetchStatReasonValue(seleniumPrecondition,
						strReasonName1);
				if (strReasonVal[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition,
						strReasonName2, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[1] = objStatRes.fetchStatReasonValue(seleniumPrecondition,
						strReasonName2);
				if (strReasonVal[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
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

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectAndDeselectStatusReason(
							seleniumPrecondition, strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName, strStatusName1, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName, strStatusName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName, strStatusName2, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName, strStatusName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statPtTypeName, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypeVisibility(seleniumPrecondition,
						false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectAndDeselectStatusReason(
							seleniumPrecondition, strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statPtTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statPtTypeName);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statPtTypeName, strStatusName3, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statPtTypeName, strStatusName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statPtTypeName, strStatusName4, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statPtTypeName, strStatusName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statPtTypeName, strStatusName3);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strPtStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statPtTypeName, strStatusName4);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strPtStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statShTypeName, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypeVisibility(seleniumPrecondition,
						true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectAndDeselectStatusReason(
							seleniumPrecondition, strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statShTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statShTypeName);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statShTypeName, strStatusName5, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statShTypeName, strStatusName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statShTypeName, strStatusName6, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statShTypeName, strStatusName6);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statShTypeName, strStatusName5);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strShStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statShTypeName, strStatusName6);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strShStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
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
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 1; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
							strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResType);
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
			// create role to update

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
						strUserName, strUsrPassword, strUsrPassword,
						strUsrFulName);
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
						strResource, false, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepStatReasonDetail"),
								true);
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	    log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");
		log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strUsrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName, statPtTypeName,
						statShTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statTypeName, strSTvalue[0],
						strStatusName1, strStatusValue[0], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statPtTypeName, strSTvalue[1],
						strStatusName3, strPtStatusValue[0], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statShTypeName, strSTvalue[2],
						strStatusName5, strShStatusValue[0], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String[] strArStatus = { strStatusName1, strStatusName3,
					strStatusName5 };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource, strArStatus);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strUpdTime1 = "";
			String[] strArFunRes = new String[5];
			String strCurrYear = dts.getCurrentDate("yyyy");
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objViewMap.getUpdTimeValue(selenium, "1");
				strFuncResult = strArFunRes[4];
				strCurrDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurrYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strUpdTime1 = strArFunRes[2] + ":" + strArFunRes[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonDetail(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep
						.enterRepDetailsAndGenStatusReasonDetRep_SelAll(
								selenium, strRSValue, statTypeName,
								strCSTApplTime, strCSTApplTime, true,
								strStatusValue[0], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes1 = new String[5];
			String strRepGenTime1 = "";
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strArFunRes1 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes1[4];

				strRepGenTime1 = strArFunRes1[2] + ":" + strArFunRes1[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName, statPtTypeName,
						statShTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statTypeName, strSTvalue[0],
						strStatusName2, strStatusValue[1], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statPtTypeName, strSTvalue[1],
						strStatusName4, strPtStatusValue[1], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statShTypeName, strSTvalue[2],
						strStatusName6, strShStatusValue[1], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String[] strArStatus1 = { strStatusName2, strStatusName4,
					strStatusName6 };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource, strArStatus1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			String strUpdTime2 = "";
			String[] strArFunRes2 = new String[5];

			try {
				assertEquals("", strFuncResult);
				strArFunRes2 = objViewMap.getUpdTimeValue(selenium, "1");
				strFuncResult = strArFunRes2[4];
				strCurrDate = dts.converDateFormat(strArFunRes2[0]
						+ strArFunRes2[1] + strCurrYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strUpdTime2 = strArFunRes2[2] + ":" + strArFunRes2[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonDetail(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep
						.enterRepDetailsAndGenStatusReasonDetRep_SelAll(
								selenium, strRSValue, statPtTypeName,
								strCSTApplTime, strCSTApplTime, true,
								strPtStatusValue[1], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strArFunRes3 = new String[5];
			String strRepGenTime2 = "";
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strArFunRes3 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes3[4];

				strRepGenTime2 = strArFunRes3[2] + ":" + strArFunRes3[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonDetail(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");

				strFuncResult = objRep
						.enterRepDetailsAndGenStatusReasonDetRep_SelAll(
								selenium, strRSValue, statShTypeName,
								strCSTApplTime, strCSTApplTime, true,
								strShStatusValue[1], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath3 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strArFunRes5 = new String[5];
			String strRepGenTime3 = "";
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strArFunRes5 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes5[4];

				strRepGenTime3 = strArFunRes5[2] + ":" + strArFunRes5[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strUsrPassword;
				strTestData[3] = strResType;
				strTestData[4] = strResource;
				strTestData[5] = "Role Based ST: " + statTypeName
						+ ", Private ST: " + statPtTypeName + ", Shared ST: "
						+ statShTypeName;
				strTestData[6] = strStatusName1 + "," + strStatusName2 + ","
						+ strStatusName3 + "," + strStatusName4 + ","
						+ strStatusName5 + "," + strStatusName6;
				strTestData[7] = strTestData[7] = "Check the Status Summary details in PDF file(Step 6): "
						+ strPDFDownlPath1
						+ ", Status updated: "
						+ strStatusName1
						+ ",Stauts Reason selected: "
						+ strReasonName1
						+ ","
						+ strReasonName2
						+ ",Start Date and End Date: "
						+ strCurrDate
						+ ", Status Update time: "
						+ strUpdTime1
						+ "Report Generation Time: "
						+ strRepGenTime1
						+ "Check the Status Summary details in PDF file(Step 9): "
						+ strPDFDownlPath2
						+ ", Status updated: "
						+ strStatusName4
						+ ",Stauts Reason selected: "
						+ strReasonName1
						+ ","
						+ strReasonName2
						+ ",Start Date and End Date: "
						+ strCurrDate
						+ ", Status Update time: "
						+ strUpdTime2
						+ "Report Generation Time: "
						+ strRepGenTime2
						+ "Check the Status Summary details in PDF file(Step 10): "
						+ strPDFDownlPath3
						+ ", Status updated: "
						+ strStatusName6
						+ ",Stauts Reason selected: "
						+ strReasonName1
						+ ","
						+ strReasonName2
						+ ",Start Date and End Date: "
						+ strCurrDate
						+ ", Status Update time: "
						+ strUpdTime2
						+ "Report Generation Time: " + strRepGenTime3;

				String strWriteFilePath = propPathDetails
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Reports");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-103967";
			gstrTO = "Generate 'Status Reason Detail report' in PDF format"
					+ " and verify that the report displays correct data";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	
	/************************************************************************
	'Description	:Generate 'Status Reason Detail report' in CSV format and 
	                 verify that the report displays correct data. 
	'Arguments		:None
	'Returns		:None
	'Date			:04-12-2012
	'Author			:QSG
	'------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	**************************************************************************/
	
	@Test
	public void testBQS103968() throws Exception {
		String strFuncResult = "";
		CSVFunctions objCSV = new CSVFunctions();
		General objGen = new General();
		StatusReason objStatRes = new StatusReason();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		try {
			gstrTCID = "BQS-103968"; // Test Case Id
			gstrTO = "Generate 'Status Reason Detail report' in CSV format and"
					+ " verify that the report displays correct data.";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			propPathDetails = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTestData[] = new String[10];
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String statTypeName = "AutoMSt_" + strTimeText;
			String statPtTypeName = "AutopMSt_" + strTimeText;
			String statShTypeName = "AutosMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";
			String strSTvalue[] = new String[3];
			strSTvalue[0] = "";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusName3 = "Stc" + strTimeText;
			String strStatusName4 = "Std" + strTimeText;
			String strStatusName5 = "Ste" + strTimeText;
			String strStatusName6 = "Stf" + strTimeText;
			String strStatusValue[] = new String[2];
			String strPtStatusValue[] = new String[2];
			String strShStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			strRSValue[0] = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strRoleForUser = "AutoRL" + strTimeText;
			String strRoleRitsForUsr[][] = {};
			String[] strRlUpdRtForUsr = {};
			String strRoleUsrVal = "";

			String strReasonName1 = "AutoReas1_" + System.currentTimeMillis();
			String strReasonName2 = "AutoReas2_" + System.currentTimeMillis();
			String[] strReasonVal = new String[2];

			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			String strUpdPwd = "abc123";
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			Properties pathProps = objAP.Read_FilePath();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUpdMulValue = "";
			String strUpdValCheck = "";

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath1 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".csv";
			String strCSVDownlPath2 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep2_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".csv";

			String strCSVDownlPath3 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep3_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".csv";

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

		 log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");

			
			strFuncResult = objLogin.login(seleniumPrecondition,
					strAdmUserName, strAdmPassword);
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes
						.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(
						seleniumPrecondition, strReasonName1, "", "", true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[0] = objStatRes.fetchStatReasonValue(
						seleniumPrecondition, strReasonName1);
				if (strReasonVal[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(
						seleniumPrecondition, strReasonName2, "", "", true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[1] = objStatRes.fetchStatReasonValue(
						seleniumPrecondition, strReasonName2);
				if (strReasonVal[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectAndDeselectStatusReason(
							seleniumPrecondition, strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
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
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName, strStatusName1,
						strStatusTypeValue, strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(
									seleniumPrecondition, strReasonVal[intSR],
									true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName, strStatusName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName, strStatusName2,
						strStatusTypeValue, strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(
									seleniumPrecondition, strReasonVal[intSR],
									true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName, strStatusName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statPtTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypeVisibility(
						seleniumPrecondition, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectAndDeselectStatusReason(
							seleniumPrecondition, strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statPtTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statPtTypeName);
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
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statPtTypeName, strStatusName3,
						strStatusTypeValue, strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(
									seleniumPrecondition, strReasonVal[intSR],
									true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statPtTypeName, strStatusName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statPtTypeName, strStatusName4,
						strStatusTypeValue, strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(
									seleniumPrecondition, strReasonVal[intSR],
									true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statPtTypeName, strStatusName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statPtTypeName, strStatusName3);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strPtStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statPtTypeName, strStatusName4);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strPtStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statShTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypeVisibility(
						seleniumPrecondition, true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectAndDeselectStatusReason(
							seleniumPrecondition, strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statShTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statShTypeName);
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
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statShTypeName, strStatusName5,
						strStatusTypeValue, strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(
									seleniumPrecondition, strReasonVal[intSR],
									true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statShTypeName, strStatusName5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statShTypeName, strStatusName6,
						strStatusTypeValue, strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(
									seleniumPrecondition, strReasonVal[intSR],
									true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statShTypeName, strStatusName6);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statShTypeName, strStatusName5);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strShStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statShTypeName, strStatusName6);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strShStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResType,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 1; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(
							seleniumPrecondition, strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResType);
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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResType, strContFName, strContLName, strState,
						strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource);

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
			// create role to update

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
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName, strUsrPassword,
						strUsrPassword, strUsrFulName);
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
				strFuncResult = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource, false, true, true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepStatReasonDetail"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");
		log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strUsrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName, statPtTypeName,
						statShTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statTypeName, strSTvalue[0],
						strStatusName1, strStatusValue[0], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statPtTypeName, strSTvalue[1],
						strStatusName3, strPtStatusValue[0], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statShTypeName, strSTvalue[2],
						strStatusName5, strShStatusValue[0], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String[] strArStatus = { strStatusName1, strStatusName3,
					strStatusName5 };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource, strArStatus);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strUpdTime1 = "";
			String[] strArFunRes = new String[5];
			String strCurrYear = dts.getCurrentDate("yyyy");
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objViewMap.getUpdTimeValue(selenium, "1");
				strFuncResult = strArFunRes[4];
				strCurrDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurrYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strUpdTime1 = strArFunRes[2] + ":" + strArFunRes[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonDetail(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");

				strFuncResult = objRep
						.enterRepDetailsAndGenStatusReasonDetRep_SelAll(
								selenium, strRSValue, statPtTypeName,
								strCSTApplTime, strCSTApplTime, false,
								strPtStatusValue[0], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strArFunRes1 = new String[5];
			String strRepGenTime1 = "";
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strArFunRes1 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes1[4];
				strRepGenTime1 = strArFunRes1[2] + ":" + strArFunRes1[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName, statPtTypeName,
						statShTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statTypeName, strSTvalue[0],
						strStatusName2, strStatusValue[1], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statPtTypeName, strSTvalue[1],
						strStatusName4, strPtStatusValue[1], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statShTypeName, strSTvalue[2],
						strStatusName6, strShStatusValue[1], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String[] strArStatus1 = { strStatusName2, strStatusName4,
					strStatusName6 };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource, strArStatus1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			String strUpdTime2 = "";
			String[] strArFunRes2 = new String[5];

			try {
				assertEquals("", strFuncResult);
				strArFunRes2 = objViewMap.getUpdTimeValue(selenium, "1");
				strFuncResult = strArFunRes2[4];
				strCurrDate = dts.converDateFormat(strArFunRes2[0]
						+ strArFunRes2[1] + strCurrYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strUpdTime2 = strArFunRes2[2] + ":" + strArFunRes2[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonDetail(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep
						.enterRepDetailsAndGenStatusReasonDetRep_SelAll(
								selenium, strRSValue, statTypeName,
								strCSTApplTime, strCSTApplTime, false,
								strStatusValue[1], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strArFunRes3 = new String[5];
			String strRepGenTime2 = "";
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strArFunRes3 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes3[4];
				strRepGenTime2 = strArFunRes3[2] + ":" + strArFunRes3[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonDetail(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep
						.enterRepDetailsAndGenStatusReasonDetRep_SelAll(
								selenium, strRSValue, statShTypeName,
								strCSTApplTime, strCSTApplTime, false,
								strShStatusValue[1], strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath3 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strArFunRes5 = new String[5];
			String strRepGenTime3 = "";
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strArFunRes5 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes5[4];

				strRepGenTime3 = strArFunRes5[2] + ":" + strArFunRes5[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strUsrPassword;
				strTestData[3] = strResType;
				strTestData[4] = strResource;
				strTestData[5] = "Role Based ST: " + statTypeName
						+ ", Private ST: " + statPtTypeName + ", Shared ST: "
						+ statShTypeName;
				strTestData[6] = strStatusName1 + "," + strStatusName2 + ","
						+ strStatusName3 + "," + strStatusName4 + ","
						+ strStatusName5 + "," + strStatusName6;
				strTestData[7] = strTestData[7] = "Check the Status Summary details in CSV file(Step 7): "
						+ strCSVDownlPath1
						+ ", Status updated: "
						+ strStatusName3
						+ ",Stauts Reason selected: "
						+ strReasonName1
						+ ","
						+ strReasonName2
						+ ",Start Date and End Date: "
						+ strCurrDate
						+ ", Status Update time: "
						+ strUpdTime1
						+ "Report Generation Time: "
						+ strRepGenTime1
						+ "Check the Status Summary details in CSV file(Step 11): "
						+ strCSVDownlPath2
						+ ", Status updated: "
						+ strStatusName2
						+ ",Stauts Reason selected: "
						+ strReasonName1
						+ ","
						+ strReasonName2
						+ ",Start Date and End Date: "
						+ strCurrDate
						+ ", Status Update time: "
						+ strUpdTime2
						+ "Report Generation Time: "
						+ strRepGenTime2
						+ "Check the Status Summary details in CSV file(Step 13): "
						+ strCSVDownlPath3
						+ ", Status updated: "
						+ strStatusName6
						+ ",Stauts Reason selected: "
						+ strReasonName1
						+ ","
						+ strReasonName2
						+ ",Start Date and End Date: "
						+ strCurrDate
						+ ", Status Update time: "
						+ strUpdTime2
						+ "Report Generation Time: " + strRepGenTime3;

				String strWriteFilePath = propPathDetails
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Reports");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-103968";
			gstrTO = "Generate 'Status Reason Detail report' in CSV "
					+ "format and verify that the report displays correct data. ";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	
	/***************************************************************
	'Description		:Update status of a multi status type MST with a status reason where MST is added at the resource level for resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role with only VIEW right for MST can view the details of selected reason in the generated 'Status Reason Detail Report'.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:06-07-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************//*

	@Test
	public void testBQS4274() throws Exception {
	try{	
		gstrTCID = "42745";	//Test Case Id	
		gstrTO = " Update status of a multi status type MST with a status reason where MST is added at the resource level for resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role with only VIEW right for MST can view the details of selected reason in the generated 'Status Reason Detail Report'.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

		log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake=dts.timeNow("hh:mm:ss");	

		String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
		String strLoginUserName, strLoginPassword, strRegn, strMandReasonName, strDefn, strAbbreviation, strReasonName, strStatusTypeValue, strStatTypDefn, strStatusType, strReasonVal, strMultiStatTypeName, strStatusName, strStatTypeColor,strStatus, strResrctTypName, strStstTypLabl, strResrcTypName, strResName, strRSAbbr, strRTLable, strFName, strLName, strState, strCountry, strStdRT, strResource, strSTValue, strRoleName, strRoleRights, strViewRightValue, strRole, strUserName, strInitPwd, strConfirmPwd, strUsrFulName, strRoleValue, strOptions, strEventStatType, strRoleStatType, strMultiST, strRoleStatTypeValue, strStatus1, strStatusValue1, strStatus2, strStatusValue2, strStatReasonVal1, strStatReasonVal2, pStrStatTypeIndex;
		boolean blnOptn, blnSav, blnSelect, blnSelct, blnViewRight, blnUpdateRight, blnSave, blnAssocWith, blnUpdStat, blnRunReport, blnViewRes, blnselectRole, blnOptions, blnCheckEveStat, blnCheckRoleStat;

		String strFuncResult = "";

		Login objLogin = new Login();
		StatusReason objStatusReason = new StatusReason();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		ViewMap objViewMap = new ViewMap();

		
		* STEP :
		  Action:Preconditions:
	<br>
	<br>1. Resource type RT is associated with status type ST1.
	<br>
	<br>2. Status type MST(multi status type) is created selecting status reason SR1
	<br>
	<br>3. Resources RS are created under RT.
	<br>
	<br>4. Status Type MST is added for resource RS at the resource level.
	<br>
	<br>5. User U1 has following rights:
	<br>
	<br>a. Report - Status Reason Detail.
	<br>b. Role to View status type MST.
	<br>c.'View Status' and 'Run Report' rights on resources RS.
	<br>
	<br>6.Status Type MST is updated with status reason on day D1.
		  Expected Result:No Expected Result
		
		//271027

		strLoginUserName=objrdExcel.ReadData("Login", 3, 1);;
		strLoginPassword=objrdExcel.ReadData("Login", 3, 2);;


		strFuncResult =objLogin.login(selenium, strLoginUserName, strLoginPassword);

		strRegn=objrdExcel.ReadData("Login", 3, 4);;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusReason.navStatusReasonList(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strMandReasonName="AutoReas_"+System.currentTimeMillis();;
		strDefn="";
		strAbbreviation="";
		blnOptn=true;
		blnSav=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusReason.createStatusReasn(selenium, strMandReasonName, strDefn, strAbbreviation, blnOptn, blnSav);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strReasonName="AutoReas_"+System.currentTimeMillis();;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusReason.fetchStatReasonValue(selenium, strReasonName);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.navStatusTypList(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strStatusTypeValue="Number";
		statTypeName="AutoNst_"+strTimeText;
		strStatTypDefn="Auto";
		blnSav=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(selenium, strStatusTypeValue, statTypeName, strStatTypDefn, blnSav);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strStatusType="AutoNst_"+strTimeText;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.fetchSTValueInStatTypeList(selenium, strStatusType);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.navStatusTypList(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strStatusTypeValue="Multi";
		statTypeName="AutoMSt_"+strTimeText;
		strStatTypDefn="Auto";
		blnSav=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(selenium, strStatusTypeValue, statTypeName, strStatTypDefn, blnSav);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strReasonVal="";
		blnSelect=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.selectAndDeselectStatusReason(selenium, strReasonVal, blnSelect);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		 strStatusType="AutoMSt_"+strTimeText;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.fetchSTValueInStatTypeList(selenium, strStatusType);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strMultiStatTypeName="AutoMSt_"+strTimeText;
		strStatusName="Sta"+strTimeText;
		strStatusTypeValue="Multi";
		strStatTypeColor="Black";
		blnSav=false;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.createSTWithinMultiTypeST(selenium, strMultiStatTypeName, strStatusName, strStatusTypeValue, strStatTypeColor, blnSav);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strReasonVal="";
		blnSelect=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusReason.selectAndDeselectStatusReasInStatus(selenium, strReasonVal, blnSelect);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strMultiStatTypeName="AutoMSt_"+strTimeText;;
		strStatusName="Sta"+strTimeText;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.saveAndVerifyStatus(selenium, strMultiStatTypeName, strStatusName);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strMultiStatTypeName="AutoMSt_"+strTimeText;
		strStatus="Sta"+strTimeText;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.fetchStatValInStatusList(selenium, strMultiStatTypeName, strStatus);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strMultiStatTypeName="AutoMSt_"+strTimeText;
		strStatusName="Stb"+strTimeText;;
		strStatusTypeValue="Multi";
		strStatTypeColor="Black";
		blnSav=false;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.createSTWithinMultiTypeST(selenium, strMultiStatTypeName, strStatusName, strStatusTypeValue, strStatTypeColor, blnSav);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strReasonVal="";
		blnSelect=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusReason.selectAndDeselectStatusReasInStatus(selenium, strReasonVal, blnSelect);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strMultiStatTypeName="AutoMSt_"+strTimeText;
		strStatusName="Stb"+strTimeText;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.saveAndVerifyStatus(selenium, strMultiStatTypeName, strStatusName);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strMultiStatTypeName="AutoMSt_"+strTimeText;;
		strStatus="Sta"+strTimeText;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.fetchStatValInStatusList(selenium, strMultiStatTypeName, strStatus);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strMultiStatTypeName="AutoMSt_"+strTimeText;
		strStatus="Stb"+strTimeText;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.fetchStatValInStatusList(selenium, strMultiStatTypeName, strStatus);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objResourceTypes.navResourceTypList(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strResrctTypName="AutoRt_"+strTimeText;
		strStstTypLabl="css=input[name='statusTypeID'][value='"+strSTAsnVal[0]+"']";


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(selenium, strResrctTypName, strStstTypLabl);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strResrcTypName="AutoRt_"+strTimeText;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objResourceTypes.saveAndvrfyResType(selenium, strResrcTypName);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objResources.navResourcesList(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strResName="AutoRs_"+strTimeText;
		strRSAbbr="Rs";
		strRTLable="AutoRt_"+strTimeText;
		strFName="auto";
		strLName="qsg";
		strState="Alabama";
		strCountry="Autauga County";
		strStdRT="Aeromedical";


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objResources.createResourceWitLookUPadres(selenium, strResName, strRSAbbr, strRTLable, strFName, strLName, strState, strCountry, strStdRT);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objResources.fetchResValueInResList(selenium, strResource);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strResource="AutoRs_"+strTimeText;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objResources.navToEditResLevelSTPage(selenium, strResource);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strSTValue="";
		blnSelct=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objResources.selDeselctSTInEditRSLevelPage(selenium, strSTValue, blnSelct);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objRoles.navRolesListPge(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strRoleName="AutoR_"+strTimeText;
		strRoleRights={};
		strViewRightValue="";
		blnViewRight=true;
		updateRightValue="";
		blnUpdateRight=true;
		blnSave=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objRoles.CreateRoleWithAllFields(selenium, strRoleName, strRoleRights, strViewRightValue, blnViewRight, updateRightValue, blnUpdateRight, blnSave);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strRole="AutoR_"+strTimeText;;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objRoles.fetchRoleValueInRoleList(selenium, strRole);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strUserName= "AutoUpdUsr"+System.currentTimeMillis();
		strInitPwd="abc123";
		strConfirmPwd="abc123";
		strUsrFulName="autouser";


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium, strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strResource="AutoRs_"+strTimeText;
		blnAssocWith=false;
		blnUpdStat=true;
		blnRunReport=false;
		blnViewRes=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.selectResourceRights(selenium, strResource, blnAssocWith, blnUpdStat, blnRunReport, blnViewRes);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strRoleValue="";
		blnselectRole=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.slectAndDeselectRole(selenium, strRoleValue, blnselectRole);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strUserName="AutoUpdUsr"+System.currentTimeMillis();


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.savVrfyUser(selenium, strUserName);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strUserName="AutoUsr"+System.currentTimeMillis();
		strInitPwd="abc123";
		strConfirmPwd="abc123";
		strUsrFulName="autouser";


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium, strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strResource="AutoRs_"+strTimeText;
		blnAssocWith=false;
		blnUpdStat=false;
		blnRunReport=true;
		blnViewRes=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.selectResourceRights(selenium, strResource, blnAssocWith, blnUpdStat, blnRunReport, blnViewRes);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strOptions= propElementDetails.getProperty("CreateNewUsr.AdvOptn.RepStatReasonDetail");
		blnOptions=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.advancedOptns(selenium, strOptions, blnOptions);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strRoleValue="";
		blnselectRole=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.slectAndDeselectRole(selenium, strRoleValue, blnselectRole);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strUserName="AutoUsr"+System.currentTimeMillis();


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.savVrfyUser(selenium, strUserName);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.logout(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strLoginUserName="AutoUpdUsr"+System.currentTimeMillis();
		strLoginPassword="abc123";


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.newUsrLogin(selenium, strLoginUserName, strLoginPassword);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strResource="AutoRs_"+strTimeText;
		strEventStatType={};
		strRoleStatType=new String[1];
		blnCheckEveStat=false;
		blnCheckRoleStat=true;


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objViewMap.verifyStatTypesInResourcePopup(selenium, strResource, strEventStatType, strRoleStatType, blnCheckEveStat, blnCheckRoleStat);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		strResource="AutoRs_"+strTimeText;
		strMultiST="AutoMSt_"+strTimeText;
		strRoleStatTypeValue="";
		strStatus1="Sta"+strTimeText;
		strStatusValue1="";
		strStatus2="Stb"+strTimeText;
		strStatusValue2="";
		strStatReasonVal1="";
		strStatReasonVal2="";


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objViewMap.updateMultiStatusTypeWithReason(selenium, strResource, strMultiST, strRoleStatTypeValue, strStatus1, strStatusValue1, strStatus2, strStatusValue2, strStatReasonVal1, strStatReasonVal2);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		pStrStatTypeIndex="1";


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objViewMap.getUpdSTValueInMap(selenium, pStrStatTypeIndex);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.logout(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		
		* STEP :
		  Action:Login as User U1, navigate to Reports>>Status Reports, click on 'Status Reason Detail'.
		  Expected Result:'Status Reason Detail Report (Step 1 of 3)' screen is displayed.
	<br>
	<br>(by default Adobe Acrobat (PDF) format is selected).
		
		//271036

		
		* STEP :
		  Action:Provide D1 as the date for 'Start Date' and 'End Date' fields, then click on 'Next'.
		  Expected Result:'Status Reason Detail Report (Step 2 of 3)' screen is displayed.
		
		//271037

		
		* STEP :
		  Action:Select status type 'MST' from 'Status Type' dropdown, then click on 'Next'.
		  Expected Result:'Status Reason Detail Report (Step 3 of 3)' screen is displayed.
		
		//271038

		
		* STEP :
		  Action:Select status reasons from 'Status Reasons' section and click on 'Generate Report'.
		  Expected Result:Status Reason Detail Report is generated in the PDF format.
	<br>
	<br>Header and Footer are displayed in all the pages of the report with following details.
	<br>
	<br>Header:
	<br>1. Start Date
	<br>2. End Date
	<br>3. Status Type
	<br>
	<br>Footer:
	<br>1. Report Run By: (name of the user)
	<br>2. From: (Name of the Region)
	<br>3. On: MM/DD/YYYY HH:MM:SS (Time Zone)
	<br>4. Intermedix Emsystems logo
	<br>5. Page number
	<br>
	<br>Details of resource RS1 is displayed appropriately in successive pages with following:
	<br>
	<br>1. Status
	<br>2. Status Start Date
	<br>3. Comment
	<br>4. Status End Date
	<br>5. Status Reason
	<br>6. Duration
	<br>7. User
	<br>8. IP
	<br>9. Trace
		
		//271039

		
		* STEP :
		  Action:Navigate to Reports >> Status Reports, click on 'Status Reason Summary'.
		  Expected Result:'Status Reason Detail Report (Step 1 of 3)' screen is displayed.
	<br>
	<br>(by default Adobe Acrobat (PDF) format will be selected)
		
		//271040

		
		* STEP :
		  Action:Provide D1 as the date for 'Start Date' and 'End Date' fields, select 'Data File, Comma-separated (CSV)' format, select resource RS, and click on 'Next'.
		  Expected Result:'Status Reason Detail Report (Step 2 of 3)' screen is displayed.
		
		//271041

		
		* STEP :
		  Action:Select status type 'MST' from 'Status Type' dropdown, then click on 'Next'.
		  Expected Result:'Status Reason Detail Report (Step 3 of 3)' screen is displayed.
		
		//271042

		
		* STEP :
		  Action:Select status reasons from 'Status Reasons' section and click on 'Generate Report'.
		  Expected Result:Status Reason Detail Report is generated in the CSV (Comma Separated Values)format.
	<br>
	<br>The following columns are displayed with appropriate data:
	<br>
	<br>1. Resource
	<br>2. Status
	<br>3. Status Reason
	<br>4. Status Start Date
	<br>5. Status End Date
	<br>6. Duration
	<br>7. User
	<br>8. IP
	<br>9. Trace
	<br>10.Comments
		
		//271043

		try{
			assertEquals("", strFuncResult);
			gstrResult = "PASS";
		}
		catch (AssertionError Ae)
		{
			gstrResult = "FAIL";
			gstrReason = gstrReason+" "+strFuncResult;
		}

		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "BQS-42745";
		gstrTO = "Update status of a multi status type MST with a status reason where MST is added at the resource level for resource RS. Verify that a user with 'Run Report' & 'View Resource' rights on RS and with a role with only VIEW right for MST can view the details of selected reason in the generated 'Status Reason Detail Report'.";
		gstrResult = "FAIL";
		log4j.info(e);
		log4j.info("========== Test Case '" + gstrTCID
		+ "' has FAILED ==========");
		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
		log4j.info("----------------------------------------------------------");
		gstrReason = e.toString();
	   }
	}*/


}
