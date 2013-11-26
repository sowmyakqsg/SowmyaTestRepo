package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.CSVFunctions;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.GetProcessList;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/********************************************************************
' Description :This class includes Create User requirement testcases
' Date		  :4-April-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                                     Modified By
' <Date>                           	                <Name>
'*******************************************************************/

public class CreateUserAutoIT  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.CreateUser");
	static{
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
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
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
		try {
			
			seleniumPrecondition.close();
		} catch (Exception e) {
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
		    gslsysDateTime, gstrBrowserName, gstrBuild,strSessionId);
	}

	/************************************************************************************************
	'Description	: Verify that a user can be provided the right to generate Status Summary report.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:17-08-2012
	'Author			:QSG
	'------------------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	*************************************************************************************************/
	@Test
	public void testBQS68131() throws Exception {
		try {
			gstrTCID = "BQS-68131"; // Test Case Id
			gstrTO = " Verify that a user can be provided the right to generate Status Summary report.";
																																// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();
					
			propElementAutoItDetails=objAP.ReadAutoit_FilePath();
			String strTestData[]=new String[10];
			CSVFunctions objCSV=new CSVFunctions();
	
			StatusTypes objST=new StatusTypes();
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			CreateUsers objCreateUsers=new CreateUsers();
			
			Roles objRole=new Roles();
			
			String strFuncResult="";
		
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login
		
			ViewMap objViewMap=new ViewMap();
			Reports objRep=new Reports();
				
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login", 3, 4);
			
			String strStatusTypeValue="Multi";
			String statTypeName="AutoMSt_"+strTimeText;
			String strStatTypDefn="Auto";
			String strStatTypeColor="Black";
			
			String strAbbrv="Rs";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String[] strRoleValue=new String[2];
						
			String strUsrFulName="autouser";
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strUsrPassword="abc123";
			
			String strUpdUsrName= "AutoUpdUsr"+System.currentTimeMillis();
			String strUpdPwd= "abc123";			
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
				
			String strState="Alabama";
			String strCountry="Autauga County";
					
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strResource="AutoRs_"+strTimeText;
			String strResType="AutoRt_"+strTimeText;	
							
			String strSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
		
			String strStatusValue[]=new String[2];
			strStatusValue[0]="";
			strStatusValue[1]="";
			
			strSTvalue[0]="";			
			String strAutoFilePath=propElementAutoItDetails.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"StatSnapRep_"+gstrTCID+"_"+strTimeText+".csv";
			String strPDFDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"StatSummary_"+gstrTCID+"_"+strTimeText+".pdf";
			String strAutoFileName=propElementAutoItDetails.getProperty("Reports_DownloadFile_Name");
			@SuppressWarnings("unused")
			String strCurrDate=dts.getCurrentDate("MM/dd/yyyy");
		
			String[][] strReportData={{"Status Summary","**","**","**","**"},
					  {"Status Type","Resource","Status","Total Hours","% of Total Hours"},
					  {statTypeName,strResource,strStatusName1,"\\d+\\.\\d+","\\d+"},
					  {statTypeName,strResource,strStatusName2,"\\d+\\.\\d+","\\d+"},
					  {"**","**","**","**","**"},
					  {"Aggregate Status Summary","**","**","**","**"},
					  {"Status Type","Resource Type","Status","Total Hours","% of Total Hours"},
					  {statTypeName,strResType,strStatusName1,"\\d+\\.\\d+","\\d+"},
					  {statTypeName,strResType,strStatusName2,"\\d+\\.\\d+","\\d+"}
					  };
	
			String strRoleForUser="AutoRL"+strTimeText;
			String strRoleRitsForUsr[][]={};
			
		
			String []strRlUpdRtForUsr={};
			String []strRlViewRtForUsr={};
			String strRoleUsrVal="";
			
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
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//create role for user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleForUser, strRoleRitsForUsr, strRlViewRtForUsr, true, strRlUpdRtForUsr, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRoleUsrVal = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleForUser);
				
				if(strRoleUsrVal.compareTo("")!=0){
					strFuncResult="";
					
				}else{
					strFuncResult="Failed to fetch Role value";
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strStatusTypeValue, statTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				String strRoleToSel[]={strRoleUsrVal};
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false, false, strRoleToSel, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition, statTypeName, strStatusName1, strStatusTypeValue, strStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition, statTypeName, strStatusName2, strStatusTypeValue, strStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,statTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,statTypeName, strStatusName2);
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResType, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition, strResource, strAbbrv, strResType, strContFName, strContLName,strState,strCountry,strStandResType);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
					
			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
				
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
			//create role to update
									
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleName);
				
				if(strRoleValue[0].compareTo("")!=0){
					strFuncResult="";
					
				}else{
					strFuncResult="Failed to fetch Role value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			//Update user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strUpdUsrName, strUpdPwd, strUpdPwd, strUsrFulName);
				
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue[0], true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUpdUsrName, strByRole,
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
				String[] strRoleStatType = {statTypeName};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objViewMap.updateMultiStatusType_ChangeVal(selenium,
							strResource, statTypeName, strSTvalue[0],
							strStatusName1,strStatusValue[0],strStatusName2,strStatusValue[1]);
				
			
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
				strFuncResult = objLogin.login(selenium, strAdmUserName,
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
				strFuncResult = objCreateUsers.selectResourceRights(selenium, strResource, false, false, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.RepStatSummary"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium, strRoleUsrVal, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

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
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime=dts.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

				strFuncResult = objRep.enterReportDetAndGenReport(selenium,
						strRSValue[0], strSTvalue[0], strCSTApplTime, strCSTApplTime,
						true);
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
				strFuncResult = objRep.navToStatusSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime=dts.getTimeOfParticularTimeZone("CST", "M/d/yyyy");
				strFuncResult = objRep.enterReportDetAndGenReport(selenium,
						strRSValue[0], strSTvalue[0], strCSTApplTime, strCSTApplTime,
						false);
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
				strTestData[6]=strStatusName1+","+strStatusName2;
				strTestData[5]="Check the Status Summary details in PDF file: "+strPDFDownlPath;
				
				String strWriteFilePath=pathProps.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Reports");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}				
													
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-68131";
			gstrTO = " Verify that a user can be provided the right to generate Status Summary report.";
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
	
	/***************************************************************
	'Description		:Verify that a user can be provided the right to generate Status Reason Summary report.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:20-08-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	@Test
	public void testBQS68159() throws Exception {
		try {
			gstrTCID = "BQS-68159"; // Test Case Id
			gstrTO = "Verify that a user can be provided the right to" +
					" generate Status Reason Summary report.";
																																// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();
					
			propElementAutoItDetails=objAP.ReadAutoit_FilePath();
			String strTestData[]=new String[10];
			CSVFunctions objCSV=new CSVFunctions();
	
			StatusReason objStatRes=new StatusReason();
			StatusTypes objST=new StatusTypes();
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			CreateUsers objCreateUsers=new CreateUsers();
			
			Roles objRole=new Roles();
			
			String strFuncResult="";
		
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login
		
			ViewMap objViewMap=new ViewMap();
			Reports objRep=new Reports();
				
			String strUpdMulValue="";
			String strUpdValCheck="";
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login", 3, 4);
			
			String strStatusTypeValue="Multi";
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
			
			String strUpdUsrName= "AutoUpdUsr"+System.currentTimeMillis();;
			String strUpdPwd= "abc123";			
			
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
				
			String strState="Alabama";
			String strCountry="Autauga County";
					
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strResource="AutoRs_"+strTimeText;
			String strResType="AutoRt_"+strTimeText;	
							
			String strSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
		
			String strStatusValue[]=new String[2];
			strStatusValue[0]="";
			strStatusValue[1]="";
			
			strSTvalue[0]="";	
			strRSValue[0]="";
			String strAutoFilePath=propElementAutoItDetails.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"StatReasSumRep_"+gstrTCID+"_"+strTimeText+".csv";
			String strPDFDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"StatReasSumRep_"+gstrTCID+"_"+strTimeText+".pdf";
			String strAutoFileName=propElementAutoItDetails.getProperty("Reports_DownloadFile_Name");
			@SuppressWarnings("unused")
			String strCurrDate=dts.getCurrentDate("MM/dd/yyyy");
					
			String strRoleForUser="AutoRL"+strTimeText;
			String strRoleRitsForUsr[][]={};
			
		
			String []strRlUpdRtForUsr={};
			String []strRlViewRtForUsr={};
			String strRoleUsrVal="";
			
			String strReasonName="AutoReas_"+System.currentTimeMillis();
			String strReasonVal="";
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,strAdmUserName,
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
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// create role for user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleForUser, strRoleRitsForUsr, strRlViewRtForUsr,
						true, strRlUpdRtForUsr, true, true);

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
				String strRoleToSel[] = { strRoleUsrVal };
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false,
						false, strRoleToSel, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(
						seleniumPrecondition, strReasonVal, true);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName, strStatusName2, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(
						seleniumPrecondition, strReasonVal, true);
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
						strResource, false, true, false, true);

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

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUpdUsrName, strByRole, strByResourceType,
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUpdUsrName,
						strUpdPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusTypeWithReason(
						selenium, strResource, statTypeName, strSTvalue[0],
						strStatusName1, strStatusValue[0], strStatusName2,
						strStatusValue[1], strReasonVal, strReasonVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdSTValueInMap(selenium, "1");
				strFuncResult = strArFunRes[1];
				strUpdMulValue = strArFunRes[0];

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
				strFuncResult = objLogin.login(selenium, strAdmUserName,
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
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strUsrPassword, strUsrPassword,
						strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(selenium,
						strResource, false, false, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepStatReasonSummary"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleUsrVal, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium,
						strUserName, strByRole, strByResourceType,
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
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime=dts.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep(
						selenium, strRSValue[0], statTypeName, strCSTApplTime,
						strCSTApplTime, true, strUpdValCheck, strReasonVal);
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
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime=dts.getTimeOfParticularTimeZone("CST", "M/d/yyyy");
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep(
						selenium, strRSValue[0], statTypeName, strCSTApplTime,
						strCSTApplTime, false, strUpdValCheck, strReasonVal);
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
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strReportData = {
						{ "Status Reason Summary", "**", "**", "**", "**", "**" },
						{ "Resource", "Status", "Status Reason", "Time(hrs)",
								"% of all Hrs on this status", "% of Total Hrs" },

						{ strResource, strUpdMulValue, strReasonName,
								"\\d+\\.\\d+", "\\d+\\.\\d+", "\\d+\\.\\d+" }, };

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
				strTestData[5] = "Check the Status Summary details in PDF file: "
						+ strPDFDownlPath + "status reason: " + strReasonName;

				String strWriteFilePath = pathProps
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
			gstrTCID = "BQS-68159";
			gstrTO = "Verify that a user can be provided the right to generate Status Reason Summary report.";
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
	
	/***************************************************************
	'Description		:Verify that a user can be provided the right to generate Status Reason Detail report.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:20-08-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	
	@Test
	public void testBQS68196() throws Exception {
		try {
			gstrTCID = "BQS-68196"; // Test Case Id
			gstrTO = "Verify that a user can be provided the right" +
					" to generate Status Reason Detail report.";
																																// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();
					
			propElementAutoItDetails=objAP.ReadAutoit_FilePath();
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
		
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login
		
			ViewMap objViewMap=new ViewMap();
			Reports objRep=new Reports();
				
			String strUpdMulValue="";
			String strUpdValCheck="";
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login", 3, 4);
			
			String strStatusTypeValue="Multi";
				
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
			
			String strUpdUsrName= "AutoUpdUsr"+System.currentTimeMillis();;
			String strUpdPwd= "abc123";			
			
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
				
			String strState="Alabama";
			String strCountry="Autauga County";
					
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strResource="AutoRs_"+strTimeText;
			String strResType="AutoRt_"+strTimeText;	
							
			String strSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
					
			String strStatusValue[]=new String[2];
			strStatusValue[0]="";
			strStatusValue[1]="";
			
			strSTvalue[0]="";	
			strRSValue[0]="";
			
			String strGenDate="";
			String strAutoFilePath=propElementAutoItDetails.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"StatReasDetRep_"+gstrTCID+"_"+strTimeText+".csv";
			String strPDFDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"StatReasDetRep_"+gstrTCID+"_"+strTimeText+".pdf";
			String strAutoFileName=propElementAutoItDetails.getProperty("Reports_DownloadFile_Name");
			@SuppressWarnings("unused")
			String strCurrDate=dts.getCurrentDate("MM/dd/yyyy");
					
			String strRoleForUser="AutoRL"+strTimeText;
			String strRoleRitsForUsr[][]={};
			String []strRlViewRtForUsr={};
		
			String []strRlUpdRtForUsr={};
			String strRoleUsrVal="";
			
			String strReasonName="AutoReas_"+System.currentTimeMillis();
			String strReasonVal="";
			String strRegGenTime="";
			String strCurYear=dts.getCurrentDate("yyyy");
			String strLastUpdDate="";
			String strLastUpdTime="";
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,strAdmUserName,
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
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//create role for user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleForUser, strRoleRitsForUsr, strRlViewRtForUsr, true, strRlUpdRtForUsr, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRoleUsrVal = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleForUser);
				
				if(strRoleUsrVal.compareTo("")!=0){
					strFuncResult="";
					
				}else{
					strFuncResult="Failed to fetch Role value";
				}
				
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
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition, strReasonName, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strReasonVal=objStatRes.fetchStatReasonValue(seleniumPrecondition, strReasonName);
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
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strRoleToSel[]={strRoleUsrVal};
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false, false, strRoleToSel, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition, statTypeName, strStatusName1, strStatusTypeValue, strStatTypeColor,false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(seleniumPrecondition, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition, statTypeName, strStatusName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition, statTypeName, strStatusName2, strStatusTypeValue, strStatTypeColor,false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(seleniumPrecondition, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition, statTypeName, strStatusName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,statTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,statTypeName, strStatusName2);
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResType, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition, strResource, strAbbrv, strResType, strContFName, strContLName,strState,strCountry,strStandResType);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
					
			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
				
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
			//create role to update
									
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
			
					
			//Update user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strUpdUsrName, strUpdPwd, strUpdPwd, strUsrFulName);
				
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUpdUsrName, strByRole, strByResourceType,
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUpdUsrName,
						strUpdPwd);
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
				strUpdMulValue=strArFunRes[0];
				
							
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("", strFuncResult);
				String[] strArFunRes=new String[5];
				strArFunRes=objViewMap.getUpdTimeValue(selenium, "1");
				
				strLastUpdTime=strArFunRes[2]+":"+strArFunRes[3];
				
				strLastUpdDate=dts.converDateFormat(strArFunRes[0]+strArFunRes[1]+strCurYear, "ddMMMyyyy", "dd-MMM-yyyy");
				strFuncResult=strArFunRes[4];									
				
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				if(strUpdMulValue.equals(strStatusName1)){
					strUpdValCheck=strStatusValue[0];
				}else{
					strUpdValCheck=strStatusValue[1];
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
				strFuncResult = objCreateUsers.selectResourceRights(selenium, strResource, false, false, true, true);
				
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium, strRoleUsrVal, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium,
						strUserName, strByRole, strByResourceType,
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
				strFuncResult = objLogin.newUsrLogin(selenium,strUserName,
						strUsrPassword);
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
				String strCSTApplTime=dts.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

				strFuncResult = objRep.enterRepDetailsAndGenStatusReasonDetRep(
						selenium, strRSValue[0], statTypeName, strCSTApplTime,
						strCSTApplTime, true, strUpdValCheck, strReasonVal);
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
				String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");

				strFuncResult = objRep.enterRepDetailsAndGenStatusReasonDetRep(
						selenium, strRSValue[0], statTypeName, strCSTtime,
						strCSTtime, false, strUpdValCheck, strReasonVal);
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
								"Duration ", "User", "IP", "Trace", "Comments" },

						{ strResource, strUpdMulValue, strReasonName,
								strLastUpdDate + " " + strLastUpdTime,
								strGenDate + " " + strRegGenTime,
								"\\d+\\.\\d+", strUpdUsrName, propEnvDetails.getProperty("ExternalIP"),
								"**", strReasonName + " " }, };

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
						+ strReasonName
						+ "IP: "
						+ propEnvDetails.getProperty("ExternalIP")
						+ ""
						+ strPDFDownlPath;

				String strWriteFilePath = pathProps
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
			gstrTCID = "BQS-68196";
			gstrTO = "Verify that a user can be provided the right to generate Status Reason Detail report.";
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
	
	/***************************************************************
	'Description		:Verify that a user can be provided the right to generate Status Reason Detail report.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:20-08-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	
	@Test
	public void testBQS68197() throws Exception {
		try {
			gstrTCID = "BQS-68197"; // Test Case Id
			gstrTO = "Verify that a user can be provided the right to generate Status Snapshot report.";
																																// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();
			propElementAutoItDetails=objAP.ReadAutoit_FilePath();
			
			String strGenTimeHrs="";
			String strGenTimeMin="";
			
			General objGen=new General();
		
			StatusTypes objST=new StatusTypes();
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			CreateUsers objCreateUsers=new CreateUsers();
			
			Roles objRole=new Roles();
			
			String strFuncResult="";
					
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login
		
			ViewMap objViewMap=new ViewMap();
			Reports objRep=new Reports();
				
			String strUpdMulValue="";
		
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login", 3, 4);
			
			String strStatusTypeValue="Multi";
				
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
			
			String strUpdUsrName= "AutoUpdUsr"+System.currentTimeMillis();;
			String strUpdPwd= "abc123";			
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
				
			String strState="Alabama";
			String strCountry="Autauga County";
					
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strResource="AutoRs_"+strTimeText;
			String strResType="AutoRt_"+strTimeText;	
							
			String strSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
					
			String strStatusValue[]=new String[2];
			strStatusValue[0]="";
			strStatusValue[1]="";
			
			strSTvalue[0]="";	
			strRSValue[0]="";
			
			String strGenDate = "";
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep_" + gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "StatSnapRep_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strRoleForUser = "AutoRL" + strTimeText;
			String strRoleRitsForUsr[][] = {};
			String[] strRlViewRtForUsr = {};
		
			String []strRlUpdRtForUsr={};
			String strRoleUsrVal="";
	
			String strRegGenTime="";
			String strCurYear=dts.getCurrentDate("yyyy");
		
			String strLastUpdTime="";
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,strAdmUserName,
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
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//create role for user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleForUser, strRoleRitsForUsr, strRlViewRtForUsr, true, strRlUpdRtForUsr, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRoleUsrVal = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleForUser);
				
				if(strRoleUsrVal.compareTo("")!=0){
					strFuncResult="";
					
				}else{
					strFuncResult="Failed to fetch Role value";
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strStatusTypeValue, statTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
						
			try {
				assertEquals("", strFuncResult);
				String strRoleToSel[]={strRoleUsrVal};
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false, false, strRoleToSel, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition, statTypeName, strStatusName1, strStatusTypeValue, strStatTypeColor,false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition, statTypeName, strStatusName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition, statTypeName, strStatusName2, strStatusTypeValue, strStatTypeColor,false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition, statTypeName, strStatusName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,statTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,statTypeName, strStatusName2);
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResType, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition, strResource, strAbbrv, strResType, strContFName, strContLName,strState,strCountry,strStandResType);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
					
			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
				
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
			//create role to update
									
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
			
					
			//Update user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strUpdUsrName, strUpdPwd, strUpdPwd, strUsrFulName);
				
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUpdUsrName, strByRole, strByResourceType,
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUpdUsrName,
						strUpdPwd);
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
			
				strFuncResult = objViewMap.updateMultiStatusType_ChangeVal(selenium,
							strResource, statTypeName, strSTvalue[0],
							strStatusName1,strStatusValue[0],strStatusName2,strStatusValue[1]);
				
			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try{
				assertEquals("", strFuncResult);
				String[] strArFunRes=new String[5];
				strArFunRes=objViewMap.getUpdSTValueInMap(selenium, "1");			
				strFuncResult=strArFunRes[1];			
				strUpdMulValue=strArFunRes[0];
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			try{
				assertEquals("", strFuncResult);
				String[] strArFunRes=new String[5];
				strArFunRes=objViewMap.getUpdTimeValue(selenium, "1");
			
				strFuncResult=strArFunRes[4];			
				strLastUpdTime=strArFunRes[0]+" "+strArFunRes[1]+" "+strCurYear+" "+strArFunRes[2]+":"+strArFunRes[3];
			}
			catch (AssertionError Ae)
			{
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
				strFuncResult = objCreateUsers.selectResourceRights(selenium, strResource, false, false, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium, strRoleUsrVal, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium,
						strUserName, strByRole, strByResourceType,
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
				strFuncResult = objLogin.newUsrLogin(selenium,strUserName,
						strUsrPassword);
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
				//Wait for 2 minutes
				Thread.sleep(120000);
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String[] strArFunRes = new String[5];
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime=dts.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strCSTApplTime, strGenTimeHrs, strGenTimeMin);
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
				   Thread.sleep(1000);
				 } while (strProcess.contains(strAutoFileName)&&intCnt<300);
						 	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			int intCount = 0;
			int intCount1=0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath)
									.exists());
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCount1++;
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);


			try {
				assertEquals("", strFuncResult);
				String[][] strReportData = {
						{
								"Status Snapshot - " + strGenDate + " "
										+ strRegGenTime + " "+propEnvDetails.getProperty("TimeZone"), "", "", "",
								"", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType, strResource, statTypeName,
								strUpdMulValue, "", strLastUpdTime },

				};
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
				
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-68197";
			gstrTO = "Verify that a user can be provided the right to generate Status Snapshot report.";
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
	
	/*******************************************************************************************
	'Description	:Verify that a user can be provided the right to generate Event Snapshot report.
	'Precondition	:	Preconditions:

					1. Status types NST (Number), MST (Multi), TST (Text) and SST (Saturation Score) are created. 

					2. Status types NST (Number), MST (Multi), TST (Text) and SST (Saturation Score) are associated with resource type RT. 
					
					3. Resource RS is created under resource type RT. 
					
					4. Event template ET1 is created selecting resource type RT and status types NST, MST, TST, SST. 
					
					5. Event E1 is created under ET1 selecting resource RS. 
					
					6. Status Types NST, MST, TST and SST are updated on day D1 time hour H1 for resource RS. 
					
					7. User U1 is created with 'Run Reports' right on resource RS. 		
	'Arguments		:None
	'Returns		:None
	'Date	 		:20-Aug-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testBQS118731() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		General objGeneral = new General();
		EventSetup objEventSetup = new EventSetup();
		OfficeCommonFunctions OFC = new OfficeCommonFunctions();

		try {
			gstrTCID = "118731"; // Test Case Id
			gstrTO = "Verify that a user can be provided the "
					+ "right to generate Event Snapshot report.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strApplTime = "";
			// Admin Login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statNumTypeName = "NST" + strTimeText;
			String statTextTypeName = "TST" + strTimeText;
			String statMultiTypeName = "MST" + strTimeText;
			String statSaturatTypeName = "SST" + strTimeText;

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			// General variable
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUserNameUpdate = "AutoUsr_Update"
					+ System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			String strFulUserNameUpdate = strUserNameUpdate;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleValue = "";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strETValue = "";

			String strHr = "";
			String strEventValue = "";
			String strMin = "";
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xlsx";
			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "EventSnapshot_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");
			String strTimeReport = "";

			String strLastUpdArr[] = {};
			String strUpdateDataHr = "";
			String strUpdateDataMin = "";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating NST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating TST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTextTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating SST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statSaturatTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statSaturatTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status Type Multi status type is created.
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statMultiTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName, strStatusName1);
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
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName, strStatusName2);
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
				blnLogin = true;
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strRoleName = "Role" + System.currentTimeMillis();
				strFuncResult = objRole.rolesMandatoryFlds(seleniumPrecondition,
						strRoleName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.savAndVerifyRoles(seleniumPrecondition, strRoleName);
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

			// 1. Resource type RT

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
								+ strSTvalue[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[2] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[3] + "']");

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
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. Resources RS is created under RT. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
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

			/*
			 * 4. Event template ET1 is created selecting resource type RT and
			 * status types NST, MST, TST, SST
			 */

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

				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strETValue = objEventSetup.fetchETInETList(seleniumPrecondition,
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

				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);

			} catch (AssertionError Ae) {
				strFuncResult = "Failed";
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(seleniumPrecondition,
						strTempName, strResource, strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strEventValue = objEventSetup.fetchEventValueInEventList(
						seleniumPrecondition, strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6. User U1 has following rights:
			 * 
			 * a. Report - Event Snapshot b. No role is associated with User U1
			 * c. 'View Status' and 'Run Report' rights on resources RS.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EventSnapShotReprt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

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

			/*
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Navigate to Setup>>Users and click on the 'Edit' link
			 * associated with user U1 and select the role R1 from 'User Type &
			 * Roles' section and save the page. 'Users List' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Any User

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserNameUpdate, strInitPwd, strConfirmPwd,
						strFulUserNameUpdate);

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameUpdate, strByRole,
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

			try {
				assertEquals("", strFuncResult);

				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserNameUpdate, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statMultiTypeName,
						statNumTypeName, statSaturatTypeName, statTextTypeName, };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Number Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateNumStatusType(selenium,
						strResource, statNumTypeName, strSTvalue[0], "1", "2");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Text Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateNumStatusType(selenium,
						strResource, statTextTypeName, strSTvalue[1], "1", "2");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Saturation Status
			try {
				assertEquals("", strFuncResult);

				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				String[] strUpdateValue2 = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objStatusTypes.updateSatuScoreStatusType(
						selenium, strResource, statSaturatTypeName,
						strSTvalue[2], strUpdateValue1, strUpdateValue2, "393",
						"429");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Multi Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.updateMultiStatusType_ChangeVal(
						selenium, strResource, statMultiTypeName,
						strSTvalue[3], strStatusName1, strStatusValue[0],
						strStatusName2, strStatusValue[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strLastUpdArr = selenium.getText(
						"//span[text()='" + strStatusName1 + "']"
								+ "/following-sibling::span[@class='time'][1]")
						.split(" ");
				strLastUpdArr = strLastUpdArr[2].split(":");
				strUpdateDataHr = strLastUpdArr[0];
				strUpdateDataMin = strLastUpdArr[1];

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
			 * 5 Login as user U1, navigate to Reports>>Event Reports, click on
			 * 'Event Snapshot'. 'Event Snapshot Report (Step 1 of 2)' screen is
			 * displayed with:
			 * 
			 * 1. Start Date field with calender widget 2. End Date field with
			 * calender widget 2. List of Event Templates
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

				strFuncResult = objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToEventSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");

				System.out.println(strApplTime);

				strFuncResult = objRep.selectHtmlOrExcelformatInEventSnapShotReport(
						selenium, strCSTApplTime, strCSTApplTime, strETValue,true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "m",
						"mm");
				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "H",
						"HH");

				String strTime = selenium.getText("//div[@id='mainContainer']"
						+ "/form/table/tbody/tr/td/table/tbody/tr/td[2]");
				strHr = strTime.substring(11, 13);
				System.out.println(strHr);

				strMin = strTime.substring(14, 16);
				System.out.println(strMin);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");

				String str=strLastUpdArr[0]+":"+strLastUpdArr[1];
				str=dts.addTimetoExisting(str, 1, "HH:mm");
				strLastUpdArr=str.split(":");
				
				
				System.out.println(strTimeReport);
				strFuncResult = objRep.enterEvntSnapshotGenerateReport(
						selenium, strCSTApplTime, strEventValue,
						strLastUpdArr[0], strLastUpdArr[1]);

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
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath)
									.exists());
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCount1++;
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);

			try {
				assertEquals("", strFuncResult);

				String strTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"dd MMM yyyy");
				String strFutureDate = dts.DaytoExistingDate(strTime, 1,
						"dd MMM yyyy");
				strApplTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"M/d/yyyy");

				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "HH",
						"H");
				/*strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "mm",
						"m");*/

				String str = "Event Snapshot for " + strEveName + " - "
						+ strApplTime + " " + strLastUpdArr[0] + ":"
						+ strLastUpdArr[1]
						+ " Central Standard Time. Event Start:" + " "
						+ strTime + " " + strHr + ":" + strMin
						+ ". Event End: " + strFutureDate + "" + " " + strHr
						+ ":" + strMin + ". ";

				String[][] strTestData = {
						{ str, "", "", "", "", "", "", "", "" },
						{ "Resource Type", "Resource", statMultiTypeName,
								statNumTypeName, statSaturatTypeName,
								statTextTypeName, "Comment", "Last Update",
								"By User" },
						{
								strResrctTypName,
								strResource,strStatusName1,
								"1",
								"393",
								"1",
								"",
								strTime + " " + strUpdateDataHr + ":"
										+ strUpdateDataMin, strUserNameUpdate } };

				strFuncResult = OFC.readAndVerifyDataExcel(strTestData,
						strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118731";
			gstrTO = "Verify that a user can be provided the "
					+ "right to generate Event Snapshot report.";
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
	
	/*******************************************************************************************
	'Description	:Verify that a user can be provided the right to generate Status Detail 
	'Precondition	:1. Status type 'ST' (Multi status type) is created selecting role 'R1' under both view and update right on the status type.
					2. Statuses S1 and S2 are created under Status type ST.
					3. Status type ST is associated with resource type RT.
					4. Resource RS is created under resource type RT.
					5. Status type ST is updated with statuses S1 or S2 on day D1. 

	'Arguments		:None
	'Returns		:None
	'Date	 		:31-Oct-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testBQS68146() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral = new General();

		try {
			gstrTCID = "BQS-68146"; // Test Case Id
			gstrTO = "Verify that a user can be provided the right to generate Status Detail ";

			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strApplTime = "";
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName = "MST" + strTimeText;
			String strStatusTypeValue = "Multi";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			// Application time
			String strUpdatdVale = "";
			String strUpdatedDate = "";
			String strReportGenrtTime = "";
			String strReportGenrtTimePDF = "";

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameUpdate = "AutoUsr_Updat"
					+ System.currentTimeMillis();
			String strUserName1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameUpdate = strUserNameUpdate;
			String strUsrFulName1 = strUserName1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// System Time
			String strTimeGenerateSystem = "";
			String strTimeUpdateSystem = "";

			String strTimePDFGenerateSystem = "";
			String strDurationDiffPDF = "";

			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
			String CSVName = "StatSummary_" + gstrTCID + "_" + strTimeText
					+ ".csv";
			String PDFName = "StatSummary_" + gstrTCID + "_" + strTimeText
					+ ".pdf";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
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

			/*
			 * 1. Status type 'ST' (Multi status type) is created selecting role
			 * 'R1' under both view and update right on the status type.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Statuses S1 and S2 are created under Status type ST.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName, strStatusName1,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName, strStatusName2,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
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
				strStatValue = objStatusTypes.fetchStatValInStatusList(
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

			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

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

			/* 3. Status type ST is associated with resource type RT. */

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
								+ strSTvalue[0] + "']");

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

			/* 4. Resource RS is created under resource type RT. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
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
						strUserNameUpdate, strInitPwd, strConfirmPwd,
						strUsrFulNameUpdate);

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameUpdate, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 5. Status type ST is updated with statuses S1 or S2 on day D1. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserNameUpdate, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Status
			try {
				assertEquals("", strFuncResult);

				String strUpdateResult[] = objStatusTypes
						.updateMultiStatusType_ChangeValWithTime(selenium,
								strResource, statTypeName, strSTvalue[0],
								strStatusName1, strStatusValue[0],
								strStatusName2, strStatusValue[1], "HH:mm:ss");
				strTimeUpdateSystem = strUpdateResult[1];
				strFuncResult = strUpdateResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = selenium
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = selenium.getText("//div/span" + "[text()='"
						+ strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * 2 Login as RegAdmin and navigate to Setup>>Users. 'Users List'
			 * page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

			/*
			 * 3 Create a user U1 providing mandatory data, select 'Run Reports'
			 * right on resource RS, role R1 and 'Report - Status Detail' right
			 * from 'Advanced Options' section. User U1 is listed in the 'Users
			 * List' screen under Setup.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserName1,
								strInitPwd, strConfirmPwd, strUsrFulName1);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
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

			/*
			 * 4 Login as User U1, navigate to Reports>>Status Reports, click on
			 * 'Status Detail'. 'Status Detail Report (Step 1 of 2)' screen is
			 * displayed.
			 * 
			 * (by default Adobe Acrobat (PDF) is selected).
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.checkPDFIsSelectedByDefault(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statTypeName,
								strStatusValue[0], "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration "
						+ strDurationDiffPDF);

				// Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("CSV generation time "
						+ strReportGenrtTimePDF);

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
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[0], strCSTtime,
								strCSTtime, false, statTypeName,
								strStatusValue[0], "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];

				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time " + strReportGenrtTime);

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
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("CSV generation duration " + strDurationDiff);

				try {
					assertEquals("", strFuncResult);
					File fileCSV = new File(strCSVDownlPath);
					File filePDF = new File(strPDFDownlPath);

					if (fileCSV.exists() && filePDF.exists()) {
						gstrResult = "PASS";

						String[] strTestData = {
								propEnvDetails.getProperty("Build"), gstrTCID,
								strResource, strUpdatdVale,
								strCurrDate + " " + strUpdatedDate,
								strCurrDate + " " + strReportGenrtTimePDF,
								strDurationDiffPDF,
								strUserNameUpdate + "," + strUserName1, propEnvDetails.getProperty("ExternalIP"), "",
								PDFName,
								"Status Detail Report need to be checked in PDF file" };

						String strWriteFilePath = pathProps
								.getProperty("WriteResultPath");

						objOFC.writeResultData(strTestData, strWriteFilePath,
								"Status_Detail_Report");

						String strTestData2[] = {
								propEnvDetails.getProperty("Build"), gstrTCID,
								strResource, strUpdatdVale,
								strCurrDate + " " + strDurationUpdat,
								strCurrDate + " " + strDurationGenerat,
								strDurationDiff,
								strUserNameUpdate + "," + strUserName1, propEnvDetails.getProperty("ExternalIP"), "",
								CSVName,
								"Status Detail Report need to be checked in CSV file" };

						strWriteFilePath = pathProps
								.getProperty("WriteResultPath");

						objOFC.writeResultData(strTestData2, strWriteFilePath,
								"Status_Detail_Report");
					}

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
			gstrTCID = "BQS-68146";
			gstrTO = "Verify that a user can be provided the right to generate Status Detail ";
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
	
	/************************************************************************
	'Description    :Verify that a user can be provided the right to generate
	                 Statewide Resource Detail report.
	'Arguments		:None
	'Returns		:None
	'Date	 		:02-11-2012
	'Author			:QSG
	'------------------------------------------------------------------------
	'Modified Date                                             Modified By
	'<Date>                                                     <Name>
	*************************************************************************/

	@Test
	public void testBQS99746() throws Exception {

		boolean blnLogin = false;
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		ReadData objReadData = new ReadData();
		StatusTypes objST = new StatusTypes();
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		Reports objRep = new Reports();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		Views objView = new Views();
		String strFailMsg = "";
		try {
			gstrTCID = "BQS-99746";
			gstrTO = "Verify that a user can be provided the right to generate"
					+ " Statewide Resource Detail report.";
			gstrResult = "FAIL";
			gstrReason = "";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strTmText = dts.getCurrentDate("HHmm");

			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword = objReadData.readData("Login", 3, 2);
			String strRegn1 = rdExcel.readData("Login", 25, 4);
			String strRegn2 = rdExcel.readData("Login", 22, 4);

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNStatType1 = "AutoNSt1_" + strTimeText;
			String strNStatType2 = "AutoNSt2_" + strTimeText;
			String strNStatType3 = "AutoNSt3_" + strTimeText;

			String strTStatType1 = "AutoTSt1_" + strTimeText;
			String strTStatType2 = "AutoTSt2_" + strTimeText;
			String strTStatType3 = "AutoTSt3_" + strTimeText;

			String strMStatType1 = "AutoMSt1_" + strTimeText;
			String strMStatType2 = "AutoMSt2_" + strTimeText;
			String strMStatType3 = "AutoMSt3_" + strTimeText;

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusName3 = "Stc" + strTimeText;

			String strNSTvalue[] = new String[3];
			String strMSTvalue[] = new String[3];
			String strTSTvalue[] = new String[3];
			String strStatusValue[] = new String[3];
			String strStatValue = "";
			// RT
			String strResrctTypName1 = "AutoRt1_" + strTimeText;
			String strResource1 = "AutoRs1_" + strTimeText;
			String strRTValue[] = new String[1];
			String strRSValue[] = new String[1];
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Home Health";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strStandardNST = "Bed Availability: Adult ICU";
			String strStandardMST = "FL: Active";
			String strStandardTST = "File Number";
			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// User
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			String strUpdPwd = "abc123";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".xls";

			String strCSVFileNameRenamed = "StatewideResDetRep1_" + gstrTCID
					+ "_" + strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

         /*Precondition: All the below mentioned test data is created in 'Florida ESRD' region.
			1. Role based number status type rNST, shared status type sNST and private status type
			    pNST are created with a standard status type 'Bed Availability: Adult ICU'.
			2. Role based multi status type rMST, shared status type sMST and private status type
			   pMST are created with a standard status type 'FL:Active'.
			3. Role based text status type rTST, shared status type sTST and private status type pTST
			    are created with a standard status type 'File Number'.
			4. Resource type RT1 is created selecting status types rNST, pNST, sNST, rMST, rTST.
			5. Resource RS1 is created under resource type RT1 selecting standard resource type 'Home Health'.
			6.Status types pMST, sMST, sTST and rTST are associated to resource RS1 at the resource level.
			7. Statuses of all the above status types are updated in region 'Florida ESRD' by any user who 
			   has update status right of the above mentioned status types.
			8. User U1 is created with 'Report-Statewide resource detail' right on 'Statewide Florida' region. 
          */

			strFailMsg = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			// NST
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strNStatType1, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardNST);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(seleniumPrecondition, strNStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strNStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strNSTvalue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strNStatType2, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardNST);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectVisibiltyValue(seleniumPrecondition,
						strNStatType2, "SHARED", true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strNStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strNSTvalue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strNStatType3, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardNST);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectVisibiltyValue(seleniumPrecondition,
						strNStatType3, "PRIVATE", true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strNStatType3);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strNSTvalue[2] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			// MST
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strMSTValue, strMStatType1, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardMST);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifyMultiST(seleniumPrecondition, strMStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strMStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strMSTvalue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						strMStatType1, strStatusName1, strMSTValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatsValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						strMStatType1, strStatusName1);
				if (strStatsValue.compareTo("") != 0) {
					strFailMsg = "";
					strStatusValue[0] = strStatsValue;
				} else {
					strFailMsg = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strMSTValue, strMStatType2, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardMST);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectVisibiltyValue(seleniumPrecondition,
						strMStatType2, "SHARED", false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifyMultiST(seleniumPrecondition, strMStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strMStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strMSTvalue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						strMStatType2, strStatusName2, strMSTValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatsValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						strMStatType2, strStatusName2);
				if (strStatsValue.compareTo("") != 0) {
					strFailMsg = "";
					strStatusValue[1] = strStatsValue;
				} else {
					strFailMsg = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strMSTValue, strMStatType3, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardMST);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectVisibiltyValue(seleniumPrecondition,
						strMStatType3, "PRIVATE", false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifyMultiST(seleniumPrecondition, strMStatType3);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strMStatType3);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strMSTvalue[2] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						strMStatType3, strStatusName3, strMSTValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStatsValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						strMStatType3, strStatusName3);
				if (strStatsValue.compareTo("") != 0) {
					strFailMsg = "";
					strStatusValue[2] = strStatsValue;
				} else {
					strFailMsg = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			// TST
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTSTValue, strTStatType1, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardTST);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(seleniumPrecondition, strTStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strTStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strTSTvalue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTSTValue, strTStatType2, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardTST);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectVisibiltyValue(seleniumPrecondition,
						strTStatType2, "SHARED", true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strTStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strTSTvalue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTSTValue, strTStatType3, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardTST);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectVisibiltyValue(seleniumPrecondition,
						strTStatType3, "PRIVATE", true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strTStatType3);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strTSTvalue[2] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ strNSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			String strSTvalue[] = { strNSTvalue[1], strNSTvalue[2],
					strMSTvalue[0], strTSTvalue[0] };
			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFailMsg);
					strFailMsg = objRs.selDeselctOnlySTInEditRSLevelPage(
							seleniumPrecondition, strSTvalue[intST], true);

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}

			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName1);
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
				strFailMsg = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			String strState = "Alabama";
			String strCountry = "Autauga County";
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource1, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource1);

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
				strFailMsg = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			String strSTvalue1[] = { strMSTvalue[1], strMSTvalue[2],
					strTSTvalue[1], strTSTvalue[2] };
			for (int intST = 0; intST < strSTvalue1.length; intST++) {
				try {
					assertEquals("", strFailMsg);
					strFailMsg = objRs.selDeselctOnlySTInEditRSLevelPage(
							seleniumPrecondition, strSTvalue1[intST], true);

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}

			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.savAndVerifyEditRSLevelPage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			// create role to update

			String strRlRtValue[] = { strNSTvalue[0], strNSTvalue[1],
					strNSTvalue[2], strMSTvalue[0], strMSTvalue[1],
					strMSTvalue[2], strTSTvalue[0], strTSTvalue[1],
					strTSTvalue[2], };
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strRlRtValue, true,
						strRlRtValue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName);

				if (strRoleValue.compareTo("") != 0) {
					strFailMsg = "";

				} else {
					strFailMsg = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			// Update user
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUpdUsrName, strUpdPwd, strUpdPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource1, false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUpdUsrName, strByRole, strByResourceType,
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
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strUsrPassword, strUsrPassword,
						strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepStatewideResDetail"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
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

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.newUsrLogin(selenium, strUpdUsrName,
						strUpdPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				String[] strEventStatType = {};
				String[] strRoleStatType = { strMStatType1, strMStatType2,
						strMStatType3, strNStatType1, strNStatType2,
						strNStatType3, strTStatType1, strTStatType2,
						strTStatType3,

				};
				strFailMsg = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;

			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;

			}
			String strArMStatType[] = { strMStatType1, strMStatType2,
					strMStatType3 };

			for (int intST = 0; intST < strArMStatType.length; intST++) {
				try {
					assertEquals("", strFailMsg);

					strFailMsg = objView.fillUpdStatusMSTWithComments(selenium,
							strStatusValue[intST], strMSTvalue[intST], "");

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;

				}

			}
			String[] strNumUpdVal = { "1", "2", "3" };
			String strArNStatType[] = { strNStatType1, strNStatType2,
					strNStatType3 };

			for (int intST = 0; intST < strArNStatType.length; intST++) {
				try {
					assertEquals("", strFailMsg);

					strFailMsg = objView.fillUpdStatusNSTWithComments(selenium,
							strNumUpdVal[intST], strNSTvalue[intST], "");

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;

				}

			}
			String[] strTxtUpdVal = { "s", "t", "u" };
			String strArTStatType[] = { strTStatType1, strTStatType2,
					strTStatType3 };

			for (int intST = 0; intST < strArTStatType.length; intST++) {
				try {
					assertEquals("", strFailMsg);

					strFailMsg = objView.fillUpdStatusNSTWithComments(selenium,
							strTxtUpdVal[intST], strTSTvalue[intST], "");

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;

				}

			}
			String strArUpdVal[] = { strStatusName1, strStatusName2,
					strStatusName3, strNumUpdVal[0], strNumUpdVal[1],
					strNumUpdVal[2], strTxtUpdVal[0], strTxtUpdVal[1],
					strTxtUpdVal[2], };
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource1, strArUpdVal);

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
				strFailMsg = objLogin.newUsrLogin(selenium, strUserName,
						strUsrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRep.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRep
						.navToStatewideResourceDetailReportPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strStdRT[] = { strStandResType };
				String strStdRTval[] = { "116" };
				strFailMsg = objRep.selectOnlyStdResTypeForReport(selenium,
						strStdRTval, strStdRT);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objRep.navToSelStandSTPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strStdST[] = { strStandardNST, strStandardMST,
						strStandardTST };
				String strStdSTval[] = { "2100", "7000", "7045" };
				strFailMsg = objRep.selectOnlyStdStatusesForReport(selenium,
						strStdSTval, strStdST);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objRep.generateStatewideResDetRep(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt < 300);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {

					assertTrue(new File(strCSVDownlPath).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath)
									.exists());
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCount1++;
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);

			try {
				assertEquals("", strFailMsg);
				String[] strReportData = { "Statewide Resource Detail Report",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "", "", "" };

				strFailMsg = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);

				String[] strReportData = { "Region", "Resource Name", "Type",
						"Address", "County", "Latitude", "Longitude",
						"EMResource ID", "AHA ID", "External ID", "Website",
						"Contact", "Phone 1", "Phone 2", "Fax", "Email",
						"Notes", "Bed Availability: Adult ICU", 
						"File Number", "FL: Active"};

				strFailMsg = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strResData[] = {
						strRegn1,
						strResource1,
						strResrctTypName1,
						"AL ",
						"Autauga County",
						"-86.902",
						"32.318",
						"\\d+",
						"",
						"",
						"",
						strContFName + " " + strContLName,
						"",
						"",
						"",
						"",
						"",
						"6",
						strTxtUpdVal[0] + "; " + strTxtUpdVal[1] + "; "
						+ strTxtUpdVal[2],
						strStatusName1 + "; " + strStatusName2 + "; "
								+ strStatusName3
						 };
				strFailMsg = objOFC.readAndVerifyParticularDataExcel(
						strResData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-99746";
			gstrTO = "Verify that a user can be provided the right to generate"
					+ " Statewide Resource Detail report.";

			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
		if (blnLogin) {
			strFailMsg = objLogin.logout(selenium);
			try {
				assertEquals("", strFailMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		}
	}
	
	/********************************************************************************
	'Description	:Verify that a user can be provided the right to Maintain 
	'				Document Library. 
	'Precondition	:1. Folder F1 is created in Document Library.
	'Arguments		:None
	'Returns		:None
	'Date	 		:21-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	

	@Test
	public void testBQS68405() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
													  // CreateUsers
		DocumentLibrary objDocumentLibrary=new DocumentLibrary();
		

		try {
			gstrTCID = "BQS-68405 ";
			gstrTO = "Verify that a user can be provided "
					+ "the right to Maintain Document Library. ";
			gstrResult = "FAIL";
			gstrReason = "";


			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			//String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
		
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String 	strRegn = rdExcel.readData("Login", 3, 4);

			String strOptions = "";
			
			//User mandatory fields
			String strUserName = "";
			String strInitPwd = "";
			String strConfirmPwd = "";
			String strUsrFulName = "";

			//Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			//Craeting folder
			String strFolderName = "";
			String strFldDesc = "";
			String strTempFoldr_1 = "";
			String strTempFoldr_2 = "";
			String strTempDocName_1 = "";
			String strTempDocName_2 = "";
			String strTempDocName_3 = "";
			
			String strFldSelValue_1="";
			String strFldSelValue_2="";
			
			
			/*
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' page is displayed.
			 */ 
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

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

				// Data for creating user
				strUserName = "auto" + System.currentTimeMillis();
				strInitPwd = rdExcel.readData("Login", 9, 2);
				strConfirmPwd = strInitPwd;
				strUsrFulName = strUserName + "fulName";

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintainDocLibrary");
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

			// Prcondition folder

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDocumentLibrary
						.navToDocumentLibrary(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fetch the value of the folder

			try {
				assertEquals("", strFuncResult);

				strFolderName = "Auto_Foldr_1" + System.currentTimeMillis();
				strTempFoldr_1 = strFolderName;

				strFldDesc = "Dynamic Folder";
				strFuncResult = objDocumentLibrary.createNewFolder(selenium,
						strFolderName, strFldDesc, true, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFldSelValue_1 = selenium
						.getAttribute("//div[@id='mainContainer']/div[2]/a/span[text()='"
								+ strFolderName + "']/parent::a@onclick");
				log4j.info(strFldSelValue_1);

				String strFoldrValue[] = strFldSelValue_1.split("'");
				for (String s : strFoldrValue) {
					log4j.info(s);
				}

				
				strFldSelValue_1 = strFoldrValue[1].substring(2);
				log4j.info(strFldSelValue_1);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Logout as region admin
			try {
				assertEquals("", strFuncResult);

				strFuncResult=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;

				strFuncResult=objDocumentLibrary.navToDocumentLibrary(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFolderName = "Auto_Foldr_2" + System.currentTimeMillis();
				strTempFoldr_2=strFolderName;
				
				strFldDesc = "Dynamic Folder";
				strFuncResult = objDocumentLibrary.createNewFolder(selenium,
						strFolderName, strFldDesc, true, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Click on 'Add a New Document' button. 'Add A Document' screen
			 * is displayed.
			 */
			/*
			 * 8 Provide TextDoc as the 'Title', browse for an .text file for
			 * the field 'Attached File', select folder F1 under 'Create in' and
			 * click on 'Save'. TextDoc is displayed under folder F1 on
			 * 'Document Library' screen with "Delete" and "Move" buttons.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFldSelValue_2 = selenium
						.getAttribute("//div[@id='mainContainer']/div[2]/a/span[text()='"
								+ strFolderName + "']/parent::a@onclick");
				log4j.info(strFldSelValue_2);

				String strFoldrValue[] = strFldSelValue_2.split("'");
				for (String s : strFoldrValue) {
					log4j.info(s);
				}

				strFldSelValue_2 = strFoldrValue[1].substring(2);
				log4j.info(strFldSelValue_2);
				

				String strDocTitle = "AutoDocTxt" + System.currentTimeMillis();
				strTempDocName_1=strDocTitle;
				
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadTxtFile_OpenPath");
				
				strFuncResult = objDocumentLibrary.addNewDocument(selenium,
						strDocTitle, strTempFoldr_1, false, strFldSelValue_1,
						strAutoFilePath, strAutoFileName, strUploadFilePath);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 9 Repeat the above step to upload a .pdf and .HTML files.
			 * Uploaded documents are displayed under folder F1 on 'Document
			 * Library' screen.
			 */ 
			
			try {
				assertEquals("", strFuncResult);
				
				String strDocTitle = "AutoDocPdf" + System.currentTimeMillis();
				strTempDocName_2=strDocTitle;
				
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadPdfFile_OpenPath");
				
				strFuncResult = objDocumentLibrary.addNewDocument(selenium,
						strDocTitle, strTempFoldr_1, false, strFldSelValue_1,
						strAutoFilePath, strAutoFileName, strUploadFilePath);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				
				String strDocTitle = "AutoDocHTML" + System.currentTimeMillis();
				strTempDocName_3=strDocTitle;
				
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadHTMLFile_OpenPath");
				
				strFuncResult = objDocumentLibrary.addNewDocument(selenium,
						strDocTitle, strTempFoldr_2, false, strFldSelValue_2,
						strAutoFilePath, strAutoFileName, strUploadFilePath);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 10 Expand folder F1, select "Move" button corresponding to file
			 * TextDoc "Move A Document" screen is displayed with folders F1 and
			 * F2 listed for the "Move Document To" field.
			 */
			
			try {
				assertEquals("", strFuncResult);

				String strOldFoldName = strTempFoldr_1;
				String strOldFldSelValue =  strFldSelValue_1;

				String strNewFoldName = strTempFoldr_2;
				String strNewFldSelValue = strFldSelValue_2;
				
				String strDocument = strTempDocName_1;
				
				strFuncResult = objDocumentLibrary.moveDocToFolder(selenium,
						strOldFoldName, strOldFldSelValue, strNewFoldName,
						strNewFldSelValue, false, strDocument);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 12 Expand folder F1, select "Delete" button corresponding to file
			 * PDFDoc A confirmation window is displayed.
			 */
			/*
			 * 13 Click on OK on the confirmation window On 'Document Library'
			 * screen,
			 * 
			 * 1. PDFDoc is not displayed under folder F1. 2. PDFDoc is NOT
			 * displayed under All Folders/F2.
			 */
			
			try {
				assertEquals("", strFuncResult);

				String strFoldName = strTempFoldr_1;
				String strFldSelValue = strFldSelValue_1;
				String strDocument = strTempDocName_2;
				strFuncResult = objDocumentLibrary.deleteDocument(selenium,
						strFoldName, strFldSelValue, strDocument);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 14 Click on 'Rename a Folder' button on 'Document Library'
			 * screen. "Select Folder to Rename" screen is displayed.
			 */
			/*
			 * 15 Select folder F1 and click on 'Rename' "Edit Folder" screen is
			 * displayed
			 */
			/*
			 * 16 Edit the folder name form 'F1' to 'F1-edit' and save On
			 * 'Document Library' screen, the chnages are reflected for the
			 * folder.
			 */
			try {
				assertEquals("", strFuncResult);

				String strFoldName = "EDIT"+strTempFoldr_1;
				String strFoldDesc = "Dynamic folder renamed";
				String strFldSelValue = strFldSelValue_1;
				strFuncResult = objDocumentLibrary.renameFolder(selenium,
						strFoldName, strFoldDesc, strFldSelValue, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 17 Click on 'Delete Folder' button on 'Document Library' screen.
			 * "Delete Folder" screen is displayed.
			 */
			/*
			 * 18 Select folder F2 and select the checkbox
			 * "Delete documents too?" and click on Save A confirmation window
			 * is displayed.
			 */
			/*
			 * 19 Click on OK on the confirmation window. Folder F2 is not
			 * displayed on the 'Document Library' screen. On 'Document Library'
			 * screen, HTMLDoc is NOT displayed under All Folders/F2.
			 */
			
			try {
				assertEquals("", strFuncResult);

				String strFoldName = strTempFoldr_2;
				String strFldSelValue = strFldSelValue_2;
				String[] strDocuments = {strTempDocName_3};
				strFuncResult = objDocumentLibrary.deleteAFolder(selenium,
						strFoldName, strFldSelValue, true, false, strDocuments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-68405";
			gstrTO = "Verify that a user can be provided the right"
					+ " to Maintain Document Library. ";
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
	
