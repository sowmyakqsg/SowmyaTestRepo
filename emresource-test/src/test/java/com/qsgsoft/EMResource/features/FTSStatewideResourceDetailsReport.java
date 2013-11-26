package com.qsgsoft.EMResource.features;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.General;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.Reports;
import com.qsgsoft.EMResource.shared.ResourceTypes;
import com.qsgsoft.EMResource.shared.Resources;
import com.qsgsoft.EMResource.shared.Roles;
import com.qsgsoft.EMResource.shared.StatusTypes;
import com.qsgsoft.EMResource.shared.ViewMap;
import com.qsgsoft.EMResource.shared.Views;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.GetProcessList;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/*******************************************************************
' Description       :This class includes requirement test cases from
' Requirement Group :Reports 
' Requirement       :Statewide Resource Details Report
' Date		        :07-October-2013
' Author	        :QSG
'-------------------------------------------------------------------
' Modified Date                                        Modified By
' <Date>                           	                   <Name>
'*******************************************************************/

public class FTSStatewideResourceDetailsReport {
	
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSEditUser");
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
	Properties propElementAutoItDetails;
	Properties pathProps;
	Selenium selenium, seleniumPrecondition;
	String gstrTimeOut;
	
	@Before
	public void setUp() throws Exception {

		dtStartDate = new Date();
		gstrBrowserName = "IE 8";
		gstrBuild = "";

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

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

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}
	
	@After
	public void tearDown() throws Exception {

		selenium.close();
		selenium.stop();

		seleniumPrecondition.close();
		seleniumPrecondition.stop();
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

		gdbTimeTaken = objOFC.TimeTaken(dtStartDate);// and execution time
		String FILE_PATH = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		FILE_PATH = pathProps.getProperty("Resultpath");
		Date_Time_settings dts = new Date_Time_settings();
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");

		// gstrBuild=PropEnvDetails.getProperty("Build");
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");

		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	


	//start//testFTS99088//
	/***************************************************************
	'Description		:Verify that user with run report right on resource and without 'Statewide resource detail report'
	 					 right DO NOT have the option to generate report.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/7/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date				           Modified By
	'Date					               Name
	***************************************************************/

	@Test
	public void testFTS99088() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();	
		Reports objReports = new Reports();
	try{	
		gstrTCID = "99088";	//Test Case Id	
		gstrTO = " Verify that user with run report right on resource and without 'Statewide resource detail report' right" +
				 " DO NOT have the option to generate report.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	
		
		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");

		String strFILE_PATH = pathProps.getProperty("TestData_path");
		String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
		String strLoginUserName = rdExcel.readData("Login", 3, 1);
		String strLoginPassword = rdExcel.readData("Login", 3, 2);
		String strRegn = rdExcel.readData("Login", 22, 4);
		// ST
		String strStatusTypeValues[] = new String[1];
		String statTypeName1 = "AutoST_1" + strTimeText;
		String strStatTypDefn = "Automation";
		String strStatusTypeValue = "Number";
		// RT
		String strRTValue[] = new String[1];
		String strResrcTypName = "AutoRT_1" + strTimeText;

		// RS
		String strResource = "AutoRs_1" + strTimeText;
		String strAbbrv = "A" + strTimeText;	
		String strStandResType = "Aeromedical";
		String strContFName = "auto";
		String strContLName = "qsg";	
		String strRSValue[] = new String[1];	

		// Search user criteria
		String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
									strFILE_PATH);
		String strByResourceType = rdExcel.readInfoExcel("User_Template",
							7, 12, strFILE_PATH);
		String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
							13, strFILE_PATH);
		String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
							14, strFILE_PATH);

		String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
		String strUsrFulName_1 = strUserName_1;
		String strInitPwd = rdExcel.readData("Login", 4, 2);
		String strConfirmPwd = rdExcel.readData("Login", 4, 2);	

		/*
		* STEP :
		  Action:Precondition:
	<br>
	<br>User U1 is created in 'Statewide Florida' region without 'Report-Statewide resource detail' right.
		  Expected Result:No Expected Result
		*/
		//584304


		try {
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);			
			strFuncResult = objLogin.navUserDefaultRgn(
					seleniumPrecondition, strRegn);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes
					.navStatusTypList(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		// ST
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
					seleniumPrecondition, strStatusTypeValue,
					statTypeName1, strStatTypDefn, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strStatusTypeValues[0] = objStatusTypes
					.fetchSTValueInStatTypeList(seleniumPrecondition,
							statTypeName1);

			if (strStatusTypeValues[0].compareTo("") != 0) {
				strFuncResult = "";
			} else {
				strFuncResult = "Failed to fetch status type value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		// RT
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResourceTypes
					.navResourceTypList(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
					seleniumPrecondition, strResrcTypName,
					strStatusTypeValues[0]);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResourceTypes.saveAndvrfyResType(
					seleniumPrecondition, strResrcTypName);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResourceTypes
					.fetchResTypeValueInResTypeList(seleniumPrecondition,
							strResrcTypName);
			if (strFuncResult.compareTo("") != 0) {

				strRTValue[0] = strFuncResult;
				strFuncResult = "";
			} else {
				strFuncResult = "Failed to fetch resource type value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		// RS

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResources.navResourcesList(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResources.navToCreateResourcePage(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objResources.createResourceWithMandFields(seleniumPrecondition,
					strResource, strAbbrv, strResrcTypName, strStandResType, strContFName, strContLName);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResources.saveAndNavToAssignUsr(seleniumPrecondition, strResource);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objResources.saveAndVerifyResourceInRSList(seleniumPrecondition, strResource);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResources.fetchResValueInResList(seleniumPrecondition,
					strResource);

			if (strFuncResult.compareTo("") != 0) {				
				strRSValue[0] = strFuncResult;
				strFuncResult = "";
			} else {
				strFuncResult = "Failed to fetch Resource value";
			}

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
					strUserName_1, strInitPwd, strConfirmPwd, strUsrFulName_1);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
					strResource, false, false, true, true);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
					strUserName_1, strByRole, strByResourceType, strByUserInfo, strNameFormat);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.logout(seleniumPrecondition);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		log4j.info("~~~~~PRECONDITION  - "+gstrTCID+" EXECUTION ENDS~~~~~");
		/*
		* STEP :
		  Action:Login as user U1
		  Expected Result:'Region Default' screen is displayed
				  'Reports' menu header is not displayed. (user does not have the option to generate statewide resource detail report)
		*/
		//584305
		log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		try{
			assertEquals("", strFuncResult);			
			strFuncResult = objReports.checkReportsHeaderPresentOrNot(selenium, false);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}			

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
		gstrTCID = "FTS-99088";
		gstrTO = "Verify that user with run report right on resource and without 'Statewide resource detail report' right DO NOT have the option to generate report.";
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

	//end//testFTS99088//
	

	//start//testFTS99039//
	/***************************************************************
	'Description		:Verify that report cannot be generated without providing mandatory data
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/7/2013
	'Author				:Suhas
	'---------------------------------------------------------------
	'Modified Date				               Modified By
	'Date					                   Name
	***************************************************************/

	@Test
	public void testFTS99039() throws Exception {
		String strFuncResult = "";		
		boolean blnLogin = false;
		Login objLogin = new Login();		
		CreateUsers objCreateUsers = new CreateUsers();
		Reports objReports = new Reports();
	try{	
		gstrTCID = "99039";	//Test Case Id	
		gstrTO = " Verify that report cannot be generated without providing mandatory data";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";
		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strFILE_PATH = pathProps.getProperty("TestData_path");		
		String strLoginUserName = rdExcel.readData("Login", 3, 1);
		String strLoginPassword = rdExcel.readData("Login", 3, 2);
		String strRegn = rdExcel.readData("Login", 22, 4);		
		String strStandResType = "Aeromedical";
		// Search user criteria
		String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
									strFILE_PATH);
		String strByResourceType = rdExcel.readInfoExcel("User_Template",
							7, 12, strFILE_PATH);
		String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
							13, strFILE_PATH);
		String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
							14, strFILE_PATH);

		//User
		String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
		String strUsrFulName_1 = strUserName_1;
		String strInitPwd = rdExcel.readData("Login", 4, 2);
		String strConfirmPwd = rdExcel.readData("Login", 4, 2);	

		

		/*
		* STEP :
		  Action:Precondition:
	<br>
	<br>User U1 is created in 'Statewide Florida' region and has 'Report-Statewide resource detail' right.
		  Expected Result:No Expected Result
		*/
		//584299
		log4j.info("~~~~~PRECONDITION - "+gstrTCID+" EXECUTION STARTS~~~~~");

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);			
			strFuncResult = objLogin.navUserDefaultRgn(
					seleniumPrecondition, strRegn);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}	


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
					strUserName_1, strInitPwd, strConfirmPwd, strUsrFulName_1);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}


		try {
			assertEquals("", strFuncResult);
			String strOptions = propElementDetails
					.getProperty("CreateNewUsr.AdvOptn.StateWideResourceDetailReport");
			strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
					strOptions, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
					strUserName_1, strByRole, strByResourceType, strByUserInfo, strNameFormat);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.logout(seleniumPrecondition);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		log4j.info("~~~~~PRECONDITION - "+gstrTCID+" EXECUTION ENDS~~~~~");

		/*
		* STEP :
		  Action:Login as user U1, navigate to Reports >> Resource reports and click on 'Statewide Resource Details' link
		  Expected Result:'Statewide Resource Detail Report' screen is displayed.
		*/
		//584300
		log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}


		try{
			assertEquals("", strFuncResult);
			strFuncResult = objReports.navResourceReportsMenu(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objReports.navToStatewideResourceDetailReportPage(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Click on 'Next' button.
		  Expected Result:'The following error occurred on this page:
	<br>At least one standard resource must be selected.' message is displayed
		*/
		//584301
		
		try{
			assertEquals("", strFuncResult);
			String strMsg="At least one standard resource must be selected.";
			strFuncResult = objReports.veifyErrorMsgInStatewideResDetailStepOne(selenium, strMsg);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		
		/*
		* STEP :
		  Action:Select any check box and click on 'Next'
		  Expected Result:'Statewide Resource Detail Report' screen is displayed.
		*/
		//584302

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objReports.selectStdResTypeForReport(selenium, "101", strStandResType);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		
		/*
		* STEP :
		  Action:Click on 'Generate Report'
		  Expected Result:'The following error occurred on this page:
	<br>At least one standard status must be selected.' message is displayed.
		*/
		//584303

		
		try{
			assertEquals("", strFuncResult);
			String strMsg="At least one standard status must be selected.";
			strFuncResult = objReports.verifyErrorMsgInStatewideResDetailReportStepTwoPage(selenium, strMsg);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

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
		gstrTCID = "FTS-99039";
		gstrTO = "Verify that report cannot be generated without providing mandatory data";
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

	//end//testFTS99039//
	//start//testFTS99093//
	/***************************************************************
	'Description		:Verify that newly created resources under resource type that are created 
	                     selecting standard resource type are displayed in the report generated.
	'Precondition		:
	'Arguments		`	:None
	'Returns			:None
	'Date				:10/15/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date				               Modified By
	'Date					                   Name
	***************************************************************/

	@Test
	public void testFTS99093() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Reports objReports = new Reports();
		try {
			gstrTCID = "99093"; // Test Case Id
			gstrTO = " Verify that newly created resources under resource type that are created selecting"
					+ " standard resource type are displayed in the report generated.";// Test
																						// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MMddyyyyhhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 22, 4);
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValue = "Number";
			// RT
			String strRTValue[] = new String[1];
			String strResrcTypName = "AutoRT_1" + strTimeText;
			// RS
			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "Abb";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// User name
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			// Report
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1" + gstrTCID + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1" + gstrTCID + strTimeText + ".xls";

			String strCSVFileNameRenamed = "StatewideResDetRep1" + gstrTCID
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			//Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Creating  ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeName1);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Creating RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName,
						strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Creating  RS

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, strStandResType, strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource);

				if (strFuncResult.compareTo("") != 0) {
					strRSValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Creating User
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
						seleniumPrecondition, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StateWideResourceDetailReport");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Logout
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION  - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			//Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports
						.navToStatewideResourceDetailReportPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objReports.selectStdResTypeForReport(selenium,
						"101", strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStdStatusVal = "3001";
				String strStdStatusName = "Ambulance/Aeromedical Availability";
				strFuncResult = objReports.selectStdStatusesForReport(selenium,
						strStdStatusVal, strStdStatusName);
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

				String[] strReportData = { "Statewide Resource Detail Report",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strReportData = { "Region", "Resource Name", "Type",
						"Address", "County", "Latitude", "Longitude",
						"EMResource ID", "AHA ID", "External ID", "Website",
						"Contact", "Phone 1", "Phone 2", "Fax", "Email",
						"Notes", "Ambulance/Aeromedical Availability" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResData[] = { strRegn, strResource, strResrcTypName,
						"", "", "", "", "\\d+", "", "", "",
						strContFName + " " + strContLName, "", "", "", "", "",
						"" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strResData, strCSVDownlRenamedPath);
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
			gstrTCID = "FTS-99093";
			gstrTO = "Verify that newly created resources under resource type that are created selecting"
					+ " standard resource type are displayed in the report generated.";
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

		// end//testFTS99093//

	}
	//start//testFTS99094//
	/***************************************************************
	'Description		:Verify that newly created status types associated with the resource type that are created selecting standard resource type are displayed in the report generated.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/15/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/

	@Test
	public void testFTS99094() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Reports objReports = new Reports();
		try {
			gstrTCID = "99094"; // Test Case Id
			gstrTO = " Verify that newly created status types associated with the resource type that are " +
					"created selecting standard resource type are displayed in the report generated.";// Test// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MMddyyyyhhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 22, 4);
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValue = "Number";
			String strStandardST = "BLS";
			// RT
			String strRTValue[] = new String[1];
			String strResrcTypName = "AutoRT_1" + strTimeText;
			// RS
			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "Abb";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// User name
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			// Report
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1" + gstrTCID + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1" + gstrTCID + strTimeText + ".xls";

			String strCSVFileNameRenamed = "StatewideResDetRep1" + gstrTCID
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			// Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Creating ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName1, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(
						seleniumPrecondition, strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeName1);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Creating RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName,
						strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RS

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, strStandResType, strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource);

				if (strFuncResult.compareTo("") != 0) {
					strRSValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating User
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
						seleniumPrecondition, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StateWideResourceDetailReport");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Logout
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION  - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			// Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports
						.navToStatewideResourceDetailReportPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objReports.selectStdResTypeForReport(selenium,
						"101", strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStdStatusVal = "4007";
				strFuncResult = objReports.selectStdStatusesForReport(selenium,
						strStdStatusVal, strStandardST);
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

				String[] strReportData = { "Statewide Resource Detail Report",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strReportData = { "Region", "Resource Name", "Type",
						"Address", "County", "Latitude", "Longitude",
						"EMResource ID", "AHA ID", "External ID", "Website",
						"Contact", "Phone 1", "Phone 2", "Fax", "Email",
						"Notes", "BLS" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResData[] = { strRegn, strResource, strResrcTypName,
						"", "", "", "", "\\d+", "", "", "",
						strContFName + " " + strContLName, "", "", "", "", "",
						"0" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strResData, strCSVDownlRenamedPath);
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
			gstrTCID = "FTS-99094";
			gstrTO = "Verify that newly created status types associated with the resource type that are" +
					" created selecting standard resource type are displayed in the report generated.";
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

	// end//testFTS99094//
	

	//start//testFTS126278//
	/***************************************************************
	'Description		:Verify that user with appropriate rights cannot view the resource details in the generated 'Statewide resource details' report whose resource types are deactivated.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/17/2013
	'Author				:Suhas
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/

	@Test
	public void testFTS126278() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		Roles objRoles = new Roles();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Reports objReports = new Reports();
		ViewMap objViewMap = new ViewMap();
		General objGeneral = new General();
		try {
			gstrTCID = "126278"; // Test Case Id
			gstrTO = " Verify that user with appropriate rights cannot view the resource details in the"
					+ " generated 'Statewide resource details' report whose resource types are deactivated.";// Test//
																												// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MMddyyyyhhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 22, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statNSTTypeName = "AutoNST_1" + strTimeText;
			String statTSTTypeName = "AutoTST" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValue = "Number";
			String strStatusTypeValue1 = "Text";
			String strStandardST = "Account Type";
			String strStandardST1 = "Average Wait Time";
			// RT
			String strRTValue[] = new String[2];
			String strResrcTypName1 = "AutoRT_1" + strTimeText;
			String strResrcTypName2 = "AutoRT_2" + strTimeText;
			// Role
			String strStatWideRole = "Rol" + strTimeText;
			String strRoleValue[] = new String[1];
			// RS
			String strResource1 = "AutoRs_1" + strTimeText;
			String strResource2 = "AutoRs_2" + strTimeText;
			String strAbbrv = "Abb";
			String strStandResType = "Ambulance";
			String strStandRTAmbulanceValue = "102";
			String strStandRTHospitalValue = "106";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[2];
			String strLongitude = "";
			String strLatitude = "";
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// User name
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			// Report
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1" + gstrTCID + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1" + gstrTCID + strTimeText + ".xls";

			String strCSVFileNameRenamed = "StatewideResDetRep1" + gstrTCID
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			/*
			 * STEP : Action:Precondition:
			 * 
			 * All the below mentioned test data is created in 'Statewide
			 * Florida' region. 1. Text status types TST1 is created with a
			 * standard status type 'Account Type'. 2. Number status type NST1
			 * is created with a standard status type 'Average Wait Time'. 3.
			 * Resource type RT1 is created selecting status types TST1.
			 * 4.Resource type RT2 is created selecting status types NST1. 5.
			 * Resource RS1 is created selecting standard resource type as
			 * 'Ambulance' under resource type RT1. 6. Resource RS2 is created
			 * selecting standard resource type as 'Ambulance' under resource
			 * type RT2. 7. User U1 is created in 'Statewide Florida' region,
			 * providing the following rights: a) Update right on resource RS1.
			 * b) Role to update the status types TST1, NST1 and selecting
			 * 'Report-Statewide resource details' right for a role. c)
			 * 'Setup-Resource type' right d)Both the status types are updated.
			 * Expected Result:No Expected Result
			 */
			// 662796
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Creating TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue1,
						statTSTTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(
						seleniumPrecondition, strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statTSTTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTSTTypeName);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statNSTTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(
						seleniumPrecondition, strStandardST1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statNSTTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statNSTTypeName);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName1,
						strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName1);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName2,
						strStatusTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName2);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrcTypName1, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrcTypName2, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.navToEditResourcePage(
						seleniumPrecondition, strResource2);
				strLongitude = seleniumPrecondition.getValue("id=longitude");
				log4j.info(strLongitude);

				char ch = '.';

				int intpos = strLongitude.indexOf(ch);

				String strLongitudeBefore = strLongitude.substring(0, intpos);
				strLongitude = strLongitude.substring(intpos, intpos + 4);

				strLongitude = strLongitudeBefore + strLongitude;
				log4j.info(strLongitude);

				strLatitude = seleniumPrecondition.getValue("id=latitude");

				intpos = strLatitude.indexOf(ch);
				String strLatitudeBefore = strLatitude.substring(0, intpos);
				strLatitude = strLatitude.substring(intpos, intpos + 4);

				strLatitude = strLatitudeBefore + strLatitude;
				log4j.info(strLatitude);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strStatWideRole);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strStatusTypeValues[0], "true" },
						{ strStatusTypeValues[1], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StateWideResourceDetailReport");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strStatWideRole);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strStatWideRole);

				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
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
						seleniumPrecondition, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName_2, strInitPwd,
						strConfirmPwd, strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource2, strRSValue[1],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_2, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Logout
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName_2, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap
						.navToRegionalMapView(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strUpdatTxtValue1 = "2";
				String strUpdatTxtValue2 = "3";
				strFuncResult = objViewMap.updateNumStatusTypeNew(
						seleniumPrecondition, strResource1, statTSTTypeName,
						strStatusTypeValues[0], strUpdatTxtValue1,
						strUpdatTxtValue2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strUpdatTxtValue1 = "2";
				String strUpdatTxtValue2 = "3";
				strFuncResult = objViewMap.updateNumStatusTypeNew(
						seleniumPrecondition, strResource2, statNSTTypeName,
						strStatusTypeValues[1], strUpdatTxtValue1,
						strUpdatTxtValue2);
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
			 * STEP : Action:Login as RegAdmin, navigate to Setup >> Resource
			 * type, and deactivate RT1. Expected Result:'RT1' is not displayed
			 * in the 'Resource Type List' screen.
			 */
			// 662797
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
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
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRTNew(
						selenium, strResrcTypName1, false);
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
			 * STEP : Action:Login as user U1 and navigate to Reports >>
			 * Resource reports Expected Result:'Resource Reports Menu' screen
			 * is displayed
			 */
			// 662798

			// Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports
						.navToStatewideResourceDetailReportPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[name='standardResourceTypeID']"
						+ "[value='" + strStandRTAmbulanceValue + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[name='standardResourceTypeID']"
						+ "[value='" + strStandRTHospitalValue + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Ambulance' and 'Hospital' are listed along with "
							+ "all the active resources on the page.  ");
				} else {
					strFuncResult = "'Ambulance' and 'Hospital' are NOT listed along with "
							+ "all the active resources on the page. ";
					log4j.info("'Ambulance' and 'Hospital' are NOT listed along with "
							+ "all the active resources on the page. ");

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Statewide resource details report' link
			 * Expected Result:'Ambulance' and 'Hospital' are listed along with
			 * all the active resources on the page.
			 */
			// 662799
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objReports.selectStdResTypeForReport(selenium,
						"102", strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select the check boxes corresponding to 'Ambulance'
			 * under 'Standard Resources' and click on 'Next' button. Expected
			 * Result:'Statewide Resource Detail Report' page is displayed with
			 * 'Standard statuses' listed. <br> <br>'Average Wait time' and
			 * 'Account Type' are listed on the page.
			 */
			// 662800

			try {
				assertEquals("", strFuncResult);

				String[] strStdStatusVal = { "7034", "4004" };
				String[] strStdStatusName = { strStandardST, strStandardST1 };
				strFuncResult = objReports.selectOnlyStdStatusesForReport(
						selenium, strStdStatusVal, strStdStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select the check box corresponding to 'Average wait
			 * time' and 'Account Type' under 'Standard statuses' and click on
			 * 'Generate Report' button. Expected Result:'Statewide Resource
			 * Detail Report' is generated in 'Excel' (.xlsx format) displaying
			 * appropriate data in all the columns. <br> <br>RS1 is not
			 * displayed. <br> <br>Details related to RS2 are displayed in the
			 * report generated.
			 */
			// 662801
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objReports.generateStatewideResDetRep(selenium);
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
						{ "Statewide Resource Detail Report", "", "", "", "",
								"", "", "", "", "", "", "", "", "", "", "", "",
								"", "" },
						{ "Region", "Resource Name", "Type", "Address",
								"County", "Latitude", "Longitude",
								"EMResource ID", "AHA ID", "External ID",
								"Website", "Contact", "Phone 1", "Phone 2",
								"Fax", "Email", "Notes", "Account Type",
								"Average Wait Time" } };

				String[] strReportData1 = { strRegn, strResource2,
						strResrcTypName2, "AL ", "Autauga County",
						strLongitude, strLatitude, strRSValue[1], "", "", "",
						"auto qsg", "", "", "", "", "", "", "2" };

				strFuncResult = objGeneral.readAndVerifySpecificDataInExcel(
						strReportData, strCSVDownlRenamedPath, strResource2,
						true, true, 2, strReportData1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResData[] = { strRegn, strResource1, "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strResData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(" All the necessary details are NOT displayed "
						+ "in the report", strFuncResult);
				log4j.info("Resource " + strResource1 + "is NOT displayed");
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-126278";
			gstrTO = "Verify that user with appropriate rights cannot view the resource details in the "
					+ "generated 'Statewide resource details' report whose resource types are deactivated.";
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

	// end//testFTS126278//
	
	//start//testFTS100380//
	/******************************************************************************
	'Description		:Verify that the report can be generated selecting multiple
	                      standard resources and standard statuses.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/23/2013
	'Author				:QSG
	'------------------------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	*******************************************************************************/

	@Test
	public void testFTS100380() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		Roles objRoles = new Roles();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Reports objReports = new Reports();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();
		try {
			gstrTCID = "100380"; // Test Case Id
			gstrTO = " Verify that the report can be generated selecting multiple standard resources" +
					" and standard statuses.";//TO																				// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MMddyyyyhhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 22, 4);
			// ST
			String strStatusTypeValues[] = new String[3];
			String statNSTTypeName = "AutoNST_1" + strTimeText;
			String statTSTTypeName = "AutoTST" + strTimeText;
			String statNEDTypeName = "AutoNed" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusTypeValue = "Number";
			String strStatusTypeValue1 = "NEDOCS Calculation";
			String strTSTValue = "Text";
			String strStandardST = "BLS";
			String strStandardST1 = "Account Type";
			// RT
			String strRTValue[] = new String[3];
			String strResrcTypName1 = "AutoRT_1" + strTimeText;
			String strResrcTypName2 = "AutoRT_2" + strTimeText;
			String strResrcTypName3 = "AutoRT_3" + strTimeText;
			// Role
			String strStatWideRole = "Rol" + strTimeText;
			String strRoleValue[] = new String[1];
			// RS
			String strResource1 = "AutoRs_1" + strTimeText;
			String strResource2 = "AutoRs_2" + strTimeText;
			String strResource3 = "AutoRs_3" + strTimeText;
			String strAbbrv = "Abb";
			String strStandResType = "Ambulance";
			String strStandResType1 = "Hospital";
			String strStandResType2 = "Clinic";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[3];
			String strLongitude = "";
			String strLatitude = "";
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// User name
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			// Report
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1" + gstrTCID + strTimeText + ".xlsx";
			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1" + gstrTCID + strTimeText + ".xls";
			String strCSVFileNameRenamed = "StatewideResDetRep1" + gstrTCID
					+ strTimeText;
			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			// Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Creating NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statNSTTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(
						seleniumPrecondition, strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statNSTTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statNSTTypeName);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statTSTTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStandardSTInCreateST(
						seleniumPrecondition, strStandardST1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statTSTTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTSTTypeName);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating NEDOC
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue1,
						statNEDTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statNEDTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statNEDTypeName);

				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName1,
						strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName1);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName2,
						strStatusTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName2);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName3,
						strStatusTypeValues[2]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName3);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[2] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrcTypName1, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrcTypName2, strContFName, strContLName, strState,
						strCountry, strStandResType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource3, strAbbrv,
						strResrcTypName3, strContFName, strContLName, strState,
						strCountry, strStandResType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[2] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource3);
				if (strRSValue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.navToEditResourcePage(
						seleniumPrecondition, strResource2);
				strLongitude = seleniumPrecondition.getValue("id=longitude");
				log4j.info(strLongitude);

				char ch = '.';

				int intpos = strLongitude.indexOf(ch);

				String strLongitudeBefore = strLongitude.substring(0, intpos);
				strLongitude = strLongitude.substring(intpos, intpos + 4);

				strLongitude = strLongitudeBefore + strLongitude;
				log4j.info(strLongitude);

				strLatitude = seleniumPrecondition.getValue("id=latitude");

				intpos = strLatitude.indexOf(ch);
				String strLatitudeBefore = strLatitude.substring(0, intpos);
				strLatitude = strLatitude.substring(intpos, intpos + 4);

				strLatitude = strLatitudeBefore + strLatitude;
				log4j.info(strLatitude);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strStatWideRole);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strStatusTypeValues[0], "true" },
						{ strStatusTypeValues[1], "true" },
						{ strStatusTypeValues[2], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StateWideResourceDetailReport");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strStatWideRole);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strStatWideRole);

				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
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
						seleniumPrecondition, strUserName_2, strInitPwd,
						strConfirmPwd, strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource2, strRSValue[1],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource3, strRSValue[2],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_2, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Logout
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			// Login
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_2,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strUpdatTxtValue1 = "2";
				String strUpdatTxtValue2 = "3";
				strFuncResult = objViewMap.updateNumStatusTypeNew(selenium,
						strResource1, statNSTTypeName, strStatusTypeValues[0],
						strUpdatTxtValue1, strUpdatTxtValue2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strUpdatTxtValue1 = "2";
				String strUpdatTxtValue2 = "3";
				strFuncResult = objViewMap.updateNumStatusTypeNew(
						selenium, strResource2, statTSTTypeName,
						strStatusTypeValues[1], strUpdatTxtValue1,
						strUpdatTxtValue2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource3);
		
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6" };
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(selenium,
						strUpdateValue1, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports
						.navToStatewideResourceDetailReportPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStdResTypeVal = { "102", "106" };
				;
				String[] strStdRt = { strStandResType, strStandResType1 };
				strFuncResult = objReports.selectOnlyStdResTypeForReport(
						selenium, strStdResTypeVal, strStdRt);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.selectStdResTypeForReport(selenium,
						"136", strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStdStatusVal = { "4007", "7034" };
				String[] strStdStatusName = { strStandardST, strStandardST1 };
				strFuncResult = objReports.selectOnlyStdStatusesForReport(
						selenium, strStdStatusVal, strStdStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objReports.generateStatewideResDetRep(selenium);
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

				String[] strReportData = { "Statewide Resource Detail Report",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "", "" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strReportData = { "Region", "Resource Name", "Type",
						"Address", "County", "Latitude", "Longitude",
						"EMResource ID", "AHA ID", "External ID", "Website",
						"Contact", "Phone 1", "Phone 2", "Fax", "Email",
						"Notes", "Account Type", "BLS" };
				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strReportData1 = { strRegn, strResource1,
						strResrcTypName1, "AL ", "Autauga County",
						strLongitude, strLatitude, strRSValue[0], "", "", "",
						"auto qsg", "", "", "", "", "", "", "2" };
				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strReportData1, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResData[] = { strRegn, strResource2,
						strResrcTypName2, "AL ", "Autauga County",
						strLongitude, strLatitude, strRSValue[1], "", "", "",
						"auto qsg", "", "", "", "", "", "2", "" };
				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strResData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResData[] = { strRegn, strResource3,
						strResrcTypName3, "AL ", "Autauga County",
						strLongitude, strLatitude, strRSValue[2], "", "", "",
						"auto qsg", "", "", "", "", "", "", "" };
				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strResData, strCSVDownlRenamedPath);
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
			gstrTCID = "FTS-100380";
			gstrTO = "Verify that the report can be generated selecting multiple standard resources" +
					" and standard statuses.";
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

	// end//testFTS100380//
}
