package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
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

/***************************************************************
' Description       :This class includes requirement test cases
' Requirement Group :Reports
' Requirement       :Status Snapshot report
' Date		        :16-July-2012
' Author	        :QSG
'---------------------------------------------------------------
' Modified Date                                      Modified By
' <Date>                           	                 <Name>
'***************************************************************/

public class FTSStatusSnapShotReport_AutoIT {

	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSStatusSnapShotReport_AutoIT");
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
	Properties propElementAutoItDetails, propAutoItDetails;
	Properties pathProps;
	String gstrTimeOut = "";
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
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();
		
		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
			    4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
			    .getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try {
			selenium.close();
		} catch (Exception e) {

		}

		// kill browser
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
		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}

	/*******************************************************************
	'Description	:Verify that status snapshot report can be generated 
	                 for a user with 'Report - Status Snapshot' right.
	'Arguments		:None
	'Returns		:None
	'Date			:30-07-2012
	'Author			:QSG
	'-------------------------------------------------------------------
	'Modified Date				                           Modified By
	'Date					                               Name
	*******************************************************************/
	@Test
	public void testFTS91477() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		General objGen = new General();
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Login objLogin = new Login();// object of class Login
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		try {
			gstrTCID = "91477"; // Test Case Id
			gstrTO = " Verify that status snapshot report can be generated for a user with"
					+ " 'Report - Status Snapshot' right.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			gstrTimetake = dts.timeNow("HH:mm:ss");			
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";

			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";

			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";
			
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";			
			String strSTvalue[] = new String[4];
			//RT
			String strResType = "AutoRt_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;			
			String strRSValue[] = new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";			
			String strAbbrv = "Rs";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			//Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			//User
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			
			String strLastUpdMST = "";
			String strLastUpdNST = "";
			String strLastUpdSST = "";
			String strLastUpdTST = "";

			String strUpdMulValue = "";
			String strUpdNumValue = "";
			String strUpdSatuValue = "";
			String strUpdTxtValue = "";
			String strUpdatNumValue1 = "2";
			String strUpdatNumValue2 = "4";

			String strUpdatTxtValue1 = "s";
			String strUpdatTxtValue2 = "st";

			String strUpdSatuValue1 = "393";
			String strUpdSatuValue2 = "429";

			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7",
					"8" };
			String strScUpdValue2[] = { "1", "2", "3", "4", "5", "6", "7", "8",
					"9" };
			
			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRegGenTime = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			
			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep_"
					+ gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "StatSnapRep_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");
					
		/*
		 * Preconditions:1. Resource type RT is created selecting status types ST1 and ST2.
		                 2. Resource RS is created under resource type RT.
		                 3. Event template ET is created selecting RT and ST1. 
		   Expected Result:No Expected Result 
		 */
			strFuncResult = objLogin.login(selenium, strAdmUserName,
					strAdmPassword);
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
									
			//Multi ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium, strMulStatTypeValue, strMulTypeName, strMulStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(selenium, strMulTypeName);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strSTvalue[0]=strStatValue;
				}else{
					strFuncResult="Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium, strMulTypeName, strStatusName1, strMulStatTypeValue, strMulStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium, strMulTypeName, strStatusName2, strMulStatTypeValue, strMulStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(selenium,strMulTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(selenium,strMulTypeName, strStatusName2);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strStatusValue[1]=strStatValue;
				}else{
					strFuncResult="Failed to fetch status value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Number ST
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium, strNumStatTypeValue, strNumTypeName, strNumStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(selenium, strNumTypeName);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strSTvalue[1]=strStatValue;
				}else{
					strFuncResult="Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Saturation Score ST
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium, strSatuStatTypeValue, strSatuTypeName, strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(selenium, strSatuTypeName);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strSTvalue[2]=strStatValue;
				}else{
					strFuncResult="Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Text ST
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium, strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(selenium, strTxtTypeName);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strSTvalue[3]=strStatValue;
				}else{
					strFuncResult="Failed to fetch Text status type value";
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium, strResType, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		

			for(int i=1;i<strSTvalue.length;i++){

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium, strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
			
					
			//Update user
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
				strFuncResult = objCreateUsers.selectResourceRights(selenium, strResource, false, true, false, true);
				
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
				strFuncResult = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"), true);
				
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
				String[] strRoleStatType = { strMulTypeName,strNumTypeName,strSatuTypeName,strTxtTypeName};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objViewMap.updateMultiStatusType_ChangeVal(selenium,
							strResource, strMulTypeName, strSTvalue[0],
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
				strLastUpdMST=strArFunRes[0]+" "+strArFunRes[1]+" "+strCurYear+" "+strArFunRes[2]+":"+strArFunRes[3];
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objViewMap.updateNumStatusType(selenium, strResource, strNumTypeName, strSTvalue[1], strUpdatNumValue1,strUpdatNumValue2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try{
				assertEquals("", strFuncResult);
				String[] strArFunRes=new String[5];
				strArFunRes=objViewMap.getUpdSTValueInMap(selenium, "3");			
				strFuncResult=strArFunRes[1];			
				strUpdNumValue=strArFunRes[0];
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("", strFuncResult);
				String[] strArFunRes=new String[5];
				strArFunRes=objViewMap.getUpdTimeValue(selenium, "2");
				strFuncResult=strArFunRes[4];	
				strLastUpdNST=strArFunRes[0]+" "+strArFunRes[1]+" "+strCurYear+" "+strArFunRes[2]+":"+strArFunRes[3];
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objViewMap.updateSatuScoreStatusType(selenium, strResource, strSatuTypeName, strSTvalue[2], strScUpdValue1,strScUpdValue2,strUpdSatuValue1,strUpdSatuValue2);
								
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try{
				assertEquals("", strFuncResult);
				String[] strArFunRes=new String[5];
				strArFunRes=objViewMap.getUpdSTValueInMap(selenium, "5");			
				strFuncResult=strArFunRes[1];			
				strUpdSatuValue=strArFunRes[0];
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			try{
				assertEquals("", strFuncResult);
				String[] strArFunRes=new String[5];
			
				strArFunRes=objViewMap.getUpdTimeValue(selenium, "3");
				strLastUpdSST=strArFunRes[0]+" "+strArFunRes[1]+" "+strCurYear+" "+strArFunRes[2]+":"+strArFunRes[3];
				strFuncResult=strArFunRes[4];						
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objViewMap.updateNumStatusType(selenium, strResource, strTxtTypeName, strSTvalue[3], strUpdatTxtValue1,strUpdatTxtValue2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try{
				assertEquals("", strFuncResult);
				String[] strArFunRes=new String[5];
				strArFunRes=objViewMap.getUpdSTValueInMap(selenium, "7");			
				strFuncResult=strArFunRes[1];			
				strUpdTxtValue=strArFunRes[0];
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("", strFuncResult);
				String[] strArFunRes=new String[5];
				strArFunRes=objViewMap.getUpdTimeValue(selenium, "4");
				strLastUpdTST=strArFunRes[0]+" "+strArFunRes[1]+" "+strCurYear+" "+strArFunRes[2]+":"+strArFunRes[3];
				strFuncResult=strArFunRes[4];			
			
			}
			catch (AssertionError Ae)
			{
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
				//Wait for 2 minutes
				Thread.sleep(120000);
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				String[] strArFunRes=new String[5];
				strArFunRes= objGen.getSnapTime(selenium);
				strFuncResult=strArFunRes[4];		
				strGenDate=dts.converDateFormat(strArFunRes[0]+strArFunRes[1]+strCurYear, "ddMMMyyyy", "MM/dd/yyyy");
				strRegGenTime=strArFunRes[2]+":"+strArFunRes[3];
				
				strGenTimeHrs=strArFunRes[2];
				strGenTimeMin=strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);												
				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium, strGenDate,strGenTimeHrs, strGenTimeMin);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				//Thread.sleep(20000);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strCSVDownlPath};
				
				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

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
					
				String[][] strReportData={{"Status Snapshot - "+strGenDate+" "+strRegGenTime+" "+propEnvDetails.getProperty("TimeZone"),"","","","",""},
						  {"Resource Type","Resource","Status Type","Status","Comments","Last Update"},
						  {strResType,strResource,strMulTypeName,strUpdMulValue,"",strLastUpdMST},
						  {strResType,strResource,strNumTypeName,strUpdNumValue,"",strLastUpdNST},
						  {strResType,strResource,strSatuTypeName,strUpdSatuValue,"",strLastUpdSST},
						  {strResType,strResource,strTxtTypeName,strUpdTxtValue,"",strLastUpdTST}
						  };
				strFuncResult=objOFC.readAndVerifyDataExcel(strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				gstrResult="PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
												
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "91477";
			gstrTO = "Verify that status snapshot report can be generated for a user with 'Report - Status Snapshot' right.";
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

	/********************************************************************
	'Description	:Edit resource type name and generate status snapshot 
	                 report and verify that report displays updated data.
	'Arguments		:None
	'Returns		:None
	'Date			:02-08-2012
	'Author			:QSG
	'--------------------------------------------------------------------
	'Modified Date				                     Modified By
	'Date					Name
	*********************************************************************/
	@Test
	public void testFTS91740() throws Exception {
		
		String strFuncResult = "";
		boolean blnLogin = false;
		ViewMap objViewMap = new ViewMap();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Login objLogin = new Login();// object of class Login
		Roles objRole = new Roles();
		Reports objRep = new Reports();
		General objGen = new General();
		try {
			gstrTCID = "91740"; // Test Case Id
			gstrTO = "Edit resource type name and generate status snapshot report and "
					+ "verify that report displays updated data"; // Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			//ST
			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";

			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";

			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";
			
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";	
			//Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			//RT
			String strResType = "AutoRt_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strEdResType = "AutoEdRt_" + strTimeText;
			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
									
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrFulName = "autouser";
			String strUsrPassword = "abc123";

			String strLastUpdMST = "";
			String strLastUpdNST = "";
			String strLastUpdSST = "";
			String strLastUpdTST = "";

			String strUpdMulValue = "";
			String strUpdNumValue = "";
			String strUpdSatuValue = "";
			String strUpdTxtValue = "";

			String strUpdatNumValue1 = "2";
			String strUpdatNumValue2 = "4";
			String strUpdatTxtValue1 = "s";
			String strUpdatTxtValue2 = "st";
			String strUpdSatuValue1 = "393";
			String strUpdSatuValue2 = "429";
			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7",
					"8" };
			String strScUpdValue2[] = { "1", "2", "3", "4", "5", "6", "7", "8",
					"9" };
			
			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRepGenTime = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strRepGenTimeBef = "";
			String strArTime[] = new String[2];

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			
			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			
			String strCSVDownlPath1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRepa_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlPath1Renamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRepa_"
					+ gstrTCID + "_" + strTimeText + ".xls";
			
			String strCSVDownlPath2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRepb_"
					+ gstrTCID + "_" + strTimeText + ".xlsX";
			
			String strCSVDownlPath2Renamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRepb_"
					+ gstrTCID + "_" + strTimeText + ".xls";
			
			String strCSVFileName1Renamed =   "StatSnapRepa_"
					+ gstrTCID + "_" + strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");
			
			String strCSVFileName2Renamed ="StatSnapRepb_"
					+ gstrTCID + "_" + strTimeText;
			
			
		/*
		 * Preconditions:1. Resource RS is created selecting RT which is associated with all
		 *                  4 status types NST, MST, TST and SST.
		          2. A region view V1 is created selecting RS and all above mentioned 4 status types.
		          3. User U1 is created with 'Update Status' right on resource RS, role to update all 4 status types.
		          4. User U1 is created selecting 'Report - Status Snapshot' right. 
		 */
			strFuncResult = objLogin.login(selenium, strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Multi ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMulStatTypeValue, strMulTypeName, strMulStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strMulTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName1, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName2, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Number ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNumStatTypeValue, strNumTypeName, strNumStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSatuStatTypeValue, strSatuTypeName,
						strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strSatuTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Text ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strTxtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Text status type value";
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 1; i < strSTvalue.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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
						strResource, false, true, false, true);

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
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);

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
				String[] strRoleStatType = { strMulTypeName, strNumTypeName,
						strSatuTypeName, strTxtTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType_ChangeVal(
						selenium, strResource, strMulTypeName, strSTvalue[0],
						strStatusName1, strStatusValue[0], strStatusName2,
						strStatusValue[1]);

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
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdTimeValue(selenium, "1");

				strFuncResult = strArFunRes[4];
				strLastUpdMST = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateNumStatusType(selenium,
						strResource, strNumTypeName, strSTvalue[1],
						strUpdatNumValue1, strUpdatNumValue2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdSTValueInMap(selenium, "3");
				strFuncResult = strArFunRes[1];
				strUpdNumValue = strArFunRes[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdTimeValue(selenium, "2");
				strFuncResult = strArFunRes[4];
				strLastUpdNST = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateSatuScoreStatusType(selenium,
						strResource, strSatuTypeName, strSTvalue[2],
						strScUpdValue1, strScUpdValue2, strUpdSatuValue1,
						strUpdSatuValue2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdSTValueInMap(selenium, "5");
				strFuncResult = strArFunRes[1];
				strUpdSatuValue = strArFunRes[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];

				strArFunRes = objViewMap.getUpdTimeValue(selenium, "3");
				strLastUpdSST = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
				strFuncResult = strArFunRes[4];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateNumStatusType(selenium,
						strResource, strTxtTypeName, strSTvalue[3],
						strUpdatTxtValue1, strUpdatTxtValue2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdSTValueInMap(selenium, "7");
				strFuncResult = strArFunRes[1];
				strUpdTxtValue = strArFunRes[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdTimeValue(selenium, "4");
				strLastUpdTST = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
				strFuncResult = strArFunRes[4];

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
				Thread.sleep(124000);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.editResrcTypeMandatoryFlds(selenium, strResType, strEdResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium, strEdResType);
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
				strRepGenTime = strArFunRes[2] + ":" + strArFunRes[3];

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
				strFuncResult = objLogin.login(selenium, strUserName,
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
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);		
				strRepGenTimeBef=dts.addTimetoExisting(strRepGenTime, -1, "HH:mm");
				strArTime=strRepGenTimeBef.split(":");
				strGenTimeHrs=strArTime[0];
				strGenTimeMin=strArTime[1];
				
			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
						
			try {
				assertEquals("", strFuncResult);				
								
				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium, strGenDate,strGenTimeHrs, strGenTimeMin);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				//Thread.sleep(20000);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strCSVDownlPath1};
				
				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath1).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileName1Renamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath1Renamed)
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
					
				String[][] strReportData={{"Status Snapshot - "+strGenDate+" "+strRepGenTimeBef+" "+propEnvDetails.getProperty("TimeZone"),"","","","",""},
						  {"Resource Type","Resource","Status Type","Status","Comments","Last Update"},
						  {strEdResType,strResource,strMulTypeName,strUpdMulValue,"",strLastUpdMST},
						  {strEdResType,strResource,strNumTypeName,strUpdNumValue,"",strLastUpdNST},
						  {strEdResType,strResource,strSatuTypeName,strUpdSatuValue,"",strLastUpdSST},
						  {strEdResType,strResource,strTxtTypeName,strUpdTxtValue,"",strLastUpdTST}
						  };
				strFuncResult=objOFC.readAndVerifyDataExcel(strReportData, strCSVDownlPath1Renamed);
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
			
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
					
				strArTime=strRepGenTime.split(":");
				strGenTimeHrs=strArTime[0];
				strGenTimeMin=strArTime[1];
				
			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);				
								
				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium, strGenDate,strGenTimeHrs, strGenTimeMin);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				//Thread.sleep(20000);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strCSVDownlPath2};
				
				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			do {

				try {
					assertTrue(new File(strCSVDownlPath2).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileName2Renamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath2Renamed)
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
				String[][] strReportData={{"Status Snapshot - "+strGenDate+" "+strRepGenTime+" "+propEnvDetails.getProperty("TimeZone"),"","","","",""},
						  {"Resource Type","Resource","Status Type","Status","Comments","Last Update"},
						  {strEdResType,strResource,strMulTypeName,strUpdMulValue,"",strLastUpdMST},
						  {strEdResType,strResource,strNumTypeName,strUpdNumValue,"",strLastUpdNST},
						  {strEdResType,strResource,strSatuTypeName,strUpdSatuValue,"",strLastUpdSST},
						  {strEdResType,strResource,strTxtTypeName,strUpdTxtValue,"",strLastUpdTST}
						  };
				strFuncResult=objOFC.readAndVerifyDataExcel(strReportData, strCSVDownlPath2Renamed);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				gstrResult="PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
														
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "91740";
			gstrTO = "Edit resource type name and generate status snapshot report and verify that report displays updated data";
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
	
	/***************************************************************
	'Description :Edit resource name and generate status snapshot 
	              report and verify that report displays updated data.
	'Arguments	 :None
	'Returns	 :None
	'Date		 :03-08-2012
	'Author		 :QSG
	'---------------------------------------------------------------
	'Modified Date				                        Modified By
	'Date					                            Name
	***************************************************************/

	@Test
	public void testFTS4051() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		ViewMap objViewMap = new ViewMap();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Login objLogin = new Login();// object of class Login
		Roles objRole = new Roles();
		General objGen = new General();
		Reports objRep = new Reports();

		try {
			gstrTCID = "4051"; // Test Case Id
			gstrTO = "Edit resource name and generate status snapshot report and verify"
					+ "that report displays updated data."; //TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";

			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";

			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";
			
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";			
			String strSTvalue[] = new String[4];
            //RT
			String strResType = "AutoRt_" + strTimeText;
			///RS
			String strResource = "AutoRs_" + strTimeText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strAbbrv = "Rs";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			String strEdResource = "AutoEdRs_" + strTimeText;
            //Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			//User
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strLastUpdMST = "";
			String strLastUpdNST = "";
			String strLastUpdSST = "";
			String strLastUpdTST = "";

			String strUpdMulValue = "";
			String strUpdNumValue = "";
			String strUpdSatuValue = "";
			String strUpdTxtValue = "";
			
			String strUpdatNumValue1 = "2";
			String strUpdatNumValue2 = "4";
			String strUpdatTxtValue1 = "s";
			String strUpdatTxtValue2 = "st";
			String strUpdSatuValue1 = "393";
			String strUpdSatuValue2 = "429";
			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7","8" };
			String strScUpdValue2[] = { "1", "2", "3", "4", "5", "6", "7", "8",	"9" };
			
			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRepGenTime = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strRepGenTimeBef = "";
			String strArTime[] = new String[2];

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRepa_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlPath1Renamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRepa_"
					+ gstrTCID + "_" + strTimeText + ".xls";

			String strCSVDownlPath2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRepb_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlPath2Renamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRepb_"
					+ gstrTCID + "_" + strTimeText + ".xls";
			
			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVFileNameRenamed1 = "StatSnapRepa_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVFileNameRenamed2 = "StatSnapRepb_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");		
		/*
		 *Preconditions:1. Resource RS is created selecting RT which is associated with all 4 
		 *                status types NST, MST, TST and SST.
                2. A region view V1 is created selecting RS and all above mentioned 4 status types.
                3. User U1 is created with 'Update Status' right on resource RS, role to update all 4 status types.
                4. User U1 is created selecting 'Report - Status Snapshot' right. 
          No Expected Result 
		 */
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
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Multi ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strMulStatTypeValue, strMulTypeName, strMulStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMulTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						strMulTypeName, strStatusName1, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						strMulTypeName, strStatusName2, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						strMulTypeName, strStatusName1);
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
						strMulTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Number ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNumStatTypeValue, strNumTypeName, strNumStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strSatuStatTypeValue, strSatuTypeName,
						strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strSatuTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Text ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTxtStatTypeValue,
						strTxtTypeName, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTxtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Text status type value";
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

			for (int i = 1; i < strSTvalue.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
							strSTvalue[i], true);
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
						seleniumPrecondition, strUserName, strUsrPassword,
						strUsrPassword, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource, false, true, false,
						true);
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
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
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
				String[] strRoleStatType = { strMulTypeName, strNumTypeName,
						strSatuTypeName, strTxtTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType_ChangeVal(
						selenium, strResource, strMulTypeName, strSTvalue[0],
						strStatusName1, strStatusValue[0], strStatusName2,
						strStatusValue[1]);

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
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdTimeValue(selenium, "1");

				strFuncResult = strArFunRes[4];
				strLastUpdMST = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateNumStatusType(selenium,
						strResource, strNumTypeName, strSTvalue[1],
						strUpdatNumValue1, strUpdatNumValue2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdSTValueInMap(selenium, "3");
				strFuncResult = strArFunRes[1];
				strUpdNumValue = strArFunRes[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdTimeValue(selenium, "2");
				strFuncResult = strArFunRes[4];
				strLastUpdNST = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateSatuScoreStatusType(selenium,
						strResource, strSatuTypeName, strSTvalue[2],
						strScUpdValue1, strScUpdValue2, strUpdSatuValue1,
						strUpdSatuValue2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdSTValueInMap(selenium, "5");
				strFuncResult = strArFunRes[1];
				strUpdSatuValue = strArFunRes[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];

				strArFunRes = objViewMap.getUpdTimeValue(selenium, "3");
				strLastUpdSST = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
				strFuncResult = strArFunRes[4];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateNumStatusType(selenium,
						strResource, strTxtTypeName, strSTvalue[3],
						strUpdatTxtValue1, strUpdatTxtValue2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdSTValueInMap(selenium, "7");
				strFuncResult = strArFunRes[1];
				strUpdTxtValue = strArFunRes[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdTimeValue(selenium, "4");
				strLastUpdTST = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
				strFuncResult = strArFunRes[4];

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
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(seleniumPrecondition,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResource_FillAllFields(seleniumPrecondition,
						strEdResource, strAbbrv, strResType, strStandResType,
						false, false, "", "", false, "", "", strState, "",
						strCountry, "", strContFName, strContLName, "", "", "",
						"", "", "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
						strEdResource, "No", "", strAbbrv, strResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strArFunRes = new String[5];
				strArFunRes = objGen.getSnapTime(seleniumPrecondition);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRepGenTime = strArFunRes[2] + ":" + strArFunRes[3];

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
				strFuncResult = objLogin.login(selenium, strUserName,
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

				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strRepGenTimeBef = dts.addTimetoExisting(strRepGenTime, -1,
						"HH:mm");
				strArTime = strRepGenTimeBef.split(":");
				strGenTimeHrs = strArTime[0];
				strGenTimeMin = strArTime[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrs, strGenTimeMin);
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
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath1).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed1);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath1Renamed)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strRepGenTimeBef
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType, strEdResource, strMulTypeName,
								strUpdMulValue, "", strLastUpdMST },
						{ strResType, strEdResource, strNumTypeName,
								strUpdNumValue, "", strLastUpdNST },
						{ strResType, strEdResource, strSatuTypeName,
								strUpdSatuValue, "", strLastUpdSST },
						{ strResType, strEdResource, strTxtTypeName,
								strUpdTxtValue, "", strLastUpdTST } };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlPath1Renamed);
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
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strArTime = strRepGenTime.split(":");
				strGenTimeHrs = strArTime[0];
				strGenTimeMin = strArTime[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrs, strGenTimeMin);
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
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			do {

				try {
					assertTrue(new File(strCSVDownlPath2).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed2);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath2Renamed)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strRepGenTime
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType, strEdResource, strMulTypeName,
								strUpdMulValue, "", strLastUpdMST },
						{ strResType, strEdResource, strNumTypeName,
								strUpdNumValue, "", strLastUpdNST }};
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlPath2Renamed);
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "4051";
			gstrTO = "Edit resource name and generate status snapshot report and verify that report displays updated data.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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
	'Description  :Update the status of a resource at time T1 for the first 
	               time, generate report selecting a time earlier than T1 and
	               verify that the report does not display the resource at all.
	'Arguments	  :None
	'Returns	  :None
	'Date		  :19-11-2012
	'Author		  :QSG
	'-------------------------------------------------------------------------
	'Modified Date				                             Modified By
	'Date					                                 Name
	***************************************************************************/

	@Test
	public void testFTS4018() throws Exception {
		
		String strFuncResult = "";
		boolean blnLogin = false;
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Login objLogin = new Login();// object of class Login
		Roles objRole = new Roles();
		Reports objRep = new Reports();
		Views objViews = new Views();
		General objGen = new General();
		try {
			gstrTCID = "4018"; // Test Case Id
			gstrTO = " Update the status of a resource at time T1 for the first time," +
					" generate report selecting a time earlier than T1 and verify that the report does not display the resource at all.";// Test																																																			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strStatusName1 = "Sta" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strUpdatNumValue1 = "2";
			String strComments1 = "aa";
			String strUpdatTxtValue1 = "s";
			String strComments2 = "bb";
			String strComments3 = "cc";
			String strComments4 = "dd";
			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7","8" };

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep_"
					+ gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "StatSnapRep_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRegGenTime = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";

			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";

			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";

		/*
		 * STEP : Action:Create all four status types number, multi, text,
		 * saturation NST, MST, TXT, SST respectively in region RG1.
		 * Expected Result:No Expected Result
		 */
			
			strFuncResult = objLogin.login(selenium, strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Multi ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMulStatTypeValue, strMulTypeName, strMulStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strMulTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName1, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Number ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNumStatTypeValue, strNumTypeName, strNumStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSatuStatTypeValue, strSatuTypeName,
						strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strSatuTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Text ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strTxtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Text status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create resource type RT selecting NST, MST, TXT and
			 * SST. Expected Result:No Expected Result
			 */
			// 19868
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 1; i < strSTvalue.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium, strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create resource RS selecting RT. Expected Result:No
			 * Expected Result
			 */
			// 19869
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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

			/*
			 * STEP : Action:Create view V1 selecting NST, MST, TXT and SST.
			 * Expected Result:No Expected Result
			 */
			// 19871

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user U1 with update status right on
			 * resource RS and a role to update status types NST, MST, TXT and
			 * SST. Expected Result:No Expected Result
			 */
			// 19875
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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
						strResource, false, true, false, true);

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
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);

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

			/*
			 * STEP : Action:Navigate to view V1 and update all status types
			 * with comments at time T1. Expected Result:No Expected Result
			 */
			// 19876
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusMSTWithComments(selenium,
						strStatusValue[0], strSTvalue[0], strComments1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatNumValue1, strSTvalue[1], strComments2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusSSTWithComments(selenium,
						strScUpdValue1, strSTvalue[2], strComments3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatTxtValue1, strSTvalue[3], strComments4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.saveAndNavToViewScreen(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String[] strArFunRes = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Navigate to Report>>Status Reports>>Status
		 * Snapshot. Expected Result:No Expected Result
		 */
		// 19877
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strEarlTime = "";
			try {
				assertEquals("", strFuncResult);
				strEarlTime = dts.addTimetoExisting(strRegGenTime, -2, "HH:mm");
				String strTime[] = strEarlTime.split(":");
				strGenTimeHrs = strTime[0];
				strGenTimeMin = strTime[1];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrs, strGenTimeMin);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt < 300);

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

				String[][] strReportData = {
						{
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strEarlTime
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },

				};
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Resource " + strResource
						+ " is not displayed in the report. ");
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "4018";
			gstrTO = "Update the status of a resource at time T1 for the first time, generate report selecting a time earlier than T1 and verify that the report does not display the resource at all.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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
	
	/*****************************************************************************
	'Description  :Update the status of a resource at time T1 for the first time,
	               generate report selecting a time equal to T1 and verify that the
	                report displays the status details of the resource correctly.
	'Arguments	  :None
	'Returns	  :None
	'Date		  :23-11-2012
	'Author		  :QSG
	'------------------------------------------------------------------------------
	'Modified Date				                                        Modified By
	'Date					                                             Name
	*******************************************************************************/

	@Test
	public void testFTS4025() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Views objViews = new Views();
		General objGen = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Login objLogin = new Login();// object of class Login
		Roles objRole = new Roles();
		Reports objRep = new Reports();
		try {
			gstrTCID = "4025"; // Test Case Id
			gstrTO = " Update the status of a resource at time T1 for the first time, " +
					"generate report selecting a time equal to T1 and verify that the report " +
					"displays the status details of the resource correctly.";
			gstrReason = "";
			gstrResult = "FAIL";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";

			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";

			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";
			
			String strStatusName1 = "Sta" + strTimeText;
			String strSTvalue[] = new String[4];
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			//RS
			String strResType = "AutoRt_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strAbbrv = "Rs";
			String strRSValue[] = new String[1];
			//Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
            //User
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strUpdatNumValue1 = "2";
			String strComments1 = "aa";
			String strUpdatTxtValue1 = "s";
			String strComments2 = "bb";
			String strComments3 = "cc";
			String strComments4 = "dd";
			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7","8" };
			String strScUpdCheckVal = "393";
			
			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapshot_" + gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapshot_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "StatSnapshot_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRegGenTime = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";

		/*
		 * STEP : Action:Create all four status types number, multi, text,
		 * saturation NST, MST, TXT, SST respectively in region RG1.
		 * Expected Result:No Expected Result
		 */
			strFuncResult = objLogin.login(selenium, strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Multi ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMulStatTypeValue, strMulTypeName, strMulStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strMulTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName1, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Number ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNumStatTypeValue, strNumTypeName, strNumStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSatuStatTypeValue, strSatuTypeName,
						strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strSatuTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Text ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strTxtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Text status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create resource type RT selecting NST, MST, TXT and
			 * SST. Expected Result:No Expected Result
			 */
			// 19868
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 1; i < strSTvalue.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium, strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create resource RS selecting RT. Expected Result:No
			 * Expected Result
			 */
			// 19869
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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

			/*
			 * STEP : Action:Create view V1 selecting NST, MST, TXT and SST.
			 * Expected Result:No Expected Result
			 */
			// 19871

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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
						strResource, false, true, false, true);

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
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);

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

			/*
			 * STEP : Action:Navigate to view V1 and update all status types
			 * with comments at time T1. Expected Result:No Expected Result
			 */
			// 19876
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusMSTWithComments(selenium,
						strStatusValue[0], strSTvalue[0], strComments1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatNumValue1, strSTvalue[1], strComments2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusSSTWithComments(selenium,
						strScUpdValue1, strSTvalue[2], strComments3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatTxtValue1, strSTvalue[3], strComments4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.saveAndNavToViewScreen(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			String strLastDateUpdTime = "";
			String strRegGenTimeBef = "";
			String[] strArFunRes = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strRegGenTimeBef = strArFunRes[2] + ":" + strArFunRes[3];
				strLastDateUpdTime = strArFunRes[0] + " " + strArFunRes[1]
						+ " " + strCurYear + " " + strRegGenTimeBef;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Report>>Status Reports>>Status
			 * Snapshot. Expected Result:No Expected Result
			 */
			// 19877
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Wait for ST to update
				Thread.sleep(60000);
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strArFunRes1 = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes1 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes1[4];
				strGenDate = dts.converDateFormat(strArFunRes1[0]
						+ strArFunRes1[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTime = strArFunRes1[2] + ":" + strArFunRes1[3];
				strGenTimeHrs = strArFunRes1[2];
				strGenTimeMin = strArFunRes1[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrs, strGenTimeMin);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt < 300);

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

				String[][] strReportData = {
						{
								"Status Snapshot - " + strGenDate + " "
										+ strRegGenTime + " "+propEnvDetails.getProperty("TimeZone"), "", "", "",
								"", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType, strResource, strMulTypeName,
								strStatusName1, strComments1,
								strLastDateUpdTime },
						{ strResType, strResource, strNumTypeName,
								strUpdatNumValue1, strComments2,
								strLastDateUpdTime },
						{ strResType, strResource, strSatuTypeName,
								strScUpdCheckVal, strComments3,
								strLastDateUpdTime },
						{ strResType, strResource, strTxtTypeName,
								strUpdatTxtValue1, strComments4,
								strLastDateUpdTime }, };
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
			gstrTCID = "4025";
			gstrTO = " Update the status of a resource at time T1 for the first time, generate report selecting a time equal to T1 and verify that the report displays the status details of the resource correctly.";
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
	
	/*********************************************************************************
	'Description :Update status at time T2 and generate status snapshot report at time
	              T1 and verify that report displays updated status as of time T1.
	'Arguments	 :None
	'Returns	 :None
	'Date		 :05-12-2012
	'Author		 :QSG
	'---------------------------------------------------------------------------------
	'Modified Date				                                        Modified By
	'Date					                                            Name
	**********************************************************************************/

	@Test
	public void testFTS105962() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Views objViews = new Views();
		General objGen = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Login objLogin = new Login();// object of class Login
		Roles objRole = new Roles();
		Reports objRep = new Reports();
		try {
			gstrTCID = "105962"; // Test Case Id
			gstrTO = "Update status at time T2 and generate status snapshot report at time T1"
					+ " and verify that report displays updated status as of time T1.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";

			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";

			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";
			
			String strSTvalue[] = new String[4];
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			//RS and RT
			String strResType = "AutoRt_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";			
			String strRSValue[] = new String[1];
			//Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			//User
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			
			String strUpdatNumValue1 = "2";
			String strComments1 = "aa";
			String strUpdatTxtValue1 = "s";
			String strComments2 = "bb";
			String strComments3 = "cc";
			String strComments4 = "dd";
			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7","8" };

			String strUpdatNumValue2 = "4";
			String strComments5 = "aa1";
			String strUpdatTxtValue2 = "t";
			String strComments6 = "bb1";
			String strComments7 = "cc1";
			String strComments8 = "dd1";
			String strScUpdValue2[] = { "1", "2", "3", "4", "5", "6", "7", "8","9" };
			String strScUpdCheckVal1 = "393";
			String strScUpdCheckVal2 = "429";
			
			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRegGenTime = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
		
			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");

			String strCSVDownlPath1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlPath1enamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText + ".xls";

			String strCSVDownlPath2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep2_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlPath2Renamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep2_"
					+ gstrTCID + "_" + strTimeText + ".xls";

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVFileNameRenamed1 = "StatSnapRep1_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVFileNameRenamed2 = "StatSnapRep2_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

		/*
		 * Precondition:1. Resource RS is created selecting RT which is associated with
		 *               all 4 status types NST, MST, TST and SST.
			2. A region view V1 is created selecting RS and all above mentioned 4 status types.
			3. User U1 is created with 'Update Status' right on resource RS, role to update all 4 status types.
			4. User U1 is created selecting 'Report - Status Snapshot' right.
			5. The status of a resource is updated with different values and comments at time T1 and T2. 
		 */
			strFuncResult = objLogin.login(selenium, strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
										
			// Multi ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMulStatTypeValue, strMulTypeName, strMulStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strMulTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName1, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName2, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Number ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNumStatTypeValue, strNumTypeName, strNumStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSatuStatTypeValue, strSatuTypeName,
						strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strSatuTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Text ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strTxtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Text status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create resource type RT selecting NST, MST, TXT and
			 * SST. Expected Result:No Expected Result
			 */
			// 19868
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 1; i < strSTvalue.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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
						strResource, false, true, false, true);

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
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);

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

				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusMSTWithComments(selenium,
						strStatusValue[0], strSTvalue[0], strComments1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatNumValue1, strSTvalue[1], strComments2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusSSTWithComments(selenium,
						strScUpdValue1, strSTvalue[2], strComments3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatTxtValue1, strSTvalue[3], strComments4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.saveAndNavToViewScreen(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			String strLastDateUpdTimeBef = "";
			String[] strArFunRes = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
				strLastDateUpdTimeBef = strArFunRes[0] + " " + strArFunRes[1]
						+ " " + strCurYear + " " + strRegGenTime;

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(120000);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusMSTWithComments(selenium,
						strStatusValue[1], strSTvalue[0], strComments5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatNumValue2, strSTvalue[1], strComments6);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusSSTWithComments(selenium,
						strScUpdValue2, strSTvalue[2], strComments7);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatTxtValue2, strSTvalue[3], strComments8);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.saveAndNavToViewScreen(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			String strLastDateUpdTime = "";

			String[] strArFunRes1 = new String[5];

			try {
				assertEquals("", strFuncResult);
				strArFunRes1 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes1[4];
				strGenDate = dts.converDateFormat(strArFunRes1[0]
						+ strArFunRes1[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strLastDateUpdTime = strArFunRes1[0] + " " + strArFunRes1[1]
						+ " " + strCurYear + " " + strArFunRes1[2] + ":"
						+ strArFunRes1[3];

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

				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strArFunRes2 = new String[5];
			try {
				assertEquals("", strFuncResult);

				strRegGenTime = dts
						.addTimetoExisting(strRegGenTime, 1, "HH:mm");

				strArFunRes2 = strRegGenTime.split(":");
				strGenTimeHrs = strArFunRes2[0];
				strGenTimeMin = strArFunRes2[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrs, strGenTimeMin);
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
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath1).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed1);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath1enamed)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strRegGenTime
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType, strResource, strMulTypeName,
								strStatusName1, strComments1,
								strLastDateUpdTimeBef },
						{ strResType, strResource, strNumTypeName,
								strUpdatNumValue1, strComments2,
								strLastDateUpdTimeBef },
						{ strResType, strResource, strSatuTypeName,
								strScUpdCheckVal1, strComments3,
								strLastDateUpdTimeBef },
						{ strResType, strResource, strTxtTypeName,
								strUpdatTxtValue1, strComments4,
								strLastDateUpdTimeBef }, };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlPath1enamed);
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

				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strArFunRes3 = new String[5];
			String strGenTimeHrsAft = "";
			String strGenTimeMinAft = "";
			String strRegGenTimeAft = "";
			try {
				assertEquals("", strFuncResult);
				strArFunRes3 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes3[4];
				strGenDate = dts.converDateFormat(strArFunRes3[0]
						+ strArFunRes3[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTimeAft = strArFunRes3[2] + ":" + strArFunRes3[3];
				strGenTimeHrsAft = strArFunRes3[2];
				strGenTimeMinAft = strArFunRes3[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrsAft, strGenTimeMinAft);
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
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			 intCount = 0;
			 intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath2).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed2);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath2Renamed)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strRegGenTimeAft
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType, strResource, strMulTypeName,
								strStatusName2, strComments5,
								strLastDateUpdTime },
						{ strResType, strResource, strNumTypeName,
								strUpdatNumValue2, strComments6,
								strLastDateUpdTime },
						{ strResType, strResource, strSatuTypeName,
								strScUpdCheckVal2, strComments7,
								strLastDateUpdTime },
						{ strResType, strResource, strTxtTypeName,
								strUpdatTxtValue2, strComments8,
								strLastDateUpdTime }, };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlPath2Renamed);
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "105962";
			gstrTO = "Update status at time T2 and generate status snapshot report at time T1 and verify that report displays updated status as of time T1.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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
	/********************************************************************************************
	'Description	:Edit timezone of a region and generate status snapshot report in that region 
	                 and verify that updated timezone abbreviation is displayed in the report.
	'Arguments		:None
	'Returns		:None
	'Date			:20-11-2012
	'Author			:QSG
	'--------------------------------------------------------------------------------------------
	'Modified Date				                                                      Modified By
	'Date					                                                          Name
	*********************************************************************************************/

	@Test
	public void testFTS4054() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Views objViews = new Views();
		General objGen = new General();
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Login objLogin = new Login();// object of class Login
		Roles objRole = new Roles();
		Regions objRegion = new Regions();
		Reports objRep = new Reports();
		try {
			gstrTCID = "4054"; // Test Case Id
			gstrTO = " Edit timezone of a region and generate status snapshot report in that region"
					+ " and verify that updated timezone abbreviation is displayed in the report.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
            //ST
			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";

			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";

			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";
			
			String strStatusName1 = "Sta" + strTimeText;
			String strSTvalue[] = new String[4];
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			//RT
			String strResType = "AutoRt_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;			
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			//Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			//User
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			// Region fields
			String strRegionName ="Region" + strTimeText;
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "FN";
			String strContLN = "LN";
			String strOrg = "qsg";
			String strAddr = "qsgsoft";
			String strContactPhone1 = "3456-678-565";
			String strContactPhone2 = "2342-456-546";
			String strContactFax = "676-575-5675";
			String strContactEMail = "autoemr@qsgsoft.com";
			String strEmailFrequency = "Never";
			String strAlertFrequency = "15";
			String strEdTimeZone = "(GMT-06:00) Central Time (US, Canada)";
			String strTimeZonCheck = "CDT";
            //Time
			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRegGenTime = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
		    //autoit
			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";
			
			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep_"
					+ gstrTCID + "_" + strTimeText + ".xls";

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");	
			
			String strCSVFileNameRenamed = "StatSnapRep_"
					+ gstrTCID + "_" + strTimeText ;

			String strCSVDownPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");
		/*
		* STEP :
		  Action:Preconditions: 
				1. Resource RS is created selecting RT which is associated with all 4 status types NST, MST, TST and SST. 
				2. A region view V1 is created selecting RS and all above mentioned 4 status types. 
				3. User U1 is created with 'Update Status' right on resource RS, role to update all 4 status types. 
				4. User U1 is created selecting 'Report - Status Snapshot' right. 
				5. Edit time zone of region RG1.
		  Expected Result:No Expected Result
		*/
		//20161
			strFuncResult = objLogin.login(selenium, strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.fillAllRegnFields(selenium,
						strRegionName, strTimeZone, strContFN, strContLN,
						strOrg, strAddr, strContactPhone1, strContactPhone2,
						strContactFax, strContactEMail, strEmailFrequency,
						strAlertFrequency);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.savAndVerifyRegion(selenium,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.navEditRegionPge(selenium,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.fillAvailableOptions(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.savAndVerifyRegion(selenium,
						strRegionName);

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
				strFuncResult = objLogin.navUserDefaultRgn(selenium,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Multi ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMulStatTypeValue, strMulTypeName, strMulStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strMulTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName1, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Number ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNumStatTypeValue, strNumTypeName, strNumStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSatuStatTypeValue, strSatuTypeName,
						strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strSatuTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Text ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strTxtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Text status type value";
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 1; i < strSTvalue.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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
						strResource, false, true, false, true);
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
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.navEditRegionPge(selenium,
						strRegionName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.fillAllRegnFields(selenium,
						strRegionName, strEdTimeZone, strContFN, strContLN,
						strOrg, strAddr, strContactPhone1, strContactPhone2,
						strContactFax, strContactEMail, strEmailFrequency,
						strAlertFrequency);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegion.savAndVerifyRegion(selenium,
						strRegionName);

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
			 * STEP : Action:Login to RG1 and navigate to Report>>Status
			 * Reports>>Status Snapshot. Expected Result:No Expected Result
			 */
			// 20162
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

				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes = new String[5];
			try {
				assertEquals("", strFuncResult);
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

			/*
			 * STEP : Action:Enter mandatory data, select the radio button CSV
			 * and click on 'Generate Report'. Expected Result:CSV report is
			 * generated with the updated timezone abbreviation
			 */
			// 20165
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrs, strGenTimeMin);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

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
									+ strCSVDownPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPathRenamed)
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
										+ strRegGenTime + " " + strTimeZonCheck,
								"", "", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },

				};
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlPathRenamed);
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "4054";
			gstrTO = "Edit timezone of a region and generate status snapshot report in that region and verify that updated timezone abbreviation is displayed in the report.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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
	/***************************************************************
	'Description	:Edit status type name and verify that updated 
	                 data is displayed in status snapshot report.
	'Arguments		:None
	'Returns		:None
	'Date			:20-11-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				                       Modified By
	'Date					                           Name
	***************************************************************/

	@Test
	public void testFTS4052() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Views objViews = new Views();
		General objGen = new General();
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Login objLogin = new Login();// object of class Login
		Roles objRole = new Roles();
		Reports objRep = new Reports();

		try {
			gstrTCID = "4052"; // Test Case Id
			gstrTO = " Edit status type name and verify that updated data is displayed"
					+ " in status snapshot report.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";

			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";

			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";

			String strEdMulTypeName = "AEdMSt_" + strTimeText;
			String strEdNumTypeName = "AEdNSt_" + strTimeText;
			String strEdTxtTypeName = "AEdTSt_" + strTimeText;
			String strEdSatuTypeName = "AEdSSt_" + strTimeText;
			
			String strStatusName1 = "Sta" + strTimeText;
			String strSTvalue[] = new String[4];
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			
			String strResType = "AutoRt_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;		
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
            //Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			//User
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
            //update
			String strUpdatNumValue1 = "2";
			String strComments1 = "aa";
			String strUpdatTxtValue1 = "s";
			String strComments2 = "bb";
			String strComments3 = "cc";
			String strComments4 = "dd";
			String strScUpdCheckVal = "393";
			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7","8" };
			
			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRegGenTimeBef = "";
			String strRegGenTime = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strRegGenTimeAft = "";
			String strGenTimeHrsAft = "";
			String strGenTimeMinAft = "";
			
			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			
			String strCSVDownlPath1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";
			
			String strCSVDownlPath1Renamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText + ".xls";
			
			String strCSVDownlPath2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep2_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";
			
			String strCSVDownlPath2Renamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep2_"
					+ gstrTCID + "_" + strTimeText + ".xls";
			
			String strCSVFileNameRenamed1 = "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText;
			
			String strCSVFileNameRenamed2 = "StatSnapRep2_"
					+ gstrTCID + "_" + strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");
			
			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");			
		
		/*
		* STEP :
		  Action:Preconditions: 
			1. Resource RS is created selecting RT which is associated with all 4 status 
			   types NST, MST, TST and SST. 
			2. A region view V1 is created selecting RS and all above mentioned 4 status types. 
			3. User U1 is created with 'Update Status' right on resource RS, role to update all 4 status types. 
			4. User U1 is created selecting 'Report - Status Snapshot' right.
		  Expected Result:No Expected Result
		*/
		
			strFuncResult = objLogin.login(selenium, strAdmUserName,
					strAdmPassword);
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Multi ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMulStatTypeValue, strMulTypeName, strMulStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strMulTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName1, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Number ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNumStatTypeValue, strNumTypeName, strNumStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSatuStatTypeValue, strSatuTypeName,
						strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strSatuTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Text ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strTxtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Text status type value";
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 1; i < strSTvalue.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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
						strResource, false, true, false, true);

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
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);

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
		/*
		* STEP :
		  Action:Navigate to view V1 and update all status types with comments at time T1.
		  Expected Result:No Expected Result
		*/
		//19876
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusMSTWithComments(selenium,
						strStatusValue[0], strSTvalue[0], strComments1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatNumValue1, strSTvalue[1], strComments2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusSSTWithComments(selenium,
						strScUpdValue1, strSTvalue[2], strComments3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatTxtValue1, strSTvalue[3], strComments4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.saveAndNavToViewScreen(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strLastDateUpdTime = "";
			String[] strArFunRes = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strRegGenTimeBef = strArFunRes[2] + ":" + strArFunRes[3];
				strLastDateUpdTime = strArFunRes[0] + " " + strArFunRes[1]
						+ " " + strCurYear + " " + strRegGenTimeBef;
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
		/*
		* STEP :
		  Action:Edit status type names of all four status types at time T1.
		  Expected Result:No Expected Result
		*/
		//175853
			try {
				assertEquals("", strFuncResult);
				// wait for 2 mins to update ST
				Thread.sleep(120000);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.editStatusTypesMandFlds(selenium,
						strMulTypeName, strEdMulTypeName, "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.editStatusTypesMandFlds(selenium,
						strNumTypeName, strEdNumTypeName, "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.editStatusTypesMandFlds(selenium,
						strSatuTypeName, strEdSatuTypeName, "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.editStatusTypesMandFlds(selenium,
						strTxtTypeName, strEdTxtTypeName, "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes1 = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes1 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes1[4];
				strGenDate = dts.converDateFormat(strArFunRes1[0]
						+ strArFunRes1[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTime = strArFunRes1[2] + ":" + strArFunRes1[3];

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
		* STEP :
		  Action:Login as user U1, navigate to Report>>Status Reports>>Status Snapshot.
		  Expected Result:No Expected Result
		*/
		//20153

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
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

				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		/*
		* STEP :
		  Action:Select time which is earlier than T1, radio button CSV and click on 'Generate Report'.
		  Expected Result:CSV report is generated and the status type names are updated in the report and are arranged in alphabetical order.
		*/
		//20154
		
			String strEarlTime = "";
			try {
				assertEquals("", strFuncResult);
				strEarlTime = dts.addTimetoExisting(strRegGenTime, -1, "HH:mm");
				String strTime[] = strEarlTime.split(":");
				strGenTimeHrs = strTime[0];
				strGenTimeMin = strTime[1];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrs, strGenTimeMin);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath1).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed1);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath1Renamed)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strEarlTime
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType, strResource, strEdMulTypeName,
								strStatusName1, strComments1,
								strLastDateUpdTime },
						{ strResType, strResource, strEdNumTypeName,
								strUpdatNumValue1, strComments2,
								strLastDateUpdTime },
						{ strResType, strResource, strEdSatuTypeName,
								strScUpdCheckVal, strComments3,
								strLastDateUpdTime },
						{ strResType, strResource, strEdTxtTypeName,
								strUpdatTxtValue1, strComments4,
								strLastDateUpdTime }, };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlPath1Renamed);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Now select time T1 or later, radio button CSV and click on 'Generate Report'.
		  Expected Result:CSV report is generated and the status type names are updated in the report and are arranged in alphabetical order.
		*/
		//20155
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes2 = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes2 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes2[4];
				strGenDate = dts.converDateFormat(strArFunRes2[0]
						+ strArFunRes2[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTimeAft = strArFunRes2[2] + ":" + strArFunRes2[3];
				strGenTimeHrsAft = strArFunRes2[2];
				strGenTimeMinAft = strArFunRes2[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrsAft, strGenTimeMinAft);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			 intCount = 0;
			 intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath2).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed2);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath2Renamed)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strRegGenTimeAft
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType, strResource, strEdMulTypeName,
								strStatusName1, strComments1,
								strLastDateUpdTime },
						{ strResType, strResource, strEdNumTypeName,
								strUpdatNumValue1, strComments2,
								strLastDateUpdTime },
						{ strResType, strResource, strEdSatuTypeName,
								strScUpdCheckVal, strComments3,
								strLastDateUpdTime },
						{ strResType, strResource, strEdTxtTypeName,
								strUpdatTxtValue1, strComments4,
								strLastDateUpdTime }, };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlPath2Renamed);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "4052";
			gstrTO = "Edit status type name and verify that updated data is displayed in status snapshot report.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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
	'Description	:Deselect a status type from resource type and verify that status
	                  type is not displayed in generated status snapshot report.
	'Arguments		:None
	'Returns		:None
	'Date			:21-11-2012
	'Author			:QSG
	'--------------------------------------------------------------------------------
	'Modified Date				                                      Modified By
	'Date					                                          Name
	**********************************************************************************/

	@Test
	public void testFTS4058() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Views objViews = new Views();
		General objGen = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Login objLogin = new Login();// object of class Login
		Roles objRole = new Roles();
		Reports objRep = new Reports();
		try {
			gstrTCID = "4058"; // Test Case Id
			gstrTO = " Deselect a status type from resource type and verify "
					+ "that status type is not displayed in generated status"
					+ " snapshot report.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatTypeValue = "Number";
			String strNumTypeName1 = "AutoNSt1_" + strTimeText;
			String strNumStatTypDefn = "Auto";
			String strNumTypeName2 = "AutoNSt2_" + strTimeText;
			String strSTvalue[] = new String[2];
			
			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
            //Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
            //User
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strUpdatNumValue1 = "2";
			String strUpdatNumValue2 = "3";
			String strComments1 = "aa";
			String strComments2 = "bb";
			
			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRegGenTimeBef = "";
			String strRegGenTime = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strRegGenTimeAft = "";
			String strGenTimeHrsAft = "";
			String strGenTimeMinAft = "";

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");

			String strCSVDownlPath1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed1 = "StatSnapRep1_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPath2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep2_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep2_"
					+ gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed2 = "StatSnapRep2_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
					
		/*
		 * STEP : Action:Precondition: RT has two status types ST1 and ST2.
		 * Both the status types are updated for resource RS at time T1.
		 * Expected Result:No Expected Result
		 */
		// 20200

			strFuncResult = objLogin.login(selenium, strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Number ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNumStatTypeValue, strNumTypeName1,
						strNumStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strNumTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNumStatTypeValue, strNumTypeName2,
						strNumStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strNumTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetchNumber status type value";
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 1; i < strSTvalue.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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
						strResource, false, true, false, true);
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
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);

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
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatNumValue1, strSTvalue[0], strComments1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatNumValue2, strSTvalue[1], strComments2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToViewScreen(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strLastDateUpdTime = "";
			String[] strArFunRes = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strRegGenTimeBef = strArFunRes[2] + ":" + strArFunRes[3];
				strLastDateUpdTime = strArFunRes[0] + " " + strArFunRes[1]
						+ " " + strCurYear + " " + strRegGenTimeBef;

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(60000);
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

			/*
			 * STEP : Action:Deselect ST1 from RT at time T2. Expected Result:No
			 * Expected Result
			 */
			// 20201
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes1 = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes1 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes1[4];
				strGenDate = dts.converDateFormat(strArFunRes1[0]
						+ strArFunRes1[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strRegGenTime = strArFunRes1[2] + ":" + strArFunRes1[3];

				strGenTimeHrs = strArFunRes1[2];
				strGenTimeMin = strArFunRes1[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.navToeditResrcTypepage(selenium,
						strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
						strSTvalue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndNavToResTypeList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Report>>Status Reports>>Status
			 * Snapshot. Expected Result:No Expected Result
			 */
			// 20202

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(60000);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Login as user U1, navigate to Report>>Status
		 * Reports>>Status Snapshot. Expected Result:No Expected Result
		 */
		// 20153

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
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
			String[] strArFunRes2 = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes2 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes2[4];
				strGenDate = dts.converDateFormat(strArFunRes2[0]
						+ strArFunRes2[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTimeAft = strArFunRes2[2] + ":" + strArFunRes2[3];
				strGenTimeHrsAft = strArFunRes2[2];
				strGenTimeMinAft = strArFunRes2[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select time T1, radio button CSV and click on
			 * 'Generate Report' Expected Result:CSV report is generated and has
			 * both status types ST1 and ST2.
			 */
			// 20203

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrs, strGenTimeMin);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt < 300);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1=0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath1).exists());
					Thread.sleep(10000);
					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed1);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath1)
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
						{ strResType, strResource, strNumTypeName1,
								strUpdatNumValue1, strComments1,
								strLastDateUpdTime },
						{ strResType, strResource, strNumTypeName2,
								strUpdatNumValue2, strComments2,
								strLastDateUpdTime } };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlRenamedPath1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select time T2, radio button CSV and click on
			 * 'Generate Report' Expected Result:CSV report is generated. Only
			 * ST2 is displayed in the report.
			 */
			// 20204

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrsAft, strGenTimeMinAft);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt < 300);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			intCount = 0;
			intCount1=0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath2).exists());
					Thread.sleep(10000);
					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed2);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath2)
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
										+ strRegGenTimeAft + " "+propEnvDetails.getProperty("TimeZone"), "", "",
								"", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },

						{ strResType, strResource, strNumTypeName2,
								strUpdatNumValue2, strComments2,
								strLastDateUpdTime }, };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlRenamedPath2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "4058";
			gstrTO = "Deselect a status type from resource type and verify "
					+ "that status type is not displayed in generated status"
					+ " snapshot report.";
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
	'Description	:Deactivate resource type and verify that it is no longer 
	                 displayed in the generated status snapshot report.
	'Arguments		:None
	'Returns		:None
	'Date			:21-11-2012
	'Author			:QSG
	'------------------------------------------------------------------------
	'Modified Date				                                 Modified By
	'Date					                                     Name
	*************************************************************************/

	@Test
	public void testFTS4055() throws Exception {
		
		boolean blnLogin = false;
		Views objViews=new Views();
		General objGen=new General();
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();	
		StatusTypes objST=new StatusTypes();
		ResourceTypes objRT=new ResourceTypes();
		Resources objRs=new Resources();
		CreateUsers objCreateUsers=new CreateUsers();
		Login objLogin = new Login();// object of class Login
		Roles objRole=new Roles();
		Reports objRep=new Reports();
		String strFuncResult = "";
	try{	
		gstrTCID = "4055";	//Test Case Id	
		gstrTO = " Deactivate resource type and verify that it is no longer displayed " +
				"in the generated status snapshot report.";//TO
		gstrReason = "";
		gstrResult = "FAIL";	

		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake=dts.timeNow("HH:mm:ss");		
		propAutoItDetails=objAP.ReadAutoit_FilePath();					
		String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
		
		// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";

			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";

			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";
			String strSTvalue[] = new String[4];
			
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			//RT
			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
		    //User
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strUpdatNumValue1 = "2";
			String strComments1 = "aa";
			String strUpdatTxtValue1 = "s";
			String strComments2 = "bb";
			String strComments3 = "cc";
			String strComments4 = "dd";
			String strScUpdCheckVal = "393";
			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7","8" };

			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRegGenTimeBef = "";
			String strRegGenTime = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strRegGenTimeAft = "";
			String strGenTimeHrsAft = "";
			String strGenTimeMinAft = "";
			
			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");

			String strCSVDownlPath1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlPath1Renamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText + ".xls";

			String strCSVDownlPath2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep2_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlPath2Renamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep2_"
					+ gstrTCID + "_" + strTimeText + ".xls";
			
			String strCSVFileNameRenamed1 = "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText;
			
			String strCSVFileNameRenamed2 = "StatSnapRep2_"
					+ gstrTCID + "_" + strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
		/*
		* STEP :
		  Action:Preconditions:1.Resource RS is created selecting RT which is associated 
		                         with all 4 status types NST, MST, TST and SST. 
			2. A region view V1 is created selecting RS and all above mentioned 4 status types. 
			3. User U1 is created with 'Update Status' right on resource RS, role to update all 4 status types. 
			4. User U1 is created selecting 'Report - Status Snapshot' right.
		  Expected Result:No Expected Result
		*/
		//20177
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

			// Multi ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMulStatTypeValue, strMulTypeName, strMulStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strMulTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName1, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Number ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNumStatTypeValue, strNumTypeName, strNumStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSatuStatTypeValue, strSatuTypeName,
						strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strSatuTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Text ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strTxtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Text status type value";
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 1; i < strSTvalue.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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
						strResource, false, true, false, true);

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
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);

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
		/*
		* STEP :
		  Action:Navigate to view V1 and update all status types with comments at time T1.
		  Expected Result:No Expected Result
		*/
		//19876
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusMSTWithComments(selenium,
						strStatusValue[0], strSTvalue[0], strComments1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatNumValue1, strSTvalue[1], strComments2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusSSTWithComments(selenium,
						strScUpdValue1, strSTvalue[2], strComments3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatTxtValue1, strSTvalue[3], strComments4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.saveAndNavToViewScreen(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strLastDateUpdTime = "";
			String[] strArFunRes = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strRegGenTimeBef = strArFunRes[2] + ":" + strArFunRes[3];
				strLastDateUpdTime = strArFunRes[0] + " " + strArFunRes[1]
						+ " " + strCurYear + " " + strRegGenTimeBef;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				// wait for 2 mins to update ST
				Thread.sleep(120000);
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
		/*
		* STEP :
		  Action:Deactivate resource type RT at time T1.
		  Expected Result:No Expected Result
		*/
		//175862
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.navToeditResrcTypepage(selenium,
						strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.activateAndDeactivateRT(selenium,
						strResType, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strArFunRes1 = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes1 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes1[4];
				strGenDate = dts.converDateFormat(strArFunRes1[0]
						+ strArFunRes1[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTime = strArFunRes1[2] + ":" + strArFunRes1[3];

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
		* STEP :
		  Action:Login as user U1, navigate to Report>>Status Reports>>Status Snapshot.
		  Expected Result:No Expected Result
		*/
		//20178

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
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

				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Select time which is earlier than T1, radio button CSV and click on 'Generate Report'.
		  Expected Result:CSV report is generated and the resource type and associated resource is displayed.
		*/
		//20179
		
			String strEarlTime = "";
			try {
				assertEquals("", strFuncResult);
				strEarlTime = dts.addTimetoExisting(strRegGenTime, -1, "HH:mm");
				String strTime[] = strEarlTime.split(":");
				strGenTimeHrs = strTime[0];
				strGenTimeMin = strTime[1];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrs, strGenTimeMin);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath1).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed1);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath1Renamed)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strEarlTime
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType, strResource, strMulTypeName,
								strStatusName1, strComments1,
								strLastDateUpdTime },
						{ strResType, strResource, strNumTypeName,
								strUpdatNumValue1, strComments2,
								strLastDateUpdTime },
						{ strResType, strResource, strSatuTypeName,
								strScUpdCheckVal, strComments3,
								strLastDateUpdTime },
						{ strResType, strResource, strTxtTypeName,
								strUpdatTxtValue1, strComments4,
								strLastDateUpdTime }, };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlPath1Renamed);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Now select time T1 or later, radio button CSV and click on 'Generate Report'.
		  Expected Result:CSV report is generated and resource type and its associated resource are no longer displayed.
		*/
		//20180
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes2 = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes2 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes2[4];
				strGenDate = dts.converDateFormat(strArFunRes2[0]
						+ strArFunRes2[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTimeAft = strArFunRes2[2] + ":" + strArFunRes2[3];
				strGenTimeHrsAft = strArFunRes2[2];
				strGenTimeMinAft = strArFunRes2[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrsAft, strGenTimeMinAft);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			 intCount = 0;
			 intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath2).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed2);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath2Renamed)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strRegGenTimeAft
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },

				};
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlPath2Renamed);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "4055";
			gstrTO = "Deactivate resource type and verify that it is no longer displayed in the generated status snapshot report.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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

	/***************************************************************
	'Description  :Reactivate resource type and verify that it is 
	               displayed in the generated status snapshot report.
	'Arguments	  :None
	'Returns	  :None
	'Date		  :06-12-2012
	'Author		  :QSG
	'---------------------------------------------------------------
	'Modified Date				                         Modified By
	'Date					                             Name
	***************************************************************/

	@Test
	public void testFTS105961() throws Exception {

		Login objLogin = new Login();
		String strFuncResult = "";
		boolean blnLogin = false;
		Views objViews = new Views();
		General objGen = new General();
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		Reports objRep = new Reports();
		try {
			gstrTCID = "105961"; // Test Case Id
			gstrTO = " Reactivate resource type and verify that it is displayed in the "
					+ "generated status snapshot report.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";
			
			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";
			
			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";
			
			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";
			
			String strSTvalue[] = new String[4];
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			//RS
			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			//user
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			
			String strCSVDownlPath1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";
			
			String strCSVDownlPath1Renamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText + ".xls";
			
			String strCSVDownlPath2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep2_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";
			
			String strCSVDownlPath2Renamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep2_"
					+ gstrTCID + "_" + strTimeText + ".xls";
			
			String strCSVDownlPath3 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep3_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";
			
			String strCSVDownlPath3Renamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep3_"
					+ gstrTCID + "_" + strTimeText + ".xls";
			
			String strCSVDownlPath4 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep4_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";
			
			String strCSVDownlPath4Renamed = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep4_"
					+ gstrTCID + "_" + strTimeText + ".xls";
			
			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");
			
			String strCSVFileNameRenamed1 ="StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText;
		
			String strCSVFileNameRenamed2="StatSnapRep2_"
					+ gstrTCID + "_" + strTimeText;
			
			String strCSVFileNameRenamed3 ="StatSnapRep3_"
					+ gstrTCID + "_" + strTimeText;
			
			
			String strCSVFileNameRenamed4 ="StatSnapRep4_"
					+ gstrTCID + "_" + strTimeText;
			
			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			
			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRegGenTimeBef = "";
			String strRegGenTime = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strRegGenTimeAft = "";
			String strGenTimeHrsAft = "";
			String strGenTimeMinAft = "";
			
			String strUpdatNumValue1 = "2";
			String strComments1 = "aa";
			String strUpdatTxtValue1 = "s";
			String strComments2 = "bb";
			String strComments3 = "cc";
			String strComments4 = "dd";
			String strScUpdCheckVal = "393";
			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7","8" };
		
		/*
		* STEP :
		  Action:Preconditions: 
			1. Resource RS is created selecting RT which is associated with all 4 status types NST, MST, TST and SST. 
			2. A region view V1 is created selecting RS and all above mentioned 4 status types. 
			3. User U1 is created with 'Update Status' right on resource RS, role to update all 4 status types. 
			4. User U1 is created selecting 'Report - Status Snapshot' right.
		  Expected Result:No Expected Result
		*/

			strFuncResult = objLogin.login(seleniumPrecondition, strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Multi ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strMulStatTypeValue, strMulTypeName, strMulStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMulTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						strMulTypeName, strStatusName1, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						strMulTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Number ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNumStatTypeValue, strNumTypeName, strNumStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strSatuStatTypeValue, strSatuTypeName,
						strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strSatuTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Text ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTxtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Text status type value";
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

			for (int i = 1; i < strSTvalue.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
							strSTvalue[i], true);
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
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
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
						strUserName, strUsrPassword, strUsrPassword,
						strUsrFulName);

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
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUser(seleniumPrecondition,
						strUserName);

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

				strFuncResult = objRT.navToeditResrcTypepage(seleniumPrecondition,
						strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.activateAndDeactivateRT(seleniumPrecondition,
						strResType, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strArFunRes1 = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes1 = objGen.getSnapTime(seleniumPrecondition);
				strFuncResult = strArFunRes1[4];
				strGenDate = dts.converDateFormat(strArFunRes1[0]
						+ strArFunRes1[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTime = strArFunRes1[2] + ":" + strArFunRes1[3];
				strGenTimeHrs = strArFunRes1[2];
				strGenTimeMin = strArFunRes1[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Wait for time T3
				Thread.sleep(120000);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.selectDesectIncludeInactiveRT(seleniumPrecondition,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navToeditResrcTypepage(seleniumPrecondition,
						strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.activateAndDeactivateRT(seleniumPrecondition,
						strResType, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selectDesectIncludeInactiveRT(seleniumPrecondition,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes2 = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes2 = objGen.getSnapTime(seleniumPrecondition);
				strFuncResult = strArFunRes2[4];
				strGenDate = dts.converDateFormat(strArFunRes2[0]
						+ strArFunRes2[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTimeAft = strArFunRes2[2] + ":" + strArFunRes2[3];
				strGenTimeHrsAft = strArFunRes2[2];
				strGenTimeMinAft = strArFunRes2[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		/*
		* STEP :
		  Action:Login as user U1, navigate to Report>>Status Reports>>Status Snapshot.
		  Expected Result:No Expected Result
		*/
		//20178

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
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrs, strGenTimeMin);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath1).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed1);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath1Renamed)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strRegGenTime
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" }, };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlPath1Renamed);
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
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrsAft, strGenTimeMinAft);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			 intCount = 0;
			 intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath2).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed2);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath2Renamed)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strRegGenTimeAft
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" }, };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlPath2Renamed);
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
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strEarlTime = "";
			String strGenTimeHrsBet = "";
			String strGenTimeMinBet = "";
			try {
				assertEquals("", strFuncResult);
				strEarlTime = dts.addTimetoExisting(strRegGenTimeAft, -1,
						"HH:mm");
				String strTime[] = strEarlTime.split(":");
				strGenTimeHrsBet = strTime[0];
				strGenTimeMinBet = strTime[1];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrsBet, strGenTimeMinBet);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			 intCount = 0;
			 intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath3).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed3);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath3Renamed)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strEarlTime
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" }, };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlPath3Renamed);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdStatusMSTWithComments(selenium,
						strStatusValue[0], strSTvalue[0], strComments1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatNumValue1, strSTvalue[1], strComments2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdStatusSSTWithComments(selenium,
						strScUpdValue1, strSTvalue[2], strComments3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatTxtValue1, strSTvalue[3], strComments4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToViewScreen(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strLastDateUpdTime = "";
			String[] strArFunRes = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strRegGenTimeBef = strArFunRes[2] + ":" + strArFunRes[3];
				strLastDateUpdTime = strArFunRes[0] + " " + strArFunRes[1]
						+ " " + strCurYear + " " + strRegGenTimeBef;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Now select time T1 or later, radio button CSV and click on 'Generate Report'.
		  Expected Result:CSV report is generated and resource type and its associated resource are no longer displayed.
		*/
		//20180
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(60000);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes3 = new String[5];
			String strGenTimeHrsUpd = "";
			String strGenTimeMinUpd = "";
			try {
				assertEquals("", strFuncResult);
				strArFunRes3 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes3[4];
				strGenDate = dts.converDateFormat(strArFunRes3[0]
						+ strArFunRes3[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTimeBef = strArFunRes3[2] + ":" + strArFunRes3[3];
				strGenTimeHrsUpd = strArFunRes3[2];
				strGenTimeMinUpd = strArFunRes3[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrsUpd, strGenTimeMinUpd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath4 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			 intCount = 0;
			 intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath4).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed4);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlPath4Renamed)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strRegGenTimeBef
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType, strResource, strMulTypeName,
								strStatusName1, strComments1,
								strLastDateUpdTime },
						{ strResType, strResource, strNumTypeName,
								strUpdatNumValue1, strComments2,
								strLastDateUpdTime },
						{ strResType, strResource, strSatuTypeName,
								strScUpdCheckVal, strComments3,
								strLastDateUpdTime },
						{ strResType, strResource, strTxtTypeName,
								strUpdatTxtValue1, strComments4,
								strLastDateUpdTime }, };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlPath4Renamed);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "105961";
			gstrTO = " Reactivate resource type and verify that it is displayed in the generated status snapshot report.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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
	
	/***********************************************************************************
	'Description	:Change the resource type of a resource and verify that the status 
	                 snapshot report displays correct data for the present and past time.
	'Arguments		:None
	'Returns		:None
	'Date			:21-11-2012
	'Author			:QSG
	'-----------------------------------------------------------------------------------
	'Modified Date				                                            Modified By
	'Date					                                                Name
	************************************************************************************/

	@Test
	public void testFTS4060() throws Exception {
		
		String strFuncResult = "";
		boolean blnLogin = false;
		Views objViews = new Views();
		General objGen = new General();
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Login objLogin = new Login();// object of class Login
		Roles objRole = new Roles();
		Reports objRep = new Reports();
		
		try {
			gstrTCID = "4060"; // Test Case Id
			gstrTO = " Change the resource type of a resource and verify that"
					+ " the status snapshot report displays correct data for the"
					+ " present and past time.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";

			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";

			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";
			
			String strSTvalue[] = new String[4];
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			//RS and RT
			String strResType = "AutoRt_" + strTimeText;
			String strResType2 = "AutoRt2_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
            //Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
            //User
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			
			String strUpdatNumValue1 = "2";
			String strComments1 = "aa";
			String strUpdatTxtValue1 = "s";
			String strComments2 = "bb";
			String strComments3 = "cc";
			String strComments4 = "dd";
			String strScUpdCheckVal = "393";
			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7","8" };
			
			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRegGenTimeBef = "";
			String strRegGenTime = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strRegGenTimeAft = "";
			String strGenTimeHrsAft = "";
			String strGenTimeMinAft = "";
				
			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");

			String strCSVDownlPath1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_" + gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed1 = "StatSnapRep1_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPath2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep2_" + gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep2_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed2 = "StatSnapRep2_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");
			
			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");			
		/*
		 * STEP : Action:Preconditions: <br> <br>1. Resource RS is created
		 * selecting RT which is associated with all 4 status types NST,
		 * MST, TST and SST. <br>2. A region view V1 is created selecting RS
		 * and all above mentioned 4 status types. <br>3. User U1 is created
		 * with 'Update Status' right on resource RS, role to update all 4
		 * status types. <br>4. User U1 is created selecting 'Report -
		 * Status Snapshot' right. Expected Result:No Expected Result
		 */
			strFuncResult = objLogin.login(selenium, strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Multi ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMulStatTypeValue, strMulTypeName, strMulStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strMulTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMulTypeName, strStatusName1, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
						strMulTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Number ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNumStatTypeValue, strNumTypeName, strNumStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSatuStatTypeValue, strSatuTypeName,
						strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strSatuTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Text ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, strTxtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Text status type value";
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 1; i < strSTvalue.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium, strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResType2, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 1; i < strSTvalue.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[i], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium, strResType2);
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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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
						strResource, false, true, false, true);

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
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);

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

				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusMSTWithComments(selenium,
						strStatusValue[0], strSTvalue[0], strComments1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatNumValue1, strSTvalue[1], strComments2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusSSTWithComments(selenium,
						strScUpdValue1, strSTvalue[2], strComments3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdatTxtValue1, strSTvalue[3], strComments4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.saveAndNavToViewScreen(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strLastDateUpdTime = "";
			String[] strArFunRes = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strRegGenTimeBef = strArFunRes[2] + ":" + strArFunRes[3];
				strLastDateUpdTime = strArFunRes[0] + " " + strArFunRes[1]
						+ " " + strCurYear + " " + strRegGenTimeBef;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				// wait for 2 mins to update ST
				Thread.sleep(120000);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Login as RegAdmin. Expected Result:No Expected
		 * Result
		 */

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

		/*
		 * STEP : Action:Edit resource RS and change its resource type from
		 * RT1 to RT2 at time T1. Expected Result:No Expected Result
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes1 = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes1 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes1[4];
				strGenDate = dts.converDateFormat(strArFunRes1[0]
						+ strArFunRes1[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTime = strArFunRes1[2] + ":" + strArFunRes1[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selctRT(selenium, strResType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Login as user U1, navigate to Report>>Status
		 * Reports>>Status Snapshot. Expected Result:No Expected Result
		 */
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(60000);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Login as user U1, navigate to Report>>Status
		 * Reports>>Status Snapshot. Expected Result:No Expected Result
		 */
		// 20178

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
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
			String[] strArFunRes2 = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes2 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes2[4];
				strGenDate = dts.converDateFormat(strArFunRes2[0]
						+ strArFunRes2[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTimeAft = strArFunRes2[2] + ":" + strArFunRes2[3];
				strGenTimeHrsAft = strArFunRes2[2];
				strGenTimeMinAft = strArFunRes2[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Select time earlier than T1, radio button CSV and
		 * click on 'Generate Report'. Expected Result:CSV report is
		 * generated. RT1 is displayed for RS.
		 */
		// 20214

			String strEarlTime = "";
			try {
				assertEquals("", strFuncResult);
				strEarlTime = dts.addTimetoExisting(strRegGenTime, -1, "HH:mm");
				String strTime[] = strEarlTime.split(":");
				strGenTimeHrs = strTime[0];
				strGenTimeMin = strTime[1];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrs, strGenTimeMin);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt < 300);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath1).exists());
					Thread.sleep(10000);
					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed1);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath1)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strEarlTime
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType2, strResource, strMulTypeName,
								strStatusName1, strComments1,
								strLastDateUpdTime },
						{ strResType2, strResource, strNumTypeName,
								strUpdatNumValue1, strComments2,
								strLastDateUpdTime },
						{ strResType2, strResource, strSatuTypeName,
								strScUpdCheckVal, strComments3,
								strLastDateUpdTime },
						{ strResType2, strResource, strTxtTypeName,
								strUpdatTxtValue1, strComments4,
								strLastDateUpdTime }, };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlRenamedPath1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Select time T2, radio button CSV and click on
		 * 'Generate Report'. Expected Result:CSV report is generated. RT2
		 * is displayed for RS.
		 */
		// 20215
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrsAft, strGenTimeMinAft);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt < 300);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			intCount = 0;
			intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath2).exists());
					Thread.sleep(10000);
					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed2);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath2)
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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strRegGenTimeAft
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType2, strResource, strMulTypeName,
								strStatusName1, strComments1,
								strLastDateUpdTime },
						{ strResType2, strResource, strNumTypeName,
								strUpdatNumValue1, strComments2,
								strLastDateUpdTime },
						{ strResType2, strResource, strSatuTypeName,
								strScUpdCheckVal, strComments3,
								strLastDateUpdTime },
						{ strResType2, strResource, strTxtTypeName,
								strUpdatTxtValue1, strComments4,
								strLastDateUpdTime }, };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlRenamedPath2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "4060";
			gstrTO = "Change the resource type of a resource and verify "
					+ "that the status snapshot report displays correct "
					+ "data for the present and past time.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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
	
	/**********************************************************************
	'Description	:Update a multi status type selecting status reason and 
	                 without comments and verify that reason is displayed in
	                  the column 'Comment' in the status snapshot report.
	'Arguments		:None
	'Returns		:None
	'Date			:22-11-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date				                               Modified By
	'Date					                                   Name
	************************************************************************/

	@Test
	public void testFTS4089() throws Exception {

		boolean blnLogin = false;
		Roles objRole = new Roles();
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		General objGen = new General();
		StatusReason objStatRes = new StatusReason();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Reports objRep = new Reports();
		ViewMap objViewMap = new ViewMap();
		Paths_Properties objAP = new Paths_Properties();

		try {
			gstrTCID = "4089"; // Test Case Id
			gstrTO = " Update a multi status type selecting status reason "
					+ "and without comments and verify that reason is displayed"
					+ " in the column 'Comment' in the status snapshot report.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Properties pathProps = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strStatusTypeValue = "Multi";

			String statTypeName = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strSTvalue[] = new String[1];
			String strStatusValue[] = new String[1];
			
			String strReasonName = "AutoReas_" + System.currentTimeMillis();
			String strReasonVal = "";
			//RT
			String strResType = "AutoRt_" + strTimeText;			
			String strResource = "AutoRs_" + strTimeText;			
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
            //ROl
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
            //user
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			String strRSValue[] = new String[1];
			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRegGenTime = "";
			String strRegGenTimeBef = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			
			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");

			String strCSVDownlPath1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep1_"
					+ gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "StatSnapRep1_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			
		/*
		* STEP :
		  Action:Precondition:
			1.Status reason rsn1 is created with option to display in comment section.
			2.Multi status type MST is created selecting rsn1.
			3.Status S1 is created under MST selecting rsn1.
			4.Associate MST with resource resource RS associated with RT.
		  Expected Result:No Expected Result
		*/

			strFuncResult = objLogin.login(selenium, strAdmUserName,
					strAdmPassword);

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
				strFuncResult = objStatRes.createStatusReasn(selenium,
						strReasonName, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal = objStatRes.fetchStatReasonValue(selenium,
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
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST
						.selectStatusTypesAndFilMandFlds(selenium,
								strStatusTypeValue, statTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(selenium,
						strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(selenium,
						statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						selenium, statTypeName);
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
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						statTypeName, strStatusName1, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(
						selenium, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(selenium,
						statTypeName, strStatusName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(selenium,
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
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(selenium,
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
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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
						strResource, false, true, false, true);
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
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);

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
		/*
		* STEP :
		  Action:Update MST with S1 selecting rsn1 without providing any comments at time T1.
		  Expected Result:No Expected Result
		*/
		//20231

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
						strStatusName1, strStatusValue[0], strStatusName1,
						strStatusValue[0], strReasonVal, strReasonVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strLastDateUpdTime = "";
			String[] strArFunRes = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strRegGenTimeBef = strArFunRes[2] + ":" + strArFunRes[3];
				strLastDateUpdTime = strArFunRes[0] + " " + strArFunRes[1]
						+ " " + strCurYear + " " + strRegGenTimeBef;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				// Wait for ST to update
				Thread.sleep(60000);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user U1, navigate to Report>>Status
			 * Reports>>Status Snapshot Expected Result:No Expected Result
			 */
			// 20232

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
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
			String[] strArFunRes1 = new String[5];
			try {
				assertEquals("", strFuncResult);
				strArFunRes1 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes1[4];
				strGenDate = dts.converDateFormat(strArFunRes1[0]
						+ strArFunRes1[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTime = strArFunRes1[2] + ":" + strArFunRes1[3];
				strGenTimeHrs = strArFunRes1[2];
				strGenTimeMin = strArFunRes1[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		/*
		* STEP :
		  Action:Select time T1, radio button CSV and click on 'Generate Report'.
		  Expected Result:CSV report is generated and the reason is displayed in column 'Comment' for status type MST.
		*/
		//20233

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strGenDate, strGenTimeHrs, strGenTimeMin);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt < 300);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath1).exists());

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
								"Status Snapshot - "
										+ strGenDate
										+ " "
										+ strRegGenTime
										+ " "
										+ propEnvDetails
												.getProperty("TimeZone"), "",
								"", "", "", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType, strResource, statTypeName,
								strStatusName1, strReasonName + " ",
								strLastDateUpdTime }, };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "4089";
			gstrTO = "Update a multi status type selecting status reason and without comments and verify that reason is displayed in the column 'Comment' in the status snapshot report.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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
