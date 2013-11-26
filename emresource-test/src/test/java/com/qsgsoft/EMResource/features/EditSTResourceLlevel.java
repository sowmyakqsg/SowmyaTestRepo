package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;


import java.util.Date;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;

import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


/**********************************************************************
' Description :This class includes EditSTResourceLlevel requirement 
'				testcases
' Precondition:
' Date		  :8-June-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class EditSTResourceLlevel  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EditSTResourceLlevel");
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
		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
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
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
		
		selenium.close();
		
		 // kill browser
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
		    gslsysDateTime, gstrBrowserName, gstrBuild,strSessionId);
	}
	
	
	/********************************************************************************
	'Description	:Add a status type to a resource and verify that changing the 
	'				 resource type of the resource does not change the resource level status types.
	'					Preconditions:
					1. Resources RS1 is created under RT1 associated with ST1 and ST3.
					2. Resource RS2 is created under RT2 associated with ST2 and ST3.
					3. User U1 has 'Setup Resource Type' and 'Setup Resources' rights 
	'Arguments		:None
	'Returns		:None
	'Date	 		:8-June-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS36746() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		Resources objResources=new Resources();
		StatusTypes objST = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		try {
			gstrTCID = "BQS-36746 ";
			gstrTO = "Add a status type to a resource and verify "
					+ "that changing the resource type of the resource "
					+ "does not change the resource level status types.";
			gstrResult = "FAIL";
			gstrReason = "";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			log4j
					.info("~~~~~TEST CASE "+gstrTCID+" EXECUTION STATRTS~~~~~");
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strInitPwd = "abc123";
			String strConfirmPwd = strInitPwd;
			String strUsrFulName = "FN" + strUserName;
			
			String strLoginUserName="";
			String strLoginPassword="";
			
			String strRegn="";
			
			String[] strRT = new String[2];
			
			strLoginUserName=rdExcel.readData("Login", 3, 1);
			strLoginPassword=rdExcel.readData("Login", 3, 2);
			
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strStatType1 = "AutoSt1_" + strTimeText;
			String strStatType2 = "AutoSt2_" + strTimeText;
			String strStatType3 = "AutoSt3_" + strTimeText;
			
			String strTxtStatTypeValue = "Text";

			String strTxtStatTypDefn = "Auto";
			String[] strST = new String[3];
			String strResType1 = "AutoRt1_" + strTimeText;
			String strResType2 = "AutoRt2_" + strTimeText;
			
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			
			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[2];
			strRegn=rdExcel.readData("Login", 3, 4);
		
			
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			
			
			/*
			 * STEP 2: Login as user U1 and navigate to Setup>>Resources screen, click
			 * on 'Edit Status Types' link corresponding to resource RS1<-> 'Edit
			 * Resource-Level Status Types' screen is displayed.
			 */
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);
			
			
			try{
				assertEquals("",strFuncResult);
						
				strFuncResult=objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTxtStatTypeValue, strStatType1, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTxtStatTypeValue, strStatType2, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTxtStatTypeValue, strStatType3, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType3);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
						strResType1, "css=input[name='statusTypeID'][value='"
								+ strST[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(seleniumPrecondition,strST[2], true);
																
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRT[0] = objRT.fetchRTValueInRTList(seleniumPrecondition, strResType1);
				if (strRT[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResType2, "css=input[name='statusTypeID'][value='"
								+ strST[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(seleniumPrecondition,strST[2], true);
																
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRT[1] = objRT.fetchRTValueInRTList(seleniumPrecondition, strResType2);
				if (strRT[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
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
						strResource1, strAbbrv, strResType1, strContFName,
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
						strResource2, strAbbrv, strResType2, strContFName,
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
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
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
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUserName, strByRole, strByResourceType,
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
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName, strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("",strFuncResult);
			
				strFuncResult=objResources.navResourcesList(selenium);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
		
			try{
				assertEquals("",strFuncResult);				
			
				strFuncResult=objResources.navToEditRTInResourcePage(selenium, strResource1);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}

			
			/*
			 * STEP 3: Select check box corresponding to ST2 and save the page User is
			 * returned to the 'Resource List' screen
			 */
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strST[1], true);
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
			 * STEP 4: Navigate to Setup>>Resource, edit resource RS1 and change the
			 * resource type form RT1 to RT2 and Save.<-> No Expected Result
			 */
			
			try {
				assertEquals("", strFuncResult);
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
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
			
			try{
				assertEquals("",strFuncResult);	
				strFuncResult=objResources.navResourcesList(selenium);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.navToEditResourcePage(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.selctRT(selenium, strResType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 5: Click on the 'Edit Resource-Level Status Types' screen Check
			 * boxes corresponding to Status Types ST2 and ST3 are checked and
			 * disabled.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.navToEditRTInResourcePage(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objResources.verifySTisEditableOrNot(selenium,
						strST[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objResources.verifySTisEditableOrNot(selenium,
						strST[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/* STEP 6: Click on Cancel <->User is returned to Resource List screen */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.backToRSListPgeFrmEditResourceLevelST(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 7: Navigate to Setup>>Resource, edit resource RS1 and change the
			 * resource type form RT2 to RT1 and Save. No Expected Result
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.navToEditResourcePage(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.selctRT(selenium, strResType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * STEP 8: Click on the 'Edit Resource-Level Status Types' screen Check
			 * boxes corresponding to Status Types ST1 and ST3 are checked and
			 * disabled.
			 * 
			 * Status type ST2 is checked and is enabled.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.navToEditRTInResourcePage(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.verifySTisEditableOrNot(selenium,
						strST[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.verifySTisEditableOrNot(selenium,
						strST[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.verifySTisCheckedOrNot(selenium, strST[1], true);
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
			gstrTCID = "BQS-36746";
			gstrTO = "Add a status type to a resource and verify that"
					+ " changing the resource type of the resource does "
					+ "not change the resource level status types.";
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

	
