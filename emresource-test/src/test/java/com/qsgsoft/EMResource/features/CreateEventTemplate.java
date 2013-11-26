package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
/**
'*************************************************************
' Description       : The class CreateEventTemplate 
' Requirement Group : Creating & managing Events 
' Requirement       : Create event template
' Date              : 17-Apr-2012
' Author            :QSG
'---------------------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*************************************************************
*/
public class CreateEventTemplate {
	
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.CreateEventTemplate");
	static{
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
		
		try {
			selenium.close();
		} catch (Exception e) {

		}
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
	
	/****************************************************************************************
	' Description: Create an event template selecting resource types, status types and verify
	               that the associated resource is available while creating an event under it
	' Arguments  :
	' Returns    :
	' Date       : 17-Apr-2012
	' Author     :QSG
	'----------------------------------------------------------------------------------------
	' Modified Date                                                          Modified By
	' <Date>                                                                 <Name>
	'***************************************************************************************/

	@Test
	public void testBQS68108()throws Exception{
		try{
			gstrTCID = "68108";			
			gstrTO = "Create an event template selecting resource types, status types and verify that the " +
					"associated resource is available while creating an event under it";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");

			Login objLogin = new Login();// object of class Login
			EventSetup objEve=new EventSetup();
			
			int intResCnt=0;
			String strTestData[]=new String[10];
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			
			String strFILE_PATH=pathProps.getProperty("TestData_path");
			ReadData objReadData = new ReadData (); 			
			CreateUsers objCreateUsers=new CreateUsers();
			StatusTypes objST=new StatusTypes();	
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();			
			Roles objRole=new Roles();
			
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			//fetch the required test data from excel
			String strEveTemp=objReadData.readInfoExcel("Event Temp Data", 2, 2, strFILE_PATH);
			strEveTemp=strEveTemp+strTimeText;
			
			String strTempDef=objReadData.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objReadData.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objReadData.readInfoExcel("Event Temp Data", 2, 5, strFILE_PATH);
			String strIconSrc=objReadData.readInfoExcel("Event Temp Data", 2, 6, strFILE_PATH);
			
			String strResType="AutoRt_"+strTimeText;			
			String strEveName="AutoEv_"+strTimeText;
			String strInfo="This is an automation event";
						
			MobileView objMob=new MobileView();
			
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
			String strCountry="Autauga County";
			String strRSValue[]=new String[1];
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String strRoleValue="";
			
				
			String strResTypeArr[]=new String[1];
				
			String strUserName = objReadData.readData("Login", 3, 1);
			String strPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);
		
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			
	 	/*STEP:Preconditions:
	 		1. Role based status types MST, NST, TST and SST are created.
	 		2. Resource type RT is created selecting MST, NST, TST and SST status types.
	 		3. Resource RS is created under RT
	 		4. User A is created selecting;
		 		a. 'View Resource' right on RS.
		 		b. Role R1 to view and update status types NST, MST, TST and SST.
		 		c. "Setup User Accounts" right.
		 		d. 'Maintain Event Templates' and 'Maintain Events' right. 
	 		*/
			//Login as Region Admin
			String strFailMsg = objLogin.login(selenium, strUserName,
					strPassword);
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//nav to default region
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
				
			//Multi ST
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strMulStatTypeValue, strMulTypeName, strMulStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
					
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(selenium, strMulTypeName);
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
				strFailMsg = objST.createSTWithinMultiTypeST(selenium, strMulTypeName, strStatusName1, strMulStatTypeValue, strMulStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.createSTWithinMultiTypeST(selenium, strMulTypeName, strStatusName2, strMulStatTypeValue, strMulStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchStatValInStatusList(selenium,strMulTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(selenium,strMulTypeName, strStatusName2);
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
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strNumStatTypeValue, strNumTypeName, strNumStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(selenium, strNumTypeName);
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
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strSatuStatTypeValue, strSatuTypeName, strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(selenium, strSatuTypeName);
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
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strStatValue = objST.fetchSTValueInStatTypeList(selenium, strTxtTypeName);
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
				strFailMsg = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(selenium, strResType, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			for(int intST=0;intST<strSTvalue.length;intST++){
				try {
					assertEquals("", strFailMsg);
					strFailMsg = objRs.selDeselctOnlySTInEditRSLevelPage(selenium,strSTvalue[intST], true);
																	
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
						
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(selenium, strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strRestypVal = objRT.fetchResTypeValueInResTypeList(selenium, strResType);
				
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
				strFailMsg = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
					
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.createResourceWitLookUPadres(selenium, strResource, strAbbrv, strResType, strContFName, strContLName,strState,strCountry,strStandResType);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
					
			try {
				assertEquals("", strFailMsg);
				String strResVal = objRs.fetchResValueInResList(selenium, strResource);
				
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
				strFailMsg = objRole.navRolesListPge(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.CreateRoleWithAllFields(selenium, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium, strRoleName);
				
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
				strFailMsg = objCreateUsers.navUserListPge(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
				
				
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(selenium, strLoginUserName, strLoginPassword, strLoginPassword, strFullUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(selenium, strResource, false, false, false, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(selenium, strRoleValue, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
								
				strFailMsg = objCreateUsers.savVrfyUser(selenium, strLoginUserName);
				
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
			 * Step 2: Login as user A, navigate to Event >> Event Setup. <-> 'Event Template List' screen is displayed. 
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
				strFailMsg="'Event Template List' screen is NOT displayed.";
				//navigate to Event Setup
				assertTrue(objEve.navToEventSetup(selenium));	
				strFailMsg="";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * Step 3: 	Click on 'Create New Event Template' button. <-> 'Create New Event Template' screen is displayed.
			 */
			try {
				assertTrue(strFailMsg.equals(""));	
				//navigate to Event Template
				strFailMsg=objEve.createEventTemplate(selenium);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * Step 4: Provide mandatory data ('ET' as template name), select RT under 'Resource Types' section, select MST, NST, TST, SST check boxes under 'Status Types' section and click on 'Save'. 
			 *  <->	'Event Notification Preferences for < template name >' screen is displayed.

				<-> Users created in that particular region are being listed.

				<-> E-mail, Pager and Web check boxes are displayed for each user.
			   Step 5: Click on 'Save' button. <->	Event template created is listed on the 'Event Template List' screen. 
			 */
			try {
				assertTrue(strFailMsg.equals(""));
				//fill the required fields in Create Event Template and save
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(selenium, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIconSrc,"","","","",true,strResTypeArr,strSTvalue,true,false,false);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
						
			/*
			 * Step 6: Navigate to Event >> Event Management <-> 'Event Management' screen is displayed. 
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
			 * Step 7: Click on 'Create New Event' button. <->	'Select Event Template' screen is displayed.
			 * Step 8: Click on 'Create' associated with 'ET'. <->	'Create New Event' screen is displayed.

				<-> Resource RS is displayed under 'Resources to Participate in This Event' section.
			   Step 9: Create an event 'EVE' providing mandatory data and selecting resource RS. <-> Event 'EVE' is listed on 'Event List' screen. 
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
				 * Step 10: Click on the event banner of 'EVE'. <->	Resource 'RS' is displayed on the 'Event Status' screen under RT along with NST, MST, TST and SST status types. 
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

		 /* Step 11: Click on 'Mobile View' link on the footer of application. <-> 'Main Menu' screen is displayed.
			<-> Event count is updated for 'Events'.
		   Step 12: Click on 'Events' link. <-> 'Event List' screen is displayed.
			<-> Event 'EVE' is listed with the selected icon. 
		   Step 13: Click on Event 'EVE'. <-> 'Event Detail' screen is displayed. 
		   Step 14: Click on 'Resources' link. <-> 'RS' is listed in the 'Event Resources' screen.
		 */			
				strFailMsg = objMob.navToEventDetailInMob(selenium, strEveName,
						strIconSrc, strResource);
				try {
					assertTrue(strFailMsg.equals(""));
					intResCnt++;
				} catch (AssertionError ae) {
					gstrReason = strFailMsg;
				}

				if (intResCnt == 2) {
					gstrResult = "PASS";
					// Write result data
					strTestData[0] = propEnvDetails.getProperty("Build");
					strTestData[1] = gstrTCID;
					strTestData[2] = strLoginUserName + "/" + strLoginPassword;
					strTestData[3] = strEveTemp;
					strTestData[4] = strEveName;
					strTestData[5] = "Icon Selected: " + strAsscIcon
							+ ", Resource Name: " + strResource
							+ " check in mobile";
					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestData, strWriteFilePath,
							"Events");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
		} catch (Exception e) {

			Exception excReason;
			gstrTCID = "68108";
			gstrTO = "Create an event template selecting resource types, status types and verify that the associated resource " +
					"is available while creating an event under it";
			gstrResult = "FAIL";
			excReason = null;

			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();

		}
	}

}
