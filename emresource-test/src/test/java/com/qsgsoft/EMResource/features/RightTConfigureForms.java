package com.qsgsoft.EMResource.features;

/**********************************************************************
' Description		:This class contains test cases from requirement
' Requirement		:RightTConfigureForms
' Requirement Group	:Setting up users  
� Product		    :EMResource v3.19
' Date			    :4/June/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/


import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.SearchUserByDiffCrteria;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class RightTConfigureForms {
	
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.settingUpRegions");
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
	Selenium selenium;
	String gstrTimeOut;



/****************************************************************************************************************
	* This function is called the setup() function which is executed before every test.
	*
	* The function will take care of creating a new selenium session for every test
	*
****************************************************************************************************************/

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
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));
		

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}
	
	/****************************************************************************************************************	*
	    * This function is called the teardown() function which is executed after every test.
		* The function will take care of stopping the selenium session for every test and writing the execution
		* result of the test. 
		*
	****************************************************************************************************************/

		@After
		public void tearDown() throws Exception {
			
			selenium.close();
			selenium.stop();

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
			gstrReason=gstrReason.replaceAll("'", " ");
			objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
					gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
					gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
		}
		

		/***************************************************************
		'Description		:Verify that only a system admin users can assign the right 'Form - User may create and modify forms' to users from 'Setup>>Setup Users'.
		'Precondition		:
		'Arguments		    :None
		'Returns		    :None
		'Date			:6/15/2012
		'Author			:QSG
		'---------------------------------------------------------------
		'Modified Date				Modified By
		'Date					Name
		***************************************************************/

		@Test
		public void testBQS46608() throws Exception {
		try{	
			
			Login objLogin = new Login();
			CreateUsers objCreateUsers = new CreateUsers();
			SearchUserByDiffCrteria objSearchUserByDiffCrteria=new SearchUserByDiffCrteria();
			
			gstrTCID = "46608";	//Test Case Id	
			gstrTO = " Verify that only a system admin users can assign the right 'Form - User may create and modify forms' to users from 'Setup>>Setup Users'.";//Test Objective
			gstrReason = "";
			gstrResult = "FAIL";	

			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");	
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strLoginUserName="";
			String strLoginPassword="";
			//User Data
			String strUserName1="AutoUsr1"+System.currentTimeMillis();
			String strUserName2="AutoUsr2"+System.currentTimeMillis();
			String strInitPwd=rdExcel.readData("Login", 4, 2);
			String strConfirmPwd=rdExcel.readData("Login", 4, 2);
			String strUsrFulName="autouser";
			//Search user
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";

			/*
			* STEP :
			  Action:Login as RegAdmin and navigate to Setup>>Users
			  Expected Result:No Expected Result
			*/
			//269787

			try {
				assertEquals("", strFuncResult);
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try{
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium,strRegn);
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

			/*
			* STEP :
			  Action:Select to create a new user
			  Expected Result:No Expected Result
			*/
			//269803
			
			/*
			* STEP :
			  Action:Provide mandatory data and select the right 'Form - User may create and modify forms' under 'Advanced Options' and save the user U1
			  Expected Result:User U1 is created and is listed in the 'Users List' screen.
			*/
			//269811

			try{
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium, strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}

			try{
				assertEquals("", strFuncResult);
				String strOptions=(propElementDetails.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms"));
				strFuncResult = objCreateUsers.advancedOptns(selenium, strOptions, true);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			try{
				assertEquals("", strFuncResult);
				String strOptions=(propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"));
				strFuncResult = objCreateUsers.advancedOptns(selenium, strOptions, true);
				selenium.click(propElementDetails.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}


			try{
				assertEquals("", strFuncResult);	
				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
						12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
						strFILE_PATH);

				strFuncResult = objSearchUserByDiffCrteria.searchUserByDifCriteria(selenium, strUserName1, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2]"
								+ "[text()='" + strUserName1 + "']"));

				log4j.info("User " + strUserName1
						+ " is listed in the 'Users List' "
						+ "screen under Setup ");

			} catch (AssertionError Ae) {
				gstrReason = "User " + strUserName1
						+ " is NOT listed in the 'Users List'"
						+ " screen under Setup " + Ae;
				log4j.info("User U1 is NOT listed in the 'Users List'"
						+ " screen under Setup " + Ae);
			}

			/*
			* STEP :
			  Action:Select to edit user U1
			  Expected Result:The checkbox corresponding to the right 'Form - User may create and modify forms' under 'Advanced Options' remains checked.
			*/
			//269812

			try {
				assertEquals("", strFuncResult);
				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
						12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
						strFILE_PATH);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
				try {
					assertTrue(selenium.isChecked(propElementDetails
							.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms")));
					log4j
							.info("The checkbox corresponding to the right 'Form - User may create "
									+ "and modify forms' under 'Advanced Options' remains checked. ");

					selenium.click(propElementDetails
							.getProperty("CreateNewUsr.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
				} catch (AssertionError ae) {
					log4j
							.info("The checkbox corresponding to the right 'Form - User may create "
									+ "and modify forms' under 'Advanced Options' remains  NOT checked. ");

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			* STEP :
			  Action:Create another user U2 without selecting the right 'Form - User may create and modify forms'
			  Expected Result:User U2 is created and is listed in the 'Users List' screen.
			*/
			//269813
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium, strUserName2, strInitPwd, strConfirmPwd, strUsrFulName);
				
				selenium.click(propElementDetails.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}


			try{
				assertEquals("", strFuncResult);
				
				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
						12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
						strFILE_PATH);

				strFuncResult = objSearchUserByDiffCrteria.searchUserByDifCriteria(selenium, strUserName2, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2]"
								+ "[text()='" + strUserName2 + "']"));

				log4j.info("User " + strUserName2
						+ " is listed in the 'Users List' "
						+ "screen under Setup ");

			} catch (AssertionError Ae) {
				gstrReason = "User " + strUserName2
						+ " is NOT listed in the 'Users List'"
						+ " screen under Setup " + Ae;
				log4j.info("User U1 is NOT listed in the 'Users List'"
						+ " screen under Setup " + Ae);
			}


			/*
			* STEP :
			  Action:Select to edit user U2, select the right 'Form - User may create and modify forms' under 'Advanced Options' and save
			  Expected Result:No Expected Result
			*/
			//269814
			
			try{
				assertEquals("", strFuncResult);
				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
						12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
						strFILE_PATH);

				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName2, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("", strFuncResult);
				String strOptions=(propElementDetails.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms"));
				strFuncResult = objCreateUsers.advancedOptns(selenium, strOptions, true);
				selenium.click(propElementDetails.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
		
			/*
			* STEP :
			  Action:Select to edit user U2
			  Expected Result:The checkbox corresponding to the right 'Form - User may create and modify forms' under 'Advanced Options' remains checked.
			*/
			//269815
			
			try{
				assertEquals("", strFuncResult);
				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
						12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
						strFILE_PATH);

				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName2, strByRole, strByResourceType, strByUserInfo, strNameFormat);
				
				try {
					assertTrue(selenium.isChecked(propElementDetails
							.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms")));
					log4j
							.info("The checkbox corresponding to the right 'Form - User may create "
									+ "and modify forms' under 'Advanced Options' remains checked. ");

					selenium.click(propElementDetails
							.getProperty("CreateNewUsr.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
				} catch (AssertionError ae) {
					log4j
							.info("The checkbox corresponding to the right 'Form - User may create "
									+ "and modify forms' under 'Advanced Options' remains  NOT checked. ");

				}

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

			/*
			* STEP :
			  Action:Login as any user U1 with 'Users - Setup User Accounts' right and navigate to Setup>>Users
			  Expected Result:No Expected Result
			*/
			//269822


			try{
				assertEquals("", strFuncResult);
				strLoginUserName = strUserName1;
				strLoginPassword =strInitPwd;
				strFuncResult = objLogin.newUsrLogin(selenium, strLoginUserName, strLoginPassword);
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

			/*
			* STEP :
			  Action:Select to create a new user
			  Expected Result:Under 'Advanced Options', the right 'Form - User may create and modify forms' is not available.
			*/
			//269823
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToCreateUserPage(selenium);
				try
				{
				assertFalse(selenium.isElementPresent(propElementDetails.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms")));
				log4j.info("Under 'Advanced Options', the right 'Form - User may create and modify forms' is NOT available.");
				selenium.click("//input[@value='Cancel']");
				selenium.waitForPageToLoad(gstrTimeOut);
				}catch(AssertionError ae)
				{
					log4j.info("Under 'Advanced Options', the right 'Form - User may create and modify forms' is  available.");
				}
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}

			/*
			* STEP :
			  Action:Select to edit user U2
			  Expected Result:Under 'Advanced Options', the right 'Form - User may create and modify forms' is not available.
			*/
			//269837
			
			try{
				assertEquals("", strFuncResult);
				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
						12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
						strFILE_PATH);

				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName2, strByRole, strByResourceType, strByUserInfo, strNameFormat);
				try
				{
				assertFalse(selenium.isVisible(propElementDetails.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms")));
				log4j.info("Under 'Advanced Options', the right 'Form - User may create and modify forms' is NOT available.");
				selenium.click("//input[@value='Cancel']");
				selenium.waitForPageToLoad(gstrTimeOut);
				}catch(AssertionError ae)
				{
					log4j.info("Under 'Advanced Options', the right 'Form - User may create and modify forms' is  available.");
				}
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			

			try{
				assertEquals("", gstrReason);
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
			gstrTCID = "BQS-46608";
			gstrTO = "Verify that only a system admin users can assign the right 'Form - User may create and modify forms' to users from 'Setup>>Setup Users'.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
			+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		   }
		}




}
