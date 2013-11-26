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
import static org.junit.Assert.*;

/****************************************************************
' Description        : The class  contains the test cases from
' Requirement Group  : Creating & managing Events
' Requirement        : Edit event template		
' Date               : 17-Apr-2012
' Author             :QSG
'---------------------------------------------------------------
' Modified Date                                     Modified By
' <Date>                           	                <Name>
'****************************************************************/
public class EditEventTemplate {
	
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.EditEventTemplate");
	static {
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
	public Properties propEnvDetails;
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
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
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

		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);
	
	}
	/**
	'*************************************************************
	' Description: Verify that an event template can be edited.
	' Precondition: 1. Role based status types MST, NST, TST and SST are created.
					2. Resource type RT is created selecting MST, NST, TST and SST status types.
					3. Resource RS is created under RT
					4. User A is created selecting;
					
					a. View Resource right on RS.
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
	public void testBQS68110()throws Exception{
		try{
			gstrTCID = "68110";			
			gstrTO = "Verify that an event template can be edited";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");

			Login objLogin = new Login();// object of class Login
			EventSetup objEve=new EventSetup();
			EventList objList=new EventList();
			CreateUsers objCreateUsers=new CreateUsers();
			StatusTypes objST=new StatusTypes();	
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();			
			Roles objRole=new Roles();
			
			int intResCnt=0;
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			
			String[] strTestData=new String[10];
			String strFILE_PATH=pathProps.getProperty("TestData_path");
			ReadData objReadData = new ReadData (); 
									
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			//fetch the required test data from excel
			String strEveTemp=objReadData.readInfoExcel("Event Temp Data", 2, 2, strFILE_PATH);
			strEveTemp=strEveTemp+strTimeText;
			
					
			String strResType="AutoRt_"+strTimeText;
					
			String strEdEveTemp=objReadData.readInfoExcel("Event Temp Data", 3, 2, strFILE_PATH);
			strEdEveTemp=strEdEveTemp+strTimeText;
			
			String strEdTempDef=objReadData.readInfoExcel("Event Temp Data", 3, 3, strFILE_PATH);
			String strEdEveColor=objReadData.readInfoExcel("Event Temp Data", 3, 4, strFILE_PATH);
			String strEdAsscIcon=objReadData.readInfoExcel("Event Temp Data", 3, 5, strFILE_PATH);
			String strEdIconSrc=objReadData.readInfoExcel("Event Temp Data", 3, 6, strFILE_PATH);
				
			String strEveName="AutoE_"+strTimeText;
			String strInfo="This is an automation event";
			//login details
			String strUserName = objReadData.readData("Login", 3, 1);
			String strPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);
			
			String strBackColr="background-image: url(/icon/event7.png);";
							
			String strLoginUserName ="AutoUsr"+System.currentTimeMillis();
			String strLoginPassword ="abc123";
			String strFullUserName="Full"+strLoginUserName;
			
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strMulStatTypeValue="Multi";
			String strMulTypeName="AutoMSt_"+strTimeText;
			String strMulStatTypDefn="Auto";
			String strMulStatTypeColor="Black";
			
			String strNumStatTypeValue="Number";
			String strNumTypeName="AutoNSt_"+strTimeText;
			String strNumStatTypDefn="Auto";
					
			String strTxtStatTypeValue="Text";
			String strTxtTypeName="AutoTSt_"+strTimeText;
			String strTxtStatTypDefn="Auto";
						
			String strSatuStatTypeValue="Saturation Score";
			String strSatuTypeName="AutoSSt_"+strTimeText;
			String strSatuStatTypDefn="Auto";
			
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strSTvalue[]=new String[4];
			String strStatusValue[]=new String[2];
			
			strStatusValue[0]="";
			strStatusValue[1]="";
				
			String strResource="AutoRs_"+strTimeText;
		
			String strAbbrv="Rs";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strState="Alabama";
			String strCountry="Autauga County";
			String strRSValue[]=new String[1];
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String strRoleValue="";
			
			String strTempDef=objReadData.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objReadData.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objReadData.readInfoExcel("Event Temp Data", 2, 5, strFILE_PATH);
			String strIconSrc= objReadData.readInfoExcel("FirefoxTestData", 2, 3,
				     strFILE_PATH);
			
			String strResTypeArr[]=new String[1];
	
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			//Login as Region Admin
			String strFailMsg = objLogin.login(seleniumPrecondition, strUserName,
					strPassword);
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//nav to default region
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
					
			//Multi ST
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strMulStatTypeValue, strMulTypeName, strMulStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
					
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strMulTypeName);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(seleniumPrecondition, strMulTypeName, strStatusName1, strMulStatTypeValue, strMulStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(seleniumPrecondition, strMulTypeName, strStatusName2, strMulStatTypeValue, strMulStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,strMulTypeName, strStatusName1);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strStatusValue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,strMulTypeName, strStatusName2);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strStatusValue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			//Number ST
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strNumStatTypeValue, strNumTypeName, strNumStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strNumTypeName);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			//Saturation Score ST
					
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strSatuStatTypeValue, strSatuTypeName, strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strSatuTypeName);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[2]=strStatValue;
				}else{
					strFailMsg="Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			//Text ST
					
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strTxtTypeName);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[3]=strStatValue;
				}else{
					strFailMsg="Failed to fetch Text status type value";
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
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResType, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			for(int intST=0;intST<strSTvalue.length;intST++){
				try {
					assertEquals("", strFailMsg);
					strFailMsg = objRs.selDeselctOnlySTInEditRSLevelPage(seleniumPrecondition,strSTvalue[intST], true);
																	
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
						
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition, strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strRestypVal = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition, strResType);
				
				if(strRestypVal.compareTo("")!=0){
					strFailMsg="";
					strResTypeArr[0]=strRestypVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
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
			
					
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.createResourceWitLookUPadres(seleniumPrecondition, strResource, strAbbrv, strResType, strContFName, strContLName,strState,strCountry,strStandResType);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
					
			try {
				assertEquals("", strFailMsg);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
				
				if(strResVal.compareTo("")!=0){
					strFailMsg="";
					strRSValue[0]=strResVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
										
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleName);
				
				if(strRoleValue.compareTo("")!=0){
					strFailMsg="";
					
				}else{
					strFailMsg="Failed to fetch Role value";
				}
				
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
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strLoginUserName, strLoginPassword, strLoginPassword, strFullUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(seleniumPrecondition, strResource, false, false, false, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
								
				strFailMsg = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strLoginUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg="'Event Template List' screen is NOT displayed.";
				//navigate to Event Setup
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
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(seleniumPrecondition, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIconSrc,"","","","",true,strResTypeArr,strSTvalue,true,false,false);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
						
			try {
				assertTrue(strFailMsg.equals(""));				
				strFailMsg=objLogin.logout(seleniumPrecondition);					
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
			
			/*
			 * Step 2: Login as user U1, navigate to Event >> Event Management. <->	'Event Management' screen is displayed.
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
				strFailMsg= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * Step 3: 	Click on 'Create New Event' button. <->	'Select Event Template' screen is displayed. 
			 * Step 4: Click on 'Create' associated with 'ET'. 	<->	'Create New Event' screen is displayed
			   Step 5:	Create an event 'EVE' providing mandatory data and selecting resource RS. <-> Event 'EVE' is listed on 'Event List' screen. 
			 */
			try {
				assertTrue(strFailMsg.equals(""));
								
				strFailMsg=objEve.createEvent(selenium, strEveTemp, strResource, strEveName, strInfo,true);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				/*
				 * Step 6: Click on the event banner of 'EVE'. 	<->	Resource 'RS' is displayed on the 'Event Status' screen under RT along with NST, MST, TST and SST status types. 
				 */
				//Click on Event name in event banner
				selenium.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"+strEveName+"']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try{
					assertTrue(selenium.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"+strResType+"']"));
					assertTrue(selenium.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"+strResource+"']"));
					assertEquals(strMulTypeName,selenium.getText("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"+strResType+"']/ancestor::tr/th[3]"));
					assertEquals(strNumTypeName,selenium.getText("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"+strResType+"']/ancestor::tr/th[4]"));
					assertEquals(strSatuTypeName,selenium.getText("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"+strResType+"']/ancestor::tr/th[5]"));
					assertEquals(strTxtTypeName,selenium.getText("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"+strResType+"']/ancestor::tr/th[6]"));
					log4j.info("Resource '"+strResource+"' is displayed on the 'Event Status' screen under "+strResType+" along with all the status types.");
					intResCnt++;
				}catch(AssertionError ae){
					log4j.info("Resource '"+strResource+"' is NOT displayed on the 'Event Status' screen under "+strResType+" along with all the status types.");
					gstrReason="Resource '"+strResource+"' is NOT displayed on the 'Event Status' screen under "+strResType+" along with all the status types.";
				}
				/*
				 * Step 7:	Navigate to Event >> Event Setup. <->	'Event Template List' screen is displayed.
				 */
				try {
					assertTrue(strFailMsg.equals(""));	
					strFailMsg= "'Event Template List' screen is NOT displayed.";
					assertTrue(objEve.navToEventSetup(selenium));
					strFailMsg="";	
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				
				/*
				 * Step 8: 	Click on 'Edit' link next to 'ET', edit name, description of template, data under 'Associated icon', 'Event Color' fields and 'Save'. <->
				 * 		Name, description and event icon is updated in the 'Event Template List' screen.

					<-> Color of banner (displayed under the main menu header) and event icon are updated. 
				 */
				try {
					assertTrue(strFailMsg.equals(""));	
					strFailMsg=objEve.editEventTemplate(selenium,strEveTemp);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}				
				
				try {
					assertTrue(strFailMsg.equals(""));
					String strEdResTypArr[]={};
					String strEdStatTypArr[]={};
					strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(selenium, strEdEveTemp, strEdTempDef, strEdEveColor, strEdAsscIcon, strEdIconSrc,"","","","",false,strEdResTypArr,strEdStatTypArr,true,false,false);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				try{
					assertTrue(strFailMsg.equals(""));
					assertEquals(strBackColr,selenium.getAttribute("//div[@id='eventsBanner']/table/tbody/tr/td[@class='black eventNotSel']/a[text()='"+strEveName+"']@style"));
					log4j.info("Color of banner (displayed under the main menu header) and event icon are updated.");
					intResCnt++;
				}catch(AssertionError ae){
					log4j.info("Color of banner (displayed under the main menu header) and event icon are NOT updated.");
					gstrReason=gstrReason+" Color of banner (displayed under the main menu header) and event icon are NOT updated.";
				}
				/*
				 * Step 9: Navigate to Event >> Event Management <-> Name and event icon is updated in the 'Event Management' screen for event EVE.
				 */
				try {
					assertTrue(strFailMsg.equals(""));	
					strFailMsg= "Event Management screen is NOT displayed";
					assertTrue(objEve.navToEventManagement(selenium));	
					strFailMsg="";	
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				
				/*
				 * Step 10: Click on 'Create New Event' button. <->  Name, description and event icon is updated in the 'Select Event Template' screen. 
				 */
				//Click on Create New Event
				selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try{
					assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"+strEdEveTemp+"']"));
					assertEquals(strEdIconSrc,selenium.getAttribute("//div[@id='mainContainer']/table/tbody/tr/td[text()='"+strEdEveTemp+"']/parent::tr/td[2]/img@src"));
					assertEquals(strEdTempDef,selenium.getText("//div[@id='mainContainer']/table/tbody/tr/td[text()='"+strEdEveTemp+"']/parent::tr/td[5]"));
					log4j.info("Name, description and event icon is updated in the 'Select Event Template' screen.");
					intResCnt++;
				} catch (AssertionError Ae) {
					log4j.info("Name, description and event icon is NOT updated in the 'Select Event Template' screen.");
					gstrReason = gstrReason+" "+" Name, description and event icon is NOT updated in the 'Select Event Template' screen.";
				}	
				
				/*
				 * Step 11: Click on 'Create New Event' button. <->	Name, description and event icon is updated in the 'Select Event Template' screen. 
				 */
				try {
					assertTrue(objList.navToEventList(selenium));	
				
					try{
						assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"+strEveName+"']"));											
						assertEquals(strEdEveTemp,selenium.getText("//div[@id='mainContainer']/table/tbody/tr/td[text()='"+strEveName+"']/parent::tr/td[8]"));
						log4j.info("Name of the event template is updated in the 'Event List' screen for event "+strEveName);
						intResCnt++;
					} catch (AssertionError Ae) {
						log4j.info("Name of the event template is NOT updated in the 'Event List' screen for event "+strEveName);
						gstrReason = gstrReason+" "+" Name of the event template is NOT updated in the 'Event List' screen for event "+strEveName;
					}	
					
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+" Event List screen is NOT displayed";
				}	
				
				if(intResCnt==4){
					gstrResult="PASS";
					
					//Write result data
					strTestData[0]= propEnvDetails.getProperty("Build");
					strTestData[1]=gstrTCID;
					strTestData[2]=strLoginUserName+"/"+strLoginPassword;
					strTestData[3]=strEdEveTemp;
					strTestData[4]=strEveName;
					strTestData[5]="Icon Selected: "+strEdAsscIcon+", Resource Name: "+strResource+" Check in mobile";
					
					String strWriteFilePath=pathProps.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestData, strWriteFilePath, "Events");
					
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "68110";
			gstrTO = "Verify that an event template can be edited";
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
	' Description: Edit the event template, select/deselect resource types, status types and verify that the already created events are not affected.
	' Precondition: 1. Resource RS1 is created under resource type RT1 associated with status type ST1.
					2. Resource RS2 is created under resource type RT2 associated with status type ST2.
					3. Create an event template ET selecting resource type RT1 and associated status type ST1.
					4. Create an event under this template selecting resource RS1.
					5. User A is created with the following rights;
					a. Role to view status types ST1 and ST2.
					b. 'View resource' right on resources RS1 and RS2.
					c. 'Maintain Events' right.
					d. 'Maintain Event Templates' right. 
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
	public void testBQS68111()throws Exception{
		try{
			gstrTCID = "68111";			
			gstrTO = "Edit the event template, select/deselect resource types, status types and verify that the already created events are not affected.";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");

			Login objLogin = new Login();// object of class Login
			EventSetup objEve=new EventSetup();
			CreateUsers objCreateUsers=new CreateUsers();
			StatusTypes objST=new StatusTypes();	
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();			
			Roles objRole=new Roles();
			
			int intResCnt=0;
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			
			String strFILE_PATH=pathProps.getProperty("TestData_path");
			ReadData objReadData = new ReadData (); 
						
			String strTestData[]=new String[10];
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			//fetch the required test data from excel
			String strEveTemp1=objReadData.readInfoExcel("Event Temp Data", 2, 2, strFILE_PATH);
			strEveTemp1=strEveTemp1+strTimeText;
			
			String strTempDef1=objReadData.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor1=objReadData.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon1=objReadData.readInfoExcel("Event Temp Data", 2, 5, strFILE_PATH);
			String strIconSrc1=objReadData.readInfoExcel("Event Temp Data", 2, 6, strFILE_PATH);
			String strIconSrc=objReadData.readInfoExcel("FirefoxTestData", 2, 3,
				     strFILE_PATH);
			
			String strResType1="AutoRt1_"+strTimeText;
			String strNumStat1="AutoSta_"+strTimeText;
						
			String strResource1="AutoRsa_"+strTimeText;
			
						
			String strResType2="AutoRt2_"+strTimeText;
			String strNumStat2="AutoStb_"+strTimeText;
			
			String strResource2="AutoRsb_"+strTimeText;
			
			String strEveName1="AutoE1_"+strTimeText;
			
			String strEveName2="AutoE2_"+strTimeText;
			
			String strInfo="This is an automation event";
			
			String strUserName = objReadData.readData("Login", 3, 1);
			String strPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);

			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strLoginUserName = "AutoUsr"+System.currentTimeMillis();
			String strLoginPassword ="abc123";
			String strFullUserName="Full"+strLoginUserName;
			
			String strResTypeArr1[]=new String[1];
			String strStatTypeArr1[]=new String[1];
			
			String strResTypeArr2[]=new String[1];
			String strStatTypeArr2[]=new String[1];
			
			String strSTvalue[]=new String[2];
			
			String strAbbrv="Rs";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strState="Alabama";
			String strCountry="Autauga County";
			String strRSValue[]=new String[2];
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String strRoleValue="";
												
			String strNumStatTypeValue="Number";
		
			String strNumStatTypDefn="Auto";
		
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			//Login as Region Admin
			String strFailMsg = objLogin.login(seleniumPrecondition, strUserName,
					strPassword);
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//nav to default region
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strNumStatTypeValue, strNumStat1, strNumStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strNumStat1);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetchNumber status type value";
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
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strNumStatTypeValue, strNumStat2, strNumStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strNumStat2);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetchNumber status type value";
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
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResType1, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition, strResType1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strRestypVal = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition, strResType1);
				
				if(strRestypVal.compareTo("")!=0){
					strFailMsg="";
					strResTypeArr1[0]=strRestypVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
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
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResType2, "css=input[name='statusTypeID'][value='"+strSTvalue[1]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition, strResType2);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strRestypVal = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition, strResType2);
				
				if(strRestypVal.compareTo("")!=0){
					strFailMsg="";
					strResTypeArr2[0]=strRestypVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
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
			
					
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.createResourceWitLookUPadres(seleniumPrecondition, strResource1, strAbbrv, strResType1, strContFName, strContLName,strState,strCountry,strStandResType);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
					
			try {
				assertEquals("", strFailMsg);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource1);
				
				if(strResVal.compareTo("")!=0){
					strFailMsg="";
					strRSValue[0]=strResVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
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
			
					
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.createResourceWitLookUPadres(seleniumPrecondition, strResource2, strAbbrv, strResType2, strContFName, strContLName,strState,strCountry,strStandResType);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
					
			try {
				assertEquals("", strFailMsg);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource2);
				
				if(strResVal.compareTo("")!=0){
					strFailMsg="";
					strRSValue[1]=strResVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleName);
				
				if(strRoleValue.compareTo("")!=0){
					strFailMsg="";
					
				}else{
					strFailMsg="Failed to fetch Role value";
				}
				
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
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strLoginUserName, strLoginPassword, strLoginPassword, strFullUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(seleniumPrecondition, strResource1, false, false, false, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(seleniumPrecondition, strResource2, false, false, false, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
								
				strFailMsg = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strLoginUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg="'Event Template List' screen is NOT displayed.";
				//navigate to Event Setup
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
				strStatTypeArr1[0]=strSTvalue[0];
				//fill the required fields in Create Event Template and save
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(seleniumPrecondition, strEveTemp1, strTempDef1, strEveColor1, strAsscIcon1, strIconSrc,"","","","",true,strResTypeArr1,strStatTypeArr1,true,false,false);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(seleniumPrecondition));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
								
				strFailMsg=objEve.createEvent(seleniumPrecondition, strEveTemp1, strResource1, strEveName1, strInfo,true);
				log4j.info(strEveName2);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
				
			try {
				assertTrue(strFailMsg.equals(""));				
				strFailMsg=objLogin.logout(seleniumPrecondition);					
								
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}		
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
			/*
			 * Step 2: Login as user A, click on the event banner. <->	RS1 is displayed under RT1 along with ST1 in 'Event Status' screen.
			 */
			try {
				assertTrue(strFailMsg.equals(""));		
				strFailMsg = objLogin.newUsrLogin(selenium, strLoginUserName,
						strLoginPassword);
				
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}	
			
					
			try {
				assertTrue(strFailMsg.equals(""));	
				//Click on Event name in event banner
				selenium.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"+strEveName1+"']");
				int intCnt=0;
				while(selenium.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"+strResType1+"']")==false&&intCnt<60){
					Thread.sleep(1000);
					intCnt++;
				}
				try{
					assertTrue(selenium.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"+strResType1+"']"));
					assertTrue(selenium.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"+strResource1+"']"));
					assertEquals(strNumStat1,selenium.getText("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"+strResType1+"']/ancestor::tr/th[3]"));
					log4j.info("Resource '"+strResource1+"' is displayed on the 'Event Status' screen under "+strResType1+" along with all the status types.");
					intResCnt++;
				}catch(AssertionError ae){
					log4j.info("Resource '"+strResource1+"' is NOT displayed on the 'Event Status' screen under "+strResType1+" along with all the status types.");
					gstrReason="Resource '"+strResource1+"' is NOT displayed on the 'Event Status' screen under "+strResType1+" along with all the status types.";
				}
						
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * Step 3: 	Navigate to Event >> Event Setup, click on 'Edit' link associated with ET, deselect ST1, RT1 and select ST2, RT2 and 'Save'. <-> 'Event Template List' screen is displayed.
			 */
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "'Event Template List' screen is NOT displayed.";
				assertTrue(objEve.navToEventSetup(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg=objEve.editEventTemplate(selenium,strEveTemp1);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}				
			
			try {
				assertTrue(strFailMsg.equals(""));
				//deselect ST1,RT1
				selenium.click("css=input[name='rt'][value='"+strResTypeArr1[0]+"']");			
				selenium.click("css=input[name='st'][value='"+strStatTypeArr1[0]+"']");
				strStatTypeArr2[0]=strSTvalue[1];
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(selenium, strEveTemp1, strTempDef1, strEveColor1, strAsscIcon1, strIconSrc1,"","","","",false,strResTypeArr2,strStatTypeArr2,true,false,false);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			
			
			try {
				assertTrue(strFailMsg.equals(""));	
				/*
				 * Step 4: Click on event banner EVE <-> RS1 is still displayed under RT1 with ST1 in 'Event Status' screen. 
				 */
				//Click on Event name in event banner
				selenium.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"+strEveName1+"']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try{
					assertTrue(selenium.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"+strResType1+"']"));
					assertTrue(selenium.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"+strResource1+"']"));
					assertEquals(strNumStat1,selenium.getText("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"+strResType1+"']/ancestor::tr/th[3]"));
					log4j.info("Resource '"+strResource1+"' is displayed on the 'Event Status' screen under "+strResType1+" along with all the status types.");
					intResCnt++;
				}catch(AssertionError ae){
					log4j.info("Resource '"+strResource1+"' is NOT displayed on the 'Event Status' screen under "+strResType1+" along with all the status types.");
					gstrReason="Resource '"+strResource1+"' is NOT displayed on the 'Event Status' screen under "+strResType1+" along with all the status types.";
				}
						
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * Step 8: 	On Web, navigate to Event>>Event Management and click on 'Create New Event' <->	'Create New Event' screen is displayed. 
			 */
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				/*
				 * Step 9: Click on 'Create' associated with ET <->	RS1 is not displayed.

					<-> RS2 is displayed. 
				 */
				//Click on Create New Event
				selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				//Click on Create for the particular Event template
				selenium.click("//div[@id='mainContainer']/table/tbody/tr/td[text()='"+strEveTemp1+"']/preceding-sibling::td/a[text()='Create']");
				selenium.waitForPageToLoad(gstrTimeOut);
				try{
					assertEquals("Create New Event", selenium.getText("css=h1"));
					log4j.info("'Create New Event' screen is displayed");
					
					try{
						assertFalse(selenium.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"+strResource1+"']"));
						assertTrue(selenium.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"+strResource2+"']"));
						
						log4j.info("Resource "+strResource2+" is displayed and "+ strResource1+ " is not displayed under 'Resources to Participate in This Event' section.");
						intResCnt++;
					}catch(AssertionError ae){
						log4j.info("Resource "+strResource2+" is NOT displayed or "+ strResource1+ " is displayed under 'Resources to Participate in This Event' section.");
						gstrReason=gstrReason+" Resource "+strResource2+" is NOT displayed or "+ strResource1+ " is displayed under 'Resources to Participate in This Event' section.";
					}
					
				}catch(AssertionError ae){
					log4j.info("'Create New Event' screen is NOT displayed");
					gstrReason=	gstrReason+" 'Create New Event' screen is NOT displayed";
				}
			
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			/*
			 * Step 10: Navigate to Event >> Event Management, create an event EVE2 selecting resource RS2. <->	EVE2 is listed on the 'Event List' screen.
			 */
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
								
				strFailMsg=objEve.createEvent(selenium, strEveTemp1, strResource2, strEveName2, strInfo,true);
				log4j.info(strEveName2);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				/*
				 * Step 11: Click on the event banner. 	<->	RS2 is displayed in the 'Event Status' screen 
				 */
				//Click on Event name in event banner
				selenium.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"+strEveName2+"']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try{
					assertTrue(selenium.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"+strResType2+"']"));
					assertTrue(selenium.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"+strResource2+"']"));
					
					log4j.info("Resource '"+strResource2+"' is displayed on the 'Event Status' screen under "+strResType2+" along with all the status types.");
					intResCnt++;
				}catch(AssertionError ae){
					log4j.info("Resource '"+strResource2+"' is NOT displayed on the 'Event Status' screen under "+strResType2+" along with all the status types.");
					gstrReason=gstrReason+" Resource '"+strResource2+"' is NOT displayed on the 'Event Status' screen under "+strResType2+" along with all the status types.";
				}
											
				if(intResCnt==4){
					gstrResult="PASS";
					//Write result data
					strTestData[0]= propEnvDetails.getProperty("Build");
					strTestData[1]=gstrTCID;
					strTestData[2]=strLoginUserName+"/"+strLoginPassword;
					strTestData[3]=strEveTemp1;
					strTestData[4]=strEveName1;
					strTestData[5]="Icon Selected: "+strAsscIcon1+", Resource Name: "+strResource1+" check in mobile";
					
					String strWriteFilePath=pathProps.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestData, strWriteFilePath, "Events");
					
				}
				
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "68111";
			gstrTO = "Edit the event template, select/deselect resource types, status types and verify that the already created events are not affected.";
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
	' Description: Verify that an event template can be deactivated.
	' Precondition: 1. Role based status types MST, NST, TST and SST are created.
					2. Resource type RT is created selecting MST, NST, TST and SST status types.
					3. Resource RS is created under RT
					4. User A is created selecting;
					
					a. View Resource right on RS.
					b. Role R1 to view and update status types NST, MST, TST and SST.
					c. 'Maintain Event Templates' and 'Maintain Events' right.
					
					5. Event Template ET is created selecting RT,NST, MST, TST and SST.
					6. Event EVE is created under ET selecting RS and providing address.
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
	public void testBQS68112()throws Exception{
		try{
			gstrTCID = "68112";			
			gstrTO = "Verify that an event template can be deactivated.";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");

			Login objLogin = new Login();// object of class Login
			EventSetup objEve=new EventSetup();
			MobileView objMob=new MobileView();
			CreateUsers objCreateUsers=new CreateUsers();
			StatusTypes objST=new StatusTypes();	
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();			
			Roles objRole=new Roles();
			
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			
			String strFILE_PATH=pathProps.getProperty("TestData_path");
			ReadData objReadData = new ReadData (); 
			
					
			String[] strTestData=new String[10];
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
			String strEveTemp=objReadData.readInfoExcel("Event Temp Data", 2, 2, strFILE_PATH);
			strEveTemp=strEveTemp+strTimeText;
					
			String strResType="AutoRt_"+strTimeText;
								
			String strEveName="AutoEv_"+strTimeText;
			
			String strInfo="This is an automation event";
			
			String strUserName = objReadData.readData("Login", 3, 1);
			String strPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);
						
							
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			int intResCnt=0;
			String strLoginUserName ="AutoUsr"+System.currentTimeMillis();
			String strLoginPassword ="abc123";
			String strFullUserName="Full"+strLoginUserName;
			
			String strMulStatTypeValue="Multi";
			String strMulTypeName="AutoMSt_"+strTimeText;
			String strMulStatTypDefn="Auto";
			String strMulStatTypeColor="Black";
			
			String strNumStatTypeValue="Number";
			String strNumTypeName="AutoNSt_"+strTimeText;
			String strNumStatTypDefn="Auto";
					
			String strTxtStatTypeValue="Text";
			String strTxtTypeName="AutoTSt_"+strTimeText;
			String strTxtStatTypDefn="Auto";
						
			String strSatuStatTypeValue="Saturation Score";
			String strSatuTypeName="AutoSSt_"+strTimeText;
			String strSatuStatTypDefn="Auto";
			
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strSTvalue[]=new String[4];
			String strStatusValue[]=new String[2];
			
			strStatusValue[0]="";
			strStatusValue[1]="";
				
			String strResource="AutoRs_"+strTimeText;
		
			String strAbbrv="Rs";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strState="Alabama";
			
			String strRSValue[]=new String[1];
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String strRoleValue="";
			
			String strTempDef=objReadData.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objReadData.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objReadData.readInfoExcel("Event Temp Data", 2, 5, strFILE_PATH);
			String strIEIconSrc=objReadData.readInfoExcel("Event Temp Data", 2, 6, strFILE_PATH);
			String strIconSrc=objReadData.readInfoExcel("FirefoxTestData", 2, 3,
				     strFILE_PATH);
			
			String strResTypeArr[]=new String[1];
				
		
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			//Login as Region Admin
			String strFailMsg = objLogin.login(seleniumPrecondition, strUserName,
					strPassword);
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//nav to default region
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
					
			//Multi ST
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strMulStatTypeValue, strMulTypeName, strMulStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
					
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strMulTypeName);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(seleniumPrecondition, strMulTypeName, strStatusName1, strMulStatTypeValue, strMulStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(seleniumPrecondition, strMulTypeName, strStatusName2, strMulStatTypeValue, strMulStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,strMulTypeName, strStatusName1);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strStatusValue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,strMulTypeName, strStatusName2);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strStatusValue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			//Number ST
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strNumStatTypeValue, strNumTypeName, strNumStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strNumTypeName);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			//Saturation Score ST
					
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strSatuStatTypeValue, strSatuTypeName, strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strSatuTypeName);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[2]=strStatValue;
				}else{
					strFailMsg="Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			//Text ST
					
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strTxtTypeName);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[3]=strStatValue;
				}else{
					strFailMsg="Failed to fetch Text status type value";
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
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResType, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			for(int intST=0;intST<strSTvalue.length;intST++){
				try {
					assertEquals("", strFailMsg);
					strFailMsg = objRs.selDeselctOnlySTInEditRSLevelPage(seleniumPrecondition,strSTvalue[intST], true);
																	
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
						
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition, strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strRestypVal = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition, strResType);
				
				if(strRestypVal.compareTo("")!=0){
					strFailMsg="";
					strResTypeArr[0]=strRestypVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
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
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.createResourceWithMandFields(seleniumPrecondition, strResource, strAbbrv, strResType, strStandResType, strContFName, strContLName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.saveAndNavToAssignUsr(seleniumPrecondition, strResource);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.saveAndVerifyResource(seleniumPrecondition, strResource, "No", "", strAbbrv, strResType);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
					
			try {
				assertEquals("", strFailMsg);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
				
				if(strResVal.compareTo("")!=0){
					strFailMsg="";
					strRSValue[0]=strResVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
										
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleName);
				
				if(strRoleValue.compareTo("")!=0){
					strFailMsg="";
					
				}else{
					strFailMsg="Failed to fetch Role value";
				}
				
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
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strLoginUserName, strLoginPassword, strLoginPassword, strFullUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(seleniumPrecondition, strResource, false, false, false, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
								
				strFailMsg = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strLoginUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg="'Event Template List' screen is NOT displayed.";
				//navigate to Event Setup
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
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(seleniumPrecondition, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIconSrc,"","","","",true,strResTypeArr,strSTvalue,true,false,false);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(seleniumPrecondition));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
								
				strFailMsg=objEve.createEventWithAddr(seleniumPrecondition, strEveTemp, strResource, strEveName, strInfo,strState,true);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			try {
				assertTrue(strFailMsg.equals(""));				
				strFailMsg=objLogin.logout(seleniumPrecondition);					
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
			try {
				assertTrue(strFailMsg.equals(""));		
				strFailMsg = objLogin.newUsrLogin(selenium, strLoginUserName,
						strLoginPassword);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
						
			/*
			 * Step 2: Navigate to Event >> Event Setup. <-> 'Event Template List' screen is displayed. 
			 */
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "'Event Template List' screen is NOT displayed.";
				assertTrue(objEve.navToEventSetup(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * Step 3: Click on 'Edit' link next to 'ET', deselect 'Active' check box and click on 'Save'. 	<->	'Event Template List' screen is displayed.
			 * <-> 'ET' is not listed. 
			 */
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg=objEve.editEventTemplate(selenium,strEveTemp);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}				
			
			try {
				assertTrue(strFailMsg.equals(""));
				
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(selenium, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIEIconSrc,"","","","",false,strResTypeArr,strSTvalue,false,false,false);
				if(strFailMsg.compareTo("The template name and definition is NOT displayed in the 'Event Template List' screen with the specified icon")==0){
					/*
					 * Step 4: Select the check-box "include inactive event types" displayed at the top right corner above the event template list table. 		'ET' is displayed in the 'Event Template List' screen.

					'Active' column is included for the event template list table.
					
					'Disabled' is displayed under the column 'Active' for 'ET'. 
					 */
					//Select include inactive event types
					selenium.click(propElementDetails.getProperty("Event.InactiveEvent"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					int intCnt=0;
					do{
						try {

							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
											+ strEveTemp + "']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					try {
						assertTrue(selenium.isTextPresent("Event Template List"));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
										+ strEveTemp + "']"));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]/thead/tr/th[5]/a[text()='Active']"));
						assertEquals(
								"Disabled",
								selenium
										.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
												+ strEveTemp + "']/parent::tr/td[5]"));

						
						log4j
								.info("'Disabled' is displayed under the column 'Active' for "+strEveTemp+" in Event Template list screen");
				
					/*
					 * Step 5: 	Navigate to Event >> Event Management. 	<->	EVE is listed on the 'Event Management' screen with 'Edit' and 'End' links.
					 */
						strFailMsg="";
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
								assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"+strEveName+"']/parent::tr/td[1]/a[text()='Edit']/following-sibling::a[text()='End']"));
								log4j.info(strEveName+" is listed on the 'Event Management' screen with 'Edit' and 'End' links.");
								intResCnt++;
							}catch(AssertionError ae){
								log4j.info(strEveName+" is NOT listed on the 'Event Management' screen with 'Edit' and 'End' links.");
								gstrReason=strEveName+" is NOT listed on the 'Event Management' screen with 'Edit' and 'End' links.";
							}
							/*Step 6: Click on 'Create New Event' button. <->	'ET' is not displayed in the 'Select Event Template' screen.
							*/
							//Click on Create New Event
							selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
							selenium.waitForPageToLoad(gstrTimeOut);
							
							intCnt=0;
							do{
								try {

									assertEquals("Select Event Template", selenium.getText("css=h1"));
									break;
								}catch(AssertionError Ae){
									Thread.sleep(1000);
									intCnt++;
								
								} catch (Exception Ae) {
									Thread.sleep(1000);
									intCnt++;
								}
							}while(intCnt<60);
							
							try{
								assertEquals("Select Event Template", selenium.getText("css=h1"));
								log4j.info("'Select Event Template' screen is displayed.");
								
								try{
									assertFalse(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"+strEveTemp+"']"));
									log4j.info(strEveTemp+" is not displayed in the 'Select Event Template' screen.");
									intResCnt++;
								}catch(AssertionError ae){
									log4j.info(strEveTemp+" is still displayed in the 'Select Event Template' screen.");
									gstrReason=gstrReason+" "+strEveTemp+" is still displayed in the 'Select Event Template' screen.";
								}
								
							}catch(AssertionError ae){
								log4j.info("'Select Event Template' screen is NOT displayed.");
								gstrReason=gstrReason+" 'Select Event Template' screen is NOT displayed.";
							}
							
							/*
							 * Step 7: 	Click on 'Mobile View' link on the footer of application. <->	'Main Menu' screen is displayed.
							   Step 9:	Click on 'Events' link. <->	Event 'EVE' is listed in the 'Event List' screen.
							   Step 10: Click on Event 'EVE'. <->	'Event Detail' screen is displayed.
							   Step 11: Click on 'Resources' link. <->	'RS' is listed in the 'Event Resources' screen. 
							 */
							strFailMsg=objMob.navToEventDetailInMob(selenium, strEveName, strIEIconSrc, strResource);
							try{
								assertTrue(strFailMsg.equals(""));	
								intResCnt++;
							}catch(AssertionError ae){
								gstrReason = gstrReason+" "+strFailMsg;
							}		
							
							if(intResCnt==3){
								gstrResult="PASS";
								
								//Write result data
								strTestData[0]= propEnvDetails.getProperty("Build");
								strTestData[1]=gstrTCID;
								strTestData[2]=strLoginUserName+"/"+strLoginPassword;
								strTestData[3]=strEveTemp;
								strTestData[4]=strEveName;
								strTestData[5]="Icon Selected: "+strAsscIcon+", Resource Name: "+strResource+" Check in Map+Mobile";
								
								String strWriteFilePath=pathProps.getProperty("WriteResultPath");
								objOFC.writeResultData(strTestData, strWriteFilePath, "Events");
							}
							
						} catch (AssertionError Ae) {
							gstrReason = strFailMsg;
						}
					} catch (AssertionError ae) {
						log4j
						.info("'Disabled' is NOT displayed under the column 'Active' for "+strEveTemp+" in Event Template list screen");
						gstrReason = "'Disabled' is NOT displayed under the column 'Active' for "+strEveTemp+" in Event Template list screen";

					}
					
				}else{
					gstrReason = "The template name and definition is still displayed in the 'Event Template List' screen with the specified icon";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "68112";
			gstrTO = "Verify that an event template can be deactivated.";
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
	' Description: Verify that an event template can be re-activated.
	' Precondition: 1. Role based status types MST, NST, TST and SST are created.
				2. Resource type RT is created selecting MST, NST, TST and SST status types.
				3. Resource RS is created under RT
				4. User A is created selecting;
				
				a. View Resource right on RS.
				b. Role R1 to view and update status types NST, MST, TST and SST.
				c. 'Maintain Event Templates' and 'Maintain Events' right.
				
				5. Event Template ET is created selecting RT,NST, MST, TST and SST.
				6. Event EVE is created selecting ET and RS
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
	public void testBQS68113()throws Exception{
		try{
			gstrTCID = "68113";			
			gstrTO = "Verify that an event template can be re-activated";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");

			Login objLogin = new Login();// object of class Login
			EventSetup objEve=new EventSetup();
			CreateUsers objCreateUsers=new CreateUsers();
			StatusTypes objST=new StatusTypes();	
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();			
			Roles objRole=new Roles();
			
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			
			String strFILE_PATH=pathProps.getProperty("TestData_path");
			ReadData objReadData = new ReadData (); 
			
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
			String strEveTemp=objReadData.readInfoExcel("Event Temp Data", 2, 2, strFILE_PATH);
			strEveTemp=strEveTemp+strTimeText;
			
			String strTempDef=objReadData.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objReadData.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objReadData.readInfoExcel("Event Temp Data", 2, 5, strFILE_PATH);
			String strIEIconSrc=objReadData.readInfoExcel("Event Temp Data", 2, 6, strFILE_PATH);
			String strIconSrc=objReadData.readInfoExcel("FirefoxTestData", 2, 3,
				     strFILE_PATH);
			
			String strResType="AutoRt_"+strTimeText;
									
			String strEveName="AutoEv_"+strTimeText;
			
			String strInfo="This is an automation event";
			
			String strUserName = objReadData.readData("Login", 3, 1);
			String strPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);
							
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			int intResCnt=0;
			String strLoginUserName ="AutoUsr"+System.currentTimeMillis();
			String strLoginPassword ="abc123";
			String strFullUserName="Full"+strLoginUserName;
			
			String strMulStatTypeValue="Multi";
			String strMulTypeName="AutoMSt_"+strTimeText;
			String strMulStatTypDefn="Auto";
			String strMulStatTypeColor="Black";
			
			String strNumStatTypeValue="Number";
			String strNumTypeName="AutoNSt_"+strTimeText;
			String strNumStatTypDefn="Auto";
					
			String strTxtStatTypeValue="Text";
			String strTxtTypeName="AutoTSt_"+strTimeText;
			String strTxtStatTypDefn="Auto";
						
			String strSatuStatTypeValue="Saturation Score";
			String strSatuTypeName="AutoSSt_"+strTimeText;
			String strSatuStatTypDefn="Auto";
			
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strSTvalue[]=new String[4];
			String strStatusValue[]=new String[2];
			
			strStatusValue[0]="";
			strStatusValue[1]="";
				
			String strResource="AutoRs_"+strTimeText;
		
			String strAbbrv="Rs";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
						
			String strRSValue[]=new String[1];
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String strRoleValue="";
									
			String strResTypeArr[]=new String[1];
				
		
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			//Login as Region Admin
			String strFailMsg = objLogin.login(seleniumPrecondition, strUserName,
					strPassword);
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//nav to default region
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
					
			//Multi ST
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strMulStatTypeValue, strMulTypeName, strMulStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
					
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strMulTypeName);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(seleniumPrecondition, strMulTypeName, strStatusName1, strMulStatTypeValue, strMulStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(seleniumPrecondition, strMulTypeName, strStatusName2, strMulStatTypeValue, strMulStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,strMulTypeName, strStatusName1);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strStatusValue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,strMulTypeName, strStatusName2);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strStatusValue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			//Number ST
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strNumStatTypeValue, strNumTypeName, strNumStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strNumTypeName);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			//Saturation Score ST
					
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strSatuStatTypeValue, strSatuTypeName, strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strSatuTypeName);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[2]=strStatValue;
				}else{
					strFailMsg="Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			//Text ST
					
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strTxtTypeName);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strSTvalue[3]=strStatValue;
				}else{
					strFailMsg="Failed to fetch Text status type value";
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
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResType, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			for(int intST=0;intST<strSTvalue.length;intST++){
				try {
					assertEquals("", strFailMsg);
					strFailMsg = objRs.selDeselctOnlySTInEditRSLevelPage(seleniumPrecondition,strSTvalue[intST], true);
																	
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
						
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition, strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strRestypVal = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition, strResType);
				
				if(strRestypVal.compareTo("")!=0){
					strFailMsg="";
					strResTypeArr[0]=strRestypVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
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
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.createResourceWithMandFields(seleniumPrecondition, strResource, strAbbrv, strResType, strStandResType, strContFName, strContLName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.saveAndNavToAssignUsr(seleniumPrecondition, strResource);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.saveAndVerifyResource(seleniumPrecondition, strResource, "No", "", strAbbrv, strResType);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
					
			try {
				assertEquals("", strFailMsg);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
				
				if(strResVal.compareTo("")!=0){
					strFailMsg="";
					strRSValue[0]=strResVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
										
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleName);
				
				if(strRoleValue.compareTo("")!=0){
					strFailMsg="";
					
				}else{
					strFailMsg="Failed to fetch Role value";
				}
				
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
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strLoginUserName, strLoginPassword, strLoginPassword, strFullUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(seleniumPrecondition, strResource, false, false, false, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
								
				strFailMsg = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strLoginUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg="'Event Template List' screen is NOT displayed.";
				//navigate to Event Setup
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
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(seleniumPrecondition, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIconSrc,"","","","",true,strResTypeArr,strSTvalue,true,false,false);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(seleniumPrecondition));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
								
				strFailMsg=objEve.createEvent(seleniumPrecondition, strEveTemp, strResource, strEveName, strInfo,true);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			try {
				assertTrue(strFailMsg.equals(""));				
				strFailMsg=objLogin.logout(seleniumPrecondition);					
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
			/*
			 * Step 2: 	Login as user A and navigate to Event >> Event Setup. <->	'Event Template List' screen is displayed. 
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
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg=objEve.editEventTemplate(selenium,strEveTemp);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}				
			
			try {
				assertTrue(strFailMsg.equals(""));
				
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(selenium, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIEIconSrc,"","","","",false,strResTypeArr,strSTvalue,false,false,false);
				if(strFailMsg.compareTo("The template name and definition is NOT displayed in the 'Event Template List' screen with the specified icon")==0){
					/*
					 * Step 3: Select the check-box "include inactive event types" displayed at the top right corner above the event template list table. <->	'ET' is displayed in the 'Event Template List' screen.

						<-> 'Active' column is included for the event template list table.

						<-> 'Disabled' is displayed under the column 'Active' for 'ET'. 
					 */
					//Select include inactive event types
					selenium.click(propElementDetails.getProperty("Event.InactiveEvent"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					try {
						assertTrue(selenium.isTextPresent("Event Template List"));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
										+ strEveTemp + "']"));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]/thead/tr/th[5]/a[text()='Active']"));
						assertEquals(
								"Disabled",
								selenium
										.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
												+ strEveTemp + "']/parent::tr/td[5]"));
						log4j
						.info("'Disabled' is displayed under the column 'Active' for "+strEveTemp+" in Event Template list screen");
						strFailMsg="";
						try {
							assertTrue(strFailMsg.equals(""));	
							strFailMsg= "'Event Template List' screen is NOT displayed.";
							assertTrue(objEve.navToEventSetup(selenium));
							strFailMsg="";	
						} catch (AssertionError Ae) {
							gstrReason = strFailMsg;
						}
						
						/*
						 * Step 4: Click on 'Edit' link next to 'ET', select 'Active' check box and click on 'Save'. <-> 'ET' is displayed in the 'Event Template List' screen.

							<-> "include inactive event types" check-box remains selected.
							
							<-> 'Active' column is still retained in the event template list table.
							
							<-> 'Active' is displayed under the column 'Active' for 'ET'. 
						 */
						try {
							assertTrue(strFailMsg.equals(""));	
							strFailMsg=objEve.editEventTemplate(selenium,strEveTemp);
						} catch (AssertionError Ae) {
							gstrReason = strFailMsg;
						}				
						strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(selenium, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIEIconSrc,"","","","",false,strResTypeArr,strSTvalue,true,false,false);
						if(strFailMsg.compareTo("The template name and definition is NOT displayed in the 'Event Template List' screen with the specified icon")==0){
						
							try {
								assertTrue(selenium.isTextPresent("Event Template List"));
								assertTrue(selenium
										.isChecked(propElementDetails.getProperty("Event.InactiveEvent")));
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
												+ strEveTemp + "']"));
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table[2]/thead/tr/th[5]/a[text()='Active']"));
								assertEquals(
										"Active",
										selenium
												.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
														+ strEveTemp + "']/parent::tr/td[5]"));
								log4j
								.info("'Active' is displayed under the column 'Active' for "+strEveTemp+" in Event Template list screen");
								strFailMsg="";
								/*
								 * Step 5: 	Navigate to Event >> Event Management. <->	Event EVE is listed on the 'Event Management' screen.
								 */
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
										assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"+strEveName+"']/parent::tr/td[1]/a[text()='Edit']/following-sibling::a[text()='End']"));
										log4j.info(strEveName+" is listed on the 'Event Management' screen with 'Edit' and 'End' links.");
										intResCnt++;
									}catch(AssertionError ae){
										log4j.info(strEveName+" is NOT listed on the 'Event Management' screen with 'Edit' and 'End' links.");
										gstrReason=strEveName+" is NOT listed on the 'Event Management' screen with 'Edit' and 'End' links.";
									}
									/*
									 * Step 6: Click on 'Create New Event' button. <->	'ET' is displayed in the 'Select Event Template' screen. 
									 */
									//Click on Create New Event
									selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
									selenium.waitForPageToLoad(gstrTimeOut);
									
									try{
										assertEquals("Select Event Template", selenium.getText("css=h1"));
										log4j.info("'Select Event Template' screen is displayed.");
										
										try{
											assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"+strEveTemp+"']"));
											log4j.info(strEveTemp+" is displayed in the 'Select Event Template' screen.");
											intResCnt++;
										}catch(AssertionError ae){
											log4j.info(strEveTemp+" is NOT displayed in the 'Select Event Template' screen.");
											gstrReason=gstrReason+" "+strEveTemp+" is NOT displayed in the 'Select Event Template' screen.";
										}
										
									}catch(AssertionError ae){
										log4j.info("'Select Event Template' screen is NOT displayed.");
										gstrReason=gstrReason+" 'Select Event Template' screen is NOT displayed.";
									}
									
									if(intResCnt==2){
										gstrResult="PASS";
																	
									}
									
								} catch (AssertionError Ae) {
									gstrReason = strFailMsg;
								}
							} catch (AssertionError ae) {
								log4j
								.info("'Active' is NOT displayed under the column 'Active' for "+strEveTemp+" in Event Template list screen");
								gstrReason = "'Active' is NOT displayed under the column 'Active' for "+strEveTemp+" in Event Template list screen";
	
							}
						}else{
							gstrReason = "The template name and definition is displayed in the 'Event Template List' screen with the specified icon";
						}
					} catch (AssertionError ae) {
						log4j
						.info("'Disabled' is NOT displayed under the column 'Active' for "+strEveTemp+" in Event Template list screen");
						gstrReason = "'Disabled' is NOT displayed under the column 'Active' for "+strEveTemp+" in Event Template list screen";

					}
					
				}else{
					gstrReason = "The template name and definition is displayed in the 'Event Template List' screen with the specified icon";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "68113";
			gstrTO = "Verify that an event template can be re-activated";
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
